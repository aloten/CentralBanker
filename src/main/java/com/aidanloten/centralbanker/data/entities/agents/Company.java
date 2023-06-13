package com.aidanloten.centralbanker.data.entities.agents;

import com.aidanloten.centralbanker.data.entities.descriptors.economy.Industry;
import com.aidanloten.centralbanker.data.entities.descriptors.economy.finance.FinancialState;
import lombok.*;

import jakarta.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Company")
public class Company implements Agent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    //    @Column(unique = true), commented out for testing
    private String formalName;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Industry industry;
    private boolean sellsB2C;
    private boolean extractsRawMaterials;
    private int employeeCount;
    private double b2bRevenue;
    private double b2cRevenue;
    private double employeeExpenditure;
    private double b2bExpenditure;
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private FinancialState financialState;
}
