package com.aidanloten.centralbanker.engine.consumption;

import com.aidanloten.centralbanker.data.entities.agents.Person;
import com.aidanloten.centralbanker.data.util.ConsumptionConstants;

public class ConsumptionExecution {
    /**
     * for a given person, get consumption needs, adjust based on person's resources,
     * then execute transactions to satisfy consumption function. Right now we assume the person has enough resources
     * to trade/buy to fulfill all consumption needs.
     */
    public void executeConsumption(Person person) {
        int foodConsumption = calculateEffectiveFoodConsumption(person);
        int toolConsumption = calculateEffectiveToolConsumption(person);

    }

    private int calculateEffectiveToolConsumption(Person person) {
        // TODO add modifiers
        return ConsumptionConstants.BASE_TOOL_CONSUMPTION;
    }

    private int calculateEffectiveFoodConsumption(Person person) {
        // TODO add modifiers
        return ConsumptionConstants.BASE_FOOD_CONSUMPTION;
    }
}
