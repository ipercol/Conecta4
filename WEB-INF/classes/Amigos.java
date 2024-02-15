import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class Amigos extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        Connection con ;
        Statement st ;
        ResultSet rs=null;
        PrintWriter out = res.getWriter();
        

        try {
            // Establecer conexión con la base de datos
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/conecta4", "root", "");
            st = con.createStatement();

            // Mostrar el formulario de búsqueda
            out.println("<HTML><HEAD><TITLE>Buscar Jugador</TITLE></HEAD><BODY>");
            out.println("<h1>Buscar Jugador</h1>");
            out.println("<FORM METHOD='POST' ACTION='Partida'>");
            out.println("<INPUT TYPE='text' NAME='search' placeholder='Buscar usuario'>");
            out.println("<INPUT TYPE='SUBMIT' VALUE='Buscar'>");
            out.println("</FORM>");

            // Obtener el parámetro de búsqueda
            String searchQuery = req.getParameter("search");
            if (searchQuery != null && !searchQuery.isEmpty()) {
                // Ejecutar la búsqueda en la base de datos
                String sql = "SELECT * FROM usuarios WHERE usuario LIKE ?";
                PreparedStatement pstmt = con.prepareStatement(sql);
                pstmt.setString(1, "%" + searchQuery + "%");
                rs = pstmt.executeQuery();

                // Mostrar los resultados de la búsqueda
                out.println("<h2>Resultados de la búsqueda:</h2>");
                out.println("<ul>");
                while (rs.next()) {
                    String userId = rs.getString("IdUsuario");
                    String userName = rs.getString("usuario");
                    out.println("<li>" + userName + " <a href='CrearPartida?oponente=" + userId + "'>Crear partida</a></li>");
                }
                out.println("</ul>");
            }
             
            out.println("</body></html>");
            if (rs != null) rs.close(); 
            st.close();
            con.close();
            out.close();
        } catch (Exception e) {
            throw new ServletException("Error al procesar la búsqueda", e);
        
            } 
            }
        }
    

