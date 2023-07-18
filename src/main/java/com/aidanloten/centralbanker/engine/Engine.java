package com.aidanloten.centralbanker.engine;

import com.aidanloten.centralbanker.data.entities.descriptors.economy.finance.assets.Asset;
import com.aidanloten.centralbanker.service.AssetService;
import com.aidanloten.centralbanker.service.EconomyService;
import com.aidanloten.centralbanker.service.GameStateService;
import com.aidanloten.centralbanker.service.WebSocketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Engine {
    private final GameCycleService gameCycleService;
    private final EconomyService economyService;
    private final GameStateService gameStateService;
    private final AssetService assetService;
    private final WebSocketService webSocketService;

    Logger logger = LoggerFactory.getLogger(Engine.class);
    private Thread engineThread = null;
    private int cycleNumber = 0;

    public Engine(GameCycleService gameCycleService, EconomyService economyService, GameStateService gameStateService,
                  AssetService assetService, WebSocketService webSocketService) {
        this.gameCycleService = gameCycleService;
        this.economyService = economyService;
        this.gameStateService = gameStateService;
        this.assetService = assetService;
        this.webSocketService = webSocketService;
    }


    public void start() {
        gameStateService.createNewRunningGameState();
        logger.info("Initializing economy");
        // Currently "perfect economy initializer class" is set in economy service class
        economyService.initializeSystem();
        economyService.displayEconomyStats();

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
            syncDataToClient();

            //            sleepSeconds(2);

            // Market Trading
            sleepIfGameIsPaused();
            logger.info(String.format("\n\nExecuting market trading in cycle number %d\n\n", cycleNumber));
            gameCycleService.executeMarketTrading();
            syncDataToClient();

            //            sleepSeconds(2);

            // Asset Consumption
            sleepIfGameIsPaused();
            logger.info(String.format("\n\nExecuting asset consumption in cycle number %d\n\n", cycleNumber));
            gameCycleService.executeAssetConsumption();
            syncDataToClient();

            // Delay the loop for a certain period of time before the next cycle
            //            sleepSeconds(2);
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

    /**
     * Uses Websocket
     */
    private void syncDataToClient() {
        if (gameStateService.getShouldStreamPersonAssets()) {
            List<Asset> personAssets = getPersonInModalAssets();
            webSocketService.sendMessageToTopic(WebSocketService.Topic.personAssets, personAssets);
        }
    }

    private List<Asset> getPersonInModalAssets() {
        int balanceSheetId = gameStateService.getGameState().getBalanceSheetIdOfPersonInModal();
        return assetService.findAssetsByBalanceSheetId(balanceSheetId);
    }

}
