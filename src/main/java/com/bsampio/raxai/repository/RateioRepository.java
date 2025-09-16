package com.bsampio.raxai.repository;

import com.bsampio.raxai.models.Rateio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RateioRepository extends JpaRepository<Rateio, Long> {
    Rateio findByInviteCode(String inviteCode);

    @Query("""
        SELECT DISTINCT r
        FROM Rateio r
        LEFT JOIN r.members m
        WHERE r.owner.id = :userId OR m.user.id = :userId
    """)
    List<Rateio> findByOwnerIdOrMemberUserId(Long userId);
}
