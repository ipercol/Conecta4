import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class Login extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Obtener parámetros del formulario
        String usuario = request.getParameter("usuario");
        String password = request.getParameter("password");

        // Validar las credenciales del usuario (por ejemplo, aquí podrías comparar con credenciales almacenadas en una base de datos)
        if (validarCredenciales(usuario, password)) {
            // Si las credenciales son válidas, redirigir al usuario a la página de juego
            response.sendRedirect("juego.jsp"); // Cambia "juego.jsp" por la URL de la página de juego
        } else {
            // Si las credenciales no son válidas, mostrar un mensaje de error o redirigir al usuario de vuelta a la página de inicio de sesión
            response.sendRedirect("inicio.jsp?error=credenciales"); // Cambia "inicio.jsp" por la URL de la página de inicio de sesión
        }
    }

    // Método para validar las credenciales del usuario (ejemplo, puede ser diferente en tu aplicación)
    private boolean validarCredenciales(String usuario, String password) {
        // Aquí podrías implementar la lógica para validar las credenciales del usuario
        // Por ejemplo, puedes comparar con credenciales almacenadas en una base de datos
        // En este ejemplo, simplemente verificamos si el usuario y la contraseña no están vacíos
        return usuario != null && !usuario.isEmpty() && password != null && !password.isEmpty();
    }
}
