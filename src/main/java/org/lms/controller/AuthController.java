package org.lms.controller;

import jakarta.validation.Valid;
import org.lms.config.JwtUtils;
import org.lms.dto.JwtResponse;
import org.lms.dto.LoginRequest;
import org.lms.entity.User;
import org.lms.repository.UserRepository;
import org.lms.utill.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user) {
        // 1. Check if email exists
        if (userRepository.existsByEmail(user.getEmail())) {
            return ResponseEntity.badRequest().body("Error: Email is already in use!");
        }

        // 2. Encode the raw password from the request
        user.setPassword(encoder.encode(user.getPassword()));

        // 3. Set default role if not provided (using your Enum)
        if (user.getRole() == null) {
            user.setRole(Role.USER);
        }

        // 4. Save the user to MySQL
        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully!");
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        // 1. Authenticate using email and password
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        // 2. Update Security Context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 3. Generate JWT Token
        String jwt = jwtUtils.generateToken(authentication);

        // 4. Get User object from authentication principal
        // Since your User entity implements UserDetails, we cast directly to User
        User user = (User) authentication.getPrincipal();

        // 5. Return Response
        return ResponseEntity.ok(new JwtResponse(
                jwt,
                user.getEmail(),
                user.getRole().name()
        ));
    }
}
