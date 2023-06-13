package com.aidanloten.centralbanker.data.entities.agents;

import com.aidanloten.centralbanker.data.entities.descriptors.economy.Modifier;
import com.aidanloten.centralbanker.data.entities.descriptors.economy.finance.FinancialState;
import com.aidanloten.centralbanker.data.entities.descriptors.economy.finance.assets.AssetType;
import com.aidanloten.centralbanker.data.entities.descriptors.person.Personality;
import com.aidanloten.centralbanker.engine.consumption.ConsumptionRequirements;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Person")
public class Person implements Agent {

    /**
     * The asset type that the person "produces" in P2P trading scenario
     */
    @ManyToOne
    private AssetType assetTypeProduces;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String sex;
    private int age;
    private String jobTitle;
    private double salary;
    @ManyToOne
    private Company company;
    @OneToOne
    private Personality personality;
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private FinancialState financialState;
    @ManyToMany
    private List<Modifier> consumptionModifiers;
    @Transient
    private ConsumptionRequirements consumptionRequirements;

}
