package juego;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.LinkedHashMap;

public class Juego {

	protected static ArrayList<Jugador> jugadores = new ArrayList<Jugador>();
	protected static Sala sala;
	protected static int bote;

	public static void empezarRonda(int id_usuario, int id_sala) {
		if (jugadores.get(0).getId_usuario() == id_usuario) {
			crearSala(id_sala, false);
			if (sala.getNum_usuarios() > 1) {
				Baraja.crearBaraja();
				Baraja.barajar();
				crearJugada(id_sala);
				// Baraja.vaciarBaraja();
			}
		}
	}
	
	public static String[] flop(int id_sala) {
		Connection con = bd.ConexionBD.abrirConexion();
		String[] flop = {"", "", ""};
		try {
			Statement st = con.createStatement();
			String consulta = "SELECT flop1, flop2, flop3 FROM Jugadas WHERE id_sala = " + id_sala;
			ResultSet rs = st.executeQuery(consulta);
			while (rs.next()) {
				flop[0] = rs.getString("flop1");
				flop[1] = rs.getString("flop2");
				flop[2] = rs.getString("flop3");
			}
			rs.close();
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			bd.ConexionBD.cerrarConexion(con);
		}
		return flop;
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
			String consulta1 = "INSERT INTO Jugadas(flop1, flop2, flop3, turn, river, id_sala) VALUES ('" + flop1
					+ "', '" + flop2 + "', '" + flop3 + "', '" + turn + "', '" + river + "', " + id_sala + ")";
			st.executeUpdate(consulta1);
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			bd.ConexionBD.cerrarConexion(con);
		}
	}

//	public static void actualizarUsuarios(int id_sala, int num_usuarios) {
//		Connection con = bd.ConexionBD.abrirConexion();
//		try {
//			Statement st = con.createStatement();
//			String consulta = "UPDATE Salas SET num_usuarios = " + num_usuarios + " WHERE id_sala = " + id_sala;
//			st.executeUpdate(consulta);
//			st.close();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			bd.ConexionBD.cerrarConexion(con);
//		}
//		sala = new Sala(id_sala, num_usuarios);
//	}

	public static void crearSala(int id_sala, boolean ronda_empezada) {
		int num_usuarios = chat.Chat.numJugadores();
		sala = new Sala(id_sala, num_usuarios, ronda_empezada);
	}

	public static void actualizarRondaEmpezada(int id_sala, boolean ronda_empezada) {
		Connection con = bd.ConexionBD.abrirConexion();
		try {
			Statement st = con.createStatement();
			String consulta = "UPDATE Salas SET ronda_empezada = " + ronda_empezada + " WHERE id_sala = " + id_sala;
			st.executeUpdate(consulta);
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			bd.ConexionBD.cerrarConexion(con);
		}
	}

	public static boolean esRondaEmpezada(int id_sala) {
		Connection con = bd.ConexionBD.abrirConexion();
		boolean ronda_empezada = false;
		try {
			Statement st = con.createStatement();
			String consulta = "SELECT ronda_empezada FROM Salas WHERE id_sala = " + id_sala;
			ResultSet rs = st.executeQuery(consulta);
			// while (rs.next()) {
			rs.next();
			ronda_empezada = rs.getBoolean("ronda_empezada");
			// }
			rs.close();
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			bd.ConexionBD.cerrarConexion(con);
		}
		return ronda_empezada;
	}

	public static void crearJugadores(int id_usuario, int id_sala) {
		Connection con = bd.ConexionBD.abrirConexion();
		try {
			Statement st = con.createStatement();
			String consulta = "INSERT INTO Jugadores(carta1, carta2, ciega_pequena, ciega_grande, activo, ganador, id_usuario, id_sala) VALUES ('card_back', 'card_back', false, false, false, false, "
					+ id_usuario + ", " + id_sala + ")";
			st.executeUpdate(consulta);
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			bd.ConexionBD.cerrarConexion(con);
		}
	}

	public static void repartir(int id_sala) {
		Connection con = bd.ConexionBD.abrirConexion();
		int id_jugador = 0, id_usuario = 0;
		Boolean ciega_pequena = null, ciega_grande = null, activo = null, ganador = null;
		try {
			Statement st = con.createStatement();
			String consulta1 = "SELECT id_jugador, ciega_pequena, ciega_grande, activo, ganador, id_usuario FROM Jugadores WHERE id_sala = "
					+ id_sala;
			ResultSet rs = st.executeQuery(consulta1);
			String[] consultas = {"", "", "", "", "", "", "", ""};
			int cont = 0;
			while (rs.next()) {
				id_jugador = rs.getInt("id_jugador");
				ciega_pequena = rs.getBoolean("ciega_pequena");
				ciega_grande = rs.getBoolean("ciega_grande");
				activo = rs.getBoolean("activo");
				ganador = rs.getBoolean("ganador");
				id_usuario = rs.getInt("id_usuario");
				
				Carta c1 = Baraja.repartirCartas();
				String carta1 = c1.getNumero() + c1.getPalo();
				Carta c2 = Baraja.repartirCartas();
				String carta2 = c2.getNumero() + c2.getPalo();
				consultas[cont] = "UPDATE Jugadores SET carta1 = '" + carta1 + "', carta2 = '" + carta2
						+ "' WHERE id_usuario = " + id_usuario + " AND id_sala = " + id_sala;
				jugadores.add(new Jugador(id_jugador, c1, c2, ciega_pequena, ciega_grande, activo, ganador, id_usuario,
						id_sala));
				cont++;
			}
			for (int i = 0; i < cont; i++) {
				if (!consultas[i].equals("")) {
					st.executeUpdate(consultas[i]);
				}
			}
			rs.close();
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			bd.ConexionBD.cerrarConexion(con);
		}
	}
	
	public static int[] todosJugadores(int id_sala) {
		Connection con = bd.ConexionBD.abrirConexion();
		int[] ids = new int[chat.Chat.numJugadores()];
		try {
			Statement st = con.createStatement();
			String consulta = "SELECT id_jugador FROM Jugadores WHERE id_sala = " + id_sala;
			ResultSet rs = st.executeQuery(consulta);
			int cont = 0;
			while (rs.next()) {
				ids[cont] = rs.getInt("id_jugador");
				cont++;
			}
			rs.close();
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			bd.ConexionBD.cerrarConexion(con);
		}
		return ids;
	}
	
	public static String[] todosNombres(int id_sala) {
		Connection con = bd.ConexionBD.abrirConexion();
		String[] nombres = new String[chat.Chat.numJugadores()];
		try {
			Statement st = con.createStatement();
			String consulta = "SELECT nombre FROM usuarios NATURAL JOIN Jugadores WHERE id_sala = " + id_sala + " ORDER BY id_jugador";
			ResultSet rs = st.executeQuery(consulta);
			int cont = 0;
			while (rs.next()) {
				nombres[cont] = rs.getString("nombre");
				cont++;
			}
			rs.close();
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			bd.ConexionBD.cerrarConexion(con);
		}
		return nombres;
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
	
	public static void borrarJugador(int id_usuario, int id_sala) {
		Connection con = bd.ConexionBD.abrirConexion();
		try {
			Statement st = con.createStatement();
			String consulta = "DELETE FROM Jugadores WHERE id_usuario = " + id_usuario + " AND id_sala = " + id_sala;
			st.executeUpdate(consulta);
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			bd.ConexionBD.cerrarConexion(con);
		}
	}

	public static String mostrarCarta1(int id_usuario, int id_sala) {
		Connection con = bd.ConexionBD.abrirConexion();
		String carta1 = "";
		try {
			Statement st = con.createStatement();
			String consulta = "SELECT carta1 FROM Jugadores WHERE id_usuario = " + id_usuario + " AND id_sala = "
					+ id_sala;
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
			String consulta = "SELECT carta2 FROM Jugadores WHERE id_usuario = " + id_usuario + " AND id_sala = "
					+ id_sala;
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

	public static int host(int id_usuario, int id_sala) {
		Connection con = bd.ConexionBD.abrirConexion();
		int minId = 0;
		try {
			Statement st = con.createStatement();
			String consulta = "SELECT MIN(id_usuario) AS minId FROM Jugadores WHERE id_sala = " + id_sala;
			ResultSet rs = st.executeQuery(consulta);
			rs.next();
			minId = rs.getInt("minId");
			rs.close();
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			bd.ConexionBD.cerrarConexion(con);
		}
		return minId;
	}

	public static void fold(int apuesta, int id_usuario) {
		for (int i = 0; i < jugadores.size(); i++) {
			if (jugadores.get(i).getId_usuario() == id_usuario) {
				jugadores.remove(i);
				break;
			}
		}
		actualizarFichas(apuesta, id_usuario);
		borrarJugador(id_usuario, apuesta);
	}

	public static void call(int apuesta, int id_usuario) {
		actualizarFichas(apuesta, id_usuario);
	}

	public static void raise(int apuesta, int id_usuario) {
		actualizarFichas(apuesta, id_usuario);
	}

	public static void actualizarFichas(int apuesta, int id_usuario) {
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
