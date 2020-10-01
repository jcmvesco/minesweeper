package com.jcmvesco.minesweeper.service;

import com.jcmvesco.minesweeper.api.Action;
import com.jcmvesco.minesweeper.domain.Board;
import com.jcmvesco.minesweeper.domain.Game;
import com.jcmvesco.minesweeper.domain.exception.CellCannotBeOpenedException;
import com.jcmvesco.minesweeper.domain.exception.GameNotFoundException;
import com.jcmvesco.minesweeper.domain.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GameService {
    private GameRepository gameRepository;

    @Autowired
    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public Game createNewGame (int rows, int columns, int mines) {
        Board board = new Board(rows, columns, mines);
        Game game = new Game(board);
        return gameRepository.save(game);
    }

    public Game takeAction(Long id, int row, int column, Action action) throws CellCannotBeOpenedException {
        Game game = findById(id);
        if(game.isNotEnded()) {
            try {
                switch (action) {
                    case DISCOVER:
                        game.discoverCell(row, column);
                        break;
                    case FLAG:
                        game.flagCell(row, column);
                        break;
                    case MARK:
                        game.markCell(row, column);
                        break;
                    case CLEAR:
                        game.clearCell(row, column);
                        break;
                }
            } finally {
                gameRepository.save(game);
            }
            return game;
        } else {
            throw new CellCannotBeOpenedException(String.format("Game with id %d is already ended", id));
        }
    }

    public Game findById(Long id) {
        Optional<Game> gameOptional = gameRepository.findById(id);
        if(gameOptional.isPresent()) {
            return gameOptional.get();
        } else {
            throw new GameNotFoundException();
        }
    }
}
