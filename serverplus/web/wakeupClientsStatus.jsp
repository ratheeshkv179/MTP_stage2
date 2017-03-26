<%-- 
    Document   : wakeupClientsStatus
    Created on : 10 Mar, 2017, 3:36:23 PM
    Author     : cse
--%>
<%@page import="java.lang.Long"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.util.Enumeration"%>
<%@page import="java.util.Vector"%>
<%@page import="java.util.logging.Logger"%>
<%@page import="java.util.logging.Level"%>
<%@page import="org.apache.commons.fileupload.FileUploadException"%>
<%@page import="org.apache.commons.fileupload.FileItem"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="java.io.File"%>
<%@page import="org.apache.commons.fileupload.disk.DiskFileItemFactory"%>
<%@page import="org.apache.commons.fileupload.servlet.ServletFileUpload"%>
<%@page import="com.iitb.cse.*"%>



<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>

        <%
            
            
            String duration = request.getParameter("newtime");
//            out.write("<br>"+request.getParameter("newtime")+"<br>");            
            Constants.currentSession.getWakeUpDevices().clear();
            
            Constants.currentSession.setStartwakeUpDuration(System.currentTimeMillis());
            Constants.currentSession.setWakeUpDuration(Long.parseLong(duration));
            
            String arr[] = request.getParameterValues("selectedclient");
            for (int i = 0; i < arr.length; i++) {
//                out.write("<br/>Selected : " + arr[i]);
                DeviceInfo d = Constants.currentSession.getConnectedClients().get(arr[i]);
                Constants.currentSession.getWakeUpDevices().add(d);
            }
            
            Constants.currentSession.getWakeUpDevicesStatus().clear();
            Constants.currentSession.setWakeUpStatus(true);
            Utils.sendWakeUpRequest(duration);            
            response.sendRedirect("wakeupClientsView.jsp");
            
            
            
//            long time = Constants.currentSession.getWakeUpDuration() - ((System.currentTimeMillis()) - Constants.currentSession.getStartwakeUpDuration());                        
//            out.write("<br>"+time+"</br>");

        %>
    </body>
</html>
