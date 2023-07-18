package com.aidanloten.centralbanker.engine.consumption;

import com.aidanloten.centralbanker.data.entities.agents.Person;
import com.aidanloten.centralbanker.data.entities.descriptors.economy.finance.assets.Asset;
import com.aidanloten.centralbanker.data.util.ConsumptionConstants;
import com.aidanloten.centralbanker.service.AssetService;
import com.aidanloten.centralbanker.service.FinancialStateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

@Service
public class ConsumptionService {
    private final AssetService assetService;
    private final FinancialStateService financialStateService;
    Logger logger = LoggerFactory.getLogger(ConsumptionService.class);

    public ConsumptionService(AssetService assetService, FinancialStateService financialStateService) {
        this.assetService = assetService;
        this.financialStateService = financialStateService;
    }

    private void consumeAsset(Asset asset, int quantityToConsume) {
        financialStateService.decreaseQuantityOfAsset(asset, quantityToConsume);
    }

    public void consumePersonAssets(Person person) {
        List<Asset> assets = assetService.findAssetsOfPerson(person);
        // Might want to not dupe this (trading and consumption cycles) in the future by making persistent or another
        // solution
        Deque<ConsumptionRequirement> consumptionRequirements = calculateConsumptionRequirements();
        while (consumptionRequirements.size() > 0) {
            ConsumptionRequirement consumptionRequirement = consumptionRequirements.pop();
            Asset asset = assetService.findAssetByAssetType(assets, consumptionRequirement.getAssetType());
            if (asset == null) {
                logger.warn("No asset to consume, despite being a consumption requirement.");
                return;
            }
            consumeAsset(asset, consumptionRequirement.getQuantity());
        }
    }

    public ConsumptionRequirements createConsumptionRequirements(Person person) {
        return new ConsumptionRequirements(person, calculateConsumptionRequirements());
    }

    private Deque<ConsumptionRequirement> calculateConsumptionRequirements() {
        Deque<ConsumptionRequirement> consumptionRequirements = new ArrayDeque<>();
        // Will need to add person modifiers below when necessary
        consumptionRequirements.push(new ConsumptionRequirement(assetService.getFoodAssetType(),
                ConsumptionConstants.BASE_FOOD_CONSUMPTION));
        consumptionRequirements.push(new ConsumptionRequirement(assetService.getToolAssetType(),
                ConsumptionConstants.BASE_TOOL_CONSUMPTION));
        consumptionRequirements.push(new ConsumptionRequirement(assetService.getLumberAssetType(),
                ConsumptionConstants.BASE_LUMBER_CONSUMPTION));
        return consumptionRequirements;
    }
}
