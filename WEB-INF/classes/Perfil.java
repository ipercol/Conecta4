import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Perfil extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        res.setContentType("text/html;charset=UTF-8");
        Connection con;
        Statement st;
        ResultSet rs;
        String SQL;
        HttpSession sesion = req.getSession();
        String idUsuario = (String) sesion.getAttribute("IdUsuario");
        try{
            try {
                    Class.forName("com.mysql.jdbc.Driver");
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(CrearCuenta.class.getName()).log(Level.SEVERE, null, ex);
                }
            
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/conecta4","root","");
            st = con.createStatement();
            
            PrintWriter out = res.getWriter();
            
            SQL = "SELECT * FROM usuarios WHERE IdUsuario = '" + idUsuario + "'";
            rs = st.executeQuery(SQL);
            
            out.println("<HTML><HEAD>");
            out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"css/perfil.css\">");
            out.println("</HEAD>");
            out.println("<BODY><CENTER>");
            out.println("<BR><BR>");
            out.println("<H1>Perfil</H1>");
            out.println("<BR><BR>");
            
            
            
            if (rs.next()) {
                
                int victorias = rs.getInt(4);
                int derrotas = rs.getInt(5);
                String correo=rs.getString(7);
                String telefono=rs.getString(8);
                
                
                
                out.println("<p class='perfil-info'>Usuario:   " + rs.getString(2) + "</p><BR><BR>");
                out.println("<p class='perfil-info'>Victorias:    " + victorias + "</p><BR><BR>");
                out.println("<p class='perfil-info'>Derrotas:    " + derrotas + "</p><BR><BR>");
                out.println("<p class='perfil-info'>Correo:    " + correo + "</p><BR><BR>");
                out.println("<p class='perfil-info'>Telefono: " + telefono + "</p><BR><BR>");
            } else {
                out.println("<p>No se encontró información del perfil.</p>");
            }
            
            // Formulario para cambiar la contraseña
            out.println("<FORM METHOD='POST' ACTION='CambioCuenta'>");
            out.println("<INPUT TYPE=SUBMIT VALUE='Cambiar Datos'><BR>");
            out.println("</FORM>");
            
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
