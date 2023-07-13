package com.aidanloten.centralbanker.controllers;

import com.aidanloten.centralbanker.data.entities.descriptors.economy.finance.assets.Asset;
import com.aidanloten.centralbanker.service.AssetService;
import com.aidanloten.centralbanker.service.GameStateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class PersonWebSocketController {
    private final AssetService assetService;
    private final GameStateService gameStateService;
    Logger logger = LoggerFactory.getLogger(PersonWebSocketController.class);

    public PersonWebSocketController(AssetService assetService, GameStateService gameStateService) {
        this.assetService = assetService;
        this.gameStateService = gameStateService;
    }

    @MessageMapping("/personAssets/stop")
    public void stopStreamingPersonAssets() {
        logger.info("Incoming websocket request: Stop streaming person assets.");
        gameStateService.setShouldStreamPersonAssets(false);
    }

    @MessageMapping("/personAssets/{balanceSheetId}")
    @SendTo("/topic/personAssets")
    public List<Asset> startStreamingPersonAssets(@DestinationVariable int balanceSheetId) {
        gameStateService.setShouldStreamPersonAssets(true);
        return assetService.findAssetsByBalanceSheetId(balanceSheetId);
    }
}
