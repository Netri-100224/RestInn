package com.hotel.booking.Service;


import com.hotel.booking.DTO.AuthRequest;
import com.hotel.booking.DTO.AuthResponse;
import com.hotel.booking.DTO.RegisterRequest;
import com.hotel.booking.Entity.Role;
import com.hotel.booking.Entity.User;
import com.hotel.booking.Exception.UserAlreadyExistsException;
import com.hotel.booking.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    @Autowired
    JWTService jwtService;

    @Autowired
    AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request) {

        if(userRepository.existsByEmail(request.getEmail())){
            throw new UserAlreadyExistsException("This user is already there");
        }

        if (request.getRole() == null) {
            request.setRole(Role.CUSTOMER);
        }

        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();

        userRepository.save(user);

//        String jwtToken = jwtService.generateToken(user);
//        //userRepository.save(user);
//        return AuthResponse.builder()
//                .Token(jwtToken)
//                .build();

        return AuthResponse.builder()
                .message("Registration successful.")
                .build();

    }

    public AuthResponse login(AuthRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()));
        } catch (BadCredentialsException e) {
            throw new RuntimeException("Invalid credentials");
        }

        User user = userRepository.findByEmail(request.getEmail());
        String jwtToken = jwtService.generateToken(user);
        return AuthResponse.builder()
                .Token(jwtToken)
                .message("Login successful.")
                .build();
    }
}
