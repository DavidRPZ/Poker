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
	private String carta1;
	private String carta2;
	private String empezarRonda;

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

	public String getCarta1() {
		return carta1;
	}

	public void setCarta1(String carta1) {
		this.carta1 = carta1;
	}

	public String getCarta2() {
		return carta2;
	}

	public void setCarta2(String carta2) {
		this.carta2 = carta2;
	}
	
	public String getEmpezarRonda() {
		return empezarRonda;
	}

	public void setEmpezarRonda(String empezarRonda) {
		this.empezarRonda = empezarRonda;
	}
	
}
