import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.math.BigInteger;


public class EditarPassword extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        res.setContentType("text/html;charset=UTF-8");
        Statement st;
        Connection con;
        String SQL;
        PrintWriter out = res.getWriter();
        try{
        out.println("<HTML><HEAD>");
        out.println("<TITLE>Resultado del Cambio de Contraseña</TITLE>");
        out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"css/inicio.css\">");
        out.println("</HEAD>");
        out.println("<BODY><CENTER>");
        out.println("<BR><BR>");
        out.println("<H1>Resultado del Cambio de Contraseña</H1>");

        // Obtener la nueva contraseña del formulario
        String newPassword = req.getParameter("newPassword");
        if (newPassword == null || newPassword.isEmpty()) {
                res.sendRedirect("Perfil");
                throw new ServletException("El nombre de usuario y la contraseña son obligatorios.");
        }
        
        try{
                MessageDigest digest = MessageDigest.getInstance("SHA-512");
                digest.reset();
                digest.update(newPassword.getBytes("utf8"));
                newPassword = String.format("%0128x", new BigInteger(1, digest.digest()));
                } catch (NoSuchAlgorithmException | UnsupportedEncodingException e){
                   e.printStackTrace(); 
        }

        // Obtener el ID del usuario de la sesión
        HttpSession sesion = req.getSession();
        String idUsuario = (String) sesion.getAttribute("IdUsuario");
           try {
                Class.forName("com.mysql.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/conecta4","root","");
                st = con.createStatement();
            } catch (ClassNotFoundException | SQLException e) {
                throw new ServletException("Error al conectar con la base de datos", e);
            }
        // Actualizar la contraseña en la base de datos
        SQL="UPDATE usuarios SET password = '" + newPassword + "' WHERE IdUsuario = '" + idUsuario + "'";
        st.executeUpdate(SQL);
        
         
        // Redirigir al usuario a una página después de cambiar la contraseña
        out.println("<FORM id='redirect' ACTION='Inicio' METHOD='POST'> </FORM>");
        //Mediante un script hacemos que redirija directamente a la pagina del menu lanzando el formulario anterior
        out.println("<script>");
        out.println("window.onload = function() {");
        out.println(" document.getElementById('redirect').submit();");
        out.println("};");
        out.println("</script>");

        out.println("</BODY></HTML>");
        out.close();
        st.close();
        con.close();
        
       }catch(Exception e){System.err.println(e);
        
      }
    }
}


      
        
    


