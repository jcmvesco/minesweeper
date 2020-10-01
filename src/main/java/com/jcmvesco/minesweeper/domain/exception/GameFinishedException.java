package com.jcmvesco.minesweeper.domain.exception;

import com.jcmvesco.minesweeper.domain.Game;

public class GameFinishedException extends RuntimeException {
    private final Game game;

    public GameFinishedException(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }
}
