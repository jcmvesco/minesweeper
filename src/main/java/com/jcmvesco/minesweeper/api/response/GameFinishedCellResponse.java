package com.jcmvesco.minesweeper.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

public class GameFinishedCellResponse extends GameInProgressCellResponse {
    @JsonProperty
    @Schema(example = "true")
    private boolean mine;

    public GameFinishedCellResponse(int row, int column, boolean mine, Integer neighborMinesCant, String state) {
        super(row, column, neighborMinesCant, state);
        this.mine = mine;
    }

    public boolean isMine() {
        return mine;
    }

    public void setMine(boolean mine) {
        this.mine = mine;
    }
}
