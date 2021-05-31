package com.gamesys.trial.model.errors;

public enum ErrorCode {
    VALIDATION_FAILED("ERROR-1","Validation error"),
    DUPLICATE_TRAVEL_DETAIL("ERROR-2","Duplicate travel detail for same pgi, place and date!");

    private final String code;
    private final String description;

    ErrorCode(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
