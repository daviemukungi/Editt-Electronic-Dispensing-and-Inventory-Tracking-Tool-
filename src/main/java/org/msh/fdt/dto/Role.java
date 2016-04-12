package org.msh.fdt.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kenny on 5/29/14.
 */
public class Role {

    private int id;
    private String name;
    private String description;
    private List<Integer> privileges;

    private Integer createdBy;
    private Integer updatedBy;

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

    public List<Integer> getPrivileges() {
        if(privileges == null)
            privileges = new ArrayList<Integer>();
        return privileges;
    }

    public void setPrivileges(List<Integer> privileges) {
        this.privileges = privileges;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Integer getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Integer updatedBy) {
        this.updatedBy = updatedBy;
    }
}
