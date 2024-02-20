import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.ServletException.*;

public class Inicio extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        
        PrintWriter out = res.getWriter();
        res.setContentType("text/html");
        
        out.println("<HTML><HEAD>");
        out.println("<TITLE>Inicio</TITLE>");
        out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"css/inicio.css\">");
        out.println("<link rel='shortcut icon' href='css/logo.jpg'></link>");
        out.println("</HEAD>");
        
        out.println("<BR>");
        out.println("<BODY><CENTER>");
        out.println("<H1>CONECTA 4444</H1>");
        out.println("<H4><FORM METHOD=POST ACTION=Login>");
        
        out.println("<LABEL for=usuario>Usuario:</LABEL><BR>");
        out.println("<INPUT TYPE=TEXT id=usuario NAME=usuario VALUE=\"\"><BR>");
        
        out.println("<LABEL for=password>Contraseña:</LABEL><BR>");
        out.println("<INPUT TYPE=PASSWORD id=password NAME=password><BR><BR>");
        
        out.println("<INPUT id='login' TYPE=SUBMIT VALUE=LOGIN><BR>");
        out.println("</FORM>");
        
        out.println("<FORM METHOD=POST ACTION=CrearCuenta>");
        out.println("<INPUT id='registro' TYPE=SUBMIT VALUE=REGISTRARSE>");
        out.println("</FORM><H4>");
        
        out.println("</CENTER></BODY></HTML>");

        out.close();
    }
}
