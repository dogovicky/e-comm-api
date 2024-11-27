package com.capricon.Ecomm_API.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String first_name;
    private String last_name;
    @Id
    private String email;
    private String password;
    private int phone;
    private boolean isVerified;

    private String otp;
    private Date otpExpiry;
}
