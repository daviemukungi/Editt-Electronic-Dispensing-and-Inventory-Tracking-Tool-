package org.msh.fdt.dto;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by kenny on 4/2/14.
 */

public class Account {
    private int id;
    private String name;
    private String description;
    private int accountTypeId;
    private String Is_satellite;
    private String Is_bulkstore;
    private String Cannot_dispense;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public int getAccountTypeId() {
        return accountTypeId;
    }

    public void setAccountTypeId(int accountTypeId) {
        this.accountTypeId = accountTypeId;
    }
    public String getIs_bulkstore() {
        return Is_bulkstore;
    }

    public void setIs_bulkstore(String is_bulkstore) {
        this.Is_bulkstore = is_bulkstore;
    }

    public String getIs_satellite() {
        return Is_satellite;
    }

    public void setIs_satellite(String is_satellite) {
        this.Is_satellite = is_satellite;
    }

    public String getCannot_dispense() {
        return Cannot_dispense;
    }

    public void setCannot_dispense(String cannot_dispense) {
        this.Cannot_dispense = cannot_dispense;
    }


}
