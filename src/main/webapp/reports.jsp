<%--
  Created by IntelliJ IDEA.
  User: kenny
  Date: 3/20/14
  Time: 8:12 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

    <title>Reports | EDITT</title>
    <meta http-equiv="Cache-Control" content="max-age=0" />
    <meta http-equiv="Cache-Control" content="no-cache" />
    <meta http-equiv="Cache-Control" content="no-store" />
    <meta http-equiv="Cache-Control" content="must-revalidate" />
    <meta http-equiv="Expires" content="0" />
    <meta http-equiv="Expires" content="Tue, 01 Jan 1980 1:00:00 GMT" />
    <meta http-equiv="Pragma" content="no-cache" />
    <%@page import="java.io.FileInputStream" %>
    <%@page import="java.util.Properties" %>
    <%
        response.setHeader("Pragma","no-cache");
        response.setHeader("Cache-Control","no-store");
        response.setHeader("Expires","0");
        response.setDateHeader("Expires",-1);


        Properties properties = new Properties();
        properties.load(new FileInputStream(request.getRealPath("settings.properties")));
        System.setProperty("version", properties.getProperty("version") );

        Object logged = session.getAttribute("loggedin");
        if(logged == null) {
            response.sendRedirect("index.jsp?action=login");
        } else if(session.getAttribute("mod_reports") == null) {
            response.sendRedirect("home.jsp?action=login");
        }
    %>
    <script language="JavaScript">
        window.accountID = <%= session.getAttribute("account") %>
        window.user_id = <%= session.getAttribute("userId") %>

        var privileges = {
            mod_ART_patient_reports : <%=session.getAttribute("mod_ART_patient_reports")%>,
            mod_ART_stock_reports : <%=session.getAttribute("mod_ART_stock_reports")%>,
            mod_general_stock_reports : <%=session.getAttribute("mod_general_stock_reports")%>
        }
    </script>
    <link type="text/css" rel="stylesheet" href="css/jquery-ui-1.10.4.css"/>
    <link type="text/css" rel="stylesheet" href="css/bootstrap.min.css"/>
    <link type="text/css" rel="stylesheet" href="css/bootstrap-theme.min.css"/>
    <link type="text/css" rel="stylesheet" href="css/style.css?v=<%= System.getProperty("version") %>"/>
    <link type="text/css" rel="stylesheet" href="css/images/font/flaticon.css"/>

</head>
<body>
<jsp:include page="header.jsp"/>
<div id="container">
    <div id="content">
<div class="content">
    <div class="menu">
        <table align="center" class="reportarrange">
            <tr>
                <td>
        <div class="menuItem" onclick="showReportDialog('enrollmentReport')">
            <img src="css/images/png/business-1.png" height="100px" width="100px"/>
            <div>Enrollment report</div>
        </div>
                </td>
                <td>
                <div class="menuItem" onclick="showReportDialog('patientServices')">
                    <img src="css/images/png/web.png" height="100px" width="100px"/>
                    <div>Patients service statistics</div>
                </div>
                </td>
                <td>
                <% if(session.getAttribute("mod_ART_patient_reports") != null && String.valueOf(session.getAttribute("mod_ART_patient_reports")).equalsIgnoreCase("true")) {%>
                <div class="menuItem" onclick="showReportDialog('patientListingByStatusReport')">
                    <img src="css/images/png/contacts.png" height="100px" width="100px"/>
                    <div>Patient listing by status</div>
                </div>
                </td>
                <td>
                <div class="menuItem" onclick="showReportDialog('visitingPatients')">
                    <img src="css/images/png/medical-6.png" height="100px" width="100px"/>
                    <div>Visiting patients</div>
                </div>
                </td>

            </tr>
            <tr>
                <td>
                <div class="menuItem" onclick="showReportDialog('listingRegimenMissedAppointments')">
                    <img src="css/images/png/computer.png" height="100px" width="100px"/>
                    <div>Patients listing regimens and missing appointments</div>
                </div>
                </td>
                <td>
                    <% }
                if(session.getAttribute("mod_ART_stock_reports") != null && String.valueOf(session.getAttribute("mod_ART_stock_reports")).equalsIgnoreCase("true")) {%>
                <div class="menuItem" onclick="showReportDialog('ewiReports')">
                    <img src="css/images/png/medical-7.png" height="100px" width="100px"/>
                    <div>Early warning indicators (EWI)</div>
                </div>
                </td>
                <td>
                    <% }
                        if(session.getAttribute("mod_ART_patient_reports") != null && String.valueOf(session.getAttribute("mod_ART_patient_reports")).equalsIgnoreCase("true")) {%>
                <div class="menuItem" onclick="showReportDialog('drugUsedReport')">
                    <img src="css/images/png/business-5.png" height="100px" width="100px"/>
                    <div>Patient listing by type of commodity used</div>
                </div>
                </td>
                <td>
                <div class="menuItem" onclick="showReportDialog('bmiReport')">
                    <img src="css/images/png/graphic-3.png" height="100px" width="100px"/>
                    <div>Patient BMI</div>
                </div>
                <% } %>
                </td>

            </tr>

           <tr>
               <td>
               <div class="menuItem" onclick="showReportDialog('receiveIssueReports')">
                   <img src="css/images/png/cup.png" height="100px" width="100px"/>
                   <div>Commodities received/Issued</div>
               </div>
               </td>
               <td>
               <div class="menuItem" onclick="showReportDialog('drugConsumption')">
                   <img src="css/images/png/graphic.png" height="100px" width="100px"/>
                   <div>Commodity consumption</div>
               </div>
               </td>
               <td>
               <div class="menuItem" onclick="showReportDialog('stockReports')">
                   <img src="css/images/png/business-11.png" height="100px" width="100px"/>
                   <div>Commodity stock status report</div>
               </div>
               </td>
               <td>
               <% if(session.getAttribute("mod_ART_stock_reports") != null && String.valueOf(session.getAttribute("mod_ART_stock_reports")).equalsIgnoreCase("true")) {%>

               <div class="menuItem" onclick="showReportDialog('monthlyReport')">
                   <img src="css/images/png/business-9.png" height="100px" width="100px"/>
                   <div>Commodity Monthly report</div>
               </div>
               <% } %>
               </td>
           </tr>
           <tr>
               <td>

               <div class="menuItem" onclick="showDailyConsumptionDispensing()">

                   <img src="css/images/png/business-8.png" height="100px" width="100px"/>
                   <div>Daily Consumption / Dispensing report</div>

               </div>
               </td>
               <td>
                   <div class="menuItem" onclick="showReportDialog('facilitpadherence')">

                       <img src="css/images/png/business-5.png" height="100px" width="100px"/>
                       <div>Facility Patient Adherence Summary</div>
                   </div>
               </td>
               <td>

                   <div class="menuItem" onclick="showfacilityappointment()">

                       <img src="css/images/png/business-8.png" height="100px" width="100px"/>
                       <div>Facility Appointment Keeping Report</div>

                   </div>
               </td>
               <td>

                   <div class="menuItem" onclick="showadherencemonitoring()">

                       <img src="css/images/png/healthy.png" height="100px" width="100px"/>
                       <div>Facility Appointment Keeping Report</div>

                   </div>
               </td>




           </tr>





 </table>
</div>


<div id="reportDialog" class="modal fade full-modal" data-backdrop="static">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">Reports</h4>
            </div>
            <div class="modal-body">
                <div id="visitingPatients" style="display: none">
                        <div><h5>Visiting patients</h5></div>
                        <fieldset class="field">
                            <div class="form-group-sm">
                            <label>List patients scheduled to visit on </label><input type="text" class="date form-control input-sm" name="visitDate" id="visitDate"/><br/>
                            <button class="btn btn-primary" onclick="downloadReport('visit')">
                                <span class="glyphicon glyphicon-cloud-download"></span> Download visit report</button></div>
                        </fieldset>
                        <fieldset class="field">
                            <div class="form-group-sm">
                                <label>List patients who visited for routine refill on </label>
                                <input type="text" class="date form-control input-sm" name="visitDate" id="routineRefillDate"/><br/>
                                <button class="btn btn-primary" onclick="downloadReport('routineRefill')">
                                    <span class="glyphicon glyphicon-cloud-download"></span> Download routine refill report
                                </button>
                            </div>
                        </fieldset>
                </div>
                <div id="stockReports" style="display: none">
                    <div><h5>Commodity stock status report</h5></div>

                        <button class="btn btn-primary" onclick="downloadReport('soh')"><span class="glyphicon glyphicon-cloud-download"></span> Commodity stock on hand report </button><br/><br/>

                        <button class="btn btn-primary" onclick="downloadReport('expired')"><span class="glyphicon glyphicon-cloud-download"></span> List of expired commodities</button><br/><br/>

                        <button class="btn btn-primary" onclick="downloadReport('shortDated')"><span class="glyphicon glyphicon-cloud-download"></span> Short Dated stock 6 month to expiry</button>

                </div>
                <div id="receiveIssueReports" style="display: none">

                        <div><h5>Receive/Issue reports</h5></div>
                        <div style="margin-bottom: 5px;" tabIndex="1">
                            Start Date <input type="text" class="form-control input-sm" id="receivedDate1"/> End Date <input type="text" class="form-control input-sm" id="receivedDate2"/>
                        </div>
                    <div>

                        Select Supplier
                        <select id="supplierSelect" class="form-control input-sm">
                            <option value="-1">All</option>
                        </select>
                    </div>
                    <div style="margin-top: 5px;">
                        <button onclick="downloadReport('received')" class="btn btn-primary">
                            <span class="glyphicon glyphicon-cloud-download"></span> Commodities Received Report
                        </button>
                        <button onclick="downloadReport('issued')" class="btn btn-primary">
                            <span class="glyphicon glyphicon-cloud-download"></span> Commodities Issued Report
                        </button>
                    </div>
                </div>
                <div id="facilitpadherence" style="display: none">

                    <div><h5>Facility Patient Adherence Summary</h5></div>
                    <div style="margin-bottom: 5px;" tabIndex="1">
                        Start Date <input type="text" class="form-control input-sm" id="receivedDate1"/> End Date <input type="text" class="form-control input-sm" id="receivedDate2"/>
                    </div>

                    <div style="margin-top: 5px;">
                        <button onclick="showadherence()">
                            <span class="btn btn-primary"></span> View Report
                        </button>
                        <button  onclick="showDailyConsumptionDispensing()"> <span class="btn btn-primary"></span> Print</button>
                        <button onclick="downloadReport('received')" class="btn btn-primary">
                            <span class="glyphicon glyphicon-cloud-download"></span> PDF Download
                        </button>
                        <button onclick="downloadReport('issued')" class="btn btn-primary">
                            <span class="glyphicon glyphicon-cloud-download"></span> Excel Download
                        </button>
                    </div>
                </div>

                <div id="drugConsumption" style="display: none">
                    <div><h4>Commodity consumption report</h4></div>
                    <form class="form-horizontal" role="form">
                        <div class="form-group-sm" >
                            <label class="col-sm-3">Select Service</label>
                            <div class="col-sm-9">
                                <select id="drugServiceSelect" class="form-control input-sm" onchange="onDrugServiceSelect()">
                                    <option value="-1">Select service</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group-sm" >
                            <label class="col-sm-3">Select Commodity</label>
                            <div class="col-sm-9">
                                <select class="form-control input-sm" id="drugConsumptionSelect">
                                    <option value="-1">All</option>
                                </select></div>
                        </div>
                        <div style="margin-bottom: 5px;">
                            <label class="col-sm-3">Select Year</label>
                            <div class="col-sm-9">
                                <select id="drugConsumptionYearSelect" class="form-control input-sm">
                                    <option value="-1">Select year</option>
                                </select>
                            </div>
                        </div>
                    </form>
                    <div class="col-sm-offset-3 col-sm-9">
                        <button onclick="downloadReport('drugConsumption')" class="btn btn-primary">
                            <span class="glyphicon glyphicon-cloud-download"></span> Download Commodity Consumption report
                        </button>
                    </div>
                </div>


                <div id="patientServices" style="display: none">
                    <h4>Patient Services Report</h4>
                    <div tabIndex="1">
                        <label>Select Service</label>
                        <select id="serviceSelect" class="form-control input-sm">
                            <option value="-1">Select service</option>
                        </select>
                    </div>
                    <div style="margin-bottom: 5px;" >
                        <label>Select Year</label>
                        <select id="patientServiceYearSelect" class="form-control input-sm">
                        </select>
                    </div>
                    <div style="margin-bottom: 5px;" >
                        <label>Select Month</label>
                        <select id="patientServiceMonthSelect" class="form-control input-sm">
                            <option value="0">January</option>
                            <option value="1">February</option>
                            <option value="2">March</option>
                            <option value="3">April</option>
                            <option value="4">May</option>
                            <option value="5">June</option>
                            <option value="6">July</option>
                            <option value="7">August</option>
                            <option value="8">September</option>
                            <option value="9">October</option>
                            <option value="10">November</option>
                            <option value="11">December</option>
                        </select>
                    </div>

                    <div style="margin-top: 5px;">

                        <button onclick="downloadReport('patientsByRegimen')" class="btn btn-primary btn-sm">
                            <span class="glyphicon glyphicon-cloud-download"></span> Download Number of Active Receiving ARVs By Regimen Report
                        </button>

                        <button onclick="downloadReport('createCumNumberOfPatientsReport')" class="btn btn-primary btn-sm">
                            <span class="glyphicon glyphicon-cloud-download"></span> Cumulative Number of Patients To Date Report
                        </button>

                        <!--<button onclick="downloadReport('patientsByRegimenExcel')" class="btn btn-primary btn-sm">
                            <span class="glyphicon glyphicon-cloud-download"></span> Facility Monthly ART Patient Summary (F-MAPS)
                        </button> -->
                    </div>
                </div>
                <div id="monthlyReport" style="display: none">
                    <h4>Monthly Reports</h4>
                    <div>
                        Select Month
                        <select id="monthlyMonth" class="form-control input-sm">
                            <option value="0">January</option>
                            <option value="1">February</option>
                            <option value="2">March</option>
                            <option value="3">April</option>
                            <option value="4">May</option>
                            <option value="5">June</option>
                            <option value="6">July</option>
                            <option value="7">August</option>
                            <option value="8">September</option>
                            <option value="9">October</option>
                            <option value="10">November</option>
                            <option value="11">December</option>
                        </select>
                        Select Year

                            <select id="monthlyYear" class="form-control input-sm">
                                <option value="">Select year</option>
                            </select>

                    </div>

                    <div id="monthlyTitle" style="margin: 5px;">
                       <h5>Select the report to download</h5>
                    </div>
                    <div id="artMonthly" style="margin: 5px; ">
                        Facility Monthly Summary report for ART Commodities <button onclick="downloadReport('fcdrrsite')" class="btn btn-primary btn-sm">
                        <span class="glyphicon glyphicon-cloud-download"></span> PDF</button>
                        <button class="btn btn-primary btn-sm" onclick="downloadReport('fcdrrsite-excel')">
                            <span class="glyphicon glyphicon-cloud-download"></span> Excel
                        </button>
                        <button onclick="downloadDhisReport('fcdrrsite')" class="btn btn-primary btn-sm">
                            <span class="glyphicon glyphicon-cloud-download"></span> DHIS </button>
                    </div>
                    <div id="fpMonthly" style="margin: 5px; ">
                        Facility Monthly Summary report for FP Commodities <button onclick="downloadReport('fpcdrrsite')" class="btn btn-primary btn-sm">
                        <span class="glyphicon glyphicon-cloud-download"></span> PDF</button>
                        <button onclick="downloadDhisReport('fpcdrrsite')" class="btn btn-primary btn-sm">
                            <span class="glyphicon glyphicon-cloud-download"></span> DHIS </button>
                    </div>
                    <div id="malariaMonthly" style="margin: 5px; ">
                        Facility Monthly Summary report for Malaria Commodities <button onclick="downloadReport('malariacdrrsite')" class="btn btn-primary btn-sm">
                        <span class="glyphicon glyphicon-cloud-download"></span> PDF</button>
                        <button onclick="downloadDhisReport('malariacdrrsite')" class="btn btn-primary btn-sm">
                            <span class="glyphicon glyphicon-cloud-download"></span> DHIS </button>
                    </div>
                    <div id="nutritionMonthly" style="margin: 5px; ">
                        Facility Monthly Summary report for Nutrition Commodities <button onclick="downloadReport('nutritioncdrrsite')" class="btn btn-primary btn-sm">
                        <span class="glyphicon glyphicon-cloud-download"></span> PDF</button>
                        <button onclick="downloadDhisReport('nutritioncdrrsite')" class="btn btn-primary btn-sm">
                            <span class="glyphicon glyphicon-cloud-download"></span> DHIS </button>
                    </div>
                    <div id="labMonthly" style="margin: 5px; ">
                        Facility Monthly Summary report for Laboratory Commodities <button onclick="downloadReport('labcdrrsite')" class="btn btn-primary btn-sm">
                        <span class="glyphicon glyphicon-cloud-download"></span> PDF</button>
                        <button onclick="downloadDhisReport('labcdrrsite')" class="btn btn-primary btn-sm">
                            <span class="glyphicon glyphicon-cloud-download"></span> DHIS </button>
                    </div>

                    <!--<div id="emmsMonthly" style="margin: 5px; ">
                        Facility Monthly Summary report for Essential Commodities <button onclick="downloadReport('emmscdrrsite')" class="btn btn-primary btn-sm">
                        <span class="glyphicon glyphicon-cloud-download"></span> PDF</button>
                        <button onclick="downloadDhisReport('emmscdrrsite')" class="btn btn-primary btn-sm">
                            <span class="glyphicon glyphicon-cloud-download"></span> DHIS </button>
                    </div> -->

                    <div id="fmapMonthly" style="margin: 5px; ">
                        Facility Monthly ART Patient Summary (F-MAPS)<button onclick="downloadReport('patientsByRegimenPDF')" class="btn btn-primary btn-sm">
                            <span class="glyphicon glyphicon-cloud-download"></span> PDF</button>
                         <button onclick="downloadReport('patientsByRegimenExcel')" class="btn btn-primary btn-sm">
                        <span class="glyphicon glyphicon-cloud-download"></span>Excel</button>
                        <button onclick="" class="btn btn-primary btn-sm">
                            <span class="glyphicon glyphicon-cloud-download"></span> DHIS </button>
                    </div>


                </div>
                <div id="patientListingByStatusReport" style="display: none">
                    <h4>Patient Status Report</h4>
                    <label>Select Status</label>
                    <select id="patientStatusSelect" class="form-control input-sm">
                        <option value="-1">Select Status</option>

                    </select>
                    <br/>
                    <button onclick="downloadReport('patientListingByStatusReport')" class="btn btn-primary btn-sm">
                        <span class="glyphicon glyphicon-cloud-download"></span> Download Report</button>
                </div>
                <div id="listingRegimenMissedAppointments" style="display: none">
                    <div style="margin: 5px;" tabIndex="1"><strong>Patients listing regimens and missing appointments</strong></div>
                    <div style="margin-bottom: 5px;">
                        Start Date <input type="text" id="missedDate1" class="form-control input-sm"/> End Date <input type="text" id="missedDate2" class="input-sm form-control"/>
                    </div>
                    <fieldset>
                        <div style="margin: 5px;"><strong>Missed Appointments Report</strong></div>
                        <div style="margin: 5px;">Type of Service
                            <select id="missedAppointmentServiceSelect" class="form-control input-sm">
                                <option value="-1">All</option>
                            </select>
                        </div>
                        <div style="margin: 5px;"><button onclick="downloadReport('missedAppointment')" class="btn btn-primary btn-sm">
                            <span class="glyphicon glyphicon-cloud-download"></span> Patients who Missed Appointments report</button></div>
                        <div style="margin: 5px;">
                            <strong>Patients who changed regimen Report</strong>
                        </div>
                        <div><button onclick="downloadReport('changedRegimen')" class="btn btn-primary btn-sm">
                            <span class="glyphicon glyphicon-cloud-download"></span> Download patients who changed Regimen</button></div>
                    </fieldset>
                    <fieldset>
                        <div style="margin: 5px;">
                            <strong>Patients Starting By Regimen Report</strong>
                        </div>
                        <button onclick="downloadReport('startingByRegimen')" class="btn btn-primary btn-sm">
                            <span class="glyphicon glyphicon-cloud-download"></span> Download patients starting by Regimen</button>
                    </fieldset>
                </div>
                <div id="drugUsedReport" style="display: none">
                    <div><strong>Patient listing by commodity used</strong></div><br/>
                    <div style="margin-bottom: 5px;" tabIndex="1">
                        Select Commodity <select id="drugUsedSelect" class="form-control input-sm"><option value="-1">Select one</option>
                        </select>
                    </div>
                    <button onclick="downloadReport('drugUsedReport')" class="btn btn-primary">
                        <span class="glyphicon glyphicon-cloud-download"></span> Commodity report
                    </button>
                </div>
                <div id="enrollmentReport" style="display: none">
                    <div style="margin: 5px;" tabIndex="1"><strong>Patients Enrollment</strong></div>

                    <fieldset class="field">
                        <div style="margin-bottom: 5px;">
                            Start Date <input type="text" id="enrollDate1"/> End Date <input type="text" id="enrollDate2"/>
                        </div>
                        <div style="margin: 5px;">Type of Service
                            <select id="enrollmentServiceSelect" class="form-control input-sm">
                                <option value="-1">Select one</option>
                            </select>
                        </div>

                            <div style="margin: 5px;"><button onclick="downloadReport('patientsEnrolled')" class="btn btn-primary btn-sm">
                                <span class="glyphicon glyphicon-cloud-download"></span> Cumulative Number of Patients Enrolled by type of service</button></div>

                            <div style="margin: 5px;"><button onclick="downloadReport('patientsStarted')" class="btn btn-primary btn-sm">
                                <span class="glyphicon glyphicon-cloud-download"></span> Download Number of Patients Started on ART Report</button></div>

                    </fieldset>

                        <fieldset class="field">
                            Graph of patients enrolled :
                            <div style="margin: 5px;">Year
                                <select id="enrollmentYearSelect" class="form-control input-sm">
                                    <option value="-1">Select one</option>
                                </select>
                            </div>
                            <div style="margin: 5px;"><button onclick="downloadReport('patientsGraphEnrolled')" class="btn btn-primary">
                                <span class="glyphicon glyphicon-cloud-download"></span> Download Graph of Patients Enrolled on ART</button></div>
                        </fieldset>

                </div>
                <div id="bmiReport" style="display: none">
                    <div style="margin: 5px;"><strong>BMI Report</strong></div>
                    <div style="margin: 5px;">Type of Service
                        <select id="bmiServiceSelect" class="form-control input-sm">
                            <option value="-1">Select one</option>
                        </select>
                    </div>
                    <div style="margin: 5px;">Adult/Child
                        <select id="bmiAdult" class="form-control input-sm">
                            <option value="-1">Select one</option>
                            <option value="true">Adult</option>
                            <option value="false">Child</option>
                        </select>
                    </div>
                    <div style="margin: 5px;"><button onclick="downloadReport('bmiReport')" class="btn btn-primary">
                        <span class="glyphicon glyphicon-cloud-download"></span> Download BMI Report
                    </button></div>

                </div>

                <div id="ewiReports" style="display: none">
                    <div style="margin: 5px;" tabIndex="1"><strong>Early Warning Indicators</strong></div>
                    <div style="margin-bottom: 5px;">
                        Start Date <input type="text" id="ewiDate1"/> End Date <input type="text" id="ewiDate2"/>
                    </div>

                    <button onclick="downloadReport('ewi1')" class="btn btn-primary btn-sm">
                        <span class="glyphicon glyphicon-cloud-download"></span> EWI 1</button> |

                    <button onclick="downloadReport('ewi2')" class="btn btn-primary btn-sm">
                        <span class="glyphicon glyphicon-cloud-download"></span> EWI 2</button> |

                    <button onclick="downloadReport('ewi3')" class="btn btn-primary btn-sm">
                        <span class="glyphicon glyphicon-cloud-download"></span> EWI 3 </button> |

                    <button onclick="downloadReport('ewi4')" class="btn btn-primary btn-sm">
                        <span class="glyphicon glyphicon-cloud-download"></span> EWI 4</button>

                </div>
            </div>
            <div class="modal-footer">
                <%--<button onclick="addTransactionRow()" class="btn btn-default">add commodity </button>--%>
                <%--<button onclick="saveIssueReceive(true)" class="btn btn-default">Save </button>--%>
                <button  type="button" class="btn btn-default" data-dismiss="modal">
                    Close
                </button>
            </div>
        </div>
    </div>
</div>



<div id="dailyConsumptionDispensing">
    <div id="printDailyComDisp">
        <div style="float: left; margin-bottom: 5px;" tabIndex="1">
            Start Date <input type="text" id="date1"/> End Date <input type="text" id="date2"/>
            Area of Service
            <select id="AreaService" >
                <option value="">Select Service Area</option>
                <option value="Family Planning Clinic">Family Planning Clinic</option>
                <option value="Diabetic Clinic">Diabetic Clinic</option>
                <option value="CCC Pharmacy">CCC Pharmacy</option>


            </select> <button onclick="loadDailyConsumptionDispensing()">List Consumption</button>
        </div>
        <table id="dailyConsumptionDispensingTable" style="width: 100%; overflow: scroll" class="formTable table-condensed" >
            <tr>
                <td>Drug name</td>
                <td>Consumed in tabs</td>
                <td>cost</td>
                <td>Cost</td>
            </tr>
        </table>
        Total Cost <label id="totalCost"></label>
    </div>
    <button id="print" onclick="PrintDailyConsumptionDispension()">Print</button>
</div>
    <div id="adherencemonitoring1">
        <div id="printadherencemonitoring1">
            <div style="float: left; margin-bottom: 5px;" tabIndex="1">
                Start Date <input type="text" id="date1"/> End Date <input type="text" id="date2"/><br><br>


                <table id="adherencemonitoringtable1" style="width: 100%; overflow: scroll;align:center" class="formTable table-condensed" >
                    <tr>
                        <th rowspan="2">Parameters</th>
                        <th colspan="7" id="adheheight1">Number & Percentage of Patient</th>

                    </tr>
                    <tr>

                        <th>PM</th>
                        <th>PF</th>
                        <th>PT</th>
                        <th>AM</th>
                        <th>AF</th>
                        <th>AT</th>
                        <th>GT</th>
                    </tr>
                    <tr>
                        <td>Number of routine refills During<br>the reporting period (A)</td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>Number of patient whom<br>pill count was done (B)</td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>% of patients for whom  pill count<br>was done (c)=(B)/(A)X 100%</td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>Number of patients who had an<br>adherence score calculated (D)</td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>% of patients who had an<br>adherence score calculated (E)=(D)/(A)X100%</td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                    </tr>
                </table>

            </div>
            <button id="printout" onclick="PrintDailyConsumptionDispension()">Print</button>
        </div>
    <div id="adherence">
        <div id="adherencemonitoring">
            <div id="printadherencemonitoring">



                <table id="adherencemonitoringtable" style="width: 100%; overflow: scroll;align:center" class="formTable table-condensed" >
                    <tr>
                        <th rowspan="2">% Adherence</th>
                        <th colspan="7" id="adheheight" align="center">Number of Patients</th>

                    </tr>
                    <tr>

                        <th>PM</th>
                        <th>PF</th>
                        <th>PT</th>
                        <th>AM</th>
                        <th>AF</th>
                        <th>AT</th>
                        <th>GT</th>
                    </tr>
                    <tr>
                        <td>95% -100%</td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>75% -<95%</td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>0% -<75%</td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td><100%</td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>Not calculated</td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>TOTALS</td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                    </tr>
                </table>

            </div>

        </div>



    </div>


    <div id="facilityappointment">
        <div id="adherencemonitoring">
            <div id="printadherencemonitoring">



                <table id="adherencemonitoringtable" style="width: 100%; overflow: scroll;align:center" class="formTable table-condensed" >

                    <tr>
                        <th>Number of Patients</th>
                        <th>PM</th>
                        <th>PF</th>
                        <th>PT</th>
                        <th>AM</th>
                        <th>AF</th>
                        <th>AT</th>
                        <th>GT</th>
                    </tr>
                    <tr>
                        <td>On time (Dl<=0)</td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>< 3 days late</td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>3 -<10 days late</td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td><10 - <30 days late</td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>< 30 days late/td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>TOTALS</td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                    </tr>
                </table>

            </div>

        </div>



    </div>






<script type="text/javascript" src="js/jquery/jquery-2.1.0.min.js"></script>
<script type="text/javascript" src="js/migrate.js"></script>
<script type="text/javascript" src="js/jquery/jquery-ui-1.10.4.min.js"></script>
<script type="text/javascript" src="js/dialogs.js?v=<%= System.currentTimeMillis() %>"></script>
<script type="text/javascript" src="js/functions.js?v=<%= System.currentTimeMillis() %>"></script>
<script type="text/javascript" src="js/printThis.js?v=<%= System.currentTimeMillis() %>"></script>
<script src="js/bootstrap.min.js"></script>
<script language="JavaScript">
    $(document).ready(function() {
        initReportsReferences();
        initReportsPage();



            // Since confModal is essentially a nested modal it's enforceFocus method
            // must be no-op'd or the following error results
            // "Uncaught RangeError: Maximum call stack size exceeded"
            // But then when the nested modal is hidden we reset modal.enforceFocus
        var enforceModalFocusFn = $.fn.modal.Constructor.prototype.enforceFocus;

        $.fn.modal.Constructor.prototype.enforceFocus = function() {};

        $confModal.on('hidden', function() {
            $.fn.modal.Constructor.prototype.enforceFocus = enforceModalFocusFn;
        });

        $confModal.modal({ backdrop : false });

    });

</script>
    </div>
        </div>

<jsp:include page="footer.jsp"/>

</body>
</html>
