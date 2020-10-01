package com.jcmvesco.minesweeper.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CellResponse {
    @JsonProperty
    private int row;
    @JsonProperty
    private int column;
    @JsonProperty
    private boolean mine;
    @JsonProperty
    private Integer neighborMinesCant;
    @JsonProperty
    private String state;

    public CellResponse(int row, int column, boolean mine, Integer neighborMinesCant, String state) {
        this.row = row;
        this.column = column;
        this.mine = mine;
        this.neighborMinesCant = neighborMinesCant;
        this.state = state;
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

    public Integer getNeighborMinesCant() {
        return neighborMinesCant;
    }

    public void setNeighborMinesCant(Integer neighborMinesCant) {
        this.neighborMinesCant = neighborMinesCant;
    }

    public boolean isMine() {
        return mine;
    }

    public void setMine(boolean mine) {
        this.mine = mine;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
