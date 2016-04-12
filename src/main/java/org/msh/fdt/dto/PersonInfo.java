package org.msh.fdt.dto;

import org.msh.fdt.model.User;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collection;

/**
 * Created by kenny on 4/18/14.
 */

public class PersonInfo {
    private int id;
    private String surname;
    private String firstName;
    private String otherNames;
    private Integer patientId;
    private String gender;
    private Integer patientStatusId;
    private BigDecimal height;
    private Timestamp dateOfBirth;
    private String[] serviceTypeId;
    private String identifier;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }


    public String getOtherNames() {
        return otherNames;
    }

    public void setOtherNames(String otherNames) {
        this.otherNames = otherNames;
    }


    public Integer getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Timestamp getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Timestamp dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Integer getPatientStatusId() {
        return patientStatusId;
    }

    public void setPatientStatusId(Integer patientStatusId) {
        this.patientStatusId = patientStatusId;
    }

    public BigDecimal getHeight() {
        return height;
    }

    public void setHeight(BigDecimal height) {
        this.height = height;
    }

    public String[] getServiceTypeId() {
        return serviceTypeId;
    }

    public void setServiceTypeId(String[] serviceTypeId) {
        this.serviceTypeId = serviceTypeId;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
}
