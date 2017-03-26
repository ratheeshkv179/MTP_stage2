<%-- 
    Document   : updateApp
    Created on : 29 Jan, 2017, 12:11:38 PM
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
<!DOCTYPE html>
<html lang="en">

    <head>

        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="description" content="">
        <meta name="author" content="">

        <title>CrowdSource-ServerHandler</title>

        <!-- Bootstrap Core CSS -->
        <link href="/serverplus/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

        <!-- MetisMenu CSS -->
        <link href="/serverplus/vendor/metisMenu/metisMenu.min.css" rel="stylesheet">

        <!-- Custom CSS -->
        <link href="/serverplus/dist/css/sb-admin-2.css" rel="stylesheet">

        <!-- Morris Charts CSS -->
        <link href="/serverplus/vendor/morrisjs/morris.css" rel="stylesheet">

        <!-- Custom Fonts -->
        <link href="/serverplus/vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">

        <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
        <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
        <!--[if lt IE 9]>
            <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
            <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
        <![endif]-->

        <script>
            function selectCheckBox() {
                if (document.getElementById("choose").checked == true) {
                    var list = document.getElementsByName("selected");
                    for (var i = 0; i < list.length; i++) {
                        list[i].checked = true;
                    }
                } else {
                    var list = document.getElementsByName("selected");
                    for (var i = 0; i < list.length; i++) {
                        list[i].checked = false;
                    }
                }
            }
        </script>

    </head>

    <body>

        <div id="wrapper">

            <!-- Navigation -->
            <nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand" href="frontpage.jsp">CrowdSource Application - SERVER</a>
                </div>
                <!-- /.navbar-header -->

                <ul class="nav navbar-top-links navbar-right">

                    <!-- /.dropdown -->
                    <li class="dropdown">
                        <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                            <i class="fa fa-user fa-fw"></i> <i class="fa fa-caret-down"></i>
                        </a>
                        <ul class="dropdown-menu dropdown-user">

                            <li class="divider"></li>
                            <li>

                                <a href="logout.jsp?logout=logout"><i class="fa fa-sign-out fa-fw"></i> Logout</a>
                            </li>
                        </ul>
                        <!-- /.dropdown-user -->
                    </li>
                    <!-- /.dropdown -->
                </ul>
                <!-- /.navbar-top-links -->

                <div class="navbar-default sidebar" role="navigation">
                    <div class="sidebar-nav navbar-collapse">
                        <ul class="nav" id="side-menu">


                            <li>
                                <a href="frontpage.jsp"><i class="fa fa-dashboard fa-fw"></i> Dashboard</a>
                            </li>

                            <!--<li>
                                <a href="addExperiment.jsp"><i class="fa fa-table fa-fw"></i> Add Experiment</a>
                            </li>-->
                            <!-- <li>
                                <a href="configParameters.jsp"><i class="fa fa-table fa-fw"></i> Configure</a> 
                            </li>-->

                            <li>
                                <a href="apchange.jsp"><i class="fa fa-table fa-fw"></i> Change AccessPoint</a>
                            </li>

                            <li>
                                <a href="sendControlFile.jsp"><i class="fa fa-table fa-fw"></i> Send Control File</a> 
                            </li>
                            <li>
                                <a href="viewControlFileDetails.jsp"><i class="fa fa-table fa-fw"></i> View Control File Details</a> 
                            </li>

                            <li>
                                <a href="startExp.jsp"><i class="fa fa-table fa-fw"></i> New Experiment</a> 
                            </li>

                            <li>
                                <a href="experimentDetails.jsp"><i class="fa fa-table fa-fw"></i> Experiment History</a>
                            </li>

                            <li>
                                <a href="getPendingLogFiles.jsp"><i class="fa fa-table fa-fw"></i> Request Log Files</a> 
                            </li>                             

                            <li>
                                <a href="userInfo.jsp"><i class="fa fa-table fa-fw"></i> User Information</a> 
                            </li>

                            <li>
                                <a href="updateApp.jsp"><i class="fa fa-table fa-fw"></i> Update Android Application</a> 
                            </li>

                            <li>
                                <a href="wakeupClients.jsp"><i class="fa fa-table fa-fw"></i> Wake Up !!!</a> 
                            </li>



                            <!--
   <li>
       <a href="configParameters.jsp"><i class="fa fa-table fa-fw"></i> Configure</a> 
   </li>
                            -->



                        </ul>
                    </div>
                    <!-- /.sidebar-collapse -->
                </div>
                <!-- /.navbar-static-side -->
            </nav>


            <%
    response.setIntHeader("refresh", 5);                 
            %>


            <div id="page-wrapper">

                <br/><br/><br/>
                <form  role="form" action='updateAppHandler.jsp' method='post'  onsubmit='return checkFields(this);'>





                    <div class="row">

                        <div class="form-group">
                            <input type='submit' class='btn btn-default' style="background-color:  #82e0aa  ;color: white;border-color: #82e0aa "  name='updateapps' value='Update Apps' onclick='return checkFields();' >                                                    
                        </div>


                        <div class="col-lg-12">
                            <div class="panel panel-primary">
                                <div class="panel-heading">
                                    <b> WiCroft - Android App Update Request</b>
                                </div>
                                <!-- /.panel-heading -->
                                <div class="panel-body">
                                    <table width="100%" class="table table-striped table-bordered table-hover" id="dataTables-example">




                                        <thead>
                                            <tr>
                                                <th  style="display: none">#</th>
                                                <th>Select <input id="choose"  checked type="checkbox" onchange="selectCheckBox(this);
                                                        "></th>
                                                <th>No.</th>
                                                <th>Mac Address</th>
                                                <th>Email</th>         
                                                <th>App Version</th>
                                                <th>Device Name</th>
                                                <th>Last HeartBeat</th>   
                                                <th>Status</th>   
                                            </tr>
                                        </thead>
                                        <tbody>

                                            <%
                                                   //   DBManager mgr =null;//= new DBManager();
                                                   ResultSet rs = DBManager.getAppUserInfo();
                                                   String path = Constants.experimentDetailsDirectory;
                                                   CopyOnWriteArrayList<DeviceInfo> activeClient = Utils.activeClients();
                                                   

                                                   if (rs != null) {
                                       //                out.write("<tableexperimentDetailsDirectory border='1'>");
                                       //                out.write("<tr><th>Exp No</th><th>Control File</th><th>Start Time</th><th>End Time</th><th>EXp Name</th><th>Exp Location</th><th>Description</th></tr>");
                                                       int count = 0;
                                                       while (rs.next()) {
                                                           count++;
                                                           
                                                           if(activeClient.contains(Constants.currentSession.getConnectedClients().get(rs.getString(1)))){
                                                                 out.write("<tr>"
                                                                   + "<td><input type='checkbox' name='selected'  value='"+rs.getString(1)+"' checked></td>"
                                                                   + "<td>" + Integer.toString(count) + "</td>"
                                                                   + "<td>" + rs.getString(1) + "</td>"
                                                                   + "<td>" + rs.getString(2) + "</td>"
                                                                   + "<td>" + rs.getString(3) + "</td>"
                                                                         
                                                                         + "<td>" + rs.getString(5) + "</td>"
                                                                   + "<td>" + rs.getString(4) + "</td>"
                                                                   + "<td style='color: green'>Active</td>"
                                                                   + "</tr>");
                                                           }else{
                                                                 out.write("<tr>"
                                                                   + "<td><input type='checkbox' name='selected'  value='"+rs.getString(1)+"' checked></td>"
                                                                   + "<td>" + Integer.toString(count) + "</td>"
                                                                   + "<td>" + rs.getString(1) + "</td>"
                                                                   + "<td>" + rs.getString(2) + "</td>"
                                                                   + "<td>" + rs.getString(3) + "</td>"
                                                                         + "<td>" + rs.getString(5) + "</td>"
                                                                   + "<td>" + rs.getString(4) + "</td>"
                                                                   + "<td style='color: red'>Passive</td>"
                                                                   + "</tr>");
                                                           }
                                                       }
                                       //                out.write("</table>");
                                                   }

                                                   // mgr.closeConnection();
                                            %>  

                                        </tbody>
                                    </table>
                                    <!-- /.table-responsive -->

                                </div>
                                <!-- /.panel-body -->
                            </div>
                            <!-- /.panel -->
                        </div>
                        <!-- /.col-lg-12 -->
                    </div>
                    <!--end Row -->

                </form>







            </div>
            <!-- /#page-wrapper -->

        </div>
        <!-- /#wrapper -->

        <!-- jQuery -->

        <script>
            function checkFields() {
                if (document.getElementsByName("selected").length <= 0) {
                    alert("No clients selected.");
                    return false;
                }
            }

        </script>
        <script src="/serverplus/vendor/jquery/jquery.min.js"></script>

        <!-- Bootstrap Core JavaScript -->
        <script src="/serverplus/vendor/bootstrap/js/bootstrap.min.js"></script>

        <!-- Metis Menu Plugin JavaScript -->
        <script src="/serverplus/vendor/metisMenu/metisMenu.min.js"></script>

        <!-- Morris Charts JavaScript -->
        <script src="/serverplus/vendor/raphael/raphael.min.js"></script>
        <script src="/serverplus/vendor/morrisjs/morris.min.js"></script>
        <script src="/serverplus/data/morris-data.js"></script>

        <!-- Custom Theme JavaScript -->
        <script src="/serverplus/dist/js/sb-admin-2.js"></script>
        <!-- DataTables JavaScript -->
        <script src="/serverplus/vendor/datatables/js/jquery.dataTables.min.js"></script>
        <script src="/serverplus/vendor/datatables-plugins/dataTables.bootstrap.min.js"></script>
        <script src="/serverplus/vendor/datatables-responsive/dataTables.responsive.js"></script>

        <!-- Custom Theme JavaScript -->
        <script src="/serverplus/dist/js/sb-admin-2.js"></script>

        <!-- Page-Level Demo Scripts - Tables - Use for reference -->
        <script>
            $(document).ready(function () {
                $('#dataTables-example').DataTable({
                    responsive: true
                });
            });
        </script>



    </body>

</html>
