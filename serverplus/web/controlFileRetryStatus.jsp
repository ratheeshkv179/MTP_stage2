<%-- 
    Document   : controlFileRetryStatus
    Created on : 23 Mar, 2017, 2:20:00 AM
    Author     : cse
--%>


<%@page import="java.util.concurrent.ConcurrentHashMap"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="com.iitb.cse.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>CrowdSource</title>
        <link rel="stylesheet" href="/serverplus/css/table.css">    </head>
    <body>

        <br/><a href='getPendingLogFiles.jsp'>Back</a><br/><br/>
        <%

//            response.setIntHeader("refresh", 5);


            String name[] = request.getParameterValues("selected");
            String file_id = Constants.currentSession.getRetryControlFileId();//request.getParameter("file_id");
            String file_name = Constants.currentSession.getRetryControlFileName();//request.getParameter("file_name");
            
//            out.write("<br>"+name.length+"<br>");            
//            out.write("<br>"+file_id+"<br>");
//            out.write("<br>"+file_name+"<br>");
            
           
            Constants.currentSession.getFilteredClients().clear();
            Constants.currentSession.getControlFileSendingSuccessClients().clear();                        
            Constants.currentSession.getControlFileSendingFailedClients().clear();
            Constants.currentSession.getControlFileSendingStatus().clear();
                

//            String name[] = request.getParameterValues("selected");

            for (String cl : name) { // 1_macAddress form
                DeviceInfo device = Constants.currentSession.getConnectedClients().get(cl);
                Constants.currentSession.getFilteredClients().add(device);
            }
            
            
//            String file_id = request.getParameter("file_id");
//            String file_name = request.getParameter("file_name");
            
            out.write("<br>"+file_id+"<br>");
            out.write("<br>"+file_name+"<br>");
                
        
            int clients = 5;

            if (request.getParameter("numclients") != null) {
                clients = Integer.parseInt(request.getParameter("numclients").trim());
            }

            int time = 10;

            if (request.getParameter("duration") != null) {
                time = Integer.parseInt(request.getParameter("duration").trim());;

            }

            
            
            Constants.currentSession.setSendingControlFile(true);
            
            Utils.reSendControlFile(file_id,file_name,Integer.toString(clients),Integer.toString(time));
            response.sendRedirect("controlFileStatus.jsp");

           
            


        %>
    </body>
</html>
