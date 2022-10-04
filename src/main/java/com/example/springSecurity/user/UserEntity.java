package com.example.springSecurity.user;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.UniqueConstraint;


@Embeddable
public class UserEntity {
    @Column(unique = true)
    private String username;
    private String password;
    private Boolean active;
    private Boolean expired;
    private Boolean locked;

    public UserEntity(String username, String password, Boolean active, Boolean expired, Boolean locked) {
        this.username = username;
        this.password = password;
        this.active = active;
        this.expired = expired;
        this.locked = locked;
    }

    public UserEntity() {
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Boolean getActive() {
        return active;
    }

    public Boolean getExpired() {
        return expired;
    }

    public Boolean getLocked() {
        return locked;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public void setExpired(Boolean expired) {
        this.expired = expired;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", active=" + active +
                ", expired=" + expired +
                ", locked=" + locked +
                '}';
    }
}
