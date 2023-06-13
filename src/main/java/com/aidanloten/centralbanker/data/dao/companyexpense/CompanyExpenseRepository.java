package com.aidanloten.centralbanker.data.dao.companyexpense;

import com.aidanloten.centralbanker.data.entities.descriptors.economy.finance.CompanyExpense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyExpenseRepository extends JpaRepository<CompanyExpense, Integer> {
}
