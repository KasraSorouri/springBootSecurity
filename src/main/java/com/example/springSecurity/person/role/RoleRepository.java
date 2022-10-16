package com.example.springSecurity.person.role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Integer> {

    Optional<RoleEntity> findRoleByRoleName (String roleName);

    RoleEntity findRoleByRoleId(Integer roleId);

}
