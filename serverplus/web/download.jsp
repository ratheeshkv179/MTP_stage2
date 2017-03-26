<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.io.*,java.util.*, javax.servlet.*" %>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="javax.servlet.ServletOutputStream" %>
<%@ page import="org.apache.commons.io.*" %>
<%@ page import="java.net.URL" %>
<%@ page import="java.net.URLEncoder" %>
<%@ page import= "java.io.File" %>


<%
    // http://localhost:8080/serverplus/download.jsp?path=/home/cse/Downloads/Loadgenerator/controlFile/&expid=496&name=f4:f1:e1:51:47:ef
    // include file="checksession2.jsp" 
    String path = (String) request.getParameter("path");
    String fileid = (String) request.getParameter("fileid");
    String name = (String) request.getParameter("name");

    //out.write("File Name : " + path + "\n" + fileid + "\n" + name);
    String filename = path + "/" + fileid + "/" + name;

    response.setHeader("Content-Disposition", "attachment;filename=" + fileid + "-" + name);

    File file = new File(filename);
    FileInputStream fileIn = new FileInputStream(file);
    ServletOutputStream out1 = response.getOutputStream();

    IOUtils.copy(fileIn, out1);

    fileIn.close();
    out1.flush();
    out1.close();

    /*
        String username= (String)session.getAttribute("username");

        String fileid = (String)request.getParameter(Constants.getExpID());
        String download = (String)request.getParameter("download");
        if(fileid==null || download==null){
                System.out.println("download.jsp: request parameters are null");
                response.sendRedirect("index.jsp");
        }
        else{

                String filePath = Constants.getMainExpLogsDir()+fileid+"/";
                response.setContentType("application/octet-stream");

                if(download.equals("event")){
                        filePath = filePath+Constants.getEventFile();
                        System.out.println("fileid: " + fileid + ". download: " + download + ". filePath: "+filePath);
                        response.setHeader("Content-Disposition", "attachment;filename=Exp" + fileid + "_" + Constants.getEventFile());
                }
                else if(download.equals("log")){
                        String macAddress = (String)request.getParameter("file");
                        filePath = filePath + macAddress;
                        System.out.println(filePath);
                        System.out.println("fileid: " + fileid + ". download: " + download + "macAddress: " + macAddress +". filePath: "+filePath);
                        response.setHeader("Content-Disposition", "attachment;filename=Exp" + fileid + "_" 
                                                                + macAddress);
                }

                else if(download.equals("detail")){
                        String macAddress = (String)request.getParameter("file");
                        filePath = filePath + macAddress;
                        System.out.println(filePath);
                        System.out.println("fileid: " + fileid + ". download: " + download + "macAddress: " + macAddress +". filePath: "+filePath);
                        response.setHeader("Content-Disposition", "attachment;filename=Exp" + fileid + "_" 
                                                                + macAddress);
                }

                else if(download.equals("trace")){
                        String filename="";
                        File dir = new File(filePath);
                        File[] files = dir.listFiles();
                        for (File file : files) {
                        if (!file.isDirectory()) {
                            filename=file.getName();
                            if(filename.startsWith("trace")){
                                filePath = filePath + filename;
                                break;
                            }
                        }
                    }
                        System.out.println(filePath);
                        System.out.println("fileid: " + fileid + ". download: " + download +". filePath: "+filePath);
                        response.setHeader("Content-Disposition", "attachment;filename=Exp" + fileid + "_" 
                                                                + filename);
                }

                File file = new File(filePath);
                FileInputStream fileIn = new FileInputStream(file);
                ServletOutputStream out1 = response.getOutputStream();
		 
                IOUtils.copy(fileIn, out1);

                fileIn.close();
                out1.flush();
                out1.close();
        }*/
%>

