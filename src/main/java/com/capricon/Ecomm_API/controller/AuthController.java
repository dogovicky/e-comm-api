package com.capricon.Ecomm_API.controller;

import com.capricon.Ecomm_API.model.AuthResponse;
import com.capricon.Ecomm_API.model.User;
import com.capricon.Ecomm_API.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/auth")
public class AuthController {

    private UserService service;

    public AuthController(UserService service) {
        this.service = service;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUpUser(@RequestBody User user) {
        String token = service.signUpUser(user);
        return ResponseEntity.ok().body(Map.of("token", token));
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<?> validateAccount(@RequestBody User user) {
        service.verifyOtp(user);
        return new ResponseEntity<>("Account verified", HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        String token = service.loginUser(user);
        user = service.getUserDetails(user.getEmail());

        AuthResponse response = new AuthResponse(user, token);
        return ResponseEntity.ok().body(response);
    }

}
