package com.bsampio.raxai.infra.dtos;

import jakarta.validation.constraints.NotNull;

public record LoginResponseDTO(@NotNull String token) {
}
