package com.capricon.Ecomm_API.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;


@Entity
@Table(name = "orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int order_id;

    @ManyToOne
    @JoinColumn(name = "email", nullable = false)
    private User customer;

    private LocalDate orderDate;
    private LocalDate deliveryDate;

    @OneToMany(mappedBy = "order")
    List<OrderItem> orderItemList;



}
