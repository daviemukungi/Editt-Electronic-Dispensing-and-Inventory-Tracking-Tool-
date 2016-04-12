package org.msh.fdt.model;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by kenny on 8/19/14.
 */
@Entity
@Table(name = "stock_take_sheet", schema = "", catalog = "fdt")
public class StockTakeSheet {
    private int id;
    private Timestamp takeDate;
    private String packSize;
    private String referenceNo;
    private String countedBy;
    private String verifiedBy;
    private byte monthly;
    private String uuid;
    private int createdBy;
    private Timestamp createdOn;
    private Integer updatedBy;
    private Timestamp updatedOn;
    private byte voided;
    private Integer voidedBy;
    private Timestamp voidedOn;
    private String voidReason;
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
    @Column(name = "pack_size", nullable = true, insertable = true, updatable = true)
    public String getPackSize() {
        return packSize;
    }

    public void setPackSize(String packSize) {
        this.packSize = packSize;
    }

    @Basic
    @Column(name = "take_date", nullable = false, insertable = true, updatable = true)
    public Timestamp getTakeDate() {
        return takeDate;
    }

    public void setTakeDate(Timestamp takeDate) {
        this.takeDate = takeDate;
    }

    @Basic
    @Column(name = "reference_no", nullable = false, insertable = true, updatable = true, length = 45)
    public String getReferenceNo() {
        return referenceNo;
    }

    public void setReferenceNo(String referenceNo) {
        this.referenceNo = referenceNo;
    }

    @Basic
    @Column(name = "counted_by", nullable = true, insertable = true, updatable = true, length = 45)
    public String getCountedBy() {
        return countedBy;
    }

    public void setCountedBy(String countedBy) {
        this.countedBy = countedBy;
    }

    @Basic
    @Column(name = "verified_by", nullable = true, insertable = true, updatable = true, length = 45)
    public String getVerifiedBy() {
        return verifiedBy;
    }

    public void setVerifiedBy(String verifiedBy) {
        this.verifiedBy = verifiedBy;
    }

    @Basic
    @Column(name = "monthly", nullable = false, insertable = true, updatable = true)
    public byte getMonthly() {
        return monthly;
    }

    public void setMonthly(byte monthly) {
        this.monthly = monthly;
    }

    @Basic
    @Column(name = "uuid", nullable = true, insertable = true, updatable = true, length = 38)
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

        StockTakeSheet takeSheet = (StockTakeSheet) o;

        if (createdBy != takeSheet.createdBy) return false;
        if (id != takeSheet.id) return false;
        if (monthly != takeSheet.monthly) return false;
        if (voided != takeSheet.voided) return false;
        if (countedBy != null ? !countedBy.equals(takeSheet.countedBy) : takeSheet.countedBy != null) return false;
        if (createdOn != null ? !createdOn.equals(takeSheet.createdOn) : takeSheet.createdOn != null) return false;
        if (referenceNo != null ? !referenceNo.equals(takeSheet.referenceNo) : takeSheet.referenceNo != null)
            return false;
        if (takeDate != null ? !takeDate.equals(takeSheet.takeDate) : takeSheet.takeDate != null) return false;
        if (updatedBy != null ? !updatedBy.equals(takeSheet.updatedBy) : takeSheet.updatedBy != null) return false;
        if (updatedOn != null ? !updatedOn.equals(takeSheet.updatedOn) : takeSheet.updatedOn != null) return false;
        if (uuid != null ? !uuid.equals(takeSheet.uuid) : takeSheet.uuid != null) return false;
        if (verifiedBy != null ? !verifiedBy.equals(takeSheet.verifiedBy) : takeSheet.verifiedBy != null) return false;
        if (voidReason != null ? !voidReason.equals(takeSheet.voidReason) : takeSheet.voidReason != null) return false;
        if (voidedBy != null ? !voidedBy.equals(takeSheet.voidedBy) : takeSheet.voidedBy != null) return false;
        if (voidedOn != null ? !voidedOn.equals(takeSheet.voidedOn) : takeSheet.voidedOn != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (takeDate != null ? takeDate.hashCode() : 0);
        result = 31 * result + (referenceNo != null ? referenceNo.hashCode() : 0);
        result = 31 * result + (countedBy != null ? countedBy.hashCode() : 0);
        result = 31 * result + (verifiedBy != null ? verifiedBy.hashCode() : 0);
        result = 31 * result + (int) monthly;
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
