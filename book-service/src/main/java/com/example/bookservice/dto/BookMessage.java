package com.example.bookservice.dto;

import lombok.Data;

@Data
public class BookMessage {
    private String action;
    private Long id;
    private BookRequest bookRequest;

}
