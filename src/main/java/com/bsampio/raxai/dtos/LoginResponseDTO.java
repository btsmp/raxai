package com.bsampio.raxai.dtos;

import jakarta.validation.constraints.NotNull;

public record LoginResponseDTO(@NotNull String token) {
}
