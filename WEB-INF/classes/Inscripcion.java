import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
public class Inscripcion extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        
        Connection con;
        Statement st;
        PrintWriter out;
        String SQL, IdUsuario, IdPartida;
        HttpSession sesion;
        
        try{
            
            sesion = req.getSession();
            IdUsuario = (String)sesion.getAttribute("IdUsuario");
            IdPartida = req.getParameter("IdPartida");
            if (IdPartida == null){
                res.sendRedirect("Inicio");
            }
            
            try {
                Class.forName("com.mysql.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/conecta4","root","");
                st = con.createStatement();
                } catch (ClassNotFoundException | SQLException e) {
                throw new ServletException("Error al conectar con la base de datos", e);
                }
            
            SQL = "UPDATE partidas SET full = 1 WHERE IdPartida='" + IdPartida + "'";

            st.executeUpdate(SQL);
            
            SQL = "INSERT INTO detallespartidas (IdPartida, IdUsuario) VALUES ('" + IdPartida + "', '" + IdUsuario + "')";
            st.executeUpdate(SQL);
            SQL="UPDATE  detallespartidas SET turno= 0 WHERE IdPartida='" + IdPartida + "' AND IdUsuario= '" + IdUsuario + "'";
            st.executeUpdate(SQL);

            res.sendRedirect("Interfaz");
            
            con.close();
            st.close();
            
        } catch (Exception e){
            System.err.println(e);
        }
    }
}