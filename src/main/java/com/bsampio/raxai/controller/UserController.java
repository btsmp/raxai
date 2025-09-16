package com.bsampio.raxai.controller;

import com.bsampio.raxai.infra.dtos.RegisterDTO;
import com.bsampio.raxai.models.User;
import com.bsampio.raxai.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService service;

    @PostMapping
    public User createUser(@Valid @RequestBody RegisterDTO user) {
        return service.createUser(user);
    }
}
