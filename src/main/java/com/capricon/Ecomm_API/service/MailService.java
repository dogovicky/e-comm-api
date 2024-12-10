package com.capricon.Ecomm_API.service;

import com.capricon.Ecomm_API.model.Order;
import com.capricon.Ecomm_API.model.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class MailService {

    @Autowired
    JavaMailSender mailSender;

    public String generateOtp() {
        Random random = new Random();
        return String.format("%06d", random.nextInt(1000000));
    }

    public void sendOtpEmail(String to, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Account Verification");
        message.setText("Your OTP is " + otp + ".\nThis OTP is valid for 3 minutes");
        mailSender.send(message);
    }

    public void sendOrderConfirmationEmail(String toEmail, Order order) {
        String subject = "Order Confirmation";
        String message = "Dear " + order.getCustomer().getFirst_name() + order.getCustomer().getLast_name()
                + ",\n\n" + "Thank you for your order! Here are the details:\n"
                + "Order Date: " + order.getOrderDate() + "\n"
                + "Delivery date: " + order.getDeliveryDate() + "\n\n"
                + "Items:\n";

        for (OrderItem item : order.getOrderItemList()) {
            message += "- " + item.getProduct().getTitle() + " (Quantity: " + item.getQuantity() + ")\n";
        }

        message += "\nWe appreciate your loyalty to us. Thank you for your business.\n";
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(toEmail);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);

        mailSender.send(mailMessage);
    }

}
