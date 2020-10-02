package com.jcmvesco.minesweeper.controller;

import com.jcmvesco.minesweeper.api.response.GameFinishedResponse;
import com.jcmvesco.minesweeper.api.response.GameResponse;
import com.jcmvesco.minesweeper.domain.GameState;
import com.jcmvesco.minesweeper.domain.exception.CellCannotBeFlaggedOrMarkedException;
import com.jcmvesco.minesweeper.domain.exception.CellCannotBeOpenedException;
import com.jcmvesco.minesweeper.domain.exception.GameFinishedException;
import com.jcmvesco.minesweeper.domain.exception.GameNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler
    ResponseEntity<String> handleException(Exception exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getMessage());
    }

    @ExceptionHandler
    ResponseEntity<String> handleException(HttpMessageNotReadableException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }

    @ExceptionHandler
    ResponseEntity<String> handleException(MethodArgumentNotValidException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }

    @ExceptionHandler
    ResponseEntity<String> handleException(IllegalArgumentException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }

    @ExceptionHandler
    ResponseEntity<GameResponse> handleException(GameFinishedException exception) {
        GameFinishedResponse response = (GameFinishedResponse) Utils.mapGameFinished(exception.getGame());
        if(GameState.WON.equals(exception.getGame().getState())) {
            response.setMessage(String.format("CONGRATULATIONS! YOU HAVE WON IN %d MOVES", exception.getGame().getMoves()));
        } else {
            response.setMessage("BAD LUCK! YOU LOST");
        }
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @ExceptionHandler
    ResponseEntity<String> handleException(CellCannotBeOpenedException exception) {
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(exception.getMessage());
    }

    @ExceptionHandler
    ResponseEntity<String> handleException(CellCannotBeFlaggedOrMarkedException exception) {
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(exception.getMessage());
    }

    @ExceptionHandler
    ResponseEntity<String> handleException(GameNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

}
