package com.aidanloten.centralbanker.data.entities.descriptors.economy.finance;

import com.aidanloten.centralbanker.data.entities.EntityModel;
import lombok.*;

import jakarta.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "FinancialState")
public class FinancialState implements EntityModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private BalanceSheet balanceSheet;
}
