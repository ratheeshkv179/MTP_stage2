<%-- 
    Document   : newjsp
    Created on : 26 Jun, 2016, 10:43:51 AM
    Author     : ratheeshkv
--%>

<%@page import="java.io.File"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%
            
            
            request.getRequestURI();
            
            String path = System.getProperty("user.dir");
            out.println("\n" + path);
            path = path.replaceAll("/bin", "/webapps/HttpGetPost/");
            out.println("\n" + path);

            File file = new File(path);
            File f = new File(path + "add-log");
            if (file.exists()) {
                out.print(f.createNewFile());
            }


        %>
    </body>
</html>
