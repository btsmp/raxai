package com.bsampio.raxai.controller;

import com.bsampio.raxai.infra.dtos.AuthRequestDTO;
import com.bsampio.raxai.infra.dtos.LoginResponseDTO;
import com.bsampio.raxai.services.AuthService;
import jakarta.validation.Valid;
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
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody AuthRequestDTO data) {
        String token = authService.login(data, authenticationManager);
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }
}
