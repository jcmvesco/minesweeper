package com.jcmvesco.minesweeper.domain;

import com.jcmvesco.minesweeper.domain.exception.CellCannotBeFlaggedOrMarkedException;
import com.jcmvesco.minesweeper.domain.exception.GameFinishedException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    @Test
    void discoverCellAndCreateGame() {
        Game game = createGame();
        assertEquals(GameState.CREATED, game.getState());
        game.discoverCell(0,3);
        assertEquals(GameState.STARTED, game.getState());
        assertNotNull(game.getStartTime());
        assertEquals(1, game.getMoves());
    }

    @Test
    void winGame() {
        Game game = createGame();
        game.discoverCell(0,3);
        List<Cell> cells = game.getBoard().getCells().stream().filter(cell -> !cell.isMine()).collect(Collectors.toList());
        Assertions.assertThrows(GameFinishedException.class, () -> cells.forEach(cell -> {
            if(!cell.isOpened()) {
                game.discoverCell(cell.getRow(), cell.getColumn());
            };
        }));
    }

    @Test
    void lostGame() {
        Game game = createGame();
        game.discoverCell(0,3);
        List<Cell> cells = game.getBoard().getCells().stream().filter(Cell::isMine).collect(Collectors.toList());
        Assertions.assertThrows(GameFinishedException.class, () -> game.discoverCell(cells.get(0).getRow(), cells.get(0).getColumn()));
    }

    @Test
    void flagCell() {
        Game game = createGame();
        game.setState(GameState.STARTED);
        game.flagCell(0,0);
        Cell cell = game.getBoard().getCell(0,0);
        assertTrue(cell.isFlagged());
    }

    @Test
    void flagCellFail() {
        Game game = createGame();
        game.setState(GameState.STARTED);
        game.flagCell(0,0);
        Assertions.assertThrows(CellCannotBeFlaggedOrMarkedException.class, () -> game.flagCell(0,0));
    }

    @Test
    void markCell() {
        Game game = createGame();
        game.setState(GameState.STARTED);
        game.markCell(0,0);
        Cell cell = game.getBoard().getCell(0,0);
        assertTrue(cell.isMarked());
    }

    @Test
    void markCellFail() {
        Game game = createGame();
        game.setState(GameState.STARTED);
        game.markCell(0,0);
        Assertions.assertThrows(CellCannotBeFlaggedOrMarkedException.class, () -> game.markCell(0,0));
    }

    @Test
    void clearCell() throws Exception {
        Game game = createGame();
        game.setState(GameState.STARTED);
        game.flagCell(0,0);
        Cell cell = game.getBoard().getCell(0,0);
        game.clearCell(0,0);
        assertEquals(CellState.CLOSED, cell.getState());
    }

    @Test
    void clearCellFails() throws Exception {
        Game game = createGame();
        game.setState(GameState.STARTED);
        Assertions.assertThrows(CellCannotBeFlaggedOrMarkedException.class, () -> game.clearCell(0,0));
    }

    private Game createGame() {
        Board board = new Board(2, 4, 2);
        return new Game(board);
    }
}
