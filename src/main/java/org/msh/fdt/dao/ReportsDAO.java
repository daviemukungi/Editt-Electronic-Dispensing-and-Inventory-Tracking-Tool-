package org.msh.fdt.dao;

import org.json.JSONObject;
import org.msh.fdt.model.Person;
import org.msh.fdt.model.TransactionItem;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by kenny on 6/18/14.
 */
public interface ReportsDAO {

    public List createRoutineRefillReport(String date);

    public List createListPatientExpectedToVisit(String date);

    public List createMissedAppointmentReport(String date1, String date2, Integer serviceId);

    public List createRegimenChangeReport(String date1, String date2, Integer serviceId);

    /**
     *
     * @param issued if true, create report of all issued drugs, false create report of all received drugs
     * @param accountId The account ID, if NULL create from all the accounts
     * @return
     */
    public List createIssueReceiveReport(boolean issued,Integer accountId, String date1, String date2, Integer supplierId);

    public Map<Integer, JSONObject> createDrugConsumptionReport(String year, Integer drugId, Integer serviceTypeId) throws Exception;

    public Map<Integer, JSONObject> createActivePatientsByRegimenReport(String date, Integer service, Integer statusId)  throws Exception;

    public Map<Integer, JSONObject> createCumNumberOfPatientsReport(String date, Integer serviceType) throws Exception;

    public List<Object[]> cumulativeTransactions(Integer[] transactionTypeId, String startDate, String endDate, Integer serviceTypeId);

    public List<Object[]> cumulativeShortDatedDrugs(String endDate, Integer serviceTypeId, Integer accountId);

    public List<Object[]> drugBalance(String date, Integer serviceTypeId, Integer accountId);

    public List<Object[]> daysOutOfStock(String date, Integer serviceTypeId);

    public List<Object[]> listPatientsByStatus(Integer patientStatus);

    public List<Object[]> listPatientsByDrugUsed(Integer drugId);

    public List<Object[]> patientsStartingByRegimen(String date1, String date2, Integer serviceId);

    public JSONObject getPatientsEnrolled(String date1, String date2, Integer serviceId);

    public JSONObject getPatientsStarted(String date1, String date2, Integer serviceId);

    public Map<Integer, JSONObject> getEnrolledRegimen(String startDate, String endDate, Integer[] serviceId);

    public Map<Integer, JSONObject> getStartedRegimen(String startDate, String endDate, Integer[] serviceId);

    public Integer getGrandTotal();

    public JSONObject getBMIReport(Integer serviceId, boolean adult);

    public Integer[] getEnrollmentByMonth(Integer year);

    public Integer getDenominator(String sql);

    public List<Object[]> execute(String sql);

    public int executeUpdate(String sql);

}
