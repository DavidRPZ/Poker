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
		case "todosJugadores":
			int[] todosJugadores = juego.Juego.todosJugadores(Integer.parseInt(object.getId_sala()));
			String[] todosNombres = juego.Juego.todosNombres(Integer.parseInt(object.getId_sala()));
			JsonObjectBuilder objeto = Json.createObjectBuilder().add("accion", object.getAccion());
			for (int i = 0; i < todosJugadores.length; i++) {
				objeto.add("J" + (i + 1), String.valueOf(todosJugadores[i]));
				objeto.add("nombre" + (i + 1), todosNombres[i]);
			}
			json = objeto.build();
			break;
		case "flop":
			json = Json.createObjectBuilder().add("accion", object.getAccion()).add("flop1", object.getFlop1()).add("flop2", object.getFlop2()).add("flop3", object.getFlop3()).build();
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
