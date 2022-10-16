package com.example.springSecurity.person.role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<RoleEntity> getRole(){
        return roleRepository.findAll();
    }

    public RoleEntity addNewRole(RoleEntity role) {

        Optional<RoleEntity> roleOptional = roleRepository.findRoleByRoleName(role.getRoleName());
        if (roleOptional.isPresent()) {
            throw new IllegalStateException("This role is registered before!");
        }
        if (role.getRoleName() != null &&
                role.getRoleName().length() > 0) {
            roleRepository.save(role);
        } else throw new IllegalStateException("The name of role can not be empty!");
        return role;
    }

    public void deleteRole(Integer roleId) {
        boolean exists = roleRepository.existsById(roleId);
        if (!exists) {
            throw new IllegalStateException("This Person whit id "+ roleId+" do not exists!");
        }
        roleRepository.deleteById(roleId);
    }

    @Transactional
    public RoleEntity updateRole(Integer roleId,
                                 RoleEntity newRole) {
        RoleEntity updateRole = roleRepository.findById(roleId)
                .orElseThrow(() -> new IllegalStateException("person with ID " + roleId + " do not exists!"));

        if ( newRole.getRoleName() != null &&
                newRole.getRoleName().length() > 0 &&
                !Objects.equals(updateRole.getRoleName(), newRole.getRoleName())) {
            Optional <RoleEntity> roleEntityOptional = roleRepository.findRoleByRoleName(newRole.getRoleName());
            if (roleEntityOptional.isPresent()) {
                throw new IllegalStateException("This role is registered before!");
            }
            updateRole.setRoleName(newRole.getRoleName());
        }
        return updateRole;
    }
}
