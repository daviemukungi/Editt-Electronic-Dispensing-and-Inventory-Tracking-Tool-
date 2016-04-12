package org.msh.fdt.dao;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.msh.fdt.dto.*;
import org.msh.fdt.model.*;
import org.msh.fdt.model.PersonAddress;
import org.msh.fdt.model.Role;
import org.msh.fdt.util.AdminRequestWrapper;
import org.msh.fdt.util.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.*;

/**
 * Created by kenny on 4/11/14.
 */
@Repository
public class AdminDAOImpl implements AdminDAO{

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Integer createRole(Role role) {
        Integer id = (Integer)sessionFactory.getCurrentSession().save(role);
        return id;
    }

    @Override
    public Integer createPrivilege(Privilege privilege) {
        Integer id = (Integer)sessionFactory.getCurrentSession().save(privilege);
        return id;
    }

    @Override
    public void createRolePrivilege(RolePrivilege rolePrivilege) {
        sessionFactory.getCurrentSession().save(rolePrivilege);
    }

    @Override
    public List<Privilege> listPrivilege() {
        Query query = sessionFactory.getCurrentSession().createQuery("from Privilege ");

        List list = query.list();
        Iterator iter = list.iterator();
        List<Privilege> privilegeList = new ArrayList<Privilege>();
        while(iter.hasNext()) {
            Privilege privilege = (Privilege)iter.next();

            Privilege p = new Privilege();
            p.setId(privilege.getId());
            p.setName(privilege.getName());
            p.setDescription(privilege.getDescription());
            privilegeList.add(p);
        }
        return privilegeList;
    }

    @Override
    public List<org.msh.fdt.dto.Role> listRole() {
        Query query = sessionFactory.getCurrentSession().createQuery("from Role WHERE voided = 0");

        List list = query.list();
        Iterator iter = list.iterator();
        List<org.msh.fdt.dto.Role> roleList = new ArrayList<org.msh.fdt.dto.Role>();
        while(iter.hasNext()) {
            Role role = (Role)iter.next();
            org.msh.fdt.dto.Role r = new org.msh.fdt.dto.Role();

            Query query1 = sessionFactory.getCurrentSession().createQuery(" from RolePrivilege WHERE roleId =  " + role.getId());
            List rolePrivileges = query1.list();
            Iterator rIterator = rolePrivileges.iterator();
            while(rIterator.hasNext()) {
                RolePrivilege rp = (RolePrivilege)rIterator.next();
                r.getPrivileges().add(rp.getPrivilegeId());
            }

            r.setId(role.getId());
            r.setName(role.getName());
            r.setDescription(role.getDescription());
            roleList.add(r);
        }
        return roleList;
    }

    @Override
    public void updatePrivilege(Privilege privilege) {
        Privilege priv = (Privilege)sessionFactory.getCurrentSession().get(Privilege.class, privilege.getId());
        if(priv != null) {
            priv.setName(privilege.getName());
            priv.setDescription(privilege.getDescription());
            priv.setUpdatedBy(privilege.getUpdatedBy());
            priv.setUpdatedOn(new Timestamp(new Date().getTime()));
            sessionFactory.getCurrentSession().update(priv);
        }
    }

    @Override
    public void updateRole(org.msh.fdt.dto.Role role) {
        Role r = (Role)sessionFactory.getCurrentSession().get(Role.class, role.getId());
        if(r != null) {
            r.setName(role.getName());
            r.setDescription(role.getDescription());
            r.setUpdatedBy(role.getUpdatedBy());
            r.setUpdatedOn(new Timestamp(new Date().getTime()));
            sessionFactory.getCurrentSession().update(r);
        }
    }

    @Override
    public void saveUser(User user) {
        Collection userRoles = user.getUserRolesById();
        user.setUserRolesById(null);
        Integer userId = (Integer)sessionFactory.getCurrentSession().save(user);

        Iterator iterator = userRoles.iterator();
        while(iterator.hasNext()) {
            UserRole r = (UserRole)iterator.next();
            UserRole userRole = new UserRole();
            userRole.setRoleId(r.getRoleId());
            userRole.setUserId(userId);
            sessionFactory.getCurrentSession().save(userRole);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<UserUtils> listUsers() {
        Query query = sessionFactory.getCurrentSession().createSQLQuery("SELECT us.id, p.surname, p.first_name, p.other_names, us.username FROM person p JOIN user us ON p.id = us.person_id  WHERE p.voided = 0");
        List list = query.list();
        Iterator iter = list.iterator();
        List<UserUtils> userList = new ArrayList<UserUtils>();
        while(iter.hasNext()) {
            Object[] row = (Object[])iter.next();
            UserUtils p = new UserUtils();
            p.setId((Integer) row[0]);
            String names = (row[1] == null ? "" : String.valueOf(row[1])) + " " + (row[2] == null ? "" : String.valueOf(row[2])) + " " + (row[3] == null ? "" : String.valueOf(row[3]));
            p.setNames(names);
            p.setUsername((String) row[4]);
            userList.add(p);
        }
        return userList;
    }

    @Override
    public AdminRequestWrapper getUserInfo(Integer userId) {
        Query query = sessionFactory.getCurrentSession().createSQLQuery("SELECT u.id as user_id, u.username, u.password,  p.id as personId, p.surname, p.first_name, p.other_names , pa.id as pa_id, pa.email_address, pa.tel_no1 FROM user u JOIN person p on u.person_id = p.id JOIN person_address pa ON p.id = pa.person_id WHERE u.id = " + userId);
        List list = query.list();

        Iterator iter = list.iterator();
        AdminRequestWrapper requestWrapper = new AdminRequestWrapper();
        while(iter.hasNext()) {
            Object[] row = (Object[])iter.next();
            User user = new User();
            user.setId((Integer)row[0]);
            user.setUsername((String)row[1]);
            user.setPassword((String)row[2]);

            Query query1 = sessionFactory.getCurrentSession().createQuery(" from UserRole WHERE userId =  " + user.getId());
            List userRoles = query1.list();
            Iterator iterator = userRoles.iterator();
            List userRoleList = new ArrayList();
            while (iterator.hasNext()) {
                UserRole userRole = (UserRole)iterator.next();
                UserRole ur = new UserRole();
                ur.setRoleId(userRole.getRoleId());
                userRoleList.add(ur);
            }
            user.setUserRolesById(userRoleList);


            requestWrapper.setUser(user);

            Person person = new Person();
            person.setId((Integer)row[3]);
            person.setSurname((String) row[4]);
            person.setFirstName((String) row[5]);
            person.setOtherNames((String) row[6]);
            requestWrapper.setPerson(person);

            PersonAddress personAddress = new PersonAddress();
            personAddress.setId((Integer)row[7]);
            personAddress.setEmailAddress((String) row[8]);
            personAddress.setTelNo1((String)row[9]);
            requestWrapper.setPersonAddress(personAddress);
        }
        return requestWrapper;
    }

    @Override
    public void deleteUser(Integer userId, Integer loggedInUserId) {
        User user = (User)sessionFactory.getCurrentSession().get(User.class, userId);
        user.setVoided((byte)1);
        user.setVoidedBy(loggedInUserId);
        user.setVoidedOn(new Timestamp(new Date().getTime()));
        sessionFactory.getCurrentSession().update(user);
    }

    @Override
    public void updateUser(User user) {
        User us = (User)sessionFactory.getCurrentSession().get(User.class, user.getId());
        if(us != null) {
            us.setUsername(user.getUsername());
            us.setPassword(user.getPassword());
            us.setUpdatedOn(new Timestamp(new Date().getTime()));
            us.setUpdatedBy(user.getUpdatedBy());
            updateUserObj(us);
        }
        deleteUserRole(user.getId());
        Collection userRoles = user.getUserRolesById();
        Iterator iterator = userRoles.iterator();
        while(iterator.hasNext()) {
            UserRole r = (UserRole)iterator.next();
            UserRole userRole = new UserRole();
            userRole.setRoleId(r.getRoleId());
            userRole.setUserId(user.getId());
            sessionFactory.getCurrentSession().save(userRole);
        }
    }

    @Override
    public void deletePrivilege(Integer privilegeId, Integer userId) {
        Privilege in = (Privilege)sessionFactory.getCurrentSession().get(Privilege.class, privilegeId);
        if(in != null) {
            in.setVoided((byte)1);
            in.setVoidedOn(new Timestamp(new Date().getTime()));
            in.setVoidedBy(userId);
            sessionFactory.getCurrentSession().update(in);
        }
    }

    @Override
    public void deleteRole(Integer roleId, Integer userId) {
        Role role = (Role)sessionFactory.getCurrentSession().get(Role.class, roleId);
        if(role != null) {
            role.setVoided((byte)1);
            role.setVoidedOn(new Timestamp(new Date().getTime()));
            role.setVoidedBy(userId);
            sessionFactory.getCurrentSession().update(role);
            deleteRoleUser(roleId);
        }
    }

    @Override
    public void deleteRolePrivilege(Integer roleId) {
        Query query = sessionFactory.getCurrentSession().createQuery("DELETE from RolePrivilege WHERE roleId = " + roleId);
        query.executeUpdate();
    }

    @Override
    public void deleteUserRole(Integer userId) {
        Query query = sessionFactory.getCurrentSession().createQuery("DELETE from UserRole WHERE userId = " + userId);
        query.executeUpdate();
    }

    @Override
    public User getUser(Integer userId) {
        User user = (User)sessionFactory.getCurrentSession().get(User.class, userId);
        return user;
    }

    @Override
    public void updateUserObj(User user) {
        sessionFactory.getCurrentSession().update(user);
    }

    @Override
    public List<Property> loadFacilityInformation() {
        Query query = sessionFactory.getCurrentSession().createQuery(" from Property WHERE key = 'facility_name' OR key = 'facility_district' OR key = 'facility_code' OR key ='Max_Appointments_Per_Day'");
        List<Property> properties = query.list();
        List<Property> propertyList = new ArrayList<Property>();
        Iterator iterator = properties.iterator();
        while(iterator.hasNext()) {
            Property prop = (Property)iterator.next();
            Property p = new Property();
            p.setKey(prop.getKey());
            p.setValue(prop.getValue());
            p.setId(prop.getId());
            propertyList.add(p);
        }
        return propertyList;
    }

    @Override
    public void saveFacilityInformation(Property property, Integer userId) {
        if(property.getId() <= 0) {
            property.setCreatedOn(new Timestamp(new Date().getTime()));
            property.setCreatedBy(userId);
            property.setUuid(UUID.randomUUID().toString());

            sessionFactory.getCurrentSession().save(property);
        } else {
            Property p = (Property)sessionFactory.getCurrentSession().get(Property.class, property.getId());
            p.setUpdatedBy(userId);
            p.setKey(property.getKey());
            p.setValue(property.getValue());
            p.setUpdatedOn(new Timestamp(new Date().getTime()));
            sessionFactory.getCurrentSession().update(p);
        }
    }

    @Override
    public void deleteRoleUser(Integer roleId) {
        if(roleId != null) {
            Query query = sessionFactory.getCurrentSession().createQuery("DELETE from UserRole WHERE roleId = " + roleId);
            query.executeUpdate();
        }
    }
}
