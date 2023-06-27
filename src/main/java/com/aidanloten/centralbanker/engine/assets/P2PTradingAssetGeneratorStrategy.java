package com.aidanloten.centralbanker.engine.assets;

import com.aidanloten.centralbanker.data.entities.agents.Person;
import com.aidanloten.centralbanker.data.entities.descriptors.economy.finance.assets.Asset;
import com.aidanloten.centralbanker.data.entities.descriptors.economy.finance.assets.AssetType;
import com.aidanloten.centralbanker.data.util.ProductionConstants;
import com.aidanloten.centralbanker.service.AssetService;
import com.aidanloten.centralbanker.service.FinancialStateService;
import com.aidanloten.centralbanker.service.PersonService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class P2PTradingAssetGeneratorStrategy implements AssetGeneratorStrategy {
    @Autowired
    PersonService personService;
    @Autowired
    AssetService assetService;
    @Autowired
    FinancialStateService financialStateService;
    Logger logger = LoggerFactory.getLogger(P2PTradingAssetGeneratorStrategy.class);

    /**
     * Add person modifiers later
     */
    @Override
    @Transactional
    public void generateAssetsForCycle() {
        try {
            for (Person person : personService.findAllPeople()) {
                AssetType assetType = person.getAssetTypeProduces();
                int quantity = 0;
                if (assetType == assetService.getLumberAssetType()) {
                    quantity = ProductionConstants.BASE_LUMBER_PRODUCTION;
                } else if (assetType == assetService.getToolAssetType()) {
                    quantity = ProductionConstants.BASE_TOOL_PRODUCTION;
                } else if (assetType == assetService.getFoodAssetType()) {
                    quantity = ProductionConstants.BASE_FOOD_PRODUCTION;
                }
                generateAssetForPerson(person, assetType, quantity);
            }
        } catch (Exception e) {
            logger.error("Error generating assets in cycle");
            e.printStackTrace();
        }
    }

    private void generateAssetForPerson(Person person, AssetType assetType, int baseQuantity) {
        Asset asset = assetService.findAssetByAssetType(person, assetType);
        double productionModifierValue = person.getProductionModifier().getEffectPercentage();
        int quantity = (int) (baseQuantity * productionModifierValue / 100);
        if (asset == null) {
            asset = Asset.builder().assetType(assetType).quantity(quantity).build();
            financialStateService.assignAssetToAgent(asset, person);
            assetService.saveAsset(asset);
        } else {
            financialStateService.increaseQuantityOfAsset(asset, quantity);
        }
    }
}
