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
@Table(name = "CompanyExpense")
public class CompanyExpense implements EntityModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    private CompanyExpenseType type;
    private double value;
    @ManyToOne
    private Company companyExpender;
    @ManyToOne
    private Company companyVendor;
    @OneToOne
    private Person employee;
}
