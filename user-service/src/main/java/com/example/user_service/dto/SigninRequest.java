package com.example.user_service.dto;

import lombok.Data;

@Data
public class SigninRequest {
    private String email;
    private String password;
}
