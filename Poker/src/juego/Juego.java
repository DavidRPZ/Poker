package juego;

import java.sql.Connection;
import java.util.ArrayList;

public class Juego {
	
	protected static ArrayList<Jugador> jugadores = new ArrayList<Jugador>();
	protected static Sala sala = new Sala(1, 8);
	
	public static void comprobarJugadores() {
		if (sala.getNum_usuarios() > 1) {
			Baraja.crearBaraja();
			Baraja.barajar();
			for (int i = 0; i < sala.getNum_usuarios(); i++) {
				jugadores.add(new Jugador());
				Carta c1 = Baraja.repartirCartas();
				Carta c2 = Baraja.repartirCartas();
			}
			Baraja.vaciarBaraja();
		}
			Connection con = bd.ConexionBD.abrirConexion();
	}
}
