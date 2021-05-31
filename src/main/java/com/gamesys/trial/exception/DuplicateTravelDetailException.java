package com.gamesys.trial.exception;

public class DuplicateTravelDetailException extends RuntimeException {

    private static final long serialVersionUID = 9144602665016133951L;

    public DuplicateTravelDetailException(String message) {
        super(message);
    }
}
