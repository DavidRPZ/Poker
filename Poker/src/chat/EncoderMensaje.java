package chat;

import java.io.IOException;
import java.io.Writer;

import javax.json.Json;
import javax.json.JsonObject;
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
		case "juego":
			json = Json.createObjectBuilder().add("accion", object.getAccion()).add("id_usuario", object.getId_usuario()).add("id_sala", object.getId_sala()).add("carta1", object.getCarta1()).add("carta2", object.getCarta2()).build();
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
