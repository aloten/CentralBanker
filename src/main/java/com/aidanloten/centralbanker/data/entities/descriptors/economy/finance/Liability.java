package com.aidanloten.centralbanker.data.entities.descriptors.economy.finance;

import com.aidanloten.centralbanker.data.entities.EntityModel;
import com.aidanloten.centralbanker.data.entities.agents.Company;
import com.aidanloten.centralbanker.data.entities.agents.Person;
import lombok.*;
import jakarta.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Liability")
public class Liability implements EntityModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private double value;
    @ManyToOne
    private Company debtorCompany;
    @ManyToOne
    private Company creditorCompany;
    @ManyToOne
    private Person debtorPerson;
    @ManyToOne
    private Person creditorPerson;
}
