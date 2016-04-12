package org.msh.fdt.model;

import javax.persistence.*;

/**
 * Created by kenny on 8/19/14.
 */
@Entity
@Table(name = "account_type_transaction_type", schema = "", catalog = "fdt")
@IdClass(AccountTypeTransactionTypePK.class)
public class AccountTypeTransactionType {
    private int accountTypeId;
    private int transactionTypeId;
    private AccountType accountTypeByAccountTypeId;
    private TransactionType transactionTypeByTransactionTypeId;

    @Id
    @Column(name = "account_type_id", nullable = false, insertable = true, updatable = true)
    public int getAccountTypeId() {
        return accountTypeId;
    }

    public void setAccountTypeId(int accountTypeId) {
        this.accountTypeId = accountTypeId;
    }

    @Id
    @Column(name = "transaction_type_id", nullable = false, insertable = true, updatable = true)
    public int getTransactionTypeId() {
        return transactionTypeId;
    }

    public void setTransactionTypeId(int transactionTypeId) {
        this.transactionTypeId = transactionTypeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AccountTypeTransactionType that = (AccountTypeTransactionType) o;

        if (accountTypeId != that.accountTypeId) return false;
        if (transactionTypeId != that.transactionTypeId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = accountTypeId;
        result = 31 * result + transactionTypeId;
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
    @JoinColumn(name = "transaction_type_id", referencedColumnName = "id", nullable = false, insertable=false, updatable=false)
    public TransactionType getTransactionTypeByTransactionTypeId() {
        return transactionTypeByTransactionTypeId;
    }

    public void setTransactionTypeByTransactionTypeId(TransactionType transactionTypeByTransactionTypeId) {
        this.transactionTypeByTransactionTypeId = transactionTypeByTransactionTypeId;
    }
}
