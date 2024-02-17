import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
public class Chess extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        
        Connection con;
        Statement st, st2, st3;
        ResultSet rs, rs2 = null, rs3 = null;
        PrintWriter out;
        String SQL, SQL2, SQL3, IdUsuario, IdPartida, IdJugador2;
        HttpSession sesion;
        boolean turno;
        
        try{
            
            sesion = req.getSession();
            IdUsuario = (String)sesion.getAttribute("IdUsuario");
            IdPartida = req.getParameter("IdPartida");
            IdJugador2 = req.getParameter("IdJugador2");
            
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/conecta4","root","");
            st = con.createStatement();
            st2 = con.createStatement();
            st3 = con.createStatement();
            
            res.setContentType("text/html;charset=UTF-8");
            out = res.getWriter();
            
            out.println("<HTML><HEAD>");
            out.println("<TITLE>Crear Partida</TITLE>");
            out.println("<CENTER>");
            if (IdUsuario == null || IdJugador2 == null){
                out.println("patatas");
            } else{
                out.println("Jugador 1: " + IdUsuario + "Jugador 2:" + IdJugador2);
            }
            SQL = "SELECT turno from detallespartidas WHERE IdPartida='" + IdPartida + "' AND IdUsuario='" + IdUsuario + "'";
            rs = st.executeQuery(SQL);
            
            SQL2 = "SELECT * FROM tablero WHERE IdUsuario=" + IdUsuario + " AND IdPartida=" + IdPartida + " ORDER BY columna, fila";
            rs2 = st2.executeQuery(SQL2);
            
            SQL3 = "SELECT * FROM tablero WHERE IdUsuario=" + IdJugador2 + " AND IdPartida=" + IdPartida + " ORDER BY columna, fila";
            rs3 = st3.executeQuery(SQL3);
            
            //Creacion del tablero:
            char [][] tablero = new char[6][7];
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 7; j++) {
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
                for (int j = 0; j < 7; j++) {
                    out.println("<TD WIDTH=\"50\" HEIGHT=\"50\"><CENTER>" + tablero[i][j] + "</CENTER></TD>");
                }
                out.println("</TR>");
            }
            out.println("</TABLE><BR>");
            
            if (rs.next()){
                turno = rs.getBoolean("turno");
                if(turno){
                    out.println("<form METHOD = 'POST' ACTION = 'Movimientos'>");
                    for(int i = 0; i < 7; i++){
                        out.println("<INPUT TYPE='radio' NAME='columna' VALUE='" + i + "'required>C" + i + "&nbsp;&nbsp;");
                    }
                    out.println("<INPUT TYPE='hidden' NAME='IdPartida' VALUE='" + IdPartida + "'>");
                    out.println("<INPUT TYPE='hidden' NAME='IdJugador2' VALUE='" + IdJugador2 + "'><BR>");
                    out.println("<input type=\"submit\" value=\"Tiradica\">");
                    out.println("</form>");
                }   else{
                    out.println("No te toca notas");
                }
            } else{
                out.println("No se ha podido determinar de quien es el turno. Consulta fallida o nula");
            }
            
            
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