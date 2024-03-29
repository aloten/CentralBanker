package com.aidanloten.centralbanker.data.entities.descriptors.economy;

import com.aidanloten.centralbanker.data.entities.EntityModel;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Industry")
public class Industry implements EntityModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private double revenueShare;
}
