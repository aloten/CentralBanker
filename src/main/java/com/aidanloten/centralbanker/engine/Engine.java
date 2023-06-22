package com.aidanloten.centralbanker.engine;

import com.aidanloten.centralbanker.controllers.EconomyController;
import com.aidanloten.centralbanker.service.GameStateService;
import com.aidanloten.centralbanker.service.SseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class Engine {
    private final GameCycleService gameCycleService;
    private final EconomyController economyControllerPersonTrading;
    private final GameStateService gameStateService;
    private final SseService sseService;

    Logger logger = LoggerFactory.getLogger(Engine.class);
    private Thread engineThread = null;
    private int cycleNumber = 0;

    public Engine(GameCycleService gameCycleService, EconomyController economyControllerPersonTrading,
                  GameStateService gameStateService, SseService sseService) {
        this.gameCycleService = gameCycleService;
        this.economyControllerPersonTrading = economyControllerPersonTrading;
        this.gameStateService = gameStateService;
        this.sseService = sseService;
    }

    public void start() {
        gameStateService.createNewRunningGameState();
        logger.info("Initializing economy");
        economyControllerPersonTrading.initializeSystem();
        economyControllerPersonTrading.displayEconomyStats();

        logger.info("Starting game engine");
        // Start the game loop in a separate thread
        engineThread = new Thread(this::runEngine);
        engineThread.start();

    }

    public void stop() {
        gameStateService.pause();
        if (engineThread != null) {
            try {
                engineThread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
        }
    }

    private void runEngine() {
        logger.info("Running engine");
        while (true) {
            cycleNumber++;
            logger.info(String.format("\n\n cycle number %d\n\n", cycleNumber));

            // Asset Generation
            sleepIfGameIsPaused();
            logger.info(String.format("\n\nExecuting asset generation in cycle number %d\n\n", cycleNumber));
            gameCycleService.executeAssetGeneration();
            syncAssets();

            sleepSeconds(5);

            // Market Trading
            sleepIfGameIsPaused();
            logger.info(String.format("\n\nExecuting market trading in cycle number %d\n\n", cycleNumber));
            gameCycleService.executeMarketTrading();
            syncAssets();

            sleepSeconds(5);

            // Asset Consumption
            sleepIfGameIsPaused();
            logger.info(String.format("\n\nExecuting asset consumption in cycle number %d\n\n", cycleNumber));
            gameCycleService.executeAssetConsumption();
            syncAssets();

            // Delay the loop for a certain period of time before the next cycle
            sleepSeconds(5);
        }
    }

    private void sleepIfGameIsPaused() {
        while (gameStateService.isGamePaused()) {
            logger.info("Game is paused");
            sleepSeconds(1);
        }
    }

    private void sleepSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }

    private void syncAssets() {
        sseService.syncAssetsOfPersonInModal();
    }
}
