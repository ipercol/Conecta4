import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.math.BigInteger;

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Login extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        Connection con;
        Statement st;
        ResultSet rs;
        PrintWriter out;
        String SQL, usuario, password;
        
        try{
            try{
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException ex){
                //Logger.getLogger(Signup.class.getName()).log(Level, SEVERE, null, ex);
            }
            HttpSession sesion = req.getSession();
            // Obtener parámetros del formulario
            usuario = req.getParameter("usuario");
            password = req.getParameter("password");
            
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/conecta","root","");
            st = con.createStatement();
        
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
            SQL = "SELECT * FROM usuarios WHERE Nombre=" + usuario + " AND password=" + password;
            rs = st.executeQuery(SQL);
            
            out = res.getWriter();
            res.setContentType("text/html");
            out.println("<HTML><HEAD>");
            out.println("<TITLE>LOG</TITLE>");
            out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"css/inicio.css\">");
            out.println("</HTML><BODY><CENTER>");
        
            if(rs.next()){
                sesion.setAttribute("IdUsuario", rs.getString(1));
                out.println("<FORM id='redirect' ACTION='Menu' METHOD='POST'>");
                out.println("</FORM>");
                
                out.println("<script>");
                out.println("window.onload = function() {");
                out.println("}");
                out.println("</script>");   
            } else{
                out.println("<BR>");
                out.println("<H2>No existe ninguna cuenta con estos credenciales</H2><BR>");
                out.println("<FORM METHOD=GET ACTION=Inicio>");
                out.println("</FORM></CENTER>");              
                }
            out.println("</CENTER></BODY></HTML>");
            rs.close();
            st.close();
            con.close();
            out.close();
        } catch (Exception e){
            System.err.println(e);
        }
    }
}
