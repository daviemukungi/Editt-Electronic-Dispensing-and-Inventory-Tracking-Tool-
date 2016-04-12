package org.msh.fdt.model;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by kenny on 8/19/14.
 */
public class DrugSupportingOrganizationPK implements Serializable {
    private int drugId;
    private int supportingOrganizationId;

    @Column(name = "drug_id", nullable = false, insertable = true, updatable = true)
    @Id
    public int getDrugId() {
        return drugId;
    }

    public void setDrugId(int drugId) {
        this.drugId = drugId;
    }

    @Column(name = "supporting_organization_id", nullable = false, insertable = true, updatable = true)
    @Id
    public int getSupportingOrganizationId() {
        return supportingOrganizationId;
    }

    public void setSupportingOrganizationId(int supportingOrganizationId) {
        this.supportingOrganizationId = supportingOrganizationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DrugSupportingOrganizationPK that = (DrugSupportingOrganizationPK) o;

        if (drugId != that.drugId) return false;
        if (supportingOrganizationId != that.supportingOrganizationId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = drugId;
        result = 31 * result + supportingOrganizationId;
        return result;
    }
}
