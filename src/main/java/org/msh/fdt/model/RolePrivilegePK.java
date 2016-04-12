package org.msh.fdt.model;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by kenny on 8/19/14.
 */
public class RolePrivilegePK implements Serializable {
    private int roleId;
    private int privilegeId;

    @Column(name = "role_id", nullable = false, insertable = true, updatable = true)
    @Id
    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    @Column(name = "privilege_id", nullable = false, insertable = true, updatable = true)
    @Id
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

        RolePrivilegePK that = (RolePrivilegePK) o;

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
}
