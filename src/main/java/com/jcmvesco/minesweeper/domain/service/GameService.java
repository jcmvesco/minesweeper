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

    @Autowired
    public GameService(GameRepository gameRepository, UserRepository userRepository) {
        this.gameRepository = gameRepository;
        this.userRepository = userRepository;
    }

    public Game createNewGame (int rows, int columns, int mines, String userName) {
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
