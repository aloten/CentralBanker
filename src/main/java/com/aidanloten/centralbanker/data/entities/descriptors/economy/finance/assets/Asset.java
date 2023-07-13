package com.aidanloten.centralbanker.data.entities.descriptors.economy.finance.assets;

import com.aidanloten.centralbanker.data.entities.EntityModel;
import com.aidanloten.centralbanker.data.entities.descriptors.economy.finance.BalanceSheet;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Asset")
public class Asset implements EntityModel {
    @ManyToOne
    private BalanceSheet balanceSheet;
    private String name;
    @ManyToOne
    private AssetType assetType;
    private String description;
    private Double value;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Builder.Default
    private int quantity = 1;

}
