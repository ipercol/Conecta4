//En este servlet se crean partidas sin contrincante. Servira para que la gente se inscriba a ellas si quiere jugar.
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
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
            
            out = res.getWriter();
            res.setContentType("text/html");
            sesion = req.getSession();
            IdUsuario = (String)sesion.getAttribute("IdUsuario");
            
            try {
                    Class.forName("com.mysql.jdbc.Driver");
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(CrearCuenta.class.getName()).log(Level.SEVERE, null, ex);
                }
            
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/conecta4","root","");
            st = con.createStatement();
            
            out.println("<HTML><BODY><HEAD>");
            out.println("<TITLE>CrearPartida</TITLE>");
            //out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"css/inicio.css\">");
            out.println("<link rel='shortcut icon' href='css/logo.jpg'></link>");
            out.println("</HEAD>");
            out.println("<CENTER>");
            
            SQL = "INSERT INTO partidas (full) VALUES(0)";
            st.executeUpdate(SQL,Statement.RETURN_GENERATED_KEYS);
            rs = st.getGeneratedKeys();
            String IdPartida=null;
            if (rs.next()) {
                IdPartida = rs.getString(1);
            }
            
            SQL = "INSERT INTO detallespartidas (IdPartida, IdUsuario) VALUES ('" + IdPartida + "', '" + IdUsuario + "')";
            st.executeUpdate(SQL);
            
            out.println("<BR><BR><BR><BR>");
            out.println("<H2>¡Perfecto!</H2>");
            out.println("<H2>Ahora espera a que se una algun rival.</H2><BR>");
            out.println("<FORM METHOD=POST ACTION=Interfaz>"); 
            out.println("<INPUT id='volver' TYPE=SUBMIT VALUE=Volver>");
            out.println("</FORM>");
            
            out.println("</CENTER></BODY></HTML>");
            
            con.close();
            st.close();
            rs.close();
            out.close();
            
        } catch (Exception e){
            System.err.println(e);
        }
    }
}
