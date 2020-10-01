package com.jcmvesco.minesweeper.service;

import com.jcmvesco.minesweeper.api.ClickRequest;
import com.jcmvesco.minesweeper.api.NewGameRequest;
import com.jcmvesco.minesweeper.domain.Game;
import com.jcmvesco.minesweeper.domain.exception.CellCannotBeOpenedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("game")
public class MineSweeperController {

    private GameService gameService;

    @Autowired
    public MineSweeperController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping
    public ResponseEntity<Long> createNewGame(@RequestBody NewGameRequest request) {
        Game game = gameService.createNewGame(request.getCantRows(), request.getCantColumns(), request.getCantMines());
        return new ResponseEntity<>(game.getId(), HttpStatus.OK);
    }

    @PostMapping("/id")
    public ResponseEntity<Long> click(@PathVariable Long id, @RequestBody ClickRequest request) {
        Game game = null;
        try {
            game = gameService.click(id, request.getRow(), request.getColumn(), request.getAction());
        } catch (CellCannotBeOpenedException e) {
            //TODO
            return new ResponseEntity<>(game.getId(), HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<>(game.getId(), HttpStatus.OK);
    }
}
