package com.jcmvesco.minesweeper.domain;

import com.jcmvesco.minesweeper.domain.exception.CellCannotBeOpenedException;
import com.jcmvesco.minesweeper.domain.exception.GameFinishedException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CellTest {

    @Test
    @DisplayName("Takes a discover action over a cell")
    void discover() {
        Game game = createGame();
        game.setState(GameState.STARTED);
        game.discoverCell(0,3);
        assertTrue(game.getBoard().getCell(1,3).isOpened());
        assertTrue(game.getBoard().getCell(0,2).isOpened());
        assertTrue(game.getBoard().getCell(1,2).isOpened());
    }

    @Test
    @DisplayName("Fails to take a discover action over a cell")
    void discoverFails() {
        Game game = createGame();
        game.setState(GameState.STARTED);
        game.discoverCell(0,3);
        Assertions.assertThrows(CellCannotBeOpenedException.class, () -> game.discoverCell(0,3));
    }

    @Test
    @DisplayName("Takes a discover action over a cell and explodes")
    void discoverExplodes() {
        Game game = createGame();
        game.setState(GameState.STARTED);
        Assertions.assertThrows(GameFinishedException.class, () -> game.discoverCell(0,0));
    }

    @Test
    @DisplayName("Discover all cells in a board of 2x4 by taking action on 3 cells")
    void discoverAll() {
        Game game = createGame();
        game.setState(GameState.STARTED);
        game.discoverCell(0,3);
        game.discoverCell(0,1);
        Assertions.assertThrows(GameFinishedException.class, () -> game.discoverCell(1,0));
    }

    @Test
    @DisplayName("Validates if a cell (0,0) is neighbor of another (0,1) and not of (1,3)")
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
