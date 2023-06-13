package com.aidanloten.centralbanker.data.dao.personexpense;

import com.aidanloten.centralbanker.data.entities.agents.Person;
import com.aidanloten.centralbanker.data.entities.descriptors.economy.finance.PersonExpense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonExpenseRepository extends JpaRepository<PersonExpense, Integer> {
    @Query("SELECT SUM(value) FROM PersonExpense")
    double sumValue();
    @Query("SELECT SUM(value) FROM PersonExpense WHERE person = :person")
    double sumValueByPerson(@Param("person") Person person);
}
