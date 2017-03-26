<%-- 
    Document   : addExperimentStatus
    Created on : 24 Jul, 2016, 12:36:50 AM
    Author     : ratheeshkv
--%>
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
        <title>CrowdSource</title>
        <link rel="stylesheet" href="/serverplus/css/table.css">
    </head>
    <body>


        <%
            
            
            response.setIntHeader("refresh", 5); 
            
            out.write("<h2>Experiment No. "+Constants.currentSession.getCurrentExperimentId()+"</h2>");
            
                String exp_name = null;
                String exp_loc = null;
                String exp_desc = null;
                Vector<String> selectedClients = new Vector<String>();
                int exp_number = 0;
                String timeout = null;
                String logBgTraffic = null;

                /* 
            out.write("Name : "+request.getParameter("exp_name"));
            out.write("Location  : "+request.getParameter("exp_loc"));
            out.write("Desc  : "+request.getParameter("exp_desc"));
            out.write("Type : "+request.getParameter("filter"));
                 */
                boolean multipart = ServletFileUpload.isMultipartContent(request);
                DiskFileItemFactory factory = new DiskFileItemFactory();
                factory.setSizeThreshold(50 * 1024 * 1024);
                File file;//= new File("/home/cse/Desktop/");
                //   factory.setRepository(file);

                ServletFileUpload upload = new ServletFileUpload(factory);
                upload.setSizeMax(50 * 1024 * 1024);

                try {
                    List fileitem = upload.parseRequest(request);
                    Iterator itr = fileitem.iterator();
                    int index = 0;
                    while (itr.hasNext()) {

                        // out.write("<br/>Index : "+(++index));
                        FileItem item = (FileItem) itr.next();
                        //  out.write("<br/>Name : " + item.getName() + " " + item.isFormField());

//                    item.getString(string)
                        if (item.isFormField()) {
                            //     out.write("Name : '" + item.getFieldName() + "'-'" + item.getName() + "' - '" + item.getString());

                            out.write("<br/>Name : " + item.getFieldName());

                            if (item.getFieldName().equals("exp_name")) {
                                exp_name = item.getString();
                            } else if (item.getFieldName().equals("exp_loc")) {
                                exp_loc = item.getString();
                            } else if (item.getFieldName().equals("exp_desc")) {
                                exp_desc = item.getString();
                            } else if (item.getFieldName().equals("selectedclient")) {
                                selectedClients.add(item.getString());
                                index++;
                            } else if (item.getFieldName().equals("exp_number")) {
                                exp_number = Integer.parseInt(item.getString());
                            }else if (item.getFieldName().equals("timeout")) {
                                timeout =  item.getString();
                            }else if (item.getFieldName().equals("bglog")) {
                                logBgTraffic =  item.getString();
                            }
                            
                            
                            
                            
                            
                            //out.write("Location  : " + item.getString("exp_loc"));
                            //out.write("Desc  : " + item.getString("exp_desc"));
                            //out.write("Type : " + item.getString("filter"));
                        } else {

                            if (Constants.experimentDetailsDirectory.endsWith("/")) {
                                file = new File(Constants.experimentDetailsDirectory + Constants.currentSession.getCurrentExperimentId());
                            } else {
                                file = new File(Constants.experimentDetailsDirectory + "/" + Constants.currentSession.getCurrentExperimentId());
                            }

                            if (!file.exists()) {
                                file.mkdirs();
                            }

                            if (item.getName() != null) {
                                //       out.write("\nFile : " + file.getAbsolutePath() + "  -- " + item.getName());
                                //file = new File(file.getAbsolutePath() + "/" + item.getName());
                                file = new File(file.getAbsolutePath() + "/"+Constants.configFile);
                                item.write(file);
                                //    break;
                            }
                        }
                    }
                } catch (FileUploadException ex) {
                    out.write(ex.toString());
                } catch (Exception ex) {
                    out.write(ex.toString());
                }

                Experiment experiment = new Experiment(exp_number, exp_name, exp_loc, exp_desc);

                //  if (DBManager.addExperiment(exp_name, exp_loc, exp_desc)) {
                if (DBManager.addExperiment(experiment)) {
                    out.write("<br/>DB Succss : Experiment added");
                } else {
                    out.write("<br/>DB Failed : Adding Experiment Failed");
                }

                Constants.currentSession.getFilteredClients().clear();

                if (selectedClients != null) {
                    for (int i = 0; i < selectedClients.size(); i++) {
                        out.write("<br/> Client " + (i + 1) + selectedClients.get(i));
                        Constants.currentSession.getFilteredClients().add(Constants.currentSession.getConnectedClients().get(selectedClients.get(i)));
                    }
                } else if (session.getAttribute("filter").equals("random")) {
                    Enumeration<String> mac_list = Constants.currentSession.getConnectedClients().keys();
                    int i = 0;
                    while (mac_list.hasMoreElements() && i < (Integer) session.getAttribute("clientcount")) {
                        Constants.currentSession.getFilteredClients().add(Constants.currentSession.getConnectedClients().get(mac_list.nextElement()));
                        i++;
                    }
                }
            
                
                //Utils.startExperiment(experiment.getNumber());
                
                out.write("\n1"+experiment.getNumber());
                out.write("\n2"+timeout);
                out.write("\n3"+logBgTraffic);
                
                if(Utils.startExperiment(experiment.getNumber(),timeout,logBgTraffic)){
                    
                    Constants.currentSession.setExperimentRunning(true);
                    Constants.currentSession.setCurExperiment(experiment);
                    response.sendRedirect("experimentStatus.jsp");

                  /* DBManager mgr = new DBManager();
                   ResultSet    rs = DBManager.getControlFileStatus(mgr, experiment.getNumber());
                   int total  = Constants.currentSession.getFilteredClients().size();
                   int success = 0;
                   int failed = 0 ;
                   int pending = 0;
                   
                       if (rs != null) {
                           
                           while(rs.next()){
                               
                               if(rs.getString(1) == "1"){
                                   success = Integer.parseInt(rs.getString(2));
                               }else if(rs.getString(1) == "2"){
                                   failed = Integer.parseInt(rs.getString(2));
                               }
                           }
                       }
                    mgr.closeConnection();
                    pending = total - (success + failed); 
                    out.write("<table>");
                    out.write("<caption>Experiment Details</caption>");
                    out.write("<tr><td>Number of Selected Clients</td><td>"+total+"</td></tr>");
                    out.write("<tr><td>Control File Sending Success</td><td>"+ success +"</td></tr>");
                    out.write("<tr><td>Control File Sending Pending</td><td>"+ pending +"</td></tr>");
                    out.write("<tr><td>Control File Sending Failed</td><td>"+ failed +"</td></tr>");
                    out.write("</table>");*/
                    
                }else{
                    out.write("<h2>Starting Experiment Failed</h2>");
                     response.sendRedirect("failedExperiment.jsp");
                   // Constants.currentSession.setExperimentRunning(false);
                }

            // clientcount
            /*String arr[] = request.getParameterValues("selectedclient");
            for (int i = 0; i < arr.length; i++) {
                out.write("\nSelected : " + arr[i]);
            }*/
//            Utils.startExperiment();

 //           Utils.startRandomExperiment();


        %>
    </body>
</html>

