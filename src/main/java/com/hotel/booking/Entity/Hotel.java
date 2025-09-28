package com.hotel.booking.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "hotel")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String location;

    private String description;

    private int totalRooms;

    private int availableRooms;
    @OneToOne
    @JoinColumn(name = "manager_id", unique = true)
    private User manager;

}
