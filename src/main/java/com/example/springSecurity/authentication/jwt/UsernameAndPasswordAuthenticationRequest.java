package com.example.springSecurity.authentication.jwt;

public class UsernameAndPasswordAuthenticationRequest {
    private String username;
    private String password;

    public UsernameAndPasswordAuthenticationRequest() {
        System.out.println("User name and Password Authentication Filter !");
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

}
