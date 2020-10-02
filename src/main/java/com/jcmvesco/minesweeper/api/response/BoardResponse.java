package com.jcmvesco.minesweeper.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class BoardResponse {
    @JsonProperty
    private int rows;
    @JsonProperty
    private int columns;
    @JsonProperty
    private int mines;
    @JsonProperty
    private List<CellResponse> cells;

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public int getMines() {
        return mines;
    }

    public void setMines(int mines) {
        this.mines = mines;
    }

    public List<CellResponse> getCells() {
        return cells;
    }

    public void setCells(List<CellResponse> cells) {
        this.cells = cells;
    }
}
