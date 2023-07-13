package com.aidanloten.centralbanker.service;

import com.aidanloten.centralbanker.data.dao.gamestate.GameStateRepository;
import com.aidanloten.centralbanker.data.entities.GameState;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class GameStateService {
    private final GameStateRepository gameStateRepository;

    public GameStateService(GameStateRepository gameStateRepository) {
        this.gameStateRepository = gameStateRepository;
    }

    public void saveGameState(GameState gameState) {
        gameStateRepository.save(gameState);
    }

    public void setShouldStreamPersonAssets(boolean shouldStreamPersonAssets) {
        GameState gameState = getGameState();
        gameState.setShouldStreamPersonAssets(shouldStreamPersonAssets);
        gameStateRepository.save(gameState);
    }

    public boolean getShouldStreamPersonAssets() {
        return getGameState().isShouldStreamPersonAssets();
    }

    public LocalDate getDate() {
        return getGameState().getDate();
    }

    public void play() {
        GameState gameState = getGameState();
        gameState.setGamePaused(false);
        gameStateRepository.save(gameState);
    }

    public void pause() {
        GameState gameState = getGameState();
        gameState.setGamePaused(true);
        gameStateRepository.save(gameState);
    }

    public boolean isGamePaused() {
        return getGameState().isGamePaused();
    }

    public void createNewRunningGameState() {
        gameStateRepository.save(new GameState());
    }

    public GameState getGameState() {
        return gameStateRepository.findById(1).orElseThrow();
    }
}
