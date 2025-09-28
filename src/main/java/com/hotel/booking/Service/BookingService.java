package com.hotel.booking.Service;

import com.hotel.booking.DTO.BookingMapper;
import com.hotel.booking.DTO.BookingResponse;
import com.hotel.booking.Entity.Booking;
import com.hotel.booking.Entity.Hotel;
import com.hotel.booking.Entity.Role;
import com.hotel.booking.Entity.User;
import com.hotel.booking.Exception.*;
import com.hotel.booking.Repository.BookingRepository;
import com.hotel.booking.Repository.HotelRepository;
import com.hotel.booking.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    BookingMapper bookingMapper;

    public BookingResponse createBooking(Long userId, Long hotelId, LocalDate checkInDate, LocalDate checkOutDate) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new HotelNotFoundException("Hotel not found"));

        if (hotel.getAvailableRooms() <= 0) {
            throw new HotelRoomsNotAvailableException("No rooms available for the selected hotel");
        }

        if (checkInDate.isBefore(LocalDate.now())) {
            throw new IncorrectDateException("Check-in date must be in the future");
        }

        if (checkOutDate.isBefore(checkInDate)) {
            throw new IncorrectDateException("Check-out date must be after check-in date");
        }


        List<Booking> overlaps = bookingRepository.findOverlappingBookings(hotelId, checkInDate, checkOutDate);
        if (!overlaps.isEmpty()) {
            throw new RuntimeException("Hotel already booked for selected dates");
        }
        // Build
        Booking booking = Booking.builder()
                .user(user)
                .hotel(hotel)
                .checkInDate(checkInDate)
                .checkOutDate(checkOutDate)
                .build();

        // Update available rooms
        hotel.setAvailableRooms(hotel.getAvailableRooms() - 1);
        hotelRepository.save(hotel);

        //save booking
        Booking savedBooking = bookingRepository.save(booking);

        BookingResponse response = BookingResponse.builder()
                .bookingId(savedBooking.getId())
                .hotelId(savedBooking.getHotel().getId())
                .checkInDate(savedBooking.getCheckInDate())
                .checkOutDate(savedBooking.getCheckOutDate())
                .build();

        return response;
    }

    public BookingResponse getBookingDetails(Long bookingId) {

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException("Booking not found"));

        BookingResponse response = BookingResponse.builder()
                .bookingId(booking.getId())
                .hotelId(booking.getHotel().getId())
                .checkInDate(booking.getCheckInDate())
                .checkOutDate(booking.getCheckOutDate())
                .build();

        return response;
    }

    public void cancelBooking(Long bookingId, Long requesterId, Role requesterRole) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException("Booking not found"));

        boolean isManager = requesterRole == Role.HOTEL_MANAGER;
        boolean isCustomer = requesterRole == Role.CUSTOMER;

        if (isCustomer) {
            // Customer can delete only their own bookings
            if (!booking.getUser().getId().equals(requesterId)) {
                throw new AccessDeniedException("You are not allowed to delete this booking");
            }
        } else if (isManager) {
            // Manager can delete booking only if they manage the hotel
            if (!booking.getHotel().getManager().getId().equals(requesterId)) {
                throw new AccessDeniedException("You are not allowed to delete this booking");
            }
        } else {
            // Other roles cannot delete booking
            throw new AccessDeniedException("You are not allowed to delete this booking");
        }

        // Restore room availability
        Hotel hotel = booking.getHotel();
        hotel.setAvailableRooms(hotel.getAvailableRooms() + 1);
        hotelRepository.save(hotel);

        // Delete booking
        bookingRepository.delete(booking);
    }




    public List<BookingResponse> getBookingsByUser(Long userId) {
        List<Booking> bookings = bookingRepository.findBookingsByUserId(userId);

        return bookings.stream()
                .map(booking -> BookingResponse.builder()
                        .bookingId(booking.getId())
                        .hotelId(booking.getHotel().getId())
                        .checkInDate(booking.getCheckInDate())
                        .checkOutDate(booking.getCheckOutDate())
                        .build())
                .collect(Collectors.toList());
    }


    public List<BookingResponse> getActiveBookings(LocalDate date) {
        List<Booking> bookings = bookingRepository.findActiveBookings(date);

        return bookings.stream()
                .map(booking -> BookingResponse.builder()
                        .bookingId(booking.getId())
                        .hotelId(booking.getHotel().getId())
                        .checkInDate(booking.getCheckInDate())
                        .checkOutDate(booking.getCheckOutDate())
                        .build())
                .collect(Collectors.toList());
    }

    public List<BookingResponse> getActiveBookingsByManager(Long managerId, LocalDate date) {
        // Find the hotel managed by this manager
        Hotel hotel = hotelRepository.findByManagerId(managerId)
                .orElseThrow(() -> new IllegalArgumentException("Manager does not manage any hotel"));

        // Get active bookings for that hotel on the given date
        List<Booking> activeBookings = bookingRepository.findActiveBookingsByHotelIdAndDate(hotel.getId(), date);

        // Map entities to DTOs and return
        return activeBookings.stream()
                .map(bookingMapper::toResponse)
                .collect(Collectors.toList());
    }

}
