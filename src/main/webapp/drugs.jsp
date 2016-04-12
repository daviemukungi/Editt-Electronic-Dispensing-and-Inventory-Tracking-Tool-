<%--
  Created by IntelliJ IDEA.
  User: kenny
  Date: 4/20/14
  Time: 3:21 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="cache-control" content="max-age=0" />
    <meta http-equiv="cache-control" content="no-cache" />
    <meta http-equiv="expires" content="0" />
    <meta http-equiv="expires" content="Tue, 01 Jan 1980 1:00:00 GMT" />
    <meta http-equiv="pragma" content="no-cache" />
    <title>Drugs/Regimens | FDT</title>
    <link type="text/css" rel="stylesheet" href="css/style.css"/>
    <%
        Object logged = session.getAttribute("loggedin");
        if(logged == null) {
            response.sendRedirect("index.jsp?action=login");
        }
    %>
    <script language="JavaScript">
        window.accountID = <%= session.getAttribute("account") %>
                window.user_id = <%= session.getAttribute("userId") %>
    </script>
    <!-- Include one of jTable styles. -->
    <link href="css/themes/metro/blue/jtable.min.css" rel="stylesheet" type="text/css" />
    <link type="text/css" rel="stylesheet" href="css/jquery-ui-1.10.4.css"/>

    <script src="js/jquery/jquery-2.1.0.min.js" type="text/javascript"></script>
    <script src="js/jquery/jquery-ui-1.10.4.min.js" type="text/javascript"></script>
    <script src="js/dialogs.js" type="text/javascript"></script>
    <script src="js/functions.js" type="text/javascript"></script>
</head>
<body>
<jsp:include page="header.jsp"/>
<div class="content">
    <div class="menu">
        <div class="menuItem" onclick="showRegimenDialog()">
            <img src="images/settings.jpg"/>
            <div>Regimen</div>
        </div>
        <div class="menuItem" onclick="showDrugDialog()">
            <img src="images/settings.jpg"/>
            <div>Drugs</div>
        </div>
    </div>
</div>
<jsp:include page="footer.jsp"/>

<!-- Dialogs for adding/editing data-->

<div id="regimenDialog">
    <div class="form">
        <div >
            <h4>Regimen</h4>
        </div>

        <div class="formItem">
            <div class="item"><label>Code :</label></div>
            <div class="item"><input type="text" name="code" id="code"/></div>
        </div>
        <div class="formItem">
            <div class="item"><label>Name :</label></div>
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
            <div class="item"><input type="radio" name="visible" value="1"/> Yes <input type="radio" name="visible" value="0"/> No</div>
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
            <h4>Drug</h4>
        </div>

        <div class="formItem">
            <div class="item"><label>Name :</label></div>
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
            </div>
        </div>
        <div class="formItem">
            <div class="item"><label>Drug Category:</label></div>
            <div class="item">
                <select id="drugCategoryId" name="drugCategoryId">
                    <option value="">Select one</option>
                </select>
            </div>
        </div>
        <div class="formItem">
            <div class="item"><label>Drug Type :</label></div>
            <div class="item">
                <select id="drugTypeId" name="drugTypeId">
                    <option value="">Select one</option>
                </select>
            </div>
        </div>
        <div class="formItem">
            <div class="item"><label>Drug Form :</label></div>
            <div class="item">
                <select id="drugFormId" name="drugFormId">
                    <option value="">Select one</option>
                </select>
            </div>
        </div>
        <div class="formItem">
            <div class="item"><label>Dispensing unit :</label></div>
            <div class="item">
                <select id="dispensingUnitId" name="dispensingUnitId">
                    <option value="">Select one</option>
                </select>
            </div>
        </div>
        <div class="formItem">
            <div class="item"><label>Pack Size :</label></div>
            <div class="item"><input type="text" name="packSize" id="packSize"/></div>
        </div>
        <div class="formItem">
            <div class="item"><label>Reorder point :</label></div>
            <div class="item"><input type="text" name="reorderPoint" id="reorderPoint"/></div>
        </div>
        <div class="formItem">
            <div class="item"><label>Dosage :</label></div>
            <div class="item">
                <select id="dosageId" name="dosageId">
                    <option value="">Select one</option>
                </select>
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
            <div class="item"><label>Comments :</label></div>
            <div class="item"><textarea cols="30" rows="5" name="drugComments" id="drugComments"></textarea></div>
        </div>
        <div class="formItem">
            <div class="formButton">
                <button onclick="saveDrug()">Save Drug</button>
            </div>

            <div class="formButton">
                <button onclick="confirmClose('drugDialog')">Cancel</button>
            </div>
        </div>
    </div>
</div>


</body>
</html>
