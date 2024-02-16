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
        
            res.setContentType("text/html;charset=UTF-8");
            out = res.getWriter();
            
            //SQL2 = "INSERT INTO tablero (IdPartida, columna, fila, IdUsuario) VALUES ('" + IdPartida + "', '" + columna + "', '" + fila + "', '" + IdUsuario + "')";
            //st.executeUpdate(SQL2);
            
            
            SQL = "SELECT MAX(fila) FROM tablero WHERE IdPartida='" + IdPartida + "' AND columna='" + columna + "'"; 
            rs = st.executeQuery(SQL);
            // Si no hay registros, establecer el valor inicial de "fila" en 0
            if (!rs.next()) {
                fila = 0;
            } else {
                fila = rs.getInt(1); // Obtener el valor máximo de "fila"
            }
            
                if (fila == 6){
                    out.println("<FORM id='redirect' ACTION='Chess' METHOD='POST'> </FORM>");
                    out.println("<script>");
                    out.println("window.onload = function() {");
                    out.println(" document.getElementById('redirect').submit();");
                    out.println("};");
                    out.println("</script>"); 
                } else{
                    fila = fila + 1;
                    SQL2 = "INSERT INTO tablero (IdPartida, columna, fila, IdUsuario) VALUES ('" + IdPartida + "', '" + columna + "', '" + fila + "', '" + IdUsuario + "')";
                    st.executeUpdate(SQL2);
                    
                    SQL2 = "UPDATE  detallespartidas SET turno = 0 WHERE IdPartida='" + IdPartida + "' AND IdUsuario= '" + IdUsuario + "'"; 
                    st.executeUpdate(SQL2);
                    
                    SQL2 = "UPDATE  detallespartidas SET turno = 1 WHERE IdPartida='" + IdPartida + "' AND IdUsuario= '" + IdJugador2 + "'"; 
                    st.executeUpdate(SQL2);
                    //NOS VAMOS
                    out.println("<FORM id='redirect' ACTION='Chess' METHOD='POST'> </FORM>");
                    out.println("<script>");
                    out.println("window.onload = function() {");
                    out.println(" document.getElementById('redirect').submit();");
                    out.println("};");
                    out.println("</script>");
                }
                        
            
            rs.close();
            st.close();
            con.close();
            out.close();
        } catch (Exception e){
            System.err.println(e);
        }
    
    }
}