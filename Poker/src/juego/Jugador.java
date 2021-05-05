package juego;

public class Jugador {
	
	//ATRIBUTOS
	protected int id_jugador;
	protected Carta carta1;
	protected Carta carta2;
	protected Boolean ciega_pequeña;
	protected Boolean ciega_grande;
	protected Boolean activo;
	protected Boolean ganador;
	protected int id_usuario;
	protected int id_sala;
	
	//CONSTRUCTOR
	public Jugador(int id_jugador, Carta carta1, Carta carta2, Boolean ciega_pequeña, Boolean ciega_grande,
			Boolean activo, Boolean ganador, int id_usuario, int id_sala) {
		super();
		this.id_jugador = id_jugador;
		this.carta1 = carta1;
		this.carta2 = carta2;
		this.ciega_pequeña = ciega_pequeña;
		this.ciega_grande = ciega_grande;
		this.activo = activo;
		this.ganador = ganador;
		this.id_usuario = id_usuario;
		this.id_sala = id_sala;
	}

	//GETETRS AND SETTERS
	public int getId_jugador() {
		return id_jugador;
	}

	public void setId_jugador(int id_jugador) {
		this.id_jugador = id_jugador;
	}

	public Carta getCarta1() {
		return carta1;
	}

	public void setCarta1(Carta carta1) {
		this.carta1 = carta1;
	}

	public Carta getCarta2() {
		return carta2;
	}

	public void setCarta2(Carta carta2) {
		this.carta2 = carta2;
	}
	
	public Boolean getCiega_pequeña() {
		return ciega_pequeña;
	}

	public void setCiega_pequeña(Boolean ciega_pequeña) {
		this.ciega_pequeña = ciega_pequeña;
	}

	public Boolean getCiega_grande() {
		return ciega_grande;
	}

	public void setCiega_grande(Boolean ciega_grande) {
		this.ciega_grande = ciega_grande;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public Boolean getGanador() {
		return ganador;
	}

	public void setGanador(Boolean ganador) {
		this.ganador = ganador;
	}
	
	public int getId_usuario() {
		return id_usuario;
	}

	public void setId_usuario(int id_usuario) {
		this.id_usuario = id_usuario;
	}
	
	public int getId_sala() {
		return id_sala;
	}

	public void setId_sala(int id_sala) {
		this.id_sala = id_sala;
	}

}
