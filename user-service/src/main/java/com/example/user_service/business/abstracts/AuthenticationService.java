package com.example.user_service.business.abstracts;

import com.example.user_service.dto.JwtAuthenticationResponse;
import com.example.user_service.dto.RefreshTokenRequest;
import com.example.user_service.dto.SignUpRequest;
import com.example.user_service.dto.SigninRequest;
import com.example.user_service.entities.User;

public interface AuthenticationService {

    User signUp(SignUpRequest signUpRequest);
    JwtAuthenticationResponse signin(SigninRequest signinRequest);
    JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
