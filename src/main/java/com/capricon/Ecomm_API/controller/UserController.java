package com.capricon.Ecomm_API.controller;

import com.capricon.Ecomm_API.model.User;
import com.capricon.Ecomm_API.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    private UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getUserDetails(Authentication authentication) {
        String email = authentication.getName();
        User user = service.getUserDetails(email);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

}
