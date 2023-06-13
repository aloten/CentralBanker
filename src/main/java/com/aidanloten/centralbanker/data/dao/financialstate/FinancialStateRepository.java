package com.aidanloten.centralbanker.data.dao.financialstate;

import com.aidanloten.centralbanker.data.entities.descriptors.economy.finance.FinancialState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FinancialStateRepository extends JpaRepository<FinancialState, Integer> {
}
