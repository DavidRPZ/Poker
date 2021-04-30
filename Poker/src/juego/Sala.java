package juego;

public class Sala {
	
	//ATRIBUTOS
	protected int id_sala;
	protected int num_usuarios;
	
	protected  final int MAX_USUARIOS = 8;
	
	//CONSTRUCTOR
	public Sala(int id_sala, int num_usuarios) {
		super();
		this.id_sala = id_sala;
		this.num_usuarios = num_usuarios;
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

	public int getMAX_USUARIOS() {
		return MAX_USUARIOS;
	}

}
