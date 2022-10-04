package com.example.springSecurity.person;

import com.example.springSecurity.user.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {

    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public PersonService(PersonRepository personRepository, PasswordEncoder passwordEncoder) {
        this.personRepository = personRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<PersonEntity> getPersons() {
        return personRepository.findAll();
    }

    public boolean inputCheck(PersonEntity person){

        boolean lastnameCheck ;
        boolean identificationCheck ;
        boolean emailCheck ;
        boolean phoneCheck ;
        boolean userCheck;

        lastnameCheck = (person.getLastName() !=null && !person.getLastName().isBlank()) ? true : false;
        identificationCheck = (person.getIdentificationID() != null && !person.getIdentificationID().isBlank()) ? !personRepository.findByIdentificationID(person.getIdentificationID()).isPresent() : true;
        emailCheck = (person.getEmail() != null && !person.getEmail().isBlank()) ? !personRepository.findByEmail(person.getEmail()).isPresent() : true;
        phoneCheck = (person.getPhoneNumber() != null && !person.getPhoneNumber().isBlank()) ? !personRepository.findByPhoneNumber(person.getPhoneNumber()).isPresent() : true;
        userCheck = (person.getUser() != null) ? usercheck(person.getUser()) : true;

        if (!lastnameCheck) {
            throw new IllegalStateException("Lastname should be provided!");
        }
        if (!identificationCheck) {
            throw new IllegalStateException("A person with this identification number is registered before!");
        }
        if (!emailCheck) {
            throw new IllegalStateException("This Email is registered before!");
        }
        if (!phoneCheck) {
            throw new IllegalStateException("This phone number is registered before!");
        }

        boolean inputCheck = identificationCheck && emailCheck && phoneCheck && userCheck;
        return inputCheck;
    }

    public boolean usercheck (UserEntity user) {
        boolean usernameCheck = false;
        boolean passwordCheck;

        if (user.getUsername() != null && !user.getUsername().isBlank()) {
            if (personRepository.findByUser_Username(user.getUsername()).isPresent()) {
                throw new IllegalStateException("This username is registered before!");
            } else {
                usernameCheck = true;
            }
        } else {
            throw new IllegalStateException("username should be provided!");
        }

//        usernameCheck = (user.getUsername() != null && !user.getUsername().isBlank()) ?
//                                (userRepository.findByUsername(user.getUsername()).isPresent() ? false : true  ) : false;

        passwordCheck = (user.getPassword() != null && user.getPassword().length() >= 4) ? true : false;

        if (!passwordCheck) {
            throw new IllegalStateException("Password is null or too short");
        }

        return (usernameCheck && passwordCheck);
    }

    public PersonEntity addNewPerson(PersonEntity person) {

        if (inputCheck(person)) {
            String rowPassword = person.getUser().getPassword();

            UserEntity user = new UserEntity(person.getUser().getUsername(),
                                             passwordEncoder.encode(rowPassword),
                                             person.getUser().getActive(),
                                             person.getUser().getExpired(),
                                             person.getUser().getLocked());

            person.setUser(user);
            personRepository.save(person);
            return person;
        }
        return null;
    }
}
