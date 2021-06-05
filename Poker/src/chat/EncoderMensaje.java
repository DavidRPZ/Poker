package chat;

import java.io.IOException;
import java.io.Writer;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonWriter;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class EncoderMensaje implements Encoder.TextStream<Mensaje> {

	@Override
	public void encode(Mensaje object, Writer writer) throws EncodeException, IOException {
		JsonObject json = null;
		switch (object.getAccion()) {
		case "chat":
			json = Json.createObjectBuilder().add("accion", object.getAccion()).add("nombre", object.getNombre()).add("mensaje", object.getMensaje()).build();
			break;
		case "empezarRonda":
			json = Json.createObjectBuilder().add("accion", object.getAccion()).add("empezarRonda", object.getEmpezarRonda()).add("empieza", object.getEmpieza()).build();
			break;
		case "todosUsuarios":
			int[] todosUsuarios = juego.Juego.todosUsuarios(Integer.parseInt(object.getId_sala()));
			juego.Juego.esperar(20);
			String[] todosNombres = juego.Juego.todosNombres(Integer.parseInt(object.getId_sala()));
			juego.Juego.esperar(20);
			int[] todasFichas = juego.Juego.todasFichas(Integer.parseInt(object.getId_sala()));
			JsonObjectBuilder objeto = Json.createObjectBuilder().add("accion", object.getAccion());
			for (int i = 0; i < todosUsuarios.length; i++) {
				objeto.add("J" + (i + 1), String.valueOf(todosUsuarios[i]));
				objeto.add("nombre" + (i + 1), todosNombres[i]);
				objeto.add("F" + (i + 1), todasFichas[i]);
			}
			json = objeto.build();
			break;
		case "flop":
			json = Json.createObjectBuilder().add("accion", object.getAccion()).add("flop1", object.getFlop1()).add("flop2", object.getFlop2()).add("flop3", object.getFlop3()).build();
			break;
		case "turn":
			json = Json.createObjectBuilder().add("accion", object.getAccion()).add("turn", object.getTurn()).build();
			break;
		case "river":
			json = Json.createObjectBuilder().add("accion", object.getAccion()).add("river", object.getRiver()).build();
			break;
		case "fold":
			json = Json.createObjectBuilder().add("accion", object.getAccion()).add("id_user", object.getId_usuario()).build();
			break;
		case "call":
			json = Json.createObjectBuilder().add("accion", object.getAccion()).add("id_usuario", object.getId_usuario()).add("apuesta", object.getApuesta()).build();
			break;
		case "raise":
			json = Json.createObjectBuilder().add("accion", object.getAccion()).add("id_usuario", object.getId_usuario()).add("apuesta", object.getApuesta()).build();
			break;
		case "comprobarGanador":
			//int[] ids = juego.Juego.comprobarGanador(Integer.parseInt(object.getId_sala()), Integer.parseInt(object.getBote()));
			JsonObjectBuilder objeto2 = Json.createObjectBuilder().add("accion", object.getAccion());
			int[] ids = juego.Juego.todosUsuarios(Integer.parseInt(object.getId_sala()));
			for (int i = 0; i < ids.length; i++) {
				objeto2.add("J" + ids[i] + "C1", juego.Juego.mostrarCarta1(ids[i], Integer.parseInt(object.getId_sala())));
				objeto2.add("J" + ids[i] + "C2", juego.Juego.mostrarCarta2(ids[i], Integer.parseInt(object.getId_sala())));
			}
			int[] ganadores = juego.Juego.todosGanadores(Integer.parseInt(object.getId_sala()));
			objeto2.add("numGanadores", String.valueOf(ganadores.length));
			for (int i = 0; i < ganadores.length; i++) {
				objeto2.add("G" + ganadores[i], String.valueOf(ganadores[i]));
			}
			objeto2.add("jugadaGanadora", object.getJugadaGanadora());
			json = objeto2.build();
			break;
		case "ganadorFold":
			json = Json.createObjectBuilder().add("accion", object.getAccion()).add("id_usuario", object.getId_usuario()).build();
			break;
		case "rondaTerminada":
			json = Json.createObjectBuilder().add("accion", object.getAccion()).build();
			break;
		}
		
		try (JsonWriter jsonWriter = Json.createWriter(writer)) {
			jsonWriter.writeObject(json);
		}
	}
	
	@Override
	public void destroy() {
	}

	@Override
	public void init(EndpointConfig config) {
	}

}
