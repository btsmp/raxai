package com.bsampio.raxai.services;

import com.bsampio.raxai.dtos.AuthRequestDTO;

import com.bsampio.raxai.infra.security.TokenService;
import com.bsampio.raxai.models.User;
import com.bsampio.raxai.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service

public class AuthService implements UserDetailsService {

    private final UserRepository repository;
    private final TokenService tokenService;

    public AuthService(UserRepository repository, TokenService tokenService) {
        this.repository = repository;
        this.tokenService = tokenService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByEmail(username);
    }

    public String login(AuthRequestDTO data, AuthenticationManager authenticationManager){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var auth = authenticationManager.authenticate(usernamePassword);
        return tokenService.generateToken((User) auth.getPrincipal());
    }

    static public User getCurrentUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
