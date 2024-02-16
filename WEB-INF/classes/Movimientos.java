import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
public class Movimientos extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        
        Connection con;
        Statement st, st2, st3;
        ResultSet rs, rs2, rs3;
        PrintWriter out;
        String SQL, SQL2, SQL3, IdUsuario, IdPartida, IdJugador2;
        HttpSession sesion;
        boolean turno;
        
    
    }
}