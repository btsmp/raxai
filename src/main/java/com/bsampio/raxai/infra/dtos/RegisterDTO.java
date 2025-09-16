package com.bsampio.raxai.infra.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegisterDTO(@NotBlank String name, @NotBlank @Email String email, @NotBlank String password) {
}
