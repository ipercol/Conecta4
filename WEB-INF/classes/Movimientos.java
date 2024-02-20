import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Movimientos extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        Connection con;
        Statement st,st2, st3, st4, st5, st6, st7, st8;
        ResultSet rs,rs2, rs3, rs4, rs5, rs6;
        PrintWriter out;
        String SQL, SQL2, SQL3, SQL4 ,IdPartida, IdUsuario, IdJugador2;
        String columna,fila;
        HttpSession sesion;

        try {
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(CrearCuenta.class.getName()).log(Level.SEVERE, null, ex);
            }

            sesion = req.getSession();
            IdUsuario = (String) sesion.getAttribute("IdUsuario");
            IdPartida = req.getParameter("IdPartida");
            IdJugador2 = req.getParameter("IdJugador2");
            columna = req.getParameter("columna");

            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/conecta4", "root", "");
            st = con.createStatement();
            st2 = con.createStatement();
            st3 = con.createStatement();
            st4 = con.createStatement();
            st5 = con.createStatement();
            st6 = con.createStatement();
            st7 = con.createStatement();
            st8 = con.createStatement();
            
            res.setContentType("text/html");
            out = res.getWriter();
            out.println("<HTML><BODY>");
            
            
            SQL="SELECT fila FROM tablero WHERE IdPartida='" + IdPartida + "' AND columna='" + columna+  "' ORDER BY fila";
            rs=st.executeQuery(SQL);
            fila="";
            
            //Casos posibles
            if(!rs.next()){
                //Nueva columna
                fila="5"; 
            } else if(rs.getInt(1)==0){
                //Columna completa
                out.println("<BODY><CENTER>");
                out.println("<H4>Columna completa, pruebe otra vez.<H4>");
                out.println("<FORM METHOD=POST ACTION=Chess>"); 
                out.println("<INPUT TYPE='hidden' NAME='IdPartida' VALUE='" + IdPartida + "'>");
                out.println("<INPUT TYPE='hidden' NAME='IdJugador2' VALUE='" + IdJugador2 + "'>");
                out.println("<INPUT TYPE=SUBMIT VALUE=Volver>");
                out.println("</FORM></CENTER></BODY>");
                
            }else{
                //Podemos tirar en la columna
                int pos = rs.getInt(1) - 1; 
                fila = pos+""; //Concatenamos con cadena vacia para convertir a string
            }
    
            if(!fila.equals("")){
                SQL="INSERT INTO tablero (IdPartida, columna, fila, IdUsuario) VALUES ('" + IdPartida + "','" + columna + "','"+ fila + "','" + IdUsuario + "')" ;
                st.executeUpdate(SQL);
                
                
                SQL2 = "UPDATE detallespartidas SET turno = 0 WHERE IdPartida='" + IdPartida + "' AND IdUsuario= '" + IdUsuario + "'";
                st2.executeUpdate(SQL2);
                SQL3 = "UPDATE detallespartidas SET turno = 1 WHERE IdPartida='" + IdPartida + "' AND IdUsuario= '" + IdJugador2 + "'"; 
                st3.executeUpdate(SQL3);
                
                
                //Tablero
                SQL2 = "SELECT * FROM tablero WHERE IdUsuario=" + IdUsuario + " AND IdPartida=" + IdPartida + " ORDER BY columna, fila";
                rs5 = st5.executeQuery(SQL2);
                SQL3 = "SELECT * FROM tablero WHERE IdUsuario=" + IdJugador2 + " AND IdPartida=" + IdPartida + " ORDER BY columna, fila";
                rs6 = st6.executeQuery(SQL3);
                
                
                char[][] tablero = new char[6][6];
                for (int i = 0; i < 6; i++) {
                    for (int j = 0; j < 6; j++) {
                        tablero[i][j] = '-';
                    }
                }
                
                while (rs5.next()) {
                    int col = rs5.getInt("columna");
                    int fil = rs5.getInt("fila");
                    tablero[fil][col] = 'X';
                }
                
                while (rs6.next()) {
                    int col = rs6.getInt("columna");
                    int fil = rs6.getInt("fila");
                    tablero[fil][col] = 'O';
                }
                
                //PUNTUACION
                int total = 0;
                int total2 = 0;
                int puntos = 0;
                int puntos2 = 0;
                int conecta = 1;
                
                //PUNTOS VERTICAL--------------------------------------------------
                
                for (int j = 0; j < 6; j++) {
                    puntos = 0;
                    puntos2 = 0;
                    conecta = 1;
                    for (int i = 1; i < 6; i++) {
                        if(tablero[i][j] == tablero[i-1][j] && tablero[i][j] != '-') {
                            conecta++;
                            if(conecta >= 4 && conecta <= 6){
                                if(tablero[i][j] == 'X'){
                                    puntos += 10;
                                } 
                                if(tablero[i][j] == 'O'){
                                    puntos2 += 10;
                                }
                            }
                        } else{
                            conecta = 1;
                        }
                    }
                    total += puntos;
                    total2 += puntos2;
                }
                
                   
                //PUNTOS HORIZONTAL--------------------------------------------------
                for (int i = 0; i < 6; i++) {
                    puntos = 0;
                    puntos2 = 0;
                    conecta = 1;
                    for (int j = 1; j < 6; j++) {
                        if(tablero[i][j] == tablero[i][j-1] && tablero[i][j] != '-') {
                            conecta++;
                            if(conecta>=4 && conecta <= 6){
                                if(tablero[i][j] == 'X'){
                                    puntos += 10;
                                }
                                if(tablero[i][j] == 'O'){
                                    puntos2 += 10;
                                }
                            }
                        } else{
                            conecta = 1;
                        }
                    }
                    total += puntos;
                    total2 += puntos2;
                }
            
                
                //PUNTOS DIAGONAL IZQ-DER--------------------------------------------------
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        puntos = 0;
                        puntos2 = 0;
                        conecta = 1;
                        for (int k = 1; k < 4; k++) {
                            int fil = i + k;
                            int col = j + k;
                            if (tablero[fil][col] == tablero[fil - 1][col - 1] && tablero[fil][col] != '-') {
                                conecta++;
                                if (conecta >= 4) {
                                    if (tablero[fil][col] == 'X') {
                                        puntos = puntos + 10;
                                    }
                                    if(tablero[i][j] == 'O'){
                                        puntos2 = puntos2 + 10;
                                    }
                                }
                            } else {
                                conecta = 1;
                            }
                        }
                        total = total + puntos;
                        total2 = total2 + puntos2;
                    }
                }
                
                //PUNTOS DIAGONAL DER-IZQ--------------------------------------------------
                for (int i = 0; i < 3; i++) {
                    for (int j = 5; j > 2; j--) {
                        puntos = 0;
                        puntos2 = 0;
                        conecta = 1;
                        for (int k = 1; k < 4; k++) {
                            int fil = i + k;
                            int col = j - k;
                            if (tablero[fil][col] == tablero[fil - 1][col + 1] && tablero[fil][col] != '-') {
                                conecta++;
                                if (conecta >= 4) {
                                    if (tablero[fil][col] == 'X') {
                                        puntos = puntos + 10;
                                    }
                                    if(tablero[i][j] == 'O'){
                                        puntos2 = puntos2 + 10;
                                    }
                                }
                            } else {
                                conecta = 1;
                            }
                        }
                        total = total + puntos;
                        total2 = total2 + puntos2;
                    }
                }
                
                SQL="UPDATE detallespartidas SET puntos =" + total + " WHERE IdUsuario='" + IdUsuario + "' AND IdPartida='" + IdPartida + "'";
                st7.executeUpdate(SQL);
                
                

 
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
                
                
                rs5.close();
                rs6.close();
            }
            
            st.close();
            st2.close();
            st3.close();
            st4.close();
            st5.close();
            st6.close();
            rs.close();
            con.close();
            out.close();
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}