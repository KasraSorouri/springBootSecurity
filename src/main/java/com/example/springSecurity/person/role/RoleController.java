package com.example.springSecurity.person.role;

import com.example.springSecurity.person.right.RightEntity;
import com.example.springSecurity.person.right.RightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/role")
public class RoleController {

    public final RoleService roleService;
    public final RoleRepository roleRepository;
    public final RightRepository rightRepository;

    @Autowired
    public RoleController(RoleService roleService,
                          RoleRepository roleRepository,
                          RightRepository rightRepository) {
        this.roleService = roleService;
        this.roleRepository = roleRepository;
        this.rightRepository = rightRepository;
    }

    @GetMapping
    public List<RoleEntity> getRole() {
        return roleService.getRole();
    }

    @GetMapping (path = "{roleId}")
    public RoleEntity getRole(@PathVariable("roleId") Integer roleId){
        RoleEntity roleEntity = getRole().stream()
                .filter(role -> roleId.equals(role.getRoleId()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Role with id" + roleId + "does not exist !"));
        return roleEntity;
    }

    @PostMapping
    public ResponseEntity registerNewRole(@RequestBody RoleEntity role) {

        roleService.addNewRole(role);
        return ResponseEntity.ok().body(role);
    }

    @DeleteMapping(path = "{roleId}")
    public void deleteRole(@PathVariable("roleId") Integer roleId) {

        roleService.deleteRole(roleId);
    }

    @PutMapping(path = "{roleId}")
    public ResponseEntity editRole(
            @PathVariable("roleId") Integer roleId,
            @RequestBody RoleEntity newRole) {
        return ResponseEntity.ok().body(roleService.updateRole(roleId, newRole));
    }

    @PutMapping(path = "/{roleId}/right/{rightId}")
    RoleEntity assignRightToRole(
            @PathVariable Integer roleId,
            @PathVariable Integer rightId
    ) {
        RoleEntity role = roleRepository.getReferenceById(roleId);
        RightEntity right = rightRepository.findRightByRightId(rightId);
        role.setAssignedRight(right);
        return  roleRepository.save(role);
    }
    @DeleteMapping(path = "/{roleId}/right/{rightId}")
    RoleEntity deleteRightFromRole(
            @PathVariable Integer roleId,
            @PathVariable Integer rightId
    ) {
        RoleEntity role = roleRepository.getReferenceById(roleId);
        RightEntity right = rightRepository.findRightByRightId(rightId);
        role.delAssignedRight(right);
        return roleRepository.save(role);
    }

    @PutMapping(path = "/{roleId}/right")
    public void assignRightToRole(@PathVariable("roleId") Integer roleId,
                                  @RequestBody Map<String, Object>[] rightsRaw) throws ParseException {
        RoleEntity role = roleRepository.getReferenceById(roleId);
        role.clearAssignedRights();
        for (Map<String, Object> rightRaw: rightsRaw){
            String stringToConvert = String.valueOf(rightRaw.get("rightId"));
            Integer rightId = Integer.parseInt(stringToConvert);
            RightEntity right = rightRepository.findRightByRightId(rightId);
            role.setAssignedRight(right);
        }
        roleRepository.save(role);
    }

}

