package com.aidanloten.centralbanker.data.dao.economy;

import com.aidanloten.centralbanker.data.entities.descriptors.economy.Economy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EconomyRepository extends JpaRepository<Economy, Integer> {
}
