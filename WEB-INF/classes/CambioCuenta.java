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
        out.println("<TITLE>CambioCuenta</TITLE>");
        //out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"css/inicio.css\">");
        out.println("<link rel='shortcut icon' href='css/logo.jpg'></link>");
        out.println("</HEAD>");
        
        out.println("<BODY><CENTER>");
        out.println("<BR><BR>");
        out.println("<H1>Cambio de Contraseña</H1>");
        
        out.println("<H4><FORM METHOD=POST ACTION=EditarPerfil>");
        
        out.println("<LABEL for=actualPassword>Contraseña anterior:</LABEL><BR>");
        out.println("<INPUT TYPE=PASSWORD id=actualPassword NAME=actualPassword VALUE=\"\"><BR><BR>");
        
        out.println("<LABEL for=newPassword>Nueva contraseña:</LABEL><BR>");
        out.println("<INPUT TYPE=PASSWORD id=newPassword NAME=newPassword VALUE=\"\"><BR><BR>");
        
        out.println("<LABEL for=newPassword2>Confirmar contraseña:</LABEL><BR>");
        out.println("<INPUT TYPE=PASSWORD id=newPassword2 NAME=repnewPassword VALUE=\"\"><BR><BR>");
        
        out.println("<INPUT TYPE=SUBMIT VALUE='ACEPTAR'><BR>");
        out.println("</FORM><H4>");
        
        out.println("<div id='triangle'></div>"); //flecha de volver
        out.println("<FORM ACTION='Interfaz' METHOD='POST'>");
        out.println("<BUTTON TYPE='SUBMIT'>Volver</BUTTON>");
        out.println("</FORM>");
        out.println("</BODY></HTML>");

        out.close();
    }
}