package com.example.userservice.controller.concretes;

import com.example.userservice.business.concretes.UserManager;
import com.example.userservice.entities.User;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserManager userService;

    @PostMapping
    public void createUser(@RequestBody User user) {
        userService.createUser(user);
    }

    @GetMapping
    public List<UserRepresentation> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public UserRepresentation getUserById(@PathVariable String id) {
        return userService.getUserById(id);
    }

    @PutMapping("/{id}")
    public void updateUser(@PathVariable String id, @RequestBody User user) {
        userService.updateUser(id, user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
    }
}
