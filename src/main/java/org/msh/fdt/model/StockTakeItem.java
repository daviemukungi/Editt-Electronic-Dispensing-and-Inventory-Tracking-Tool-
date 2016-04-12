package org.msh.fdt.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by kenny on 10/23/14.
 */
@Entity
@javax.persistence.Table(name = "stock_take_item", schema = "", catalog = "fdt")
public class StockTakeItem {
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

    private int drugId;

    @Basic
    @javax.persistence.Column(name = "drug_id", nullable = false, insertable = true, updatable = true)
    public int getDrugId() {
        return drugId;
    }

    public void setDrugId(int drugId) {
        this.drugId = drugId;
    }

    private int accountId;

    @Basic
    @javax.persistence.Column(name = "account_id", nullable = false, insertable = true, updatable = true)
    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    private String batchNo;

    @Basic
    @javax.persistence.Column(name = "batch_no", nullable = false, insertable = true, updatable = true, length = 45)
    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    private BigDecimal systemCount;

    @Basic
    @javax.persistence.Column(name = "system_count", nullable = false, insertable = true, updatable = true, precision = 2)
    public BigDecimal getSystemCount() {
        return systemCount;
    }

    public void setSystemCount(BigDecimal systemCount) {
        this.systemCount = systemCount;
    }

    private String packSize;

    @Basic
    @javax.persistence.Column(name = "pack_Size", nullable = true, insertable = true, updatable = true, precision = 2)
    public String getPackSize() {
        return packSize;
    }

    public void setPackSize(String packSize) {
        this.packSize = packSize;
    }

    private Integer packs;
    @Basic
    @javax.persistence.Column(name = "packs", nullable = true, insertable = true, updatable = true, precision = 2)
    public Integer getPacks() {
        return packs;
    }

    public void setPacks(Integer packs) {
        this.packs = packs;
    }

    private Integer looseQty;

    @Basic
    @javax.persistence.Column(name = "looseQty", nullable = true, insertable = true, updatable = true, precision = 2)
    public Integer getLooseQty() {
        return looseQty;
    }

    public void setLooseQty(Integer looseQty) {
        if(looseQty == null){
            looseQty=0;
        }
        this.looseQty = looseQty;
    }

    private Timestamp expiryDate;

    @Basic
    @javax.persistence.Column(name = "expiry_date", nullable = true, insertable = true, updatable = true)
    public Timestamp getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Timestamp expiryDate) {
        this.expiryDate = expiryDate;
    }

    private BigDecimal price;

    @Basic
    @javax.persistence.Column(name = "price", nullable = true, insertable = true, updatable = true, precision = 2)
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    private byte adjusted;

    @Basic
    @javax.persistence.Column(name = "adjusted", nullable = false, insertable = true, updatable = true)
    public byte getAdjusted() {
        return adjusted;
    }

    public void setAdjusted(byte adjusted) {
        this.adjusted = adjusted;
    }

    private Integer stockTakeSheetId;

    @Basic
    @javax.persistence.Column(name = "stock_take_sheet_id", nullable = true, insertable = true, updatable = true)
    public Integer getStockTakeSheetId() {
        return stockTakeSheetId;
    }

    public void setStockTakeSheetId(Integer stockTakeSheetId) {
        this.stockTakeSheetId = stockTakeSheetId;
    }

    private String uuid;

    @Basic
    @javax.persistence.Column(name = "uuid", nullable = true, insertable = true, updatable = true, length = 38)
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

        StockTakeItem takeItem = (StockTakeItem) o;

        if (accountId != takeItem.accountId) return false;
        if (adjusted != takeItem.adjusted) return false;
        if (createdBy != takeItem.createdBy) return false;
        if (drugId != takeItem.drugId) return false;
        if (id != takeItem.id) return false;
        if (voided != takeItem.voided) return false;
        if (batchNo != null ? !batchNo.equals(takeItem.batchNo) : takeItem.batchNo != null) return false;
        if (createdOn != null ? !createdOn.equals(takeItem.createdOn) : takeItem.createdOn != null) return false;
        if (expiryDate != null ? !expiryDate.equals(takeItem.expiryDate) : takeItem.expiryDate != null) return false;
        if (price != null ? !price.equals(takeItem.price) : takeItem.price != null) return false;
        if (stockTakeSheetId != null ? !stockTakeSheetId.equals(takeItem.stockTakeSheetId) : takeItem.stockTakeSheetId != null)
            return false;
        if (systemCount != null ? !systemCount.equals(takeItem.systemCount) : takeItem.systemCount != null)
            return false;
        if (updatedBy != null ? !updatedBy.equals(takeItem.updatedBy) : takeItem.updatedBy != null) return false;
        if (updatedOn != null ? !updatedOn.equals(takeItem.updatedOn) : takeItem.updatedOn != null) return false;
        if (uuid != null ? !uuid.equals(takeItem.uuid) : takeItem.uuid != null) return false;
        if (voidReason != null ? !voidReason.equals(takeItem.voidReason) : takeItem.voidReason != null) return false;
        if (voidedBy != null ? !voidedBy.equals(takeItem.voidedBy) : takeItem.voidedBy != null) return false;
        if (voidedOn != null ? !voidedOn.equals(takeItem.voidedOn) : takeItem.voidedOn != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + drugId;
        result = 31 * result + accountId;
        result = 31 * result + (batchNo != null ? batchNo.hashCode() : 0);
        result = 31 * result + (systemCount != null ? systemCount.hashCode() : 0);
        result = 31 * result + (expiryDate != null ? expiryDate.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (int) adjusted;
        result = 31 * result + (stockTakeSheetId != null ? stockTakeSheetId.hashCode() : 0);
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
