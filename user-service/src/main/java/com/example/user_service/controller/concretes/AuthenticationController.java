package com.example.user_service.controller.concretes;


import com.example.user_service.business.abstracts.AuthenticationService;
import com.example.user_service.dto.JwtAuthenticationResponse;
import com.example.user_service.dto.RefreshTokenRequest;
import com.example.user_service.dto.SignUpRequest;
import com.example.user_service.dto.SigninRequest;
import com.example.user_service.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
  private  final AuthenticationService authenticationService;

  @PostMapping("/signup")
  @ResponseStatus(HttpStatus.OK)
    public User signup(@RequestBody SignUpRequest signUpRequest)
  {
      return authenticationService.signUp(signUpRequest);
  }

  @PostMapping("/signin")
  @ResponseStatus(HttpStatus.OK)
  public JwtAuthenticationResponse signin(@RequestBody SigninRequest signinRequest)
  {
    return authenticationService.signin(signinRequest);
  }

  @PostMapping("/refresh")
  @ResponseStatus(HttpStatus.OK)
  public JwtAuthenticationResponse refresh(@RequestBody RefreshTokenRequest refreshTokenRequest)
  {
    return authenticationService.refreshToken(refreshTokenRequest);
  }
}
