/**
 * Created by kenny on 4/10/14.
 */

$(function() {
    $("#accountTypeDialog" ).dialog({
        autoOpen: false,
        width: 600,
        height: "auto",
        minHeight: 300,
        minWidth: 600,
        maxHeight: 300,
        title: 'Account types',
        position: { my: "top", at: "top", of: window },
        modal: true,
        close: function() {
            location.reload();
        }
    });

    $("#regimenTypeDialog" ).dialog({
        autoOpen: false,
        width: 600,
        height: "auto",
        minHeight: 300,
        minWidth: 600,
        maxHeight: 300,
        title: 'Regimen types',
        position: { my: "top", at: "top", of: window },
        modal: true,
        close: function() {
        }
    });

    $("#regimenStatusDialog" ).dialog({
        autoOpen: false,
        width: 600,
        height: "auto",
        minHeight: 300,
        minWidth: 600,
        maxHeight: 300,
        title: 'Regimen types',
        position: { my: "top", at: "top", of: window },
        modal: true,
        close: function() {
        }
    });

    $("#dosageDialog" ).dialog({
        autoOpen: false,
        width: 600,
        height: "auto",
        minHeight: 300,
        minWidth: 600,
        maxHeight: 300,
        title: 'Dosages',
        position: { my: "top", at: "top", of: window },
        modal: true,
        close: function() {
        }
    });

    $("#dispensingUnitDialog" ).dialog({
        autoOpen: false,
        width: 700,
        height: "auto",
        minHeight: 300,
        minWidth: 600,
        maxHeight: 300,
        title: 'Dispensing units',
        position: { my: "top", at: "top", of: window },
        modal: true,
        close: function() {
        }
    });

    $("#genericNameDialog" ).dialog({
        autoOpen: false,
        width: 500,
        height: "auto",
        minHeight: 300,
        minWidth: 600,
        maxHeight: 300,
        title: 'Generic name',
        position: { my: "top", at: "top", of: window },
        modal: true,
        close: function() {
        }
    });

    $("#districtDialog" ).dialog({
        autoOpen: false,
        width: 500,
        height: "auto",
        minHeight: 300,
        minWidth: 600,
        maxHeight: 300,
        title: 'Counties',
        position: { my: "top", at: "top", of: window },
        modal: true,
        close: function() {
        }
    });

    $("#identifierTypeDialog" ).dialog({
        autoOpen: false,
        width: 500,
        height: "auto",
        minHeight: 300,
        minWidth: 600,
        maxHeight: 300,
        title: 'Identifier Type',
        position: { my: "top", at: "top", of: window },
        modal: true,
        close: function() {
        }
    });

    $("#locationDialog" ).dialog({
        autoOpen: false,
        width: 700,
        height: "auto",
        minHeight: 300,
        minWidth: 600,
        maxHeight: 300,
        title: 'Locations',
        position: { my: "top", at: "top", of: window },
        modal: true,
        close: function() {
        }
    });

    $("#patientSourceDialog" ).dialog({
        autoOpen: false,
        width: 700,
        height: "auto",
        minHeight: 300,
        minWidth: 600,
        maxHeight: 300,
        title: 'Patient Source',
        position: { my: "top", at: "top", of: window },
        modal: true,
        close: function() {
        }
    });

    $("#serviceTypeDialog" ).dialog({
        autoOpen: false,
        width: 700,
        height: "auto",
        minHeight: 300,
        minWidth: 600,
        maxHeight: 300,
        title: 'Service type',
        position: { my: "top", at: "top", of: window },
        modal: true,
        close: function() {
        }
    });

    $("#supportingOrganizationDialog" ).dialog({
        autoOpen: false,
        width: 700,
        height: "auto",
        minHeight: 300,
        minWidth: 600,
        maxHeight: 300,
        title: 'Supporting organizations',
        position: { my: "top", at: "top", of: window },
        modal: true,
        close: function() {
        }
    });

    $("#transactionTypeDialog" ).dialog({
        autoOpen: false,
        width: 700,
        height: "auto",
        minHeight: 300,
        minWidth: 600,
        maxHeight: 300,
        title: 'Transaction Types',
        position: { my: "top", at: "top", of: window },
        modal: true,
        close: function() {
        }
    });

    $("#privilegeDialog" ).dialog({
        autoOpen: false,
        width: 700,
        height: "auto",
        minHeight: 300,
        minWidth: 600,
        maxHeight: 300,
        title: 'Privileges',
        position: { my: "top", at: "top", of: window },
        modal: true,
        close: function() {
        }
    });

    $("#regionDialog" ).dialog({
        autoOpen: false,
        width: 700,
        height: "auto",
        minHeight: 300,
        minWidth: 600,
        maxHeight: 300,
        title: 'Regions',
        position: { my: "top", at: "top", of: window },
        modal: true,
        close: function() {
        }
    });

    $("#patientStatusDialog" ).dialog({
        autoOpen: false,
        width: 700,
        height: "auto",
        minHeight: 300,
        minWidth: 600,
        maxHeight: 300,
        title: 'Patient Status',
        position: { my: "top", at: "top", of: window },
        modal: true,
        close: function() {
        }
    });

    $("#indicationDialog" ).dialog({
        autoOpen: false,
        width: 700,
        height: "auto",
        minHeight: 300,
        minWidth: 600,
        maxHeight: 300,
        title: 'Indication',
        position: { my: "top", at: "top", of: window },
        modal: true,
        close: function() {
        }
    });

    $("#regimenChangeReasonDialog" ).dialog({
        autoOpen: false,
        width: 700,
        height: "auto",
        minHeight: 300,
        minWidth: 600,
        maxHeight: 300,
        title: 'Regimen Change Reason',
        position: { my: "top", at: "top", of: window },
        modal: true,
        close: function() {
        }
    });

    $("#visitTypeDialog" ).dialog({
        autoOpen: false,
        width: 700,
        height: "auto",
        minHeight: 300,
        minWidth: 600,
        maxHeight: 300,
        position: { my: "top", at: "top", of: window },
        title: 'Visit type',
        modal: true,
        close: function() {
        }
    });

    $("#drugCategoryDialog" ).dialog({
        autoOpen: false,
        width: 900,
        height: "auto",
        minHeight: 300,
        minWidth: 725,
        title: 'Drug Category',
        position: { my: "top", at: "top", of: window },
        modal: true,
        close: function() {
        }
    });

    $("#drugFormDialog" ).dialog({
        autoOpen: false,
        width: 900,
        height: "auto",
        minHeight: 300,
        minWidth: 725,
        title: 'Drug Form',
        position: { my: "top", at: "top", of: window },
        modal: true,
        close: function() {
        }
    });

    $("#drugTypeDialog" ).dialog({
        autoOpen: false,
        width: 900,
        height: "auto",
        minHeight: 300,
        minWidth: 725,
        title: 'Drug Type',
        position: { my: "top", at: "top", of: window },
        modal: true,
        close: function() {
        }
    });

    /*$("#newPatientDialog" ).dialog({
        autoOpen: false,
        width: 900,
        height: "auto",
        minHeight: 300,
        minWidth: 725,
        position: { my: "top", at: "top", of: window },
        title: 'New patient',
        modal: true,
        close: function() {
        }
    });

    /*$("#visitDialog" ).dialog({
        autoOpen: false,
        width: 1200,
        height: "auto",
        minHeight: 400,
        minWidth: 1200,
        title: 'Patient visit',
        modal: true,
        close: function() {
            //confirmClose('visitDialog');
        }
    });*/

    $("#regimenDialog" ).dialog({
        autoOpen: false,
        width: 900,
        height: "auto",
        minHeight: 300,
        minWidth: 725,
        title: 'Regimen',
        position: { my: "top", at: "top", of: window },
        modal: true,
        close: function() {
        }
    });

    $("#drugDialog" ).dialog({
        autoOpen: false,
        width: 900,
        height: "auto",
        minHeight: 300,
        minWidth: 725,
        position: { my: "top", at: "top", of: window },
        title: 'Commodity',
        modal: true,
        close: function() {
        }
    });

    $("#accountDialog" ).dialog({
        autoOpen: false,
        width: 900,
        height: "auto",
        minHeight: 300,
        minWidth: 725,
        position: { my: "top", at: "top", of: window },
        title: 'Accounts',
        modal: true,
        close: function() {
        }
    });

    $("#cdrrCategoryDialog" ).dialog({
        autoOpen: false,
        width: 900,
        height: "auto",
        minHeight: 300,
        minWidth: 725,
        position: { my: "top", at: "top", of: window },
        title: 'CDRR Category',
        modal: true,
        close: function() {
        }
    });

    /*$("#receiveIssueDialog" ).dialog({
        autoOpen: false,
        width: 1200,
        height: "auto",
        minHeight: 500,
        minWidth: 725,
        title: 'Receive/Issue drugs',
        position: { my: "top", at: "top", of: window },
        modal: true,
        close: function() {
        }
    });*/

    $("#listRegimen" ).dialog({
        autoOpen: false,
        width: 900,
        height: "auto",
        minHeight: 300,
        minWidth: 725,
        maxHeight: 500,
        title: 'Regimens',
        position: { my: "top", at: "top", of: window },
        modal: true,
        close: function() {
        }
    });

    $("#listDrugs" ).dialog({
        autoOpen: false,
        width: 900,
        height: "auto",
        minHeight: 300,
        minWidth: 725,
        maxHeight: 500,
        title: 'Commodities',
        position: { my: "top", at: "top", of: window },
        modal: true,
        close: function() {
            $("#visitDialog").modal("show");
        }
    });

    $("#listUsers" ).dialog({
        autoOpen: false,
        width: 800,
        height: "auto",
        minHeight: 400,
        minWidth: 1200,
        title: 'Users',
        modal: true,
        beforeClose : function() {
            return confirm("Are you sure you want to close?");
        }
    });

    $("#usersDialog" ).dialog({
        autoOpen: false,
        width: 800,
        height: "auto",
        minHeight: 400,
        minWidth: 1200,
        title: 'Users',
        modal: true,
        beforeClose : function() {
            //return confirm("Are you sure you want to close?");
        }
    });

    $("#privilegeDialog" ).dialog({
        autoOpen: false,
        width: 900,
        height: "auto",
        minHeight: 300,
        minWidth: 725,
        title: 'Privileges',
        position: { my: "top", at: "top", of: window },
        modal: true,
        close: function() {
        }
    });

    $("#listRoles" ).dialog({
        autoOpen: false,
        width: 900,
        height: "auto",
        minHeight: 300,
        minWidth: 725,
        title: 'Roles',
        position: { my: "top", at: "top", of: window },
        modal: true,
        close: function() {
        }
    });

    $("#rolesDialog" ).dialog({
        autoOpen: false,
        width: 900,
        height: "auto",
        minHeight: 300,
        minWidth: 725,
        title: 'Roles',
        position: { my: "top", at: "top", of: window },
        modal: true,
        close: function() {
        }
    });

    $("#insulinTypeDialog" ).dialog({
        autoOpen: false,
        width: 600,
        height: "auto",
        minHeight: 300,
        minWidth: 600,
        maxHeight: 300,
        title: 'Insulin Type',
        position: { my: "top", at: "top", of: window },
        modal: true,
        close: function() {
        }
    });

    $("#randomBloodSugarLevelDialog" ).dialog({
        autoOpen: false,
        width: 600,
        height: "auto",
        minHeight: 300,
        minWidth: 600,
        maxHeight: 300,
        title: 'Random Blood Sugar Level',
        position: { my: "top", at: "top", of: window },
        modal: true,
        close: function() {
        }
    });

    /*$("#stockTakingDialog" ).dialog({
        autoOpen: false,
        width: 900,
        height: "auto",
        minHeight: 300,
        minWidth: 900,
        title: 'Stock taking ',
        position: { my: "top", at: "top", of: window },
        modal: true,
        close: function() {
        }
    });

    $("#stockVarianceDialog" ).dialog({
        autoOpen: false,
        width: 1200,
        height: "auto",
        minHeight: 500,
        title: 'Stock variance report',
        position: { my: "top", at: "top", of: window },
        modal: true,
        close: function() {
        }
    });

    /*$("#reportDialog" ).dialog({
        autoOpen: false,
        width: 800,
        height: "auto",
        minHeight: 400,
        title: 'Reports',
        position: { my: "top", at: "top", of: window },
        modal: true,
        close: function() {
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
        }
    });*/

    $("#binCards" ).dialog({
        autoOpen: false,
        width: 1200,
        height: "auto",
        minHeight: 400,
        title: 'Commodity Stock movement entry/view',
        position: { my: "top", at: "top", of: window },
        modal: true,
        close: function() {
        }
    });

    $("#dailyConsumption" ).dialog({
        autoOpen: false,
        width: 900,
        height: 600,
        minHeight: 400,
        title: 'Daily Dispensing',
        position: { my: "top", at: "top", of: window },
        modal: true,
        close: function() {
        }
    });
    $("#dailyConsumptionDispensing" ).dialog({
        autoOpen: false,
        width: 900,
        height: 600,
        minHeight: 400,
        title: 'Daily Consumption and Dispensing',
        position: { my: "top", at: "top", of: window },
        modal: true,
        close: function() {
        }
    });

    $("#forgotPass" ).dialog({
        autoOpen: false,
        width: 500,
        height: 300,
        minHeight: 300,
        title: 'Forgot password',
        position: { my: "top", at: "top", of: window },
        modal: true,
        close: function() {
        }
    });

    /*$("#DrugInRegimen" ).dialog({
        autoOpen: false,
        width: "auto",
        height: 300,
        minHeight: 300,
        title: 'Drugs in regimen',
        position: { my: "top", at: "top", of: window },
        modal: true,
        close: function() {
        }
    }); */

    $("#facilityDialog" ).dialog({
        autoOpen: false,
        width: 600,
        height: "auto",
        minHeight: 300,
        minWidth: 600,
        maxHeight: 300,
        title: 'Facility information',
        position: { my: "top", at: "top", of: window },
        modal: true,
        close: function() {
        }
    });
    $("#facilityappointment" ).dialog({
        autoOpen: false,
        width: 900,
        height: 600,
        minHeight: 400,
        title: 'Facility Appointment Keeping Report',
        position: { my: "top", at: "top", of: window },
        modal: true,
        close: function() {
        }
    });
    $("#adherencemonitoring1" ).dialog({
        autoOpen: false,
        width: 900,
        height: 600,
        minHeight: 400,
        title: 'Facility Adherence Monitoring Report',
        position: { my: "top", at: "top", of: window },
        modal: true,
        close: function() {
        }
    });

    $("#facilitpadherence" ).dialog({
        autoOpen: false,
        width: 900,
        height: 600,
        minHeight: 400,
        title: 'Facility adherence summary Report',
        position: { my: "top", at: "top", of: window },
        modal: true,
        close: function() {
        }
    });
    $("#adherence" ).dialog({
        autoOpen: false,
        width: 900,
        height: 600,
        minHeight: 400,
        title: 'Facility Patient Adherence Summary',
        position: { my: "top", at: "top", of: window },
        modal: true,
        close: function() {
        }
    });

    /*******************************************************************/
    $("#databaseDialog" ).dialog({
        autoOpen: false,
        width: 600,
        height: "auto",
        minHeight: 300,
        minWidth: 600,
        maxHeight: 300,
        title: 'Database Backup',
        position: { my: "top", at: "top", of: window },
        modal: true,
        close: function() {
        }
    });
    /******************************************************************************************************/

    $("#userProfile" ).dialog({
        autoOpen: false,
        width: 400,
        minWidth: 500,
        height: 400,
        minHeight: 300,
        title: 'User Profile',
        position: { my: "top", at: "top", of: window },
        modal: true,
        close: function() {
        }
    });

    $("#familyPlanningMethodDialog" ).dialog({
        autoOpen: false,
        width: 900,
        height: "auto",
        minHeight: 300,
        minWidth: 725,
        title: 'Family Planning Method',
        position: { my: "top", at: "top", of: window },
        modal: true,
        close: function() {
        }
    });

    $("#familyPlanningMethodChangeReasonsDialog" ).dialog({
        autoOpen: false,
        width: 900,
        height: "auto",
        minHeight: 300,
        minWidth: 725,
        title: 'Family Planning Method Change Reasons',
        position: { my: "top", at: "top", of: window },
        modal: true,
        close: function() {
        }
    });

    $("#satelliteDataDialog" ).dialog({
        autoOpen: false,
        width: 1200,
        height: "auto",
        minHeight: 300,
        minWidth: 725,
        title: '',
        position: { my: "top", at: "top", of: window },
        modal: true,
        close: function() {
        }
    });


    $(".date").datepicker({dateFormat: 'dd/mm/yy', changeMonth: true,changeYear: true});
    $('#dob').datepicker({ maxDate: new Date(), dateFormat: 'dd/mm/yy', changeMonth: true,changeYear: true});
    $('#nextAppointmentDate').datepicker({ minDate: new Date(), dateFormat: 'dd/mm/yy', changeMonth: true,changeYear: true});
    $('#dateEnrolled').datepicker({ maxDate: new Date(), dateFormat: 'dd/mm/yy', changeMonth: true,changeYear: true});
    $('#serviceStartDate').datepicker({ maxDate: new Date(), dateFormat: 'dd/mm/yy', changeMonth: true,changeYear: true});
    $('#startDate').datepicker({ maxDate: new Date(), dateFormat: 'dd/mm/yy', changeMonth: true,changeYear: true});
    $('#transactionDate').datepicker({ maxDate: new Date(), dateFormat: 'dd/mm/yy', changeMonth: true,changeYear: true});
    $('#date1').datepicker({ maxDate: new Date(), dateFormat: 'dd/mm/yy', changeMonth: true,changeYear: true});
    $('#date2').datepicker({ maxDate: new Date(), dateFormat: 'dd/mm/yy', changeMonth: true,changeYear: true});
    $('#receivedDate1').datepicker({ maxDate: new Date(), dateFormat: 'dd/mm/yy', changeMonth: true,changeYear: true});
    $('#receivedDate2').datepicker({ maxDate: new Date(), dateFormat: 'dd/mm/yy', changeMonth: true,changeYear: true});
    $('#monthlyDate1').datepicker({ maxDate: new Date(), dateFormat: 'dd/mm/yy', changeMonth: true,changeYear: true});
    $('#monthlyDate2').datepicker({ maxDate: new Date(), dateFormat: 'dd/mm/yy', changeMonth: true,changeYear: true});
    $('#missedDate1').datepicker({ maxDate: new Date(), dateFormat: 'dd/mm/yy', changeMonth: true,changeYear: true});
    $('#missedDate2').datepicker({ maxDate: new Date(), dateFormat: 'dd/mm/yy', changeMonth: true,changeYear: true});
    $('#enrollDate1').datepicker({ maxDate: new Date(), dateFormat: 'dd/mm/yy', changeMonth: true,changeYear: true});
    $('#enrollDate2').datepicker({ maxDate: new Date(), dateFormat: 'dd/mm/yy', changeMonth: true,changeYear: true});

    $('#ewiDate1').datepicker({ maxDate: new Date(), dateFormat: 'dd/mm/yy', changeMonth: true,changeYear: true});
    $('#ewiDate2').datepicker({ maxDate: new Date(), dateFormat: 'dd/mm/yy', changeMonth: true,changeYear: true});
    //$('#nextAppointmentDate').datepicker({ minDate: new Date(), dateFormat: 'dd/mm/yy'});
    $( "button" ).button();
});
