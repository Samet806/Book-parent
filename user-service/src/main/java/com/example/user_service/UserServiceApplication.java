package com.example.user_service;

import com.example.user_service.entities.Role;
import com.example.user_service.entities.User;
import com.example.user_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class UserServiceApplication implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
       User adminAccount=userRepository.findByRole(Role.ADMIN);
	   if(adminAccount==null)
	   {
		   User user=User.builder()
				   .email("admin@admin.com")
				   .username("admin")
				   .role(Role.ADMIN)
				   .password(new BCryptPasswordEncoder().encode("admin"))
				   .build();
		   userRepository.save(user);
	   }
	}
}
