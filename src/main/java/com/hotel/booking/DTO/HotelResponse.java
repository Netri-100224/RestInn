package com.hotel.booking.DTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HotelResponse {

    private Long id;

    private String name;

    private String location;

    private String description;

    private int totalRooms;

    private int availableRooms;
}