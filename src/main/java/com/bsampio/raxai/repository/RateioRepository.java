package com.bsampio.raxai.repository;

import com.bsampio.raxai.models.Rateio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RateioRepository extends JpaRepository<Rateio, Long> {
    Rateio findByInviteCode(String inviteCode);
}
