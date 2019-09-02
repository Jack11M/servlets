<%@include file="../jspf/initial.jspf" %>

<!DOCTYPE HTML>
<html>
    <head>
        <meta charset="utf-8">
        <title>Admin page</title>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.0/jquery.min.js"></script>
        <script src="http://code.jquery.com/ui/1.11.0/jquery-ui.js"></script>
        <script src="https://code.jquery.com/jquery-migrate-3.0.0.min.js"></script>
        <link rel="stylesheet" href="http://code.jquery.com/ui/1.11.0/themes/smoothness/jquery-ui.css">
        <style>
            table {
                font-family: arial, sans-serif;
                border-collapse: collapse;
                width: 70%;
            }

            td, th {
                border: 1px solid #dddddd;
                text-align: left;
                padding: 8px;
            }

            tr:nth-child(even) {
                background-color: #dddddd;
            }
        </style>
    </head>
    <body>
    <c:import url="../jspf/header.jspf" charEncoding="utf-8"> </c:import>

	    <center>
		<h1 style="text-align:center">ADMIN PAGE<br/></h1>

        <div class="container">
            <table id="usersTable" class="table">
                <thread>
                <tr>
                    <th>First name</th>
                    <th>Last name</th>
                    <th>Username</th>
                    <th>Update (button)</th>
                    <th>Delete (button)</th>
                </tr>
                </thread>
            </table>
        </div>

        <p><br/></p>

        <div><button id="createUserForm"><h3>Create new user</h3></button></div>

        <div id="registrationForm" title="Registration form">
            <form id="registration">
                <center>
                <label for="firstName">First name</label>
                <input type="text" name="firstName" id="firstName" class="text ui-widget-content ui-corner-all" required>
                <label for="lastName">Last name</label>
                <input type="text" name="lastName" id="lastName" class="text ui-widget-content ui-corner-all">
                <label for="username">Username</label>
                <input type="text" name="username" id="username" class="text ui-widget-content ui-corner-all" required>
                <label for="password">Password</label>
                <input type="password" name="password" id="password" class="text ui-widget-content ui-corner-all" required>
                <button id="createUser">Create new user</button>
                </center>
            </form>
        </div>

        <div id="formForUpdate" title="Update form">
            <form id="updateForm">
                <center>
                <label for="firstNameUpdate">Values for update: First name</label>
                <input type="text" name="firstNameUpdate" id="firstNameUpdate" class="text ui-widget-content ui-corner-all">
                <label for="lastNameUpdate">Last name</label>
                <input type="text" name="lastNameUpdate" id="lastNameUpdate" class="text ui-widget-content ui-corner-all">
                <label for="usernameUpdate">Username</label>
                <input type="text" name="usernameUpdate" id="usernameUpdate" class="text ui-widget-content ui-corner-all">
                <button id="update" onclick="updateUserFunc()">Update user</button>
                </center>
            </form>
        </div>
        </center>

		<script>
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

        var dialog = $("#registration").dialog({autoOpen: false});
            $("#createUserForm").click(function (){
            dialog.dialog("open");
        });

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

        $( function() {
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
        </script>
    </body>
</html>
