package com.aidanloten.centralbanker.data.entities.descriptors.economy.finance;

import com.aidanloten.centralbanker.data.entities.EntityModel;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "CompanyExpenseType")
public class CompanyExpenseType implements EntityModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
}
