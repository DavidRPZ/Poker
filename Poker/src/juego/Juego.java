package juego;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class Juego {

	protected static HashMap<Integer, Jugador> jugadores = new HashMap<Integer, Jugador>();
	protected static Sala sala;
	protected static int bote;

	public static void empezarRonda(int id_jugador, int id_sala) {
		crearSala(id_sala);
		if (sala.getNum_usuarios() > 1) {
			Baraja.crearBaraja();
			Baraja.barajar();
			crearJugadores(id_jugador, id_sala);
			Baraja.vaciarBaraja();
		}
	}

	public static void crearSala(int id_sala) {
		Connection con = bd.ConexionBD.abrirConexion();
		int num_usuarios = 0;
		try {
			Statement st = con.createStatement();
			String consulta = "SELECT num_usuarios FROM Salas WHERE id_sala = " + id_sala;
			ResultSet rs = st.executeQuery(consulta);
			// while (rs.next())
			// {
			rs.next();
			num_usuarios = rs.getInt("num_usuarios");
			// }
			rs.close();
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			bd.ConexionBD.cerrarConexion(con);
		}
		sala = new Sala(id_sala, num_usuarios);
	}

	public static void crearJugadores(int id_jugador, int id_sala) {
		Connection con = bd.ConexionBD.abrirConexion();
		Boolean ciega_pequeña = null, ciega_grande = null, activo = null, ganador = null;
		int id_usuario = 0;
		try {
			Statement st = con.createStatement();
			for (int i = 0; i < sala.getNum_usuarios(); i++) {
				Carta c1 = Baraja.repartirCartas();
				Carta c2 = Baraja.repartirCartas();
				String consulta1 = "INSERT INTO Jugadores VALUES (" + c1 + ", " + c2 + ", false, false, false, false, "
						+ id_jugador + ", " + id_sala + ")";
				st.executeUpdate(consulta1);
				String consulta2 = "SELECT ciega_pequeña, ciega_grande, activo, ganador, id_usuario FROM Jugadores";
				ResultSet rs = st.executeQuery(consulta2);
				while (rs.next()) {
					ciega_pequeña = rs.getBoolean("ciega_pequeña");
					ciega_grande = rs.getBoolean("ciega_grande");
					activo = rs.getBoolean("activo");
					ganador = rs.getBoolean("ganador");
					id_usuario = rs.getInt("id_usuario");
				}
				rs.close();
				jugadores.put(id_jugador, new Jugador(id_jugador, c1, c2, ciega_pequeña, ciega_grande, activo, ganador, id_usuario, id_sala));
			}
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			bd.ConexionBD.cerrarConexion(con);
		}
	}

	public static void fold(int apuesta, int id_jugador, int id_sala) {
		jugadores.remove(id_jugador);// probar esto
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
				String consulta1 = "SELECT id_usuario FROM Jugadores WHERE id_jugador = " + id_jugador
						+ " AND id_sala = " + id_sala;
				ResultSet rs = st.executeQuery(consulta1);
				rs.next();
				int id_usuario = rs.getInt("id_usuario");
				rs.close();
				String consulta2 = "UPDATE Usuarios SET fichas = fichas - " + apuesta + " WHERE id_usuario = "
						+ id_usuario;
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
