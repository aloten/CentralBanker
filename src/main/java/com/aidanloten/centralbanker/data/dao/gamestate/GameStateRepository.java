package com.aidanloten.centralbanker.data.dao.gamestate;

import com.aidanloten.centralbanker.data.entities.GameState;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameStateRepository extends JpaRepository<GameState, Integer> {
}
