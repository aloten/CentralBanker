package com.aidanloten.centralbanker.controllers;

import com.aidanloten.centralbanker.service.GameStateService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/engine")
public class EngineController {
    GameStateService gameStateService;

    public EngineController(GameStateService gameStateService) {
        this.gameStateService = gameStateService;
    }

    @PostMapping
    public void pauseResume() {
        gameStateService.pauseResume();
    }
}
