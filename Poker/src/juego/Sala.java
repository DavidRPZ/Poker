package juego;

public class Sala {
	
	//ATRIBUTOS
	protected int id_sala;
	protected int num_usuarios;
	protected boolean ronda_empezada;
	
	protected  final int MAX_USUARIOS = 8;
	
	//CONSTRUCTOR
	public Sala(int id_sala, int num_usuarios, boolean ronda_empezada) {
		super();
		this.id_sala = id_sala;
		this.num_usuarios = num_usuarios;
		this.ronda_empezada = ronda_empezada;
	}

	//GETTERS AND SETTERS
	public int getId_sala() {
		return id_sala;
	}

	public int getNum_usuarios() {
		return num_usuarios;
	}

	public void setNum_usuarios(int num_usuarios) {
		this.num_usuarios = num_usuarios;
	}

	public boolean isRonda_empezada() {
		return ronda_empezada;
	}

	public void setRonda_empezada(boolean ronda_empezada) {
		this.ronda_empezada = ronda_empezada;
	}

	public int getMAX_USUARIOS() {
		return MAX_USUARIOS;
	}

}
