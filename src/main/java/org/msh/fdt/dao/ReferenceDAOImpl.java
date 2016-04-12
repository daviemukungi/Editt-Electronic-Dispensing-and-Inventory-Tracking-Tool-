package org.msh.fdt.dao;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.msh.fdt.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by kenny on 4/8/14.
 */
@Repository
public class ReferenceDAOImpl implements ReferenceDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Integer saveAccountType(AccountType accountType) {
        Integer id = (Integer)sessionFactory.getCurrentSession().save(accountType);

        Collection accTypeTxType = accountType.getAccountTypeTransactionTypesById();
        Iterator iterator = accTypeTxType.iterator();
        while (iterator.hasNext()) {
            AccountTypeTransactionType atty = (AccountTypeTransactionType)iterator.next();
            AccountTypeTransactionType actxType = new AccountTypeTransactionType();
            actxType.setAccountTypeId(id);
            actxType.setTransactionTypeId(atty.getTransactionTypeId());
            saveAccountTypeTransactionType(actxType);
        }
        return id;
    }

    @Override
    public Integer saveServiceType(ServiceType serviceType) {
        Integer id = (Integer)sessionFactory.getCurrentSession().save(serviceType);
        return id;
    }

    @Override
    public Integer saveRegimenType(RegimenType regimenType) {
        Integer id = (Integer)sessionFactory.getCurrentSession().save(regimenType);
        return id;
    }

    @Override
    public Integer saveTransactionType(TransactionType transactionType) {
        Integer id = (Integer)sessionFactory.getCurrentSession().save(transactionType);
        return id;
    }

    @Override
    public Integer saveIdentifierType(IdentifierType identifierType) {
        Integer id = (Integer)sessionFactory.getCurrentSession().save(identifierType);
        return id;
    }

    @Override
    public Integer saveDispensingUnit(DispensingUnit dispensingUnit) {
        Object id = sessionFactory.getCurrentSession().save(dispensingUnit);
        return (Integer)id;
    }

    @Override
    public Integer saveDosage(Dosage dosage) {
        Integer id = (Integer)sessionFactory.getCurrentSession().save(dosage);
        return id;
    }

    @Override
    public Integer saveDistrict(District district) {
        Integer id = (Integer)sessionFactory.getCurrentSession().save(district);
        return id;
    }

    @Override
    public Integer saveGenericName(GenericName genericName) {
        Integer id = (Integer)sessionFactory.getCurrentSession().save(genericName);
        return id;
    }

    @Override
    public Integer saveSupportingOrganization(SupportingOrganization o) {
        Integer id = (Integer)sessionFactory.getCurrentSession().save(o);
        return id;
    }

    @Override
    public Integer savePatientSource(PatientSource patientSource) {
        Integer id = (Integer)sessionFactory.getCurrentSession().save(patientSource);
        return id;
    }

    @Override
    public Integer addAccount(Account account) {
        Integer id = (Integer)sessionFactory.getCurrentSession().save(account);
        return id;
    }

    @Override
    public Integer saveRegion(Region region) {
        Integer id = (Integer)sessionFactory.getCurrentSession().save(region);
        return id;
    }

    @Override
    public Integer savePatientStatus(PatientStatus patientStatus) {
        Integer id = (Integer)sessionFactory.getCurrentSession().save(patientStatus);
        return id;
    }

    @Override
    public Integer saveIndication(Indication indication) {
        Integer id = (Integer)sessionFactory.getCurrentSession().save(indication);
        return id;
    }

    @Override
    public Integer saveRegimenChangeReason(RegimenChangeReason regimenChangeReason) {
        Integer id = (Integer)sessionFactory.getCurrentSession().save(regimenChangeReason);
        return id;
    }

    @Override
    public Integer saveVisitType(VisitType visitType) {
        Integer id = (Integer)sessionFactory.getCurrentSession().save(visitType);
        return id;
    }

    @Override
    public Integer saveRegimenStatus(RegimenStatus regimenStatus) {
        Integer id = (Integer)sessionFactory.getCurrentSession().save(regimenStatus);
        return id;
    }

    @Override
    public Integer saveDrugCategory(DrugCategory drugCategory) {
        Integer id = (Integer)sessionFactory.getCurrentSession().save(drugCategory);
        return id;
    }

    @Override
    public Integer saveDrugForm(DrugForm drugForm) {
        Integer id = (Integer)sessionFactory.getCurrentSession().save(drugForm);
        return id;
    }

    @Override
    public Integer saveDrugType(DrugType drugType) {
        Integer id = (Integer)sessionFactory.getCurrentSession().save(drugType);
        return id;
    }

    @Override
    public Integer saveAccount(Account account) {
        Integer id = (Integer)sessionFactory.getCurrentSession().save(account);
        return id;
    }

    @Override
    public Integer saveFamilyPlanningMethod(FamilyPlanningMethod planningMethod) {
        return (Integer)sessionFactory.getCurrentSession().save(planningMethod);
    }

    @Override
    public Integer saveFamilyPlanningMethodChangeReason(FamilyPlanningMethodChangeReason changeReason) {
        return (Integer)sessionFactory.getCurrentSession().save(changeReason);
    }

    @Override
    public Integer saveInsulin(Insulin insulin) {
        return (Integer)sessionFactory.getCurrentSession().save(insulin);
    }

    @Override
    public Integer saveBloodSugarLevel(BloodSugarLevel bloodSugarLevel) {
        return (Integer)sessionFactory.getCurrentSession().save(bloodSugarLevel);
    }

    @Override
    public Integer saveCDRRCategory(CdrrCategory category) {
        return (Integer)sessionFactory.getCurrentSession().save(category);
    }

    @Override
    public List<AccountType> listAccountType() {
        Query query = sessionFactory.getCurrentSession().createQuery("from AccountType WHERE voided = 0");
        List list = query.list();
        Iterator iter = list.iterator();
        List<AccountType> accountTypes = new ArrayList<AccountType>();
        while(iter.hasNext()) {
            AccountType accountType = (AccountType)iter.next();

            AccountType newA = new AccountType();
            newA.setId(accountType.getId());
            newA.setName(accountType.getName());
            newA.setDescription(accountType.getDescription());
            List acctxtype = new ArrayList();
            Collection acctype = accountType.getAccountTypeTransactionTypesById();
            Iterator iterator = acctype.iterator();
            while (iterator.hasNext()) {
                AccountTypeTransactionType attt = (AccountTypeTransactionType)iterator.next();
                AccountTypeTransactionType acttxt = new AccountTypeTransactionType();
                acttxt.setTransactionTypeId(attt.getTransactionTypeId());
                acttxt.setAccountTypeId(attt.getAccountTypeId());
                acctxtype.add(acttxt);
            }
            newA.setAccountTypeTransactionTypesById(acctxtype);
            accountTypes.add(newA);
        }
        return accountTypes;
    }

    @Override
    public List<RegimenType> listRegimentType() {
        Query query = sessionFactory.getCurrentSession().createQuery("from RegimenType WHERE voided = 0 ");

        List list = query.list();
        Iterator iter = list.iterator();
        List<RegimenType> regimenTypes = new ArrayList<RegimenType>();
        while(iter.hasNext()) {
            RegimenType regimenType = (RegimenType)iter.next();

            RegimenType newRegimenType = new RegimenType();
            newRegimenType.setId(regimenType.getId());
            newRegimenType.setName(regimenType.getName());
            newRegimenType.setDescription(regimenType.getDescription());
            regimenTypes.add(newRegimenType);
        }
        return regimenTypes;
    }

    @Override
    public List<Dosage> listDosage() {
        Query query = sessionFactory.getCurrentSession().createQuery("from Dosage WHERE voided = 0 ");

        List list = query.list();
        Iterator iter = list.iterator();
        List<Dosage> dosages = new ArrayList<Dosage>();
        while(iter.hasNext()) {
            Dosage dosage = (Dosage)iter.next();

            Dosage dose = new Dosage();
            dose.setId(dosage.getId());
            dose.setName(dosage.getName());
            dose.setDescription(dosage.getDescription());
            dosages.add(dose);
        }
        return dosages;
    }

    @Override
    public List<DispensingUnit> listDispensingUnit() {
        Query query = sessionFactory.getCurrentSession().createQuery("from DispensingUnit WHERE voided = 0 ");

        List list = query.list();
        Iterator iter = list.iterator();
        List<DispensingUnit> dispensingUnitList = new ArrayList<DispensingUnit>();
        while(iter.hasNext()) {
            DispensingUnit dispensingUnit = (DispensingUnit)iter.next();

            DispensingUnit unit = new DispensingUnit();
            unit.setId(dispensingUnit.getId());
            unit.setName(dispensingUnit.getName());
            unit.setDescription(dispensingUnit.getDescription());
            unit.setMinimum(dispensingUnit.getMinimum());
            unit.setMaximum(dispensingUnit.getMaximum());
            dispensingUnitList.add(unit);
        }
        return dispensingUnitList;
    }

    @Override
    public List<GenericName> listGenericName() {
        Query query = sessionFactory.getCurrentSession().createQuery("from GenericName WHERE voided = 0 ");

        List list = query.list();
        Iterator iter = list.iterator();
        List<GenericName> genericNameList = new ArrayList<GenericName>();
        while(iter.hasNext()) {
            GenericName genericName = (GenericName)iter.next();

            GenericName gen = new GenericName();
            gen.setId(genericName.getId());
            gen.setName(genericName.getName());
            genericNameList.add(gen);
        }
        return genericNameList;
    }

    @Override
    public List<District> listDistrict() {
        Query query = sessionFactory.getCurrentSession().createQuery("from District WHERE voided = 0 ORDER BY name");

        List list = query.list();
        Iterator iter = list.iterator();
        List<District> durationUnitList = new ArrayList<District>();
        while(iter.hasNext()) {
            District durationUnit = (District)iter.next();

            District unit = new District();
            unit.setId(durationUnit.getId());
            unit.setName(durationUnit.getName());
            unit.setCode(durationUnit.getCode());
            durationUnitList.add(unit);
        }
        return durationUnitList;
    }

    @Override
    public List<IdentifierType> listIdentifierType() {
        Query query = sessionFactory.getCurrentSession().createQuery("from IdentifierType WHERE voided = 0 ");

        List list = query.list();
        Iterator iter = list.iterator();
        List<IdentifierType> identifierTypeList = new ArrayList<IdentifierType>();
        while(iter.hasNext()) {
            IdentifierType identifierType = (IdentifierType)iter.next();

            IdentifierType idType = new IdentifierType();
            idType.setId(identifierType.getId());
            idType.setName(identifierType.getName());
            idType.setDescription(identifierType.getDescription());
            idType.setRequired(identifierType.getRequired());
            identifierTypeList.add(idType);
        }
        return identifierTypeList;
    }



    @Override
    public List<PatientSource> listPatientSource() {
        Query query = sessionFactory.getCurrentSession().createQuery("from PatientSource WHERE voided = 0 ");

        List list = query.list();
        Iterator iter = list.iterator();
        List<PatientSource> patientSourceList = new ArrayList<PatientSource>();
        while(iter.hasNext()) {
            PatientSource patientSource = (PatientSource)iter.next();

            PatientSource source = new PatientSource();
            source.setId(patientSource.getId());
            source.setName(patientSource.getName());
            source.setDescription(patientSource.getDescription());
            patientSourceList.add(source);
        }
        return patientSourceList;
    }

    @Override
    public List<ServiceType> listServiceType() {
        Query query = sessionFactory.getCurrentSession().createQuery("from ServiceType WHERE voided = 0 ");

        List list = query.list();
        Iterator iter = list.iterator();
        List<ServiceType> serviceTypeList = new ArrayList<ServiceType>();
        while(iter.hasNext()) {
            ServiceType serviceType = (ServiceType)iter.next();

            ServiceType type = new ServiceType();
            type.setId(serviceType.getId());
            type.setName(serviceType.getName());
            type.setService_area(serviceType.getService_area());
            type.setDescription(serviceType.getDescription());
            serviceTypeList.add(type);
        }
        return serviceTypeList;
    }

    @Override
    public List<SupportingOrganization> listSupportingOrganization() {
        Query query = sessionFactory.getCurrentSession().createQuery("from SupportingOrganization WHERE voided = 0 ");

        List list = query.list();
        Iterator iter = list.iterator();
        List<SupportingOrganization> organizationList = new ArrayList<SupportingOrganization>();
        while(iter.hasNext()) {
            SupportingOrganization organization = (SupportingOrganization)iter.next();

            SupportingOrganization org = new SupportingOrganization();
            org.setId(organization.getId());
            org.setName(organization.getName());
            org.setDescription(organization.getDescription());
            organizationList.add(org);
        }
        return organizationList;
    }

    @Override
    public List<TransactionType> listTransactionType() {
        Query query = sessionFactory.getCurrentSession().createQuery("from TransactionType WHERE voided = 0 ");

        List list = query.list();
        Iterator iter = list.iterator();
        List<TransactionType> transactionTypeList = new ArrayList<TransactionType>();
        while(iter.hasNext()) {
            TransactionType transactionType = (TransactionType)iter.next();

            TransactionType type = new TransactionType();
            type.setId(transactionType.getId());
            type.setName(transactionType.getName());
            type.setDescription(transactionType.getDescription());
            type.setDocumentable(transactionType.getDocumentable());
            type.setNarrated(transactionType.getNarrated());

            List acctxtype = new ArrayList();
            Collection acctype = transactionType.getAccountTypeTransactionTypesById();
            Iterator iterator = acctype.iterator();
            while (iterator.hasNext()) {
                AccountTypeTransactionType attt = (AccountTypeTransactionType)iterator.next();
                AccountTypeTransactionType acttxt = new AccountTypeTransactionType();
                acttxt.setTransactionTypeId(attt.getTransactionTypeId());
                acttxt.setAccountTypeId(attt.getAccountTypeId());
                acctxtype.add(acttxt);
            }
            type.setAccountTypeTransactionTypesById(acctxtype);
            transactionTypeList.add(type);

        }
        return transactionTypeList;
    }

    @Override
    public List<Region> listRegion() {
        Query query = sessionFactory.getCurrentSession().createQuery("from Region WHERE voided = 0 ");

        List list = query.list();
        Iterator iter = list.iterator();
        List<Region> regionList = new ArrayList<Region>();
        while(iter.hasNext()) {
            Region region = (Region)iter.next();

            Region r = new Region();
            r.setId(region.getId());
            r.setName(region.getName());
            regionList.add(r);
        }
        return regionList;
    }

    @Override
    public List<PatientStatus> listPatientStatus() {
        Query query = sessionFactory.getCurrentSession().createQuery("from PatientStatus WHERE voided = 0 ");

        List list = query.list();
        Iterator iter = list.iterator();
        List<PatientStatus> patientStatusList = new ArrayList<PatientStatus>();
        while(iter.hasNext()) {
            PatientStatus patientStatus = (PatientStatus)iter.next();

            PatientStatus p = new PatientStatus();
            p.setId(patientStatus.getId());
            p.setName(patientStatus.getName());
            patientStatusList.add(p);
        }
        return patientStatusList;
    }

    @Override
    public List<Indication> listIndication() {
        Query query = sessionFactory.getCurrentSession().createQuery(" from Indication WHERE voided = 0 ");

        List list = query.list();
        Iterator iter = list.iterator();
        List<Indication> patientStatusList = new ArrayList<Indication>();
        while(iter.hasNext()) {
            Indication indication = (Indication)iter.next();

            Indication i = new Indication();
            i.setId(indication.getId());
            i.setName(indication.getName());
            i.setDescription(indication.getDescription());
            patientStatusList.add(i);
        }
        return patientStatusList;
    }

    @Override
    public List<RegimenChangeReason> listRegimenChangeReason() {
        Query query = sessionFactory.getCurrentSession().createQuery(" from RegimenChangeReason WHERE voided = 0 ");

        List list = query.list();
        Iterator iter = list.iterator();
        List<RegimenChangeReason> regimenChangeReasonList = new ArrayList<RegimenChangeReason>();
        while(iter.hasNext()) {
            RegimenChangeReason regimenChangeReason = (RegimenChangeReason)iter.next();

            RegimenChangeReason reason = new RegimenChangeReason();
            reason.setId(regimenChangeReason.getId());
            reason.setName(regimenChangeReason.getName());
            reason.setDescription(regimenChangeReason.getDescription());
            regimenChangeReasonList.add(reason);
        }
        return regimenChangeReasonList;
    }

    @Override
    public List<VisitType> listVisitType() {
       // Criteria c = sessionFactory.getCurrentSession().createCriteria(Regimen.class).setProjection(Projections.projectionList().add(Projections.property("id"), "id").add(Projections.property("description"), "description").add(Projections.property("name"), "name").add(Projections.property("serviceTypeId"), "serviceTypeId")).setResultTransformer(Transformers.aliasToBean(VisitType.class));
        Query query = sessionFactory.getCurrentSession().createQuery(" from VisitType WHERE voided = 0 ");

        List list = query.list();
        Iterator iter = list.iterator();
        List<VisitType> visitTypeList = new ArrayList<VisitType>();
        while(iter.hasNext()) {
            VisitType visitType = (VisitType)iter.next();

            VisitType visit = new VisitType();
            visit.setId(visitType.getId());
            visit.setName(visitType.getName());
            visit.setDescription(visitType.getDescription());
            visit.setServiceTypeId(visitType.getServiceTypeId());
            visitTypeList.add(visit);
        }
        return visitTypeList;
    }

    @Override
    public List<RegimenStatus> listRegimenStatus() {
        Query query = sessionFactory.getCurrentSession().createQuery(" from RegimenStatus WHERE voided = 0 ");

        List list = query.list();
        Iterator iter = list.iterator();
        List<RegimenStatus> regimenStatusList = new ArrayList<RegimenStatus>();
        while(iter.hasNext()) {
            RegimenStatus regimenStatus = (RegimenStatus)iter.next();

            RegimenStatus status = new RegimenStatus();
            status.setId(regimenStatus.getId());
            status.setName(regimenStatus.getName());
            status.setDescription(regimenStatus.getDescription());
            regimenStatusList.add(status);
        }
        return regimenStatusList;
    }

    @Override
    public List<DrugCategory> listDrugCategory() {
        Query query = sessionFactory.getCurrentSession().createQuery(" from DrugCategory WHERE voided = 0 ");

        List list = query.list();
        Iterator iter = list.iterator();
        List<DrugCategory> drugCategoryList = new ArrayList<DrugCategory>();
        while(iter.hasNext()) {
            DrugCategory category = (DrugCategory)iter.next();

            DrugCategory drugCategory = new DrugCategory();
            drugCategory.setId(category.getId());
            drugCategory.setName(category.getName());
            drugCategory.setDescription(category.getDescription());
            drugCategoryList.add(drugCategory);
        }
        return drugCategoryList;
    }

    @Override
    public List<DrugForm> listDrugForm() {
        Query query = sessionFactory.getCurrentSession().createQuery(" from DrugForm WHERE voided = 0 ");

        List list = query.list();
        Iterator iter = list.iterator();
        List<DrugForm> drugFormList = new ArrayList<DrugForm>();
        while(iter.hasNext()) {
            DrugForm drugForm = (DrugForm)iter.next();

            DrugForm form = new DrugForm();
            form.setId(drugForm.getId());
            form.setName(drugForm.getName());
            form.setDescription(drugForm.getDescription());
            drugFormList.add(form);
        }
        return drugFormList;
    }

    @Override
    public List<DrugType> listDrugType() {
        Query query = sessionFactory.getCurrentSession().createQuery(" from DrugType WHERE voided = 0 ");

        List list = query.list();
        Iterator iter = list.iterator();
        List<DrugType> drugTypeList = new ArrayList<DrugType>();
        while(iter.hasNext()) {
            DrugType drugType = (DrugType)iter.next();

            DrugType type = new DrugType();
            type.setId(drugType.getId());
            type.setName(drugType.getName());
            type.setDescription(drugType.getDescription());
            drugTypeList.add(type);
        }
        return drugTypeList;
    }

    @Override
    public List<Account> listAccount() {
        Query query = sessionFactory.getCurrentSession().createQuery(" from Account WHERE voided = 0 ");

        List list = query.list();
        Iterator iter = list.iterator();
        List<Account> accountList = new ArrayList<Account>();
        while(iter.hasNext()) {
            Account account = (Account)iter.next();

            Account acc = new Account();
            acc.setId(account.getId());
            acc.setName(account.getName());
            acc.setDescription(account.getDescription());
            acc.setIs_bulkstore(account.getIs_bulkstore());
            acc.setIs_satellite(account.getIs_satellite());
            acc.setCannot_dispense(account.getCannot_dispense());
            acc.setAccountTypeId(account.getAccountTypeId());
            accountList.add(acc);
        }
        return accountList;
    }

    @Override
    public List<Account> listStores() {
        Query query = sessionFactory.getCurrentSession().createSQLQuery(" SELECT a.id, a.name FROM account a WHERE account_type_id IN (SELECT id FROM  account_type WHERE name = 'Store') AND a.voided = 0 AND a.Is_satellite<>1");

        List list = query.list();
        Iterator iter = list.iterator();
        List<Account> accountList = new ArrayList<Account>();
        while(iter.hasNext()) {
            Object[] row = (Object[])iter.next();

            Account acc = new Account();
            acc.setId((Integer)row[0]);
            acc.setName((String)row[1]);
            accountList.add(acc);
        }
        return accountList;
    }

    @Override
    public List<RegimenDrug> listRegimenDrug() {
        Query query = sessionFactory.getCurrentSession().createSQLQuery(" SELECT a.regimen_id, a.drug_id FROM regimen_drug a");

        List list = query.list();
        Iterator iter = list.iterator();
        List<RegimenDrug> regimenDrugList = new ArrayList<RegimenDrug>();
        while(iter.hasNext()) {
            Object[] row = (Object[])iter.next();

            RegimenDrug acc = new RegimenDrug();
            acc.setRegimenId((Integer)row[0]);
            acc.setDrugId((Integer)row[1]);
            regimenDrugList.add(acc);
        }
        return regimenDrugList;
    }

    @Override
    public List<FamilyPlanningMethod> listFamilyPlanningMethod() {
        Query query = sessionFactory.getCurrentSession().createQuery(" from FamilyPlanningMethod WHERE voided = 0 ");

        List list = query.list();
        Iterator iter = list.iterator();
        List<FamilyPlanningMethod> fpList = new ArrayList<FamilyPlanningMethod>();
        while(iter.hasNext()) {
            FamilyPlanningMethod planningMethod = (FamilyPlanningMethod)iter.next();

            FamilyPlanningMethod fp = new FamilyPlanningMethod();
            fp.setId(planningMethod.getId());
            fp.setName(planningMethod.getName());
            fp.setDescription(planningMethod.getDescription());
            fpList.add(fp);
        }
        return fpList;
    }

    @Override
    public List<FamilyPlanningMethodChangeReason> listFamilyPlanningMethodChangeReason() {
        Query query = sessionFactory.getCurrentSession().createQuery(" from FamilyPlanningMethodChangeReason WHERE voided = 0 ");

        List list = query.list();
        Iterator iter = list.iterator();
        List<FamilyPlanningMethodChangeReason> fpList = new ArrayList<FamilyPlanningMethodChangeReason>();
        while(iter.hasNext()) {
            FamilyPlanningMethodChangeReason changeReason = (FamilyPlanningMethodChangeReason)iter.next();

            FamilyPlanningMethodChangeReason fp = new FamilyPlanningMethodChangeReason();
            fp.setId(changeReason.getId());
            fp.setName(changeReason.getName());
            fp.setDescription(changeReason.getDescription());
            fpList.add(fp);
        }
        return fpList;
    }

    @Override
    public List<Insulin> listInsulinType() {
        Query query = sessionFactory.getCurrentSession().createQuery(" from Insulin WHERE voided = 0 ");

        List list = query.list();
        Iterator iter = list.iterator();
        List<Insulin> inList = new ArrayList<Insulin>();
        while(iter.hasNext()) {
            Insulin insulin = (Insulin)iter.next();

            Insulin in = new Insulin();
            in.setId(insulin.getId());
            in.setName(insulin.getName());
            in.setDescription(insulin.getDescription());
            inList.add(in);
        }
        return inList;
    }

    @Override
    public List<BloodSugarLevel> listRandomBloodSugarLevel() {
        Query query = sessionFactory.getCurrentSession().createQuery(" from BloodSugarLevel WHERE voided = 0 ");

        List list = query.list();
        Iterator iter = list.iterator();
        List<BloodSugarLevel> bloodSugarLevelList = new ArrayList<BloodSugarLevel>();
        while(iter.hasNext()) {
            BloodSugarLevel sugarLevel = (BloodSugarLevel)iter.next();

            BloodSugarLevel level = new BloodSugarLevel();
            level.setId(sugarLevel.getId());
            level.setName(sugarLevel.getName());
            level.setDescription(sugarLevel.getDescription());
            bloodSugarLevelList.add(level);
        }
        return bloodSugarLevelList;
    }

    @Override
    public List<CdrrCategory> listCDRRCategoty() {
        Query query = sessionFactory.getCurrentSession().createQuery(" from CdrrCategory WHERE voided = 0 ");

        List list = query.list();
        Iterator iter = list.iterator();
        List<CdrrCategory> cdrrCategoryList = new ArrayList<CdrrCategory>();
        while(iter.hasNext()) {
            CdrrCategory category = (CdrrCategory)iter.next();

            CdrrCategory cat = new CdrrCategory();
            cat.setId(category.getId());
            cat.setName(category.getName());
            cat.setDescription(category.getDescription());
            cdrrCategoryList.add(cat);
        }
        return cdrrCategoryList;
    }

    @Override
    public void updateAccountType(AccountType accountType) {
        AccountType accType = (AccountType) sessionFactory.getCurrentSession().get(AccountType.class, accountType.getId());
        accType.setName(accountType.getName());
        accType.setDescription(accountType.getDescription());
        accType.setUpdatedOn(new Timestamp(new Date().getTime()));
        accType.setUpdatedBy(accountType.getUpdatedBy());

        deleteAccountTypeTransactionType(accType.getId());

        Collection accTypeTxType = accountType.getAccountTypeTransactionTypesById();
        Iterator iterator = accTypeTxType.iterator();
        while (iterator.hasNext()) {
            AccountTypeTransactionType atty = (AccountTypeTransactionType)iterator.next();
            AccountTypeTransactionType actxType = new AccountTypeTransactionType();
            actxType.setAccountTypeId(accType.getId());
            actxType.setTransactionTypeId(atty.getTransactionTypeId());
            saveAccountTypeTransactionType(actxType);
        }

        sessionFactory.getCurrentSession().update(accType);
    }

    @Override
    public void updateRegimenType(RegimenType regimenType) {
        RegimenType regType = (RegimenType)sessionFactory.getCurrentSession().get(RegimenType.class, regimenType.getId());
        if(regType != null) {
            regType.setName(regimenType.getName());
            regType.setDescription(regimenType.getDescription());
            regType.setUpdatedBy(regimenType.getUpdatedBy());
            regType.setUpdatedOn(new Timestamp(new Date().getTime()));
            sessionFactory.getCurrentSession().update(regType);
        }

    }

    @Override
    public void updateDosage(Dosage dosage) {
        Dosage dos = (Dosage)sessionFactory.getCurrentSession().get(Dosage.class, dosage.getId());
        if(dos != null) {
            dos.setName(dosage.getName());
            dos.setDescription(dosage.getDescription());
            dos.setUpdatedOn(new Timestamp(new Date().getTime()));
            dos.setUpdatedBy(dosage.getUpdatedBy());
            sessionFactory.getCurrentSession().update(dos);
        }
    }

    @Override
    public void updateDispensingUnit(DispensingUnit dispensingUnit) {
        Query q = sessionFactory.getCurrentSession().createQuery(" FROM DispensingUnit WHERE id = " + dispensingUnit.getId());
        List<DispensingUnit> dList = q.list();
        Iterator iterator = dList.iterator();
        DispensingUnit dUnit = null;
        while(iterator.hasNext()) {
            dUnit = (DispensingUnit)iterator.next();
        }

        if(dUnit != null) {
            dUnit.setName(dispensingUnit.getName());
            dUnit.setDescription(dispensingUnit.getDescription());
            dUnit.setMaximum(dispensingUnit.getMaximum());
            dUnit.setMinimum(dispensingUnit.getMinimum());
            dUnit.setUpdatedBy(dispensingUnit.getUpdatedBy());
            dUnit.setUpdatedOn(new Timestamp(new Date().getTime()));
            sessionFactory.getCurrentSession().update(dUnit);
        }
    }

    @Override
    public void updateGenericName(GenericName genericName) {
        GenericName gName = (GenericName)sessionFactory.getCurrentSession().get(GenericName.class, genericName.getId());
        if(gName != null) {
            gName.setName(genericName.getName());
            gName.setUpdatedOn(new Timestamp(new Date().getTime()));
            gName.setUpdatedBy(genericName.getUpdatedBy());
            sessionFactory.getCurrentSession().update(gName);
        }
    }

    @Override
    public void updateDistrict(District district) {
        District dist = (District)sessionFactory.getCurrentSession().get(District.class, district.getId());
        if(dist != null) {
            dist.setName(district.getName());
            dist.setRegionId(district.getRegionId());
            dist.setCode(district.getCode());
            dist.setUpdatedBy(district.getUpdatedBy());
            dist.setUpdatedOn(new Timestamp(new Date().getTime()));
            sessionFactory.getCurrentSession().update(dist);
        }

    }

    @Override
    public void updateIdentifierType(IdentifierType identifierType) {
        IdentifierType idType = (IdentifierType)sessionFactory.getCurrentSession().get(IdentifierType.class, identifierType.getId());
        if(idType != null) {
            idType.setName(identifierType.getName());
            idType.setDescription(identifierType.getDescription());
            idType.setRequired(identifierType.getRequired());
            idType.setUpdatedOn(new Timestamp(new Date().getTime()));
            idType.setUpdatedBy(identifierType.getUpdatedBy());
            sessionFactory.getCurrentSession().update(idType);
        }
    }

    @Override
    public void updatePatientSource(PatientSource patientSource) {
        PatientSource pSource = (PatientSource)sessionFactory.getCurrentSession().get(PatientSource.class, patientSource.getId());
        if(pSource != null) {
            pSource.setName(patientSource.getName());
            pSource.setDescription(patientSource.getDescription());
            pSource.setUpdatedBy(patientSource.getUpdatedBy());
            pSource.setUpdatedOn(new Timestamp(new Date().getTime()));
            sessionFactory.getCurrentSession().update(pSource);
        }
    }

    @Override
    public void updateServiceType(ServiceType serviceType) {
        ServiceType sType = (ServiceType)sessionFactory.getCurrentSession().get(ServiceType.class, serviceType.getId());
        if(sType != null) {
            sType.setName(serviceType.getName());
            sType.setDescription(serviceType.getDescription());
            sType.setService_area(serviceType.getService_area());
            sType.setUpdatedBy(serviceType.getUpdatedBy());
            sType.setUpdatedOn(new Timestamp(new Date().getTime()));
            sessionFactory.getCurrentSession().update(sType);
        }
    }

    @Override
    public void updateSupportingOrganization(SupportingOrganization supportingOrganization) {
        SupportingOrganization sOrg = (SupportingOrganization)sessionFactory.getCurrentSession().get(SupportingOrganization.class, supportingOrganization.getId());
        if(sOrg != null) {
            sOrg.setName(supportingOrganization.getName());
            sOrg.setDescription(supportingOrganization.getDescription());
            sOrg.setUpdatedBy(supportingOrganization.getUpdatedBy());
            sOrg.setUpdatedOn(new Timestamp(new Date().getTime()));
            sessionFactory.getCurrentSession().update(sOrg);
        }
    }

    @Override
    public void updateTransactionType(TransactionType transactionType) {
        TransactionType tType = (TransactionType)sessionFactory.getCurrentSession().get(TransactionType.class, transactionType.getId());
        if(tType != null) {
            tType.setName(transactionType.getName());
            tType.setDescription(transactionType.getDescription());
            tType.setNarrated(transactionType.getNarrated());
            tType.setDocumentable(transactionType.getDocumentable());
            tType.setUpdatedOn(new Timestamp(new Date().getTime()));
            tType.setUpdatedBy(transactionType.getUpdatedBy());
            sessionFactory.getCurrentSession().update(tType);
        }
    }

    @Override
    public void updateRegion(Region region) {
        Region reg = (Region)sessionFactory.getCurrentSession().get(Region.class, region.getId());
        if(reg != null) {
            reg.setName(region.getName());
            reg.setUpdatedBy(region.getUpdatedBy());
            reg.setUpdatedOn(new Timestamp(new Date().getTime()));
            sessionFactory.getCurrentSession().update(reg);
        }

    }

    @Override
    public void updatePatientStatus(PatientStatus patientStatus) {
        PatientStatus pStatus = (PatientStatus)sessionFactory.getCurrentSession().get(PatientStatus.class, patientStatus.getId());
        if(pStatus != null) {
            pStatus.setName(patientStatus.getName());
            pStatus.setUpdatedBy(patientStatus.getUpdatedBy());
            pStatus.setUpdatedOn(new Timestamp(new Date().getTime()));
            sessionFactory.getCurrentSession().update(pStatus);
        }
    }

    @Override
    public void updateIndication(Indication indication) {
        Indication in = (Indication)sessionFactory.getCurrentSession().get(Indication.class, indication.getId());
        if(in != null) {
            in.setName(indication.getName());
            in.setDescription(indication.getDescription());
            in.setUpdatedOn(new Timestamp(new Date().getTime()));
            in.setUpdatedBy(indication.getUpdatedBy());
            sessionFactory.getCurrentSession().update(in);
        }
    }

    @Override
    public void updateRegimenChangeReason(RegimenChangeReason regimenChangeReason) {
        RegimenChangeReason regC = (RegimenChangeReason)sessionFactory.getCurrentSession().get(RegimenChangeReason.class, regimenChangeReason.getId());
        if(regC != null) {
            regC.setName(regimenChangeReason.getName());
            regC.setDescription(regimenChangeReason.getDescription());
            regC.setUpdatedBy(regimenChangeReason.getUpdatedBy());
            regC.setUpdatedOn(new Timestamp(new Date().getTime()));
            sessionFactory.getCurrentSession().update(regC);
        }
    }

    @Override
    public void updateVisitType(VisitType visitType) {
        VisitType vType = (VisitType)sessionFactory.getCurrentSession().get(VisitType.class, visitType.getId());
        if(vType != null) {
            vType.setName(visitType.getName());
            vType.setDescription(visitType.getDescription());
            vType.setServiceTypeId(visitType.getServiceTypeId());
            vType.setUpdatedBy(visitType.getUpdatedBy());
            vType.setUpdatedOn(new Timestamp(new Date().getTime()));
            sessionFactory.getCurrentSession().update(vType);
        }
    }

    @Override
    public void updateRegimenStatus(RegimenStatus regimenStatus) {
        RegimenStatus rStatus = (RegimenStatus)sessionFactory.getCurrentSession().get(RegimenStatus.class, regimenStatus.getId());
        if(rStatus != null) {
            rStatus.setName(regimenStatus.getName());
            rStatus.setDescription(regimenStatus.getDescription());
            rStatus.setUpdatedBy(regimenStatus.getUpdatedBy());
            rStatus.setUpdatedOn(new Timestamp(new Date().getTime()));
            sessionFactory.getCurrentSession().update(rStatus);
        }
    }

    @Override
    public void updateDrugCategory(DrugCategory drugCategory) {
        DrugCategory dCat = (DrugCategory)sessionFactory.getCurrentSession().get(DrugCategory.class, drugCategory.getId());
        if(dCat != null) {
            dCat.setName(drugCategory.getName());
            dCat.setDescription(drugCategory.getDescription());
            dCat.setUpdatedBy(drugCategory.getUpdatedBy());
            dCat.setUpdatedOn(new Timestamp(new Date().getTime()));
            sessionFactory.getCurrentSession().update(dCat);
        }
    }

    @Override
    public void updateDrugForm(DrugForm drugForm) {
        DrugForm dForm = (DrugForm)sessionFactory.getCurrentSession().get(DrugForm.class, drugForm.getId());
        if(dForm != null) {
            dForm.setName(drugForm.getName());
            dForm.setDescription(drugForm.getDescription());
            dForm.setUpdatedOn(new Timestamp(new Date().getTime()));
            dForm.setUpdatedBy(drugForm.getUpdatedBy());
            sessionFactory.getCurrentSession().update(dForm);
        }
    }

    @Override
    public void updateDrugType(DrugType drugType) {
        DrugType dType = (DrugType)sessionFactory.getCurrentSession().get(DrugType.class, drugType.getId());
        dType.setName(drugType.getName());
        dType.setDescription(drugType.getDescription());
        dType.setUpdatedBy(drugType.getUpdatedBy());
        dType.setUpdatedOn(new Timestamp(new Date().getTime()));
        sessionFactory.getCurrentSession().update(dType);
    }

    @Override
    public void updateAccount(Account account) {
        Account acc = (Account)sessionFactory.getCurrentSession().get(Account.class, account.getId());
        acc.setName(account.getName());
        acc.setDescription(account.getDescription());
        acc.setCannot_dispense(account.getCannot_dispense());
        acc.setIs_bulkstore(account.getIs_bulkstore());
        acc.setIs_satellite(account.getIs_satellite());
        acc.setAccountTypeId(account.getAccountTypeId());
        acc.setUpdatedBy(account.getUpdatedBy());
        acc.setUpdatedOn(new Timestamp(new Date().getTime()));
        sessionFactory.getCurrentSession().update(acc);
    }

    @Override
    public void updateFamilyPlanningMethod(FamilyPlanningMethod planningMethod) {
        FamilyPlanningMethod fp = (FamilyPlanningMethod)sessionFactory.getCurrentSession().get(FamilyPlanningMethod.class, planningMethod.getId());
        fp.setName(planningMethod.getName());
        fp.setDescription(planningMethod.getDescription());
        fp.setUpdatedBy(planningMethod.getUpdatedBy());
        fp.setUpdatedOn(new Timestamp(new Date().getTime()));
        sessionFactory.getCurrentSession().update(fp);
    }

    @Override
    public void updateFamilyPlanningMethodChangeReason(FamilyPlanningMethodChangeReason methodChangeReason) {
        FamilyPlanningMethodChangeReason fp = (FamilyPlanningMethodChangeReason)sessionFactory.getCurrentSession().get(FamilyPlanningMethodChangeReason.class, methodChangeReason.getId());
        fp.setName(methodChangeReason.getName());
        fp.setDescription(methodChangeReason.getDescription());
        fp.setUpdatedBy(methodChangeReason.getUpdatedBy());
        fp.setUpdatedOn(new Timestamp(new Date().getTime()));
        sessionFactory.getCurrentSession().update(fp);
    }

    @Override
    public void updateInsulin(Insulin insulin) {
        Insulin in = (Insulin)sessionFactory.getCurrentSession().get(Insulin.class, insulin.getId());
        in.setName(insulin.getName());
        in.setDescription(insulin.getDescription());
        in.setUpdatedBy(insulin.getUpdatedBy());
        in.setUpdatedOn(new Timestamp(new Date().getTime()));
        sessionFactory.getCurrentSession().update(in);
    }

    @Override
    public void updateBloodSugarLevel(BloodSugarLevel bloodSugarLevel) {
        BloodSugarLevel in = (BloodSugarLevel)sessionFactory.getCurrentSession().get(BloodSugarLevel.class, bloodSugarLevel.getId());
        in.setName(bloodSugarLevel.getName());
        in.setDescription(bloodSugarLevel.getDescription());
        in.setUpdatedBy(bloodSugarLevel.getUpdatedBy());
        in.setUpdatedOn(new Timestamp(new Date().getTime()));
        sessionFactory.getCurrentSession().update(in);
    }

    @Override
    public void updateCDRRCategory(CdrrCategory cdrrCategory) {
        CdrrCategory cat = (CdrrCategory)sessionFactory.getCurrentSession().get(CdrrCategory.class, cdrrCategory.getId());
        cat.setName(cdrrCategory.getName());
        cat.setDescription(cdrrCategory.getDescription());
        cat.setUpdatedBy(cdrrCategory.getUpdatedBy());
        cat.setUpdatedOn(new Timestamp(new Date().getTime()));
        sessionFactory.getCurrentSession().update(cat);
    }

    /**
     *  Delete functions
     * @param
     */
    @Override
    public void deleteAccountType(Integer accountTypeId, Integer userId) {
        AccountType accType = (AccountType) sessionFactory.getCurrentSession().get(AccountType.class, accountTypeId);
        accType.setVoided((byte)1);
        accType.setVoidedOn(new Timestamp(new Date().getTime()));
        accType.setVoidedBy(userId);
        sessionFactory.getCurrentSession().update(accType);
    }

    @Override
    public void deleteRegimenType(Integer regimenTypeId, Integer userId) {
        RegimenType regType = (RegimenType)sessionFactory.getCurrentSession().get(RegimenType.class, regimenTypeId);
        if(regType != null) {
            regType.setVoided((byte)1);
            regType.setVoidedBy(userId);
            regType.setVoidedOn(new Timestamp(new Date().getTime()));
            sessionFactory.getCurrentSession().update(regType);
        }

    }

    @Override
    public void deleteDosage(Integer dosageId, Integer userId) {
        Dosage dos = (Dosage)sessionFactory.getCurrentSession().get(Dosage.class, dosageId);
        if(dos != null) {
            dos.setVoided((byte)1);
            dos.setVoidedOn(new Timestamp(new Date().getTime()));
            dos.setVoidedBy(userId);
            sessionFactory.getCurrentSession().update(dos);
        }
    }

    @Override
    public void deleteDispensingUnit(Integer dispensingUnitId, Integer userId) {
        DispensingUnit dUnit = (DispensingUnit)sessionFactory.getCurrentSession().get(DispensingUnit.class, dispensingUnitId);
        if(dUnit != null) {
            dUnit.setVoided((byte)1);
            dUnit.setVoidedBy(userId);
            dUnit.setVoidedOn(new Timestamp(new Date().getTime()));
            sessionFactory.getCurrentSession().update(dUnit);
        }
    }

    @Override
    public void deleteGenericName(Integer genericNameId, Integer userId) {
        GenericName gName = (GenericName)sessionFactory.getCurrentSession().get(GenericName.class, genericNameId);
        if(gName != null) {
            gName.setVoided((byte)1);
            gName.setVoidedOn(new Timestamp(new Date().getTime()));
            gName.setVoidedBy(userId);
            sessionFactory.getCurrentSession().update(gName);
        }
    }

    @Override
    public void deleteDistrict(Integer districtId, Integer userId) {
        District dist = (District)sessionFactory.getCurrentSession().get(District.class, districtId);
        if(dist != null) {
            dist.setVoided((byte)1);
            dist.setVoidedBy(userId);
            dist.setVoidedOn(new Timestamp(new Date().getTime()));
            sessionFactory.getCurrentSession().update(dist);
        }

    }

    @Override
    public void deleteIdentifierType(Integer identifierTypeId, Integer userId) {
        IdentifierType idType = (IdentifierType)sessionFactory.getCurrentSession().get(IdentifierType.class, identifierTypeId);
        if(idType != null) {
            idType.setVoided((byte)1);
            idType.setVoidedOn(new Timestamp(new Date().getTime()));
            idType.setVoidedBy(userId);
            sessionFactory.getCurrentSession().update(idType);
        }
    }

    @Override
    public void deletePatientSource(Integer patientSourceId, Integer userId) {
        PatientSource pSource = (PatientSource)sessionFactory.getCurrentSession().get(PatientSource.class, patientSourceId);
        if(pSource != null) {
            pSource.setVoided((byte)1);
            pSource.setVoidedBy(userId);
            pSource.setVoidedOn(new Timestamp(new Date().getTime()));
            sessionFactory.getCurrentSession().update(pSource);
        }
    }

    @Override
    public void deleteServiceType(Integer serviceTypeId, Integer userId) {
        ServiceType sType = (ServiceType)sessionFactory.getCurrentSession().get(ServiceType.class, serviceTypeId);
        if(sType != null) {
            sType.setVoided((byte)1);
            sType.setVoidedBy(userId);
            sType.setVoidedOn(new Timestamp(new Date().getTime()));
            sessionFactory.getCurrentSession().update(sType);
        }
    }

    @Override
    public void deleteSupportingOrganization(Integer supportingOrganizationId, Integer userId) {
        SupportingOrganization sOrg = (SupportingOrganization)sessionFactory.getCurrentSession().get(SupportingOrganization.class, supportingOrganizationId);
        if(sOrg != null) {
            sOrg.setVoided((byte)1);
            sOrg.setVoidedBy(userId);
            sOrg.setVoidedOn(new Timestamp(new Date().getTime()));
            sessionFactory.getCurrentSession().update(sOrg);
        }
    }

    @Override
    public void deleteTransactionType(Integer transactionTypeId, Integer userId) {
        TransactionType tType = (TransactionType)sessionFactory.getCurrentSession().get(TransactionType.class, transactionTypeId);
        if(tType != null) {
            tType.setVoided((byte)1);
            tType.setVoidedOn(new Timestamp(new Date().getTime()));
            tType.setVoidedBy(userId);
            sessionFactory.getCurrentSession().update(tType);
        }
    }

    @Override
    public void deleteRegion(Integer regionId, Integer userId) {
        Region reg = (Region)sessionFactory.getCurrentSession().get(Region.class, regionId);
        if(reg != null) {
            reg.setVoided((byte)1);
            reg.setVoidedBy(userId);
            reg.setVoidedOn(new Timestamp(new Date().getTime()));
            sessionFactory.getCurrentSession().update(reg);
        }

    }

    @Override
    public void deletePatientStatus(Integer patientStatusId, Integer userId) {
        PatientStatus pStatus = (PatientStatus)sessionFactory.getCurrentSession().get(PatientStatus.class, patientStatusId);
        if(pStatus != null) {
            pStatus.setVoided((byte)1);
            pStatus.setVoidedBy(userId);
            pStatus.setVoidedOn(new Timestamp(new Date().getTime()));
            sessionFactory.getCurrentSession().update(pStatus);
        }
    }

    @Override
    public void deleteIndication(Integer indicationId, Integer userId) {
        Indication in = (Indication)sessionFactory.getCurrentSession().get(Indication.class, indicationId);
        if(in != null) {
            in.setVoided((byte)1);
            in.setVoidedOn(new Timestamp(new Date().getTime()));
            in.setVoidedBy(userId);
            sessionFactory.getCurrentSession().update(in);
        }
    }

    @Override
    public void deleteRegimenChangeReason(Integer regimenChangeReasonId, Integer userId) {
        RegimenChangeReason regC = (RegimenChangeReason)sessionFactory.getCurrentSession().get(RegimenChangeReason.class, regimenChangeReasonId);
        if(regC != null) {
            regC.setVoided((byte)1);
            regC.setVoidedBy(userId);
            regC.setVoidedOn(new Timestamp(new Date().getTime()));
            sessionFactory.getCurrentSession().update(regC);
        }
    }

    @Override
    public void deleteVisitType(Integer visitTypeId, Integer userId) {
        VisitType vType = (VisitType)sessionFactory.getCurrentSession().get(VisitType.class, visitTypeId);
        if(vType != null) {
            vType.setVoided((byte)1);
            vType.setVoidedBy(userId);
            vType.setVoidedOn(new Timestamp(new Date().getTime()));
            sessionFactory.getCurrentSession().update(vType);
        }
    }

    @Override
    public void deleteRegimenStatus(Integer regimenStatusId, Integer userId) {
        RegimenStatus rStatus = (RegimenStatus)sessionFactory.getCurrentSession().get(RegimenStatus.class, regimenStatusId);
        if(rStatus != null) {
            rStatus.setVoided((byte)1);
            rStatus.setVoidedBy(userId);
            rStatus.setVoidedOn(new Timestamp(new Date().getTime()));
            sessionFactory.getCurrentSession().update(rStatus);
        }
    }

    @Override
    public void deleteDrugCategory(Integer drugCategoryId, Integer userId) {
        DrugCategory dCat = (DrugCategory)sessionFactory.getCurrentSession().get(DrugCategory.class, drugCategoryId);
        if(dCat != null) {
            dCat.setVoided((byte)1);
            dCat.setVoidedBy(userId);
            dCat.setVoidedOn(new Timestamp(new Date().getTime()));
            sessionFactory.getCurrentSession().update(dCat);
        }
    }

    @Override
    public void deleteDrugForm(Integer drugFormId, Integer userId) {
        DrugForm dForm = (DrugForm)sessionFactory.getCurrentSession().get(DrugForm.class, drugFormId);
        if(dForm != null) {
            dForm.setVoided((byte)1);
            dForm.setVoidedOn(new Timestamp(new Date().getTime()));
            dForm.setVoidedBy(userId);
            sessionFactory.getCurrentSession().update(dForm);
        }
    }

    @Override
    public void deleteDrugType(Integer drugTypeId, Integer userId) {
        DrugType dType = (DrugType)sessionFactory.getCurrentSession().get(DrugType.class, drugTypeId);
        dType.setVoided((byte)1);
        dType.setVoidedBy(userId);
        dType.setVoidedOn(new Timestamp(new Date().getTime()));
        sessionFactory.getCurrentSession().update(dType);
    }

    @Override
    public void deleteAccount(Integer accountId, Integer userId) {
        Account acc = (Account)sessionFactory.getCurrentSession().get(Account.class, accountId);
        acc.setVoided((byte)1);
        acc.setVoidedBy(userId);
        acc.setVoidedOn(new Timestamp(new Date().getTime()));
        sessionFactory.getCurrentSession().update(acc);
    }

    @Override
    public void deleteAccountTypeTransactionType(Integer accountTypeId) {
        Query query = sessionFactory.getCurrentSession().createQuery("DELETE from AccountTypeTransactionType WHERE accountTypeId = " + accountTypeId);
        query.executeUpdate();
    }

    @Override
    public void deleteFamilyPlanningMethod(Integer familyPlanningMethodId, Integer userId) {
        FamilyPlanningMethod fp = (FamilyPlanningMethod)sessionFactory.getCurrentSession().get(FamilyPlanningMethod.class, familyPlanningMethodId);
        fp.setVoided((byte)1);
        fp.setVoidedBy(userId);
        fp.setVoidedOn(new Timestamp(new Date().getTime()));
        sessionFactory.getCurrentSession().update(fp);
    }

    @Override
    public void deleteFamilyPlanningMethodChangeReason(Integer familyPlanningMethodChangeReasonId, Integer userId) {
        FamilyPlanningMethodChangeReason fp = (FamilyPlanningMethodChangeReason)sessionFactory.getCurrentSession().get(FamilyPlanningMethodChangeReason.class, familyPlanningMethodChangeReasonId);
        fp.setVoided((byte)1);
        fp.setVoidedBy(userId);
        fp.setVoidedOn(new Timestamp(new Date().getTime()));
        sessionFactory.getCurrentSession().update(fp);
    }

    @Override
    public void deleteInsulin(Integer insulinId, Integer userId) {
        Insulin insulin = (Insulin)sessionFactory.getCurrentSession().get(Insulin.class, insulinId);
        insulin.setVoided((byte) 1);
        insulin.setVoidedBy(userId);
        insulin.setVoidedOn(new Timestamp(new Date().getTime()));
        sessionFactory.getCurrentSession().update(insulin);
    }

    @Override
    public void deleteBloodSugarLevel(Integer bloodSugarLevelId, Integer userId) {
        BloodSugarLevel sugarLevel = (BloodSugarLevel)sessionFactory.getCurrentSession().get(BloodSugarLevel.class, bloodSugarLevelId);
        sugarLevel.setVoided((byte) 1);
        sugarLevel.setVoidedBy(userId);
        sugarLevel.setVoidedOn(new Timestamp(new Date().getTime()));
        sessionFactory.getCurrentSession().update(sugarLevel);
    }

    @Override
    public void deleteCDRRCategory(Integer categoryID, Integer userId) {
        CdrrCategory category = (CdrrCategory)sessionFactory.getCurrentSession().get(CdrrCategory.class, categoryID);
        category.setVoided((byte) 1);
        category.setVoidedBy(userId);
        category.setVoidedOn(new Timestamp(new Date().getTime()));
        sessionFactory.getCurrentSession().update(category);
    }

    @Override
    public void saveAccountTypeTransactionType(AccountTypeTransactionType accountTypeTransactionType) {
        sessionFactory.getCurrentSession().save(accountTypeTransactionType);
    }

    @Override
    public String getServiceName(Integer serviceTypeId) {
        ServiceType serviceType = (ServiceType)sessionFactory.getCurrentSession().get(ServiceType.class, serviceTypeId);
        if(serviceType != null)
            return  serviceType.getName();
        return "";
    }

    @Override
    public String getPatientStatusName(Integer patientStatusId) {
        PatientStatus patientStatus = (PatientStatus)sessionFactory.getCurrentSession().get(PatientStatus.class, patientStatusId);
        if(patientStatus != null)
            return  patientStatus.getName();
        return "";
    }

    @Override
    public String getDrugName(Integer drugId) {
        Drug drug = (Drug)sessionFactory.getCurrentSession().get(Drug.class, drugId);
        if(drug != null)
            return  drug.getName();
        return "";
    }

    @Override
    public Integer createProperty(Property property) {
        Integer id = (Integer)sessionFactory.getCurrentSession().save(property);
        return id;
    }

    @Override
    public void deleteProperty(Integer propertyId, Integer userId) {
        Property p = (Property) sessionFactory.getCurrentSession().get(Property.class, propertyId);
        if(p != null) {
            p.setVoidedOn(new Timestamp(new Date().getTime()));
            p.setVoided((byte)1);
            p.setVoidedBy(userId);
        }
    }

    @Override
    public void updateProperty(Property property) {
        Property p = (Property) sessionFactory.getCurrentSession().get(Property.class, property.getId());
        if(p != null) {
            p.setKey(property.getKey());
            p.setValue(property.getValue());
            p.setDescription(property.getDescription());
            p.setUpdatedOn(property.getUpdatedOn());
            p.setUpdatedBy(property.getUpdatedBy());
            sessionFactory.getCurrentSession().update(p);
        }
    }

    @Override
    public Property getProperty(String key) {
        List<Property> pList = sessionFactory.getCurrentSession().createQuery( " FROM Property WHERE key = '" + key + "' AND voided = 0").list();
        if(pList != null) {
            Iterator iterator = pList.iterator();
            while(iterator.hasNext()) {
                Property p = (Property)iterator.next();
                return p;
            }
        }
        return null;
    }

    @Override
    public List<ServiceType> getSubServices(Integer serviceId) {
        List<ServiceType> stypes = sessionFactory.getCurrentSession().createQuery( " FROM ServiceType  WHERE name IN ('ART', 'PEP', 'PMTCT', 'OI Only')").list();
        if(stypes != null) {
            return stypes;
        }
        return null;
    }

    @Override
    public ServiceType getServiceType(Integer serviceTypeId) {
        ServiceType sType = (ServiceType)sessionFactory.getCurrentSession().get(ServiceType.class, serviceTypeId);
        return sType;
    }

    @Override
    public ServiceType getServiceType(String serviceType) {
        List<ServiceType> ps = sessionFactory.getCurrentSession().createQuery(" FROM ServiceType  WHERE name = '" + serviceType+ "'").list();
        Iterator iterator = ps.iterator();
        while(iterator.hasNext()) {
            ServiceType p = (ServiceType)iterator.next();
            return p;
        }
        return null;
    }

    @Override
    public PatientStatus getPatientStatus(String name) {
        List<PatientStatus> ps = sessionFactory.getCurrentSession().createQuery(" FROM PatientStatus  WHERE name = '" + name+ "'").list();
        Iterator iterator = ps.iterator();
        while(iterator.hasNext()) {
            PatientStatus p = (PatientStatus)iterator.next();
            return p;
        }
        return null;
    }

    @Override
    public TransactionType getTransactionType(String name) {
        List<TransactionType> ps = sessionFactory.getCurrentSession().createQuery(" FROM TransactionType  WHERE name = '" + name+ "'").list();
        Iterator iterator = ps.iterator();
        while(iterator.hasNext()) {
            TransactionType tp = (TransactionType)iterator.next();
            return tp;
        }
        return null;
    }

    @Override
    public VisitType getVisitType(String name) {
        List<VisitType> ps = sessionFactory.getCurrentSession().createQuery(" FROM VisitType  WHERE name = '" + name+ "'").list();
        Iterator iterator = ps.iterator();
        while(iterator.hasNext()) {
            VisitType tp = (VisitType)iterator.next();
            return tp;
        }
        return null;
    }

    @Override
    public List<Facility> listFacility() {
        Query query = sessionFactory.getCurrentSession().createQuery(" FROM Facility WHERE voided = 0 ");

        List list = query.list();
        Iterator iter = list.iterator();
        List<Facility> facilityList = new ArrayList<Facility>();
        while(iter.hasNext()) {
            Facility facility = (Facility)iter.next();

            Facility f = new Facility();
            f.setId(facility.getId());
            f.setName(facility.getName());

            facilityList.add(f);
        }
        return facilityList;
    }

    @Override
    public Account getAccount(Integer accountId) {
        Account account = (Account)sessionFactory.getCurrentSession().get(Account.class, accountId);
        return account;
    }

    @Override
    public List<Account> listAccounts(String type) {
        Query query = sessionFactory.getCurrentSession().createSQLQuery(" SELECT a.id, a.name FROM account a WHERE account_type_id IN (SELECT id FROM  account_type WHERE name = '" + type + "') AND a.voided = 0");

        List list = query.list();
        Iterator iter = list.iterator();
        List<Account> accountList = new ArrayList<Account>();
        while(iter.hasNext()) {
            Object[] row = (Object[])iter.next();

            Account acc = new Account();
            acc.setId((Integer)row[0]);
            acc.setName((String)row[1]);
            accountList.add(acc);
        }
        return accountList;
    }

    @Override
    public List<PatientIdentifier> listPatientIdentifier() {
        Query query = sessionFactory.getCurrentSession().createQuery(" FROM PatientIdentifier ");

        List list = query.list();
        Iterator iter = list.iterator();
        List<PatientIdentifier> patientIdentifierList = new ArrayList<PatientIdentifier>();
        while(iter.hasNext()) {
            PatientIdentifier row = (PatientIdentifier)iter.next();

            PatientIdentifier acc = new PatientIdentifier();
            acc.setIdentifier(row.getIdentifier());
            acc.setIdentifierTypeId(row.getIdentifierTypeId());
            acc.setPatientId(row.getPatientId());
            patientIdentifierList.add(acc);
        }
        return patientIdentifierList;
    }
}
