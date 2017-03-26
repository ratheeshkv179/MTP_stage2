<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.io.*,java.util.*, javax.servlet.*" %>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="org.apache.commons.fileupload.*" %>
<%@ page import="org.apache.commons.fileupload.disk.*" %>
<%@ page import="org.apache.commons.fileupload.servlet.*" %>
<%@ page import="org.apache.commons.io.output.*" %>
<%@ page import="com.iitb.cse.*" %>

<%

    String expID = null, macAddress = null;

    File file;
    int maxFileSize = 100 * 1024 * 1024;
    int maxMemSize = 100 * 1024 * 1024;
    String filePath = Constants.experimentDetailsDirectory;

    String expDir = "";
    // Verify the content type
    String contentType = request.getContentType();
    if ((contentType != null) && (contentType.indexOf("multipart/form-data") >= 0)) {

        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setSizeThreshold(100 * maxFileSize);

//        factory.setRepository(100 * maxFileSize);
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setSizeMax(maxFileSize);
        try {
            List fileItems = upload.parseRequest(request);

            Iterator i = fileItems.iterator();

            while (i.hasNext()) {

                FileItem fi = (FileItem) i.next();
                if (fi.isFormField()) {

                    String fieldName = fi.getFieldName();
                    String fieldValue = fi.getString();
                    System.out.println("File Upload :=>  Field name: " + fieldName + ", Field value: " + fieldValue);

                    if (fieldName.equals(Constants.getExpID())) {
                        expID = fieldValue;
                    } else if (fieldName.equals(Constants.getMacAddress())) {
                        macAddress = fieldValue;
                    }
                }
            }

            if (expID == null || macAddress == null) {
                System.out.println("File Upload :=> Error while getting parameters");
            } else {

                System.out.println("Hahahaha");

                if (expID.equals("Debug_logs")) {
                    expID = expID + "/NEW";
                }

                if (filePath.endsWith("/")) {
                    filePath = filePath + expID + "/";
                } else {
                    filePath = filePath + "/" + expID + "/";
                }
                
                expDir = filePath;
                File theDir = new File(filePath);
                if (!theDir.exists()) {
                    theDir.mkdir();
                }
                
                System.out.println("Location : "+theDir.getAbsolutePath());
                System.out.println("Exp ID  : "+expID);
                System.out.println("MacAddress : "+macAddress);

                i = fileItems.iterator();

                String fileName = "";

                while (i.hasNext()) {
                    FileItem fi = (FileItem) i.next();
                    if (!fi.isFormField()) {
                        fileName = macAddress;
                        boolean isInMemory = fi.isInMemory();
                        long sizeInBytes = fi.getSize();
                        if (fileName.lastIndexOf("\\") >= 0) {
                            file = new File(filePath
                                    + fileName.substring(fileName.lastIndexOf("\\")));
                        } else {
                            file = new File(filePath
                                    + fileName.substring(fileName.lastIndexOf("\\") + 1));
                        }
                        fi.write(file);
                    }
                }
                System.out.println("HAI");
                
                
                if (!expID.contains("Debug_logs")) {

                    DeviceInfo device = Constants.currentSession.getGetLogFilefFilteredDevices().get(macAddress);
                    System.out.println("Device : "+device);
                    
                    
                    device.setLogFileReceived(true);
                    String details = device.getDetails();
                    details += " , Received [" + expID + "]";
                    System.out.println("\nDetails : " + details);
                    device.setDetails(details);

                    if (DBManager.updateFileReceivedField(Integer.parseInt(expID), macAddress)) {
                        System.out.println("File Upload :=> " + expID + " Successfully");
                    } else {
                        response.setStatus(response.SC_REQUEST_URI_TOO_LONG);
                        System.out.println("File Upload :=>  Failed");
                    }

                } else {

                    String mac[] = macAddress.split("_");
                    DeviceInfo device = Constants.currentSession.getGetLogFilefFilteredDevices().get(mac[0]);
                    String details = device.getDetails();
                    details += " , Received [" + expID + "]";
                    System.out.println("\nDetails : " + details);
                    device.setDetails(details);
                    System.out.println("File Upload :=> " + expID + "(debug) " + macAddress + " Successfully");
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setStatus(response.SC_REQUEST_URI_TOO_LONG);
            
            System.out.println(ex);
        }

    }


%>

