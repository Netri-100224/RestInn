package com.hotel.booking.DTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HotelRequest {

    // @NotBlank(message = "name is required")
    private String name;

    // @NotBlank(message = "location is required")
    private String location;

    // @NotBlank(message = "description is required")
    private String description;

    // @PositiveOrZero(message = "totalRooms cannot be negative")
    private int totalRooms;

    // @PositiveOrZero(message = "availableRooms cannot be negative")
    private int availableRooms;
}
