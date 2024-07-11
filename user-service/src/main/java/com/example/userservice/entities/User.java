package com.example.userservice.entities;

import lombok.Data;
import java.util.List;

@Data

public class User {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private List<Role> roles;


}
