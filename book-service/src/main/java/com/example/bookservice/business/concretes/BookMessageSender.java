package com.example.bookservice.business.concretes;

import com.example.bookservice.config.RabbitMQConfig;
import com.example.bookservice.dto.BookMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class BookMessageSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public void sendMessage(BookMessage bookMessage) {
        try {
            String message = objectMapper.writeValueAsString(bookMessage);
            rabbitTemplate.convertAndSend(RabbitMQConfig.BOOK_QUEUE, message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
