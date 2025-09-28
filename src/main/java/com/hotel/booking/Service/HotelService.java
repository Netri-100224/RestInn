package com.hotel.booking.Service;

import com.hotel.booking.DTO.HotelRequest;
import com.hotel.booking.DTO.HotelResponse;
import com.hotel.booking.Entity.Hotel;
import com.hotel.booking.Entity.User;
import com.hotel.booking.Exception.HotelNotFoundException;
import com.hotel.booking.Repository.BookingRepository;
import com.hotel.booking.Repository.HotelRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HotelService {

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    BookingRepository bookingRepository;

    // @Transactional
    public HotelResponse createHotel(HotelRequest hotelRequest, User manager) {
        Hotel hotel = Hotel.builder()
                .name(hotelRequest.getName())
                .location(hotelRequest.getLocation())
                .description(hotelRequest.getDescription())
                .totalRooms(hotelRequest.getTotalRooms())
                .availableRooms(hotelRequest.getAvailableRooms())
                .manager(manager)
                .build();

        Hotel savedHotel = hotelRepository.save(hotel);

        return mapToResponse(savedHotel);
    }

    public Page<HotelResponse> getPaginatedHotels(int page, int size, String sortBy, boolean asc) {
        Sort sort = asc ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
            Pageable pageable = PageRequest.of(page, size, sort);

        Page<Hotel> hotelPage = hotelRepository.findAll(pageable);

        return hotelPage.map(this::mapToResponse);
    }

    public List<HotelResponse> getAvailableHotelsByLocation(String location) {
        List<Hotel> hotels = hotelRepository.findAvailableHotelsByLocation(location);

        return hotels.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public HotelResponse updateHotel(Long id, HotelRequest request, Long managerId) {
        // Only find the hotel if it's managed by this manager
        Hotel hotel = hotelRepository.findByIdAndManagerId(id, managerId)
                .orElseThrow(() -> new AccessDeniedException("You are not authorized to update this hotel."));

        hotel.setName(request.getName());
        hotel.setAvailableRooms(request.getAvailableRooms());
        // update other fields if needed

        Hotel updatedHotel = hotelRepository.save(hotel);
        return mapToResponse(updatedHotel);
    }


    @Transactional
    public void deleteHotel(Long id) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new HotelNotFoundException("Hotel with id "+ id +"not found"));
        hotelRepository.delete(hotel);
    }

    private HotelResponse mapToResponse(Hotel hotel) {
        return HotelResponse.builder()
                .id(hotel.getId())
                .name(hotel.getName())
                .location(hotel.getLocation())
                .description(hotel.getDescription())
                .totalRooms(hotel.getTotalRooms())
                .availableRooms(hotel.getAvailableRooms())
                .build();
    }
}



