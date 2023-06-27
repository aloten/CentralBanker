package com.aidanloten.centralbanker.data.entities.descriptors.economy;

import com.aidanloten.centralbanker.data.entities.EntityModel;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Modifier")
public class Modifier implements EntityModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String description;
    @Builder.Default
    private double effectPercentage = 100;
}
