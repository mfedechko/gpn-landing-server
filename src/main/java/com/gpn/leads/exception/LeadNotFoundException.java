package com.gpn.leads.exception;

public class LeadNotFoundException extends RuntimeException {
    private final String message;

    public LeadNotFoundException(final String message) {
        this.message = message;
    }
}
