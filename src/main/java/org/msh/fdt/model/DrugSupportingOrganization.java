package org.msh.fdt.model;

import javax.persistence.*;

/**
 * Created by kenny on 8/19/14.
 */
@Entity
@Table(name = "drug_supporting_organization", schema = "", catalog = "fdt")
@IdClass(DrugSupportingOrganizationPK.class)
public class DrugSupportingOrganization {
    private int drugId;
    private int supportingOrganizationId;
    private SupportingOrganization supportingOrganizationBySupportingOrganizationId;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "drug_id", nullable = false, insertable = true, updatable = true)
    public int getDrugId() {
        return drugId;
    }

    public void setDrugId(int drugId) {
        this.drugId = drugId;
    }

    @Id
    @Column(name = "supporting_organization_id", nullable = false, insertable = true, updatable = true)
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

        DrugSupportingOrganization that = (DrugSupportingOrganization) o;

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

    @ManyToOne
    @JoinColumn(name = "supporting_organization_id", referencedColumnName = "id", nullable = false, insertable=false, updatable=false)
    public SupportingOrganization getSupportingOrganizationBySupportingOrganizationId() {
        return supportingOrganizationBySupportingOrganizationId;
    }

    public void setSupportingOrganizationBySupportingOrganizationId(SupportingOrganization supportingOrganizationBySupportingOrganizationId) {
        this.supportingOrganizationBySupportingOrganizationId = supportingOrganizationBySupportingOrganizationId;
    }
}
