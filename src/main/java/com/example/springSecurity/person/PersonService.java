package com.example.springSecurity.person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {

    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<PersonEntity> getPersons() {
        return personRepository.findAll();
    }

    public PersonEntity addNewPerson(PersonEntity person) {

        boolean identificationCheck = false;
        boolean emailCheck = false;
        boolean phoneCheck = false;

        identificationCheck = (person.getIdentificationID() != null && !person.getIdentificationID().isBlank()) ? personRepository.findByIdentificationID(person.getIdentificationID()).isPresent() : false;
        emailCheck = (person.getEmail() != null && !person.getEmail().isBlank()) ? personRepository.findByEmail(person.getEmail()).isPresent() : false;
        phoneCheck = (person.getPhoneNumber() != null && !person.getPhoneNumber().isBlank()) ? personRepository.findByPhoneNumber(person.getPhoneNumber()).isPresent() : false;

        boolean lastnameCheck = person.getLastName().isBlank();

        if (lastnameCheck) {
           throw new IllegalStateException("Lastname should be provided!");
        }
        if (identificationCheck) {
            throw new IllegalStateException("A person with this identification number is registered before!");
        }
        if (emailCheck) {
            throw new IllegalStateException("This Email is registered before!");
        }
        if (phoneCheck) {
            throw new IllegalStateException("This phone number is registered before!");
        }
        if (!lastnameCheck && !identificationCheck && !emailCheck && !phoneCheck) {
            personRepository.save(person);
            return person;
        }
        return null;
    }
}
