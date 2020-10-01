package com.jcmvesco.minesweeper.domain.repository;

import com.jcmvesco.minesweeper.domain.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface GameRepository extends JpaRepository<Game, Long>, JpaSpecificationExecutor<Game> {
    Game save(Game game);
    Game get(Long id);
    Game update(Game game);
}
