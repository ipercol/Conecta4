import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
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
        String SQL, usuario, password,correo,telefono;
        HttpSession sesion;

        try {
            usuario = req.getParameter("usuario");
            password = req.getParameter("password");
            correo = req.getParameter("correo");
            telefono = req.getParameter("telefono");
            
            res.setContentType("text/html");
            out = res.getWriter();
            
            if (usuario == null || usuario.isEmpty() || password == null || password.isEmpty() || correo == null || correo.isEmpty() || telefono == null || telefono.isEmpty()) {
                out.println("<FORM id='redirect' ACTION='CrearCuenta' METHOD='POST'> </FORM>");
                out.println("<script>");
                out.println("window.onload = function() {");
                out.println(" document.getElementById('redirect').submit();");
                out.println("};");
                out.println("</script>");
                throw new ServletException("Todos los campos son obligatorios.");
            }
            
            // Hash de la contraseña
             try{
                MessageDigest digest = MessageDigest.getInstance("SHA-512");
                digest.reset();
                digest.update(password.getBytes("utf8"));
                password = String.format("%0128x", new BigInteger(1, digest.digest()));
                } catch (NoSuchAlgorithmException | UnsupportedEncodingException e){
                   e.printStackTrace(); 
                }

            // Establece la conexión a la base de datos
            try {
                Class.forName("com.mysql.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/conecta4","root","");
                st = con.createStatement();
            } catch (ClassNotFoundException | SQLException e) {
                throw new ServletException("Error al conectar con la base de datos", e);
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
                
                out.println("<FORM id='redirect' ACTION='CrearCuenta' METHOD='POST'> </FORM>");
                //Mediante un script hacemos que redirija directamente a la pagina del menu lanzando el formulario anterior
                out.println("<script>");
                out.println("window.onload = function() {");
                out.println(" document.getElementById('redirect').submit();");
                out.println("};");
                out.println("</script>");
                // Puede agregar un enlace para volver al formulario de registro o simplemente redirigir
            }
            rs.close();
            st.close();
            con.close();
            out.close();
        } catch (Exception e) {
            System.err.println(e);
        } 
        }
}