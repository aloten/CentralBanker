package com.aidanloten.centralbanker.data.entities.descriptors.economy.finance;

import com.aidanloten.centralbanker.data.entities.EntityModel;
import com.aidanloten.centralbanker.data.entities.descriptors.economy.finance.assets.Asset;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "BalanceSheet")
public class BalanceSheet implements EntityModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
}
