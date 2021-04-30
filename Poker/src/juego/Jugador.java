package juego;

public class Jugador {
	
	//ATRIBUTOS
	protected int id_jugador;
	protected Carta carta1;
	protected Carta carta2;
	protected int id_usuario;
	protected int id_sala;
	
	//CONSTRUCTOR
	public Jugador(int id_jugador, Carta carta1, Carta carta2, int id_usuario, int id_sala) {
		super();
		this.id_jugador = id_jugador;
		this.carta1 = carta1;
		this.carta2 = carta2;
		this.id_usuario = id_usuario;
		this.id_sala = id_sala;
	}
	
	public Jugador() {
		super();
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

	public int getId_usuario() {
		return id_usuario;
	}

	public int getId_sala() {
		return id_sala;
	}

}
