package com.jcmvesco.minesweeper.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

public class GameInProgressCellResponse {
    @JsonProperty
    @Schema(example = "4")
    private int row;
    @JsonProperty
    @Schema(example = "4")
    private int column;
    @JsonProperty
    @Schema(example = "1")
    private Integer neighborMinesCant;
    @JsonProperty
    @Schema(example = "CLOSED, OPENED, FLAGGED, MARKED, EXPLODED")
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
