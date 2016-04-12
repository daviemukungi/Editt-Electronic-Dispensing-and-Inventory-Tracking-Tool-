package org.msh.fdt.service;

import org.msh.fdt.dao.PersonDAO;
import org.msh.fdt.dao.ReferenceDAO;
import org.msh.fdt.dao.ReportsDAO;
import org.msh.fdt.dao.StocksDAO;
import org.msh.fdt.dto.PersonInfo;
import org.msh.fdt.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by kenny on 4/2/14.
 */
@Service
public class PersonServiceImpl implements PersonService{

    @Autowired
    private PersonDAO personDao;
    @Autowired
    private StocksDAO stocksDAO;
    @Autowired
    private ReferenceDAO referenceDAO;
    @Autowired
    private ReportsDAO reportsDAO;

    @Transactional
    public Integer addPerson(Person person) {
        return personDao.addPerson(person);
    }

    @Transactional
    public List<PersonInfo> getAllPerson() {
        return personDao.getAllPerson();
    }

    @Transactional
    public Integer addPatient(Patient patient) {
        return personDao.addPatient(patient);
    }

    @Transactional
    public Integer savePersonAddress(PersonAddress address) {
        return personDao.savePersonAddress(address);
    }

    @Transactional
    public Integer saveVisit(Visit visit) {
        return personDao.saveVisit(visit);
    }

    @Override
    public Integer savePatientTransactionItem(PatientTransactionItem transactionItem) {
        return personDao.savePatientTransactionItem(transactionItem);
    }

    @Override
    @Transactional
    public PersonInfo savePerson( Person person, Patient patient, PersonAddress address, String service, PatientIdentifier patientIdentifier, List<PatientServiceType> patientServiceTypes) {

        try {
            Integer pid = addPerson(person);
            address.setPersonId(pid);
            savePersonAddress(address);
            Integer patientId = null;
            if(patient != null) {
                patient.setPersonId(pid);
                patientId = addPatient(patient);
            }
            System.out.println("Found Patient ID " + patientId);
            if(patientIdentifier != null) {
                patientIdentifier.setPatientId(patientId);
                ///--------------------


//               if( patient.getPatientStatusId() !=85){
//                    patientIdentifier.setActive(1);
//                }else
//                {
        patientIdentifier.setActive(1);
//                }
                personDao.savePatientIdentifier(patientIdentifier);
            }

            if(patientServiceTypes != null) {
                for(int i = 0; i < patientServiceTypes.size(); i++) {
                    PatientServiceType pst = patientServiceTypes.get(i);
                    pst.setPatientId(patientId);
                    pst.setTimeStamp(new Timestamp(new Date().getTime()));
                    personDao.savePatientServiceType(pst);
                }
            }
            //}
            PersonInfo p = new PersonInfo();
            p.setId(pid);
            p.setSurname(person.getSurname());
            p.setFirstName(person.getFirstName());
            p.setOtherNames(person.getOtherNames());
            p.setPatientId(patientId);
            p.setGender(person.getSex());
            p.setDateOfBirth(person.getDateOfBirth());
            return p;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
        return null;
    }

    @Override
    @Transactional
    public void saveTransactions(Visit visit, List<PatientTransactionItem> patientTransactionItems, org.msh.fdt.model.Transaction transaction, List<TransactionItem> transactionItems, Integer accountId) {
        try {
            int patient_id = visit.getPatientId();
            Integer visitId = saveVisit(visit);

            transaction.setTransactionTypeId(stocksDAO.getTransactionTypeId("Dispensed to Patients"));
            transaction.setUuid(UUID.randomUUID().toString());
            transaction.setCreatedOn(new Timestamp(new Date().getTime()));
            transaction.setVisitId(visitId);
            Integer txId = stocksDAO.saveTransaction(transaction);
            Integer patientAccountId = stocksDAO.getAccountId("PATIENTS");
            List list = loadLastDispense(patient_id);
            // TODO: 12/16/15  create check for pill count and adherence and do adherence total calculation
            for (int i = 0; i < transactionItems.size(); i++) {
                TransactionItem txItemIn = transactionItems.get(i);
                txItemIn.setCreatedOn(new Timestamp(new Date().getTime()));
                txItemIn.setUuid(UUID.randomUUID().toString());
                txItemIn.setTransactionId(txId);
                txItemIn.setAccountId(patientAccountId);
                stocksDAO.saveTransactionItem(txItemIn);

                TransactionItem txItemOut = new TransactionItem();
                txItemOut.setCreatedOn(new Timestamp(new Date().getTime()));
                txItemOut.setUuid(UUID.randomUUID().toString());
                txItemOut.setTransactionId(txId);
                txItemOut.setUnitsOut(txItemIn.getUnitsIn());
                txItemOut.setUnitCost(txItemIn.getUnitCost());
                txItemOut.setTotalCost(txItemIn.getTotalCost());
                txItemOut.setBatchNo(txItemIn.getBatchNo());
                txItemOut.setAccountId(accountId);
                txItemOut.setDrugId(txItemIn.getDrugId());
                txItemOut.setCreatedBy(txItemIn.getCreatedBy());
                Integer txItemId = stocksDAO.saveTransactionItem(txItemOut);

                PatientTransactionItem ptxItem = patientTransactionItems.get(i);
                ptxItem.setCreatedOn(new Timestamp(new Date().getTime()));
                ptxItem.setUuid(UUID.randomUUID().toString());
                ptxItem.setCreatedBy(txItemIn.getCreatedBy());
                ptxItem.setTransactionItemId(txItemId);

                List<BigDecimal> adherencelist = new ArrayList<BigDecimal>();
                if(list != null){
                    Iterator iter = list.iterator();
                    while (iter.hasNext()){
                        Object[] obj = (Object[])iter.next();
                        String s = obj[5].toString();
                        if(txItemOut.getDrugId() == Integer.parseInt(s)){
                            int q1 =  ((BigDecimal) obj[3]).intValue();
                            int pc1 = Integer.parseInt(obj[7].toString()) ;
                            BigDecimal n = txItemOut.getUnitsOut().divide(new BigDecimal(ptxItem.getDuration()), 2, RoundingMode.HALF_UP) ;
                            //days calculation
                            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                            String dateInString = obj[1].toString();
                            Date startDate = formatter.parse(dateInString);
                            Date endDate = new Date();
                            long diff = endDate.getTime() - startDate.getTime();
                            BigDecimal d = new BigDecimal(diff / (3600000 * 24));
                            int pc2 = ptxItem.getPill_count();
                            //set this to take value from frontend
                            int pd = 1;
                            MathContext mc = new MathContext(2, RoundingMode.HALF_UP);

                            BigDecimal adhe = (new BigDecimal((1+q1) - ((pc1/pc2) -pd) ).divide(n.multiply(d,mc),mc)).multiply(new BigDecimal(100),mc);
//                                    (n.multiply(d))) * 100;
                            ptxItem.setAdherence(adhe);
                            adherencelist.add(adhe);
                            break;
                        }
                    }


                }
                if(adherencelist.size() > 0){
                    Collections.sort(adherencelist);
                    ptxItem.setAdherence(adherencelist.get(adherencelist.size() -1));
                }
                personDao.savePatientTransactionItem(ptxItem);
            }

            System.out.println(visit.getPregnant());
            if(visit.getPregnant() == (byte)1) { // If Patient is pregnant, update service type to PMTCT if not yet updated
                //Patient p = personDao.getPatientById(visit.getPatientId());
                ServiceType st = referenceDAO.getServiceType("PMTCT");
                ServiceType artST = referenceDAO.getServiceType("ART");
                ServiceType pepST = referenceDAO.getServiceType("PEP");
                ServiceType oiST = referenceDAO.getServiceType("OI Only");

                List<PatientServiceType> pst = personDao.getPatientServiceType(visit.getPatientId());
                boolean pmtc = false;
                PatientServiceType art = null;
                for(int i = 0; i < pst.size(); i++) {
                    PatientServiceType ps = pst.get(i);
                    if(ps.getServiceTypeId() == st.getId() && ps.getEndDate() != null) {
                        pmtc = true;
                    }

                    if((ps.getServiceTypeId() == artST.getId() || ps.getServiceTypeId() == pepST.getId() || ps.getServiceTypeId() == oiST.getId()) && ps.getEndDate() == null) {
                        art = ps;
                    }
                }
                if(!pmtc) {
                    PatientServiceType nPst = new PatientServiceType();
                    nPst.setServiceTypeId(st.getId());
                    nPst.setStartDate(new Timestamp(new Date().getTime()));
                    nPst.setPatientId(visit.getPatientId());
                    personDao.savePatientServiceType(nPst);
                    if(art != null) {
                        art.setEndDate(new Timestamp(new Date().getTime()));
                        personDao.updatePatientServiceType(art);
                    } else {
                        System.out.println("ART is NULL");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    @Transactional
    public PersonAddress getPersonAddress(Integer personId) {
        return personDao.getPersonAddress(personId);
    }

    @Override
    @Transactional
    public Person getPerson(Integer personId) {
        return personDao.getPerson(personId);
    }

    @Override
    @Transactional
    public Patient getPatient(Integer personId) {
        return personDao.getPatient(personId);
    }

    @Override
    @Transactional
    public PatientIdentifier getPatientIdentifier(Integer patientId) {
        return personDao.getPatientIdentifier(patientId);
    }

    @Override
    @Transactional
    public List<PatientIdentifier> getPatientIdentifiers(Integer patientId) {
        return personDao.getPatientIdentifiers(patientId);
    }

    @Override
    @Transactional
    public PersonInfo updatePerson(Person person, Patient patient, PersonAddress personAddress, String service, PatientIdentifier patientIdentifier, List<PatientServiceType> patientServiceTypeList) {
        Integer personId = personDao.updatePerson(person);
        personDao.updatePersonAddress(personAddress);
        Integer patientId = null;
        if(patient != null) {
            patient.setPersonId(personId);
            patientId = personDao.updatePatient(patient);
        }
        if(patientIdentifier != null)
            personDao.updatePatientIdentifier(patientIdentifier);
        if(patientServiceTypeList != null) {
            for(int i = 0; i < patientServiceTypeList.size(); i++) {
                PatientServiceType pst = patientServiceTypeList.get(i);
                if(pst.getTimeStamp() == null) {
                    pst.setPatientId(patientId);
                    pst.setTimeStamp(new Timestamp(new Date().getTime()));
                    personDao.savePatientServiceType(pst);
                } else {
                    personDao.updatePatientServiceType(pst);
                }
            }
        }

        PersonInfo p = new PersonInfo();
        p.setId(personId);
        p.setSurname(person.getSurname());
        p.setFirstName(person.getFirstName());
        p.setOtherNames(person.getOtherNames());
        p.setPatientId(patientId);
        p.setGender(person.getSex());
        p.setDateOfBirth(person.getDateOfBirth());
        return p;
    }

    @Override
    @Transactional
    public Visit getLastVisit(Integer patientId) {
        return personDao.getLastVisit(patientId);
    }

    @Override
    @Transactional
    public List loadLastDispense(Integer patientId) {
        return personDao.loadLastDispense(patientId);
    }

    @Override
    @Transactional
    public Integer getPersonAge(Integer patientId) {
        String sql = "SELECT floor(datediff(date(now()), date(p.date_of_birth)) / 365) as age, p.id FROM person p JOIN patient pt ON p.id = pt.person_id WHERE pt.id = " + patientId;
        List result = reportsDAO.execute(sql);
        Iterator iterator = result.iterator();
        while(iterator.hasNext()) {
            Object[] row = (Object[])iterator.next();
            String age = row[0] != null ? String.valueOf(row[0]) : "0";
            return Integer.parseInt(age);
        }
        return 0;
    }

    @Override
    @Transactional
    public List listVisit(Integer patientId, Integer account) {
        String sql = "SELECT v.id as visitId,pti.id as patientTransactionItemId,ti.id as transactionItemId,t.id as transactionId,date(v.start_date) as `date`, vt.name as `Purpose`, du.name as `dispensing unit`, pti.dosage_name, pti.duration, d.name, ti.units_out, v.weight, r.name as `Last Regimen`, ti.batch_no, pti.pill_count, i.name as `indication`, pti.adherence, v.next_appointment_date, u.username FROM visit v LEFT JOIN visit_type vt ON v.visit_type_id = vt.id LEFT JOIN regimen r ON v.regimen_id = r.id LEFT JOIN transaction t ON v.id = t.visit_id LEFT JOIN transaction_item ti ON t.id = ti.transaction_id LEFT JOIN drug d ON d.id = ti.drug_id LEFT JOIN dispensing_unit du ON d.dispensing_unit_id = du.id LEFT JOIN patient_transaction_item pti ON pti.transaction_item_id = ti.id LEFT JOIN indication i ON i.id = pti.indication JOIN user u ON u.id = v.created_by WHERE v.patient_id = "+ patientId + " AND pti.voided != TRUE and ti.voided != TRUE HAVING units_out >0 ORDER BY v.id DESC" ;
        List obj = reportsDAO.execute(sql);
        return obj;
    }

    @Override
    public void voidDispense(Integer visitId, Integer patientTransactionItemId, Integer transactionId, Integer transactionItemID) {
        personDao.voidDispense(visitId,patientTransactionItemId,transactionId,transactionItemID);
    }

    @Override
    @Transactional
    public List getPatientServiceType(Integer patientId) {
        return personDao.getPatientServiceType(patientId);
    }

    @Override
    @Transactional
    public Integer loadExpectedPatients(String date, String servicearea) {
        String query =  "SELECT count( visit.id )\n" +
                "FROM visit\n" +
                "INNER JOIN visit_type ON visit_type.id = visit.visit_type_id\n" +
                "INNER JOIN service_type ON service_type.id = visit_type.service_type_id\n" +
                "WHERE date( visit.next_appointment_date ) = '"+date+"'\n" +
                "AND (service_type.service_area LIKE '"+servicearea+"')";
        List obj = reportsDAO.execute(query);
        Iterator iterator = obj.iterator();
        while(iterator.hasNext()) {
            BigInteger integer = (BigInteger)iterator.next();
            return integer.intValue();
        }
        return null;
    }
    @Override
    @Transactional
    public Integer
    loadSeenPatients(String service_area) {
        String query = "SELECT count(*) from visit inner join visit_type on visit_type.id=visit.visit_type_id\n" +
                "inner join service_type on service_type.id=visit_type.service_type_id\n" +
                "where service_type.service_area LIKE '" + service_area + "' and visit.start_date= date(now())";
        List obj = reportsDAO.execute(query);
        Iterator iterator = obj.iterator();
        while(iterator.hasNext()) {
            BigInteger integer = (BigInteger)iterator.next();
            return integer.intValue();
        }
        return null;
    }

    @Override
    @Transactional
    public void updatePEPPatients() {
        ServiceType pepST = referenceDAO.getServiceType("PEP");
        PatientStatus pepEnd = referenceDAO.getPatientStatus("PEP end");
        String query = "select distinct x.patient_id from ( SELECT patient_service_type.patient_id, max(next_appointment_date) as LastAppointment" +
                " FROM patient_service_type INNER JOIN visit  on visit.patient_id = patient_service_type.patient_id" +
                " WHERE service_type_id = " + pepST.getId() + " AND patient_service_type.start_date is null group by patient_id " +
                " union all" +
                " SELECT patient_id, start_date FROM patient_service_type WHERE service_type_id =" + pepST.getId() +" " +
                " AND start_date is not null and DATEDIFF(date(now()),start_date) >30) x";
        List obj = reportsDAO.execute(query);
        if (obj.isEmpty()){
            return;
        }
        Iterator iterator = obj.iterator();
        String patientIds = "";
        while(iterator.hasNext()) {
            Integer p = (Integer) iterator.next();
            if(!patientIds.equalsIgnoreCase(""))
                patientIds += ",";
            patientIds += p;
        }
        reportsDAO.executeUpdate("UPDATE patient set patient_status_id = " + pepEnd.getId() + ", updated_on = now() WHERE id IN (" + patientIds + ")");
    }
    @Override
    @Transactional
    public void updatePMCTPatients() {
        ServiceType pmctST = referenceDAO.getServiceType("PMTCT");
        PatientStatus pmctEnd = referenceDAO.getPatientStatus("PMTCT end");
        String query = "select distinct x.patient_id from ( SELECT patient_service_type.patient_id, max(next_appointment_date) as LastAppointment" +
                " FROM patient_service_type INNER JOIN visit  on visit.patient_id = patient_service_type.patient_id" +
                " WHERE service_type_id = " + + pmctST.getId()  + " AND patient_service_type.start_date is null group by patient_id " +
                " union all" +
                " SELECT patient_id, start_date FROM patient_service_type WHERE service_type_id =" + pmctST.getId() +" " +
                " AND start_date is not null and DATEDIFF(date(now()),start_date) >30) x";
        List obj = reportsDAO.execute(query);
        if(obj.isEmpty()){
            return;
        }
        Iterator iterator = obj.iterator();
        String patientIds = "";
        while(iterator.hasNext()) {
            Integer p = (Integer) iterator.next();
            if(!patientIds.equalsIgnoreCase(""))
                patientIds += ",";
            patientIds += p;
        }
        reportsDAO.executeUpdate("UPDATE patient set patient_status_id = " + pmctEnd.getId() + ", updated_on = now() WHERE id IN (" + patientIds + ")");
    }

    @Override
    @Transactional
    public void updateLostToFollowupPatients() {
        PatientStatus lost = referenceDAO.getPatientStatus("Lost to follow-up");
        List obj = reportsDAO.execute("SELECT id,patient_id FROM  visit v WHERE DATEDIFF(date(now()),v.next_appointment_date) >90 AND v.patient_status_id != " + lost.getId()+ " AND v.id IN (SELECT max(id) FROM visit group by patient_id)");
        if (obj.isEmpty()){
            return;
        }
        Iterator iterator = obj.iterator();
        String patientIds = "";
        while(iterator.hasNext()) {
            Object[] p = (Object[])iterator.next();
            if(!patientIds.equalsIgnoreCase(""))
                patientIds += ",";
            patientIds += p[1];
        }
        reportsDAO.executeUpdate("UPDATE patient set patient_status_id = " + lost.getId() + ", updated_on = now() WHERE id IN (" + patientIds + ")");
    }

    @Override
    public List getPatientPrescription(Integer patientId) {
        return personDao.getPatientPrescription(patientId);
    }

    @Override
    @Transactional
    public Patient getPatientById(Integer patientId) {
        return personDao.getPatientById(patientId);
    }
}