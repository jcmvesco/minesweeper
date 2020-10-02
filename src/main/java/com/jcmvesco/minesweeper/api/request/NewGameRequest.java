package com.jcmvesco.minesweeper.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NewGameRequest {
    @JsonProperty("cant_rows")
    private int cantRows;
    @JsonProperty("cant_columns")
    private int cantColumns;
    @JsonProperty("cant_mines")
    private int cantMines;
    @JsonProperty("user_name")
    private String userName;

    public NewGameRequest() {
    }

    public NewGameRequest(int cantRows, int cantColumns, int cantMines, String userName) {
        this.cantRows = cantRows;
        this.cantColumns = cantColumns;
        this.cantMines = cantMines;
        this.userName = userName;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
