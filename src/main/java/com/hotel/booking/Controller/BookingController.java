package com.hotel.booking.Controller;

import com.hotel.booking.DTO.BookingRequest;
import com.hotel.booking.DTO.BookingResponse;
import com.hotel.booking.Entity.User;
import com.hotel.booking.Repository.UserRepository;
import com.hotel.booking.Service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookingService bookingService;

    @PostMapping("/{hotelId}")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public ResponseEntity<BookingResponse> createBooking(Principal principal , @PathVariable Long hotelId , @RequestBody BookingRequest bookingRequest) {

        String email = principal.getName();
        Long userId = userRepository.findByEmail(email).getId();

        LocalDate checkIn = bookingRequest.getCheckInDate();
        LocalDate checkOut = bookingRequest.getCheckOutDate();

        BookingResponse bookingResponse = bookingService.createBooking(userId, hotelId, checkIn, checkOut);

        return ResponseEntity.ok(bookingResponse);
    }

//    @GetMapping("/{bookingId}")
//    @PreAuthorize("hasAuthority('CUSTOMER')")
//    public ResponseEntity<BookingResponse> getBooking(@PathVariable Long bookingId) {
//
//        BookingResponse bookingResponse = bookingService.getBookingDetails(bookingId);
//
//        return ResponseEntity.ok(bookingResponse);
//    }

    @DeleteMapping("/{bookingId}")
    @PreAuthorize("hasAnyAuthority('HOTEL_MANAGER','CUSTOMER')")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long bookingId, Principal principal) {

        String email = principal.getName();
        User currentUser = userRepository.findByEmail(email);

        bookingService.cancelBooking(bookingId,currentUser.getId(), currentUser.getRole());

        return ResponseEntity.noContent().build();
    }

        @GetMapping("/myBookings")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public ResponseEntity<List<BookingResponse>> getMyBookings(Principal principal) {
        String email = principal.getName();
        Long userId = userRepository.findByEmail(email).getId();

        List<BookingResponse> bookings = bookingService.getBookingsByUser(userId);

        return ResponseEntity.ok(bookings);
    }
    @GetMapping("/active")
    @PreAuthorize("hasAuthority('HOTEL_MANAGER')")
    public ResponseEntity<List<BookingResponse>> getActiveBookingsByManager(
            Principal principal,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        String email = principal.getName();
        Long managerId = userRepository.findByEmail(email).getId();

        List<BookingResponse> activeBookings = bookingService.getActiveBookingsByManager(managerId, date);
        return ResponseEntity.ok(activeBookings);
    }



}
