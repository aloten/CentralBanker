package com.aidanloten.centralbanker.service;

import com.aidanloten.centralbanker.data.dao.gamestate.GameStateRepository;
import com.aidanloten.centralbanker.data.entities.GameState;
import org.springframework.stereotype.Service;

@Service
public class GameStateService {
    private final GameStateRepository gameStateRepository;

    public GameStateService(GameStateRepository gameStateRepository) {
        this.gameStateRepository = gameStateRepository;
    }

    /** flip the isGameRunning flag */
    public void pauseResume() {
        GameState gameState = getGameState();
        gameState.setGameRunning(!gameState.isGameRunning());
    }

    public boolean isGameRunning() {
        return getGameState().isGameRunning();
    }

    public void setGameRunningFalse() {
        GameState gameState = getGameState();
        gameState.setGameRunning(false);
        gameStateRepository.save(gameState);
    }

    public void createNewRunningGameState() {
        gameStateRepository.save(GameState.builder().isGameRunning(true).build());
    }

    public GameState getGameState() {
        return gameStateRepository.findById(1).orElseThrow();
    }
}
