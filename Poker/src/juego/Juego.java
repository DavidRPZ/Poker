package juego;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
//import java.util.HashMap;
//import java.util.LinkedHashMap;

public class Juego {

	protected static ArrayList<Jugador> jugadores = new ArrayList<Jugador>();
	protected static Sala sala;
	protected static ArrayList<Carta> jugada = new ArrayList<Carta>();

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
		String[] flop = { "", "", "" };
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

	public static String turn(int id_sala) {
		Connection con = bd.ConexionBD.abrirConexion();
		String turn = "";
		try {
			Statement st = con.createStatement();
			String consulta = "SELECT turn FROM Jugadas WHERE id_sala = " + id_sala;
			ResultSet rs = st.executeQuery(consulta);
			rs.next();
			turn = rs.getString("turn");
			rs.close();
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			bd.ConexionBD.cerrarConexion(con);
		}
		return turn;
	}

	public static String river(int id_sala) {
		Connection con = bd.ConexionBD.abrirConexion();
		String river = "";
		try {
			Statement st = con.createStatement();
			String consulta = "SELECT river FROM Jugadas WHERE id_sala = " + id_sala;
			ResultSet rs = st.executeQuery(consulta);
			rs.next();
			river = rs.getString("river");
			rs.close();
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			bd.ConexionBD.cerrarConexion(con);
		}
		return river;
	}

	public static void crearJugada(int id_sala) {
		Connection con = bd.ConexionBD.abrirConexion();
		try {
			Statement st = con.createStatement();
			Carta carta1 = Baraja.repartirCartas();
			jugada.add(carta1);
			String flop1 = carta1.getNumero() + carta1.getPalo();
			Carta carta2 = Baraja.repartirCartas();
			jugada.add(carta2);
			String flop2 = carta2.getNumero() + carta2.getPalo();
			Carta carta3 = Baraja.repartirCartas();
			jugada.add(carta3);
			String flop3 = carta3.getNumero() + carta3.getPalo();
			Carta carta4 = Baraja.repartirCartas();
			jugada.add(carta4);
			String turn = carta4.getNumero() + carta4.getPalo();
			Carta carta5 = Baraja.repartirCartas();
			jugada.add(carta5);
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

//	public static void actualizarCiegas(int id_sala, int empieza) {
//		Connection con = bd.ConexionBD.abrirConexion();
//		String consulta1 = "";
//		String consulta2 = "";
//		int ciega_pequena = 0;
//		int ciega_grande = 0;
//		if (empieza > 1) {
//			consulta1 = "UPDATE Jugadores SET ciega_grande = true WHERE id_usuario = "
//					+ jugadores.get(empieza - 1).getId_usuario() + " AND id_sala = " + id_sala;
//			consulta2 = "UPDATE Jugadores SET ciega_pequena = true WHERE id_usuario = "
//					+ jugadores.get(empieza - 2).getId_usuario() + " AND id_sala = " + id_sala;
//		} else {
//			if (empieza == 1) {
//				consulta1 = "UPDATE Jugadores SET ciega_grande = true WHERE id_usuario = "
//						+ jugadores.get(empieza - 1).getId_usuario() + " AND id_sala = " + id_sala;
//				consulta2 = "UPDATE Jugadores SET ciega_pequena = true WHERE id_usuario = "
//						+ jugadores.get(jugadores.size() - 1).getId_usuario() + " AND id_sala = " + id_sala;
//			} else {
//				if (empieza == 0) {
//					consulta1 = "UPDATE Jugadores SET ciega_grande = true WHERE id_usuario = "
//							+ jugadores.get(jugadores.size() - 1).getId_usuario() + " AND id_sala = " + id_sala;
//					consulta2 = "UPDATE Jugadores SET ciega_pequena = true WHERE id_usuario = "
//							+ jugadores.get(jugadores.size() - 2).getId_usuario() + " AND id_sala = " + id_sala;
//				}
//			}
//		}
//		try {
//			Statement st = con.createStatement();
//			st.executeUpdate(consulta1);
//			st.executeUpdate(consulta2);
//			String consulta3 = "SELECT id_usuario FROM Jugadores WHERE ciega_pequena = true AND id_sala = " + id_sala;
//			String consulta4 = "SELECT id_usuario FROM Jugadores WHERE ciega_grande = true AND id_sala = " + id_sala;
//			ResultSet rs = st.executeQuery(consulta3);
//			rs.next();
//			ciega_pequena = rs.getInt("id_usuario");
//			rs = st.executeQuery(consulta4);
//			rs.next();
//			ciega_grande = rs.getInt("id_usuario");
//			rs.close();
//			st.close();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			bd.ConexionBD.cerrarConexion(con);
//			actualizarFichas(utils.Constantes.CIEGA_PEQUENA, ciega_pequena);
//			actualizarFichas(utils.Constantes.CIEGA_GRANDE, ciega_grande);
//		}
//	}

	public static void actualizarCiegas(int id_sala, int empieza) {
		int ids[] = todosUsuarios(id_sala);
		Connection con = bd.ConexionBD.abrirConexion();
		String consulta1 = "";
		String consulta2 = "";
		int ciega_pequena = 0;
		int ciega_grande = 0;
		if (empieza > 1) {
			consulta1 = "UPDATE Jugadores SET ciega_grande = true WHERE id_usuario = " + ids[empieza - 1]
					+ " AND id_sala = " + id_sala;
			consulta2 = "UPDATE Jugadores SET ciega_pequena = true WHERE id_usuario = " + ids[empieza - 2]
					+ " AND id_sala = " + id_sala;
		} else {
			if (empieza == 1) {
				consulta1 = "UPDATE Jugadores SET ciega_grande = true WHERE id_usuario = " + ids[empieza - 1]
						+ " AND id_sala = " + id_sala;
				consulta2 = "UPDATE Jugadores SET ciega_pequena = true WHERE id_usuario = " + ids[ids.length - 1]
						+ " AND id_sala = " + id_sala;
			} else {
				if (empieza == 0) {
					consulta1 = "UPDATE Jugadores SET ciega_grande = true WHERE id_usuario = " + ids[ids.length - 1]
							+ " AND id_sala = " + id_sala;
					consulta2 = "UPDATE Jugadores SET ciega_pequena = true WHERE id_usuario = " + ids[ids.length - 2]
							+ " AND id_sala = " + id_sala;
				}
			}
		}
		try {
			Statement st = con.createStatement();
			st.executeUpdate(consulta1);
			st.executeUpdate(consulta2);
			String consulta3 = "SELECT id_usuario FROM Jugadores WHERE ciega_pequena = true AND id_sala = " + id_sala;
			String consulta4 = "SELECT id_usuario FROM Jugadores WHERE ciega_grande = true AND id_sala = " + id_sala;
			ResultSet rs = st.executeQuery(consulta3);
			rs.next();
			ciega_pequena = rs.getInt("id_usuario");
			rs = st.executeQuery(consulta4);
			rs.next();
			ciega_grande = rs.getInt("id_usuario");
			rs.close();
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			bd.ConexionBD.cerrarConexion(con);
			actualizarFichas(utils.Constantes.CIEGA_PEQUENA, ciega_pequena);
			actualizarFichas(utils.Constantes.CIEGA_GRANDE, ciega_grande);
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
			ArrayList<String> consultas = new ArrayList<String>();
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
				consultas.add("UPDATE Jugadores SET carta1 = '" + carta1 + "', carta2 = '" + carta2
						+ "' WHERE id_usuario = " + id_usuario + " AND id_sala = " + id_sala);
				jugadores.add(new Jugador(id_jugador, c1, c2, ciega_pequena, ciega_grande, activo, ganador, id_usuario,
						id_sala));
				cont++;
			}
			for (int i = 0; i < cont; i++) {
				st.executeUpdate(consultas.get(i));
				esperar(5);
			}
			rs.close();
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			bd.ConexionBD.cerrarConexion(con);
			Collections.sort(jugadores);
		}
	}

	public static int numUsuarios(int id_sala) {
		Connection con = bd.ConexionBD.abrirConexion();
		int numUsuarios = 0;
		try {
			Statement st = con.createStatement();
			String consulta = "SELECT COUNT(*) AS numUsuarios FROM Jugadores WHERE id_sala = " + id_sala;
			ResultSet rs = st.executeQuery(consulta);
			while (rs.next()) {
				numUsuarios = rs.getInt("numUsuarios");
			}
			rs.close();
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			bd.ConexionBD.cerrarConexion(con);
		}
		return numUsuarios;
	}

	public static int[] todosUsuarios(int id_sala) {
		Connection con = bd.ConexionBD.abrirConexion();
		int[] ids = new int[numUsuarios(id_sala)];
		System.out.println("Hay " + numUsuarios(id_sala) + " jugadores");
		// int[] ids = new int[chat.Chat.numJugadores()];
		try {
			Statement st = con.createStatement();
			String consulta = "SELECT id_usuario FROM Jugadores WHERE id_sala = " + id_sala + " ORDER BY id_usuario";
			ResultSet rs = st.executeQuery(consulta);
			int cont = 0;
			while (rs.next()) {
				ids[cont] = rs.getInt("id_usuario");
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
		String[] nombres = new String[numUsuarios(id_sala)];
		// String[] nombres = new String[chat.Chat.numJugadores()];
		try {
			Statement st = con.createStatement();
			String consulta = "SELECT nombre FROM usuarios NATURAL JOIN Jugadores WHERE id_sala = " + id_sala
					+ " ORDER BY id_usuario";
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

	public static int[] todasFichas(int id_sala) {
		Connection con = bd.ConexionBD.abrirConexion();
		int[] fichas = new int[numUsuarios(id_sala)];
		// int[] fichas = new int[chat.Chat.numJugadores()];
		try {
			Statement st = con.createStatement();
			String consulta = "SELECT fichas FROM usuarios NATURAL JOIN Jugadores WHERE id_sala = " + id_sala
					+ " ORDER BY id_usuario";
			ResultSet rs = st.executeQuery(consulta);
			int cont = 0;
			while (rs.next()) {
				fichas[cont] = rs.getInt("fichas");
				cont++;
			}
			rs.close();
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			bd.ConexionBD.cerrarConexion(con);
		}
		return fichas;
	}

	public static int[] todosGanadores(int id_sala) {
		Connection con = bd.ConexionBD.abrirConexion();
		int[] ganadores = new int[numUsuarios(id_sala)];
		try {
			Statement st = con.createStatement();
			String consulta = "SELECT id_usuario FROM Jugadores WHERE ganador = true AND id_sala = " + id_sala
					+ " ORDER BY id_usuario";
			ResultSet rs = st.executeQuery(consulta);
			int cont = 0;
			while (rs.next()) {
				ganadores[cont] = rs.getInt("id_usuario");
				cont++;
			}
			rs.close();
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			bd.ConexionBD.cerrarConexion(con);
		}
		return ganadores;
	}

	public static void borrarJugada(int id_sala) {
		Connection con = bd.ConexionBD.abrirConexion();
		try {
			Statement st = con.createStatement();
			String consulta = "DELETE FROM Jugadas WHERE id_sala = " + id_sala;
			st.executeUpdate(consulta);
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			bd.ConexionBD.cerrarConexion(con);
		}
		jugada.clear();
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
		for (int i = 0; i < jugadores.size(); i++) {
			if (jugadores.get(i).getId_usuario() == id_usuario) {
				jugadores.remove(i);
				break;
			}
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

	public static void fold(int id_usuario, int id_sala) {
		borrarJugador(id_usuario, id_sala);
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

	public static int comprobarGanador(int id_sala, int fichasGanadas) {
		/*
		 * Carta[] mano = new Carta[7]; mano[0] = jugada.get(0); mano[1] =
		 * jugada.get(1); mano[2] = jugada.get(2); mano[3] = jugada.get(3); mano[4] =
		 * jugada.get(4);
		 */

		int[] ids = todosUsuarios(id_sala);
		Carta[][] mano = new Carta[ids.length][7];

		int[] resultado = new int[ids.length];

		// 0 -> CartaAlta, 1 -> Pareja, -> 2 DoblePareja, 3 -> Trio, 4 -> Escalera, 5 ->
		// Color, 6 -> FullHouse, 7 -> Poker, 8 -> EscaleraDeColor
		for (int i = 0; i < ids.length; i++) {
			System.out.println("ids: " + ids[i]);
		}
		for (int i = 0; i < jugadores.size(); i++) {
			System.out.println("jugadores: " + jugadores.get(i).getId_usuario());
		}

		for (int i = 0; i < jugadores.size(); i++) {
			System.out.println(jugadores.get(i).getCarta1().getNumero() + jugadores.get(i).getCarta1().getPalo());
			mano[i][0] = jugada.get(0);
			mano[i][1] = jugada.get(1);
			mano[i][2] = jugada.get(2);
			mano[i][3] = jugada.get(3);
			mano[i][4] = jugada.get(4);
			mano[i][5] = jugadores.get(i).getCarta1();
			mano[i][6] = jugadores.get(i).getCarta2();
			if (juego.EvaluadorDeManos.esEscaleraDeColor(mano[i])) {
				resultado[i] = 8;
			} else {
				if (juego.EvaluadorDeManos.esPoker(mano[i])) {
					resultado[i] = 7;
				} else {
					if (juego.EvaluadorDeManos.esFullHouse(mano[i])) {
						resultado[i] = 6;
					} else {
						if (juego.EvaluadorDeManos.esColor(mano[i])) {
							resultado[i] = 5;
						} else {
							if (juego.EvaluadorDeManos.esEscalera(mano[i])) {
								resultado[i] = 4;
							} else {
								if (juego.EvaluadorDeManos.esTrio(mano[i])) {
									resultado[i] = 3;
								} else {
									if (juego.EvaluadorDeManos.esDoblePareja(mano[i])) {
										resultado[i] = 2;
									} else {
										if (juego.EvaluadorDeManos.esPareja(mano[i])) {
											resultado[i] = 1;
										} else {
											// CartaAlta
											resultado[i] = 0;
										}
									}
								}
							}
						}
					}
				}
			}
		}

		int jugadaGanadora = 0;
		for (int i = 0; i < resultado.length; i++) {
			if (resultado[i] > jugadaGanadora) {
				jugadaGanadora = resultado[i];
			}
		}

		ArrayList<Integer> ganadores = new ArrayList<Integer>();
		ArrayList<Integer> posiciones = new ArrayList<Integer>();
		for (int i = 0; i < ids.length; i++) {
			if (resultado[i] == jugadaGanadora) {
				ganadores.add(ids[i]);
				posiciones.add(i);

			}
		}

		if (ganadores.size() == 1) {
			ganador(fichasGanadas, ganadores.get(0));
			actualizarGanador(ganadores.get(0), id_sala);
		} else {
			int[][] manoJugador = new int[ganadores.size()][5];

			switch (jugadaGanadora) {
			case 8:
				for (int i = 0; i < ganadores.size(); i++) {
					manoJugador[i] = juego.EvaluadorDeManos.desempateEscaleraDeColor(mano[posiciones.get(i)]);
				}
				for (int i = 0; i < ganadores.size(); i++) {
					for (int j = 0; j < ganadores.size(); j++) {
						for (int k = 0; k < manoJugador.length; k++) {
							// //if (i != j) {
							if (manoJugador[i][k] != manoJugador[j][k]) {
								if (manoJugador[i][k] > manoJugador[j][k]) {
									ganadores.set(j, 0);
								} else {
									ganadores.set(i, 0);
								}
							}
							// }
						}
					}
				}
				break;
			case 7:
				for (int i = 0; i < ganadores.size(); i++) {
					manoJugador[i] = juego.EvaluadorDeManos.desempatePoker(mano[posiciones.get(i)]);
				}
				for (int i = 0; i < ganadores.size(); i++) {
					for (int j = 0; j < ganadores.size(); j++) {
						for (int k = 0; k < manoJugador.length; k++) {
							// if (i != j) {
							if (manoJugador[i][k] != manoJugador[j][k]) {
								if (manoJugador[i][k] > manoJugador[j][k]) {
									ganadores.set(j, 0);
								} else {
									ganadores.set(i, 0);
								}
							}
							// }
						}
					}
				}
				break;
			case 6:
				for (int i = 0; i < ganadores.size(); i++) {
					manoJugador[i] = juego.EvaluadorDeManos.desempateFullHouse(mano[posiciones.get(i)]);
				}
				for (int i = 0; i < ganadores.size(); i++) {
					for (int j = 0; j < ganadores.size(); j++) {
						for (int k = 0; k < manoJugador.length; k++) {
							// if (i != j) {
							if (manoJugador[i][k] != manoJugador[j][k]) {
								if (manoJugador[i][k] > manoJugador[j][k]) {
									ganadores.set(j, 0);
								} else {
									ganadores.set(i, 0);
								}
							}
							// }
						}
					}
				}
				break;
			case 5:
				for (int i = 0; i < ganadores.size(); i++) {
					manoJugador[i] = juego.EvaluadorDeManos.desempateColor(mano[posiciones.get(i)]);
				}
				for (int i = 0; i < ganadores.size(); i++) {
					for (int j = 0; j < ganadores.size(); j++) {
						for (int k = 0; k < manoJugador.length; k++) {
							// if (i != j) {
							if (manoJugador[i][k] != manoJugador[j][k]) {
								if (manoJugador[i][k] > manoJugador[j][k]) {
									ganadores.set(j, 0);
								} else {
									ganadores.set(i, 0);
								}
							}
							// }
						}
					}
				}
				break;
			case 4:
				for (int i = 0; i < ganadores.size(); i++) {
					manoJugador[i] = juego.EvaluadorDeManos.desempateEscalera(mano[posiciones.get(i)]);
				}
				for (int i = 0; i < ganadores.size(); i++) {
					for (int j = 0; j < ganadores.size(); j++) {
						for (int k = 0; k < manoJugador.length; k++) {
							// if (i != j) {
							if (manoJugador[i][k] != manoJugador[j][k]) {
								if (manoJugador[i][k] > manoJugador[j][k]) {
									ganadores.set(j, 0);
								} else {
									ganadores.set(i, 0);
								}
							}
							// }
						}
					}
				}
				break;
			case 3:
				for (int i = 0; i < ganadores.size(); i++) {
					manoJugador[i] = juego.EvaluadorDeManos.desempateTrio(mano[posiciones.get(i)]);
				}
				for (int i = 0; i < ganadores.size(); i++) {
					for (int j = 0; j < ganadores.size(); j++) {
						for (int k = 0; k < manoJugador.length; k++) {
							// if (i != j) {
							if (manoJugador[i][k] != manoJugador[j][k]) {
								if (manoJugador[i][k] > manoJugador[j][k]) {
									ganadores.set(j, 0);
								} else {
									ganadores.set(i, 0);
								}
							}
							// }
						}
					}
				}
				break;
			case 2:
				for (int i = 0; i < ganadores.size(); i++) {
					manoJugador[i] = juego.EvaluadorDeManos.desempateDoblePareja(mano[posiciones.get(i)]);
				}
				for (int i = 0; i < ganadores.size(); i++) {
					for (int j = 0; j < ganadores.size(); j++) {
						for (int k = 0; k < manoJugador.length; k++) {
							// if (i != j) {
							if (manoJugador[i][k] != manoJugador[j][k]) {
								if (manoJugador[i][k] > manoJugador[j][k]) {
									ganadores.set(j, 0);
								} else {
									ganadores.set(i, 0);
								}
							}
							// }
						}
					}
				}
				break;
			case 1:
				for (int i = 0; i < ganadores.size(); i++) {
					manoJugador[i] = juego.EvaluadorDeManos.desempatePareja(mano[posiciones.get(i)]);
				}
				for (int i = 0; i < ganadores.size(); i++) {
					for (int j = 0; j < ganadores.size(); j++) {
						for (int k = 0; k < manoJugador.length; k++) {
							// if (i != j) {
							if (manoJugador[i][k] != manoJugador[j][k]) {
								if (manoJugador[i][k] > manoJugador[j][k]) {
									ganadores.set(j, 0);
								} else {
									ganadores.set(i, 0);
								}
							}
							// }
						}
					}
				}
				break;
			case 0:
				for (int i = 0; i < ganadores.size(); i++) {
					manoJugador[i] = juego.EvaluadorDeManos.desempateCartaAlta(mano[posiciones.get(i)]);
				}
				for (int i = 0; i < ganadores.size(); i++) {
					for (int j = 0; j < ganadores.size(); j++) {
						for (int k = 0; k < manoJugador.length; k++) {
							// if (i != j) {
							if (manoJugador[i][k] != manoJugador[j][k]) {
								if (manoJugador[i][k] > manoJugador[j][k]) {
									ganadores.set(j, 0);
								} else {
									ganadores.set(i, 0);
								}
							}
							// }
						}
					}
				}
				break;
			}
			int nuevaLongitud = 0;
			for (int i = 0; i < ganadores.size(); i++) {
				if (ganadores.get(i) != 0) {
					nuevaLongitud++;
				}
			}
			fichasGanadas = fichasGanadas / nuevaLongitud;
			for (int i = 0; i < ganadores.size(); i++) {
				if (ganadores.get(i) != 0) {
					ganador(fichasGanadas, ganadores.get(i));
					actualizarGanador(ganadores.get(i), id_sala);
				}
			}
		}
		return jugadaGanadora;
	}

	public static void ganador(int bote, int id_usuario) {
		if (bote > 0) {
			Connection con = bd.ConexionBD.abrirConexion();
			try {
				Statement st = con.createStatement();
				String consulta = "UPDATE Usuarios SET fichas = fichas + " + bote + " WHERE id_usuario = " + id_usuario;
				st.executeUpdate(consulta);
				st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				bd.ConexionBD.cerrarConexion(con);
			}
		}
	}

	public static void actualizarGanador(int id_usuario, int id_sala) {
		Connection con = bd.ConexionBD.abrirConexion();
		try {
			Statement st = con.createStatement();
			String consulta = "UPDATE Jugadores SET ganador = true WHERE id_usuario = " + id_usuario + " AND id_sala = "
					+ id_sala;
			st.executeUpdate(consulta);
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			bd.ConexionBD.cerrarConexion(con);
		}
	}

	public static void recargarFichas() {
		Connection con = bd.ConexionBD.abrirConexion();
		try {
			Statement st = con.createStatement();
			String consulta = "UPDATE Usuarios SET fichas = 100 WHERE fichas <= 0";
			st.executeUpdate(consulta);
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			bd.ConexionBD.cerrarConexion(con);
		}
	}

	public static void esperar(int ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
