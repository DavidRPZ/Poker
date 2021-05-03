package juego;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;

public class Juego {
	
	protected static HashMap<Integer, Jugador> jugadores = new HashMap<Integer, Jugador>();
	protected static Sala sala = new Sala(1, 8);
	protected static int bote;
	
	public static void comprobarJugadores() {
		if (sala.getNum_usuarios() > 1) {
			Baraja.crearBaraja();
			Baraja.barajar();
			for (int i = 0; i < sala.getNum_usuarios(); i++) {
				jugadores.put(1, new Jugador());
				Carta c1 = Baraja.repartirCartas();
				Carta c2 = Baraja.repartirCartas();
			}
			Baraja.vaciarBaraja();
		}
		Connection con = bd.ConexionBD.abrirConexion();
	}
	
	public static void fold(int apuesta, int id_jugador, int id_sala) {
		jugadores.remove(id_jugador);//probar esto
		//consulta donde se quite lo apostado
	}
	
	public static void call(int apuesta, int id_jugador, int id_sala) {
		//consulta donde se quite lo apostado
	}

	public static void raise(int apuesta, int id_jugador, int id_sala) {
		//consulta donde se quite lo apostado
	}
}
