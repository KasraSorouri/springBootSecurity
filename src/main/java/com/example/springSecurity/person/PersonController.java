package com.example.springSecurity.person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/person")
public class PersonController {

    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    public List<PersonEntity> getPersons(){
        return personService.getPersons();
    }

    @GetMapping(path = "/{personId}")
    public PersonEntity getPersons(@PathVariable("personId") Long personId) {
        PersonEntity personEntity = getPersons().stream()
                .filter(person -> personId.equals(person.getPersonID()))
                .findFirst()
                .orElseThrow(()-> new IllegalStateException("Person with id " + personId + "dose not exist!"));
        return personEntity;
    }

    @PostMapping
    public ResponseEntity RegisterNewPerson(@RequestBody PersonEntity person) {
        personService.addNewPerson(person);
        return ResponseEntity.ok().body(person);
    }

}