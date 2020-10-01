package com.jcmvesco.minesweeper.domain.exception;

public class CellCannotBeFlaggedOrMarkedException extends RuntimeException {
    public CellCannotBeFlaggedOrMarkedException(String message) {
        super(message);
    }
}
