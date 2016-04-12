package org.msh.fdt.model;

import javax.persistence.*;

/**
 * Created by kenny on 8/19/14.
 */
@Entity
@Table(name = "role_privilege", schema = "", catalog = "fdt")
@IdClass(RolePrivilegePK.class)
public class RolePrivilege {
    private int roleId;
    private int privilegeId;
    private Privilege privilegeByPrivilegeId;
    private Role roleByRoleId;

    @Id
    @Column(name = "role_id", nullable = false, insertable = true, updatable = true)
    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    @Id
    @Column(name = "privilege_id", nullable = false, insertable = true, updatable = true)
    public int getPrivilegeId() {
        return privilegeId;
    }

    public void setPrivilegeId(int privilegeId) {
        this.privilegeId = privilegeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RolePrivilege that = (RolePrivilege) o;

        if (privilegeId != that.privilegeId) return false;
        if (roleId != that.roleId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = roleId;
        result = 31 * result + privilegeId;
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "privilege_id", referencedColumnName = "id", nullable = false, insertable=false, updatable=false)
    public Privilege getPrivilegeByPrivilegeId() {
        return privilegeByPrivilegeId;
    }

    public void setPrivilegeByPrivilegeId(Privilege privilegeByPrivilegeId) {
        this.privilegeByPrivilegeId = privilegeByPrivilegeId;
    }

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false, insertable=false, updatable=false)
    public Role getRoleByRoleId() {
        return roleByRoleId;
    }

    public void setRoleByRoleId(Role roleByRoleId) {
        this.roleByRoleId = roleByRoleId;
    }
}
