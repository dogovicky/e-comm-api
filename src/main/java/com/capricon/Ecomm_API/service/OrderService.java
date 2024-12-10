package com.capricon.Ecomm_API.service;

import com.capricon.Ecomm_API.model.*;
import com.capricon.Ecomm_API.repo.OrderItemRepo;
import com.capricon.Ecomm_API.repo.OrderRepo;
import com.capricon.Ecomm_API.repo.ProductRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class OrderService {

    private OrderRepo repo;
    private MailService mailService;
    private ProductRepo productRepo;
    private OrderItemRepo itemRepo;

    public OrderService(OrderRepo repo, MailService mailService, ProductRepo productRepo, OrderItemRepo itemRepo) {
        this.repo = repo;
        this.mailService = mailService;
        this.productRepo = productRepo;
        this.itemRepo = itemRepo;
    }

    @Transactional
    public Order createOrder(User user, List<OrderItem> orderItems) {

        //calculate shipping date (7 days from order date)
        LocalDate deliveryDate = LocalDate.now().plusDays(7);

        //create order
        Order order = new Order();
        order.setCustomer(user);
        order.setOrderDate(LocalDate.now());
        order.setDeliveryDate(deliveryDate);

        //Process and validate each order item
        List<OrderItem> processedItems = orderItems.stream().map(item -> {
           //fetch product from the database
            Product product = productRepo.findByTitle(item.getProduct().getTitle());

            //calculate price based on product price and quantity
            OrderItem processedItem = new OrderItem();
            processedItem.setProduct(product);
            processedItem.setQuantity(item.getQuantity());
            processedItem.setPrice(product.getPrice() * item.getQuantity());
            processedItem.setOrder(order);
            return processedItem;
        }).toList();

        order.setOrderItemList(processedItems);

        Order placedOrder = repo.save(order);
        for (OrderItem item : processedItems) {
            itemRepo.save(item);
        }

        //send email notification
        mailService.sendOrderConfirmationEmail(user.getEmail(), placedOrder);
        return placedOrder;

    }

}
