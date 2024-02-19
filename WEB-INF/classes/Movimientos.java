import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
public class Movimientos extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
                
        Connection con;
        Statement st, st2, st4, st3;
        ResultSet rs, rs2, rs4, rs3;
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
            st2 = con.createStatement();
            st4 = con.createStatement();
            
            res.setContentType("text/html;charset=UTF-8");
            out = res.getWriter();
            
            out.println("<HTML><BODY>");
            
            
            SQL = "SELECT MIN(fila) FROM tablero WHERE IdPartida='" + IdPartida + "' AND columna='" + columna + "'"; 
            rs = st.executeQuery(SQL);
            fila = 5;
            
            
            while (rs.next()){
                SQL4 = "SELECT COUNT(*) FROM tablero WHERE IdPartida='" + IdPartida + "'";
                rs4 = st4.executeQuery(SQL4);
                while(rs4.next()){
                    int tirada = rs4.getInt(1) + 1;
                        while (rs.getInt(1) > 0){
                        
                        fila = rs.getInt(1) - 1 ;
                        
                        //INSERT
                        SQL2 = "INSERT INTO tablero (IdPartida, columna, fila, IdUsuario, tirada) VALUES ('" + IdPartida + "', '" + columna + "', '" + fila + "', '" + IdUsuario + "', '" + tirada + "')";
                        st.executeUpdate(SQL2);
                        //UPDATE
                        SQL2 = "UPDATE detallespartidas SET turno = 0 WHERE IdPartida='" + IdPartida + "' AND IdUsuario= '" + IdUsuario + "'";
                        SQL3 = "UPDATE detallespartidas SET turno = 1 WHERE IdPartida='" + IdPartida + "' AND IdUsuario= '" + IdJugador2 + "'"; 
                        
                        con.setAutoCommit(false);
                        st.executeUpdate(SQL2);
                        st.executeUpdate(SQL3);
                        con.commit();
                        con.setAutoCommit(true);
                        
                        //PUNTOS VERTICAL--------------------------------------------------
                        //Seleccionamos todas las filas (y mas cosas) en las que un jugador ha puesto ficha para cierta columna
                         if (fila < 3){
                            int cuenta = fila + 3;
                            SQL = "SELECT COUNT(fila) FROM tablero WHERE IdUsuario='" + IdUsuario + "' AND IdPartida='" + IdPartida + "'AND fila <= '" + cuenta + "' AND columna='" + columna + "'";
                            rs2 = st2.executeQuery(SQL);
                            while (rs2.next()){
                                int conecta = rs2.getInt(1);
                                if(conecta == 4){
                                  SQL2 = "UPDATE detallespartidas SET puntos=puntos + 10 WHERE IdPartida='" + IdPartida + "' AND IdUsuario='" + IdUsuario + "'";
                                  st2.executeUpdate(SQL2);
                                  break;
                                } else {
                                    conecta = conecta;
                                }
                            }
                            rs2.close();
                        }
                        
                        //PUNTOS HORIZONTAL--------------------------------------------------
                         if (columna < 3){
                            int cuenta = columna + 3;
                            SQL = "SELECT COUNT(fila) FROM tablero WHERE IdUsuario='" + IdUsuario + "' AND IdPartida='" + IdPartida + "'AND fila = '" + fila + "' AND columna <='" + cuenta + "'";
                            rs2 = st2.executeQuery(SQL);
                            while (rs2.next()){
                                int conecta = rs2.getInt(1);
                                if(conecta == 4){
                                  SQL2 = "UPDATE detallespartidas SET puntos=puntos + 10 WHERE IdPartida='" + IdPartida + "' AND IdUsuario='" + IdUsuario + "'";
                                  st2.executeUpdate(SQL2);
                                  break;
                                } else {
                                    conecta = conecta;
                                }
                            }
                            rs2.close();
                        } else if (columna > 3){
                            int cuenta = columna - 3;
                            SQL = "SELECT COUNT(fila) FROM tablero WHERE IdUsuario='" + IdUsuario + "' AND IdPartida='" + IdPartida + "'AND fila = '" + fila + "' AND columna <='" + cuenta + "'";
                            rs2 = st2.executeQuery(SQL);
                            while (rs2.next()){
                                int conecta = rs2.getInt(1);
                                if(conecta == 4){
                                  SQL2 = "UPDATE detallespartidas SET puntos=puntos + 10 WHERE IdPartida='" + IdPartida + "' AND IdUsuario='" + IdUsuario + "'";
                                  st2.executeUpdate(SQL2);
                                  break;
                                } else {
                                    conecta = conecta;
                                }
                            }
                            rs2.close();
                        } else{
                            int cuenta = columna + 2;
                            SQL = "SELECT COUNT(fila) FROM tablero WHERE IdUsuario='" + IdUsuario + "' AND IdPartida='" + IdPartida + "'AND fila = '" + fila + "' AND columna <='" + cuenta + "'";
                            rs2 = st2.executeQuery(SQL);
                            while (rs2.next()){
                                int conecta = rs2.getInt(1);
                                if(conecta == 4){
                                  SQL2 = "UPDATE detallespartidas SET puntos=puntos + 10 WHERE IdPartida='" + IdPartida + "' AND IdUsuario='" + IdUsuario + "'";
                                  st2.executeUpdate(SQL2);
                                  break;
                                } else {
                                    conecta = conecta;
                                }
                            }
                            rs2.close();
                            cuenta = columna - 2;
                            SQL = "SELECT COUNT(fila) FROM tablero WHERE IdUsuario='" + IdUsuario + "' AND IdPartida='" + IdPartida + "'AND fila = '" + fila + "' AND columna <='" + cuenta + "'";
                            rs2 = st2.executeQuery(SQL);
                            while (rs2.next()){
                                int conecta = rs2.getInt(1);
                                if(conecta == 4){
                                  SQL2 = "UPDATE detallespartidas SET puntos=puntos + 10 WHERE IdPartida='" + IdPartida + "' AND IdUsuario='" + IdUsuario + "'";
                                  st2.executeUpdate(SQL2);
                                  break;
                                } else {
                                    conecta = conecta;
                                }
                            }
                            rs2.close();
                        }
                        
                        if (tirada == 42) {
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
                        } else {
                            //REDIRECT
                            out.println("<FORM id='redirect' ACTION='Chess' METHOD='POST'>");
                            out.println("<INPUT TYPE='hidden' NAME='IdPartida' VALUE='" + IdPartida + "'>");
                            out.println("<INPUT TYPE='hidden' NAME='IdJugador2' VALUE='" + IdJugador2 + "'>");
                            out.println("</FORM>");
                            //script
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
                    //script
                    out.println("<script>");
                    out.println("window.onload = function() {");
                    out.println(" document.getElementById('redirect').submit();");
                    out.println("};");
                    out.println("</script>");
                    
                }
                rs4.close();
            }
            
            while (!rs.next()){
                     SQL4 = "SELECT COUNT(*) FROM tablero WHERE IdPartida='" + IdPartida + "'";
                     rs4 = st4.executeQuery(SQL4);
                     while (rs4.next()){
                        fila = fila ;
                        int tirada = rs4.getInt(1) + 1;
                        //INSERT
                        SQL2 = "INSERT INTO tablero (IdPartida, columna, fila, IdUsuario, tirada) VALUES ('" + IdPartida + "', '" + columna + "', '" + fila + "', '" + IdUsuario + "', '" + tirada + "')";
                        st.executeUpdate(SQL2);
                        //UPDATE
                        SQL2 = "UPDATE  detallespartidas SET turno = 0 WHERE IdPartida='" + IdPartida + "' AND IdUsuario= '" + IdUsuario + "'"; 
                        SQL3 = "UPDATE  detallespartidas SET turno = 1 WHERE IdPartida='" + IdPartida + "' AND IdUsuario= '" + IdJugador2 + "'"; 
                        con.setAutoCommit(false);
                        st.executeUpdate(SQL2);
                        st.executeUpdate(SQL3);
                        con.commit();
                        con.setAutoCommit(true);
                        
                        //PUNTOS HORIZONTAL--------------------------------------------------
                         if (columna < 3){
                            int cuenta = columna + 3;
                            SQL = "SELECT COUNT(fila) FROM tablero WHERE IdUsuario='" + IdUsuario + "' AND IdPartida='" + IdPartida + "'AND fila = '" + fila + "' AND columna <='" + cuenta + "'";
                            rs2 = st2.executeQuery(SQL);
                            while (rs2.next()){
                                int conecta = rs2.getInt(1);
                                if(conecta == 4){
                                  SQL2 = "UPDATE detallespartidas SET puntos=puntos + 10 WHERE IdPartida='" + IdPartida + "' AND IdUsuario='" + IdUsuario + "'";
                                  st2.executeUpdate(SQL2);
                                  break;
                                } else {
                                    conecta = conecta;
                                }
                            }
                            rs2.close();
                        } else if (columna > 3){
                            int cuenta = columna - 3;
                            SQL = "SELECT COUNT(fila) FROM tablero WHERE IdUsuario='" + IdUsuario + "' AND IdPartida='" + IdPartida + "'AND fila = '" + fila + "' AND columna <='" + cuenta + "'";
                            rs2 = st2.executeQuery(SQL);
                            while (rs2.next()){
                                int conecta = rs2.getInt(1);
                                if(conecta == 4){
                                  SQL2 = "UPDATE detallespartidas SET puntos=puntos + 10 WHERE IdPartida='" + IdPartida + "' AND IdUsuario='" + IdUsuario + "'";
                                  st2.executeUpdate(SQL2);
                                  break;
                                } else {
                                    conecta = conecta;
                                }
                            }
                            rs2.close();
                        } else{
                            int cuenta = columna + 2;
                            SQL = "SELECT COUNT(fila) FROM tablero WHERE IdUsuario='" + IdUsuario + "' AND IdPartida='" + IdPartida + "'AND fila = '" + fila + "' AND columna <='" + cuenta + "'";
                            rs2 = st2.executeQuery(SQL);
                            while (rs2.next()){
                                int conecta = rs2.getInt(1);
                                if(conecta == 4){
                                  SQL2 = "UPDATE detallespartidas SET puntos=puntos + 10 WHERE IdPartida='" + IdPartida + "' AND IdUsuario='" + IdUsuario + "'";
                                  st2.executeUpdate(SQL2);
                                  break;
                                } else {
                                    conecta = conecta;
                                }
                            }
                        
                        }                        
                        //REDIRECT
                        out.println("<FORM id='redirect' ACTION='Chess' METHOD='POST'>");
                        out.println("<INPUT TYPE='hidden' NAME='IdPartida' VALUE='" + IdPartida + "'>");
                        out.println("<INPUT TYPE='hidden' NAME='IdJugador2' VALUE='" + IdJugador2 + "'>");
                        out.println("</FORM>");
                        //script
                        out.println("<script>");
                        out.println("window.onload = function() {");
                        out.println(" document.getElementById('redirect').submit();");
                        out.println("};");
                        out.println("</script>");
                     }
                        
  
            }
            
            
            
            
            
            
            
            out.println("</HTML></BODY>");
            
            rs.close();
            st.close();
            st4.close();
            con.close();
            out.close();
        } catch (Exception e){
            System.err.println(e);
        }
    }
}