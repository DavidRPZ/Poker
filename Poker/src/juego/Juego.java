package juego;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
	}

	public static void fold(int apuesta, int id_jugador, int id_sala) {
		jugadores.remove(id_jugador);//probar esto
		actualizarFichas(apuesta, id_jugador, id_sala);
	}

	public static void call(int apuesta, int id_jugador, int id_sala) {
		actualizarFichas(apuesta, id_jugador, id_sala);
	}

	public static void raise(int apuesta, int id_jugador, int id_sala) {
		actualizarFichas(apuesta, id_jugador, id_sala);
	}
	
	public static void actualizarFichas(int apuesta, int id_jugador, int id_sala) {
		if (apuesta > 0) {
			Connection con = bd.ConexionBD.abrirConexion();
			try {
				Statement st = con.createStatement();
				String consulta1 = "SELECT id_usuario FROM Jugadores WHERE id_jugador = " + id_jugador + " AND id_sala = " + id_sala;
				ResultSet rs = st.executeQuery(consulta1);
				rs.next();
				int id_usuario = rs.getInt("id_usuario");
				rs.close();
				String consulta2 = "UPDATE Usuarios SET fichas = fichas - " + apuesta + " WHERE id_usuario = " + id_usuario;
				st.executeUpdate(consulta2);
				st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				bd.ConexionBD.cerrarConexion(con);
			}
		}
	}
	
	
}
