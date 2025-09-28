package com.hotel.booking.Repository;


import com.hotel.booking.Entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
    @Query("SELECT h FROM Hotel h WHERE h.location = :location AND h.availableRooms > 0")
    List<Hotel> findAvailableHotelsByLocation(@Param("location") String location);
    Optional<Hotel> findByManagerId(Long managerId);
    Optional<Hotel> findByIdAndManagerId(Long hotelID, Long managerId );

}
