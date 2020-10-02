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

    private void start(int row, int column) {
        this.board.populateMines(row, column);
        this.startTime = LocalDateTime.now();
        this.state = GameState.STARTED;
        this.moves = 0;
    }

    private void finish(GameState state) {
        this.endTime = LocalDateTime.now();
        this.state = state;
    }

    /**
     * Uncovers a cell identified by row and column and deducts if the game change to a won or lost state
     * @param row row position of the cell
     * @param column column position of the cell
     * @throws CellCannotBeOpenedException if the cell cannot be opened
     */
    public void discoverCell(int row, int column) {
        try {
            if(GameState.CREATED.equals(this.state)) {
                start(row, column);
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

    /**
     * Change the status of a cell to FLAGGED
     * @param row row position of the cell
     * @param column column position of the cell
     */
    public void flagCell(int row, int column) {
        Cell cell = getBoard().getCell(row, column);
        if(!cell.isFlagged() && !cell.isOpened()) {
            cell.flag();
        } else {
            throw new CellCannotBeFlaggedOrMarkedException("This cell (%d,%d) is already flagged or it's opened");
        }
    }

    /**
     * Change the status of a cell to MARKED
     * @param row row position of the cell
     * @param column column position of the cell
     */
    public void markCell(int row, int column) {
        Cell cell = getBoard().getCell(row, column);
        if(!cell.isMarked()  && !cell.isOpened()) {
            cell.mark();
        } else {
            throw new CellCannotBeFlaggedOrMarkedException("This cell (%d,%d) is already marked or it's opened");
        }
    }

    /**
     * Change the status of a cell to CLOSED
     * @param row row position of the cell
     * @param column column position of the cell
     */
    public void clearCell(int row, int column) {
        Cell cell = getBoard().getCell(row, column);
        if(cell.isMarked() || cell.isFlagged()) {
            cell.clear();
        } else {
            throw new CellCannotBeFlaggedOrMarkedException("This cell (%d,%d) cannot be un-flagged or un-marked");
        }
    }

    /**
     * Check if all cells are uncovered except for the ones with mines
     * @return true if all cells are in an OPENED state and any has mine on it
     */
    public boolean isAllDiscovered() {
        return this.board.getCells().stream().noneMatch(c -> !c.isMine() && !CellState.OPENED.equals(c.getState()));
    }

    /**
     * Validates if the current game is still in progress
     * @return true if the game is not in WON or LOST state
     */
    public boolean isNotEnded() {
        return !GameState.WON.equals(this.state) && !GameState.LOST.equals(this.state);
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
}
