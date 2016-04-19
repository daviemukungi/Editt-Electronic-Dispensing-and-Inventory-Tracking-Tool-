package org.msh.fdt.dao;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.msh.fdt.model.Person;
import org.msh.fdt.model.TransactionItem;
import org.msh.fdt.model.VisitType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

/**
 * Created by kenny on 6/18/14.
 */
@Repository
public class ReportsDAOImpl implements ReportsDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private StocksDAO stocksDAO;

    @Autowired
    private ReferenceDAO referenceDAO;

    private Integer grandTotal = 0;

    public enum GENDER_AGE_FILTER{
        AdultMale ("Male", " <= DATE_SUB(date(now()), INTERVAL 15 YEAR)", 0, true),
        AdultFemale ("Female", " <= DATE_SUB(date(now()), INTERVAL 15 YEAR)", 1, true),
        ChildMale ("Male", " > DATE_SUB(date(now()), INTERVAL 15 YEAR)", 2, false),
        ChildFemale ("Female", " > DATE_SUB(date(now()), INTERVAL 15 YEAR)", 3, false);

        private final String gender;
        private final String age;
        private final int index;
        private final boolean adult;
        GENDER_AGE_FILTER(String gender, String age, int index, boolean adult) {
            this.gender = gender;
            this.age = age;
            this.index = index;
            this.adult = adult;
        }

        public int getIndex() {
            return index;
        }

        public boolean getAdult() { return adult; }
    }

    public enum BMI {
        Severe(" 0 AND 15 ", "Severe"),
        Moderate("  15 AND 16 ", "Moderate"),
        Mild(" 16 and 18.5 ", "Mild"),
        Normal(" 18.5 and 25 ", "Normal"),
        Overweight(" 25 AND 30 ", "Overweight"),
        Obese(" 30 and 100", "Obese"); //

        private String bmi;
        private String title;
        BMI(String bmi, String title) {
            this.bmi = bmi;
            this.title = title;
        }
    }

    @Override
    public List createRoutineRefillReport(String date) {
        VisitType vt = referenceDAO.getVisitType("Routine Refill");
        Integer visitTypeId = 0;
        if(vt != null) {
            visitTypeId = vt.getId();
        }
        String sql = "SELECT service_type.name as service, p.surname, p.first_name, floor(DATEDIFF(date(now()), p.date_of_birth)/ 365), p.sex, regimen.name,  visit.weight, pi.identifier FROM person p JOIN patient ON p.id = patient.person_id JOIN patient_service_type ON patient.id = patient_service_type.patient_id JOIN service_type ON patient_service_type.service_type_id = service_type.id JOIN visit ON patient.id = visit.patient_id JOIN regimen ON visit.regimen_id = regimen.id LEFT JOIN patient_identifier pi ON patient.id = pi.patient_id WHERE date(visit.start_date) = '" + date + "' AND visit.visit_type_id = " + visitTypeId ;
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        List resp = query.list();
        return resp;
    }

    @Override
    public List createListPatientExpectedToVisit(String date) {
        String sql = "SELECT p.first_name, p.surname, p.sex, floor(DATEDIFF(date(now()), p.date_of_birth)/ 365), pa.tel_no1, pa.postal_address, regimen.name, pi.identifier FROM person p JOIN person_address pa ON p.id = pa.person_id JOIN patient ON p.id = patient.person_id JOIN visit ON patient.id = visit.patient_id JOIN regimen ON visit.regimen_id = regimen.id LEFT JOIN patient_identifier pi ON patient.id = pi.patient_id WHERE date(visit.next_appointment_date) = '" + date + "'";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        List resp = query.list();
        return resp;
    }

    @Override
    public List createMissedAppointmentReport(String date1, String date2, Integer serviceId) {
        String sql = "SELECT ps.surname, ps.first_name, ps.sex, pa.postal_address, pa.tel_no1, v.next_appointment_date, DATEDIFF(date(v2.start_date), date(v.next_appointment_date)) as days,v.adherence, v.patient_id,  v2.start_date, pi.identifier FROM person ps JOIN person_address pa ON ps.id = pa.person_id JOIN patient p ON ps.id = p.person_id JOIN patient_service_type pst ON p.id = pst.patient_id JOIN visit v ON p.id = v.patient_id INNER JOIN visit v2 on v.patient_id = v2.patient_id LEFT JOIN patient_identifier pi ON p.id = pi.patient_id WHERE (date(v.start_date) BETWEEN '" + date1 + "' AND '" + date2 + "') AND date(v.next_appointment_date) != date(v2.start_date) and v2.id IN (SELECT min(id) FROM visit WHERE id > v.id AND patient_id = v.patient_id)";
        if(serviceId != -1) {
            sql += " AND pst.service_type_id = " + serviceId;
        }
        sql += " having days > 0";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        List resp = query.list();
        return resp;
    }

    @Override
    public List createRegimenChangeReport(String date1, String date2, Integer serviceId) {
        String sql = "SELECT r.name as `from`, r2.name as `to`, ps.surname, ps.first_name,st.name as `service_name`, v2.start_date, rc.name as `rc_name`, v.regimen_id as `reg_1`, v2.regimen_id as `reg2`, v2.regimen_change_reason_id, v.patient_id FROM person ps JOIN patient p ON ps.id = p.person_id JOIN patient_service_type pst ON p.id = pst.patient_id  JOIN visit v ON p.id = v.patient_id INNER JOIN visit v2 on v.patient_id = v2.patient_id LEFT JOIN regimen_change_reason rc ON rc.id = v2.regimen_change_reason_id JOIN service_type st ON pst.service_type_id = st.id JOIN regimen r ON r.id = v.regimen_id JOIN regimen r2 ON r2.id = v2.regimen_id  WHERE (date(pst.start_date) BETWEEN '" + date1 + "' AND '" + date2 + "') AND v.regimen_id != v2.regimen_id and v2.id IN (SELECT min(id) FROM visit WHERE id > v.id AND patient_id = v.patient_id) ";
        if(serviceId != -1) {
            sql += " AND pst.service_type_id = " + serviceId;
        }
        sql += "  order by reg_1";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        List resp = query.list();
        return resp;
    }

    @Override
    public List createIssueReceiveReport(boolean issued,Integer accountId, String date1, String date2, Integer supplierId) {
        String sql = "SELECT  d.name, ti.account_id, b.pack_size, b.no_of_packs, ti.units_in, b.open_packs, t.id, ti.unit_cost, ti.total_cost, t.date, t.reference_no FROM batch_transaction_item b JOIN transaction_item ti ON b.transaction_item_id = ti.id JOIN drug d ON ti.drug_id = d.id JOIN transaction t ON ti.transaction_id = t.id WHERE ";
        if(issued) {
            sql += " t.transaction_type_id = " + stocksDAO.getTransactionTypeId("Issued To") + "";
            sql += " AND t.id IN ( SELECT t.id FROM transaction t JOIN transaction_item ti ON t.id = ti.transaction_id WHERE t.transaction_type_id = " + stocksDAO.getTransactionTypeId("Issued To") + " AND ti.units_in IS NULL AND ti.account_id = " + accountId + ")" ;
        } else {
            sql += " t.transaction_type_id = " + stocksDAO.getTransactionTypeId("Received from") + "";
            if(supplierId != null && supplierId > 0) {
                sql += " AND t.id IN ( SELECT t.id FROM transaction t JOIN transaction_item ti ON t.id = ti.transaction_id WHERE t.transaction_type_id = " + stocksDAO.getTransactionTypeId("Received from") + " AND ti.account_id = " + supplierId + ")" ;
            }
            sql += " AND ti.account_id = " + accountId;
        }
        if(!issued && accountId != null && accountId != -1) {

        }
        sql += " AND (date(t.date) BETWEEN '" + date1 + "' AND '" + date2 + "')";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        List resp = query.list();
        return resp;
    }

    @Override
    public Map<Integer, JSONObject> createDrugConsumptionReport(String year, Integer drugId, Integer serviceTypeId) throws  Exception {
        String sql = "SELECT d.name, d.pack_size, SUM(units_out),drug_id  FROM transaction_item ti LEFT JOIN drug d ON  d.id = ti.drug_id JOIN transaction t ON ti.transaction_id = t.id WHERE  t.transaction_type_id = " + stocksDAO.getTransactionTypeId("Dispensed to Patients") + " AND (year(t.date) = '" + year + "')";
        if(drugId != null) {
            sql += " AND ti.drug_id = " + drugId;
        }
        Calendar c = Calendar.getInstance();
        int months = 12;

        int month = c.get(Calendar.MONTH);
        if(year.equalsIgnoreCase(String.valueOf(c.get(Calendar.YEAR)))) {
            months = month + 1;
        }

        Map<Integer, JSONObject> data = new HashMap<Integer, JSONObject>();

        for(int i = 0; i < months; i++) {
            sql = "SELECT d.name, d.pack_size, (SUM(ti.units_in) / d.pack_size), ti.drug_id  FROM transaction_item ti LEFT JOIN drug d ON  d.id = ti.drug_id JOIN transaction t ON ti.transaction_id = t.id WHERE ti.account_id = " + stocksDAO.getAccountId("PATIENTS") + " AND d.service_type_id = " + serviceTypeId + " AND t.transaction_type_id = " + stocksDAO.getTransactionTypeId("Dispensed to Patients") + " AND (year(t.date) = '" + year + "') AND (month(t.date) = '" + ( i + 1) + "')";
            if(drugId != null) {
                sql += " AND ti.drug_id = " + drugId;
            }
            Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
            List resp = query.list();
            Iterator iterator = resp.iterator();
            while(iterator.hasNext()) {
                Object[] temp = (Object[]) iterator.next();
                if(temp[0] == null)
                    continue;
                JSONObject obj = data.get(temp[3]);
                if(obj == null) {
                    obj = new JSONObject();
                    obj.put("name", temp[0]);
                    JSONArray count = new JSONArray();
                    obj.put("count", count);
                }
                obj.put("name", temp[0]);
                JSONArray count = obj.getJSONArray("count");
                count.put(i, (BigDecimal)temp[2]);
                data.put((Integer)temp[3], obj);
            }
        }
        return data;
    }

    /**
     *  Create Active Patients By Regimen Report Data
     *  This is a Map with the key being the Regimen ID
     *  The Value is a JSON Object which takes the following form
     *      name : The Regimen Name
     *      data : This is a JSON Array of the counted data. It will take the form [adultMale, adultFemale, childMale, childFemale]
     *
     * @param serviceId
     * @return
     */
    @Override
    public Map<Integer, JSONObject> createActivePatientsByRegimenReport(String date, Integer serviceId, Integer statusId)  throws Exception{
        String sql = "";

        Map<Integer, JSONObject> data = new HashMap<Integer, JSONObject>();
        for(GENDER_AGE_FILTER gaf : GENDER_AGE_FILTER.values()) {
            sql = "SELECT r.name, r.id, COUNT(v.regimen_id) FROM visit v JOIN regimen r ON v.regimen_id = r.id JOIN patient p ON p.id = v.patient_id JOIN person pr ON pr.id = p.person_id  JOIN patient_service_type pst ON p.id = pst.patient_id WHERE v.id IN (SELECT max(id) FROM visit GROUP by patient_id) AND pst.service_type_id = " + serviceId + " AND p.patient_status_id = " + statusId + " AND pr.sex = '" + gaf.gender + "' AND date(pr.date_of_birth)  " + gaf.age + " AND date(v.start_date) <= '" + date + "' GROUP BY regimen_id";
            Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
            List resp = query.list();
            Iterator iterator = resp.iterator();


            while(iterator.hasNext()) {
                Object[] temp = (Object[]) iterator.next();
                if (temp[0] == null)
                    continue;
                JSONObject obj = data.get(temp[1]);
                if(obj == null) {
                    obj = new JSONObject();
                    obj.put("name", temp[0]);
                    JSONArray count = new JSONArray();
                    obj.put("data", count);
                }
                obj.put("name", temp[0]);
                JSONArray count = obj.getJSONArray("data");
                count.put(gaf.index, temp[2]);
                data.put((Integer)temp[1], obj);
            }
        }
        return data;
    }

    /**
     *  Creates Cumulative Number of Patients Report
     *  The key for the returned {@link java.util.Map} is the {@link org.msh.fdt.model.PatientStatus} ID
     *  The value is a {@link org.json.JSONObject} which takes the form
     *      name : The Status Name
     *      total : The total number of patients with this status
     *      data : The actual data count per patient group i.e. Adult Male, Adult Female, Child Male, Child Female
     * @param month
     * @param year
     * @param serviceType
     * @return
     */
    @Override
    public Map<Integer, JSONObject> createCumNumberOfPatientsReport(String date, Integer serviceType) throws Exception{
        String sql = "";
        //month = month + 1;
        sql = "SELECT t.patient_status_id, count(t.patient_status_id), t.patient_status FROM patient_view t WHERE date(t.visit_date) <= '" + date + "' AND t.service_type_id = " + serviceType + " group by patient_status_id";
        Map<Integer, JSONObject> data = new HashMap<Integer, JSONObject>();
        List resp = sessionFactory.getCurrentSession().createSQLQuery(sql).list();
        Iterator iterator = resp.iterator();
        Integer total = 0;
        while(iterator.hasNext()) {
            Object[] temp = (Object[])iterator.next();
            if (temp[0] == null)
                continue;
            JSONObject obj = data.get(temp[1]);
            if(obj == null) {
                obj = new JSONObject();
                obj.put("name", temp[2]);
                obj.put("total", temp[1]);
                total = total + ((BigInteger)temp[1]).intValue();
            }
            data.put((Integer)temp[0], obj);
        }

        for(GENDER_AGE_FILTER gaf : GENDER_AGE_FILTER.values()) {
            sql = "SELECT t.patient_status_id, count(t.patient_status_id), t.service_type_id,t.service_type FROM patient_view t WHERE date(t.visit_date) <= '" + date + "' AND t.sex = '" + gaf.gender + "' AND date(t.date_of_birth) " + gaf.age + " AND t.service_type_id = " + serviceType + " group by patient_status_id";
            resp = sessionFactory.getCurrentSession().createSQLQuery(sql).list();
            iterator = resp.iterator();
            while (iterator.hasNext()) {
                Object[] temp = (Object[]) iterator.next();
                if (temp[0] == null)
                    continue;
                JSONObject obj = data.get(temp[0]);
                if (obj == null) {
                    obj = new JSONObject();
                }
                if(!obj.has("data")) {
                    JSONArray dt = new JSONArray();
                    obj.put("data", dt);
                }
                JSONArray count = obj.getJSONArray("data");
                count.put(gaf.index, temp[1]);
                data.put((Integer)temp[0], obj);
                //total = total + ((Integer)temp[0]);
            }
        }
        this.grandTotal = total;
        return data;
    }

    /**
     *  Creates Cumulative Transaction Objects.
     *  Can be used to get Quantities received, Dispensed e.t.c
     * @param transactionTypeId Transaction Type ID used to filter
     * @param startDate The time frame when the transactions happened.
     * @param endDate The time frame when the transactions happened.
     * @return
     */
    @Override
    public List<Object[]> cumulativeTransactions(Integer[] transactionTypeId, String startDate, String endDate, Integer serviceTypeId) {
        String txTypes = "";
        for(int i = 0; i < transactionTypeId.length; i++) {
            if(i > 0)
                txTypes += ",";
            txTypes += transactionTypeId[i];
        }
        String sql = "SELECT SUM(ti.units_in) as units_in, ti.drug_id FROM transaction_item ti JOIN transaction t ON ti.transaction_id = t.id JOIN drug d ON ti.drug_id = d.id WHERE t.transaction_type_id IN ( " + txTypes + ") AND (date(t.date) BETWEEN '" + startDate + "' AND '" + endDate + "')";
        if(serviceTypeId != null) {
            sql += " AND d.service_type_id = " + serviceTypeId;
        }
        sql += " group by drug_id";

        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        List<Object[]> transactionItemList = query.list();
        return transactionItemList;
    }

    /**
     *  Crates an Object of all short dated drugs Quantities
     *  The returned Object has drug_id, quantity of short dated drug
     * @return
     */
    @Override
    public List<Object[]> cumulativeShortDatedDrugs(String date, Integer serviceTypeId, Integer accountId) {
        String sql = "SELECT ti.drug_id, (COALESCE(SUM(ti.units_in), 0) - COALESCE(SUM(ti.units_out), 0)) as diff, MIN(bt.date_of_expiry) FROM batch_transaction_item bt JOIN transaction_item ti ON bt.transaction_item_id = ti.id JOIN drug d ON ti.drug_id = d.id WHERE date(bt.date_of_expiry) > date('" + date + "') AND date(bt.date_of_expiry) < DATE_ADD(date('" + date + "'), INTERVAL 6 MONTH ) ";
        if(serviceTypeId != null) {
            sql += " AND d.service_type_id = " + serviceTypeId;
        }
        if(accountId != null) {
            sql += " AND ti.account_id = " + accountId;
        }
        sql += " group by drug_id";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        List<Object[]> shortDatedDrugs = query.list();
        return shortDatedDrugs;
    }

    /**
     *  Creates the drug balance as at a specific day.
     * @param date
     * @return
     */
    @Override
    public List<Object[]> drugBalance(String date, Integer serviceTypeId, Integer accountId) {
        String sql = "SELECT ti.drug_id, (COALESCE(SUM(ti.units_in), 0) - COALESCE(SUM(ti.units_out), 0)) as diff, SUM(ti.units_in) as units_in, SUM(ti.units_out) as units_out FROM transaction_item ti JOIN transaction t ON ti.transaction_id = t.id JOIN drug d ON ti.drug_id = d.id WHERE date(t.date) < '" + date + "'";
        if(serviceTypeId != null) {
            sql += " AND d.service_type_id = " + serviceTypeId;
        }
        if(accountId != null) {
            sql += " AND ti.account_id = " + accountId;
        }
        sql += " group by drug_id";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        List<Object[]> shortDatedDrugs = query.list();
        return shortDatedDrugs;
    }

    @Override
    public List<Object[]> daysOutOfStock(String date, Integer serviceTypeId) {
        String sql = "SELECT ti.drug_id, (COALESCE(SUM(ti.units_in), 0) - COALESCE(SUM(ti.units_out), 0)) as diff, datediff(date('" + date + "'), max(t.date)) FROM transaction_item ti JOIN transaction t ON ti.transaction_id = t.id JOIN drug d ON ti.drug_id = d.id WHERE date(t.date) < '" + date + "' ";
        if(serviceTypeId != null) {
            sql += " AND d.service_type_id = " + serviceTypeId;
        }
        sql += " group by drug_id";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        List<Object[]> shortDatedDrugs = query.list();
        return shortDatedDrugs;
    }

    /**
     *  Lists patients based on the {@link org.msh.fdt.model.PatientStatus}
     *
     * @param patientStatus Patient Status ID
     * @return
     */
    @Override
    public List<Object[]> listPatientsByStatus(Integer patientStatus) {
        String sql = "SELECT st.name, p.first_name, p.surname, p.other_names, p.sex, pst.service_type_id, pt.patient_status_id, ps.name as `patient status`, pi.identifier, floor(datediff(date(now()), date(p.date_of_birth)) / 365) as age FROM person p JOIN patient pt ON p.id = pt.person_id JOIN patient_service_type pst ON pt.id = pst.patient_id JOIN service_type st ON pst.service_type_id = st.id JOIN patient_status ps ON pt.patient_status_id = ps.id LEFT JOIN patient_identifier pi ON pt.id = pi.patient_id WHERE pt.patient_status_id = " + patientStatus + " ORDER BY st.name ASC";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        List<Object[]> patientsByStatus = query.list();
        return patientsByStatus;
    }

    @Override
    public List<Object[]> listPatientsByDrugUsed(Integer drugId) {
        String sql = "SELECT p.first_name, p.surname, floor(datediff(date(now()), date(p.date_of_birth)) / 365) as age, p.other_names, pt.id as `patient id`, v.id as `visit id`, t.id as `transaction_id`, pi.identifier FROM person p JOIN patient pt ON p.id = pt.person_id JOIN visit v ON v.patient_id = pt.id JOIN transaction t ON t.visit_id = v.id LEFT JOIN patient_identifier pi ON pt.id = pi.patient_id WHERE v.id IN (SELECT max(id) FROM visit GROUP by patient_id) AND t.id IN (SELECT transaction_id FROM transaction_item WHERE drug_id = " + drugId + ") ORDER BY pt.id";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        List<Object[]> listByDrugUsed = query.list();
        return listByDrugUsed;
    }

    @Override
    public List<Object[]> patientsStartingByRegimen(String date1, String date2, Integer serviceId) {
        String sql = "SELECT r.name, p.first_name, p.surname, st.name as `service type`, pt.id, pt.start_regimen_id FROM person p JOIN patient pt ON p.id = pt.person_id JOIN patient_service_type pst ON pt.id = pst.patient_id  JOIN regimen r ON r.id = pt.start_regimen_id JOIN service_type st ON st.id = pst.service_type_id WHERE  (date(pst.start_date) BETWEEN '" + date1 + "' AND '" + date2 + "') AND pst.service_type_id = " + serviceId + " ORDER BY pst.service_type_id";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        List<Object[]> patientsStartingByRegimen = query.list();
        return patientsStartingByRegimen;
    }

    @Override
    public JSONObject getPatientsEnrolled(String date1, String date2, Integer serviceId) {
        JSONObject data = new JSONObject();
        data.put("data", new JSONArray());
        for(GENDER_AGE_FILTER gaf : GENDER_AGE_FILTER.values()) {
            String sql = "SELECT count(pst.service_type_id), st.name, st.id FROM patient_service_type pst JOIN service_type st ON pst.service_type_id = st.id JOIN patient pt ON pst.patient_id = pt.id JOIN person ps ON pt.person_id = ps.id WHERE  (date(pt.enrollment_date) BETWEEN '" + date1 + "' AND '" + date2 + "') AND st.id = " + serviceId + " AND date(ps.date_of_birth) " + gaf.age + " AND ps.sex = '" + gaf.gender + "' group by service_type_id ";
            Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
            List<Object[]> enrolledPatients = query.list();
            Integer enrolled = 0;
            Iterator iterator = enrolledPatients.iterator();
            while (iterator.hasNext()) {
                Object[] row = (Object[])iterator.next();
                enrolled = ((BigInteger)row[0]).intValue();
            }

            JSONArray arr = data.getJSONArray("data");
            arr.put(gaf.getIndex(), enrolled);
            data.put("data", arr);
        }
        return data;
    }

    @Override
    public JSONObject getPatientsStarted(String date1, String date2, Integer serviceId) {
        JSONObject data = new JSONObject();
        data.put("data", new JSONArray());
        for(GENDER_AGE_FILTER gaf : GENDER_AGE_FILTER.values()) {
            String sql = "SELECT count(pst.service_type_id), st.name, st.id FROM patient_service_type pst JOIN service_type st ON pst.service_type_id = st.id JOIN patient pt ON pst.patient_id = pt.id JOIN person ps ON pt.person_id = ps.id WHERE  (date(pst.start_date) BETWEEN '" + date1 + "' AND '" + date2 + "') AND st.id = " + serviceId + " AND date(ps.date_of_birth) " + gaf.age + "  AND ps.sex = '" + gaf.gender + "' group by service_type_id ";
            Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
            List<Object[]> enrolledPatients = query.list();
            Integer enrolled = 0;
            Iterator iterator = enrolledPatients.iterator();
            while (iterator.hasNext()) {
                Object[] row = (Object[])iterator.next();
                enrolled = ((BigInteger)row[0]).intValue();
            }

            JSONArray arr = data.getJSONArray("data");
            arr.put(gaf.getIndex(), enrolled);
            data.put("data", arr);
        }
        return data;
    }

    @Override
    public Map<Integer, JSONObject> getEnrolledRegimen(String startDate, String endDate, Integer[] serviceId) {
        String sql = "";

        String services = "";
        for(int i = 0; i < serviceId.length; i++) {
            if(i > 0)
                services += ",";
            services += serviceId[i];
        }

        Integer grandTotal = 0;

        Map<Integer, JSONObject> data = new HashMap<Integer, JSONObject>();
        for(GENDER_AGE_FILTER gaf : GENDER_AGE_FILTER.values()) {
            sql = "SELECT r.name, r.id, COUNT(v.regimen_id) FROM visit v JOIN regimen r ON v.regimen_id = r.id JOIN patient p ON p.id = v.patient_id JOIN person pr ON pr.id = p.person_id JOIN patient_service_type pst ON p.id = pst.patient_id WHERE v.id IN (SELECT min(id) FROM visit GROUP by patient_id) AND pst.service_type_id IN (" + services + ") AND pr.sex = '" + gaf.gender + "' AND date(pr.date_of_birth)  " + gaf.age + " AND (date(p.enrollment_date) BETWEEN '" + startDate + "' AND '" + endDate + "') GROUP BY regimen_id";
            Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
            List resp = query.list();
            Iterator iterator = resp.iterator();


            while(iterator.hasNext()) {
                Object[] temp = (Object[]) iterator.next();
                if (temp[0] == null)
                    continue;
                JSONObject obj = data.get(temp[1]);
                if(obj == null) {
                    obj = new JSONObject();
                    obj.put("name", temp[0]);
                    JSONArray count = new JSONArray();
                    obj.put("data", count);
                    obj.put("total", 0);
                }
                obj.put("name", temp[0]);
                JSONArray count = obj.getJSONArray("data");
                count.put(gaf.index, temp[2]);
                obj.put("total", obj.optInt("total") + ((BigInteger)temp[2]).intValue());
                data.put((Integer)temp[1], obj);
                grandTotal += ((BigInteger)temp[2]).intValue();
            }
        }
        this.grandTotal = grandTotal;
        return data;
    }

    @Override
    public Map<Integer, JSONObject> getStartedRegimen(String startDate, String endDate, Integer[] serviceId) {
        String sql = "";

        String services = "";
        for(int i = 0; i < serviceId.length; i++) {
            if(i > 0)
                services += ",";
            services += serviceId[i];
        }
        Integer grandTotal = 0;
        Map<Integer, JSONObject> data = new HashMap<Integer, JSONObject>();
        for(GENDER_AGE_FILTER gaf : GENDER_AGE_FILTER.values()) {
            sql = "SELECT r.name, r.id, COUNT(v.regimen_id) FROM visit v JOIN regimen r ON v.regimen_id = r.id JOIN patient p ON p.id = v.patient_id JOIN patient_service_type pst ON p.id = pst.patient_id JOIN person pr ON pr.id = p.person_id WHERE v.id IN (SELECT min(id) FROM visit GROUP by patient_id) AND pst.service_type_id IN (" + services + ") AND pr.sex = '" + gaf.gender + "' AND date(pr.date_of_birth)  " + gaf.age + " AND (date(pst.start_date) BETWEEN '" + startDate + "' AND '" + endDate + "') GROUP BY regimen_id";
            Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
            List resp = query.list();
            Iterator iterator = resp.iterator();


            while(iterator.hasNext()) {
                Object[] temp = (Object[]) iterator.next();
                if (temp[0] == null)
                    continue;
                JSONObject obj = data.get(temp[1]);
                if(obj == null) {
                    obj = new JSONObject();
                    obj.put("name", temp[0]);
                    JSONArray count = new JSONArray();
                    obj.put("data", count);
                    obj.put("total", 0);
                }
                obj.put("name", temp[0]);
                JSONArray count = obj.getJSONArray("data");
                count.put(gaf.index, temp[2]);
                grandTotal += ((BigInteger)temp[2]).intValue();
                obj.put("total", obj.optInt("total") + ((BigInteger)temp[2]).intValue());
                data.put((Integer)temp[1], obj);
            }
        }
        this.grandTotal = grandTotal;
        return data;
    }

    public Integer getGrandTotal() {
        return this.grandTotal;
    }

    /**
     *  Returns a {@link org.json.JSONObject} with {@link org.json.JSONArray} items
     * @param serviceId
     * @param adult
     * @return
     */
    @Override
    public JSONObject getBMIReport(Integer serviceId, boolean adult) {
        String sql = "";

        JSONObject obj = new JSONObject();
        for(GENDER_AGE_FILTER gaf : GENDER_AGE_FILTER.values()) {
            if(gaf.adult == adult) {
                for(BMI bmi : BMI.values()) {
                    sql = "SELECT  count(p.id), v.weight, v.height, (v.weight / pow((v.height / 100),2))  as bmi FROM visit v JOIN patient p ON v.patient_id = p.id JOIN person ps ON ps.id = p.person_id JOIN patient_service_type pst ON p.id = pst.patient_id WHERE v.id IN (SELECT max(id) FROM visit WHERE visit.weight IS NOT NULL GROUP by patient_id) AND ps.sex = '" + gaf.gender + "' AND date(ps.date_of_birth) " + gaf.age + " AND  (v.weight / pow((v.height / 100),2))  BETWEEN " + bmi.bmi + " AND pst.service_type_id = " + serviceId;
                    Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
                    List resp = query.list();
                    Iterator iterator = resp.iterator();

                    while(iterator.hasNext()) {
                        Object[] temp = (Object[]) iterator.next();
                        if (temp[0] == null)
                            continue;
                        JSONArray arr;
                        if(!obj.has(bmi.name())) {
                            arr = new JSONArray();
                        } else {
                            arr = obj.getJSONArray(bmi.name());
                        }
                        arr.put(gaf.getIndex(), temp[0]);
                        obj.put(bmi.name(), arr);
                    }
                }
            }

        }
        return obj;
    }

    @Override
    public Integer[] getEnrollmentByMonth(Integer year) {
        String query = "SELECT count(p.id), month(p.enrollment_date) as month FROM patient p WHERE year(p.enrollment_date) = " + year + " group by month(p.enrollment_date) ORDER BY month ASC";
        List result = sessionFactory.getCurrentSession().createSQLQuery(query).list();
        Iterator iterator = result.iterator();
        Integer[] arr = new Integer[12];
        for(int i = 0; i < arr.length; i++) {
            arr[i] = 0;
        }
        while(iterator.hasNext()) {
            Object[] data = (Object[])iterator.next();
            arr[(Integer)data[1] - 1] = ((BigInteger)data[0]).intValue();
        }
        return arr;
    }

    @Override
    public Integer getDenominator(String sql) {
        List result = sessionFactory.getCurrentSession().createSQLQuery(sql).list();
        Iterator iterator = result.iterator();
        while(iterator.hasNext()) {
            Object[] row = (Object[])iterator.next();
            Integer count = ((BigInteger)row[0]).intValue();
            return count;
        }
        return 0;
    }

    @Override
    public List<Object[]> execute(String sql) {
        List<Object[]> result = sessionFactory.getCurrentSession().createSQLQuery(sql).list();
        return result;
    }



    @Override
    public int executeUpdate(String sql) {
        int result = sessionFactory.getCurrentSession().createSQLQuery(sql).executeUpdate();
        return result;
    }
}
