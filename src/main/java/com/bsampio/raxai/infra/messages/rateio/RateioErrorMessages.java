package com.bsampio.raxai.infra.messages.rateio;

public enum RateioErrorMessages {
    RATEIO_NOT_FOUND("Rateio not found"),
    INVALID_INVITE_CODE("Invalid invite code"),
    RATEIO_FULL("Rateio is full"),
    OWNER_CANNOT_JOIN("Owner cannot join their own rateio"),
    USER_ALREADY_MEMBER("User is already a member of this rateio"),
    USER_NOT_MEMBER("User is not a member of this rateio"),
    OWNER_CANNOT_LEAVE("Owner cannot leave their own rateio"),
    ONLY_OWNER_CAN_DELETE("Only the owner can delete the rateio");

    private final String message;

    RateioErrorMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
