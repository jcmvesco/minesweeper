package com.jcmvesco.minesweeper.domain;

import com.jcmvesco.minesweeper.domain.exception.CellCannotBeOpenedException;
import com.jcmvesco.minesweeper.domain.exception.CellExplodedException;

import javax.persistence.*;

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

    @Column(name = "state", nullable = false)
    @Enumerated(EnumType.STRING)
    private CellState state;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    public Cell(int row, int column, Board board) {
        this.row = row;
        this.column = column;
        this.board = board;
        this.state = CellState.CLOSED;
    }

    public void flag() {
        this.setState(CellState.FLAGGED);
    }

    public void unFlag() {
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
            throw new CellCannotBeOpenedException();
        }
    }

    private void open() {
        //TODO
    }

    private boolean isAbleToOpen() {
        //TODO
        return false;
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
