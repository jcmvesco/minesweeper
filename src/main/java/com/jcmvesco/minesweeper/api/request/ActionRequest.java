package com.jcmvesco.minesweeper.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

public class ActionRequest {
    @JsonProperty
    @Schema(example = "4")
    private int row;
    @JsonProperty
    @Schema(example = "4")
    private int column;
    @JsonProperty
    @Schema(example = "DISCOVER, FLAG, MARK, CLEAR")
    private Action action;

    public ActionRequest() {
    }

    public ActionRequest(int row, int column, Action action) {
        this.row = row;
        this.column = column;
        this.action = action;
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

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }
}
