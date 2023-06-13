package com.aidanloten.centralbanker.data.dao.companyexpensetype;

import com.aidanloten.centralbanker.data.entities.descriptors.economy.finance.CompanyExpenseType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyExpenseTypeRepository extends JpaRepository<CompanyExpenseType, Integer> {
    CompanyExpenseType findByName(String name);

}
