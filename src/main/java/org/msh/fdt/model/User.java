package org.msh.fdt.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

/**
 * Created by kenny on 1/22/15.
 */
@Entity
@Table(name = "user", schema = "", catalog = "fdt")
public class User {
    private int id;
    private Integer personId;
    private String username;
    private String password;
    private String secretQuestion;
    private String secretAnswer;
    private String uuid;
    private int createdBy;
    private Timestamp createdOn;
    private Integer updatedBy;
    private Timestamp updatedOn;
    private byte voided;
    private Integer voidedBy;
    private Timestamp voidedOn;
    private String voidReason;
    private Collection<Account> accountsById;
    private Collection<Account> accountsById_0;
    private Collection<Account> accountsById_1;
    private Collection<AccountType> accountTypesById;
    private Collection<AccountType> accountTypesById_0;
    private Collection<AccountType> accountTypesById_1;
    private Collection<BatchTransactionItem> batchTransactionItemsById;
    private Collection<BatchTransactionItem> batchTransactionItemsById_0;
    private Collection<BatchTransactionItem> batchTransactionItemsById_1;
    private Collection<BloodSugarLevel> bloodSugarLevelsById;
    private Collection<BloodSugarLevel> bloodSugarLevelsById_0;
    private Collection<BloodSugarLevel> bloodSugarLevelsById_1;
    private Collection<CdrrCategory> cdrrCategoriesById;
    private Collection<CdrrCategory> cdrrCategoriesById_0;
    private Collection<CdrrCategory> cdrrCategoriesById_1;
    private Collection<DispensingUnit> dispensingUnitsById;
    private Collection<DispensingUnit> dispensingUnitsById_0;
    private Collection<DispensingUnit> dispensingUnitsById_1;
    private Collection<District> districtsById;
    private Collection<District> districtsById_0;
    private Collection<District> districtsById_1;
    private Collection<Dosage> dosagesById;
    private Collection<Dosage> dosagesById_0;
    private Collection<Dosage> dosagesById_1;
    private Collection<Dosage> dosagesById_2;
    private Collection<DrugCategory> drugCategoriesById;
    private Collection<DrugCategory> drugCategoriesById_0;
    private Collection<DrugCategory> drugCategoriesById_1;
    private Collection<DrugForm> drugFormsById;
    private Collection<DrugForm> drugFormsById_0;
    private Collection<DrugForm> drugFormsById_1;
    private Collection<DrugType> drugTypesById;
    private Collection<DrugType> drugTypesById_0;
    private Collection<DrugType> drugTypesById_1;
    private Collection<Facility> facilitiesById;
    private Collection<Facility> facilitiesById_0;
    private Collection<Facility> facilitiesById_1;
    private Collection<GenericName> genericNamesById;
    private Collection<GenericName> genericNamesById_0;
    private Collection<GenericName> genericNamesById_1;
    private Collection<IdentifierType> identifierTypesById;
    private Collection<IdentifierType> identifierTypesById_0;
    private Collection<IdentifierType> identifierTypesById_1;
    private Collection<Indication> indicationsById;
    private Collection<Indication> indicationsById_0;
    private Collection<Indication> indicationsById_1;
    private Collection<Insulin> insulinsById;
    private Collection<Insulin> insulinsById_0;
    private Collection<Insulin> insulinsById_1;
    private Collection<PatientIdentifier> patientIdentifiersById;
    private Collection<PatientIdentifier> patientIdentifiersById_0;
    private Collection<PatientIdentifier> patientIdentifiersById_1;
    private Collection<PatientSource> patientSourcesById;
    private Collection<PatientSource> patientSourcesById_0;
    private Collection<PatientSource> patientSourcesById_1;
    private Collection<PatientStatus> patientStatusesById;
    private Collection<PatientStatus> patientStatusesById_0;
    private Collection<PatientStatus> patientStatusesById_1;
    private Collection<PatientTransactionItem> patientTransactionItemsById;
    private Collection<PatientTransactionItem> patientTransactionItemsById_0;
    private Collection<PatientTransactionItem> patientTransactionItemsById_1;
    private Collection<Person> personsById;
    private Collection<Person> personsById_0;
    private Collection<Person> personsById_1;
    private Collection<Prescription> prescriptionsById;
    private Collection<Prescription> prescriptionsById_0;
    private Collection<Prescription> prescriptionsById_1;
    private Collection<PrescriptionItem> prescriptionItemsById;
    private Collection<PrescriptionItem> prescriptionItemsById_0;
    private Collection<PrescriptionItem> prescriptionItemsById_1;
    private Collection<Privilege> privilegesById;
    private Collection<Privilege> privilegesById_0;
    private Collection<Privilege> privilegesById_1;
    private Collection<Property> propertiesById;
    private Collection<Property> propertiesById_0;
    private Collection<Property> propertiesById_1;
    private Collection<RegimenChangeReason> regimenChangeReasonsById;
    private Collection<RegimenChangeReason> regimenChangeReasonsById_0;
    private Collection<RegimenChangeReason> regimenChangeReasonsById_1;
    private Collection<RegimenStatus> regimenStatusesById;
    private Collection<RegimenStatus> regimenStatusesById_0;
    private Collection<RegimenStatus> regimenStatusesById_1;
    private Collection<RegimenType> regimenTypesById;
    private Collection<RegimenType> regimenTypesById_0;
    private Collection<RegimenType> regimenTypesById_1;
    private Collection<Region> regionsById;
    private Collection<Region> regionsById_0;
    private Collection<Region> regionsById_1;
    private Collection<Role> rolesById;
    private Collection<Role> rolesById_0;
    private Collection<Role> rolesById_1;
    private Collection<ServiceType> serviceTypesById;
    private Collection<ServiceType> serviceTypesById_0;
    private Collection<ServiceType> serviceTypesById_1;
    private Collection<StockTakeSheet> stockTakeSheetsById;
    private Collection<StockTakeSheet> stockTakeSheetsById_0;
    private Collection<StockTakeSheet> stockTakeSheetsById_1;
    private Collection<SupportingOrganization> supportingOrganizationsById;
    private Collection<SupportingOrganization> supportingOrganizationsById_0;
    private Collection<SupportingOrganization> supportingOrganizationsById_1;
    private Collection<Transaction> transactionsById;
    private Collection<Transaction> transactionsById_0;
    private Collection<Transaction> transactionsById_1;
    private Collection<TransactionItem> transactionItemsById;
    private Collection<TransactionItem> transactionItemsById_0;
    private Collection<TransactionItem> transactionItemsById_1;
    private Collection<TransactionType> transactionTypesById;
    private Collection<TransactionType> transactionTypesById_0;
    private Collection<TransactionType> transactionTypesById_1;
    private Person personByPersonId;
    private User userByUpdatedBy;
    private Collection<User> usersById;
    private User userByCreatedBy;
    private Collection<User> usersById_0;
    private User userByVoidedBy;
    private Collection<User> usersById_1;
    private Collection<UserRole> userRolesById;
    private Collection<VisitType> visitTypesById;
    private Collection<VisitType> visitTypesById_0;
    private Collection<VisitType> visitTypesById_1;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "person_id", nullable = true, insertable = true, updatable = true)
    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    @Basic
    @Column(name = "username", nullable = false, insertable = true, updatable = true, length = 45)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "password", nullable = true, insertable = true, updatable = true, length = 128)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "secret_question", nullable = true, insertable = true, updatable = true, length = 255)
    public String getSecretQuestion() {
        return secretQuestion;
    }

    public void setSecretQuestion(String secretQuestion) {
        this.secretQuestion = secretQuestion;
    }

    @Basic
    @Column(name = "secret_answer", nullable = true, insertable = true, updatable = true, length = 255)
    public String getSecretAnswer() {
        return secretAnswer;
    }

    public void setSecretAnswer(String secretAnswer) {
        this.secretAnswer = secretAnswer;
    }

    @Basic
    @Column(name = "uuid", nullable = false, insertable = true, updatable = true, length = 36)
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Basic
    @Column(name = "created_by", nullable = false, insertable = true, updatable = true)
    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    @Basic
    @Column(name = "created_on", nullable = false, insertable = true, updatable = true)
    public Timestamp getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Timestamp createdOn) {
        this.createdOn = createdOn;
    }

    @Basic
    @Column(name = "updated_by", nullable = true, insertable = true, updatable = true)
    public Integer getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Integer updatedBy) {
        this.updatedBy = updatedBy;
    }

    @Basic
    @Column(name = "updated_on", nullable = true, insertable = true, updatable = true)
    public Timestamp getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Timestamp updatedOn) {
        this.updatedOn = updatedOn;
    }

    @Basic
    @Column(name = "voided", nullable = false, insertable = true, updatable = true)
    public byte getVoided() {
        return voided;
    }

    public void setVoided(byte voided) {
        this.voided = voided;
    }

    @Basic
    @Column(name = "voided_by", nullable = true, insertable = true, updatable = true)
    public Integer getVoidedBy() {
        return voidedBy;
    }

    public void setVoidedBy(Integer voidedBy) {
        this.voidedBy = voidedBy;
    }

    @Basic
    @Column(name = "voided_on", nullable = true, insertable = true, updatable = true)
    public Timestamp getVoidedOn() {
        return voidedOn;
    }

    public void setVoidedOn(Timestamp voidedOn) {
        this.voidedOn = voidedOn;
    }

    @Basic
    @Column(name = "void_reason", nullable = true, insertable = true, updatable = true, length = 255)
    public String getVoidReason() {
        return voidReason;
    }

    public void setVoidReason(String voidReason) {
        this.voidReason = voidReason;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (createdBy != user.createdBy) return false;
        if (id != user.id) return false;
        if (voided != user.voided) return false;
        if (createdOn != null ? !createdOn.equals(user.createdOn) : user.createdOn != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        if (personId != null ? !personId.equals(user.personId) : user.personId != null) return false;
        if (secretAnswer != null ? !secretAnswer.equals(user.secretAnswer) : user.secretAnswer != null) return false;
        if (secretQuestion != null ? !secretQuestion.equals(user.secretQuestion) : user.secretQuestion != null)
            return false;
        if (updatedBy != null ? !updatedBy.equals(user.updatedBy) : user.updatedBy != null) return false;
        if (updatedOn != null ? !updatedOn.equals(user.updatedOn) : user.updatedOn != null) return false;
        if (username != null ? !username.equals(user.username) : user.username != null) return false;
        if (uuid != null ? !uuid.equals(user.uuid) : user.uuid != null) return false;
        if (voidReason != null ? !voidReason.equals(user.voidReason) : user.voidReason != null) return false;
        if (voidedBy != null ? !voidedBy.equals(user.voidedBy) : user.voidedBy != null) return false;
        if (voidedOn != null ? !voidedOn.equals(user.voidedOn) : user.voidedOn != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (personId != null ? personId.hashCode() : 0);
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (secretQuestion != null ? secretQuestion.hashCode() : 0);
        result = 31 * result + (secretAnswer != null ? secretAnswer.hashCode() : 0);
        result = 31 * result + (uuid != null ? uuid.hashCode() : 0);
        result = 31 * result + createdBy;
        result = 31 * result + (createdOn != null ? createdOn.hashCode() : 0);
        result = 31 * result + (updatedBy != null ? updatedBy.hashCode() : 0);
        result = 31 * result + (updatedOn != null ? updatedOn.hashCode() : 0);
        result = 31 * result + (int) voided;
        result = 31 * result + (voidedBy != null ? voidedBy.hashCode() : 0);
        result = 31 * result + (voidedOn != null ? voidedOn.hashCode() : 0);
        result = 31 * result + (voidReason != null ? voidReason.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "userByUpdatedBy")
    public Collection<Account> getAccountsById() {
        return accountsById;
    }

    public void setAccountsById(Collection<Account> accountsById) {
        this.accountsById = accountsById;
    }

    @OneToMany(mappedBy = "userByCreatedBy")
    public Collection<Account> getAccountsById_0() {
        return accountsById_0;
    }

    public void setAccountsById_0(Collection<Account> accountsById_0) {
        this.accountsById_0 = accountsById_0;
    }

    @OneToMany(mappedBy = "userByVoidedBy")
    public Collection<Account> getAccountsById_1() {
        return accountsById_1;
    }

    public void setAccountsById_1(Collection<Account> accountsById_1) {
        this.accountsById_1 = accountsById_1;
    }

    @OneToMany(mappedBy = "userByUpdatedBy")
    public Collection<AccountType> getAccountTypesById() {
        return accountTypesById;
    }

    public void setAccountTypesById(Collection<AccountType> accountTypesById) {
        this.accountTypesById = accountTypesById;
    }

    @OneToMany(mappedBy = "userByCreatedBy")
    public Collection<AccountType> getAccountTypesById_0() {
        return accountTypesById_0;
    }

    public void setAccountTypesById_0(Collection<AccountType> accountTypesById_0) {
        this.accountTypesById_0 = accountTypesById_0;
    }

    @OneToMany(mappedBy = "userByVoidedBy")
    public Collection<AccountType> getAccountTypesById_1() {
        return accountTypesById_1;
    }

    public void setAccountTypesById_1(Collection<AccountType> accountTypesById_1) {
        this.accountTypesById_1 = accountTypesById_1;
    }

    @OneToMany(mappedBy = "userByUpdatedBy")
    public Collection<BatchTransactionItem> getBatchTransactionItemsById() {
        return batchTransactionItemsById;
    }

    public void setBatchTransactionItemsById(Collection<BatchTransactionItem> batchTransactionItemsById) {
        this.batchTransactionItemsById = batchTransactionItemsById;
    }

    @OneToMany(mappedBy = "userByCreatedBy")
    public Collection<BatchTransactionItem> getBatchTransactionItemsById_0() {
        return batchTransactionItemsById_0;
    }

    public void setBatchTransactionItemsById_0(Collection<BatchTransactionItem> batchTransactionItemsById_0) {
        this.batchTransactionItemsById_0 = batchTransactionItemsById_0;
    }

    @OneToMany(mappedBy = "userByVoidedBy")
    public Collection<BatchTransactionItem> getBatchTransactionItemsById_1() {
        return batchTransactionItemsById_1;
    }

    public void setBatchTransactionItemsById_1(Collection<BatchTransactionItem> batchTransactionItemsById_1) {
        this.batchTransactionItemsById_1 = batchTransactionItemsById_1;
    }

    @OneToMany(mappedBy = "userByUpdatedBy")
    public Collection<BloodSugarLevel> getBloodSugarLevelsById() {
        return bloodSugarLevelsById;
    }

    public void setBloodSugarLevelsById(Collection<BloodSugarLevel> bloodSugarLevelsById) {
        this.bloodSugarLevelsById = bloodSugarLevelsById;
    }

    @OneToMany(mappedBy = "userByCreatedBy")
    public Collection<BloodSugarLevel> getBloodSugarLevelsById_0() {
        return bloodSugarLevelsById_0;
    }

    public void setBloodSugarLevelsById_0(Collection<BloodSugarLevel> bloodSugarLevelsById_0) {
        this.bloodSugarLevelsById_0 = bloodSugarLevelsById_0;
    }

    @OneToMany(mappedBy = "userByVoidedBy")
    public Collection<BloodSugarLevel> getBloodSugarLevelsById_1() {
        return bloodSugarLevelsById_1;
    }

    public void setBloodSugarLevelsById_1(Collection<BloodSugarLevel> bloodSugarLevelsById_1) {
        this.bloodSugarLevelsById_1 = bloodSugarLevelsById_1;
    }

    @OneToMany(mappedBy = "userByUpdatedBy")
    public Collection<CdrrCategory> getCdrrCategoriesById() {
        return cdrrCategoriesById;
    }

    public void setCdrrCategoriesById(Collection<CdrrCategory> cdrrCategoriesById) {
        this.cdrrCategoriesById = cdrrCategoriesById;
    }

    @OneToMany(mappedBy = "userByCreatedBy")
    public Collection<CdrrCategory> getCdrrCategoriesById_0() {
        return cdrrCategoriesById_0;
    }

    public void setCdrrCategoriesById_0(Collection<CdrrCategory> cdrrCategoriesById_0) {
        this.cdrrCategoriesById_0 = cdrrCategoriesById_0;
    }

    @OneToMany(mappedBy = "userByVoidedBy")
    public Collection<CdrrCategory> getCdrrCategoriesById_1() {
        return cdrrCategoriesById_1;
    }

    public void setCdrrCategoriesById_1(Collection<CdrrCategory> cdrrCategoriesById_1) {
        this.cdrrCategoriesById_1 = cdrrCategoriesById_1;
    }

    @OneToMany(mappedBy = "userByUpdatedBy")
    public Collection<DispensingUnit> getDispensingUnitsById() {
        return dispensingUnitsById;
    }

    public void setDispensingUnitsById(Collection<DispensingUnit> dispensingUnitsById) {
        this.dispensingUnitsById = dispensingUnitsById;
    }

    @OneToMany(mappedBy = "userByCreatedBy")
    public Collection<DispensingUnit> getDispensingUnitsById_0() {
        return dispensingUnitsById_0;
    }

    public void setDispensingUnitsById_0(Collection<DispensingUnit> dispensingUnitsById_0) {
        this.dispensingUnitsById_0 = dispensingUnitsById_0;
    }

    @OneToMany(mappedBy = "userByVoidedBy")
    public Collection<DispensingUnit> getDispensingUnitsById_1() {
        return dispensingUnitsById_1;
    }

    public void setDispensingUnitsById_1(Collection<DispensingUnit> dispensingUnitsById_1) {
        this.dispensingUnitsById_1 = dispensingUnitsById_1;
    }

    @OneToMany(mappedBy = "userByUpdatedBy")
    public Collection<District> getDistrictsById() {
        return districtsById;
    }

    public void setDistrictsById(Collection<District> districtsById) {
        this.districtsById = districtsById;
    }

    @OneToMany(mappedBy = "userByCreatedBy")
    public Collection<District> getDistrictsById_0() {
        return districtsById_0;
    }

    public void setDistrictsById_0(Collection<District> districtsById_0) {
        this.districtsById_0 = districtsById_0;
    }

    @OneToMany(mappedBy = "userByVoidedBy")
    public Collection<District> getDistrictsById_1() {
        return districtsById_1;
    }

    public void setDistrictsById_1(Collection<District> districtsById_1) {
        this.districtsById_1 = districtsById_1;
    }

    @OneToMany(mappedBy = "userByCreatedBy")
    public Collection<Dosage> getDosagesById() {
        return dosagesById;
    }

    public void setDosagesById(Collection<Dosage> dosagesById) {
        this.dosagesById = dosagesById;
    }

    @OneToMany(mappedBy = "userByUpdatedBy")
    public Collection<Dosage> getDosagesById_0() {
        return dosagesById_0;
    }

    public void setDosagesById_0(Collection<Dosage> dosagesById_0) {
        this.dosagesById_0 = dosagesById_0;
    }

    @OneToMany(mappedBy = "userByCreatedBy_0")
    public Collection<Dosage> getDosagesById_1() {
        return dosagesById_1;
    }

    public void setDosagesById_1(Collection<Dosage> dosagesById_1) {
        this.dosagesById_1 = dosagesById_1;
    }

    @OneToMany(mappedBy = "userByVoidedBy")
    public Collection<Dosage> getDosagesById_2() {
        return dosagesById_2;
    }

    public void setDosagesById_2(Collection<Dosage> dosagesById_2) {
        this.dosagesById_2 = dosagesById_2;
    }

    @OneToMany(mappedBy = "userByUpdatedBy")
    public Collection<DrugCategory> getDrugCategoriesById() {
        return drugCategoriesById;
    }

    public void setDrugCategoriesById(Collection<DrugCategory> drugCategoriesById) {
        this.drugCategoriesById = drugCategoriesById;
    }

    @OneToMany(mappedBy = "userByCreatedBy")
    public Collection<DrugCategory> getDrugCategoriesById_0() {
        return drugCategoriesById_0;
    }

    public void setDrugCategoriesById_0(Collection<DrugCategory> drugCategoriesById_0) {
        this.drugCategoriesById_0 = drugCategoriesById_0;
    }

    @OneToMany(mappedBy = "userByVoidedBy")
    public Collection<DrugCategory> getDrugCategoriesById_1() {
        return drugCategoriesById_1;
    }

    public void setDrugCategoriesById_1(Collection<DrugCategory> drugCategoriesById_1) {
        this.drugCategoriesById_1 = drugCategoriesById_1;
    }

    @OneToMany(mappedBy = "userByUpdatedBy")
    public Collection<DrugForm> getDrugFormsById() {
        return drugFormsById;
    }

    public void setDrugFormsById(Collection<DrugForm> drugFormsById) {
        this.drugFormsById = drugFormsById;
    }

    @OneToMany(mappedBy = "userByCreatedBy")
    public Collection<DrugForm> getDrugFormsById_0() {
        return drugFormsById_0;
    }

    public void setDrugFormsById_0(Collection<DrugForm> drugFormsById_0) {
        this.drugFormsById_0 = drugFormsById_0;
    }

    @OneToMany(mappedBy = "userByVoidedBy")
    public Collection<DrugForm> getDrugFormsById_1() {
        return drugFormsById_1;
    }

    public void setDrugFormsById_1(Collection<DrugForm> drugFormsById_1) {
        this.drugFormsById_1 = drugFormsById_1;
    }

    @OneToMany(mappedBy = "userByUpdatedBy")
    public Collection<DrugType> getDrugTypesById() {
        return drugTypesById;
    }

    public void setDrugTypesById(Collection<DrugType> drugTypesById) {
        this.drugTypesById = drugTypesById;
    }

    @OneToMany(mappedBy = "userByCreatedBy")
    public Collection<DrugType> getDrugTypesById_0() {
        return drugTypesById_0;
    }

    public void setDrugTypesById_0(Collection<DrugType> drugTypesById_0) {
        this.drugTypesById_0 = drugTypesById_0;
    }

    @OneToMany(mappedBy = "userByVoidedBy")
    public Collection<DrugType> getDrugTypesById_1() {
        return drugTypesById_1;
    }

    public void setDrugTypesById_1(Collection<DrugType> drugTypesById_1) {
        this.drugTypesById_1 = drugTypesById_1;
    }

    @OneToMany(mappedBy = "userByUpdatedBy")
    public Collection<Facility> getFacilitiesById() {
        return facilitiesById;
    }

    public void setFacilitiesById(Collection<Facility> facilitiesById) {
        this.facilitiesById = facilitiesById;
    }

    @OneToMany(mappedBy = "userByCreatedBy")
    public Collection<Facility> getFacilitiesById_0() {
        return facilitiesById_0;
    }

    public void setFacilitiesById_0(Collection<Facility> facilitiesById_0) {
        this.facilitiesById_0 = facilitiesById_0;
    }

    @OneToMany(mappedBy = "userByVoidedBy")
    public Collection<Facility> getFacilitiesById_1() {
        return facilitiesById_1;
    }

    public void setFacilitiesById_1(Collection<Facility> facilitiesById_1) {
        this.facilitiesById_1 = facilitiesById_1;
    }

    @OneToMany(mappedBy = "userByUpdatedBy")
    public Collection<GenericName> getGenericNamesById() {
        return genericNamesById;
    }

    public void setGenericNamesById(Collection<GenericName> genericNamesById) {
        this.genericNamesById = genericNamesById;
    }

    @OneToMany(mappedBy = "userByCreatedBy")
    public Collection<GenericName> getGenericNamesById_0() {
        return genericNamesById_0;
    }

    public void setGenericNamesById_0(Collection<GenericName> genericNamesById_0) {
        this.genericNamesById_0 = genericNamesById_0;
    }

    @OneToMany(mappedBy = "userByVoidedBy")
    public Collection<GenericName> getGenericNamesById_1() {
        return genericNamesById_1;
    }

    public void setGenericNamesById_1(Collection<GenericName> genericNamesById_1) {
        this.genericNamesById_1 = genericNamesById_1;
    }

    @OneToMany(mappedBy = "userByUpdatedBy")
    public Collection<IdentifierType> getIdentifierTypesById() {
        return identifierTypesById;
    }

    public void setIdentifierTypesById(Collection<IdentifierType> identifierTypesById) {
        this.identifierTypesById = identifierTypesById;
    }

    @OneToMany(mappedBy = "userByCreatedBy")
    public Collection<IdentifierType> getIdentifierTypesById_0() {
        return identifierTypesById_0;
    }

    public void setIdentifierTypesById_0(Collection<IdentifierType> identifierTypesById_0) {
        this.identifierTypesById_0 = identifierTypesById_0;
    }

    @OneToMany(mappedBy = "userByVoidedBy")
    public Collection<IdentifierType> getIdentifierTypesById_1() {
        return identifierTypesById_1;
    }

    public void setIdentifierTypesById_1(Collection<IdentifierType> identifierTypesById_1) {
        this.identifierTypesById_1 = identifierTypesById_1;
    }

    @OneToMany(mappedBy = "userByUpdatedBy")
    public Collection<Indication> getIndicationsById() {
        return indicationsById;
    }

    public void setIndicationsById(Collection<Indication> indicationsById) {
        this.indicationsById = indicationsById;
    }

    @OneToMany(mappedBy = "userByCreatedBy")
    public Collection<Indication> getIndicationsById_0() {
        return indicationsById_0;
    }

    public void setIndicationsById_0(Collection<Indication> indicationsById_0) {
        this.indicationsById_0 = indicationsById_0;
    }

    @OneToMany(mappedBy = "userByVoidedBy")
    public Collection<Indication> getIndicationsById_1() {
        return indicationsById_1;
    }

    public void setIndicationsById_1(Collection<Indication> indicationsById_1) {
        this.indicationsById_1 = indicationsById_1;
    }

    @OneToMany(mappedBy = "userByUpdatedBy")
    public Collection<Insulin> getInsulinsById() {
        return insulinsById;
    }

    public void setInsulinsById(Collection<Insulin> insulinsById) {
        this.insulinsById = insulinsById;
    }

    @OneToMany(mappedBy = "userByCreatedBy")
    public Collection<Insulin> getInsulinsById_0() {
        return insulinsById_0;
    }

    public void setInsulinsById_0(Collection<Insulin> insulinsById_0) {
        this.insulinsById_0 = insulinsById_0;
    }

    @OneToMany(mappedBy = "userByVoidedBy")
    public Collection<Insulin> getInsulinsById_1() {
        return insulinsById_1;
    }

    public void setInsulinsById_1(Collection<Insulin> insulinsById_1) {
        this.insulinsById_1 = insulinsById_1;
    }

    @OneToMany(mappedBy = "userByUpdatedBy")
    public Collection<PatientIdentifier> getPatientIdentifiersById() {
        return patientIdentifiersById;
    }

    public void setPatientIdentifiersById(Collection<PatientIdentifier> patientIdentifiersById) {
        this.patientIdentifiersById = patientIdentifiersById;
    }

    @OneToMany(mappedBy = "userByCreatedBy")
    public Collection<PatientIdentifier> getPatientIdentifiersById_0() {
        return patientIdentifiersById_0;
    }

    public void setPatientIdentifiersById_0(Collection<PatientIdentifier> patientIdentifiersById_0) {
        this.patientIdentifiersById_0 = patientIdentifiersById_0;
    }

    @OneToMany(mappedBy = "userByVoidedBy")
    public Collection<PatientIdentifier> getPatientIdentifiersById_1() {
        return patientIdentifiersById_1;
    }

    public void setPatientIdentifiersById_1(Collection<PatientIdentifier> patientIdentifiersById_1) {
        this.patientIdentifiersById_1 = patientIdentifiersById_1;
    }

    @OneToMany(mappedBy = "userByUpdatedBy")
    public Collection<PatientSource> getPatientSourcesById() {
        return patientSourcesById;
    }

    public void setPatientSourcesById(Collection<PatientSource> patientSourcesById) {
        this.patientSourcesById = patientSourcesById;
    }

    @OneToMany(mappedBy = "userByCreatedBy")
    public Collection<PatientSource> getPatientSourcesById_0() {
        return patientSourcesById_0;
    }

    public void setPatientSourcesById_0(Collection<PatientSource> patientSourcesById_0) {
        this.patientSourcesById_0 = patientSourcesById_0;
    }

    @OneToMany(mappedBy = "userByVoidedBy")
    public Collection<PatientSource> getPatientSourcesById_1() {
        return patientSourcesById_1;
    }

    public void setPatientSourcesById_1(Collection<PatientSource> patientSourcesById_1) {
        this.patientSourcesById_1 = patientSourcesById_1;
    }

    @OneToMany(mappedBy = "userByUpdatedBy")
    public Collection<PatientStatus> getPatientStatusesById() {
        return patientStatusesById;
    }

    public void setPatientStatusesById(Collection<PatientStatus> patientStatusesById) {
        this.patientStatusesById = patientStatusesById;
    }

    @OneToMany(mappedBy = "userByCreatedBy")
    public Collection<PatientStatus> getPatientStatusesById_0() {
        return patientStatusesById_0;
    }

    public void setPatientStatusesById_0(Collection<PatientStatus> patientStatusesById_0) {
        this.patientStatusesById_0 = patientStatusesById_0;
    }

    @OneToMany(mappedBy = "userByVoidedBy")
    public Collection<PatientStatus> getPatientStatusesById_1() {
        return patientStatusesById_1;
    }

    public void setPatientStatusesById_1(Collection<PatientStatus> patientStatusesById_1) {
        this.patientStatusesById_1 = patientStatusesById_1;
    }

    @OneToMany(mappedBy = "userByUpdatedBy")
    public Collection<PatientTransactionItem> getPatientTransactionItemsById() {
        return patientTransactionItemsById;
    }

    public void setPatientTransactionItemsById(Collection<PatientTransactionItem> patientTransactionItemsById) {
        this.patientTransactionItemsById = patientTransactionItemsById;
    }

    @OneToMany(mappedBy = "userByCreatedBy")
    public Collection<PatientTransactionItem> getPatientTransactionItemsById_0() {
        return patientTransactionItemsById_0;
    }

    public void setPatientTransactionItemsById_0(Collection<PatientTransactionItem> patientTransactionItemsById_0) {
        this.patientTransactionItemsById_0 = patientTransactionItemsById_0;
    }

    @OneToMany(mappedBy = "userByVoidedBy")
    public Collection<PatientTransactionItem> getPatientTransactionItemsById_1() {
        return patientTransactionItemsById_1;
    }

    public void setPatientTransactionItemsById_1(Collection<PatientTransactionItem> patientTransactionItemsById_1) {
        this.patientTransactionItemsById_1 = patientTransactionItemsById_1;
    }

    @OneToMany(mappedBy = "userByUpdatedBy")
    public Collection<Person> getPersonsById() {
        return personsById;
    }

    public void setPersonsById(Collection<Person> personsById) {
        this.personsById = personsById;
    }

    @OneToMany(mappedBy = "userByCreatedBy")
    public Collection<Person> getPersonsById_0() {
        return personsById_0;
    }

    public void setPersonsById_0(Collection<Person> personsById_0) {
        this.personsById_0 = personsById_0;
    }

    @OneToMany(mappedBy = "userByVoidedBy")
    public Collection<Person> getPersonsById_1() {
        return personsById_1;
    }

    public void setPersonsById_1(Collection<Person> personsById_1) {
        this.personsById_1 = personsById_1;
    }

    @OneToMany(mappedBy = "userByUpdatedBy")
    public Collection<Prescription> getPrescriptionsById() {
        return prescriptionsById;
    }

    public void setPrescriptionsById(Collection<Prescription> prescriptionsById) {
        this.prescriptionsById = prescriptionsById;
    }

    @OneToMany(mappedBy = "userByCreatedBy")
    public Collection<Prescription> getPrescriptionsById_0() {
        return prescriptionsById_0;
    }

    public void setPrescriptionsById_0(Collection<Prescription> prescriptionsById_0) {
        this.prescriptionsById_0 = prescriptionsById_0;
    }

    @OneToMany(mappedBy = "userByVoidedBy")
    public Collection<Prescription> getPrescriptionsById_1() {
        return prescriptionsById_1;
    }

    public void setPrescriptionsById_1(Collection<Prescription> prescriptionsById_1) {
        this.prescriptionsById_1 = prescriptionsById_1;
    }

    @OneToMany(mappedBy = "userByUpdatedBy")
    public Collection<PrescriptionItem> getPrescriptionItemsById() {
        return prescriptionItemsById;
    }

    public void setPrescriptionItemsById(Collection<PrescriptionItem> prescriptionItemsById) {
        this.prescriptionItemsById = prescriptionItemsById;
    }

    @OneToMany(mappedBy = "userByCreatedBy")
    public Collection<PrescriptionItem> getPrescriptionItemsById_0() {
        return prescriptionItemsById_0;
    }

    public void setPrescriptionItemsById_0(Collection<PrescriptionItem> prescriptionItemsById_0) {
        this.prescriptionItemsById_0 = prescriptionItemsById_0;
    }

    @OneToMany(mappedBy = "userByVoidedBy")
    public Collection<PrescriptionItem> getPrescriptionItemsById_1() {
        return prescriptionItemsById_1;
    }

    public void setPrescriptionItemsById_1(Collection<PrescriptionItem> prescriptionItemsById_1) {
        this.prescriptionItemsById_1 = prescriptionItemsById_1;
    }

    @OneToMany(mappedBy = "userByUpdatedBy")
    public Collection<Privilege> getPrivilegesById() {
        return privilegesById;
    }

    public void setPrivilegesById(Collection<Privilege> privilegesById) {
        this.privilegesById = privilegesById;
    }

    @OneToMany(mappedBy = "userByCreatedBy")
    public Collection<Privilege> getPrivilegesById_0() {
        return privilegesById_0;
    }

    public void setPrivilegesById_0(Collection<Privilege> privilegesById_0) {
        this.privilegesById_0 = privilegesById_0;
    }

    @OneToMany(mappedBy = "userByVoidedBy")
    public Collection<Privilege> getPrivilegesById_1() {
        return privilegesById_1;
    }

    public void setPrivilegesById_1(Collection<Privilege> privilegesById_1) {
        this.privilegesById_1 = privilegesById_1;
    }

    @OneToMany(mappedBy = "userByUpdatedBy")
    public Collection<Property> getPropertiesById() {
        return propertiesById;
    }

    public void setPropertiesById(Collection<Property> propertiesById) {
        this.propertiesById = propertiesById;
    }

    @OneToMany(mappedBy = "userByCreatedBy")
    public Collection<Property> getPropertiesById_0() {
        return propertiesById_0;
    }

    public void setPropertiesById_0(Collection<Property> propertiesById_0) {
        this.propertiesById_0 = propertiesById_0;
    }

    @OneToMany(mappedBy = "userByVoidedBy")
    public Collection<Property> getPropertiesById_1() {
        return propertiesById_1;
    }

    public void setPropertiesById_1(Collection<Property> propertiesById_1) {
        this.propertiesById_1 = propertiesById_1;
    }

    @OneToMany(mappedBy = "userByUpdatedBy")
    public Collection<RegimenChangeReason> getRegimenChangeReasonsById() {
        return regimenChangeReasonsById;
    }

    public void setRegimenChangeReasonsById(Collection<RegimenChangeReason> regimenChangeReasonsById) {
        this.regimenChangeReasonsById = regimenChangeReasonsById;
    }

    @OneToMany(mappedBy = "userByCreatedBy")
    public Collection<RegimenChangeReason> getRegimenChangeReasonsById_0() {
        return regimenChangeReasonsById_0;
    }

    public void setRegimenChangeReasonsById_0(Collection<RegimenChangeReason> regimenChangeReasonsById_0) {
        this.regimenChangeReasonsById_0 = regimenChangeReasonsById_0;
    }

    @OneToMany(mappedBy = "userByVoidedBy")
    public Collection<RegimenChangeReason> getRegimenChangeReasonsById_1() {
        return regimenChangeReasonsById_1;
    }

    public void setRegimenChangeReasonsById_1(Collection<RegimenChangeReason> regimenChangeReasonsById_1) {
        this.regimenChangeReasonsById_1 = regimenChangeReasonsById_1;
    }

    @OneToMany(mappedBy = "userByUpdatedBy")
    public Collection<RegimenStatus> getRegimenStatusesById() {
        return regimenStatusesById;
    }

    public void setRegimenStatusesById(Collection<RegimenStatus> regimenStatusesById) {
        this.regimenStatusesById = regimenStatusesById;
    }

    @OneToMany(mappedBy = "userByCreatedBy")
    public Collection<RegimenStatus> getRegimenStatusesById_0() {
        return regimenStatusesById_0;
    }

    public void setRegimenStatusesById_0(Collection<RegimenStatus> regimenStatusesById_0) {
        this.regimenStatusesById_0 = regimenStatusesById_0;
    }

    @OneToMany(mappedBy = "userByVoidedBy")
    public Collection<RegimenStatus> getRegimenStatusesById_1() {
        return regimenStatusesById_1;
    }

    public void setRegimenStatusesById_1(Collection<RegimenStatus> regimenStatusesById_1) {
        this.regimenStatusesById_1 = regimenStatusesById_1;
    }

    @OneToMany(mappedBy = "userByUpdatedBy")
    public Collection<RegimenType> getRegimenTypesById() {
        return regimenTypesById;
    }

    public void setRegimenTypesById(Collection<RegimenType> regimenTypesById) {
        this.regimenTypesById = regimenTypesById;
    }

    @OneToMany(mappedBy = "userByCreatedBy")
    public Collection<RegimenType> getRegimenTypesById_0() {
        return regimenTypesById_0;
    }

    public void setRegimenTypesById_0(Collection<RegimenType> regimenTypesById_0) {
        this.regimenTypesById_0 = regimenTypesById_0;
    }

    @OneToMany(mappedBy = "userByVoidedBy")
    public Collection<RegimenType> getRegimenTypesById_1() {
        return regimenTypesById_1;
    }

    public void setRegimenTypesById_1(Collection<RegimenType> regimenTypesById_1) {
        this.regimenTypesById_1 = regimenTypesById_1;
    }

    @OneToMany(mappedBy = "userByUpdatedBy")
    public Collection<Region> getRegionsById() {
        return regionsById;
    }

    public void setRegionsById(Collection<Region> regionsById) {
        this.regionsById = regionsById;
    }

    @OneToMany(mappedBy = "userByCreatedBy")
    public Collection<Region> getRegionsById_0() {
        return regionsById_0;
    }

    public void setRegionsById_0(Collection<Region> regionsById_0) {
        this.regionsById_0 = regionsById_0;
    }

    @OneToMany(mappedBy = "userByVoidedBy")
    public Collection<Region> getRegionsById_1() {
        return regionsById_1;
    }

    public void setRegionsById_1(Collection<Region> regionsById_1) {
        this.regionsById_1 = regionsById_1;
    }

    @OneToMany(mappedBy = "userByUpdatedBy")
    public Collection<Role> getRolesById() {
        return rolesById;
    }

    public void setRolesById(Collection<Role> rolesById) {
        this.rolesById = rolesById;
    }

    @OneToMany(mappedBy = "userByCreatedBy")
    public Collection<Role> getRolesById_0() {
        return rolesById_0;
    }

    public void setRolesById_0(Collection<Role> rolesById_0) {
        this.rolesById_0 = rolesById_0;
    }

    @OneToMany(mappedBy = "userByVoidedBy")
    public Collection<Role> getRolesById_1() {
        return rolesById_1;
    }

    public void setRolesById_1(Collection<Role> rolesById_1) {
        this.rolesById_1 = rolesById_1;
    }

    @OneToMany(mappedBy = "userByUpdatedBy")
    public Collection<ServiceType> getServiceTypesById() {
        return serviceTypesById;
    }

    public void setServiceTypesById(Collection<ServiceType> serviceTypesById) {
        this.serviceTypesById = serviceTypesById;
    }

    @OneToMany(mappedBy = "userByCreatedBy")
    public Collection<ServiceType> getServiceTypesById_0() {
        return serviceTypesById_0;
    }

    public void setServiceTypesById_0(Collection<ServiceType> serviceTypesById_0) {
        this.serviceTypesById_0 = serviceTypesById_0;
    }

    @OneToMany(mappedBy = "userByVoidedBy")
    public Collection<ServiceType> getServiceTypesById_1() {
        return serviceTypesById_1;
    }

    public void setServiceTypesById_1(Collection<ServiceType> serviceTypesById_1) {
        this.serviceTypesById_1 = serviceTypesById_1;
    }

    @OneToMany(mappedBy = "userByUpdatedBy")
    public Collection<StockTakeSheet> getStockTakeSheetsById() {
        return stockTakeSheetsById;
    }

    public void setStockTakeSheetsById(Collection<StockTakeSheet> stockTakeSheetsById) {
        this.stockTakeSheetsById = stockTakeSheetsById;
    }

    @OneToMany(mappedBy = "userByCreatedBy")
    public Collection<StockTakeSheet> getStockTakeSheetsById_0() {
        return stockTakeSheetsById_0;
    }

    public void setStockTakeSheetsById_0(Collection<StockTakeSheet> stockTakeSheetsById_0) {
        this.stockTakeSheetsById_0 = stockTakeSheetsById_0;
    }

    @OneToMany(mappedBy = "userByVoidedBy")
    public Collection<StockTakeSheet> getStockTakeSheetsById_1() {
        return stockTakeSheetsById_1;
    }

    public void setStockTakeSheetsById_1(Collection<StockTakeSheet> stockTakeSheetsById_1) {
        this.stockTakeSheetsById_1 = stockTakeSheetsById_1;
    }

    @OneToMany(mappedBy = "userByUpdatedBy")
    public Collection<SupportingOrganization> getSupportingOrganizationsById() {
        return supportingOrganizationsById;
    }

    public void setSupportingOrganizationsById(Collection<SupportingOrganization> supportingOrganizationsById) {
        this.supportingOrganizationsById = supportingOrganizationsById;
    }

    @OneToMany(mappedBy = "userByCreatedBy")
    public Collection<SupportingOrganization> getSupportingOrganizationsById_0() {
        return supportingOrganizationsById_0;
    }

    public void setSupportingOrganizationsById_0(Collection<SupportingOrganization> supportingOrganizationsById_0) {
        this.supportingOrganizationsById_0 = supportingOrganizationsById_0;
    }

    @OneToMany(mappedBy = "userByVoidedBy")
    public Collection<SupportingOrganization> getSupportingOrganizationsById_1() {
        return supportingOrganizationsById_1;
    }

    public void setSupportingOrganizationsById_1(Collection<SupportingOrganization> supportingOrganizationsById_1) {
        this.supportingOrganizationsById_1 = supportingOrganizationsById_1;
    }

    @OneToMany(mappedBy = "userByUpdatedBy")
    public Collection<Transaction> getTransactionsById() {
        return transactionsById;
    }

    public void setTransactionsById(Collection<Transaction> transactionsById) {
        this.transactionsById = transactionsById;
    }

    @OneToMany(mappedBy = "userByCreatedBy")
    public Collection<Transaction> getTransactionsById_0() {
        return transactionsById_0;
    }

    public void setTransactionsById_0(Collection<Transaction> transactionsById_0) {
        this.transactionsById_0 = transactionsById_0;
    }

    @OneToMany(mappedBy = "userByVoidedBy")
    public Collection<Transaction> getTransactionsById_1() {
        return transactionsById_1;
    }

    public void setTransactionsById_1(Collection<Transaction> transactionsById_1) {
        this.transactionsById_1 = transactionsById_1;
    }

    @OneToMany(mappedBy = "userByUpdatedBy")
    public Collection<TransactionItem> getTransactionItemsById() {
        return transactionItemsById;
    }

    public void setTransactionItemsById(Collection<TransactionItem> transactionItemsById) {
        this.transactionItemsById = transactionItemsById;
    }

    @OneToMany(mappedBy = "userByCreatedBy")
    public Collection<TransactionItem> getTransactionItemsById_0() {
        return transactionItemsById_0;
    }

    public void setTransactionItemsById_0(Collection<TransactionItem> transactionItemsById_0) {
        this.transactionItemsById_0 = transactionItemsById_0;
    }

    @OneToMany(mappedBy = "userByVoidedBy")
    public Collection<TransactionItem> getTransactionItemsById_1() {
        return transactionItemsById_1;
    }

    public void setTransactionItemsById_1(Collection<TransactionItem> transactionItemsById_1) {
        this.transactionItemsById_1 = transactionItemsById_1;
    }

    @OneToMany(mappedBy = "userByUpdatedBy")
    public Collection<TransactionType> getTransactionTypesById() {
        return transactionTypesById;
    }

    public void setTransactionTypesById(Collection<TransactionType> transactionTypesById) {
        this.transactionTypesById = transactionTypesById;
    }

    @OneToMany(mappedBy = "userByCreatedBy")
    public Collection<TransactionType> getTransactionTypesById_0() {
        return transactionTypesById_0;
    }

    public void setTransactionTypesById_0(Collection<TransactionType> transactionTypesById_0) {
        this.transactionTypesById_0 = transactionTypesById_0;
    }

    @OneToMany(mappedBy = "userByVoidedBy")
    public Collection<TransactionType> getTransactionTypesById_1() {
        return transactionTypesById_1;
    }

    public void setTransactionTypesById_1(Collection<TransactionType> transactionTypesById_1) {
        this.transactionTypesById_1 = transactionTypesById_1;
    }

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id", insertable=false, updatable=false)
    public Person getPersonByPersonId() {
        return personByPersonId;
    }

    public void setPersonByPersonId(Person personByPersonId) {
        this.personByPersonId = personByPersonId;
    }

    @ManyToOne
    @JoinColumn(name = "updated_by", referencedColumnName = "id", insertable=false, updatable=false)
    public User getUserByUpdatedBy() {
        return userByUpdatedBy;
    }

    public void setUserByUpdatedBy(User userByUpdatedBy) {
        this.userByUpdatedBy = userByUpdatedBy;
    }

    @OneToMany(mappedBy = "userByUpdatedBy")
    public Collection<User> getUsersById() {
        return usersById;
    }

    public void setUsersById(Collection<User> usersById) {
        this.usersById = usersById;
    }

    @ManyToOne
    @JoinColumn(name = "created_by", referencedColumnName = "id", nullable = false, insertable=false, updatable=false)
    public User getUserByCreatedBy() {
        return userByCreatedBy;
    }

    public void setUserByCreatedBy(User userByCreatedBy) {
        this.userByCreatedBy = userByCreatedBy;
    }

    @OneToMany(mappedBy = "userByCreatedBy")
    public Collection<User> getUsersById_0() {
        return usersById_0;
    }

    public void setUsersById_0(Collection<User> usersById_0) {
        this.usersById_0 = usersById_0;
    }

    @ManyToOne
    @JoinColumn(name = "voided_by", referencedColumnName = "id", insertable=false, updatable=false)
    public User getUserByVoidedBy() {
        return userByVoidedBy;
    }

    public void setUserByVoidedBy(User userByVoidedBy) {
        this.userByVoidedBy = userByVoidedBy;
    }

    @OneToMany(mappedBy = "userByVoidedBy")
    public Collection<User> getUsersById_1() {
        return usersById_1;
    }

    public void setUsersById_1(Collection<User> usersById_1) {
        this.usersById_1 = usersById_1;
    }

    @OneToMany(mappedBy = "userByUserId")
    public Collection<UserRole> getUserRolesById() {
        return userRolesById;
    }

    public void setUserRolesById(Collection<UserRole> userRolesById) {
        this.userRolesById = userRolesById;
    }

    @OneToMany(mappedBy = "userByUpdatedBy")
    public Collection<VisitType> getVisitTypesById() {
        return visitTypesById;
    }

    public void setVisitTypesById(Collection<VisitType> visitTypesById) {
        this.visitTypesById = visitTypesById;
    }

    @OneToMany(mappedBy = "userByCreatedBy")
    public Collection<VisitType> getVisitTypesById_0() {
        return visitTypesById_0;
    }

    public void setVisitTypesById_0(Collection<VisitType> visitTypesById_0) {
        this.visitTypesById_0 = visitTypesById_0;
    }

    @OneToMany(mappedBy = "userByVoidedBy")
    public Collection<VisitType> getVisitTypesById_1() {
        return visitTypesById_1;
    }

    public void setVisitTypesById_1(Collection<VisitType> visitTypesById_1) {
        this.visitTypesById_1 = visitTypesById_1;
    }
}
