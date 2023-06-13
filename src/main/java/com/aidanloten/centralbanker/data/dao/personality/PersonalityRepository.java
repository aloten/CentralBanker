package com.aidanloten.centralbanker.data.dao.personality;

import com.aidanloten.centralbanker.data.entities.descriptors.person.Personality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonalityRepository extends JpaRepository<Personality, Integer> {
}
