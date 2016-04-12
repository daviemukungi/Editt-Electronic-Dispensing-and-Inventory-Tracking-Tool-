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
    <title>Transactions | EDITT</title>
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
        }
    %>
    <script language="JavaScript">
        window.accountID = <%= session.getAttribute("account") %>
        window.user_id = <%= session.getAttribute("userId") %>
        window.is_bulk = <%= session.getAttribute("Is_bulkstore") %>
    </script>
    <link type="text/css" rel="stylesheet" href="css/jquery-ui-1.10.4.css"/>
    <link type="text/css" rel="stylesheet" href="css/jquery.appendGrid-1.3.4.min.css"/>
    <link type="text/css" rel="stylesheet" href="css/jquery.dataTables.min.css"/>
    <link rel="stylesheet" href="css/flexselect.css" type="text/css" media="screen" />
    <link type="text/css" rel="stylesheet" href="css/bootstrap.min.css"/>
    <link type="text/css" rel="stylesheet" href="css/bootstrap-theme.min.css"/>
    <link type="text/css" rel="stylesheet" href="css/style.css?v=<%= System.getProperty("version") %>"/>
</head>
<body>
<jsp:include page="header.jsp"/>
<div class="row">
    <div class="menu" style="height: 220px">
        <!--<div class="menuItem">
            <img src="images/transactions.jpg"/>
            <div>Dispense</div>
        </div> -->
        <% if(session.getAttribute("mod_transactions") != null) { %>
            <div class="menuItem" onclick="showIssueReceiveDialog()">
                <img src="images/transactions.jpg"/>
                <div>Receive or issue commodities</div>
            </div>

        <div class="menuItem" onclick="showBinCard()">
            <img src="images/transactions.jpg"/>
            <div>View bin cards</div>
        </div>
        <div class="menuItem" onclick="showDailyConsumption()">
            <img src="images/transactions.jpg"/>
            <div>View daily commodity consumption</div>
        </div>

            <div class="menuItem" onclick="showStockTakingDialog()">
                <img src="images/transactions.jpg"/>
                <div>Stock taking</div>
            </div>
        <% } %>
        <div class="menuItem" onclick="showSatelliteDataDialog()">
            <img src="images/transactions.jpg"/>
            <div>Satellite Data / SDP (Service Delivery Point)</div>
        </div>
    </div>
</div>
<jsp:include page="footer.jsp"/>

<div id="receiveIssueDialog" class="modal fade full-modal" data-backdrop="static">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">Commodity Transaction</h4>
            </div>
            <div class="modal-body">
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
                            <select id="source" name="source" disabled>
                                <option value="">Select one</option>
                            </select>
                        </div>
                    </div>
                    <div class="formItem">
                        <div class="item"><label>Destination :</label></div>
                        <div class="item">
                            <select id="destination" name="destination" disabled>
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
                            <th>Loose units</th>
                            <th>Qty</th>
                            <th>Pack Cost</th>
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
                <%--<button onclick="addTransactionRow()" class="btn btn-default">add commodity </button>--%>
                <button onclick="saveIssueReceive(true)" class="btn btn-default">Save </button>
                <button  type="button" class="btn btn-default" data-dismiss="modal">
                    Close
                </button>
            </div>
        </div>
    </div>
</div>

<%--<div id="receiveIssueDialog">--%>
    <%--<div class="form">--%>
        <%--<div >--%>
            <%--<h4>Commodity Transaction</h4>--%>
        <%--</div>--%>
        <%--<div class="formItem" tabIndex="1">--%>
            <%--<div class="item"><label>Transaction Date :</label></div>--%>
            <%--<div class="item"><input type="text" name="transactionDate" id="transactionDate"/></div>--%>
        <%--</div>--%>
        <%--<div class="formItem">--%>
            <%--<div class="item"><label>Transaction type :</label></div>--%>
            <%--<div class="item">--%>
                <%--<select id="transactionTypeId" name="transactionTypeId" onchange="return transactionTypeSelected()">--%>
                    <%--<option value="">Select one</option>--%>
                <%--</select>--%>
            <%--</div>--%>
        <%--</div>--%>
        <%--<div class="formItem">--%>
            <%--<div class="item"><label>Ref No/Order No :</label></div>--%>
            <%--<div class="item"><input type="text" name="referenceNo" id="referenceNo"/></div>--%>
        <%--</div>--%>
        <%--<div class="formItem">--%>
            <%--<div class="item"><label>Source :</label></div>--%>
            <%--<div class="item">--%>
                <%--<select id="source" name="source">--%>
                    <%--<option value="">Select one</option>--%>
                <%--</select>--%>
            <%--</div>--%>
        <%--</div>--%>
        <%--<div class="formItem">--%>
            <%--<div class="item"><label>Destination :</label></div>--%>
            <%--<div class="item">--%>
                <%--<select id="destination" name="destination">--%>
                    <%--<option value="">Select one</option>--%>
                <%--</select>--%>
            <%--</div>--%>
        <%--</div>--%>
        <%--<div class="formItem">--%>
            <%--<div class="item"><label>Comments :</label></div>--%>
            <%--<div class="item"><textarea type="text" name="transactionComments" id="transactionComments" cols="30"></textarea></div>--%>
        <%--</div>--%>
        <%--<table class="formTable" id="transactionTable">--%>
            <%--<tr id="transactionTable-0">--%>
                <%--<th class="counter"></th>--%>
                <%--<th>Commodity name</th>--%>
                <%--<th>Unit</th>--%>
                <%--<th>Pack size</th>--%>
                <%--<th>Batch No.</th>--%>
                <%--<th>Available qty</th>--%>
                <%--&lt;%&ndash;<th>Man. date</th> &ndash;%&gt;--%>
                <%--<th>Expiry date</th>--%>
                <%--<th>Packs</th>--%>
                <%--<th>Free units</th>--%>
                <%--<th>Qty</th>--%>
                <%--<th>Unit Cost</th>--%>
                <%--<th>Total Cost</th>--%>
                <%--<th>Remarks</th>--%>
                <%--<th></th>--%>
            <%--</tr>--%>

        <%--</table>--%>
        <%--<div class="formItem">--%>
            <%--<div>--%>
                <%--<button onclick="addTransactionRow()">add commodity </button>--%>
            <%--</div>--%>
        <%--</div>--%>
        <%--<div class="formItem">--%>
            <%--<div class="formButton">--%>
                <%--<button onclick="saveIssueReceive()">Save </button>--%>
            <%--</div>--%>
            <%--<div class="formButton">--%>
                <%--<button onclick="confirmClose('receiveIssueDialog')">Cancel</button>--%>
            <%--</div>--%>
        <%--</div>--%>
    <%--</div>--%>
<%--</div>--%>


<div id="stockTakingDialog" class="modal fade full-modal" data-backdrop="static">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">Stock Taking</h4>
            </div>
            <div class="modal-body">
                <p ><button class="btn btn-primary btn-sm" onclick="window.open( 'stocktake.jsp', '_blank')">Print Stock Taking Sheet</button><br/></p>
                <p><button class="btn btn-primary btn-sm" onclick="downloadStockTakeExcel()">Download Stock Taking Sheet</button></p>
                <p >Enter Sheet No : <input type="text" class="form-control input-sm" id="sheetNo" placeholder="Stock sheet number"/>
                    <button class="btn btn-primary btn-sm" data-dismiss="modal" onclick="loadVarianceReport()">Open stock sheet</button>
                    <% if(session.getAttribute("mod_stockadjustments") != null) { %>
                        <button class="btn btn-primary btn-sm" data-dismiss="modal" onclick="loadVarianceReport(true)">Open stock sheet for adjustment</button>
                    <% } %></p>
            </div>
            <div class="modal-footer">
                <button  type="button" class="btn btn-default" data-dismiss="modal">
                    Close
                </button>
            </div>
        </div>
    </div>
</div>

<div id="stockVarianceDialog" class="modal fade full-modal" data-backdrop="static">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">Stock Taking</h4>
            </div>
            <div class="modal-body">
                <div class="form">
                    <table class="formTable" id="stockVarianceTable">
                        <td id="stockVarianceTable-0">
                            <th>Commodity</th>
                            <th>Pack Size</th>
                            <th>Computer count(Units)</th>
                            <th>Batch</th>
                            <th>Expiry</th>
                            <th>Packs</th>
                            <th>Loose Qty</th>
                            <th>Total Qty(Units)</th>
                            <th>Variance</th>
                            <th class="adjustColumn">Adjust</th>
                        </tr>
                    </table>
                </div>
            </div>
            <div class="modal-footer">
                <button onclick="saveVarianceReport()" class="btn btn-default" data-dismiss="modal">
                    Save
                </button>
                <button onclick="saveStockTakeAdjustments()" class="btn btn-default saveAdjustment" data-dismiss="modal">
                    Adjust stock
                </button>
                <button  type="button" class="btn btn-default" data-dismiss="modal">
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
                <button  type="button" class="btn btn-default" data-dismiss="modal" onclick="closePCommodity()">
                    Cancel
                </button>
            </div>

        </div>
    </div>
</div>

<div id="binCards">
    <div>
        <select id="drugList" style="height: 20px; min-width: 300px;">
            <option value="0">Select commodity</option>
        </select>
         Select Account <select id="binCardAccounts">
            <option value="-1">All</option>
        </select>
    </div>
    <div style="float: left; width: 400px; margin: 5px 10px">Medicine information
        <table id="">
            <tr>
                <td class="title">Commodity name</td>
                <td><input type="text" readonly id="binCardDrugName"/></td>
            </tr>
            <tr>
                <td class="title">Unit</td>
                <td><input type="text" readonly id="binCardUnit"/></td>
            </tr>
            <tr>
                <td class="title">Total Stock</td>
                <td><input type="text" readonly id="binCardTotalStock"/></td>
            </tr>
            <tr>
                <td class="title">Min Stock level</td>
                <td><input type="text" readonly id="binCardMinLevel"/></td>
            </tr>
            <tr>
                <td class="title">Max Stock level</td>
                <td><input type="text" readonly id="binCardMaxLevel"/></td>
            </tr>
        </table>
    </div>
    <div style="float: left; width: 600px; margin: 5px 5px;">Batch information
        <table id="binCardBatch" class="formTable">
            <tr>
                <td>Medicine name</td>
                <td>Pack size</td>
                <td>Batch No</td>
                <td>Quantity</td>
                <td>Expiry date</td>
            </tr>
        </table>
    </div>
    <div style="float: left; width: 100%" id="voidedCommodity" class="bg-primary">
        <p> The selected commodity has been voided</p>
    </div>
    <table id="binTransaction" class="formTable" style="width: 100%; overflow: scroll">
        <tr>
            <td>Ref/Order No</td><td>Date</td><td>Transaction type</td><td>Batch No.</td><td>Expiry date</td><td>Pack size</td>
            <td>No of packs</td><td>Qty</td><!--<td>Unit cost</td><td>Amount</td>--><td>Source</td><td>Destination</td><td>Remarks</td><td>Operator</td>
            <td>Stock Trans. No</td>
        </tr>
    </table>
</div>
<div id="dailyConsumption">
    <div style="float: left; margin-bottom: 5px;" tabIndex="1">
        Start Date <input type="text" id="date1"/> End Date <input type="text" id="date2"/> <button onclick="loadDailyConsumption()">List Consumption</button>
    </div>
    <table id="dailyConsumptionTable" style="width: 100%; overflow: scroll" class="formTable table-condensed" >
        <tr>
            <td>Dispensed date</td>
            <td>Drug name</td>
            <td>Consumed in tabs/ caps</td>
        </tr>
    </table>
</div>

<div id="satelliteDataDialog">
    <div style="padding: 10px" tabIndex="1">
        Health Facility <select name="facility" id="facility">
            <option value=""></option>
        </select> Year <select id="satelliteYear"></select> Month
        <select id="month">
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
        Reported Date <input name="reportedDate" class="date"/>
        Service Type <select name="satelliteServiceType" id="satelliteServiceType" onchange="onSatelliteServiceSelect()"><option value=""></option></select>
    </div>
    <table class="formTable" id="satelliteTable">
        <tr>
            <td></td>
            <td>Commodity name</td>
            <td>Unit</td>
            <td>BF</td>
            <td>Received</td>
            <td>Returns(-)</td>
            <td>Adj(+)</td>
            <td>Adj(-)</td>
            <td>Dispensed</td>
            <td>Losses</td>
            <td>Days out of stock</td>
            <td>SOH</td>
            <td>Short Dated Stock</td>
            <td>Requested</td>
        </tr>
    </table>
    <div style="padding: 4px"><button type="button" class="button" value="" onclick="addSatelliteRow()">Add row</button></div>
    <div class="form">
        <div class="formItem">
            <div class="formButton">
                <button onclick="">Save </button>
            </div>
            <div class="formButton">
                <button onclick="confirmClose('satelliteDataDialog')">Cancel</button>
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

    <script type="text/javascript" src="js/jquery/jquery-2.1.0.min.js"></script>
    <script type="text/javascript" src="js/jquery/jquery-ui-1.10.4.min.js"></script>
    <script type="text/javascript" src="js/jquery.appendGrid-1.3.4.min.js"></script>
    <script type="text/javascript" src="js/jquery.dataTables.min.js"></script>
    <script type="text/javascript" src="js/jquery.searchabledropdown-1.0.8.min.js"></script>
    <script type="text/javascript" src="js/dialogs.js?v=<%= System.getProperty("version") %>"></script>
    <script type="text/javascript" src="js/functions.js?v=<%= System.getProperty("version") %>"></script>
    <script type="text/javascript" src="js/table.js?v=<%= System.getProperty("version") %>"></script>
    <script type="text/javascript" src="js/printThis.js?v=<%= System.getProperty("version") %>"></script>
    <script type="text/javascript" src="js/migrate.js?v=<%= System.getProperty("version") %>"></script>
    <script src="js/liquidmetal.js" type="text/javascript"></script>
    <script src="js/jquery.flexselect.js" type="text/javascript"></script>
    <script src="js/bootstrap.min.js"></script>
    <% if(session.getAttribute("mod_stockadjustments") == null) { %>
        <script>
            $(document).ready(function() {
               $('.adjustColumn').hide();
            });
            
        </script>
    <% } %>
    <script>
        var btn = $.fn.button.noConflict();
        $.fn.btn = btn
    </script>
    <script>
        $(document).ready(function() {
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
</body>

</html>
