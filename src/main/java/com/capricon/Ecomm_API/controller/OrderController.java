package com.capricon.Ecomm_API.controller;

import com.capricon.Ecomm_API.model.CheckOutRequest;
import com.capricon.Ecomm_API.model.Order;
import com.capricon.Ecomm_API.model.User;
import com.capricon.Ecomm_API.service.OrderService;
import com.capricon.Ecomm_API.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {

//    @Autowired
//    private CheckOutRequest checkOutRequest;

    @Autowired
    private OrderService service;

    @Autowired
    private UserService userService;

    @PostMapping("/checkout")
    public ResponseEntity<Order> saveOrder(@RequestBody CheckOutRequest checkOutRequest, Authentication authentication) {

        String email = authentication.getName();
        User user = userService.getUserDetails(email);

        Order order = service.createOrder(user, checkOutRequest.getItems());
        return ResponseEntity.ok(order);
    }

}
