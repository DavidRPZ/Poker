package juego;

public class Carta {
	
	protected String numero;
	protected char palo;
	
	public Carta(String numero, char palo) {
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
		return "Carta [numero=" + numero + ", palo=" + palo + "]";
	}

}
