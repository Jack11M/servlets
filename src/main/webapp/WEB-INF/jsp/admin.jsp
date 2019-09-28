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
        <script type="text/javascript">
            <%@include file="/WEB-INF/js/admin.js"%>
        </script>
        <style>
            <%@include file="/WEB-INF/css/admin.css"%>
        </style>
    </head>
    <body>
    <c:import url="../jspf/header.jspf" charEncoding="utf-8"> </c:import>

	    <center>
	    <br/><br/><br/>
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
    </body>
</html>
