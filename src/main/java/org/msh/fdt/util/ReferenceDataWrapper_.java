package org.msh.fdt.util;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.msh.fdt.dto.*;
import org.msh.fdt.model.*;
import org.msh.fdt.model.Account;
import org.msh.fdt.model.District;
import org.msh.fdt.model.PatientSource;
import org.msh.fdt.model.ServiceType;
import org.msh.fdt.model.SupportingOrganization;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kenny on 4/8/14.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class ReferenceDataWrapper_ implements Serializable {

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public ServiceType getServiceType() {
        return serviceType;
    }

    public void setServiceType(ServiceType serviceType) {
        this.serviceType = serviceType;
    }

    public RegimenType getRegimenType() {
        return regimenType;
    }

    public void setRegimenType(RegimenType regimenType) {
        this.regimenType = regimenType;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public IdentifierType getIdentifierType() {
        return identifierType;
    }

    public void setIdentifierType(IdentifierType identifierType) {
        this.identifierType = identifierType;
    }

    public DispensingUnit getDispensingUnit() {
        return dispensingUnit;
    }

    public void setDispensingUnit(DispensingUnit dispensingUnit) {
        this.dispensingUnit = dispensingUnit;
    }

    public Dosage getDosage() {
        return dosage;
    }

    public void setDosage(Dosage dosage) {
        this.dosage = dosage;
    }

    public District getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    public GenericName getGenericName() {
        return genericName;
    }

    public void setGenericName(GenericName genericName) {
        this.genericName = genericName;
    }

    public PatientSource getPatientSource() {
        return patientSource;
    }

    public void setPatientSource(PatientSource patientSource) {
        this.patientSource = patientSource;
    }


    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }


    @JsonProperty("Result")
    public String getResult() {
        return Result;
    }

    public void setResult(String result) {
        Result = result;
    }

    @JsonProperty("Records")
    public List<?> getRecords() {
        if(Records == null)
            Records = new ArrayList<Object>();
        return Records;
    }

    public void setRecords(List<?> records) {
        Records = records;
    }

    public SupportingOrganization getSupportingOrganization() {
        return supportingOrganization;
    }

    public void setSupportingOrganization(SupportingOrganization supportingOrganization) {
        this.supportingOrganization = supportingOrganization;
    }

    public org.msh.fdt.dto.Region getRegion() {
        return region;
    }

    public void setRegion(org.msh.fdt.dto.Region region) {
        this.region = region;
    }

    public PatientStatus getPatientStatus() {
        return patientStatus;
    }

    public void setPatientStatus(PatientStatus patientStatus) {
        this.patientStatus = patientStatus;
    }

    public Indication getIndication() {
        return indication;
    }

    public void setIndication(Indication indication) {
        this.indication = indication;
    }

    public RegimenChangeReason getRegimenChangeReason() {
        return regimenChangeReason;
    }

    public void setRegimenChangeReason(RegimenChangeReason regimenChangeReason) {
        this.regimenChangeReason = regimenChangeReason;
    }

    public VisitType getVisitType() {
        return visitType;
    }

    public void setVisitType(VisitType visitType) {
        this.visitType = visitType;
    }

    public RegimenStatus getRegimenStatus() {
        return regimenStatus;
    }

    public void setRegimenStatus(RegimenStatus regimenStatus) {
        this.regimenStatus = regimenStatus;
    }

    public DrugCategory getDrugCategory() {
        return drugCategory;
    }

    public void setDrugCategory(DrugCategory drugCategory) {
        this.drugCategory = drugCategory;
    }

    public DrugForm getDrugForm() {
        return drugForm;
    }

    public void setDrugForm(DrugForm drugForm) {
        this.drugForm = drugForm;
    }

    public DrugType getDrugType() {
        return drugType;
    }

    public void setDrugType(DrugType drugType) {
        this.drugType = drugType;
    }


    public FamilyPlanningMethod getPlanningMethod() {
        return planningMethod;
    }

    public void setPlanningMethod(FamilyPlanningMethod planningMethod) {
        this.planningMethod = planningMethod;
    }

    public FamilyPlanningMethodChangeReason getChangeReason() {
        return changeReason;
    }

    public void setChangeReason(FamilyPlanningMethodChangeReason changeReason) {
        this.changeReason = changeReason;
    }

    public Insulin getInsulin() {
        return insulin;
    }

    public void setInsulin(Insulin insulin) {
        this.insulin = insulin;
    }

    public BloodSugarLevel getBloodSugarLevel() {
        return bloodSugarLevel;
    }

    public void setBloodSugarLevel(BloodSugarLevel bloodSugarLevel) {
        this.bloodSugarLevel = bloodSugarLevel;
    }

    public CdrrCategory getCdrrCategory() {
        return cdrrCategory;
    }

    public void setCdrrCategory(CdrrCategory cdrrCategory) {
        this.cdrrCategory = cdrrCategory;
    }

    private AccountType accountType;

    private ServiceType serviceType;

    private RegimenType regimenType;

    private TransactionType transactionType;

    private IdentifierType identifierType;

    private DispensingUnit dispensingUnit;

    private Dosage dosage;

    private District district;

    private GenericName genericName;

    private PatientSource patientSource;

    private Account account;

    private SupportingOrganization supportingOrganization;

    private org.msh.fdt.dto.Region region;

    private PatientStatus patientStatus;

    private Indication indication;

    private RegimenChangeReason regimenChangeReason;

    private VisitType visitType;

    private RegimenStatus regimenStatus;

    private DrugCategory drugCategory;

    private DrugForm drugForm;

    private DrugType drugType;

    private FamilyPlanningMethod planningMethod;

    private FamilyPlanningMethodChangeReason changeReason;

    private Insulin insulin;

    private BloodSugarLevel bloodSugarLevel;

    private CdrrCategory cdrrCategory;

    private String Result = null;

    private List<?> Records = null;

}
