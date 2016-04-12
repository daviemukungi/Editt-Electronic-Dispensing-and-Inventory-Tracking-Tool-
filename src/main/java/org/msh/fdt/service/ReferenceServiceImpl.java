package org.msh.fdt.service;

import org.msh.fdt.dao.ReferenceDAO;
import org.msh.fdt.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by kenny on 4/8/14.
 */
@Service
public class ReferenceServiceImpl implements ReferenceService{

    @Autowired
    private ReferenceDAO referenceDAO;

    @Override
    @Transactional
    public Integer saveAccountType(AccountType accountType) {
        Integer id = referenceDAO.saveAccountType(accountType);
        return id;
    }

    @Override
    @Transactional
    public Integer saveServiceType(ServiceType serviceType) {
        Integer id = referenceDAO.saveServiceType(serviceType);
        return id;
    }

    @Override
    @Transactional
    public Integer saveRegimenType(RegimenType regimenType) {
        Integer id = referenceDAO.saveRegimenType(regimenType);
        return id;
    }

    @Override
    @Transactional
    public Integer saveTransactionType(TransactionType transactionType) {
        Integer id = referenceDAO.saveTransactionType(transactionType);
        return id;
    }

    @Override
    @Transactional
    public Integer saveIdentifierType(IdentifierType identifierType) {
        Integer id = referenceDAO.saveIdentifierType(identifierType);
        return id;
    }

    @Override
    @Transactional
    public Integer saveDispensingUnit(DispensingUnit dispensingUnit) {
        Object id = referenceDAO.saveDispensingUnit(dispensingUnit);
        return (Integer)id;
    }

    @Override
    @Transactional
    public Integer saveDosage(Dosage dosage) {
        Integer id = referenceDAO.saveDosage(dosage);
        return id;
    }

    @Override
    @Transactional
    public Integer saveDistrict(District district) {
        Integer id = referenceDAO.saveDistrict(district);
        return id;
    }

    @Override
    @Transactional
    public Integer saveGenericName(GenericName genericName) {
        Integer id = referenceDAO.saveGenericName(genericName);
        return id;
    }

    @Override
    @Transactional
    public Integer savePatientSource(PatientSource patientSource) {
        Integer id = referenceDAO.savePatientSource(patientSource);
        return id;
    }



    @Override
    @Transactional
    public Integer addAccount(Account account) {
        return referenceDAO.addAccount(account);
    }

    @Override
    @Transactional
    public Integer saveSupportingOrganization(SupportingOrganization o) {
        return referenceDAO.saveSupportingOrganization(o);
    }

    @Override
    @Transactional
    public Integer saveRegion(Region region) {
        return referenceDAO.saveRegion(region);
    }

    @Override
    @Transactional
    public Integer savePatientStatus(PatientStatus patientStatus) {
        return referenceDAO.savePatientStatus(patientStatus);
    }

    @Override
    @Transactional
    public Integer saveIndication(Indication indication) {
        return referenceDAO.saveIndication(indication);
    }

    @Override
    @Transactional
    public Integer saveRegimenChangeReason(RegimenChangeReason regimenChangeReason) {
        return referenceDAO.saveRegimenChangeReason(regimenChangeReason);
    }

    @Override
    @Transactional
    public Integer saveVisitType(VisitType visitType) {
        return  referenceDAO.saveVisitType(visitType);
    }

    @Override
    @Transactional
    public Integer saveRegimenStatus(RegimenStatus regimenStatus) {
        return referenceDAO.saveRegimenStatus(regimenStatus);
    }

    @Override
    @Transactional
    public Integer saveDrugType(DrugType drugType) {
        return referenceDAO.saveDrugType(drugType);
    }

    @Override
    @Transactional
    public Integer saveDrugForm(DrugForm drugForm) {
        return referenceDAO.saveDrugForm(drugForm);
    }

    @Override
    @Transactional
    public Integer saveDrugCategory(DrugCategory drugCategory) {
        return referenceDAO.saveDrugCategory(drugCategory);
    }

    @Override
    @Transactional
    public Integer saveAccount(Account account) {
        return referenceDAO.saveAccount(account);
    }

    @Override
    @Transactional
    public Integer saveFamilyPlanningMethod(FamilyPlanningMethod planningMethod) {
        return referenceDAO.saveFamilyPlanningMethod(planningMethod);
    }

    @Override
    @Transactional
    public Integer saveFamilyPlanningMethodChangeReason(FamilyPlanningMethodChangeReason methodChangeReason) {
        return referenceDAO.saveFamilyPlanningMethodChangeReason(methodChangeReason);
    }

    @Override
    @Transactional
    public Integer saveBloodSugarLevel(BloodSugarLevel bloodSugarLevel) {
        return referenceDAO.saveBloodSugarLevel(bloodSugarLevel);
    }

    @Override
    @Transactional
    public Integer saveCDRRCategory(CdrrCategory category) {
        return referenceDAO.saveCDRRCategory(category);
    }

    @Override
    @Transactional
    public Integer saveInsulin(Insulin insulin) {
        return referenceDAO.saveInsulin(insulin);
    }

    @Override
    @Transactional
    public List<AccountType> accountTypeList() {
        return referenceDAO.listAccountType();
    }

    @Override
    @Transactional
    public List<RegimenType> regimenTypeList() {
        return referenceDAO.listRegimentType();
    }

    @Override
    @Transactional
    public List<Dosage> dosageList() {
        return referenceDAO.listDosage();
    }

    @Override
    @Transactional
    public List<DispensingUnit> dispensingUnitList() {
        return referenceDAO.listDispensingUnit();
    }

    @Override
    @Transactional
    public List<GenericName> genericNameList() {
        return referenceDAO.listGenericName();
    }

    @Override
    @Transactional
    public List<District> districtList() {
        return referenceDAO.listDistrict();
    }

    @Override
    @Transactional
    public List<IdentifierType> identifierTypeList() {
        return referenceDAO.listIdentifierType();
    }



    @Override
    @Transactional
    public List<PatientSource> patientSourceList() {
        return referenceDAO.listPatientSource();
    }

    @Override
    @Transactional
    public List<ServiceType> serviceTypeList() {
        return referenceDAO.listServiceType();
    }

    @Override
    @Transactional
    public List<SupportingOrganization> supportingOrganizationList() {
        return referenceDAO.listSupportingOrganization();
    }

    @Override
    @Transactional
    public List<TransactionType> transactionTypeList() {
        return referenceDAO.listTransactionType();
    }

    @Override
    @Transactional
    public List<Region> regionList() {
        return referenceDAO.listRegion();
    }

    @Override
    @Transactional
    public List<PatientStatus> patientStatusList() {
        return referenceDAO.listPatientStatus();
    }

    @Override
    @Transactional
    public List<Indication> indicationList() {
        return referenceDAO.listIndication();
    }

    @Override
    @Transactional
    public List<RegimenChangeReason> regimenChangeReasonList() {
        return referenceDAO.listRegimenChangeReason();
    }

    @Override
    @Transactional
    public List<VisitType> visitTypeList() {
        return referenceDAO.listVisitType();
    }

    @Override
    @Transactional
    public List<RegimenStatus> regimenStatusList() {
        return referenceDAO.listRegimenStatus();
    }

    @Override
    @Transactional
    public List<DrugCategory> drugCategoryList() {
        return referenceDAO.listDrugCategory();
    }

    @Override
    @Transactional
    public List<DrugForm> drugFormList() {
        return referenceDAO.listDrugForm();
    }

    @Override
    @Transactional
    public List<DrugType> drugTypeList() {
        return referenceDAO.listDrugType();
    }

    @Override
    @Transactional
    public List<Account> accountList() {
        return referenceDAO.listAccount();
    }

    @Override
    @Transactional
    public List<Account> storeList() {
        return referenceDAO.listStores();
    }

    @Override
    @Transactional
    public List<RegimenDrug> listRegimenDrug() {
        return referenceDAO.listRegimenDrug();
    }

    @Override
    @Transactional
    public List<FamilyPlanningMethod> listFamilyPlanningMethod() {
        return referenceDAO.listFamilyPlanningMethod();
    }

    @Override
    @Transactional
    public List<FamilyPlanningMethodChangeReason> listFamilyPlanningMethodChangeReason() {
        return referenceDAO.listFamilyPlanningMethodChangeReason();
    }

    @Override
    @Transactional
    public List<BloodSugarLevel> listBloodSugarLevel() {
        return referenceDAO.listRandomBloodSugarLevel();
    }

    @Override
    @Transactional
    public List<Insulin> listInsulin() {
        return referenceDAO.listInsulinType();
    }

    @Override
    @Transactional
    public List<CdrrCategory> listCDRRCategory() {
        return referenceDAO.listCDRRCategoty();
    }

    @Override
    @Transactional
    public void updateAccountType(AccountType accountType) {
        referenceDAO.updateAccountType(accountType);
    }

    @Override
    @Transactional
    public void updateRegimenType(RegimenType regimenType) {
        referenceDAO.updateRegimenType(regimenType);
    }

    @Override
    @Transactional
    public void updateDosage(Dosage dosage) {
        referenceDAO.updateDosage(dosage);
    }

    @Override
    @Transactional
    public void updateDispensingUnit(DispensingUnit dispensingUnit) {
        referenceDAO.updateDispensingUnit(dispensingUnit);
    }

    @Override
    @Transactional
    public void updateGenericName(GenericName genericName) {
        referenceDAO.updateGenericName(genericName);
    }

    @Override
    @Transactional
    public void updateDistrict(District district) {
        referenceDAO.updateDistrict(district);
    }

    @Override
    @Transactional
    public void updateIdentifierType(IdentifierType identifierType) {
        referenceDAO.updateIdentifierType(identifierType);
    }

    @Override
    @Transactional
    public void updatePatientSource(PatientSource patientSource) {
        referenceDAO.updatePatientSource(patientSource);
    }

    @Override
    @Transactional
    public void updateServiceType(ServiceType serviceType) {
        referenceDAO.updateServiceType(serviceType);
    }

    @Override
    @Transactional
    public void updateSupportingOrganization(SupportingOrganization supportingOrganization) {
        referenceDAO.updateSupportingOrganization(supportingOrganization);
    }

    @Override
    @Transactional
    public void updateTransactionType(TransactionType transactionType) {
        referenceDAO.updateTransactionType(transactionType);
    }

    @Override
    @Transactional
    public void updateRegion(Region region) {
        referenceDAO.updateRegion(region);
    }

    @Override
    @Transactional
    public void updatePatientStatus(PatientStatus patientStatus) {
        referenceDAO.updatePatientStatus(patientStatus);
    }

    @Override
    @Transactional
    public void updateIndication(Indication indication) {
        referenceDAO.updateIndication(indication);
    }

    @Override
    @Transactional
    public void updateRegimenChangeReason(RegimenChangeReason regimenChangeReason) {
        referenceDAO.updateRegimenChangeReason(regimenChangeReason);
    }

    @Override
    @Transactional
    public void updateVisitType(VisitType visitType) {
        referenceDAO.updateVisitType(visitType);
    }

    @Override
    @Transactional
    public void updateRegimenStatus(RegimenStatus regimenStatus) {
        referenceDAO.updateRegimenStatus(regimenStatus);
    }

    @Override
    @Transactional
    public void updateDrugType(DrugType drugType) {
        referenceDAO.updateDrugType(drugType);
    }

    @Override
    @Transactional
    public void updateDrugCategory(DrugCategory drugCategory) {
        referenceDAO.updateDrugCategory(drugCategory);
    }

    @Override
    @Transactional
    public void updateDrugForm(DrugForm drugForm) {
        referenceDAO.updateDrugForm(drugForm);
    }

    @Override
    @Transactional
    public void updateAccount(Account account) {
        referenceDAO.updateAccount(account);
    }

    @Override
    @Transactional
    public void updateFamilyPlanningMethod(FamilyPlanningMethod planningMethod) {
        referenceDAO.updateFamilyPlanningMethod(planningMethod);
    }

    @Override
    @Transactional
    public void updateFamilyPlanningMethodChangeReason(FamilyPlanningMethodChangeReason methodChangeReason) {
        referenceDAO.updateFamilyPlanningMethodChangeReason(methodChangeReason);
    }


    @Override
    @Transactional
    public void updateInsulin(Insulin insulin) {
        referenceDAO.updateInsulin(insulin);
    }

    @Override
    @Transactional
    public void updateBloodSugarLevel(BloodSugarLevel bloodSugarLevel) {
        referenceDAO.updateBloodSugarLevel(bloodSugarLevel);
    }

    @Override
    @Transactional
    public void updateCDRRCategory(CdrrCategory cdrrCategory) {
        referenceDAO.updateCDRRCategory(cdrrCategory);
    }

    /**
     *  Update functions
     * @param
     */
    @Override
    @Transactional
    public void deleteAccountType(Integer accountTypeId, Integer userId) {
        referenceDAO.deleteAccountType(accountTypeId, userId);
    }

    @Override
    @Transactional
    public void deleteRegimenType(Integer regimenTypeId, Integer userId) {
        referenceDAO.deleteRegimenType(regimenTypeId, userId);
    }

    @Override
    @Transactional
    public void deleteDosage(Integer dosageId, Integer userId) {
        referenceDAO.deleteDosage(dosageId, userId);
    }

    @Override
    @Transactional
    public void deleteDispensingUnit(Integer dispensingUnitId, Integer userId) {
        referenceDAO.deleteDispensingUnit(dispensingUnitId, userId);
    }

    @Override
    @Transactional
    public void deleteGenericName(Integer genericNameId, Integer userId) {
        referenceDAO.deleteGenericName(genericNameId, userId);
    }

    @Override
    @Transactional
    public void deleteDistrict(Integer districtId, Integer userId) {
        referenceDAO.deleteDistrict(districtId, userId);
    }

    @Override
    public void deleteIdentifierType(Integer identifierTypeId, Integer userId) {
        referenceDAO.deleteIdentifierType(identifierTypeId, userId);
    }

    @Override
    @Transactional
    public void deletePatientSource(Integer patientSourceId, Integer userId) {
        referenceDAO.deletePatientStatus(patientSourceId, userId);
    }

    @Override
    @Transactional
    public void deleteServiceType(Integer serviceTypeId, Integer userId) {
        referenceDAO.deleteServiceType(serviceTypeId, userId);
    }

    @Override
    @Transactional
    public void deleteSupportingOrganization(Integer supportingOrganizationId, Integer userId) {
        referenceDAO.deleteSupportingOrganization(supportingOrganizationId, userId);
    }

    @Override
    @Transactional
    public void deleteTransactionType(Integer transactionTypeId, Integer userId) {
        referenceDAO.deleteTransactionType(transactionTypeId, userId);
    }

    @Override
    @Transactional
    public void deleteRegion(Integer regionId, Integer userId) {
        referenceDAO.deleteRegion(regionId, userId);
    }

    @Override
    @Transactional
    public void deletePatientStatus(Integer patientStatusId, Integer userId) {
        referenceDAO.deletePatientStatus(patientStatusId, userId);
    }

    @Override
    @Transactional
    public void deleteIndication(Integer indicationId, Integer userId) {
        referenceDAO.deleteIndication(indicationId, userId);
    }

    @Override
    @Transactional
    public void deleteRegimenChangeReason(Integer regimenChangeReasonId, Integer userId) {
        referenceDAO.deleteRegimenChangeReason(regimenChangeReasonId, userId);
    }

    @Override
    @Transactional
    public void deleteVisitType(Integer visitTypeId, Integer userId) {
        referenceDAO.deleteVisitType(visitTypeId, userId);
    }

    @Override
    @Transactional
    public void deleteRegimenStatus(Integer regimenStatusId, Integer userId) {
        referenceDAO.deleteRegimenStatus(regimenStatusId, userId);
    }

    @Override
    @Transactional
    public void deleteDrugCategory(Integer drugCategoryId, Integer userId) {
        referenceDAO.deleteDrugCategory(drugCategoryId, userId);
    }

    @Override
    @Transactional
    public void deleteDrugForm(Integer drugFormId, Integer userId) {
        referenceDAO.deleteDrugForm(drugFormId, userId);
    }

    @Override
    @Transactional
    public void deleteDrugType(Integer drugTypeId, Integer userId) {
        referenceDAO.deleteDrugType(drugTypeId, userId);
    }

    @Override
    @Transactional
    public void deleteAccount(Integer accountId, Integer userId) {
        referenceDAO.deleteAccount(accountId, userId);
    }

    @Override
    @Transactional
    public void deleteFamilyPlanningMethod(Integer planningMethodId, Integer userId) {
        referenceDAO.deleteFamilyPlanningMethod(planningMethodId, userId);
    }

    @Override
    @Transactional
    public void deleteFamilyPlanningMethodChangeReason(Integer methodChangeReasonId, Integer userId) {
        referenceDAO.deleteFamilyPlanningMethodChangeReason(methodChangeReasonId, userId);
    }

    @Override
    @Transactional
    public void deleteInsulin(Integer insulinType, Integer userId) {
        referenceDAO.deleteInsulin(insulinType, userId);
    }

    @Override
    @Transactional
    public void deleteBloodSugarLevel(Integer bloodSugarLevelId, Integer userId) {
        referenceDAO.deleteBloodSugarLevel(bloodSugarLevelId, userId);
    }

    @Override
    @Transactional
    public void deleteCDRRCategory(Integer categoryID, Integer userId) {
        referenceDAO.deleteCDRRCategory(categoryID, userId);
    }

    @Override
    @Transactional
    public List<Facility> listFacility() {
        return referenceDAO.listFacility();
    }

    @Override
    @Transactional
    public Account getAccount(Integer accountId) {
        return referenceDAO.getAccount(accountId);
    }

    @Override
    @Transactional
    public List<Account> listAccounts(String type) {
        return referenceDAO.listAccounts(type);
    }

    @Override
    @Transactional
    public List<PatientIdentifier> listPatientIdentifiers() {
        return referenceDAO.listPatientIdentifier();
    }
}
