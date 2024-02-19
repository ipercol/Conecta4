import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Puntuacion extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        Connection con;
        Statement st,st2;
        ResultSet rs=null,rs2 ;
        PrintWriter out;
        String SQL,SQL2, IdUsuario, IdPartida, IdJugador2;
        HttpSession sesion;
        out=res.getWriter();

    try{
        // Obtener el ID de usuario de la sesión
        sesion = req.getSession();
        IdUsuario = (String)sesion.getAttribute("IdUsuario");
        IdPartida=req.getParameter("IdPartida");
        IdJugador2=req.getParameter("IdJugador2");
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/conecta4","root","");
            st = con.createStatement();
            st2 = con.createStatement();
        } catch (ClassNotFoundException | SQLException e) {
            throw new ServletException("Error al conectar con la base de datos", e);
        }
        //para recorrer filas
        for(int i=0; i<6;i++){
            SQL="SELECT columna from tablero WHERE IdUsuario='" + IdUsuario + "'AND fila='" + i + "'AND IdPartida='" + IdPartida + "'";
            rs=st.executeQuery(SQL);
            int conteoConsecutivos = 0;
            int valorAnterior = -1; // Inicializado con un valor que no puede ser un resultado válido
            
                while (rs.next()) { 
                    int valorActual = rs.getInt(1);
                    
                    if (valorAnterior != -1 && valorActual - valorAnterior == 1) {
                        // Incrementar el conteo si la diferencia es 1
                        conteoConsecutivos++;
                    } else {
                        // Reiniciar el conteo si la diferencia no es 1
                        conteoConsecutivos = 1;
                    }
                    
                    // Actualizar el valor anterior
                    valorAnterior = valorActual;
                }
                if(conteoConsecutivos==4){
                    SQL2="UPDATE detallespartidas SET puntos= puntos +10 WHERE IdUsuario='" + IdUsuario + "'AND IdPartida='" + IdPartida + "'";
                    st2.executeQuery(SQL2);
                }
                else if(conteoConsecutivos==5){
                     SQL2="UPDATE detallespartidas SET puntos= puntos +20 WHERE IdUsuario='" + IdUsuario + "'AND IdPartida='" + IdPartida + "'";
                    st2.executeQuery(SQL2);
                }
                else if(conteoConsecutivos==6){
                  SQL2="UPDATE detallespartidas SET puntos= puntos +30 WHERE IdUsuario='" + IdUsuario + "'AND IdPartida='" + IdPartida + "'";
                st2.executeQuery(SQL2);
                }
                else if(conteoConsecutivos==7){
                    SQL2="UPDATE detallespartidas SET puntos= puntos +40 WHERE IdUsuario='" + IdUsuario + "'AND IdPartida='" + IdPartida + "'";
                st2.executeQuery(SQL2);
               } else{
                   int puntos = 0;
               }
                    
        }
               
        st.close();
        rs.close();
        st2.close();
        out.close();
        con.close();
        
            out.println("<FORM id='redirect' ACTION='FinalPartida' METHOD='POST'> </FORM>");
            out.println("<INPUT TYPE='hidden' NAME='IdPartida' VALUE='" + IdPartida + "'>");
            out.println("<INPUT TYPE='hidden' NAME='IdJugador2' VALUE='" + IdJugador2 + "'>");
            out.println("<script>");
            out.println("window.onload = function() {");
            out.println(" document.getElementById('redirect').submit();");
            out.println("};");
            out.println("</script>");

                
            }
             catch(Exception e){
        System.err.println(e);
        }
    }
}