package com.bsampio.raxai.controller;

import com.bsampio.raxai.dtos.CreateRateioDTO;
import com.bsampio.raxai.dtos.out.OwnerRateioDetailsDTO;
import com.bsampio.raxai.dtos.out.RateioByUserDTO;
import com.bsampio.raxai.dtos.out.RateioDetailsDTO;
import com.bsampio.raxai.dtos.out.RateioMemberDTO;
import com.bsampio.raxai.models.Rateio;
import com.bsampio.raxai.models.User;
import com.bsampio.raxai.services.RateioService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.bsampio.raxai.services.AuthService.getCurrentUser;

@RestController
@RequestMapping("/rateios")
public class RateioController {

    private final RateioService rateioService;

    public RateioController(RateioService rateioService) {
        this.rateioService = rateioService;
    }

    @PostMapping("/create")
    public Rateio createRateio(@RequestBody @Valid CreateRateioDTO data) {

        User user = getCurrentUser();

        return rateioService.createRateio(data, user);
    }

    @PostMapping("/{inviteCode}/join")
    public String joinRateio(@PathVariable String inviteCode) {

        User user = getCurrentUser();

        return rateioService.joinRateio(inviteCode, user);
    }

    @GetMapping("/me")
    public List<RateioByUserDTO> getUserRateios() {
        User user = getCurrentUser();
        List<Rateio> rateios =  rateioService.listRateioByUser(user);

        return rateios.stream()
                .map(rateio -> new RateioByUserDTO(
                        rateio.getId(),
                        rateio.getTitle(),
                        rateio.getMaxMembers(),
                        rateio.getTotalAmount(),
                        rateio.getAmountPerParticipant(),
                        rateio.getDueDate(),
                        rateio.getPixKey(),
                        rateio.getCurrentMembers(),
                        new OwnerRateioDetailsDTO(
                                rateio.getOwner().getId(),
                                rateio.getOwner().getName()
                        ),
                        rateio.getCreatedAt()
                ))
                .toList();
    }

    @GetMapping("details/{rateioId}")
    public RateioDetailsDTO getRateioDetails(@PathVariable Long rateioId) {
        User user = getCurrentUser();

        Rateio rateio = rateioService.getRateioDetails(rateioId, user);

        return new RateioDetailsDTO(
                rateio.getId(),
                rateio.getTitle(),
                rateio.getMaxMembers(),
                rateio.getTotalAmount(),
                rateio.getAmountPerParticipant(),
                rateio.getDueDate(),
                rateio.getPixKey(),
                rateio.getCurrentMembers(),
                rateio.getMembers().stream()
                        .map(member -> new RateioMemberDTO(
                                member.getId(),
                                member.getUser().getName(),
                                member.getAmountPaid(),
                                member.getStatus()
                        ))
                        .toList(),
                new OwnerRateioDetailsDTO(
                        rateio.getOwner().getId(),
                        rateio.getOwner().getName()
                ),
                rateio.getCreatedAt()
        );
    }
}
