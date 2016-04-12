<%--
  Created by IntelliJ IDEA.
  User: kenny
  Date: 3/20/14
  Time: 8:08 AM
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Home | EDITT </title>
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
    %>
    <meta http-equiv="Cache-Control" content="max-age=0" />
    <meta http-equiv="Cache-Control" content="no-cache" />
    <meta http-equiv="Cache-Control" content="no-store" />
    <meta http-equiv="Cache-Control" content="must-revalidate" />

    <meta http-equiv="Expires" content="0" />
    <meta http-equiv="Pragma" content="no-cache" />

    <link type="text/css" rel="stylesheet" href="css/jquery-ui-1.10.4.css"/>
    <link type="text/css" rel="stylesheet" href="css/jquery.appendGrid-1.3.4.min.css"/>
    <link href="css/themes/metro/blue/jtable.min.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" type="text/css" href="css/token-input.css" />
    <link rel="stylesheet" type="text/css" href="css/token-input-facebook.css" />
    <link type="text/css" rel="stylesheet" href="css/bootstrap.min.css"/>
    <link type="text/css" rel="stylesheet" href="css/bootstrap-theme.min.css"/>
    <link type="text/css" rel="stylesheet" href="css/style.css?v=<%= System.getProperty("version") %>"/>

    <link rel="stylesheet" href="css/flexselect.css" type="text/css" media="screen" />
    <%
        Object logged = session.getAttribute("loggedin");
        if(logged == null) {
            response.sendRedirect("index.jsp?action=login");
        }
    %>
    <script language="JavaScript">
        window.accountID = <%= session.getAttribute("account") %>
        window.user_id = <%= session.getAttribute("userId") %>
        window.Is_bulkstore = <%=session.getAttribute("Is_bulkstore")%>

        var privileges = {
            mod_servicegeneralNutrition : <%=session.getAttribute("mod_servicegeneralNutrition")%>,
            mod_servicegeneralLab : <%=session.getAttribute("mod_servicegeneralLab")%>,
            mod_servicegeneralPharmacy : <%=session.getAttribute("mod_servicegeneralPharmacy")%>,
            mod_servicechronicART : <%=session.getAttribute("mod_servicechronicART")%>,
            mod_servicechronicFP : <%=session.getAttribute("mod_servicechronicFP")%>,
            mod_servicechronicDiabetic : <%=session.getAttribute("mod_servicechronicDiabetic")%>
        }

    </script>

</head>
<body>
<jsp:include page="header.jsp"/>
<div class="content">

    <% if(session.getAttribute("mod_dispense") != null) { %>
    <div class="searchLabel" id="serviceSelect">
        <form class="form-inline" role="form">
            <div class="form-group" style="padding-right: 20px;">
                <label for="serviceTypeSelect">Patient Type</label>
                <select name="" id="serviceTypeSelect" class="form-control" onchange="return onServiceSelected()">
                    <option value="">Patient Type</option>
                    <% if(session.getAttribute("mod_servicegeneral") != null) { %>
                    <option value="1">General</option>
                    <% }
                        if(session.getAttribute("mod_servicechronic") != null) {
                    %>
                    <option value="2">Chronic</option>
                    <% } %>
                </select>
            </div>
            <div class="form-group">
                <%--<% if( window.Is_bulkstore != null) { %>--%>
                <label for="serviceTypeSelect">Select Service</label>
                <select name="" id="clinicTypeSelect" class="form-control" onchange="return onClinicSelected()">
                    <option value="">Select Service</option>
                </select>
            </div>
        </form>
    </div>
    <div id="search" hidden>
        <div class="searchLabel" style="width:150px"><label>Search patients</label></div>
        <div class="search">
             <input type="text" class="autocomplete" name="" id="autocomplete" placeholder="type the patients name"/>
             <div id="selection"style="margin-top: 10px;"></div>
            <%--<% } %>--%>

                <div style="margin-top: 10px;"> <button onclick="showNewPatientDialog()" class="btn btn-sm btn-default"><span class="glyphicon glyphicon-plus"></span> add new patient</button></div>

        </div>
    </div>
    <div id="labItems" hidden>
        <div class="search">
            <div style="margin-top: 10px;"><button onclick='return showIssueReceiveDialog(true)' class="btn btn-sm btn-default">Receive/Issue Lab Commodities</button></div>
        </div>
    </div>
    <% } %>
    <!--<div class="search">
        <input type="text" name="search" id="search_box" placeholder="type the patients name or number" value=""/>
    </div> -->
    <div class="main_menu">
        <div class="menuItem">
            <a onclick="window.location='transactions.jsp'"><img src="images/transactions.jpg"/>
            <div>Transactions</div></a>
        </div>

        <%

         if(session.getAttribute("mod_reports") != null) {  %>
            <div class="menuItem">
                <a onclick="window.location='reports.jsp'"><img src="images/reports.jpg"/>
                <div>Reports</div></a>
            </div>
        <%
            }
            if(session.getAttribute("mod_settings") != null) {  %>
            <div class="menuItem">
                <a onclick="window.location='settings.jsp'"><img src="images/settings.jpg"/>
                <div>Settings</div></a>
            </div>
        <% } %>
        <div class="menuItem">
            <a onclick="window.alert('Help content coming soon :-)')"><img src="images/help.png"/>
            <div>Help</div></a>
        </div>
    </div>

    <div class="main_menu">
        <div class="frontReport ewi1" title="On time pill pick-up EWI">
            <div id="ewi1" style="margin: 5px; color: #000000; text-align: center">ART On time pill pick-up</div>
        </div>

        <div class="frontReport ewi2" title="Retention in care EWI">
              <a><div id="ewi2" style="margin: 5px; color: #000000; text-align: center">ART Retention in care</div></a>
        </div>

        <div class="frontReport ewi3" title="Retention in care EWI">
                <a><div id="ewi3" style="margin: 5px; color: #000000; text-align: center">Pharmacy no stock-outs</div></a>
        </div>

        <div class="frontReport ewi4">
            <a><div id="ewi4" style="margin: 5px; color: #000000; text-align: center">ART Dispensing practices</div></a>
        </div>
    </div>
</div>
<div id="newPatientDialog" class="modal fade full-modal" data-backdrop="static">
    <div class="modal-dialog" style="max-width: 1200px">
        <div class="modal-content">
            <div class="modal-body" style="background-color: lightblue">
                <table class="dispense">
                    <tr>
                        <td><h4>Patient Identification and Demographics</h4></td>
                        <td class="hivPatientsInfo"><h4>Program information</h4></td>
                    </tr>
                    <tr><td colspan="2">Fields marked <span class="required">*</span> are required.</td></tr>
                    <tr>
                        <td>
                            <div class="form-group-sm">
                                <label class="col-sm-6"><span class="required">*</span>Surname</label>
                                <div class="col-sm-6">
                                    <input type="hidden" id="patientId" value="-1"/>
                                    <input type="hidden" id="personAddressId" value="-1"/>
                                    <input type="hidden" id="personId" value="-1"/>
                                    <input type="hidden" id="patientIdentifierId" value="-1"/>
                                    <input type="hidden" id="personUpdate" value="false"/>
                                    <input type="text" name="surname" id="surname" class="form-control name"/>
                                </div>
                            </div>
                        </td>
                        <td>
                            <div class="form-group-sm">
                                <label class="col-sm-6">Patient phone contact</label>
                                <div class="col-sm-6">
                                    <input type="text" name="phone" id="phone" class="form-control"/>
                                </div>
                            </div>
                        </td>

                    </tr>
                    <tr>
                        <td>
                            <div class="form-group-sm">
                                <label class="col-sm-6"><span class="required">*</span>First name</label>
                                <div class="col-sm-6">
                                    <input type="text" name="firstName" id="firstName" class="name form-control"/>
                                </div>
                            </div>
                        </td>
                        <td>
                            <div class="form-group-sm">
                                <label class="col-sm-6">Email address</label>
                                <div class="col-sm-6">
                                    <input type="text" name="email" id="email" class="form-control"/>
                                </div>
                            </div>
                        </td>

                    </tr>
                    <tr>
                        <td>
                            <div class="form-group-sm">
                                <label class="col-sm-6">Other names</label>
                                <div class="col-sm-6">
                                    <input type="text" name="otherNames" id="otherNames" class="name form-control"/>
                                </div>
                            </div>
                        </td>
                        <td>
                            <div class="form-group-sm">
                                <label class="col-sm-6">Patients postal address</label>
                                <div class="col-sm-6">
                                    <textarea cols="30" rows="2" name="postalAddress" id="postalAddress" class="form-control"></textarea>
                                </div>
                            </div>
                        </td>

                    </tr>
                    <tr>
                        <td>
                            <div class="form-group-sm">
                                <label class="col-sm-6"><span class="required">*</span>Date of birth</label>
                                <div class="col-sm-6">
                                    <input type="text" name="dob" id="dob" class="form-control"/>
                                </div>
                            </div>
                            <div class="form-group-sm">
                                <label class="col-sm-6">Age</label>
                                <div class="col-sm-6">
                                    <input type="text" name="age" id="age" class="form-control number" onkeyup="calculateDateOfBirthColo()"/>
                                </div>
                            </div>
                        </td>
                        <td>
                            <div class="form-group-sm">
                                <label class="col-sm-6">Patients physical address</label>
                                <div class="col-sm-6">
                                    <textarea cols="30" rows="2" name="physicalAddress" id="physicalAddress" class="form-control"></textarea>
                                </div>
                            </div>
                        </td>

                    </tr>
                    <tr>
                        <td>
                            <div class="form-group-sm">
                                <label class="col-sm-6">Birth county</label>
                                <div class="col-sm-6">
                                    <select id="birthDistrict" name="birthDistrict" class="form-control">
                                        <option value=""></option>
                                    </select>
                                </div>
                            </div>
                        </td>
                        <td>
                            <div class="form-group-sm">
                                <label class="col-sm-6">Alternate contacts</label>
                                <div class="col-sm-6">
                                    <input type="text" name="alternativeAddress" id="alternativeAddress" class="form-control"/>
                                </div>
                            </div>
                        </td>

                    </tr>
                    <tr>
                        <td>
                            <div class="form-group-sm">
                                <label class="col-sm-6"><span class="required">*</span>Gender</label>
                                <div class="col-sm-6">
                                    <select id="sex" name="sex" class="form-control">
                                        <option>Select gender</option>
                                        <option value="Male">Male</option>
                                        <option value="Female">Female</option>
                                    </select>
                                </div>
                            </div>
                        </td>
                        <td>
                            <div class="form-group-sm">
                                <label class="col-sm-6" for="identifierType"><span class="required">*</span>Identifier Type
                                </label>
                                <div class="col-sm-6">
                                    <select name="identifierType" id="identifierType" class="form-control">
                                        <option value="">Select one</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group-sm">
                                <label class="col-sm-6"><span class="required">*</span>Medical record number</label>
                                <div class="col-sm-6">
                                    <input type="text" name="medicalRecordNumber" id="medicalRecordNumber" class="form-control"/>
                                </div>
                            </div>
                        </td>

                    </tr>
                    <tr class="">
                        <td>
                            <div class="form-group-sm">
                                <label class="col-sm-6">Patient Status</label>
                                <div class="col-sm-6">
                                    <select name="patientStatusId" id="regPatientStatusId" class="form-controlf">
                                        <option value="">Select one</option>
                                    </select>
                                </div>
                            </div>
                        </td>
                        <td>
                            <div class="hivPatientsInfo">
                                <label class="col-sm-6">Starting Regimen</label>
                                <div class="col-sm-6">
                                    <select name="startRegimenId" class="form-control" id="startRegimenId" >
                                        <option value=""></option>
                                    </select>
                                </div>
                            </div>
                        </td>

                    </tr>
                    <tr class="hivPatientsInfo">
                        <td>
                            <div class="form-group-sm">
                                <label class="col-sm-6"><span class="required">*</span>Date enrolled</label>
                                <div class="col-sm-6">
                                    <input type="text" name="dateEnrolled" id="dateEnrolled" class="form-control"/>
                                </div>
                            </div>
                        </td>
                        <td>
                            <div class="form-group-sm">
                                <label class="col-sm-6">Source of patient</label>
                                <div class="col-sm-6">
                                    <select name="patientSource" id="patientSource" class="form-control">
                                        <option value="">Select one</option>
                                    </select>
                                </div>
                            </div>
                        </td>

                    </tr>

                    <tr class="hivPatientsInfo">
                        <td>
                            <div class="form-group-sm">
                                <label class="col-sm-6">Drug allergies</label>
                                <div class="col-sm-6">
                                    <textarea cols="30" rows="2" name="drugAllergies" id="drugAllergies" class="form-control"></textarea>
                                </div>
                            </div>
                        </td>
                        <td>
                            <div class="form-group-sm">
                                <label class="col-sm-6">List other illness</label>
                                <div class="col-sm-6">
                                    <textarea cols="30" rows="2" name="chronicIllness" id="chronicIllness" class="form-control"></textarea>
                                </div>
                            </div>
                        </td>
                    </tr>
                    <tr class="hivPatientsInfo">
                        <td>
                            <div class="form-group-sm">
                                <label class="col-sm-6">Does the patient smoke</label>
                                <div class="col-sm-6 radio">
                                    <label class="radio-inline" style="width: auto">
                                        <input type="radio" name="smoker" value="1"/> Yes
                                    </label>
                                    <label class="radio-inline" style="width: auto"><input type="radio" name="smoker" value="0"/> No</label>
                                </div>
                            </div>
                        </td>
                        <td>
                            <div class="form-group-sm">
                                <label class="col-sm-6">Does the patient drink alcohol</label>
                                <div class="col-sm-6 radio">
                                    <label class="radio-inline" style="width: auto">
                                        <input type="radio" name="drinker" value="1"/> Yes
                                    </label>
                                    <label class="radio-inline" style="width: auto"><input type="radio" name="drinker" value="0"/> No</label>
                                </div>
                            </div>
                        </td>
                    </tr>

                </table>

                <table class="formTable" id="patientServiceTypeTable">
                    <tr id="patientServiceTypeTable-0">
                        <th class="counter"></th>
                        <th>Service Type</th>
                        <th>Start Date</th>
                        <th>End Date</th>
                        <th></th>
                    </tr>
                </table><br/>
                <p>
                    <button onclick="addPatientServiceRow()" class="btn btn-sm btn-default">add service </button>
                </p>
            </div>
            <div class="modal-footer">
                <button onclick="savePatient()" class="btn btn-primary">Save</button>
                <button onclick="savePatient(true);"  class="btn btn-primary">Save and dispense</button>
                <button class="btn btn-primary">Register Patient KenyaEMR</button>
                <button  type="button" class="btn btn-default" data-dismiss="modal">
                    Close
                </button>
            </div>
        </div>
    </div>
</div>


<div id='visitDialog' class="modal fade full-modal" data-backdrop="static" >
    <div class="modal-dialog" style="max-width: 1200px">
        <div class="modal-content">
            <div class="modal-body visitDialog" style="background-color: lightpink;">
                <table class="dispense">
                    <tr>
                        <td>
                            <div class="form-group-sm">
                                <div class="col-sm-6"> <label class="well-sm pull-right">Patient name</label></div>
                                <div class="col-sm-5">
                                    <input type="text" name="patientName" class="form-control" readonly id="patientName"/>


                                    <input type="hidden" id="visitPersonId" value="-1"/><input type="hidden" id="visitPatientId" value="-1"/>
                                </div>
                                <div class="col-sm-1">
                                     <button class="btn btn-primary" onclick="editPatientInfo()"><span class="glyphicon glyphicon-edit"></span> Edit</button>
                                </div>

                                <%--<div class="form-group-sm">--%>
                                    <%--<div class="col-sm-6"></div>--%>
                                    <%--<div class="col-sm-6"> <label class="well-sm">Max Patients: </label><label id="maxPatientsId"></label></div>--%>
                                <%--</div>--%>
                                <%--<div class="form-group-sm">--%>
                                    <%--<div class="col-sm-6"></div>--%>
                                    <%--<div class="col-sm-6"> <label class="well-sm">Expected: </label><label id="expectedPatientsId"></label></div>--%>
                                <%--</div>--%>
                                <%--<div class="form-group-sm">--%>
                                    <%--<div class="col-sm-6"></div>--%>
                                    <%--<div class="col-sm-6"> <label class="well-sm">Patients Seen: </label><label id="seenPatients"></label></div>--%>
                                <%--</div>--%>

                            </div>
                            <div class="form-group-sm">
                                <div class="col-sm-6"></div>
                                <div class="col-sm-6"> <label class="well-sm">Age</label><label id="ageLabel"></label></div>
                            </div>

                            <div class="form-group-sm">
                                <div class="col-sm-6"></div>
                                <div class="col-sm-6"><label id="lastVisitDate" style="color:#0b67cd"></label></div>
                            </div>
                            <div class="form-group-sm">
                                <div class="col-sm-6"></div>
                                <div class="col-sm-6"><label id="lastVisitDate2" style="color:#8f0000"></label></div>
                            </div>
                        </td>
                        <td>
                            <div class="form-group-sm">
                                <div class="col-sm-10 radio-inline" style="color:#9d0d0d; padding-left: 24%;"> <label>Max Patients:&nbsp&nbsp </label><label id="maxPatientsId" style="color: #0b67cd;"></label><label>&nbsp&nbsp Expected : &nbsp</label><label id="expectedPatientsId" style="color: #0b67cd;"></label></label><label>&nbsp&nbsp Seen :&nbsp&nbsp </label><label id="seenPatients" style="color: #0b67cd;"></label></div>

                                <div class="col-sm-6"><label class="well-sm pull-right">Is patient TB positive</label></div>
                                <div class="col-sm-6 radio">
                                    <label class="radio-inline" style="width: auto">
                                        <input type="radio" name="tbConfirmed"/> Yes
                                    </label>
                                    <label class="radio-inline" style="width: auto"><input type="radio" name="tbConfirmed"/> No</label>
                                </div>
                            </div>
                        </td>
                        <tr>
                    </tr>
                    </tr>
                    <tr>
                        <td>
                            <div class="form-group-sm">
                                <div class="col-sm-6"><label class="well-sm pull-right">Visit Date</label></div>
                                <div class="col-sm-6">
                                    <input type="text" name="startDate" id="startDate" class="form-control"/>
                                </div>
                            </div>
                        </td>
                        <td class="chronicPatientInfo">
                            <div class="form-group-sm">
                                <div class="col-sm-6"><label class="well-sm pull-right">Type of visit</label></div>
                                <div class="col-sm-6">
                                    <select name="visitTypeId" id="visitTypeId" class="form-control"  onchange="Routinerefill(this);">
                                        <option value="">Select one</option>
                                    </select>
                                </div>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div class="form-group-sm">
                                <div class="col-sm-6"><label class="well-sm pull-right">Weight(Kgs)</label></div>
                                <div class="col-sm-6">
                                        <input type="text" name="weight" id="weight" onkeyup="calculateBSA()" class="form-control number"/>
                                        <!--<span class="input-group-addon">Kgs</span>-->
                                </div>
                            </div>
                        </td>
                        <td class="chronicPatientInfo">
                            <div class="form-group-sm">
                                <div class="col-sm-6"><label class="well-sm pull-right">Days to next clinic</label></div>
                                <div class="col-sm-6">
                                    <input type="text" name="nextAppointment" id="daysToNextAppointment" class="form-control" onkeyup="calculateDaysToNextApp()"/>
                                </div>
                            </div>
                        </td>
                    </tr>


                    <tr class="chronicPatientInfok">
                        <td>
                            <div class="form-group-sm">
                                <div class="col-sm-6"><label class="well-sm pull-right">Height (cm)</label></div>
                                <div class="col-sm-6 ">
                                        <input type="text" name="height" id="height" onkeyup="calculateBSA()" class="form-control number"/>
                                        <%--<div class="input-group-addon">cm</div>--%>
                                </div>
                            </div>
                        </td>
                        <td>
                            <div class="form-group-sm">
                                <div class="col-sm-6"><label class="well-sm pull-right">Date of next appointment</label></div>
                                <div class="col-sm-6">
                                    <input type="text" name="nextAppointment" id="nextAppointmentDate" onchange="calculateDays()" class="form-control number"/>
                                </div>
                                <div class="col-sm-6"><label class="well-sm pull-right"></label></div>
                            </div>
                            <div class="form-group-sm">
                                <div class="col-sm-6"><label class="well-sm pull-right">Patients booked on date</label></div>
                                <div class="col-sm-6">
                                    <input type="text" name="patientBooked" id="patientBooked" disabled class="form-control"/>
                                </div>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div class="form-group-sm" id="b">
                                <div class="col-sm-6"><label class="well-sm pull-right">Babies</label></div>
                                <div class="col-sm-6 ">
                                    <input type="text" name="babies" id="babies" class="form-control"/>
                                </div>
                            </div>
                        </td>
                    </tr>
                    <tr class="chronicPatientInfo">
                        <td>
                            <div class="form-group-sm">
                                <div class="col-sm-6"><label class="well-sm pull-right">Body surface area (msq) </label></div>
                                <div class="col-sm-6">
                                    <input type="text" name="bodySurfaceArea" id="bodySurfaceArea" readonly class="form-control"/>
                                </div>
                            </div>
                        </td>
                        <td>
                            <div class="form-group-sm nonFP">
                                <div class="col-sm-6"><label class="well-sm pull-right">Last regimen dispensed</label></div>
                                <div class="col-sm-6">
                                    <input name="regimenId" class="form-control" id="currentRegimen"  readonly/>
                                    <input id="currentRegimenId" type="hidden"/>
                                </div>
                            </div>
                        </td>
                    </tr>
                    <tr class="chronicPatientInfo">
                        <td>
                            <div class="form-group-sm">
                                <div class="col-sm-6"><label class="well-sm pull-right">List other drugs patient is taking </label></div>
                                <div class="col-sm-6">
                                    <textarea rows="3" name="otherDrugs" id="otherDrugs" class="form-control"></textarea>
                                </div>
                            </div>
                        </td>
                        <td>
                            <div class="form-group-sm nonFP">
                                <div class="col-sm-6"><label class="well-sm pull-right">Current Regimen</label></div>
                                <div class="col-sm-6">
                                    <select name="regimenId" class="form-control" id="regimenId" onchange="return onRegimenSelected()">
                                        <option value=""></option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group-sm FP">
                                <div class="col-sm-6"><label class="well-sm pull-right">Family Planning method</label></div>
                                <div class="col-sm-6">
                                    <select name="familyPlanningMethodId" id="familyPlanningMethodId" class="form-control">
                                        <option value=""></option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group-sm diabetic">
                                <div class="col-sm-6"><label class="well-sm pull-right">Type of insulin</label></div>
                                <div class="col-sm-6">
                                    <select name="Insulin" id="Insulin" class="form-control">
                                        <option value=""></option>
                                    </select>
                                </div>
                            </div>
                        </td>
                    </tr>
                    <tr class="chronicPatientInfo">
                        <td>
                            <div class="form-group-sm nonFP">
                                <div class="col-sm-6"><label class="well-sm pull-right">Adherence </label></div>
                                <div class="col-sm-6">
                                    <input type="text" name="adherence" id="adherence" class="form-control number"/>
                                </div>
                            </div>
                        </td>
                        <td>
                            <div class="form-group-sm nonFP">
                                <div class="col-sm-6"><label class="well-sm pull-right">Regimen Change Reason</label></div>
                                <div class="col-sm-6">
                                    <select name="regimenChangeReasonId" id="regimenChangeReasonId" class="form-control" disabled>
                                        <option value=""></option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group-sm FP">
                                <div class="col-sm-6"><label class="well-sm pull-right">Family planning method change reason</label></div>
                                <div class="col-sm-6">
                                    <select name="fpMethodChangeReasonId" id="fpMethodChangeReasonId" class="form-control">
                                        <option value=""></option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group-sm diabetic">
                                <div class="col-sm-6"><label class="well-sm pull-right">Blood Sugar Level</label></div>
                                <div class="col-sm-6">
                                    <select name="bloodSugarLevel" id="bloodSugarLevel" class="form-control">
                                        <option value=""></option>
                                    </select>
                                </div>
                            </div>
                        </td>
                    </tr>
                    <tr class="chronicPatientIn">
                        <td>
                            <div class="form-group-sm" id="pregnantDiv">
                                <div class="col-sm-6"><label class="well-sm pull-right">Pregnant </label></div>
                                <div class="col-sm-6 radio">
                                    <label class="radio-inline" style="width: auto">
                                        <input type="radio" id="pregnant1" name="pregnant" value="1"/> Yes
                                    </label>
                                    <label class="radio-inline" style="width: auto"><input type="radio" id="pregnant0" name="pregnant" value="0"/> No</label>
                                    <input type="radio" hidden name="pregnant" value=""/>
                                </div>
                            </div>
                        </td>
                        <td>
                            <div class="">
                                <div class="col-sm-6"><label class="well-sm pull-right">Patient Status</label></div>
                                <div class="col-sm-6">
                                    <select name="patientStatusId" id="patientStatusId" onchange="checkPatientStatus()" class="form-control">
                                        <option value=""></option>
                                    </select>
                                </div>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div class="form-group-sm">
                                <div class="col-sm-6"><label class="well-sm pull-right">Reference </label></div>
                                <div class="col-sm-6">
                                    <textarea rows="3" name="comments" id="comments" class="form-control"></textarea>
                                </div>
                            </div>
                        </td>
                        <td >
                            <div class="form-group-sm">
                                <div class="col-sm-6"><label class="well-sm pull-right">Drug Pay</label></div>
                                <div class="col-sm-6">
                                    <select name="drugPay" id="drugPay" class="form-control">
                                        <option value="">Select one</option>
                                        <option value="Non-Paying" selected>Non-Paying</option>
                                        <option value="Paying">Paying</option>
                                        <option value="Waiver">Waiver</option>
                                        <option value="NHIF">NHIF</option>


                                    </select>
                                </div>
                            </div>
                        </td>
                    </tr>
                </table>
                <p class="previous">Previous prescription</p>
                <table class="formTable" id="dispenseHistoryTableAA">
                    <tr id="dispenseHistoryTableAA-0">
                        <th class="counter"></th>
                        <th>Date</th>
                        <th>Purpose</th>
                        <th>Unit</th>
                        <th>Dose</th>
                        <th>Duration</th>
                        <th>Drug</th>
                        <th>Qty</th>
                        <th>Weight</th>
                        <th>Last Regimen</th>
                        <th>Batch Number</th>
                        <th>Pill count</th>
                        <th>Indication</th>
                        <th>Adherence</th>
                        <th>Days late</th>
                        <th>Operator</th>

                    </tr>
                </table>

                <table class="formTable" id="visitGrid">
                    <tr id="visitGrid-0">
                        <th class="counter"></th>
                        <th>Commodity</th>
                        <th>Unit</th>
                        <th>Batch No.</th>
                        <th>Expiry date</th>
                        <th class="pillcount">Pill Count</th>
                        <th>Stock in batch</th>
                        <th>Dose</th>
                        <th>Duration</th>
                        <th>Indication</th>
                        <th>Qty Disp</th>
                        <th>Price</th>
                        <th>Total Cost</th>
                        <!--<th class="indication">Indication</th> -->
                        <th>Comments</th>
                        <th></th>
                    </tr>
                </table>

                <p>
                    <button onclick="addDispenseDrugRow()" class="btn btn-sm btn-default" id="addCommodity">Add Commodity </button>
                    <button onclick="initDrugInRegimen()" id="drugInRegimenButton" class="btn btn-sm btn-default">Add Commodity in Regimen </button>
                    <button onclick="showIssueReceiveDialog()" class="btn btn-sm btn-default">Receive Commodities</button>
                    <button onclick="loadVisits()" class="btn btn-sm btn-primary">Dispensing History</button>
                    <button onclick="loadPrescription()" class="btn btn-sm btn-primary">Prescription History</button>
                    <button onclick="openDrugListDialog()" class="btn btn-sm btn-primary">Commodity Setup</button>
                </p>
            </div>
            <div class="modal-footer">
                <button onclick="saveVisit()" class="btn btn-default" id="saveChanges">Save </button>
                <button  type="button" class="btn btn-default" data-dismiss="modal">
                    Close
                </button>
            </div>
        </div>
    </div>
</div>

<div class="modal-div">
    <div style="text-align: center">
        <img src="css/images/loading.gif"/>
        <br/>
        <p id='syncMessage'>
            Loading...
        </p>
    </div>
</div>

<!-- Receive/Issue drugs from the dispense button-->
<div id="receiveIssueDialog" class="modal fade full-modal" data-backdrop="static" >
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">Commodity Transaction</h4>
            </div>
            <div class="modal-body" >
                <div class="form" id="printablediv">

                    <div class="formItem" tabIndex="1">
                        <div class="item"><label>Transaction Date :</label></div>
                        <div class="item"><input type="text" name="transactionDate" id="transactionDate"/></div>
                    </div>
                    <div class="formItem">
                        <div class="item"><label>Transaction type :</label></div>
                        <div class="item">
                            <select id="transactionTypeId" name="transactionTypeId" onchange="return transactionTypeSelected()">
                                <option value="">Select one</option>
                            </select>
                        </div>
                    </div>
                    <div class="formItem">
                        <div class="item"><label>Ref No/Order No :</label></div>
                        <div class="item"><input type="text" name="referenceNo" id="referenceNo"/></div>
                    </div>
                    <div class="formItem">
                        <div class="item"><label>Source :</label></div>
                        <div class="item">
                            <select id="source" name="source">
                                <option value="">Select one</option>
                            </select>
                        </div>
                    </div>
                    <div class="formItem">
                        <div class="item"><label>Destination :</label></div>
                        <div class="item">
                            <select id="destination" name="destination">
                                <option value="">Select one</option>
                            </select>
                        </div>
                    </div>
                    <div class="formItem">
                        <div class="item"><label>Comments :</label></div>
                        <div class="item"><textarea type="text" name="transactionComments" id="transactionComments" cols="30"></textarea></div>
                    </div>
                    <table class="formTable" id="transactionTable">
                        <tr id="transactionTable-0">
                            <th class="counter"></th>
                            <th>Commodity name</th>
                            <th>Unit</th>
                            <th>Pack size</th>
                            <th>Batch No.</th>
                            <th class="qty-avail">Available qty</th>
                            <%--<th>Man. date</th> --%>
                            <th>Expiry date</th>
                            <th>Packs</th>
                            <th>Free units</th>
                            <th>Qty</th>
                            <th>Unit Cost</th>
                            <th>Total Cost</th>
                            <th>Remarks</th>
                            <th></th>
                        </tr>

                    </table>
                    <div class="formItem">
                        <div>
                            <button class="btn btn-default" onclick="addTransactionRow()">add commodity </button>
                        </div>
                    </div>

                </div>
            </div>
            <div class="modal-footer">

                <button onclick="saveIssueReceive(true)" class="btn btn-default" >Save </button>
                <button  type="button" class="btn btn-default" data-dismiss="modal" onclick="$('#visitDialog').modal('show')">
                    Close
                </button>
            </div>
        </div>
    </div>
</div>



<div id="print-commodity" class="modal fade" data-backdrop="static" >
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">

            </div>
            <div class="modal-body">

                <h4 >Do You Want To Print Commodity Transaction?</h4>

            </div>
            <div class="modal-footer">
                <button onclick="PrintCommodity()" class="btn btn-default">Print </button>
                <button  type="button" class="btn btn-default" data-dismiss="modal" onclick="closePrintCommodity()">
                    Cancel
                </button>
            </div>

        </div>
    </div>
</div>


<div id="listDrugs"  data-backdrop="static" >
    <div>
        <select id="commodityServiceSelect" onchange="onCommodityServiceSelect()">
            <option>Select service</option>
        </select>
        <select id="commoditySearch" style="height: 20px; min-width: 300px;">
            <option value="0"></option>
        </select>
    </div>
    <div id="listDrugsContent"></div>
</div>


<%--modal used to check generic name from dispensing form--%>
<%--modal used to check generic name--%>
<div id="drugDialog">
    <div class="form">
        <div >
            <h4>Commodity</h4>
        </div>

        <div class="formItem">
            <input id="drugId" name="drugId" value="-1" type="hidden"/>
            <div class="item"><label>Name* :</label></div>
            <div class="item"><input type="text" name="drugName" id="drugName"/></div>
        </div>
        <div class="formItem">
            <div class="item"><label>Strength :</label></div>
            <div class="item"><input type="text" name="strength" id="strength"/></div>
        </div>
        <div class="formItem">
            <div class="item"><label>Generic name:</label></div>
            <div class="item">
                <select id="genericNameId" name="genericNameId">
                    <option value="">Select one</option>
                </select>
                <a class="pointer" onclick="showGenericNameDialog()">Add/Edit list</a>
            </div>
        </div>
        <div class="formItem">
            <div class="item"><label>Kemsa name :</label></div>
            <div class="item"><input type="text" name="strength" id="kemsaName"/></div>
        </div>
        <div class="formItem">
            <div class="item"><label>SCA 1 name :</label></div>
            <div class="item"><input type="text" name="strength" id="sca1Name"/></div>
        </div>
        <div class="formItem">
            <div class="item"><label>SCA 2 name :</label></div>
            <div class="item"><input type="text" name="strength" id="sca2Name"/></div>
        </div>
        <div class="formItem">
            <div class="item"><label>SCA 3 name :</label></div>
            <div class="item"><input type="text" name="strength" id="sca3Name"/></div>
        </div>
        <div class="formItem">
            <div class="item"><label>Drug Category:</label></div>
            <div class="item">
                <select id="drugCategoryId" name="drugCategoryId">
                    <option value="">Select one</option>
                </select>
                <a class="pointer" onclick="showDrugCategoryDialog()">Add/Edit list</a>
            </div>
        </div>
        <div class="formItem">
            <div class="item"><label>Service:</label></div>
            <div class="item">
                <select id="drugServiceTypeId" name="drugServiceTypeId">
                    <option value="">Select one</option>
                </select>
                <a class="pointer" onclick="showServiceTypeDialog()">Add/Edit list</a>
            </div>
        </div>
        <div class="formItem">
            <div class="item"><label>Commodity Type :</label></div>
            <div class="item">
                <select id="drugTypeId" name="drugTypeId">
                    <option value="">Select one</option>
                </select>
                <a class="pointer" onclick="showDrugTypeDialog()">Add/Edit list</a>
            </div>

        </div>
        <div class="formItem">
            <div class="item"><label>Commodity Form :</label></div>
            <div class="item">
                <select id="drugFormId" name="drugFormId">
                    <option value="">Select one</option>
                </select>
                <a class="pointer" onclick="showDrugFormDialog()">Add/Edit list</a>
            </div>
        </div>
        <div class="formItem">
            <div class="item"><label>Dispensing unit :</label></div>
            <div class="item">
                <select id="dispensingUnitId" name="dispensingUnitId">
                    <option value="">Select one</option>
                </select>
                <a class="pointer" onclick="showDispensingUnitDialog()">Add/Edit list</a>
            </div>
        </div>
        <div class="formItem">
            <div class="item"><label>Pack Size :</label></div>
            <div class="item"><input type="text" name="packSize" id="packSize"/></div>
        </div>
        <div class="formItem">
            <div class="item"><label>DHIS Name :</label></div>
            <div class="item"><input type="text" name="dhisId" id="dhisId"/></div>
        </div>
        <div class="formItem">
            <div class="item"><label>CDRR Category:</label></div>
            <div class="item">
                <select id="cdrrCategoryId" name="cdrrCategoryId">
                    <option value="">Select one</option>
                </select>
                <a class="pointer" onclick="showCDRRCategoryDialog()">Add/Edit list</a>
            </div>
        </div>
        <div class="formItem">
            <div class="item"><label>Reorder level quantity :</label></div>
            <div class="item"><input type="text" name="reorderPoint" id="reorderPoint"/></div>
        </div>
        <div class="formItem">
            <div class="item"><label>Dosage :</label></div>
            <div class="item">
                <select id="dosageId" name="dosageId">
                    <option value="">Select one</option>
                </select>
                <a class="pointer" onclick="showDosageDialog()">Add/Edit list</a>
            </div>
        </div>
        <div class="formItem">
            <div class="item"><label>Quantity :</label></div>
            <div class="item"><input type="text" name="quantity" id="quantity"/></div>
        </div>
        <div class="formItem">
            <div class="item"><label>Duration :</label></div>
            <div class="item"><input type="text" name="duration" id="duration"/></div>
        </div>
        <div class="formItem">
            <div class="item"><label>Combination :</label></div>
            <div class="item">
                <input type="radio" name="combination_type" value="1"/> Mono
                <input type="radio" name="combination_type" dual="2"/> Dual
                <input type="radio" name="combination_type" value="0"/> None</div>
        </div>
        <div class="formItem">
            <div class="item"><label>Commodity in use :</label></div>
            <div class="item"><input type="radio" name="drug-voided" value="0"/> Yes <input type="radio" name="drug-voided" value="1"/> No</div>
        </div>
        <div class="formItem">
            <div class="item"><label>Tracer :</label></div>
            <div class="item"><input type="radio" name="tracer" value="1"/> Yes <input type="radio" name="tracer" value="0"/> No</div>
        </div>
        <div class="formItem">
            <div class="item"><label>Comments :</label></div>
            <div class="item"><textarea cols="30" rows="5" name="drugComments" id="drugComments"></textarea></div>
        </div>
        <div class="formItem">
            <div class="formButton">
                <button onclick="saveDrug()">Save Commodity</button>
            </div>

            <div class="formButton">
                <button onclick="confirmClose('drugDialog')">Cancel</button>
            </div>
        </div>
    </div>
</div>


<div id="DrugInRegimen" class="modal fade full-modal" data-backdrop="static">
    <div class="modal-dialog">
        <div class="modal-content">
        <div class="modal-header">
            <h4 class="modal-title">Drugs in Regimen</h4>
        </div>
        <div class="modal-body">
            <div>Regimen<br/><select style="width: 60%;" id="regimenSelect" onchange="onRegimenDrugsSelected()"><option value=""></option></select></div>
            <div>Drugs in regimen <br/><input type="text" id="drugsInRegimen" /></div>
        </div>
            <div class="modal-footer">
            <button onclick="saveRegimenDrug()" class="btn btn-default">Save</button>
            <button  type="button" class="btn btn-default" data-dismiss="modal">
                Close
            </button>
            </div>
        </div>
    </div>
</div>


<div id='patientPresciptionDialog' class="modal fade full-modal" data-backdrop="static">
    <div class="modal-dialog" style="max-width: 1200px">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">Prescription history</h4>
            </div>
            <div class="modal-body">
                <table class="formTable" id="prescriptionHistoryTable">
                    <tr id="prescriptionHistoryTable-0">
                        <th></th>
                        <th>Date</th>
                        <th>Regimen</th>
                        <th>Drug</th>
                        <th>Dosage</th>
                        <th>Quantity</th>
                        <th>Duration</th>
                    </tr>
                </table>
            </div>
            <div class="modal-footer">
                <button  type="button" class="btn btn-default" data-dismiss="modal">
                    Close
                </button>
            </div>
        </div>
    </div>
</div>

<div id='patientHistoryDialog' class="modal fade full-modal" data-backdrop="static">
    <div class="modal-dialog" style="max-width: 1200px">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">Dispensing history</h4>
            </div>
            <div class="modal-body">
                <table class="formTable" id="dispenseHistoryTable">
                    <tr id="dispenseHistoryTable-0">
                        <th class="counter"></th>
                        <th>Date</th>
                        <th>Purpose</th>
                        <th>Unit</th>
                        <th>Dose</th>
                        <th>Duration</th>
                        <th>Drug</th>
                        <th>Qty</th>
                        <th>Weight</th>
                        <th>Last Regimen</th>
                        <th>Batch Number</th>
                        <th>Pill count</th>
                        <th>Indication</th>
                        <th>Adherence</th>
                        <th>Days late</th>
                        <th>Operator</th>
                        <th>Actions</th>
                    </tr>
                </table>
            </div>
            <div class="modal-footer">
                <button  type="button" class="btn btn-default" data-dismiss="modal">
                    Close
                </button>
            </div>
        </div>
    </div>
</div>



    <jsp:include page="footer.jsp"/>
    </body>
    <script type="text/javascript" src="js/jquery/jquery-2.1.0.min.js"></script>
    <script type="text/javascript" src="js/migrate.js"></script>
    <script type="text/javascript" src="js/jquery/jquery-ui-1.10.4.min.js"></script>
    <script type="text/javascript" src="js/jquery.appendGrid-1.3.4.min.js"></script>
    <script type="text/javascript" src="js/jquery.searchabledropdown-1.0.8.min.js"></script>
    <script type="text/javascript" src="js/dialogs.js?v=<%= System.getProperty("version") %>"></script>
    <script type="text/javascript" src="js/functions.js?v=<%= System.getProperty("version") %>"></script>
    <script type="text/javascript" src="js/printThis.js?v=<%= System.getProperty("version") %>"></script>
    <script type="text/javascript" src="js/table.js?v=<%= System.getProperty("version") %>"></script>
    <script type="text/javascript" src="js/jquery.tokeninput.js"></script>
<script src="js/jtable/jquery.jtable.js" type="text/javascript"></script>
    <script src="js/liquidmetal.js" type="text/javascript"></script>
    <script src="js/jquery.flexselect.js" type="text/javascript"></script>
    <script src="js/bootstrap.min.js"></script>
    <script language="JavaScript">
        $(document).ready(function() {
            initSearchField();
            $('#clinicTypeSelect').val('');
            $('#serviceTypeSelect').val('');
            loadServices();
            loadEWI();
            $('#newPatientDialog').on('show', function () {
               // $.fn.modal.Constructor.prototype.enforceFocus = function () { };
            });
            //$( "#registrationTabs" ).tabs();

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

        if(window.Is_bulkstore == 1){
            document.getElementById("serviceSelect").style.visibility = "hidden";
        }

    </script>


</html>
