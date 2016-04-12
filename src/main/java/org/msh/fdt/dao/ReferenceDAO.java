package org.msh.fdt.dao;

import org.msh.fdt.model.*;

import java.util.List;

/**
 * Created by kenny on 4/8/14.
 */
public interface ReferenceDAO {

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

    public Integer saveSupportingOrganization(SupportingOrganization supportingOrganization);

    public Integer saveRegion(Region region);

    public Integer savePatientStatus(PatientStatus patientStatus);

    public Integer saveIndication(Indication indication);

    public Integer saveRegimenChangeReason(RegimenChangeReason regimenChangeReason);

    public Integer saveVisitType(VisitType visitType);

    public Integer saveRegimenStatus(RegimenStatus regimenStatus);

    public Integer saveDrugCategory(DrugCategory drugCategory);

    public Integer saveDrugForm(DrugForm drugForm);

    public Integer saveDrugType(DrugType drugType);

    public Integer saveAccount(Account account);

    public Integer saveFamilyPlanningMethod(FamilyPlanningMethod planningMethod);

    public Integer saveFamilyPlanningMethodChangeReason(FamilyPlanningMethodChangeReason changeReason);

    public Integer saveInsulin(Insulin insulinType);

    public Integer saveBloodSugarLevel(BloodSugarLevel bloodSugarLevel);

    public Integer saveCDRRCategory(CdrrCategory category);

    /**
     * Listing functions
     */
    public List<AccountType> listAccountType();

    public List<RegimenType> listRegimentType();

    public List<Dosage> listDosage();

    public List<DispensingUnit> listDispensingUnit();

    public List<GenericName> listGenericName();

    public List<District> listDistrict();

    public List<IdentifierType> listIdentifierType();

    public List<PatientSource> listPatientSource();

    public List<ServiceType> listServiceType();

    public List<SupportingOrganization> listSupportingOrganization();

    public List<TransactionType> listTransactionType();

    public List<Region> listRegion();

    public List<PatientStatus> listPatientStatus();

    public List<Indication> listIndication();

    public List<RegimenChangeReason> listRegimenChangeReason();

    public List<VisitType> listVisitType();

    public List<RegimenStatus> listRegimenStatus();

    public List<DrugCategory> listDrugCategory();

    public List<DrugForm> listDrugForm();

    public List<DrugType> listDrugType();

    public List<Account> listAccount();

    public List<Account> listStores();

    public List<RegimenDrug> listRegimenDrug();

    public List<FamilyPlanningMethod> listFamilyPlanningMethod();

    public List<FamilyPlanningMethodChangeReason> listFamilyPlanningMethodChangeReason();

    public List<Insulin> listInsulinType();

    public List<BloodSugarLevel> listRandomBloodSugarLevel();

    public List<CdrrCategory> listCDRRCategoty();
    /**
     *
     *  Update functions
     */

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

    /**
     *
     * @param regimenTypeId
     * @param userId
     */
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

    public void deleteAccountTypeTransactionType(Integer accountTypeId) ;

    public void saveAccountTypeTransactionType(AccountTypeTransactionType accountTypeTransactionType);

    public void deleteFamilyPlanningMethod(Integer familyPlanningMethodId, Integer userId);

    public void deleteFamilyPlanningMethodChangeReason(Integer familyPlanningMethodChangeReasonId, Integer userId);

    public void deleteInsulin(Integer insulin, Integer userId);

    public void deleteBloodSugarLevel(Integer bloodSugarLevelId, Integer userId);

    public void deleteCDRRCategory(Integer categoryID, Integer userId);


    public String getServiceName(Integer serviceTypeId);

    public String getPatientStatusName(Integer patientStatusId);

    public String getDrugName(Integer drugId);

    public Integer createProperty(Property property);

    public void updateProperty(Property property);

    public void deleteProperty(Integer propertyId, Integer userId);

    public Property getProperty(String key);

    public List<ServiceType> getSubServices(Integer serviceId);

    public ServiceType getServiceType(Integer serviceTypeId);

    public ServiceType getServiceType(String serviceName);

    public PatientStatus getPatientStatus(String name);

    public TransactionType getTransactionType(String name);

    public VisitType getVisitType(String name);

    public List<Facility> listFacility();

    public Account getAccount(Integer accountId);

    public List<Account> listAccounts(String type);

    public List<PatientIdentifier> listPatientIdentifier();
}
