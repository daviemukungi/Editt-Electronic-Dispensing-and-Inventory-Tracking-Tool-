package org.msh.fdt.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by kenny on 3/17/15.
 */
@Entity
@Table(name = "drug", schema = "", catalog = "fdt")
public class Drug {
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

    private String name;

    @Basic
    @javax.persistence.Column(name = "name", nullable = false, insertable = true, updatable = true, length = 255)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String strength;

    @Basic
    @javax.persistence.Column(name = "strength", nullable = true, insertable = true, updatable = true, length = 45)
    public String getStrength() {
        return strength;
    }

    public void setStrength(String strength) {
        this.strength = strength;
    }

    private Integer genericNameId;

    @Basic
    @javax.persistence.Column(name = "generic_name_id", nullable = true, insertable = true, updatable = true)
    public Integer getGenericNameId() {
        return genericNameId;
    }

    public void setGenericNameId(Integer genericNameId) {
        this.genericNameId = genericNameId;
    }

    private String kemsaName;

    @Basic
    @javax.persistence.Column(name = "kemsa_name", nullable = true, insertable = true, updatable = true, length = 255)
    public String getKemsaName() {
        return kemsaName;
    }

    public void setKemsaName(String kemsaName) {
        this.kemsaName = kemsaName;
    }

    private String sca1Name;

    @Basic
    @javax.persistence.Column(name = "sca1_name", nullable = true, insertable = true, updatable = true, length = 255)
    public String getSca1Name() {
        return sca1Name;
    }

    public void setSca1Name(String sca1Name) {
        this.sca1Name = sca1Name;
    }

    private String sca2Name;

    @Basic
    @javax.persistence.Column(name = "sca2_name", nullable = true, insertable = true, updatable = true, length = 255)
    public String getSca2Name() {
        return sca2Name;
    }

    public void setSca2Name(String sca2Name) {
        this.sca2Name = sca2Name;
    }

    private String sca3Name;

    @Basic
    @javax.persistence.Column(name = "sca3_name", nullable = true, insertable = true, updatable = true, length = 255)
    public String getSca3Name() {
        return sca3Name;
    }

    public void setSca3Name(String sca3Name) {
        this.sca3Name = sca3Name;
    }

    private String cdrrName;

    @Basic
    @javax.persistence.Column(name = "cdrr_name", nullable = true, insertable = true, updatable = true, length = 255)
    public String getCdrrName() {
        return cdrrName;
    }

    public void setCdrrName(String cdrrName) {
        this.cdrrName = cdrrName;
    }

    private String abbreviation;

    @Basic
    @javax.persistence.Column(name = "abbreviation", nullable = true, insertable = true, updatable = true, length = 45)
    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    private String dhisId;

    @Basic
    @javax.persistence.Column(name = "dhis_id", nullable = true, insertable = true, updatable = true, length = 45)
    public String getDhisId() {
        return dhisId;
    }

    public void setDhisId(String dhisId) {
        this.dhisId = dhisId;
    }

    private Integer drugCategoryId;

    @Basic
    @javax.persistence.Column(name = "drug_category_id", nullable = true, insertable = true, updatable = true)
    public Integer getDrugCategoryId() {
        return drugCategoryId;
    }

    public void setDrugCategoryId(Integer drugCategoryId) {
        this.drugCategoryId = drugCategoryId;
    }

    private Integer cdrrCategoryId;

    @Basic
    @javax.persistence.Column(name = "cdrr_category_id", nullable = true, insertable = true, updatable = true)
    public Integer getCdrrCategoryId() {
        return cdrrCategoryId;
    }

    public void setCdrrCategoryId(Integer cdrrCategoryId) {
        this.cdrrCategoryId = cdrrCategoryId;
    }

    private BigDecimal cdrrIndex;

    @Basic
    @javax.persistence.Column(name = "cdrr_index", nullable = false, insertable = true, updatable = true, precision = 2)
    public BigDecimal getCdrrIndex() {
        return cdrrIndex;
    }

    public void setCdrrIndex(BigDecimal cdrrIndex) {
        this.cdrrIndex = cdrrIndex;
    }

    private Integer drugTypeId;

    @Basic
    @javax.persistence.Column(name = "drug_type_id", nullable = true, insertable = true, updatable = true)
    public Integer getDrugTypeId() {
        return drugTypeId;
    }

    public void setDrugTypeId(Integer drugTypeId) {
        this.drugTypeId = drugTypeId;
    }

    private Integer drugFormId;

    @Basic
    @javax.persistence.Column(name = "drug_form_id", nullable = true, insertable = true, updatable = true)
    public Integer getDrugFormId() {
        return drugFormId;
    }

    public void setDrugFormId(Integer drugFormId) {
        this.drugFormId = drugFormId;
    }

    private Integer dispensingUnitId;

    @Basic
    @javax.persistence.Column(name = "dispensing_unit_id", nullable = true, insertable = true, updatable = true)
    public Integer getDispensingUnitId() {
        return dispensingUnitId;
    }

    public void setDispensingUnitId(Integer dispensingUnitId) {
        this.dispensingUnitId = dispensingUnitId;
    }

    private Integer packSize;

    @Basic
    @javax.persistence.Column(name = "pack_size", nullable = true, insertable = true, updatable = true)
    public Integer getPackSize() {
        return packSize;
    }

    public void setPackSize(Integer packSize) {
        this.packSize = packSize;
    }

    private BigDecimal reorderPoint;

    @Basic
    @javax.persistence.Column(name = "reorder_point", nullable = true, insertable = true, updatable = true, precision = 2)
    public BigDecimal getReorderPoint() {
        return reorderPoint;
    }

    public void setReorderPoint(BigDecimal reorderPoint) {
        this.reorderPoint = reorderPoint;
    }

    private Integer serviceTypeId;

    @Basic
    @javax.persistence.Column(name = "service_type_id", nullable = true, insertable = true, updatable = true)
    public Integer getServiceTypeId() {
        return serviceTypeId;
    }

    public void setServiceTypeId(Integer serviceTypeId) {
        this.serviceTypeId = serviceTypeId;
    }

    private Integer dosageId;

    @Basic
    @javax.persistence.Column(name = "dosage_id", nullable = true, insertable = true, updatable = true)
    public Integer getDosageId() {
        return dosageId;
    }

    public void setDosageId(Integer dosageId) {
        this.dosageId = dosageId;
    }

    private BigDecimal quantity;

    @Basic
    @javax.persistence.Column(name = "quantity", nullable = true, insertable = true, updatable = true, precision = 2)
    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    private Integer duration;

    @Basic
    @javax.persistence.Column(name = "duration", nullable = true, insertable = true, updatable = true)
    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    private String combinationType;

    @Basic
    @javax.persistence.Column(name = "combination_type", nullable = true, insertable = true, updatable = true, length = 45)
    public String getCombinationType() {
        return combinationType;
    }

    public void setCombinationType(String combinationType) {
        this.combinationType = combinationType;
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

    private byte tracer;

    @Basic
    @javax.persistence.Column(name = "tracer", nullable = false, insertable = true, updatable = true)
    public byte getTracer() {
        return tracer;
    }

    public void setTracer(byte tracer) {
        this.tracer = tracer;
    }

    private byte standard;

    @Basic
    @javax.persistence.Column(name = "standard", nullable = false, insertable = true, updatable = true)
    public byte getStandard() {
        return standard;
    }

    public void setStandard(byte standard) {
        this.standard = standard;
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

        Drug drug = (Drug) o;

        if (createdBy != drug.createdBy) return false;
        if (id != drug.id) return false;
        if (standard != drug.standard) return false;
        if (tracer != drug.tracer) return false;
        if (voided != drug.voided) return false;
        if (abbreviation != null ? !abbreviation.equals(drug.abbreviation) : drug.abbreviation != null) return false;
        if (cdrrCategoryId != null ? !cdrrCategoryId.equals(drug.cdrrCategoryId) : drug.cdrrCategoryId != null)
            return false;
        if (cdrrIndex != null ? !cdrrIndex.equals(drug.cdrrIndex) : drug.cdrrIndex != null) return false;
        if (cdrrName != null ? !cdrrName.equals(drug.cdrrName) : drug.cdrrName != null) return false;
        if (combinationType != null ? !combinationType.equals(drug.combinationType) : drug.combinationType != null)
            return false;
        if (comments != null ? !comments.equals(drug.comments) : drug.comments != null) return false;
        if (createdOn != null ? !createdOn.equals(drug.createdOn) : drug.createdOn != null) return false;
        if (dhisId != null ? !dhisId.equals(drug.dhisId) : drug.dhisId != null) return false;
        if (dispensingUnitId != null ? !dispensingUnitId.equals(drug.dispensingUnitId) : drug.dispensingUnitId != null)
            return false;
        if (dosageId != null ? !dosageId.equals(drug.dosageId) : drug.dosageId != null) return false;
        if (drugCategoryId != null ? !drugCategoryId.equals(drug.drugCategoryId) : drug.drugCategoryId != null)
            return false;
        if (drugFormId != null ? !drugFormId.equals(drug.drugFormId) : drug.drugFormId != null) return false;
        if (drugTypeId != null ? !drugTypeId.equals(drug.drugTypeId) : drug.drugTypeId != null) return false;
        if (duration != null ? !duration.equals(drug.duration) : drug.duration != null) return false;
        if (genericNameId != null ? !genericNameId.equals(drug.genericNameId) : drug.genericNameId != null)
            return false;
        if (kemsaName != null ? !kemsaName.equals(drug.kemsaName) : drug.kemsaName != null) return false;
        if (name != null ? !name.equals(drug.name) : drug.name != null) return false;
        if (packSize != null ? !packSize.equals(drug.packSize) : drug.packSize != null) return false;
        if (quantity != null ? !quantity.equals(drug.quantity) : drug.quantity != null) return false;
        if (reorderPoint != null ? !reorderPoint.equals(drug.reorderPoint) : drug.reorderPoint != null) return false;
        if (sca1Name != null ? !sca1Name.equals(drug.sca1Name) : drug.sca1Name != null) return false;
        if (sca2Name != null ? !sca2Name.equals(drug.sca2Name) : drug.sca2Name != null) return false;
        if (sca3Name != null ? !sca3Name.equals(drug.sca3Name) : drug.sca3Name != null) return false;
        if (serviceTypeId != null ? !serviceTypeId.equals(drug.serviceTypeId) : drug.serviceTypeId != null)
            return false;
        if (strength != null ? !strength.equals(drug.strength) : drug.strength != null) return false;
        if (updatedBy != null ? !updatedBy.equals(drug.updatedBy) : drug.updatedBy != null) return false;
        if (updatedOn != null ? !updatedOn.equals(drug.updatedOn) : drug.updatedOn != null) return false;
        if (uuid != null ? !uuid.equals(drug.uuid) : drug.uuid != null) return false;
        if (voidReason != null ? !voidReason.equals(drug.voidReason) : drug.voidReason != null) return false;
        if (voidedBy != null ? !voidedBy.equals(drug.voidedBy) : drug.voidedBy != null) return false;
        if (voidedOn != null ? !voidedOn.equals(drug.voidedOn) : drug.voidedOn != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (strength != null ? strength.hashCode() : 0);
        result = 31 * result + (genericNameId != null ? genericNameId.hashCode() : 0);
        result = 31 * result + (kemsaName != null ? kemsaName.hashCode() : 0);
        result = 31 * result + (sca1Name != null ? sca1Name.hashCode() : 0);
        result = 31 * result + (sca2Name != null ? sca2Name.hashCode() : 0);
        result = 31 * result + (sca3Name != null ? sca3Name.hashCode() : 0);
        result = 31 * result + (cdrrName != null ? cdrrName.hashCode() : 0);
        result = 31 * result + (abbreviation != null ? abbreviation.hashCode() : 0);
        result = 31 * result + (dhisId != null ? dhisId.hashCode() : 0);
        result = 31 * result + (drugCategoryId != null ? drugCategoryId.hashCode() : 0);
        result = 31 * result + (cdrrCategoryId != null ? cdrrCategoryId.hashCode() : 0);
        result = 31 * result + (cdrrIndex != null ? cdrrIndex.hashCode() : 0);
        result = 31 * result + (drugTypeId != null ? drugTypeId.hashCode() : 0);
        result = 31 * result + (drugFormId != null ? drugFormId.hashCode() : 0);
        result = 31 * result + (dispensingUnitId != null ? dispensingUnitId.hashCode() : 0);
        result = 31 * result + (packSize != null ? packSize.hashCode() : 0);
        result = 31 * result + (reorderPoint != null ? reorderPoint.hashCode() : 0);
        result = 31 * result + (serviceTypeId != null ? serviceTypeId.hashCode() : 0);
        result = 31 * result + (dosageId != null ? dosageId.hashCode() : 0);
        result = 31 * result + (quantity != null ? quantity.hashCode() : 0);
        result = 31 * result + (duration != null ? duration.hashCode() : 0);
        result = 31 * result + (combinationType != null ? combinationType.hashCode() : 0);
        result = 31 * result + (comments != null ? comments.hashCode() : 0);
        result = 31 * result + (int) tracer;
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
