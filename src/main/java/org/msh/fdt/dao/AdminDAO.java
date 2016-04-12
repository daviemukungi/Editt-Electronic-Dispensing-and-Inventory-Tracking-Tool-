package org.msh.fdt.dao;

import org.msh.fdt.model.*;
import org.msh.fdt.util.AdminRequestWrapper;
import org.msh.fdt.util.UserUtils;

import java.util.List;

/**
 * Created by kenny on 4/11/14.
 */
public interface AdminDAO {

    public Integer createRole(Role role);

    public Integer createPrivilege(Privilege privilege);

    public void createRolePrivilege(RolePrivilege rolePrivilege);

    public List<Privilege> listPrivilege();

    public List<org.msh.fdt.dto.Role> listRole() ;

    public void updatePrivilege(Privilege privilege);

    public void updateRole(org.msh.fdt.dto.Role role);

    public void deletePrivilege(Integer privilegeId, Integer userId);

    public void deleteRole(Integer privilegeId, Integer userId);

    public void deleteRoleUser(Integer roleId);

    public void saveUser(User user);

    public List<UserUtils> listUsers();

    public AdminRequestWrapper getUserInfo(Integer userId);

    public void deleteUser(Integer userId, Integer loggedInUserId);

    public void updateUser(User user);

    public void deleteRolePrivilege(Integer roleId);

    public void deleteUserRole(Integer userId);

    public User getUser(Integer userId);

    public void updateUserObj(User user);

    public List<Property> loadFacilityInformation();

    public void saveFacilityInformation(Property property, Integer userId);
}
