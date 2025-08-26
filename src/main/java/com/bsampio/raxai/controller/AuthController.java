package com.bsampio.raxai.controller;

import com.bsampio.raxai.dtos.AuthRequestDTO;
import com.bsampio.raxai.dtos.LoginResponseDTO;
import com.bsampio.raxai.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @Autowired
    AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody AuthRequestDTO data) {
        String token = authService.login(data, authenticationManager);
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }
}
