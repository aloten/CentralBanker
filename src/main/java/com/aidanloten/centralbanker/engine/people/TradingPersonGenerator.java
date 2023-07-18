package com.aidanloten.centralbanker.engine.people;

import com.aidanloten.centralbanker.data.util.EconomicConstants;
import com.aidanloten.centralbanker.service.PersonService;
import org.springframework.stereotype.Component;

@Component
public class TradingPersonGenerator implements PersonGeneratorStrategy {
    private final PersonService personService;

    public TradingPersonGenerator(PersonService personService) {
        this.personService = personService;
    }

    @Override
    public void generatePeople() {
        int populationSize = personService.getNumberOfPeople();
        int numOfPeopleToAdd = (int) (populationSize * EconomicConstants.BIRTH_RATE);
        // TODO after I add 100 people initially
    }
}
