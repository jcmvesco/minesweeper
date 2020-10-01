package com.jcmvesco.minesweeper.domain;

import com.jcmvesco.minesweeper.domain.exception.CellCannotBeOpenedException;
import com.jcmvesco.minesweeper.domain.exception.CellExplodedException;
import com.jcmvesco.minesweeper.domain.exception.GameLostException;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "board_id", referencedColumnName = "id", nullable = false)
    private Board board;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(name = "state", nullable = false)
    @Enumerated(EnumType.STRING)
    private GameState state;

    @Column(name = "moves")
    private int moves;

    public Game(Board board) {
        this.board = board;
        this.state = GameState.CREATED;
    }

    private void start() {
        startTime = LocalDateTime.now();
        state = GameState.STARTED;
        moves = 0;
    }

    public void discoverCell(int row, int column) throws CellCannotBeOpenedException, GameLostException {
        try {
            getBoard().getCell(row, column).discover();
        } catch (CellExplodedException e) {
            setState(GameState.LOST);
            throw new GameLostException();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public GameState getState() {
        return state;
    }

    public void setState(GameState state) {
        this.state = state;
    }

    public int getMoves() {
        return moves;
    }

    public void setMoves(int moves) {
        this.moves = moves;
    }

    public boolean isAllDiscovered() {
        //TODO
        return false;
    }
}
