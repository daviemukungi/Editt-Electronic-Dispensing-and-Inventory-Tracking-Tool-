package org.msh.fdt.model;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by kenny on 2/12/15.
 */
@Entity
@Table(name = "patient", schema = "", catalog = "fdt")
public class Patient {
    private int id;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @javax.persistence.Column(name = "id", nullable = false, insertable = true, updatable = true)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int personId;

    @Basic
    @javax.persistence.Column(name = "person_id", nullable = false, insertable = true, updatable = true)
    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    private Timestamp enrollmentDate;

    @Basic
    @javax.persistence.Column(name = "enrollment_date", nullable = true, insertable = true, updatable = true)
    public Timestamp getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setEnrollmentDate(Timestamp enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }

    private Timestamp therapyStartDate;

    @Basic
    @javax.persistence.Column(name = "therapy_start_date", nullable = true, insertable = true, updatable = true)
    public Timestamp getTherapyStartDate() {
        return therapyStartDate;
    }

    public void setTherapyStartDate(Timestamp therapyStartDate) {
        this.therapyStartDate = therapyStartDate;
    }

    private Integer patientSourceId;

    @Basic
    @javax.persistence.Column(name = "patient_source_id", nullable = true, insertable = true, updatable = true)
    public Integer getPatientSourceId() {
        return patientSourceId;
    }

    public void setPatientSourceId(Integer patientSourceId) {
        this.patientSourceId = patientSourceId;
    }

    private Integer supportingOrganizationId;

    @Basic
    @javax.persistence.Column(name = "supporting_organization_id", nullable = true, insertable = true, updatable = true)
    public Integer getSupportingOrganizationId() {
        return supportingOrganizationId;
    }

    public void setSupportingOrganizationId(Integer supportingOrganizationId) {
        this.supportingOrganizationId = supportingOrganizationId;
    }

    private Integer startRegimenId;

    @Basic
    @javax.persistence.Column(name = "start_regimen_id", nullable = true, insertable = true, updatable = true)
    public Integer getStartRegimenId() {
        return startRegimenId;
    }

    public void setStartRegimenId(Integer startRegimenId) {
        this.startRegimenId = startRegimenId;
    }

    private String drugAllergies;

    @Basic
    @javax.persistence.Column(name = "drug_allergies", nullable = true, insertable = true, updatable = true, length = 255)
    public String getDrugAllergies() {
        return drugAllergies;
    }

    public void setDrugAllergies(String drugAllergies) {
        this.drugAllergies = drugAllergies;
    }

    private String chronicIllnesses;

    @Basic
    @javax.persistence.Column(name = "chronic_illnesses", nullable = true, insertable = true, updatable = true, length = 255)
    public String getChronicIllnesses() {
        return chronicIllnesses;
    }

    public void setChronicIllnesses(String chronicIllnesses) {
        this.chronicIllnesses = chronicIllnesses;
    }

    private Byte smoker;

    @Basic
    @javax.persistence.Column(name = "smoker", nullable = true, insertable = true, updatable = true)
    public Byte getSmoker() {
        return smoker;
    }

    public void setSmoker(Byte smoker) {
        this.smoker = smoker;
    }

    private Byte drinker;

    @Basic
    @javax.persistence.Column(name = "drinker", nullable = true, insertable = true, updatable = true)
    public Byte getDrinker() {
        return drinker;
    }

    public void setDrinker(Byte drinker) {
        this.drinker = drinker;
    }

    private Integer fromFacilityId;

    @Basic
    @javax.persistence.Column(name = "from_facility_id", nullable = true, insertable = true, updatable = true)
    public Integer getFromFacilityId() {
        return fromFacilityId;
    }

    public void setFromFacilityId(Integer fromFacilityId) {
        this.fromFacilityId = fromFacilityId;
    }

    private Integer patientStatusId;

    @Basic
    @javax.persistence.Column(name = "patient_status_id", nullable = true, insertable = true, updatable = true)
    public Integer getPatientStatusId() {
        return patientStatusId;
    }

    public void setPatientStatusId(Integer patientStatusId) {
        this.patientStatusId = patientStatusId;
    }

    private String legacyPk;

    @Basic
    @javax.persistence.Column(name = "legacy_pk", nullable = true, insertable = true, updatable = true, length = 45)
    public String getLegacyPk() {
        return legacyPk;
    }

    public void setLegacyPk(String legacyPk) {
        this.legacyPk = legacyPk;
    }

    private String uuid;

    @Basic
    @javax.persistence.Column(name = "uuid", nullable = false, insertable = true, updatable = true, length = 36)
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    private int createdBy;

    @Basic
    @javax.persistence.Column(name = "created_by", nullable = false, insertable = true, updatable = true)
    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    private Timestamp createdOn;

    @Basic
    @javax.persistence.Column(name = "created_on", nullable = false, insertable = true, updatable = true)
    public Timestamp getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Timestamp createdOn) {
        this.createdOn = createdOn;
    }

    private Integer updatedBy;

    @Basic
    @javax.persistence.Column(name = "updated_by", nullable = true, insertable = true, updatable = true)
    public Integer getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Integer updatedBy) {
        this.updatedBy = updatedBy;
    }

    private Timestamp updatedOn;

    @Basic
    @javax.persistence.Column(name = "updated_on", nullable = true, insertable = true, updatable = true)
    public Timestamp getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Timestamp updatedOn) {
        this.updatedOn = updatedOn;
    }

    private byte voided;

    @Basic
    @javax.persistence.Column(name = "voided", nullable = false, insertable = true, updatable = true)
    public byte getVoided() {
        return voided;
    }

    public void setVoided(byte voided) {
        this.voided = voided;
    }

    private Integer voidedBy;

    @Basic
    @javax.persistence.Column(name = "voided_by", nullable = true, insertable = true, updatable = true)
    public Integer getVoidedBy() {
        return voidedBy;
    }

    public void setVoidedBy(Integer voidedBy) {
        this.voidedBy = voidedBy;
    }

    private Timestamp voidedOn;

    @Basic
    @javax.persistence.Column(name = "voided_on", nullable = true, insertable = true, updatable = true)
    public Timestamp getVoidedOn() {
        return voidedOn;
    }

    public void setVoidedOn(Timestamp voidedOn) {
        this.voidedOn = voidedOn;
    }

    private String voidReason;

    @Basic
    @javax.persistence.Column(name = "void_reason", nullable = true, insertable = true, updatable = true, length = 255)
    public String getVoidReason() {
        return voidReason;
    }

    public void setVoidReason(String voidReason) {
        this.voidReason = voidReason;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Patient patient = (Patient) o;

        if (createdBy != patient.createdBy) return false;
        if (id != patient.id) return false;
        if (personId != patient.personId) return false;
        if (voided != patient.voided) return false;
        if (chronicIllnesses != null ? !chronicIllnesses.equals(patient.chronicIllnesses) : patient.chronicIllnesses != null)
            return false;
        if (createdOn != null ? !createdOn.equals(patient.createdOn) : patient.createdOn != null) return false;
        if (drinker != null ? !drinker.equals(patient.drinker) : patient.drinker != null) return false;
        if (drugAllergies != null ? !drugAllergies.equals(patient.drugAllergies) : patient.drugAllergies != null)
            return false;
        if (enrollmentDate != null ? !enrollmentDate.equals(patient.enrollmentDate) : patient.enrollmentDate != null)
            return false;
        if (fromFacilityId != null ? !fromFacilityId.equals(patient.fromFacilityId) : patient.fromFacilityId != null)
            return false;
        if (legacyPk != null ? !legacyPk.equals(patient.legacyPk) : patient.legacyPk != null) return false;
        if (patientSourceId != null ? !patientSourceId.equals(patient.patientSourceId) : patient.patientSourceId != null)
            return false;
        if (patientStatusId != null ? !patientStatusId.equals(patient.patientStatusId) : patient.patientStatusId != null)
            return false;
        if (smoker != null ? !smoker.equals(patient.smoker) : patient.smoker != null) return false;
        if (startRegimenId != null ? !startRegimenId.equals(patient.startRegimenId) : patient.startRegimenId != null)
            return false;
        if (supportingOrganizationId != null ? !supportingOrganizationId.equals(patient.supportingOrganizationId) : patient.supportingOrganizationId != null)
            return false;
        if (therapyStartDate != null ? !therapyStartDate.equals(patient.therapyStartDate) : patient.therapyStartDate != null)
            return false;
        if (updatedBy != null ? !updatedBy.equals(patient.updatedBy) : patient.updatedBy != null) return false;
        if (updatedOn != null ? !updatedOn.equals(patient.updatedOn) : patient.updatedOn != null) return false;
        if (uuid != null ? !uuid.equals(patient.uuid) : patient.uuid != null) return false;
        if (voidReason != null ? !voidReason.equals(patient.voidReason) : patient.voidReason != null) return false;
        if (voidedBy != null ? !voidedBy.equals(patient.voidedBy) : patient.voidedBy != null) return false;
        if (voidedOn != null ? !voidedOn.equals(patient.voidedOn) : patient.voidedOn != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + personId;
        result = 31 * result + (enrollmentDate != null ? enrollmentDate.hashCode() : 0);
        result = 31 * result + (therapyStartDate != null ? therapyStartDate.hashCode() : 0);
        result = 31 * result + (patientSourceId != null ? patientSourceId.hashCode() : 0);
        result = 31 * result + (supportingOrganizationId != null ? supportingOrganizationId.hashCode() : 0);
        result = 31 * result + (startRegimenId != null ? startRegimenId.hashCode() : 0);
        result = 31 * result + (drugAllergies != null ? drugAllergies.hashCode() : 0);
        result = 31 * result + (chronicIllnesses != null ? chronicIllnesses.hashCode() : 0);
        result = 31 * result + (smoker != null ? smoker.hashCode() : 0);
        result = 31 * result + (drinker != null ? drinker.hashCode() : 0);
        result = 31 * result + (fromFacilityId != null ? fromFacilityId.hashCode() : 0);
        result = 31 * result + (patientStatusId != null ? patientStatusId.hashCode() : 0);
        result = 31 * result + (legacyPk != null ? legacyPk.hashCode() : 0);
        result = 31 * result + (uuid != null ? uuid.hashCode() : 0);
        result = 31 * result + createdBy;
        result = 31 * result + (createdOn != null ? createdOn.hashCode() : 0);
        result = 31 * result + (updatedBy != null ? updatedBy.hashCode() : 0);
        result = 31 * result + (updatedOn != null ? updatedOn.hashCode() : 0);
        result = 31 * result + (int) voided;
        result = 31 * result + (voidedBy != null ? voidedBy.hashCode() : 0);
        result = 31 * result + (voidedOn != null ? voidedOn.hashCode() : 0);
        result = 31 * result + (voidReason != null ? voidReason.hashCode() : 0);
        return result;
    }
}
