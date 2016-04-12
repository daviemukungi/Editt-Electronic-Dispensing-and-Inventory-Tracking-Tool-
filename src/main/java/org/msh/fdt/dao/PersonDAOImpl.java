package org.msh.fdt.dao;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.msh.fdt.dto.PersonInfo;
import org.msh.fdt.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by kenny on 4/2/14.
 */
@Repository
public class PersonDAOImpl implements PersonDAO {

    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    private ReportsDAO reportsDAO;

    public Integer addPerson(Person person) {
        Integer id = (Integer)sessionFactory.getCurrentSession().save(person);
        return id;
    }

    @Override
    public Integer savePatientIdentifier(PatientIdentifier identifier) {
        Integer id = (Integer)sessionFactory.getCurrentSession().save(identifier);
        return id;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<PersonInfo> getAllPerson() {
       // Query query = sessionFactory.getCurrentSession().createSQLQuery("SELECT p.id, p.surname, p.first_name, p.other_names, pt.id as patient_id, p.sex, p.date_of_birth, pi.identifier, (SELECT group_concat(service_type_id) FROM patient_service_type pst WHERE pst.patient_id = pt.id) as gid FROM person p JOIN patient pt ON p.id = pt.person_id JOIN patient_identifier pi ON pi.patient_id = pt.id AND pi.active =1");
        Query query = sessionFactory.getCurrentSession().createSQLQuery("SELECT p.id, p.surname, p.first_name, p.other_names, pt.id as patient_id, p.sex, p.date_of_birth, pi.identifier, (SELECT group_concat(service_type_id) FROM patient_service_type pst WHERE pst.patient_id = pt.id) as gid FROM person p JOIN patient pt ON p.id = pt.person_id JOIN patient_identifier pi ON pi.patient_id = pt.id");

        List list = query.list();
        Iterator iter = list.iterator();
        List<PersonInfo> personList = new ArrayList<PersonInfo>();
        while(iter.hasNext()) {
            Object[] row = (Object[])iter.next();
            PersonInfo p = new PersonInfo();
            p.setId((Integer)row[0]);
            p.setSurname(row[1] == null ? "" : (String) row[1]);
            p.setFirstName(row[2] == null ? "" : (String) row[2]);
            p.setOtherNames(row[3] == null ? "" : (String) row[3]);
            p.setPatientId((Integer) row[4]);
            p.setGender((String) row[5]);
            p.setDateOfBirth((Timestamp) row[6]);
            //p.setPatientStatusId();
           // p.setServiceTypeId((Integer)row[7]);
            p.setIdentifier(row[7] == null ? "" : (String)row[7]);
            p.setServiceTypeId(row[8] == null ? new String[0] : ((String)row[8]).split(","));
            personList.add(p);
        }
        return personList;
    }

    public Integer addPatient(Patient patient){
        Integer id = (Integer)sessionFactory.getCurrentSession().save(patient);
        return id;
    }

    @Override
    public Integer savePersonAddress(PersonAddress address) {
        Integer id = (Integer)sessionFactory.getCurrentSession().save(address);
        return id;
    }

    @Override
    public Integer saveVisit(Visit visit) {
        Integer id = (Integer)sessionFactory.getCurrentSession().save(visit);
        return id;
    }

    @Override
    public Integer savePatientTransactionItem(PatientTransactionItem transactionItem) {
        Integer id = (Integer)sessionFactory.getCurrentSession().save(transactionItem);
        return id;
    }

    @Override
    public Patient getPatient(Integer personId) {
        Criteria c = sessionFactory.getCurrentSession().createCriteria(Patient.class, "patient").setProjection(Projections.projectionList().add(Projections.property("id"), "id").add(Projections.property("enrollmentDate"), "enrollmentDate").add(Projections.property("therapyStartDate"), "therapyStartDate").add(Projections.property("patientSourceId"), "patientSourceId").add(Projections.property("patientStatusId"), "patientStatusId").add(Projections.property("startRegimenId"), "startRegimenId").add(Projections.property("supportingOrganizationId"), "supportingOrganizationId").add(Projections.property("drugAllergies"), "drugAllergies").add(Projections.property("chronicIllnesses"), "chronicIllnesses").add(Projections.property("smoker"), "smoker").add(Projections.property("drinker"), "drinker").add(Projections.property("fromFacilityId"), "fromFacilityId")).add(Restrictions.eq("personId", personId)).setResultTransformer(Transformers.aliasToBean(Patient.class));
        List<Patient> pList = c.list();
        if(pList != null && pList.size() > 0) {
            return pList.get(0);
        }
        return null;
    }

    @Override
    public Person getPerson(Integer personId) {
        Criteria c = sessionFactory.getCurrentSession().createCriteria(Person.class, "person").setProjection(Projections.projectionList().add(Projections.property("id"), "id").add(Projections.property("surname"), "surname").add(Projections.property("firstName"), "firstName").add(Projections.property("otherNames"), "otherNames").add(Projections.property("sex"), "sex").add(Projections.property("dateOfBirth"), "dateOfBirth").add(Projections.property("birthDistrictId"), "birthDistrictId")).add(Restrictions.eq("id", personId)).setResultTransformer(Transformers.aliasToBean(Person.class));
        List<Person> pList = c.list();
        if(pList != null && pList.size() > 0) {
            return pList.get(0);
        }
        return null;
    }

    @Override
    public PersonAddress getPersonAddress(Integer personId) {
        Criteria c = sessionFactory.getCurrentSession().createCriteria(PersonAddress.class, "personAddress").setProjection(Projections.projectionList().add(Projections.property("id"), "id").add(Projections.property("telNo1"), "telNo1").add(Projections.property("telNo2"), "telNo2").add(Projections.property("postalAddress"), "postalAddress").add(Projections.property("emailAddress"), "emailAddress").add(Projections.property("physicalAddress"), "physicalAddress").add(Projections.property("alternativeAddress"), "alternativeAddress")).add(Restrictions.eq("personId", personId)).setResultTransformer(Transformers.aliasToBean(PersonAddress.class));
        List<PersonAddress> pList = c.list();
        if(pList != null && pList.size() > 0) {
            return pList.get(0);
        }
        return null;
    }

    @Override
    public PatientIdentifier getPatientIdentifier(Integer patientId) {
        Criteria c = sessionFactory.getCurrentSession().createCriteria(PatientIdentifier.class, "patientIdentifier").setProjection(Projections.projectionList().add(Projections.property("id"), "id").add(Projections.property("patientId"), "patientId").add(Projections.property("identifierTypeId"), "identifierTypeId").add(Projections.property("identifier"), "identifier")).add(Restrictions.eq("patientId", patientId)).add(Restrictions.eq("active", 1)).setResultTransformer(Transformers.aliasToBean(PatientIdentifier.class));
        List<PatientIdentifier> pList = c.list();
        if(pList != null && pList.size() > 0) {
            return pList.get(0);
        }
        return null;
    }

    @Override
    public List<PatientIdentifier> getPatientIdentifiers(Integer patientId) {
        Criteria c = sessionFactory.getCurrentSession().createCriteria(PatientIdentifier.class, "patientIdentifier").setProjection(Projections.projectionList().add(Projections.property("id"), "id").add(Projections.property("patientId"), "patientId").add(Projections.property("active"), "active").add(Projections.property("identifierTypeId"), "identifierTypeId").add(Projections.property("identifier"), "identifier")).add(Restrictions.eq("patientId", patientId)).setResultTransformer(Transformers.aliasToBean(PatientIdentifier.class));
        List<PatientIdentifier> pList = c.list();
        return pList;
    }

    public Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public Integer updatePerson(Person person) {
        Person per = (Person)sessionFactory.getCurrentSession().get(Person.class, person.getId());
        if(per != null) {
            per.setFirstName(person.getFirstName());
            per.setSurname(person.getSurname());
            per.setOtherNames(person.getOtherNames());
            per.setSex(person.getSex());
            per.setDateOfBirth(person.getDateOfBirth());
            per.setBirthDistrictId(person.getBirthDistrictId());
            per.setUpdatedOn(new Timestamp(new Date().getTime()));
            per.setUpdatedBy(person.getUpdatedBy());
            sessionFactory.getCurrentSession().update(per);
        }
        return per.getId();
    }

    @Override
    public void updatePersonAddress(PersonAddress personAddress) {
        PersonAddress pa = (PersonAddress)sessionFactory.getCurrentSession().get(PersonAddress.class, personAddress.getId());
        if(pa != null) {
            pa.setTelNo1(personAddress.getTelNo1());
            pa.setTelNo2(personAddress.getTelNo2());
            pa.setPostalAddress(personAddress.getPostalAddress());
            pa.setAlternativeAddress(personAddress.getAlternativeAddress());
            pa.setEmailAddress(personAddress.getEmailAddress());
            pa.setPhysicalAddress(personAddress.getPhysicalAddress());
            pa.setUpdatedOn(new Timestamp(new Date().getTime()));
            pa.setUpdatedBy(personAddress.getUpdatedBy());
            sessionFactory.getCurrentSession().update(pa);
        }
    }

    @Override
    public Integer updatePatient(Patient patient) {
        Patient pt = (Patient)sessionFactory.getCurrentSession().get(Patient.class, patient.getId());
        if(pt != null) {
            pt.setEnrollmentDate(patient.getEnrollmentDate());
            pt.setPatientSourceId(patient.getPatientSourceId());
            pt.setSupportingOrganizationId(patient.getSupportingOrganizationId());
            pt.setDrugAllergies(patient.getDrugAllergies());
            pt.setChronicIllnesses(patient.getChronicIllnesses());
            pt.setSmoker(patient.getSmoker());
            pt.setDrinker(patient.getDrinker());
            pt.setFromFacilityId(patient.getFromFacilityId());
            pt.setUpdatedOn(new Timestamp(new Date().getTime()));
            pt.setTherapyStartDate(patient.getTherapyStartDate());
            pt.setUpdatedBy(patient.getUpdatedBy());
            pt.setStartRegimenId(patient.getStartRegimenId());
            pt.setPatientStatusId(patient.getPatientStatusId());
            sessionFactory.getCurrentSession().update(pt);
        }
        return pt.getId();
    }

    @Override
    public Integer updatePatientIdentifier(PatientIdentifier identifier) {
        List<PatientIdentifier> pList = sessionFactory.getCurrentSession().createQuery("FROM PatientIdentifier WHERE patientId = " + identifier.getPatientId()).list();
        if(pList.size() > 0){
            for (PatientIdentifier identifier1 : pList) {
                identifier1.setActive(1);
                sessionFactory.getCurrentSession().update(identifier1);
            }
        }
        PatientIdentifier patientIdentifier = (PatientIdentifier)sessionFactory.getCurrentSession().get(PatientIdentifier.class, identifier.getId());
        if(patientIdentifier != null) {
            patientIdentifier.setIdentifier(identifier.getIdentifier());
            patientIdentifier.setIdentifierTypeId(identifier.getIdentifierTypeId());
            patientIdentifier.setActive(identifier.getActive());
            sessionFactory.getCurrentSession().update(patientIdentifier);
        }else{
            patientIdentifier = identifier;
            sessionFactory.getCurrentSession().save(patientIdentifier);
        }
        return patientIdentifier.getId();
    }

    @Override
    public Visit getLastVisit(Integer patientId) {
        List<Visit> v = sessionFactory.getCurrentSession().createQuery(" FROM Visit WHERE patientId = " + patientId + " AND id IN (SELECT max(id) FROM Visit WHERE  regimenId != null group by patientId )").list();
        if(v != null && v.size() > 0)
            return v.get(0);
        return null;
    }
    @Override
    public List loadLastDispense(Integer patientId) {
        String query  =  "SELECT id,date,duration,units_out,dosage_name,drug_id,adherence,pill_count FROM ( SELECT pti.id AS id, date(v.start_date) AS date, vt.name AS Purpose, du.name AS dispensing_unit, pti.dosage_name, pti.duration, d.name, ti.units_out, v.weight, r.name AS Last_Regimen, ti.batch_no, ti.drug_id as drug_id, pti.pill_count, i.name AS indication, pti.adherence, v.next_appointment_date, u.username FROM visit v LEFT JOIN visit_type vt ON v.visit_type_id = vt.id LEFT JOIN regimen r ON v.regimen_id = r.id LEFT JOIN transaction t ON v.id = t.visit_id LEFT JOIN transaction_item ti ON t.id = ti.transaction_id LEFT JOIN drug d ON d.id = ti.drug_id LEFT JOIN dispensing_unit du ON d.dispensing_unit_id = du.id LEFT JOIN patient_transaction_item pti ON pti.transaction_item_id = ti.id LEFT JOIN indication i ON i.id = t.indication_id JOIN user u ON u.id = v.created_by WHERE v.patient_id = "+patientId+" AND v.start_date = (SELECT max(start_date) FROM visit WHERE visit.patient_id = "+patientId+" AND visit.id = (SELECT max(id) FROM visit WHERE patient_id = "+patientId+")) HAVING units_out > 0 ORDER BY id DESC) as list";
        List v = reportsDAO.execute(query);
        if(v != null && v.size() > 0)
            return v;
        return null;
    }

    @Override
    @Transactional
    public void voidDispense(Integer visitId, Integer patientTransactionItemId, Integer transactionId, Integer transactionItemID) {
        Query query = sessionFactory.getCurrentSession().createQuery(" from PatientTransactionItem WHERE id ="+patientTransactionItemId);
        List<PatientTransactionItem> list = query.list();
        PatientTransactionItem patientTransactionItem = list.get(0);
        patientTransactionItem.setVoided((byte) 1);
        sessionFactory.getCurrentSession().update(patientTransactionItem);

        query = sessionFactory.getCurrentSession().createQuery(" from TransactionItem WHERE transactionId ="+transactionId);
        List<TransactionItem> t = query.list();
        TransactionItem transactionItem= t.get(0);
        TransactionItem transactionItem2= t.get(1);

        if(transactionItem.getUnitsIn() != null){
            transactionItem.setVoided((byte) 1);
        }else if(transactionItem2.getUnitsIn() !=null){
                transactionItem2.setVoided((byte) 1);
        }
        if(transactionItem.getUnitsOut() != null){
            transactionItem.setUnitsIn(transactionItem.getUnitsOut());
            transactionItem.setUnitsOut(null);
        }else if(transactionItem2.getUnitsOut() != null){
            transactionItem2.setUnitsIn(transactionItem2.getUnitsOut());
            transactionItem2.setUnitsOut(null);
        }

    }

    @Override
    public Patient getPatientById(Integer patientId) {
        Criteria c = sessionFactory.getCurrentSession().createCriteria(Patient.class, "patient").setProjection(Projections.projectionList().add(Projections.property("id"), "id").add(Projections.property("enrollmentDate"), "enrollmentDate").add(Projections.property("therapyStartDate"), "therapyStartDate").add(Projections.property("patientSourceId"), "patientSourceId").add(Projections.property("patientStatusId"), "patientStatusId").add(Projections.property("startRegimenId"), "startRegimenId").add(Projections.property("supportingOrganizationId"), "supportingOrganizationId").add(Projections.property("drugAllergies"), "drugAllergies").add(Projections.property("chronicIllnesses"), "chronicIllnesses").add(Projections.property("smoker"), "smoker").add(Projections.property("drinker"), "drinker").add(Projections.property("fromFacilityId"), "fromFacilityId")).add(Restrictions.eq("id", patientId)).setResultTransformer(Transformers.aliasToBean(Patient.class));
        List<Patient> pList = c.list();
        if(pList != null && pList.size() > 0) {
            return pList.get(0);
        }
        return null;
    }

    @Override
    public List<Visit> listVisit(Integer patientId) {
        Criteria c = sessionFactory.getCurrentSession().createCriteria(Visit.class, "patient").setProjection(Projections.projectionList().add(Projections.property("id"), "id").add(Projections.property("enrollmentDate"), "enrollmentDate").add(Projections.property("therapyStartDate"), "therapyStartDate").add(Projections.property("patientSourceId"), "patientSourceId").add(Projections.property("serviceTypeId"), "serviceTypeId").add(Projections.property("serviceStartDate"), "serviceStartDate").add(Projections.property("supportingOrganizationId"), "supportingOrganizationId").add(Projections.property("drugAllergies"), "drugAllergies").add(Projections.property("chronicIllnesses"), "chronicIllnesses").add(Projections.property("smoker"), "smoker").add(Projections.property("drinker"), "drinker").add(Projections.property("fromFacilityId"), "fromFacilityId")).add(Restrictions.eq("patientId", patientId)).setResultTransformer(Transformers.aliasToBean(Patient.class));
        List<Visit> visitList = c.list();
        return visitList;
    }

    @Override
    public Object savePatientServiceType(PatientServiceType patientServiceType) {
        Object in = sessionFactory.getCurrentSession().save(patientServiceType);
        return in;
    }

    @Override
    public void updatePatientServiceType(PatientServiceType patientServiceType) {
        PatientServiceTypePK pstK = new PatientServiceTypePK();
        pstK.setTimeStamp(patientServiceType.getTimeStamp());
        pstK.setPatientId(patientServiceType.getPatientId());
        pstK.setServiceTypeId(patientServiceType.getServiceTypeId());
        PatientServiceType pst = (PatientServiceType)sessionFactory.getCurrentSession().get(PatientServiceType.class, pstK);
        if(pst != null) {
            pst.setEndDate(patientServiceType.getEndDate());
            sessionFactory.getCurrentSession().update(pst);
        }
    }

    @Override
    public List getPatientServiceType(Integer patientId) {
        Criteria c = sessionFactory.getCurrentSession().createCriteria(PatientServiceType.class, "patientServiceType").setProjection(Projections.projectionList().add(Projections.property("patientId"), "patientId").add(Projections.property("serviceTypeId"), "serviceTypeId").add(Projections.property("timeStamp"), "timeStamp").add(Projections.property("startDate"), "startDate").add(Projections.property("endDate"), "endDate").add(Projections.property("status"), "status")).add(Restrictions.eq("patientId", patientId)).setResultTransformer(Transformers.aliasToBean(PatientServiceType.class));
        return c.list();
    }

    @Override
    @Transactional
    public List getPatientPrescription(Integer patientId) {
        String sql = "SELECT p.id, p.date_of_issue as dateCreated,r.name as regimen, d.name as drug, ds.name as dosage,pi.quantity as quantity,pi.duration as duration\n" +
                "from prescription_item pi\n" +
                "  LEFT JOIN prescription p on p.id = pi.prescription_id\n" +
                "  LEFT JOIN drug d on pi.drug_id = d.id\n" +
                "  LEFT JOIN dosage ds on ds.id = pi.dosage_id\n" +
                "  Left JOIN regimen r on r.id = p.regimen_id\n" +
                "where prescription_id = (SELECT max(id) FROM prescription WHERE patient_id="+patientId+");";
        Query query =  sessionFactory.getCurrentSession().createSQLQuery(sql);
        return query.list();
    }


}
