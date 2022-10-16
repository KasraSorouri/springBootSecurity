package com.example.springSecurity.person.role;

import com.example.springSecurity.person.right.RightEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "Role")
@Table(name ="Role",
        uniqueConstraints = {
                @UniqueConstraint(name = "role_unique",
                        columnNames = "roleName")
        })
public class RoleEntity {
    @Id
    @SequenceGenerator(
            name = "role_sequence",
            sequenceName = "role_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "role_sequence"
    )
    @Column (name = "RoleId")
    private Integer roleId;

    @Column (name = "RoleName")
    private String roleName;

    @ManyToMany(cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    private Set<RightEntity> assignedRight = new HashSet<RightEntity>();


    public RoleEntity(Integer roleId,
                      String roleName) {
        this.roleId = roleId;
        this.roleName = roleName;
    }

    public RoleEntity(String roleName) {
        this.roleName = roleName;
    }

    public RoleEntity() {

    }

    public Integer getRoleId() {
        return roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public Set<RightEntity> getAssignedRight() {
        return assignedRight;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public void setAssignedRight(RightEntity right) {
        assignedRight.add(right);
    }

    public void delAssignedRight(RightEntity right) {
        assignedRight.remove(right);
    }
    public void clearAssignedRights() {assignedRight.clear();
    }

    @Override
    public String toString() {
        return "RoleEntity{" +
                "roleId=" + roleId +
                ", roleName='" + roleName + '\'' +
                ", assignedRight=" + assignedRight +
                '}';
    }

}
