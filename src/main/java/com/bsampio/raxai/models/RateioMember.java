package com.bsampio.raxai.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "rateio_members")
@Data
public class RateioMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "rateio_id", nullable = false)
    private Rateio rateio;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private Double amountPaid;

    private LocalDateTime joinedAt = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    private MemberRateioStatus status;
}
