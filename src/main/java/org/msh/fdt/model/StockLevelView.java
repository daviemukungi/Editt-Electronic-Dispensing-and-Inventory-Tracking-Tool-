package org.msh.fdt.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by kenny on 3/17/15.
 */
@Entity
@Table(name = "stock_level_view", schema = "", catalog = "fdt")
public class StockLevelView {
    private Timestamp date;
    private BigDecimal difference;

    @Basic
    @Column(name = "date", nullable = true, insertable = true, updatable = true)
    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    @Basic
    @Column(name = "difference", nullable = true, insertable = true, updatable = true, precision = 2)
    public BigDecimal getDifference() {
        return difference;
    }

    public void setDifference(BigDecimal difference) {
        this.difference = difference;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StockLevelView that = (StockLevelView) o;

        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        if (difference != null ? !difference.equals(that.difference) : that.difference != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = date != null ? date.hashCode() : 0;
        result = 31 * result + (difference != null ? difference.hashCode() : 0);
        return result;
    }
}
