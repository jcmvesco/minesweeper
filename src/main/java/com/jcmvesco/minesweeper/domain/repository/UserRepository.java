package com.jcmvesco.minesweeper.domain.repository;

import com.jcmvesco.minesweeper.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    User save(User game);
    User findByName(String name);
}
