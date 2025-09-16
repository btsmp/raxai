package com.bsampio.raxai.infra.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;

public record CreateRateioDTO(@NotNull String title,
                              @NotNull Integer maxMembers,
                              @NotNull Double totalAmount,
                              @NotNull @Max(31) Integer dueDate,
                              @NotNull String pixKey) {
}
