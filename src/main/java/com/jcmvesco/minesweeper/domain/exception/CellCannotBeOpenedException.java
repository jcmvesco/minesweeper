package com.jcmvesco.minesweeper.domain.exception;

public class CellCannotBeOpenedException extends RuntimeException {
    public CellCannotBeOpenedException(String message) {
        super(message);
    }
}
