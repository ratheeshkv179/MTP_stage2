<%-- 
    Document   : configParametersHandler
    Created on : 15 Sep, 2016, 11:31:32 PM
    Author     : cse
--%>

<%@page import="java.util.concurrent.CopyOnWriteArrayList"%>
<%@page import="com.iitb.cse.Utils"%>
<%@page import="com.iitb.cse.Utils"%>
<%@page import="java.util.Enumeration"%>
<%@page import="java.util.concurrent.ConcurrentHashMap"%>
<%@page import="com.iitb.cse.Constants"%>
<%@page import="com.iitb.cse.Constants"%>
<%@page import="com.iitb.cse.DeviceInfo"%>
<%@page import="com.iitb.cse.DBManager"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>CrowdSource</title>
        <link rel="stylesheet" href="/serverplus/css/table.css">
        <script>

            function check() {

                return true;
            }

            function setOption1() {
//                alert("hai-1")

                var el = document.getElementById("heartbeat");
                if (el.checked) {
                    document.getElementById("sIp").disabled = true;
                    document.getElementById("sPort").disabled = true;
                    document.getElementById("cPort").disabled = true;
                    document.getElementById("sIp").checked = true;
                    document.getElementById("sPort").checked = true;
                    document.getElementById("cPort").checked = true;

                } else {
                    document.getElementById("sIp").disabled = false;
                    document.getElementById("sPort").disabled = false;
                    document.getElementById("cPort").disabled = false;
                }


            }

            function setOption2() {
//                alert("hai-2")

                var el = document.getElementById("heartbeat");
                if (el.checked) {
                    document.getElementById("sIp").disabled = true;
                    document.getElementById("sPort").disabled = true;
                    document.getElementById("cPort").disabled = true;
                    document.getElementById("sIp").checked = true;
                    document.getElementById("sPort").checked = true;
                    document.getElementById("cPort").checked = true;

                } else {
                    document.getElementById("sIp").disabled = false;
                    document.getElementById("sPort").disabled = false;
                    document.getElementById("cPort").disabled = false;
                }


            }


        </script>
    </head>
    <body>

        <br/><a href='configParameters.jsp'>Back</a><br/><br/>

        <h2 class="heading">Change Configurations</h2>

        <form action="configParametersStatus.jsp"  method="get"  enctype="multipart/form-data" onsubmit="return check();">
            <!--<fieldset>-->
            <table class='table1'>





                <!--                <div><input type="radio" name="o1" id="burger" onchange="setFries();"/>Burger</div>
                                <div >Hi<input type="text" id="yesFries" ><input type="checkbox" name="e1" id="fries" checked disabled="disabled"/>Fries with that?</div>
                                <div><input type="radio" name="o1" id="pizza" onchange="setFries();"/>Pizza</div>
                                <div><input type="radio" name="o1" id="hotdog" onchange="setFries();"/>Hot Dog</div>
                -->

                <tr><td><input type="radio" checked  name='radioOptions' id="heartbeat" onchange="setOption1();" value="heartBeat"/>HeartBeat</td><td></td></tr>
                <tr><td></td><td>Heart Beat Duration<i>(seconds)</i></td><td><input type="text" name="hbDuration"/></td></tr>                


                <tr><td><input type="radio"   name='radioOptions' onchange="setOption2();" value="serverConf"/>Server Parameters</td><td></td></tr>
                <tr><td></td><td><input type="checkbox" checked id="sIp" disabled="disabled" name='selectedOptions' value="serverIp"/>Server IP</td><td><input type="text" name="serverIp"/></td></tr>                
                <tr><td></td><td><input type="checkbox" checked id="sPort" disabled="disabled" name='selectedOptions' value="serverPort"/>Server Port</td><td><input type="text" name="serverPort"/></td></tr>                
                <tr><td></td><td><input type="checkbox" checked id="cPort" disabled="disabled" name='selectedOptions' value="connectionPort"/>Connection Port</td><td><input type="text" name="connectionPort"/></td></tr>                



                <!--out.write("<tr><td><input type=\"checkbox\" checked  name='selectedclient' value=\"" + macAddr + "\"/></td><td>" + macAddr + "</td><td>" + device.getSsid() + "</td><td>" + device.getBssid() + "</td><td>" + device.getLastHeartBeatTime() + "</td><td>" + device.getExpOver() + "</td><td>" + device.isConnectionStatus() + "</td></tr>");-->

                <tr><td></td><td></td><td><input type='submit' class='button' value='Send Configuration'></td></tr>
            </table>
            <!--</fieldset>-->

            <br/>
            <br/>
            <br/>

            <h4 class="heading">Selected Clients</h4>

            <%
                session.setAttribute("filter", null);
                session.setAttribute("clientcount", null);

                if (request.getParameter("getclient") != null) {

                    /*      
                    out.write("<h1>Helo'" + request.getParameter("filter") + "'</h1>");
                    out.write("<h1>Helo'" + request.getParameter("bssid") + "'</h1>");
                    out.write("<h1>Helo'" + request.getParameter("ssid") + "'</h1>");
                    out.write("<h1>Helo'" + request.getParameter("random") + "'</h1>");
                     */
                     if (request.getParameter("filter").equals("bssid")) {

                    ConcurrentHashMap<String, DeviceInfo> clients = Constants.currentSession.getConnectedClients();
                    Enumeration<String> macList = clients.keys();
                    if (clients != null) {
                        out.write("<table border='1'>");
                        out.write("<tr><th>No</th><th>Select</th><th>Mac Address</th><th>SSID</th><th>BSSID</th><th>Last HeartBeat</th></tr>");
                        if (clients.size() == 0) {
                            out.write("<tr><td colspan=\"7\">No Clients</td></tr>");
                        } else {
                            int i = 0;
                            int flag = 0;
                            while (macList.hasMoreElements()) {
                                String macAddr = macList.nextElement();
                                DeviceInfo device = clients.get(macAddr);

                                CopyOnWriteArrayList<DeviceInfo> activeClient = Utils.activeClients();
                                if (activeClient.contains(device)) {
                                    if (request.getParameter("bssid").equalsIgnoreCase(device.getBssid())) {
                                        i++;
                                        flag = 1;
                                        out.write("<tr><td>" + i + "</td><td><input type=\"checkbox\" checked  name='selectedclient' value=\"" + macAddr + "\"/></td><td>" + macAddr + "</td><td>" + device.getSsid() + "</td><td>" + device.getBssid() + "</td><td>" + device.getLastHeartBeatTime() + "</td></tr>");
                                    }
                                }
                            }
                            if (flag == 0) {
                                out.write("<tr><td colspan=\"7\">No Active Client</td></tr>");
                            }
                        }
                        out.write("</table>");
                    }
                } else if (request.getParameter("filter").equals("ssid")) {

                    ConcurrentHashMap<String, DeviceInfo> clients = Constants.currentSession.getConnectedClients();
                    Enumeration<String> macList = clients.keys();
                    if (clients != null) {
                        out.write("<table border='1'>");
                        out.write("<tr><th>No</th><th>Select</th><th>Mac Address</th><th>SSID</th><th>BSSID</th><th>Last HeartBeat</th></tr>");
                        if (clients.size() == 0) {
                            out.write("<tr><td colspan=\"7\">No Clients</td></tr>");
                        } else {
                            int i = 0;
                            int flag = 0;
                            while (macList.hasMoreElements()) {
                                String macAddr = macList.nextElement();
                                DeviceInfo device = clients.get(macAddr);
                                CopyOnWriteArrayList<DeviceInfo> activeClient = Utils.activeClients();
                                if (activeClient.contains(device)) {
                                    if (request.getParameter("ssid").equalsIgnoreCase(device.getSsid())) {
                                        i++;
                                        flag = 1;
                                        out.write("<tr><td>" + i + "</td><td><input type=\"checkbox\" checked  name='selectedclient' value=\"" + macAddr + "\"/></td><td>" + macAddr + "</td><td>" + device.getSsid() + "</td><td>" + device.getBssid() + "</td><td>" + device.getLastHeartBeatTime() + "</td></tr>");
                                    }
                                }
                            }
                            if (flag == 0) {
                                out.write("<tr><td colspan=\"7\">No Active Client</td></tr>");
                            }
                        }
                        out.write("</table>");
                    }

                } else if (request.getParameter("filter").equals("manual")) {

                    ConcurrentHashMap<String, DeviceInfo> clients = Constants.currentSession.getConnectedClients();
                    Enumeration<String> macList = clients.keys();
                    if (clients != null) {
                        out.write("<table border='1'>");
                        out.write("<tr><th>No</th><th>Select</th><th>Mac Address</th><th>SSID</th><th>BSSID</th><th>Last HeartBeat</th></tr>");
                        if (clients.size() == 0) {
                            out.write("<tr><td colspan=\"7\">No Clients</td></tr>");
                        } else {
                            int i = 0;
                            int flag = 0;
                            while (macList.hasMoreElements()) {
                                String macAddr = macList.nextElement();
                                DeviceInfo device = clients.get(macAddr);
                                CopyOnWriteArrayList<DeviceInfo> activeClient = Utils.activeClients();
                                if (activeClient.contains(device)) {
                                    i++;
                                    flag = 1;
                                    out.write("<tr><td>" + i + "</td><td><input type=\"checkbox\" checked  name='selectedclient' value=\"" + macAddr + "\"/></td><td>" + macAddr + "</td><td>" + device.getSsid() + "</td><td>" + device.getBssid() + "</td><td>" + device.getLastHeartBeatTime() + "</td></tr>");
                                }
                            }
                            if (flag == 0) {
                                out.write("<tr><td colspan=\"7\">No Active Client</td></tr>");
                            }
                        }
                        out.write("</table>");
                    }

                } else if (request.getParameter("filter").equals("random")) {
//                        session.setAttribute("filter", "random");
//                        session.setAttribute("clientcount", request.getParameter("random"));

                    ConcurrentHashMap<String, DeviceInfo> clients = Constants.currentSession.getConnectedClients();
                    Enumeration<String> macList = clients.keys();
                    if (clients != null) {
                        out.write("<table border='1'>");
                        out.write("<tr><th>No</th><th>Select</th><th>Mac Address</th><th>SSID</th><th>BSSID</th><th>Last HeartBeat</th></tr>");
                        if (clients.size() == 0) {
                            out.write("<tr><td colspan=\"7\">No Clients</td></tr>");
                        } else {
                            int i = 0;
                            int count = 0;
                            int flag = 0;
                            while (macList.hasMoreElements()) {
                                String macAddr = macList.nextElement();
                                DeviceInfo device = clients.get(macAddr);

                                CopyOnWriteArrayList<DeviceInfo> activeClient = Utils.activeClients();
                                if (activeClient.contains(device)) {

                                    i++;
                                    count++;
                                    flag = 1;
                                    if (count <= Integer.parseInt(request.getParameter("random"))) {
                                        out.write("<tr><td>" + i + "</td><td><input type=\"checkbox\" checked  name='selectedclient' value=\"" + macAddr + "\"/></td><td>" + macAddr + "</td><td>" + device.getSsid() + "</td><td>" + device.getBssid() + "</td><td>" + device.getLastHeartBeatTime() + "</td></tr>");
                                    } else {
                                        out.write("<tr><td>" + i + "</td><td><input type=\"checkbox\"   name='selectedclient' value=\"" + macAddr + "\"/></td><td>" + macAddr + "</td><td>" + device.getSsid() + "</td><td>" + device.getBssid() + "</td><td>" + device.getLastHeartBeatTime() + "</td></tr>");
                                    }
                                }

                            }
                            if (flag == 0) {
                                out.write("<tr><td colspan=\"7\">No Active Client</td></tr>");
                            }
                        }
                        out.write("</table>");
                    }
                }

                    /*mgr = new DBManager();
                       rs = DBManager.getClientList(mgr);
                       if (rs != null) {
                           out.write("<table style=\"overflow-y:auto\"><tr><th></th><th>MAC ADDRESS</th><th>SSID</th><th>BSSID</th></tr>");
                           while (rs.next()) {
                               out.write("<tr><td><input type=\"checkbox\" checked  name='selectedclient' value=\"" + rs.getString(1) + "\"/></td><td>" + rs.getString(1) + "</td><td>" + rs.getString(2) + "</td><td>" + rs.getString(3) + "</td>");
                           }
                           out.write("</table>");
                           out.write("<input type=\"submit\" id=\'addexperiment\' name=\'getclient\' value=\"Add Experiment\" />");
                       }
                       mgr.closeConnection();
                     */
                }
            %>

        </form>


    </body>
</html>
