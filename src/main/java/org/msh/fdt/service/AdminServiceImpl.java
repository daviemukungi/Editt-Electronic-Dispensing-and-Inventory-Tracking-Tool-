package org.msh.fdt.service;

import org.msh.fdt.dao.AdminDAO;
import org.msh.fdt.dao.ReferenceDAO;
import org.msh.fdt.model.*;
import org.msh.fdt.util.AdminRequestWrapper;
import org.msh.fdt.util.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

/**
 * Created by kenny on 4/11/14.
 */
@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminDAO adminDAO;

    @Autowired
    private ReferenceDAO referenceDAO;


    @Override
    @Transactional
    public Integer createPrivilege(Privilege privilege) {
        return adminDAO.createPrivilege(privilege);
    }

    @Override
    @Transactional
    public Integer createRole(Role role, List<Integer> privileges) {
        Integer roleId = adminDAO.createRole(role);
        if(privileges != null) {
            for(int i = 0; i < privileges.size(); i++) {
                Integer privilege = privileges.get(i);
                RolePrivilege r = new RolePrivilege();
                r.setRoleId(roleId);
                r.setPrivilegeId(privilege);
                adminDAO.createRolePrivilege(r);
            }
        }
        return roleId;
    }

    @Override
    @Transactional
    public List<Privilege> privilegeList() {
        return adminDAO.listPrivilege();
    }

    @Override
    @Transactional
    public List<org.msh.fdt.dto.Role> roleList() {
        return adminDAO.listRole();
    }

    @Override
    @Transactional
    public void updatePrivilege(Privilege privilege) {
        adminDAO.updatePrivilege(privilege);
    }

    @Override
    @Transactional
    public void updateRole(org.msh.fdt.dto.Role role, List<Integer> privileges) {
        adminDAO.updateRole(role);
        adminDAO.deleteRolePrivilege(role.getId());
        for(int i = 0; i < privileges.size(); i++) {
            Integer privilege = privileges.get(i);
            RolePrivilege r = new RolePrivilege();
            r.setRoleId(role.getId());
            r.setPrivilegeId(privilege);
            adminDAO.createRolePrivilege(r);
        }
    }

    @Override
    @Transactional
    public void deleteRole(Integer roleId, Integer userId) {
        adminDAO.deleteRolePrivilege(roleId);
        adminDAO.deleteRole(roleId, userId);
    }

    @Override
    @Transactional
    public void saveUser(User user) {
        adminDAO.saveUser(user);
    }

    @Override
    @Transactional
    public List<UserUtils> listUsers() {
        return adminDAO.listUsers();
    }

    @Override
    @Transactional
    public AdminRequestWrapper getUser(Integer userId) {
        return adminDAO.getUserInfo(userId);
    }

    @Override
    @Transactional
    public void deleteUser(Integer userId, Integer loggedInUserId) {
        adminDAO.deleteUser(userId, loggedInUserId);
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        adminDAO.updateUser(user);
    }

    @Override
    @Transactional
    public void deletePrivilege(Integer privilegeId, Integer userId) {
        adminDAO.deleteUser(privilegeId, userId);
    }

    @Override
    @Transactional
    public boolean updatePassword(String currentPassword, String newPassword, Integer userId) {
        User user = adminDAO.getUser(userId);
        if(user.getPassword() != null && !user.getPassword().equals(currentPassword)) {
            return false;
        }
        user.setPassword(newPassword);
        adminDAO.updateUserObj(user);
        return true;
    }

    @Override
    @Transactional
    public List<Property> getFacilityInformation() {
        return adminDAO.loadFacilityInformation();
    }

    @Override
    @Transactional
    public void updateFacilityInformation(List<Property> properties, Integer userId) {
        Iterator iterator = properties.iterator();
        while(iterator.hasNext()) {
            Property p = (Property)iterator.next();
            if(p.getId() <= 0) {
                p.setCreatedBy(userId);
                p.setCreatedOn(new Timestamp(new Date().getTime()));
                p.setUuid(UUID.randomUUID().toString());
                referenceDAO.createProperty(p);
            } else {
                p.setUpdatedBy(userId);
                p.setUpdatedOn(new Timestamp(new Date().getTime()));
                referenceDAO.updateProperty(p);
            }
            //adminDAO.saveFacilityInformation((Property)iterator.next(), userId);
        }
    }

    @Override
    @Transactional
    public List<Property> getDatabaseInformation() {
        return adminDAO.loadFacilityInformation();
    }

    @Override
    @Transactional
    public void updateDatabaseInformation(List<Property> properties, Integer userId) {
        Iterator iterator = properties.iterator();
        while(iterator.hasNext()) {
            Property p = (Property)iterator.next();
            if(p.getId() <= 0) {
                p.setCreatedBy(userId);
                p.setCreatedOn(new Timestamp(new Date().getTime()));
                p.setUuid(UUID.randomUUID().toString());
                referenceDAO.createProperty(p);
            } else {
                p.setUpdatedBy(userId);
                p.setUpdatedOn(new Timestamp(new Date().getTime()));
                referenceDAO.updateProperty(p);
            }
            //adminDAO.saveFacilityInformation((Property)iterator.next(), userId);
        }
    }
}
