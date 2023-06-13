package com.aidanloten.centralbanker.data.entities.descriptors.economy.finance.assets;

import com.aidanloten.centralbanker.data.entities.EntityModel;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "AssetType")
public class AssetType implements EntityModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String description;
}
