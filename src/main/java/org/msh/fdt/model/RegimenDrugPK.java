package org.msh.fdt.model;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by kenny on 8/19/14.
 */
public class RegimenDrugPK implements Serializable {
    private int regimenId;
    private int drugId;

    @Column(name = "regimen_id", nullable = false, insertable = true, updatable = true)
    @Id
    public int getRegimenId() {
        return regimenId;
    }

    public void setRegimenId(int regimenId) {
        this.regimenId = regimenId;
    }

    @Column(name = "drug_id", nullable = false, insertable = true, updatable = true)
    @Id
    public int getDrugId() {
        return drugId;
    }

    public void setDrugId(int drugId) {
        this.drugId = drugId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RegimenDrugPK that = (RegimenDrugPK) o;

        if (drugId != that.drugId) return false;
        if (regimenId != that.regimenId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = regimenId;
        result = 31 * result + drugId;
        return result;
    }
}
