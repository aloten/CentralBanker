package com.aidanloten.centralbanker.controllers;

import com.aidanloten.centralbanker.service.AssetService;
import com.aidanloten.centralbanker.service.EventName;
import com.aidanloten.centralbanker.service.SseEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

import static java.lang.Integer.parseInt;

@RestController
@RequestMapping("api/v1/sse")
public class SseController {

    private final AssetService assetService;
    Logger logger = LoggerFactory.getLogger(SseController.class);
    private SseEmitter sseEmitter;
    private SseEmitter assetsEmitter;
    private int balanceSheetId;

    public SseController(AssetService assetService) {
        this.assetService = assetService;
    }

    @CrossOrigin
    @GetMapping(path = "/assets", consumes = MediaType.ALL_VALUE)
    SseEmitter createConnection(@RequestParam("balanceSheetId") String balanceSheetId) {
        this.balanceSheetId = parseInt(balanceSheetId);
        assetsEmitter = new SseEmitter(Long.MAX_VALUE);
        logger.info("Sse connection created for balanceSheetId: " + balanceSheetId);

        assetsEmitter.onCompletion(() -> {
            logger.info("SSE connection completed for balanceSheetId: " + balanceSheetId);
            this.balanceSheetId = 0;
            assetsEmitter = null;
        });

        assetsEmitter.onTimeout(() -> {
            logger.warn("SSE connection timeout for balanceSheetId: " + balanceSheetId);
            this.balanceSheetId = 0;
            assetsEmitter = null;
        });

        try {
            assetsEmitter.send(SseEmitter.event().name("INIT assets sse connection"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return assetsEmitter;
    }

    private void handleAssetsSseEvent(SseEvent event) throws IOException {
        if (assetsEmitter != null) {
            assetsEmitter.send(SseEmitter.event().name(event.getEventName())
                    .data(assetService.findAssetsByBalanceSheetId(balanceSheetId)));
            logger.info("sse sent: assets");
        }
    }

    @EventListener
    public void handleSseEvent(SseEvent event) {
        try {
            if (event.getEventName().equals(EventName.ASSETS.name())) {
                handleAssetsSseEvent(event);
            }
        } catch (IOException e) {
            assetsEmitter.complete();
            logger.debug(e.getMessage());
        }
    }

    //    @CrossOrigin
    @GetMapping(consumes = MediaType.ALL_VALUE)
    SseEmitter createConnection() {
        sseEmitter = new SseEmitter();
        try {
            sseEmitter.send(SseEmitter.event().name("INIT general sse connection"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sseEmitter;
    }
}
