package com.bsampio.raxai.dtos.out;

import java.time.LocalDateTime;

public record RateioByUserDTO(
        Long id,
        String title,
        Integer maxMembers,
        Double totalAmount,
        Double amountPerParticipant,
        Integer dueDate,
        String pixKey,
        Integer currentMembers,
        OwnerRateioDetailsDTO owner,
        LocalDateTime createdAt
) {}
