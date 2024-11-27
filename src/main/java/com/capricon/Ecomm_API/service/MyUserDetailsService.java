package com.capricon.Ecomm_API.service;

import com.capricon.Ecomm_API.model.User;
import com.capricon.Ecomm_API.model.UserPrincipal;
import com.capricon.Ecomm_API.repo.UserRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private UserRepo repo;

    public MyUserDetailsService(UserRepo repo) {
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = repo.findUserByEmail(email);
        if (user != null) {
            return new UserPrincipal(user);
        }
        return null;
    }
}
