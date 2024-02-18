import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class FinalPartida extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        Connection con;
        Statement st,st2,st3;
        ResultSet rs,rs2,rs3;
        PrintWriter out;
        HttpSession sesion;
        String IdPartida,SQL,SQL2,SQL3,ganador,ganadorNombre ;

        try {
            
            IdPartida = req.getParameter("IdPartida");

            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/conecta4", "root", "");
            st = con.createStatement();
            st2=con.createStatement();
            st3=con.createStatement();
            

            res.setContentType("text/html;charset=UTF-8");
            out = res.getWriter();
            out.println("<HTML><BODY>");
            SQL="SELECT usuarios.usuario, detallespartidas.puntos FROM detallespartidas INNER JOIN usuarios ON detallespartidas.IdUsuario = usuarios.IdUsuario WHERE detallespartidas.Idpartida='" + IdPartida + "' ORDER BY detallespartidas.puntos";
            rs=st.executeQuery(SQL);
            while (rs.next()) {
                        out.println("<p>Jugador: " + rs.getString(1) + ", Puntos: " + rs.getString(2) + "</p><BR>");
                    }

            // Obtener el ganador de la partida y la puntuación
            SQL2="SELECT TOP1 detallespartidas.IdUsuario, usuarios.usuario, detallespartidas.puntos FROM detallespartidas INNER JOIN usuarios ON detallespartidas.IdUsuario = usuarios.IdUsuario WHERE detallespartidas.Idpartida='" + IdPartida + "' ORDER BY detallespartidas.puntos";
            rs2=st.executeQuery(SQL2);
            
            
            if (rs2.next()) {
            ganador = rs2.getString(1);
            ganadorNombre=rs2.getString(2);
            int puntos = rs2.getInt(3);
            

                out.println("<head><title>Resultado de la Partida</title></head>");
                out.println("<h2>Resultado de la Partida</h2>");
                out.println("<p>El ganador es: " + ganadorNombre + "</p>");
                out.println("<p>Puntuación obtenida: " + puntos + "</p>");
                out.println("</body></html>");
                
                SQL3="UPDATE usuarios SET victorias= victorias + 1 WHERE IdUsuario='" + ganador + "'";
                st3.executeUpdate(SQL3);
                SQL3="UPDATE usuarios SET derrotas= derrotas + 1 WHERE IdUsuario<>'" + ganador + "'";
                st3.executeUpdate(SQL3);
                
                
            } else {
                out.println("<html><head><title>Resultado de la Partida</title></head><body>");
                out.println("<h2>Resultado de la Partida</h2>");
                out.println("<p>La partida ha terminado en empate.</p>");
                out.println("</body></html>");
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
