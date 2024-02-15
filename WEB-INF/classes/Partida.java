import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class Partida extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        
        res.setContentType("text/html;charset=UTF-8");
        PrintWriter out = res.getWriter();
        out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"css/inicio.css\">");
        // Obtener información de la sesión
        
        

        // Generar el tablero
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Conecta4 - Tablero</title>");
        out.println("</head>");

        out.println("<body><CENTER>");
        out.println("<BR><BR><BR><BR><BR>");


        // Imprimir tablero
        out.println("<table border=\"1\">");
        for (int i = 0; i < 6; i++) {
            out.println("<tr>");
            for (int j = 0; j < 7; j++) {
                out.println("<td width=\"50\" height=\"50\"></td>");
            }
            out.println("</tr>");
        }
        out.println("</table>");

        out.println("<form METHOD = 'POST' ACTION = 'Interfaz'>");
        out.println("<input type=\"checkbox\" name=\"myCheckbox\" value=\"1\">");
        out.println("<input type=\"submit\" value=\"Submit\">");
        out.println("</form>");
        out.println("</form>");

        out.println("</body>");
        out.println("</html>");

        out.close();
    }
}
