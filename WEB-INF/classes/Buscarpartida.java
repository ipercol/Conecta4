import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BuscarPartida extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        Connection con;
        Statement st,st2;
        ResultSet rs,rs2 = null;
        PrintWriter out;
        String SQL,SQL2, IdUsuario, IdPartida, IdJugador2;
        HttpSession sesion;

    try{
        sesion = req.getSession();
        IdUsuario = (String)sesion.getAttribute("IdUsuario");
        try {
                    Class.forName("com.mysql.jdbc.Driver");
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(CrearCuenta.class.getName()).log(Level.SEVERE, null, ex);
                }
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/conecta4","root","");
        st = con.createStatement();
        st2 = con.createStatement();
        
        out = res.getWriter();
        res.setContentType("text/html");
        out.println("<HTML><HEAD>");
        //out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"css/inicio.css\">");
        out.println("<link rel='shortcut icon' href='css/logo.jpg'></link>");
        out.println("<TITLE>BuscarPartida</TITLE>");
        out.println("</HEAD>");
        
        out.println("<BODY><CENTER>");
        out.println("<BR><BR>");
        out.println("<H1>CONECTA 4444</H1>");
        
        out.println("<FORM METHOD = 'POST' ACTION = 'Perfil'>");
        out.println("<INPUT TYPE='SUBMIT' VALUE='Perfil'>");
        out.println("</FORM>");
        
        out.println("<div id='triangle'></div>"); 
        out.println("<FORM ACTION='Interfaz' METHOD='POST'>");
        out.println("<BUTTON TYPE='SUBMIT'>Volver</BUTTON>");
        out.println("</FORM>");
        
        // Consulta SQL para obtener todas las partidas disponibles para el usuario
        SQL = "SELECT * FROM partidas WHERE full=0 AND IdPartida NOT IN (SELECT IdPartida FROM detallespartidas WHERE IdUsuario='" + IdUsuario + "')";
        rs = st.executeQuery(SQL);
        out.println("<h2>Partidas disponibles:</h2>");
        if (!rs.next()){
            out.println("No tienes ninguna partida disponible.");
        } else{
            do{
                IdPartida = rs.getString(1);
                out.println("<FORM ACTION='Inscripcion' METHOD='POST'>");
                out.println("<INPUT TYPE='hidden' NAME='IdPartida' VALUE='" + IdPartida + "'>");
                out.println("<INPUT TYPE='hidden' NAME='IdUsuario' VALUE='" + IdUsuario + "'>");
                out.println("<BUTTON id='entrar' TYPE='SUBMIT'>Partida " + IdPartida + "</BUTTON></FORM>");
            }
            while (rs.next());
        }
        rs.close();

        out.println("</BODY></HTML>");
        
        st.close();
        st2.close();
        out.close();
        con.close();
    } catch(Exception e){
        System.err.println(e);
        }
    }
}