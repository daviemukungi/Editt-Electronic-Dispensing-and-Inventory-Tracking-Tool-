package org.msh.fdt.model;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by kenny on 8/19/14.
 */
@Entity
@Table(name = "person_address", schema = "", catalog = "fdt")
public class PersonAddress {
    private int id;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int personId;

    @Basic
    @Column(name = "person_id", nullable = false, insertable = true, updatable = true)
    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    private String telNo1;

    @Basic
    @Column(name = "tel_no1", nullable = true, insertable = true, updatable = true, length = 45)
    public String getTelNo1() {
        return telNo1;
    }

    public void setTelNo1(String telNo1) {
        this.telNo1 = telNo1;
    }

    private String telNo2;

    @Basic
    @Column(name = "tel_no2", nullable = true, insertable = true, updatable = true, length = 45)
    public String getTelNo2() {
        return telNo2;
    }

    public void setTelNo2(String telNo2) {
        this.telNo2 = telNo2;
    }

    private String postalAddress;

    @Basic
    @Column(name = "postal_address", nullable = true, insertable = true, updatable = true, length = 45)
    public String getPostalAddress() {
        return postalAddress;
    }

    public void setPostalAddress(String postalAddress) {
        this.postalAddress = postalAddress;
    }

    private String emailAddress;

    @Basic
    @Column(name = "email_address", nullable = true, insertable = true, updatable = true, length = 45)
    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    private String physicalAddress;

    @Basic
    @Column(name = "physical_address", nullable = true, insertable = true, updatable = true, length = 255)
    public String getPhysicalAddress() {
        return physicalAddress;
    }

    public void setPhysicalAddress(String physicalAddress) {
        this.physicalAddress = physicalAddress;
    }

    private String alternativeAddress;

    @Basic
    @Column(name = "`alternative address`", nullable = true, insertable = true, updatable = true, length = 255)
    public String getAlternativeAddress() {
        return alternativeAddress;
    }

    public void setAlternativeAddress(String alternativeAddress) {
        this.alternativeAddress = alternativeAddress;
    }

    private String legacyPk;

    @Basic
    @Column(name = "legacy_pk", nullable = true, insertable = true, updatable = true, length = 45)
    public String getLegacyPk() {
        return legacyPk;
    }

    public void setLegacyPk(String legacyPk) {
        this.legacyPk = legacyPk;
    }

    private String uuid;

    @Basic
    @Column(name = "uuid", nullable = false, insertable = true, updatable = true, length = 36)
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    private int createdBy;

    @Basic
    @Column(name = "created_by", nullable = false, insertable = true, updatable = true)
    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    private Timestamp createdOn;

    @Basic
    @Column(name = "created_on", nullable = false, insertable = true, updatable = true)
    public Timestamp getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Timestamp createdOn) {
        this.createdOn = createdOn;
    }

    private Integer updatedBy;

    @Basic
    @Column(name = "updated_by", nullable = true, insertable = true, updatable = true)
    public Integer getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Integer updatedBy) {
        this.updatedBy = updatedBy;
    }

    private Timestamp updatedOn;

    @Basic
    @Column(name = "updated_on", nullable = true, insertable = true, updatable = true)
    public Timestamp getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Timestamp updatedOn) {
        this.updatedOn = updatedOn;
    }

    private byte voided;

    @Basic
    @Column(name = "voided", nullable = false, insertable = true, updatable = true)
    public byte getVoided() {
        return voided;
    }

    public void setVoided(byte voided) {
        this.voided = voided;
    }

    private Integer voidedBy;

    @Basic
    @Column(name = "voided_by", nullable = true, insertable = true, updatable = true)
    public Integer getVoidedBy() {
        return voidedBy;
    }

    public void setVoidedBy(Integer voidedBy) {
        this.voidedBy = voidedBy;
    }

    private Timestamp voidedOn;

    @Basic
    @Column(name = "voided_on", nullable = true, insertable = true, updatable = true)
    public Timestamp getVoidedOn() {
        return voidedOn;
    }

    public void setVoidedOn(Timestamp voidedOn) {
        this.voidedOn = voidedOn;
    }

    private String voidReason;

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

        PersonAddress that = (PersonAddress) o;

        if (createdBy != that.createdBy) return false;
        if (id != that.id) return false;
        if (personId != that.personId) return false;
        if (voided != that.voided) return false;
        if (alternativeAddress != null ? !alternativeAddress.equals(that.alternativeAddress) : that.alternativeAddress != null)
            return false;
        if (createdOn != null ? !createdOn.equals(that.createdOn) : that.createdOn != null) return false;
        if (emailAddress != null ? !emailAddress.equals(that.emailAddress) : that.emailAddress != null) return false;
        if (legacyPk != null ? !legacyPk.equals(that.legacyPk) : that.legacyPk != null) return false;
        if (physicalAddress != null ? !physicalAddress.equals(that.physicalAddress) : that.physicalAddress != null)
            return false;
        if (postalAddress != null ? !postalAddress.equals(that.postalAddress) : that.postalAddress != null)
            return false;
        if (telNo1 != null ? !telNo1.equals(that.telNo1) : that.telNo1 != null) return false;
        if (telNo2 != null ? !telNo2.equals(that.telNo2) : that.telNo2 != null) return false;
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
        result = 31 * result + personId;
        result = 31 * result + (telNo1 != null ? telNo1.hashCode() : 0);
        result = 31 * result + (telNo2 != null ? telNo2.hashCode() : 0);
        result = 31 * result + (postalAddress != null ? postalAddress.hashCode() : 0);
        result = 31 * result + (emailAddress != null ? emailAddress.hashCode() : 0);
        result = 31 * result + (physicalAddress != null ? physicalAddress.hashCode() : 0);
        result = 31 * result + (alternativeAddress != null ? alternativeAddress.hashCode() : 0);
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
