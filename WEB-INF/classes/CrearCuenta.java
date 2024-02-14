import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.ServletException.*;

public class CrearCuenta extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        res.setContentType("text/html;charset=UTF-8");

        PrintWriter out = res.getWriter();
        out.println("<HTML><HEAD>");
        out.println("<TITLE>CrearCuenta</TITLE>");
        out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"css/inicio.css\">");

        out.println("</HEAD>");
        out.println("<BODY><CENTER>");
        out.println("<BR><BR>");
        out.println("<H1>CONECTA 4</H1>");
        out.println("<FORM METHOD=POST ACTION=Registro>");
        
        out.println("<LABEL for=usuario>Usuario:</LABEL><BR>");
        out.println("<INPUT TYPE=TEXT id=usuario NAME=usuario VALUE=\"\"><BR>");
        
        out.println("<LABEL for=password>Contraseña:</LABEL><BR>");
        out.println("<INPUT TYPE=TEXT id=password NAME=password><BR><BR>");
        
        out.println("<INPUT TYPE=SUBMIT VALUE='CREAR CUENTA'><BR>");
        out.println("</FORM>");
        
        out.println("</BODY></HTML>");

        out.close();
    }
}