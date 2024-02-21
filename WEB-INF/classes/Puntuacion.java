import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Puntuacion extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        Connection con;
        Statement st,st2,st3;
        ResultSet rs,rs2,rs3;
        PrintWriter out;
        HttpSession sesion;
        String SQL,SQL2,SQL3,Usuario1,Usuario2 ;
        String IdPartida, IdUsuario, IdJugador2;

        try {
            sesion = req.getSession();
            IdUsuario = (String)sesion.getAttribute("IdUsuario");
            IdPartida = req.getParameter("IdPartida");
            IdJugador2 = req.getParameter("IdJugador2");
            try {
                    Class.forName("com.mysql.jdbc.Driver");
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(CrearCuenta.class.getName()).log(Level.SEVERE, null, ex);
                }
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/conecta4", "root", "");
            st = con.createStatement();
            st2=con.createStatement();
            st3=con.createStatement();
            
            res.setContentType("text/html;charset=UTF-8");
            out = res.getWriter();
            out.println("<HTML><BODY>");
            out.println("<link rel='shortcut icon' href='css/logo.jpg'></link>");
            out.println("<TITLE>Puntuacion</TITLE>");
            out.println("<CENTER>");

            SQL2 = "SELECT puntos FROM detallespartidas WHERE Idpartida='" + IdPartida + "' AND IdUsuario='" + IdUsuario + "'";
            rs2 = st2.executeQuery(SQL2);
            rs2.next(); 
            int puntosUsuario1 = rs2.getInt(1);
            rs2.close();
            
            SQL3 = "SELECT puntos FROM detallespartidas WHERE Idpartida='" + IdPartida + "' AND IdUsuario='" + IdJugador2 + "'";
            rs3 = st3.executeQuery(SQL3);
            rs3.next(); 
            int puntosUsuario2 = rs3.getInt(1);
            rs3.close();
            
            if (puntosUsuario1 > puntosUsuario2) {
                SQL="UPDATE usuarios SET victorias=victorias+1 WHERE IdUsuario="+IdUsuario;
                st2.executeUpdate(SQL);
                SQL="UPDATE clientes SET derrotas=derrotas+1 WHERE IdUsuario="+IdJugador2;
                st3.executeUpdate(SQL);
            } else if (puntosUsuario1 == puntosUsuario2) {
                SQL="UPDATE usuarios SET empates=empates+1 WHERE IdUsuario="+IdUsuario;
                st2.executeUpdate(SQL);
                SQL="UPDATE usuarios SET empates=empates+1 WHERE IdUsuario="+IdJugador2;
                st3.executeUpdate(SQL);
            } else {
                SQL="UPDATE usuarios SET derrotas=derrotas+1 WHERE IdUsuario="+IdUsuario;
                st2.executeUpdate(SQL);
                SQL="UPDATE usuarios SET victorias=victorias+1 WHERE IdUsuario="+IdJugador2;
                st3.executeUpdate(SQL);

            }
            
            out.println("<FORM id='redirect' ACTION='FinalPartida' METHOD='POST'>");
            out.println("<INPUT TYPE='hidden' NAME='IdPartida' VALUE='" + IdPartida + "'>");
            out.println("<INPUT TYPE='hidden' NAME='IdJugador2' VALUE='" + IdJugador2 + "'>");
            out.println("</FORM>");
            //script
            out.println("<script>");
            out.println("window.onload = function() {");
            out.println(" document.getElementById('redirect').submit();");
            out.println("};");
            out.println("</script>");
            
           
            out.println("</BODY></HTML>");
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}
