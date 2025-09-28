package com.hotel.booking.Exception;

public class HotelRoomsNotAvailableException extends RuntimeException {
    public HotelRoomsNotAvailableException(String message) {
        super(message);
    }
}