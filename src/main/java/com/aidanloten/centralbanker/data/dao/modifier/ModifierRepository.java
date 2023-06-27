package com.aidanloten.centralbanker.data.dao.modifier;

import com.aidanloten.centralbanker.data.entities.descriptors.economy.Modifier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModifierRepository extends JpaRepository<Modifier, Integer> {
}
