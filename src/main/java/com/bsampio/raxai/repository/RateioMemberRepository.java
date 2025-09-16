package com.bsampio.raxai.repository;

import com.bsampio.raxai.models.RateioMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RateioMemberRepository extends JpaRepository<RateioMember, Long> {
}
