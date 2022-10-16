package com.example.springSecurity.authentication.jwt;

import com.example.springSecurity.authentication.ApplicationUser;
import com.example.springSecurity.person.PersonEntity;
import com.example.springSecurity.person.PersonRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/auth/")
public class authenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PersonRepository personRepository;

    private String SECRET_KEY = "SpringSecurity*SpringSecurity*SpringSecurity*SpringSecurity*SpringSecurity*";
    @PostMapping("/login")
    public ResponseEntity<?> login (@RequestBody @Validated UsernameAndPasswordAuthenticationRequest request) {

        try {
            System.out.println("request is analysing ......... "+request.getUsername());
            Optional<PersonEntity> test= personRepository.findByUser_Username(request.getUsername());
            Authentication authenticate = authenticationManager
                    .authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    request.getUsername(),
                                    request.getPassword()
                            )
                    );

            ApplicationUser applicationUser = (ApplicationUser) authenticate.getPrincipal();

            String token = "Bearer " + Jwts.builder()
                    .setSubject(applicationUser.getUsername())
                    .claim("authorities", applicationUser.getAuthorities())
                    .setIssuedAt(new Date())
                    .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusWeeks(2)))
                    .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                    .compact();

            return ResponseEntity.ok()
                    .header(HttpHeaders.AUTHORIZATION, token)
                    .contentType(MediaType.valueOf("application/json"))
                    .body(Map.of("Token", token, "username", applicationUser.getUsername(),
                            "Authorities", applicationUser.getAuthorities(), "Detail",
                            personRepository.findByUser_Username(applicationUser.getUsername())));


        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
