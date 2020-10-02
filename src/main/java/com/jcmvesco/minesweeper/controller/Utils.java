package com.jcmvesco.minesweeper.controller;

import com.jcmvesco.minesweeper.api.response.*;
import com.jcmvesco.minesweeper.domain.Board;
import com.jcmvesco.minesweeper.domain.Game;

import java.util.List;
import java.util.stream.Collectors;

public class Utils {
    public static GameResponse mapGameInProgress(Game game) {
        List<GameInProgressCellResponse> cellResponseList = game.getBoard().getCells().stream().map(c -> new GameInProgressCellResponse(c.getRow(), c.getColumn(), c.getNeighborMinesCant(), c.getState().name())).collect(Collectors.toList());
        GameInProgressBoardResponse boardResponse = new GameInProgressBoardResponse();
        boardResponse.setCells(cellResponseList);
        Board board = game.getBoard();
        boardResponse.setRows(board.getCantRows());
        boardResponse.setColumns(board.getCantColumns());
        boardResponse.setMines(board.getCantMines());
        GameInProgressResponse gameResponse = new GameInProgressResponse();
        gameResponse.setId(game.getId());
        gameResponse.setState(game.getState().name());
        gameResponse.setStarted(game.getStartTime());
        gameResponse.setEnded(game.getEndTime());
        gameResponse.setBoard(boardResponse);
        gameResponse.setMoves(game.getMoves());
        UserResponse userResponse = new UserResponse(game.getUser().getId(), game.getUser().getName());
        gameResponse.setUser(userResponse);
        return gameResponse;
    }

    public static GameResponse mapGameFinished(Game game) {
        List<GameFinishedCellResponse> cellResponseList = game.getBoard().getCells().stream().map(c -> new GameFinishedCellResponse(c.getRow(), c.getColumn(), c.isMine(), c.getNeighborMinesCant(), c.getState().name())).collect(Collectors.toList());
        GameFinishedBoardResponse boardResponse = new GameFinishedBoardResponse();
        boardResponse.setCells(cellResponseList);
        Board board = game.getBoard();
        boardResponse.setRows(board.getCantRows());
        boardResponse.setColumns(board.getCantColumns());
        boardResponse.setMines(board.getCantMines());
        GameFinishedResponse gameResponse = new GameFinishedResponse();
        gameResponse.setId(game.getId());
        gameResponse.setState(game.getState().name());
        gameResponse.setStarted(game.getStartTime());
        gameResponse.setEnded(game.getEndTime());
        gameResponse.setBoard(boardResponse);
        gameResponse.setMoves(game.getMoves());
        gameResponse.setMessage("Game finished");
        UserResponse userResponse = new UserResponse(game.getUser().getId(), game.getUser().getName());
        gameResponse.setUser(userResponse);
        return gameResponse;
    }
}
