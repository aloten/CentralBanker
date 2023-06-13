package com.aidanloten.centralbanker.controllers;

import com.aidanloten.centralbanker.data.entities.agents.Person;
import com.aidanloten.centralbanker.data.entities.descriptors.economy.Economy;
import com.aidanloten.centralbanker.data.util.InitialDataUtilityPersonTrading;
import com.aidanloten.centralbanker.service.EconomyService;
import com.aidanloten.centralbanker.service.PersonService;
import com.aidanloten.centralbanker.utils.CBUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/economy")
public class EconomyControllerPersonTrading implements EconomyController {
    @Autowired
    InitialDataUtilityPersonTrading initialDataUtilityPersonTrading;
    @Autowired
    EconomyService economyService;
    @Autowired
    PersonService personService;

    @Override
    public void startEngine() {

    }

    @Override
    @GetMapping
    public ResponseEntity<Economy> getEconomy() {
        return new ResponseEntity<Economy>(economyService.getEconomy(), HttpStatus.valueOf(200));
    }

    @Override
    @GetMapping("/gdp")
    public double getGdp() {
        return economyService.getGDP();
    }

    @Override
    public void initializeSystem() {
        economyService.saveEconomy(Economy.builder().moneySupply(100).build());
        initialDataUtilityPersonTrading.initializeSystem();
    }

    @Override
    public void displayEconomyStats() {
        List<Person> people = personService.findAllPeople();
        double gdp = economyService.getGDP();
        System.out.println("\n------- Economy Stats -------");
        System.out.println("No. of people=" + people.size());
        System.out.println("gdp=" + CBUtility.formatCurrency(gdp));
        System.out.println("------------------------------\n");
    }

}
