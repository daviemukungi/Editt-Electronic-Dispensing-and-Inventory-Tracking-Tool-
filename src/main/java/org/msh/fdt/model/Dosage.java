package org.msh.fdt.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collection;

/**
 * Created by kenny on 8/19/14.
 */
@Entity
@Table(name = "dosage", schema = "", catalog = "fdt")
public class Dosage {
    private int id;
    private String name;
    private BigDecimal value;
    private Integer frequency;
    private String description;
    private byte standard;
    private String uuid;
    private int createdBy;
    private Timestamp createdOn;
    private Integer updatedBy;
    private Timestamp updatedOn;
    private byte voided;
    private Integer voidedBy;
    private Timestamp voidedOn;
    private String voidReason;
    private User userByCreatedBy;
    private User userByUpdatedBy;
    private User userByCreatedBy_0;
    private User userByVoidedBy;
    private Collection<PatientTransactionItem> patientTransactionItemsById;

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
    @Column(name = "name", nullable = false, insertable = true, updatable = true, length = 45)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "value", nullable = true, insertable = true, updatable = true, precision = 2)
    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    @Basic
    @Column(name = "frequency", nullable = true, insertable = true, updatable = true)
    public Integer getFrequency() {
        return frequency;
    }

    public void setFrequency(Integer frequency) {
        this.frequency = frequency;
    }

    @Basic
    @Column(name = "description", nullable = true, insertable = true, updatable = true, length = 45)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "standard", nullable = false, insertable = true, updatable = true)
    public byte getStandard() {
        return standard;
    }

    public void setStandard(byte standard) {
        this.standard = standard;
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

        Dosage dosage = (Dosage) o;

        if (createdBy != dosage.createdBy) return false;
        if (id != dosage.id) return false;
        if (standard != dosage.standard) return false;
        if (voided != dosage.voided) return false;
        if (createdOn != null ? !createdOn.equals(dosage.createdOn) : dosage.createdOn != null) return false;
        if (description != null ? !description.equals(dosage.description) : dosage.description != null) return false;
        if (frequency != null ? !frequency.equals(dosage.frequency) : dosage.frequency != null) return false;
        if (name != null ? !name.equals(dosage.name) : dosage.name != null) return false;
        if (updatedBy != null ? !updatedBy.equals(dosage.updatedBy) : dosage.updatedBy != null) return false;
        if (updatedOn != null ? !updatedOn.equals(dosage.updatedOn) : dosage.updatedOn != null) return false;
        if (uuid != null ? !uuid.equals(dosage.uuid) : dosage.uuid != null) return false;
        if (value != null ? !value.equals(dosage.value) : dosage.value != null) return false;
        if (voidReason != null ? !voidReason.equals(dosage.voidReason) : dosage.voidReason != null) return false;
        if (voidedBy != null ? !voidedBy.equals(dosage.voidedBy) : dosage.voidedBy != null) return false;
        if (voidedOn != null ? !voidedOn.equals(dosage.voidedOn) : dosage.voidedOn != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (value != null ? value.hashCode() : 0);
        result = 31 * result + (frequency != null ? frequency.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (int) standard;
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
    @JoinColumn(name = "created_by", referencedColumnName = "id", nullable = false, insertable=false, updatable=false)
    public User getUserByCreatedBy() {
        return userByCreatedBy;
    }

    public void setUserByCreatedBy(User userByCreatedBy) {
        this.userByCreatedBy = userByCreatedBy;
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
    public User getUserByCreatedBy_0() {
        return userByCreatedBy_0;
    }

    public void setUserByCreatedBy_0(User userByCreatedBy_0) {
        this.userByCreatedBy_0 = userByCreatedBy_0;
    }

    @ManyToOne
    @JoinColumn(name = "voided_by", referencedColumnName = "id", insertable=false, updatable=false)
    public User getUserByVoidedBy() {
        return userByVoidedBy;
    }

    public void setUserByVoidedBy(User userByVoidedBy) {
        this.userByVoidedBy = userByVoidedBy;
    }

    @OneToMany(mappedBy = "dosageByDosageId")
    public Collection<PatientTransactionItem> getPatientTransactionItemsById() {
        return patientTransactionItemsById;
    }

    public void setPatientTransactionItemsById(Collection<PatientTransactionItem> patientTransactionItemsById) {
        this.patientTransactionItemsById = patientTransactionItemsById;
    }
}
