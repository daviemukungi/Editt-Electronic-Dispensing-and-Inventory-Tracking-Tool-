<%--
  Created by IntelliJ IDEA.
  User: kenny
  Date: 3/20/14
  Time: 8:03 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

    <title>EDITT Login</title>
    <meta http-equiv="Cache-Control" content="max-age=0" />
    <meta http-equiv="Cache-Control" content="no-cache" />
    <meta http-equiv="Cache-Control" content="no-store" />
    <meta http-equiv="Cache-Control" content="must-revalidate" />
    <meta http-equiv="Expires" content="0" />
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
        if(session.getAttribute("loggedin") != null && (Boolean)session.getAttribute("loggedin") == true) {
            response.sendRedirect("home.jsp");
        }
    %>
    <link type="text/css" rel="stylesheet" href="css/jquery-ui-1.10.4.css"/>
    <link type="text/css" rel="stylesheet" href="css/jquery.appendGrid-1.3.4.min.css"/>
    <link type="text/css" rel="stylesheet" href="css/bootstrap.min.css"/>
    <link type="text/css" rel="stylesheet" href="css/bootstrap-theme.min.css"/>
    <link type="text/css" rel="stylesheet" href="css/style.css?v=<%= System.getProperty("version") %>"/>

</head>
<body>
<div class="header">
    <div class="logo">
        <img src="images/gok.png"/>
    </div>

    <div class="facilityName" style="margin-bottom: 10px">
        <label style="font-size: 14px;">Ministry of Health</label>
    </div>
    <div class="facilityName">
        <label style="font-size: 14px;" id="facilityName"></label>
    </div>
    <div class="facilityName">
        <label style="font-size: 14px;">Electronic Dispensing & Inventory Tracking Tool</label>
    </div>
    <div class="facilityName">
        <label style="font-size: 14px;">(EDITT)</label>
    </div>
    <div class="facilityName">
        <label style="font-size: 14px;">Version: 1.8.6</label>
    </div>
</div>
<div class="logbody">
    <div class="logbodyinn">
        <form method="POST" onsubmit="return false">
            <div style="text-align: center"> <label style="font-size: 14px;">User login</label></div>
            <div class="loginline"><span id="loginnotification" style="color: red">
                <%
                    String action = request.getParameter("action");
                    if(action != null) {
                %>
                        Please login to access the system.
                <%
                    }
                %>
            </span></div>
            <div class="form-group">
                <label>Select store</label>

                    <select name="store" id="store" class="form-control">
                        <option value="">Select store</option>
                    </select>
            </div>
            <div class="form-group">
                <label>User id </label><input class="form-control" type="text" name="username" id="username"/>
            </div>
            <div class="form-group">
                <label>Password </label><input type="password" class="form-control" name="password" id="password"/>
            </div>
            <button class="btn btn-default" onclick="return validateLogin()">Sign in</button>
            <div class="loginline"><a onclick="showForgotPasswordDialog()" class="pointer">Forgot password?</a></div>
        </form>
    </div>
</div>
<div class="container" style="background-color: #90EE90;border:none;margin-left:31%">
    <div class="row ">
        <div>
            <img class="logo " src="images/logo.png"style="width:50%;">
        </div>

    </div>
</div>
<div class="modal-div">
    <div style="text-align: center">
        <img src="css/images/loading.gif"/>
        <br/>
        <p id='syncMessage'>
            Logging in...
        </p>
    </div>
</div>
<div id="forgotPass">
    <input type="hidden" id="userid"/>
    <p id="usernameInput">Enter your username : <input id="userNameForgot" type="text" placeholder="Username"/> <button onclick="loadSecretQuestion()">Submit</button></p>
    <div id="questionArea" style="display:none">
        <p id="question">Question</p>
        <p><input type="text" name="answer" id="answer"/> <button onclick="confirmAnswer()">Verify</button></p>
    </div>
    <div id="newPasswordArea" style="display:none">
        <table>
            <tr><td style="text-align: right"><label>New password :</label></td><td><input type="password" id="password1"></td></tr>
            <tr><td style="text-align: right"><label>Confirm New password :</label></td><td><input type="password" id="password2"></td></tr></table>
        <p><button onclick="changePassword()">Change password</button></p>
    </div>
</div>
</body>
<script type="text/javascript" src="js/jquery/jquery-2.1.0.min.js"></script>
<script type="text/javascript" src="js/dialogs.js?v=<% System.getProperty("version"); %>"></script>
<script type="text/javascript" src="js/jquery/jquery-ui-1.10.4.min.js"></script>
<script language="JavaScript" src="js/login.js?v=<% System.getProperty("version"); %>"></script>
<script src="js/bootstrap.min.js"></script>
<script>
    $(document).ready(function() {
        $.ajax({
            url: 'settings',
            headers: {
                "Content-Type": "application/json"
            },
            type: 'GET',
            success: function (data) {
                if(data) {
                    for(var i = 0; i < data.length; i++) {
                        if(data[i].key == "facility_name") {
                            $("#facilityName").html(data[i].value);
                        }
                    }
                }
            },
            error: function () {

            },
            complete: function () {
                //loadingEnd();
            }
        });
    });
</script>
</html>
