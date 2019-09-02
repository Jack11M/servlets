<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isErrorPage="true" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
    <head>
        <title>Error Page</title>
    </head>
    <body>
        <center>
        <h1>500 Internal Server Error</h1>
        <div style="color: #F00;">
            <h2>Exception: <c:out value="${requestScope['javax.servlet.error.exception']}"/>
            <br/>
            Exception type: <c:out value="${requestScope['javax.servlet.error.exception_type']}"/>
            <br/>
            Request URI: <c:out value="${requestScope['javax.servlet.error.request_uri']}"/>
            <br/>
            Servlet name: <c:out value="${requestScope['javax.servlet.error.servlet_name']}"/>
            <br/>
            Status code: <c:out value="${requestScope['javax.servlet.error.status_code']}"/></h2>
        </div>

        <p><c:set var="exception" value="${requestScope['javax.servlet.error.exception']}"/>
            <h3>Stack trace: </h3>
                <c:if test="${exception != null}">
                <c:forEach var="stackTraceElem" items="${exception.stackTrace}">
                <c:out value="${stackTraceElem}"/><br/>
            </c:forEach>
        </c:if></p>
        </center>
	</body>
</html>
