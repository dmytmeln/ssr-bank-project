<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  String errorMsg = (String) request.getAttribute("errorMsg");
  errorMsg = (errorMsg == null) ? "" : errorMsg;
%>
<!doctype html>
<html lang="en">

  <head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Error 404</title>
    <style>
      <%@include file="../../static/css/style.css"%>
    </style>
  </head>

  <body>
    <!-- Header -->
    <%@include file="header.html"%>

    <!-- Home -->
    <section class="home">
      <h1 class="error_msg"><%=errorMsg%></h1>
    </section>

  </body>

</html>
