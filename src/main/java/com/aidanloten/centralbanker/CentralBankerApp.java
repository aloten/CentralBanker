package com.aidanloten.centralbanker;

import com.aidanloten.centralbanker.controllers.EconomyController;
import com.aidanloten.centralbanker.engine.GameCycleService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.aidanloten.centralbanker")
public class CentralBankerApp {
    @Autowired
    GameCycleService gameCycleService;
    @Autowired
    EconomyController economyControllerPersonTrading;
    Logger logger = LoggerFactory.getLogger(CentralBankerApp.class);
    private boolean isGameRunning = false;
    private Thread engineThread = null;
    private int cycleNumber = 0;

    public static void main(String[] args) {
        SpringApplication.run(CentralBankerApp.class, args);
    }

    @PostConstruct
    private void startGameEngine() {
        logger.info("Initializing economy");
        economyControllerPersonTrading.initializeSystem();
        economyControllerPersonTrading.displayEconomyStats();

        logger.info("Starting game engine");
        isGameRunning = true;
        // Start the game loop in a separate thread
        engineThread = new Thread(this::runEngine);
        engineThread.start();
    }

    @PreDestroy
    public void stopEngine() {
        isGameRunning = false;
        if (engineThread != null) {
            try {
                engineThread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
        }
    }

    public void resumeEngine() {
        if (!isGameRunning) {
            runEngine();
        }
    }

    public void pauseEngine() {
        isGameRunning = false;
    }

    private void runEngine() {
        logger.info("Running engine");
        while (isGameRunning) {
            cycleNumber++;
            logger.info(String.format("\n\n cycle number %d\n\n", cycleNumber));
            logger.info(String.format("\n\nExecuting asset generation in cycle number %d\n\n", cycleNumber));
            gameCycleService.executeAssetGeneration();
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
            logger.info(String.format("\n\nExecuting market trading in cycle number %d\n\n", cycleNumber));
            gameCycleService.executeMarketTrading();
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
            logger.info(String.format("\n\nExecuting asset consumption in cycle number %d\n\n", cycleNumber));
            gameCycleService.executeAssetConsumption();
            // Delay the loop for a certain period of time before the next cycle
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
        }
    }
}
