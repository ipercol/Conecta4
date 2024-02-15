import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class Tablero extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        
        res.setContentType("text/html;charset=UTF-8");
        PrintWriter out = res.getWriter();
        // Obtener información de la sesión
        
        

        // Generar el tablero
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Conecta4 - Tablero</title>");
        out.println("</head>");

        out.println("<body><CENTER>");
        out.println("<BR><BR><BR><BR><BR>");


        // Imprimir tablero
        out.println("<TABLE BORDER=\"1\">");
        for (int i = 0; i < 6; i++) {
            out.println("<TR>");
            for (int j = 0; j < 7; j++) {
                out.println("<TD WIDTH=\"50\" HEIGHT=\"50\"></TD>");
            }
            out.println("</TR>");
        }
        out.println("</TABLE>");

        out.println("<form METHOD = 'POST' ACTION = 'Interfaz'>");
        out.println("<input type=\"radio\" name=\"myRadio\" value=\"1\">C1");
        out.println("<input type=\"radio\" name=\"myRadio\" value=\"2\">C2");
        out.println("<input type=\"radio\" name=\"myRadio\" value=\"3\">C3");
        out.println("<input type=\"radio\" name=\"myRadio\" value=\"3\">C4");
        out.println("<input type=\"radio\" name=\"myRadio\" value=\"3\">C5");
        out.println("<input type=\"radio\" name=\"myRadio\" value=\"3\">C6");
        out.println("<input type=\"radio\" name=\"myRadio\" value=\"3\">C7<BR>");
        out.println("<input type=\"submit\" value=\"Submit\">");
        out.println("</form>");
        out.println("</form>");

        out.println("</body>");
        out.println("</html>");

        out.close();
     }
}