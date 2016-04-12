package org.msh.fdt.model;

import javax.persistence.*;

/**
 * Created by kenny on 8/19/14.
 */
@Entity
@Table(name = "regimen_drug", schema = "", catalog = "fdt")
@IdClass(RegimenDrugPK.class)
public class RegimenDrug {
    private int regimenId;
    private int drugId;

    @Id
    @Column(name = "regimen_id", nullable = false, insertable = true, updatable = true)
    public int getRegimenId() {
        return regimenId;
    }

    public void setRegimenId(int regimenId) {
        this.regimenId = regimenId;
    }

    @Id
    @Column(name = "drug_id", nullable = false, insertable = true, updatable = true)
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

        RegimenDrug that = (RegimenDrug) o;

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
