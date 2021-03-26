package juego;

public class Cartas {
	
	protected String numero;
	protected char palo;
	
	public Cartas(String numero, char palo) {
		super();
		this.numero = numero;
		this.palo = palo;
	}

	public String getNumero() {
		return numero;
	}

	public char getPalo() {
		return palo;
	}

	@Override
	public String toString() {
		return "Cartas [numero=" + numero + ", palo=" + palo + "]";
	}

}
