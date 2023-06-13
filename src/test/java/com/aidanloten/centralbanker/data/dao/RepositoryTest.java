package com.aidanloten.centralbanker.data.dao;

import com.aidanloten.centralbanker.data.dao.person.PersonRepository;
import com.aidanloten.centralbanker.data.entities.agents.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class RepositoryTest {
    @Autowired
    PersonRepository personRepository;

    @BeforeEach
    void setup() {
        Person person1 = Person.builder().firstName("Anthony").salary(1000).build();
        Person person2 = Person.builder().firstName("Jian Yang").salary(2000).build();
        personRepository.saveAllAndFlush(List.of(person1, person2));
    }

    @Test
    void findAll() {
        List<Person> people = personRepository.findAll();
        assertEquals(people.size(), 2);
    }

    @Test
    void sumSalary() {
        double sumSalary = personRepository.sumSalary();
        assertEquals(3000, sumSalary);
    }
}