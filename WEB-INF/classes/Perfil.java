import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

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
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/conecta4","root","");
                st = con.createStatement();
            } catch (ClassNotFoundException | SQLException e) {
                throw new ServletException("Error al conectar con la base de datos", e);
            }
            PrintWriter out = res.getWriter();
            SQL = "SELECT victorias, derrotas FROM usuarios WHERE IdUsuario = '" + idUsuario + "'";
            rs = st.executeQuery(SQL);
            
            out.println("<HTML><HEAD>");
            out.println("<TITLE>Editar Cuenta</TITLE>");
            out.println("</HEAD>");
            out.println("<BODY><CENTER>");
            out.println("<BR><BR>");
            out.println("<H1>Perfil</H1>");

        // Formulario para cambiar la contraseña
            out.println("<FORM METHOD='POST' ACTION='EditarPassword'>");
            out.println("<LABEL for=newPassword>Nueva Contraseña:</LABEL><BR>");
            out.println("<INPUT TYPE=PASSWORD id=newPassword NAME=newPassword><BR><BR>");
            out.println("<INPUT TYPE=SUBMIT VALUE='Cambiar Contraseña'><BR>");
            out.println("</FORM>");
            
            if (rs.next()) {
                int victorias = rs.getInt("victorias");
                int derrotas = rs.getInt("derrotas");

                out.println("<p>Victorias: " + victorias + "</p>");
                out.println("<p>Derrotas: " + derrotas + "</p>");
            } else {
                out.println("<p>No se encontró información del perfil.</p>");
            }
            
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
