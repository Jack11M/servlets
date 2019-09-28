<%@include file="../jspf/initial.jspf" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>Registration</title>
        <style>
            <%@include file="/WEB-INF/css/login.css"%>
        </style>
    </head>
    <body>
    <c:import url="../jspf/header.jspf" charEncoding="utf-8"> </c:import>

        <center>
        <br/><br/><br/>
        <h2>REGISTRATION PAGE</h2>
        <form action="${pageContext.request.contextPath}/user/register" method="post" style="margin-top: 1cm" align="center">
            <br/>
            <b>Enter your first name, please:</b>
            <br/>
            <input name="firstName" type="text" placeholder="first name/>" size="35" align="center" required>
            <br/>
            <b>Enter your last name, please:</b>
            <br/>
            <input name="lastName" type="text" placeholder="last name/>" size="35" align="center">
            </br>
            <b>Enter your username, please:</b>
            <br/>
            <input name="username" type="text" placeholder="username/>" size="35" align="center" required>
            <br/>
            <b>Enter your password, please:</b>
            <br/>
            <input name="password" type="password" placeholder="password/>" size="35" align="center" required>
            </br></br>
            <input type="submit" value="Register" align="center">
            </br>
        </form>
        </center>
	</body>
</html>