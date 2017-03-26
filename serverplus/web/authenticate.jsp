<%-- 
    Document   : authenticate
    Created on : 12 Jul, 2016, 10:24:40 PM
    Author     : ratheeshkv
--%>

<%@page import="com.iitb.cse.DBManager"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>CrowdSource</title>
        <link rel="stylesheet" href="/serverplus/css/table.css">    </head>
    <body>
        <%
              String username = request.getParameter("name");
              String password = request.getParameter("pwd");
              
              if(DBManager.authenticate(username, password)){
                  session.setAttribute("currentUser", username);
              }else {
                  session.setAttribute("currentUser", null);
              }
              response.sendRedirect("index.jsp");
        %>
    </body>
</html>







