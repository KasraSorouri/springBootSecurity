package com.example.springSecurity.authentication;

import com.example.springSecurity.person.PersonRepository;
import com.example.springSecurity.user.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ApplicationUserService implements UserDetailsService {

    @Autowired
    PersonRepository personRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("checking for user ................!");
        Optional<UserEntity> user = Optional.ofNullable(personRepository.findByUser_Username(username).get().getUser());
        user.orElseThrow(() -> new UsernameNotFoundException(String.format("Username %s not found",username)));
        return user.map(ApplicationUser::new).get();
    }
}
