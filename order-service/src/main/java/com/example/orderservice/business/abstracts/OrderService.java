package com.example.orderservice.business.abstracts;

import com.example.orderservice.entities.Order;

import java.util.List;

public interface OrderService {

    List<Order> getAllOrders();
    Order getOrderById(Long id);
    Order addOrder(Order order);
    Order updateOrder(Long id, Order orderDetails);
    void deleteOrder(Long id);
}
