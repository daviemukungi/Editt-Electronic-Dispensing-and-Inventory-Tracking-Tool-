package org.msh.fdt.controller;

import org.json.JSONObject;
import org.msh.fdt.model.Property;
import org.msh.fdt.service.AdminService;
import org.msh.fdt.service.ReportsService;
import org.msh.fdt.service.StocksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kenny on 6/16/14.
 *
 * This is the main controller for downloading reports.
 */
@Controller
@RequestMapping("/reports")
public class ReportsController extends BaseController {

	@Autowired
	private AdminService adminService;

    @Autowired
    private StocksService stocksService;

    @Autowired
    private ReportsService reportsService;

    @Autowired
    private ServletContext servletContext;
    /**
     *  Creates Stock On Hand Report
     *
     */
    @RequestMapping(value = "/json/sohReport/{accountId}", method = RequestMethod.GET)
    public @ResponseBody
    void sohReport(HttpServletResponse response, @PathVariable("accountId") Integer accountId) {

        try {
            File file = stocksService.createStockOnHandReport(accountId);
            returnFile(file, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.setContentType("text/html; charset=UTF-8");
            response.setHeader("", "");
            try {
                response.getOutputStream().write((""
                        + "Error creating report " + e.getMessage() + ".</p>").getBytes());
            } catch (Exception ex) {}
        }
    }

    /**
     *  Creates Expired drugs Report
     *
     */
    @RequestMapping(value = "/json/expired/{accountId}", method = RequestMethod.GET)
    public @ResponseBody
    void expiredReport(HttpServletResponse response, @PathVariable("accountId") Integer accountId) {

        try {
            File file = reportsService.createExpiredDrugsReport(accountId);
            returnFile(file, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.setContentType("text/html; charset=UTF-8");
            response.setHeader("", "");
            try {
                response.getOutputStream().write((""
                        + "Error creating report " + e.getMessage() + ".</p>").getBytes());
            } catch (Exception ex) {}
        }
    }

    /**
     *  Creates Short Dated drugs Report
     *
     */
    @RequestMapping(value = "/json/shortDated/{accountId}", method = RequestMethod.GET)
    public @ResponseBody
    void shortDatedStockReport(HttpServletResponse response, @PathVariable("accountId") Integer accountId) {

        try {
            File file = reportsService.createShortDatedStockReport(accountId);
            returnFile(file, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.setContentType("text/html; charset=UTF-8");
            response.setHeader("", "");
            try {
                response.getOutputStream().write((""
                        + "Error creating report " + e.getMessage() + ".</p>").getBytes());
            } catch (Exception ex) {}
        }
    }

    /**
     *  Creates a report of patients who visited a facility for Routine refill.
     * @param response
     * @param request
     */
    @RequestMapping(value = "/json/routineRefill", method = RequestMethod.GET)
    public @ResponseBody
    void createRoutineRefillReport(HttpServletResponse response, HttpServletRequest request) {
        String date = request.getParameter("date");
        if(date != null) {
            try {
                File file = reportsService.createRoutineRefillReport(date);
                returnFile(file, response);
            } catch (Exception e) {
                e.printStackTrace();
                response.setContentType("text/html; charset=UTF-8");
                response.setHeader("", "");
                try {
                    response.getOutputStream().write((""
                            + "Error creating report " + e.getMessage() + ".</p>").getBytes());
                } catch (Exception ex) {}
            }
        }
    }

    /**
     *  Creates the Report of patients expected to visit on a
     *  particular day.
     *
     * @param response
     * @param request
     */
    @RequestMapping(value = "/json/toVisit", method = RequestMethod.GET)
    public @ResponseBody
    void createListPatientExpectedToVisit(HttpServletResponse response, HttpServletRequest request) {
        String date = request.getParameter("date");
        if(date != null) {
            try {
                File file = reportsService.createListPatientExpectedToVisit(date);
                returnFile(file, response);
            } catch (Exception e) {
                e.printStackTrace();
                response.setContentType("text/html; charset=UTF-8");
                response.setHeader("", "");
                try {
                    response.getOutputStream().write((""
                            + "Error creating report " + e.getMessage() + ".</p>").getBytes());
                } catch (Exception ex) {}
            }
        }
    }

    /**
     *  Creates the Report of patients who have missed appointments
     *
     * @param response
     * @param request
     */
    @RequestMapping(value = "/json/missedAppointment/{serviceId}", method = RequestMethod.GET)
    public @ResponseBody
    void createMissedAppointmentReport(HttpServletResponse response, HttpServletRequest request, @PathVariable("serviceId") Integer serviceId) {
        String date1 = request.getParameter("date1");
        String date2 =  request.getParameter("date2");
        if(date1 != null && date2 != null) {
            try {
                File file = reportsService.createMissedAppointmentReport(date1, date2, serviceId);
                returnFile(file, response);
            } catch (Exception e) {
                e.printStackTrace();
                response.setContentType("text/html; charset=UTF-8");
                response.setHeader("", "");
                try {
                    response.getOutputStream().write((""
                            + "Error creating report " + e.getMessage() + ".</p>").getBytes());
                } catch (Exception ex) {}
            }
        }
    }

    /**
     *  Creates the Report of patients who have missed appointments
     *
     * @param response
     * @param request
     */
    @RequestMapping(value = "/json/changedRegimen/{serviceId}", method = RequestMethod.GET)
    public @ResponseBody
    void createChangedRegimenReport(HttpServletResponse response, HttpServletRequest request, @PathVariable("serviceId") Integer serviceId) {
        String date1 = request.getParameter("date1");
        String date2 =  request.getParameter("date2");
        if(date1 != null && date2 != null) {
            try {
                File file = reportsService.createChangedRegimenReport(date1, date2, serviceId);
                returnFile(file, response);
            } catch (Exception e) {
                e.printStackTrace();
                response.setContentType("text/html; charset=UTF-8");
                response.setHeader("", "");
                try {
                    response.getOutputStream().write((""
                            + "Error creating report " + e.getMessage() + ".</p>").getBytes());
                } catch (Exception ex) {}
            }
        }
    }

    /**
     *  Creates the Commodities Received from all suppliers report
     *
     * @param response
     * @param request
     */
    @RequestMapping(value = "/json/createIssueReceive/{accountId}/{issued}/{supplierId}", method = RequestMethod.GET)
    public @ResponseBody
    void createIssueReceiptReport(HttpServletResponse response, HttpServletRequest request, @PathVariable("accountId") Integer accountId, @PathVariable("issued") Boolean issued, @PathVariable("supplierId") Integer supplierId) {
        String date1 = request.getParameter("date1");
        String date2 =  request.getParameter("date2");

        try {
            if(accountId == -1)
                accountId = null;
            File file = reportsService.createIssueReceiveReport(issued, accountId, date1, date2, supplierId);
            returnFile(file, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.setContentType("text/html; charset=UTF-8");
            response.setHeader("", "");
            try {
                response.getOutputStream().write((""
                            + "Error creating report " + e.getMessage() + ".</p>").getBytes());
            } catch (Exception ex) {}
        }

    }

    /**
     *  Creates the Commodities Received from all suppliers report
     *
     * @param response
     * @param request
     */
    @RequestMapping(value = "/json/createConsumptionReport/{year}/{drugId}/{serviceTypeId}", method = RequestMethod.GET)
    public @ResponseBody
    void createConsumptionReport(HttpServletResponse response, HttpServletRequest request, @PathVariable("year") Integer year, @PathVariable("drugId") Integer drugId, @PathVariable("serviceTypeId") Integer serviceTypeId) {

        try {
            if(drugId == -1)
                drugId = null;
            File file = reportsService.createDrugConsumptionReport(String.valueOf(year), drugId, serviceTypeId);
            returnFile(file, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.setContentType("text/html; charset=UTF-8");
            response.setHeader("", "");
            try {
                response.getOutputStream().write((""
                        + "Error creating report " + e.getMessage() + ".</p>").getBytes());
            } catch (Exception ex) {}
        }

    }


    /**
     *  Creates the Commodities Received from all suppliers report
     *
     * @param response
     * @param request
     */
    @RequestMapping(value = "/json/patientsByRegimen/{year}/{month}/{serviceId}", method = RequestMethod.GET)
    public @ResponseBody
    void createActivePatientsByRegimenReport(HttpServletResponse response, HttpServletRequest request, @PathVariable("year") Integer year, @PathVariable("month") Integer month, @PathVariable("serviceId") Integer serviceId) {
        File file = null;
        try {
            if(serviceId == -1)
                serviceId = null;
            file = reportsService.createActivePatientsByRegimenReport(month, year, serviceId);
            returnFile(file, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.setContentType("text/html; charset=UTF-8");
            response.setHeader("", "");
            try {
                response.getOutputStream().write((""
                        + "Errors creating report " + e.getMessage() + ".</p>").getBytes());
            } catch (Exception ex) {}
        } finally {
            if(file != null)
                file.delete();
        }
    }

    /**
     *  Creates the Commodities Received from all suppliers report
     *
     * @param response
     * @param request
     */
    @RequestMapping(value = "/json/createCumNumberOfPatientsReport/{year}/{month}/{serviceId}", method = RequestMethod.GET)
    public @ResponseBody
    void createCumNumberOfPatientsReport(HttpServletResponse response, HttpServletRequest request, @PathVariable("year") Integer year, @PathVariable("month") Integer month, @PathVariable("serviceId") Integer serviceId) {
        File file = null;
        try {
            if(serviceId == -1)
                serviceId = null;
            file = reportsService.createCumNumberOfPatientsReport(year, month, serviceId);
            returnFile(file, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.setContentType("text/html; charset=UTF-8");
            response.setHeader("", "");
            try {
                response.getOutputStream().write((""
                        + "Errors creating report " + e.getMessage() + ".</p>").getBytes());
            } catch (Exception ex) {}
        } finally {
            if(file != null)
                file.delete();
        }
    }

    /**
     *  Creates the Report of patients who have missed appointments
     *
     * @param response
     * @param request
     */
    @RequestMapping(value = "/json/facilityCDRRStandAloneReport", method = RequestMethod.GET)
    public @ResponseBody
    void createFacilityCDRRStandAloneReport(HttpServletResponse response, HttpServletRequest request) {
        String month = request.getParameter("month");
        String year =  request.getParameter("year");
       //Integer id= Integer.parseInt(String.valueOf(request.getParameter("id")));
        HttpSession ss = request.getSession();
        Integer accountId = (Integer)ss.getAttribute("account");

        File file = null;
        if(month != null && year != null) {
            try {
               file = reportsService.createFacilityCDRRStandAloneReport(month, year, accountId);
                returnFile(file, response);
            } catch (Exception e) {
                e.printStackTrace();
                response.setContentType("text/html; charset=UTF-8");
                response.setHeader("", "");
                try {
                    response.getOutputStream().write((""
                            + "Error creating report " + e.getMessage() + ".</p>").getBytes());
                } catch (Exception ex) {}
            }finally {
                if(file != null)
                    file.delete();
            }
        }
    }
/** creates the pdf report for FMAPS*/




//@RequestMapping(value = "/json/fmapspdf", method = RequestMethod.GET)
//public @ResponseBody
//void createFacilityCDRRStandAloneReport(HttpServletResponse response, HttpServletRequest request) {
//    String month = request.getParameter("month");
//    String year =  request.getParameter("year");
//
//    HttpSession ss = request.getSession();
//    Integer accountId = (Integer)ss.getAttribute("account");
//
//    File file = null;
//    if(month != null && year != null) {
//        try {
//            file = reportsService.createFacilityFmapsReport(month, year, accountId);
//            returnFile(file, response);
//        } catch (Exception e) {
//            e.printStackTrace();
//            response.setContentType("text/html; charset=UTF-8");
//            response.setHeader("", "");
//            try {
//                response.getOutputStream().write((""
//                        + "Error creating report " + e.getMessage() + ".</p>").getBytes());
//            } catch (Exception ex) {}
//        }finally {
//            if(file != null)
//                file.delete();
//        }
//    }
//}


//

	/**
	 * Creates the Report of patients who have missed appointments
	 *
	 * @param response
	 * @param request
	 */
	@RequestMapping(value = "/json/facilityCDRRStandAloneReportDhis", method = RequestMethod.GET)
	public
	@ResponseBody
	void createFacilityCDRRStandAloneReportDhis(HttpServletResponse response, HttpServletRequest request) throws IOException {
		String month = request.getParameter("month");
		String year = request.getParameter("year");

        HttpSession ss = request.getSession();
        Integer accountId = (Integer)ss.getAttribute("account");

		String period = getPeriod(month, year);
		JSONObject allData = reportsService.createARTCDRRData(month, year, accountId);
		Map<String, String> catMappings = getArtOiMappings();

		generateDhisFile(allData, period, catMappings, response, "MOH 729A  F-CDRR for Antiretroviral and opportunistic infection medicine.xml");
	}

	/**
	 *  Creates the Report of patients who have missed appointments
	 *
	 * @param response
	 * @param request
	 */
	@RequestMapping(value = "/json/facilityEMMSCDRRStandAloneReportDhis", method = RequestMethod.GET)
	public @ResponseBody
	void facilityEMMSCDRRStandAloneReportDhis(HttpServletResponse response, HttpServletRequest request) throws IOException {
		String month = request.getParameter("month");
		String year = request.getParameter("year");

		String period = getPeriod(month, year);
		JSONObject allData = reportsService.createEMMSCDRRData(month, year);
		Map<String, String> catMappings = getEmmsMappings();

		generateDhisFile(allData, period, catMappings, response, "MOH 643B F-CDRR for ART lab monitoring reagents.xml");
	}

	/**
	 *  Creates the Malaria CDRR Report
	 *
	 * @param response
	 * @param request
	 */
	@RequestMapping(value = "/json/createMalariaCDRRReportDhis", method = RequestMethod.GET)
	public @ResponseBody
	void createFacilityMalariaCDRRStandAloneReportDhis(HttpServletResponse response, HttpServletRequest request) throws IOException {
		String month = request.getParameter("month");
		String year = request.getParameter("year");

		String period = getPeriod(month, year);
		JSONObject allData = reportsService.createMalariaCDRRData(month, year);
		Map<String, String> catMappings = getMalariaMappings();

		generateDhisFile(allData, period, catMappings, response, "Malaria Commodities Form.xml");
	}

	/**
	 *  Creates the Lab CDRR Report
	 *
	 * @param response
	 * @param request
	 */
	@RequestMapping(value = "/json/createFacilityLabCDRRStandAloneReportDhis", method = RequestMethod.GET)
	public @ResponseBody
	void createFacilityLabCDRRStandAloneReportDhis(HttpServletResponse response, HttpServletRequest request) throws IOException {
		String month = request.getParameter("month");
		String year = request.getParameter("year");

		String period = getPeriod(month, year);
		JSONObject allData = reportsService.createLabCDRRData(month, year);
		Map<String, String> catMappings = getLabMappings();

		generateDhisFile(allData, period, catMappings, response, "MOH 643 F-CDRR for Lab commodities.xml");
	}

	/**
	 *  Creates the Nutrition CDRR Report
	 *
	 * @param response
	 * @param request
	 */
	@RequestMapping(value = "/json/createFacilityNutritionCDRRStandAloneReportDhis", method = RequestMethod.GET)
	public @ResponseBody
	void createFacilityNutritionCDRRStandAloneReportDhis(HttpServletResponse response, HttpServletRequest request) throws IOException {
		String month = request.getParameter("month");
		String year = request.getParameter("year");

		String period = getPeriod(month, year);
		JSONObject allData = reportsService.createNutritionCDRRData(month, year);
		Map<String, String> catMappings = getNutritionMappings();

		generateDhisFile(allData, period, catMappings, response, "MOH 734 F-CDRR for HIV Nutrition Commodities.xml");
	}

	/**
	 *  Creates the FP CDRR Report
	 *
	 * @param response
	 * @param request
	 */
	@RequestMapping(value = "/json/createFPCDRRReportDhis", method = RequestMethod.GET)
	public @ResponseBody
	void createFacilityFPCDRRStandAloneReportDhis(HttpServletResponse response, HttpServletRequest request) throws IOException {
		String month = request.getParameter("month");
		String year = request.getParameter("year");

		String period = getPeriod(month, year);
		JSONObject allData = reportsService.createFPCDRRData(month, year);
		Map<String, String> catMappings = getFpMappings();

		generateDhisFile(allData, period, catMappings, response, "Facility Contraceptives Consumption Report and Request Form.xml");
	}

	/**
	 * Writes out XML output in DXF format for import into DHIS.
	 *
	 * @param allData the data for the report
	 * @param period the reporting period.
	 * @param catMappings the mappings of JSONObject keys (that point to values) to the category combo uids specified
	 * in the DXF template.
	 * @param response the HttpServletResponse whose OutputStream we'll write to.
	 */
	private void generateDhisFile(JSONObject allData, String period, Map<String, String> catMappings,
								  HttpServletResponse response, String fileName)
			throws IOException{
		String mflCode = getProperty("facility_code");
		response.setContentType("text/xml");
		response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
		OutputStream out = null;
		try {
			out= response.getOutputStream();
			out.write("<?xml version='1.0' encoding='UTF-8'?>\n".getBytes());
			String dataSetLine = "<dataValueSet xmlns=\"http://dhis2.org/schema/dxf/2.0\" orgUnitIdScheme=\"code\">\n";
			out.write(dataSetLine.getBytes());
			for (Object key : allData.keySet()) {
				String drugId = (String) key;
				JSONObject drugData = (JSONObject) allData.get(drugId);
				for (String catKey : catMappings.keySet()) {
					String value = getValue(drugData, catKey);
                    String def = "missing";
                    String dhisId = getValue(drugData, "dhisid", def, true);
                    if (def.equals(dhisId)) {
                        continue;
                    }
					String dataValueLine = "<dataValue dataElement=\"" + dhisId + "\" " + "orgUnit=\"" +mflCode
							+ "\"" + " categoryOptionCombo=\"" + catMappings.get(catKey) + "\" period=\"" + period + "\" value=\"" + value + "\"/>\n";
					out.write(dataValueLine.getBytes());
				}
			}
			out.write("</dataValueSet>\n".getBytes());
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

	/**
	 * Returns a String representing the period of reporting in the format YYYYMM.
	 */
	private String getPeriod(String month, String year) {
		int m = Integer.parseInt(month);
		m++;
		month = String.valueOf(m);
		if (month.length() == 1) {
			month = "0" + month;
		}
		return year + month;
	}

	/**
	 * Returns the value from the given JSONObject associated with the given key.
	 */
	private String getValue(JSONObject json, String key) {
		return getValue(json, key, "0");
	}

    private String getValue(JSONObject json, String key, String def) {
        return getValue(json, key, def, false);
    }

	/**
	 * Returns the value from the given JSONObject associated with the given key. If the key is found
     * in the JSONObject, the corresponding drug DHIS ID value is returned. If the key is not found,
     * the drug name is returned. If that too is not found, the default value passed as def is returned
     * instead. However, if enforceDef is true, the default value is always returned if the key is not
     * found.
	 */
	private String getValue(JSONObject json, String key, String def, boolean enforceDef) {
		String value = def;
		if (json.has(key)) {
			Object val = json.get(key);
			if (val != null) {
				value = val.toString();
			}
		} else {
			if (!enforceDef && key.equals("dhisid")) {
				Object name = json.get("name");
				if (name != null) {
					value = name.toString();
				}
			}
		}
		return value;
	}

	private Map<String, String> properties;

	private String getProperty(String key) {
		if (properties == null) {
			properties = new HashMap<String, String>();
			List<Property> propertyList = adminService.getFacilityInformation();
			if (propertyList != null) {
				for (Property p : propertyList) {
					properties.put(p.getKey(), p.getValue());
				}
			}
		}
		return properties.get(key);
	}

    /**
     *  Creates the Report of patients who have missed appointments
     *
     * @param response
     * @param request
     */
    @RequestMapping(value = "/excel/facilityCDRRStandAloneReport", method = RequestMethod.GET)
    public @ResponseBody
    void createExcelFacilityCDRRStandAloneReport(HttpServletResponse response, HttpServletRequest request) {
        String month = request.getParameter("month");
        String year =  request.getParameter("year");
       // Integer id= Integer.parseInt(String.valueOf(request.getParameter("id")));
        HttpSession ss = request.getSession();
       Integer accountId = (Integer)ss.getAttribute("account");
        File file = null;
        if(month != null && year != null) {
            try {
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition",
                        "attachment; filename=FCDRR.xls");
                response.setCharacterEncoding( "UTF-8" );
                OutputStream out = response.getOutputStream();
                reportsService.createFacilityCDRR(month, year, servletContext, accountId).write(out);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *  Creates the Report of patients who have missed appointments
     *
     * @param response
     * @param request
     */
    @RequestMapping(value = "/json/facilityEMMSCDRRStandAloneReport", method = RequestMethod.GET)
    public @ResponseBody
    void facilityEMMSCDRRStandAloneReport(HttpServletResponse response, HttpServletRequest request) {
        String month = request.getParameter("month");
        String year =  request.getParameter("year");
        File file = null;
        if(month != null && year != null) {
            try {
                file = reportsService.createEMMSFacilityCDRRStandAloneReport(month, year);
                returnFile(file, response);
            } catch (Exception e) {
                e.printStackTrace();
                response.setContentType("text/html; charset=UTF-8");
                response.setHeader("", "");
                try {
                    response.getOutputStream().write((""
                            + "Error creating report " + e.getMessage() + ".</p>").getBytes());
                } catch (Exception ex) {}
            }finally {
                if(file != null)
                    file.delete();
            }
        }
    }

    /**
     *  Creates the Malaria CDRR Report
     *
     * @param response
     * @param request
     */
    @RequestMapping(value = "/json/createMalariaCDRRReport", method = RequestMethod.GET)
    public @ResponseBody
    void createFacilityMalariaCDRRStandAloneReport(HttpServletResponse response, HttpServletRequest request) {
        String month = request.getParameter("month");
        String year =  request.getParameter("year");
        File file = null;
        if(month != null && year != null) {
            try {
                file = reportsService.createMalariaCDRRReport(month, year);
                returnFile(file, response);
            } catch (Exception e) {
                e.printStackTrace();
                response.setContentType("text/html; charset=UTF-8");
                response.setHeader("", "");
                try {
                    response.getOutputStream().write((""
                            + "Error creating report " + e.getMessage() + ".</p>").getBytes());
                } catch (Exception ex) {}
            }finally {
                if(file != null)
                    file.delete();
            }
        }
    }

    /**
     *  Creates the Lab CDRR Report
     *
     * @param response
     * @param request
     */
    @RequestMapping(value = "/json/createFacilityLabCDRRStandAloneReport", method = RequestMethod.GET)
    public @ResponseBody
    void createFacilityLabCDRRStandAloneReport(HttpServletResponse response, HttpServletRequest request) {
        String month = request.getParameter("month");
        String year =  request.getParameter("year");
        File file = null;
        if(month != null && year != null) {
            try {
                file = reportsService.createLabCDRRReport(month, year);
                returnFile(file, response);
            } catch (Exception e) {
                e.printStackTrace();
                response.setContentType("text/html; charset=UTF-8");
                response.setHeader("", "");
                try {
                    response.getOutputStream().write((""
                            + "Error creating report " + e.getMessage() + ".</p>").getBytes());
                } catch (Exception ex) {}
            }finally {
                if(file != null)
                    file.delete();
            }
        }
    }

    /**
     *  Creates the Nutrition CDRR Report
     *
     * @param response
     * @param request
     */
    @RequestMapping(value = "/json/createFacilityNutritionCDRRStandAloneReport", method = RequestMethod.GET)
    public @ResponseBody
    void createFacilityNutritionCDRRStandAloneReport(HttpServletResponse response, HttpServletRequest request) {
        String month = request.getParameter("month");
        String year =  request.getParameter("year");
        File file = null;
        if(month != null && year != null) {
            try {
                file = reportsService.createNutritionCDRRReport(month, year);
                returnFile(file, response);
            } catch (Exception e) {
                e.printStackTrace();
                response.setContentType("text/html; charset=UTF-8");
                response.setHeader("", "");
                try {
                    response.getOutputStream().write((""
                            + "Error creating report " + e.getMessage() + ".</p>").getBytes());
                } catch (Exception ex) {}
            }finally {
                if(file != null)
                    file.delete();
            }
        }
    }

    /**
     *  Creates the FP CDRR Report
     *
     * @param response
     * @param request
     */
    @RequestMapping(value = "/json/createFPCDRRReport", method = RequestMethod.GET)
    public @ResponseBody
    void createFacilityFPCDRRStandAloneReport(HttpServletResponse response, HttpServletRequest request) {
        String month = request.getParameter("month");
        String year =  request.getParameter("year");
        File file = null;
        if(month != null && year != null) {
            try {
                file = reportsService.createFPCDRRReport(month, year);
                returnFile(file, response);
            } catch (Exception e) {
                e.printStackTrace();
                response.setContentType("text/html; charset=UTF-8");
                response.setHeader("", "");
                try {
                    response.getOutputStream().write((""
                            + "Error creating report " + e.getMessage() + ".</p>").getBytes());
                } catch (Exception ex) {}
            }finally {
                if(file != null)
                    file.delete();
            }
        }
    }

    @RequestMapping(value="/json/createStockTakeExcel/{accountId}/{userID}", method = RequestMethod.GET)
    public void createStockTakeExcel(@PathVariable("accountId") Integer accountId, @PathVariable("userID") Integer userId, HttpServletResponse response) {
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition",
                    "attachment; filename=StockTake.xls");
            response.setCharacterEncoding( "UTF-8" );
            OutputStream out = response.getOutputStream();
            stocksService.createStockTakeExcel(accountId, userId).write(out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/json/createPatientsByStatusReport/{patientStatus}", method = RequestMethod.GET)
    public @ResponseBody
    void createPatientsByStatusReport(HttpServletResponse response, HttpServletRequest request, @PathVariable("patientStatus")Integer patientStatus) {
        File file = null;
        try {
            file = reportsService.createPatientsByStatus(patientStatus);
            returnFile(file, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.setContentType("text/html; charset=UTF-8");
            response.setHeader("", "");
            try {
                response.getOutputStream().write((""
                            + "Error creating report " + e.getMessage() + ".</p>").getBytes());
            } catch (Exception ex) {}
        }finally {
            if(file != null)
                file.delete();
        }

    }

    @RequestMapping(value = "/json/patientListingByDrugUsed/{drugId}", method = RequestMethod.GET)
    public @ResponseBody
    void patientListingByDrugUsed(HttpServletResponse response, HttpServletRequest request, @PathVariable("drugId")Integer drugId) {
        File file = null;
        try {
            file = reportsService.createPatientsByDrugUsedReport(drugId);
            returnFile(file, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.setContentType("text/html; charset=UTF-8");
            response.setHeader("", "");
            try {
                response.getOutputStream().write((""
                        + "Error creating report " + e.getMessage() + ".</p>").getBytes());
            } catch (Exception ex) {}
        }finally {
            if(file != null)
                file.delete();
        }

    }

    /**
     *  Creates the Report of patients who have missed appointments
     *
     * @param response
     * @param request
     */
    @RequestMapping(value = "/json/patientsStartingByRegimenReport/{serviceId}", method = RequestMethod.GET)
    public @ResponseBody
    void createPatientsStartingByRegimenReport(HttpServletResponse response, HttpServletRequest request, @PathVariable("serviceId") Integer serviceId) {
        String date1 = request.getParameter("date1");
        String date2 =  request.getParameter("date2");

        File file = null;
        if(date1 != null && date2 != null) {
            try {
                file = reportsService.createPatientsStartingByRegimen(date1, date2, serviceId);
                returnFile(file, response);
            } catch (Exception e) {
                e.printStackTrace();
                response.setContentType("text/html; charset=UTF-8");
                response.setHeader("", "");
                try {
                    response.getOutputStream().write((""
                            + "Error creating report " + e.getMessage() + ".</p>").getBytes());
                } catch (Exception ex) {}
            }finally {
                if(file != null)
                    file.delete();
            }
        }
    }

    /**
     *  Creates the Report of patients who have missed appointments
     *
     * @param response
     * @param request
     */
    @RequestMapping(value = "/json/patientsEnrolled/{serviceId}", method = RequestMethod.GET)
    public @ResponseBody
    void createEnrolledPatientsReport(HttpServletResponse response, HttpServletRequest request, @PathVariable("serviceId") Integer serviceId) {
        String date1 = request.getParameter("date1");
        String date2 =  request.getParameter("date2");
        if(date1 != null && date2 != null) {
            try {
                File file = reportsService.createEnrolledPatientsReport(date1, date2, serviceId);
                returnFile(file, response);
            } catch (Exception e) {
                e.printStackTrace();
                response.setContentType("text/html; charset=UTF-8");
                response.setHeader("", "");
                try {
                    response.getOutputStream().write((""
                            + "Error creating report " + e.getMessage() + ".</p>").getBytes());
                } catch (Exception ex) {}
            }
        }
    }

    /**
     *  Creates the Report of patients who have Started
     *
     * @param response
     * @param request
     */
    @RequestMapping(value = "/json/patientsStarted/{serviceId}", method = RequestMethod.GET)
    public @ResponseBody
    void createStartedPatientsReport(HttpServletResponse response, HttpServletRequest request, @PathVariable("serviceId") Integer serviceId) {
        String date1 = request.getParameter("date1");
        String date2 =  request.getParameter("date2");
        if(date1 != null && date2 != null) {
            try {
                File file = reportsService.createStartedPatientsReport(date1, date2, serviceId);
                returnFile(file, response);
            } catch (Exception e) {
                e.printStackTrace();
                response.setContentType("text/html; charset=UTF-8");
                response.setHeader("", "");
                try {
                    response.getOutputStream().write((""
                            + "Error creating report " + e.getMessage() + ".</p>").getBytes());
                } catch (Exception ex) {}
            }
        }
    }

    /**
     *  Creates the Report of patients who have missed appointments
     *
     * @param response
     * @param request
     */
    @RequestMapping(value = "/json/bmi/{serviceId}/{adult}", method = RequestMethod.GET)
    public @ResponseBody
    void createBMIReport(HttpServletResponse response, HttpServletRequest request, @PathVariable("serviceId") Integer serviceId, @PathVariable("adult") boolean adult) {
        try {
            File file = reportsService.createBMIReport(serviceId, adult);
            returnFile(file, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.setContentType("text/html; charset=UTF-8");
            response.setHeader("", "");
            try {
                response.getOutputStream().write((""
                        + "Error creating report " + e.getMessage() + ".</p>").getBytes());
            } catch (Exception ex) {}
        }
    }

    /**
     *  Creates the Report of patients who have missed appointments
     *
     * @param response
     * @param request
     */
    @RequestMapping(value = "/json/enrollmentGraph/{year}", method = RequestMethod.GET)
    public @ResponseBody
    void createEnrollmentGraphReport(HttpServletResponse response, HttpServletRequest request, @PathVariable("year") Integer year) {
        try {
            File file = reportsService.createEnrollmentGraph(year);
            returnFile(file, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.setContentType("text/html; charset=UTF-8");
            response.setHeader("", "");
            try {
                response.getOutputStream().write((""
                        + "Error creating report " + e.getMessage() + ".</p>").getBytes());
            } catch (Exception ex) {}
        }
    }

    /**
     *  Creates the Report of patients who have Started
     *
     * @param response
     * @param request
     */
    @RequestMapping(value = "/json/ewi1", method = RequestMethod.GET)
    public @ResponseBody
    void createEWIReport(HttpServletResponse response, HttpServletRequest request) {
        String date1 = request.getParameter("date1");
        String date2 =  request.getParameter("date2");
        if(date1 != null && date2 != null) {
            try {
                File file = reportsService.createEWI1Report(date1, date2);
                returnFile(file, response);
            } catch (Exception e) {
                e.printStackTrace();
                response.setContentType("text/html; charset=UTF-8");
                response.setHeader("", "");
                try {
                    response.getOutputStream().write((""
                            + "Error creating report " + e.getMessage() + ".</p>").getBytes());
                } catch (Exception ex) {}
            }
        }
    }

    /**
     *  Creates the Report of patients who have Started
     *
     * @param response
     * @param request
     */
    @RequestMapping(value = "/json/ewi2", method = RequestMethod.GET)
    public @ResponseBody
    void createEWI2Report(HttpServletResponse response, HttpServletRequest request) {
        String date1 = request.getParameter("date1");
        String date2 =  request.getParameter("date2");
        if(date1 != null && date2 != null) {
            try {
                File file = reportsService.createEWI2Report(date1, date2);
                returnFile(file, response);
            } catch (Exception e) {
                e.printStackTrace();
                response.setContentType("text/html; charset=UTF-8");
                response.setHeader("", "");
                try {
                    response.getOutputStream().write((""
                            + "Error creating report " + e.getMessage() + ".</p>").getBytes());
                } catch (Exception ex) {}
            }
        }
    }

    /**
     *  Creates the Report of patients who have Started
     *
     * @param response
     * @param request
     */
    @RequestMapping(value = "/json/ewi3", method = RequestMethod.GET)
    public @ResponseBody
    void createEWI3Report(HttpServletResponse response, HttpServletRequest request) {
        String date1 = request.getParameter("date1");
        String date2 =  request.getParameter("date2");
        if(date1 != null && date2 != null) {
            try {
                File file = reportsService.createEWI3Report(date1, date2);
                returnFile(file, response);
            } catch (Exception e) {
                e.printStackTrace();
                response.setContentType("text/html; charset=UTF-8");
                response.setHeader("", "");
                try {
                    response.getOutputStream().write((""
                            + "Error creating report " + e.getMessage() + ".</p>").getBytes());
                } catch (Exception ex) {}
            }
        }
    }


    /**
     *  Creates the Report of patients who have Started
     *
     * @param response
     * @param request
     */
    @RequestMapping(value = "/json/ewi4", method = RequestMethod.GET)
    public @ResponseBody
    void createEWI4Report(HttpServletResponse response, HttpServletRequest request) {
        String date1 = request.getParameter("date1");
        String date2 =  request.getParameter("date2");
        if(date1 != null && date2 != null) {
            try {
                File file = reportsService.createEWI4Report(date1, date2);
                returnFile(file, response);
            } catch (Exception e) {
                e.printStackTrace();
                response.setContentType("text/html; charset=UTF-8");
                response.setHeader("", "");
                try {
                    response.getOutputStream().write((""
                            + "Error creating report " + e.getMessage() + ".</p>").getBytes());
                } catch (Exception ex) {}
            }
        }
    }

    /**
     *  Creates the Report of patients who have Started
     *
     * @param response
     * @param request
     */
    @RequestMapping(value = "/json/ewi", method = RequestMethod.POST)
    public @ResponseBody
    List<Double> loadEWI(HttpServletResponse response, HttpServletRequest request) {
        Calendar c = Calendar.getInstance();
        java.util.Date d = c.getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");

        String date1 = df.format(d);
        c.add(Calendar.DAY_OF_YEAR, -365);
        String date2 =  df.format(c.getTime());
        if(date1 != null && date2 != null) {
            try {
                List<Double> report = reportsService.getEWI(date2, date1);
                return report;
            } catch (Exception e) {
                e.printStackTrace();
                response.setContentType("text/html; charset=UTF-8");
                response.setHeader("", "");
                try {
                    response.getOutputStream().write((""
                            + "Error creating report " + e.getMessage() + ".</p>").getBytes());
                } catch (Exception ex) {}
            }

        }
        return null;
    }

    @RequestMapping(value="/json/activePatients/{year}/{month}", method = RequestMethod.GET)
    public void createActivePatientsByRegimen(HttpServletRequest request, HttpServletResponse response, @PathVariable("year") String year, @PathVariable("month") String month) {
            try {
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition",
                        "attachment; filename=ActivePatientsByRegimen.xls");
                response.setCharacterEncoding( "UTF-8" );
                OutputStream out = response.getOutputStream();
                reportsService.createActivePatientsByRegimen(servletContext, year, month).write(out);
            } catch (Exception e) {
                e.printStackTrace();
            }

    }

    public void returnFile(File file, HttpServletResponse response) throws Exception{
        if(file != null) {
            ServletOutputStream out = null;
            response.setHeader("Content-Disposition", "inline; filename="
                    + file.getName().replace(" ", "_"));
            response.setContentType("application/octet-stream");
            response.setCharacterEncoding("UTF-8");
            out = response.getOutputStream();
            out.write(getBytesFromFile(file));
        } else {
            response.setContentType("text/html; charset=UTF-8");
            response.setHeader("", "");

            response.getOutputStream().write((""
                    + "Error creating report File NULL.</p>").getBytes());
        }
    }

    public byte[] getBytesFromFile(File file) throws IOException {
        InputStream is = new FileInputStream(file);

        // Get the size of the file
        long length = file.length();

        if (length > Integer.MAX_VALUE) {
            // File is too large
        }

        // Create the byte array to hold the data
        byte[] bytes = new byte[(int) length];

        // Read in the bytes
        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length
                && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
            offset += numRead;
        }
        // Close the input stream and return bytes
        is.close();
        // Ensure all the bytes have been read in
        if (offset < bytes.length) {
            throw new IOException("Could not completely read file "+ file.getName());
        }
        return bytes;
    }

	private Map<String, String> getArtOiMappings() {
		Map<String, String> catMappings = new LinkedHashMap<String, String>();
		catMappings.put("balance", "GiX18Ajg802");
		catMappings.put("closingBalance", "DwXmxHIaCl4");
		catMappings.put("quantityReceived", "nRJ0tEAbtXD");
		catMappings.put("quantityDispensed", "Yzos1HQDu7P");
		catMappings.put("losses", "GcXMqOO4vEX");
		catMappings.put("adjustments", "hfsmZ1WRgiE");
		catMappings.put("shortDated", "afDvnfILp4s");
		catMappings.put("expiryDate", "Jpk73oSEQh8");
		catMappings.put("outOfStock", "scfszTbInOZ");
		return catMappings;
	}

	private Map<String, String> getEmmsMappings() {
		Map<String, String> catMappings = new LinkedHashMap<String, String>();
		catMappings.put("balance", "QB1xe2zMhpN");
//		catMappings.put("closingBalance", "");
		catMappings.put("quantityReceived", "G4TgmzMQ55W");
		catMappings.put("quantityDispensed", "h0fpbebs1cB");
//		catMappings.put("dispensedPacks", "");
		catMappings.put("losses", "U3zV9xMKzOt");
		catMappings.put("adjustments", "CyfZGAvmR1q");
//		catMappings.put("shortDated", "");
//		catMappings.put("expiryDate", "");
//		catMappings.put("outOfStock", "");
		return catMappings;
	}

	private Map<String, String> getFpMappings() {
		Map<String, String> catMappings = new LinkedHashMap<String, String>();
		catMappings.put("balance", "AO6xfSyHuHU");
		catMappings.put("closingBalance", "VW51Ep3auXR");
		catMappings.put("quantitfyReceived", "p8PidvKaOkp");
		catMappings.put("quantityDispensed", "wkXcJRRTRxA");
//		catMappings.put("dispensedPacks", "wkXcJRRTRxA");
		catMappings.put("losses", "wlwCxWgecNX");
//		catMappings.put("adjustments", "");
//		catMappings.put("negativeAdjustments", "");
		return catMappings;
	}

	private Map<String, String> getLabMappings() {
		Map<String, String> catMappings = new LinkedHashMap<String, String>();
		catMappings.put("balance", "SW1genWXdys");
		catMappings.put("closingBalance", "UbFZzGAky0H");
		catMappings.put("quantityReceived", "v0szLFkFN1L");
		catMappings.put("quantityDispensed", "EJ8nUfcupBq");
//		catMappings.put("dispensedPacks", "");
		catMappings.put("losses", "LDmTsQdoXs5");
		catMappings.put("adjustments", "jFOwIS0MEFZ");
		catMappings.put("negativeAdjustments", "qrh0ydN5iqx");
		catMappings.put("shortDated", "ZxUQw1ay1cw");
//		catMappings.put("expiryDate", "");
		catMappings.put("outOfStock", "sL6cDCzo7h9");
		return catMappings;
	}

	private Map<String, String> getMalariaMappings() {
		Map<String, String> catMappings = new LinkedHashMap<String, String>();
		catMappings.put("balance", "NhSoXUMPK2K");
		catMappings.put("closingBalance", "unVIt2C0cdW");
		catMappings.put("quantityReceived", "HWtHCLAwprR");
		catMappings.put("quantityDispensed", "yuvCdaFqdCW");
		catMappings.put("dispensedPacks", "yuvCdaFqdCW");
		catMappings.put("losses", "w77uMi1KzOH");
		catMappings.put("adjustments", "rqzfl66VFyd");
		catMappings.put("negativeAdjustments", "CckV73xy6HB");
		catMappings.put("shortDated", "lWysQ1ZqF0W");
//		catMappings.put("expiryDate", "");
		catMappings.put("outOfStock", "iVuuwYeI4rW");
		return catMappings;
	}

	private Map<String, String> getNutritionMappings() {
		Map<String, String> catMappings = new LinkedHashMap<String, String>();
		catMappings.put("balance", "lJkBKg5NQmP");
		catMappings.put("closingBalance", "SXnAzLRZeuJ");
		catMappings.put("quantityReceived", "JyAgi5sSnG9");
		catMappings.put("quantityDispensed", "dfornXoUXQR");
		catMappings.put("dispensedPacks", "dfornXoUXQR");
		catMappings.put("losses", "DSI1HjB8pD7");
		catMappings.put("adjustments", "RWCHIdXizft");
		catMappings.put("negativeAdjustments", "voc59fThdd9");
		catMappings.put("shortDated", "OmTneOE9mMF");
		catMappings.put("expiryDate", "Rm5hfNrukCN");
		catMappings.put("outOfStock", "hCi6jA02tGb");
		return catMappings;
	}
}
