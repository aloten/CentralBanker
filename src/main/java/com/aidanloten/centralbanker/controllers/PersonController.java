package com.aidanloten.centralbanker.controllers;

import com.aidanloten.centralbanker.data.dto.PersonRequest;
import com.aidanloten.centralbanker.data.entities.agents.Person;
import com.aidanloten.centralbanker.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/person")
public class PersonController {
    @Autowired
    private final PersonService personService;

    public PersonController() {
        this.personService = new PersonService();
    }

    @PostMapping
    public ResponseEntity<String> createPerson(@RequestBody PersonRequest personRequest) {
        Person person = Person.builder().firstName(personRequest.getFirstName()).lastName(personRequest.getLastName())
                .salary(personRequest.getSalary()).build();
        personService.savePerson(person);

        return ResponseEntity.status(201).body("Person created successfully");
    }

    @GetMapping
    public ResponseEntity<List<Person>> getAllPeople() {
        return new ResponseEntity<List<Person>>(personService.findAllPeople(), HttpStatus.valueOf(200));
    }
}
