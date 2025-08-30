package com.bsampio.raxai.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AuthRequestDTO(@Email @NotBlank String email, @NotBlank String password) {
}
