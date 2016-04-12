package org.msh.fdt.model;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by kenny on 8/19/14.
 */
@Entity
@Table(name = "batch_transaction_item", schema = "", catalog = "fdt")
public class BatchTransactionItem {
    private int id;
    private Integer transactionItemId;
    private Integer noOfPacks;
    private Integer packSize;
    private Integer openPacks;
    private Timestamp dateOfManufacture;
    private Timestamp dateOfExpiry;
    private Integer legacyPk;
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
    private TransactionItem transactionItemByTransactionItemId;

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
    @Column(name = "transaction_item_id", nullable = true, insertable = true, updatable = true)
    public Integer getTransactionItemId() {
        return transactionItemId;
    }

    public void setTransactionItemId(Integer transactionItemId) {
        this.transactionItemId = transactionItemId;
    }

    @Basic
    @Column(name = "no_of_packs", nullable = true, insertable = true, updatable = true)
    public Integer getNoOfPacks() {
        return noOfPacks;
    }

    public void setNoOfPacks(Integer noOfPacks) {
        this.noOfPacks = noOfPacks;
    }

    @Basic
    @Column(name = "pack_size", nullable = true, insertable = true, updatable = true)
    public Integer getPackSize() {
        return packSize;
    }

    public void setPackSize(Integer packSize) {
        this.packSize = packSize;
    }

    @Basic
    @Column(name = "open_packs", nullable = true, insertable = true, updatable = true)
    public Integer getOpenPacks() {
        return openPacks;
    }

    public void setOpenPacks(Integer openPacks) {
        this.openPacks = openPacks;
    }

    @Basic
    @Column(name = "date_of_manufacture", nullable = true, insertable = true, updatable = true)
    public Timestamp getDateOfManufacture() {
        return dateOfManufacture;
    }

    public void setDateOfManufacture(Timestamp dateOfManufacture) {
        this.dateOfManufacture = dateOfManufacture;
    }

    @Basic
    @Column(name = "date_of_expiry", nullable = true, insertable = true, updatable = true)
    public Timestamp getDateOfExpiry() {
        return dateOfExpiry;
    }

    public void setDateOfExpiry(Timestamp dateOfExpiry) {
        this.dateOfExpiry = dateOfExpiry;
    }

    @Basic
    @Column(name = "legacy_pk", nullable = true, insertable = true, updatable = true)
    public Integer getLegacyPk() {
        return legacyPk;
    }

    public void setLegacyPk(Integer legacyPk) {
        this.legacyPk = legacyPk;
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

        BatchTransactionItem that = (BatchTransactionItem) o;

        if (createdBy != that.createdBy) return false;
        if (id != that.id) return false;
        if (voided != that.voided) return false;
        if (createdOn != null ? !createdOn.equals(that.createdOn) : that.createdOn != null) return false;
        if (dateOfExpiry != null ? !dateOfExpiry.equals(that.dateOfExpiry) : that.dateOfExpiry != null) return false;
        if (dateOfManufacture != null ? !dateOfManufacture.equals(that.dateOfManufacture) : that.dateOfManufacture != null)
            return false;
        if (legacyPk != null ? !legacyPk.equals(that.legacyPk) : that.legacyPk != null) return false;
        if (noOfPacks != null ? !noOfPacks.equals(that.noOfPacks) : that.noOfPacks != null) return false;
        if (openPacks != null ? !openPacks.equals(that.openPacks) : that.openPacks != null) return false;
        if (packSize != null ? !packSize.equals(that.packSize) : that.packSize != null) return false;
        if (transactionItemId != null ? !transactionItemId.equals(that.transactionItemId) : that.transactionItemId != null)
            return false;
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
        result = 31 * result + (transactionItemId != null ? transactionItemId.hashCode() : 0);
        result = 31 * result + (noOfPacks != null ? noOfPacks.hashCode() : 0);
        result = 31 * result + (packSize != null ? packSize.hashCode() : 0);
        result = 31 * result + (openPacks != null ? openPacks.hashCode() : 0);
        result = 31 * result + (dateOfManufacture != null ? dateOfManufacture.hashCode() : 0);
        result = 31 * result + (dateOfExpiry != null ? dateOfExpiry.hashCode() : 0);
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

    @ManyToOne
    @JoinColumn(name = "transaction_item_id", referencedColumnName = "id", insertable=false, updatable=false)
    public TransactionItem getTransactionItemByTransactionItemId() {
        return transactionItemByTransactionItemId;
    }

    public void setTransactionItemByTransactionItemId(TransactionItem transactionItemByTransactionItemId) {
        this.transactionItemByTransactionItemId = transactionItemByTransactionItemId;
    }
}
