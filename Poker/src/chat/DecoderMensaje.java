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

public class DecoderMensaje implements Decoder.TextStream<Mensaje>{

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
				break;
			case "todosUsuarios":
				mensaje.setAccion(json.getString("accion"));
				mensaje.setId_sala(json.getString("id_sala"));
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
				mensaje.setAccion(json.getString("accion"));
				mensaje.setId_usuario(json.getString("id_usuario"));
				break;
			case "call":
				int id_usuarioCall = Integer.parseInt(json.getString("id_usuario"));
				int salaCall = Integer.parseInt(json.getString("id_sala"));
				mensaje.setAccion(json.getString("accion"));
				break;
			case "raise":
				int id_usuarioRaise = Integer.parseInt(json.getString("id_usuario"));
				int salaRaise = Integer.parseInt(json.getString("id_sala"));
				mensaje.setAccion(json.getString("accion"));
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
