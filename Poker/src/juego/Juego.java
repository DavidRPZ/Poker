package juego;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class Juego {

	protected static ArrayList<Jugador> jugadores = new ArrayList<Jugador>();
	protected static Sala sala;
	protected static int bote;

	public static void empezarRonda(int id_usuario, int id_sala) {
		if (jugadores.get(jugadores.size() - 1).getId_usuario() == id_usuario) {
			crearSala(id_sala);
			if (sala.getNum_usuarios() > 1) {
				Baraja.crearBaraja();
				Baraja.barajar();
				crearJugada(id_sala);
				// Baraja.vaciarBaraja();
			}
		}
	}
	
	public static void crearJugada(int id_sala) {
		Connection con = bd.ConexionBD.abrirConexion();
		try {
			Statement st = con.createStatement();
			Carta carta1 = Baraja.repartirCartas();
			String flop1 = carta1.getNumero() + carta1.getPalo();
			Carta carta2 = Baraja.repartirCartas();
			String flop2 = carta2.getNumero() + carta2.getPalo();
			Carta carta3 = Baraja.repartirCartas();
			String flop3 = carta3.getNumero() + carta3.getPalo();
			Carta carta4 = Baraja.repartirCartas();
			String turn = carta4.getNumero() + carta4.getPalo();
			Carta carta5 = Baraja.repartirCartas();
			String river = carta5.getNumero() + carta5.getPalo();
			String consulta1 = "INSERT INTO Jugadores(flop1, flop2, flop3, turn, river, id_sala) VALUES ('" + flop1
					+ "', '" + flop2 + "', '" + flop3 + "', '" + turn + "', '" + river + "', " + id_sala + ")";
			st.executeUpdate(consulta1);
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			bd.ConexionBD.cerrarConexion(con);
		}
	}

	public static void actualizarUsuarios(int id_sala, int num_usuarios) {
		Connection con = bd.ConexionBD.abrirConexion();
		try {
			Statement st = con.createStatement();
			String consulta = "UPDATE Salas SET num_usuarios = " + num_usuarios + " WHERE id_sala = " + id_sala;
			st.executeUpdate(consulta);
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			bd.ConexionBD.cerrarConexion(con);
		}
		sala = new Sala(id_sala, num_usuarios);
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

	public static void crearJugadores(int id_usuario, int id_sala) {
		Connection con = bd.ConexionBD.abrirConexion();
		int id_jugador = 0;
		Boolean ciega_pequena = null, ciega_grande = null, activo = null, ganador = null;
		try {
			Statement st = con.createStatement();
			for (int i = 0; i < sala.getNum_usuarios(); i++) {
				Carta c1 = Baraja.repartirCartas();
				Carta c2 = Baraja.repartirCartas();
				String carta1 = c1.getNumero() + c1.getPalo();
				String carta2 = c2.getNumero() + c2.getPalo();
				String consulta1 = "INSERT INTO Jugadores(carta1, carta2, activo, ganador, id_usuario, id_sala) VALUES ('" + carta1 + "', '" + carta2 + "', false, false, false, false, "
						+ id_usuario + ", " + id_sala + ")";
				st.executeUpdate(consulta1);
				String consulta2 = "SELECT id_jugador, ciega_pequeña, ciega_grande, activo, ganador FROM Jugadores WHERE id_usuario = " + id_usuario + "AND id_sala = " + id_sala;
				ResultSet rs = st.executeQuery(consulta2);
				while (rs.next()) {
					id_jugador = rs.getInt("id_jugador");
					ciega_pequena = rs.getBoolean("ciega_pequeña");
					ciega_grande = rs.getBoolean("ciega_grande");
					activo = rs.getBoolean("activo");
					ganador = rs.getBoolean("ganador");
				}
				rs.close();
				jugadores.add(new Jugador(id_jugador, c1, c2, ciega_pequena, ciega_grande, activo, ganador, id_usuario, id_sala));
			}
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			bd.ConexionBD.cerrarConexion(con);
		}
	}
	
	public static void borrarJugadores(int id_sala) {
		Connection con = bd.ConexionBD.abrirConexion();
		try {
			Statement st = con.createStatement();
			String consulta = "DELETE FROM Jugadores WHERE id_sala = " + id_sala;
			st.executeUpdate(consulta);
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			bd.ConexionBD.cerrarConexion(con);
		}
		jugadores.clear();
	}
	
	public static String mostrarCarta1(int id_usuario, int id_sala) {
		Connection con = bd.ConexionBD.abrirConexion();
		String carta1 = "";
		try {
			Statement st = con.createStatement();
			String consulta = "SELECT carta1 FROM Jugadores WHERE id_usuario = " + id_usuario + " AND id_sala = " + id_sala;
			ResultSet rs = st.executeQuery(consulta);
			rs.next();
			carta1 = rs.getString("carta1");
			rs.close();
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			bd.ConexionBD.cerrarConexion(con);
		}
		return carta1;
	}
	
	public static String mostrarCarta2(int id_usuario, int id_sala) {
		Connection con = bd.ConexionBD.abrirConexion();
		String carta2 = "";
		try {
			Statement st = con.createStatement();
			String consulta = "SELECT carta2 FROM Jugadores WHERE id_usuario = " + id_usuario + " AND id_sala = " + id_sala;
			ResultSet rs = st.executeQuery(consulta);
			rs.next();
			carta2 = rs.getString("carta2");
			rs.close();
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			bd.ConexionBD.cerrarConexion(con);
		}
		return carta2;
	}

	public static void fold(int apuesta, int id_usuario, int id_sala) {
		for (int i = 0; i < jugadores.size(); i++) {
			if (jugadores.get(i).getId_usuario() == id_usuario) {
				jugadores.remove(i);
				break;
			}
		}
		actualizarFichas(apuesta, id_usuario, id_sala);
	}

	public static void call(int apuesta, int id_usuario, int id_sala) {
		actualizarFichas(apuesta, id_usuario, id_sala);
	}

	public static void raise(int apuesta, int id_usuario, int id_sala) {
		actualizarFichas(apuesta, id_usuario, id_sala);
	}

	public static void actualizarFichas(int apuesta, int id_usuario, int id_sala) {
		if (apuesta > 0) {
			Connection con = bd.ConexionBD.abrirConexion();
			try {
				Statement st = con.createStatement();
				String consulta = "UPDATE Usuarios SET fichas = fichas - " + apuesta + " WHERE id_usuario = "
						+ id_usuario;
				st.executeUpdate(consulta);
				st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				bd.ConexionBD.cerrarConexion(con);
			}
		}
	}

}
