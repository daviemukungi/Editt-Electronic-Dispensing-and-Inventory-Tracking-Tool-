package org.msh.fdt.service;

import org.msh.fdt.model.*;

import java.util.List;

/**
 * Created by kenny on 4/8/14.
 */
public interface ReferenceService {

    public Integer saveAccountType(AccountType accountType);

    public Integer saveServiceType(ServiceType serviceType);

    public Integer saveRegimenType(RegimenType regimenType);

    public Integer saveTransactionType(TransactionType transactionType);

    public Integer saveIdentifierType(IdentifierType identifierType);

    public Integer saveDispensingUnit(DispensingUnit dispensingUnit);

    public Integer saveDosage(Dosage dosage);

    public Integer saveDistrict(District district);

    public Integer saveGenericName(GenericName genericName);

    public Integer savePatientSource(PatientSource patientSource);

    public Integer addAccount(Account account);

    public Integer saveSupportingOrganization(SupportingOrganization o);

    public Integer saveRegion(Region region);

    public Integer savePatientStatus(PatientStatus patientStatus);

    public Integer saveIndication(Indication indication);

    public Integer saveRegimenChangeReason(RegimenChangeReason regimenChangeReason);

    public Integer saveVisitType(VisitType visitType);

    public Integer saveRegimenStatus(RegimenStatus regimenStatus);

    public Integer saveDrugCategory(DrugCategory drugCategory);

    public Integer saveDrugType(DrugType drugType);

    public Integer saveDrugForm(DrugForm drugForm);

    public Integer saveAccount(Account account);

    public Integer saveFamilyPlanningMethod(FamilyPlanningMethod planningMethod);

    public Integer saveFamilyPlanningMethodChangeReason(FamilyPlanningMethodChangeReason methodChangeReason);

    public Integer saveInsulin(Insulin insulin);

    public Integer saveBloodSugarLevel(BloodSugarLevel bloodSugarLevel);

    public Integer saveCDRRCategory(CdrrCategory category);

    public List<AccountType> accountTypeList();

    public List<RegimenType> regimenTypeList();

    public List<Dosage> dosageList();

    public List<DispensingUnit> dispensingUnitList();

    public List<GenericName> genericNameList();

    public List<District> districtList();

    public List<IdentifierType> identifierTypeList();

    public List<PatientSource> patientSourceList();

    public List<ServiceType> serviceTypeList();

    public List<SupportingOrganization> supportingOrganizationList();

    public List<TransactionType> transactionTypeList();

    public List<Region> regionList();

    public List<PatientStatus> patientStatusList();

    public List<Indication> indicationList();

    public List<RegimenChangeReason> regimenChangeReasonList();

    public List<VisitType> visitTypeList();

    public List<RegimenStatus> regimenStatusList();

    public List<DrugCategory> drugCategoryList();

    public List<DrugForm> drugFormList();

    public List<DrugType> drugTypeList();

    public List<Account> accountList();

    public List<Account> storeList();

    public List<RegimenDrug> listRegimenDrug();

    public List<FamilyPlanningMethod> listFamilyPlanningMethod();

    public List<FamilyPlanningMethodChangeReason> listFamilyPlanningMethodChangeReason();

    public List<Insulin> listInsulin();

    public List<BloodSugarLevel> listBloodSugarLevel();

    public List<CdrrCategory> listCDRRCategory();


    public void updateAccountType(AccountType accountType);

    public void updateRegimenType(RegimenType regimenType);

    public void updateDosage(Dosage dosage);

    public void updateDispensingUnit(DispensingUnit dispensingUnit);

    public void updateGenericName(GenericName genericName);

    public void updateDistrict(District district);

    public void updateIdentifierType(IdentifierType identifierType);

    public void updatePatientSource(PatientSource patientSource);

    public void updateServiceType(ServiceType serviceType);

    public void updateSupportingOrganization(SupportingOrganization supportingOrganization);

    public void updateTransactionType(TransactionType transactionType);

    public void updateRegion(Region region);

    public void updatePatientStatus(PatientStatus patientStatus);

    public void updateIndication(Indication indication);

    public void updateRegimenChangeReason(RegimenChangeReason regimenChangeReason);

    public void updateVisitType(VisitType visitType);

    public void updateRegimenStatus(RegimenStatus regimenStatus);

    public void updateDrugCategory(DrugCategory drugCategory);

    public void updateDrugForm(DrugForm drugForm);

    public void updateDrugType(DrugType drugType);

    public void updateAccount(Account account);

    public void updateFamilyPlanningMethod(FamilyPlanningMethod planningMethod);

    public void updateFamilyPlanningMethodChangeReason(FamilyPlanningMethodChangeReason methodChangeReason);

    public void updateInsulin(Insulin insulinType);

    public void updateBloodSugarLevel(BloodSugarLevel bloodSugarLevel);

    public void updateCDRRCategory(CdrrCategory cdrrCategory);

    /**
     *
     *  Delete functions
     */

    public void deleteAccountType(Integer id, Integer userId);

    public void deleteRegimenType(Integer regimenTypeId, Integer userId);

    public void deleteDosage(Integer Id, Integer userId);

    public void deleteDispensingUnit(Integer dispensingUnitId, Integer userId);

    public void deleteGenericName(Integer genericNameId, Integer userId);

    public void deleteDistrict(Integer districtId, Integer userId);

    public void deleteIdentifierType(Integer identifierTypeId, Integer userId);

    public void deletePatientSource(Integer patientSourceId, Integer userId);

    public void deleteServiceType(Integer serviceTypeId, Integer userId);

    public void deleteSupportingOrganization(Integer supportingOrganizationId, Integer userId);

    public void deleteTransactionType(Integer transactionTypeId, Integer userId);

    public void deleteRegion(Integer regionId, Integer userId);

    public void deletePatientStatus(Integer patientStatusId, Integer userId);

    public void deleteIndication(Integer indicationId, Integer userId);

    public void deleteRegimenChangeReason(Integer regimenChangeReasonId, Integer userId);

    public void deleteVisitType(Integer visitTypeId, Integer userId);

    public void deleteRegimenStatus(Integer regimenStatusId, Integer userId);

    public void deleteDrugCategory(Integer drugCategoryId, Integer userId);

    public void deleteDrugForm(Integer drugFormId, Integer userId);

    public void deleteDrugType(Integer drugTypeId, Integer userId);

    public void deleteAccount(Integer accountId, Integer userId);

    public void deleteFamilyPlanningMethod(Integer planningMethodId, Integer userId);

    public void deleteFamilyPlanningMethodChangeReason(Integer methodChangeReasonId, Integer userId);

    public void deleteInsulin(Integer insulinType, Integer userId);

    public void deleteBloodSugarLevel(Integer randomBloodSugarLevelId, Integer userId);

    public void deleteCDRRCategory(Integer categoryID, Integer userId);

    public List<Facility> listFacility();

    public Account getAccount(Integer accountId);

    public List<Account> listAccounts(String type);

    public List<PatientIdentifier> listPatientIdentifiers();
}
