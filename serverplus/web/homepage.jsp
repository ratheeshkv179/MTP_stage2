<%-- 
    Document   : homepage
    Created on : 22 Jul, 2016, 4:21:25 PM
    Author     : cse
--%>

<%@page import="com.sun.java.swing.plaf.windows.resources.windows"%>
<%@page import="java.util.concurrent.CopyOnWriteArrayList"%>
<%@page import="com.iitb.cse.Utils"%>
<%@page import="com.mysql.jdbc.Util"%>
<%@page import="org.eclipse.jdt.internal.compiler.impl.Constant"%>
<%@page import="java.util.Enumeration"%>
<%@page import="java.util.concurrent.ConcurrentHashMap"%>
<%@page import="com.iitb.cse.DeviceInfo"%>
<%@page import="com.iitb.cse.Constants"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>CrowdSource</title>
        <link rel="stylesheet" href="/serverplus/css/table.css"> 

        <script>
            function refreshPage() {
                alert("calllled");
            }

        </script>

    </head>
    <body>

        <%
            response.setIntHeader("refresh", 5); // refresh in every 5 seconds
%>

        <table border='1'>
            <caption><h3>Status</h3></caption>
         <!--   <tr><td>Server Status</td><td><%=(Constants.currentSession.isServerOn() ? "Running" : "Not Running")%></td></tr> --> 
            <tr><td>Experiment Status</td><td><%=(Constants.currentSession.isExperimentRunning() ? "Running" : "Not Running")%></td></tr>
            <tr><td>Number of Clients Connected</td><td><%=Constants.currentSession.getConnectedClients().size()%></td></tr>   


            <%
                CopyOnWriteArrayList<DeviceInfo> activeClient = Utils.activeClients();
            %>




            <tr><td>Number of Active Clients</td><td><%=activeClient.size()%></td></tr>   
            <tr><td>Number of Passive Clients</td><td><%=(Constants.currentSession.getConnectedClients().size() - activeClient.size())%></td></tr>   

        </table>
        <br/>
        <br/>

        <table border="1">
            <caption><h3>AccessPoint Connection Details</h3></caption>
            <tr><th>BSSID</th><th>SSID</th><th>No. Of Clients Connected</th></tr>
                    <%

                        ConcurrentHashMap<String, String> apConnection = Utils.getAccessPointConnectionDetails();
                        Enumeration detail = apConnection.keys();

                        boolean flag = false;

                        while (detail.hasMoreElements()) {
                            flag = true;
                            String bssid = (String) detail.nextElement();
                            String ssid_count = apConnection.get(bssid);
                            String info[] = ssid_count.split("#");

                            out.write("<tr><td>" + bssid + "</td><td>" + info[0] + "</td><td>" + info[1] + "</td></tr>");

                        }

                        if (!flag) {
                            out.write("<tr><td colspan='3' >No Clients!!!</td></tr>");
                        }


                    %>
        </table>


        <br/>
        <br/>
        <br/>






        <%            if (request.getParameter("clearclientlist") != null) {
                Constants.currentSession.getConnectedClients().clear();
                response.setIntHeader("refresh", 0); // refresh in every 5 seconds
            }

            if (request.getParameter("refreshclientlist") != null) {
                //
            }

            ConcurrentHashMap<String, DeviceInfo> clients = Constants.currentSession.getConnectedClients();
            Enumeration<String> macList = clients.keys();

            boolean flag1 = false;

            if (clients != null) {

                if (Constants.currentSession.getConnectedClients().size() > 0) {
        %>


        <table class='table1'>
            <tr><td><h3 class="heading">List Of Connected Clients</h3></td><td>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                </td><td>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

                </td>
                <td>
                    <form action="homepage.jsp" method="post">
                        <input type="submit" class='button1'  formtarget="myframe" name="clearclientlist" onclick="refreshPage();"value="Clear Connections">
                    </form>
                </td>
            </tr>
        </table>




        <%//            out.write("<h3>List Of Connected Clients</h3>");
                }
                out.write("<table border='1'>");
                //                out.write("<tr><th>Mac Address</th><th>SSID</th><th>BSSID</th><th>Last HeartBeat</th><th>Experiment status</th><th>Connection Status</th></tr>");
                out.write("<tr><th></th><th>Mac Address</th><th>IP Address</th><th>SSID</th><th>BSSID</th><th>Last HeartBeat</th><th>Connection Status</th></tr>");

                int index = 0;
                while (macList.hasMoreElements()) {
                    flag1 = true;
                    String macAddr = macList.nextElement();
                    DeviceInfo device = clients.get(macAddr);
                    index++;

                    if (activeClient.contains(device)) {
                        out.write("<tr><td>" + index + "</td><td><a href=deviceInformation.jsp?macAddr=" + macAddr + ">" + macAddr + "</a></td><td>" + device.getIp() + "</td><td>" + device.getSsid() + "</td><td>" + device.getBssid() + "</td><td>" + device.getLastHeartBeatTime() + "</td><td><b style='color:green'>Active</b></td></tr>");
                    } else {
                        out.write("<tr><td>" + index + "</td><td><a href=deviceInformation.jsp?macAddr=" + macAddr + ">" + macAddr + "</a></td><td>" + device.getIp() + "</td><td>" + device.getSsid() + "</td><td>" + device.getBssid() + "</td><td>" + device.getLastHeartBeatTime() + "</td><td><b style='color:red'>Passive</b></td></tr>");
                    }

                    //                  out.write("<tr><td>" + macAddr + "</td><td>" + device.getSsid() + "</td><td>" + device.getBssid() + "</td><td>" + device.getLastHeartBeatTime() + "</td><td>" + device.getExpOver() + "</td><td>" + device.isConnectionStatus() + "</td></tr>");
                }

                if (!flag1) {
                    out.write("<tr><td colspan='6' >No Clients!!!</td></tr>");
                }
                out.write("</table>");
            }


        %>



    </body>
</html>

