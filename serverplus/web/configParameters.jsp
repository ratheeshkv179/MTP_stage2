<%-- 
    Document   : configParameters
    Created on : 15 Sep, 2016, 11:26:13 PM
    Author     : cse
--%>

<%@page import="java.util.Enumeration"%>
<%@page import="com.iitb.cse.Utils"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>CrowdSource</title>
        <link rel="stylesheet" href="/serverplus/css/table.css">


        <script>
 

            function _check() {
                //   alert(document.getElementById('filter').value);
                if (document.getElementById('filter').value == "bssid") {
                    document.getElementById('selectonbssid').style.display = 'block'
                    document.getElementById('selectonssid').style.display = 'none'
                    document.getElementById('random').style.display = 'none'
                    document.getElementById('getclient').style.display = 'block'
                } else if (document.getElementById('filter').value == "ssid") {
                    document.getElementById('selectonssid').style.display = 'block'
                    document.getElementById('selectonbssid').style.display = 'none'
                    document.getElementById('random').style.display = 'none'
                    document.getElementById('getclient').style.display = 'block'
                } else if (document.getElementById('filter').value == "manual") {

                    document.getElementById('random').style.display = 'none'
                    document.getElementById('selectonssid').style.display = 'none'
                    document.getElementById('selectonbssid').style.display = 'none'
                    document.getElementById('getclient').style.display = 'block'

                } else if (document.getElementById('filter').value == "random") {
                    document.getElementById('random').style.display = 'block'
                    document.getElementById('selectonssid').style.display = 'none'
                    document.getElementById('selectonbssid').style.display = 'none'
                    document.getElementById('getclient').style.display = 'block'
                } else {
                    document.getElementById('random').style.display = 'none'
                    document.getElementById('selectonssid').style.display = 'none'
                    document.getElementById('selectonbssid').style.display = 'none'
                    document.getElementById('getclient').style.display = 'none'
                }
            }


        </script>
    </head>
    <body>
        
        <% 
        response.sendRedirect("frontpage.jsp");
        %>
        <!--<h1>Hello World!</h1>-->
        <br/><br/>

        <h2 class="heading">Configuration</h2>

        <br/>
        <br/>
        <form action="configParametersHandler.jsp" method="get" >

            <table class='table1'>



                <tr><td>
                        Select Clients Based on :
                        <select name='filter' id='filter' onchange="_check(this)">
                            <option value="none" ></option>
                            <option value="bssid" >BSSID</option>
                            <option value="ssid" >SSID</option>
                            <option value="manual" >Manual</option>
                            <option value="random" >Random</option>
                        </select>
                    </td><td>


                        <select name='bssid' id='selectonbssid' style="display: none">
                            <%  Enumeration<String> bssidList = Utils.getAllBssids();
                                while (bssidList.hasMoreElements()) {
                                    String bssid = bssidList.nextElement();
                                    out.write("<option value=\"" + bssid + "\">" + bssid + "</option>");
                                }
                            %>
                        </select>



                        <select name='ssid' id='selectonssid' style="display: none">
                            <%
                                Enumeration<String> ssidList = Utils.getAllSsids();
                                while (ssidList.hasMoreElements()) {
                                    String ssid = ssidList.nextElement();
                                    out.write("<option value=\"" + ssid + "\">" + ssid + "</option>");
                                }
                            %>
                        </select>


                        <input type="number" id ='random' name='random' value='1' min="1" max="5" style="display: none" />



                    </td></tr>

                <tr><td></td><td><input type="submit" class='button' id='getclient' name='getclient' value="Get_Clients"  style="display: none"/></td></tr>

                <!--             <tr><td></td><td> <a href="apchange.jsp?getclient=geclient&" id='getclient' name='getclient'  style="display: none">Get_Clients</a></td></tr>-->


            </table>



        </form>


    </body>
</html>
