import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.ServletException.*;

import java.util.logging.Level;
import java.util.logging.Logger;

public class CrearCuenta extends HttpServlet {

    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
    
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        
        out.println("<HTML><HEAD>");
        out.println("<TITLE>CrearCuenta</TITLE>");
        //out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"css/inicio.css\">");
        out.println("<link rel='shortcut icon' href='css/logo.jpg'></link>");
        out.println("</HEAD>");
        
        out.println("<BODY><CENTER>");
        out.println("<BR><BR>");
        out.println("<H2>REGISTRO</H2>");
        out.println("<BR>");
        
        out.println("<FORM METHOD=POST ACTION=Registro>");
        out.println("<LABEL for=usuario>Usuario:</LABEL><BR>");
        out.println("<INPUT TYPE=TEXT id=usuario NAME=usuario VALUE=\"\"><BR>");
        
        out.println("<LABEL for=correo>Correo electrónico:</LABEL><BR>");
        out.println("<INPUT TYPE=TEXT id=correo NAME=correo><BR>");
        
        out.println("<LABEL for=telefono>Teléfono:</LABEL><BR>");
        out.println("<INPUT TYPE=TEXT id=telefono NAME=telefono><BR>");
        
        out.println("<LABEL for=password>Contraseña:</LABEL><BR>");
        out.println("<INPUT TYPE=PASSWORD id=password NAME=password><BR>");
        
        out.println("<LABEL for=password>Repita su Contraseña:</LABEL><BR>");
        out.println("<INPUT TYPE=password id=reppassword NAME=reppassword><BR><BR>");
        
        out.println("<INPUT TYPE=SUBMIT VALUE='CREAR CUENTA'><BR><BR>");
        out.println("</FORM>");
        
        out.println("<div id='triangle'></div>"); //flecha de volver
        out.println("<FORM ACTION='Inicio' METHOD='GET'>");
        out.println("<BUTTON TYPE='SUBMIT'>Volver</BUTTON>");
        out.println("</FORM>");
        
        out.println("</BODY></HTML>");

        out.close();
    }
}