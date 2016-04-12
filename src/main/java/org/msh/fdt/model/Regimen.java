package org.msh.fdt.model;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by kenny on 8/19/14.
 */
@Entity
@Table(name = "regimen", schema = "", catalog = "fdt")
public class Regimen {
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

    private String code;

    @Basic
    @Column(name = "code", nullable = false, insertable = true, updatable = true, length = 45)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    private String name;

    @Basic
    @Column(name = "name", nullable = true, insertable = true, updatable = true, length = 255)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private Integer line;

    @Basic
    @Column(name = "line", nullable = true, insertable = true, updatable = true)
    public Integer getLine() {
        return line;
    }

    public void setLine(Integer line) {
        this.line = line;
    }

    private Integer regimenTypeId;

    @Basic
    @Column(name = "regimen_type_id", nullable = true, insertable = true, updatable = true)
    public Integer getRegimenTypeId() {
        return regimenTypeId;
    }

    public void setRegimenTypeId(Integer regimenTypeId) {
        this.regimenTypeId = regimenTypeId;
    }

    private Integer serviceTypeId;

    @Basic
    @Column(name = "service_type_id", nullable = true, insertable = true, updatable = true)
    public Integer getServiceTypeId() {
        return serviceTypeId;
    }

    public void setServiceTypeId(Integer serviceTypeId) {
        this.serviceTypeId = serviceTypeId;
    }

    private Integer regimenStatusId;

    @Basic
    @Column(name = "regimen_status_id", nullable = true, insertable = true, updatable = true)
    public Integer getRegimenStatusId() {
        return regimenStatusId;
    }

    public void setRegimenStatusId(Integer regimenStatusId) {
        this.regimenStatusId = regimenStatusId;
    }

    private Byte visible;

    @Basic
    @Column(name = "visible", nullable = true, insertable = true, updatable = true)
    public Byte getVisible() {
        return visible;
    }

    public void setVisible(Byte visible) {
        this.visible = visible;
    }

    private String comments;

    @Basic
    @Column(name = "comments", nullable = true, insertable = true, updatable = true, length = 255)
    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    private byte standard;

    @Basic
    @Column(name = "standard", nullable = false, insertable = true, updatable = true)
    public byte getStandard() {
        return standard;
    }

    public void setStandard(byte standard) {
        this.standard = standard;
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

        Regimen regimen = (Regimen) o;

        if (createdBy != regimen.createdBy) return false;
        if (id != regimen.id) return false;
        if (standard != regimen.standard) return false;
        if (voided != regimen.voided) return false;
        if (code != null ? !code.equals(regimen.code) : regimen.code != null) return false;
        if (comments != null ? !comments.equals(regimen.comments) : regimen.comments != null) return false;
        if (createdOn != null ? !createdOn.equals(regimen.createdOn) : regimen.createdOn != null) return false;
        if (line != null ? !line.equals(regimen.line) : regimen.line != null) return false;
        if (name != null ? !name.equals(regimen.name) : regimen.name != null) return false;
        if (regimenStatusId != null ? !regimenStatusId.equals(regimen.regimenStatusId) : regimen.regimenStatusId != null)
            return false;
        if (regimenTypeId != null ? !regimenTypeId.equals(regimen.regimenTypeId) : regimen.regimenTypeId != null)
            return false;
        if (serviceTypeId != null ? !serviceTypeId.equals(regimen.serviceTypeId) : regimen.serviceTypeId != null)
            return false;
        if (updatedBy != null ? !updatedBy.equals(regimen.updatedBy) : regimen.updatedBy != null) return false;
        if (updatedOn != null ? !updatedOn.equals(regimen.updatedOn) : regimen.updatedOn != null) return false;
        if (uuid != null ? !uuid.equals(regimen.uuid) : regimen.uuid != null) return false;
        if (visible != null ? !visible.equals(regimen.visible) : regimen.visible != null) return false;
        if (voidReason != null ? !voidReason.equals(regimen.voidReason) : regimen.voidReason != null) return false;
        if (voidedBy != null ? !voidedBy.equals(regimen.voidedBy) : regimen.voidedBy != null) return false;
        if (voidedOn != null ? !voidedOn.equals(regimen.voidedOn) : regimen.voidedOn != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (code != null ? code.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (line != null ? line.hashCode() : 0);
        result = 31 * result + (regimenTypeId != null ? regimenTypeId.hashCode() : 0);
        result = 31 * result + (serviceTypeId != null ? serviceTypeId.hashCode() : 0);
        result = 31 * result + (regimenStatusId != null ? regimenStatusId.hashCode() : 0);
        result = 31 * result + (visible != null ? visible.hashCode() : 0);
        result = 31 * result + (comments != null ? comments.hashCode() : 0);
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
}
