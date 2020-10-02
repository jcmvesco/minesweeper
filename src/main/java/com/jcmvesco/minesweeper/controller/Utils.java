package com.jcmvesco.minesweeper.controller;

import com.jcmvesco.minesweeper.api.response.*;
import com.jcmvesco.minesweeper.domain.Board;
import com.jcmvesco.minesweeper.domain.Game;

import java.util.List;
import java.util.stream.Collectors;

public class Utils {
    public static GameResponse mapGameInProgress(Game game) {
        List<GameInProgressCellResponse> cellResponseList = game.getBoard().getCells().stream().map(c -> new GameInProgressCellResponse(c.getRow(), c.getColumn(), c.getNeighborMinesCant(), c.getState().name())).collect(Collectors.toList());
        GameInProgressBoardResponse gameInProgressBoardResponse = new GameInProgressBoardResponse();
        gameInProgressBoardResponse.setCells(cellResponseList);
        Board board = game.getBoard();
        gameInProgressBoardResponse.setRows(board.getCantRows());
        gameInProgressBoardResponse.setColumns(board.getCantColumns());
        gameInProgressBoardResponse.setMines(board.getCantMines());
        GameInProgressResponse gameInProgressResponse = new GameInProgressResponse();
        gameInProgressResponse.setId(game.getId());
        gameInProgressResponse.setState(game.getState().name());
        gameInProgressResponse.setStarted(game.getStartTime());
        gameInProgressResponse.setEnded(game.getEndTime());
        gameInProgressResponse.setBoard(gameInProgressBoardResponse);
        gameInProgressResponse.setMoves(game.getMoves());
        return gameInProgressResponse;
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
        return gameResponse;
    }
}
