package juego;

import java.util.ArrayList;

public class Baraja {
	protected static ArrayList<Cartas> baraja = new ArrayList<Cartas>();
	protected static String[] numeros = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};
	protected static char[] palos = {'P', 'T', 'C', 'D'}; // P -> Picas, T -> Tréboles, C -> Corazones, D -> Diamantes
	
	public static void crearBaraja() {
		for (int i = 0; i < palos.length; ++i) {
			for (int j = 0; j < numeros.length; ++j) {
				baraja.add(new Cartas(numeros[j], palos[i]));
			}
		}
	}
	
	public static void mostrarBaraja() {
		for (int i = 0; i < 52; i++) {
			System.out.println(baraja.get(i).toString());
		}
	}
}
