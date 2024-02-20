import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.logging.Level;
import java.util.logging.Logger;
public class Interfaz extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        
        Connection con;
        Statement st,st2;
        ResultSet rs,rs2 = null;
        PrintWriter out;
        String SQL,SQL2, IdUsuario, IdJugador2;
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
        out.println("<TITLE>Interfaz</TITLE>");
        //out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"css/interfaz.css\">");
        out.println("<link rel='shortcut icon' href='css/logo.jpg'></link>");
        out.println("</HEAD>");
        
        out.println("<BODY><CENTER>");
        out.println("<H1>MENU</H1><BR>");
        out.println("</CENTER>");
        
    
        out.println("<FORM ACTION='Perfil' METHOD='POST'>");
        out.println("<INPUT TYPE='SUBMIT' VALUE='PERFIL' id='perfil'>");
        out.println("</FORM>");
        
        out.println("<FORM ACTION='LogOut' METHOD='POST'>");
        out.println("<INPUT TYPE='SUBMIT' VALUE='LOGOUT' id='logout'>");
        out.println("</FORM>");

        
        out.println("<CENTER>");
        
        // Consulta SQL para obtener todas las partidas disponibles para el usuario actual
        SQL = "SELECT * FROM detallespartidas WHERE IdUsuario='" + IdUsuario + "' AND turno=1";
        rs = st.executeQuery(SQL);
        
        out.println("<div id='partidas-container'>");
        // Mostrar las partidas disponibles
        out.println("<h2>Tus partidas</h2>");
        out.println("<H4>Tu turno:</H4>");
        while (rs.next()){
            String IdPartida = rs.getString(2);
            SQL2 = "SELECT detallespartidas.IdPartida, usuarios.IdUsuario, usuarios.usuario FROM usuarios INNER JOIN detallespartidas ON "  
            + "usuarios.IdUsuario = detallespartidas.IdUsuario WHERE detallespartidas.IdPartida='" + IdPartida + "' AND usuarios.IdUsuario <> '" + IdUsuario + "'"; 
            rs2=st2.executeQuery(SQL2);
            while (rs2.next()){
                IdJugador2 = rs2.getString(2);
                out.println("<FORM ACTION='Chess' METHOD='POST'>");
                out.println("<INPUT TYPE='hidden' NAME='IdPartida' VALUE='" + rs.getString(2) + "'>");
                out.println("<INPUT TYPE='hidden' NAME='IdJugador2' VALUE='" + rs2.getString(2) + "'>");
                out.println("<BUTTON id='entrar' TYPE='SUBMIT'>" + rs.getString(2) + ".Tú contra " + rs2.getString(3) + "</BUTTON></FORM>");
            }
            rs2.close();
        }
        rs.close();
        
        out.println("<H4>Turno del rival:</H4>");
        SQL = "SELECT * FROM detallespartidas WHERE IdUsuario='" + IdUsuario + "' AND turno=0";
        rs = st.executeQuery(SQL);
        while (rs.next()){
            String IdPartida = rs.getString(2);
            SQL2 = "SELECT detallespartidas.IdPartida, usuarios.IdUsuario, usuarios.usuario FROM usuarios INNER JOIN detallespartidas ON "  
            + "usuarios.IdUsuario = detallespartidas.IdUsuario WHERE detallespartidas.IdPartida='" + IdPartida + "' AND usuarios.IdUsuario <> '" + IdUsuario + "'"; 
            rs2=st2.executeQuery(SQL2);
            while (rs2.next()){
                IdJugador2 = rs2.getString(2);
                out.println("<FORM ACTION='Chess' METHOD='POST'>");
                out.println("<INPUT TYPE='hidden' NAME='IdPartida' VALUE='" + rs.getString(2) + "'>");
                out.println("<INPUT TYPE='hidden' NAME='IdJugador2' VALUE='" + rs2.getString(2) + "'>");
                out.println("<BUTTON id='entrar' TYPE='SUBMIT'>" + rs.getString(2) + " .Tú contra " + rs2.getString(3) + "</BUTTON></FORM>");
            }
            rs2.close();
        }
        rs.close();
        out.println("</div>");
        
        out.println("<FORM ACTION='BuscarPartida' METHOD='POST'>");
        out.println("<INPUT TYPE='SUBMIT' VALUE='BUSCAR PARTIDA'>");
        out.println("</FORM>");
   
        
        //BOTON CREAR PARTIDA
        out.println("<FORM ACTION='CrearPartida' METHOD='POST'>");
        out.println("<INPUT TYPE='SUBMIT' VALUE='CREAR PARTIDA'>");
        out.println("</FORM>");

        out.println("</BODY></HTML>");
        
        rs.close();
        rs2.close();
        st.close();
        st2.close();
        out.close();
        con.close();
    } catch(Exception e){
        System.err.println(e);
        }
    }
}