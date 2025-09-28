package com.hotel.booking.Controller;

import com.hotel.booking.DTO.HotelRequest;
import com.hotel.booking.DTO.HotelResponse;
import com.hotel.booking.Entity.User;
import com.hotel.booking.Repository.UserRepository;
import com.hotel.booking.Service.HotelService;
import com.hotel.booking.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;


@RestController
@RequestMapping("/api/hotels")
public class HotelController {

    @Autowired
    private HotelService hotelService;
    @Autowired
    private UserRepository userRepository;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('HOTEL_MANAGER','ADMIN')")
    public ResponseEntity<HotelResponse> createHotel(Principal principal, @Valid @RequestBody HotelRequest hotelRequest) {
        String managerEmail = principal.getName();
        User manager = userRepository.findByEmail(managerEmail);
        if (manager == null) {
            throw new UsernameNotFoundException("Manager not found");
        }

        HotelResponse response = hotelService.createHotel(hotelRequest, manager);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    //@PreAuthorize("hasAnyAuthority('ADMIN', 'HOTEL_MANAGER', 'CUSTOMER')")
    public ResponseEntity<Page<HotelResponse>> getAllHotels(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "true") boolean asc) {

        Page<HotelResponse> hotels = hotelService.getPaginatedHotels(page, size, sortBy, asc);
        return ResponseEntity.ok(hotels);
    }

    @GetMapping("/searchByLocation")
   // @PreAuthorize("hasAnyAuthority('ADMIN', 'HOTEL_MANAGER', 'CUSTOMER')")
    public ResponseEntity<List<HotelResponse>> searchHotelsByLocation(@RequestParam String location) {
        List<HotelResponse> hotels = hotelService.getAvailableHotelsByLocation(location);
        return ResponseEntity.ok(hotels);
    }

    //localhost:8080/api/hotels/searchByLocation?location=Chicago


    @PutMapping("/{hotelId}")
    @PreAuthorize("hasAuthority('HOTEL_MANAGER')")
    public ResponseEntity<HotelResponse> updateHotel(
            Principal principal,
            @PathVariable Long hotelId,
            @Valid @RequestBody HotelRequest hotelRequest) {

        // Extract manager's email from the security context
        String managerEmail = principal.getName();

        // Get manager's user id (assuming you have a userRepository to fetch user info)
        User manager = userRepository.findByEmail(managerEmail);
        if (manager == null) {
            throw new UsernameNotFoundException("Manager not found");
        }
        Long managerId = manager.getId();


        // Call service method passing hotelId, request and managerId
        HotelResponse updatedHotel = hotelService.updateHotel(hotelId, hotelRequest, managerId);

        return ResponseEntity.ok(updatedHotel);
    }

    @DeleteMapping("/{hotelId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteHotel(@PathVariable Long hotelId) {
        hotelService.deleteHotel(hotelId);
        return ResponseEntity.noContent().build();
    }
}
