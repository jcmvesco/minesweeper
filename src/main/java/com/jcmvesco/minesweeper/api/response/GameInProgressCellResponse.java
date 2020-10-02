package com.jcmvesco.minesweeper.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GameInProgressCellResponse {
    @JsonProperty
    private int row;
    @JsonProperty
    private int column;
    @JsonProperty
    private Integer neighborMinesCant;
    @JsonProperty
    private String state;

    public GameInProgressCellResponse(int row, int column, Integer neighborMinesCant, String state) {
        this.row = row;
        this.column = column;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
