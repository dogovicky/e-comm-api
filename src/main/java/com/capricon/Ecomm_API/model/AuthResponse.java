package com.capricon.Ecomm_API.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    private User user;
    private String token;
//    private String email;
//    private String first_name;
//    private String last_name;
//    private String password;
   // private int phone;
}
