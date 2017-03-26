<%-- 
    Document   : getLogFiles
    Created on : 31 Jul, 2016, 3:56:07 PM
    Author     : ratheeshkv
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
            Constants.currentSession.getGetLogFilefFilteredDevices().clear();

            String name[] = request.getParameterValues("selected");

            for (String cl : name) { // 1_macAddress form
                String list[] = cl.split("_");
                DeviceInfo device = Constants.currentSession.getConnectedClients().get(list[1]);
                device.setGetlogrequestsend(false);
                device.setLogFileReceived(false);
                device.setDetails("");
                Constants.currentSession.getGetLogFilefFilteredDevices().put(list[1], device);
            }

            /*          

            int requestCount = 0;
            if (request.getParameter("fetchLogFile") != null) {
                requestCount = Utils.getClientListForLogRequest(Constants.currentSession.getCurrentExperimentId());
            }

            int expId = Constants.currentSession.getCurrentExperimentId();
            ResultSet rs = DBManager.getLogFileRequestStatus(expId);
            int fileReceived = DBManager.getLogFileReceivedCount(expId);

            int getLogSuccess = 0;
            int getLogFailed = 0;
            int getLogPending = 0;

            if (rs != null) {
                while (rs.next()) {
                    out.write("\n->" + rs.getString(1) + "" + rs.getString(2));
                    if (rs.getString(1).equals("1")) {
                        getLogSuccess = Integer.parseInt(rs.getString(2));
                    } else if (rs.getString(1).equals("2")) {
                        getLogFailed = Integer.parseInt(rs.getString(2));
                    }
                }
            }

            getLogPending = requestCount - getLogSuccess - getLogFailed;

            getLogPending = (getLogPending < 0) ? 0 : getLogPending;

            out.write(
                    "<br/><br/>Fetch Log File Req Success</td><td>" + getLogSuccess + " ");
            out.write(
                    "<br/><br/>Fetch Log File Req Failed</td><td>" + getLogFailed + " ");
            out.write(
                    "<br/><br/><td>Fetch Log File Req Pending</td><td>" + getLogPending + " <br/><br/>");
            int expOver = DBManager.getExperimentOverCount(expId);

            if (fileReceived == expOver && expOver != 0) {

                out.write("<br/><br/><form action='experimentOver.jsp' method='post'>");
                out.write("<tr><td><input type='submit' name='stopExp' value='Stop Experiment'></td><td>All Requested Log FIle received</td</tr>");
                out.write("</form>");

            }
            out.write(
                    "<br/>Duration :" + request.getParameter("duration"));
            out.write(
                    "<br/>No. Of Clients : " + request.getParameter("numclients"));
            
            
             */
            int clients = 5;

            if (request.getParameter("numclients") != null) {

                clients = Integer.parseInt(request.getParameter("numclients").trim());
            }

            int time = 10;

            if (request.getParameter("duration") != null) {

                time = Integer.parseInt(request.getParameter("duration").trim());;

            }

            Utils.requestLogFiles(clients, time, name);

            response.sendRedirect("getLogFilesReqStatus.jsp");

        %>
    </body>
</html>
