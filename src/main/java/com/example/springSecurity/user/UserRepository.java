package com.example.springSecurity.user;

import com.example.springSecurity.person.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<PersonEntity,Long > {

    Optional<UserEntity> findByUser_Username(String username);
}
