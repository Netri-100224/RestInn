package com.hotel.booking.DTO;

import com.hotel.booking.Entity.Role;
import lombok.Builder;
import lombok.Data;

    @Data
    @Builder
    public class UserResponse {
        private Long id;
        private String firstName;
        private String lastName;
        private String email;
        private Role role;
    }


