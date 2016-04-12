/**
 * Created by kenny on 5/27/14.
 */
window.user_id = 1;
window.users = null;
window.person = null
window.personAddress = null;
window.user = null;
window.privileges = null;
window.roles = null;
window.role = null;

function showCreateUserDialog(edit) {
    $('#firstName').val('');
    $('#surname').val('');
    $('#otherNames').val('');
    $('#personId').val('');

    $('#phone').val('');
    $('#email').val('');
    $('#personAddressId').val('');

    $('#username').val('');
    $('#userId').val('');

    $('#userUpdate').val(false);
    $.ajax({
        url: 'admin/json/listRoles',
        headers: {
            "Content-Type": "application/json"
        },
        type: 'POST',
        success: function (data) {
            window.roles = data.Records;
            var html = "";
            for(var i = 0; i < window.roles.length; i++) {
                html += '<input type="checkbox" name="role" value="' + window.roles[i].id + '">  ' + window.roles[i].name + '<br/>';
            }
            $('#roles').html(html);
            if(edit) {
                $('#firstName').val(person.firstName);
                $('#surname').val(person.surname);
                $('#otherNames').val(person.otherNames);
                $('#personId').val(person.id);

                $('#phone').val(personAddress.telNo1);
                $('#email').val(personAddress.emailAddress);
                $('#personAddressId').val(personAddress.id);

                $('#username').val(user.username);
                $('#userId').val(user.id);
                $('#currentPasswordDiv').show();

                $('#userUpdate').val(true);
                if(window.user.userRolesById != undefined) {
                    var userRoles = new Array();
                    for(var i = 0; i < window.user.userRolesById.length; i++) {
                        userRoles.push(window.user.userRolesById[i].roleId);
                    }
                    setCheckBoxValue('role', userRoles);
                }

            } else {
                $('#userUpdate').val(false);
            }
            $('#usersDialog').dialog("open");
        },
        error: function () {

        }
    });

}

function saveUser() {
    var update = $('#userUpdate').val();
    if($('#firstName').val() == "") {
        alert("Fill in the First name.");
        return false;
    } else if($('#lastName').val() == "") {
        alert("Fill in the Last name");
        return false;
    } else if($('#username').val() == "") {
        alert("Fill in the Surname of the user.");
        return false;
    } else if(update == 'true' && $('#currentPassword').val() == "") {
        alert("Enter your current password.");
        return false;
    } else if(update == 'true' && ($('#currentPassword').val() != window.user.password)) {
        alert("Enter your current password.");
        return false;
    } else if($('#password').val() == "") {
        alert("Fill in the password.");
        return false;
    } else if($('#password2').val() == "") {
        alert("Fill in the password confirmation.");
        return false;
    } else if($('#password2').val() != $('#password').val()) {
        alert("Passwords do not match");
        $('#password2').val('');
        $('#password').val('');
        $('#password').focus();
    } else if($('#email').val() == "") {

    }
    var user = {};
    user.username = ($('#username').val() == "" ? null : $('#username').val());
    user.password = ($('#password').val() == "" ? null : $('#password').val());
    //user.password2 = ($('#password2').val() == "" ? null : $('#password2').val());
    user.createdBy = window.user_id;

    var roles = getCheckBoxValue('role');

    var userRoles = new Array();
    for(var i = 0; i < roles.length; i++) {
        userRoles.push({roleId : roles[i]});
    }

    user.userRolesById = userRoles;

    var person = {};
    person.firstName = ($('#firstName').val() == "" ? null : $('#firstName').val())
    person.surname = ($('#surname').val() == "" ? null : $('#surname').val())
    person.otherNames = ($('#otherNames').val() == "" ? null : $('#otherNames').val());
    person.createdBy = window.user_id;

    var personAddress = {};
    personAddress.telNo1 = ($('#phone').val() == "" ? null : $('#phone').val());
    personAddress.emailAddress = ($('#email').val() == "" ? null : $('#email').val());
    personAddress.createdBy = window.user_id;

    var url = 'admin/json/createUser';
    if(update == 'true') {
        person.id = $('#personId').val();
        personAddress.id = $('#personAddressId').val();
        user.id = $('#userId').val();
        person.updatedBy = window.user_id;
        personAddress.updatedBy = window.user_id;
        user.updatedBy = window.user_id;

        url = 'admin/json/updateUser';
    }
    loading();
    json = "{ \"person\" : " + JSON.stringify(person) + ", \"personAddress\" : " + JSON.stringify(personAddress) + ", \"user\" : " + JSON.stringify(user) + "}";
    $.ajax({
        url: url,
        headers: {
            "Content-Type": "application/json"
        },
        type: 'POST',
        data: json,
        success: function (data) {
            alert(data);
            showUserListDialog();
            closeDialog("usersDialog");
        },
        error: function () {
            $("#usersDialog").dialog("close");
            alert("Error saving user.");
            loadingEnd();
        }
    });
}

/**
 *  This function reloads the regimens from the server and displays them
 *  in a dialog.
 */
function showUserListDialog() {
    loading();
    $.ajax({
        url: 'admin/json/listUsers',
        headers: {
            "Content-Type": "application/json"
        },
        type: 'POST',
        success: function (data) {
            window.users = data;
            if(window.users != null) {
                createUserListContent()
                $("#listUsers").dialog("open");
            }
            loadingEnd();
        },
        error: function () {
            loadingEnd();
        }
    });

}

/**
 *  This function creates user list content from the Object {@link window.users}
 *
 *  It has an optional parameter, regimen which will replace the object with the same Id as it in the list.
 *
 */
function createUserListContent(user) {
    var table = "<p><button onclick='showCreateUserDialog()'>Add new user</button></p><div class='jtable-main-container'><table class='jtable'><tr><th class='jtable-column-header'>Names</th><th class='jtable-column-header'>Username</th></tr>";

    for(var i = 0; i < window.users.length; i++) {
        var us = window.users[i];
        if(user && us.id == user.id) {
            window.users[i] = user;
            us = user;
            user = null;
        }

        table += "<tr class='jtable-data-row jtable-row-even'><td style='width: 500px'>" + us.names + "</td><td style='width: 500px'>" + us.username + "</td><td class='jtable-command-column'><img class='pointer' src='css/themes/metro/edit.png' alt='Edit' onclick='editUser(" + i + ")'/></td><td class='jtable-command-column'><img class='pointer' src='css/themes/metro/delete.png' alt='delete' onclick='deleteUser(" + i + ")'/></td></tr>";
    }
    table += "</table></div>";

    $('#listUserContent').html(table);
    $( "button" ).button();
}

function editUser(i) {
    if(window.users[i] != null && window.users[i] != undefined) {
        var userId = window.users[i].id;
        if(userId != -1 && userId != undefined) {
            $.ajax({
                url: 'admin/json/getUser/' + userId,
                headers: {
                    "Content-Type": "application/json"
                },
                type: 'POST',
                success: function (data) {
                    if(data.person == undefined) {
                        alert('Error loading person data, contact admin.');
                        return;
                    }
                    window.person = data.person;
                    window.personAddress = data.personAddress;
                    window.user = data.user;
                    showCreateUserDialog(true);
                },
                error: function () {

                }
            });

        }
    }
}

function deleteUser(index) {
    var user = window.users[index];
    if(user == undefined)
        return;
    if(confirm("Delete this record?")) {

        $.ajax({
            url: 'admin/json/deleteUser/' + user.id + '/' + window.user_id,
            headers: {
                "Content-Type": "application/json"
            },
            type: 'POST',
            success: function (data) {
                if(data == 'deleted') {
                    window.users.splice(index, 1);
                    createUserListContent();
                    alert("Deleted");
                }
            },
            error: function () {
                alert(data);
            }
        });
    }
}

function showPrivilegeDialog() {

    $('#privilegeContainer').jtable({
        title: 'Privileges',
        actions: {
            listAction: 'admin/json/listPrivileges',
            createAction: function (postData) {
                var json = "{\"privilege\" : {\"name\" : \"" + $('#Edit-name').val() + "\", \"description\" : \"" + $('#Edit-description').val() + "\", \"createdBy\" : " + window.user_id + "}}";

                return $.Deferred(function ($dfd) {
                    $.ajax({
                        url: 'admin/json/createPrivilege',
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
                var json = "{\"privilege\" : {\"id\": \"" + $('#Edit-id').val() + "\", \"name\" : \"" + $('#Edit-name').val() + "\", \"description\" : \"" + $('#Edit-description').val() + "\", \"updatedBy\" : \"" + window.user_id + "\"}}";

                return $.Deferred(function ($dfd) {
                    $.ajax({
                        url: 'admin/json/updatePrivilege',
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
            }/*,
            deleteAction: function (postData) {
                return $.Deferred(function ($dfd) {
                    $.ajax({
                        url: 'admin/json/deletePrivilege/' + postData.id + '/' + window.user_id,
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
            }*/
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
    $('#privilegeContainer').jtable('load');
    $("#privilegeDialog").dialog("open");
}

/**
 *  This function initializes the District dialog. Since we need the regions from the server,
 *  we load them first if they are not loaded into the page.
 *  If they are already loaded, we just show the dialog.
 */
function showRolesListDialog() {

        $.ajax({
            url: 'admin/json/listRoles',
            headers: {
                "Content-Type": "application/json"
            },
            type: 'POST',
            success: function(data) {
                if(data.Records != undefined) {
                    window.roles = data.Records;
                    createRolesListContent()
                    $("#listRoles").dialog("open");
                }
            } ,
            error: function() {

            }
        });

}

function createRolesListContent(role) {
    var table = "<p><button onclick='showCreateRolesDialog()'>Add new role</button></p><div class='jtable-main-container'><table class='jtable'><tr><th class='jtable-column-header'>Role</th><th class='jtable-column-header'>Description</th></tr>";

    for(var i = 0; i < window.roles.length; i++) {
        var us = window.roles[i];
        if(role && us.id == role.id) {
            window.roles[i] = role;
            us = role;
            role = null;
        }

        table += "<tr class='jtable-data-row jtable-row-even'><td style='width: 500px'>" + us.name + "</td><td style='width: 500px'>" + us.description + "</td><td class='jtable-command-column'><img class='pointer' src='css/themes/metro/edit.png' alt='Edit' onclick='editRole(" + i + ")'/></td><td class='jtable-command-column'><img class='pointer' src='css/themes/metro/delete.png' alt='delete' onclick='deleteRole(" + i + ")'/></td></tr>";
    }
    table += "</table></div>";

    $('#listRolesContent').html(table);
    $( "button" ).button();
}

function showCreateRolesDialog(edit) {
    $.ajax({
        url: 'admin/json/listPrivileges',
        headers: {
            "Content-Type": "application/json"
        },
        type: 'POST',
        success: function (data) {
            window.privileges = data.Records;
            var html = "";
            for(var i = 0; i < window.privileges.length; i++) {
                html += '<input type="checkbox" name="privilege" value="' + window.privileges[i].id + '">  ' + window.privileges[i].description + '<br/>';
            }
            $('#privileges').html(html);
            if(edit) {
                $('#name').val(window.role.name);
                $('#roleId').val(window.role.id);
                $('#description').val(window.role.description);
                $('#roleUpdate').val(true);
                $('#roleActionLabel').html("Update Role");
                setCheckBoxValue('privilege', window.role.privileges)
            } else {
                $('#roleUpdate').val(false);
                $('#roleActionLabel').html("Create Role");
            }
            $('#rolesDialog').dialog("open");
        },
        error: function () {

        }
    });

}

function setCheckBoxValue(name, value) {
    var buttons, checked, i, j, v;
    buttons = document.getElementsByName(name);
    if (value === undefined) {
        return;
    }
    v = value;
    checked = false;
    i = 0;
    while (i < buttons.length) {
        buttons[i].checked = false;
        if (v.length > 0) {
            j = 0;
            while (j < v.length) {
                if (v[j] == buttons[i].value) {
                    buttons[i].checked = true;
                }
                j++;
            }
        } else {
            buttons[i].checked = false;
        }
        i++;
    }
}

function getCheckBoxValue(name) {
    var buttons, checked, i, j, v;
    buttons = document.getElementsByName(name);
    var value = new Array();
    checked = false;
    i = 0;
    while (i < buttons.length) {
        if(buttons[i].checked) {
            value.push(buttons[i].value);
        }
        i++;
    }
    return value;
}

function saveRole() {
    var update = $('#roleUpdate').val();
    if($('#name').val() == "") {
        alert("Fill in the name.");
        return false;
    }

    var role = {};
    role.name = ($('#name').val() == "" ? null : $('#name').val());
    role.description = ($('#description').val() == "" ? null : $('#description').val());
    role.privileges = getCheckBoxValue('privilege');
    role.createdBy = window.user_id;

    var url = 'admin/json/createRole';
    if(update == 'true') {
        role.id = $('#roleId').val();
        role.updatedBy = window.user_id;

        url = 'admin/json/updateRole';
    }

    json = "{ \"role\" : " + JSON.stringify(role) + "}";
    $.ajax({
        url: url,
        headers: {
            "Content-Type": "application/json"
        },
        type: 'POST',
        data: json,
        success: function (data) {
            if(role.id == undefined)
                window.roles.push(role);
            createRolesListContent(data.Record);
            alert("Saved");
            closeDialog("rolesDialog");
        },
        error: function () {
            $("#usersDialog").dialog("close");
            alert("Error saving user.");
        }
    });
}

function editRole(i) {
    if(window.roles[i] != null && window.roles[i] != undefined) {
        window.role = window.roles[i];
        if(window.role != -1 && window.role != undefined) {
           showCreateRolesDialog(true);
        }
    }
}

function deleteRole(index) {
    var role = window.roles[index];
    if(role == undefined)
        return;
    if(confirm("Delete this record?")) {

        $.ajax({
            url: 'admin/json/deleteRole/' + role.id + '/' + window.user_id,
            headers: {
                "Content-Type": "application/json"
            },
            type: 'POST',
            success: function (data) {
                if(data.Result == 'OK') {
                    window.roles.splice(index, 1);
                    createRolesListContent();
                    alert("Role deleted");
                }
            },
            error: function () {
                alert(data);
            }
        });
    }
}