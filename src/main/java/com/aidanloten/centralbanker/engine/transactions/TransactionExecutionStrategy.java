package com.aidanloten.centralbanker.engine.transactions;

import com.aidanloten.centralbanker.data.dao.balancesheet.BalanceSheetRepository;
import com.aidanloten.centralbanker.data.entities.agents.Agent;
import com.aidanloten.centralbanker.data.entities.agents.Person;
import com.aidanloten.centralbanker.data.entities.descriptors.economy.finance.BalanceSheet;
import com.aidanloten.centralbanker.data.entities.descriptors.economy.finance.assets.Asset;
import com.aidanloten.centralbanker.data.entities.descriptors.economy.finance.assets.AssetType;
import com.aidanloten.centralbanker.service.AssetService;
import com.aidanloten.centralbanker.service.FinancialStateService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class TransactionExecutionStrategy {
    FinancialStateService financialStateService;
    BalanceSheetRepository balanceSheetRepository;
    AssetService assetService;

    public TransactionExecutionStrategy(FinancialStateService financialStateService,
                                        BalanceSheetRepository balanceSheetRepository, AssetService assetService) {
        this.financialStateService = financialStateService;
        this.balanceSheetRepository = balanceSheetRepository;
        this.assetService = assetService;
    }

    private void removeAssetFromTrader(Asset asset, int quantity) {
        financialStateService.decreaseQuantityOfAsset(asset, quantity);
    }

    private void addAssetToTrader(Person trader, AssetType assetType, int quantity) {
        Asset assetOfTraderWhichAlreadyExists = assetService.findAssetByAssetType(trader, assetType);
        if (assetOfTraderWhichAlreadyExists != null) {
            financialStateService.increaseQuantityOfAsset(assetOfTraderWhichAlreadyExists, quantity);
        } else {
            Asset asset = Asset.builder().assetType(assetType).quantity(quantity).build();
            asset = assetService.saveAsset(asset);
            financialStateService.assignAssetToAgent(asset, trader);
        }
    }

    @Transactional
    public void executeTradeTransaction(TradeTransaction tradeTransaction) {
        Person buyer = tradeTransaction.getBuyer();
        Person seller = tradeTransaction.getSeller();
        removeAssetFromTrader(tradeTransaction.getBuyerAsset(), tradeTransaction.getQuantityOfBuyerAssetToTrade());
        removeAssetFromTrader(tradeTransaction.getSellerAsset(), tradeTransaction.getQuantityOfSellerAssetToTrade());
        addAssetToTrader(buyer, tradeTransaction.getSellerAsset().getAssetType(),
                tradeTransaction.getQuantityOfSellerAssetToTrade());
        addAssetToTrader(seller, tradeTransaction.getBuyerAsset().getAssetType(),
                tradeTransaction.getQuantityOfBuyerAssetToTrade());
        assetService.saveAsset(tradeTransaction.getBuyerAsset());
        assetService.saveAsset(tradeTransaction.getSellerAsset());
    }

    public void executeTransaction(Transaction transaction) {
        Agent seller = transaction.getSeller();
        Agent buyer = transaction.getBuyer();
        if (isValidTransaction(transaction, buyer)) {
            transferCashAndAsset(transaction.getAsset(), buyer, seller);
        }
    }

    private void transferCashAndAsset(Asset asset, Agent buyer, Agent seller) {
        BalanceSheet sellerBalanceSheet = seller.getFinancialState().getBalanceSheet();
        financialStateService.increaseCash(seller, asset.getValue());
        financialStateService.removeAssetFromBalanceSheet(asset);
        //        ?? not sure if i need to save here, unit test works without, but I can see saving creates a query
        //        which is
        //        more secure
        balanceSheetRepository.save(sellerBalanceSheet);

        BalanceSheet buyerBalanceSheet = buyer.getFinancialState().getBalanceSheet();
        financialStateService.decreaseCash(buyer, asset.getValue());
        financialStateService.assignBalanceSheetToAsset(asset, buyerBalanceSheet);
        //        ?? not sure if i need to save here, unit test works without
        balanceSheetRepository.save(buyerBalanceSheet);
    }

    private boolean isValidTransaction(Transaction transaction, Agent buyer) {
        return financialStateService.getCashValue(buyer) > transaction.getAsset().getValue();
    }
}
