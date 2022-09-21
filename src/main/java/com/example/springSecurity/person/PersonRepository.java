package com.example.springSecurity.person;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<PersonEntity,Long > {

    Optional<PersonEntity> findByFirstName(String identificationID);
    Optional<PersonEntity> findByLastName(String identificationID);
    Optional<PersonEntity> findByIdentificationID(String identificationID);
    Optional<PersonEntity> findByEmail(String email);
    Optional<PersonEntity> findByPhoneNumber(String phoneNumber);

}

