import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
public class Movimientos extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
                
        Connection con;
        Statement st, st4, st3;
        ResultSet rs, rs4, rs3;
        PrintWriter out;
        String SQL, SQL2, SQL3, SQL4, IdUsuario, IdPartida, IdJugador2;
        HttpSession sesion;
        int fila, columna;
        
        try{
            sesion = req.getSession();
            IdUsuario = (String)sesion.getAttribute("IdUsuario");
            IdPartida = req.getParameter("IdPartida");
            IdJugador2 = req.getParameter("IdJugador2");
            columna = Integer.parseInt(req.getParameter("columna"));
        
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/conecta4","root","");
            st = con.createStatement();
            st4 = con.createStatement();
            
            res.setContentType("text/html;charset=UTF-8");
            out = res.getWriter();
            
            out.println("<HTML><BODY>");
            
            
            SQL = "SELECT MIN(fila) FROM tablero WHERE IdPartida='" + IdPartida + "' AND columna='" + columna + "'"; 
            rs = st.executeQuery(SQL);
            fila = 5;
            
            SQL4 = "SELECT MAX(tirada) FROM tablero WHERE IdPartida='" + IdPartida + "'";
            rs4 = st4.executeQuery(SQL4);
            
            
            while (rs.next()){
                while(rs4.next()){
                    int tirada = rs4.getInt(1) + 1;
                    
                            while (rs.getInt(1) > 0){
                            
                            fila = rs.getInt(1) - 1 ;
                            
                            
                            SQL2 = "INSERT INTO tablero (IdPartida, columna, fila, IdUsuario, tirada) VALUES ('" + IdPartida + "', '" + columna + "', '" + fila + "', '" + IdUsuario + "', '" + tirada + "')";
                            st.executeUpdate(SQL2);
                            
                            
                            
                            SQL2 = "UPDATE detallespartidas SET turno = 0 WHERE IdPartida='" + IdPartida + "' AND IdUsuario= '" + IdUsuario + "'";
                            SQL3 = "UPDATE detallespartidas SET turno = 1 WHERE IdPartida='" + IdPartida + "' AND IdUsuario= '" + IdJugador2 + "'"; 
                            con.setAutoCommit(false);
                            
                            st.executeUpdate(SQL2);
                            st.executeUpdate(SQL3);
                            
                            con.commit();
                            con.setAutoCommit(true);
                            if (tirada == 36) {
                                out.println("<FORM id='redirect' ACTION='FinalPartida' METHOD='POST'>");
                                out.println("<INPUT TYPE='hidden' NAME='IdPartida' VALUE='" + IdPartida + "'>");
                                out.println("<INPUT TYPE='hidden' NAME='IdJugador2' VALUE='" + IdJugador2 + "'>");
                                out.println("</FORM>");
                                out.println("<script>");
                                out.println("window.onload = function() {");
                                out.println(" document.getElementById('redirect').submit();");
                                out.println("};");
                                out.println("</script>");    
                            } else {
                                //NOS VAMOS
                                out.println("<FORM id='redirect' ACTION='Chess' METHOD='POST'>");
                                out.println("<INPUT TYPE='hidden' NAME='IdPartida' VALUE='" + IdPartida + "'>");
                                out.println("<INPUT TYPE='hidden' NAME='IdJugador2' VALUE='" + IdJugador2 + "'>");
                                out.println("</FORM>");
                                out.println("<script>");
                                out.println("window.onload = function() {");
                                out.println(" document.getElementById('redirect').submit();");
                                out.println("};");
                                out.println("</script>");
                            }
                        } 
                            out.println("<FORM id='redirect' ACTION='Chess' METHOD='POST'>");
                            out.println("<INPUT TYPE='hidden' NAME='IdPartida' VALUE='" + IdPartida + "'>");
                            out.println("<INPUT TYPE='hidden' NAME='IdJugador2' VALUE='" + IdJugador2 + "'>");
                            out.println("</FORM>");
                            out.println("<script>");
                            out.println("window.onload = function() {");
                            out.println(" document.getElementById('redirect').submit();");
                            out.println("};");
                            out.println("</script>");
                    
                }
            }
            
            while (!rs.next()){
                     
                        fila = fila ;
                        SQL2 = "INSERT INTO tablero (IdPartida, columna, fila, IdUsuario) VALUES ('" + IdPartida + "', '" + columna + "', '" + fila + "', '" + IdUsuario + "')";
                        st.executeUpdate(SQL2);
                        
                        
                        SQL2 = "UPDATE  detallespartidas SET turno = 0 WHERE IdPartida='" + IdPartida + "' AND IdUsuario= '" + IdUsuario + "'"; 
                        SQL3 = "UPDATE  detallespartidas SET turno = 1 WHERE IdPartida='" + IdPartida + "' AND IdUsuario= '" + IdJugador2 + "'"; 
                        
                        con.setAutoCommit(false);
                        
                        st.executeUpdate(SQL2);
                        st.executeUpdate(SQL3);
                        
                        con.commit();
                        con.setAutoCommit(true);
                        
                        //NOS VAMOS
                        out.println("<FORM id='redirect' ACTION='Chess' METHOD='POST'>");
                        out.println("<INPUT TYPE='hidden' NAME='IdPartida' VALUE='" + IdPartida + "'>");
                        out.println("<INPUT TYPE='hidden' NAME='IdJugador2' VALUE='" + IdJugador2 + "'>");
                        out.println("</FORM>");
                        out.println("<script>");
                        out.println("window.onload = function() {");
                        out.println(" document.getElementById('redirect').submit();");
                        out.println("};");
                        out.println("</script>");
                      
                    
            }
            
            out.println("</HTML></BODY>");
            
            rs.close();
            rs4.close();
            st.close();
            st4.close();
            con.close();
            out.close();
        } catch (Exception e){
            System.err.println(e);
        }
    
    }
}