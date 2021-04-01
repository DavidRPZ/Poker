package juego;

public class Usuario {
	
	protected int id_usuario;
	protected String nombre;
	protected String contrasena;
	protected String email;
	protected int fichas;
	
	//CONSTRUCTOR 1
	public Usuario(int id_usuario, String nombre, String contrasena, String email, int fichas) {
		super();
		this.id_usuario = id_usuario;
		this.nombre = nombre;
		this.contrasena = contrasena;
		this.email = email;
		this.fichas = fichas;
	}
	
	//CONSTRUCTOR 2
	public Usuario(int id_usuario, int fichas) {
		super();
		this.id_usuario = id_usuario;
		this.fichas = fichas;
	}

	//GETTERS AND SETTERS
	public int getId_usuario() {
		return id_usuario;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getFichas() {
		return fichas;
	}

	public void setFichas(int fichas) {
		this.fichas = fichas;
	}

}
