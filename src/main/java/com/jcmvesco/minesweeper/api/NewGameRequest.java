package com.jcmvesco.minesweeper.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NewGameRequest {
    @JsonProperty("cant_rows")
    private int cantRows;
    @JsonProperty("cant_columns")
    private int cantColumns;
    @JsonProperty("cant_mines")
    private int cantMines;

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
}
