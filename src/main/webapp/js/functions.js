/**
 * Created by kenny on 4/10/14.
 */

window.supportingOrganization = null;
window.serviceTypes = null;
window.patientSources = null;
window.district = null;
window.regions = null;
window.patientStatus = null;
window.indication = null;
window.regimen = null; //List of regimen consisting of name and id
window.visitType = null;
window.regimen = null;
window.regimenChangeReason = null;
window.regimenType = null;
window.regimenStatus = null;
window.genericName = null;
window.drugCategory = null;
window.drugType = null;
window.drugForm = null;
window.dispensingUnit = null;
window.dosage = null;
window.transactionType = null;
window.accounts = null;
window.drugList = null;  // List of Drugs used for dispensing UI. This is modified for use with jQuery auto complete
window.accountType = null;
window.batchList = null; //List of batches
window.regimenDrugList = null;
window.identifierTypeList = null;

window.cdrrCategory = null;

window.selectedService = null;

window.service = null;

window.mainDrugList = null;  //List of drugs
window.mainServiceList = null;  //List of Services

window.patient = null;
window.personAddress = null;
window.person = null;
window.patientIdentifier = null;
window.patientServiceTypeList = null;

window.stockTakingItemList = null;
window.stockTakeSheet = null;

window.txType = null; // Transaction types for use when creating drop down for jTable.

window.regimenObjs = null;  // Stores list of regimen from the server which can be edited and deleted.

window.storeList = null;  //List of stores

window.drugIds = null;

window.binCards = {};

window.clinic = null;

window.familyPlanningMethod = null;

window.familyPlanningMethodChangeReasons = null;

window.insulinList = null;

window.bloodSugarlevelList = null;

window.facilities = null;

window.txSelectBatch = false; // Whether to select batch when Recording transactions

window.supplierAccounts = new Array();

window.labItems = false;

window.persons = new Array();

window.patientIdentifiers = null;
window.patientIdents = null;
/**
 *  Listens for ajax errors.
 */

function initTransactionTypeDialog() {

        $.ajax({
            url: 'reference/json/listTransactionType',
            headers: {
                "Content-Type": "application/json"
            },
            type: 'POST',
            success: function (data) {
                if(data.Records != undefined) {
                    var tempRec = data.Records;
                    window.txType = new Array();
                    for(var i = 0; i < tempRec.length; i++) {
                        window.txType.push({ Value : tempRec[i].id, DisplayText : tempRec[i].name})
                    }
                    showAccountTypeDialog();
                }

            },
            error: function () {

            }
        });

}

function showAccountTypeDialog() {

    $('#accountTypeContainer').jtable({
        title: 'Account Types',
        actions: {
            listAction: function (postData, jtParams) {
                return $.Deferred(function ($dfd) {
                    $.ajax({
                        url: 'reference/json/accountTypeList',
                        type: 'POST',
                        dataType: 'json',
                        data: postData,
                        success: function (data) {
                            var records = data.Records;
                            var i = 0;
                            while (i < records.length) {
                                var accType = records[i].accountTypeTransactionTypesById;
                                var j = 0;
                                while (j < accType.length) {
                                    accType[j] = accType[j].transactionTypeId;
                                    j++;
                                }
                                i++;
                            }
                            $dfd.resolve(data);
                        },
                        error: function () {
                            $dfd.reject();
                        }
                    });
                });
            },
            createAction: function (postData) {
                var txTypes = $('#Edit-accountTypeTransactionTypesById').val();
                var txTypeIds = new Array();
                for(var i = 0; txTypes != null && i < txTypes.length; i++) {
                    txTypeIds.push({transactionTypeId : txTypes[i]});
                }
                var json = "{\"accountType\" : {\"name\" : \"" + $('#Edit-name').val() + "\", \"description\" : \"" + $('#Edit-description').val() + "\", \"createdBy\" : " + window.user_id + ", \"accountTypeTransactionTypesById\" : " + JSON.stringify(txTypeIds) + "}}";

                return $.Deferred(function ($dfd) {
                    $.ajax({
                        url: 'reference/json/saveAccountType',
                        headers: {
                            "Content-Type": "application/json"
                        },
                        type: 'POST',
                        data: json,
                        success: function (data) {
                            var record = data.Record;

                            var accType = record.accountTypeTransactionTypesById;
                            var j = 0;
                            while (j < accType.length) {
                                accType[j] = accType[j].transactionTypeId;
                                j++;
                            }

                            $dfd.resolve(data);
                        },
                        error: function () {
                            $dfd.reject();
                        }
                    });
                });
            },
            updateAction: function (postData) {
                var txTypes = $('#Edit-accountTypeTransactionTypesById').val();
                var txTypeIds = new Array();
                for(var i = 0; i < txTypes.length; i++) {
                    txTypeIds.push({transactionTypeId : txTypes[i]});
                }
                var json = "{\"accountType\" : {\"id\": \"" + $('#Edit-id').val() + "\", \"name\" : \"" + $('#Edit-name').val() + "\", \"description\" : \"" + $('#Edit-description').val() + "\", \"updatedBy\" : \"" + window.user_id + "\", \"accountTypeTransactionTypesById\" : " + JSON.stringify(txTypeIds) + "}}";

                return $.Deferred(function ($dfd) {
                    $.ajax({
                        url: 'reference/json/updateAccountType',
                        headers: {
                            "Content-Type": "application/json"
                        },
                        type: 'POST',
                        data: json,
                        success: function (data) {
                            var record = data.Record;


                            var accType = record.accountTypeTransactionTypesById;
                            var j = 0;
                            while (j < accType.length) {
                                accType[j] = accType[j].transactionTypeId;
                                j++;
                            }


                            $dfd.resolve(data);
                        },
                        error: function () {
                            $dfd.reject();
                        }
                    });
                });
            },
            deleteAction: function (postData) {
                return $.Deferred(function ($dfd) {
                    $.ajax({
                        url: 'reference/json/deleteAccountType/' + postData.id + '/' + window.user_id,
                        headers: {
                            "Content-Type": "application/json"
                        },
                        type: 'POST',
                        success: function (data) {
                            $dfd.resolve(data);
                        },
                        error: function () {
                            $dfd.reject();
                        }
                    });
                });
            }
        },
        fields: {
            id: {
                key: true,
                list: false
            },
            name: {
                title: 'Name',
                width: '30%'
            },
            description: {
                title: 'Description',
                type: 'textarea',
                width: '60%'
            },
            accountTypeTransactionTypesById: {
                title: 'Transaction Types',
                type : 'multiselectddl',
                options: window.txType,
                list: false
            }
        },
        formSubmitting: function(event, data) {
            if($('#Edit-name').val() == "") {
                alert("Enter the name");
                return false;
            }
            return true;
        },
        recordDelete: function(event, data) {
            alert('deleteing');
        }
    });
    $('#accountTypeContainer').jtable('load');
    $("#accountTypeDialog").dialog("open");
}

function showRegimenTypeDialog() {

    $('#regimenTypeContainer').jtable({
        title: 'Regimen Types',
        actions: {
            listAction: 'reference/json/listRegimenType',
            createAction: function (postData) {
                var json = "{\"regimenType\" : {\"name\" : \"" + $('#Edit-name').val() + "\", \"description\" : \"" + $('#Edit-description').val() + "\", \"createdBy\" : " + window.user_id + "}}";

                return $.Deferred(function ($dfd) {
                    $.ajax({
                        url: 'reference/json/saveRegimenType',
                        headers: {
                            "Content-Type": "application/json"
                        },
                        type: 'POST',
                        data: json,
                        success: function (data) {
                            $dfd.resolve(data);
                        },
                        error: function () {
                            $dfd.reject();
                        }
                    });
                });
            },
            updateAction: function (postData) {
                var json = "{\"regimenType\" : {\"id\": \"" + $('#Edit-id').val() + "\", \"name\" : \"" + $('#Edit-name').val() + "\", \"description\" : \"" + $('#Edit-description').val() + "\", \"updatedBy\" : \"" + window.user_id + "\"}}";

                return $.Deferred(function ($dfd) {
                    $.ajax({
                        url: 'reference/json/updateRegimenType',
                        headers: {
                            "Content-Type": "application/json"
                        },
                        type: 'POST',
                        data: json,
                        success: function (data) {
                            $dfd.resolve(data);
                        },
                        error: function () {
                            $dfd.reject();
                        }
                    });
                });
            },
            deleteAction: function (postData) {
                return $.Deferred(function ($dfd) {
                    $.ajax({
                        url: 'reference/json/deleteRegimenType/' + postData.id + '/' + window.user_id,
                        headers: {
                            "Content-Type": "application/json"
                        },
                        type: 'POST',
                        success: function (data) {
                            $dfd.resolve(data);
                        },
                        error: function () {
                            $dfd.reject();
                        }
                    });
                });
            }
        },
        fields: {
            id: {
                key: true,
                list: false
            },
            name: {
                title: 'Name',
                width: '30%'
            },
            description: {
                title: 'Description',
                type: 'textarea',
                width: '60%'
            }
        },
        formSubmitting: function(event, data) {
            if($('#Edit-name').val() == "") {
                alert("Enter the name");
                return false;
            }
            return true;
        }
    });
    $('#regimenTypeContainer').jtable('load');
    $("#regimenTypeDialog").dialog("open");
}

function showRegimenStatusDialog() {

    $('#regimenStatusContainer').jtable({
        title: 'Regimen Status',
        actions: {
            listAction: 'reference/json/listRegimenStatus',
            createAction: function (postData) {
                var json = "{\"regimenStatus\" : {\"name\" : \"" + $('#Edit-name').val() + "\", \"description\" : \"" + $('#Edit-description').val() + "\", \"createdBy\" : " + window.user_id + "}}";

                return $.Deferred(function ($dfd) {
                    $.ajax({
                        url: 'reference/json/createRegimenStatus',
                        headers: {
                            "Content-Type": "application/json"
                        },
                        type: 'POST',
                        data: json,
                        success: function (data) {
                            $dfd.resolve(data);
                        },
                        error: function () {
                            $dfd.reject();
                        }
                    });
                });
            },
            updateAction: function (postData) {
                var json = "{\"regimenStatus\" : {\"id\": \"" + $('#Edit-id').val() + "\", \"name\" : \"" + $('#Edit-name').val() + "\", \"description\" : \"" + $('#Edit-description').val() + "\", \"updatedBy\" : \"" + window.user_id + "\"}}";

                return $.Deferred(function ($dfd) {
                    $.ajax({
                        url: 'reference/json/updateRegimenStatus',
                        headers: {
                            "Content-Type": "application/json"
                        },
                        type: 'POST',
                        data: json,
                        success: function (data) {
                            $dfd.resolve(data);
                        },
                        error: function () {
                            $dfd.reject();
                        }
                    });
                });
            },
            deleteAction: function (postData) {
                return $.Deferred(function ($dfd) {
                    $.ajax({
                        url: 'reference/json/deleteRegimenStatus/' + postData.id + '/' + window.user_id,
                        headers: {
                            "Content-Type": "application/json"
                        },
                        type: 'POST',
                        success: function (data) {
                            $dfd.resolve(data);
                        },
                        error: function () {
                            $dfd.reject();
                        }
                    });
                });
            }
        },
        fields: {
            id: {
                key: true,
                list: false
            },
            name: {
                title: 'Name',
                width: '30%'
            },
            description: {
                title: 'Description',
                type: 'textarea',
                width: '60%'
            }
        },
        formSubmitting: function(event, data) {
            if($('#Edit-name').val() == "") {
                alert("Enter the name");
                return false;
            }
            return true;
        }
    });
    $('#regimenStatusContainer').jtable('load');
    $("#regimenStatusDialog").dialog("open");
}
function showadherence(){
    $('#reportDialog').modal('hide');
    $('#adherence').dialog('open');


}
function showfacilityappointment() {
    $('#facilityappointment').dialog('open');

}
function showadherencemonitoring() {
    $('#adherencemonitoring1').dialog('open');

}

function showDosageDialog() {

    $('#dosageContainer').jtable({
        title: 'Dosage',
        actions: {
            listAction: 'reference/json/dosageList',
            createAction: function (postData) {
                var json = "{\"dosage\" : {\"name\" : \"" + $('#Edit-name').val() + "\", \"description\" : \"" + $('#Edit-description').val() + "\", \"createdBy\" : " + window.user_id + "}}";

                return $.Deferred(function ($dfd) {
                    $.ajax({
                        url: 'reference/json/createDosage',
                        headers: {
                            "Content-Type": "application/json"
                        },
                        type: 'POST',
                        data: json,
                        success: function (data) {
                            $dfd.resolve(data);
                            reloadDosage();
                        },
                        error: function () {
                            $dfd.reject();
                        }
                    });
                });
            },
            updateAction: function (postData) {
                var json = "{\"dosage\" : {\"id\": \"" + $('#Edit-id').val() + "\", \"name\" : \"" + $('#Edit-name').val() + "\", \"description\" : \"" + $('#Edit-description').val() + "\", \"updatedBy\" : \"" + window.user_id + "\"}}";

                return $.Deferred(function ($dfd) {
                    $.ajax({
                        url: 'reference/json/updateDosage',
                        headers: {
                            "Content-Type": "application/json"
                        },
                        type: 'POST',
                        data: json,
                        success: function (data) {
                            $dfd.resolve(data);
                            reloadDosage();
                        },
                        error: function () {
                            $dfd.reject();
                        }
                    });
                });
            },
            deleteAction: function (postData) {
                return $.Deferred(function ($dfd) {
                    $.ajax({
                        url: 'reference/json/deleteDosage/' + postData.id + '/' + window.user_id,
                        headers: {
                            "Content-Type": "application/json"
                        },
                        type: 'POST',
                        success: function (data) {
                            $dfd.resolve(data);
                            reloadDosage();
                        },
                        error: function () {
                            $dfd.reject();
                        }
                    });
                });
            }
        },
        fields: {
            id: {
                key: true,
                list: false
            },
            name: {
                title: 'Name',
                width: '30%'
            },
            description: {
                title: 'Description',
                type: 'textarea',
                width: '60%'
            }
        },
        formSubmitting: function(event, data) {
            if($('#Edit-name').val() == "") {
                alert("Enter the name");
                return false;
            }
            return true;
        }
    });
    $('#dosageContainer').jtable('load');
    $("#dosageDialog").dialog("open");
}

function showDispensingUnitDialog() {

    $('#dispensingUnitContainer').jtable({
        title: 'Dispensing Unit',
        actions: {
            listAction: 'reference/json/listDispensingUnit',
            createAction: function (postData) {
                var json = "{\"dispensingUnit\" : {\"name\" : \"" + $('#Edit-name').val() + "\", \"description\" : \"" + $('#Edit-description').val() + "\", \"minimum\" : \"" + $('#Edit-minimum').val() + "\", \"maximum\" : \"" + $('#Edit-maximum').val() + "\",\"createdBy\" : " + window.user_id + "}}";

                return $.Deferred(function ($dfd) {
                    $.ajax({
                        url: 'reference/json/createDispensingUnit',
                        headers: {
                            "Content-Type": "application/json"
                        },
                        type: 'POST',
                        data: json,
                        success: function (data) {
                            $dfd.resolve(data);
                            reloadDispensingUnit();
                        },
                        error: function () {
                            $dfd.reject();
                        }
                    });
                });
            },
            updateAction: function (postData) {
                var json = "{\"dispensingUnit\" : {\"id\": \"" + $('#Edit-id').val() + "\", \"name\" : \"" + $('#Edit-name').val() + "\", \"description\" : \"" + $('#Edit-description').val() + "\", \"minimum\" : \"" + $('#Edit-minimum').val() + "\", \"maximum\" : \"" + $('#Edit-maximum').val() + "\", \"updatedBy\" : \"" + window.user_id + "\"}}";

                return $.Deferred(function ($dfd) {
                    $.ajax({
                        url: 'reference/json/updateDispensingUnit',
                        headers: {
                            "Content-Type": "application/json"
                        },
                        type: 'POST',
                        data: json,
                        success: function (data) {
                            $dfd.resolve(data);
                            reloadDispensingUnit();
                        },
                        error: function () {
                            $dfd.reject();
                        }
                    });
                });
            },
            deleteAction: function (postData) {
                return $.Deferred(function ($dfd) {
                    $.ajax({
                        url: 'reference/json/deleteDispensingUnit/' + postData.id + '/' + window.user_id,
                        headers: {
                            "Content-Type": "application/json"
                        },
                        type: 'POST',
                        success: function (data) {
                            $dfd.resolve(data);
                            reloadDispensingUnit();
                        },
                        error: function () {
                            $dfd.reject();
                        }
                    });
                });
            }
        },
        fields: {
            id: {
                key: true,
                list: false
            },
            name: {
                title: 'Name',
                width: '30%'
            },
            description: {
                title: 'Description',
                type: 'textarea',
                width: '60%'
            },
            minimum: {
                title: 'Minimum',
                width: '20%'
            },
            maximum: {
                title: 'Maximum',
                width: '20%'
            }
        },
        formSubmitting: function(event, data) {
            if($('#Edit-name').val() == "") {
                alert("Enter the name");
                return false;
            }
            if($('#Edit-minimum').val() == "") {
                alert("Enter the minimum dispensing unit.");
                return false;
            }
            if(isNaN($('#Edit-minimum').val())) {
                alert("Minimum dispensing unit must be a number.");
                return false;
            }
            if($('#Edit-maximum').val() == "") {
                alert("Enter the maximum dispensing unit.");
                return false;
            }
            if(isNaN($('#Edit-maximum').val())) {
                alert("maximum dispensing unit must be a number.");
                return false;
            }
            return true;
        }
    });
    $('#dispensingUnitContainer').jtable('load');
    $("#dispensingUnitDialog").dialog("open");
}

function showGenericNameDialog() {

    $('#genericNameContainer').jtable({
        title: 'Generic Name',
        actions: {
            listAction: 'reference/json/listGenericName',
            createAction: function (postData) {
                var json = "{\"genericName\" : {\"name\" : \"" + $('#Edit-name').val() + "\", \"createdBy\" : " + window.user_id + "}}";

                    return $.Deferred(function ($dfd) {
                        $.ajax({
                            url: 'reference/json/createGenericName',
                            headers: {
                                "Content-Type": "application/json"
                            },
                            type: 'POST',
                            data: json,
                            success: function (data) {
                                $dfd.resolve(data);
                                reloadGenericName();
                            },
                            error: function () {
                                $dfd.reject();
                            }
                        });
                    });

            },
            updateAction: function (postData) {
                var json = "{\"genericName\" : {\"id\": \"" + $('#Edit-id').val() + "\", \"name\" : \"" + $('#Edit-name').val() + "\", \"updatedBy\" : \"" + window.user_id + "\"}}";

                return $.Deferred(function ($dfd) {
                    $.ajax({
                        url: 'reference/json/updateGenericName',
                        headers: {
                            "Content-Type": "application/json"
                        },
                        type: 'POST',
                        data: json,
                        success: function (data) {
                            $dfd.resolve(data);
                            reloadGenericName();
                        },
                        error: function () {
                            $dfd.reject();
                        }
                    });
                });
            },
            deleteAction: function (postData) {
                return $.Deferred(function ($dfd) {
                    $.ajax({
                        url: 'reference/json/deleteGenericName/' + postData.id + '/' + window.user_id,
                        headers: {
                            "Content-Type": "application/json"
                        },
                        type: 'POST',
                        success: function (data) {
                            $dfd.resolve(data);
                            reloadGenericName();
                        },
                        error: function () {
                            $dfd.reject();
                        }
                    });
                });
            }
        },
        fields: {
            id: {
                key: true,
                list: false
            },
            name: {
                title: 'Name',
                width: '50%'
            }
        },
        formSubmitting: function(event, data) {
            if($('#Edit-name').val() == "") {
                alert("Enter the name");
                return false;
            }
            return true;
        }

    });
    $('#genericNameContainer').jtable('load');
    $("#genericNameDialog").dialog("open");
}

function showIdentifierTypeDialog() {

    $('#identifierTypeContainer').jtable({
        title: 'Identifier type',
        actions: {
            listAction: 'reference/json/listIdentifierType',
            createAction: function (postData) {
                var json = "{\"identifierType\" : {\"name\" : \"" + $('#Edit-name').val() + "\", \"description\" : \"" + $('#Edit-description').val() + "\", \"required\" : \"" + $('input[name=required]:checked').val() + "\", \"createdBy\" : " + window.user_id + "}}";

                return $.Deferred(function ($dfd) {
                    $.ajax({
                        url: 'reference/json/createIdentifierType',
                        headers: {
                            "Content-Type": "application/json"
                        },
                        type: 'POST',
                        data: json,
                        success: function (data) {
                            $dfd.resolve(data);
                        },
                        error: function () {
                            $dfd.reject();
                        }
                    });
                });
            },
            updateAction: function (postData) {
                var json = "{\"identifierType\" : {\"id\": \"" + $('#Edit-id').val() + "\", \"name\" : \"" + $('#Edit-name').val() + "\", \"description\" : \"" + $('#Edit-description').val() + "\", \"required\" : \"" + $('input[name=required]:checked').val() + "\", \"updatedBy\" : \"" + window.user_id + "\"}}";

                return $.Deferred(function ($dfd) {
                    $.ajax({
                        url: 'reference/json/updateIdentifierType',
                        headers: {
                            "Content-Type": "application/json"
                        },
                        type: 'POST',
                        data: json,
                        success: function (data) {
                            $dfd.resolve(data);
                        },
                        error: function () {
                            $dfd.reject();
                        }
                    });
                });
            },
            deleteAction: function (postData) {
                return $.Deferred(function ($dfd) {
                    $.ajax({
                        url: 'reference/json/deleteIdentifierType/' + postData.id + '/' + window.user_id,
                        headers: {
                            "Content-Type": "application/json"
                        },
                        type: 'POST',
                        success: function (data) {
                            $dfd.resolve(data);
                        },
                        error: function () {
                            $dfd.reject();
                        }
                    });
                });
            }
        },
        fields: {
            id: {
                key: true,
                list: false
            },
            name: {
                title: 'Name',
                width: '30%'
            },
            description: {
                title: 'Description',
                type: 'textarea',
                width: '60%'
            },
            required: {
                title:"Required",
                type: 'radiobutton',
                width: '30%',
                options: { '1': 'Yes', '0': 'No' }
            }
        },
        formSubmitting: function(event, data) {
            if($('#Edit-name').val() == "") {
                alert("Enter the name");
                return false;
            }
            return true;
        }
    });
    $('#identifierTypeContainer').jtable('load');
    $("#identifierTypeDialog").dialog("open");
}

function showLocationDialog() {

    $('#locationContainer').jtable({
        title: 'Locations',
        actions: {
            listAction: 'reference/json/listLocation',
            createAction: function (postData) {
                var json = "{\"location\" : {\"name\" : \"" + $('#Edit-name').val() + "\", \"county\" : \"" + $('#Edit-county').val() + "\", \"region\" : \"" + $('#Edit-region').val() + "\"}}";

                return $.Deferred(function ($dfd) {
                    $.ajax({
                        url: 'reference/json/createLocation',
                        headers: {
                            "Content-Type": "application/json"
                        },
                        type: 'POST',
                        data: json,
                        success: function (data) {
                            $dfd.resolve(data);
                        },
                        error: function () {
                            $dfd.reject();
                        }
                    });
                });
            },
            updateAction: function (postData) {
                var json = "{\"location\" : {\"id\": \"" + $('#Edit-id').val() + "\", \"name\" : \"" + $('#Edit-name').val() + "\", \"county\" : \"" + $('#Edit-county').val() + "\", \"region\" : \"" + $('#Edit-region').val() + "\"}}";

                return $.Deferred(function ($dfd) {
                    $.ajax({
                        url: 'reference/json/updateLocation',
                        headers: {
                            "Content-Type": "application/json"
                        },
                        type: 'POST',
                        data: json,
                        success: function (data) {
                            $dfd.resolve(data);
                        },
                        error: function () {
                            $dfd.reject();
                        }
                    });
                });
            },
            deleteAction: function (postData) {
                return $.Deferred(function ($dfd) {
                    $.ajax({
                        url: 'reference/json/deleteLocation/' + postData.id + '/' + window.user_id,
                        headers: {
                            "Content-Type": "application/json"
                        },
                        type: 'POST',
                        success: function (data) {
                            $dfd.resolve(data);
                        },
                        error: function () {
                            $dfd.reject();
                        }
                    });
                });
            }
        },
        fields: {
            id: {
                key: true,
                list: false
            },
            name: {
                title: 'Name',
                width: '30%'
            },
            county: {
                title: 'County',
                width: '30%'
            },
            region: {
                title: "Region",
                width: "30%"
            }
        },
        formSubmitting: function(event, data) {
            if($('#Edit-name').val() == "") {
                alert("Enter the name");
                return false;
            }
            return true;
        }
    });
    $('#locationContainer').jtable('load');
    $("#locationDialog").dialog("open");
}

function showPatientSourceDialog() {

    $('#patientSourceContainer').jtable({
        title: 'Patient Source',
        actions: {
            listAction: 'reference/json/listPatientSource',
            createAction: function (postData) {
                var json = "{\"patientSource\" : {\"name\" : \"" + $('#Edit-name').val() + "\", \"description\" : \"" + $('#Edit-description').val() + "\", \"createdBy\" : " + window.user_id + "}}";
                if($('#Edit-name').val() == "") {
                    alert("Enter the name");
                    return;
                }
                return $.Deferred(function ($dfd) {
                    $.ajax({
                        url: 'reference/json/createPatientSource',
                        headers: {
                            "Content-Type": "application/json"
                        },
                        type: 'POST',
                        data: json,
                        success: function (data) {
                            $dfd.resolve(data);
                        },
                        error: function () {
                            $dfd.reject();
                        }
                    });
                });
            },
            updateAction: function (postData) {
                var json = "{\"patientSource\" : {\"id\": \"" + $('#Edit-id').val() + "\", \"name\" : \"" + $('#Edit-name').val() + "\", \"description\" : \"" + $('#Edit-description').val() + "\", \"updatedBy\" : " + window.user_id + "}}";
                if($('#Edit-name').val() == "") {
                    alert("Enter the name");
                    return;
                }
                return $.Deferred(function ($dfd) {
                    $.ajax({
                        url: 'reference/json/updatePatientSource',
                        headers: {
                            "Content-Type": "application/json"
                        },
                        type: 'POST',
                        data: json,
                        success: function (data) {
                            $dfd.resolve(data);
                        },
                        error: function () {
                            $dfd.reject();
                        }
                    });
                });
            },
            deleteAction: function (postData) {
                return $.Deferred(function ($dfd) {
                    $.ajax({
                        url: 'reference/json/deletePatientSource/' + postData.id + '/' + window.user_id,
                        headers: {
                            "Content-Type": "application/json"
                        },
                        type: 'POST',
                        success: function (data) {
                            $dfd.resolve(data);
                        },
                        error: function () {
                            $dfd.reject();
                        }
                    });
                });
            }
        },
        fields: {
            id: {
                key: true,
                list: false
            },
            name: {
                title: 'Name',
                width: '30%'
            },
            description: {
                title: 'Description',
                type: 'textarea',
                width: '60%'
            }
        },
        formSubmitting: function(event, data) {
            if($('#Edit-name').val() == "") {
                alert("Enter the name");
                return false;
            }
            return true;
        }
    });
    $('#patientSourceContainer').jtable('load');
    $("#patientSourceDialog").dialog("open");
}

function showServiceTypeDialog() {

    $('#serviceTypeContainer').jtable({
        title: 'Service type',
        actions: {
            listAction: 'reference/json/listServiceType',
            createAction: function (postData) {
                var json = "{\"serviceType\" : {\"name\" : \"" + $('#Edit-name').val() + "\"," +
                                                " \"description\" : \"" + $('#Edit-description').val() + "\"," +
                                                " \"service_area\" : \"" + $('#Edit-service_area').val() + "\"," +
                                                 " \"createdBy\" : " + window.user_id + "}}";
                if($('#Edit-name').val() == "") {
                    alert("Enter the name");
                    return;
                }if($('#Edit-service_area').val() == "") {
                    alert("Enter the Service area");
                    return;
                }
                return $.Deferred(function ($dfd) {
                    $.ajax({
                        url: 'reference/json/createServiceType',
                        headers: {
                            "Content-Type": "application/json"
                        },
                        type: 'POST',
                        data: json,
                        success: function (data) {
                            $dfd.resolve(data);
                            reloadServiceTypes();
                        },
                        error: function () {
                            $dfd.reject();
                        }
                    });
                });
            },
            updateAction: function (postData) {
                var json = "{\"serviceType\" : {\"id\": \"" + $('#Edit-id').val() + "\"," +
                                        " \"name\" : \"" + $('#Edit-name').val() + "\"," +
                                         " \"description\" : \"" + $('#Edit-description').val() + "\"," +
                                         " \"service_area\" : \"" + $('#Edit-service_area').val() + "\"," +
                                            " \"updatedBy\" : \"" + window.user_id + "\"}}";


                return $.Deferred(function ($dfd) {
                    $.ajax({
                        url: 'reference/json/updateServiceType',
                        headers: {
                            "Content-Type": "application/json"
                        },
                        type: 'POST',
                        data: json,
                        success: function (data) {
                            $dfd.resolve(data);
                            reloadServiceTypes();
                        },
                        error: function () {
                            $dfd.reject();
                        }
                    });
                });
            },
            deleteAction: function (postData) {
                return $.Deferred(function ($dfd) {
                    $.ajax({
                        url: 'reference/json/deleteServiceType/' + postData.id + '/' + window.user_id,
                        headers: {
                            "Content-Type": "application/json"
                        },
                        type: 'POST',
                        success: function (data) {
                            $dfd.resolve(data);
                            reloadServiceTypes();
                        },
                        error: function () {
                            $dfd.reject();
                        }
                    });
                });
            }
        },
        fields: {
            id: {
                key: true,
                list: false
            },
            name: {
                title: 'Name',
                width: '30%'
            },
            service_area: {
                title: 'Service Area',
                width: '30%',
                options: {'Family Planning Clinic': 'Family Planning Clinic', 'Diabetic Clinic': 'Diabetic Clinic', 'CCC Pharmacy': 'CCC Pharmacy','General Pharmacy':'General Pharmacy','Nutrition Clinic':'Nutrition Clinic','Lab':'Lab'}
            },
            description: {
                title: 'Description',
                type: 'textarea',
                width: '60%'
            }
        },
        formSubmitting: function(event, data) {
            if($('#Edit-name').val() == "") {
                alert("Enter the name");
                return false;
            }
            return true;
        }
    });
    $('#serviceTypeContainer').jtable('load');
    $("#serviceTypeDialog").dialog("open");
}

function showSupportingOrganizationDialog() {

    $('#supportingOrganizationContainer').jtable({
        title: 'Supporting organizations',
        actions: {
            listAction: 'reference/json/listSupportingOrganization',
            createAction: function (postData) {
                var json = "{\"supportingOrganization\" : {\"name\" : \"" + $('#Edit-name').val() + "\", \"description\" : \"" + $('#Edit-description').val() + "\", \"createdBy\" : " + window.user_id + "}}";

                return $.Deferred(function ($dfd) {
                    $.ajax({
                        url: 'reference/json/createSupportingOrganization',
                        headers: {
                            "Content-Type": "application/json"
                        },
                        type: 'POST',
                        data: json,
                        success: function (data) {
                            $dfd.resolve(data);
                        },
                        error: function () {
                            $dfd.reject();
                        }
                    });
                });
            },
            updateAction: function (postData) {
                var json = "{\"supportingOrganization\" : {\"id\": \"" + $('#Edit-id').val() + "\", \"name\" : \"" + $('#Edit-name').val() + "\", \"description\" : \"" + $('#Edit-description').val() + "\", \"updatedBy\" : \"" + window.user_id + "\"}}";

                return $.Deferred(function ($dfd) {
                    $.ajax({
                        url: 'reference/json/updateSupportingOrganization',
                        headers: {
                            "Content-Type": "application/json"
                        },
                        type: 'POST',
                        data: json,
                        success: function (data) {
                            $dfd.resolve(data);
                        },
                        error: function () {
                            $dfd.reject();
                        }
                    });
                });
            },
            deleteAction: function (postData) {
                return $.Deferred(function ($dfd) {
                    $.ajax({
                        url: 'reference/json/deleteSupportingOrganization/' + postData.id + '/' + window.user_id,
                        headers: {
                            "Content-Type": "application/json"
                        },
                        type: 'POST',
                        success: function (data) {
                            $dfd.resolve(data);
                        },
                        error: function () {
                            $dfd.reject();
                        }
                    });
                });
            }
        },
        fields: {
            id: {
                key: true,
                list: false
            },
            name: {
                title: 'Name',
                width: '30%'
            },
            description: {
                title: 'Description',
                type: 'textarea',
                width: '60%'
            }
        },
        formSubmitting: function(event, data) {
            if($('#Edit-name').val() == "") {
                alert("Enter the name");
                return false;
            }
            return true;
        }
    });
    $('#supportingOrganizationContainer').jtable('load');
    $("#supportingOrganizationDialog").dialog("open");
}

function showTransactionTypeDialog() {

    $('#transactionTypeContainer').jtable({
        title: 'Supporting organizations',
        actions: {
            listAction: 'reference/json/listTransactionType',
            createAction: function (postData) {
                var json = "{\"transactionType\" : {\"name\" : \"" + $('#Edit-name').val() + "\", \"description\" : \"" + $('#Edit-description').val() + "\", \"documentable\" : \"" + $('input[name=documentable]:checked').val() + "\", \"narrated\" : \"" + $('input[name=narrated]:checked').val() + "\", \"createdBy\" : " + window.user_id + "}}";

                return $.Deferred(function ($dfd) {
                    $.ajax({
                        url: 'reference/json/createTransactionType',
                        headers: {
                            "Content-Type": "application/json"
                        },
                        type: 'POST',
                        data: json,
                        success: function (data) {
                            $dfd.resolve(data);
                        },
                        error: function () {
                            $dfd.reject();
                        }
                    });
                });
            },
            updateAction: function (postData) {
                var json = "{\"transactionType\" : {\"id\": \"" + $('#Edit-id').val() + "\", \"name\" : \"" + $('#Edit-name').val() + "\", \"description\" : \"" + $('#Edit-description').val() + "\", \"documentable\" : \"" + $('input[name=documentable]:checked').val() + "\", \"narrated\" : \"" + $('input[name=narrated]:checked').val() + "\", \"updatedBy\" : \"" + window.user_id + "\"}}";

                return $.Deferred(function ($dfd) {
                    $.ajax({
                        url: 'reference/json/updateTransactionType',
                        headers: {
                            "Content-Type": "application/json"
                        },
                        type: 'POST',
                        data: json,
                        success: function (data) {
                            $dfd.resolve(data);
                        },
                        error: function () {
                            $dfd.reject();
                        }
                    });
                });
            },
            deleteAction: function (postData) {
                return $.Deferred(function ($dfd) {
                    $.ajax({
                        url: 'reference/json/deleteTransactionType/' + postData.id + '/' + window.user_id,
                        headers: {
                            "Content-Type": "application/json"
                        },
                        type: 'POST',
                        success: function (data) {
                            $dfd.resolve(data);
                        },
                        error: function () {
                            $dfd.reject();
                        }
                    });
                });
            }
        },
        fields: {
            id: {
                key: true,
                list: false
            },
            name: {
                title: 'Name',
                width: '30%'
            },
            description: {
                title: 'Description',
                type: 'textarea',
                width: '60%'
            },
            documentable: {
                title: 'Documentable',
                type: 'radiobutton',
                width: '30%',
                options: { '0': 'No', '1': 'Yes' }
            },
            narrated: {
                title: 'Narrated',
                type: 'radiobutton',
                width: '30%',
                options: { '0': 'No', '1': 'Yes' }
            }
        },
        formSubmitting: function(event, data) {
            if($('#Edit-name').val() == "") {
                alert("Enter the name");
                return false;
            }
            return true;
        }
    });
    $('#transactionTypeContainer').jtable('load');
    $("#transactionTypeDialog").dialog("open");
}

function showCDRRCategoryDialog() {

    $('#cdrrCategoryContainer').jtable({
        title: 'CDRR Category',
        actions: {
            listAction: 'reference/json/cdrrCatgoryList',
            createAction: function (postData) {
                var json = "{\"cdrrCategory\" : {\"name\" : \"" + $('#Edit-name').val() + "\", \"description\" : \"" + $('#Edit-description').val() + "\", \"createdBy\" : " + window.user_id + "}}";

                return $.Deferred(function ($dfd) {
                    $.ajax({
                        url: 'reference/json/createCDRRCategory',
                        headers: {
                            "Content-Type": "application/json"
                        },
                        type: 'POST',
                        data: json,
                        success: function (data) {
                            $dfd.resolve(data);
                            reloadCDRRCategory();
                        },
                        error: function () {
                            $dfd.reject();
                        }
                    });
                });
            },
            updateAction: function (postData) {
                var json = "{\"cdrrCategory\" : {\"id\": \"" + $('#Edit-id').val() + "\", \"name\" : \"" + $('#Edit-name').val() + "\", \"description\" : \"" + $('#Edit-description').val() + "\", \"updatedBy\" : \"" + window.user_id + "\"}}";

                return $.Deferred(function ($dfd) {
                    $.ajax({
                        url: 'reference/json/updateCDRRCategory',
                        headers: {
                            "Content-Type": "application/json"
                        },
                        type: 'POST',
                        data: json,
                        success: function (data) {
                            $dfd.resolve(data);
                            reloadCDRRCategory();
                        },
                        error: function () {
                            $dfd.reject();
                        }
                    });
                });
            },
            deleteAction: function (postData) {
                return $.Deferred(function ($dfd) {
                    $.ajax({
                        url: 'reference/json/deleteCDRRCategory/' + postData.id + '/' + window.user_id,
                        headers: {
                            "Content-Type": "application/json"
                        },
                        type: 'POST',
                        success: function (data) {
                            $dfd.resolve(data);
                            reloadCDRRCategory();
                        },
                        error: function () {
                            $dfd.reject();
                        }
                    });
                });
            }
        },
        fields: {
            id: {
                key: true,
                list: false
            },
            name: {
                title: 'Name',
                width: '30%'
            },
            description: {
                title: 'Description',
                type: 'textarea',
                width: '60%'
            }
        },
        formSubmitting: function(event, data) {
            if($('#Edit-name').val() == "") {
                alert("Enter the name");
                return false;
            }
            return true;
        }
    });
    $('#cdrrCategoryContainer').jtable('load');
    $("#cdrrCategoryDialog").dialog("open");
}

function showRegionDialog() {

    $('#regionContainer').jtable({
        title: 'Regions',
        actions: {
            listAction: 'reference/json/listRegion',
            createAction: function (postData) {
                var json = "{\"region\" : {\"name\" : \"" + $('#Edit-name').val() + "\", \"createdBy\" : " + window.user_id + "}}";

                return $.Deferred(function ($dfd) {
                    $.ajax({
                        url: 'reference/json/createRegion',
                        headers: {
                            "Content-Type": "application/json"
                        },
                        type: 'POST',
                        data: json,
                        success: function (data) {
                            $dfd.resolve(data);
                        },
                        error: function () {
                            $dfd.reject();
                        }
                    });
                });
            },
            updateAction: function (postData) {
                var json = "{\"region\" : {\"id\": \"" + $('#Edit-id').val() + "\", \"name\" : \"" + $('#Edit-name').val() + "\", \"updatedBy\" : \"" + window.user_id + "\"}}";

                return $.Deferred(function ($dfd) {
                    $.ajax({
                        url: 'reference/json/updateRegion',
                        headers: {
                            "Content-Type": "application/json"
                        },
                        type: 'POST',
                        data: json,
                        success: function (data) {
                            $dfd.resolve(data);
                        },
                        error: function () {
                            $dfd.reject();
                        }
                    });
                });
            },
            deleteAction: function (postData) {
                return $.Deferred(function ($dfd) {
                    $.ajax({
                        url: 'reference/json/deleteRegion/' + postData.id + '/' + window.user_id,
                        headers: {
                            "Content-Type": "application/json"
                        },
                        type: 'POST',
                        success: function (data) {
                            $dfd.resolve(data);
                        },
                        error: function () {
                            $dfd.reject();
                        }
                    });
                });
            }
        },
        fields: {
            id: {
                key: true,
                list: false
            },
            name: {
                title: 'Name',
                width: '30%'
            }
        },
        formSubmitting: function(event, data) {
            if($('#Edit-name').val() == "") {
                alert("Enter the name");
                return false;
            }
            return true;
        }
    });
    $('#regionContainer').jtable('load');
    $("#regionDialog").dialog("open");
}

/**
 *  This function initializes the District dialog. Since we need the regions from the server,
 *  we load them first if they are not loaded into the page.
 *  If they are already loaded, we just show the dialog.
 */
function initDistrictDialog() {
    if(window.regions == null) {
        $.ajax({
            url: 'reference/json/listRegion',
            headers: {
                "Content-Type": "application/json"
            },
            type: 'POST',
            success: function (data) {
                if(data.Records != undefined) {
                    var tempRec = data.Records;
                    window.regions = new Array();
                    for(var i = 0; i < tempRec.length; i++) {
                        window.regions.push({ Value : tempRec[i].id, DisplayText : tempRec[i].name})
                    }
                    showDistrictDialog();
                }

            },
            error: function () {

            }
        });
    } else {
        showDistrictDialog();
    }
}

function showDistrictDialog() {

    $('#districtContainer').jtable({
        title: 'Counties',
        actions: {
            listAction: 'reference/json/listDistrict',
            createAction: function (postData) {
                var json = "{\"district\" : {\"name\" : \"" + $('#Edit-name').val() + "\", \"code\" : \"" + $('#Edit-code').val() + "\", \"regionId\" : " + $('#Edit-regionId').val() + ", \"createdBy\" : " + window.user_id + "}}";

                return $.Deferred(function ($dfd) {
                    $.ajax({
                        url: 'reference/json/createDistrict',
                        headers: {
                            "Content-Type": "application/json"
                        },
                        type: 'POST',
                        data: json,
                        success: function (data) {
                            $dfd.resolve(data);
                        },
                        error: function () {
                            $dfd.reject();
                        }
                    });
                });
            },
            updateAction: function (postData) {
                var json = "{\"district\" : {\"id\": \"" + $('#Edit-id').val() + "\", \"name\" : \"" + $('#Edit-name').val() + "\", \"code\" : \"" + $('#Edit-code').val() + "\", \"regionId\" : " + $('#Edit-regionId').val() + ", \"updatedBy\" : \"" + window.user_id + "\"}}";

                return $.Deferred(function ($dfd) {
                    $.ajax({
                        url: 'reference/json/updateDistrict',
                        headers: {
                            "Content-Type": "application/json"
                        },
                        type: 'POST',
                        data: json,
                        success: function (data) {
                            $dfd.resolve(data);
                        },
                        error: function () {
                            $dfd.reject();
                        }
                    });
                });
            },
            deleteAction: function (postData) {
                return $.Deferred(function ($dfd) {
                    $.ajax({
                        url: 'reference/json/deleteDistrict/' + postData.id + '/' + window.user_id,
                        headers: {
                            "Content-Type": "application/json"
                        },
                        type: 'POST',
                        success: function (data) {
                            $dfd.resolve(data);
                        },
                        error: function () {
                            $dfd.reject();
                        }
                    });
                });
            }
        },
        fields: {
            id: {
                key: true,
                list: false
            },
            name: {
                title: 'Name',
                width: '30%'
            },
            code: {
                title: 'Code',
                width: '60%'
            },
            regionId: {
                title: "Region",
                options: window.regions
            }
        },
        formSubmitting: function(event, data) {
            if($('#Edit-name').val() == "") {
                alert("Enter the name");
                return false;
            }
            return true;
        }
    });
    $('#districtContainer').jtable('load');
    $("#districtDialog").dialog("open");
}

function showPatientStatusDialog() {
    $('#patientStatusContainer').jtable({
        title: 'Patient Status',
        actions: {
            listAction: 'reference/json/listPatientStatus',
            createAction: function (postData) {
                var json = "{\"patientStatus\" : {\"name\" : \"" + $('#Edit-name').val() + "\", \"createdBy\" : " + window.user_id + "}}";

                return $.Deferred(function ($dfd) {
                    $.ajax({
                        url: 'reference/json/createPatientStatus',
                        headers: {
                            "Content-Type": "application/json"
                        },
                        type: 'POST',
                        data: json,
                        success: function (data) {
                            $dfd.resolve(data);
                        },
                        error: function () {
                            $dfd.reject();
                        }
                    });
                });
            },
            updateAction: function (postData) {
                var json = "{\"patientStatus\" : {\"id\": \"" + $('#Edit-id').val() + "\", \"name\" : \"" + $('#Edit-name').val() + "\", \"updatedBy\" : \"" + window.user_id + "\"}}";

                return $.Deferred(function ($dfd) {
                    $.ajax({
                        url: 'reference/json/updatePatientStatus',
                        headers: {
                            "Content-Type": "application/json"
                        },
                        type: 'POST',
                        data: json,
                        success: function (data) {
                            $dfd.resolve(data);
                        },
                        error: function () {
                            $dfd.reject();
                        }
                    });
                });
            },
            deleteAction: function (postData) {
                return $.Deferred(function ($dfd) {
                    $.ajax({
                        url: 'reference/json/deletePatientStatus/' + postData.id + '/' + window.user_id,
                        headers: {
                            "Content-Type": "application/json"
                        },
                        type: 'POST',
                        success: function (data) {
                            $dfd.resolve(data);
                        },
                        error: function () {
                            $dfd.reject();
                        }
                    });
                });
            }
        },
        fields: {
            id: {
                key: true,
                list: false
            },
            name: {
                title: 'Name',
                width: '30%'
            }
        },
        formSubmitting: function(event, data) {
            if($('#Edit-name').val() == "") {
                alert("Enter the name");
                return false;
            }
            return true;
        }
    });
    $('#patientStatusContainer').jtable('load');
    $("#patientStatusDialog").dialog("open");
}

function showIndicationDialog() {
    $('#indicationContainer').jtable({
        title: 'Indications',
        actions: {
            listAction: 'reference/json/listIndication',
            createAction: function (postData) {
                var json = "{\"indication\" : {\"name\" : \"" + $('#Edit-name').val() + "\", \"description\" : \"" + $('#Edit-description').val() + "\", \"createdBy\" : " + window.user_id + "}}";

                return $.Deferred(function ($dfd) {
                    $.ajax({
                        url: 'reference/json/createIndication',
                        headers: {
                            "Content-Type": "application/json"
                        },
                        type: 'POST',
                        data: json,
                        success: function (data) {
                            $dfd.resolve(data);
                        },
                        error: function () {
                            $dfd.reject();
                        }
                    });
                });
            },
            updateAction: function (postData) {
                var json = "{\"indication\" : {\"id\": \"" + $('#Edit-id').val() + "\", \"name\" : \"" + $('#Edit-name').val() + "\", \"description\" : \"" + $('#Edit-description').val() + "\", \"updatedBy\" : \"" + window.user_id + "\"}}";

                return $.Deferred(function ($dfd) {
                    $.ajax({
                        url: 'reference/json/updateIndication',
                        headers: {
                            "Content-Type": "application/json"
                        },
                        type: 'POST',
                        data: json,
                        success: function (data) {
                            $dfd.resolve(data);
                        },
                        error: function () {
                            $dfd.reject();
                        }
                    });
                });
            },
            deleteAction: function (postData) {
                return $.Deferred(function ($dfd) {
                    $.ajax({
                        url: 'reference/json/deleteIndication/' + postData.id + '/' + window.user_id,
                        headers: {
                            "Content-Type": "application/json"
                        },
                        type: 'POST',
                        success: function (data) {
                            $dfd.resolve(data);
                        },
                        error: function () {
                            $dfd.reject();
                        }
                    });
                });
            }
        },
        fields: {
            id: {
                key: true,
                list: false
            },
            name: {
                title: 'Name',
                width: '30%'
            },
            description: {
                title: 'Description',
                type: "textarea",
                width: '30%'
            }
        },
        formSubmitting: function(event, data) {
            if($('#Edit-name').val() == "") {
                alert("Enter the name");
                return false;
            }
            return true;
        }
    });
    $('#indicationContainer').jtable('load');
    $("#indicationDialog").dialog("open");
}
function showIndicationDialog() {
    $('#indicationContainer').jtable({
        title: 'Indications',
        actions: {
            listAction: 'reference/json/listIndication',
            createAction: function (postData) {
                var json = "{\"indication\" : {\"name\" : \"" + $('#Edit-name').val() + "\", \"description\" : \"" + $('#Edit-description').val() + "\", \"createdBy\" : " + window.user_id + "}}";

                return $.Deferred(function ($dfd) {
                    $.ajax({
                        url: 'reference/json/createIndication',
                        headers: {
                            "Content-Type": "application/json"
                        },
                        type: 'POST',
                        data: json,
                        success: function (data) {
                            $dfd.resolve(data);
                        },
                        error: function () {
                            $dfd.reject();
                        }
                    });
                });
            },
            updateAction: function (postData) {
                var json = "{\"indication\" : {\"id\": \"" + $('#Edit-id').val() + "\", \"name\" : \"" + $('#Edit-name').val() + "\", \"description\" : \"" + $('#Edit-description').val() + "\", \"updatedBy\" : \"" + window.user_id + "\"}}";

                return $.Deferred(function ($dfd) {
                    $.ajax({
                        url: 'reference/json/updateIndication',
                        headers: {
                            "Content-Type": "application/json"
                        },
                        type: 'POST',
                        data: json,
                        success: function (data) {
                            $dfd.resolve(data);
                        },
                        error: function () {
                            $dfd.reject();
                        }
                    });
                });
            },
            deleteAction: function (postData) {
                return $.Deferred(function ($dfd) {
                    $.ajax({
                        url: 'reference/json/deleteIndication/' + postData.id + '/' + window.user_id,
                        headers: {
                            "Content-Type": "application/json"
                        },
                        type: 'POST',
                        success: function (data) {
                            $dfd.resolve(data);
                        },
                        error: function () {
                            $dfd.reject();
                        }
                    });
                });
            }
        },
        fields: {
            id: {
                key: true,
                list: false
            },
            name: {
                title: 'Name',
                width: '30%'
            },
            description: {
                title: 'Description',
                type: "textarea",
                width: '30%'
            }
        },
        formSubmitting: function(event, data) {
            if($('#Edit-name').val() == "") {
                alert("Enter the name");
                return false;
            }
            return true;
        }
    });
    $('#indicationContainer').jtable('load');
    $("#indicationDialog").dialog("open");
}






function showRegimenChangeReasonDialog() {
    $('#regimenChangeReasonContainer').jtable({
        title: 'Regimen change reason',
        actions: {
            listAction: 'reference/json/listRegimenChangeReason',
            createAction: function (postData) {
                var json = "{\"regimenChangeReason\" : {\"name\" : \"" + $('#Edit-name').val() + "\", \"description\" : \"" + $('#Edit-description').val() + "\", \"createdBy\" : \"" + window.user_id + "\"}}";

                return $.Deferred(function ($dfd) {
                    $.ajax({
                        url: 'reference/json/createRegimenChangeReason',
                        headers: {
                            "Content-Type": "application/json"
                        },
                        type: 'POST',
                        data: json,
                        success: function (data) {
                            $dfd.resolve(data);
                        },
                        error: function () {
                            $dfd.reject();
                        }
                    });
                });
            },
            updateAction: function (postData) {
                var json = "{\"regimenChangeReason\" : {\"id\": \"" + $('#Edit-id').val() + "\", \"name\" : \"" + $('#Edit-name').val() + "\", \"description\" : \"" + $('#Edit-description').val() + "\", \"updatedBy\" : \"" + window.user_id + "\"}}";
                if($('#Edit-name').val() == "") {
                    alert("Enter the name");
                    return;
                }
                return $.Deferred(function ($dfd) {
                    $.ajax({
                        url: 'reference/json/updateRegimenChangeReason',
                        headers: {
                            "Content-Type": "application/json"
                        },
                        type: 'POST',
                        data: json,
                        success: function (data) {
                            $dfd.resolve(data);
                        },
                        error: function () {
                            $dfd.reject();
                        }
                    });
                });
            },
            deleteAction: function (postData) {
                return $.Deferred(function ($dfd) {
                    $.ajax({
                        url: 'reference/json/deleteRegimenChangeReason/' + postData.id + '/' + window.user_id,
                        headers: {
                            "Content-Type": "application/json"
                        },
                        type: 'POST',
                        success: function (data) {
                            $dfd.resolve(data);
                        },
                        error: function () {
                            $dfd.reject();
                        }
                    });
                });
            }
        },
        fields: {
            id: {
                key: true,
                list: false
            },
            name: {
                title: 'Name',
                width: '30%'
            },
            description: {
                title: 'Description',
                type: "textarea",
                width: '30%'
            }
        },
        formSubmitting: function(event, data) {
            if($('#Edit-name').val() == "") {
                alert("Enter the name");
                return false;
            }
            return true;
        }
    });
    $('#regimenChangeReasonContainer').jtable('load');
    $("#regimenChangeReasonDialog").dialog("open");
}


/**
 *  This function initializes the Visit Type dialog. Since we need the Service types from the server,
 *  we load them first if they are not loaded into the page.
 *  If they are already loaded, we just show the dialog.
 */
function initVisitTypeDialog() {
    $.ajax({
        url: 'reference/json/listServiceType',
        headers: {
            "Content-Type": "application/json"
        },
        type: 'POST',
        success: function (data) {
            if(data.Records != undefined) {
                var tempRec = data.Records;
                window.serviceTypes = new Array();
                for(var i = 0; i < tempRec.length; i++) {
                    window.serviceTypes.push({ Value : tempRec[i].id, DisplayText : tempRec[i].name})
                }
                showVisitTypeDialog();
            }

        },
        error: function () {

        }
    });

}

function showVisitTypeDialog() {
    $('#visitTypeContainer').jtable({
        title: 'Visit type',
        fields: {
            id: {
                key: true,
                list: false
            },
            name: {
                title: 'Name',
                width: '30%'
            },
            description: {
                title: 'Description',
                type: "textarea",
                width: '30%'
            },
            serviceTypeId: {
                title: "Service type",
                options: window.serviceTypes
            }
        },
        actions: {
            listAction: 'reference/json/listVisitType',
            createAction: function (postData) {
                var json = "{\"visitType\" : {\"name\" : \"" + $('#Edit-name').val() + "\", \"description\" : \"" + $('#Edit-description').val() + "\", \"serviceTypeId\" : \"" + $('#Edit-serviceTypeId').val() + "\", \"createdBy\" : \"" + window.user_id + "\"}}";

                return $.Deferred(function ($dfd) {
                    $.ajax({
                        url: 'reference/json/createVisitType',
                        headers: {
                            "Content-Type": "application/json"
                        },
                        type: 'POST',
                        data: json,
                        success: function (data) {
                            $dfd.resolve(data);
                        },
                        error: function () {
                            $dfd.reject();
                        }
                    });
                });
            },
            updateAction: function (postData) {
                var json = "{\"visitType\" : {\"id\": \"" + $('#Edit-id').val() + "\", \"name\" : \"" + $('#Edit-name').val() + "\", \"serviceTypeId\" : \"" + $('#Edit-serviceTypeId').val() + "\", \"description\" : \"" + $('#Edit-description').val() + "\", \"updatedBy\" : \"" + window.user_id + "\"}}";

                return $.Deferred(function ($dfd) {
                    $.ajax({
                        url: 'reference/json/updateVisitType',
                        headers: {
                            "Content-Type": "application/json"
                        },
                        type: 'POST',
                        data: json,
                        success: function (data) {
                            $dfd.resolve(data);
                        },
                        error: function () {
                            $dfd.reject();
                        }
                    });
                });
            },
            deleteAction: function (postData) {
                return $.Deferred(function ($dfd) {
                    $.ajax({
                        url: 'reference/json/deleteVisitType/' + postData.id + '/' + window.user_id,
                        headers: {
                            "Content-Type": "application/json"
                        },
                        type: 'POST',
                        success: function (data) {
                            $dfd.resolve(data);
                        },
                        error: function () {
                            $dfd.reject();
                        }
                    });
                });
            }
        },
        formSubmitting: function(event, data) {
            if($('#Edit-name').val() == "") {
                alert("Enter the name");
                return false;
            }
            return true;
        }
    });
    $('#visitTypeContainer').jtable('load');
    $("#visitTypeDialog").dialog("open");
}

function showDrugCategoryDialog() {

    $('#drugCategoryContainer').jtable({
        title: 'Drug Category',
        actions: {
            listAction: 'reference/json/listDrugCategory',
            createAction: function (postData) {
                var json = "{\"drugCategory\" : {\"name\" : \"" + $('#Edit-name').val() + "\", \"description\" : \"" + $('#Edit-description').val() + "\", \"createdBy\" : " + window.user_id + "}}";

                return $.Deferred(function ($dfd) {
                    $.ajax({
                        url: 'reference/json/createDrugCategory',
                        headers: {
                            "Content-Type": "application/json"
                        },
                        type: 'POST',
                        data: json,
                        success: function (data) {
                            $dfd.resolve(data);
                            reloadDrugCategory();
                        },
                        error: function () {
                            $dfd.reject();
                        }
                    });
                });
            },
            updateAction: function (postData) {
                var json = "{\"drugCategory\" : {\"id\": \"" + $('#Edit-id').val() + "\", \"name\" : \"" + $('#Edit-name').val() + "\", \"description\" : \"" + $('#Edit-description').val() + "\", \"updatedBy\" : \"" + window.user_id + "\"}}";

                return $.Deferred(function ($dfd) {
                    $.ajax({
                        url: 'reference/json/updateDrugCategory',
                        headers: {
                            "Content-Type": "application/json"
                        },
                        type: 'POST',
                        data: json,
                        success: function (data) {
                            $dfd.resolve(data);
                            reloadDrugCategory();
                        },
                        error: function () {
                            $dfd.reject();
                        }
                    });
                });
            },
            deleteAction: function (postData) {
                return $.Deferred(function ($dfd) {
                    $.ajax({
                        url: 'reference/json/deleteDrugCategory/' + postData.id + '/' + window.user_id,
                        headers: {
                            "Content-Type": "application/json"
                        },
                        type: 'POST',
                        success: function (data) {
                            $dfd.resolve(data);
                            reloadDrugCategory();
                        },
                        error: function () {
                            $dfd.reject();
                        }
                    });
                });
            }
        },
        fields: {
            id: {
                key: true,
                list: false
            },
            name: {
                title: 'Name',
                width: '30%'
            },
            description: {
                title: 'Description',
                type: 'textarea',
                width: '60%'
            }
        },
        formSubmitting: function(event, data) {
            if($('#Edit-name').val() == "") {
                alert("Enter the name");
                return false;
            }
            return true;
        }
    });
    $('#drugCategoryContainer').jtable('load');
    $("#drugCategoryDialog").dialog("open");
}


function showDrugFormDialog() {
    $('#drugFormContainer').jtable({
        title: 'Drug Form',
        actions: {
            listAction: 'reference/json/listDrugForm',
            createAction: function (postData) {
                var json = "{\"drugForm\" : {\"name\" : \"" + $('#Edit-name').val() + "\", \"description\" : \"" + $('#Edit-description').val() + "\", \"createdBy\" : " + window.user_id + "}}";

                return $.Deferred(function ($dfd) {
                    $.ajax({
                        url: 'reference/json/createDrugForm',
                        headers: {
                            "Content-Type": "application/json"
                        },
                        type: 'POST',
                        data: json,
                        success: function (data) {
                            $dfd.resolve(data);
                            reloadDrugForm();
                        },
                        error: function () {
                            $dfd.reject();
                        }
                    });
                });
            },
            updateAction: function (postData) {
                var json = "{\"drugForm\" : {\"id\": \"" + $('#Edit-id').val() + "\", \"name\" : \"" + $('#Edit-name').val() + "\", \"description\" : \"" + $('#Edit-description').val() + "\", \"updatedBy\" : \"" + window.user_id + "\"}}";

                return $.Deferred(function ($dfd) {
                    $.ajax({
                        url: 'reference/json/updateDrugForm',
                        headers: {
                            "Content-Type": "application/json"
                        },
                        type: 'POST',
                        data: json,
                        success: function (data) {
                            $dfd.resolve(data);
                            reloadDrugForm();
                        },
                        error: function () {
                            $dfd.reject();
                        }
                    });
                });
            },
            deleteAction: function (postData) {
                return $.Deferred(function ($dfd) {
                    $.ajax({
                        url: 'reference/json/deleteDrugForm/' + postData.id + '/' + window.user_id,
                        headers: {
                            "Content-Type": "application/json"
                        },
                        type: 'POST',
                        success: function (data) {
                            $dfd.resolve(data);
                            reloadDrugForm();
                        },
                        error: function () {
                            $dfd.reject();
                        }
                    });
                });
            }
        },
        fields: {
            id: {
                key: true,
                list: false
            },
            name: {
                title: 'Name',
                width: '30%'
            },
            description: {
                title: 'Description',
                type: 'textarea',
                width: '60%'
            }
        },
        formSubmitting: function(event, data) {
            if($('#Edit-name').val() == "") {
                alert("Enter the name");
                return false;
            }
            return true;
        }
    });
    $('#drugFormContainer').jtable('load');
    $("#drugFormDialog").dialog("open");
}

function showDrugTypeDialog() {

    $('#drugTypeContainer').jtable({
        title: 'Drug Types',
        actions: {
            listAction: 'reference/json/listDrugType',
            createAction: function (postData) {
                var json = "{\"drugType\" : {\"name\" : \"" + $('#Edit-name').val() + "\", \"description\" : \"" + $('#Edit-description').val() + "\", \"createdBy\" : " + window.user_id + "}}";

                return $.Deferred(function ($dfd) {
                    $.ajax({
                        url: 'reference/json/createDrugType',
                        headers: {
                            "Content-Type": "application/json"
                        },
                        type: 'POST',
                        data: json,
                        success: function (data) {
                            $dfd.resolve(data);
                            reloadDrugType();
                        },
                        error: function () {
                            $dfd.reject();
                        }
                    });
                });
            },
            updateAction: function (postData) {
                var json = "{\"drugType\" : {\"id\": \"" + $('#Edit-id').val() + "\", \"name\" : \"" + $('#Edit-name').val() + "\", \"description\" : \"" + $('#Edit-description').val() + "\", \"updatedBy\" : \"" + window.user_id + "\"}}";

                return $.Deferred(function ($dfd) {
                    $.ajax({
                        url: 'reference/json/updateDrugType',
                        headers: {
                            "Content-Type": "application/json"
                        },
                        type: 'POST',
                        data: json,
                        success: function (data) {
                            $dfd.resolve(data);
                            reloadDrugType();
                        },
                        error: function () {
                            $dfd.reject();
                        }
                    });
                });
            },
            deleteAction: function (postData) {
                return $.Deferred(function ($dfd) {
                    $.ajax({
                        url: 'reference/json/deleteDrugType/' + postData.id + '/' + window.user_id,
                        headers: {
                            "Content-Type": "application/json"
                        },
                        type: 'POST',
                        success: function (data) {
                            $dfd.resolve(data);
                            reloadDrugType();
                        },
                        error: function () {
                            $dfd.reject();
                        }
                    });
                });
            }
        },
        fields: {
            id: {
                key: true,
                list: false
            },
            name: {
                title: 'Name',
                width: '30%'
            },
            description: {
                title: 'Description',
                type: 'textarea',
                width: '60%'
            }
        },
        formSubmitting: function(event, data) {
            if($('#Edit-name').val() == "") {
                alert("Enter the name");
                return false;
            }
            return true;
        }
    });
    $('#drugTypeContainer').jtable('load');
    $("#drugTypeDialog").dialog("open");
}

/**
 *  This function initializes the District dialog. Since we need the regions from the server,
 *  we load them first if they are not loaded into the page.
 *  If they are already loaded, we just show the dialog.
 */
function initAccountDialog() {
    $.ajax({
         url: 'reference/json/accountTypeList',
            headers: {
                "Content-Type": "application/json"
            },
            type: 'POST',
            success: function (data) {
                if(data.Records != undefined) {
                    var tempRec = data.Records;
                    window.accountType = new Array();
                    for(var i = 0; i < tempRec.length; i++) {
                        window.accountType.push({ Value : tempRec[i].id, DisplayText : tempRec[i].name})
                    }
                    showAccountDialog();
                }

            },
            error: function () {

            }
        });

}

function showAccountDialog() {
    $('#accountContainer').jtable({
        title: 'Account',
        actions: {
            listAction: 'reference/json/listAccount',
            createAction: function (postData) {
                console.log($('#Edit-Is_satellite').val());
                var isSatelite = $('#Edit-Is_satellite').val() == 'true'? 1:0;
                var isbulkstore= $('#Edit-Is_bulkstore').val() == 'true'? 1:0;
                var cannotdispencse =$('#Edit-Cannot_dispense').val() == 'true'? 1:0;
                var json = "{\"account\" : {\"name\" : \"" + $('#Edit-name').val() + "\", \"description\" : \"" + $('#Edit-description').val() + "\", \"accountTypeId\" : " + $('#Edit-accountTypeId').val() + ", \"createdBy\" : " + window.user_id + ",\"is_satellite\" : " + isSatelite + ",\"is_bulkstore\" : " + isbulkstore + ",\"cannot_dispense\" : " + cannotdispencse + "}}";
                return $.Deferred(function ($dfd) {
                    $.ajax({
                        url: 'reference/json/createAccount',
                        headers: {
                            "Content-Type": "application/json"
                        },
                        type: 'POST',
                        data: json,
                        success: function (data) {
                            $dfd.resolve(data);
                        },
                        error: function () {
                            $dfd.reject();
                        }
                    });
                });
            },
            updateAction: function (postData) {
                console.log($('#Edit-Is_satellite').val());
                var isSatelite = $('#Edit-Is_satellite').val() == 'true'? 1:0;
                var isbulkstore= $('#Edit-Is_bulkstore').val() == 'true'? 1:0;
                var cannotdispencse =$('#Edit-Cannot_dispense').val() == 'true'? 1:0;
                var json = "{\"account\" : {\"id\" : \"" + $('#Edit-id').val() + "\",\"name\" : \"" + $('#Edit-name').val() + "\", \"description\" : \"" + $('#Edit-description').val() + "\", \"accountTypeId\" : " + $('#Edit-accountTypeId').val() + ", \"createdBy\" : " + window.user_id + ",\"is_satellite\" : " + isSatelite + ",\"is_bulkstore\" : " + isbulkstore + ",\"cannot_dispense\" : " + cannotdispencse + "}}";

                return $.Deferred(function ($dfd) {
                    $.ajax({
                        url: 'reference/json/updateAccount',
                        headers: {
                            "Content-Type": "application/json"
                        },
                        type: 'POST',
                        data: json,
                        success: function (data) {
                            $dfd.resolve(data);
                        },
                        error: function () {
                            $dfd.reject();
                        }
                    });
                });
            },
            deleteAction: function (postData) {
                return $.Deferred(function ($dfd) {
                    $.ajax({
                        url: 'reference/json/deleteAccount/' + postData.id + '/' + window.user_id,
                        headers: {
                            "Content-Type": "application/json"
                        },
                        type: 'POST',
                        success: function (data) {
                            $dfd.resolve(data);
                        },
                        error: function () {
                            $dfd.reject();
                        }
                    });
                });
            }
        },
        fields: {
            id: {
                key: true,
                list: false
            },
            accountTypeId: {
                title: "Account type",
                options: window.accountType
            },
            name: {
                title: 'Name',
                width: '20%'
            },
            description: {
                title: 'Description',
                type: "textarea",
                width: '40%'
            },
            mflCode: {
                title: 'mflCode',
                list: false
            },
            Is_bulkstore: {
                type: "checkbox",
                values: { 'false': 'bulk store', 'true': 'bulk store' },
                defaultValue:'false',
                list: false
            },
            Is_satellite: {
                type: "checkbox",
                values: { 'false': 'satellite', 'true': 'satellite' },
                defaultValue:'false',
                list: false
            },
            Cannot_dispense: {
                type: "checkbox",
                values: { 'false': 'cannot dispense', 'true': 'cannot dispense' },
                defaultValue: 'false',
                list: false
            }
        },
        formSubmitting: function(event, data) {
            if($('#Edit-name').val() == "") {
                alert("Enter the name");
                return false;
            }
            return true;
        }
    });
    $('#accountContainer').jtable('load');
    $("#accountDialog").dialog("open");
}function showPatientIdentifierDialog() {

}


function showFamilyPlanningMethodDialog() {
    $('#familyPlanningMethodContainer').jtable({
        title: 'Family planning methods',
        actions: {
            listAction: 'reference/json/listFPMethods',
            createAction: function (postData) {
                var json = "{\"planningMethod\" : {\"name\" : \"" + $('#Edit-name').val() + "\", \"description\" : \"" + $('#Edit-description').val() + "\", \"createdBy\" : \"" + window.user_id + "\"}}";

                return $.Deferred(function ($dfd) {
                    $.ajax({
                        url: 'reference/json/createFPMethods',
                        headers: {
                            "Content-Type": "application/json"
                        },
                        type: 'POST',
                        data: json,
                        success: function (data) {
                            $dfd.resolve(data);
                        },
                        error: function () {
                            $dfd.reject();
                        }
                    });
                });
            },
            updateAction: function (postData) {
                var json = "{\"planningMethod\" : {\"id\": \"" + $('#Edit-id').val() + "\", \"name\" : \"" + $('#Edit-name').val() + "\", \"description\" : \"" + $('#Edit-description').val() + "\", \"updatedBy\" : \"" + window.user_id + "\"}}";
                if($('#Edit-name').val() == "") {
                    alert("Enter the name");
                    return;
                }
                return $.Deferred(function ($dfd) {
                    $.ajax({
                        url: 'reference/json/updateFPMethod',
                        headers: {
                            "Content-Type": "application/json"
                        },
                        type: 'POST',
                        data: json,
                        success: function (data) {
                            $dfd.resolve(data);
                        },
                        error: function () {
                            $dfd.reject();
                        }
                    });
                });
            },
            deleteAction: function (postData) {
                return $.Deferred(function ($dfd) {
                    $.ajax({
                        url: 'reference/json/deleteFPMethod/' + postData.id + '/' + window.user_id,
                        headers: {
                            "Content-Type": "application/json"
                        },
                        type: 'POST',
                        success: function (data) {
                            $dfd.resolve(data);
                        },
                        error: function () {
                            $dfd.reject();
                        }
                    });
                });
            }
        },
        fields: {
            id: {
                key: true,
                list: false
            },
            name: {
                title: 'Name',
                width: '30%'
            },
            description: {
                title: 'Description',
                type: "textarea",
                width: '30%'
            }
        },
        formSubmitting: function(event, data) {
            if($('#Edit-name').val() == "") {
                alert("Enter the name");
                return false;
            }
            return true;
        }
    });
    $('#familyPlanningMethodContainer').jtable('load');
    $("#familyPlanningMethodDialog").dialog("open");
}

function showFamilyPlanningMethodChangeReasonsDialog() {
    $('#familyPlanningMethodChangeReasonsContainer').jtable({
        title: 'Regimen change reason',
        actions: {
            listAction: 'reference/json/listFPMethodChangeReason',
            createAction: function (postData) {
                var json = "{\"changeReason\" : {\"name\" : \"" + $('#Edit-name').val() + "\", \"description\" : \"" + $('#Edit-description').val() + "\", \"createdBy\" : \"" + window.user_id + "\"}}";

                return $.Deferred(function ($dfd) {
                    $.ajax({
                        url: 'reference/json/createFPMethodChangeReason',
                        headers: {
                            "Content-Type": "application/json"
                        },
                        type: 'POST',
                        data: json,
                        success: function (data) {
                            $dfd.resolve(data);
                        },
                        error: function () {
                            $dfd.reject();
                        }
                    });
                });
            },
            updateAction: function (postData) {
                var json = "{\"changeReason\" : {\"id\": \"" + $('#Edit-id').val() + "\", \"name\" : \"" + $('#Edit-name').val() + "\", \"description\" : \"" + $('#Edit-description').val() + "\", \"updatedBy\" : \"" + window.user_id + "\"}}";
                if($('#Edit-name').val() == "") {
                    alert("Enter the name");
                    return;
                }
                return $.Deferred(function ($dfd) {
                    $.ajax({
                        url: 'reference/json/updateFPMethodChangeReason',
                        headers: {
                            "Content-Type": "application/json"
                        },
                        type: 'POST',
                        data: json,
                        success: function (data) {
                            $dfd.resolve(data);
                        },
                        error: function () {
                            $dfd.reject();
                        }
                    });
                });
            },
            deleteAction: function (postData) {
                return $.Deferred(function ($dfd) {
                    $.ajax({
                        url: 'reference/json/deleteFPMethodChangeReason/' + postData.id + '/' + window.user_id,
                        headers: {
                            "Content-Type": "application/json"
                        },
                        type: 'POST',
                        success: function (data) {
                            $dfd.resolve(data);
                        },
                        error: function () {
                            $dfd.reject();
                        }
                    });
                });
            }
        },
        fields: {
            id: {
                key: true,
                list: false
            },
            name: {
                title: 'Name',
                width: '30%'
            },
            description: {
                title: 'Description',
                type: "textarea",
                width: '30%'
            }
        },
        formSubmitting: function(event, data) {
            if($('#Edit-name').val() == "") {
                alert("Enter the name");
                return false;
            }
            return true;
        }
    });
    $('#familyPlanningMethodChangeReasonsContainer').jtable('load');
    $("#familyPlanningMethodChangeReasonsDialog").dialog("open");
}

function showInsulinTypeDialog() {
    $('#insulinTypeContainer').jtable({
        title: 'Insulin Type',
        actions: {
            listAction: 'reference/json/listInsulinType',
            createAction: function (postData) {
                var json = "{\"insulin\" : {\"name\" : \"" + $('#Edit-name').val() + "\", \"description\" : \"" + $('#Edit-description').val() + "\", \"createdBy\" : " + window.user_id + "}}";

                return $.Deferred(function ($dfd) {
                    $.ajax({
                        url: 'reference/json/createInsulinType',
                        headers: {
                            "Content-Type": "application/json"
                        },
                        type: 'POST',
                        data: json,
                        success: function (data) {
                            $dfd.resolve(data);
                        },
                        error: function () {
                            $dfd.reject();
                        }
                    });
                });
            },
            updateAction: function (postData) {
                var json = "{\"insulin\" : {\"id\": \"" + $('#Edit-id').val() + "\", \"name\" : \"" + $('#Edit-name').val() + "\", \"description\" : \"" + $('#Edit-description').val() + "\", \"updatedBy\" : \"" + window.user_id + "\"}}";

                return $.Deferred(function ($dfd) {
                    $.ajax({
                        url: 'reference/json/updateInsulinType',
                        headers: {
                            "Content-Type": "application/json"
                        },
                        type: 'POST',
                        data: json,
                        success: function (data) {
                            $dfd.resolve(data);
                        },
                        error: function () {
                            $dfd.reject();
                        }
                    });
                });
            },
            deleteAction: function (postData) {
                return $.Deferred(function ($dfd) {
                    $.ajax({
                        url: 'reference/json/deleteInsulinType/' + postData.id + '/' + window.user_id,
                        headers: {
                            "Content-Type": "application/json"
                        },
                        type: 'POST',
                        success: function (data) {
                            $dfd.resolve(data);
                        },
                        error: function () {
                            $dfd.reject();
                        }
                    });
                });
            }
        },
        fields: {
            id: {
                key: true,
                list: false
            },
            name: {
                title: 'Name',
                width: '30%'
            },
            description: {
                title: 'Description',
                type: 'textarea',
                width: '60%'
            }
        },
        formSubmitting: function(event, data) {
            if($('#Edit-name').val() == "") {
                alert("Enter the name");
                return false;
            }
            return true;
        }
    });
    $('#insulinTypeContainer').jtable('load');
    $("#insulinTypeDialog").dialog("open");
}

function showRandomBloodSugarLevelDialog() {
    $('#randomBloodSugarLevelContainer').jtable({
        title: 'Random Blood Sugar Level',
        actions: {
            listAction: 'reference/json/listRandomBloodSugarLevel',
            createAction: function (postData) {
                var json = "{\"bloodSugarLevel\" : {\"name\" : \"" + $('#Edit-name').val() + "\", \"description\" : \"" + $('#Edit-description').val() + "\", \"createdBy\" : " + window.user_id + "}}";

                return $.Deferred(function ($dfd) {
                    $.ajax({
                        url: 'reference/json/createRandomBloodSugarLevel',
                        headers: {
                            "Content-Type": "application/json"
                        },
                        type: 'POST',
                        data: json,
                        success: function (data) {
                            $dfd.resolve(data);
                        },
                        error: function () {
                            $dfd.reject();
                        }
                    });
                });
            },
            updateAction: function (postData) {
                var json = "{\"bloodSugarLevel\" : {\"id\": \"" + $('#Edit-id').val() + "\", \"name\" : \"" + $('#Edit-name').val() + "\", \"description\" : \"" + $('#Edit-description').val() + "\", \"updatedBy\" : \"" + window.user_id + "\"}}";

                return $.Deferred(function ($dfd) {
                    $.ajax({
                        url: 'reference/json/updateRandomBloodSugarLevel',
                        headers: {
                            "Content-Type": "application/json"
                        },
                        type: 'POST',
                        data: json,
                        success: function (data) {
                            $dfd.resolve(data);
                        },
                        error: function () {
                            $dfd.reject();
                        }
                    });
                });
            },
            deleteAction: function (postData) {
                return $.Deferred(function ($dfd) {
                    $.ajax({
                        url: 'reference/json/deleteRandomBloodSugarLevel/' + postData.id + '/' + window.user_id,
                        headers: {
                            "Content-Type": "application/json"
                        },
                        type: 'POST',
                        success: function (data) {
                            $dfd.resolve(data);
                        },
                        error: function () {
                            $dfd.reject();
                        }
                    });
                });
            }
        },
        fields: {
            id: {
                key: true,
                list: false
            },
            name: {
                title: 'Name',
                width: '30%'
            },
            description: {
                title: 'Description',
                type: 'textarea',
                width: '60%'
            }
        },
        formSubmitting: function(event, data) {
            if($('#Edit-name').val() == "") {
                alert("Enter the name");
                return false;
            }
            return true;
        }
    });
    $('#randomBloodSugarLevelContainer').jtable('load');
    $("#randomBloodSugarLevelDialog").dialog("open");
}
/**
 *  Shows a patient dialog.
 *  It is used  when adding/editing patient information.
 * @param edit
 * @param personId
 */
function showNewPatientDialog(edit, personId) {
    //if(window.supportingOrganization == null || window.serviceTypes == null || window.patientSources == null || window.district != null) {
        $.ajax({
            url: 'reference/json/patient/listReferences',
            headers: {
                "Content-Type": "application/json"
            },
            type: 'POST',
            success: function (data) {
                window.supportingOrganization = data.supportingOrganizationList;
                window.serviceTypes = data.serviceTypeList;
                window.patientSources = data.patientSourceList;
                window.patientStatus = data.patientStatusList;
                window.district = data.districtList;
                window.identifierTypeList = data.identifierTypeList;
                window.patientIdentifiers = data.patientIdentifierList;
                window.regimen = data.regimenList;
                initPatientDialog(edit);
            },
            error: function () {

            }
        });


}

function initPatientDialog(edit) {
    clearTable("patientServiceTypeTable");
    var art = new Option("CCC Pharmacy", "1");

    var fp = new Option("Family Planning Clinic", "2");


    var fp = new Option("Diabetic Clinic", "6");
    for(var i = 0; window.serviceTypes != null && i < window.serviceTypes.length; i++) {
        if($('#clinicTypeSelect').val() == "6" && window.serviceTypes[i].name == "Diabetic") {
            //optionList.add(new Option(window.serviceTypes[i].name, window.serviceTypes[i].id), null);
        } else if($('#clinicTypeSelect').val() == "2" && window.serviceTypes[i].name == "FP/RH") {
            //optionList.add(new Option(window.serviceTypes[i].name, window.serviceTypes[i].id), null);
        } else if($('#clinicTypeSelect').val() == "1" && (window.serviceTypes[i].name == "ART" || window.serviceTypes[i].name == "PEP" || window.serviceTypes[i].name == "PMTCT" || window.serviceTypes[i].name == "OI Only")){
           // optionList.add(new Option(window.serviceTypes[i].name, window.serviceTypes[i].id), null);
        }
    }
    var optionList = document.getElementById("patientSource");
    for(var i = optionList.length; i > 0; i--) {
        optionList.remove(i);
    }

    for(var i = 0; window.patientSources != null && i <   window.patientSources.length; i++) {
        optionList.add(new Option(window.patientSources[i].name, window.patientSources[i].id), null);
    }

    optionList = document.getElementById("identifierType");
    for(var i = optionList.length; i > 0; i--) {
        optionList.remove(i);
    }

    for(var i = 0; window.identifierTypeList != null && i < window.identifierTypeList.length; i++) {
        optionList.add(new Option(window.identifierTypeList[i].name, window.identifierTypeList[i].id), null);
    }

    optionList = document.getElementById("birthDistrict");
    for(var i = optionList.length; i > 0; i--) {
        optionList.remove(i);
    }

    for(var i = 0; window.district != null && i < window.district.length; i++) {
        optionList.add(new Option(window.district[i].name, window.district[i].id), null);
    }

    optionList = document.getElementById("startRegimenId");
    for(var i = optionList.length; i > 0; i--) {
        optionList.remove(i);
    }

    for(var i = 0; window.regimen != null && i < window.regimen.length; i++) {
        optionList.add(new Option(window.regimen[i].name, window.regimen[i].id), null);
    }

    optionList = document.getElementById("regPatientStatusId");
    for(var i = optionList.length; i > 0; i--) {
        optionList.remove(i);
    }

    for(var i = 0; window.patientStatus != null && i < window.patientStatus.length; i++) {
        optionList.add(new Option(window.patientStatus[i].name, window.patientStatus[i].id), null);
    }



    $("#age").change(function() {
        var age = $('#age').val();
        if(isNaN(age)) {
            $('#age').val("")
            alert("Please fill in a valid number.");
            this.value = "";
        } else {
            if(age == "")
                return;
            var date = new Date();
            var dob = new Date();
            dob.setDate(date.getDate() - parseInt(age));

            $('#dob').val(dob.getDate() + '/' + (dob.getMonth() + 1) + '/' + dob.getFullYear());
        }
    });
    $("#dob").change(function() {
        var dob = $("#dob").val();
        var date1 = new Date(formatDate(dob));
        var date2 = new Date();
        var timeDiff = Math.abs(date2.getTime() - date1.getTime());
        var diffDays = Math.floor(timeDiff / (1000 * 3600 * 24 * 365));
        $("#age").val(diffDays);
    });

    $('#dateEnrolled').change(function() {
        var patientServiceTypeTable = document.getElementById("patientServiceTypeTable");
        var rows = patientServiceTypeTable.rows.length;
        var enrolled = $(this).val();

        var enrolleddate = new Date(formatDate(enrolled)); // some mock date
        var enrolledTime = enrolleddate.getTime();


        for (var i = 1; i < rows; i++) {
            var started = $('#startDate-' + i).val();
            var startDate = new Date(formatDate(started)); // some mock date
            var startTime = startDate.getTime();
            if(started != "" && enrolledTime > startTime ) {
                alert("Enrolled date cannot be after started date");
                $(this).val('');
                break;
            }
        }
    });

    //$('#birthDistrict').flexselect();
    if(edit) {
        if ($("#visitDialog").data() != undefined && $("#visitDialog").data('bs.modal') != undefined && $("#visitDialog").data('bs.modal').isShown) {
            $("#visitDialog").modal("hide");
        }
        $('#personUpdate').val('true');
        $('#firstName').val(person.firstName);
        $('#surname').val(person.surname);
        $('#otherNames').val(person.otherNames);
        $('#sex').val(person.sex);
        $('#dob').val(getDate(person.dateOfBirth));
        $('#birthDistrict').val(person.birthDistrictId);
        $('#personId').val(person.id);

        $('#postalAddress').val(personAddress.postalAddress);
        $('#physicalAddress').val(personAddress.physicalAddress);
        $('#alternativeAddress').val(personAddress.alternativeAddress);
        $('#phone').val(personAddress.telNo1);
        $('#email').val(personAddress.emailAddress);
        $('#personAddressId').val(personAddress.id);
        $('#patientId').val(patient.id);
        if(window.patientIdents != null){
            for(var i =0 ; i<window.patientIdents.length ;i++){
                if(window.patientIdents[i].active === 1){
                    $('#patientIdentifierId').val(window.patientIdents[i].id);
                    $('#identifierType').val(window.patientIdents[i].identifierTypeId);
                    $('#medicalRecordNumber').val(window.patientIdents[i].identifier);
                }
            }
        }
        $('#dateEnrolled').val(getDate(patient.enrollmentDate));
        $('#patientSource').val(patient.patientSourceId);
        $('#startRegimenId').val(window.patient.startRegimenId);
        $('#startRegimenId').flexselect();
        if(window.patient.patientStatusId == null || window.patient.patientStatusId == undefined)
            window.patient.patientStatusId = "";
        $('#regPatientStatusId').val(window.patient.patientStatusId);
        //$('#serviceType').val(patient.serviceTypeId);
        //$('#serviceStartDate').val(patient.serviceStartDate);
       // $('#supportingOrganization').val(patient.supportingOrganizationId);
        $('#drugAllergies').val(patient.drugAllergies);
        $('#chronicIllness').val(patient.chronicIllnesses);
        $("input[name=smoker][value=" + patient.smoker + "]").attr('checked', 'checked');
        $("input[name=drinker][value=" + patient.drinker + "]").attr('checked', 'checked');
        $('#identifierType').change(function(){
            $('#medicalRecordNumber').val('');
            var identifierId =  $('#identifierType').val();
            for(var i =0; i<window.patientIdents.length;i++){
                if(window.patientIdents[i].identifierTypeId && window.patientIdents[i].identifierTypeId.toString() == identifierId.toString()){
                    $('#patientIdentifierId').val(window.patientIdents[i].id);
                    $('#medicalRecordNumber').val(window.patientIdents[i].identifier);
                    break;
                }else{
                    $('#patientIdentifierId').val(null);
                    $('#medicalRecordNumber').val('');
                }
            }
        });
        initPatientServiceTable(window.patientServiceTypeList);
    } else {
        $('#saveAndDispense').show();
        $('#personUpdate').val('false');
        $('#firstName').val('');
        $('#surname').val('');
        $('#otherNames').val('');
        $('#sex').val('');
        $('#birthDistrict').val('');
        $('#dob').val('');
        $('#age').val('');

        $('#postalAddress').val('');
        $('#physicalAddress').val('');
        $('#alternativeAddress').val('');
        $('#phone').val('');
        $('#email').val('');

        $('#dateEnrolled').val('');
        $('#patientSource').val('');
        $('#serviceType').val('');
        $('#serviceStartDate').val('');
        $('#medicalRecordNumber').val('');
        $('#startRegimenId').val('');
        $('#drugAllergies').val('');
        $('#chronicIllness').val('');
        $("input[name=smoker][value=0]").attr('checked', 'checked');
        $("input[name=drinker][value=0]").attr('checked', 'checked');
        $('#startRegimenId').flexselect();
        $('#identifierType').change(function(){
            $('#medicalRecordNumber').val('');
        });
        addPatientServiceRow();
    }
    $("#newPatientDialog").modal("show");
}

function savePatient(dispense) {
    if($('#firstName').val() == "") {
        alert("Fill in the first name");
        $('#firstName').focus();
        return;
    } else if($('#surname').val() == "") {
        alert("Fill in the Surname");
        $('#surname').focus();
        return;
    } else if($('#sex').val() == "") {
        alert("Select the sex of the patient");
        $('#sex').focus();
        return;
    }  else if($('#dob').val() == "") {
        alert("Select the date of birth of the patient");
        $('#dob').focus();
        return;
    } /*else if($('#serviceType').val() == "" && window.selectedService == "2") {
        alert("Select the service type.");
        //$( "#registrationTabs" ).tabs( "option", "active", 1 );
        $('#serviceType').focus();
        return;
    } */else if($('#dateEnrolled').val() == "" && window.selectedService == "2") {
        alert("Select the date the patient was enrolled.");
        //$( "#registrationTabs" ).tabs( "option", "active", 1 );
        $('#dateEnrolled').focus();
        return;
    } /*else if($('#serviceStartDate').val() == "" && window.selectedService == "2") {
        alert("Select the date the patient started the service.");
        //$( "#registrationTabs" ).tabs( "option", "active", 1 );
        $('#serviceStartDate').focus();
        return;
    } else if($('#serviceStartDate').val() == "" ) {
        alert("Select the patient identifier type");
        return;
    }*/ else if($('#identifierType').val() == "") {
        alert("Select the identifier type");
        $('#identifierType').focus();
        return;
    } else if($('#medicalRecordNumber').val() == "" ) {
        alert("Enter the patient medical record number");
        $('#medicalRecordNumber').focus();
        return;
    }
    var json = "";

    var dob = $("#dob").val();
    var date = new Date(formatDate(dob)); // some mock date
    var dob_milliseconds = date.getTime();
    if(dob_milliseconds > new Date().getTime()) {
        alert("Date of birth of the patient cannot be in future");
        $('#dob').focus();
        $('#dob').val("");
        return;
    }

    var update = $('#personUpdate').val();
    var person = {};
    person.firstName = ($('#firstName').val() == "" ? null : $('#firstName').val())
    person.surname = ($('#surname').val() == "" ? null : $('#surname').val())
    person.otherNames = ($('#otherNames').val() == "" ? null : $('#otherNames').val())
    person.sex = ($('#sex').val() == "" ? null : $('#sex').val());
    person.dateOfBirth = dob_milliseconds;
    person.birthDistrictId = ($('#birthDistrict').val() == "" ? null : $('#birthDistrict').val());
    person.createdBy = window.user_id;

    var personAddress = {};
    personAddress.postalAddress = ($('#postalAddress').val() == "" ? null : $('#postalAddress').val());
    personAddress.physicalAddress = ($('#physicalAddress').val() == "" ? null : $('#physicalAddress').val());
    personAddress.alternativeAddress = ($('#alternativeAddress').val() == "" ? null : $('#alternativeAddress').val());
    personAddress.telNo1 = ($('#phone').val() == "" ? null : $('#phone').val());
    personAddress.emailAddress = ($('#email').val() == "" ? null : $('#email').val());
    personAddress.createdBy = window.user_id;

    var patient = {};
    patient.enrollmentDate = ($('#dateEnrolled').val() == "" ? null : new Date(formatDate($('#dateEnrolled').val())).getTime());
    patient.patientSourceId = ($('#patientSource').val() == "" ? null : $('#patientSource').val());
    patient.startRegimenId = ($('#startRegimenId').val() == "" ? null : $('#startRegimenId').val());
    patient.patientStatusId = ($('#regPatientStatusId').val() == "" ? null : $('#regPatientStatusId').val());
    //patient.serviceStartDate = ($('#serviceStartDate').val() == "" ? null : new Date($('#serviceStartDate').val()).getTime());
    //patient.supportingOrganizationId = ($('#supportingOrganization').val() == "" ? null : $('#supportingOrganization').val());
    patient.drugAllergies = ($('#drugAllergies').val() == "" ? null : $('#drugAllergies').val());
    patient.chronicIllnesses = ($('#chronicIllness').val() == "" ? null : $('#chronicIllness').val());
    patient.smoker = $("input:radio[name=smoker]:checked").val();
    patient.drinker = $("input:radio[name=drinker]:checked").val();
    patient.createdBy = window.user_id;
    var recordnumber=$('#medicalRecordNumber').val();
    var str = recordnumber.replace(/\s/g, '');

    for(var i = 0; i < window.patientIdentifiers.length; i++) {
        if(window.patientIdentifiers[i].identifier == str && update != 'true') {
            alert("Patient Identifier must be unique.");
            $('#medicalRecordNumber').focus();
            $('#medicalRecordNumber').val("");
            return;
        }
    }
    var patientIdentifier = {};
    patientIdentifier.identifier = $('#medicalRecordNumber').val();
    patientIdentifier.identifierTypeId = $('#identifierType').val();
    patientIdentifier.createdBy = window.user_id;
    patientIdentifier.active = 1;

    var patientServicesList = [];
    var patientServiceTypeTable = document.getElementById("patientServiceTypeTable");
    var rows = patientServiceTypeTable.rows.length;
    var serviceIds = []
    for (var i = 1; i < rows; i++) {
        if(window.selectedService == "1") {
            break;
        }
        var pst = {};
        var data = $('#patientServiceTypeTable-' + i).data("pst");
        if(data != null) { //We have an object from the server, we update it.
            data.endDate = $('#endDate-' + i).val() == "" ? null : new Date(formatDate($('#endDate-' + i).val())).getTime();
            patientServicesList.push(data);
        } else {
            if ($('#serviceTypeId-' + i).val() == "") {
                alert("A service type has not been selected");
                return;
            } else if ($('#startDate-' + i).val() == "") {
                alert("Service start date not selected.");
                return;
            }savePatient
            pst.serviceTypeId = $('#serviceTypeId-' + i).val() == "" ? null : $('#serviceTypeId-' + i).val();
            pst.startDate = $('#startDate-' + i).val() == "" ? null : new Date(formatDate($('#startDate-' + i).val())).getTime();
            pst.endDate = $('#endDate-' + i).val() == "" ? null : new Date(formatDate($('#endDate-' + i).val())).getTime();
            patientServicesList.push(pst);
            serviceIds.push(pst.serviceTypeId);
        }
    }

    var url = 'person/json/add';
    if(update == 'true') {
        patient.id = $('#patientId').val();
        person.id = $('#personId').val();
        personAddress.id = $('#personAddressId').val();
        patient.updatedBy = window.user_id;
        person.updatedBy = window.user_id;
        personAddress.updatedBy = window.user_id;
        patientIdentifier.updatedBy = window.user_id;
        patientIdentifier.createdOn = new Date();
        patientIdentifier.active = 1;
        patientIdentifier.uuid = guid();
        patientIdentifier.id = $('#patientIdentifierId').val();
        patientIdentifier.patientId = $('#patientId').val();
        url = 'person/json/update';
    }
    json = "{ \"person\" : " + JSON.stringify(person) + ", \"personAddress\" : " + JSON.stringify(personAddress) + ", \"patient\" : " + JSON.stringify(patient) + ", \"service\" : \"" + window.selectedService + "\", \"patientIdentifier\" : " + JSON.stringify(patientIdentifier) + ", \"patientServiceTypeList\" : " + JSON.stringify(patientServicesList) + "}";
    loading();
    $.ajax({
        url: url,
        headers: {
            "Content-Type": "application/json"
        },
        type: 'POST',
        data: json,
        success: function (data) {
            if(data != 'Error') {
                $('#patientId').val(-1);
                $('#personId').val(-1);
                $('#personAddressId').val(-1);
                $('#patientIdentifierId').val(-1);
                $("#patientName").val((data.surname == null ? "" : data.surname) + " " + (data.firstName == null ? "" : data.firstName) + " " + (data.otherNames == null ? "" : data.otherNames));
                $("#visitPersonId").val(data.id);
                $("#visitPatientId").val(data.patientId);
                if(data.gender == 'Male') {
                    $('[name="pregnant"]').attr('disabled', true);
                } else {
                    $('[name="pregnant"]').attr('disabled', false);
                }
                if(update != 'true') {
                    persons.push({ label : ((person.surname == null || person.surname == "null") ? "" : person.surname) + " " + ((person.firstName == null || person.firstName == "null") ? "" : person.firstName) + " " + ((person.otherNames == null || person.otherNames == "null")? "" : person.otherNames) + " " + (patientIdentifier.identifier == null ? "" : " - " + patientIdentifier.identifier) , value : (person.lastName == null ? "" : person.lastName) + " " + (person.firstName == null ? "" : person.firstName) + " " + (person.otherNames == null ? "" : person.otherNames), id : data.id , patientId : data.patientId, gender: data.gender, dateOfBirth: data.dateOfBirth, serviceTypeId: serviceIds, identifier : patientIdentifier.identifier});
                }
                $("#newPatientDialog").modal("hide");
                 if(dispense) {
                    showVisitDialog(data.patientId);
                 } else {
                     //alert("Patient saved");
                     window.location.reload();
                 }
            } else {
                alert("An error has occurred.");
            }

        },
        complete: function() {
            //window.location.reload();
            loadingEnd();
        },
        error: function () {
            alert("An error has occurred, try again.")
        }
    });
}
//date of birth reverse calculation
//given age
function calculateDateOfBirthColo(){
    var days = $('#age').val();
    if(isNaN(days)){
        $('#age').val("");
        alert("Please provide a valid number.");
        this.value = "";
    }else{
        if(days == "")
            return;
        var date = new Date();
        var dob = new Date();
        dob.setDate(date.getDate() - (parseInt(days) * 365));
        //if (dob.getDay() == 0 || dob.getDay() < 0) {
        //    alert("This person is not yet born.")
        //    $('#dob').val('');
        //}
        $('#dob').val(dob.getDate() + '/' + (dob.getMonth() + 1) + '/' + dob.getFullYear());
        $('#dob').off("change");
    }
}


function guid() {
    function s4() {
        return Math.floor((1 + Math.random()) * 0x10000)
            .toString(16)
            .substring(1);
    }
    return s4() + s4() + '-' + s4() + '-' + s4() + '-' +
        s4() + '-' + s4() + s4() + s4();
}
function initSearchField() {
    loadFacilityInformation();
    persons = new Array();
    $('#autocomplete').attr('disabled', true).attr("placeholder", "Loading patients");
    $.ajax({
        url: 'person/json/personSearchList',
        headers: {
            "Content-Type": "application/json"
        },
        type: 'POST',
        success: function (data) {
            $('#autocomplete').attr('disabled', false).attr("placeholder", "type the patients name");
            persons = $.map(data, function (value, index) {
                return { label : ((value.surname == null || value.surname == "null") ? "" : value.surname) + " " + ((value.firstName == null || value.firstName == "null") ? "" : value.firstName) + " " + ((value.otherNames == null || value.otherNames == "null") ? "" : value.otherNames) + " " + (value.identifier == null ? "" : " - " + value.identifier) , value : (value.lastName == null ? "" : value.lastName) + " " + (value.firstName == null ? "" : value.firstName) + " " + (value.otherNames == null ? "" : value.otherNames), id : value.id , patientId : value.patientId, gender: value.gender, dateOfBirth: value.dateOfBirth, serviceTypeId: value.serviceTypeId, identifier : value.identifier};
            });

            $( "#autocomplete" ).autocomplete({
                source: persons,
                response: function(event, ui) {
                    if (ui.content.length === 0) {
                        $("#selection").html("No results found");
                    } else {
                        $("#selection").empty();
                    }
                },
                select: function(event, ui) {
                    $("#patientName").val(ui.item.label);
                    $("#visitPersonId").val(ui.item.id);
                    $("#visitPatientId").val(ui.item.patientId);
                    var serviceTypeIds = ui.item.serviceTypeId;
                    for(var i =0; i< serviceTypeIds.length; i++){
                        if (serviceTypeIds[i] == 83) {
                            document.getElementById('b').style.visibility = 'visible';
                        }else{
                            document.getElementById('b').style.visibility = 'hidden';
                        }
                    }

                    if(ui.item.gender == 'Male') {
                        $('[name="pregnant"]').attr('disabled', true);
                    } else {
                        $('[name="pregnant"]').attr('disabled', false);
                    }
                    if(findPatientService(ui.item.serviceTypeId, window.service) == -1 && window.selectedService == "2") {
                        alert("The patient is not registered for the selected service, update the patient information before dispensing.");
                        editPatientInfo();
                    } else {
                        showVisitDialog(ui.item.patientId);
                        loadVisitAA();
                    }
                    this.value = ""
                    return false;
                }
            });
        },
        error: function () {

        }
    });
}

function findPatientService(patientService, selectedService) {
    if(patientService.length <= 0)
        return -1;
    for(var i = 0; i < patientService.length; i++) {
        for(var j = 0; j < selectedService.length; j++) {
            if(selectedService[j] == patientService[i])
                return true;
        }
    }
    return -1;
}
/**
 *  This function will show the Visit Dialog. If certain parameters are not loaded, they would
 *  be loaded first before the dialog is displayed.
 */
function showVisitDialog(patientId) {
    //if(window.indication == null || window.patientStatus == null || window.regimen == null ) {
    loading();
        $.ajax({
            url: 'reference/json/visit/listReferences/' + window.accountID + "/" + patientId,
            headers: {
                "Content-Type": "application/json"
            },
            type: 'POST',
            success: function (data) {
                window.patientStatus = data.patientStatusList;
                window.regimen = data.regimenList;
                window.regimenChangeReason = data.regimenChangeReasonList;
                window.visitType = data.visitTypeList;
                window.batchList = data.batchList;
                window.mainDrugList = data.drugList;
                window.mainServiveList = data.ServiceList;
                window.dispensingUnit = data.dispensingUnitList;
                window.dosage = data.dosageList;
                window.indication = data.indicationList;
                window.regimenDrugList = data.regimenDrugList;
                window.familyPlanningMethod = data.familyPlanningMethodList;
                window.familyPlanningMethodChangeReasons = data.familyPlanningMethodChangeReasonList;
                window.insulinList = data.insulinList;
                window.bloodSugarlevelList = data.bloodSugarLevelList;
                var patientStatusId = data.patientStatusId;
                var height = data.height;
                var regimenId = data.regimenID;
                var age = data.age;
                var nextAppointmentDate = data.nextAppointmentDate;
                initVisitDialog(patientStatusId, height, regimenId, age, nextAppointmentDate);
                loadFacilityInformation();
                loadingEnd();
            },
            error: function () {

            }
        });
    //} else {
    //    initVisitDialog();
    //}

}

/**
 *  This function initializes the Visit Dialog which also takes
 *  care of dispensing.
 */
function initVisitDialog(patientStatusId, height, regimenId, age, nextAppointmentDate, patientid) {


    var optionList = document.getElementById('visitTypeId');
    for(var i = optionList.length; i > 0; i--) {
        optionList.remove(i);
    }
    for(var i = 0; window.visitType != null && i <  window.visitType.length; i++) {
        if(window.visitType[i].serviceTypeId == window.service[0]) {
            optionList.add(new Option(window.visitType[i].name, window.visitType[i].id), null);
        }
    }
    /*optionList = document.getElementById("indicationId");
    for(var i = optionList.length; i > 0; i--) {
        optionList.remove(i);
    }

    for(var i = 0; window.indication != null && i < window.indication.length; i++) {
        optionList.add(new Option(window.indication[i].name, window.indication[i].id), null);
    }*/

    optionList = document.getElementById("patientStatusId");
    for(var i = optionList.length; i > 0; i--) {
        optionList.remove(i);
    }

    for(var i = 0; window.patientStatus != null && i < window.patientStatus.length; i++) {
        optionList.add(new Option(window.patientStatus[i].name, window.patientStatus[i].id), null);
    }

    if(patientStatusId != null && patientStatusId != undefined) {
        document.getElementById("patientStatusId").value = patientStatusId;
    }

    optionList = document.getElementById("regimenChangeReasonId");
    for(var i = optionList.length; i > 0; i--) {
        optionList.remove(i);
    }

    for(var i = 0; window.regimenChangeReason != null && i < window.regimenChangeReason.length; i++) {
        optionList.add(new Option(window.regimenChangeReason[i].name, window.regimenChangeReason[i].id), null);
    }

    optionList = document.getElementById("regimenId");
    for(var i = optionList.length; i > 0; i--) {
        optionList.remove(i);
    }

    for(var i = 0; window.regimen != null && i < window.regimen.length; i++) {
        //if(window.regimen[i].serviceTypeId == window.service[0]) {
            optionList.add(new Option(window.regimen[i].name, window.regimen[i].id), null);
        //}

    }

    optionList = document.getElementById("fpMethodChangeReasonId");
    for(var i = optionList.length; i > 0; i--) {
        optionList.remove(i);
    }

    for(var i = 0; window.familyPlanningMethodChangeReasons != null && i < window.familyPlanningMethodChangeReasons.length; i++) {
        optionList.add(new Option(window.familyPlanningMethodChangeReasons[i].name, window.familyPlanningMethodChangeReasons[i].id), null);
    }

    optionList = document.getElementById("familyPlanningMethodId");
    for(var i = optionList.length; i > 0; i--) {
        optionList.remove(i);
    }

    for(var i = 0; window.familyPlanningMethod != null && i < window.familyPlanningMethod.length; i++) {
        //if(window.regimen[i].serviceTypeId == window.service[0]) {
        optionList.add(new Option(window.familyPlanningMethod[i].name, window.familyPlanningMethod[i].id), null);
        //}

    }

    optionList = document.getElementById("Insulin");
    for(var i = optionList.length; i > 0; i--) {
        optionList.remove(i);
    }

    for(var i = 0; window.insulinList != null && i < window.insulinList.length; i++) {
        optionList.add(new Option(window.insulinList[i].name, window.insulinList[i].id), null);
    }

    optionList = document.getElementById("bloodSugarLevel");
    for(var i = optionList.length; i > 0; i--) {
        optionList.remove(i);
    }

    for(var i = 0; window.bloodSugarlevelList != null && i < window.bloodSugarlevelList.length; i++) {
        optionList.add(new Option(window.bloodSugarlevelList[i].name, window.bloodSugarlevelList[i].id), null);
    }

    var txTabel = document.getElementById("visitGrid");
    var rows = txTabel.rows.length;
    for(var i = rows; i > 0; i--) {
        $('#visitGrid-' + i).remove();
    }
    $('#maxPatientsId').html(sessionStorage.getItem("Max_Appointments"));
    getNumberOfAppointments();
    loadSeenPatients();


    if(nextAppointmentDate > 0) {
        var nextDate = new Date(nextAppointmentDate);
        var date = new Date();
        var days = date - nextDate;
        days = days / 1000 / 60 / 60 / 24;


        $('#adherence').val(Math.ceil(days));
    }
    $('#patientBooked').val('');

    $('#daysToNextAppointment').change(function() {
        var days = $('#daysToNextAppointment').val();
        if(isNaN(days)) {
            $('#daysToNextAppointment').val("")
            alert("Please fill in a valid number.");
            this.value = "";
        } else {
            if(days == ""){
                return;
            }
            var date = new Date();
            var nextAppointmentDate = new Date();
            nextAppointmentDate.setDate(date.getDate() + parseInt(days));
            if(nextAppointmentDate.getDay() == 0 || nextAppointmentDate.getDay() == 6) {
                alert("This will fall on a weekend.");
                //$('#nextAppointmentDate').val('');
                //$('#daysToNextAppointment').val('');
            }
            $('#nextAppointmentDate').off('change');
            $('#nextAppointmentDate').val(nextAppointmentDate.getDate() + '/' + (nextAppointmentDate.getMonth() + 1) + '/' + nextAppointmentDate.getFullYear());
        }
    });


    var myDate = new Date();
    var prettyDate = myDate.getDate() + "/" + (myDate.getMonth()+1) + '/' +
        myDate.getFullYear();
    $("#startDate").val(prettyDate);
    loadLastVisit();
    $("#visitDialog").modal("show");
    if(window.clinic == "1") {
        $('.pillcount').show();
        $('.indication').show();
    } else {
        $('.pillcount').hide();
        $('.indication').hide();
    }
    setDispensingDrugs(window.service);
    $('#weight').val('');
    if(height)
        $('#height').val(height);
    else
        $('#height').val('');
    if(regimenId) {
        $('#currentRegimen').val(getRegimenName(regimenId));
        $('#currentRegimenId').val(regimenId);
        $('#regimenId').val('');
        clearTable('visitGrid');
        for (var i = 0; i < window.regimenDrugList.length; i++) {
            if (window.regimenDrugList[i].regimenId == regimenId) {
                window.drugIds[window.regimenDrugList[i].drugId] = true;
            }
        }
    } else {
        $("#regimenId").val('');
        $("#currentRegimen").val('');
        $('#currentRegimenId').val('');

    }
    $('#nextAppointmentDate').change(function() {
        calculateDays();
    });
    $('#ageLabel').html(age + " Years");
    $('#bodySurfaceArea').val('');
    $('#endDate').val('');
    $('#daysToNextAppointment').val('');
    $('#regimenId').val('');
    $('#regimenChangeReasonId').val('');
    $('#pillCount').val('');
    $('#adherence').val('');
    setRadioValue('pregnant','');
    $('#otherDrugs').val('');
    $('#nextAppointmentDate').val('');
    $('#comments').val('');
    enableDispense(regimenId);
    addDispenseDrugRow();
    $('#regimenId_flexselect_dropdown').remove();
    $('#regimenId_flexselect').remove();
    $('#regimenId').show();
    $('#regimenId').flexselect();
    checkPatientStatus();
}
function getNumberOfAppointments(){
    var date = new Date();
    date = date.getFullYear() + '-' + pad((date.getMonth() + 1)) + '-' + pad(date.getDate());
    var servicearea= null;
    if(window.clinic == 1){
        servicearea = "CCC Pharmacy";
    }else if(window.clinic == 2){
        servicearea = "Family Planning Clinic";
    }else if(window.clinic == 6){
        servicearea = "Diabetic Clinic"
    }
    $.ajax({
        url: 'person/json/loadExpectedPatients/' + date +'/'+servicearea,
        headers: {
            "Content-Type": "application/json"
        },
        type: 'POST',
        success: function (data) {
            $('#expectedPatientsId').html(data.Record.toString());
        },
        error: function () {

        }
    });
}
function enableDispense(regimenId) {

    $('.nonFP').hide();
    $('.FP').hide();
    $('.diabetic').hide();
    if (window.clinic == '2') {
        $('.FP').show();
    }
    if (window.clinic == '1') {
        $('.nonFP').show();
        if(!regimenId)
            window.drugIds = new Array();
    } else {
        $('#currentRegimenId').val('')
    }
    if (window.clinic == '6') {
        $('.diabetic').show();
    }
}
        /**
         *  This function saves a visit. It will create a visit Object, Transaction Object, Transaction Items Object,
         *  PatientTransaction Object and PatientTransactionItem Object.
         */
function saveVisit() {
    var visitTable = document.getElementById("visitGrid");
            var rows = visitTable.rows.length;
            if (rows <= 1) {
                alert("Dispense at least one drugs");
                return;
            } else if ($('#startDate').val() == "") {
                alert("Fill in the visit date");
                $('#startDate').focus();
                return;
            } else if ($('#weight').val() == "") {
                alert("Fill in the weight");
                $('#weight').focus();
                return;
            }
            else if ($('#height').val() == "") {
                alert("Fill in the height");
                $('#height').focus();
                return;
            }
            else if ($('#babies').val() == "" && (window.clinic == "6") && ($("#babies").item.style.visibility = 'hidden')) {
                alert("Fill in the Field");
                $('#babies').focus();
                return;
            }

            else if ($('#daysToNextAppointment').val() == "" && (window.clinic == "1" || window.clinic == "1")) {
                alert("Fill in the days to next appointment");
                $('#daysToNextAppointment').focus();
                return;
            } else if (($('#regimenId').val() == "" && $('#currentRegimen').val() == "") && (window.clinic == "1")) {
                if (window.clinic == "1") {
                    alert("Select the regimen for the patient.");
                }
                $('#regimenId').focus();
                return;
            } else if ($('#visitTypeId').val() == "" && (window.clinic == "1" || window.clinic == '2')) {
                alert("Select the visit type.");
                $('#visitTypeId').focus();
                return;
            } else if($('#familyPlanningMethodId').val() == "" && window.clinic == '2') {
                alert("Select the family planning method.");
                $('#familyPlanningMethodId').focus();
                return;
            } else if(($('#regimenId').val() != "" && $('#currentRegimen').val() != "") && (window.clinic == "1") && $('#regimenId').val() != $('#currentRegimenId').val()) {
                if($('#regimenChangeReasonId').val() == "") {
                    alert("Select the regimen change reason.");
                    return;
                }
            }
            else if($('#drugPay').val() == "" && window.clinic == '2') {
                alert("Select the Drug Pay.");
                $('#drugPay').focus();
                return;
            }
            var visit = {};
            visit.patientId = ($('#visitPatientId').val() == "" ? null : $('#visitPatientId').val());
            //visit.personId = ($('#visitPersonId').val() == "" ? null : $('#visitPersonId').val());
            visit.visitTypeId = ($('#visitTypeId').val() == "" ? null : $('#visitTypeId').val());
            visit.weight = ($('#weight').val() == "" ? null : $('#weight').val());
            visit.height = ($('#height').val() == "" ? null : $('#height').val());
            visit.babies = ($('#babies').val() == "" ? null : $('#babies').val());
            visit.bodySurfaceArea = ($('#bodySurfaceArea').val() == "" ? null : $('#bodySurfaceArea').val());
            visit.startDate = ($('#startDate').val() == "" ? null : new Date(formatDate($('#startDate').val())).getTime());
            visit.endDate = ($('#endDate').val() == "" ? null : new Date($('#endDate').val()).getTime());
            visit.appointmentVisitId = ($('#appointmentVisitId').val() == "" ? null : $('#appointmentVisitId').val());
            if($('#regimenId').val() == "") {
                visit.regimenId = $('#currentRegimenId').val() == "" ? null : $('#currentRegimenId').val()
            } else if($('#regimenId').val() != "") {
                visit.regimenId = $('#regimenId').val();
            }
            //visit.regimenId = ($('#currentRegimenId').val() == "" ? null : $('#regimenId').val());
            visit.regimenChangeReasonId = ($('#regimenChangeReasonId').val() == "" ? null : $('#regimenChangeReasonId').val());
            visit.familyPlanningMethodId = ($('#familyPlanningMethodId').val() == "" ? null : $('#familyPlanningMethodId').val());
            visit.drugPay = ($('#drugPay').val() == "" ? null : $('#drugPay').val());
            visit.familyPlanningMethodChangeReasonId = ($('#fpMethodChangeReasonId').val() == "" ? null : $('#fpMethodChangeReasonId').val());
            //visit.pillCount = ($('#pillCount').val() == "" ? null : $('#pillCount').val());
            //visit.adherence = ($('#adherence').val() == "" ? null : $('#adherence').val());
            //visit.indicationId = ($('#indicationId').val() == "" ? null : $('#indicationId').val());
            visit.pregnant = ($("input:radio[name=pregnant]:checked").val() == "" ? '0' : $('input:radio[name=pregnant]:checked').val());
            visit.otherDrugs = ($('#otherDrugs').val() == "" ? null : $('#otherDrugs').val());
            visit.nextAppointmentDate = ($('#nextAppointmentDate').val() == "" ? null : new Date(formatDate($('#nextAppointmentDate').val())).getTime());
            visit.comments = ($('#comments').val() == "" ? null : $('#comments').val());
            visit.tbConfirmed = ($('input:radio[name=pregnant]:checked').val() == "" ? '0' : $('input:radio[name=pregnant]:checked').val());
            visit.patientStatusId = ($('#patientStatusId').val() == "" ? null : $('#patientStatusId').val());
            visit.bloodSugarLevelId = ($('#bloodSugarLevel').val() == "" ? null : $('#bloodSugarLevel').val());
            visit.insulinId = ($('#Insulin').val() == "" ? null : $('#Insulin').val());
            visit.createdBy = window.user_id;

            var transactionItems = new Array();
            var patientTransactionItems = new Array();

            var transaction = {};
            transaction.transactionTypeId = null;
            transaction.date = new Date().getTime();
            transaction.comments = ($('#comments').val() == "" ? null : $('#comments').val());

            transaction.createdBy = window.user_id;

            if (rows < 2) {
                alert("No drug dispensed.");
                return;
            }

            for (var i = 1; i < rows; i++) {
                if ($('#drug-' + i).val() == '') {
                    alert('A drug has been left empty');
                    return;
                } else if ($('#batch_no-' + i).val() == "") {
                    alert("A batch has not been selected");
                    return;
                } else if ($('#qty_disp-' + i).val() == "") {
                    alert("A quantity has not been filled");
                    return;
                }
                else if ($('#price-' + i).val() == "") {
                    alert("Price has not been filled");
                    return;
                }
                var transactionItem = {};
                transactionItem.batchNo = ($('#batch_no-' + i).val() == "" ? null : $('#batch_no-' + i).val());
                transactionItem.unitsIn = ($('#qty_disp-' + i).val() == "" ? null : $('#qty_disp-' + i).val());
                transactionItem.totalCost = ($('#total_cost-' + i).val() == "" ? 0 : parseInt($('#total_cost-' + i).val()));
                transactionItem.unitCost = ($('#price-' + i).val() == "" ? 0 : parseInt($('#price-' + i).val()));
                transactionItem.createdBy = window.user_id;
                transactionItem.drugId = $('#drug-' + i).val();
                transactionItems.push(transactionItem);


                var patientTransactionItem = {};
                patientTransactionItem.dosageId = ($('#dosage-' + i).val() == "" ? null : $('#dosage-' + i).val());
                patientTransactionItem.dosageName = ($('#dosage-' + i).val() == "" ? null : getDosage($('#dosage-' + i).val()));
                patientTransactionItem.duration = ($('#duration-' + i).val() == "" ? null : $('#duration-' + i).val());
                patientTransactionItem.pill_count = ($('#pill_count-' + i).val() == "" ? null : $('#pill_count-' + i).val());
                patientTransactionItem.indication = ($('#indication-' + i).val() == "" ? null : $('#indication-' + i).val());
                patientTransactionItem.createdBy = window.user_id;
                patientTransactionItems.push(patientTransactionItem);
            }
            var json = "{\"visit\" : " + JSON.stringify(visit) + ", \"transaction\" : " + JSON.stringify(transaction) + ", \"transactionItems\" : " + JSON.stringify(transactionItems) + ", \"patientTransactionItems\" : " + JSON.stringify(patientTransactionItems) + "}";
            loading();
            $.ajax({
                url: 'person/json/dispense/' + window.accountID,
                headers: {
                    "Content-Type": "application/json"
                },
                type: 'POST',
                data: json,
                success: function (data) {
                    $("#visitDialog").modal("hide");
                    alert(data);
                },
                complete: function () {
                    //loadingEnd();
                    window.location.reload();
                },
                error: function () {
                    alert(data);
                }
            });

        }
function editIdentifiers(){

}
function getDosage(id) {
    if(window.dosage == undefined || window.dosage.length == 0)
        return ""

    for(var i = 0; i < window.dosage.length; i++) {
        if(window.dosage[i].id == id) {
            return window.dosage[i].name;
        }
    }
    return "";
}
        /**
         *  This function validates administrative password to restrict regime setup
         *
         */

        function clearText()
        {
            $('#uname').val("");
            $('#pass').val("");
        }


        function checkPassword()
        {
            var userN = $('#uname').val();
            var userP = $('#pass').val();


            if (userN == ""){
                alert("Fill in user Name");
                return;
            }
            else if (userP == ""){
                alert("Fill in user Password");
                return;
            }
            else
            {
                $("#regimenPassword").modal("hide");
                loading();
                var json = "{\"username\" : \"" + userN + "\", \"password\" : \"" + userP + "\"}";

                $.ajax({
                    url: 'login/json/authenticateAdmin',
                    headers: {
                        "Content-Type": "application/json"
                    },
                    type: 'POST',
                    data: json,
                    success: function (data) {
                        loadingEnd();
                        if((data !== null)&&(data == "loggedin"))
                            showRegimenListDialog();

                        else if((data !== null)&&(data == "notauthenticated"))

                            alert("Not Authenticated to Access.");

                        else
                            alert("Invalid username/password.");
                            clearText();
                    },
                    error: function () {
                        loadingEnd();
                        clearText();
                    }
                });

            }



        }

        /**
         *  This function validates session Attribute to restrict regime setup
         *
         */

        function checkAttribute() {

          var attribute = $('#att').val() ;


            if (attribute == "null") {
                $("#regimenPassword").modal("show");

            }
            else if (attribute == "true") {

               showRegimenListDialog();

            }



        }


        /**
         *  This function reloads the regimens from the server and displays them
         *  in a dialog.
         */

        function showRegimenListDialog() {
            $.ajax({
                url: 'stocks/json/listRegimen',
                headers: {
                    "Content-Type": "application/json"
                },
                type: 'POST',
                success: function (data) {
                    window.regimenObjs = data;
                    if (window.regimenObjs != null) {
                        createRegimenListContent()
                        $("#listRegimen").dialog("open");
                        clearText();
                    }
                },
                error: function () {

                }
            });

        }

        /**
         *  This function creates regimen list content from the Object {@link window.regimenObjs}
         *
         *  It has an optional parameter, regimen which will replace the object with the same Id as it in the list.
         *
         */
        function createRegimenListContent(regimen) {
            var table = "<p><button onclick='showRegimenDialog()'>Add new Regimen</button></p><div class='jtable-main-container'><table class='jtable'><tr><th class='jtable-column-header'>Regimen name</th></tr>";

            for (var i = 0; i < window.regimenObjs.length; i++) {
                var reg = window.regimenObjs[i];
                if (regimen && reg.id == regimen.id) {
                    window.regimenObjs[i] = regimen;
                    reg = regimen;
                    regimen = null;
                }

                table += "<tr class='jtable-data-row jtable-row-even'><td style='width: 500px'>" + reg.name + "</td><td class='jtable-command-column'><img class='pointer' src='css/themes/metro/edit.png' alt='Edit' onclick='editRegimen(" + i + ")'/></td><td class='jtable-command-column'><img class='pointer' src='css/themes/metro/delete.png' alt='delete' onclick='deleteRegimen(" + i + ")'/></td></tr>";
            }
            table += "</table></div>";

            $('#listRegimenContent').html(table);
        }

        function editRegimen(i) {
            if (window.regimenObjs != null && window.regimenObjs[i] != undefined) {
                var reg = window.regimenObjs[i];
                showRegimenDialog(true, reg);
            }
        }

        /**
         *  This function shows the Regimen Dialog. If certain data are not loaded, they would be loaded first before the
         *  dialog is displayed.
         */
        function showRegimenDialog(edit, reg) {
            if (window.regimenStatus == null || window.regimenType == null || window.serviceTypes == null) {
                $.ajax({
                    url: 'reference/json/regimen/listReferences',
                    headers: {
                        "Content-Type": "application/json"
                    },
                    type: 'POST',
                    success: function (data) {
                        window.regimenStatus = data.regimenStatusList;
                        window.serviceTypes = data.serviceTypeList;
                        window.regimenType = data.regimenTypeList;
                        initRegimenDialog(edit, reg);
                    },
                    error: function () {

                    }
                });
            } else {
                initRegimenDialog(edit, reg);
            }
        }

        /**
         *  This function initializes the Regimen dialog before displaying.
         *  It will load the necessary drop downs
         */
        function initRegimenDialog(edit, reg) {
            var optionList = document.getElementById('regimenTypeId');
            for (var i = optionList.length; i > 0; i--) {
                optionList.remove(i);
            }
            for (var i = 0; window.regimenType != null && i < window.regimenType.length; i++) {
                optionList.add(new Option(window.regimenType[i].name, window.regimenType[i].id), null);
            }
            optionList = document.getElementById("serviceTypeId");
            for (var i = optionList.length; i > 0; i--) {
                optionList.remove(i);
            }

            for (var i = 0; window.serviceTypes != null && i < window.serviceTypes.length; i++) {
                optionList.add(new Option(window.serviceTypes[i].name, window.serviceTypes[i].id), null);
            }

            optionList = document.getElementById("regimenStatusId");
            for (var i = optionList.length; i > 0; i--) {
                optionList.remove(i);
            }

            for (var i = 0; window.regimenStatus != null && i < window.regimenStatus.length; i++) {
                optionList.add(new Option(window.regimenStatus[i].name, window.regimenStatus[i].id), null);
            }
            if (edit && reg) {
                $('#regimenId').val(reg.id);
                $('#code').val(reg.code);
                $('#name').val(reg.name);
                $('#line').val(reg.line);
                $('#regimenTypeId').val(reg.regimenTypeId);
                $('#serviceTypeId').val(reg.serviceTypeId);
                $('#regimenStatusId').val(reg.regimenStatusId);
                $("input[name=visible][value=" + reg.visible + "]").attr('checked', 'checked');
                $('#comments').val(reg.comments);

            } else {
                $('#regimenId').val(-1);
                $('#code').val("");
                $('#name').val("");
                $('#line').val("");
                $('#regimenTypeId').val("");
                $('#serviceTypeId').val("");
                $('#regimenStatusId').val("");
                $("input[name=visible][value=-1]").attr('checked', 'checked');
                $('#comments').val("");
            }
            $("#regimenDialog").dialog("open");
        }

        function saveRegimen() {
            var regimen = {};
            regimen.code = ($('#code').val() == "" ? null : $('#code').val());
            if (regimen.code == null) {
                alert("Enter the regimen code.")
                return;
            }
            regimen.name = ($('#name').val() == "" ? null : $('#name').val());
            if (regimen.name == null) {
                alert("Enter the regimen name.")
                return;
            }
            regimen.line = ($('#line').val() == "" ? null : $('#line').val());
            if (regimen.line != null && isNaN(regimen.line)) {
                alert("Line should be a number.");
                $('#line').val("");
                $('#line').focus();
                return;
            }
            regimen.regimenTypeId = ($('#regimenTypeId').val() == "" ? null : $('#regimenTypeId').val());

            regimen.serviceTypeId = ($('#serviceTypeId').val() == "" ? null : $('#serviceTypeId').val());
            regimen.regimenStatusId = ($('#regimenStatusId').val() == "" ? null : $('#regimenStatusId').val());

            regimen.visible = $("input:radio[name=visible]:checked").val();
            regimen.comments = ($('#comments').val() == "" ? null : $('#comments').val());
            regimen.createdBy = window.user_id;

            var url = 'stocks/json/saveRegimen';
            var regId = $('#regimenId').val();
            if (regId != -1) {
                regimen.updatedBy = window.user_id;
                regimen.id = regId;
                url = 'stocks/json/updateRegimen';
            }

            var json = "{\"regimen\" : " + JSON.stringify(regimen) + "}";
            loading();
            $.ajax({
                url: url,
                headers: {
                    "Content-Type": "application/json"
                },
                type: 'POST',
                data: json,
                success: function (data) {
                    $("#regimenDialog").dialog("close");
                    if (regId != -1) {
                        createRegimenListContent(regimen);
                    }
                    alert(data);
                },
                complete: function () {
                    loadingEnd();
                },
                error: function () {
                    alert(data);
                }
            });
        }

        function deleteRegimen(index) {
            var regimen = window.regimenObjs[index];
            if (regimen == undefined)
                return;
            if (confirm("Delete this record?")) {

                $.ajax({
                    url: 'stocks/json/deleteRegimen/' + regimen.id + '/' + window.user_id,
                    headers: {
                        "Content-Type": "application/json"
                    },
                    type: 'POST',
                    success: function (data) {
                        if (data == 'deleted') {
                            window.regimenObjs.splice(index, 1);
                            createRegimenListContent();
                            alert("Deleted");
                        }
                    },
                    error: function () {
                        alert(data);
                    }
                });
            }
        }

        function showDrugListDialog() {
            loading();
            $.ajax({
                url: 'reference/json/commoditySetup/listReferences',
                headers: {
                    "Content-Type": "application/json"
                },
                type: 'POST',
                success: function (data) {
                    window.mainDrugList = data.drugList;
                    window.serviceTypes = data.serviceTypeList;
                    if (window.mainDrugList != null) {
                        createDrugListContent();
                        $("#listDrugs").dialog("open");
                    }
                },
                error: function () {

                },
                complete: function () {
                    loadingEnd();
                }
            });
        }


function openDrugListDialog() {
    loading();

    $.ajax({
        url: 'reference/json/commoditySetup/listReferences',
        headers: {
            "Content-Type": "application/json"
        },
        type: 'POST',
        success: function (data) {
            $("#visitDialog").modal("hide");
            window.mainDrugList = data.drugList;
            window.serviceTypes = data.serviceTypeList;
            if (window.mainDrugList != null) {
                createDrugListContent();
                $("#listDrugs").dialog("open");

            }
        },
        error: function () {

        },
        complete: function () {
            loadingEnd();
        }
    });
}

        /**
         *  This function creates drugs list content from the Object {@link window.mainDrugList}
         *
         *  It has an optional parameter, regimen which will replace the object with the same Id as it in the list.
         */
        function createDrugListContent(drug) {
            var commoditySearch = document.getElementById('commoditySearch');
            for (var i = commoditySearch.length; i > 0; i--) {
                commoditySearch.remove(i);
            }

            var commodityServiceSelect = document.getElementById('commodityServiceSelect');
            for (var i = commodityServiceSelect.length; i > 0; i--) {
                commodityServiceSelect.remove(i);
            }
            for(var i = 0; i < window.serviceTypes.length; i++) {
                commodityServiceSelect.add(new Option(window.serviceTypes[i].name, window.serviceTypes[i].id), null);
            }
            var table = "<p><button onclick='showDrugDialog()'>Add new Commodity</button></p><div class='jtable-main-container'><table class='jtable'><tr><th class='jtable-column-header'>Drug name</th></tr>";
            for (var i = 0; i < window.mainDrugList.length; i++) {
                var dr = window.mainDrugList[i];
                if (drug && dr.id == drug.id) {
                    window.mainDrugList[i] = drug;
                    dr = drug;
                    drug = null;
                }
                var opt = new Option(dr.name, dr.id);
                $(opt).data('index', i);
                commoditySearch.add(opt);
                table += "<tr class='jtable-data-row jtable-row-even'><td style='width: 500px'>" + dr.name + "</td><td class='jtable-command-column'><img class='pointer' src='css/themes/metro/edit.png' alt='Edit' onclick='editDrug(" + i + ")'/></td><td class='jtable-command-column'><img class='pointer' src='css/themes/metro/delete.png' alt='delete' onclick='deleteDrug(" + i + ")'/></td></tr>";
            }
            table += "</table></div>";
            $('#listDrugsContent').html(table);
            $('#commoditySearch').flexselect();
            $('#commoditySearch').change(function() {
                var sel = $(this).find(":selected");
                var i = $(sel).data('index');
                editDrug(i);
            });
        }

        function editDrug(i) {
            if (window.mainDrugList != null && window.mainDrugList[i] != undefined) {
                var reg = window.mainDrugList[i];
                showDrugDialog(true, reg);
            }
        }

    function onCommodityServiceSelect() {
        var id = $('#commodityServiceSelect').val();
        $('#commoditySearch_flexselect_dropdown').remove();
        $('#commoditySearch_flexselect').remove();
        $('#commoditySearch').show();
        //destroyPlugin($('#commoditySearch'), "events");
        var commoditySearch = document.getElementById('commoditySearch');
        for (var i = commoditySearch.length; i > 0; i--) {
            commoditySearch.remove(i);
        }
        for (var i = 0; i < window.mainDrugList.length; i++) {
            if(window.mainDrugList[i].serviceTypeId == id) {
                var opt = new Option(window.mainDrugList[i].name, window.mainDrugList[i].id);
                $(opt).data('index', i);
                commoditySearch.add(opt);
            }
        }
        $('#commoditySearch').flexselect();
    }
        function showDrugDialog(edit, drug) {
            if (window.genericName == null || window.drugCategory == null || window.drugType == null || window.drugForm == null || window.dispensingUnit == null || window.dosage == null) {
                $.ajax({
                    url: 'reference/json/drug/listReferences',
                    headers: {
                        "Content-Type": "application/json"
                    },
                    type: 'POST',
                    success: function (data) {
                        window.genericName = data.genericNameList;
                        window.drugCategory = data.drugCategoryList;
                        window.drugType = data.drugTypeList;
                        window.drugForm = data.drugFormList;
                        window.dispensingUnit = data.dispensingUnitList;
                        window.dosage = data.dosageList;
                        window.serviceTypes = data.serviceTypeList;
                        window.cdrrCategory = data.cdrrCategoryList;
                        initDrugDialog(edit, drug);
                    },
                    error: function () {

                    }
                });
            } else {
                initDrugDialog(edit, drug);
            }
        }

        function initDrugDialog(edit, drug) {
            var optionList = document.getElementById('genericNameId');
            for (var i = optionList.length; i > 0; i--) {
                optionList.remove(i);
            }
            for (var i = 0; window.genericName != null && i < window.genericName.length; i++) {
                optionList.add(new Option(window.genericName[i].name, window.genericName[i].id), null);
            }
            optionList = document.getElementById("drugCategoryId");
            for (var i = optionList.length; i > 0; i--) {
                optionList.remove(i);
            }

            for (var i = 0; window.drugCategory != null && i < window.drugCategory.length; i++) {
                optionList.add(new Option(window.drugCategory[i].name, window.drugCategory[i].id), null);
            }

            optionList = document.getElementById("drugServiceTypeId");
            for (var i = optionList.length; i > 0; i--) {
                optionList.remove(i);
            }

            for (var i = 0; window.serviceTypes != null && i < window.serviceTypes.length; i++) {
                optionList.add(new Option(window.serviceTypes[i].name, window.serviceTypes[i].id), null);
            }

            optionList = document.getElementById("drugTypeId");
            for (var i = optionList.length; i > 0; i--) {
                optionList.remove(i);
            }

            for (var i = 0; window.drugType != null && i < window.drugType.length; i++) {
                optionList.add(new Option(window.drugType[i].name, window.drugType[i].id), null);
            }

            optionList = document.getElementById("drugFormId");
            for (var i = optionList.length; i > 0; i--) {
                optionList.remove(i);
            }

            for (var i = 0; window.drugForm != null && i < window.drugForm.length; i++) {
                optionList.add(new Option(window.drugForm[i].name, window.drugForm[i].id), null);
            }

            optionList = document.getElementById("dispensingUnitId");
            for (var i = optionList.length; i > 0; i--) {
                optionList.remove(i);
            }

            for (var i = 0; window.dispensingUnit != null && i < window.dispensingUnit.length; i++) {
                optionList.add(new Option(window.dispensingUnit[i].name, window.dispensingUnit[i].id), null);
            }

            optionList = document.getElementById("dosageId");
            for (var i = optionList.length; i > 0; i--) {
                optionList.remove(i);
            }

            for (var i = 0; window.dosage != null && i < window.dosage.length; i++) {
                optionList.add(new Option(window.dosage[i].name, window.dosage[i].id), null);
            }

            optionList = document.getElementById("cdrrCategoryId");
            for (var i = optionList.length; i > 0; i--) {
                optionList.remove(i);
            }

            for (var i = 0; window.cdrrCategory != null && i < window.cdrrCategory.length; i++) {
                optionList.add(new Option(window.cdrrCategory[i].name, window.cdrrCategory[i].id), null);
            }

            if (edit && drug) {
                $('#drugId').val(drug.id);
                $('#drugName').val(drug.name);
                $('#strength').val(drug.strength);
                $('#genericNameId').val(drug.genericNameId);
                $('#kemsaName').val(drug.kemsaName);
                $('#sca1Name').val(drug.sca1Name);
                $('#sca2Name').val(drug.sca2Name);
                $('#sca3Name').val(drug.sca3Name);
                $('#drugCategoryId').val(drug.drugCategoryId);
                $('#drugServiceTypeId').val(drug.serviceTypeId);
                $('#drugTypeId').val(drug.drugTypeId);
                $('#drugFormId').val(drug.drugFormId);
                $('#dispensingUnitId').val(drug.dispensingUnitId);
                $('#packSize').val(drug.packSize);
                $('#dhisId').val(drug.dhisId);
                $('#cdrrCategoryId').val(drug.cdrrCategoryId);
                $('#reorderPoint').val(drug.reorderPoint);
                $('#dosageId').val(drug.dosageId);
                $('#quantity').val(drug.quantity);
                $('#duration').val(drug.duration);
                $("input[name=drug-voided][value=" + drug.voided + "]").attr('checked', 'checked');
                $("input[name=tracer][value=" + drug.tracer + "]").attr('checked', 'checked');
                $("input[name=combination_type][value=" + drug.combination_type + "]").attr('checked', 'checked');
                $('#drugComments').val(drug.comments);
            } else {
                $('#drugId').val(-1);
                $('#drugName').val("");
                $('#strength').val("");
                $('#genericNameId').val("");
                $('#kemsaName').val('');
                $('#sca1Name').val('');
                $('#sca2Name').val('');
                $('#sca3Name').val('');
                $('#drugCategoryId').val("");
                $('#drugTypeId').val("");
                $('#drugFormId').val("");
                $('#dispensingUnitId').val("");
                $('#packSize').val("");
                $('#dhisId').val("");
                $('#cdrrCategoryId').val("");
                $('#reorderPoint').val("");
                $('#dosageId').val("");
                $('#quantity').val("");
                $('#duration').val("");
                $('#drugComments').val("");
                $("input[name=tracer][value=0]").prop('checked', 'checked');
            }
            $("#drugDialog").dialog("open");
        }

        function saveDrug() {
            var drug = {};
            drug.name = ($('#drugName').val() == "" ? null : $('#drugName').val());
            drug.strength = ($('#strength').val() == "" ? null : $('#strength').val());
            drug.genericNameId = ($('#genericNameId').val() == "" ? null : $('#genericNameId').val());
            drug.kemsaName = ($('#kemsaName').val() == "" ? null : $('#kemsaName').val());
            drug.sca1Name = ($('#sca1Name').val() == "" ? null : $('#sca1Name').val());
            drug.sca2Name = ($('#sca2Name').val() == "" ? null : $('#sca2Name').val());
            drug.sca3Name = ($('#sca3Name').val() == "" ? null : $('#sca3Name').val());
            drug.drugCategoryId = ($('#drugCategoryId').val() == "" ? null : $('#drugCategoryId').val());
            drug.serviceTypeId = ($('#drugServiceTypeId').val() == "" ? null : $('#drugServiceTypeId').val());
            drug.drugTypeId = ($('#drugTypeId').val() == "" ? null : $('#drugTypeId').val());
            drug.drugFormId = ($('#drugFormId').val() == "" ? null : $('#drugFormId').val());
            drug.dispensingUnitId = ($('#dispensingUnitId').val() == "" ? null : $('#dispensingUnitId').val());
            drug.packSize = ($('#packSize').val() == "" ? null : $('#packSize').val());
            if (drug.packSize != null && isNaN(drug.packSize)) {
                alert("Pack size should be a number.");
                $('#packSize').val("");
                $('#packSize').focus();
                return;
            }
            drug.dhisId = ($('#dhisId').val() == "" ? null : $('#dhisId').val());
            drug.cdrrCategoryId = ($('#cdrrCategoryId').val() == "" ? null : $('#cdrrCategoryId').val());
            drug.reorderPoint = ($('#reorderPoint').val() == "" ? null : $('#reorderPoint').val());
            if (drug.reorderPoint != null && isNaN(drug.reorderPoint)) {
                alert("Reorder point should be a number.");
                $('#reorderPoint').val("");
                $('#reorderPoint').focus();
                return;
            }
            drug.dosageId = ($('#dosageId').val() == "" ? null : $('#dosageId').val());
            drug.quantity = ($('#quantity').val() == "" ? null : $('#quantity').val());
            if (drug.quantity != null && isNaN(drug.quantity)) {
                alert("Quantity should be a number.");
                $('#quantity').val("");
                $('#quantity').focus();
                return;
            }
            drug.combinationType = ($("input:radio[name=combination_type]:checked").val() == "" ? '0' : $('input:radio[name=combination_type]:checked').val());
            drug.duration = ($('#duration').val() == "" ? null : $('#duration').val());
            if (drug.duration != null && isNaN(drug.duration)) {
                alert("Duration should be a number.");
                $('#duration').val("");
                $('#duration').focus();
                return;
            }
            drug.voided = $("input:radio[name=drug-voided]:checked").val();
            drug.tracer = $("input:radio[name=tracer]:checked").val() == "" ? '0' : $("input:radio[name=tracer]:checked").val();
            drug.comments = ($('#drugComments').val() == "" ? null : $('#drugComments').val());
            drug.createdBy = window.user_id;

            var drugId = $('#drugId').val();
            var url = 'stocks/json/saveDrug';
            if (drugId != -1) {
                drug.id = drugId;
                drug.updatedBy = window.user_id;
                url = 'stocks/json/updateDrug'
            }

            var json = "{\"drug\" : " + JSON.stringify(drug) + "}";
            $.ajax({
                url: url,
                headers: {
                    "Content-Type": "application/json"
                },
                type: 'POST',
                data: json,
                success: function (data) {
                    if(data == "saved") {
                        $("#drugDialog").dialog("close");
                        if (drugId != -1) {
                            createDrugListContent(drug);
                        }
                        alert("Commodity saved");
                    } else if(data == "updated") {
                        $("#drugDialog").dialog("close");
                        if (drugId != -1) {
                            createDrugListContent(drug);
                        }
                        alert("Commodity updated");
                    } else {
                        alert("An error has occured, contact admin.");
                    }
                },
                error: function () {
                    alert("A communication error has occured.");
                }
            });
        }

        function deleteDrug(index) {
            var drug = window.mainDrugList[index];
            if (drug == undefined)
                return;
            if (confirm("Delete this record?")) {

                $.ajax({
                    url: 'stocks/json/deleteDrug/' + drug.id + '/' + window.user_id,
                    headers: {
                        "Content-Type": "application/json"
                    },
                    type: 'POST',
                    success: function (data) {
                        if (data == 'deleted') {
                            window.mainDrugList.splice(index, 1);
                            createDrugListContent();
                        }

                    },
                    error: function () {
                        alert(data);
                    }
                });
            }
        }

        function showIssueReceiveDialog(lab) {
            if (window.transactionType == null || window.accounts == null) {
                loading();
                $.ajax({
                    url: 'reference/json/issue/listReferences/' + window.accountID,
                    headers: {
                        "Content-Type": "application/json"
                    },
                    type: 'POST',
                    success: function (data) {
                        window.transactionType = data.transactionTypeList;
                        window.accounts = data.accountList;
                        window.mainDrugList = data.drugList;
                        window.dispensingUnit = data.dispensingUnitList;
                        window.batchList = data.batchList;
                        initIssueReceiveDialog(lab);
                        loadingEnd();
                    },
                    error: function () {

                    }
                });
            } else {
                initIssueReceiveDialog(lab);
            }
        }

        function initIssueReceiveDialog(lab) {
            window.labItems = lab;
            var optionList = document.getElementById('transactionTypeId');

                for (var i = optionList.length; i > 0; i--) {
                    optionList.remove(i);
                }
                for (var i = 0; window.transactionType != null && i < window.transactionType.length; i++) {
                    if (lab && window.transactionType[i].name != 'Issued To' && window.transactionType[i].name != 'Received from') {
                        continue;
                    }

                    //if (window.is_bulk == 1){
                    //    var option = new Option('Issued To', 94);
                    //    $(option).data('obj', window.transactionType[i]);
                    //
                    //    optionList.add(option, null);
                    //    break;
                    //
                    //}
                    else {
                        var option = new Option(window.transactionType[i].name, window.transactionType[i].id);

                        $(option).data('obj', window.transactionType[i]);

                        optionList.add(option, null);

                    }

                }

            optionList = document.getElementById('source');
            for (var i = optionList.length; i > 0; i--) {
                optionList.remove(i);
            }
//
            for (var i = 0; window.accounts != null && i < window.accounts.length; i++) {
                optionList.add(new Option(window.accounts[i].name, window.accounts[i].id), null);
            }

            optionList = document.getElementById('destination');
            for (var i = optionList.length; i > 0; i--) {
                optionList.remove(i);
            }

            for (var i = 0; window.accounts != null && i < window.accounts.length; i++) {
                if ($("#visitDialog").data() != undefined && $("#visitDialog").data('bs.modal') != undefined && $("#visitDialog").data('bs.modal').isShown) {
                    $("#visitDialog").modal("hide");
                    if (window.accounts[i].id == window.accountID)
                        optionList.add(new Option(window.accounts[i].name, window.accounts[i].id), null);
                } else {
                    optionList.add(new Option(window.accounts[i].name, window.accounts[i].id), null);
                }
            }
            var myDate = new Date();
            var prettyDate = myDate.getDate() + '/' + (myDate.getMonth() + 1) + '/' +
                myDate.getFullYear();
            $("#transactionDate").val(prettyDate);
            $("referenceNo").focus();
            clearTable("transactionTable");
            /**/
            addTransactionRow(lab);
            $("#receiveIssueDialog").modal("show");
        }

        function saveIssueReceive(reload) {


            var transaction = {};
            transaction.transactionTypeId = ($('#transactionTypeId').val() == "" ? null : $('#transactionTypeId').val());
            transaction.referenceNo = ($('#referenceNo').val() == "" ? null : $('#referenceNo').val());

            var tx_date = ($('#transactionDate').val() == "" ? null : $('#transactionDate').val());
            transaction.date = new Date(formatDate(tx_date)).getTime();
            transaction.comments = ($('#transactionComments').val() == "" ? null : $('#transactionComments').val());
            transaction.createdBy = window.user_id;

            var txTabel = document.getElementById("transactionTable");
            var rows = txTabel.rows.length;
            var transactionItems = new Array();
            var batchTransactionItems = new Array();

            if($('#source').val() == "") {
                alert("No source selected, ensure there are possible sources defined");
                return;
            }
            if($('#destination').val() == "") {
                alert("No destination selected, ensure there are possible destination defined");
                return;
            }

            var sourceId = ($('#source').val() == "" ? null : $('#source').val());
            if (rows < 2) {
                alert("No commodity defined.");
                return;
            }
            for (var i = 1; i < rows; i++) {
                if ($('#tx-drug-' + i).val() == '') {
                    alert('A drug has been left empty');
                    return;
                } else if ($('#tx-batchNo-' + i).val() == "") {
                    alert("A batch has not been selected");
                    return;
                } else if ($('#tx-qty-' + i).val() == "" || $('#tx-qty-' + i).val() == "0") {
                    alert("Fill in the number of packs or free units.");
                    return;
                }
                var transactionItem = {};
                transactionItem.accountId = ($('#destination').val() == "" ? null : $('#destination').val());
                transactionItem.batchNo = ($('#tx-batchNo-' + i).val() == "" ? null : $('#tx-batchNo-' + i).val());
                transactionItem.unitsIn = ($('#tx-qty-' + i).val() == "" ? null : $('#tx-qty-' + i).val());
                transactionItem.drugId = ($('#tx-drug-' + i).val() == "" ? null : $('#tx-drug-' + i).val());
                transactionItem.unitCost = ($('#tx-unit-cost-' + i).val() == "" ? null : $('#tx-unit-cost-' + i).val());
                transactionItem.totalCost = ($('#tx-total-cost-' + i).val() == "" ? null : $('#tx-total-cost-' + i).val());
                transactionItem.createdBy = window.user_id;
                transactionItems.push(transactionItem);


                var batchTransactionItem = {};
                batchTransactionItem.noOfPacks = ($('#tx-packs-' + i).val() == "" ? null : $('#tx-packs-' + i).val());
                batchTransactionItem.packSize = ($('#tx-packSize-' + i).val() == "" ? null : $('#tx-packSize-' + i).val());
                batchTransactionItem.openPacks = ($('#tx-open-packs-' + i).val() == "" ? null : $('#tx-open-packs-' + i).val());

                var exp_date = ($('#tx-expDate-' + i).val() == "" ? null : $('#tx-expDate-' + i).val());
                if (exp_date != null) {
                    var date = new Date(formatDate(exp_date));
                    var date_milliseconds = date.getTime();
                    batchTransactionItem.dateOfExpiry = date_milliseconds;
                }
                batchTransactionItem.createdBy = window.user_id;
                batchTransactionItems.push(batchTransactionItem);
            }



            var json = "{\"transaction\" : " + JSON.stringify(transaction) + ", \"transactionItemList\" : " + JSON.stringify(transactionItems) + ", \"batchTransactionItemList\" : " + JSON.stringify(batchTransactionItems) + "}";
            loading();
            $.ajax({
                url: 'stocks/json/saveTransactions/' + sourceId,
                headers: {
                    "Content-Type": "application/json"
                },
                type: 'POST',
                data: json,
                success: function (data) {

                    if(window.labItems) {
                        loadingEnd();
                        window.labItems = false;
                    } else if (reload) {
                        reloadDrugs();

                    } else {
                        loadingEnd();
                    }
                    alert(data);
                    $("#print-commodity").modal("show");


                },
                error: function () {
                    $("#receiveIssueDialog").modal("hide");
                    alert(data);
                },
                complete: function () {

                }
            });
        }

        function closeDialog(dialog) {
            $("#" + dialog).dialog("close");
        }

        function confirmClose(dialog) {
            if (confirm("Are you sure you want to cancel?")) {
                closeDialog(dialog);
                return true;
            }
            return false;
        }

        function editPatientInfo() {
            var personId = $("#visitPersonId").val();
            if (personId != -1 && personId != undefined) {
                $.ajax({
                    url: 'person/json/getPerson/' + personId,
                    headers: {
                        "Content-Type": "application/json"
                    },
                    type: 'POST',
                    success: function (data) {
                        if (data.person == undefined) {
                            alert('Error loading person data, contact admin.');
                            return;
                        }
                        window.person = data.person;
                        window.personAddress = data.personAddress;
                        window.patient = data.patient;
                        window.patientIdentifier = data.patientIdentifier;
                        window.patientIdents = data.patientIdentifiers;
                        window.patientServiceTypeList = data.patientServiceTypeList;
                        showNewPatientDialog(true, personId);
                    },
                    error: function () {

                    }
                });

            }
        }

        /**
         *  Fetches the service types from the database and populates the service select in the home page.
         */
        function initServiceSelect() {
            $.ajax({
                url: 'reference/json/listServiceType',
                headers: {
                    "Content-Type": "application/json"
                },
                type: 'POST',
                success: function (data) {
                    window.serviceTypes = data.Records;
                    var optionList = document.getElementById('serviceTypeSelect');
                    for (var i = optionList.length; i > 0; i--) {
                        optionList.remove(i);
                    }
                    for (var i = 0; window.serviceTypes != null && i < window.serviceTypes.length; i++) {
                        optionList.add(new Option(window.serviceTypes[i].name, window.serviceTypes[i].name), null);
                    }
                },
                error: function () {

                }
            });
        }

        function onServiceSelected() {
            window.selectedService = $('#serviceTypeSelect').val();
            $('#clinicTypeSelect').val('');

            var optionList = document.getElementById('clinicTypeSelect');
            for (var i = optionList.length; i > 0; i--) {
                optionList.remove(i);
            }

            $('#search').hide();
            if (window.selectedService == '2') {
                var art = new Option("CCC Pharmacy", "1");
                $(art).attr('name', "ART");
                if (privileges.mod_servicechronicART == true){
                    optionList.add(art, null);
                    $('.hivPatientsInfo').show();
                }

                var fp = new Option("Family Planning Clinic", "2");
                $(fp).attr('name', "FP/RH");
                if(privileges.mod_servicechronicFP == true)
                    optionList.add(fp, null);

                var db = new Option("Diabetic Clinic", "6");
                $(db).attr('name', "Diabetic");
                if(privileges.mod_servicechronicDiabetic == true)
                    optionList.add(db, null);


                $('#drugInRegimenButton').show();
                $('.chronicPatientInfo').show();
            } else if (window.selectedService == '1') {
                var art = new Option("Nutrition Clinic", "3");
                $(art).attr('name', "HIV Nutrition");
                if(privileges.mod_servicegeneralNutrition == true)
                    optionList.add(art, null);

                var lab = new Option("Lab", "4");
                $(lab).attr('name', "LAB");
                if(privileges.mod_servicegeneralLab == true)
                    optionList.add(lab, null);

                var EMMS = new Option("General Pharmacy", "5");
                $(EMMS).attr('name', "EMMS");
                if(privileges.mod_servicegeneralPharmacy == true)
                    optionList.add(EMMS, null);

                $('.hivPatientsInfo').hide();
                $('#drugInRegimenButton').hide();
                $('.chronicPatientInfo').hide();
            }
        }

function onClinicSelected() {
    window.clinic = $('#clinicTypeSelect').val();
    var service = $('#clinicTypeSelect').find(":selected").attr('name');
    $('#labItems').hide();
            /*if (window.clinic == '1' || window.clinic == '2') {
                $('#hivPatientsInfo').show();
                $('#drugInRegimenButton').show();
                $('#chronicPatientInfo').show();
            } else */
    if (window.clinic == '') {
        $('#search').hide();
    }
            /*else {
                $('#hivPatientsInfo').hide();
                $('#drugInRegimenButton').hide();
                $('#chronicPatientInfo').hide();
            }*/
    $('#search').show();
    window.service = new Array();
    if (service == 'EMMS') {
        for (var i = 0; i < window.serviceTypes.length; i++) {
            if ('MALARIA' == window.serviceTypes[i].name || 'EMMS' == window.serviceTypes[i].name) {
                window.service.push(window.serviceTypes[i].id);
            }
        }
                //setDispensingDrugs(window.service);
                return;
    } else if (service == 'LAB') {
        $('#search').hide();
        $('#labItems').show();
    }
    if($('#serviceTypeSelect').val() == '1') {
        $('.visitDialog').css('background-color', '#FEFFCE');
    } else {
        if(service == 'ART') {
            $('.visitDialog').css('background-color', '#FECBCC');
        } else if(service == 'FP/RH') {
            $('.visitDialog').css('background-color', '#C3D9F9');
        } else if(service == 'Diabetic') {
            $('.visitDialog').css('background-color', '#FACDA9');
        }
    }

    for (var i = 0; i < window.serviceTypes.length; i++) {
        var name = window.serviceTypes[i].name;
        if (service == name) {
            window.service.push(window.serviceTypes[i].id);
                    //setDispensingDrugs(window.service);
        }
        if(service == 'ART' && (name == 'PEP' || name == 'PMTCT' || name == 'OI Only')) {
            window.service.push(window.serviceTypes[i].id);
        }
        if ('MALARIA' == window.serviceTypes[i].name || 'EMMS' == window.serviceTypes[i].name) {
            window.service.push(window.serviceTypes[i].id);
        }
    }
 }


        function findIndexInArray(value, array, compareFunc) {

            //If not defined, use default comparision
            if (!compareFunc) {
                compareFunc = function (a, b) {
                    return a == b;
                };
            }

            for (var i = 0; array != undefined && i < array.length; i++) {
                if (compareFunc(value, array[i])) {
                    return i;
                }
            }

            return -1;
        }

        function transactionTypeSelected() {
            var selected = $('#transactionTypeId').find(":selected");
            var txType = $(selected).data('obj');
            var accTypes = txType.accountTypeTransactionTypesById;

            var accounts = new Array();
            for (var i = 0; i < accTypes.length; i++) {
                accounts.push(accTypes[i].accountTypeId);
            }
            var optionList = document.getElementById('source');
            for (var i = optionList.length; i > 0; i--) {
                optionList.remove(i);
            }

            for (var i = 0; window.accounts != null && i < window.accounts.length; i++) {
                if (findIndexInArray(window.accounts[i].accountTypeId, accounts) != -1)
                    optionList.add(new Option(window.accounts[i].name, window.accounts[i].id), null);
            }

            optionList = document.getElementById('destination');
            for (var i = optionList.length; i > 0; i--) {
                optionList.remove(i);
            }

            for (var i = 0; window.accounts != null && i < window.accounts.length; i++) {
                if (findIndexInArray(window.accounts[i].accountTypeId, accounts) != -1)
                    optionList.add(new Option(window.accounts[i].name, window.accounts[i].id), null);
            }
            setSourceDest(txType);
        }

        function showStockTakingDialog() {
            $('#stockTakingDialog').modal('show');
            return;
            clearTable('stockTakingTable');
            $('#countedBy').val('');
            $('#stockDate').val('');
            $('#verifiedBy').val('');
            $('#sheetNo').val('');
            $('#location').val('');
            $('#keyedInBy').val('');
            if (window.mainDrugList == null) {
                $.ajax({
                    url: 'reference/json/stocks/listReferences/' + window.accountID,
                    headers: {
                        "Content-Type": "application/json"
                    },
                    type: 'POST',
                    success: function (data) {
                        window.mainDrugList = data.drugList;
                        window.batchList = data.batchList;
                        window.storeList = data.accountList;

                        var optionList = document.getElementById('location');
                        for (var i = optionList.length; i > 0; i--) {
                            optionList.remove(i);
                        }
                        for (var i = 0; i < window.storeList.length; i++) {
                            optionList.add(new Option(window.storeList[i].name, window.storeList[i].id), null);
                        }

                        $('#stockTakingDialog').modal('show');
                    },
                    error: function () {

                    }
                });
            } else {
                $('#stockTakingDialog').modal('show');
            }
        }

        function saveStockTaking() {
            var stockTakeSheet = {};

            stockTakeSheet.countedBy = ($('#countedBy').val() == "" ? null : $('#countedBy').val());

            var stockDate = ($('#stockDate').val() == "" ? null : $('#stockDate').val());
            stockTakeSheet.takeDate = new Date(stockDate).getTime();
            stockTakeSheet.verifiedBy = ($('#verifiedBy').val() == "" ? null : $('#verifiedBy').val());
            stockTakeSheet.referenceNo = ($('#sheetNo').val() == "" ? null : $('#sheetNo').val());
            stockTakeSheet.createdBy = window.user_id;

            var txTable = document.getElementById("stockTakingTable");
            var rows = txTable.rows.length;
            var stockTakeItems = new Array();

            for (var i = 1; i < rows; i++) {

                var stockTakeItem = {};
                //transactionItem.accountId = ($('#destination').val() == "" ? null : $('#destination').val());
                stockTakeItem.batchNo = ($('#batch_no-' + i).val() == "" ? null : $('#batch_no-' + i).val());
                stockTakeItem.quantity = ($('#qty-' + i).val() == "" ? null : $('#qty-' + i).val());
                stockTakeItem.accountId = 1122;

                stockTakeItem.price = ($('#price-' + i).val() == "" ? null : $('#price-' + i).val());
                stockTakeItem.drugId = ($('#drug-' + i).val() == "" ? null : $('#drug-' + i).val());
                stockTakeItem.createdBy = window.user_id;

                var exp_date = ($('#expDate-' + i).val() == "" ? null : $('#expDate-' + i).val());
                if (exp_date != null) {
                    var date = new Date(exp_date);
                    var date_milliseconds = date.getTime();
                    stockTakeItem.expiryDate = date_milliseconds;
                }

                stockTakeItems.push(stockTakeItem);
            }

            var json = "{\"stockTakeSheet\" : " + JSON.stringify(stockTakeSheet) + ", \"stockTakeItemList\" : " + JSON.stringify(stockTakeItems) + "}";
            $.ajax({
                url: 'stocks/json/saveStockTake',
                headers: {
                    "Content-Type": "application/json"
                },
                type: 'POST',
                data: json,
                success: function (data) {
                    $("#stockTakingDialog").modal("hide");
                    loadVarianceReport($('#sheetNo').val());
                },
                error: function () {
                    alert(data);
                }
            });
        }

        function loadVarianceReport(adjust) {
            var sheetNo = $('#sheetNo').val();
            if (sheetNo == '') {
                alert("Fill in the sheet number");
                return;
            }

            var json = {}
            json.sheetNo = sheetNo;
            $.ajax({
                url: 'stocks/json/getStockVariance',
                headers: {
                    "Content-Type": "application/json"
                },
                data: JSON.stringify(json),
                type: 'POST',
                success: function (data) {
                    if (data.stockTakeSheet && data.stockTakeItemList) {
                        window.stockTakeSheet = data.stockTakeSheet;
                        window.stockTakingItemList = data.stockTakeItemList;
                        window.mainDrugList = data.drugList;
                    }
                    createVarianceReportTable(adjust);
                },
                error: function () {
                    alert(data);
                }
            });
        }

        /**
         *  Saves variance report which has been filled
         */
        function saveVarianceReport() {
            for (var i = 0; i < window.stockTakingItemList.length; i++) {
                var packs = ($('#packs-' + (i )).val() == "" ? null : $('#packs-' + (i)).val());
                var looseQty = ($('#looseQty-' + (i )).val() == "" ? null : $('#looseQty-' + (i)).val());
                var packSize = ($('#packSize-' + (i )).val() == "" ? null : $('#packSize-' + (i)).val());
                window.stockTakingItemList[i].packs = packs;
                window.stockTakingItemList[i].looseQty = looseQty;
                window.stockTakingItemList[i].packSize = packSize;
            }

            var json = "{\"stockTakeItemList\" : " + JSON.stringify(window.stockTakingItemList) + "}";
            $.ajax({
                url: 'stocks/json/saveStockVariance/' + window.accountID + "/" + window.user_id,
                headers: {
                    "Content-Type": "application/json"
                },
                data: json,
                type: 'POST',
                success: function (data) {
                    alert(data);
                    $('#stockVarianceDialog').modal("hide");
                },
                error: function () {
                    alert(data);
                }
            });
        }

        /**
         *  Allows for adjustments to be done per line item.
         */
        function saveStockTakeAdjustments() {
            var makeAdjustment = false;
            var adjustedList = new Array();
            for (var i = 0; i < window.stockTakingItemList.length; i++) {
                var adjust = $('#adjust-' + (i)).is(":checked");
                if (adjust && !window.stockTakingItemList[i].adjusted) {
                    window.stockTakingItemList[i].adjusted = 1;
                    adjustedList.push(window.stockTakingItemList[i]);
                }
            }
            if (adjustedList.length == 0)
                return;
            var json = "{\"stockTakeItemList\" : " + JSON.stringify(adjustedList) + "}";
            $.ajax({
                url: 'stocks/json/adjustStockVariance/' + window.accountID + "/" + window.user_id,
                headers: {
                    "Content-Type": "application/json"
                },
                data: json,
                type: 'POST',
                success: function (data) {
                    if(data == "saved") {
                        alert("Adjustments saved.");
                        $('#stockVarianceDialog').modal("hide");
                    } else {
                        alert("Error adjusting stock, please contact admin.")
                    }


                },
                error: function () {
                    alert(data);
                }
            });
        }

        function printStockTakingForm() {
            var sheetNo = $('#sheetNo').val();
            //if(sheetNo != '') {
            window.location = 'stocktake.jsp?sheetNo' + sheetNo;

        }

        function onRegimenSelected() {
            if (window.clinic != '1') { //We are not dispensing ART Drugs so no need to go in here.
                return;
            }
            var regimenId = $('#regimenId').val();
            window.drugIds = new Array();

            if($('#currentRegimenId').val() != regimenId) {
                $('#regimenChangeReasonId').prop("disabled", false);
                for(var i = 0; window.visitType != null && i <  window.visitType.length; i++) {
                    if(window.visitType[i].name == "Switch Regimen") {
                        $('#visitTypeId').val(window.visitType[i].id);
                    }
                }

            } else {
                $('#regimenChangeReasonId').prop("disabled", true);
                for(var i = 0; window.visitType != null && i <  window.visitType.length; i++) {
                    if(window.visitType[i].name == "Routine Refill") {
                        $('#visitTypeId').val(window.visitType[i].id);
                    }
                }
                //return;
            }
            clearTable('visitGrid');
            for (var i = 0; i < window.regimenDrugList.length; i++) {
                if (window.regimenDrugList[i].regimenId == regimenId) {
                    window.drugIds[window.regimenDrugList[i].drugId] = true;
                }
            }
            addDispenseDrugRow();
        }


        /**
         *  Given the service ID, it activates the drugs to be used to dispense.
         * @param serviceId
         */
        function setDispensingDrugs(serviceId) {
            window.drugIds = new Array();
            for (var i = 0; window.mainDrugList != undefined && i < window.mainDrugList.length; i++) {
                if (window.mainDrugList[i].serviceTypeId == serviceId[0] || (serviceId[1] != undefined && window.mainDrugList[i].serviceTypeId == serviceId[1])) {
                    window.drugIds[window.mainDrugList[i].id] = true
                } else {
                    window.drugIds[window.mainDrugList[i].id] = false;
                }
            }
        }
///
        function logout() {
            $.ajax({
                url: 'login/json/logoutUser',
                headers: {
                    "Content-Type": "application/json"
                },
                type: 'POST',
                success: function (data) {
                    window.location = 'index.jsp';
                },
                error: function () {
                    window.location = 'index.jsp';
                }
            });

        }
///

        function showReportDialog(id) {
            $('#reportDialog').modal("show");
            $('#' + id).show();
            if (id == 'drugConsumption') {
                var drugsListSelect = document.getElementById('drugConsumptionSelect');
                for (var i = drugsListSelect.length; i > 0; i--) {
                    drugsListSelect.remove(i);
                }
                if (window.mainDrugList != null) {
                    for (var i = 0; i < window.mainDrugList.length; i++) {
                        drugsListSelect.add(new Option(window.mainDrugList[i].name, window.mainDrugList[i].id));
                    }
                }
                var date = new Date();
                var year = date.getFullYear();
                var yearListSelect = document.getElementById('drugConsumptionYearSelect');
                for (var i = yearListSelect.length; i > 0; i--) {
                    yearListSelect.remove(i);
                }
                for (var i = 2009; i <= year; i++) {
                    yearListSelect.add(new Option(i, i));
                }
                var serviceSelect = document.getElementById('drugServiceSelect');
                for (var i = serviceSelect.length; i > 0; i--) {
                    serviceSelect.remove(i);
                }
                if (window.serviceTypes != null) {
                    for (var i = 0; i < window.serviceTypes.length; i++) {
                        var name = window.serviceTypes[i].name;
                        if(name == 'ART' || name == 'PEP' || name == 'PMTCT' || name == 'OI Only') {
                            if(privileges.mod_ART_stock_reports == true )
                                serviceSelect.add(new Option(window.serviceTypes[i].name, window.serviceTypes[i].id));
                            else
                                continue;
                        } else {
                            if(privileges.mod_general_stock_reports == true )
                                serviceSelect.add(new Option(window.serviceTypes[i].name, window.serviceTypes[i].id));
                        }
                    }
                }
            } else if (id == 'patientServices') {
                var serviceSelect = document.getElementById('serviceSelect');
                for (var i = serviceSelect.length; i > 0; i--) {
                    serviceSelect.remove(i);
                }
                if (window.serviceTypes != null) {
                    for (var i = 0; i < window.serviceTypes.length; i++) {
                        var name = window.serviceTypes[i].name;
                        if(name == 'ART' || name == 'PEP' || name == 'PMTCT' || name == 'OI Only') {
                            if(privileges.mod_ART_patient_reports == true )
                                serviceSelect.add(new Option(window.serviceTypes[i].name, window.serviceTypes[i].id));
                            else
                                continue;
                        } else {
                            //serviceSelect.add(new Option(window.serviceTypes[i].name, window.serviceTypes[i].id));
                        }
                    }
                }
                var date = new Date();
                var year = date.getFullYear();
                var yearListSelect = document.getElementById('patientServiceYearSelect');
                for (var i = yearListSelect.length; i > 0; i--) {
                    yearListSelect.remove(i);
                }
                for (var i = 2009; i <= year; i++) {
                    yearListSelect.add(new Option(i, i));
                }
            } else if (id == 'patientListingByStatusReport') {
                var statusSelect = document.getElementById('patientStatusSelect');
                for (var i = statusSelect.length; i > 0; i--) {
                    statusSelect.remove(i);
                }
                if (window.patientStatus != null) {
                    for (var i = 0; i < window.patientStatus.length; i++) {
                        statusSelect.add(new Option(window.patientStatus[i].name, window.patientStatus[i].id));
                    }
                }
            } else if (id == 'listingRegimenMissedAppointments') {
                var serviceSelect = document.getElementById('missedAppointmentServiceSelect');
                for (var i = serviceSelect.length; i > 0; i--) {
                    serviceSelect.remove(i);
                }
                if (window.serviceTypes != null) {
                    for (var i = 0; i < window.serviceTypes.length; i++) {
                        var name = window.serviceTypes[i].name;
                        if(name == 'ART' || name == 'PEP' || name == 'PMTCT' || name == 'OI Only') {
                            if(privileges.mod_ART_patient_reports == true )
                                serviceSelect.add(new Option(window.serviceTypes[i].name, window.serviceTypes[i].id));
                            else
                                continue;
                        } else {
                            serviceSelect.add(new Option(window.serviceTypes[i].name, window.serviceTypes[i].id));
                        }
                    }
                }
            } else if (id == 'drugUsedReport') {
                var drugUsedSelect = document.getElementById('drugUsedSelect');
                for (var i = drugUsedSelect.length; i > 0; i--) {
                    drugUsedSelect.remove(i);
                }
                if (window.mainDrugList != null) {
                    for (var i = 0; i < window.mainDrugList.length; i++) {
                        drugUsedSelect.add(new Option(window.mainDrugList[i].name, window.mainDrugList[i].id));
                    }
                }
            } else if (id == 'enrollmentReport') {
                var serviceSelect = document.getElementById('enrollmentServiceSelect');
                for (var i = serviceSelect.length; i > 0; i--) {
                    serviceSelect.remove(i);
                }
                if (window.serviceTypes != null) {
                    for (var i = 0; i < window.serviceTypes.length; i++) {
                        var name = window.serviceTypes[i].name;
                        if(name == 'ART' || name == 'PEP' || name == 'PMTCT' || name == 'OI Only') {
                            if(privileges.mod_ART_patient_reports == true )
                                serviceSelect.add(new Option(window.serviceTypes[i].name, window.serviceTypes[i].id));
                            else
                                continue;
                        } else {
                            serviceSelect.add(new Option(window.serviceTypes[i].name, window.serviceTypes[i].id));
                        }
                    }
                }
                var date = new Date();
                var year = date.getFullYear();
                var yearListSelect = document.getElementById('enrollmentYearSelect');
                for (var i = yearListSelect.length; i > 0; i--) {
                    yearListSelect.remove(i);
                }
                for (var i = 2005; i <= year; i++) {
                    yearListSelect.add(new Option(i, i));
                }
            } else if (id == 'bmiReport') {
                var serviceSelect = document.getElementById('bmiServiceSelect');
                for (var i = serviceSelect.length; i > 0; i--) {
                    serviceSelect.remove(i);
                }
                if (window.serviceTypes != null) {
                    for (var i = 0; i < window.serviceTypes.length; i++) {
                        var name = window.serviceTypes[i].name;
                        if(name == 'ART' || name == 'PEP' || name == 'PMTCT' || name == 'OI Only') {
                            if(privileges.mod_ART_patient_reports == true )
                                serviceSelect.add(new Option(window.serviceTypes[i].name, window.serviceTypes[i].id));
                            else
                                continue;
                        } else {
                            if(name == 'HIV Nutrition')
                                serviceSelect.add(new Option(window.serviceTypes[i].name, window.serviceTypes[i].id));
                        }
                    }
                }
            } else if (id == 'monthlyReport') {
                var date = new Date();
                var year = date.getFullYear();
                var yearListSelect = document.getElementById('monthlyYear');
                for (var i = yearListSelect.length; i > 0; i--) {
                    yearListSelect.remove(i);
                }
                for (var i = 2009; i <= year; i++) {
                    yearListSelect.add(new Option(i, i));
                }
            }
            else if (id == 'receiveIssueReports') {
                var supplierSelect = document.getElementById('supplierSelect');
                for (var i = supplierSelect.length; i > 0; i--) {
                    supplierSelect.remove(i);
                }
                if (window.supplierAccounts != null) {
                    for (var i = 0; i < window.supplierAccounts.length; i++) {
                        supplierSelect.add(new Option(window.supplierAccounts[i].name, window.supplierAccounts[i].id));
                    }
                }

            }


            else if (id == 'ConsumptionDispensing') {
                var serviceListSelect = document.getElementById('VisitConsumptionDispensingSelect');
                for (var i = serviceListSelect.length; i > 0; i--) {
                    serviceListSelect.remove(i);
                }
                if (window.mainDrugList != null) {
                    for (var i = 0; i < window.mainDrugList.length; i++) {
                        serviceListSelect.add(new Option(window.mainDrugList[i].name, window.mainDrugList[i].id));
                    }
                }
                var date = new Date();
                var year = date.getFullYear();
                var yearListSelect = document.getElementById('ConsumptionDispensingYearSelect');
                for (var i = yearListSelect.length; i > 0; i--) {
                    yearListSelect.remove(i);
                }
                for (var i = 2009; i <= year; i++) {
                    yearListSelect.add(new Option(i, i));
                }
                var serviceSelect = document.getElementById('ConsumptionDispensingServiceSelect');
                for (var i = serviceSelect.length; i > 0; i--) {
                    serviceSelect.remove(i);
                }
                if (window.serviceTypes != null) {
                    for (var i = 0; i < window.serviceTypes.length; i++) {
                        var name = window.serviceTypes[i].name;
                        if(name == 'ART' || name == 'PEP' || name == 'PMTCT' || name == 'OI Only') {
                            if(privileges.mod_ART_stock_reports == true )
                                serviceSelect.add(new Option(window.serviceTypes[i].name, window.serviceTypes[i].id));
                            else
                                continue;
                        } else {
                            if(privileges.mod_general_stock_reports == true )
                                serviceSelect.add(new Option(window.serviceTypes[i].name, window.serviceTypes[i].id));
                        }
                    }
                }
            }



        }


function showMonthlySubReport(id) {
            $('#artMonthly').hide();
            $('#monthlyTitle').hide();

            $('#' + id).show();
            $('#monthlyTitle').show();
        }

        function downloadReport(type) {
            if (type == 'visit') {
                var date = $('#visitDate').val();
                if (date != '') {
                    window.location = 'reports/json/toVisit?date=' + getDatabaseFormatDate(date);
                } else {
                    alert("Select the date")
                }
            } else if (type == 'routineRefill') {
                var date = $('#routineRefillDate').val();
                if (date != '') {
                    window.location = 'reports/json/routineRefill?date=' + getDatabaseFormatDate(date);
                } else {
                    alert("Select the date")
                }
            } else if (type == 'soh') {
                window.location = 'reports/json/sohReport/' + window.accountID;
            } else if (type == 'expired') {
                window.location = 'reports/json/expired/' + window.accountID;
            } else if (type == 'shortDated') {
                window.location = 'reports/json/shortDated/' + window.accountID;
            } else if (type == 'received') {
                var date1 = $('#receivedDate1').val();
                var date2 = $('#receivedDate2').val();
                if (date1 == '') {
                    alert("Select the start date.");
                    return;
                } else if (date2 == '') {
                    alert("Select the end date.");
                    return;
                } else if (date1 > date2) {
                    alert("Start date cannot be an earler date than end date.");
                    $('#receivedDate1').val('');
                    $('#receivedDate2').val('');
                    return;
                }
                var supplier = $('#supplierSelect').val();
                window.location = 'reports/json/createIssueReceive/' + window.accountID + '/false/' + supplier + '?date1=' + getDatabaseFormatDate(date1) + "&date2=" + getDatabaseFormatDate(date2);
            } else if (type == 'issued') {
                var date1 = $('#receivedDate1').val();
                var date2 = $('#receivedDate2').val();
                if (date1 == '') {
                    alert("Select the start date.");
                    return;
                } else if (date2 == '') {
                    alert("Select the end date.");
                    return;
                } else if (date1 > date2) {
                    alert("Start date cannot be an earlier date than end date.");
                    $('#receivedDate1').val('');
                    $('#receivedDate2').val('');
                    return;
                }
                window.location = 'reports/json/createIssueReceive/' + window.accountID + '/true/-1?date1=' + getDatabaseFormatDate(date1) + "&date2=" + getDatabaseFormatDate(date2)
            }
            else if (type == 'drugConsumption') {
                var drug = $('#drugConsumptionSelect').val();
                var year = $('#drugConsumptionYearSelect').val();
                var service = $('#drugServiceSelect').val();
                if (year == '-1') {
                    alert("Select Year.");
                    return;
                }
                if (service == '-1') {
                    alert("Select the service");
                    return;
                }
                window.location = 'reports/json/createConsumptionReport/' + year + '/' + drug + "/" + service;
            }


            else if (type == 'patientsByRegimen' || type == 'createCumNumberOfPatientsReport') {
                var service = $('#serviceSelect').val();
                var year = $('#patientServiceYearSelect').val();
                var month = $('#patientServiceMonthSelect').val();
                if (service == '-1') {
                    alert("Select the service");
                    return;
                }
                window.location = 'reports/json/' + type + '/' + year + '/' + month + '/' + service;
            } else if (type == 'fcdrrsite') {
                var month = $('#monthlyMonth').val();
                var year = $('#monthlyYear').val();
                //var id=$('#store').val();
                if (month == '') {
                    alert("Select the month.");
                    return;
                } else if (year == '') {
                    alert("Select the year.");
                    return;
                }
                window.location = 'reports/json/facilityCDRRStandAloneReport?month=' + month + "&year=" + year +"&id=" +id
            } else if (type == 'fcdrrsite-excel') {
                var month = $('#monthlyMonth').val();
                var year = $('#monthlyYear').val();
                var id=$('#store').val();
                if (month == '') {
                    alert("Select the month.");
                    return;
                } else if (year == '') {
                    alert("Select the year.");
                    return;
                }
                window.location = 'reports/excel/facilityCDRRStandAloneReport?month=' + month + "&year=" + year +"&id=" +id
            } else if (type == 'patientListingByStatusReport') {
                var status = $('#patientStatusSelect').val();
                if (status == '-1')
                    alert("Select the patient Status");
                else
                    window.location = 'reports/json/createPatientsByStatusReport/' + status;
            } else if (type == 'missedAppointment') {
                var date1 = $('#missedDate1').val();
                var date2 = $('#missedDate2').val();
                if (date1 == '') {
                    alert("Select the start date.");
                    return;
                } else if (date2 == '') {
                    alert("Select the end date.");
                    return;
                } else if (date1 > date2) {
                    alert("Start date cannot be an earlier date than end date.");
                    $('#missedDate1').val('');
                    $('#missedDate2').val('');
                    return;
                }
                window.location = 'reports/json/missedAppointment/' + $('#missedAppointmentServiceSelect').val() + '?date1=' + getDatabaseFormatDate(date1) + "&date2=" + getDatabaseFormatDate(date2)
            } else if (type == 'changedRegimen') {
                var date1 = $('#missedDate1').val();
                var date2 = $('#missedDate2').val();
                if (date1 == '') {
                    alert("Select the start date.");
                    return;
                } else if (date2 == '') {
                    alert("Select the end date.");
                    return;
                } else if (date1 > date2) {
                    alert("Start date cannot be an earlier date than end date.");
                    $('#missedDate1').val('');
                    $('#missedDate2').val('');
                    return;
                }
                var name = $('#missedAppointmentServiceSelect').find('option:selected').text();
                if(name == 'ART' || name == 'PEP' || name == 'PMTCT' || name == 'OI Only') {
                    window.location = 'reports/json/changedRegimen/' + $('#missedAppointmentServiceSelect').val() + '?date1=' + getDatabaseFormatDate(date1) + "&date2=" + getDatabaseFormatDate(date2)
                } else {
                    alert("Report only for ART services.")
                }
            } else if (type == 'startingByRegimen') {
                var date1 = $('#missedDate1').val();
                var date2 = $('#missedDate2').val();
                if (date1 == '') {
                    alert("Select the start date.");
                    return;
                } else if (date2 == '') {
                    alert("Select the end date.");
                    return;
                } else if (date1 > date2) {
                    alert("Start date cannot be an earlier date than end date.");
                    $('#missedDate1').val('');
                    $('#missedDate2').val('');
                    return;
                }

                var name = $('#missedAppointmentServiceSelect').find('option:selected').text();
                if(name == 'ART' || name == 'PEP' || name == 'PMTCT' || name == 'OI Only') {
                    window.location = 'reports/json/patientsStartingByRegimenReport/' + $('#missedAppointmentServiceSelect').val() + '?date1=' + getDatabaseFormatDate(date1) + "&date2=" + getDatabaseFormatDate(date2)
                } else {
                    alert("Report only for ART services.")
                }

            } else if (type == 'drugUsedReport') {
                var drug = $('#drugUsedSelect').val();
                window.location = 'reports/json/patientListingByDrugUsed/' + drug;
            } else if (type == 'malariacdrrsite') {
                var month = $('#monthlyMonth').val();
                var year = $('#monthlyYear').val();
                if (month == '') {
                    alert("Select the month.");
                    return;
                } else if (year == '') {
                    alert("Select the year.");
                    return;
                }
                window.location = 'reports/json/createMalariaCDRRReport?month=' + month + "&year=" + year
            } else if (type == 'nutritioncdrrsite') {
                var month = $('#monthlyMonth').val();
                var year = $('#monthlyYear').val();
                if (month == '') {
                    alert("Select the month.");
                    return;
                } else if (year == '') {
                    alert("Select the year.");
                    return;
                }
                window.location = 'reports/json/createFacilityNutritionCDRRStandAloneReport?month=' + month + "&year=" + year
            } else if (type == 'labcdrrsite') {
                var month = $('#monthlyMonth').val();
                var year = $('#monthlyYear').val();
                if (month == '') {
                    alert("Select the month.");
                    return;
                } else if (year == '') {
                    alert("Select the year.");
                    return;
                }
                window.location = 'reports/json/createFacilityLabCDRRStandAloneReport?month=' + month + "&year=" + year
            } else if (type == 'emmscdrrsite') {
                var month = $('#monthlyMonth').val();
                var year = $('#monthlyYear').val();
                if (month == '') {
                    alert("Select the month.");
                    return;
                } else if (year == '') {
                    alert("Select the year.");
                    return;
                }
                window.location = 'reports/json/facilityEMMSCDRRStandAloneReport?month=' + month + "&year=" + year
            } else if (type == 'fpcdrrsite') {
                var month = $('#monthlyMonth').val();
                var year = $('#monthlyYear').val();
                if (month == '') {
                    alert("Select the month.");
                    return;
                } else if (year == '') {
                    alert("Select the year.");
                    return;
                }
                window.location = 'reports/json/createFPCDRRReport?month=' + month + "&year=" + year
            } else if (type == 'patientsEnrolled' || type == 'patientsStarted') {
                var date1 = $('#enrollDate1').val();
                var date2 = $('#enrollDate2').val();
                if (date1 == '') {
                    alert("Select the start date.");
                    return;
                } else if (date2 == '') {
                    alert("Select the end date.");
                    return;
                } else if (date1 > date2) {
                    alert("Start date cannot be an earlier date than end date.");
                    $('#enrollDate1').val('');
                    $('#enrollDate1').val('');
                    return;
                }
                if ($('#enrollmentServiceSelect').val() == '-1') {
                    alert("Select service");
                    return;
                }
                window.location = 'reports/json/' + type + '/' + $('#enrollmentServiceSelect').val() + '?date1=' + getDatabaseFormatDate(date1) + "&date2=" + getDatabaseFormatDate(date2)
            } else if (type == 'bmiReport') {
                if ($('#bmiServiceSelect').val() == '-1') {
                    alert("Select service");
                    return;
                } else if ($('#bmiAdult').val() == '-1') {
                    alert("Select if it is for children or adults");
                    return;
                }
                window.location = 'reports/json/bmi/' + $('#bmiServiceSelect').val() + '/' + $('#bmiAdult').val();
            } else if (type == 'patientsGraphEnrolled') {
                var year = $('#enrollmentYearSelect').val();
                if (year == '-1') {
                    alert("Select year.");
                    return;
                }
                window.location = 'reports/json/enrollmentGraph/' + year;
            } else if (type == 'ewi1' || type == 'ewi2' || type == 'ewi3' || type == 'ewi4') {
                var date1 = $('#ewiDate1').val();
                var date2 = $('#ewiDate2').val();
                if (date1 == '') {
                    alert("Select the start date.");
                    return;
                } else if (date2 == '') {
                    alert("Select the end date.");
                    return;
                } else if (date1 > date2) {
                    alert("Start date cannot be an earlier date than end date.");
                    $('#ewiDate1').val('');
                    $('#ewiDate2').val('');
                    return;
                }
                window.location = 'reports/json/' + type + '?date1=' + getDatabaseFormatDate(date1) + "&date2=" + getDatabaseFormatDate(date2)
            } else if (type == 'patientsByRegimenExcel') {
                var year = $('#monthlyYear').val();
                var month = $('#monthlyMonth').val();
                if(year == "") {
                    alert("Select year.");
                    return;
                } else if(month == "") {
                    alert("Select the month.");
                    return;
                }
                window.location = 'reports/json/activePatients/' + year + '/' + month;
            }
        }

function downloadDhisReport(type) {
	if (type == 'fcdrrsite') {
		var month = $('#monthlyMonth').val();
		var year = $('#monthlyYear').val();
        var id=$('#id').val();
		if (month == '') {
			alert("Select the month.");
			return;
		} else if (year == '') {
			alert("Select the year.");
			return;
		}
		window.location = 'reports/json/facilityCDRRStandAloneReportDhis?month=' + month + "&year=" + year
	} else if (type == 'malariacdrrsite') {
		var month = $('#monthlyMonth').val();
		var year = $('#monthlyYear').val();
		if (month == '') {
			alert("Select the month.");
			return;
		} else if (year == '') {
			alert("Select the year.");
			return;
		}
		window.location = 'reports/json/createMalariaCDRRReportDhis?month=' + month + "&year=" + year
	} else if (type == 'nutritioncdrrsite') {
		var month = $('#monthlyMonth').val();
		var year = $('#monthlyYear').val();
		if (month == '') {
			alert("Select the month.");
			return;
		} else if (year == '') {
			alert("Select the year.");
			return;
		}
		window.location = 'reports/json/createFacilityNutritionCDRRStandAloneReportDhis?month=' + month + "&year=" + year
	} else if (type == 'labcdrrsite') {
		var month = $('#monthlyMonth').val();
		var year = $('#monthlyYear').val();
		if (month == '') {
			alert("Select the month.");
			return;
		} else if (year == '') {
			alert("Select the year.");
			return;
		}
		window.location = 'reports/json/createFacilityLabCDRRStandAloneReportDhis?month=' + month + "&year=" + year
	} else if (type == 'emmscdrrsite') {
		var month = $('#monthlyMonth').val();
		var year = $('#monthlyYear').val();
		if (month == '') {
			alert("Select the month.");
			return;
		} else if (year == '') {
			alert("Select the year.");
			return;
		}
		window.location = 'reports/json/facilityEMMSCDRRStandAloneReportDhis?month=' + month + "&year=" + year
	} else if (type == 'fpcdrrsite') {
		var month = $('#monthlyMonth').val();
		var year = $('#monthlyYear').val();
		if (month == '') {
			alert("Select the month.");
			return;
		} else if (year == '') {
			alert("Select the year.");
			return;
		}
		window.location = 'reports/json/createFPCDRRReportDhis?month=' + month + "&year=" + year
	}
}

        /**
         *  This function reloads batches in the given account.
         */
        function reloadDrugs() {
            $.ajax({
                url: 'reference/json/visit/listBatch/' + window.accountID,
                headers: {
                    "Content-Type": "application/json"
                },
                type: 'POST',
                success: function (data) {
                    window.batchList = data.batchList;
                    loadingEnd();
                },
                error: function () {
                    alert("An error has occured when reloading drugs.");
                },
                complete: function () {
                    loadingEnd();
                }
            });
        }

        function calculateBSA() {
            var weight = $('#weight').val();
            var height = $('#height').val();
            if (weight != "" && height != "") {
                if (!isNaN(weight) && !isNaN(height)) {
                    var bsa = 0.007184 * (Math.pow(parseInt(weight), 0.425) * (Math.pow(parseInt(height), 0.725)));
                    bsa = Math.round(bsa * 100) / 100;
                    $('#bodySurfaceArea').val(bsa);
                    return;
                }
            }
            $('#bodySurfaceArea').val('');

        }

        function loading() {
            $("body").addClass("loading");
        }

        function loadingEnd() {
            $("body").removeClass("loading");
        }

        function calculateDaysToNextApp() {
            var days = $('#daysToNextAppointment').val();
            if (isNaN(days)) {
                $('#daysToNextAppointment').val("");
                alert("Please fill in a valid number.");
                this.value = "";
            } else {
                if (days == "")
                    return;
                var date = new Date();
                var nextAppointmentDate = new Date();
                nextAppointmentDate.setDate(date.getDate() + parseInt(days));
                if (nextAppointmentDate.getDay() == 0 || nextAppointmentDate.getDay() == 6) {
                    alert("This will fall on a weekend.")
                    //$('#daysToNextAppointment').val('');
                }
                $('#nextAppointmentDate').val(nextAppointmentDate.getDate() + '/' + (nextAppointmentDate.getMonth() + 1) + '/' + nextAppointmentDate.getFullYear());
                $('#nextAppointmentDate').off("change");
                loadExpectedPatients(nextAppointmentDate.getFullYear() + '-' + pad((nextAppointmentDate.getMonth() + 1)) + '-' + pad(nextAppointmentDate.getDate()));

            }
        }

function calculateDays() {
    var date = $('#nextAppointmentDate').val();
    if(date == '') {
        $('#patientBooked').val('');
        $('#daysToNextAppointment').val('');
        return;
    }
    var appointmentDate = new Date(formatDate(date));
    if (appointmentDate.getDay() == 0 || appointmentDate.getDay() == 6) {
        alert("This will fall on a weekend.");
    }
    var today = new Date();
    var days = appointmentDate - today;
    days = days / 1000 / 60 / 60 / 24;
    $('#daysToNextAppointment').val(Math.ceil(days));
    var d = date.split('/');
    loadExpectedPatients(d[2] + '-' + pad(d[1]) + '-' + pad(d[0]));
}

function pad(val) {
    if(val < 10) {
        return '0' + val;
    }
    return val;
}

        function showBinCard() {
            clearTable('binTransaction');
            clearTable('binCardBatch');
            $('#binCardDrugName').val('');
            $('#binCardUnit').val('');
            $('#binCardTotalStock').val('');
            $("#voidedCommodity").hide();
            var optionList = document.getElementById('drugList');
            for (var i = optionList.length; i > 0; i--) {
                optionList.remove(i);
            }

            var optionList = document.getElementById('binCardAccounts');
            for (var i = optionList.length; i > 0; i--) {
                optionList.remove(i);
            }

            loading();
            $.ajax({
                url: 'reference/json/viewbin/listBinCardReferences/' + window.accountID,
                headers: {
                    "Content-Type": "application/json"
                },
                type: 'POST',
                success: function (data) {
                    window.batchList = data.batchList;
                    window.mainDrugList = data.drugList;
                    window.dispensingUnit = data.dispensingUnitList;
                    window.transactionType = data.transactionTypeList;
                    window.accounts = data.accountList;

                    var drugList = document.getElementById('drugList');
                    for (var i = 0; i < window.mainDrugList.length; i++) {
                        var opt = new Option(window.mainDrugList[i].name, window.mainDrugList[i].id);
                        $(opt).data('index', i);
                        drugList.add(opt);
                    }

                    optionList = document.getElementById('binCardAccounts');
                    for (var i = 0; i < window.accounts.length; i++) {
                        var opt = new Option(window.accounts[i].name, window.accounts[i].id);
                        $(opt).data('index', i);
                        optionList.add(opt);
                    }
                    $('#binCardAccounts').val(window.accountID);
                    $('#drugList').flexselect();
                    $('#drugList').change(function () {
                        var selected = $(this).find(":selected");
                        if ($(selected).attr('value') == "0")
                            return;
                        var index = $(selected).data('index');
                        var drug = window.mainDrugList[index];

                        var acc = $('#binCardAccounts').val();
                        //Let the Bin Cards to be loaded from server every time, this ensures if drugs have been received they will reflect in the Bin Card
                        /*if (window.binCards[drug.id] != undefined) {
                            populateBinCard(drug);
                        } else {*/
                            viewBinCard(drug, acc);
                        //}
                    });

                    $('#binCardAccounts').change(function () {
                        var selected = $('#drugList').find(":selected");
                        if ($(selected).attr('value') == "0")
                            return;
                        var index = $(selected).data('index');
                        var drug = window.mainDrugList[index];

                        var acc = $('#binCardAccounts').val();
                        //Let the Bin Cards to be loaded from server every time, this ensures if drugs have been received they will reflect in the Bin Card
                        /*if (window.binCards[drug.id] != undefined) {
                         populateBinCard(drug);
                         } else {*/
                        viewBinCard(drug, acc);
                        //}
                    });
                    $('#binCards').dialog('open');
                },
                error: function () {
                    alert("An error has occured when reloading drugs.");
                },
                complete: function () {
                    loadingEnd();
                }
            });
        }

        function viewBinCard(drug, account) {

            $.ajax({
                url: 'stocks/json/viewBinCards/' + account + '/' + drug.id,
                headers: {
                    "Content-Type": "application/json"
                },
                type: 'POST',
                success: function (data) {
                    var drugBin = {};
                    drugBin.transactions = data.transactionMap;
                    drugBin.transactionItems = data.transactionItemsListMap;
                    drugBin.batchTransactionItems = data.batchTransactionItemMap;
                    drugBin.destTransactionItems = data.destinationTransactionItems;
                    drugBin.totalStock = data.totalStock;
                    drugBin.batchList = data.batchList;
                    //window.batchList = data.batchList;
                    window.binCards[drug.id] = drugBin;
                    populateBinCard(drug);

                },
                error: function () {
                    alert("An error has occured when reloading drugs.");
                },
                complete: function () {
                    loadingEnd();
                }
            });
        }

function populateBinCard(drug) {
            $('#binCardDrugName').val(drug.name);
    if(drug.voided == 1) {
        $("#voidedCommodity").show();
    } else {
        $("#voidedCommodity").hide();
    }
            var totalStock = 0;
            var drugBin = window.binCards[drug.id];
            var TotalQty = 0;

            clearTable('binTransaction');
            clearTable('binCardBatch');
            $('#binCardTotalStock').val(totalStock);
            $('#binCardUnit').val(getDispensingUnit(drug.dispensingUnitId));

            for (var i = 0; i < drugBin.batchList.length; i++) {
                var batch = drugBin.batchList[i];
                if (batch.drugId == drug.id) {
                    var html = "<tr><td>" + drug.name + "</td><td class='title'>" + batch.packSize + "</td><td class='title'>" + batch.batch_no + "</td><td class='title'>" + batch.totalQty + "</td><td>" + getDate(batch.expiry_date) + "</td></tr>";
                    $('#binCardBatch > tbody:last').append(html);
                }
            }

            for(var i = 0; i < drugBin.totalStock.length; i++) {
                totalStock = totalStock + drugBin.totalStock[i];
            }
            drugBin.transactionItems[drug.id].sort(function(a,b) {
                tx1 = drugBin.transactions[a.transactionId];
                tx2 = drugBin.transactions[b.transactionId];
                return tx2.date - tx1.date;
            });
            for (var i = 0; drugBin.transactionItems != undefined && drugBin.transactionItems[drug.id] != undefined && i < drugBin.transactionItems[drug.id].length; i++) {
                var txItem = drugBin.transactionItems[drug.id][i];
                var tx = drugBin.transactions[txItem.transactionId];
               console.log(drugBin);
                var btx = drugBin.batchTransactionItems[txItem.id];
                var otherTx = drugBin.destTransactionItems[tx.id];
                var qty = "";
                var source = "";
                var destination = "";
                if (txItem.unitsIn != null) {
                    qty = txItem.unitsIn;
                    destination = txItem.accountId;
                    source = otherTx == undefined ? "" : otherTx.accountId;
                } else if (txItem.unitsOut != null) {
                    qty = txItem.unitsOut;
                    source = txItem.accountId;
                    destination = otherTx == undefined ? "" : otherTx.accountId;
                }
                var html = "<tr><td>" + (tx.referenceNo == null ? '' : tx.referenceNo ) + "</td><td>" + getDate(tx.date) + "</td><td>" + getTransactionType(tx.transactionTypeId) + "</td><td>" + (txItem.batchNo == null ? "" : txItem.batchNo) + "</td><td>" + (btx != undefined ? getDate(btx.dateOfExpiry) : "") + "</td><td> " + (btx != undefined ? btx.packSize : "") + "</td><td>" + (btx != undefined ? btx.noOfPacks : "") + "</td><td>" + qty + "</td><!--<td>cost</td><td>Amount</td>--><td>" + getAccount(source) + "</td><td>" + getAccount(destination) + "</td><td>" + (tx.comments == null ? "" : tx.comments) + "</td><td>" + tx.creator + "</td><td>" + tx.id + "</td></tr>";
                $('#binTransaction > tbody:last').append(html);
            }
            $('#binCardTotalStock').val(totalStock);
        }

        function showDailyConsumption() {
            $('#dailyConsumption').dialog('open');

        }

        function showDailyConsumptionDispensing() {
            $('#dailyConsumptionDispensing').dialog('open');

        }

        function loadDailyConsumption() {
            var date1 = $('#date1').val();
            var date2 = $('#date2').val();

            var dat1 = date1.split('/');
            var dat2 = date2.split('/');

            var start = new Date(dat1[2],dat1[1],dat1[0]);
            var end = new Date(dat2[2],dat2[1],dat2[0]);

            if (date1 == '') {
                alert("Select the start date.");
                return;
            }

            if (date2 == '') {
                alert("Select the end date.");
                return;
            }
            if (start > end) {
                alert("Start date cannot be an earlier date than end date.");
                $('#date1').val('');
                $('#date2').val('');
                return;
            }
            clearTable('dailyConsumptionTable');
            $.ajax({
                url: 'stocks/json/dailyConsumption  ?date1=' + getDatabaseFormatDate(date1) + '&date2=' + getDatabaseFormatDate(date2),
                headers: {
                    "Content-Type": "application/json"
                },
                type: 'GET',
                success: function (data) {
                    if (data == undefined || data.length == 0) {
                        alert('No data for the selected dates');
                        return;
                    }
                    for (var i = 0; i < data.length; i++) {
                        var obj = data[i];
                        var html = "<tr><td>" + getDate(obj[2]) + "</td><td>" + obj[0] + "</td><td style='text-align: right'>" + (obj[1] == null? "" : obj[1]) + "</td></tr>";
                        $('#dailyConsumptionTable > tbody:last').append(html);
                    }

                },
                error: function () {
                    alert("An error has occurred loading drug consumption.");
                },
                complete: function () {
                    loadingEnd();
                }
            });
        }


function loadDailyConsumptionDispensing() {

    var date1 = $('#date1').val();
    var date2 = $('#date2').val();
    var AreaService = $('#AreaService').val();
    var PayerType = $('#PayerType').val();
    var totalcost = 0;
    $('#tcost').html(totalcost);
    var dat1 = date1.split('/');
    var dat2 = date2.split('/');
    var start = new Date(dat1[2],dat1[1],dat1[0]);
    var end = new Date(dat2[2],dat2[1],dat2[0]);



    if (AreaService == '') {
        alert("Select the service Area.");
        return;
    }if (PayerType == '') {
        alert("Select the Payer Type.");
        return;
    }
    if (date1 == '') {
        alert("Select the start date.");
        return;
    }

    if (date2 == '') {
        alert("Select the end date.");
        return;
    }

    if (start > end) {
        alert("Start date cannot be an earlier date than end date.");
        $('#date1').val('');
        $('#date2').val('');
        return;
    }
   // clearTable("dailyConsumptionDispensingTable");
    $.ajax({
        url: 'stocks/json/dailyConsumptionDispension  ?date1=' + getDatabaseFormatDate(date1) + '&date2=' + getDatabaseFormatDate(date2) + '&AreaService=' + AreaService +'&PayerType=' + PayerType,
        headers: {
            "Content-Type": "application/json"
        },
        type: 'GET',

        success: function (data) {

            if (data == undefined || data.length == 0) {
                //alert(getDatabaseFormatDate(date1));
                return;
            }


            for (var i = 0; i < data.length; i++) {
               alert(data);
                var obj = data[i];

                var html = "<tr><td>" + (obj[0]) + "</td><td>" + obj[1] + "</td><td>" + obj[2] + "</td></tr>";
                $('#dailyConsumptionDispensingTable > tbody:last').append(html);
                totalcost += obj[2];
            }
            $('#tcost').html(totalcost);

        },
        error: function () {
            alert("An error has occurred loading drug consumption/Dispensing.");
        },
        complete: function () {
            loadingEnd();
        }
    });
}




/**
         *  This functions takes a date string and formats it to be compliant with the MySQL date.
         * @param date
         * @returns {string}
         */
        function getDatabaseFormatDate(date) {
            var dates = date.split('/');
            return dates[2] + '/' + dates[1] + '/' + dates[0];
        }

        function getDate(timeStamp) {
            if (timeStamp == 0 || timeStamp == '0' || timeStamp == null)
                return "";

            var date = new Date(timeStamp);
            var prettyDate = date.getDate() + '/' + (date.getMonth() + 1) + '/' + date.getFullYear();
            return prettyDate;
        }

        function getTransactionType(id) {
            for (var i = 0; window.transactionType != undefined && i < window.transactionType.length; i++) {
                if (window.transactionType[i].id == id) {
                    return window.transactionType[i].name;
                }
            }
            return "";
        }

        function getAccount(id) {
            for (var i = 0; window.accounts != undefined && i < window.accounts.length; i++) {
                if (window.accounts[i].id == id) {
                    return window.accounts[i].name;
                }
            }
            return "";
        }

    function getAccountID(name) {
        for (var i = 0; window.accounts != undefined && i < window.accounts.length; i++) {
            if (window.accounts[i].name == name) {
                return window.accounts[i].id;
            }
        }
        return "";
    }
        function getDispensingUnit(id) {
            for (var i = 0; i < window.dispensingUnit.length; i++) {
                if (window.dispensingUnit[i] != undefined && window.dispensingUnit[i].id == id) {
                    return window.dispensingUnit[i].name;
                }
            }
            return "";
        }

        function isNumber(event) {
            if (event) {
                var charCode = (event.which) ? event.which : event.keyCode;
                if (charCode != 190 && charCode > 31 &&
                    (charCode < 48 || charCode > 57) &&
                    (charCode < 96 || charCode > 105) &&
                    (charCode < 37 || charCode > 40) &&
                    charCode != 110 && charCode != 8 && charCode != 46)
                    return false;
            }
            return true;
        }

        function initReportsReferences() {
            loading();
            $.ajax({
                url: 'reference/json/reports/listReferences',
                headers: {
                    "Content-Type": "application/json"
                },
                type: 'POST',
                success: function (data) {
                    window.mainDrugList = data.drugList;
                    window.serviceTypes = data.serviceTypeList;
                    window.patientStatus = data.patientStatusList;
                    window.supplierAccounts = data.suppliers;
                    loadingEnd();
                },
                error: function (error) {
                    handleError(error);
                },
                complete: function () {
                    loadingEnd();
                }
            });
        }

        function initDrugInRegimen() {
            loading();
            $.ajax({
                url: 'reference/json/regimen/drugInRegimenReference',
                headers: {
                    "Content-Type": "application/json"
                },
                type: 'POST',
                success: function (data) {
                    window.regimen = data.regimenList;
                    window.mainDrugList = data.drugList;
                    window.regimenDrugList = data.regimenDrugList;
                    showDrugInRegimen();
                },
                error: function (error) {
                    handleError(error);
                },
                complete: function () {
                    loadingEnd();
                }
            });
        }

        function showDrugInRegimen() {
            var regimenSelect = document.getElementById('regimenSelect');
            var opt = new Option("", "");
            regimenSelect.add(opt);
            for (var i = 0; i < regimen.length; i++) {
                var opt = new Option(window.regimen[i].name, window.regimen[i].id);
                $(opt).data('obj', window.regimen[i]);
                regimenSelect.add(opt);
            }
            $('#regimenSelect').val("")
            $(".token-input-list-facebook").remove();
            $("#drugsInRegimen").tokenInput(mainDrugList, {
                theme: "facebook", preventDuplicates: true
            });
            $('#regimenSelect').flexselect();
            $(".token-input-dropdown").css("z-index", "10001")

            $('#DrugInRegimen').modal("show")
        }
        function onIdentifierTypeChange(){
            $('#medicalRecordNumber').val('');
            console.log(window.patientIdents);
            var identifierId =  $('#identifierType').val();
            for(var i =0; i<window.patientIdents.length;i++){
                if(window.patientIdents[i].identifierTypeId && window.patientIdents[i].identifierTypeId.toString() == identifierId.toString()){
                    $('#patientIdentifierId').val(window.patientIdents[i].id);
                    $('#medicalRecordNumber').val(window.patientIdents[i].identifier);
                    break;
                }else{
                    $('#patientIdentifierId').val(null);
                    $('#medicalRecordNumber').val('');
                }
            }
        }

        function saveRegimenDrug(dispensing) {
            loading();
            var drugs = $("#drugsInRegimen").val().split(',');
            var drugsInRegimen = [];

            for (var i = 0; i < drugs.length; i++) {
                drugsInRegimen.push(drugs[i]);
            }
            $.ajax({
                url: 'stocks/json/saveDrugRegimen/' + $('#regimenSelect').val(),
                headers: {
                    "Content-Type": "application/json"
                },
                type: 'POST',
                data: JSON.stringify(drugsInRegimen),
                success: function (data) {
                    alert('Saved');
                    if (dispensing && $('#regimenId').val() == $('#regimenSelect').val()) { //Saved regimen equal to the current selected regimen when dsipensing
                        window.drugIds = new Array();
                        for (var i = 0; i < drugs.length; i++) {
                            window.drugIds[parseInt(drugs[i])] = true;
                        }
                    }
                },
                error: function (error) {
                    handleError(error);
                },
                complete: function () {
                    loadingEnd();
                }
            });
        }

        function onRegimenDrugsSelected() {
            var regId = $('#regimenSelect').val();
            var drugs = new Array();
            $("#drugsInRegimen").tokenInput("clear");
            if (regId != '') {
                for (var i = 0; i < window.regimenDrugList.length; i++) {
                    if (window.regimenDrugList[i].regimenId == regId) {
                        drugs[window.regimenDrugList[i].drugId] = true;
                    }
                }
                for (var i = 0; i < window.mainDrugList.length; i++) {
                    if (drugs[window.mainDrugList[i].id]) {
                        $("#drugsInRegimen").tokenInput("add", {id: window.mainDrugList[i].id, name: window.mainDrugList[i].name});
                    }
                }
            }

        }


        function downloadStockTakeExcel() {
            window.location = 'reports/json/createStockTakeExcel/' + window.accountID + '/' + window.user_id;
        }

        setRadioValue = function (name, value) {
            var buttons, i;
            buttons = document.getElementsByName(name);
            i = 0;
            while (i < buttons.length) {
                if (buttons[i].value === value) {
                    buttons[i].checked = true;
                } else {
                    buttons[i].checked = false;
                }
                i++;
            }
        };

        function showFacilityInfo() {
            loading();
            $.ajax({
                url: 'admin/json/loadFacilityInfo',
                headers: {
                    "Content-Type": "application/json"
                },
                type: 'POST',
                success: function (data) {
                    if (data.length > 0) {
                        for (var i = 0; i < data.length; i++) {
                            $('#' + data[i].key).val(data[i].value);
                            $('#' + data[i].key + '_id').val(data[i].id);
                        }
                    }
                    $('#facilityDialog').dialog("open");
                },
                error: function (error) {
                    handleError(error);
                },
                complete: function () {
                    loadingEnd();
                }
            });
        }

        function loadFacilityInformation() {
            loading();
            $.ajax({
                url: 'admin/json/loadFacilityInfo',
                headers: {
                    "Content-Type": "application/json"
                },
                type: 'POST',
                success: function (data) {
                    if (data.length > 0) {
                        for (var i = 0; i < data.length; i++) {
                            if(data[i].key == "Max_Appointments_Per_Day"){
                                sessionStorage.setItem("Max_Appointments",data[i].value);
                            }
                        }
                    }
                },
                error: function (error) {
                    handleError(error);
                },
                complete: function () {
                    loadingEnd();
                }
            });
        }

        function saveFacilityInformation() {
            loading();
            var resp = '{ "properties" : [';
            resp += '{"id" : ' + $('#facility_name_id').val() + ', "key" : "facility_name", "value" : "' + $('#facility_name').val() + '"},' +
                ' { "id" : ' + $('#facility_district_id').val() + ', "key" : "facility_district", "value" : "' + $('#facility_district').val() + '"}, ' +
                ' { "id" : ' + $('#Max_Appointments_Per_Day_id').val() + ', "key" : "Max_Appointments_Per_Day", "value" : "' + $('#Max_Appointments_Per_Day').val() + '"}, ' +
                '{ "id" : ' + $('#facility_code_id').val() + ', "key" : "facility_code", "value" : "' + $('#facility_code').val() + '"}]}';
            $.ajax({
                url: 'admin/json/saveFacilityInfo/' + window.user_id,
                headers: {
                    "Content-Type": "application/json"
                },
                data: resp,
                type: 'POST',
                success: function (data) {
                    alert("Facility saved");
                    closeDialog('facilityDialog');
                },
                error: function (error) {
                    handleError(error);
                },
                complete: function () {
                    loadingEnd();
                }
            });
        }

  /***************************************************************/
function databaseBackup() {
    loading();
    $.ajax({
        url: 'admin/json/databaseInfo',
        headers: {
            "Content-Type": "application/json"
        },
        type: 'POST',
        success: function (data) {
            if (data.length > 0) {
                for (var i = 0; i < data.length; i++) {
                    $('#' + data[i].key).val(data[i].value);
                    $('#' + data[i].key + '_id').val(data[i].id);
                }
            }
            $('#databaseDialog').dialog("open");
        },
        error: function (error) {
            handleError(error);
        },
        complete: function () {
            loadingEnd();
        }
    });
}

function loadDatabaseInformation() {
    loading();
    $.ajax({
        url: 'admin/json/databaseInfo',
        headers: {
            "Content-Type": "application/json"
        },
        type: 'POST',
        success: function (data) {
            if (data.length > 0) {
                for (var i = 0; i < data.length; i++) {
                    if(data[i].key == "Max_Appointments_Per_Day"){
                        sessionStorage.setItem("Max_Appointments",data[i].value);
                    }
                }
            }
        },
        error: function (error) {
            handleError(error);
        },
        complete: function () {
            loadingEnd();
        }
    });
}

function saveDatabaseInformation() {
    loading();


}
  /**************************************************************/


        function loadServices() {
            $.ajax({
                url: 'reference/json/listServiceType',
                headers: {
                    "Content-Type": "application/json"
                },
                type: 'POST',
                success: function (data) {
                    if (data != undefined && data.Records != undefined) {
                        window.serviceTypes = data.Records;
                    }
                    for (var i = 0; i < window.serviceTypes.length; i++) {
                        if (window.serviceTypes[i].name == 'EMMS') {
                            window.service = window.serviceTypes[i].id;
                        }
                    }
                },
                error: function (error) {
                    if (error.errorCode)
                        handleError(error);
                }
            });
        }


function handleError(error) {
    loadingEnd();
    if (error.errorCode == 1011) {
        alert(error.errorMessage);
        window.location = "login.jsp";
    }
}

        function initUserProfileDialog() {
            $('#userProfile').dialog('open');
        }

        function changePassword() {
            var currentPassword = $('#currentPassword').val();
            var pass1 = $('#password1').val();
            var pass2 = $('#password2').val();
            if (currentPassword == '') {
                alert("Enter the current password.");
                $('#currentPassword').focus();
                return;
            } else if (pass1 == '') {
                alert("Enter the new password.");
                $('#password1').focus();
                return;
            } else if (pass2 == '') {
                alert("Enter the confirmation password.");
                $('#password2').focus();
                return;
            } else if (pass1 != pass2) {
                alert("Passwords do not match.");
                $('#password1').val('');
                $('#password1').focus();
                $('#password2').val('');
                return;
            }
            loading();
            var json = {}
            json.id = window.user_id;
            json.password = currentPassword;
            json.newPassword = pass1;
            $.ajax({
                url: 'admin/json/updatePassword',
                headers: {
                    "Content-Type": "application/json"
                },
                type: 'POST',
                data: JSON.stringify(json),
                success: function (data) {
                    if (data == 'updated') {
                        alert("Password updated");
                        $('#userProfile').dialog('close');
                    }
                    $('#changePasswordDiv').hide();
                },
                error: function () {
                    alert(data);
                },
                complete: function () {
                    loadingEnd();
                }
            });
        }

        function formatDate(date) {
            var d = date.split('/');
            return d[1] + '/' + d[0] + '/' + d[2];
        }

        function loadSecretQuestionAnswer() {
            loading();
            $('#changePasswordDiv').hide();
            $.ajax({
                url: 'reference/json/loadSecretQuestionAnswer/' + window.user_id,
                headers: {
                    "Content-Type": "application/json"
                },
                type: 'POST',
                success: function (data) {
                    if (data.Result == 'OK') {
                        var user = data.Record;
                        $('#secretQuestion').val(user.secretQuestion);
                        $('#answer').val(user.secretAnswer);
                        $('#secretQuestionDiv').show()
                    }

                },
                error: function () {
                    alert(data);
                },
                complete: function () {
                    loadingEnd();
                }
            });
        }

        function updateSecretQuestionAnswer() {
            loading();
            var user = {};
            user.secretQuestion = $('#secretQuestion').val();
            user.secretAnswer = $('#answer').val();
            user.id = window.user_id;
            $.ajax({
                url: 'reference/json/saveSecretQuestionAnswer',
                headers: {
                    "Content-Type": "application/json"
                },
                type: 'POST',
                data: JSON.stringify(user),
                success: function (data) {
                    if (data.Result == 'OK') {
                        alert('Secret question and answer updated.');
                    }
                },
                error: function () {
                    alert(data);
                },
                complete: function () {
                    loadingEnd();
                }
            });

        }

function loadEWI() {
            $.ajax({
                url: 'reports/json/ewi',
                headers: {
                    "Content-Type": "application/json"
                },
                type: 'POST',
                success: function (data) {
                    if (data != undefined) {
                        var ewi1 = data[0];
                        $("#ewi1").append("<p>" + ewi1 + "%</p>");
                        if (ewi1 < 80) {
                            $('.ewi1').css('background', '#ff0000');
                        } else if (ewi1 >= 80 && ewi1 < 90) {
                            $('.ewi1').css('background', '#FFBF00');
                        } else {
                            $('.ewi1').css('background', '#00ff00');
                        }
                        var ewi2 = data[1];
                        $("#ewi2").append("<p>" + ewi2 + "%</p>");
                        if (ewi2 < 75) {
                            $('.ewi2').css('background', '#ff0000');
                        } else if (ewi2 >= 75 && ewi2 < 85) {
                            $('.ewi2').css('background', '#FFBF00');
                        } else {
                            $('.ewi2').css('background', '#00ff00');
                        }
                        var ewi3 = data[2];
                        $("#ewi3").append("<p>" + ewi3 + "%</p>");
                        if (ewi3 < 100) {
                            $('.ewi3').css('background', '#ff0000');
                        } else {
                            $('.ewi3').css('background', '#00ff00');
                        }
                        var ewi4 = data[3];
                        $("#ewi4").append("<p>" + ewi4 + "%</p>");
                        if (ewi4 > 0) {
                            $('.ewi4').css('background', '#ff0000');
                        } else {
                            $('.ewi4').css('background', '#00ff00');
                        }
                    }
                },
                error: function () {
                    alert(data);
                },
                complete: function () {
                    loadingEnd();
                }
            });

}

function onSatelliteServiceSelect() {
    var service = $('#satelliteServiceType').val();
    window.service = new Array();
    window.service[0] = service;
    setDispensingDrugs(window.service)
}

function showSatelliteDataDialog() {
    loading();
    $.ajax({
        url: 'reference/json/listSatelliteDataReferences',
        headers: {
            "Content-Type": "application/json"
        },
        type: 'POST',
        success: function (data) {
            window.serviceTypes = data.serviceTypeList;
            window.mainDrugList = data.drugList;
            window.facilities = data.facilityList;
            var optionList = document.getElementById('satelliteServiceType');
            for(var i = optionList.length; i > 0; i--) {
                optionList.remove(i);
            }
            for(var i = 0; window.serviceTypes != null && i <  window.serviceTypes.length; i++) {
                optionList.add(new Option(window.serviceTypes[i].name, window.serviceTypes[i].id), null);
            }

            optionList = document.getElementById('facility');
            for(var i = optionList.length; i > 0; i--) {
                optionList.remove(i);
            }
            for(var i = 0; window.facilities != null && i <  window.facilities.length; i++) {
                optionList.add(new Option(window.facilities[i].name, window.facilities[i].id), null);
            }

            var yearListSelect = document.getElementById('satelliteYear');
            var date = new Date();
            var year = date.getFullYear();
            for (var i = yearListSelect.length; i > 0; i--) {
                yearListSelect.remove(i);
            }
            for (var i = 2009; i <= year; i++) {
                yearListSelect.add(new Option(i, i));
            }
            $('#facility').flexselect();
            $('#satelliteDataDialog').dialog('open');
            loadingEnd();
        },
        error: function (error) {
            if (error.errorCode)
                handleError(error);
        }
    });
}

function setSourceDest(txType) {
    clearTable("transactionTable");
    $('#destination').prop("disabled", false);
    $('#source').prop("disabled", false);
    window.txSelectBatch = false;
    $('.qty-avail').show();
    if(txType.name == "Received from" || txType.name == "Returns from (+)" || txType.name == "Opening Balance" || txType.name == "Ajustment (+)") {
        $('#destination').val(window.accountID);
        $('#destination').prop("disabled", "disabled");
    } else if( txType.name == "Dispensed to Patients" || txType.name == "Issued To" || txType.name == "Ajustment (-)" || txType.name == "Returns to (-)" || txType.name == "Losses(-)" || txType.name == "Expired(-)") {
        $('#source').val(window.accountID);
        $('#source').prop("disabled", "disabled");
    }
    if( txType.name == "Ajustment (-)") {
        var accId = getAccountID("STOCK TAKE");
        $('#destination').val(accId);
        $('#destination').prop("disabled", "disabled");
    }
    if(txType.name == "Ajustment (+)" ) {
        var accId = getAccountID("STOCK TAKE");
        $('#source').val(accId);
        $('#source').prop("disabled", "disabled");
    }
    if(txType.name == "Dispensed to Patients") {
        var accId = getAccountID("PATIENTS");
        $('#destination').val(accId);
        $('#destination').prop("disabled", "disabled");
    }
    if(txType.name == "Returns from (+)" || txType.name == "Ajustment (+)" || txType.name == "Ajustment (-)" || txType.name == "Dispensed to Patients" || txType.name == "Issued To" || txType.name == "Returns to (-)" || txType.name == "Expired(-)" )  {
        window.txSelectBatch = true;
        $('.qty-avail').show();
    } else {
        $('.qty-avail').hide();
    }
    addTransactionRow();
}

function saveSatelliteData() {

}

function destroyPlugin($elem, eventNamespace) {
    var isInstantiated  = !! $.data($elem.get(0));

    if (isInstantiated) {
        $.removeData($elem.get(0));
        $elem.off(eventNamespace);
        $elem.unbind('.' + eventNamespace);
    }
};

function initReportsPage() {
    $('#reportDialog').on('hidden.bs.modal', function () {
        $('#visitingPatients').hide();
        $('#stockReports').hide();
        $('#receiveIssueReports').hide();
        $('#drugConsumption').hide();
        $('#patientServices').hide();
        $('#monthlyReport').hide();
        $('#patientListingByStatusReport').hide();
        $('#listingRegimenMissedAppointments').hide();
        $('#drugUsedReport').hide();
        $('#enrollmentReport').hide();
        $('#bmiReport').hide();
        $('#ewiReports').hide();
        $('#facilitpadherence').hide();
    })
}

function getRegimenName(regimenId) {
    for(var i =0; window.regimen != undefined && i < window.regimen.length; i++) {
        if(window.regimen[i].id == regimenId)
            return window.regimen[i].name
    }
    return "";
}

function reloadDrugForm() {
    loading();
    $.ajax({
        url: 'reference/json/listDrugForm',
        headers: {
            "Content-Type": "application/json"
        },
        type: 'POST',
        success: function (data) {
            window.drugForm = data.Records
            var optionList = document.getElementById("drugFormId");
            if(optionList != null) {
                for (var i = optionList.length; i > 0; i--) {
                    optionList.remove(i);
                }

                for (var i = 0; window.drugForm != null && i < window.drugForm.length; i++) {
                    optionList.add(new Option(window.drugForm[i].name, window.drugForm[i].id), null);
                }
            }
        },
        error: function () {

        },
        complete: function () {
            loadingEnd();
        }
    });
}

function reloadDrugType() {
    loading();
    $.ajax({
        url: 'reference/json/listDrugType',
        headers: {
            "Content-Type": "application/json"
        },
        type: 'POST',
        success: function (data) {
            window.drugType = data.Records
            var optionList = document.getElementById("drugTypeId");
            if(optionList != null) {
                for (var i = optionList.length; i > 0; i--) {
                    optionList.remove(i);
                }

                for (var i = 0; window.drugType != null && i < window.drugType.length; i++) {
                    optionList.add(new Option(window.drugType[i].name, window.drugType[i].id), null);
                }
            }
        },
        error: function () {

        },
        complete: function () {
            loadingEnd();
        }
    });
}

function reloadDrugCategory() {
    loading();
    $.ajax({
        url: 'reference/json/listDrugCategory',
        headers: {
            "Content-Type": "application/json"
        },
        type: 'POST',
        success: function (data) {
            window.drugCategory = data.Records
            var optionList = document.getElementById("drugCategoryId");
            if(optionList != null) {
                for (var i = optionList.length; i > 0; i--) {
                    optionList.remove(i);
                }

                for (var i = 0; window.drugCategory != null && i < window.drugCategory.length; i++) {
                    optionList.add(new Option(window.drugCategory[i].name, window.drugCategory[i].id), null);
                }
            }
        },
        error: function () {

        },
        complete: function () {
            loadingEnd();
        }
    });
}

function reloadGenericName() {
    loading();
    $.ajax({
        url: 'reference/json/listGenericName',
        headers: {
            "Content-Type": "application/json"
        },
        type: 'POST',
        success: function (data) {
            window.genericName = data.Records
            var optionList = document.getElementById("genericNameId");
            if(optionList != null) {
                for (var i = optionList.length; i > 0; i--) {
                    optionList.remove(i);
                }

                for (var i = 0; window.genericName != null && i < window.genericName.length; i++) {
                    optionList.add(new Option(window.genericName[i].name, window.genericName[i].id), null);
                }
            }
        },
        error: function () {

        },
        complete: function () {
            loadingEnd();
        }
    });
}


function reloadCDRRCategory() {
    loading();
    $.ajax({
        url: 'reference/json/cdrrCatgoryList',
        headers: {
            "Content-Type": "application/json"
        },
        type: 'POST',
        success: function (data) {
            window.cdrrCategory = data.Records
            var optionList = document.getElementById("cdrrCategoryId");
            if(optionList != null) {
                for (var i = optionList.length; i > 0; i--) {
                    optionList.remove(i);
                }

                for (var i = 0; window.cdrrCategory != null && i < window.cdrrCategory.length; i++) {
                    optionList.add(new Option(window.cdrrCategory[i].name, window.cdrrCategory[i].id), null);
                }
            }
        },
        error: function () {

        },
        complete: function () {
            loadingEnd();
        }
    });
}

function reloadDispensingUnit() {
    loading();
    $.ajax({
        url: 'reference/json/listDispensingUnit',
        headers: {
            "Content-Type": "application/json"
        },
        type: 'POST',
        success: function (data) {
            window.dispensingUnit = data.Records
            var optionList = document.getElementById("dispensingUnitId");
            if(optionList != null) {
                for (var i = optionList.length; i > 0; i--) {
                    optionList.remove(i);
                }

                for (var i = 0; window.dispensingUnit != null && i < window.dispensingUnit.length; i++) {
                    optionList.add(new Option(window.dispensingUnit[i].name, window.dispensingUnit[i].id), null);
                }
            }
        },
        error: function () {

        },
        complete: function () {
            loadingEnd();
        }
    });
}

function reloadDosage() {
    loading();
    $.ajax({
        url: 'reference/json/listDosage',
        headers: {
            "Content-Type": "application/json"
        },
        type: 'POST',
        success: function (data) {
            window.dosage = data.Records
            var optionList = document.getElementById("dosageId");
            if(optionList != null) {
                for (var i = optionList.length; i > 0; i--) {
                    optionList.remove(i);
                }

                for (var i = 0; window.dosage != null && i < window.dosage.length; i++) {
                    optionList.add(new Option(window.dosage[i].name, window.dosage[i].id), null);
                }
            }
        },
        error: function () {

        },
        complete: function () {
            loadingEnd();
        }
    });
}

function reloadServiceTypes() {
    loading();
    $.ajax({
        url: 'reference/json/listServiceType',
        headers: {
            "Content-Type": "application/json"
        },
        type: 'POST',
        success: function (data) {
            window.serviceTypes = data.Records
            var optionList = document.getElementById("drugServiceTypeId");
            if(optionList != null) {
                for (var i = optionList.length; i > 0; i--) {
                    optionList.remove(i);
                }

                for (var i = 0; window.serviceTypes != null && i < window.serviceTypes.length; i++) {
                    optionList.add(new Option(window.serviceTypes[i].name, window.serviceTypes[i].id), null);
                }
            }
        },
        error: function () {

        },
        complete: function () {
            loadingEnd();
        }
    });
}

function loadVisits(patientId) {
    loading();
    if(!patientId) {
        patientId = $('#visitPatientId').val();
    }
    $.ajax({
        url: 'person/json/loadVisit/' + patientId + '/' + window.accountID,
        headers: {
            "Content-Type": "application/json"
        },
        type: 'POST',
        success: function (data) {
            showPatientVisit(data.objectList);
        },
        error: function () {

        },
        complete: function () {
            loadingEnd();
        }
    });
}
function loadVisitAA(patientId) {
    loading();
    if(!patientId) {
        patientId = $('#visitPatientId').val();
    }
    $.ajax({
        url: 'person/json/loadVisit/' + patientId + '/' + window.accountID,
        headers: {
            "Content-Type": "application/json"
        },
        type: 'POST',
        success: function (data) {
            showPatientVisitAA(data.objectList);
        },
        error: function () {

        },
        complete: function () {
            loadingEnd();
        }
    });
}

function loadPrescription(patientId){
  loading();
    if(!patientId) {
        patientId = $('#visitPatientId').val();
    }
    $.ajax({
        url: 'person/json/loadPatientPrescription/' + patientId ,
        headers: {
            "Content-Type": "application/json"
        },
        type: 'GET',
        success: function (data) {
            showPatientPrescription(data.prescriptionList);
        },
        error: function () {

        },
        complete: function () {
            loadingEnd();
        }
    });
}
function loadLastVisit(patientId) {
    loading();
    if(!patientId) {
        patientId = $('#visitPatientId').val();
    }
    $.ajax({
        url: 'person/json/loadLastVisit/' + patientId,
        headers: {
            "Content-Type": "application/json"
        },
        type: 'POST',
        success: function (data) {
            if (!data.nextAppointmentDate || data.nextAppointmentDate == null || data.nextAppointmentDate == ""){
                return;
            }
            var date = new Date(data.nextAppointmentDate);
            var currentDate = new Date;
            ////Get 1 day in milliseconds
            var one_day=1000*60*60*24;
            //// Convert both dates to milliseconds
            //var date1_ms = date.getTime();
            //var date2_ms = currentDate.getTime();
            //// Calculate the difference in milliseconds
            var difference_ms = date - currentDate;
            var diff = Math.ceil(difference_ms/one_day);
            var lateBY = diff*-1;
            var LateOrEarly = diff > 0 ? "Early by "+ diff + " days": "Late by "+ lateBY + " days";
            if(diff >= 0){
                $("#lastVisitDate").html(+ date.getDate() + '/' + (date.getMonth() + 1) + '/' + date.getFullYear()+ "  "+ LateOrEarly);
            }else{
                $("#lastVisitDate2").html("Expected Date: "+ date.getDate() + '/' + (date.getMonth() + 1) + '/' + date.getFullYear()+ "  "+ LateOrEarly);
            }
        },
        error: function () {
        },
        complete: function () {
            loadingEnd();
        }
    });
}

function loadExpectedPatients(date) {
    var servicearea= null;
    if(window.clinic == 1){
         servicearea = "CCC Pharmacy";
    }else if(window.clinic == 2){
        servicearea = "Family Planning Clinic";
    }else if(window.clinic == 6){
        servicearea = "Diabetic Clinic"
    }
    $.ajax({
        url: 'person/json/loadExpectedPatients/' + date +'/'+servicearea,
        headers: {
            "Content-Type": "application/json"
        },
        type: 'POST',
        success: function (data) {

            $('#patientBooked').val(data.Record);
           //var patientsBooked = $('#patientBooked').val(data.Record);
            var maxAppointments = sessionStorage.getItem("Max_Appointments");
            if(data.Record>= maxAppointments){
                $('#daysToNextAppointment').val('');
                $('#nextAppointmentDate').val('');
                alert("Do not exceed maximum number of patient booking ! "+maxAppointments);

            }
        },
        error: function () {

        }
    });
}
function loadSeenPatients() {
    var servicearea = null;

    if(window.clinic == 1){
        servicearea = "CCC Pharmacy";
    }else if(window.clinic == 2){
        servicearea = "Family Planning Clinic";
    }else if(window.clinic == 6){
        servicearea = "Diabetic Clinic"
    }
    $.ajax({
        url: 'person/json/loadSeenPatients/'+servicearea,
        headers: {
            "Content-Type": "application/json"
        },
        type: 'POST',
        success: function (data) {
            $('#seenPatients').html(data.Record.toString());
        },
        error: function () {

        }
    });
}


function showPatientVisit(visitData) {
    clearTable('dispenseHistoryTable');
    for(var i = 0; i < visitData.length; i++) {
        var html = "<tr><td>" + (i + 1) + "</td>";
        for(var j = 4; j < visitData[i].length; j++) {
            if(j == 16) {
                html += "<td></td>"
            } else {
                if(visitData[i][j] == "null") {
                    html += "<td></td>";
                } else
                    html += "<td>" + (visitData[i][j] == null ? "" : visitData[i][j]) + "</td>";
            }
        }
        html +="<td><button type='button' class='btn btn-default' onclick='voidDispense("+visitData[i][0]+","+visitData[i][1]+","+visitData[i][2]+","+visitData[i][3]+")'>Void</button></td>";
        html += "</tr>";
        $('#dispenseHistoryTable > tbody:last').append(html);
    }
    $('#patientHistoryDialog').modal("show");
}
function showPatientVisitAA(visitData) {
    clearTable('dispenseHistoryTableAA');
    for(var i = 0; i < visitData.length; i++) {
        var html = "<tr><td>"  + "</td>";
        for(var j = 4; j < visitData[i].length; j++) {
            if(visitData[i][4] == visitData[0][4]){

                if(j == 16) {
                    html += "<td></td>"
                } else {
                    if(visitData[i][j] == "null") {
                        html += "<td></td>";
                    } else
                        html += "<td>" + (visitData[i][j] == null ? "" : visitData[i][j]) + "</td>";
                }
            }


        }

        $('#dispenseHistoryTableAA > tbody:last').append(html);

    }
    //$('#patientHistoryDialog').modal("show");
}
function showPatientPrescription(data){
    if(data.length == 0 ){
       alert("No Prescription data available for this Patient");
    }else{
        clearTable("prescriptionHistoryTable");
        var html = "";
        var counter = 1;
        for(var i= 0; i<data.length; i++){
           html += "<tr><td>" + counter +  "</td>";
            for(var j = 1; j < data[i].length; j++){
                if(j == 1){
                    data[i][j] = new Date(data[i][j]);
                }
                html += "<td>"+data[i][j]+"</td>"
            }
            html += "</tr>";
            counter ++;
        }
        $('#prescriptionHistoryTable > tbody:last').append(html);

        $('#patientPresciptionDialog').modal("show");
    }
}
function voidDispense(visitId,patientTransactionItemId,transactionItemId, transactionID){
  var voidDis =  confirm("Are you sure you want to void this Dispense?");
if(!visitId || !patientTransactionItemId ||!transactionID || !transactionItemId || !voidDis){
    return;
}
    $.ajax({
        url: 'person/json/voidDispense/' + visitId + '/' + patientTransactionItemId+ '/' + transactionItemId + '/' + transactionID+ '/' + window.accountID +'/'+$('#visitPatientId').val(),
        headers: {
            "Content-Type": "application/json"
        },
        type: 'POST',
        success: function (data) {
            alert("Dispense Voided");
            loadVisits($('#visitPatientId').val())
        },
        error: function () {

        },
        complete: function () {
            loadingEnd();
        }
    });

}
function onDrugServiceSelect() {
    var id = $('#drugServiceSelect').val();

    var commoditySearch = document.getElementById('drugConsumptionSelect');
    for (var i = commoditySearch.length; i > 0; i--) {
        commoditySearch.remove(i);
    }
    for (var i = 0; i < window.mainDrugList.length; i++) {
        if(window.mainDrugList[i].serviceTypeId == id) {
            var opt = new Option(window.mainDrugList[i].name, window.mainDrugList[i].id);
            $(opt).data('index', i);
            commoditySearch.add(opt);
        }
    }
    //$('#commoditySearch').flexselect();
}

$(document).ready(function() {
    initNumberevent();
});

function initNumberevent() {
    $('.number').keypress(function(event) {
        var code = (event.keyCode ? event.keyCode : event.which);
        if (!(
            (code >= 48 && code <= 57) //numbers
            || (code == 46) //period
            || (code == 8) //Back space
            || (code == 37 || code == 39) // Left & Right arrow
            )
            || (code == 46 && $(this).val().indexOf('.') != -1)
        )
            event.preventDefault();
    });
}

function showUploadMetadataDialog() {
	$("#metadata-upload").dialog();
}

function closePrintCommodity()
{
    $("#receiveIssueDialog").modal("hide");
    $('#visitDialog').modal('show');

}

function closePCommodity()
{
    $("#receiveIssueDialog").modal("hide");

}

function PrintCommodity()
{
    $('#print-commodity').modal("hide");

    $("#printablediv").printThis({
        debug: false,
        importCSS: true,
        importStyle: true,
        printContainer: true,
        loadCSS: "../css/style.css",
        pageTitle: "Daily Commodity / Dispensing Transaction",
        removeInline: false,
        printDelay: 333,
        header: null,
        formValues: true
    });

    $("#receiveIssueDialog").modal("hide");
    $('#visitDialog').modal('show');

}
function PrintDailyConsumDisp(){
    $("#dailyConsumptionDispensing").printThis({
        debug: false,
        importCSS: true,
        importStyle: true,
        printContainer: true,
        loadCSS: "../css/style.css",
        pageTitle: "Daily Commodity / Dispensing Transaction",
        removeInline: false,
        printDelay: 333,
        header: null,
        formValues: true
    });

}


function PrintDailyConsumptionDispension()
{

    $("#printablediv").printThis({
        debug: false,
        importCSS: true,
        importStyle: true,
        printContainer: true,
        loadCSS: "../css/style.css",
        pageTitle: "Daily Commodity / Dispensing Transaction",
        removeInline: false,
        printDelay: 333,
        header: null,
        formValues: true
    });

}


