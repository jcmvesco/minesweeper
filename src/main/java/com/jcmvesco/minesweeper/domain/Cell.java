package com.jcmvesco.minesweeper.domain;

import com.jcmvesco.minesweeper.domain.exception.CellCannotBeOpenedException;
import com.jcmvesco.minesweeper.domain.exception.CellExplodedException;

import javax.persistence.*;
import java.util.List;

@Entity
public class Cell {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "row_position", nullable = false)
    private int row;

    @Column(name = "column_position", nullable = false)
    private int column;

    @Column(name = "mine", nullable = false)
    private boolean mine;

    @Column(name = "neighbor_mines_cant")
    private Integer neighborMinesCant;

    @Column(name = "state", nullable = false)
    @Enumerated(EnumType.STRING)
    private CellState state;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    public Cell() {
    }

    public Cell(int row, int column, Board board) {
        this.row = row;
        this.column = column;
        this.board = board;
        this.state = CellState.CLOSED;
    }

    public boolean isOpened() {
        return CellState.OPENED.equals(this.state);
    }

    public void flag() {
        this.setState(CellState.FLAGGED);
    }

    public boolean isFlagged() {
        return CellState.FLAGGED.equals(this.state);
    }

    public void mark() {
        this.setState(CellState.MARKED);
    }

    public boolean isMarked() {
        return CellState.MARKED.equals(this.state);
    }

    public void clear() {
        this.setState(CellState.CLOSED);
    }

    public void discover() throws CellExplodedException, CellCannotBeOpenedException {
        if(isAbleToOpen()){
            if(isMine()){
                explode();
            } else {
                open();
            }
        } else {
            throw new CellCannotBeOpenedException(String.format("This cell (%d,%d) is already opened, flagged or marked", row, column));
        }
    }

    private void open() {
        this.setState(CellState.OPENED);
        List<Cell> neighbors = this.board.getNeighbors(this);
        neighborMinesCant = (int) neighbors.stream().filter(Cell::isMine).count();
        if(neighborMinesCant == 0) {
            neighbors.stream().filter(Cell::isAbleToOpen).forEach(Cell::open);
        }
    }

    private boolean isAbleToOpen() {
        return CellState.CLOSED.equals(this.state);
    }

    public boolean isNeighborOf(Cell cell) {
        return this != cell && Math.abs(this.column - cell.getColumn()) <= 1 && Math.abs(this.row - cell.getRow()) <= 1;
    }

    private void explode() throws CellExplodedException {
        setState(CellState.EXPLODED);
        throw new CellExplodedException();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public boolean isMine() {
        return mine;
    }

    public void setMine(boolean mine) {
        this.mine = mine;
    }

    public Integer getNeighborMinesCant() {
        return neighborMinesCant;
    }

    public void setNeighborMinesCant(Integer neighborMinesCant) {
        this.neighborMinesCant = neighborMinesCant;
    }

    public CellState getState() {
        return state;
    }

    public void setState(CellState state) {
        this.state = state;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }
}
