package com.aidanloten.centralbanker.engine;

import com.aidanloten.centralbanker.data.entities.agents.Person;
import com.aidanloten.centralbanker.engine.assets.AssetGeneratorStrategy;
import com.aidanloten.centralbanker.engine.consumption.ConsumptionService;
import com.aidanloten.centralbanker.engine.markets.Market;
import com.aidanloten.centralbanker.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    AssetGeneratorStrategy p2PTradingAssetGeneratorStrategy;
    @Autowired
    Market market;
    @Autowired
    PersonService personService;
    @Autowired
    ConsumptionService consumptionService;

    public void executeAssetGeneration() {
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
