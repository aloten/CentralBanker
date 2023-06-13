package com.aidanloten.centralbanker.data.dao.balancesheet;

import com.aidanloten.centralbanker.data.entities.descriptors.economy.finance.BalanceSheet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BalanceSheetRepository extends JpaRepository<BalanceSheet, Integer> {
}
