<%-- 
    Document   : configParametersStatus
    Created on : 16 Sep, 2016, 12:48:26 AM
    Author     : cse
--%>

<%@page import="com.iitb.cse.Utils"%>
<%@page import="com.iitb.cse.DeviceInfo"%>
<%@page import="com.iitb.cse.Constants"%>
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

            String[] macAddr = request.getParameterValues("selectedclient");
            Constants.currentSession.getConfigFilteredDevices().clear();

            for (String mac : macAddr) {
                DeviceInfo device = Constants.currentSession.getConnectedClients().get(mac);
                Constants.currentSession.getConfigFilteredDevices().add(device);
            }

            String msg = request.getParameter("radioOptions");

            if (msg.equalsIgnoreCase("heartBeat")) {

                String msg1 = request.getParameter("hbDuration");

                out.write(msg + " = " + msg1);

                Utils.sendHeartBeatDuration(msg1);

            } else if (msg.equalsIgnoreCase("serverConf")) {

                String[] msg1 = request.getParameterValues("selectedOptions");
                String serverIP = null;
                String serverPORT = null;
                String connectionPORT = null;
                for (String x : msg1) {

                    if (x.equalsIgnoreCase("serverIp")) {
                        serverIP = request.getParameter("serverIp");

                    } else if (x.equalsIgnoreCase("serverPort")) {
                        serverPORT = request.getParameter("serverPort");

                    } else if (x.equalsIgnoreCase("connectionPort")) {
                        connectionPORT = request.getParameter("connectionPort");
                    }
                    out.write(msg + " = " + x + "\n");

                }

                Utils.sendServerConfiguration(serverIP, serverPORT, connectionPORT);

            }


        %>
    </body>
</html>

