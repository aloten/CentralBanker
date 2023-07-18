package com.aidanloten.centralbanker.controllers;

import com.aidanloten.centralbanker.data.entities.descriptors.economy.Economy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

public interface EconomyControllerOld {
    void startEngine();

    @GetMapping
    ResponseEntity<Economy> getEconomy();

    @GetMapping
    double getGdp();

    void initializeSystem();

    void displayEconomyStats();
}
