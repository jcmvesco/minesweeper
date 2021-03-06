package com.jcmvesco.minesweeper.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public class GameFinishedResponse implements GameResponse {
    @JsonProperty
    @Schema(example = "YOU LOST!")
    private String message;
    @JsonProperty
    @Schema(example = "1")
    private Long id;
    @JsonProperty
    @Schema(example = "CREATED, STARTED, WON, LOST")
    private String state;
    @JsonProperty
    private LocalDateTime started;
    @JsonProperty
    private LocalDateTime ended;
    @JsonProperty
    private GameFinishedBoardResponse board;
    @JsonProperty
    @Schema(example = "10")
    private int moves;
    @JsonProperty
    private UserResponse user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public LocalDateTime getStarted() {
        return started;
    }

    public void setStarted(LocalDateTime started) {
        this.started = started;
    }

    public LocalDateTime getEnded() {
        return ended;
    }

    public void setEnded(LocalDateTime ended) {
        this.ended = ended;
    }

    public GameFinishedBoardResponse getBoard() {
        return board;
    }

    public void setBoard(GameFinishedBoardResponse board) {
        this.board = board;
    }

    public int getMoves() {
        return moves;
    }

    public void setMoves(int moves) {
        this.moves = moves;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UserResponse getUser() {
        return user;
    }

    public void setUser(UserResponse user) {
        this.user = user;
    }

}
