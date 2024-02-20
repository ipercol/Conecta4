import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Perfil extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
    Connection con;
    Statement st;
    ResultSet rs;
    String SQL;
    HttpSession sesion = req.getSession();
        try{
            
            try {
                    Class.forName("com.mysql.jdbc.Driver");
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(CrearCuenta.class.getName()).log(Level.SEVERE, null, ex);
                }
            
            String IdUsuario = (String) sesion.getAttribute("IdUsuario");
                
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/conecta4","root","");
            st = con.createStatement();
            
            PrintWriter out = res.getWriter();
            res.setContentType("text/html");
            
            
            
            out.println("<HTML><HEAD>");
            //out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"css/interfaz.css\">");
            out.println("<link rel='shortcut icon' href='css/logo.jpg'></link>");
            out.println("</HEAD>");
            out.println("<BODY><CENTER>");
            out.println("<BR><BR>");
            out.println("<H1>Perfil</H1>");
            out.println("<BR><BR>");
            
            SQL = "SELECT * FROM usuarios WHERE IdUsuario = '" + IdUsuario + "'";
            rs = st.executeQuery(SQL);
 
            if (rs.next()) {
                
                int victorias = rs.getInt(4);
                int derrotas = rs.getInt(5);
                String correo=rs.getString(7);
                String telefono=rs.getString(8);
                
                
                
                out.println("<p class='perfil-info'>Usuario:  " + rs.getString(2) + "</p>");
                out.println("<p class='perfil-info'>Victorias:   " + victorias + "</p>");
                out.println("<p class='perfil-info'>Derrotas:   " + derrotas + "</p>");
                out.println("<p class='perfil-info'>Correo:   " + correo + "</p>");
                out.println("<p class='perfil-info'>Telefono:   " + telefono + "</p>");
            } else {
                out.println("<p>No se encontró información del perfil.</p>");
            }
            
            // Formulario para cambiar la contraseña
            out.println("<FORM METHOD='POST' ACTION='CambioCuenta'>");
            out.println("<INPUT TYPE=SUBMIT VALUE='Cambiar Contraseña'><BR>");
            out.println("</FORM>");
            
            out.println("<div id='triangle'></div>"); //flecha de volver
            out.println("<FORM ACTION='Interfaz' METHOD='POST'>");
            out.println("<BUTTON TYPE='SUBMIT'>Volver</BUTTON>");
            out.println("</FORM>");
            out.println("</BODY></HTML>");
            
            out.println("</BODY></HTML>");
            out.close();
            con.close();
            rs.close();
            st.close();
    } catch(Exception e){
        System.err.println(e);
    }


    }
}
