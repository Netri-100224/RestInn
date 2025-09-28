package com.hotel.booking.Exception;

public class IncorrectDateException extends RuntimeException{
    public IncorrectDateException(String message){
        super(message);
    }
}
