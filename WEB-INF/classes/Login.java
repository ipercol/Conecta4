import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.math.BigInteger;

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class Login extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        Connection con;
        Statement st;
        ResultSet rs;
        PrintWriter out;
        String SQL, usuario, password;
        HttpSession sesion;
        
        try{
            try {
                Class.forName("com.mysql.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/conecta4","root","");
                st = con.createStatement();
            } catch (ClassNotFoundException | SQLException e) {
                throw new ServletException("Error al conectar con la base de datos", e);
            }
            sesion = req.getSession();
            // Obtener parámetros del formulario
            usuario = req.getParameter("usuario");
            password = req.getParameter("password");
        
            //hash de la contraseña
            try{
                MessageDigest digest = MessageDigest.getInstance("SHA-512");
                digest.reset();
                digest.update(password.getBytes("utf8"));
                password = String.format("%0128x", new BigInteger(1, digest.digest()));
                } catch (NoSuchAlgorithmException | UnsupportedEncodingException e){
                   e.printStackTrace(); 
                }
            
            //Consulta
            SQL = "SELECT * FROM usuarios WHERE usuario='" + usuario + "' AND password='" + password + "'";

            rs = st.executeQuery(SQL);
            
            out = res.getWriter();
            res.setContentType("text/html");
            out.println("<HTML><HEAD>");
            out.println("<TITLE>LOG</TITLE>");
            out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"css/inicio.css\">");
            out.println("</HTML><BODY><CENTER>");
        
            if(rs.next()){
                sesion.setAttribute("IdUsuario", rs.getString(1));
                out.println("<FORM id='redirect' ACTION='Interfaz' METHOD='POST'> </FORM>");
                //Mediante un script hacemos que redirija directamente a la pagina del menu lanzando el formulario anterior
                out.println("<script>");
                out.println("window.onload = function() {");
                out.println(" document.getElementById('redirect').submit();");
                out.println("};");
                out.println("</script>"); 
            } else{
                out.println("<BR>");
                out.println("<H2>No existe ninguna cuenta con estos credenciales</H2><BR>");
                res.sendRedirect("Inicio");
                }

            rs.close();
            st.close();
            con.close();
            out.close();
        } catch (Exception e){
            System.err.println(e);
        }
    }
}
