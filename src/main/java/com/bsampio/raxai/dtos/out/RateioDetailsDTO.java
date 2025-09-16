package com.bsampio.raxai.dtos.out;

import java.time.LocalDateTime;
import java.util.List;

public record RateioDetailsDTO(
        Long id,
        String title,
        Integer maxMembers,
        Double totalAmount,
        Double amountPerParticipant,
        Integer dueDate,
        String pixKey,
        Integer currentMembers,
        List<RateioMemberDTO> members,
        OwnerRateioDetailsDTO owner,
        LocalDateTime createdAt
) {
}
