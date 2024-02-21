import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException.*;

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
            out.println("<link rel='shortcut icon' href='css/logo.jpg'></link>");
            out.println("<TITLE>FinalPartida</TITLE>");
            out.println("<CENTER>");
            
            SQL="SELECT usuarios.usuario, detallespartidas.puntos FROM detallespartidas INNER JOIN usuarios ON detallespartidas.IdUsuario = usuarios.IdUsuario WHERE detallespartidas.Idpartida='" + IdPartida + "' ORDER BY detallespartidas.puntos";
            rs = st.executeQuery(SQL);
            
            out.println("<BR><BR><TABLE><TR>");
            out.println("<CENTER>");
            while (rs.next()) {
                out.println("<TD STYLE='width: 50%; vertical-align: top; padding-right: 20px;'>");
                out.println("<H4>Jugador: " + rs.getString(1) + "<BR>" + "  Puntos: " + rs.getString(2) + "</H4>");
                out.println("</TD>");
            }
            out.println("</CENTER></TR></TABLE><BR>");
            rs.close();
  
            SQL2 = "SELECT usuarios.usuario, detallespartidas.puntos FROM detallespartidas INNER JOIN usuarios ON detallespartidas.IdUsuario = usuarios.IdUsuario WHERE detallespartidas.Idpartida='" + IdPartida + "' AND usuarios.IdUsuario='" + IdUsuario + "'";
            rs2 = st2.executeQuery(SQL2);
            rs2.next(); 
            Usuario1 = rs2.getString(1);
            int puntosUsuario1 = rs2.getInt(2);
            rs2.close();
            
            SQL3 = "SELECT usuarios.usuario, detallespartidas.puntos FROM detallespartidas INNER JOIN usuarios ON detallespartidas.IdUsuario = usuarios.IdUsuario WHERE detallespartidas.Idpartida='" + IdPartida + "' AND usuarios.IdUsuario='" + IdJugador2 + "'";
            rs3 = st3.executeQuery(SQL3);
            rs3.next(); 
            Usuario2 = rs3.getString(1);
            int puntosUsuario2 = rs3.getInt(2);
            rs3.close();
            
            out.println("<BR><BR><BR><H1>");
            if (puntosUsuario1 > puntosUsuario2) {
                out.println("¡El ganador es: " + Usuario1 + "!");
            } else if (puntosUsuario1 == puntosUsuario2) {
                out.println("¡EMPATE!");
            } else {
                out.println("¡El ganador es: " + Usuario2 + "!");
            }
            out.println("</H1>");
            
            out.println("<div id='triangle'></div>");
            out.println("<FORM ACTION='Interfaz' METHOD='POST'>");
            out.println("<BUTTON TYPE='SUBMIT'>Volver</BUTTON>");
            out.println("</FORM>");
            
            
            out.println("</CENTER></BODY></HTML>");
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}
