package com.example.bookservice.config;

import com.example.bookservice.business.abstracts.BookService;
import com.example.bookservice.dto.BookMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class BookMessageReceiver {

    @Autowired
    private BookService bookService;

    private ObjectMapper objectMapper = new ObjectMapper();

    public void receiveMessage(String message) {
        try {
            BookMessage bookMessage = objectMapper.readValue(message, BookMessage.class);
            switch (bookMessage.getAction()) {
                case "ADD":
                    bookService.processAddBook(bookMessage.getBookRequest());
                    break;
                case "UPDATE":
                    bookService.processUpdateBook(bookMessage.getId(), bookMessage.getBookRequest());
                    break;
                case "DELETE":
                    bookService.processDeleteBook(bookMessage.getId());
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
