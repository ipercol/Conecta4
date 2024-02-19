import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.ServletException.*;
public class CambioCuenta extends HttpServlet {

    public void doPost(HttpServletRequest req, HttpServletResponse res)
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
        out.println("<FORM METHOD=POST ACTION=EditarPerfil>");
        
        out.println("<LABEL for=newPassword>Nueva password:</LABEL><BR>");
        out.println("<INPUT TYPE=PASSWORD id=newPassword NAME=newPassword><BR><BR>");
        
        out.println("<LABEL for=newPassword2>Confirmar nueva password:</LABEL><BR>");
        out.println("<INPUT TYPE=PASSWORD id=newPassword2 NAME=newPassword2><BR><BR>");
        
        out.println("<INPUT TYPE=SUBMIT VALUE='Cambio Datos'><BR>");
        out.println("</FORM>");
        
        out.println("</BODY></HTML>");

        out.close();
    }
}