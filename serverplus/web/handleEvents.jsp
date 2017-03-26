<%-- 
    Document   : handleEvents
    Created on : 7 Mar, 2017, 2:34:00 PM
    Author     : cse
--%>



<%@page import="java.sql.ResultSet"%>
<%@page import="com.sun.java.swing.plaf.windows.resources.windows"%>
<%@page import="java.util.concurrent.CopyOnWriteArrayList"%>
<%@page import="com.iitb.cse.Utils"%>
<%@page import="com.mysql.jdbc.Util"%>
<%@page import="org.eclipse.jdt.internal.compiler.impl.Constant"%>
<%@page import="java.util.Enumeration"%>
<%@page import="java.util.concurrent.ConcurrentHashMap"%>
<%@page import="com.iitb.cse.DeviceInfo"%>
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

//        out.write("<b>"+request.getParameter("event")+"<b>")    
            if (request.getParameter("event") != null && request.getParameter("event").equalsIgnoreCase("clearlist")) {
                Constants.currentSession.getConnectedClients().clear();
                response.sendRedirect("frontpage.jsp");
            }

            if (request.getParameter("event") != null && request.getParameter("event").equalsIgnoreCase("wakeUpStatusExit")) {
                Constants.currentSession.setWakeUpStatus(false);
                response.sendRedirect("wakeupClients.jsp");
            }

            if (request.getParameter("event") != null && request.getParameter("event").equalsIgnoreCase("exitControlFileSending")) {
                Constants.currentSession.setSendingControlFile(false);
                response.sendRedirect("sendControlFile.jsp");
            }


        %>


    </body>
</html>
