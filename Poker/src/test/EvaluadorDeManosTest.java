package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import juego.Carta;
import juego.EvaluadorDeManos;

class EvaluadorDeManosTest {
	
	@Test
	void testCartaAlta() {
		Carta[] cartas = { new Carta("8", 'P'), new Carta("J", 'P'), new Carta("A", 'D'), new Carta("2", 'T'),
				new Carta("9", 'C'), new Carta("4", 'T'), new Carta("6", 'P') };
		int[] resultadoEsperado = {14, 11, 9, 8, 6};
		assertTrue(Arrays.equals(EvaluadorDeManos.desempateCartaAlta(cartas), resultadoEsperado));
	}
	
	@Test
	void testPareja() {
		Carta[] cartas = { new Carta("4", 'P'), new Carta("J", 'P'), new Carta("A", 'D'), new Carta("2", 'T'),
				new Carta("9", 'C'), new Carta("4", 'T'), new Carta("6", 'P') };
		assertTrue(EvaluadorDeManos.esPareja(cartas));
		int[] resultadoEsperado = {4, 4, 14, 11, 9};
		assertTrue(Arrays.equals(EvaluadorDeManos.desempatePareja(cartas), resultadoEsperado));
	}
	
	@Test
	void testDoblePareja() {
		Carta[] cartas = { new Carta("4", 'P'), new Carta("4", 'P'), new Carta("5", 'D'), new Carta("2", 'T'),
				new Carta("3", 'C'), new Carta("10", 'T'), new Carta("10", 'P') };
		int[] resultadoEsperado = {10, 10, 4, 4, 5};
		
		assertTrue(EvaluadorDeManos.esDoblePareja(cartas));
		assertTrue(Arrays.equals(EvaluadorDeManos.desempateDoblePareja(cartas), resultadoEsperado));
	}

	@Test
	void testTrio() {
		Carta[] cartas = { new Carta("4", 'P'), new Carta("J", 'P'), new Carta("A", 'D'), new Carta("2", 'T'),
				new Carta("A", 'C'), new Carta("10", 'T'), new Carta("A", 'P') };
		int[] resultadoEsperado = {14, 14, 14, 11, 10};
		
		assertTrue(EvaluadorDeManos.esTrio(cartas));
		assertTrue(Arrays.equals(EvaluadorDeManos.desempateTrio(cartas), resultadoEsperado));
	}
	
	@Test
	void testEscalera() {
		Carta[] cartas = { new Carta("4", 'P'), new Carta("K", 'P'), new Carta("5", 'D'), new Carta("2", 'T'),
				new Carta("3", 'C'), new Carta("10", 'T'), new Carta("6", 'P') };
		int[] resultadoEsperado = {6, 5, 4, 3, 2};
		
		assertTrue(EvaluadorDeManos.esEscalera(cartas));
		assertTrue(Arrays.equals(EvaluadorDeManos.desempateEscalera(cartas), resultadoEsperado));
	}
	
	@Test
	void testColor() {
		Carta[] cartas = { new Carta("7", 'T'), new Carta("K", 'P'), new Carta("2", 'D'), new Carta("2", 'P'),
				new Carta("3", 'P'), new Carta("10", 'P'), new Carta("6", 'P') };
		int[] resultadoEsperado = {13, 10, 6, 3, 2};
		
		assertTrue(EvaluadorDeManos.esColor(cartas));
		assertTrue(Arrays.equals(EvaluadorDeManos.desempateColor(cartas), resultadoEsperado));
	}
	
	@Test
	void testFullHouse() {
		Carta[] cartas = { new Carta("9", 'P'), new Carta("3", 'P'), new Carta("5", 'D'), new Carta("3", 'T'),
				new Carta("3", 'C'), new Carta("10", 'T'), new Carta("9", 'T') };
		int[] resultadoEsperado = {3, 3, 3, 9, 9};
		
		assertTrue(EvaluadorDeManos.esFullHouse(cartas));
		assertTrue(Arrays.equals(EvaluadorDeManos.desempateFullHouse(cartas), resultadoEsperado));
	}	
	
	@Test
	void testPoker() {
		Carta[] cartas = { new Carta("4", 'P'), new Carta("J", 'P'), new Carta("J", 'D'), new Carta("2", 'T'),
				new Carta("J", 'C'), new Carta("Q", 'T'), new Carta("J", 'T') };
		int[] resultadoEsperado = {11, 11, 11, 11, 12};
		
		assertTrue(EvaluadorDeManos.esPoker(cartas));
		assertTrue(Arrays.equals(EvaluadorDeManos.desempatePoker(cartas), resultadoEsperado));
	}
	
	@Test
	void testEscaleraDeColor() {
		Carta[] cartas = { new Carta("4", 'P'), new Carta("K", 'T'), new Carta("5", 'P'), new Carta("2", 'P'),
				new Carta("3", 'P'), new Carta("10", 'D'), new Carta("6", 'P') };
		int[] resultadoEsperado = {6, 5, 4, 3, 2};
		
		assertTrue(EvaluadorDeManos.esEscaleraDeColor(cartas));
		assertTrue(Arrays.equals(EvaluadorDeManos.desempateEscaleraDeColor(cartas), resultadoEsperado));
	}


}
