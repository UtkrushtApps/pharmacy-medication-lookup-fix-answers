package com.example.task.exception;

public class MedicationNotFoundException extends RuntimeException {

    private final String code;

    public MedicationNotFoundException(String code) {
        super("Medication with code '" + code + "' not found");
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
