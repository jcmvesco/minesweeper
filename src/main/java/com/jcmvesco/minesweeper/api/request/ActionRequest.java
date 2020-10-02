package com.jcmvesco.minesweeper.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ActionRequest {
    @JsonProperty
    private int row;
    @JsonProperty
    private int column;
    @JsonProperty
    private Action action;

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