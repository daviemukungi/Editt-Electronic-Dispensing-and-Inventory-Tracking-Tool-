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
    <title>Settings | EDITT</title>
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
        }
    %>
    <script language="JavaScript">
        window.accountID = <%= session.getAttribute("account") %>
        window.user_id = <%= session.getAttribute("userId") %>
                window.Is_bulkstore = <%=session.getAttribute("Is_bulkstore")%>
    </script>

    <!-- Include one of jTable styles. -->
    <link href="css/themes/metro/blue/jtable.min.css" rel="stylesheet" type="text/css" />
    <link type="text/css" rel="stylesheet" href="css/jquery-ui-1.10.4.css"/>
    <link type="text/css" rel="stylesheet" href="css/token-input-facebook.css"/>
    <link rel="stylesheet" href="css/flexselect.css" type="text/css" media="screen" />
    <link type="text/css" rel="stylesheet" href="css/bootstrap.min.css"/>
    <link type="text/css" rel="stylesheet" href="css/bootstrap-theme.min.css"/>
    <link type="text/css" rel="stylesheet" href="css/style.css?v=<%= System.getProperty("version") %>"/>

    <!-- Include jTable script file. -->

</head>
<body style="height: 100%">
<jsp:include page="header.jsp"/>
<div class="content">
    <input type="hidden" id="att" name="att" value="<%= session.getAttribute("null") %>" />
    <input type="hidden" id="acname" name="att" value="<%= session.getAttribute("account_name") %>" />

    <div class="menu">


        <div class="menuItem" onclick="checkAttribute()" title="Add/Edit/Delete Regimen">
            <img src="images/regimen.jpg" width="132" height="132"/>
            <div>Regimen</div>

        </div>

            <div class="menuItem" onclick="showDrugListDialog()" title="Add/Edit/Delete Commodity">
                <img src="images/drugs.jpg" width="132" height="132"/>
                <div>Commodities</div>
            </div>

            <div class="menuItem" onclick="initDrugInRegimen()" title="Add/Remove Drugs in Regimen">
                <img src="images/settings.jpg"/>
                <div>Drugs in Regimen</div>
            </div>

            <div class="menuItem" onclick="showGenericNameDialog()" title="Add/Edit/Delete Generic name">
                <img src="images/generic_name.jpg" width="132" height="132"/>
                <div>Generic name</div>
            </div>

        <div class="menuItem" onclick="showRegimenTypeDialog()" title="Add/Edit/Delete Regimen Types">
            <img src="images/regimen_type.jpg" width="132" height="132"/>
            <div>Regimen type</div>
        </div>

        <div class="menuItem" onclick="showRegimenStatusDialog()" title="Add/Edit/Delete Regimen Status">
            <img src="images/settings.jpg"/>
            <div>Regimen status</div>
        </div>
        <div class="menuItem" onclick="showDrugCategoryDialog()" title="Add/Edit/Delete Commodity Category">
            <img src="images/drug_category.jpg" height="132" width="132"/>
            <div>Commodity Category</div>
        </div>
        <div class="menuItem" onclick="showDrugFormDialog()" title="Add/Edit/Delete Commodity Formulation">
            <img src="images/settings.jpg"/>
            <div>Commodity Formulation</div>
        </div>
        <div class="menuItem" onclick="showCDRRCategoryDialog()" title="Add/Edit/Delete CDRR Category">
            <img src="images/settings.jpg"/>
            <div>CDRR Category</div>
        </div>
        <div class="menuItem" onclick="showDrugTypeDialog()" title="Add/Edit/Delete Commodity Types">
            <img src="images/settings.jpg"/>
            <div>Commodity Type</div>
        </div>
        <div class="menuItem" onclick="showDosageDialog()" title="Add/Edit/Delete Dosage">
            <img src="images/dosage.jpg" width="132" height="132"/>
            <div>Dosage</div>
        </div>
        <div class="menuItem" onclick="showRegimenChangeReasonDialog()" title="Add/Edit/Delete Reason for changing a regimen">
            <img src="images/settings.jpg"/>
            <div>Regimen Change reason</div>
        </div>
        <div class="menuItem" onclick="showFamilyPlanningMethodDialog()" title="Add/Edit/Delete Family planning methods">
            <img src="images/settings.jpg"/>
            <div>Family planning method</div>
        </div>
        <div class="menuItem" onclick="showFamilyPlanningMethodChangeReasonsDialog()" title="Add/Edit/Delete Reasons for changing Family planning methods">
            <img src="images/settings.jpg"/>
            <div>Family planning method change reason</div>
        </div>
        <div class="menuItem" onclick="showInsulinTypeDialog()" title="Add/Edit/Delete Types of Insulin">
            <img src="images/settings.jpg"/>
            <div>Type of insulin</div>
        </div>
        <div class="menuItem" onclick="showRandomBloodSugarLevelDialog()" title="Add/Edit/Delete Random blood sugar levels">
            <img src="images/settings.jpg"/>
            <div>Random blood sugar level</div>
        </div>
        <div class="menuItem" onclick="showDispensingUnitDialog()" title="Add/Edit/Delete Dispensing Units">
            <img src="images/settings.jpg"/>
            <div>Dispensing Units</div>
        </div>
        <div class="menuItem" onclick="showIdentifierTypeDialog()" title="Add/Edit/Delete  types of Patient Identifier ">
            <img src="images/settings.jpg"/>
            <div>Identifier Type</div>
        </div>
        <div class="menuItem" onclick="showPatientStatusDialog()" title="Add/Edit/Delete Patient status">
            <img src="images/patient_status.jpg" width="132" height="132"/>
            <div>Patient status</div>
        </div>
        <div class="menuItem" onclick="showIndicationDialog()" title="Add/Edit/Delete Indications">
            <img src="images/settings.jpg"/>
            <div>Indications</div>
        </div>
        <div class="menuItem" onclick="initVisitTypeDialog()" title="Add/Edit/Delete Type of visit">
            <img src="images/visit_type.jpg" width="132" height="132"/>
            <div>Visit Type</div>
        </div>
        <div class="menuItem" onclick="showServiceTypeDialog()" title="Add/Edit/Delete Type of service">
            <img src="images/service_types.jpg" width="132" height="132"/>
            <div>Service Type</div>
        </div>
        <div class="menuItem" onclick="showPatientSourceDialog()" title="Add/Edit/Delete Patient sources">
            <img src="images/patient_source.jpg" width="132" height="132"/>
            <div>Patient Source</div>
        </div>
        <div class="menuItem" onclick="showTransactionTypeDialog()" title="Add/Edit/Delete Transaction types">
            <img src="images/transaction_type.jpg" width="132" height="132"/>
            <div>Transaction types</div>
        </div>
        <div class="menuItem" onclick="initTransactionTypeDialog()" title="Add/Edit/Delete Account types">
            <img src="images/account_type.jpg" width="132" height="132"/>
            <div>Account types</div>
        </div>

        <div class="menuItem" onclick="initAccountDialog()" title="Add/Edit/Delete Accounts">
            <img src="images/account.jpg" width="132" height="132"/>
            <div>Accounts</div>
        </div>



        <!--<div class="menuItem" onclick="showLocationDialog()">
            <img src="images/settings.jpg"/>
            <div>Locations</div>
        </div> -->

        <div class="menuItem" onclick="showSupportingOrganizationDialog()" title="Add/Edit/Delete Supporting Organizations">
            <img src="images/settings.jpg"/>
            <div>Supporting Organizations</div>
        </div>
        <div class="menuItem" onclick="showRegionDialog()" title="Add/Edit/Delete Regions">
            <img src="images/regions.jpg" width="132" height="132"/>
            <div>Regions</div>
        </div>
        <div class="menuItem" onclick="initDistrictDialog()" title="Add/Edit/Delete Counties">
            <img src="images/districts.jpg" width="132" height="132"/>
            <div>Counties</div>
        </div>

        <div class="menuItem" onclick="initUserProfileDialog()" title="Change password">
            <img src="images/settings.jpg"/>
            <div>User profile</div>
        </div>
		<div class="menuItem" onclick="location.href='/metadata/download';" title="Download standard metadata to XML file">
			<img src="images/download.png" width="132" height="132"/>
			<div>Download metadata</div>
		</div>
		<div class="menuItem" onclick="showUploadMetadataDialog()" title="Upload standard metadata from XML file">
			<img src="images/upload.png" width="132" height="132"/>
			<div>Upload metadata</div>
		</div>
    </div>

	<div id="metadata-upload" title="Metadata Upload" style="display: none">
		<form method="POST" enctype="multipart/form-data" action="/metadata/upload">
			<input id="file" type="file" name="file">
			<br>
			<input type="submit" value="Upload" style="text-align: center">
		</form>
	</div>

</div>
<jsp:include page="footer.jsp"/>

    <!-- Dialogs for adding/editing data-->


    <div id="accountTypeDialog">
        <div id="accountTypeContainer"></div>
    </div>

    <div id="accountDialog">
        <div id="accountContainer"></div>
    </div>

    <div id="regimenTypeDialog">
        <div id="regimenTypeContainer"></div>
    </div>

    <div id="regimenStatusDialog">
        <div id="regimenStatusContainer"></div>
    </div>

    <div id="dosageDialog">
        <div id="dosageContainer"></div>
    </div>

    <div id="dispensingUnitDialog">
        <div id="dispensingUnitContainer"></div>
    </div>

    <div id="districtDialog">
        <div id="districtContainer"></div>
    </div>

    <div id="genericNameDialog">
        <div id="genericNameContainer"></div>
    </div>

    <div id="identifierTypeDialog">
        <div id="identifierTypeContainer"></div>
    </div>

    <div id="locationDialog">
        <div id="locationContainer"></div>
    </div>

    <div id="patientSourceDialog">
        <div id="patientSourceContainer"></div>
    </div>

    <div id="serviceTypeDialog">
        <div id="serviceTypeContainer"></div>
    </div>

    <div id="supportingOrganizationDialog">
        <div id="supportingOrganizationContainer"></div>
    </div>

    <div id="transactionTypeDialog">
        <div id="transactionTypeContainer"></div>
    </div>

    <div id="regionDialog">
        <div id="regionContainer"></div>
    </div>

    <div id="patientStatusDialog">
        <div id="patientStatusContainer"></div>
    </div>
    <div id="indicationDialog">
        <div id="indicationContainer"></div>
    </div>
    <div id="regimenChangeReasonDialog">
        <div id="regimenChangeReasonContainer"></div>
    </div>
    <div id="visitTypeDialog">
        <div id="visitTypeContainer"></div>
    </div>

    <div id="drugCategoryDialog">
        <div id="drugCategoryContainer"></div>
    </div>

    <div id="drugFormDialog">
        <div id="drugFormContainer"></div>
    </div>

    <div id="drugTypeDialog">
        <div id="drugTypeContainer"></div>
    </div>

    <div id="familyPlanningMethodDialog">
        <div id="familyPlanningMethodContainer"></div>
    </div>

    <div id="familyPlanningMethodChangeReasonsDialog">
        <div id="familyPlanningMethodChangeReasonsContainer"></div>
    </div>

    <div id="insulinTypeDialog">
        <div id="insulinTypeContainer"></div>
    </div>

    <div id="randomBloodSugarLevelDialog">
        <div id="randomBloodSugarLevelContainer"></div>
    </div>

    <div id="cdrrCategoryDialog">
        <div id="cdrrCategoryContainer"></div>
    </div>

<div id="regimenDialog">
    <div class="form">
        <div >
            <h4>Regimen</h4>
        </div>

        <div class="formItem">
            <input type="hidden" name="regimenId" id="regimenId" value="-1"/>
            <div class="item"><label>Code* :</label></div>
            <div class="item"><input type="text" name="code" id="code"/></div>
        </div>
        <div class="formItem">
            <div class="item"><label>Name* :</label></div>
            <div class="item"><input type="text" name="name" id="name"/></div>
        </div>
        <div class="formItem">
            <div class="item"><label>Line :</label></div>
            <div class="item"><input type="text" name="line" id="line"/></div>
        </div>
        <div class="formItem">
            <div class="item"><label>Regimen type:</label></div>
            <div class="item">
                <select id="regimenTypeId" name="regimenTypeId">
                    <option value="">Select one</option>
                </select>
            </div>
        </div>
        <div class="formItem">
            <div class="item"><label>Service type:</label></div>
            <div class="item">
                <select id="serviceTypeId" name="serviceTypeId">
                    <option value="">Select one</option>
                </select>
            </div>
        </div>
        <div class="formItem">
            <div class="item"><label>Regimen status :</label></div>
            <div class="item">
                <select id="regimenStatusId" name="regimenStatusId">
                    <option value="">Select one</option>
                </select>
            </div>
        </div>
        <div class="formItem">
            <div class="item"><label>Visible :</label></div>
            <div class="item"><input type="radio" name="visible" value="1"/> Yes <input type="radio" name="visible" value="0"/> No <input type="radio" name="visible" value="-1"/> None</div>
        </div>
        <div class="formItem">
            <div class="item"><label>Comments :</label></div>
            <div class="item"><textarea cols="30" rows="5" name="comments" id="comments"></textarea></div>
        </div>
        <div class="formItem">
            <div class="formButton">
                <button onclick="saveRegimen()">Save Regimen</button>
            </div>

            <div class="formButton">
                <button onclick="confirmClose('regimenDialog')">Cancel</button>
            </div>
        </div>
    </div>
</div>

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

<div id="listRegimen">

    <div id="listRegimenContent"></div>
</div>

<div id="listDrugs">
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
                <%--<button onclick="addTransactionRow()" class="btn btn-default">add commodity </button>--%>
                <button onclick="saveRegimenDrug()" class="btn btn-default">Save </button>
                <button  type="button" class="btn btn-default" data-dismiss="modal">
                    Close
                </button>
            </div>
        </div>
    </div>
</div>


<div id="regimenPassword" class="modal fade " data-backdrop="static">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">Admin Regimen Password</h4>
            </div>

            <div>
                <div class="formItem">
                    <div class="item" style="width: 15%"><label>UserName :</label></div>
                    <div class="item"><input style="width: 60%;" type="text" name="uname" id="uname"/></div>
                </div>
                <div class="formItem">
                    <div class="item" style="width: 15%"><label>Password :</label></div>
                    <div class="item"><input style="width: 60%;" type="password" name="pass" id="pass"/></div>
                </div>
            </div>

            <div class="modal-footer">
                <%--<button onclick="addTransactionRow()" class="btn btn-default">add commodity </button>--%>
                <button style="float: left" onclick="checkPassword()" class="btn btn-default">Login </button>
                <button  type="button" class="btn btn-default" data-dismiss="modal">
                    Close
                </button>
            </div>
        </div>
    </div>
</div>



<div id="userProfile">
    <div><a onclick="$('#changePasswordDiv').show()">Change password</a></div>
    <div id="changePasswordDiv" style="display: none">
        <table>
            <tr><td>Current Password</td><td><input type="password" name="currentPassword" id="currentPassword"/></td></tr>
            <tr><td>New password</td><td><input type="password" name="password1" id="password1"/></td></tr>
            <tr><td>Confirm new password</td><td><input type="password" name="password2" id="password2"/></td></tr>
        </table>
        <div><button onclick="changePassword()">Update password</button>  <button>Cancel</button></div>
    </div>
    <div><a onclick="loadSecretQuestionAnswer()">Reset Secret question and answer</a></div>
    <div id="secretQuestionDiv" style="display: none">
        <table>
            <tr><td>Secret Question</td><td><input type="text" name="currentPassword" id="secretQuestion"/></td></tr>
            <tr><td>Answer</td><td><input type="text" name="password1" id="answer"/></td></tr>
        </table>
        <div><button onclick="updateSecretQuestionAnswer()">Update secret question</button>  <button>Cancel</button></div>
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
<!--
    <div id="accountDialog">
        <div>Account Name <input id="accountName" type="text" name="accountName"/></div>
        <div>Account Description <textarea id="accountDescription" name="accountDescription" cols="25" row="10"></textarea></div>
        <div>Account type <select name="accountType"><option value="">Select one</option></select></div>
        <div><button id="saveAccountButton">Save account</button></div>
    </div>

    <div id="transactionTypeDialog">
        <div>Name <input type="text" name="transactionTypeName" id="transactionTypeName"/></div>
        <div>Account Description <textarea id="transactionTypeDescription" name="transactionTypeDescription" cols="25" row="10"></textarea></div>
        <div><input type="checkbox" name="documentable" id="documentable"/> Documentable</div>
        <div><input type="checkbox" name="narrated" id="narrated"/> Narrated</div>
        <div><button id="saveTransactionTypeButton">Save transaction type</button></div>
    </div>

    <div id="serviceTypeDialog">
        <div>Service Name <input id="serviceName" type="text" name="serviceName"/></div>
        <div>Service Description <textarea id="serviceDescription" name="serviceDescription" cols="25" row="10"></textarea></div>
        <div><button id="saveServiceTypeButton">Save account</button></div>
    </div>

    <div id="regimenTypeDialog">
        <div>Regimen Name <input id="regimenName" type="text" name="serviceName"/></div>
        <div>Regimen Description <textarea id="regimenDescription" name="regimenDescription" cols="25" row="10"></textarea></div>
        <div><button id="saveRegimenTypeButton">Save account</button></div>
    </div>

    <div id="identifierTypeDialog">
        <div>Regimen Name <input id="identifierName" type="text" name="identifierName"/></div>
        <div>Regimen Description <textarea id="identifierDescription" name="identifierDescription" cols="25" row="10"></textarea></div>
        <div><input type="checkbox" name="required" id="required"/> Required</div>
        <div><button id="saveIdentifierTypeButton">Save identifier type</button></div>
    </div>

    <div id="patientSourceDialog">
        <div>Regimen Name <input id="patientSourceName" type="text" name="patientSourceName"/></div>
        <div>Regimen Description <textarea id="patientSourceDescription" name="patientSourceDescription" cols="25" row="10"></textarea></div>
        <div><button id="savepatientSourceButton">Save patient source</button></div>
    </div>
    -->

    <script src="js/jquery/jquery-2.1.0.min.js" type="text/javascript"></script>
    <script src="js/jquery/jquery-ui-1.10.4.min.js" type="text/javascript"></script>
    <script type="text/javascript" src="js/dialogs.js?v=<%= System.getProperty("version") %>"></script>
    <script type="text/javascript" src="js/functions.js?v=<%= System.getProperty("version") %>"></script>
    <script src="js/jquery.tokeninput.js" type="text/javascript"></script>
    <script type="text/javascript" src="js/jquery.searchabledropdown-1.0.8.min.js"></script>
    <script type="text/javascript" src="js/migrate.js?v=<%= System.getProperty("version") %>"></script>
    <script src="js/jtable/jquery.jtable.js" type="text/javascript"></script>
    <script src="js/liquidmetal.js" type="text/javascript"></script>
    <script src="js/jquery.flexselect.js" type="text/javascript"></script>
    <script src="js/bootstrap.min.js"></script>
    <script>
        var btn = $.fn.button.noConflict();
        $.fn.btn = btn
    </script>

    <script>
        $(document).ready(function () {
            $(".ui-dialog-content").bind("dialogopen", function() {
                // Reposition dialog, 'this' refers to the element the even occurred on.
                $(this).dialog("option", "position", "top");
            });
        });

    </script>
</body>
</html>
