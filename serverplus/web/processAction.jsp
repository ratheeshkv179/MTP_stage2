<%-- 
    Document   : processAction
    Created on : 22 Jul, 2016, 3:12:54 PM
    Author     : cse
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
             <title>CrowdSource</title>
        <link rel="stylesheet" href="/serverplus/css/table.css">
    </head>
    <body>
        <%

            if (request.getParameter("home") != null) {
                out.write("Home");
                response.sendRedirect("homepage.jsp");

            } else if (request.getParameter("addExperiment") != null) {
                out.write("Add experiment");
                response.sendRedirect("addExperiment.jsp");

            } else if (request.getParameter("apChange") != null) {
                out.write("AP Change");
                response.sendRedirect("apchange.jsp");

            } else if (request.getParameter("expDetails") != null) {
                out.write("Experiment details");
                response.sendRedirect("experimentDetails.jsp");
                
            } else if (request.getParameter("getLogFile") != null) {
                out.write("Get Log files");
                response.sendRedirect("getPendingLogFiles.jsp");
            } else if(request.getParameter("configureParams") != null){
                out.write("Configure Parameters");
                response.sendRedirect("configParameters.jsp");
            }else {
                out.write("Other conditions");

            }
            
//

        %>
    </body>
</html>
