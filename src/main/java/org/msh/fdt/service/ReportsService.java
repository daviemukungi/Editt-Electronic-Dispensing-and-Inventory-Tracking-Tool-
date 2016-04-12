package org.msh.fdt.service;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.json.JSONObject;

import javax.servlet.ServletContext;
import java.io.File;
import java.util.List;

/**
 * Created by kenny on 6/18/14.
 */
public interface ReportsService {

    public File createRoutineRefillReport(String date);

    public File createListPatientExpectedToVisit(String date);

    public File createMissedAppointmentReport(String date1, String date2, Integer serviceId);

    public File createChangedRegimenReport(String date1, String date2, Integer serviceId);

    public File createExpiredDrugsReport(Integer accountId);

    public File createShortDatedStockReport(Integer accountId);

    public File createIssueReceiveReport(boolean issued,Integer accountId, String date1, String date2, Integer supplierId);

    public File createDrugConsumptionReport(String year, Integer drugId, Integer serviceTypeId);

    public File createActivePatientsByRegimenReport(Integer month, Integer year, Integer serviceId);

    public File createCumNumberOfPatientsReport(Integer month, Integer year, Integer serviceType);

    public File createFacilityCDRRStandAloneReport(String startDate, String endDate, Integer accountId);

    public File createPatientsByStatus(Integer patientStatus);

    public File createPatientsByDrugUsedReport(Integer drugId);

    public File createPatientsStartingByRegimen(String startDate, String endDate, Integer serviceId);

    public File createMalariaCDRRReport(String startDate, String endDate);

    public File createLabCDRRReport(String startDate, String endDate);

    public File createNutritionCDRRReport(String startDate, String endDate);

    public File createFPCDRRReport(String startDate, String endDate);

    //public File createCumNumberOfPatientsSubServices(Integer year, Integer month, Integer serviceTypeId);

    //public File createActivePatientsByRegimenReportSubService(Integer month, Integer year, Integer serviceId);

    public File createEnrolledPatientsReport(String startDate, String endDate, Integer serviceId);

    public File createStartedPatientsReport(String startDate, String endDate, Integer serviceId);

    public File createBMIReport(Integer serviceId, boolean adult);

    public File createEnrollmentGraph(Integer year);

    public File createEWI1Report(String startDate, String endDate);

    public File createEWI2Report(String startDate, String endDate);

    public File createEWI3Report(String startDate, String endDate);

    public File createFacilityFmapsReport(String month, String year,Integer accountId);

    public File createEWI4Report(String startDate, String endDate);

    public List<Double> getEWI(String startDate, String endDate) throws Exception;

    public HSSFWorkbook createActivePatientsByRegimen(ServletContext context, String date1, String date2) throws Exception;

    public File createEMMSFacilityCDRRStandAloneReport(String month, String year);

	public JSONObject createARTCDRRData(String month, String year, Integer accountId);

	public JSONObject createEMMSCDRRData(String month, String year);

	public JSONObject createMalariaCDRRData(String month, String year);

	public JSONObject createLabCDRRData(String month, String year);

	public JSONObject createNutritionCDRRData(String month, String year);

	public JSONObject createFPCDRRData(String month, String year);

    public HSSFWorkbook createFacilityCDRR(String month, String year, ServletContext context, Integer accountId) throws Exception;

}
