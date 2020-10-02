package com.jcmvesco.minesweeper.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public Board() {
    }

    public Board(int cantRows, int cantColumns, int cantMines) {
        this.cantRows = cantRows;
        this.cantColumns = cantColumns;
        this.cantMines = cantMines;

        populateCells();
    }

    private void populateCells() {
        for (int row = 0; row < cantRows; row++) {
            for (int column = 0; column < cantColumns; column++) {
                cells.add(new Cell(row, column, this));
            }
        }
    }

    /**
     * Populates the game board with mines, except in the cell of the first move choice
     * @param row row position of the cell
     * @param column column position of the cell
     */
    public void populateMines(int row, int column) {
        int mines = 0;
        do {
            int index = (int) (Math.random() * cells.size());
            Cell cell = cells.get(index);
            if(!cell.isMine() && cell.getRow() != row && cell.getColumn() != column) {
                cell.setMine(true);
                mines++;
            }
        } while(mines < cantMines);
    }

    /**
     * Retrieves the cell of the given position
     * @param row row position of the cell
     * @param column column position of the cell
     * @return the selected cell
     */
    public Cell getCell(int row, int column) {
        int index = row * cantColumns + column;
        return cells.get(index);
    }

    /**
     * Retrieves the list of all cells adjacent to the given one
     * @param cell the given cell
     * @return the list of the cell's neightbors
     */
    public List<Cell> getNeighbors(Cell cell) {
        return cells.stream().filter(c -> c.isNeighborOf(cell)).collect(Collectors.toList());
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
