package com.hotel.booking.Repository;

import com.hotel.booking.Entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("SELECT b FROM Booking b WHERE b.user.id = :userId ORDER BY b.checkInDate DESC")
    List<Booking> findBookingsByUserId(@Param("userId") Long userId);

    @Query("SELECT b FROM Booking b WHERE b.hotel.id = :hotelId AND " +
            "b.checkInDate <= :checkOutDate AND b.checkOutDate >= :checkInDate")
    List<Booking> findOverlappingBookings(@Param("hotelId") Long hotelId,
                                          @Param("checkInDate") LocalDate checkInDate,
                                          @Param("checkOutDate") LocalDate checkOutDate);
    @Query("SELECT b FROM Booking b WHERE :date BETWEEN b.checkInDate AND b.checkOutDate")
    List<Booking> findActiveBookings(@Param("date") LocalDate date);

    @Query("SELECT b FROM Booking b WHERE b.hotel.id = :hotelId AND :date BETWEEN b.checkInDate AND b.checkOutDate")
    List<Booking> findActiveBookingsByHotelIdAndDate(@Param("hotelId") Long hotelId, @Param("date") LocalDate date);



}


