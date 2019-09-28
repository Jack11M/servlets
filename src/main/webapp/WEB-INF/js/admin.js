    $(document).ready(function(){
        $.getJSON("${pageContext.request.contextPath}/management/users", function(data) {
            var users = [];
            $.each(data, function(key, user){
                users.push("<tr>");
                    users.push("<td id=''" + key + "''>" + user.firstName + "</td>");
                    users.push("<td id=''" + key + "''>" + user.lastName + "</td>");
                    users.push("<td id=''" + key + "''>" + user.username + "</td>");
                    users.push("<td>" + "<button id='updateUser' type='button' onclick='updateUser(`"+user.username+"`)'>" + "update" + "</button>" + "</td>");
                    users.push("<td>" + "<button id='deleteUser' type='button' onclick='deleteUser(`"+user.username+"`)'>" + "delete" + "</button>" + "</td>");
                users.push("</tr>");
            });
            $("<tbody/>", {html: users.join("")}).appendTo("table");
        })
        .done(function(){
            console.log("Table of users has been got");
        })
        .fail(function(){
           console.log("Error: table of users has not been got");
        });
    });

    $(document).ready(function(){
        var dialog = $("#registration").dialog({autoOpen: false});
            $("#createUserForm").click(function (){
            dialog.dialog("open");
        });
    });

    $(document).ready(function(){
        $('#createUser').click(function () {
            var newUser = new Object();
            newUser.firstName = $('#firstName').val();
            newUser.username = $('#username').val();
            newUser.password = $('#password').val();
            if ($('#lastName') != ""){
                newUser.lastName = $('#lastName').val();
            }

            $.ajax({
                url: "${pageContext.request.contextPath}/management/user",
                type: 'POST',
                data: JSON.stringify(newUser),
                contentType: 'application/json',
                dataType: 'json'
            })
            .done(function(){
                console.log("User has been created");
            })
            .fail(function(){
                console.log("Error: User has not been created");
            });
        });
    });

    $(function() {
        $("#updateForm").dialog({
            autoOpen: false,
        });
    });

    function updateUser(usernameForUpdate) {
        $("#updateForm").data('usernameForUpdate', usernameForUpdate).dialog("open");
    };

    function updateUserFunc(){
        var usernameForUpdate = $("#updateForm").data("usernameForUpdate");
        var updatedUser = new Object();
        updatedUser.oldUsername = usernameForUpdate;
        updatedUser.updatedUsername = $("#usernameUpdate").val();
        updatedUser.firstName = $("#firstNameUpdate").val();
        updatedUser.lastName = $("#lastNameUpdate").val()

        console.log("Update user with username: " + usernameForUpdate);
        $.ajax({
            url: "${pageContext.request.contextPath}/management/user",
            type: 'PUT',
            data: JSON.stringify(updatedUser),
            contentType: 'application/json',
            dataType: 'json'
        })
        .done(function(){
            console.log("User has been updated");
        })
        .fail(function(){
            console.log("Error: User has not been updated");
        });
    }

    function deleteUser(username) {
        var usernameForDelete = username;
        var askPermission = confirm("Are you sure?");
        if(askPermission == true){
            console.log("Delete user with username: " + usernameForDelete);
            $.ajax({
                url: "${pageContext.request.contextPath}/management/user",
                type: 'DELETE',
                data: JSON.stringify(usernameForDelete),
                contentType: 'application/json',
                dataType: 'json'
            })
            .done(function(){
                console.log("User has been deleted");
            })
            .fail(function(){
                console.log("Error: User has not been deleted");
            });
        }
    };
