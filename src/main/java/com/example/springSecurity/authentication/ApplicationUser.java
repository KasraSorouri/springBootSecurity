package com.example.springSecurity.authentication;

import com.example.springSecurity.person.right.RightEntity;
import com.example.springSecurity.person.role.RoleEntity;
import com.example.springSecurity.user.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ApplicationUser implements UserDetails {

    private UserEntity user;

    public ApplicationUser(UserEntity user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<RoleEntity> roles = user.getAssignedRole();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        for (RoleEntity role : roles){
            Set<RightEntity> rights = role.getAssignedRight().stream().collect(Collectors.toSet());

            for (RightEntity right: rights){

                authorities.add(new SimpleGrantedAuthority(right.getRightName()));
            }
            authorities.add(new SimpleGrantedAuthority("ROLE_"+role.getRoleName().toUpperCase()));
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
//        return (user.getExpired() != null ? !user.getExpired() : true);
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
//        return (user.getLocked() != null ? !user.getLocked() : true);
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {

//        return (user.getActive() != null ? user.getActive() : true);
        return  true;
    }
}

