<%@include file="../jspf/initial.jspf" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>Login</title>
    </head>
    <style>
        body {
        	background-color: #DAA520;
        }
    </style>
    <body>
    <c:import url="../jspf/header.jspf" charEncoding="utf-8"> </c:import>
        <center>
        <br/><br/><br/>
        <h2>LOGIN PAGE</h2>
        <form action="${pageContext.request.contextPath}/user/login" method="post" style="margin-top: 1cm" align="center">
            <br/>
            <b>Enter your username, please:</b>
            <br/>
            <input name="username" type="text" placeholder="username/>" size="35" align="center" required>
            <br/>
            <b>Enter your password, please:</b>
            <br/>
            <input name="password" type="password" placeholder="password/>" size="35" align="center" required>
            </br></br>
            <input type="submit" value="Login" align="center">
            </br>
        </form>
        </center>
	</body>
</html>