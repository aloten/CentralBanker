package com.aidanloten.centralbanker.engine.transactions;

import com.aidanloten.centralbanker.data.entities.agents.Agent;
import com.aidanloten.centralbanker.data.entities.descriptors.economy.finance.assets.Asset;

public class Transaction {
    private final Agent buyer;
    private final Agent seller;
    private final Asset asset;

    public Transaction(Agent buyer, Agent seller, Asset asset) {
        this.buyer = buyer;
        this.seller = seller;
        this.asset = asset;
    }

    public Agent getBuyer() {
        return buyer;
    }

    public Agent getSeller() {
        return seller;
    }

    public Asset getAsset() {
        return asset;
    }
}
