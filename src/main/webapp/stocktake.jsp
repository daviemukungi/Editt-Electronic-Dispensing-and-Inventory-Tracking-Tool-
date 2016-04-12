<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%--
  Created by IntelliJ IDEA.
  User: kenny
  Date: 6/9/14
  Time: 7:19 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

    <title>Stock Taking form</title>
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
        } else if(session.getAttribute("mod_transactions") == null) {
            response.sendRedirect("home.jsp?action=unauthorized");
        }
    %>
    <script language="JavaScript">
        window.accountID = <%= session.getAttribute("account") %>
                window.user_id = <%= session.getAttribute("userId") %>
    </script>
    <link type="text/css" rel="stylesheet" href="css/style.css?v=<%= System.getProperty("version") %>"/>
    <link type="text/css" rel="stylesheet" href="css/jquery-ui-1.10.4.css"/>
    <script type="text/javascript" src="js/jquery/jquery-2.1.0.min.js"></script>
    <script type="text/javascript" src="js/jquery/jquery-ui-1.10.4.min.js"></script>
    <script type="text/javascript" src="js/stockTake.js?v=<%= System.getProperty("version") %>"></script>
    <script language="JavaScript">
        $(document).ready(function() {
            loadStockData();
        });
    </script>
    <%
        SimpleDateFormat formatter=new SimpleDateFormat("dd-MM-yyyy");
        String dates = formatter.format(new Date());
    %>
</head>
<body style="background-color: #ffffff">
<div class="form">
    <table class="formTable" id="">
        <tr>
            <td style="max-width: 300px;">Facility Name</td>
            <td colspan="3"><%= session.getAttribute("facility_name") %></td>
        </tr>
        <tr>
            <td>Stock Date</td>
            <td style="min-width: 300px;"><%= dates%></td>
            <td>Counted By</td>
            <td style="min-width: 300px;"><%= session.getAttribute("username") %></td>
        </tr>
        <tr>
            <td>Location</td>
            <td><%= session.getAttribute("account_name") %></td>
            <td>Sheet no :</td>
            <td id="sheetNo"></td>
        </tr>
    </table>

    <div>
        <strong>Details</strong>
    </div>
    <table class="formTable" id="stockTakingTable">
        <tr id="stockTakingTable-0">
            <th class="counter"></th>
            <th>Item( Name - Strength - Units)</th>
            <th>System Count</th>
            <th>Batch No.</th>
            <th>Expiry Date</th>
            <th>Store Count</th>
        </tr>
    </table>
</div>

</body>
</html>
