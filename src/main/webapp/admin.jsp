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
    <title>Administration | FDT</title>
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
        } else if(session.getAttribute("mod_admin") == null) {
            response.sendRedirect("home.jsp?action=login");
        }
    %>
    <script language="JavaScript">
        window.accountID = <%= session.getAttribute("account") %>
                window.user_id = <%= session.getAttribute("userId") %>
    </script>
    <link type="text/css" rel="stylesheet" href="css/style.css?v=<%= System.getProperty("version") %>"/>
    <!-- Include one of jTable styles. -->
    <link href="css/themes/metro/blue/jtable.min.css" rel="stylesheet" type="text/css" />
    <link type="text/css" rel="stylesheet" href="css/jquery-ui-1.10.4.css"/>

    <script src="js/jquery/jquery-2.1.0.min.js" type="text/javascript"></script>
    <script src="js/jquery/jquery-ui-1.10.4.min.js" type="text/javascript"></script>
    <script src="js/dialogs.js?v=<%= System.getProperty("version") %>" type="text/javascript"></script>
    <script src="js/functions.js?v=<%= System.getProperty("version") %>" type="text/javascript"></script>
    <script src="js/admin.js?v=<%= System.getProperty("version") %>" type="text/javascript"></script>

    <!-- Include jTable script file. -->
    <script src="js/jtable/jquery.jtable.min.js" type="text/javascript"></script>
</head>
<body>
<jsp:include page="header.jsp"/>
<div class="content">

        <div class="menuItem" onclick="showPrivilegeDialog()">
            <img src="images/settings.jpg"/>
            <div>Privileges</div>
        </div>
        <div class="menuItem" onclick="showRolesListDialog()">
            <img src="images/settings.jpg"/>
            <div>Roles</div>
        </div>
        <div class="menuItem" onclick="showUserListDialog()">
            <img src="images/settings.jpg"/>
            <div>Users</div>
        </div>
        <div class="menuItem" onclick="showFacilityInfo()">
            <img src="images/settings.jpg"/>
            <div>Facility Information</div>
        </div>
        <div class="menuItem" onclick="databaseBackup()">
            <img src="images/settings.jpg"/>
            <div>Database Backup</div>
        </div>
        <%--<div class="menuItem" onclick="showRegimenTypeDialog()">--%>
            <%--<img src="images/settings.jpg"/>--%>
            <%--<div>Regimen type</div>--%>
        <%--</div>--%>
        <%--<div class="menuItem" onclick="showDosageDialog()">--%>
            <%--<img src="images/settings.jpg"/>--%>
            <%--<div>Dosage</div>--%>
        <%--</div>--%>
        <%--<div class="menuItem" onclick="showDispensingUnitDialog()">--%>
            <%--<img src="images/settings.jpg"/>--%>
            <%--<div>Dispensing Units</div>--%>
        <%--</div>--%>

    </div>
</div>
<jsp:include page="footer.jsp"/>

<!-- Dialogs for adding/editing data-->

<div id="usersDialog">
    <div class="form">
        <div >
            <h4>Create User</h4>
        </div>
        <div class="formItem">
            <input type="hidden" id="personId" value="-1"/>
            <input type="hidden" id="userId" value="-1"/>
            <input type="hidden" id="personAddressId" value="-1"/>
            <input type="hidden" id="userUpdate" value="false"/>
            <div class="item"><label>Surname :</label></div>
            <div class="item"><input type="text" name="surname" id="surname"/></div>
        </div>
        <div class="formItem">
            <div class="item"><label>First name :</label></div>
            <div class="item"><input type="text" name="firstName" id="firstName"/></div>
        </div>
        <div class="formItem">
            <div class="item"><label>Other names :</label></div>
            <div class="item"><input type="text" name="otherNames" id="otherNames"/></div>
        </div>
        <div class="formItem">
            <div class="item"><label>username :</label></div>
            <div class="item"><input type="text" name="username" id="username"/></div>
        </div>
        <div class="formItem" id="currentPasswordDiv" style="display: none">
            <div class="item"><label>Current Password :</label></div>
            <div class="item"><input type="password" name="password" id="currentPassword"/></div>
        </div>
        <div class="formItem">
            <div class="item"><label>Password :</label></div>
            <div class="item"><input type="password" name="password" id="password"/></div>
        </div>
        <div class="formItem">
            <div class="item"><label>Confirm password :</label></div>
            <div class="item"><input type="password" name="password" id="password2"/></div>
        </div>
        <div class="formItem">
            <div class="item"><label>Email :</label></div>
            <div class="item"><input type="text" name="email" id="email"/></div>
        </div>
        <div class="formItem">
            <div class="item"><label>Phone number :</label></div>
            <div class="item"><input type="text" name="email" id="phone"/></div>
        </div>
        <div class="formItem">
            <div class="item"><label>Roles :</label></div>
            <div class="item" id="roles"></div>
        </div>
        <div class="formItem">
            <div class="formButton">
                <button onclick="saveUser(true)">Save </button>
            </div>
            <div class="formButton">
                <button onclick="confirmClose('usersDialog')">Cancel</button>
            </div>
        </div>
    </div>
</div>

<div id="listUsers">
    <div id="listUserContent"></div>
</div>

<div id="privilegeDialog">
    <div id="privilegeContainer"></div>
</div>

<div id="rolesDialog">
    <div class="form">
        <div >
            <h4 id="roleActionLabel">Create Role</h4>
        </div>
        <div class="formItem">
            <input type="hidden" id="roleId" value="-1"/>
            <input type="hidden" id="roleUpdate" value="false"/>

            <div class="item"><label>Name :</label></div>
            <div class="item"><input type="text" name="name" id="name"/></div>
        </div>
        <div class="formItem">
            <div class="item"><label>Description :</label></div>
            <div class="item"><input type="text" name="email" id="description"/></div>
        </div>
        <div class="formItem">
            <div class="item"><label>Privileges :</label></div>
            <div class="item" id="privileges"></div>
        </div>
        <div class="formItem">
            <div class="formButton">
                <button onclick="saveRole(true)">Save </button>
            </div>
            <div class="formButton">
                <button onclick="confirmClose('rolesDialog')">Cancel</button>
            </div>
        </div>
    </div>
 </div>

<div id="listRoles">
    <div id="listRolesContent"></div>
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

<div id="facilityDialog">
    <div class="form">
        <div >
            <h4 >Facility information</h4>
        </div>
        <div class="formItem">
            <div class="item"><label>Code :</label></div>
            <div class="item"><input type="text" name="name" id="facility_code"/><input type="hidden" id="facility_code_id" value="-1"/></div>
        </div>
        <div class="formItem">
            <div class="item"><label>Name :</label></div>
            <div class="item"><input type="text" name="name" id="facility_name"/><input type="hidden" id="facility_name_id" value="-1"/></div>
        </div>
        <div class="formItem">
            <div class="item"><label>County :</label></div>
            <div class="item"><input type="text" name="email" id="facility_district"/><input type="hidden" id="facility_district_id" value="-1"/></div>
        </div>
        <div class="formItem">
            <div class="item"><label>Max Appointments Per Day :</label></div>
            <div class="item"><input type="number" name="name" id="Max_Appointments_Per_Day"/><input type="hidden" id="Max_Appointments_Per_Day_id" value="-1"/></div>
        </div>

        <div class="formItem">
            <div class="formButton">
                <button onclick="saveFacilityInformation()">Save </button>
            </div>
            <div class="formButton">
                <button onclick="confirmClose('facilityDialog')">Cancel</button>
            </div>
        </div>
    </div>
</div>




<div id="databaseDialog">
    <div class="form">
        <div >
            <h4 >Database Backup</h4>
        </div>
        <div class="formItem">
            <div class="item"><label>After how long do you want to Back up</label></div>
            <select name="name" id="backUpTime">
                <option value="72">72 Hours</option>
                <option value="48">48 Hours</option>
                <option value="24">24 Hours</option>
                <option value="12">12 Hours</option>
                <option value="6">6 Hours</option>
            </select>
        </div>
        <div class="formItem">
            <div class="item"><label>Select the path to backup the database</label></div>
            <div class="item"><input type="file" name="datafile" size="40" id="databasePath"/></div>
            <%--<input type="file" webkitdirectory="webkitdirectory" directory="directory" multiple="multiple"/>--%>
        </div>
        <div class="formItem">
            <div class="formButton">
                <button onclick="saveDatabaseInformation()">Save </button>
            </div>
            <div class="formButton">
                <button onclick="confirmClose('databaseDialog')">Cancel</button>
            </div>
        </div>
        <h4 >Back up the database now</h4>
        <div class="formItem">
            <div class="item"><label>Do you want to back up now?</label></div>
            <div class="formButton">
                <button onclick="backUpDatabaseInformation()">Backup Now </button>
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
</body>
</html>

