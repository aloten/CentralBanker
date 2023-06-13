package com.aidanloten.centralbanker.engine.consumption;

import com.aidanloten.centralbanker.data.entities.agents.Person;

import java.util.Deque;

public class ConsumptionRequirements {
    private final Person person;
    private Deque<ConsumptionRequirement> consumptionRequirements;

    public ConsumptionRequirements(Person person, Deque<ConsumptionRequirement> consumptionRequirements) {
        this.person = person;
        this.consumptionRequirements = consumptionRequirements;
    }

    public Deque<ConsumptionRequirement> getConsumptionRequirements() {
        return consumptionRequirements;
    }

    public Person getPerson() {
        return person;
    }


}
