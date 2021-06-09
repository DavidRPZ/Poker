package juego;

public class Carta {
	
	//ATRIBUTOS
	protected String numero;
	protected char palo;
	
	//CONSTRUCTOR
	public Carta(String numero, char palo) {
		super();
		this.numero = numero;
		this.palo = palo;
	}

	//GETTERS AND SETTERS
	public String getNumero() {
		return numero;
	}

	public char getPalo() {
		return palo;
	}

	//TOSTRING
	/*@Override
	public String toString() {
		return "Carta [numero=" + numero + ", palo=" + palo + "]";
	}*/

}
