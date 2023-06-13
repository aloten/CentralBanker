package com.aidanloten.centralbanker.service;

import com.aidanloten.centralbanker.data.dao.person.PersonRepository;
import com.aidanloten.centralbanker.data.dao.personality.PersonalityRepository;
import com.aidanloten.centralbanker.data.dao.personexpense.PersonExpenseRepository;
import com.aidanloten.centralbanker.data.entities.agents.Person;
import com.aidanloten.centralbanker.data.entities.descriptors.economy.finance.PersonExpense;
import com.aidanloten.centralbanker.data.entities.descriptors.person.Personality;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {
    @Autowired
    PersonRepository personRepository;
    @Autowired
    PersonExpenseRepository personExpenseRepository;
    @Autowired
    PersonalityRepository personalityRepository;

    public void savePersonality(Personality personality) {
        personalityRepository.save(personality);
    }

    public List<Personality> getAllPersonalities() {
        return personalityRepository.findAll();
    }

    public double getSumOfAllPersonExpenses() {
        return personExpenseRepository.sumValue();
    }

    public double getSumOfSalaries() {
        return personRepository.sumSalary();
    }

    public void savePersonExpense(PersonExpense personExpense) {
        personExpenseRepository.save(personExpense);
    }

    public Optional<Person> findById(int id) {
        return personRepository.findById(id);
    }

    public List<Person> findAllPeople() {
        return personRepository.findAll();
    }

    public void savePerson(Person person) {
        personRepository.save(person);
    }
}
