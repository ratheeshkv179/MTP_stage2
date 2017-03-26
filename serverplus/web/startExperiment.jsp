<%-- 
    Document   : startExperiment
    Created on : 22 Jul, 2016, 5:59:41 PM
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
        <h1>Hello World!</h1>

        <%

            for (String val : request.getParameterValues("list")) {
                out.write("\n<br/>" + val);
            }


        %>
    </body>
</html>
