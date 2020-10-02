package com.jcmvesco.minesweeper.service;

import com.jcmvesco.minesweeper.api.request.Action;
import com.jcmvesco.minesweeper.domain.Board;
import com.jcmvesco.minesweeper.domain.Game;
import com.jcmvesco.minesweeper.domain.GameState;
import com.jcmvesco.minesweeper.domain.exception.CellCannotBeOpenedException;
import com.jcmvesco.minesweeper.domain.exception.GameFinishedException;
import com.jcmvesco.minesweeper.domain.exception.GameNotFoundException;
import com.jcmvesco.minesweeper.domain.repository.GameRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class GameServiceTest {
    private GameService gameService;

    @Mock
    private GameRepository gameRepository;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
        gameService = new GameService(gameRepository);
        when(gameRepository.save(any())).then(returnsFirstArg());
    }

    @Test
    void createNewGame() {
        Game game = gameService.createNewGame(2,3,2);
        ArgumentCaptor<Game> gameCaptor = ArgumentCaptor.forClass(Game.class);
        verify(gameRepository, times(1)).save(gameCaptor.capture());
        assertEquals(6, game.getBoard().getCells().size());
    }

    @Test
    void takeActionSuccess() {
        Game game = createGame();
        when(gameRepository.findById(anyLong())).thenReturn(Optional.of(game));
        gameService.takeAction(1L, 1, 3, Action.DISCOVER);
        when(gameRepository.save(eq(game))).thenReturn(game);
        verify(gameRepository, times(1)).findById(anyLong());
        verify(gameRepository, times(1)).save(game);
    }

    @Test
    void takeActionDiscoverCellWinGame() {
        Game game = createGame();
        when(gameRepository.findById(anyLong())).thenReturn(Optional.of(game));
        gameService.takeAction(1L, 0, 3, Action.DISCOVER);
        gameService.takeAction(1L, 0, 1, Action.DISCOVER);
        Assertions.assertThrows(GameFinishedException.class, () -> gameService.takeAction(1L, 1, 0, Action.DISCOVER));
        when(gameRepository.save(eq(game))).thenReturn(game);
        verify(gameRepository, times(3)).findById(anyLong());
        verify(gameRepository, times(3)).save(game);
    }

    @Test
    void takeActionDiscoverCellGameFinishedFails() throws Exception {
        Game game = createGame();
        when(gameRepository.findById(anyLong())).thenReturn(Optional.of(game));
        gameService.takeAction(1L, 0, 3, Action.DISCOVER);
        gameService.takeAction(1L, 0, 1, Action.DISCOVER);
        Assertions.assertThrows(GameFinishedException.class, () -> gameService.takeAction(1L, 1, 0, Action.DISCOVER));
        Assertions.assertThrows(CellCannotBeOpenedException.class, () -> gameService.takeAction(1L, 1, 0, Action.DISCOVER));
        when(gameRepository.save(eq(game))).thenReturn(game);
        verify(gameRepository, times(4)).findById(anyLong());
        verify(gameRepository, times(3)).save(game);
    }

    @Test
    void findById() throws Exception {
        Game game1 = createGame();
        when(gameRepository.findById(anyLong())).thenReturn(Optional.of(game1));
        Game game2 = gameService.findById(1L);
        assertEquals(game1, game2);
    }

    @Test
    void findByIdNotFound() throws Exception {
        Game game1 = createGame();
        when(gameRepository.findById(anyLong())).thenReturn(Optional.empty());
        Assertions.assertThrows(GameNotFoundException.class, () -> gameService.findById(1L));
    }

    private Game createGame() {
        Board board = new Board(2, 4, 2);
        board.getCell(0,0).setMine(true);
        board.getCell(1,1).setMine(true);
        Game game = new Game(board);
        game.setId(1L);
        game.setState(GameState.STARTED);
        return game;
    }
}
