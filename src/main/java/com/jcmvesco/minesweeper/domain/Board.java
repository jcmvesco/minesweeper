package com.jcmvesco.minesweeper.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "rows")
    private int cantRows;

    @Column(name = "columns")
    private int cantColumns;

    @Column(name = "mines")
    private int cantMines;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Cell> cells = new ArrayList<>();

    public Board(int cantRows, int cantColumns, int cantMines) {
        this.cantRows = cantRows;
        this.cantColumns = cantColumns;
        this.cantMines = cantMines;

        populateCells();
        populateMines();
    }

    private void populateCells() {
        for (int row = 0; row < cantRows; row++) {
            for (int column = 0; column < cantColumns; column++) {
                cells.add(new Cell(row, column, this));
            }
        }
    }

    private void populateMines() {
        int mines = 0;
        while(mines <= cantMines){
            int index = (int) (Math.random() * cells.size());
            Cell cell = cells.get(index);
            if(!cell.isMine()) {
                cell.setMine(true);
                mines++;
            }
        }
    }

    public Cell getCell(int row, int column) {
        int index = row * cantColumns + column;
        return cells.get(index);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCantRows() {
        return cantRows;
    }

    public void setCantRows(int cantRows) {
        this.cantRows = cantRows;
    }

    public int getCantColumns() {
        return cantColumns;
    }

    public void setCantColumns(int cantColumns) {
        this.cantColumns = cantColumns;
    }

    public int getCantMines() {
        return cantMines;
    }

    public void setCantMines(int cantMines) {
        this.cantMines = cantMines;
    }

    public List<Cell> getCells() {
        return cells;
    }

    public void setCells(List<Cell> cells) {
        this.cells = cells;
    }
}
