import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class Partida extends HttpServlet {
    private static final int FILAS = 6;
    private static final int COLUMNAS = 7;
    private char[][] tablero = new char[FILAS][COLUMNAS];
    private char jugadorActual = 'X'; // Jugador por defecto

    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        
        res.setContentType("text/html;charset=UTF-8");
        PrintWriter out = res.getWriter();

        // Generar el tablero
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Conecta4 - Tablero</title>");
        out.println("</head>");
        out.println("<body><CENTER>");
        out.println("<BR><BR><BR><BR><BR>");

        // Imprimir tablero
        imprimirTablero(out);

        // Formulario para elegir la columna
        out.println("<form METHOD='POST'>");
        out.println("<input type='hidden' name='jugador' value='" + jugadorActual + "'>");
        for (int i = 0; i < COLUMNAS; i++) {
            out.println("<input type='submit' name='columna' value='C" + (i + 1) + "'>");
        }
        out.println("</form>");

        out.println("</body>");
        out.println("</html>");

        out.close();
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        // Obtener la columna seleccionada por el usuario
        int columna = Integer.parseInt(req.getParameter("columna").substring(1)) - 1;
        char jugador = req.getParameter("jugador").charAt(0);

        // Agregar la ficha en la columna seleccionada
        for (int i = FILAS - 1; i >= 0; i--) {
            if (tablero[i][columna] == 0) {
                tablero[i][columna] = jugador;
                break;
            }
        }

        // Cambiar de jugador
        jugadorActual = (jugador == 'X') ? 'O' : 'X';

        // Redirigir de nuevo al doGet para actualizar la página
        res.sendRedirect(req.getRequestURI());
    }

    private void imprimirTablero(PrintWriter out) {
        out.println("<TABLE BORDER=\"1\">");
        for (int i = 0; i < FILAS; i++) {
            out.println("<TR>");
            for (int j = 0; j < COLUMNAS; j++) {
                out.print("<TD WIDTH=\"50\" HEIGHT=\"50\">");
                if (tablero[i][j] != 0) {
                    out.print(tablero[i][j]);
                }
                out.println("</TD>");
            }
            out.println("</TR>");
        }
        out.println("</TABLE>");
    }
}

