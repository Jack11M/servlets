<%@include file="../jspf/initial.jspf" %>

<html>
    <head>
        <meta charset="utf-8">
        <title>User page</title>
        <style>
            <%@include file="/WEB-INF/css/success.css"%>
        </style>
    </head>
    <body>
    <c:import url="../jspf/header.jspf" charEncoding="utf-8"> </c:import>

        <center>
        <br/><br/><br/><br/>
        <h1><b>User page</h1>
        <br/>
        <h3><table>
            <tr>Username: ${userDto.username}</tr><br/>
            <tr>First name: ${userDto.firstName}</tr><br/>
            <tr>Last name: ${userDto.lastName}</tr><br/>
            <tr>Status: ${userDto.status}</tr><br/>
            <tr>Roles: ${userDto.roles}</tr><br/>
        </table></h3>
        </center>
    </body>
</html>
