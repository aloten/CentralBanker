package com.aidanloten.centralbanker.service;

import com.aidanloten.centralbanker.data.dao.balancesheet.BalanceSheetRepository;
import com.aidanloten.centralbanker.data.dao.financialstate.FinancialStateRepository;
import com.aidanloten.centralbanker.data.entities.agents.Agent;
import com.aidanloten.centralbanker.data.entities.descriptors.economy.finance.BalanceSheet;
import com.aidanloten.centralbanker.data.entities.descriptors.economy.finance.FinancialState;
import com.aidanloten.centralbanker.data.entities.descriptors.economy.finance.assets.Asset;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FinancialStateService {
    BalanceSheetRepository balanceSheetRepository;
    FinancialStateRepository financialStateRepository;
    AssetService assetService;

    public FinancialStateService(BalanceSheetRepository balanceSheetRepository,
                                 FinancialStateRepository financialStateRepository, AssetService assetService) {
        this.balanceSheetRepository = balanceSheetRepository;
        this.financialStateRepository = financialStateRepository;
        this.assetService = assetService;
    }

    public void decreaseQuantityOfAsset(Asset asset, int quantity) {
        if (asset.getQuantity() - quantity < 0) {
            throw new RuntimeException("Cannot have negative asset quantity");
        }
        asset.setQuantity(asset.getQuantity() - quantity);
        if (asset.getQuantity() == 0) {
            assetService.deleteAsset(asset);
        }
    }

    public void increaseQuantityOfAsset(Asset asset, int quantity) {
        asset.setQuantity(asset.getQuantity() + quantity);
    }

    public void assignAssetToAgent(Asset asset, Agent agent) {
        assignBalanceSheetToAsset(asset, agent.getFinancialState().getBalanceSheet());
    }

    public FinancialState saveFinancialState(FinancialState financialState) {
        return financialStateRepository.save(financialState);
    }

    public Asset getAssetByAgentAndName(Agent agent, String assetName) {
        for (Asset asset : assetService.findAssetsByBalanceSheet(agent.getFinancialState().getBalanceSheet())) {
            if (asset.getName().equals(assetName)) {
                return asset;
            }
        }
        return null;
    }

    public void addAssetsToBalanceSheet(List<Asset> assets, BalanceSheet balanceSheet) {
        for (Asset asset : assets) {
            assignBalanceSheetToAsset(asset, balanceSheet);
        }
    }

    public void assignBalanceSheetToAsset(Asset asset, BalanceSheet balanceSheet) {
        asset.setBalanceSheet(balanceSheet);
    }

    public void removeAssetFromBalanceSheet(Asset asset) {
        asset.setBalanceSheet(null);
    }

    public void increaseCash(Agent agent, double value) {
        Asset cashAsset = getCashAsset(agent);
        cashAsset.setValue(cashAsset.getValue() + value);
    }

    public void decreaseCash(Agent agent, double value) {
        Asset cashAsset = getCashAsset(agent);
        cashAsset.setValue(cashAsset.getValue() - value);
    }

    public Asset getCashAsset(Agent agent) {
        return assetService.findAssetByAssetType(agent, assetService.getCashAssetType());
    }

    public double getCashValue(Agent agent) {
        return getCashAsset(agent).getValue();
    }

    public void addAssetsToAgentBalanceSheet(List<Asset> assets, Agent agent) {
        for (Asset asset : assets) {
            assignAssetToAgent(asset, agent);
        }
    }
}
