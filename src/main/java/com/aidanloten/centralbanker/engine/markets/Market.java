package com.aidanloten.centralbanker.engine.markets;

import com.aidanloten.centralbanker.data.entities.agents.Person;
import com.aidanloten.centralbanker.data.entities.descriptors.economy.finance.assets.Asset;
import com.aidanloten.centralbanker.data.entities.descriptors.economy.finance.assets.AssetType;
import com.aidanloten.centralbanker.engine.consumption.ConsumptionRequirement;
import com.aidanloten.centralbanker.engine.consumption.ConsumptionRequirements;
import com.aidanloten.centralbanker.engine.consumption.ConsumptionService;
import com.aidanloten.centralbanker.engine.transactions.TradeTransaction;
import com.aidanloten.centralbanker.engine.transactions.TransactionService;
import com.aidanloten.centralbanker.service.AssetService;
import com.aidanloten.centralbanker.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

@Component
public class Market {
    private final Queue<Person> participants;
    @Autowired
    TransactionService transactionService;
    @Autowired
    ConsumptionService consumptionService;
    @Autowired
    PersonService personService;
    @Autowired
    AssetService assetService;

    Logger logger = LoggerFactory.getLogger(Market.class);

    public Market() {
        participants = new LinkedList<>();
    }

    /**
     * @param buyer refers to the person whose needs we are focused on filling sequentially before processing another
     *              participant. Assumes each participant only trades away 1 type of AssetType (the one they
     *              produce)
     */
    private void performTradingForPerson(Person buyer) {
        // get list of assets and quantity buyer needs
        ConsumptionRequirements consumptionRequirements = consumptionService.createConsumptionRequirements(buyer);
        while (consumptionRequirements.getConsumptionRequirements().size() > 0) {
            ConsumptionRequirement consumptionRequirement = consumptionRequirements.getConsumptionRequirements().pop();
            Person seller = findSeller(consumptionRequirement.getAssetType());
            if (seller != null) {
                executeTrade(buyer, seller, consumptionRequirement);
            }
        }
    }

    /**
     * trade assets from "buyer" of asset type which buyer produces
     * for assets of "seller" of asset type which seller produces.
     * Quantity to transfer is as many seller assets as possible to fulfill buyer ConsumptionStrategy.
     * "Price" right now is 1:1 for every good based on constants
     */
    private void executeTrade(Person buyer, Person seller, ConsumptionRequirement consumptionRequirement) {
        Asset assetBuyerProduces = assetService.getAssetPersonProduces(buyer);
        int quantityBuyerAsset = assetService.getQuantityOfAssetPersonProduces(buyer);

        Asset assetSellerProduces = assetService.getAssetPersonProduces(seller);
        int quantitySellerAsset = assetService.getQuantityOfAssetPersonProduces(seller);

        Map<String, Integer> quantitiesToTrade = calculateTradeQuantities(quantitySellerAsset, quantityBuyerAsset,
                consumptionRequirement.getQuantity());
        int quantitySellerAssetToTrade = quantitiesToTrade.get("quantitySellerAssetToTrade");
        int quantityBuyerAssetToTrade = quantitiesToTrade.get("quantityBuyerAssetToTrade");

        if (quantityBuyerAssetToTrade == 0) {
            return;
        }

        TradeTransaction tradeTransaction = new TradeTransaction(buyer, assetBuyerProduces, quantityBuyerAssetToTrade,
                seller, assetSellerProduces, quantitySellerAssetToTrade);
        transactionService.executeTradeTransaction(tradeTransaction);
    }

    /**
     * Seller quantity should not be zero, because of earlier validation
     */
    private Map<String, Integer> calculateTradeQuantities(int quantitySellerAsset, int quantityBuyerAsset,
                                                          int consumptionRequirementQuantity) {
        Map<String, Integer> quantitiesToTrade = new HashMap<>();

        // fixed price, every good is 1:1 ratio for trading, maybe this will pull from a price map later
        double priceRatioOfBuyerAssetToSellerAsset = 1;
        // minus one accounts for requirement of buyer not to give away all their asset they produce
        int quantityBuyerCanAfford = (int) ((quantityBuyerAsset - 1) * priceRatioOfBuyerAssetToSellerAsset);
        // minus one accounts for requirement of seller not to give away all their asset they produce
        int quantitySellerCanSell = quantitySellerAsset - 1;
        int quantityBuyerWantsAndCanAfford = Math.min(quantityBuyerCanAfford, consumptionRequirementQuantity);
        // The minimum of quantity that seller markets AND quantity buyer wants and can afford
        int quantitySellerAssetToTrade = Math.min(quantitySellerCanSell, quantityBuyerWantsAndCanAfford);

        // only works with 1:1 pricing, otherwise if the value is a double then buyer trades less than they should,
        // given certain prices when quantity gets chopped to an int
        int quantityBuyerAssetToTrade = (int) (quantitySellerAssetToTrade * (1 / priceRatioOfBuyerAssetToSellerAsset));

        quantitiesToTrade.put("quantitySellerAssetToTrade", quantitySellerAssetToTrade);
        quantitiesToTrade.put("quantityBuyerAssetToTrade", quantityBuyerAssetToTrade);
        return quantitiesToTrade;
    }

    /**
     * Check if the asset that seller produces is greater than the amount they need for their consumption
     * Temporarily set threshold to > 1, because consumption requirements is transient and only accessible when person
     * is going through buying stage
     */
    private boolean sellerHasValidQuantity(Person seller, AssetType assetType) {
        for (Asset asset : assetService.findAssetsOfPerson(seller)) {
            if (assetService.isEqual(asset.getAssetType(), assetType) && asset.getQuantity() > 1) {
                return true;
            }
        }
        return false;
    }

    /**
     * Iterate through participants and check if they are selling asset type and if, so, then how much quantity.
     * Need to decide if they will trade some if they can't fulfill full quantity requirement
     */
    private Person findSeller(AssetType assetType) {
        for (Person participant : participants) {
            final boolean sellerProducesAssetTypeAndHasValidQuantity = assetService.isEqual(
                    participant.getAssetTypeProduces(), assetType) && sellerHasValidQuantity(participant, assetType);
            if (sellerProducesAssetTypeAndHasValidQuantity) {
                return participant;
            }
        }
        logger.warn("No seller exists or has valid quantity for AssetType: ", assetType);
        return null;
    }

    public void executeMarketCycle() {
        while (!participants.isEmpty()) {
            Person buyer = participants.poll();
            performTradingForPerson(buyer);
        }
    }

    public void initializeMarket() {
        for (Person person : personService.findAllPeople()) {
            participants.add(person);
        }
    }
}
