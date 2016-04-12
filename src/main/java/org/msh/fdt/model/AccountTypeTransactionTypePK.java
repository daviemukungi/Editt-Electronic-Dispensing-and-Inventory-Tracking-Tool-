package org.msh.fdt.model;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by kenny on 8/19/14.
 */
public class AccountTypeTransactionTypePK implements Serializable {
    private int accountTypeId;
    private int transactionTypeId;

    @Column(name = "account_type_id", nullable = false, insertable = true, updatable = true)
    @Id
    public int getAccountTypeId() {
        return accountTypeId;
    }

    public void setAccountTypeId(int accountTypeId) {
        this.accountTypeId = accountTypeId;
    }

    @Column(name = "transaction_type_id", nullable = false, insertable = true, updatable = true)
    @Id
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

        AccountTypeTransactionTypePK that = (AccountTypeTransactionTypePK) o;

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
}
