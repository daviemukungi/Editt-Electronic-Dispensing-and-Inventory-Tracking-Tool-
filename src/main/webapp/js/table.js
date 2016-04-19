/**
 * Created by kenny on 5/25/14.
 */

/**
 *  Add a new row to the dispense drugs table
 */
function addDispenseDrugRow() {
    var visitTabel = document.getElementById("visitGrid");
    var rows = visitTabel.rows.length;
    var html = getDispenseRow(rows);
    $('#visitGrid > tbody:last').append(html);
    initNumberevent();
    $('#drug-' + rows).flexselect();
        if(window.selectedService == "1") {
            $('.pillcount').hide();
            // $('.indication').hide();
        } else {
            $('.pillcount').show();
            //$('.indication').show();
        }
    $('#drug-' + rows).change(function() {
        var index = $(this).parents('tr').attr('index');
        var selected = $(this).find(":selected");
        /**
         *  set the dispensing unit
         */
        for(var i = 0; i < window.dispensingUnit.length; i++) {
            if(window.dispensingUnit[i] != undefined && window.dispensingUnit[i].id == $(selected).attr('unit')) {
                $('#unit-' + index).html('<span>' + window.dispensingUnit[i].name + '</span>');
            }
        }

        /**
         *  set the dispensing duration
         */
        for(var i = 0; i < window.dispensingUnit.length; i++) {
            if(window.dispensingUnit[i] != undefined && window.dispensingUnit[i].id == $(selected).attr('duration')) {
                $('#duration-' + index).html('<input>' + window.dispensingUnit[i].name + '</input>');
            }
        }



        /**
         *  set the Price
         */
        for(var i = 0; i < window.dispensingUnit.length; i++) {
            if(window.dispensingUnit[i] != undefined && window.dispensingUnit[i].id == $(selected).attr('price')) {
                $('#price-' + index).html('<input>' + window.dispensingUnit[i].name + '</input>');
            }
        }

        /**
         *  set the Cost
         */
        for(var i = 0; i < window.dispensingUnit.length; i++) {
            if(window.dispensingUnit[i] != undefined && window.dispensingUnit[i].id == $(selected).attr('total_cost')) {
                $('#total_cost-' + index).html('<input>' + window.dispensingUnit[i].name + '</input>');
            }
        }



        /**
         *  set the dispensing quantity
         */
        for(var i = 0; i < window.dispensingUnit.length; i++) {
            if(window.dispensingUnit[i] != undefined && window.dispensingUnit[i].id == $(selected).attr('qty_disp')) {
                $('#qty_disp-' + index).html('<input>' + window.dispensingUnit[i].name + '</input>');
            }
        }



        var batchList = document.getElementById('batch_no-' + index);
        for(var i = batchList.length; i > 0; i--) {
            batchList.remove(i);
        }

        var batches = []
        for(var i = 0; window.batchList != null && i < window.batchList.length; i++) {
            if(window.batchList[i] != undefined && window.batchList[i].drugId == this.value) {
                if(window.batchList[i].stockInBatch > 0) {
                    batches.push(window.batchList[i]);

                }
            }
        }

        batches.sort(function(a,b) { return (a.expiry_date) - (b.expiry_date) })
        for(var i = 0; i < batches.length; i++) {
            var opt = new Option(batches[i].batch_no, batches[i].batch_no);
            $(opt).data('obj', batches[i]);
            batchList.add(opt);
        }

        $("#dosage-" + index).val(window.mainDrugList[$(selected).attr('index')].dosageId);
        $("#duration-" + index).val(window.mainDrugList[$(selected).attr('index')].duration);
        $("#qty_disp-" + index).val(window.mainDrugList[$(selected).attr('index')].quantity);
        $("#price-" + index).val(window.mainDrugList[$(selected).attr('index')].price | 0);
        //$("#total_cost-" + index).val(window.mainDrugList[$(selected).attr('index')].total_cost);
        sessionStorage.setItem("#duration-" + index,$("#duration-" + index).val());
        sessionStorage.setItem("#quantity-" + index,$("#qty_disp-" + index).val());
        sessionStorage.setItem("#price-" + index,$("#price-" + index).val());

    });
    $("#duration-" + rows).change(function(){
        var dbDuration  = sessionStorage.getItem("#duration-" + rows);
        var dbQuantity  = sessionStorage.getItem("#quantity-" + rows);
        if((dbDuration == 0 || "" || null) || (dbQuantity == 0 || "" || null)){
            return;
        }
        var newDuration = $("#duration-" + rows).val();
        var newQuantity = newDuration * dbQuantity /dbDuration;
        $("#qty_disp-" + rows).val(Math.trunc(newQuantity));
    });



    $("#qty_disp-" + rows).change(function(){
        var dbDuration  = sessionStorage.getItem("#duration-" + rows);
        var dbQuantity  = sessionStorage.getItem("#quantity-" + rows);
        var dbPrice  = sessionStorage.getItem("#price-" + rows);
        if((dbDuration == 0 || "" || null) || (dbQuantity == 0 || "" || null)){
            return;
        }
        var newQuantity = $("#qty_disp-" + rows).val();
        var newDuration = newQuantity / dbQuantity * dbDuration;
        var newCost = (newDuration * dbQuantity * dbPrice).toFixed(2);
        $("#duration-" + rows).val(Math.trunc(newDuration));
        $("#total_cost-" + rows).val(newCost);
    });

    $("#price-" + rows).change(function(){
        var dbQuantity  = $("#qty_disp-" + rows).val();
        if((dbQuantity == 0 || "" || null) || ($("#price-" + rows).val() ==0 || "" ||  null)){
            return;
        }
        var newPrice = $("#price-" + rows).val();
        var newCost = (newPrice * dbQuantity).toFixed(2);
        $("#total_cost-" + rows).val(newCost);
    });

    $('#batch_no-' + rows).change(function() {
        var index = $(this).parents('tr').attr('index');
        var selected = $(this).find(":selected");
        var batch = $(selected).data('obj');
        var date = new Date(batch.expiry_date);

        var drugId = $('#drug-' + rows).val();
        var batchId = $(this).val();
        var newer = false;
        $('#batch_no-' + rows + ' option').each(function() {
            var data = $(this).data('obj');
        });
        var options = document.getElementById('batch_no-' + rows).options;

        var notify = false;
        for(var i = 0; i < options.length; i++) {
            var data = $(options[i]).data('obj');
            if(data == undefined)
                continue;
            if(data.expiry_date < batch.expiry_date) {
                notify = true;
            }
        }
        var table =  document.getElementById("visitGrid");
        var tableRows =table.rows.length;
        for(var i = 1; i < tableRows; i++) {
            if(rows != i) {
                var dId = $('#drug-' + i).val();
                if(dId == drugId) {

                    var batch =  $('#batch_no-' + i).val();
                    if(batch == batchId) {
                        $(this).val('');
                        alert("Batch already selected.");
                        return;
                    }
                    if(batch != batchId) {
                        var index = $(this).parents('tr').attr('index');
                        var selected = $(this).find(":selected");
                        var b = $(selected).data('obj');
                        alert("Batch Are different.");
                        var bb = val(b.stockInBatch)
                        alert(bb);

                        $('#stockInBatch-' + index).val(batchId.stockInBatch);
                    }
                }
            }
        }
        if(notify)
            alert("There is a batch which will expire sooner than the one selected.");
        var prettyDate =(date.getDate()) + '/' + (date.getMonth() + 1) + '/' +
            date.getFullYear();
        $('#expiry_date-' + index).val(prettyDate);
        $('#stockInBatch-' + index).val(batch.stockInBatch);
        validateQuantity(rows, null);
    });
}

function validateQuantity(index, table) {
    var qty = $('#qty_disp-' + index).val();
    var stockInBatch = $('#stockInBatch-' + index).val();

    if(stockInBatch == '') {
        $('#qty_disp-' + index).val('');
        alert("Select the batch");
        return;
    }
    if(parseInt(stockInBatch) < qty) {
        $('#qty_disp-' + index).val('');
        alert("You cannot dispense more commodities than is available in stock.");
    }
    calculateTotalCost(index,table)
}

function calculateTotalCost(index, table) {
    var qty = $('#qty_disp-' + index).val();
    var price = $('#price-' + index).val();
    if((qty == "" || null) || (price == "" || null)){
       var totalCost = 0;
    }else{
        totalCost = (price * qty ).toFixed(2);
    }
    $("#total_cost-" + index).val(totalCost);

}

function calculateTotalCost(index, table) {
    var qty = $('#qty_disp-' + index).val();
    var price = $('#price-' + index).val();
    var duration = $('#duration-' + index).val();
    var totalCost = 0;


    if(qty == '' || price=='' || duration == '') {

        return;
    }
    if(qty != '' && price != '' && duration != '') {
         totalCost = (price * qty * duration).toFixed(2);
        $("#total_cst-" + rows).val(totalCost);
    }
}

/**
 *  Creates the HTML for a dispense drug row.
 * @param index The row-index we are creating a row for.
 * @returns {string}
 */
function getDispenseRow(index) {
    var html = '<tr id="visitGrid-' + index + '" index="' + index + '"><td><label style="width: auto" name="index" class="index" index="' + index + '">' + index + '</label></td><td style="min-width: 300px;"><select name="drug" class="drugs searchable" id="drug-' + index + '"><option value=""></option>';

    for(var i = 0; window.mainDrugList != undefined && i < window.mainDrugList.length; i++) {
        if(window.mainDrugList[i].strength == 'null' || window.mainDrugList[i].strength == null) {
            window.mainDrugList[i].strength = '';
        }
        if($('#regimenId').val() != "") {
            if(window.drugIds != null && window.drugIds[window.mainDrugList[i].id] == true) {
                html += '<option index="' + i + '" unit="' + window.mainDrugList[i].dispensingUnitId + '" value="' + window.mainDrugList[i].id + '">' + window.mainDrugList[i].name + ' ' + (window.mainDrugList[i].strength == null? "" : window.mainDrugList[i].strength) + ' ' + (window.mainDrugList[i].packSize == null? "" : window.mainDrugList[i].packSize) + '</option>';
            }
        } else if(window.drugIds != null && window.drugIds[window.mainDrugList[i].id] == true){
            html += '<option index="' + i + '" unit="' + window.mainDrugList[i].dispensingUnitId + '" value="' + window.mainDrugList[i].id + '">' + window.mainDrugList[i].name  + ' ' + window.mainDrugList[i].strength + ' ' + window.mainDrugList[i].packSize + '</option>';

        }
    }

    html += '</select></td><td id="unit-' + index + '"></td><td style="width: 80px;"><select class="" name="batch_no" id="batch_no-' + index + '"><option value=""></option></select></td>' +
        '<td><input readonly type="text" name="expiry_date" id="expiry_date-' + index + '"/></td><td class="pillcount number" style="width: 50px;"><input type="text"  name="pill_count" id="pill_count-' + index + '"/></td>' +
        '<td style="width: 60px;"><input type="text" readonly name="stock_in_batch" id="stockInBatch-' + index + '"/></td><td style="width: 60px;"><select name="dosage" id="dosage-' + index + '"><option value=""></option>';
    for(var i = 0; i < window.dosage.length; i++) {
        html += '<option value="' + window.dosage[i].id  + '">' + window.dosage[i].name + '</option>';
    }

    html += '</select></td><td style="width: 40px;"><input type="text" name="duration" class="number" id="duration-' + index + '"/></td>' +
        '<td style="width: 60px;"><select class="" name="indication" id="indication-' + index + '"><option value=""></option>';
    for(var i = 0; i < window.indication.length; i++) {
        html += '<option value="' + window.indication[i].id  + '">' + window.indication[i].name + '</option>';
    }

    html += '</select></td><td style="width: 80px;"><input class="number" onkeyup="validateQuantity(' + index + ', \'visitGrid\')" type="text" name="qty_disp" id="qty_disp-' + index + '"/></td>'+
        '<td ><input style="width: 60px;" value="0" class="number" onkeyup="calculateTotalCost(' + index + ', \'visitGrid\')" name="price" id="price-' + index + '" /></td>' +
        '<td "><input class="number" style="width: 60px;" name="total_cost" id="total_cost-' + index + '"/></td>';

    html += '<td><input type="text" name="comment" id="comment-' + index + '"/></td>' +
        '<td style="width: 60px;"><button class="pointer" title="Add row" onclick="addDispenseDrugRow()"><span>+</span></button><button class="pointer" title="Delete row" onclick="deleteRow(event, \'visitGrid\')"><span>x</span></button></td></tr>';
    return html;
}

function addTransactionRow() {
    var txTabel = document.getElementById("transactionTable");
    var rows = txTabel.rows.length;
    var html = getTransactionRow(rows);
    $('#transactionTable > tbody:last').append(html);
    initNumberevent();
    $('#tx-drug-' + rows).flexselect();
    $('#tx-expDate-' + rows).datepicker({minDate: new Date(), dateFormat: 'dd/mm/yy', changeMonth: true,changeYear: true})
    $(".date").datepicker();

    var selected = $('#transactionTypeId').find(":selected");
    var txType = $(selected).data('obj');

    if(txType != undefined) {
        if (txType.name == "Returns from (+)" || txType.name == "Ajustment (+)" || txType.name == "Ajustment (-)" || txType.name == "Dispensed to Patients" || txType.name == "Issued To" || txType.name == "Returns to (-)" || txType.name == "Expired(-)") {
            window.txSelectBatch = true;
            $('.qty-avail').show();
        } else {
            $('.qty-avail').hide();
        }
    }
    $('#tx-drug-' + rows).change(function() {
        var index = $(this).parents('tr').attr('index');
        var selected = $(this).find(":selected");

        $('#tx-packSize-' + index).val(($(selected).attr('packSize') == null || $(selected).attr('packSize') == '' || $(selected).attr('packSize') == 'null') ? "" : $(selected).attr('packSize'));
        /**
         *  set the dispensing unit
         */
        for(var i = 0; i < window.dispensingUnit.length; i++) {
            if(window.dispensingUnit[i] != undefined && window.dispensingUnit[i].id == $(selected).attr('unit')) {
                $('#tx-unit-' + index).html(window.dispensingUnit[i].name);
            }
        }

        if(window.txSelectBatch) {
            var batchList = document.getElementById('tx-batchNo-' + index);
            for(var i = batchList.length; i > 0; i--) {
                batchList.remove(i);
            }

            for (var i = 0; window.batchList != null && i < window.batchList.length; i++) {
                if (window.batchList[i] != undefined && window.batchList[i].drugId == this.value) {
                    if (window.batchList[i].stockInBatch > 0) {
                        var opt = new Option(window.batchList[i].batch_no, window.batchList[i].batch_no);
                        $(opt).data('obj', window.batchList[i]);
                        batchList.add(opt);
                    }
                }
            }

            $('#tx-batchNo-' + rows).change(function() {
                var index = $(this).parents('tr').attr('index');
                var selected = $(this).find(":selected");
                var batch = $(selected).data('obj');
                var date = new Date(batch.expiry_date);

                var newer = false;

                var today = new Date();
                var selectedTxType = $('#transactionTypeId').find(":selected");
                var selTxType = $(selectedTxType).data("obj")
                if(date.getTime() > today.getTime() && selTxType != undefined && selTxType.name == 'Expired(-)') {
                    $(this).val('');
                    alert("Batch has not expired");
                    return;
                }

                $('#tx-batchNo-' + rows + ' option').each(function() {
                    var data = $(this).data('obj');
                });


                var options = document.getElementById('tx-batchNo-' + rows).options;

                var table =  document.getElementById("transactionTable");
                var tableRows = table.rows.length;

                var drugId = $('#tx-drug-' + rows).val();
                var batchId = $(this).val();

                for(var i = 1; i < tableRows; i++) {
                    if(rows != i) {
                        var dId = $('#tx-drug-' + i).val();
                        if(dId == drugId) {
                            var batch =  $('#tx-batchNo-' + i).val();
                            if(batch == batchId) {
                                $(this).val('');
                                alert("Batch already selected.");
                                return;
                            }
                        }
                    }
                }

                var notify = false;
                for(var i = 0; i < options.length; i++) {
                    var data = $(options[i]).data('obj');
                    if(data == undefined)
                        continue;
                    if(data.expiry_date < batch.expiry_date) {
                        notify = true;
                    }
                }


                var prettyDate = date.getDate() + '/' + (date.getMonth()+1) + '/' +
                    date.getFullYear();
                $('#tx-expDate-' + index).val(prettyDate);
                $('#tx-ava-qty-' + index).val(batch.stockInBatch);
            });

        }
    });
}


function calculateQuantity(index) {
    var packSize = $('#tx-packSize-' + index).val();
    var packs = $('#tx-packs-' + index).val();
    var avail = $('#tx-ava-qty-' + index).val();
    var open_packs = $('#tx-open-packs-' + index).val();
    var openPacksQty = 0;
    var qty = 0;
    if(isNaN(packSize) || packSize == ""){
        alert("Drug has no pack size, cannot calculate quantity.");
        return;
    }

    if(open_packs != '' && !isNaN(open_packs)) {
        openPacksQty = parseInt(open_packs);
    } else {
        $('#tx-open-packs-' + index).val('')
    }
    if(!isNaN(packSize) && !isNaN(packs) && packSize != "" && packs != "") {
        qty = parseInt(packSize) * parseInt(packs);
    } else {
        $('#tx-packs-' + index).val('');
        $('#tx-qty-' + index).val('');
    }
    var total = (openPacksQty + qty);
    if(total > parseInt(avail)) {
        $('#tx-open-packs-' + index).val('0');
        $('#tx-packs-' + index).val('0');
        $('#tx-qty-' + index).val('0');
        alert("Cannot issue more than is available");
        return;
    }
    $('#tx-qty-' + index).val(total);
    calculateCost(index);

}

function calculateCost(index) {
    var qty = $('#tx-qty-' + index).val();
    var unitCost = $('#tx-unit-cost-' + index).val();
    var looseUnit = $('#tx-open-packs-' + index).val();
    var packs = $('#tx-packs-' + index).val();
    var packSize = $('#tx-packSize-' + index).val();
    var totalLooseUnits = 0;
    var totalCost = 0;
    if(isNaN(unitCost) || unitCost == ""){
        // alert("Unit Cost not valid.");
        $('#tx-unit-cost-' + index).val("")
        return;
    }


    if(!isNaN(packs) && !isNaN(unitCost) && packs != "" && unitCost != "" && looseUnit == 0 ) {

        totalCost = parseInt(packs) * parseInt(unitCost);
    }
    else if (!isNaN(packs) && !isNaN(unitCost) && packs != "" && unitCost != "" && !isNaN(looseUnit) && looseUnit != "" && !isNaN(packSize) && packSize != ""){
        totalLooseUnits = ((looseUnit) / (packSize)) * parseInt(unitCost);
        totalCost = (parseInt(packs) * parseInt(unitCost)) + totalLooseUnits;
    }
    $('#tx-total-cost-' + index).val(totalCost);
}


function getTransactionRow(index) {
    var html = '<tr id="transactionTable-' + index + '" index="' + index + '"><td><label name="index" class="index" index="' + index + '">' + index + '</label></td><td style="min-width: 200px;"><select name="drug" class="drugs searchable" id="tx-drug-' + index + '"><option value=""></option>';

    for(var i = 0; i < window.mainDrugList.length; i++) {
        if(window.service != undefined && ((window.service != undefined && window.mainDrugList[i].serviceTypeId == window.service[0]) || (window.service[1] != undefined && window.mainDrugList[i].serviceTypeId == window.service[1]))) {
            html += '<option unit="' + window.mainDrugList[i].dispensingUnitId + '" value="' + window.mainDrugList[i].id + '" packSize="' + window.mainDrugList[i].packSize + '">' + window.mainDrugList[i].name + '</option>';
        } else {
            html += '<option unit="' + window.mainDrugList[i].dispensingUnitId + '" value="' + window.mainDrugList[i].id + '" packSize="' + window.mainDrugList[i].packSize + '">' + window.mainDrugList[i].name + '</option>';
        }
    }

    html += '</select></td><td><label name="unit" id="tx-unit-' + index + '"></label></td><td style="width: 40px;"><input type="text" readonly name="packSize" id="tx-packSize-' + index + '"/></td>';

    if(window.txSelectBatch) {
        html += '<td style="width: 80px;"><select class="" name="batch_no" id="tx-batchNo-' + index + '"><option value=""></option></select></td>';
    } else {
        html += '<td style="width: 80px;"><input type="text" name="batchNo" id="tx-batchNo-' + index + '"/></td>';
    }

    html += '<td class="qty-avail" style="width: 80px;"><input type="text" readonly  name="availableQty" id="tx-ava-qty-' + index + '"/></td><td style="width: 130px;"><input type="text" name="expDate" id="tx-expDate-' + index + '" readonly/></label></td><td style="width: 80px;"><input type="text"  onkeyup="calculateQuantity(' + index + ')" name="packs" id="tx-packs-' + index + '"/></label></td>' +
        '<td style="width: 80px;"><input type="text"  name="packs" onkeyup="calculateQuantity(' + index + ')"  id="tx-open-packs-' + index + '"/></td><td style="width: 80px;"><input type="text" readonly name="qty" id="tx-qty-' + index + '"/></td>' +
        '<td style="width: 80px;"><input type="text" name="unit-cost" onkeyup="calculateCost(' + index + ')" id="tx-unit-cost-' + index + '"/></label></td><td style="width: 80px;"><input type="text" readonly name="total-cost" id="tx-total-cost-' + index + '"/></td>' +
        '<td><input type="text" name="comment" id="tx-comment-' + index + '"/></td>' +
        '<td><button class="pointer" title="Delete" onclick="deleteRow(event, \'transactionTable\')"><span>x</span></button></td></tr>';

    //<td><input type="text" class="date" name="manDate" id="manDate-' + index + '"/></label></td>
    return html;
}
function checkPatientStatus(){

    var patientId = document.getElementById("patientStatusId");
    var patientidtext = patientId.options[patientId.selectedIndex].text.trim();

    if(patientidtext !="Active") {
        document.getElementById("addCommodity").disabled = true;
        document.getElementById("saveChanges").disabled = true;
        clearTable("visitGrid");
    }else{
        document.getElementById("addCommodity").disabled = false;
        document.getElementById("saveChanges").disabled = false;
    }
}
function addStockRow() {
    var stockTable = document.getElementById("stockTakingTable");
    var rows = stockTable.rows.length;
    var html = getStockRow(rows);
    $('#stockTakingTable > tbody:last').append(html);
    $('#drug-' + rows).searchable();
    $(".date").datepicker();
    $('#drug-' + rows).change(function() {
        var index = $(this).parents('tr').attr('index');
        /**
         *  set the batch number to select
         */
        var batchList = document.getElementById('batch_no-' + index);
        for(var i = batchList.length; i > 0; i--) {
            batchList.remove(i);
        }

        for(var i = 0; window.batchList != null && i < window.batchList.length; i++) {
            if(window.batchList[i] != undefined && window.batchList[i].drugId == this.value) {
                var opt = new Option(window.batchList[i].batch_no, window.batchList[i].batch_no);
                $(opt).data('obj', window.batchList[i]);
                batchList.add(opt);
            }
        }
    });

}

function getStockRow(index) {
    var html = '<tr id="stockTakingTable-' + index + '" index="' + index + '"><td><label name="index" class="index">' + index + '</label></td><td style="min-width: 200px;"><select name="drug" class="drugs searchable" id="drug-' + index + '"><option value=""></option>';

    for(var i = 0; i < window.mainDrugList.length; i++) {
        html += '<option value="' + window.mainDrugList[i].id + '">' + window.mainDrugList[i].name  + '</option>';
    }

    html += '</select></td><td style="width: 50px;"><input type="text" name="qty" id="qty-' + index + '"/></td>' +
        '<td style="width: 120px;"><input type="text" class="date" name="expDate" id="expDate-' + index + '"/></label></td><td style="width: 120px;"><select class="" name="batch_no" id="batch_no-' + index + '"><option value=""></option></select></td>' +
        '<td style="width: 120px;"><input type="text" name="price" id="price-' + index + '"/></td>' +
        '<td style="width: 80px;"><div></div></td></tr>';
    return html;
}

function createVarianceReportTable(adjust) {
    clearTable('stockVarianceTable');
    $('#stockVarianceDialog').modal("show");

    for(var i = 0; i < window.stockTakingItemList.length; i++) {
        var html = getStockVarianceRow(i, window.stockTakingItemList[i]);
        $('#stockVarianceTable > tbody:last').append(html);
        initNumberevent();
        $('#packs-' + i).val(window.stockTakingItemList[i].packs);
        $('#looseQty-' + i).val(window.stockTakingItemList[i].looseQty);


        var packSize = parseInt($('#packSize-' + i).text()) ;


        var physicalCount = ( parseInt($('#packs-' + i).val()) * packSize) + parseInt($('#looseQty-' + i).val());


        $('#Tqty-'+i).val(physicalCount);

        var computerCount = window.stockTakingItemList[i].systemCount;
        if(!isNaN(computerCount) && !isNaN(physicalCount)) {
            var variance = parseInt(physicalCount) - parseInt(computerCount);
            $('#variance-' + i).attr('value', variance);
            $('#variance-' + i).html(variance);
        }
        if(adjust || window.stockTakingItemList[i].adjusted == 1) {
            $('#packs-' + i).attr('readonly', true).attr('value', physicalCount).attr('title', "Value already adjusted and cannot be edited.");
            $('#looseQty-' + i).attr('readonly', true).attr('value', packSize).attr('title', "Value already adjusted and cannot be edited.");
        } else {
            $('#packs-' + i).attr('value', physicalCount);
            $('#looseQty-' + i).attr('value', packSize);
        }

        $('#packs-' + i).change(function() {
            var index = $(this).parents('tr').attr('index');
            var packSize = parseInt($('#packSize-' + index).text()) ;
            var packs = parseInt($('#packs-' + index).val());
            var loose = parseInt($('#looseQty-' + index).val() | 0);
            $('#Tqty-'+index).val((packs * packSize) + loose);
            var totalQuantity = $('#Tqty-'+index).val();
            var computerCount = $('#qty-' + index).attr('value');
                if(!isNaN(computerCount) && !isNaN(totalQuantity) && computerCount != "" && totalQuantity != "") {
                    var variance = parseInt(totalQuantity) - parseInt(computerCount);
                    $('#variance-' + index).attr('value', variance);
                    $('#variance-' + index).html(variance);
                }
        });
          $('#looseQty-' + i).change(function() {
              var index = $(this).parents('tr').attr('index');
              var packSize = parseInt($('#packSize-' + index).text() | 0) ;
              var packs = parseInt($('#packs-' + index).val() | 0);
              var loose = parseInt($('#looseQty-' + index).val() | 0);
              $('#Tqty-'+index).val((packs * packSize) + loose);
              var totalQuantity = $('#Tqty-'+index).val();
              var computerCount = $('#qty-' + index).attr('value');
              if(!isNaN(computerCount) && !isNaN(totalQuantity) && computerCount != "" && totalQuantity != "") {
                  var variance = parseInt(totalQuantity) - parseInt(computerCount);
                  $('#variance-' + index).attr('value', variance);
                  $('#variance-' + index).html(variance);
              }
        });
    }
    if(!adjust) {
        $('.adjustColumn').hide();
        $('.saveAdjustment').hide();
        $('.saveVariance').show();
    } else {
        $('.adjustColumn').show();
        $('.saveAdjustment').show();
        $('.saveVariance').hide();
    }
}
function getStockVarianceRow(index, batch) {
    var html = '<tr id="stockTakingTable-' + index + '" index="' + index + '">' +
        '<td><label name="index" class="index" index="' + index + '">' + (index + 1 ) + '</label></td><td style="min-width: 200px;">';
var packsize = 0;
    for(var i = 0; i < window.mainDrugList.length; i++) {
        if( window.mainDrugList[i].id == batch.drugId) {
            html += '' + window.mainDrugList[i].name + '';
             packsize = window.mainDrugList[i].packSize;
            break;
        }
    }
    var date = new Date(batch.expiryDate);
    var prettyDate =(date.getMonth() + 1) + '/' + date.getDate() + '/' +
        date.getFullYear();
    html += '</select></td>' +
        '<td style="width: 50px;text-align: right;" id="packSize-'+index+'" value="'+packsize+'" >' +packsize+'</td>'+
        '<td style="width: 50px;text-align: right;" id="qty-' + index + '" value="' + batch.systemCount + '">' + batch.systemCount + '</td>' +
        '<td style="width: 120px;text-align: right;">' + batch.batchNo + '</td><td style="width: 120px;text-align: right;">' + prettyDate + '</td>' +
        '<td style="width: 120px"><input type="number" style="text-align: right;" class="number" id="packs-' + index + '"/></td>' +
        '<td style="width: 120px"><input type="number" style="text-align: right;" class="number" id="looseQty-' + index + '"/></td>' +
        '<td style="width: 120px"><input type="number" style="text-align: right;" class="number" disabled id="Tqty-' + index + '"/></td>' +
        '<td id="variance-' + index + '"></td>><td class="adjustColumn"><input name="adjustments" type="checkbox" id="adjust-' + index + '" ' + (batch.adjusted == true? 'disabled' : '') + '/></td>td></tr>';
    return html;
}

function clearTable(tableId) {
    var table = document.getElementById(tableId);
    var rowCount = table.rows.length;
    for(var i = (rowCount -1); i >= 1; i--) {
        table.deleteRow(i);
    }
}

function deleteRow(event, table) {
    try {
        var index = $(event.target).parents('tr').attr('index');
        console.log(index);
        var deleteIndex = -1;
        var table = document.getElementById(table);
        var rowCount = table.rows.length;

        for(var i = 0; i < rowCount; i++) {
            if(i == 0)
                continue;
            var row = table.rows[i];
            var countLabel = row.cells[0].childNodes[0];
            console.log($(countLabel).attr('index'));
            if($(countLabel).attr('index') == index) {
                deleteIndex = i;
            }
            if(i > index) {
                var row = table.rows[i];
                var id = row.id;
                id = id.substring(0, id.length - 1);
                id = id + (i - 1);
                row.setAttribute('id', id);
                row.setAttribute('index', i - 1);
                for(var x = 0; x < row.cells.length; x++) {
                    var item = row.cells[x].childNodes[0];
                    if(item == undefined)
                        item = row.cells[x];
                    if($(item).attr('class') == 'index') {
                        item.innerHTML = (i - 1);
                        item.setAttribute('index', (i - 1));
                    }
                    if(x == 1) {
                        item = item.childNodes[0];
                    }
                    id = item.id;
                    id = id.substring(0, id.length - 1);
                    id = id + (i - 1);
                    item.setAttribute('id', id);
                }
            } else {
                console.log('skipping');
            }
        }
        console.log(deleteIndex)
        if(deleteIndex != -1) {
            table.deleteRow(deleteIndex);
        }
    } catch(e) {
        alert(e);
    }
}

function addSatelliteRow() {
    if($('#satelliteServiceType').val() == "") {
        alert("Select the service type");
        return;
    }
    var satelliteTable = document.getElementById("satelliteTable");
    var rows = satelliteTable.rows.length;
    var html = getSatelliteRow(rows);
    $('#satelliteTable > tbody:last').append(html);

    $('#st-commodity-' + rows).flexselect();
    $('#st-commodity-' + rows).change(function() {
        var index = $(this).parents('tr').attr('index');
        /**
         *  set the batch number to select
         */

    });

}

function getSatelliteRow(index) {
    var html = '<tr id="satelliteTable-' + index + '" index="' + index + '"><td><label name="index" class="index">' + index + '</label></td><td style="min-width: 300px;"><select name="drug" class="drugs" id="st-commodity-' + index + '"><option value=""></option>';

    for(var i = 0; i < window.mainDrugList.length; i++) {
        if(window.drugIds[window.mainDrugList[i].id] == true) {
            html += '<option value="' + window.mainDrugList[i].id + '">' + window.mainDrugList[i].name + '</option>';
        }
    }

    html += '</select></td><td style="width: 50px;"></td>' +
        '<td style="width: 120px;"><input type="text" name="bf" id="bf-' + index + '"/></td><td style="width: 120px;"><input type="text" name="bf" id="received-' + index + '"/></td>' +
        '<td style="width: 120px;"><input type="text" name="price" id="returns-' + index + '"/></td>' +
        '<td style="width: 80px;"><input type="text" name="bf" id="adj1-' + index + '"/></td><td style="width: 80px;"><input type="text" name="bf" id="adj2-' + index + '"/></td>'+
        '<td style="width: 80px;"><input type="text" name="bf" id="dispensed-' + index + '"/></td><td style="width: 80px;"><input type="text" name="bf" id="losses-' + index + '"/></td>' +
        '<td style="width: 80px;"><input type="text" name="bf" id="dos-' + index + '"/></td>' +
        '<td style="width: 80px;"><input type="text" name="bf" id="soh-' + index + '"/></td><td style="width: 80px;"><input type="text" name="bf" id="sds-' + index + '"/></td>' +
        '<td style="width: 80px;"><input type="text" name="bf" id="requested-' + index + '"/></td></tr>';
    return html;
}

function addPatientServiceRow(all) {
    var patientServiceType = document.getElementById("patientServiceTypeTable");
    var rows = patientServiceType.rows.length;
    var html = getPatientServiceRow(rows, all);
    $('#patientServiceTypeTable > tbody:last').append(html);
    $('#startDate-' + rows).datepicker({ maxDate: new Date(), dateFormat: 'dd/mm/yy', changeMonth: true,changeYear: true});
    $('#endDate-' + rows).datepicker({ maxDate: new Date(), dateFormat: 'dd/mm/yy', changeMonth: true,changeYear: true});
    $("#serviceTypeId-" + rows).change(function() {
        var id = $(this).val();
        var patientServiceType = document.getElementById("patientServiceTypeTable");
        var rows = patientServiceType.rows.length;
        for(var i = 1; i < rows; i++) {
            var sId = $("#serviceTypeId-" + i).val();
            if(sId == id && $(this).attr("id") != "serviceTypeId-" + i) {
                alert("Patient already added to selected service");
                $(this).val("");
                return;
            }
        }
    });
    $('#startDate-' + rows).change(function() {
        var val = $(this).val();
        var enrolled = $("#dateEnrolled").val();

        var enrolleddate = new Date(formatDate(enrolled)); // some mock date
        var enrolledTime = enrolleddate.getTime();

        var startDate = new Date(formatDate(val)); // some mock date
        var startTime = startDate.getTime();

        if(enrolled != "" && enrolledTime > startTime ) {
            alert("Date started cannot be before date enrolled.")
            $(this).val("");
        }
    });
}

function getPatientServiceRow(index, all) {
    var html = "";
    html += "<tr id='patientServiceTypeTable-" + index + "'><td><label name='index' class='index'>" + index + '</label></td><td><select id="serviceTypeId-' + index + '"><option value="">Select one</option>';

    for(var i = 0; window.serviceTypes != null && i < window.serviceTypes.length; i++) {
        if(all) {
            html += "<option value='" + window.serviceTypes[i].id + "'>" + window.serviceTypes[i].name + "</option>";
        } else {
            if ($('#clinicTypeSelect').val() == "6" && window.serviceTypes[i].name == "Diabetic") {
                html += "<option value='" + window.serviceTypes[i].id + "'>" + window.serviceTypes[i].name + "</option>";
            } else if ($('#clinicTypeSelect').val() == "2" && window.serviceTypes[i].name == "FP/RH") {
                html += "<option value='" + window.serviceTypes[i].id + "'>" + window.serviceTypes[i].name + "</option>";
            } else if ($('#clinicTypeSelect').val() == "1" && (window.serviceTypes[i].name == "ART" || window.serviceTypes[i].name == "PEP" || window.serviceTypes[i].name == "PMTCT" || window.serviceTypes[i].name == "OI Only")) {
                html += "<option value='" + window.serviceTypes[i].id + "'>" + window.serviceTypes[i].name + "</option>";
            }
        }
    }

    html += "</select></td><td><input type='text' id='startDate-" + index + "'/></td><td><input type='text' id='endDate-" + index + "'/></td>";
    html += '<td><button class="pointer patientServiceTypeTableDelete-' + index + '" title="Delete" onclick="deleteRow(event, \'patientServiceTypeTable\')"><span>x</span></button></td>';
    return html;
}

function initPatientServiceTable(data) {
    clearTable("patientServiceTypeTable");
    var patientServiceType = document.getElementById("patientServiceTypeTable");
    for(var i = 0; i < data.length; i++) {
        var rows = patientServiceType.rows.length;
        addPatientServiceRow(true);
        var st = data[i];
        $('#patientServiceTypeTable-' + rows).data("pst", st);
        $('#startDate-' + rows).val(getDate(st.startDate));
        $('#startDate-' + rows).attr("disabled", true);
        $('#serviceTypeId-' + rows).val(st.serviceTypeId);
        $('#serviceTypeId-' + rows).attr("disabled", true);
        $('.patientServiceTypeTableDelete-' + rows).attr("disabled", true);
    }
}

