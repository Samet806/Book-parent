package com.example.orderservice.business.concretes;

import com.example.orderservice.business.abstracts.OrderService;
import com.example.orderservice.dto.Book;
import com.example.orderservice.entities.Order;
import com.example.orderservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class OrderManager implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RestTemplate restTemplate;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    public Order addOrder(Order order) {

        Book book = restTemplate.getForObject("http://BOOK-SERVICE/api/books/" + order.getBookId(), Book.class);
        if (book != null) {
            order.setTotalPrice(book.getPrice() * order.getQuantity());
            return orderRepository.save(order);
        }
        return null;
    }

    public Order updateOrder(Long id, Order orderDetails) {
        Order order = orderRepository.findById(id).orElse(null);
        if (order != null) {
            order.setBookId(orderDetails.getBookId());
            order.setCustomerName(orderDetails.getCustomerName());
            order.setQuantity(orderDetails.getQuantity());

            Book book = restTemplate.getForObject("http://BOOK-SERVICE/api/books/" + order.getBookId(), Book.class);
            if (book != null) {
                order.setTotalPrice(book.getPrice() * order.getQuantity());
                return orderRepository.save(order);
            }
        }
        return null;
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}
