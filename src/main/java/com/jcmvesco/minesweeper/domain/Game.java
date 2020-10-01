package com.jcmvesco.minesweeper.domain;

import com.jcmvesco.minesweeper.domain.exception.CellCannotBeFlaggedOrMarkedException;
import com.jcmvesco.minesweeper.domain.exception.CellCannotBeOpenedException;
import com.jcmvesco.minesweeper.domain.exception.CellExplodedException;
import com.jcmvesco.minesweeper.domain.exception.GameFinishedException;

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

    public Game() {
    }

    public Game(Board board) {
        this.board = board;
        this.state = GameState.CREATED;
    }

    private void start() {
        this.startTime = LocalDateTime.now();
        this.state = GameState.STARTED;
        this.moves = 0;
    }

    private void finish(GameState state) {
        this.endTime = LocalDateTime.now();
        this.state = state;
    }

    public void discoverCell(int row, int column) throws CellCannotBeOpenedException, GameFinishedException {
        try {
            if(GameState.CREATED.equals(this.state)) {
                start();
            }
            this.moves++;
            getBoard().getCell(row, column).discover();
            if(isAllDiscovered()) {
                finish(GameState.WON);
                throw new GameFinishedException(this);
            }
        } catch (CellExplodedException e) {
            finish(GameState.LOST);
            throw new GameFinishedException(this);
        }
    }

    public void flagCell(int row, int column) {
        Cell cell = getBoard().getCell(row, column);
        if(!cell.isFlagged() && !cell.isOpened()) {
            cell.flag();
        } else {
            throw new CellCannotBeFlaggedOrMarkedException("This cell (%d,%d) is already flagged or it's opened");
        }
    }

    public void markCell(int row, int column) {
        Cell cell = getBoard().getCell(row, column);
        if(!cell.isMarked()  && !cell.isOpened()) {
            cell.mark();
        } else {
            throw new CellCannotBeFlaggedOrMarkedException("This cell (%d,%d) is already marked or it's opened");
        }
    }

    public void clearCell(int row, int column) {
        Cell cell = getBoard().getCell(row, column);
        if(cell.isMarked() || cell.isFlagged()) {
            cell.clear();
        } else {
            throw new CellCannotBeFlaggedOrMarkedException("This cell (%d,%d) cannot be un-flagged or un-marked");
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
        return this.board.getCells().stream().noneMatch(c -> !c.isMine() && !CellState.OPENED.equals(c.getState()));
    }

    public boolean isNotEnded() {
        return !GameState.WON.equals(this.state) && !GameState.LOST.equals(this.state);
    }
}
