import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.math.BigInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EditarPerfil extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        res.setContentType("text/html;charset=UTF-8");
        Statement st, st2;
        ResultSet rs;
        Connection con;
        String SQL;
        PrintWriter out = res.getWriter();

        try {
            try {
                    Class.forName("com.mysql.jdbc.Driver");
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(CrearCuenta.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            out.println("<HTML><HEAD>");
            out.println("<TITLE>Resultado del Cambio de Contraseña</TITLE>");
            out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"css/inicio.css\">");
            out.println("</HEAD>");
            out.println("<BODY><CENTER>");
            out.println("<BR><BR>");
            out.println("<H1>Resultado del Cambio de Contraseña</H1>");

            // Obtener la nueva contraseña del formulario
            HttpSession sesion = req.getSession();
            String idUsuario = (String) sesion.getAttribute("IdUsuario");
            String newPassword = req.getParameter("newPassword");
            String newPassword2 = req.getParameter("newPassword2");
            
            // Comprobar si la contraseña actual coincide con la almacenada en la base de datos
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/conecta4", "root", "");
            st = con.createStatement();
            st2 = con.createStatement();


            // Obtener la contraseña almacenada en la base de datos
            SQL = "SELECT password FROM usuarios WHERE IdUsuario = '" + idUsuario + "'";
            rs = st.executeQuery(SQL);
            rs.next();
                        
            String storedPassword = rs.getString(1);
            
            MessageDigest digest = MessageDigest.getInstance("SHA-512");
            digest.reset();
            digest.update(storedPassword.getBytes("utf8"));
            storedPassword = String.format("%0128x", new BigInteger(1, digest.digest())); 


            
            // Comprobar si la nueva contraseña no está vacía y si newPassword y newPassword2 son iguales
            if (newPassword == null || newPassword.isEmpty() || !newPassword.equals(newPassword2)) {
                out.println("<FORM id='redirect' ACTION='CambioCuenta' METHOD='POST'> </FORM>");
                out.println("<script>");
                out.println("window.onload = function() {");
                out.println(" document.getElementById('redirect').submit();");
                out.println("};");
                out.println("</script>");
                throw new ServletException("Las contraseñas no coinciden o son inválidas.");
            }
            
            try {
                digest = MessageDigest.getInstance("SHA-512");
                digest.reset();
                digest.update(newPassword.getBytes("utf8"));
                newPassword = String.format("%0128x", new BigInteger(1, digest.digest()));
            } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
                throw new ServletException("Error al generar el hash de la nueva contraseña", e);
            }
            // Actualizar la contraseña en la base de datos
            SQL = "UPDATE usuarios SET password='" + newPassword + "' WHERE IdUsuario='" + idUsuario + "'";
            st2.executeUpdate(SQL);

            // Redirigir al usuario a una página después de cambiar la contraseña
            out.println("<FORM id='redirect' ACTION='Perfil' METHOD='POST'> </FORM>");
            out.println("<script>");
            out.println("window.onload = function() {");
            out.println(" document.getElementById('redirect').submit();");
            out.println("};");
            out.println("</script>");
                
            

            
                
            


            out.println("</BODY></HTML>");
            out.close();
            st.close();
            st2.close();
            con.close();

        } catch (Exception e) {
            System.err.println(e);
        }
    }
}


      
        
    


