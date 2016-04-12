package org.msh.fdt.controller;

import org.msh.fdt.model.Property;
import org.msh.fdt.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by kenny on 11/10/14.
 */
@Controller
@RequestMapping("/settings")
public class IndexController {

    @Autowired
    private AdminService adminService;
    @RequestMapping( method = RequestMethod.GET)
    public @ResponseBody List<Property> index(HttpServletRequest request) {
        List<Property> facilityInformation = adminService.getFacilityInformation();
        HttpSession session = request.getSession();
        for(int i = 0; i < facilityInformation.size(); i++) {
            Property p = facilityInformation.get(i);
            session.setAttribute(p.getKey(), p.getValue());
        }

        return facilityInformation;
    }
}
