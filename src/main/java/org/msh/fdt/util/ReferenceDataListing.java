package org.msh.fdt.util;

import org.msh.fdt.model.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by kenny on 4/16/14.
 */
public class ReferenceDataListing implements Serializable {


    public List<ServiceType> getServiceTypeList() {
        return serviceTypeList;
    }

    public void setServiceTypeList(List<ServiceType> serviceTypeList) {
        this.serviceTypeList = serviceTypeList;
    }

    public List<PatientSource> getPatientSourceList() {
        return patientSourceList;
    }

    public void setPatientSourceList(List<PatientSource> patientSourceList) {
        this.patientSourceList = patientSourceList;
    }

    public List<SupportingOrganization> getSupportingOrganizationList() {
        return supportingOrganizationList;
    }

    public void setSupportingOrganizationList(List<SupportingOrganization> supportingOrganizationList) {
        this.supportingOrganizationList = supportingOrganizationList;
    }

    public List<District> getDistrictList() {
        return districtList;
    }

    public void setDistrictList(List<District> districtList) {
        this.districtList = districtList;
    }

    public List<Indication> getIndicationList() {
        return indicationList;
    }

    public void setIndicationList(List<Indication> indicationList) {
        this.indicationList = indicationList;
    }

    public List<PatientStatus> getPatientStatusList() {
        return patientStatusList;
    }

    public void setPatientStatusList(List<PatientStatus> patientStatusList) {
        this.patientStatusList = patientStatusList;
    }

    public List<RegimenChangeReason> getRegimenChangeReasonList() {
        return regimenChangeReasonList;
    }

    public void setRegimenChangeReasonList(List<RegimenChangeReason> regimenChangeReasonList) {
        this.regimenChangeReasonList = regimenChangeReasonList;
    }

    public List<FamilyPlanningMethod> getFamilyPlanningMethodList() {
        return familyPlanningMethodList;
    }

    public void setFamilyPlanningMethodList(List<FamilyPlanningMethod> familyPlanningMethodList) {
        this.familyPlanningMethodList = familyPlanningMethodList;
    }

    public List<FamilyPlanningMethodChangeReason> getFamilyPlanningMethodChangeReasonList() {
        return familyPlanningMethodChangeReasonList;
    }

    public void setFamilyPlanningMethodChangeReasonList(List<FamilyPlanningMethodChangeReason> familyPlanningMethodChangeReasonList) {
        this.familyPlanningMethodChangeReasonList = familyPlanningMethodChangeReasonList;
    }

    public List<Regimen> getRegimenList() {
        return regimenList;
    }

    public List<RegimenType> getRegimenTypeList() {
        return regimenTypeList;
    }

    public void setRegimenTypeList(List<RegimenType> regimenTypeList) {
        this.regimenTypeList = regimenTypeList;
    }

    public List<RegimenStatus> getRegimenStatusList() {
        return regimenStatusList;
    }

    public void setRegimenStatusList(List<RegimenStatus> regimenStatusList) {
        this.regimenStatusList = regimenStatusList;
    }

    public void setRegimenList(List<Regimen> regimenList) {
        this.regimenList = regimenList;
    }

    public List<VisitType> getVisitTypeList() {
        return visitTypeList;
    }

    public void setVisitTypeList(List<VisitType> visitTypeList) {
        this.visitTypeList = visitTypeList;
    }

    public List<GenericName> getGenericNameList() {
        return genericNameList;
    }

    public void setGenericNameList(List<GenericName> genericNameList) {
        this.genericNameList = genericNameList;
    }

    public List<DrugCategory> getDrugCategoryList() {
        return drugCategoryList;
    }

    public void setDrugCategoryList(List<DrugCategory> drugCategoryList) {
        this.drugCategoryList = drugCategoryList;
    }

    public List<DrugForm> getDrugFormList() {
        return drugFormList;
    }

    public void setDrugFormList(List<DrugForm> drugFormList) {
        this.drugFormList = drugFormList;
    }

    public List<DrugType> getDrugTypeList() {
        return drugTypeList;
    }

    public void setDrugTypeList(List<DrugType> drugTypeList) {
        this.drugTypeList = drugTypeList;
    }

    public List<Dosage> getDosageList() {
        return dosageList;
    }

    public void setDosageList(List<Dosage> dosageList) {
        this.dosageList = dosageList;
    }

    public List<DispensingUnit> getDispensingUnitList() {
        return dispensingUnitList;
    }

    public void setDispensingUnitList(List<DispensingUnit> dispensingUnitList) {
        this.dispensingUnitList = dispensingUnitList;
    }

    public List<TransactionType> getTransactionTypeList() {
        return transactionTypeList;
    }

    public void setTransactionTypeList(List<TransactionType> transactionTypeList) {
        this.transactionTypeList = transactionTypeList;
    }

    public List<RegimenDrug> getRegimenDrugList() {
        return regimenDrugList;
    }

    public void setRegimenDrugList(List<RegimenDrug> regimenDrugList) {
        this.regimenDrugList = regimenDrugList;
    }

    public List<Batch> getBatchList() {
        return batchList;
    }

    public void setBatchList(List<Batch> batchList) {
        this.batchList = batchList;
    }

    public List<Drug> getDrugList() {
        return drugList;
    }

    public void setDrugList(List<Drug> drugList) {
        this.drugList = drugList;
    }

    public String getSheetNo() {
        return sheetNo;
    }

    public void setSheetNo(String sheetNo) {
        this.sheetNo = sheetNo;
    }

    public List<Account> getAccountList() {
        return accountList;
    }

    public void setAccountList(List<Account> accountList) {
        this.accountList = accountList;
    }

    public BigDecimal getHeight() {
        return height;
    }

    public void setHeight(BigDecimal height) {
        this.height = height;
    }

    public Integer getPatientStatusId() {
        return patientStatusId;
    }

    public void setPatientStatusId(Integer patientStatusId) {
        this.patientStatusId = patientStatusId;
    }

    public List<Facility> getFacilityList() {
        return facilityList;
    }

    public void setFacilityList(List<Facility> facilityList) {
        this.facilityList = facilityList;
    }

    public int getRegimenID() {
        return regimenID;
    }

    public void setRegimenID(int regimenID) {
        this.regimenID = regimenID;
    }

    public List<IdentifierType> getIdentifierTypeList() {
        return identifierTypeList;
    }

    public void setIdentifierTypeList(List<IdentifierType> identifierTypeList) {
        this.identifierTypeList = identifierTypeList;
    }

    public List<Insulin> getInsulinList() {
        return insulinList;
    }

    public void setInsulinList(List<Insulin> insulinList) {
        this.insulinList = insulinList;
    }

    public List<BloodSugarLevel> getBloodSugarLevelList() {
        return bloodSugarLevelList;
    }

    public void setBloodSugarLevelList(List<BloodSugarLevel> bloodSugarLevelList) {
        this.bloodSugarLevelList = bloodSugarLevelList;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getAge() {
        return age;
    }

    public Timestamp getNextAppointmentDate() {
        return nextAppointmentDate;
    }

    public void setNextAppointmentDate(Timestamp nextAppointmentDate) {
        this.nextAppointmentDate = nextAppointmentDate;
    }


    public List<CdrrCategory> getCdrrCategoryList() {
        return cdrrCategoryList;
    }

    public void setCdrrCategoryList(List<CdrrCategory> cdrrCategoryList) {
        this.cdrrCategoryList = cdrrCategoryList;
    }

    public List<Account> getSuppliers() {
        return suppliers;
    }

    public void setSuppliers(List<Account> suppliers) {
        this.suppliers = suppliers;
    }

    public List getObjectList() {
        return objectList;
    }

    public void setObjectList(List objectList) {
        this.objectList = objectList;
    }

    public List<PatientIdentifier> getPatientIdentifierList() {
        return patientIdentifierList;
    }

    public void setPatientIdentifierList(List<PatientIdentifier> patientIdentifierList) {
        this.patientIdentifierList = patientIdentifierList;
    }

    public List getPrescriptionList() {
        return prescriptionList;
    }

    public void setPrescriptionList(List prescriptionList) {
        this.prescriptionList = prescriptionList;
    }

    private List<ServiceType> serviceTypeList;

    private List<PatientSource> patientSourceList;

    private List<SupportingOrganization> supportingOrganizationList;

    private List<District> districtList;

    private List<Indication> indicationList;

    private List<PatientStatus> patientStatusList;

    private List<RegimenChangeReason> regimenChangeReasonList;

    private List<FamilyPlanningMethod> familyPlanningMethodList;

    private List<FamilyPlanningMethodChangeReason> familyPlanningMethodChangeReasonList;

    private List<VisitType> visitTypeList;

    private List<Regimen> regimenList;

    private List<RegimenType> regimenTypeList;

    private List<RegimenStatus> regimenStatusList;

    private List<GenericName> genericNameList;

    private List<DrugCategory> drugCategoryList;

    private List<DrugForm> drugFormList;

    private List<DrugType> drugTypeList;

    private List<Dosage> dosageList;

    private List<DispensingUnit> dispensingUnitList;

    private List<TransactionType> transactionTypeList;

    private List<Account> accountList;

    private List drugList;

    private List<Batch> batchList;

    private List<RegimenDrug> regimenDrugList;

    private List<Facility> facilityList;

    private List<IdentifierType> identifierTypeList;

    private List<Account> suppliers;

    private List<Insulin> insulinList;

    private List<BloodSugarLevel> bloodSugarLevelList;

    private List<CdrrCategory> cdrrCategoryList;

    private List<PatientIdentifier> patientIdentifierList;

    private List objectList;

    private String sheetNo;

    private int regimenID;

    private BigDecimal height;

    private Integer patientStatusId;

    private Integer age;

    private Timestamp nextAppointmentDate;
    private List prescriptionList;

}
