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
        int id_usuario;
        int id_sala;
        String accion = request.getParameter("accion");
      	//juego.Juego.empezarRonda(id_sala); //Solo lo ejecuta el primer jugador, el "host"
      	//juego.Juego.crearJugadores(id_usuario, id_sala);
        switch (accion) {
        case "nuevoJugador":
        	out.print(chat.Chat.numJugadores());
        	break;
        case "comprobarRondaEmpezada":
          	id_usuario = Integer.parseInt(request.getParameter("id_usuario"));
          	id_sala = Integer.parseInt(request.getParameter("id_sala"));
        	String esRondaEmpezada = "false";
        	//juego.Juego.crearJugadores(id_usuario, id_sala);
        	if (juego.Juego.esRondaEmpezada(id_sala)) {
        		esRondaEmpezada = "true";
        	}
        	out.print(esRondaEmpezada);
        	break;
        case "host":
          	id_usuario = Integer.parseInt(request.getParameter("id_usuario"));
          	id_sala = Integer.parseInt(request.getParameter("id_sala"));
          	juego.Juego.crearJugadores(id_usuario, id_sala);
          	juego.Juego.esperar(50);
          	if (id_usuario == juego.Juego.host(id_usuario, id_sala)) {
    			out.print("true");
          	}
          	else {
          		out.print("false");
          	}
          	juego.Juego.esperar(50);
        	break;
        case "juego":
        	id_usuario = Integer.parseInt(request.getParameter("id_usuario"));
          	id_sala = Integer.parseInt(request.getParameter("id_sala"));
        	String cartas = juego.Juego.mostrarCarta1(id_usuario, id_sala) + "@" + juego.Juego.mostrarCarta2(id_usuario, id_sala);
        	juego.Juego.esperar(100);
        	out.print(cartas);
        	break;
        case "borrarJugadores":
        	juego.Juego.actualizarRondaEmpezada(Integer.parseInt(request.getParameter("id_sala")), false);
			juego.Juego.borrarJugadores(Integer.parseInt(request.getParameter("id_sala")));
			juego.Juego.borrarJugada(Integer.parseInt(request.getParameter("id_sala")));
			juego.Juego.recargarFichas();;
			juego.Baraja.vaciarBaraja();
			juego.Juego.esperar(100);
        	break;
        }
        
        out.close();
	}

}
