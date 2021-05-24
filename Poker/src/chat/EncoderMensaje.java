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
			json = Json.createObjectBuilder().add("accion", object.getAccion()).add("empezarRonda", object.getEmpezarRonda()).build();
			break;
		case "todosUsuarios":
			int[] todosUsuarios = juego.Juego.todosUsuarios(Integer.parseInt(object.getId_sala()));
			String[] todosNombres = juego.Juego.todosNombres(Integer.parseInt(object.getId_sala()));
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
			json = Json.createObjectBuilder().add("accion", object.getAccion()).build();
			break;
		case "raise":
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
