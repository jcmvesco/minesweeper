package com.jcmvesco.minesweeper.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @Test
    @DisplayName("Populates a board with mines randomly")
    void populateMines() {
        Game game = createGame();
        Board board = game.getBoard();
        board.populateMines(0,0);
        assertEquals(2, board.getCells().stream().filter(cell -> cell.isMine()).count());
        assertFalse(board.getCell(0,0).isMine());
    }

    @Test
    @DisplayName("Retrieves the cell (1,1) from the given board")
    void getCell() {
        Game game = createGame();
        Board board = game.getBoard();
        Cell cell1 = board.getCell(1,1);
        Cell cell2 = board.getCells().get(5);
        assertEquals(cell1, cell2);
    }

    @Test
    @DisplayName("Retrieves neighbors of the cell (0,3) from the given board (2x4)")
    void getNeighbors() {
        Game game = createGame();
        Board board = game.getBoard();
        Cell cell1 = board.getCell(0,3);
        Cell cell2 = board.getCell(0,2);
        Cell cell3 = board.getCell(1,2);
        Cell cell4 = board.getCell(1,3);
        List<Cell> neighbors = board.getNeighbors(cell1);
        assertEquals(3, neighbors.size());
        assertTrue(neighbors.contains(cell2));
        assertTrue(neighbors.contains(cell3));
        assertTrue(neighbors.contains(cell4));
    }

    private Game createGame() {
        Board board = new Board(2, 4, 2);
        return new Game(board);
    }
}
