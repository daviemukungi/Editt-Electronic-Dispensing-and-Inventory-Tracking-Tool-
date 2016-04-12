package org.msh.fdt.dto;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by kenny on 4/2/14.
 */

public class ServiceType {
    private int id;
    private String name;
    private String service_area;
    private String description;
    private int createdBy;


    public String getService_area() {
        return service_area;
    }

    public void setService_area(String service_area) {
        this.service_area = service_area;
    }


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

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }
}
