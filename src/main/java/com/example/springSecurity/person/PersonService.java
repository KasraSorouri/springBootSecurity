package com.example.springSecurity.person;

import com.example.springSecurity.user.UserEntity;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

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

    public boolean lastnameCheck(String lastName) {
        boolean lastnameCheck = (lastName !=null && !lastName.isBlank()) ? true : false;

        if (!lastnameCheck) {
            throw new IllegalStateException("Lastname should be provided!");
        }
        return  lastnameCheck;
    }

    public boolean identificationCheck(String identification) {
        boolean identificationCheck = (identification != null && !identification.isBlank()) ?
                                        !personRepository.findByIdentificationID(identification).isPresent() : true;

        if (!identificationCheck) {
            throw new IllegalStateException("A person with this identification number is registered before!");
        }
        return identificationCheck;
    }

    public boolean emailCheck(String email) {
        boolean emailCheck = (email != null && !email.isBlank()) ?
                                !personRepository.findByEmail(email).isPresent() : true;

        if (!emailCheck) {
            throw new IllegalStateException("This Email is registered before!");
        }
        return emailCheck;
    }

    public boolean phoneCheck(String phoneNumber) {
        boolean phoneCheck = (phoneNumber != null && !phoneNumber.isBlank()) ?
                                !personRepository.findByPhoneNumber(phoneNumber).isPresent() : true;

        if (!phoneCheck) {
           throw new IllegalStateException("This phone number is registered before!");
        }
        return phoneCheck;
    }

    public boolean usernamecheck (String username) {
        boolean usernameCheck = false;

        if (username != null && !username.isBlank()) {
            if (personRepository.findByUser_Username(username).isPresent()) {
                throw new IllegalStateException("This username is registered before!");
            } else {
                usernameCheck = true;
            }
        } else {
            throw new IllegalStateException("username should be provided!");
        }

        return usernameCheck;
    }

    public boolean passwordCheck (String password) {

        boolean passwordCheck = (password != null && password.length() >= 4) ? true : false;

        if (!passwordCheck) {
            throw new IllegalStateException("Password is null or too short");
        }

        return passwordCheck;
    }

    public PersonEntity addNewPerson(PersonEntity person) {

        if (lastnameCheck(person.getLastName()) &&
                identificationCheck(person.getIdentificationID()) &&
                emailCheck(person.getEmail()) &&
                phoneCheck(person.getPhoneNumber())) {

            if (person.getUser() != null) {
                String rowPassword = person.getUser().getPassword();

                UserEntity user = new UserEntity(person.getUser().getUsername(),
                        passwordEncoder.encode(rowPassword),
                        person.getUser().getActive(),
                        person.getUser().getExpired(),
                        person.getUser().getLocked());

                person.setUser(user);
            }
            personRepository.save(person);
            return person;
        }
        return null;
    }
    @Transactional
    public PersonEntity updatePerson (Long personId, PersonEntity newPerson) {
        PersonEntity person = personRepository.findById(personId)
                .orElseThrow(()-> new IllegalStateException("Person with ID " + personId +" does not exist!"));

        if (newPerson.getFirstName() != null &&
            !newPerson.getFirstName().isBlank() &&
            !Objects.equals(newPerson.getFirstName() , person.getFirstName())) {

            person.setFirstName(newPerson.getFirstName());
        }

        if (newPerson.getLastName() != null &&
            !Objects.equals(newPerson.getLastName(), person.getLastName()) &&
            lastnameCheck(newPerson.getLastName())) {

            person.setLastName(newPerson.getLastName());
        }

        if (newPerson.getIdentificationID() != null &&
            !Objects.equals(newPerson.getIdentificationID(), person.getIdentificationID()) &&
            identificationCheck(newPerson.getIdentificationID())) {

            person.setIdentificationID(newPerson.getIdentificationID());
        }

        if (newPerson.getAddress() != null &&
            !newPerson.getAddress().isBlank() &&
            !Objects.equals(newPerson.getAddress(), person.getAddress())) {

            person.setAddress(newPerson.getAddress());
        }

        if (newPerson.getEmail() != null &&
            !Objects.equals(newPerson.getEmail(), person.getEmail()) &&
            emailCheck(newPerson.getEmail())) {

            person.setEmail(newPerson.getEmail());
        }

        if (newPerson.getPhoneNumber() != null &&
            !Objects.equals(newPerson.getPhoneNumber(), person.getPhoneNumber()) &&
            phoneCheck(newPerson.getPhoneNumber())) {

            person.setPhoneNumber(newPerson.getPhoneNumber());
        }

        if (newPerson.getUser() != null) {
            if (!Objects.equals(newPerson.getUser(), person.getUser())) {

               UserEntity user = person.getUser();

               if (newPerson.getUser().getUsername() != null &&
                   !Objects.equals(newPerson.getUser().getUsername(), user.getUsername()) &&
                   usernamecheck(newPerson.getUser().getUsername())) {

                      user.setUsername(newPerson.getUser().getUsername());
               }

               if (newPerson.getUser().getPassword() != null &&
                   !Objects.equals(newPerson.getUser().getPassword(), user.getPassword()) &&
                   passwordCheck(newPerson.getUser().getPassword())) {

                      user.setPassword(passwordEncoder.encode(newPerson.getUser().getPassword()));
               }

               if (newPerson.getUser().getExpired() != null &&
                  !Objects.equals(newPerson.getUser().getExpired(), user.getExpired())) {

                      user.setExpired(newPerson.getUser().getExpired());
                    }

               if (newPerson.getUser().getActive() != null &&
                   !Objects.equals(newPerson.getUser().getActive(), user.getActive())) {

                       user.setActive(newPerson.getUser().getActive());
               }

               if (newPerson.getUser().getLocked() != null &&
                   !Objects.equals(newPerson.getUser().getLocked(), user.getLocked())) {

                      user.setLocked(newPerson.getUser().getLocked());
               }

               person.setUser(user);
            }
        }

        personRepository.save(person);

        return person;

    }

    public void deletePerson(Long personId) {
        personRepository.deleteById(personId);
    }
}
