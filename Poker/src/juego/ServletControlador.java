package juego;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ServletControlador")
public class ServletControlador extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public ServletControlador() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		//int apuesta = Integer.parseInt(request.getParameter("apuesta"));
		//String jugada = request.getParameter("jugada");
		//int id_jugador = Integer.parseInt(request.getParameter("id_jugador"));
		//int id_sala = Integer.parseInt(request.getParameter("id_sala"));
		//juego.Juego.empezarRonda(id_sala);
		
		//response.setContentType("text/html");
        //ServletInputStream in = request.getInputStream();
        PrintWriter out = response.getWriter();
        
        String accion = request.getParameter("accion");
        int id_usuario = Integer.parseInt(request.getParameter("id_usuario"));
      	int id_sala = Integer.parseInt(request.getParameter("id_sala"));
      	//juego.Juego.empezarRonda(id_sala); //Solo lo ejecuta el primer jugador, el "host"
      	//juego.Juego.crearJugadores(id_usuario, id_sala);
        switch (accion) {
        case "nuevoJugador":
        	out.print(chat.Chat.numJugadores());
        	break;
        case "juego":
        	String cartas = juego.Juego.mostrarCarta1(id_usuario, id_sala) + "/" + juego.Juego.mostrarCarta2(id_usuario, id_sala);
        	out.print(cartas);
        	break;
        case "fold":
        	//juego.Juego.fold(apuesta, id_jugador, id_sala);
        	out.print("<br>Ha elegido fold");
        	break;
        case "call":
        	//juego.Juego.call(apuesta, id_jugador, id_sala);
        	out.print("<br>Ha elegido call");
        	break;
        case "raise":
        	//juego.Juego.raise(apuesta, id_jugador, id_sala);
        	out.print("<br>Ha elegido raise");
        	break;
        }
        
        out.close();
	}

}
