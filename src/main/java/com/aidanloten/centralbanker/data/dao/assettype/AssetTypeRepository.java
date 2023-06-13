package com.aidanloten.centralbanker.data.dao.assettype;

import com.aidanloten.centralbanker.data.entities.descriptors.economy.finance.assets.AssetType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssetTypeRepository extends JpaRepository<AssetType, Integer> {
    AssetType findByName(String name);
}
