<%-- 
    Document   : index
    Created on : 12 Jul, 2016, 10:12:40 PM
    Author     : ratheeshkv
--%>

<%@page import="com.iitb.cse.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>CrowdSource</title>
        <link rel="stylesheet" href="/serverplus/css/login.css">
        <link type="text/css" rel="stylesheet" href="./css/frontPage.css" />
    </head>
    <body>


        <%
            if (session.getAttribute("currentUser") == null) {

                response.sendRedirect("login.jsp");

            } else {

                final String _session = session.getAttribute("currentUser").toString();

                // start Listen for connection
                Runnable run = new Runnable() {
                    @Override
                    public void run() {
                        // Session sessn = new Session(_session);
                        ClientConnection.startlistenForClients(Constants.currentSession);
                    }
                };

                Thread t = new Thread(run);
                t.start();

                /*
                while (!Constants.listenOnPort) {
                    Thread.sleep(1000);
                }
                 */
                // out.print("hello!!!");
                response.sendRedirect("frontpage.jsp");

        %>












        <p>
            Server started
        </p>

        <%                    }
        %>

    </body>
</html>
