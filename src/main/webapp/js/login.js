/**
 * Created by kenny on 5/27/14.
 */
$(document).ready(function() {
    loadStores();

    $("input").keypress(function(event) {
        if (event.which == 13) {
            event.preventDefault();
            validateLogin();
        }
    });
});

function loadStores() {
    $.ajax({
        url: 'reference/json/login/listReferences',
        headers: {
            "Content-Type": "application/json"
        },
        type: 'POST',
        success: function (data) {
            if(data != undefined && data.accountList != undefined) {
                var optionList = document.getElementById('store');
                for(var i = 0; i < data.accountList.length; i++) {
                    optionList.add(new Option(data.accountList[i].name, data.accountList[i].id), null);
                }
            }
        },
        error: function () {
            ;
        }
    });
}

function validateLogin() {
    if(document.getElementById("username").value == '') {
        document.getElementById("loginnotification").innerHTML = "Please provide any username.";
    } else if(document.getElementById("password").value == '') {
        document.getElementById("loginnotification").innerHTML = "Please provide your password.";
    } else if(document.getElementById("store").value == '') {
        document.getElementById("loginnotification").innerHTML = "Please select the store.";
    } else {
        loading();
        var json = "{\"username\" : \"" + $('#username').val() + "\", \"password\" : \"" + $('#password').val() + "\", \"store\" : \"" + $('#store').val() + "\"}";
        $.ajax({
            url: 'login/json/authenticateUser',
            headers: {
                "Content-Type": "application/json"
            },
            type: 'POST',
            data: json,
            success: function (data) {
                loadingEnd();
                if(data !== null)
                    window.location = 'home.jsp';
                else
                    document.getElementById("loginnotification").innerHTML = "Invalid username/password.";
            },
            error: function () {
                loadingEnd();
            }
        });
    }

}

function loadSecretQuestion() {
    if(document.getElementById("userNameForgot").value == '') {
        alert("Enter your username");
    } else {
        loading();
        var json = {};
        json.username = $('#userNameForgot').val();
        $.ajax({
            url: 'login/json/getSecretQuestion',
            headers: {
                "Content-Type": "application/json"
            },
            type: 'POST',
            data: JSON.stringify(json),
            success: function (data) {
                if(data.secretQuestion != undefined) {
                    $('#userid').val(data.id);
                    $('#question').html(data.secretQuestion);
                    $('#questionArea').show();
                    $('#usernameInput').hide();
                    $('#userNameForgot').val('');
                } else {
                    alert("No secret question defined");
                }

            },
            error: function () {

            },
            complete: function() {
                loadingEnd();
            }
        });
    }
}

function confirmAnswer() {
    var answer = $('#answer').val();
    if(answer == '') {
        alert("Enter the answer to the question.");
        $('#answer').focus();
        return;
    }
    loading();
    var json = {};
    json.id = "";
    json.username = $('#userNameForgot').val();
    json.secretAnswer = $('#answer').val();
    $.ajax({
        url: 'login/json/validateSecretQuestion',
        headers: {
            "Content-Type": "application/json"
        },
        type: 'POST',
        data: JSON.stringify(json),
        success: function (data) {
            if(data.Result == 'OK') {
                $('#newPasswordArea').show();
                $('#questionArea').hide();
            } else {
                alert("The answer is wrong, confirm again.");
            }

        },
        error: function () {

        },
        complete: function() {
            loadingEnd();
        }
    });

}
function changePassword() {
    var pass1 = $('#password1').val();
    var pass2 = $('#password2').val();
    if(pass1 == '') {
        alert("Enter the new password.");
        $('#password1').focus();
        return;
    } else if(pass2 == '') {
        alert("Enter the confirmation password.");
        $('#password2').focus();
        return;
    } else if(pass1 != pass2) {
        alert("Passwords do not match.");
        $('#password1').val('');
        $('#password1').focus();
        $('#password2').val('');
        return;
    }
    loading();
    var json = {}
    json.id = $('#userid').val();
    json.newPassword = pass1;
    $.ajax({
        url: 'admin/json/updatePassword',
        headers: {
            "Content-Type": "application/json"
        },
        type: 'POST',
        data: JSON.stringify(json),
        success: function (data) {
            if(data == 'updated') {
                alert("Password updated");
                $('#forgotPass').dialog('close');
                $('#userid').val('')
            }
        },
        error: function () {
            alert(data);
        },
        complete: function() {
            loadingEnd();
        }
    });
}

function loading() {
    $("body").addClass("loading");
}

function loadingEnd() {
    $("body").removeClass("loading");
}

function showForgotPasswordDialog() {
    $('#userNameForgot').val('');
    $('#userid').val('');
    $('#answer').val('');
    $('#password1').val('');
    $('#password2').val('');

    $('#questionArea').hide();
    $('#newPasswordArea').hide();
    $('#usernameInput').show();
    $('#forgotPass').dialog('open');
}

