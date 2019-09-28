<%@include file="../jspf/initial.jspf" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>Logout</title>
        <style>
            <%@include file="/WEB-INF/css/login.css"%>
        </style>
    </head>
    <body>
    <c:import url="../jspf/header.jspf" charEncoding="utf-8"> </c:import>

    <center>
        <br/><br/><br/><br/>
        <form action="${pageContext.request.contextPath}/user/logout" method="post" style="margin-top: 1cm">
            <h1><b>Do you really want to logout? </b></h1>
            <input type="submit" value="Yes" name="logout">
            <input type="submit" value="No" name="logout">
        </form>
    </center>
    </body>
</html>