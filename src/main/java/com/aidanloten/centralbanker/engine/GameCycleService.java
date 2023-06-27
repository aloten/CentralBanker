package com.aidanloten.centralbanker.engine;

import com.aidanloten.centralbanker.data.entities.agents.Person;
import com.aidanloten.centralbanker.data.entities.descriptors.economy.Modifier;
import com.aidanloten.centralbanker.engine.assets.AssetGeneratorStrategy;
import com.aidanloten.centralbanker.engine.consumption.ConsumptionService;
import com.aidanloten.centralbanker.engine.markets.Market;
import com.aidanloten.centralbanker.service.ModifierService;
import com.aidanloten.centralbanker.service.PersonService;
import org.springframework.stereotype.Service;

/**
 * Describes and controls processing of game:
 * 1. Asset production
 * 2. Market activity
 * 3. Asset consumption / destruction
 * <p>
 * General updating of statistics for the UI
 * Will include ability to speed up and slow down processing relative to realtime based on user input
 */
@Service
public class GameCycleService {
    private final AssetGeneratorStrategy p2PTradingAssetGeneratorStrategy;
    private final Market market;
    private final PersonService personService;
    private final ConsumptionService consumptionService;
    private final ModifierService modifierService;

    public GameCycleService(AssetGeneratorStrategy p2PTradingAssetGeneratorStrategy, Market market,
                            PersonService personService, ConsumptionService consumptionService,
                            ModifierService modifierService) {
        this.p2PTradingAssetGeneratorStrategy = p2PTradingAssetGeneratorStrategy;
        this.market = market;
        this.personService = personService;
        this.consumptionService = consumptionService;
        this.modifierService = modifierService;
    }

    /**
     * potentially increase production modifiers of people based on personality and luck
     */
    private void potentiallyIncreaseProductionModifiers() {
        for (Person person : personService.findAllPeople()) {
            double productionModifierIncreaseValue = modifierService.calculateProductionModifierValue(
                    person.getPersonality());
            Modifier productionModifier = person.getProductionModifier();
            productionModifier.setEffectPercentage(
                    productionModifier.getEffectPercentage() + productionModifierIncreaseValue);
            modifierService.saveModifier(productionModifier);
            personService.savePerson(person);
        }
    }

    /**
     * Potentially increase production modifiers and then create assets for people
     */
    public void executeAssetGeneration() {
        potentiallyIncreaseProductionModifiers();
        p2PTradingAssetGeneratorStrategy.generateAssetsForCycle();
    }

    public void executeMarketTrading() {
        market.initializeMarket();
        market.executeMarketCycle();
    }

    public void executeAssetConsumption() {
        for (Person person : personService.findAllPeople()) {
            consumptionService.consumePersonAssets(person);
        }
    }
}
