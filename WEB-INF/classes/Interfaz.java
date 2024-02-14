import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class Interfaz extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        
        Connection con;
        Statement st;
        ResultSet rs;
        PrintWriter out;
        String SQL, IdUsuario;
        HttpSession sesion;

    try{
        // Obtener el ID de usuario de la sesión
        sesion = req.getSession();
        IdUsuario = (String)sesion.getAttribute("IdUsuario");

        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/conecta4","root","");
            st = con.createStatement();
        } catch (ClassNotFoundException | SQLException e) {
            throw new ServletException("Error al conectar con la base de datos", e);
        }

        // Consulta SQL para obtener todas las partidas disponibles para el usuario actual
        SQL = "SELECT * FROM detallespartidas WHERE IdUsuario='" + IdUsuario + "'";
        rs = st.executeQuery(SQL);

        // Renderizar la página HTML para mostrar las partidas disponibles
        out = res.getWriter();
        res.setContentType("text/html");
        out.println("<HTML><HEAD>");
        out.println("<TITLE>CONECTA 4444</TITLE>");
        out.println("</HEAD>");
        out.println("<BODY>");

        // Mostrar las partidas disponibles
        out.println("<h2>Tus partidas:</h2>");
        if (!rs.next()){
            out.println("No tienes ninguna partida en curso.");
        } else{
            do{
                out.println("<P><A HREF='Partida=" + rs.getString(2) + "'></A></P><BR>");      
            }
            while (rs.next());
        }
        // Consulta SQL para obtener todas las partidas pendientes de aceptación para el usuario actual
            SQL = "SELECT * FROM detallespartidas GROUP BY IdPartida HAVING COUNT(IdUsuario) < 2";
            rs = st.executeQuery(SQL);
            out.println("<h2>Partidas pendientes de aceptación:</h2>");
            if (!rs.next()) {
                out.println("No tienes ninguna partida pendiente de aceptación.");
            } else {
                do {
                    out.println("<A HREF='AceptarPartida?IdPartida=" + rs.getString("IdPartida") + "'>Aceptar</A>");
                } while (rs.next());
            }
        // Agregar botón para iniciar una partida contra alguien de manera aleatoria
        out.println("<FORM METHOD='POST' ACTION='Partida'>");
        out.println("<INPUT TYPE='SUBMIT' VALUE='BUSCAR PARTIDA'>");
        out.println("</FORM>");

        out.println("</BODY></HTML>");
        rs.close();
        st.close();
        out.close();
        con.close();
    } catch(Exception e){
        System.err.println(e);
        }
    }
}