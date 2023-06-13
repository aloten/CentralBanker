package com.aidanloten.centralbanker.data.entities.descriptors.person;

import com.aidanloten.centralbanker.data.entities.EntityModel;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Personality")
public class Personality implements EntityModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int industrySkillLevel; // most = 10, least = 1, e.g. (most skilled = 10)
    private int communicationLevel;
    private int industriousLevel;
    private int abilityToLearnLevel;
}
