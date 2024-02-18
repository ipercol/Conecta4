import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class LogOut extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            try{
            HttpSession session = request.getSession(false);
    
            if (session != null) {
                session.invalidate(); // Invalida la sesión
            }
    
            response.sendRedirect("Inicio"); // Redirige a la página de inicio u otra página deseada
        } catch (Exception e){
            System.err.println(e);
        }
    }
}
