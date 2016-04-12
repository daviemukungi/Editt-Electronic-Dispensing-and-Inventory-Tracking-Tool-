package org.msh.fdt.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by kenny on 8/19/14.
 */
@Entity
@Table(name = "prescription_item", schema = "", catalog = "fdt")
public class PrescriptionItem {
    private int id;
    private int prescriptionId;
    private int drugId;
    private String dosageId;
    private BigDecimal quantity;
    private String duration;
    private String uuid;
    private int createdBy;
    private Timestamp createdOn;
    private Integer updatedBy;
    private Timestamp updatedOn;
    private byte voided;
    private Integer voidedBy;
    private Timestamp voidedOn;
    private String voidReason;
    private Prescription prescriptionByPrescriptionId;
    private User userByUpdatedBy;
    private User userByCreatedBy;
    private User userByVoidedBy;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "prescription_id", nullable = false, insertable = true, updatable = true)
    public int getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(int prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    @Basic
    @Column(name = "drug_id", nullable = false, insertable = true, updatable = true)
    public int getDrugId() {
        return drugId;
    }

    public void setDrugId(int drugId) {
        this.drugId = drugId;
    }

    @Basic
    @Column(name = "dosage_id", nullable = true, insertable = true, updatable = true, length = 45)
    public String getDosageId() {
        return dosageId;
    }

    public void setDosageId(String dosageId) {
        this.dosageId = dosageId;
    }

    @Basic
    @Column(name = "quantity", nullable = true, insertable = true, updatable = true, precision = 2)
    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    @Basic
    @Column(name = "duration", nullable = true, insertable = true, updatable = true, length = 45)
    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    @Basic
    @Column(name = "uuid", nullable = false, insertable = true, updatable = true, length = 36)
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Basic
    @Column(name = "created_by", nullable = false, insertable = true, updatable = true)
    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    @Basic
    @Column(name = "created_on", nullable = false, insertable = true, updatable = true)
    public Timestamp getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Timestamp createdOn) {
        this.createdOn = createdOn;
    }

    @Basic
    @Column(name = "updated_by", nullable = true, insertable = true, updatable = true)
    public Integer getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Integer updatedBy) {
        this.updatedBy = updatedBy;
    }

    @Basic
    @Column(name = "updated_on", nullable = true, insertable = true, updatable = true)
    public Timestamp getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Timestamp updatedOn) {
        this.updatedOn = updatedOn;
    }

    @Basic
    @Column(name = "voided", nullable = false, insertable = true, updatable = true)
    public byte getVoided() {
        return voided;
    }

    public void setVoided(byte voided) {
        this.voided = voided;
    }

    @Basic
    @Column(name = "voided_by", nullable = true, insertable = true, updatable = true)
    public Integer getVoidedBy() {
        return voidedBy;
    }

    public void setVoidedBy(Integer voidedBy) {
        this.voidedBy = voidedBy;
    }

    @Basic
    @Column(name = "voided_on", nullable = true, insertable = true, updatable = true)
    public Timestamp getVoidedOn() {
        return voidedOn;
    }

    public void setVoidedOn(Timestamp voidedOn) {
        this.voidedOn = voidedOn;
    }

    @Basic
    @Column(name = "void_reason", nullable = true, insertable = true, updatable = true, length = 255)
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

        PrescriptionItem that = (PrescriptionItem) o;

        if (createdBy != that.createdBy) return false;
        if (drugId != that.drugId) return false;
        if (id != that.id) return false;
        if (prescriptionId != that.prescriptionId) return false;
        if (voided != that.voided) return false;
        if (createdOn != null ? !createdOn.equals(that.createdOn) : that.createdOn != null) return false;
        if (dosageId != null ? !dosageId.equals(that.dosageId) : that.dosageId != null) return false;
        if (duration != null ? !duration.equals(that.duration) : that.duration != null) return false;
        if (quantity != null ? !quantity.equals(that.quantity) : that.quantity != null) return false;
        if (updatedBy != null ? !updatedBy.equals(that.updatedBy) : that.updatedBy != null) return false;
        if (updatedOn != null ? !updatedOn.equals(that.updatedOn) : that.updatedOn != null) return false;
        if (uuid != null ? !uuid.equals(that.uuid) : that.uuid != null) return false;
        if (voidReason != null ? !voidReason.equals(that.voidReason) : that.voidReason != null) return false;
        if (voidedBy != null ? !voidedBy.equals(that.voidedBy) : that.voidedBy != null) return false;
        if (voidedOn != null ? !voidedOn.equals(that.voidedOn) : that.voidedOn != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + prescriptionId;
        result = 31 * result + drugId;
        result = 31 * result + (dosageId != null ? dosageId.hashCode() : 0);
        result = 31 * result + (quantity != null ? quantity.hashCode() : 0);
        result = 31 * result + (duration != null ? duration.hashCode() : 0);
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

    @ManyToOne
    @JoinColumn(name = "prescription_id", referencedColumnName = "id", nullable = false, insertable=false, updatable=false)
    public Prescription getPrescriptionByPrescriptionId() {
        return prescriptionByPrescriptionId;
    }

    public void setPrescriptionByPrescriptionId(Prescription prescriptionByPrescriptionId) {
        this.prescriptionByPrescriptionId = prescriptionByPrescriptionId;
    }

    @ManyToOne
    @JoinColumn(name = "updated_by", referencedColumnName = "id", insertable=false, updatable=false)
    public User getUserByUpdatedBy() {
        return userByUpdatedBy;
    }

    public void setUserByUpdatedBy(User userByUpdatedBy) {
        this.userByUpdatedBy = userByUpdatedBy;
    }

    @ManyToOne
    @JoinColumn(name = "created_by", referencedColumnName = "id", nullable = false, insertable=false, updatable=false)
    public User getUserByCreatedBy() {
        return userByCreatedBy;
    }

    public void setUserByCreatedBy(User userByCreatedBy) {
        this.userByCreatedBy = userByCreatedBy;
    }

    @ManyToOne
    @JoinColumn(name = "voided_by", referencedColumnName = "id", insertable=false, updatable=false)
    public User getUserByVoidedBy() {
        return userByVoidedBy;
    }

    public void setUserByVoidedBy(User userByVoidedBy) {
        this.userByVoidedBy = userByVoidedBy;
    }
}
