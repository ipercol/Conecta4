import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.math.BigInteger;


public class Registro extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        Connection con;
        Statement st;
        ResultSet rs;
        PrintWriter out;
        out=res.getWriter();
        String SQL, usuario, password, reppassword, correo,telefono;
        HttpSession sesion;

        try {
            try {
                    Class.forName("com.mysql.jdbc.Driver");
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(CrearCuenta.class.getName()).log(Level.SEVERE, null, ex);
                }
            usuario = req.getParameter("usuario");
            password = req.getParameter("password");
            reppassword = req.getParameter("reppassword");
            correo = req.getParameter("correo");
            telefono = req.getParameter("telefono");
            // Establece la conexión a la base de datos
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/conecta4","root","");
            st = con.createStatement();
            
            res.setContentType("text/html");
            out = res.getWriter();
            
            out.println("<HTML><HEAD>");
            out.println("<TITLE>Registro</TITLE>");
            //out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"css/inicio.css\">");
            out.println("<link rel='shortcut icon' href='css/logo.jpg'></link>");
            out.println("</HEAD>");
            out.println("<BR><BR><BR>");
            out.println("<BODY><CENTER>");
            
            if (usuario == null || usuario.isEmpty() || password == null || password.isEmpty() || reppassword == null || reppassword.isEmpty() || correo == null || correo.isEmpty() || telefono == null || telefono.isEmpty()) {
                out.println("<H2>No puede dejar campos vacios.</H2>");
                out.println("<FORM METHOD=POST ACTION=CrearCuenta>"); 
                out.println("<INPUT TYPE=SUBMIT VALUE=Volver>");
                out.println("</FORM></CENTER>");
            } else  if (!password.equals(reppassword)){
                out.println("<H2>Las contraseñas no coinciden.</H2>");
                out.println("<FORM METHOD=POST ACTION=CrearCuenta>"); 
                out.println("<INPUT TYPE=SUBMIT VALUE=Volver>");
                out.println("</FORM></CENTER>");
            } else {
            
                // Hash de la contraseña
                try{
                    MessageDigest digest = MessageDigest.getInstance("SHA-512");
                    digest.reset();
                    digest.update(password.getBytes("utf8"));
                    password = String.format("%0128x", new BigInteger(1, digest.digest()));
                    } catch (NoSuchAlgorithmException | UnsupportedEncodingException e){
                       e.printStackTrace(); 
                    }
                
        
                // Verificar si el usuario ya existe
                SQL = "SELECT COUNT(*) FROM usuarios WHERE usuario='" + usuario + "'";
                rs = st.executeQuery(SQL);
                
                if (rs.next() && rs.getInt(1)==0) {
                    // Usuario no existe, procedemos a registrarlo
                    SQL = "INSERT INTO usuarios (usuario, password, correo, telefono) VALUES ('" + usuario + "', '" + password + "','" + correo + "','" + telefono + "')";
                    st.executeUpdate(SQL);
                    rs.close();
                    
                    SQL = "SELECT IdUsuario FROM usuarios WHERE usuario='" + usuario + "'";
                    rs = st.executeQuery(SQL);
                    res.sendRedirect("Inicio");
                } else {
                    // Usuario ya existe
                    out.println("<H2>El usuario ya existe. Pruebe otro nombre.</H2>");
                    out.println("<FORM METHOD=POST ACTION=CrearCuenta>"); 
                    out.println("<INPUT TYPE=SUBMIT VALUE=Volver>");
                    out.println("</FORM></CENTER>");
                }
                rs.close();
                }
 
            st.close();
            con.close();
            out.close();
        } catch (Exception e) {
            System.err.println(e);
        } 
        }
}