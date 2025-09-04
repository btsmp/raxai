package com.bsampio.raxai.controller;

import com.bsampio.raxai.dtos.CreateRateioDTO;
import com.bsampio.raxai.models.Rateio;
import com.bsampio.raxai.models.User;
import com.bsampio.raxai.services.RateioService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import static com.bsampio.raxai.services.AuthService.getCurrentUser;

@RestController
@RequestMapping("/rateios")
public class RateioController {

    private final RateioService rateioService;

    public RateioController(RateioService rateioService) {
        this.rateioService = rateioService;
    }

    @PostMapping()
    public Rateio createRateio(@RequestBody @Valid CreateRateioDTO data) {

        User user = getCurrentUser();

        return rateioService.createRateio(data, user);
    }

    @PostMapping("/{inviteCode}/join")
    public String joinRateio(@PathVariable String inviteCode) {

        User user = getCurrentUser();

        return rateioService.joinRateio(inviteCode, user);
    }
}
