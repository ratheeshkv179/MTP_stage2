<%-- 
    Document   : apchangeStatus
    Created on : 24 Jul, 2016, 12:37:05 AM
    Author     : ratheeshkv
--%>

<%@page import="com.iitb.cse.DeviceInfo"%>
<%@page import="com.iitb.cse.Constants"%>
<%@page import="com.iitb.cse.Utils"%>
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
            
            String ssid = request.getParameter("_ssid");
            String bssid = request.getParameter("_bssid");
            String name = request.getParameter("_username");
            String pwd = request.getParameter("_password");
            String sec = request.getParameter("_security");
            
//            out.write("<br/>Ssid : [" + ssid + "]");
//            out.write("<br/>Bssid : [" + bssid + "]");
//            out.write("<br/>Username : [" + name + "]");
//            out.write("<br/>Password : [" + pwd + "]<br/>");
//            out.write("<br/>Security : [" + sec + "]<br/>");
            
            if (Constants.currentSession.getApConfFilteredDevices() != null) {
                Constants.currentSession.getApConfFilteredDevices().clear();
            }
            
            String arr[] = request.getParameterValues("selectedclient");
            for (int i = 0; i < arr.length; i++) {
//                out.write("<br/>Selected : " + arr[i]);
                DeviceInfo d = Constants.currentSession.getConnectedClients().get(arr[i]);
                Constants.currentSession.getApConfFilteredDevices().add(d);
            }
            
            String setting = "USERNAME=" + name.trim() + "\nPASSWORD=" + pwd.trim() + "\nBSSID=" + bssid.trim() + "\nSSID=" + ssid.trim() + "\nSECURITY=" + sec.trim();
            Utils.sendApSettings(setting);
            response.sendRedirect("apchangeStatusDetails.jsp");

        %>


    </body>
</html>
