import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FinalPartida extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        Connection con;
        Statement st,st2,st3;
        ResultSet rs,rs2,rs3;
        PrintWriter out;
        HttpSession sesion;
        String SQL,SQL2,SQL3,Usuario1,Usuario2 ;
        String IdPartida, IdUsuario, IdJugador2;

        try {
            sesion = req.getSession();
            IdUsuario = (String)sesion.getAttribute("IdUsuario");
            IdPartida = req.getParameter("IdPartida");
            IdJugador2 = req.getParameter("IdJugador2");
            try {
                    Class.forName("com.mysql.jdbc.Driver");
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(CrearCuenta.class.getName()).log(Level.SEVERE, null, ex);
                }
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/conecta4", "root", "");
            st = con.createStatement();
            st2=con.createStatement();
            st3=con.createStatement();
            
            res.setContentType("text/html;charset=UTF-8");
            out = res.getWriter();
            out.println("<HTML><BODY>");
            
            SQL="SELECT usuarios.usuario, detallespartidas.puntos FROM detallespartidas INNER JOIN usuarios ON detallespartidas.IdUsuario = usuarios.IdUsuario WHERE detallespartidas.Idpartida='" + IdPartida + "' ORDER BY detallespartidas.puntos";
            rs = st.executeQuery(SQL);
            while (rs.next()) {
                out.println("<p>Jugador: " + rs.getString(1) + ", Puntos: " + rs.getString(2) + "</p><BR>");
            }
            rs.close();
  
            SQL2 = "SELECT usuarios.usuario, detallespartidas.puntos FROM detallespartidas INNER JOIN usuarios ON detallespartidas.IdUsuario = usuarios.IdUsuario WHERE detallespartidas.Idpartida='" + IdPartida + "' AND usuarios.IdUsuario='" + IdUsuario + "'";
            rs2 = st2.executeQuery(SQL2);
            rs2.next(); // Move to the first row
            Usuario1 = rs2.getString(1);
            int puntosUsuario1 = rs2.getInt(2);
            rs2.close();
            
            SQL3 = "SELECT usuarios.usuario, detallespartidas.puntos FROM detallespartidas INNER JOIN usuarios ON detallespartidas.IdUsuario = usuarios.IdUsuario WHERE detallespartidas.Idpartida='" + IdPartida + "' AND usuarios.IdUsuario='" + IdJugador2 + "'";
            rs3 = st3.executeQuery(SQL3);
            rs3.next(); // Move to the first row
            Usuario2 = rs3.getString(1);
            int puntosUsuario2 = rs3.getInt(2);
            rs3.close();
            
            if (puntosUsuario1 > puntosUsuario2) {
                out.println("El ganador es: " + Usuario1);
                SQL="UPDATE usuarios SET victorias=victorias+1 WHERE IdUsuario="+IdUsuario;
                st2.executeUpdate(SQL);
                SQL="UPDATE clientes SET derrotas=derrotas+1 WHERE IdUsuario="+IdJugador2;
                st3.executeUpdate(SQL);
            } else if (puntosUsuario1 == puntosUsuario2) {
                out.println("EMPATE");
                SQL="UPDATE usuarios SET empates=empates+1 WHERE IdUsuario="+IdUsuario;
                st2.executeUpdate(SQL);
                SQL="UPDATE usuarios SET empates=empates+1 WHERE IdUsuario="+IdJugador2;
                st3.executeUpdate(SQL);
            } else {
                out.println("El ganador es: " + Usuario2);
                SQL="UPDATE usuarios SET derrotas=derrotas+1 WHERE IdUsuario="+IdUsuario;
                st2.executeUpdate(SQL);
                SQL="UPDATE usuarios SET victorias=victorias+1 WHERE IdUsuario="+IdJugador2;
                st3.executeUpdate(SQL);

            }
            
            

            
            out.println("</body></html>");
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}
