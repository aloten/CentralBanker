package com.aidanloten.centralbanker.engine.consumption;

import com.aidanloten.centralbanker.data.entities.descriptors.economy.finance.assets.AssetType;

public class ConsumptionRequirement {
    private final AssetType assetType;
    private final int quantity;

    public ConsumptionRequirement(AssetType assetType, int quantity) {
        this.assetType = assetType;
        this.quantity = quantity;
    }

    public AssetType getAssetType() {
        return assetType;
    }

    public int getQuantity() {
        return quantity;
    }
}
