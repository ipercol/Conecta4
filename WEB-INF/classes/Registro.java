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
        String SQL, usuario, password;
        HttpSession sesion;

        try {
            usuario = req.getParameter("usuario");
            password = req.getParameter("password");
            
            if (usuario == null || usuario.isEmpty() || password == null || password.isEmpty()) {
                res.sendRedirect("CrearCuenta");
                throw new ServletException("El nombre de usuario y la contraseña son obligatorios.");
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
            
            res.setContentType("text/html");
            out = res.getWriter();
            
            if (rs.next() && rs.getInt(1)==0) {
                // Usuario no existe, procedemos a registrarlo
                SQL = "INSERT INTO usuarios (usuario, password) VALUES ('" + usuario + "', '" + password + "')";
                rs.close();
                st.executeUpdate(SQL);
                rs.close();
                sesion = req.getSession();
                SQL = "SELECT IdUsuario FROM usuarios WHERE usuario='" + usuario + "'";
                rs = st.executeQuery(SQL);
                sesion.setAttribute("IdUsuario", rs.getString(1));
                res.sendRedirect("Inicio");
            } else {
                // Usuario ya existe
                out.println("<p>El nombre de usuario ya está en uso. Por favor, elija otro.</p>");
                res.sendRedirect("CrearCuenta");
                // Puede agregar un enlace para volver al formulario de registro o simplemente redirigir
            }
            rs.close();
            st.close();
            con.close();
            out.close();
        } catch (Exception e) {
            throw new ServletException("Error al iniciar la conexion con la base de datos",e);
        } 
        }
}