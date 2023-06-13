package com.aidanloten.centralbanker.data.dao.industry;

import com.aidanloten.centralbanker.data.entities.descriptors.economy.Industry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IndustryRepository extends JpaRepository<Industry, Integer> {
    Industry findByName(String name);
}
