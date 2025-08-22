package com.bsampio.raxai.repository;

import com.bsampio.raxai.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<User, Long> {
    UserDetails findByName(String login);
    boolean existsByEmail(String email);
}
