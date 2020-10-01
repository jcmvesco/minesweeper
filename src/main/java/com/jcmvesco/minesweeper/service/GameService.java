package com.jcmvesco.minesweeper.service;

import com.jcmvesco.minesweeper.api.Action;
import com.jcmvesco.minesweeper.domain.Board;
import com.jcmvesco.minesweeper.domain.Game;
import com.jcmvesco.minesweeper.domain.exception.CellCannotBeOpenedException;
import com.jcmvesco.minesweeper.domain.exception.GameLostException;
import com.jcmvesco.minesweeper.domain.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Game click(Long id, int row, int column, Action action) throws CellCannotBeOpenedException {
        Game game = gameRepository.get(id);
        try {
            switch (action) {
                case DISCOVER:
                    game.discoverCell(row, column);
                    break;
                case FLAG:
                    game.getBoard().getCell(row, column).flag();
                    break;
                case UNFLAG:
                    game.getBoard().getCell(row, column).unFlag();
                    break;
            }
        } catch (GameLostException e) {
            //TODO
        } finally {
            gameRepository.update(game);
        }
        return game;
    }
}
