<%-- 
    Document   : sendControlFileStatus
    Created on : 17 Jan, 2017, 9:23:16 PM
    Author     : cse
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
            
            
            // response.setIntHeader("refresh", 5); 
            
            String file_name = "dummy.txt";
            String file_id = "-1";
            String file_info = "";
            
            //out.write("<h2>Experiment No. "+Constants.currentSession.getCurrentExperimentId()+"</h2>");
            
                String exp_name = null;
                String exp_loc = null;
                String exp_desc = "";
                Vector<String> selectedClients = new Vector<String>();
                int exp_number = 0;
                String timeout = null;
                String logBgTraffic = null;
                String newOrOld = "";
                String numclients = "1";
                String duration = "10";

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

                            
                            if (item.getFieldName().equals("newOrOld")) {
                                newOrOld = item.getString();
                            } else if(item.getFieldName().equals("numclients")){
                                numclients = item.getString();
                            } else if(item.getFieldName().equals("duration")){
                                duration =  item.getString();
                            }
                            else if (item.getFieldName().equals("file_info")) {
                                file_info = item.getString();                            
                            } else if (item.getFieldName().equals("file_id")) {
                                file_id = item.getString();
                            } else if (item.getFieldName().equals("file_name")) {
                                file_name = item.getString();
                            } else if (item.getFieldName().equals("exp_name")) {
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
                            } else if (item.getFieldName().equals("timeout")) {
                                timeout =  item.getString();
                            } else if (item.getFieldName().equals("bglog")) {
                                logBgTraffic =  item.getString();
                            }
                            
                            
                            
                            
                            
                            //out.write("Location  : " + item.getString("exp_loc"));
                            //out.write("Desc  : " + item.getString("exp_desc"));
                            //out.write("Type : " + item.getString("filter"));
                        } else {
                            
                            out.write("<br>Location  : ");

                            if (Constants.experimentDetailsDirectory.endsWith("/")) {
                                file = new File(Constants.experimentDetailsDirectory + Constants.controlFile+"/"+file_id);
                            } else {
                                file = new File(Constants.experimentDetailsDirectory + "/" + Constants.controlFile+"/"+file_id);
                            }

                            if (!file.exists()) {
                                file.mkdirs();
                            }
                            
                            if(!file_name.endsWith(".txt")){
                                file_name = file_name+".txt";            
                            }       

                            if (item.getName() != null) {
                                //       out.write("\nFile : " + file.getAbsolutePath() + "  -- " + item.getName());
                                //file = new File(file.getAbsolutePath() + "/" + item.getName());
                                file = new File(file.getAbsolutePath() + "/"+file_name);                                
                                item.write(file);
                            }
                        }
                    }
                } catch (FileUploadException ex) {
                    out.write(ex.toString());
                } catch (Exception ex) {
                    out.write(ex.toString());
                }

                
                
                if(newOrOld.equalsIgnoreCase("selectNewFile")){
                    DBManager.addControlFileInfo(file_name,exp_desc); ;
                }
                
                
                
                
                        
               
/*                
                Experiment experiment = new Experiment(exp_number, exp_name, exp_loc, exp_desc);
                //  if (DBManager.addExperiment(exp_name, exp_loc, exp_desc)) {
                if (DBManager.addExperiment(experiment)) {
                    out.write("<br/>DB Succss : Experiment added");
                } else {
                    out.write("<br/>DB Failed : Adding Experiment Failed");
                }
*/
                
                
                Constants.currentSession.getFilteredClients().clear();
                //Constants.currentSession.getControlFileReceviedClients().clear();                
                Constants.currentSession.getControlFileSendingSuccessClients().clear();                        
                Constants.currentSession.getControlFileSendingFailedClients().clear();
                Constants.currentSession.getControlFileSendingStatus().clear();

                if (selectedClients != null) {
                    for (int i = 0; i < selectedClients.size(); i++) {
                        out.write("<br/> Client " + (i + 1) + selectedClients.get(i));
                        Constants.currentSession.getFilteredClients().add(Constants.currentSession.getConnectedClients().get(selectedClients.get(i)));
                    }}
                 else if (session.getAttribute("filter").equals("random")) {
                    Enumeration<String> mac_list = Constants.currentSession.getConnectedClients().keys();
                    int i = 0;
                    while (mac_list.hasMoreElements() && i < (Integer) session.getAttribute("clientcount")) {
                        Constants.currentSession.getFilteredClients().add(Constants.currentSession.getConnectedClients().get(mac_list.nextElement()));
                        i++;
                    }
                }
            

                out.write("<br>newOrOld :"+newOrOld);
                out.write("<br>file_info : "+file_info);
                if(newOrOld.equalsIgnoreCase("selectOldFile")){
                    String [] file1 = file_info.split("#");
                    file_id = file1[0];
                    file_name = file1[1];
                }
                
                Constants.currentSession.setRetryControlFileId(file_id);
                Constants.currentSession.setRetryControlFileName(file_name);
                
                Constants.currentSession.setSendingControlFile(true);   
                Utils.sendControlFile(file_id,file_name,newOrOld,numclients,duration);
                response.sendRedirect("controlFileStatus.jsp");
               
                
                //Utils.startExperiment(experiment.getNumber());
                
                //out.write("\n1"+experiment.getNumber());
                //out.write("\n2"+timeout);
                //out.write("\n3"+logBgTraffic);
                
/*                if(Utils.startExperiment(experiment.getNumber(),timeout,logBgTraffic)){
                    
                    Constants.currentSession.setExperimentRunning(true);
                    Constants.currentSession.setCurExperiment(experiment);
                    response.sendRedirect("experimentStatus.jsp");
*/
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
                    
/*                }else{
                    out.write("<h2>Starting Experiment Failed</h2>");
                     response.sendRedirect("failedExperiment.jsp");
                   // Constants.currentSession.setExperimentRunning(false);
                }
*/

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

