package chat;

public class Mensaje {
	
	//ATRIBUTOS
	private String accion;
	//CHAT
	private String nombre;
	private String mensaje;
	//JUEGO
	private String id_usuario;
	private String id_sala;
	private String flop1;
	private String flop2;
	private String flop3;
	private String turn;
	private String river;
	private String apuesta;
	private String empezarRonda;
	private String bote;
	private String empieza;

	//CONSTRUCTOR
	public Mensaje() {
	}

	//GETTERS AND SETTERS
	public String getAccion() {
		return accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public String getId_usuario() {
		return id_usuario;
	}

	public void setId_usuario(String id_usuario) {
		this.id_usuario = id_usuario;
	}

	public String getId_sala() {
		return id_sala;
	}

	public void setId_sala(String id_sala) {
		this.id_sala = id_sala;
	}

	public String getFlop1() {
		return flop1;
	}

	public void setFlop1(String flop1) {
		this.flop1 = flop1;
	}

	public String getFlop2() {
		return flop2;
	}

	public void setFlop2(String flop2) {
		this.flop2 = flop2;
	}
	
	public String getFlop3() {
		return flop3;
	}

	public void setFlop3(String flop3) {
		this.flop3 = flop3;
	}

	public String getTurn() {
		return turn;
	}

	public void setTurn(String turn) {
		this.turn = turn;
	}

	public String getRiver() {
		return river;
	}

	public void setRiver(String river) {
		this.river = river;
	}
	
	public String getApuesta() {
		return apuesta;
	}

	public void setApuesta(String apuesta) {
		this.apuesta = apuesta;
	}
	
	public String getEmpezarRonda() {
		return empezarRonda;
	}

	public void setEmpezarRonda(String empezarRonda) {
		this.empezarRonda = empezarRonda;
	}

	public String getBote() {
		return bote;
	}

	public void setBote(String bote) {
		this.bote = bote;
	}

	public String getEmpieza() {
		return empieza;
	}

	public void setEmpieza(String empieza) {
		this.empieza = empieza;
	}
	
}
