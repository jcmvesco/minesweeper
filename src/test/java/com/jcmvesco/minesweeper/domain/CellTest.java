package com.jcmvesco.minesweeper.domain;

import com.jcmvesco.minesweeper.domain.exception.CellCannotBeOpenedException;
import com.jcmvesco.minesweeper.domain.exception.GameFinishedException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CellTest {

    @Test
    void discover() {
        Game game = createGame();
        game.setState(GameState.STARTED);
        game.discoverCell(0,3);
        assertTrue(game.getBoard().getCell(1,3).isOpened());
        assertTrue(game.getBoard().getCell(0,2).isOpened());
        assertTrue(game.getBoard().getCell(1,2).isOpened());
    }

    @Test
    void discoverFails() {
        Game game = createGame();
        game.setState(GameState.STARTED);
        game.discoverCell(0,3);
        Assertions.assertThrows(CellCannotBeOpenedException.class, () -> game.discoverCell(0,3));
    }

    @Test
    void discoverExplodes() {
        Game game = createGame();
        game.setState(GameState.STARTED);
        Assertions.assertThrows(GameFinishedException.class, () -> game.discoverCell(0,0));
    }

    @Test
    void discoverAll() {
        Game game = createGame();
        game.setState(GameState.STARTED);
        game.discoverCell(0,3);
        game.discoverCell(0,1);
        Assertions.assertThrows(GameFinishedException.class, () -> game.discoverCell(1,0));
    }

    @Test
    void isNeighborOf() {
        Game game = createGame();
        Cell cell1 = game.getBoard().getCell(0,0);
        Cell cell2 = game.getBoard().getCell(0,1);
        assertTrue(cell1.isNeighborOf(cell2));
        Cell cell3 = game.getBoard().getCell(1,3);
        assertFalse(cell1.isNeighborOf(cell3));
    }

    private Game createGame() {
        Board board = new Board(2, 4, 2);
        board.getCell(0,0).setMine(true);
        board.getCell(1,1).setMine(true);
        return new Game(board);
    }
}
