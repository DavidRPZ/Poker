package juego;

import java.util.ArrayList;
import java.util.Collections;

public class Baraja {
	protected static ArrayList<Carta> baraja = new ArrayList<Carta>();
	protected static String[] numeros = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};
	protected static char[] palos = {'P', 'T', 'C', 'D'}; // P -> Picas, T -> Tréboles, C -> Corazones, D -> Diamantes
	
	public static void crearBaraja() {
		for (int i = 0; i < palos.length; ++i) {
			for (int j = 0; j < numeros.length; ++j) {
				baraja.add(new Carta(numeros[j], palos[i]));
			}
		}
	}
	
	public static String mostrarBaraja() {
		String str = "";
		for (int i = 0; i < baraja.size(); i++) {
			//str += baraja.get(i).toString();
			str += baraja.get(i).getNumero();
			str += baraja.get(i).getPalo() + "\n";
		}
		return str;
	}
	
	public static void barajar() {
		Collections.shuffle(baraja);
	}
}
