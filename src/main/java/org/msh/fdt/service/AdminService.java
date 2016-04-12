package org.msh.fdt.service;

import org.msh.fdt.model.Privilege;
import org.msh.fdt.model.Property;
import org.msh.fdt.model.Role;
import org.msh.fdt.model.User;
import org.msh.fdt.util.AdminRequestWrapper;
import org.msh.fdt.util.UserUtils;

import java.util.List;

/**
 * Created by kenny on 4/11/14.
 */
public interface AdminService {

    public Integer createPrivilege(Privilege privilege);

    public Integer createRole(Role role, List<Integer> privileges);

    public List<Privilege> privilegeList();

    public List<org.msh.fdt.dto.Role> roleList();

    public void updatePrivilege(Privilege privilege);

    public void updateRole(org.msh.fdt.dto.Role role, List<Integer> privileges);

    public void deletePrivilege(Integer privilegeId, Integer userId);

    public void deleteRole(Integer roleId, Integer userId);

    public void saveUser(User user);

    public List<UserUtils> listUsers();

    public AdminRequestWrapper getUser(Integer userId);

    public void deleteUser(Integer userId, Integer loggedInUserId);

    public void updateUser(User user);

    public boolean updatePassword(String currentPassword, String newPassword, Integer personId);

    public List<Property> getFacilityInformation();

    public void updateFacilityInformation(List<Property> properties, Integer userId);


    public List<Property> getDatabaseInformation();

    public void updateDatabaseInformation(List<Property> properties, Integer userId);

}
