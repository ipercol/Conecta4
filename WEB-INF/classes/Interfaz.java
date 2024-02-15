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
        String SQL, IdUsuario, IdPartida;
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
        out.println("<TITLE>Interfaz</TITLE>");
        out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"css/inicio.css\">");

        out.println("</HEAD>");
        out.println("<BODY><CENTER>");
        out.println("<BR><BR>");
        out.println("<H1>CONECTA 4444</H1>");

        // Mostrar las partidas disponibles
        out.println("<h2>Tus partidas:</h2>");
        if (!rs.next()){
            out.println("No tienes ninguna partida en curso.");
        } else{
            do{ 
                out.println("<a href='Partida?IdPartida=" + rs.getString(2) + "'>Partida " + rs.getString(2) + "</a><br>");
            }
            while (rs.next());
        }
        rs.close();
        // Consulta SQL para obtener todas las partidas disponibles para el usuario
        SQL = "SELECT * FROM partidas WHERE full=0 AND IdPartida NOT IN (SELECT IdPartida FROM detallespartidas WHERE IdUsuario='" + IdUsuario + "')";
        rs = st.executeQuery(SQL);
        out.println("<h2>Partidas disponibles:</h2>");
        if (!rs.next()){
            out.println("No tienes ninguna partida disponible.");
        } else{
            do{
                IdPartida = rs.getString(1);
                out.println("<a href='Inscripcion?IdPartida=" + IdPartida + "'>Partida " + IdPartida + "</a><br>");
            }
            while (rs.next());
        }
        // Agregar botón para iniciar una partida contra alguien de manera aleatoria
        out.println("<FORM METHOD='POST' ACTION='CrearPartida'>");
        out.println("<INPUT TYPE='SUBMIT' VALUE='CREAR PARTIDA'>");
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