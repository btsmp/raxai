package com.bsampio.raxai.services;

import com.bsampio.raxai.infra.dtos.CreateRateioDTO;
import com.bsampio.raxai.infra.messages.rateio.RateioErrorMessages;
import com.bsampio.raxai.models.MemberRateioStatusPayment;
import com.bsampio.raxai.models.Rateio;
import com.bsampio.raxai.models.RateioMember;
import com.bsampio.raxai.models.User;
import com.bsampio.raxai.repository.RateioMemberRepository;
import com.bsampio.raxai.repository.RateioRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

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
            throw new IllegalArgumentException(RateioErrorMessages.INVALID_INVITE_CODE.getMessage());
        }

        if (rateio.getCurrentMembers() >= rateio.getMaxMembers()) {
            throw new IllegalStateException(RateioErrorMessages.RATEIO_FULL.getMessage());
        }

        if (rateio.getOwner().getId().equals(currentUser.getId())) {
            throw new IllegalStateException(RateioErrorMessages.OWNER_CANNOT_JOIN.getMessage());
        }

        boolean alreadyMember = rateio.getMembers().stream()
                .anyMatch(member -> member.getUser().getId().equals(currentUser.getId()));

        if (alreadyMember) {
            throw new IllegalStateException(RateioErrorMessages.USER_ALREADY_MEMBER.getMessage());
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

    @Transactional
    public String leaveRateio(Long id, User currentUser) {

        Rateio rateio = rateioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(RateioErrorMessages.RATEIO_NOT_FOUND.getMessage()));

        if (rateio.getOwner().getId().equals(currentUser.getId())) {
            throw new IllegalStateException(RateioErrorMessages.OWNER_CANNOT_LEAVE.getMessage());
        }

        RateioMember member = rateio.getMembers().stream()
                .filter(m -> m.getUser().getId().equals(currentUser.getId()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(RateioErrorMessages.USER_NOT_MEMBER.getMessage()));

        rateio.getMembers().remove(member);
        rateioMemberRepository.delete(member);

        int newCurrentMembers = rateio.getCurrentMembers() - 1;
        rateio.setCurrentMembers(newCurrentMembers);

        if (newCurrentMembers > 0) {
            double newAmountPerParticipant = rateio.getTotalAmount() / newCurrentMembers;
            rateio.setAmountPerParticipant(newAmountPerParticipant);
        } else {
            rateio.setAmountPerParticipant(0.0);
        }

        rateioRepository.save(rateio);

        return "Left rateio successfully";
    }

    public List<Rateio> listRateioByUser(User currentUser) {
        Long userId = currentUser.getId();
        return rateioRepository.findByOwnerIdOrMemberUserId(userId);
    }

    public Rateio getRateioDetailsById(Long id) {
        return rateioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(RateioErrorMessages.RATEIO_NOT_FOUND.getMessage()));
    }

    public String deleteRateio(Long id, User currentUser) {
        Rateio rateio = rateioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(RateioErrorMessages.RATEIO_NOT_FOUND.getMessage()));

        if (!rateio.getOwner().getId().equals(currentUser.getId())) {
            throw new IllegalStateException(RateioErrorMessages.ONLY_OWNER_CAN_DELETE.getMessage());
        }

        rateioRepository.delete(rateio);
        return "Rateio deleted successfully";
    }


    /* TODO:
    *   Excluir rateio se o owner sair e não tiver mais membros;
    *   Atualizar amountPerParticipant quando um membro sair;
    *   Rota de sair do rateio;
    *   Rota de detalhes do rateio;
    *   Rota para listar os rateios que o user participa;
    *   Rota para listar os membros do rateio;
    *   Rota para o owner marcar um membro como pago;
    *   Rota para o membro ver o status do pagamento dele;
    *   Rota de listar os rateios que um user participa; ✔
    *   Rota de deletar o rateio (somente o owner pode);
    *   Rota para remover um membro (somente o owner pode);
    *   Rota para atualizar o rateio (somente o owner pode);
    * */


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
