package com.bsampio.raxai.controller;

import com.bsampio.raxai.infra.dtos.CreateRateioDTO;
import com.bsampio.raxai.infra.dtos.out.OwnerRateioDetailsDTO;
import com.bsampio.raxai.infra.dtos.out.RateioByUserDTO;
import com.bsampio.raxai.infra.dtos.out.RateioDetailsDTO;
import com.bsampio.raxai.infra.dtos.out.RateioMemberDTO;
import com.bsampio.raxai.infra.http.ApiResponse;
import com.bsampio.raxai.infra.messages.rateio.RateioMessages;
import com.bsampio.raxai.models.Rateio;
import com.bsampio.raxai.models.User;
import com.bsampio.raxai.services.RateioService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ApiResponse<Rateio>> createRateio(@RequestBody @Valid CreateRateioDTO data) {
        User user = getCurrentUser();
        Rateio rateio =  rateioService.createRateio(data, user);
        return ResponseEntity.ok(ApiResponse.success(RateioMessages.RATEIO_CREATED.getMessage(), rateio));
    }

    @PostMapping("/{inviteCode}/join")
    public ResponseEntity<ApiResponse<String>> joinRateio(@PathVariable String inviteCode) {
        User user = getCurrentUser();
        String data = rateioService.joinRateio(inviteCode, user);
        return ResponseEntity.ok(ApiResponse.success(RateioMessages.RATEIO_JOINED.getMessage(), data));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<List<RateioByUserDTO>>> getUserRateios() {
        User user = getCurrentUser();
        List<Rateio> rateios =  rateioService.listRateioByUser(user);

        List<RateioByUserDTO> data = rateios.stream()
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

        return ResponseEntity.ok(ApiResponse.success(RateioMessages.RATEIO_LIST.getMessage(), data));
    }

    @GetMapping("details/{rateioId}")
    public ResponseEntity<ApiResponse<RateioDetailsDTO>> getRateioDetails(@PathVariable Long rateioId) {
        Rateio rateio = rateioService.getRateioDetailsById(rateioId);

        RateioDetailsDTO data = new RateioDetailsDTO(
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

        return ResponseEntity.ok(ApiResponse.success(RateioMessages.RATEIO_DETAILS.getMessage(), data));
    }

    @DeleteMapping("/{rateioId}/leave")
    public ResponseEntity<ApiResponse<String>> leaveRateio(@PathVariable Long rateioId) {
        User user = getCurrentUser();
        String data = rateioService.leaveRateio(rateioId, user);

        return ResponseEntity.ok(ApiResponse.success(RateioMessages.RATEIO_LEFT.getMessage(), data));
    }

    @DeleteMapping("/{rateioId}/delete")
    public ResponseEntity<ApiResponse<String>> deleteRateio(@PathVariable Long rateioId) {
        User user = getCurrentUser();
        String data = rateioService.deleteRateio(rateioId, user);

        return ResponseEntity.ok(ApiResponse.success(RateioMessages.RATEIO_DELETED.getMessage(), data));
    }
}
