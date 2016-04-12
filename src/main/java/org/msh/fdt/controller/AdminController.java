package org.msh.fdt.controller;

import org.msh.fdt.dto.PersonInfo;
import org.msh.fdt.model.Privilege;
import org.msh.fdt.model.Property;
import org.msh.fdt.model.Role;
import org.msh.fdt.model.User;
import org.msh.fdt.service.AdminService;
import org.msh.fdt.service.PersonService;
import org.msh.fdt.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by kenny on 4/11/14.
 * This class is the entry point for the admin interface.
 * It contains endpoints for listing, creating, updating and deleting
 *  - Privileges
 *  - Users
 *  - Roles
 *  - Facility information
 */
@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController {
    @Autowired
    private AdminService adminService;

    @Autowired
    private PersonService personService;

    /**
     * Privilege functions  List/Create/Update
     */
    @RequestMapping(value = "/json/listPrivileges", method = RequestMethod.POST)
    public
    @ResponseBody
    AdminRequestWrapper listPrivileges() {
        AdminRequestWrapper requestWrapper = new AdminRequestWrapper();
        try {
            List<Privilege> privilegeList = adminService.privilegeList();
            requestWrapper.setResult("OK");
            requestWrapper.setRecords(privilegeList);
            return requestWrapper;
        } catch (Exception e) {
            requestWrapper.setResult("Error");
            e.printStackTrace();
            return requestWrapper;
        }
    }

    @RequestMapping(value = "/json/createPrivilege", method = RequestMethod.POST)
    public
    @ResponseBody
    JTableResponse createPrivilege(@RequestBody AdminRequestWrapper adminRequestWrapper) {
        JTableResponse response = new JTableResponse();
        try {
            adminRequestWrapper.getPrivilege().setCreatedOn(new Timestamp(new Date().getTime()));
            adminRequestWrapper.getPrivilege().setUuid(UUID.randomUUID().toString());
            Integer id = adminService.createPrivilege(adminRequestWrapper.getPrivilege());
            adminRequestWrapper.getPrivilege().setId(id);
            response.setResult("OK");
            response.setRecord(adminRequestWrapper.getPrivilege());
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.setResult("ERROR");
            return response;
        }
    }

    @RequestMapping(value = "/json/updatePrivilege", method = RequestMethod.POST)
    public
    @ResponseBody
    JTableResponse updatePrivilege(@RequestBody AdminRequestWrapper requestWrapper) {
        JTableResponse response = new JTableResponse();
        try {
            requestWrapper.getPrivilege().setUpdatedOn(new Timestamp(new Date().getTime()));
            adminService.updatePrivilege(requestWrapper.getPrivilege());
            response.setResult("OK");
            response.setRecord(requestWrapper.getPrivilege());
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.setResult("ERROR");
            return response;
        }
    }

    @RequestMapping(value = "/json/deletePrivilege/{id}/{userId}", method = RequestMethod.POST)
    public
    @ResponseBody
    JTableResponse deletePrivilege(@PathVariable("id") Integer id, @PathVariable("userId") Integer userId) {
        JTableResponse response = new JTableResponse();
        try {
            adminService.deletePrivilege(id, userId);
            response.setResult("OK");
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.setResult("ERROR");
            return response;
        }
    }

    /**
     * Creates a new User.
     *
     * @param requestWrapper
     * @return
     */
    @RequestMapping(value = "/json/createUser", method = RequestMethod.POST)
    public
    @ResponseBody
    String createUser(@RequestBody AdminRequestWrapper requestWrapper) {
        try {
            requestWrapper.getPerson().setCreatedOn(new Timestamp(new Date().getTime()));
            requestWrapper.getPerson().setUuid(UUID.randomUUID().toString());
            requestWrapper.getPersonAddress().setCreatedOn(new Timestamp(new Date().getTime()));
            requestWrapper.getPersonAddress().setUuid(UUID.randomUUID().toString());

            PersonInfo person = personService.savePerson(requestWrapper.getPerson(), null, requestWrapper.getPersonAddress(), "USER", null, null);

            requestWrapper.getUser().setCreatedOn(new Timestamp(new Date().getTime()));
            requestWrapper.getUser().setUuid(UUID.randomUUID().toString());
            requestWrapper.getUser().setPersonId(person.getId());
            adminService.saveUser(requestWrapper.getUser());
            return "saved";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    /**
     * Returns a LIst of users
     *
     * @return
     */
    @RequestMapping(value = "/json/listUsers", method = RequestMethod.POST)
    public
    @ResponseBody
    List<UserUtils> listUsers() {
        try {
            List<UserUtils> userList = adminService.listUsers();
            return userList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Gets the User information for the user with the give ID.
     *
     * @param userId
     * @return
     */
    @RequestMapping(value = "/json/getUser/{userId}", method = RequestMethod.POST)
    public
    @ResponseBody
    AdminRequestWrapper getUser(@PathVariable("userId") Integer userId) {
        try {
            AdminRequestWrapper requestWrapper = adminService.getUser(userId);
            return requestWrapper;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Deletes a user from the database
     *
     * @param userId         The ID of the @link{org.msh.fdt.model.User} being deleted
     * @param loggedInUserId The ID of the @link{User} who has logged into the system.
     * @return
     */
    @RequestMapping(value = "/json/deleteUser/{userId}/{loggedInUserId}", method = RequestMethod.POST)
    public
    @ResponseBody
    String deleteUser(@PathVariable("userId") Integer userId, @PathVariable("loggedInUserId") Integer loggedInUserId) {
        try {
            adminService.deleteUser(userId, loggedInUserId);
            return "deleted";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    @RequestMapping(value = "/json/updateUser", method = RequestMethod.POST)
    public
    @ResponseBody
    String updateUser(@RequestBody AdminRequestWrapper requestWrapper) {
        try {

            PersonInfo pi = personService.updatePerson(requestWrapper.getPerson(), null, requestWrapper.getPersonAddress(), "USER", null, null);
            adminService.updateUser(requestWrapper.getUser());
            return "saved";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    /**
     * Roles functions  List/Create/Update
     */
    @RequestMapping(value = "/json/listRoles", method = RequestMethod.POST)
    public
    @ResponseBody
    AdminRequestWrapper listRoles() {
        AdminRequestWrapper requestWrapper = new AdminRequestWrapper();
        try {
            List<org.msh.fdt.dto.Role> roleList = adminService.roleList();
            requestWrapper.setResult("OK");
            requestWrapper.setRecords(roleList);
            return requestWrapper;
        } catch (Exception e) {
            requestWrapper.setResult("Error");
            e.printStackTrace();
            return requestWrapper;
        }
    }

    /**
     * This function creates a new @link{Role}
     *
     * @param adminRequestWrapper This Object contains the new @link{Role} which is being created.
     * @return
     */
    @RequestMapping(value = "/json/createRole", method = RequestMethod.POST)
    public
    @ResponseBody
    JTableResponse createRole(@RequestBody AdminRequestWrapper adminRequestWrapper) {
        JTableResponse response = new JTableResponse();
        try {
            Role role = new Role();
            role.setName(adminRequestWrapper.getRole().getName());
            role.setDescription(adminRequestWrapper.getRole().getDescription());
            role.setCreatedBy(adminRequestWrapper.getRole().getCreatedBy());
            role.setCreatedOn(new Timestamp(new Date().getTime()));
            role.setUuid(UUID.randomUUID().toString());
            Integer id = adminService.createRole(role, adminRequestWrapper.getRole().getPrivileges());
            adminRequestWrapper.getRole().setId(id);
            response.setResult("OK");
            response.setRecord(adminRequestWrapper.getRole());
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.setResult("ERROR");
            return response;
        }
    }

    /**
     * This function updates a @link{Role}
     *
     * @param requestWrapper This object holds the @link{Role}  and the @link{Privilege} which will be updated.
     * @return
     */
    @RequestMapping(value = "/json/updateRole", method = RequestMethod.POST)
    public
    @ResponseBody
    JTableResponse updateRole(@RequestBody AdminRequestWrapper requestWrapper) {
        JTableResponse response = new JTableResponse();
        try {

            adminService.updateRole(requestWrapper.getRole(), requestWrapper.getRole().getPrivileges());
            response.setResult("OK");
            response.setRecord(requestWrapper.getPrivilege());
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.setResult("ERROR");
            return response;
        }
    }

    /**
     * This function is used to delete a role from the database
     *
     * @param id     The ID of the @link{Role} being deleted
     * @param userId The ID of the @link{org.msh.fdt.model.User} who performed the delete
     * @return
     */
    @RequestMapping(value = "/json/deleteRole/{id}/{userId}", method = RequestMethod.POST)
    public
    @ResponseBody
    JTableResponse deleteRole(@PathVariable("id") Integer id, @PathVariable("userId") Integer userId) {
        JTableResponse response = new JTableResponse();
        try {
            adminService.deleteRole(id, userId);
            response.setResult("OK");
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.setResult("ERROR");
            return response;
        }
    }

    /**
     * Updates the password for the given user.
     *
     * @param user : The user id for the user
     * @return
     */
    @RequestMapping(value = "/json/updatePassword", method = RequestMethod.POST)
    public
    @ResponseBody
    String updatePassword(@RequestBody UserUtils user) {
        try {
            boolean updated = adminService.updatePassword(user.getPassword(), user.getNewPassword(), user.getId());
            if (updated)
                return "updated";
            else
                return "error";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    @RequestMapping(value = "/json/loadFacilityInfo", method = RequestMethod.POST)
    public
    @ResponseBody
    List<Property> loadFacilityInformation() {
        try {
            List<Property> properties = adminService.getFacilityInformation();
            return properties;
        } catch (Exception e) {
            return new ArrayList<Property>();
        }
    }

    @RequestMapping(value = "/json/saveFacilityInfo/{userId}", method = RequestMethod.POST)
    public
    @ResponseBody
    AdminRequestWrapper saveFacilityInformation(HttpServletRequest request, @RequestBody AdminRequestWrapper wrapper, @PathVariable("userId") Integer userId) {
        try {
            adminService.updateFacilityInformation(wrapper.getProperties(), userId);
            List<Property> properties = adminService.getFacilityInformation();
            HttpSession session = request.getSession();
            for (int i = 0; i < properties.size(); i++) {
                Property p = properties.get(i);
                session.setAttribute(p.getKey(), p.getValue());
            }
            wrapper.setProperties(properties);
            return wrapper;
        } catch (Exception e) {
            e.printStackTrace();
            return new AdminRequestWrapper();
        }


    }

    /***************************************************/
    @RequestMapping(value = "/json/databaseInfo", method = RequestMethod.POST)
    public
    @ResponseBody
    List<Property> loadDatabaseInformation() {
        try {
            List<Property> properties = adminService.getDatabaseInformation();
            return properties;
        } catch (Exception e) {
            return new ArrayList<Property>();
        }
    }

    @RequestMapping(value = "/json/saveDatabaseInfo/{userId}", method = RequestMethod.POST)
    public
    @ResponseBody
    AdminRequestWrapper saveDatabaseInformation(HttpServletRequest request, @RequestBody AdminRequestWrapper wrapper, @PathVariable("userId") Integer userId) {
        try {
            adminService.updateDatabaseInformation(wrapper.getProperties(), userId);
            List<Property> properties = adminService.getDatabaseInformation();
            HttpSession session = request.getSession();
            for (int i = 0; i < properties.size(); i++) {
                Property p = properties.get(i);
                session.setAttribute(p.getKey(), p.getValue());
            }
            wrapper.setProperties(properties);
            return wrapper;
        } catch (Exception e) {
            e.printStackTrace();
            return new AdminRequestWrapper();
        }
        /***************************************************/

    }
}
