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
	
	public static Carta repartirCartas() {
		Carta c1 = baraja.get(0);
		baraja.remove(0);
		return c1;
	}
	
	public static String mostrarBaraja(int i) {
		return baraja.get(i).getNumero() + baraja.get(i).getPalo();
	}
	
	public static void barajar() {
		Collections.shuffle(baraja);
	}
	
	public static int barajaSize() {
		return baraja.size();
	}
	
	public static void vaciarBaraja() {
		baraja.clear();
	}
}
