<%--
  Created by IntelliJ IDEA.
  User: kenny
  Date: 3/20/14
  Time: 8:08 AM
  To change this template use File | Settings | File Templates.
--%>
<div class="row">
    <div class="logo">
        <img src="images/gok.png"/>
    </div>
    <div class="facilityName" style="margin-bottom: 10px">
        <label style="font-size: 14px;">Ministry of Health</label>
    </div>
    <div class="facilityName">
        <label><%= session.getAttribute("facility_name") %></label>
    </div>
    <link rel="stylesheet" type="text/css" href="css/demo.css" />
    <link rel="stylesheet" type="text/css" href="css/homeview.css" />
    <link rel="stylesheet" type="text/css" href="css/flaticon.css">

    <div id="tabs" class="tabs">
        <nav>
            <ul>
                <li><a href="#" onclick="window.location='home.jsp'"><span class="flaticon-home145">Home</span></a></li>

                <li><a href="#" onclick="window.location='transactions.jsp'"><span class="flaticon-paragraph19">Transactions</span></a></li>
                <% if(session.getAttribute("mod_reports") != null) { %>

                <li><a href="#" onclick="window.location='reports.jsp'"><span class="flaticon-file85">Reports</span></a></li>
                <% }
                    if(session.getAttribute("mod_settings") != null) {
                %>

                <li><a href="#" onclick="window.location='settings.jsp'"><span class="flaticon-gears5">Settings</span></a></li>
                <% }
                    if(session.getAttribute("mod_admin") != null) { %>
                <li><a href="#" onclick="window.location='admin.jsp'"><span class="flaticon-shield89">Admin</span></a></li>
                <% } %>
                <li><a href="#" ><span class="">Help</span></a></li>
                <li><a href="#"><span><img src="images/user.png" class='user-circle' width="50px" height="50px"> &#9662;</span></a>
                    <ul class="dropdown">
                        <li><a href="#"><%= session.getAttribute("username") %></a></li>
                        <li><a href="#"><%= session.getAttribute("account_name") %></a></li>
                        <li><a href="#" onclick="logout()">logout</a></li>
                    </ul>

                </li>
            </ul>

        </nav>


    </div><!-- /tabs -->




</div>
