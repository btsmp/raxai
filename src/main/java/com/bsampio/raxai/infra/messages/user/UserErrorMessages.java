package com.bsampio.raxai.infra.messages.user;

public enum UserErrorMessages {
    EMAIL_ALREADY_EXISTS("Email already exists.");

    private final String message;

    UserErrorMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
