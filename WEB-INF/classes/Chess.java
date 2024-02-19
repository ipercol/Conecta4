import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
public class Chess extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        
        Connection con;
        Statement st, st2, st3, st4, st5;
        ResultSet rs, rs2 = null, rs3 = null, rs4, rs5;
        PrintWriter out;
        String SQL, SQL2, SQL3, SQL4, SQL5;
        String IdUsuario, IdPartida, IdJugador2, Nrival;
        HttpSession sesion;
        boolean turno;
        
        try{
            
            sesion = req.getSession();
            IdUsuario = (String)sesion.getAttribute("IdUsuario");
            IdPartida = req.getParameter("IdPartida");
            IdJugador2 = req.getParameter("IdJugador2");
            
            try {
                    Class.forName("com.mysql.jdbc.Driver");
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(CrearCuenta.class.getName()).log(Level.SEVERE, null, ex);
                }
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/conecta4","root","");
            st = con.createStatement();
            st2 = con.createStatement();
            st3 = con.createStatement();
            st4 = con.createStatement();
            st5 = con.createStatement();
            
            res.setContentType("text/html;charset=UTF-8");
            out = res.getWriter();
            
            out.println("<HTML><HEAD>");
            out.println("<TITLE>Crear Partida</TITLE>");
            out.println("<CENTER>");
            
             
            SQL = "SELECT turno from detallespartidas WHERE IdPartida='" + IdPartida + "' AND IdUsuario='" + IdUsuario + "'";
            rs = st.executeQuery(SQL);
            
            SQL2 = "SELECT * FROM tablero WHERE IdUsuario=" + IdUsuario + " AND IdPartida=" + IdPartida + " ORDER BY columna, fila";
            rs2 = st2.executeQuery(SQL2);
            
            SQL3 = "SELECT * FROM tablero WHERE IdUsuario=" + IdJugador2 + " AND IdPartida=" + IdPartida + " ORDER BY columna, fila";
            rs3 = st3.executeQuery(SQL3);
            
            SQL4 = "SELECT * from usuarios WHERE IdUsuario='" + IdUsuario + "'";
            rs4 = st4.executeQuery(SQL4);
            rs4.next();
            
            SQL5 = "SELECT * from usuarios WHERE IdUsuario='" + IdJugador2 + "'";
            rs5 = st5.executeQuery(SQL5);
            rs5.next();
            Nrival = rs5.getString(2);
            
            if (IdJugador2 == null){
                out.println("¡Aun no tienes ningún rival!");
            } else{
                out.println(rs4.getString(2) + "    " + "VS    " + rs5.getString(2));
            }
            out.println("<BR><BR><BR>");
            
            //Creacion del tablero:
            char [][] tablero = new char[6][6];
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 6; j++) {
                    tablero[i][j] = '-';
                }
            }
            
            //Turno del que crea la partida
            while (rs2.next()){
                int columna = rs2.getInt(2);
                int fila = rs2.getInt(3);
                tablero[fila][columna] = 'X';
            }
            
            //Turno del que se inscribe
            while (rs3.next()){
                int columna = rs3.getInt(2);
                int fila = rs3.getInt(3);
                tablero[fila][columna] = 'O';
            }
            
            out.println("<TABLE BORDER=\"1\">");
            for (int i = 0; i < 6; i++) {
                out.println("<TR>");
                for (int j = 0; j < 6; j++) {
                    out.println("<TD WIDTH=\"50\" HEIGHT=\"50\"><CENTER>" + tablero[i][j] + "</CENTER></TD>");
                }
                out.println("</TR>");
            }
            out.println("</TABLE><BR>");
            
            //cerramos 
            rs4.close();
            rs5.close();
            
            SQL4 = "SELECT * from detallespartidas WHERE IdPartida='" + IdPartida + "' AND IdUsuario='" + IdUsuario + "'";
            rs4 = st4.executeQuery(SQL4);
            rs4.next();
            
            SQL5 = "SELECT * from detallespartidas WHERE IdPartida='" + IdPartida + "' AND IdUsuario='" + IdJugador2 + "'";
            rs5 = st5.executeQuery(SQL5);
            rs5.next();
            
            
            out.println(rs4.getInt(3) + " - " + rs5.getInt(3));
            out.println("<BR>");
            
            if (rs.next()){
                turno = rs.getBoolean("turno");
                if(turno){
                    out.println("<form METHOD = 'POST' ACTION = 'Movimientos'>");
                    for(int i = 0; i < 6; i++){
                        out.println("<INPUT TYPE='radio' NAME='columna' VALUE='" + i + "'required>C" + i + "&nbsp;&nbsp;");
                    }
                    out.println("<INPUT TYPE='hidden' NAME='IdPartida' VALUE='" + IdPartida + "'>");
                    out.println("<INPUT TYPE='hidden' NAME='IdJugador2' VALUE='" + IdJugador2 + "'><BR>");
                    out.println("<input type=\"submit\" value=\"Tiradica\">");
                    out.println("</form>");
                }   else{
                    out.println("<BR>");
                    out.println("Espera a que " + Nrival + " tire.");
                }
            } else{
                out.println("No se ha podido determinar de quien es el turno. Consulta fallida");
            }
            out.println("<BR><BR>");
            out.println("<form action='Interfaz' method='post'>");
            out.println("<button type='submit'>Volver</button>");
            out.println("</form>");

            
            
            out.println("</BODY></HTML>");
            
            rs.close();
            rs2.close();
            rs3.close();
            st.close();
            st2.close();
            st3.close();
            con.close();
        } catch (Exception e){
            System.err.println(e);
        }
    }
}