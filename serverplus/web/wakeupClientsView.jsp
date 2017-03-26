<%-- 
    Document   : wakeupClientsView
    Created on : 11 Mar, 2017, 1:46:32 AM
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

<%@page import="java.util.Date"%>

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





            <div id="page-wrapper">


                <div class="row">
                    <div class="col-lg-12">



                        <%
                            response.setIntHeader("refresh", 1);

                            long time = (Constants.currentSession.getWakeUpDuration()) - ((System.currentTimeMillis()) - Constants.currentSession.getStartwakeUpDuration()) / 1000;
                            long t = time;
                            //                    out.write("<br>"+time+"<br>")

                            long hour = 0, min = 0, sec = 0;
                            if (time >= 3600) {
                                hour = time / 3600;
                                time = time % 3600;
                            }

                            if (time >= 60) {
                                min = time / 60;
                                time = time % 60;
                            }

                            sec = time;

                            if (t > 0) {
                        %>

                        <br>
                        <br>
                        <div class="col-lg-3 col-md-6">
                            <div class="panel panel-danger">
                                <div class="panel-heading">
                                    <div class="row">
                                        <div class="col-xs-3">
                                            <i class="fa fa-clock-o fa-5x"></i>
                                        </div>
                                        <div class="col-xs-9 text-right">
                                            <div class="huge"><%=hour%>h &nbsp;<%=min%>m&nbsp; <%=sec%>s </div>
                                            <div>Current Wake Up Timer</div>
                                        </div>
                                    </div>
                                </div>
                                <a href="#">
                                    <div class="panel-footer">
                                        <!--                                    <span class="pull-left">View Details</span>
                                                                            <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span> -->
                                        <div class="clearfix"></div>
                                    </div>
                                </a>
                            </div>
                        </div>              

                        <%  } else {
                        %>
                        <br>
                        <br>
                        <div class="col-lg-3 col-md-6">
                            <div class="panel panel-danger">
                                <div class="panel-heading">
                                    <div class="row">
                                        <div class="col-xs-3">
                                            <i class="fa fa-clock-o fa-5x"></i>
                                        </div>
                                        <div class="col-xs-9 text-right">
                                            <!--<div class="huge"><%=hour%>h &nbsp;<%=min%>m&nbsp; <%=sec%>s </div>-->
                                            <div>Timer Expired!!!</div>
                                        </div>
                                    </div>
                                </div>
                                <a href="#">
                                    <div class="panel-footer">
                                        <!--                                    <span class="pull-left">View Details</span>
                                                                            <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span> -->
                                        <div class="clearfix"></div>
                                    </div>
                                </a>
                            </div>
                        </div>              


                        <%
                            }
                        %>

                        <br>
                        <br>
                        <a href="handleEvents.jsp?event=wakeUpStatusExit" class='btn btn-default' style="background-color: #e74c3c ;color: white;border-color:#e74c3c">Set New Timer</a>
                        <br>

                    </div>
                    <!-- /.col-lg-12 -->
                </div>

                <div class="row">
                    <br/>
                    
                      
                    <b>[Server Time &nbsp;:&nbsp; <%
                        Date date = new Date();
                        out.write(date.toString());
                    %> ]</b>
                        
                    
                    <br/><br/>
                    <div class="col-lg-12">
                        <div class="panel panel-primary">
                            <div class="panel-heading">
                                <b>Wake Up Request Status</b>
                            </div>
                            <!-- /.panel-heading -->
                            <div class="panel-body">
                                <!--<table width="100%" class="table table-striped table-bordered table-hover" id="dataTables-example">-->
                                <table width="100%" class="table table-striped table-bordered table-hover" >

                                    <thead>
                                        <tr>
                                            <th>#</th>
                                            <th>Mac Address</th>
                                            <th>Last HearBeat</th>
                                            <th>Running in Foreground</th>
                                            <th>Request Status</th>
                                        </tr>
                                    </thead>
                                    <tbody>

                                        <%
                                            ConcurrentHashMap<String, DeviceInfo> clients = Constants.currentSession.getConnectedClients();
                                            int count = 0;
                                            Enumeration<String> macList = Constants.currentSession.getWakeUpDevicesStatus().keys();
                                            while (macList.hasMoreElements()) {
                                                count++;
                                                String mac = macList.nextElement();
                                                DeviceInfo device = clients.get(mac);
                                                String status = Constants.currentSession.getWakeUpDevicesStatus().get(mac);
                                                out.write("<tr><td>" + count + "</td><td>" + mac + "</td><td>" + device.getLastHeartBeatTime() + "</td><td>"+    (device.isInForeground()?"<b style='color:blue'>True</b>":"<b>False</b>")  + "</td><td>" + status + "</td></tr>");
                                            }
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









            </div>
            <!-- /#page-wrapper -->

        </div>
        <!-- /#wrapper -->

        <!-- jQuery -->
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
