package com.example.springSecurity.authentication.initalConfig;

import com.example.springSecurity.person.PersonEntity;
import com.example.springSecurity.person.PersonRepository;
import com.example.springSecurity.user.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Configuration
public class InitialAdmin {
    private final PersonRepository personRepository;

    private final PasswordEncoder passwordEncoder;


    @Autowired
    public InitialAdmin(PersonRepository personRepository,
                        PasswordEncoder passwordEncoder) {
        this.personRepository = personRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public void addAdmin(){
         PersonEntity initAdmin = new PersonEntity(100L,
                "Admin",
                "Initial",
                "",
                "",
                "",
                new UserEntity("admin",
                        passwordEncoder.encode("p1230"),
                        true,
                        false,
                        false)
        ,"");
        if (personRepository.findByUser_Username(initAdmin.getUser().getUsername()).isPresent()){}
        else personRepository.save(initAdmin);
    }

}
