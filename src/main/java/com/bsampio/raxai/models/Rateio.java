package com.bsampio.raxai.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "rateios")
public class Rateio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String title;

    private Integer maxMembers;
    private Double totalAmount;
    private Double amountPerParticipant;
    private Integer dueDate;
    private String pixKey;

    private Integer currentMembers = 0;


    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt =  LocalDateTime.now();

    @Column(unique = true, nullable = false, length = 6)
    private String inviteCode;


    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @OneToMany(mappedBy = "rateio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RateioMember> members = new ArrayList<>();
}
