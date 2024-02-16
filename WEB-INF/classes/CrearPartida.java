//En este servlet se crean partidas sin contrincante. Servira para que la gente se inscriba a ellas si quiere jugar.
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
public class CrearPartida extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        
        Connection con;
        Statement st;
        ResultSet rs;
        PrintWriter out;
        String SQL, IdUsuario;
        HttpSession sesion;
        
        try{
            
            sesion = req.getSession();
            IdUsuario = (String)sesion.getAttribute("IdUsuario");
            
            try {
                Class.forName("com.mysql.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/conecta4","root","");
                st = con.createStatement();
                } catch (ClassNotFoundException | SQLException e) {
                throw new ServletException("Error al conectar con la base de datos", e);
                }
            
            SQL = "INSERT INTO partidas (full) VALUES(0)";
            st.executeUpdate(SQL,Statement.RETURN_GENERATED_KEYS);
            rs = st.getGeneratedKeys();
            String IdPartida=null;
            if (rs.next()) {
                IdPartida = rs.getString(1);
            }
            
            SQL = "INSERT INTO detallespartidas (IdPartida, IdUsuario) VALUES ('" + IdPartida + "', '" + IdUsuario + "')";

            st.executeUpdate(SQL);

            out = res.getWriter();
            res.setContentType("text/html");
            out.println("<HTML><HEAD>");
            out.println("<TITLE>Crear Partida</TITLE>");
            out.println("</HTML><BODY><CENTER>");
            out.println("<FORM id='redirect' ACTION='Interfaz' METHOD='POST'> </FORM>");
            out.println("<script>");
            out.println("window.onload = function() {");
            out.println(" document.getElementById('redirect').submit();");
            out.println("};");
            out.println("</script>");
            
            con.close();
            st.close();
            rs.close();
            out.close();
            
            
            
            
            
        } catch (Exception e){
            System.err.println(e);
        }
    }
}
