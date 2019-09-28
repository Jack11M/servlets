<%@include file="../jspf/initial.jspf" %>

<html>
    <head>
        <title>Success login</title>
        <style>
            <%@include file="/WEB-INF/css/success.css"%>
        </style>
    </head>
    <body>
    <c:import url="../jspf/header.jspf" charEncoding="utf-8"> </c:import>
        <center>
        <br/><br/><br/><br/>
        <h1>${userDto.username} is logged in!</h1>
        </center>
	</body>
</html>