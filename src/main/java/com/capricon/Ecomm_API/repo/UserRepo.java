package com.capricon.Ecomm_API.repo;

import com.capricon.Ecomm_API.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, String> {

    //find user by email
    User findUserByEmail(String email);

}
