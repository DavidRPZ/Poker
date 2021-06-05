package chat;

import java.io.IOException;
import java.io.Reader;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

import juego.Juego;

public class DecoderMensaje implements Decoder.TextStream<Mensaje> {

	@Override
	public Mensaje decode(Reader reader) throws DecodeException, IOException {
		Mensaje mensaje = new Mensaje();

		try (JsonReader jsonReader = Json.createReader(reader)) {
			JsonObject json = jsonReader.readObject();
			switch (json.getString("accion")) {
			case "chat":
				mensaje.setAccion(json.getString("accion"));
				mensaje.setNombre(json.getString("nombre"));
				mensaje.setMensaje(json.getString("mensaje"));
				break;
			case "empezarRonda":
				mensaje.setAccion("empezarRonda");
				mensaje.setEmpezarRonda("true");
				mensaje.setEmpieza(json.getString("empieza"));
				break;
			case "todosUsuarios":
				mensaje.setAccion(json.getString("accion"));
				mensaje.setId_sala(json.getString("id_sala"));
				int empieza = Integer.parseInt(json.getString("empieza"));
				juego.Juego.actualizarRondaEmpezada(Integer.parseInt(mensaje.getId_sala()), true);
				juego.Juego.esperar(20);
				juego.Baraja.crearBaraja();
				juego.Juego.esperar(20);
				juego.Baraja.barajar();
				juego.Juego.esperar(20);
				juego.Juego.repartir(Integer.parseInt(mensaje.getId_sala()));
				juego.Juego.esperar(20);
				juego.Juego.crearJugada(Integer.parseInt(mensaje.getId_sala()));
				juego.Juego.esperar(20);
				juego.Juego.actualizarCiegas(Integer.parseInt(mensaje.getId_sala()), empieza);
				juego.Juego.esperar(20);
				break;
			case "flop":
				int id_sala = Integer.parseInt(json.getString("id_sala"));
				String[] flop = Juego.flop(id_sala);
				mensaje.setAccion(json.getString("accion"));
				mensaje.setFlop1(flop[0]);
				mensaje.setFlop2(flop[1]);
				mensaje.setFlop3(flop[2]);
				break;
			case "turn":
				int id_salaTurn = Integer.parseInt(json.getString("id_sala"));
				String turn = Juego.turn(id_salaTurn);
				mensaje.setAccion(json.getString("accion"));
				mensaje.setTurn(turn);
				break;
			case "river":
				int id_salaRiver = Integer.parseInt(json.getString("id_sala"));
				String river = Juego.river(id_salaRiver);
				mensaje.setAccion(json.getString("accion"));
				mensaje.setRiver(river);
				break;
			case "fold":
				juego.Juego.fold(Integer.parseInt(json.getString("id_usuario")),
						Integer.parseInt(json.getString("id_sala")));
				mensaje.setAccion(json.getString("accion"));
				mensaje.setId_usuario(json.getString("id_usuario"));
				break;
			case "call":
				int id_usuarioCall = Integer.parseInt(json.getString("id_usuario"));
				int salaCall = Integer.parseInt(json.getString("id_sala"));
				mensaje.setAccion(json.getString("accion"));
				mensaje.setId_usuario(json.getString("id_usuario"));
				mensaje.setApuesta(json.getString("apuesta"));
				juego.Juego.actualizarFichas(Integer.parseInt(mensaje.getApuesta()), id_usuarioCall);
				break;
			case "raise":
				int id_usuarioRaise = Integer.parseInt(json.getString("id_usuario"));
				int salaRaise = Integer.parseInt(json.getString("id_sala"));
				mensaje.setAccion(json.getString("accion"));
				mensaje.setId_usuario(json.getString("id_usuario"));
				mensaje.setApuesta(json.getString("apuesta"));
				juego.Juego.actualizarFichas(Integer.parseInt(mensaje.getApuesta()), id_usuarioRaise);
				break;
			case "comprobarGanador":
				mensaje.setAccion(json.getString("accion"));
				mensaje.setId_usuario(json.getString("id_usuario"));
				mensaje.setId_sala(json.getString("id_sala"));
				mensaje.setBote(json.getString("bote"));
				int jugadaGanadora = juego.Juego.comprobarGanador(Integer.parseInt(mensaje.getId_sala()),
						Integer.parseInt(mensaje.getBote()));
				String jugada = "";
				switch (jugadaGanadora) {
				case 8:
					jugada = "Escalera de color";
					break;
				case 7:
					jugada = "Poker";
					break;
				case 6:
					jugada = "FullHouse";
					break;
				case 5:
					jugada = "Color";
					break;
				case 4:
					jugada = "Escalera";
					break;
				case 3:
					jugada = "Trío";
					break;
				case 2:
					jugada = "Doble pareja";
					break;
				case 1:
					jugada = "Pareja";
					break;
				case 0:
					jugada = "Carta alta";
					break;
				}
				mensaje.setJugadaGanadora(jugada);
				break;
			case "ganadorFold":
				mensaje.setAccion(json.getString("accion"));
				mensaje.setId_usuario(json.getString("id_usuario"));
				mensaje.setId_sala(json.getString("id_sala"));
				mensaje.setBote(json.getString("bote"));
				juego.Juego.ganador(Integer.parseInt(mensaje.getBote()), Integer.parseInt(mensaje.getId_usuario()));
				break;
			case "rondaTerminada":
				mensaje.setAccion(json.getString("accion"));
				mensaje.setId_usuario(json.getString("id_usuario"));
				mensaje.setId_sala(json.getString("id_sala"));
//				juego.Juego.actualizarRondaEmpezada(Integer.parseInt(mensaje.getId_sala()), false);
//				juego.Juego.esperar(20);
//				juego.Juego.borrarJugadores(Integer.parseInt(mensaje.getId_sala()));
//				juego.Juego.esperar(20);
//				juego.Juego.borrarJugada(Integer.parseInt(mensaje.getId_sala()));
//				juego.Juego.esperar(20);
//				juego.Juego.recargarFichas();
//				juego.Baraja.vaciarBaraja();
				break;
			}
		}
		return mensaje;
	}

	@Override
	public void destroy() {
	}

	@Override
	public void init(EndpointConfig config) {
	}

}
