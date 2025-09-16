package com.bsampio.raxai.infra.messages.rateio;

public enum RateioMessages {
    RATEIO_CREATED("Rateio created successfully"),
    RATEIO_JOINED("Joined rateio successfully"),
    RATEIO_LIST("User rateios retrieved successfully"),
    RATEIO_DETAILS("Rateio details retrieved successfully"),
    RATEIO_LEFT("Left rateio successfully"),
    RATEIO_DELETED("Rateio deleted successfully");

    private final String message;

    RateioMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}