package chat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value="/sala", encoders = {EncoderMensaje.class},
decoders = {DecoderMensaje.class})
public class Chat {

	private static final List<Session> conectados = new ArrayList<>();
	
	@OnOpen
	public void OnOpen(Session sesion) {
		conectados.add(sesion);
	}
	
	@OnClose
	public void OnClose(Session sesion) {
		conectados.remove(sesion);
	}
	
	@OnMessage
	public void OnMessage(Mensaje mensaje) throws IOException, EncodeException {
		for (Session sesion: conectados) {
			sesion.getBasicRemote().sendObject(mensaje);
		}
	}
	
	public static int numJugadores() {
		return conectados.size();
	}
	
}