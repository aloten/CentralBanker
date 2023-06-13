package com.aidanloten.centralbanker.engine.transactions;

import com.aidanloten.centralbanker.data.entities.agents.Person;
import com.aidanloten.centralbanker.data.entities.descriptors.economy.finance.assets.Asset;

public class TradeTransaction {
    private final Person buyer;
    /**
     * Asset type of asset buyer is trading away
     */
    private final Asset buyerAsset;
    private final int quantityOfBuyerAssetToTrade;
    private final Person seller;
    /**
     * Asset type of asset seller is trading away
     */
    private final Asset sellerAsset;
    private final int quantityOfSellerAssetToTrade;

    public TradeTransaction(Person buyer, Asset buyerAsset, int quantityOfBuyerAssetToTrade, Person seller,
                            Asset sellerAsset, int quantityOfSellerAssetToTrade) {
        this.buyer = buyer;
        this.buyerAsset = buyerAsset;
        this.quantityOfBuyerAssetToTrade = quantityOfBuyerAssetToTrade;
        this.seller = seller;
        this.sellerAsset = sellerAsset;
        this.quantityOfSellerAssetToTrade = quantityOfSellerAssetToTrade;
    }

    public Person getBuyer() {
        return buyer;
    }

    public Asset getBuyerAsset() {
        return buyerAsset;
    }

    public int getQuantityOfBuyerAssetToTrade() {
        return quantityOfBuyerAssetToTrade;
    }

    public Person getSeller() {
        return seller;
    }

    public Asset getSellerAsset() {
        return sellerAsset;
    }

    public int getQuantityOfSellerAssetToTrade() {
        return quantityOfSellerAssetToTrade;
    }
}
