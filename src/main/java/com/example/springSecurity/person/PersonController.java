package com.example.springSecurity.person;

import com.example.springSecurity.person.role.RoleEntity;
import com.example.springSecurity.person.role.RoleRepository;
import com.example.springSecurity.user.UserEntity;
import com.example.springSecurity.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/person")
public class PersonController {

    private final PersonService personService;
    private final PersonRepository personRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Autowired
    public PersonController(PersonService personService,
                            PersonRepository personRepository,
                            RoleRepository roleRepository,
                            UserRepository userRepository) {
        this.personService = personService;
        this.personRepository = personRepository;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
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
    @PutMapping(path = "/{personId}")
    public ResponseEntity EditPerson(@PathVariable("personId") Long personId,
                                     @RequestBody PersonEntity newPerson) {
        return ResponseEntity.ok().body(personService.updatePerson(personId,newPerson));
    }

    @DeleteMapping(path = "/{personId}")
    public ResponseEntity DelPerson(@PathVariable("personId") Long personId) {

        personService.deletePerson(personId);

        return ResponseEntity.ok(personId);

    }


    @PutMapping(path = "/{personId}/role/{roleId}")
    PersonEntity assignRoleToUser(
            @PathVariable Long personId,
            @PathVariable Integer roleId
    ) {
        PersonEntity person = personRepository.getReferenceById(personId);
        UserEntity user = person.getUser();
        RoleEntity role = roleRepository.findRoleByRoleId(roleId);
        user.setAssignedRole(role);
        return  personRepository.save(person);
    }

    @DeleteMapping(path = "/{personId}/role/{roleId}")
    PersonEntity deleteRoleFromUser(
            @PathVariable Long personId,
            @PathVariable Integer roleId
    ) {
        PersonEntity person = personRepository.getReferenceById(personId);
        UserEntity user = person.getUser();
        RoleEntity role = roleRepository.getReferenceById(roleId);

        user.delAssignedRole(role);
        return personRepository.save(person);
    }

    @PutMapping(path = "/{personId}/role")
    public void assignRolesToPeople(@PathVariable("personId") Long personId,
                                    @RequestBody Map<String, Object>[] rolesRaw) throws ParseException {
        PersonEntity person = personRepository.findById(personId).get();
        UserEntity user = person.getUser();
        user.clearAssignedRoles();
        for (Map<String, Object> roleRaw: rolesRaw){
            String stringToConvert = String.valueOf(roleRaw.get("roleId"));
            Integer roleId = Integer.parseInt(stringToConvert);
            RoleEntity role = roleRepository.findRoleByRoleId(roleId);
            user.setAssignedRole(role);
        }
        personRepository.save(person);
    }

}