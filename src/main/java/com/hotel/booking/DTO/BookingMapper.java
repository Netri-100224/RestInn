package com.hotel.booking.DTO;

import com.hotel.booking.Entity.Booking;
import org.springframework.stereotype.Component;

@Component
public class BookingMapper {

    public BookingResponse toResponse(Booking booking) {
        BookingResponse response = new BookingResponse();
        response.setBookingId(booking.getId());
        response.setHotelId(booking.getHotel().getId());
        response.setCheckInDate(booking.getCheckInDate());
        response.setCheckOutDate(booking.getCheckOutDate());
        // add more fields if your BookingResponse has them

        return response;
    }
}

