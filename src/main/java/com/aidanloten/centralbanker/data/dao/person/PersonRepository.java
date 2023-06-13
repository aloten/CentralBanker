package com.aidanloten.centralbanker.data.dao.person;

import com.aidanloten.centralbanker.data.entities.agents.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {
    @Query("SELECT SUM(p.salary) FROM Person p")
    double sumSalary();
}
