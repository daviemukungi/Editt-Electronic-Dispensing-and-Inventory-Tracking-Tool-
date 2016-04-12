package org.msh.fdt.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collection;

/**
 * Created by kenny on 9/17/14.
 */
@Entity
@Table(name = "transaction_item", schema = "", catalog = "fdt")
public class TransactionItem {
    private int id;
    private int transactionId;
    private int drugId;
    private int accountId;
    private String batchNo;
    private BigDecimal unitsIn;
    private BigDecimal unitsOut;
    private String legacyPk;
    private String uuid;
    private int createdBy;
    private Timestamp createdOn;
    private Integer updatedBy;
    private Timestamp updatedOn;
    private byte voided;
    private Integer voidedBy;
    private Timestamp voidedOn;
    private String voidReason;
    private BigDecimal unitCost;
    private Integer totalCost;
    private Collection<BatchTransactionItem> batchTransactionItemsById;
    private Collection<PatientTransactionItem> patientTransactionItemsById;
    private Account accountByAccountId;
    private Transaction transactionByTransactionId;
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
    @Column(name = "transaction_id", nullable = false, insertable = true, updatable = true)
    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
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
    @Column(name = "account_id", nullable = false, insertable = true, updatable = true)
    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    @Basic
    @Column(name = "batch_no", nullable = true, insertable = true, updatable = true, length = 45)
    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    @Basic
    @Column(name = "units_in", nullable = true, insertable = true, updatable = true, precision = 2)
    public BigDecimal getUnitsIn() {
        return unitsIn;
    }

    public void setUnitsIn(BigDecimal unitsIn) {
        this.unitsIn = unitsIn;
    }

    @Basic
    @Column(name = "units_out", nullable = true, insertable = true, updatable = true, precision = 2)
    public BigDecimal getUnitsOut() {
        return unitsOut;
    }

    public void setUnitsOut(BigDecimal unitsOut) {
        this.unitsOut = unitsOut;
    }

    @Basic
    @Column(name = "legacy_pk", nullable = true, insertable = true, updatable = true, length = 45)
    public String getLegacyPk() {
        return legacyPk;
    }

    public void setLegacyPk(String legacyPk) {
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

    @Basic
    @Column(name = "unit_cost", nullable = true, insertable = true, updatable = true, precision = 2)
    public BigDecimal getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(BigDecimal unitCost) {
        this.unitCost = unitCost;
    }

    @Basic
    @Column(name = "total_cost", nullable = true, insertable = true, updatable = true)
    public Integer getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Integer totalCost) {
        this.totalCost = totalCost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TransactionItem that = (TransactionItem) o;

        if (accountId != that.accountId) return false;
        if (createdBy != that.createdBy) return false;
        if (drugId != that.drugId) return false;
        if (id != that.id) return false;
        if (transactionId != that.transactionId) return false;
        if (voided != that.voided) return false;
        if (batchNo != null ? !batchNo.equals(that.batchNo) : that.batchNo != null) return false;
        if (createdOn != null ? !createdOn.equals(that.createdOn) : that.createdOn != null) return false;
        if (legacyPk != null ? !legacyPk.equals(that.legacyPk) : that.legacyPk != null) return false;
        if (totalCost != null ? !totalCost.equals(that.totalCost) : that.totalCost != null) return false;
        if (unitCost != null ? !unitCost.equals(that.unitCost) : that.unitCost != null) return false;
        if (unitsIn != null ? !unitsIn.equals(that.unitsIn) : that.unitsIn != null) return false;
        if (unitsOut != null ? !unitsOut.equals(that.unitsOut) : that.unitsOut != null) return false;
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
        result = 31 * result + transactionId;
        result = 31 * result + drugId;
        result = 31 * result + accountId;
        result = 31 * result + (batchNo != null ? batchNo.hashCode() : 0);
        result = 31 * result + (unitsIn != null ? unitsIn.hashCode() : 0);
        result = 31 * result + (unitsOut != null ? unitsOut.hashCode() : 0);
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
        result = 31 * result + (unitCost != null ? unitCost.hashCode() : 0);
        result = 31 * result + (totalCost != null ? totalCost.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "transactionItemByTransactionItemId")
    public Collection<BatchTransactionItem> getBatchTransactionItemsById() {
        return batchTransactionItemsById;
    }

    public void setBatchTransactionItemsById(Collection<BatchTransactionItem> batchTransactionItemsById) {
        this.batchTransactionItemsById = batchTransactionItemsById;
    }

    @OneToMany(mappedBy = "transactionItemByTransactionItemId")
    public Collection<PatientTransactionItem> getPatientTransactionItemsById() {
        return patientTransactionItemsById;
    }

    public void setPatientTransactionItemsById(Collection<PatientTransactionItem> patientTransactionItemsById) {
        this.patientTransactionItemsById = patientTransactionItemsById;
    }

    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id", nullable = false, insertable=false, updatable=false)
    public Account getAccountByAccountId() {
        return accountByAccountId;
    }

    public void setAccountByAccountId(Account accountByAccountId) {
        this.accountByAccountId = accountByAccountId;
    }

    @ManyToOne
    @JoinColumn(name = "transaction_id", referencedColumnName = "id", nullable = false, insertable=false, updatable=false)
    public Transaction getTransactionByTransactionId() {
        return transactionByTransactionId;
    }

    public void setTransactionByTransactionId(Transaction transactionByTransactionId) {
        this.transactionByTransactionId = transactionByTransactionId;
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
