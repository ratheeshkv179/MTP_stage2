/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rkv.com;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ratheeshkv
 */
public class RequestHandler extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String method = request.getMethod();
        
        
        

        response.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {
            //  TODO output your page here. You may use following sample code. 

            out.write("<!DOCTYPE html>");
            out.write("<html>");
            out.write("<head>");
            out.write("<title>Servlet RequestHandler</title>");
            out.write("</head>");
            out.write("<body>");
            out.write("<h1>Servlet RequestHandler at " + request.getContextPath() + "</h1>");
            out.write("</body>");
            out.write("</html>");

        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * /* out.println("<!DOCTYPE html>"); out.println("<html>");
     * out.println("<head>"); out.println("<title>Servlet
     * RequestHandler</title>"); out.println("</head>"); out.println("<body>");
     * out.println("<h1>Servlet RequestHandler at " + request.getContextPath() +
     * "</h1>"); out.println("</body>"); out.println("</html>");
     */
    /* Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);

        //      System.out.println("\nRequest: "+request.getRequestURI());
        response.getWriter().print("\nRequest: " + request.getRequestURI());
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write("<!DOCTYPE html>"
                + "<html><body>"
                + "<p> My name is Ratheesh</p>"
                + "</body></html>");
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //   processRequest(request, response);
        response.getWriter().print("\nPOST Request: " + request.getRequestURI());
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
