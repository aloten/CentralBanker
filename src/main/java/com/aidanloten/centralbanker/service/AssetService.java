package com.aidanloten.centralbanker.service;

import com.aidanloten.centralbanker.data.dao.asset.AssetRepository;
import com.aidanloten.centralbanker.data.dao.assettype.AssetTypeRepository;
import com.aidanloten.centralbanker.data.entities.agents.Agent;
import com.aidanloten.centralbanker.data.entities.agents.Person;
import com.aidanloten.centralbanker.data.entities.descriptors.economy.finance.BalanceSheet;
import com.aidanloten.centralbanker.data.entities.descriptors.economy.finance.assets.Asset;
import com.aidanloten.centralbanker.data.entities.descriptors.economy.finance.assets.AssetType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssetService {
    private final AssetRepository assetRepository;
    private final AssetTypeRepository assetTypeRepository;

    public AssetService(AssetRepository assetRepository, AssetTypeRepository assetTypeRepository) {
        this.assetRepository = assetRepository;
        this.assetTypeRepository = assetTypeRepository;
    }

    public Asset getAssetPersonProduces(Person person) {
        for (Asset asset : findAssetsOfPerson(person)) {
            if (isEqual(asset.getAssetType(), person.getAssetTypeProduces())) {
                return asset;
            }
        }
        return null;
    }

    public int getQuantityOfAssetPersonProduces(Person buyer) {
        Asset assetPersonProduces = getAssetPersonProduces(buyer);
        int quantity = 0;
        if (assetPersonProduces != null) {
            quantity = assetPersonProduces.getQuantity();
        }
        return quantity;
    }

    public void deleteAsset(Asset asset) {
        assetRepository.delete(asset);
    }

    public Asset findAssetByAssetType(List<Asset> assets, AssetType assetType) {
        for (Asset asset : assets) {
            if (isEqual(asset.getAssetType(), assetType)) {
                return asset;
            }
        }
        return null;
    }

    public boolean isEqual(AssetType assetType1, AssetType assetType2) {
        return assetType1.getName().equals(assetType2.getName());
    }

    public List<Asset> findAssetsByBalanceSheetId(int balanceSheetId) {
        return assetRepository.findByBalanceSheetId(balanceSheetId);
    }

    public List<Asset> findAssetsByBalanceSheet(BalanceSheet balanceSheet) {
        return assetRepository.findByBalanceSheet(balanceSheet);
    }

    public void saveAssetType(AssetType assetType) {
        assetTypeRepository.save(assetType);
    }

    public Asset findAssetByAssetType(Agent agent, AssetType assetType) {
        for (Asset asset : findAssetsByBalanceSheet(agent.getFinancialState().getBalanceSheet())) {
            if (isEqual(asset.getAssetType(), assetType)) {
                return asset;
            }
        }
        return null;
    }

    public void saveAssets(List<Asset> assets) {
        assetRepository.saveAll(assets);
    }

    public AssetType getFoodAssetType() {
        return assetTypeRepository.findByName("FOOD");
    }

    public AssetType getRawMaterialAssetType() {
        return assetTypeRepository.findByName("RAW_MATERIAL");
    }

    public AssetType getCashAssetType() {
        return assetTypeRepository.findByName("CASH");
    }

    public AssetType getLongTermConsumableAssetType() {
        return assetTypeRepository.findByName("LONG_TERM_CONSUMABLE");
    }

    public Asset saveAsset(Asset asset) {
        return assetRepository.save(asset);
    }

    public AssetType getLumberAssetType() {
        return assetTypeRepository.findByName("LUMBER");
    }

    public AssetType getToolAssetType() {
        return assetTypeRepository.findByName("TOOL");
    }

    public List<Asset> findAssetsOfPerson(Person seller) {
        return findAssetsByBalanceSheet(seller.getFinancialState().getBalanceSheet());
    }
}
