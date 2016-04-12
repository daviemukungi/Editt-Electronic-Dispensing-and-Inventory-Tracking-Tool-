package org.msh.fdt.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by kenny on 10/29/14.
 */
@Entity
@Table(name = "visit", schema = "", catalog = "fdt")
public class Visit {
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

    private int patientId;

    @Basic
    @javax.persistence.Column(name = "patient_id", nullable = false, insertable = true, updatable = true)
    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    private Integer visitTypeId;

    @Basic
    @javax.persistence.Column(name = "visit_type_id", nullable = true, insertable = true, updatable = true)
    public Integer getVisitTypeId() {
        return visitTypeId;
    }

    public void setVisitTypeId(Integer visitTypeId) {
        this.visitTypeId = visitTypeId;
    }

    private Integer appointmentVisitId;

    @Basic
    @javax.persistence.Column(name = "appointment_visit_id", nullable = true, insertable = true, updatable = true)
    public Integer getAppointmentVisitId() {
        return appointmentVisitId;
    }

    public void setAppointmentVisitId(Integer appointmentVisitId) {
        this.appointmentVisitId = appointmentVisitId;
    }

    private Timestamp startDate;

    @Basic
    @javax.persistence.Column(name = "start_date", nullable = true, insertable = true, updatable = true)
    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    private Timestamp endDate;

    @Basic
    @javax.persistence.Column(name = "end_date", nullable = true, insertable = true, updatable = true)
    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    private Integer regimenId;

    @Basic
    @javax.persistence.Column(name = "regimen_id", nullable = true, insertable = true, updatable = true)
    public Integer getRegimenId() {
        return regimenId;
    }

    public void setRegimenId(Integer regimenId) {
        this.regimenId = regimenId;
    }

    private Integer regimenChangeReasonId;

    @Basic
    @javax.persistence.Column(name = "regimen_change_reason_id", nullable = true, insertable = true, updatable = true)
    public Integer getRegimenChangeReasonId() {
        return regimenChangeReasonId;
    }

    public void setRegimenChangeReasonId(Integer regimenChangeReasonId) {
        this.regimenChangeReasonId = regimenChangeReasonId;
    }

    private Integer familyPlanningMethodId;

    @Basic
    @javax.persistence.Column(name = "family_planning_method_id", nullable = true, insertable = true, updatable = true)
    public Integer getFamilyPlanningMethodId() {
        return familyPlanningMethodId;
    }

    public void setFamilyPlanningMethodId(Integer familyPlanningMethodId) {
        this.familyPlanningMethodId = familyPlanningMethodId;
    }

    private Integer familyPlanningMethodChangeReasonId;

    @Basic
    @javax.persistence.Column(name = "family_planning_method_change_reason_id", nullable = true, insertable = true, updatable = true)
    public Integer getFamilyPlanningMethodChangeReasonId() {
        return familyPlanningMethodChangeReasonId;
    }

    public void setFamilyPlanningMethodChangeReasonId(Integer familyPlanningMethodChangeReasonId) {
        this.familyPlanningMethodChangeReasonId = familyPlanningMethodChangeReasonId;
    }

    private String otherRegimenChangeReason;

    @Basic
    @javax.persistence.Column(name = "other_regimen_change_reason", nullable = true, insertable = true, updatable = true, length = 45)
    public String getOtherRegimenChangeReason() {
        return otherRegimenChangeReason;
    }

    public void setOtherRegimenChangeReason(String otherRegimenChangeReason) {
        this.otherRegimenChangeReason = otherRegimenChangeReason;
    }

    private BigDecimal weight;

    @Basic
    @javax.persistence.Column(name = "weight", nullable = true, insertable = true, updatable = true, precision = 2)
    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    private BigDecimal height;

    @Basic
    @javax.persistence.Column(name = "height", nullable = true, insertable = true, updatable = true, precision = 2)
    public BigDecimal getHeight() {
        return height;
    }

    public void setHeight(BigDecimal height) {
        this.height = height;
    }

    private String babies;

    @Basic
    @javax.persistence.Column(name = "babies", nullable = true, insertable = true, updatable = true)
      public String getBabies() {
        return babies;
    }

    public void setBabies(String babies) {
        this.babies = babies;
    }

    private BigDecimal bodySurfaceArea;

    @Basic
    @javax.persistence.Column(name = "body_surface_area", nullable = true, insertable = true, updatable = true, precision = 2)
    public BigDecimal getBodySurfaceArea() {
        return bodySurfaceArea;
    }

    public void setBodySurfaceArea(BigDecimal bodySurfaceArea) {
        this.bodySurfaceArea = bodySurfaceArea;
    }

    private Integer pillCount;

    @Basic
    @javax.persistence.Column(name = "pill_count", nullable = true, insertable = true, updatable = true)
    public Integer getPillCount() {
        return pillCount;
    }

    public void setPillCount(Integer pillCount) {
        this.pillCount = pillCount;
    }

    private BigDecimal adherence;

    @Basic
    @javax.persistence.Column(name = "adherence", nullable = true, insertable = true, updatable = true, precision = 2)
    public BigDecimal getAdherence() {
        return adherence;
    }

    public void setAdherence(BigDecimal adherence) {
        this.adherence = adherence;
    }

    private byte pregnant;

    @Basic
    @javax.persistence.Column(name = "pregnant", nullable = false, insertable = true, updatable = true)
    public byte getPregnant() {
        return pregnant;
    }

    public void setPregnant(byte pregnant) {
        this.pregnant = pregnant;
    }

    private String otherDrugs;

    @Basic
    @javax.persistence.Column(name = "other_drugs", nullable = true, insertable = true, updatable = true, length = 255)
    public String getOtherDrugs() {
        return otherDrugs;
    }

    public void setOtherDrugs(String otherDrugs) {
        this.otherDrugs = otherDrugs;
    }

    private byte tbConfirmed;

    @Basic
    @javax.persistence.Column(name = "tb_confirmed", nullable = false, insertable = true, updatable = true)
    public byte getTbConfirmed() {
        return tbConfirmed;
    }

    public void setTbConfirmed(byte tbConfirmed) {
        this.tbConfirmed = tbConfirmed;
    }

    private Integer bloodSugarLevelId;

    @Basic
    @javax.persistence.Column(name = "blood_sugar_level_id", nullable = true, insertable = true, updatable = true)
    public Integer getBloodSugarLevelId() {
        return bloodSugarLevelId;
    }

    public void setBloodSugarLevelId(Integer bloodSugarLevelId) {
        this.bloodSugarLevelId = bloodSugarLevelId;
    }

    private Integer insulinId;

    @Basic
    @javax.persistence.Column(name = "insulin_id", nullable = true, insertable = true, updatable = true)
    public Integer getInsulinId() {
        return insulinId;
    }

    public void setInsulinId(Integer insulinId) {
        this.insulinId = insulinId;
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

    private Timestamp nextAppointmentDate;

    @Basic
    @javax.persistence.Column(name = "next_appointment_date", nullable = true, insertable = true, updatable = true)
    public Timestamp getNextAppointmentDate() {
        return nextAppointmentDate;
    }

    public void setNextAppointmentDate(Timestamp nextAppointmentDate) {
        this.nextAppointmentDate = nextAppointmentDate;
    }
    private String drugPay;

    @Basic
    @javax.persistence.Column(name = "drugPay", nullable = true, insertable = true, updatable = true, length = 255)

    public String getDrugPay() {
        return drugPay;
    }

    public void setDrugPay(String drugPay) {
        this.drugPay = drugPay;
    }



    private String comments;

    @Basic
    @javax.persistence.Column(name = "comments", nullable = true, insertable = true, updatable = true, length = 255)
    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
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

        Visit visit = (Visit) o;

        if (createdBy != visit.createdBy) return false;
        if (id != visit.id) return false;
        if (patientId != visit.patientId) return false;
        if (pregnant != visit.pregnant) return false;
        if (tbConfirmed != visit.tbConfirmed) return false;
        if (voided != visit.voided) return false;
        if (adherence != null ? !adherence.equals(visit.adherence) : visit.adherence != null) return false;
        if (appointmentVisitId != null ? !appointmentVisitId.equals(visit.appointmentVisitId) : visit.appointmentVisitId != null)
            return false;
        if (bloodSugarLevelId != null ? !bloodSugarLevelId.equals(visit.bloodSugarLevelId) : visit.bloodSugarLevelId != null)
            return false;
        if (bodySurfaceArea != null ? !bodySurfaceArea.equals(visit.bodySurfaceArea) : visit.bodySurfaceArea != null)
            return false;
        if (comments != null ? !comments.equals(visit.comments) : visit.comments != null) return false;
        if (createdOn != null ? !createdOn.equals(visit.createdOn) : visit.createdOn != null) return false;
        if (endDate != null ? !endDate.equals(visit.endDate) : visit.endDate != null) return false;
        if (familyPlanningMethodChangeReasonId != null ? !familyPlanningMethodChangeReasonId.equals(visit.familyPlanningMethodChangeReasonId) : visit.familyPlanningMethodChangeReasonId != null)
            return false;
        if (familyPlanningMethodId != null ? !familyPlanningMethodId.equals(visit.familyPlanningMethodId) : visit.familyPlanningMethodId != null)
            return false;
        if (height != null ? !height.equals(visit.height) : visit.height != null) return false;
        if (insulinId != null ? !insulinId.equals(visit.insulinId) : visit.insulinId != null) return false;
        if (legacyPk != null ? !legacyPk.equals(visit.legacyPk) : visit.legacyPk != null) return false;
        if (nextAppointmentDate != null ? !nextAppointmentDate.equals(visit.nextAppointmentDate) : visit.nextAppointmentDate != null)
            return false;
        if (otherDrugs != null ? !otherDrugs.equals(visit.otherDrugs) : visit.otherDrugs != null) return false;
        if (otherRegimenChangeReason != null ? !otherRegimenChangeReason.equals(visit.otherRegimenChangeReason) : visit.otherRegimenChangeReason != null)
            return false;
        if (patientStatusId != null ? !patientStatusId.equals(visit.patientStatusId) : visit.patientStatusId != null)
            return false;
        if (drugPay != null ? !drugPay.equals(visit.drugPay) : visit.drugPay != null) return false;
        if (pillCount != null ? !pillCount.equals(visit.pillCount) : visit.pillCount != null) return false;
        if (regimenChangeReasonId != null ? !regimenChangeReasonId.equals(visit.regimenChangeReasonId) : visit.regimenChangeReasonId != null)
            return false;
        if (regimenId != null ? !regimenId.equals(visit.regimenId) : visit.regimenId != null) return false;
        if (startDate != null ? !startDate.equals(visit.startDate) : visit.startDate != null) return false;
        if (updatedBy != null ? !updatedBy.equals(visit.updatedBy) : visit.updatedBy != null) return false;
        if (updatedOn != null ? !updatedOn.equals(visit.updatedOn) : visit.updatedOn != null) return false;
        if (uuid != null ? !uuid.equals(visit.uuid) : visit.uuid != null) return false;
        if (visitTypeId != null ? !visitTypeId.equals(visit.visitTypeId) : visit.visitTypeId != null) return false;
        if (voidReason != null ? !voidReason.equals(visit.voidReason) : visit.voidReason != null) return false;
        if (voidedBy != null ? !voidedBy.equals(visit.voidedBy) : visit.voidedBy != null) return false;
        if (voidedOn != null ? !voidedOn.equals(visit.voidedOn) : visit.voidedOn != null) return false;
        if (weight != null ? !weight.equals(visit.weight) : visit.weight != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + patientId;
        result = 31 * result + (visitTypeId != null ? visitTypeId.hashCode() : 0);
        result = 31 * result + (appointmentVisitId != null ? appointmentVisitId.hashCode() : 0);
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        result = 31 * result + (regimenId != null ? regimenId.hashCode() : 0);
        result = 31 * result + (regimenChangeReasonId != null ? regimenChangeReasonId.hashCode() : 0);
        result = 31 * result + (familyPlanningMethodId != null ? familyPlanningMethodId.hashCode() : 0);
        result = 31 * result + (familyPlanningMethodChangeReasonId != null ? familyPlanningMethodChangeReasonId.hashCode() : 0);
        result = 31 * result + (otherRegimenChangeReason != null ? otherRegimenChangeReason.hashCode() : 0);
        result = 31 * result + (weight != null ? weight.hashCode() : 0);
        result = 31 * result + (height != null ? height.hashCode() : 0);
        result = 31 * result + (bodySurfaceArea != null ? bodySurfaceArea.hashCode() : 0);
        result = 31 * result + (pillCount != null ? pillCount.hashCode() : 0);
        result = 31 * result + (adherence != null ? adherence.hashCode() : 0);
        result = 31 * result + (int) pregnant;
        result = 31 * result + (otherDrugs != null ? otherDrugs.hashCode() : 0);
        result = 31 * result + (int) tbConfirmed;
        result = 31 * result + (bloodSugarLevelId != null ? bloodSugarLevelId.hashCode() : 0);
        result = 31 * result + (insulinId != null ? insulinId.hashCode() : 0);
        result = 31 * result + (patientStatusId != null ? patientStatusId.hashCode() : 0);
        result = 31 * result + (nextAppointmentDate != null ? nextAppointmentDate.hashCode() : 0);
        result = 31 * result + (comments != null ? comments.hashCode() : 0);
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
