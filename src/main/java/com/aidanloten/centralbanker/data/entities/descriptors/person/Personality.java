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
/**
 * skill levels: from 1 to 10 (most skilled = 10)
 * */
public class Personality implements EntityModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
//    private int industrySkill;
    private int hardworking;
    private int innovative;
}
