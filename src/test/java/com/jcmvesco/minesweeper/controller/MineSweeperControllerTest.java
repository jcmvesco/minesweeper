package com.jcmvesco.minesweeper.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jcmvesco.minesweeper.api.request.Action;
import com.jcmvesco.minesweeper.api.request.ActionRequest;
import com.jcmvesco.minesweeper.api.request.NewGameRequest;
import com.jcmvesco.minesweeper.domain.Board;
import com.jcmvesco.minesweeper.domain.Game;
import com.jcmvesco.minesweeper.domain.GameState;
import com.jcmvesco.minesweeper.domain.exception.CellCannotBeOpenedException;
import com.jcmvesco.minesweeper.domain.exception.GameNotFoundException;
import com.jcmvesco.minesweeper.service.GameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = MineSweeperController.class)
class MineSweeperControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GameService gameService;

    @InjectMocks
    private MineSweeperController mineSweeperController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void createNewGame() throws Exception {
        when(gameService.createNewGame(anyInt(), anyInt(), anyInt())).thenReturn(createGame());
        NewGameRequest request = new NewGameRequest(2,4,2);
        mockMvc.perform(post("/api/minesweeper/game")
                .contextPath("/api/minesweeper")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(request)))
                .andDo(print())
                .andExpect(status().isOk());
        verify(gameService, times(1)).createNewGame(anyInt(), anyInt(), anyInt());
        verifyNoMoreInteractions(gameService);
    }

    @Test
    void takeAction() throws Exception {
        when(gameService.takeAction(anyLong(), anyInt(), anyInt(), any())).thenReturn(createGame());
        ActionRequest request = new ActionRequest(2,4, Action.DISCOVER);
        mockMvc.perform(post("/api/minesweeper/game/1")
                .contextPath("/api/minesweeper")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(request)))
                .andDo(print())
                .andExpect(status().isOk());
        verify(gameService, times(1)).takeAction(anyLong(), anyInt(), anyInt(), any());
        verifyNoMoreInteractions(gameService);
    }

    @Test
    void takeActionFails() throws Exception {
        when(gameService.takeAction(anyLong(), anyInt(), anyInt(), any())).thenThrow(new CellCannotBeOpenedException("Game with id 1 is already ended"));
        ActionRequest request = new ActionRequest(2,4, Action.DISCOVER);
        mockMvc.perform(post("/api/minesweeper/game/1")
                .contextPath("/api/minesweeper")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(request)))
                .andDo(print())
                .andExpect(status().isNotAcceptable());
        verify(gameService, times(1)).takeAction(anyLong(), anyInt(), anyInt(), any());
        verifyNoMoreInteractions(gameService);
    }

    @Test
    void getGameInProgress() throws Exception {
        Game game = createGame();
        game.setState(GameState.STARTED);
        when(gameService.findById(anyLong())).thenReturn(game);
        ActionRequest request = new ActionRequest(2,4, Action.DISCOVER);
        mockMvc.perform(get("/api/minesweeper/game/1")
                .contextPath("/api/minesweeper")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(request)))
                .andDo(print())
                .andExpect(status().isOk());
        verify(gameService, times(1)).findById(anyLong());
        verifyNoMoreInteractions(gameService);
    }

    @Test
    void getGameInFinished() throws Exception {
        Game game = createGame();
        game.setState(GameState.WON);
        when(gameService.findById(anyLong())).thenReturn(game);
        ActionRequest request = new ActionRequest(2,4, Action.DISCOVER);
        mockMvc.perform(get("/api/minesweeper/game/1")
                .contextPath("/api/minesweeper")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(request)))
                .andDo(print())
                .andExpect(status().isOk());
        verify(gameService, times(1)).findById(anyLong());
        verifyNoMoreInteractions(gameService);
    }

    @Test
    void getGameNotFound() throws Exception {
        Game game = createGame();
        game.setState(GameState.WON);
        when(gameService.findById(anyLong())).thenThrow(new GameNotFoundException("Game with id 1 not exists"));
        ActionRequest request = new ActionRequest(2,4, Action.DISCOVER);
        mockMvc.perform(get("/api/minesweeper/game/1")
                .contextPath("/api/minesweeper")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(request)))
                .andDo(print())
                .andExpect(status().isNotFound());
        verify(gameService, times(1)).findById(anyLong());
        verifyNoMoreInteractions(gameService);
    }

    static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Game createGame() {
        Board board = new Board(2, 4, 2);
        board.setId(1L);
        Game game = new Game(board);
        game.setId(1L);
        return game;
    }
}
