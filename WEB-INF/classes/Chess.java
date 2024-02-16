import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
public class Chess extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        
        Connection con;
        Statement st, st2, st3;
        ResultSet rs, rs2, rs3;
        PrintWriter out;
        String SQL, SQL2, SQL3, IdUsuario, IdPartida;
        HttpSession sesion;
        boolean turno;
        
        try{
            
            sesion = req.getSession();
            IdUsuario = (String)sesion.getAttribute("IdUsuario");
            IdPartida = req.getParameter("IdPartida");
            
            
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/conecta4","root","");
            st = con.createStatement();
            st2 = con.createStatement();
            
            res.setContentType("text/html;charset=UTF-8");
            out = res.getWriter();
            
            out.println("<HTML><HEAD>");
            out.println("<TITLE>Crear Partida</TITLE>");
            out.println("<CENTER>");
            
            SQL = "SELECT turno from detallespartidas WHERE IdPartida='" + IdPartida + "' AND IdUsuario='" + IdUsuario + "'";
            rs = st.executeQuery(SQL);
            
            
            
            SQL2 = "SELECT * FROM tablero WHERE IdUsuario=" + IdUsuario;
            rs2 = st2.executeQuery(SQL2);
            
            
            
            //Creacion del tablero:
            char [][] tablero = new char[6][7];
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 7; j++) {
                    tablero[i][j] = '-';
                }
            }
            
            while (rs2.next()){
                int columna = rs2.getInt(2);
                int fila = rs2.getInt(3);
                tablero[fila][columna] = 'X';
            }
            
            
            out.println("<TABLE BORDER=\"1\">");
            for (int i = 0; i < 6; i++) {
                out.println("<TR>");
                for (int j = 0; j < 7; j++) {
                    out.println("<TD WIDTH=\"50\" HEIGHT=\"50\"></TD>");
                }
                out.println("</TR>");
            }
            out.println("</TABLE><BR>");
            
            if (rs.next()){
                turno = rs.getBoolean("turno");
                if(turno){
                    out.println("<form METHOD = 'POST' ACTION = 'Movimientos'>");
                    for(int i = 1; i < 8; i++){
                        out.println("<INPUT TYPE='radio' NAME='Columna' VALUE='" + i + "'required>C" + i + "&nbsp;&nbsp;");
                    }
                    out.println("<INPUT TYPE='hidden' NAME='IdPartida' VALUE='" + IdPartida + "'>");
                    //out.println("<INPUT TYPE='hidden' NAME='IdJugador2' VALUE='" + IdJugador2 + "'><BR>");
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
            st.close();
            st2.close();
            con.close();
        } catch (Exception e){
            System.err.println(e);
        }
    }
}