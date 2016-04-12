package org.msh.fdt.util;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.msh.fdt.dto.*;
import org.msh.fdt.model.*;
import org.msh.fdt.model.PersonAddress;
import org.msh.fdt.dto.Role;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kenny on 4/11/14.
 */
public class AdminRequestWrapper {


    public Privilege getPrivilege() {
        return privilege;
    }

    public void setPrivilege(Privilege privilege) {
        this.privilege = privilege;
    }

    @JsonProperty("Result")
    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @JsonProperty("Records")
    public List<?> getRecords() {
        if(records == null)
            records = new ArrayList<Object>();
        return records;
    }

    public org.msh.fdt.dto.Role getRole() {
        return role;
    }

    public void setRole(org.msh.fdt.dto.Role role) {
        this.role = role;
    }

    public List<Integer> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(List<Integer> privileges) {
        this.privileges = privileges;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public PersonAddress getPersonAddress() {
        return personAddress;
    }

    public void setPersonAddress(PersonAddress personAddress) {
        this.personAddress = personAddress;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setRecords(List<?> records) {
        this.records = records;
    }

    public List<Property> getProperties() {
        return properties;
    }

    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }

    private Privilege privilege;

    private String result;

    private List<?> records;

    private Person person;

    private PersonAddress personAddress;

    private User user;

    private List<Integer> privileges;

    private Role role;

    List<Property> properties;
}
