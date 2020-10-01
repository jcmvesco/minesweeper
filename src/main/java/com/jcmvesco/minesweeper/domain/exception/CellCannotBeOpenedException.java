package com.jcmvesco.minesweeper.domain.exception;

public class CellCannotBeOpenedException extends Exception {
    public CellCannotBeOpenedException(String message) {
        super(message);
    }
}
