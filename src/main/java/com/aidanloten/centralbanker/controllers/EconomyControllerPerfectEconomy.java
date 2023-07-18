package com.aidanloten.centralbanker.controllers;


import com.aidanloten.centralbanker.data.entities.descriptors.economy.Economy;
import com.aidanloten.centralbanker.service.EconomyService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class EconomyControllerPerfectEconomy {
    private final EconomyService economyService;

    public EconomyControllerPerfectEconomy(EconomyService economyService) {
        this.economyService = economyService;
    }

    @MessageMapping("/economy")
    @SendTo("/topic/economy")
    public Economy getEconomy() {
        return economyService.getEconomy();
    }
}
