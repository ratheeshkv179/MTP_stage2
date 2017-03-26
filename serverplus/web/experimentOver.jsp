<%-- 
    Document   : experimentOver
    Created on : 2 Aug, 2016, 2:07:56 AM
    Author     : cse
--%>

<%@page import="com.iitb.cse.Utils"%>
<%@page import="com.iitb.cse.Constants"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>CrowdSource</title>
        <link rel="stylesheet" href="/serverplus/css/table.css">    </head>
    <body>

        <%
            if (request.getParameter("stopExp") != null) {
                Constants.currentSession.setExperimentRunning(false);
                Utils.sendStopExperiment(Constants.currentSession.getCurrentExperimentId()); 
                response.sendRedirect("startExp.jsp");
            }/*else if(){ // exitControlFileSending
                Constants.currentSession.setExperimentRunning(false);
                Utils.sendStopExperiment(Constants.currentSession.getCurrentExperimentId()); 
                response.sendRedirect("addExperiment.jsp");
            }*/

        %>

    </body>
</html>
