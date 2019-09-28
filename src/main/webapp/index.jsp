<%@include file="../WEB-INF/jspf/initial.jspf" %>

<!DOCTYPE HTML>
<html>
    <head>
        <title>Home page</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Lato">
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Montserrat">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <style>
            <%@include file="WEB-INF/css/index.css"%>
        </style>
    </head>
    <body>
        <c:import url="WEB-INF/jspf/header.jspf" charEncoding="utf-8"> </c:import>

        <div class="w3-container w3-red w3-center" style="padding:128px 16px">
          <h1 class="w3-margin w3-jumbo">HOME PAGE</h1>
          <a href="${pageContext.request.contextPath}/admin" method="get" class="w3-button w3-black w3-padding-large w3-large w3-margin-top">Admin page</a>
        </div>

        <!-- First Grid -->
        <div class="w3-row-padding w3-padding-64 w3-container">
          <div class="w3-content">
            <div class="w3-twothird">
              <h1>Your ad may be here</h1>
            </div>

            <div class="w3-third w3-center">
              <i class="fa fa-anchor w3-padding-64 w3-text-red"></i>
            </div>
          </div>
        </div>

        <!-- Second Grid -->
        <div class="w3-row-padding w3-light-grey w3-padding-64 w3-container">
          <div class="w3-content">
            <div class="w3-third w3-center">
              <i class="fa fa-coffee w3-padding-64 w3-text-red w3-margin-right"></i>
            </div>

            <div class="w3-twothird">
              <h1>Your ad may be here</h1>
            </div>
          </div>
        </div>

        <div class="w3-container w3-black w3-center w3-opacity w3-padding-64">
            <h1 class="w3-margin w3-xlarge">Quote of the day: Just do it!</h1>
        </div>
    </body>
</html>
