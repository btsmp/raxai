package com.bsampio.raxai.services;

import com.bsampio.raxai.dtos.CreateRateioDTO;
import com.bsampio.raxai.models.MemberRateioStatusPayment;
import com.bsampio.raxai.models.Rateio;
import com.bsampio.raxai.models.RateioMember;
import com.bsampio.raxai.models.User;
import com.bsampio.raxai.repository.RateioMemberRepository;
import com.bsampio.raxai.repository.RateioRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class RateioService {
    private final RateioRepository rateioRepository;
    private final RateioMemberRepository rateioMemberRepository;


    public RateioService(RateioRepository rateioRepository, RateioMemberRepository rateioMemberRepository) {
        this.rateioRepository = rateioRepository;
        this.rateioMemberRepository = rateioMemberRepository;
    }

    @Transactional
    public Rateio createRateio(CreateRateioDTO data, User currentUser) {

        Rateio rateio = new Rateio();
        rateio.setTitle(data.title());

        rateio.setDueDate(data.dueDate());
        rateio.setPixKey(data.pixKey());
        rateio.setMaxMembers(data.maxMembers());
        rateio.setTotalAmount(data.totalAmount());
        rateio.setAmountPerParticipant(data.totalAmount());

        rateio.setOwner(currentUser);

        rateio.setInviteCode(generateInviteCode(6));

        rateio.setCurrentMembers(1);

        return rateioRepository.save(rateio);
    }

    @Transactional
    public String joinRateio(String inviteCode, User currentUser) {
        Rateio rateio = rateioRepository.findByInviteCode(inviteCode);

        if (rateio == null) {
            throw new IllegalArgumentException("Invalid invite code");
        }

        if (rateio.getCurrentMembers() >= rateio.getMaxMembers()) {
            throw new IllegalStateException("Rateio is full");
        }

        if (rateio.getOwner().getId().equals(currentUser.getId())) {
            throw new IllegalStateException("Owner cannot join their own rateio");
        }

        boolean alreadyMember = rateio.getMembers().stream()
                .anyMatch(member -> member.getUser().getId().equals(currentUser.getId()));

        if (alreadyMember) {
            throw new IllegalStateException("User is already a member of this rateio");
        }

        RateioMember newMember = new RateioMember();
        newMember.setRateio(rateio);
        newMember.setUser(currentUser);
        newMember.setAmountPaid(0.0);
        newMember.setStatus(MemberRateioStatusPayment.PENDING);

        rateioMemberRepository.save(newMember);

        Integer newCurrentMembers = rateio.getCurrentMembers() + 1;


       double portion = rateio.getTotalAmount() / newCurrentMembers;
       rateio.setCurrentMembers(newCurrentMembers);
       rateio.setAmountPerParticipant(portion);

        rateioRepository.save(rateio);

        return "Joined rateio successfully";
    }

    private String generateInviteCode(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder(length);
        java.util.Random random = new java.util.Random();
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }
}
