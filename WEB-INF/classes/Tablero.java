import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class Tablero extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        Connection con;
        Statement st, st2, st3;
        ResultSet rs, rs2, rs3;
        PrintWriter out;
        String SQL, SQL2, SQL3, IdUsuario, IdPartida, IdJugador2;
        int turno;
        HttpSession sesion;
        
        try{
            sesion = req.getSession();
            IdUsuario = (String)sesion.getAttribute("IdUsuario");
            IdPartida = req.getParameter("IdPartida");
            //IdJugador2 = req.getParameter("IdJugador2");
        
            try {
                Class.forName("com.mysql.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/conecta4","root","");
                st = con.createStatement();
                //st2 = con.createStatement();
            } catch (ClassNotFoundException | SQLException e) {
                throw new ServletException("Error al conectar con la base de datos", e);
            }
        
            SQL = "SELECT turno FROM detallespartidas WHERE IdUsuario='" + IdUsuario + "' AND IdPartida='" + IdPartida + "'";
            rs = st.executeQuery(SQL);
            turno = rs.getInt(1);
            
            //SQL2 = "SELECT * FROM tablero WHERE IdUsuario=" + IdUsuario;
            //rs2 = st2.executeQuery(SQL);

            res.setContentType("text/html;charset=UTF-8");
            out = res.getWriter();
            // Generar el tablero
            
            out.println("<HTML><HEAD>");
            out.println("<TITLE>Conecta4-Tablero</TITLE>");
            

            out.println("</HEAD>");
            out.println("<BODY><CENTER>");
            out.println("<BR><BR>");
            out.println("<H1>CONECTA 4444</H1>");
            
            out.println("Hola" + turno);
            
            // Imprimir tablero
            /*
            while (rs2.next()){
                int columna = rs.getInt(2);
                int fila = rs.getInt(3);
                tablero[fila][columna] = 'X';
            }   
            
             while (rs2.next()){
                int columna = rs2.getInt(2);
                int fila = rs2.getInt(3);
                tablero[fila][columna] = 'O';
            }
            */
            out.println("<TABLE BORDER=\"1\">");
            for (int i = 0; i < 6; i++) {
                out.println("<TR>");
            for (int j = 0; j < 7; j++) {
                out.println("<TD WIDTH=\"50\" HEIGHT=\"50\"></TD>");
            }
                out.println("</TR>");
            }
            out.println("</TABLE><BR>");
            
            if(turno == 1){
                    out.println("<form METHOD = 'POST' ACTION = 'Movimientos'>");
                for(int i = 0; i < 7; i++){
                    out.println("<INPUT TYPE='radio' NAME='Columna' VALUE='" + i + "'required>C" + i + "&nbsp;&nbsp;");
                }
                    out.println("<INPUT TYPE='hidden' NAME='IdPartida' VALUE='" + IdPartida + "'>");
                    //out.println("<INPUT TYPE='hidden' NAME='IdJugador2' VALUE='" + IdJugador2 + "'><BR>");
                    out.println("<input type=\"submit\" value=\"Tiradica\">");
                    out.println("</form>");
                }   else{
                    out.println("No te toca notas");
                }
            
            out.println("</BODY></HTML>");

            rs.close();
            //rs2.close();
            st.close();
            //st2.close();
            con.close();
        } catch (Exception e){
            System.err.println(e);
        }
    }
}