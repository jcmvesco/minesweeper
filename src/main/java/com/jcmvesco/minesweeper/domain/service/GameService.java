package com.jcmvesco.minesweeper.domain.service;

import com.jcmvesco.minesweeper.api.request.Action;
import com.jcmvesco.minesweeper.domain.Board;
import com.jcmvesco.minesweeper.domain.Game;
import com.jcmvesco.minesweeper.domain.User;
import com.jcmvesco.minesweeper.domain.exception.CellCannotBeOpenedException;
import com.jcmvesco.minesweeper.domain.exception.GameNotFoundException;
import com.jcmvesco.minesweeper.domain.repository.GameRepository;
import com.jcmvesco.minesweeper.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GameService {
    private final GameRepository gameRepository;
    private final UserRepository userRepository;

    private static final int MAX_ROWS = 30;
    private static final int MAX_COLUMNS = 30;
    private static final int MAX_MINES = 99;

    private static final int MIN_ROWS = 2;
    private static final int MIN_COLUMNS = 2;
    private static final int MIN_MINES = 1;

    @Autowired
    public GameService(GameRepository gameRepository, UserRepository userRepository) {
        this.gameRepository = gameRepository;
        this.userRepository = userRepository;
    }

    public Game createNewGame (int rows, int columns, int mines, String userName) {
        validate(rows, columns, mines, userName);
        User user = userRepository.findByName(userName);
        if(user == null) {
            user = new User(userName);
            userRepository.save(user);
        }
        Board board = new Board(rows, columns, mines);
        Game game = new Game(board);
        game.setUser(user);
        return gameRepository.save(game);
    }

    private void validate(int rows, int columns, int mines, String userName) {
        if(rows > MAX_ROWS || columns > MAX_COLUMNS || mines > MAX_MINES) {
            throw new IllegalArgumentException(String.format("Max size for rows is %d, for columns is %d and for mines is %d", MAX_ROWS, MAX_COLUMNS, MAX_MINES));
        }
        if(rows < 2 || columns < 2 || mines < 1) {
            throw new IllegalArgumentException(String.format("There should be at least %d rows, %d columns and %d mines", MIN_ROWS, MIN_COLUMNS, MIN_MINES));
        }
        if(mines > rows * columns) {
            throw new IllegalArgumentException("The amount of mines can't be greater than existing cells");
        }
    }

    public Game takeAction(Long id, int row, int column, Action action) {
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
            throw new GameNotFoundException(String.format("Game with id %d not exists", id));
        }
    }
}
