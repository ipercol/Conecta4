import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.math.BigInteger;


public class EditarPerfil extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        res.setContentType("text/html");
        Statement st;
        Connection con;
        ResultSet rs;
        String SQL;
        PrintWriter out = res.getWriter();
        try{
        out.println("<HTML><HEAD>");
        out.println("<TITLE>CambioContra</TITLE>");
        //out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"css/inicio.css\">");
        out.println("<link rel='shortcut icon' href='css/logo.jpg'></link>");
        out.println("</HEAD>");
        out.println("<BODY><CENTER>");
        out.println("<BR><BR>");

        HttpSession sesion = req.getSession();
        String IdUsuario = (String) sesion.getAttribute("IdUsuario");
        String actualPassword = req.getParameter("actualPassword");
        String newPassword = req.getParameter("newPassword");
        String repnewPassword = req.getParameter("repnewPassword");
        try {
                Class.forName("com.mysql.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/conecta4","root","");
                st = con.createStatement();
            } catch (ClassNotFoundException | SQLException e) {
                throw new ServletException("Error al conectar con la base de datos", e);
            }
            
        if (actualPassword == null || newPassword.isEmpty() || newPassword == null || newPassword.isEmpty() || repnewPassword == null || repnewPassword.isEmpty()) {
                out.println("<H2>No puede dejar campos vacios.</H2>");
                out.println("<FORM METHOD=POST ACTION=CambioCuenta>"); 
                out.println("<INPUT TYPE=SUBMIT VALUE=Volver>");
                out.println("</FORM></CENTER>");
            } else  if (!newPassword.equals(repnewPassword)){
                out.println("<H2>Las contraseñas no coinciden.</H2>");
                out.println("<FORM METHOD=POST ACTION=CambioCuenta>"); 
                out.println("<INPUT TYPE=SUBMIT VALUE=Volver>");
                out.println("</FORM></CENTER>");
            } else {
                try{
                        MessageDigest digest = MessageDigest.getInstance("SHA-512");
                        digest.reset();
                        digest.update(actualPassword.getBytes("utf8"));
                        actualPassword = String.format("%0128x", new BigInteger(1, digest.digest()));
                    } catch (NoSuchAlgorithmException | UnsupportedEncodingException e){
                       e.printStackTrace(); 
                    }
                SQL = "SELECT password FROM usuarios WHERE IdUsuario='"+ IdUsuario +"' AND password='" + actualPassword + "'";
                rs=st.executeQuery(SQL);
                if (rs.next()){
                    try {
                        MessageDigest digest = MessageDigest.getInstance("SHA-512");
                        digest.reset();
                        digest.update(newPassword.getBytes("utf8"));
                        newPassword = String.format("%0128x", new BigInteger(1, digest.digest()));
                    } catch (NoSuchAlgorithmException | UnsupportedEncodingException e){
                        e.printStackTrace();
                    }
                    //Actualizamos los datos en la base de datos
                    SQL="UPDATE usuarios SET password='" + newPassword + "' WHERE IdUsuario='" + IdUsuario + "'";
                    st.executeUpdate(SQL);
                        
                    out.println("<FORM id='redirect' ACTION='Interfaz' METHOD='POST'> </FORM>");
                    out.println("<script>");
                    out.println("window.onload = function() {");
                    out.println(" document.getElementById('redirect').submit();");
                    out.println("};");
                    out.println("</script>");
                } else {
                out.println("<H2>Su contraseña actual es incorrecta.</H2>");
                out.println("<FORM METHOD=POST ACTION=CambioCuenta>"); 
                out.println("<INPUT TYPE=SUBMIT VALUE=Volver>");
                out.println("</FORM></CENTER>"); 
                }
            }
            
            out.println("</BODY></HTML>");
            out.close();
            st.close();
            con.close();
            
           }catch(Exception e){System.err.println(e);
        }
        
      
    }   
}
      
        
    


