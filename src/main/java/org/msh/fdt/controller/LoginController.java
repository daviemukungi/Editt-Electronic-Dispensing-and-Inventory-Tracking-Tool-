package org.msh.fdt.controller;

import org.msh.fdt.model.Account;
import org.msh.fdt.model.Property;
import org.msh.fdt.model.User;
import org.msh.fdt.service.*;
import org.msh.fdt.util.JTableResponse;
import org.msh.fdt.util.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by kenny on 5/27/14.
 * This is the entry point for the Login screen.
 * It has the various endpoints for the login screen
 * It performs
 *      - User Login
 *      - Getting the secret question for a user
 *      - Validating a secret question
 */
@Controller
@RequestMapping("/login")
public class LoginController extends BaseController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private AdminService adminService;
 @Autowired
    private PersonService personService;

    @Autowired
    private ReferenceService referenceService;

    @Autowired
    private ReportsService reportsService;

    @RequestMapping(value = "/json/authenticateUser", method = RequestMethod.POST)
    public @ResponseBody String authenticateUser(@RequestBody UserUtils userUtils, HttpServletRequest request) {
        try {
            User user = loginService.getUser(userUtils.getUsername(), userUtils.getPassword());
            if(user != null) {
                HttpSession session = request.getSession();
                session.setAttribute("loggedin", true);
                session.setAttribute("account", userUtils.getStore());

                Account acc = referenceService.getAccount(userUtils.getStore());
                session.setAttribute("account_name", acc.getName());
                session.setAttribute("Is_bulkstore", acc.getIs_bulkstore());
                session.setAttribute("userId", user.getId());
                session.setAttribute("username", user.getUsername());
                List<Property> facilityInformation = adminService.getFacilityInformation();
                for(int i = 0; i < facilityInformation.size(); i++) {
                    Property p = facilityInformation.get(i);
                    session.setAttribute(p.getKey(), p.getValue());
                }

                List<Object[]> objects = loginService.getPrivileges(user.getId());
                for(int i = 0; i < objects.size(); i++) {
                    Object[] obj = objects.get(i);
                    session.setAttribute(String.valueOf(obj[0]), true);
                }
                personService.updateLostToFollowupPatients();
                personService.updatePEPPatients();
                personService.updatePMCTPatients();
                return "loggedin";

            } else {
                return "error";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }


    @RequestMapping(value = "/json/authenticateAdmin", method = RequestMethod.POST)
    public @ResponseBody String authenticateAdmin(@RequestBody UserUtils userUtils, HttpServletRequest request) {
        try {
            User user = loginService.getUser(userUtils.getUsername(), userUtils.getPassword());
            String prev="";
            String a = "";
            if(user != null) {


                List<Object[]> objects = loginService.getPrivileges(user.getId());
                for(int i = 0; i < objects.size(); i++) {
                    Object[] obj = objects.get(i);
                    prev = String.valueOf(obj[0]);

                    if (prev.equals("mod_admin")) break;

                }

                if (prev.equals("mod_admin")){
                    return "loggedin";

                }
                else {
                    return "notauthenticated";
                }



            } else {
                return "error";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    @RequestMapping(value = "/json/logoutUser", method = RequestMethod.POST)
    public @ResponseBody String logoutUser(HttpServletRequest request) {
        request.getSession().invalidate();
        return "loggedout";
    }

    /**
     *  Gets the Secret Question for the given user
     * @param
     * @return
     */
    @RequestMapping(value = "/json/getSecretQuestion", method = RequestMethod.POST)
    public @ResponseBody Object getSecretQuestion(@RequestBody User us) {
        try {
            String username = us.getUsername();
            System.out.println("Username is " + username);
            User user = loginService.getUser(username);
            return user;
        } catch (Exception e) {
            return new JTableResponse("ERROR", "Error fetching user information");
        }
    }

    /**
     * Validates the answer provided
     * @param user
     * @return
     */
    @RequestMapping(value = "/json/validateSecretQuestion", method = RequestMethod.POST)
    public @ResponseBody JTableResponse validateSecretQuestion(@RequestBody User user) {
        try {
            boolean validAnswer = loginService.validateSecretQuestion(user);
            if(validAnswer)
                return new JTableResponse("OK", "Question validated.");
            else
                return new JTableResponse("ERROR", "Error resetting password, make sure the answer is correct.");
        } catch (Exception e){
            e.printStackTrace();
            return new JTableResponse("ERROR", "Error fetching user information");
        }
    }
}
