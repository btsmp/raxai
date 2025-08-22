package com.bsampio.raxai.services;

import com.bsampio.raxai.dtos.UserDTO;
import com.bsampio.raxai.models.User;
import com.bsampio.raxai.models.UserRole;
import com.bsampio.raxai.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

     public User createUser(UserDTO user) {
         if (repository.existsByEmail(user.email())) {
            throw new IllegalArgumentException("Email already in use");
         }

         User newUser = new User();
         newUser.setName(user.name());
         newUser.setEmail(user.email());

         String hashed = encoder.encode(user.password());
         newUser.setPassword(hashed);

         newUser.setRole(UserRole.USER);

         return repository.save(newUser);

    }
}
