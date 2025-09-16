package com.bsampio.raxai.infra.dtos.out;

import com.bsampio.raxai.models.MemberRateioStatusPayment;

public record RateioMemberDTO(
        Long userId,
        String userName,
        Double amountPaid,
        Enum<MemberRateioStatusPayment> status
) {}