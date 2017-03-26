/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author cse
 */
@WebServlet(urlPatterns = {"/bgtraffic"})
public class bgtraffic extends HttpServlet {

    static int index = -1;
    double UrlResponse[] = new double[]{795.0, 178.0, 532.0, 1430.0, 217.0, 463.0, 423.0, 72.0, 280.0, 188.0, 502.0, 1006.0, 113.0, 884.0, 227.0, 82.0, 42.0, 1125.0, 198.0, 158.0, 923.0, 306.0, 1096.0, 740.0, 123.0, 894.0, 608.0, 237.0, 277.0, 92.0, 631.0, 1135.0, 539.0, 933.0, 1254.0, 710.0, 790.0, 79.0, 287.0, 62.0, 1185.0, 641.0, 1487.0, 326.0, 760.0, 49.0, 143.0, 103.0, 668.0, 628.0, 1412.0, 405.0, 1155.0, 691.0, 559.0, 1343.0, 262.0, 336.0, 1160.0, 1120.0, 770.0, 99.0, 153.0, 193.0, 209.0, 569.0, 272.0, 688.0, 1472.0, 351.0, 219.0, 465.0, 242.0, 396.0, 915.0, 544.0, 1402.0, 1442.0, 115.0, 361.0, 229.0, 475.0, 84.0, 623.0, 292.0, 252.0, 400.0, 1018.0, 308.0, 331.0, 125.0, 856.0, 896.0, 239.0, 54.0, 94.0, 410.0, 490.0, 450.0, 1479.0, 358.0, 1462.0, 712.0, 381.0, 175.0, 64.0, 1147.0, 460.0, 214.0, 905.0, 328.0, 1112.0, 516.0, 105.0, 185.0, 1003.0, 447.0, 1197.0, 841.0, 264.0, 470.0, 955.0, 1236.0, 155.0, 303.0, 1093.0, 828.0, 457.0, 234.0, 274.0, 799.0, 388.0, 1132.0, 576.0, 930.0, 427.0, 467.0, 76.0, 130.0, 170.0, 284.0, 729.0, 769.0, 398.0, 1296.0, 940.0, 900.0, 117.0, 363.0, 888.0, 848.0, 808.0, 511.0, 46.0, 180.0, 625.0, 1375.0, 254.0, 294.0, 402.0, 927.0, 596.0, 333.0, 127.0, 56.0, 150.0, 110.0, 1139.0, 492.0, 566.0, 1218.0, 1464.0, 1281.0, 1035.0, 571.0, 66.0, 1149.0, 439.0, 833.0, 462.0, 947.0, 907.0, 71.0, 1114.0, 1268.0, 107.0, 187.0, 541.0, 449.0, 112.0, 1387.0, 266.0, 81.0, 1124.0, 922.0, 591.0, 1015.0, 1095.0, 345.0, 459.0, 122.0, 607.0, 1020.0, 51.0, 91.0, 1134.0, 578.0, 167.0, 201.0, 315.0, 1065.0, 78.0, 863.0, 286.0, 61.0, 680.0, 640.0, 508.0, 211.0, 902.0, 982.0, 365.0, 325.0, 119.0, 479.0, 88.0, 182.0, 142.0, 1377.0, 667.0, 627.0, 296.0, 404.0, 444.0, 929.0, 1302.0, 746.0, 335.0, 523.0, 98.0, 58.0, 300.0, 454.0, 414.0, 208.0, 568.0, 1392.0, 716.0, 139.0, 385.0, 179.0, 1283.0, 573.0, 1471.0, 350.0, 310.0, 464.0, 218.0, 73.0, 652.0, 766.0, 726.0, 149.0, 395.0, 189.0, 1007.0, 1087.0, 543.0, 320.0, 845.0, 885.0, 999.0, 959.0, 622.0, 251.0, 159.0, 199.0, 347.0, 307.0, 1097.0, 124.0, 370.0, 330.0, 895.0, 238.0, 93.0, 672.0, 632.0, 203.0, 563.0, 1067.0, 751.0, 1255.0, 174.0, 619.0, 865.0, 1072.0, 63.0, 830.0, 1488.0, 1111.0, 144.0, 104.0, 1379.0, 669.0, 298.0, 406.0, 800.0, 223.0, 748.0, 1275.0, 154.0, 194.0, 639.0, 1012.0, 302.0, 827.0, 416.0, 456.0, 1108.0, 810.0, 233.0, 273.0, 1502.0, 1131.0, 1039.0, 575.0, 649.0, 312.0, 352.0, 75.0, 243.0, 768.0, 397.0, 431.0, 585.0, 1443.0, 322.0, 116.0, 362.0, 847.0, 476.0, 85.0, 624.0, 870.0, 1128.0, 664.0, 253.0, 738.0, 1099.0, 309.0, 349.0, 332.0, 817.0, 857.0, 95.0, 1178.0, 788.0, 411.0, 451.0, 1069.0, 359.0, 399.0, 176.0, 438.0, 421.0, 106.0, 186.0, 146.0, 877.0, 911.0, 580.0, 1044.0, 408.0, 111.0, 842.0, 80.0, 379.0, 1123.0, 527.0, 344.0, 304.0, 418.0, 1396.0, 235.0, 90.0, 389.0, 1173.0, 200.0, 1475.0, 354.0, 314.0, 560.0, 428.0, 171.0, 131.0, 285.0, 245.0, 1297.0, 507.0, 118.0, 324.0, 849.0, 87.0, 101.0, 295.0, 597.0, 260.0, 220.0, 991.0, 705.0, 374.0, 859.0, 97.0, 57.0, 151.0, 191.0, 636.0, 1420.0, 207.0, 453.0, 1311.0};

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
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet bgTraffic</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet bgTraffic at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);

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
//        processRequest(request, response);

        String msg = "";

        String top = "<!DOCTYPE html><html><head><meta charset=\"UTF-8\">\n"
                + "<link rel=\"icon\" type=\"image/x-icon\" href=\"data:image/x-icon\"></head>\n"
                + "<body>";
        String bottom = "</body></html>";

        index++;
        index = (index) % UrlResponse.length;

        int size = top.length() + bottom.length();
        if (UrlResponse[index] <= size) {
            msg = top + bottom;
        } else {
            String data = "";
            for (int i = 0; i < (UrlResponse[index] - size); i++) {
                data += "A";
            }
            msg = top + data + bottom;
        }

        
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(new BufferedWriter(new FileWriter("/home/cse/Downloads/Loadgenerator/ServerFiles/httpgetpost/url-response-pkt-size.txt", true)));
            writer.println("Sent data : "+msg.length());
//            writer.println("The second line");
            writer.close();
        } catch (FileNotFoundException ex) {
            System.out.println("FileNotFoundException : " + ex.toString());
        } catch (UnsupportedEncodingException ex) {
            System.out.println("UnsupportedEncodingException : " + ex.toString());
        }
        
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println(msg);

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
