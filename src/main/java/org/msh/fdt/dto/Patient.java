package org.msh.fdt.dto;

import org.msh.fdt.model.*;
import org.msh.fdt.model.PatientSource;
import org.msh.fdt.model.ServiceType;
import org.msh.fdt.model.SupportingOrganization;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Collection;

/**
 * Created by kenny on 4/18/14.
 */

public class Patient {
    private int id;
    private int personId;
    private Timestamp enrollmentDate;
    private Timestamp therapyStartDate;
    private Integer patientSourceId;
    private Integer serviceTypeId;
    private Date serviceStartDate;
    private Integer supportingOrganizationId;
    private String drugAllergies;
    private String chronicIllnesses;
    private Byte smoker;
    private Byte drinker;
    private Integer fromFacilityId;
    private String legacyPk;
    private String uuid;
    private int createdBy;
    private Timestamp createdOn;
    private Integer updatedBy;
    private Timestamp updatedOn;
    private byte voided;
    private Integer voidedBy;
    private Timestamp voidedOn;
    private String voidReason;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public Timestamp getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setEnrollmentDate(Timestamp enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }

    public Timestamp getTherapyStartDate() {
        return therapyStartDate;
    }

    public void setTherapyStartDate(Timestamp therapyStartDate) {
        this.therapyStartDate = therapyStartDate;
    }

    public Integer getPatientSourceId() {
        return patientSourceId;
    }

    public void setPatientSourceId(Integer patientSourceId) {
        this.patientSourceId = patientSourceId;
    }

    public Integer getServiceTypeId() {
        return serviceTypeId;
    }

    public void setServiceTypeId(Integer serviceTypeId) {
        this.serviceTypeId = serviceTypeId;
    }

    public Date getServiceStartDate() {
        return serviceStartDate;
    }

    public void setServiceStartDate(Date serviceStartDate) {
        this.serviceStartDate = serviceStartDate;
    }

    public Integer getSupportingOrganizationId() {
        return supportingOrganizationId;
    }

    public void setSupportingOrganizationId(Integer supportingOrganizationId) {
        this.supportingOrganizationId = supportingOrganizationId;
    }

    public String getDrugAllergies() {
        return drugAllergies;
    }

    public void setDrugAllergies(String drugAllergies) {
        this.drugAllergies = drugAllergies;
    }

    public String getChronicIllnesses() {
        return chronicIllnesses;
    }

    public void setChronicIllnesses(String chronicIllnesses) {
        this.chronicIllnesses = chronicIllnesses;
    }

    public Byte getSmoker() {
        return smoker;
    }

    public void setSmoker(Byte smoker) {
        this.smoker = smoker;
    }

    public Byte getDrinker() {
        return drinker;
    }

    public void setDrinker(Byte drinker) {
        this.drinker = drinker;
    }

    public Integer getFromFacilityId() {
        return fromFacilityId;
    }

    public void setFromFacilityId(Integer fromFacilityId) {
        this.fromFacilityId = fromFacilityId;
    }

    public String getLegacyPk() {
        return legacyPk;
    }

    public void setLegacyPk(String legacyPk) {
        this.legacyPk = legacyPk;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public Timestamp getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Timestamp createdOn) {
        this.createdOn = createdOn;
    }

    public Integer getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Integer updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Timestamp getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Timestamp updatedOn) {
        this.updatedOn = updatedOn;
    }

    public byte getVoided() {
        return voided;
    }

    public void setVoided(byte voided) {
        this.voided = voided;
    }

    public Integer getVoidedBy() {
        return voidedBy;
    }

    public void setVoidedBy(Integer voidedBy) {
        this.voidedBy = voidedBy;
    }

    public Timestamp getVoidedOn() {
        return voidedOn;
    }

    public void setVoidedOn(Timestamp voidedOn) {
        this.voidedOn = voidedOn;
    }

    public String getVoidReason() {
        return voidReason;
    }

    public void setVoidReason(String voidReason) {
        this.voidReason = voidReason;
    }

}
