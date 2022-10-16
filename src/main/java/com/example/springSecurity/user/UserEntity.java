package com.example.springSecurity.user;

import com.example.springSecurity.person.role.RoleEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Embeddable
public class UserEntity {
    @Column(unique = true)
    private String username;
    private String password;
    private Boolean active;
    private Boolean expired;
    private Boolean locked;

    @ManyToMany(cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    @JoinTable(
            name = "User_Role",
            joinColumns = {@JoinColumn(name = "personID")},
            inverseJoinColumns = {@JoinColumn(name = "RoleId")}
    )
    private Set<RoleEntity> assignedRole = new HashSet<RoleEntity>();



    public UserEntity(String username,
                      String password,
                      Boolean active,
                      Boolean expired,
                      Boolean locked) {
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

    public void setAssignedRole(RoleEntity role) {
        assignedRole.add(role);
    }

    public Set<RoleEntity> getAssignedRole() {
        return assignedRole;
    }

    public void delAssignedRole(RoleEntity role) {
        assignedRole.remove(role);
    }
    public void clearAssignedRoles() {assignedRole.clear();}


    @Override
    public String toString() {
        return "UserEntity{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", active='" + active + '\''+
                ", expired='" + expired + '\''+
                ", locked='" + locked +'\''+
                ", roles='" + assignedRole+
                '}';
    }
}
