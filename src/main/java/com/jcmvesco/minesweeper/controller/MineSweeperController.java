package com.jcmvesco.minesweeper.controller;

import com.jcmvesco.minesweeper.api.request.ActionRequest;
import com.jcmvesco.minesweeper.api.request.NewGameRequest;
import com.jcmvesco.minesweeper.api.response.BoardResponse;
import com.jcmvesco.minesweeper.api.response.CellResponse;
import com.jcmvesco.minesweeper.api.response.GameResponse;
import com.jcmvesco.minesweeper.domain.Board;
import com.jcmvesco.minesweeper.domain.Game;
import com.jcmvesco.minesweeper.domain.exception.CellCannotBeOpenedException;
import com.jcmvesco.minesweeper.domain.exception.GameFinishedException;
import com.jcmvesco.minesweeper.service.GameService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("game")
@Api(value = "/game", tags = { "Game Controller" })
public class MineSweeperController {

    private GameService gameService;

    @Autowired
    public MineSweeperController(GameService gameService) {
        this.gameService = gameService;
    }

    @ApiOperation(value = "createNewGame", notes = "Creates a new minesweeper game", response = Long.class)
    @PostMapping
    public ResponseEntity<Long> createNewGame(@RequestBody NewGameRequest request) {
        Game game = gameService.createNewGame(request.getCantRows(), request.getCantColumns(), request.getCantMines());
        return new ResponseEntity<>(game.getId(), HttpStatus.OK);
    }

    @ApiOperation(value = "takeAction", notes = "Takes an action over a cell", response = GameResponse.class)
    @PostMapping("/{id}")
    public ResponseEntity<GameResponse> takeAction(@PathVariable Long id, @RequestBody ActionRequest request) {
        try {
            Game game = gameService.takeAction(id, request.getRow(), request.getColumn(), request.getAction());
            return new ResponseEntity<>(map(game),HttpStatus.OK);
        } catch (CellCannotBeOpenedException e) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        } catch (GameFinishedException e) {
            return new ResponseEntity<>(map(e.getGame()), HttpStatus.OK);
        }
    }

    @ApiOperation(value = "getGame", notes = "Retrieves the game's detail", response = GameResponse.class)
    @GetMapping("/{id}")
    public ResponseEntity<GameResponse> getGame(@PathVariable Long id) {
        Game game = gameService.findById(id);
        return new ResponseEntity<>(map(game), HttpStatus.OK);
    }

    private GameResponse map(Game game) {
        List<CellResponse> cellResponseList = game.getBoard().getCells().stream().map(c -> new CellResponse(c.getRow(), c.getColumn(), c.isMine(), c.getNeighborMinesCant(), c.getState().name())).collect(Collectors.toList());
        BoardResponse boardResponse = new BoardResponse();
        boardResponse.setCells(cellResponseList);
        Board board = game.getBoard();
        boardResponse.setRows(board.getCantRows());
        boardResponse.setColumns(board.getCantColumns());
        boardResponse.setMines(board.getCantMines());
        GameResponse gameResponse = new GameResponse();
        gameResponse.setId(game.getId());
        gameResponse.setState(game.getState().name());
        gameResponse.setStarted(game.getStartTime());
        gameResponse.setEnded(game.getEndTime());
        gameResponse.setBoard(boardResponse);
        gameResponse.setMoves(game.getMoves());
        return gameResponse;
    }
}
