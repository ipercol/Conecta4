import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.ServletException.*;

public class Inicio extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();
        out.println("<HTML><HEAD>");
        out.println("<TITLE>Inicio</TITLE>");
        out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"css/inicio.css\">");

        out.println("</HEAD>");
        out.println("<BODY><CENTER>");
        out.println("<H1>CONECTA 4</H1><BR><BR>");
        out.println("<FORM METHOD=POST ACTION=Login>");
        
        out.println("<LABEL for=usuario>Usuario:</LABEL><BR>");
        out.println("<INPUT TYPE=TEXT id=usuario NAME=usuario VALUE=\"\"><BR>");
        
        out.println("<LABEL for=password>Contraseña:</LABEL><BR>");
        out.println("<INPUT TYPE=TEXT id=password NAME=password><BR><BR>");
        
        out.println("<INPUT TYPE=SUBMIT VALUE=LOGIN><BR>");
        out.println("</FORM>");
        
        out.println("<FORM METHOD=GET ACTION=Registro>");
        out.println("<INPUT TYPE=SUBMIT VALUE=REGISTRO>");
        out.println("</FORM>");
        
        out.println("</BODY></HTML>");

        out.close();
    }
}
