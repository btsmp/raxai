package com.bsampio.raxai.services;

import com.bsampio.raxai.dtos.CreateRateioDTO;
import com.bsampio.raxai.models.Rateio;
import com.bsampio.raxai.models.User;
import com.bsampio.raxai.repository.RateioRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class RateioService {
    private final RateioRepository rateioRepository;


    public RateioService(RateioRepository rateioRepository) {
        this.rateioRepository = rateioRepository;
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

        currentUser.getOwnedRateios().add(rateio);

        Rateio savedRateio = rateioRepository.save(rateio);
        return rateio;
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
