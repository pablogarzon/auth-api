package com.example.authapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.authapi.dtos.LoginDTO;
import com.example.authapi.dtos.UserResponseDTO;
import com.example.authapi.services.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<UserResponseDTO> login(@RequestBody LoginDTO loginDTO) {
    	UserResponseDTO authResponse = authService.login(loginDTO);
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader("Authorization") String token) {
        authService.logout(token);
        return ResponseEntity.noContent().build();
    }

    /*
    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refreshToken(@RequestBody RefreshTokenDTO refreshTokenDTO) {
        AuthResponse authResponse = authService.refreshToken(refreshTokenDTO);
        return ResponseEntity.ok(authResponse);
    }*/
}
