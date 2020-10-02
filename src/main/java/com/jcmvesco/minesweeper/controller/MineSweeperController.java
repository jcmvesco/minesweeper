package com.jcmvesco.minesweeper.controller;

import com.jcmvesco.minesweeper.api.request.ActionRequest;
import com.jcmvesco.minesweeper.api.request.NewGameRequest;
import com.jcmvesco.minesweeper.api.response.GameInProgressResponse;
import com.jcmvesco.minesweeper.api.response.GameResponse;
import com.jcmvesco.minesweeper.domain.Game;
import com.jcmvesco.minesweeper.service.GameService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.jcmvesco.minesweeper.controller.Utils.mapGameFinished;
import static com.jcmvesco.minesweeper.controller.Utils.mapGameInProgress;

@RestController
@RequestMapping("game")
@Api(value = "/game", tags = { "Game Controller" })
public class MineSweeperController {

    private final GameService gameService;

    @Autowired
    public MineSweeperController(GameService gameService) {
        this.gameService = gameService;
    }

    @ApiOperation(value = "createNewGame", notes = "Creates a new minesweeper game", response = Long.class)
    @PostMapping
    public ResponseEntity<GameResponse> createNewGame(@RequestBody NewGameRequest request) {
        Game game = gameService.createNewGame(request.getCantRows(), request.getCantColumns(), request.getCantMines());
        return new ResponseEntity<>(mapGameInProgress(game), HttpStatus.OK);
    }

    @ApiOperation(value = "takeAction", notes = "Takes an action over a cell", response = GameInProgressResponse.class)
    @PostMapping("/{id}")
    public ResponseEntity<GameResponse> takeAction(@PathVariable Long id, @RequestBody ActionRequest request) {
        Game game = gameService.takeAction(id, request.getRow(), request.getColumn(), request.getAction());
        return new ResponseEntity<>(mapGameInProgress(game),HttpStatus.OK);
    }

    @ApiOperation(value = "getGame", notes = "Retrieves the game's detail", response = GameResponse.class)
    @GetMapping("/{id}")
    public ResponseEntity<GameResponse> getGame(@PathVariable Long id) {
        Game game = gameService.findById(id);
        if(game.isNotEnded()) {
            return new ResponseEntity<>(mapGameInProgress(game), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(mapGameFinished(game), HttpStatus.OK);
        }
    }
}
