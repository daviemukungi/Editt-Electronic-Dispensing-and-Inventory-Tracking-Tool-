package org.msh.fdt.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

/**
 * Created by kenny on 8/19/14.
 */
@Entity
@Table(name = "account", schema = "", catalog = "fdt")
public class Account {
    private int id;
    private String name;
    private String description;
    private int accountTypeId;
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
    private byte 	Is_satellite;
    private byte  Is_bulkstore;
    private byte Cannot_dispense;
    private AccountType accountTypeByAccountTypeId;
    private User userByUpdatedBy;
    private User userByCreatedBy;
    private User userByVoidedBy;
    private Collection<TransactionItem> transactionItemsById;

    public Account() {
    }

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
    @Column(name = "description", nullable = true, insertable = true, updatable = true, length = 45)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "account_type_id", nullable = false, insertable = true, updatable = true)
    public int getAccountTypeId() {
        return accountTypeId;
    }

    public void setAccountTypeId(int accountTypeId) {
        this.accountTypeId = accountTypeId;
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

    @Column(name = "Is_satellite", nullable = false, insertable = true, updatable = true, length = 1)
    public byte getIs_satellite() {
        return Is_satellite;
    }

    public void setIs_satellite(byte is_satellite) {
        Is_satellite = is_satellite;
    }

    @Column(name = "Is_bulkstore", nullable = false, insertable = true, updatable = true, length = 1)
    public byte getIs_bulkstore() {
        return Is_bulkstore;
    }

    public void setIs_bulkstore(byte is_bulkstore) {
        Is_bulkstore = is_bulkstore;
    }

    @Column(name = "Cannot_dispense", nullable = false, insertable = true, updatable = true, length = 1)
    public byte getCannot_dispense() {
        return Cannot_dispense;
    }

    public void setCannot_dispense(byte cannot_dispense) {
        Cannot_dispense = cannot_dispense;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Account account = (Account) o;

        if (accountTypeId != account.accountTypeId) return false;
        if (createdBy != account.createdBy) return false;
        if (id != account.id) return false;
        if (voided != account.voided) return false;
        if (createdOn != null ? !createdOn.equals(account.createdOn) : account.createdOn != null) return false;
        if (description != null ? !description.equals(account.description) : account.description != null) return false;
        if (legacyPk != null ? !legacyPk.equals(account.legacyPk) : account.legacyPk != null) return false;
        if (name != null ? !name.equals(account.name) : account.name != null) return false;
        if (updatedBy != null ? !updatedBy.equals(account.updatedBy) : account.updatedBy != null) return false;
        if (updatedOn != null ? !updatedOn.equals(account.updatedOn) : account.updatedOn != null) return false;
        if (uuid != null ? !uuid.equals(account.uuid) : account.uuid != null) return false;
        if (voidReason != null ? !voidReason.equals(account.voidReason) : account.voidReason != null) return false;
        if (voidedBy != null ? !voidedBy.equals(account.voidedBy) : account.voidedBy != null) return false;
        if (voidedOn != null ? !voidedOn.equals(account.voidedOn) : account.voidedOn != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + accountTypeId;
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
    @JoinColumn(name = "account_type_id", referencedColumnName = "id", nullable = false, insertable=false, updatable=false)
    public AccountType getAccountTypeByAccountTypeId() {
        return accountTypeByAccountTypeId;
    }

    public void setAccountTypeByAccountTypeId(AccountType accountTypeByAccountTypeId) {
        this.accountTypeByAccountTypeId = accountTypeByAccountTypeId;
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

    @OneToMany(mappedBy = "accountByAccountId")
    public Collection<TransactionItem> getTransactionItemsById() {
        return transactionItemsById;
    }

    public void setTransactionItemsById(Collection<TransactionItem> transactionItemsById) {
        this.transactionItemsById = transactionItemsById;
    }


}
