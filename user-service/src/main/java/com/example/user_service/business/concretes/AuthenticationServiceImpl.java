package com.example.user_service.business.concretes;

import com.example.user_service.business.abstracts.AuthenticationService;
import com.example.user_service.dto.JwtAuthenticationResponse;
import com.example.user_service.dto.RefreshTokenRequest;
import com.example.user_service.dto.SignUpRequest;
import com.example.user_service.dto.SigninRequest;
import com.example.user_service.entities.Role;
import com.example.user_service.entities.User;
import com.example.user_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTHelper jwtHelper;
    public User signUp(SignUpRequest signUpRequest)
    {
        User user= User.builder()
                .email(signUpRequest.getEmail())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .role(Role.USER).build();
        return  userRepository.save(user);
    }

    public JwtAuthenticationResponse signin(SigninRequest signinRequest)
    {
      authenticationManager.authenticate((new UsernamePasswordAuthenticationToken(signinRequest.getEmail(),signinRequest.getPassword())));
      var user=userRepository.findByEmail(signinRequest.getEmail()).orElseThrow(()-> new IllegalArgumentException("Invalid email and password"));
      var jwt=jwtHelper.generateToken(user);
      var refreshToken=jwtHelper.generateRefreshToken(new HashMap<>(),user);

      JwtAuthenticationResponse jwtAuthenticationResponse=new JwtAuthenticationResponse();
      jwtAuthenticationResponse.setToken(jwt);
      jwtAuthenticationResponse.setRefreshToken(refreshToken);
      return jwtAuthenticationResponse;
    }

    public JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest)
    {
        String userEmail= jwtHelper.getUsernameFromToken(refreshTokenRequest.getToken());
        User user=userRepository.findByEmail((userEmail)).orElseThrow();
       if(jwtHelper.validateToken(refreshTokenRequest.getToken()))
       {
           var jwt=jwtHelper.generateToken(user);

           JwtAuthenticationResponse jwtAuthenticationResponse=new JwtAuthenticationResponse();
           jwtAuthenticationResponse.setToken(jwt);
           jwtAuthenticationResponse.setRefreshToken(refreshTokenRequest.getToken());
           return jwtAuthenticationResponse;
       }
       return null;
    }
}
