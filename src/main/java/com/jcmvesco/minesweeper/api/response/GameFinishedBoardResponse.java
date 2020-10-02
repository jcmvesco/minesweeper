package com.jcmvesco.minesweeper.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public class GameFinishedBoardResponse {
    @JsonProperty
    @Schema(example = "4")
    private int rows;
    @JsonProperty
    @Schema(example = "4")
    private int columns;
    @JsonProperty
    @Schema(example = "3")
    private int mines;
    @JsonProperty
    private List<GameFinishedCellResponse> cells;

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

    public List<GameFinishedCellResponse> getCells() {
        return cells;
    }

    public void setCells(List<GameFinishedCellResponse> cells) {
        this.cells = cells;
    }
}
