package com.capricon.Ecomm_API.service;

import com.capricon.Ecomm_API.model.User;
import com.capricon.Ecomm_API.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.regex.Pattern;

@Service
public class UserService {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private UserRepo repo;

    @Autowired
    MailService mailService;

    @Autowired
    ApplicationContext context;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean findUserByEmail(String email) {
        User user =  repo.findUserByEmail(email);
        if (user != null) {
            return true;
        }
        return false;
    }

    public String loginUser(User user) {
        boolean isUserAvailable = findUserByEmail(user.getEmail());
        if (isUserAvailable) {
            Authentication auth = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
            if (auth.isAuthenticated()) {
                return jwtService.generateToken(user.getEmail());
            }
        }
        return "Failed";
    }


    public String signUpUser(User newUser) {
        //check if user already exists
        boolean isUserRegistered = findUserByEmail(newUser.getEmail());
        if (!isUserRegistered) {
            if (isEmailValid(newUser.getEmail()) && isPasswordValid(newUser.getPassword())) {
                newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
                newUser.setVerified(false);

                String otp = mailService.generateOtp();
                newUser.setOtp(otp);
                newUser.setOtpExpiry(new Date(System.currentTimeMillis() + 3 * 60 * 1000)); //3 minutes

                repo.save(newUser);

                mailService.sendOtpEmail(newUser.getEmail(), otp);
                String token = jwtService.generateToken(newUser.getEmail());
                return token;

            } else {
                throw new IllegalArgumentException("Invalid credentials. Please ensure the email address is valid and password is strong!");
            }
        } else {
            throw new IllegalArgumentException("User already exists in the system. Try logging in instead!");
        }

    }

    public boolean isEmailValid(String email) {
        String emailRegEx = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        Pattern pattern = Pattern.compile(emailRegEx);
        return pattern.matcher(email).matches();
    }

    public boolean isPasswordValid(String password) {
        String passRegEx = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
        Pattern pattern = Pattern.compile(passRegEx);
        return pattern.matcher(password).matches();
    }

    //OTP verification
    public void verifyOtp(User newUser) {
        User user = repo.findUserByEmail(newUser.getEmail());
        if (user != null) {
            if (user.isVerified()) {
                throw new IllegalArgumentException("Account already verified");
            }

            if (!user.getOtp().equals(newUser.getOtp())) {
                throw new IllegalArgumentException("Invalid OTP");
            }

            if (user.getOtpExpiry().before(new Date())) {
                throw new IllegalArgumentException("OTP has already expired. Please request another.");
            }

            user.setVerified(true);
            user.setOtp(null);
            user.setOtpExpiry(null);
            repo.save(user);
        }
    }

    public User getUserDetails(String email) {
        return repo.findUserByEmail(email);
    }
}
