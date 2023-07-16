package com.aidanloten.centralbanker.controllers;

import com.aidanloten.centralbanker.data.dto.PersonRequest;
import com.aidanloten.centralbanker.data.entities.agents.Person;
import com.aidanloten.centralbanker.data.entities.descriptors.economy.finance.assets.Asset;
import com.aidanloten.centralbanker.service.AssetService;
import com.aidanloten.centralbanker.service.GameStateService;
import com.aidanloten.centralbanker.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.lang.Integer.parseInt;

@RestController
@RequestMapping("api/v1/person")
public class PersonController {
    private final PersonService personService;
    private final AssetService assetService;
    private final GameStateService gameStateService;
    Logger logger = LoggerFactory.getLogger(PersonController.class);

    public PersonController(PersonService personService, AssetService assetService, GameStateService gameStateService) {
        this.personService = personService;
        this.assetService = assetService;
        this.gameStateService = gameStateService;
    }

    @MessageMapping("/personAssets/stop")
    public void stopStreamingPersonAssets() {
        gameStateService.setShouldStreamPersonAssets(false);
    }

    @MessageMapping("/personAssets/{balanceSheetId}")
    @SendTo("/topic/personAssets")
    public List<Asset> startStreamingPersonAssets(@DestinationVariable int balanceSheetId) {
        gameStateService.setShouldStreamPersonAssets(true);
        gameStateService.setBalanceSheetIdOfPersonInModal(balanceSheetId);
        return assetService.findAssetsByBalanceSheetId(balanceSheetId);
    }

    @GetMapping("/assets")
    @ResponseBody
    public List<Asset> getAssetsOfBalanceSheet(@RequestParam("balanceSheetId") String balanceSheetId) {
        logger.info(balanceSheetId);
        return assetService.findAssetsByBalanceSheetId(parseInt(balanceSheetId));
    }

    @PostMapping
    public ResponseEntity<String> createPerson(@RequestBody PersonRequest personRequest) {
        Person person = Person.builder().firstName(personRequest.getFirstName()).lastName(personRequest.getLastName())
                .salary(personRequest.getSalary()).build();
        personService.savePerson(person);

        return ResponseEntity.status(201).body("Person created successfully");
    }

    @GetMapping
    public ResponseEntity<List<Person>> getAllPeople() {
        return new ResponseEntity<List<Person>>(personService.findAllPeople(), HttpStatus.valueOf(200));
    }
}
