package juego;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class EvaluadorDeManos implements Comparator<Carta> {

	// ORDEN DE EVALUACIÓN: esEscaleraDeColor -> esPoker -> esFullHouse
	// -> esColor -> esEscalera -> esTrio -> esDoblePareja -> esPareja ->
	// esCartaAlta

	// metodo innecesario
	/*public static Boolean esCartaAlta(Carta[] mano) {
		int[] nuevaMano = transformar(mano);
		nuevaMano = ordenarCartasPorNumero(nuevaMano);
		for (int i = 0; i < nuevaMano.length - 1; i++) {
			if (nuevaMano[i] == nuevaMano[i + 1]) {
				return false;
			}
		}
		return true;
	}*/

	public static Boolean esPareja(Carta[] mano) {
		int[] nuevaMano = transformar(mano);
		nuevaMano = ordenarCartasPorNumero(nuevaMano);
		for (int i = 0; i < nuevaMano.length - 1; i++) {
			if (nuevaMano[i] == nuevaMano[i + 1]) {
				return true;
			}
		}
		return false;
	}

	public static Boolean esDoblePareja(Carta[] mano) {
		int[] nuevaMano = transformar(mano);
		nuevaMano = ordenarCartasPorNumero(nuevaMano);
		int numParejas = 0;
		for (int i = 0; i < nuevaMano.length - 1; i++) {
			if (nuevaMano[i] == nuevaMano[i + 1]) {
				numParejas++;
				if (numParejas > 1) {
					return true;
				}
			}
		}
		return false;
	}

	public static Boolean esTrio(Carta[] mano) {
		int[] nuevaMano = transformar(mano);
		nuevaMano = ordenarCartasPorNumero(nuevaMano);
		for (int i = 0; i < nuevaMano.length - 2; i++) {
			if (nuevaMano[i] == nuevaMano[i + 2]) {
				return true;
			}
		}
		return false;
	}

	public static Boolean esEscalera(Carta[] mano) {
		int[] nuevaMano = transformar(mano);
		nuevaMano = sinRepetir(nuevaMano);
		nuevaMano = ordenarCartasPorNumero(nuevaMano);
		if (nuevaMano.length > 4) {
			if (nuevaMano[3] == 5 && nuevaMano[nuevaMano.length - 1] == 14) {
				return true;
			}
			for (int i = 0; i < nuevaMano.length - 4; i++) {
				if (nuevaMano[i] + 4 == (nuevaMano[i + 4])) {
					return true;
				}
			}
		}
		return false;
	}

	/*public static Boolean esColor(Carta[] mano) {
		int contP = 0, contT = 0, contC = 0, contD = 0;
		for (int i = 0; i < mano.length; i++) {
			if (mano[i].getPalo() == 'P') {
				contP++;
				if (contP > 4) {
					return true;
				}
			} else {
				if (mano[i].getPalo() == 'T') {
					contT++;
					if (contT > 4) {
						return true;
					}
				} else {
					if (mano[i].getPalo() == 'C') {
						contC++;
						if (contC > 4) {
							return true;
						}
					} else {
						if (mano[i].getPalo() == 'D') {
							contD++;
							if (contD > 4) {
								return true;
							}
						}
					}
				}
			}
		}
		return false;
	}*/
	
	public static Boolean esColor(Carta[] mano) {
		mano = ordenarCartas(mano);
		Carta[] soloP = soloP(mano);
		if (soloP.length > 4) {
			return true;
		}
		Carta[] soloT = soloT(mano);
		if (soloT.length > 4) {
			return true;
		}
		Carta[] soloC = soloC(mano);
		if (soloC.length > 4) {
			return true;
		}
		Carta[] soloD = soloC(mano);
		if (soloD.length > 4) {
			return true;
		}
		return false;
	}

	public static Boolean esFullHouse(Carta[] mano) {
		int[] nuevaMano = transformar(mano);
		nuevaMano = ordenarCartasPorNumero(nuevaMano);
		int contT = 0;
		int contP = 0;
		for (int i = 0; i < nuevaMano.length - 1; i++) {
			if (i < nuevaMano.length - 2) {
				if (nuevaMano[i] == nuevaMano[i + 2]) {
					contT++;
					i += 2;
				} else {
					if (nuevaMano[i] == nuevaMano[i + 1]) {
						contP++;
					}
				}
			} else {
				if (nuevaMano[i] == nuevaMano[i + 1]) {
					contP++;
				}
			}
			if ((contT > 0 && contP > 0) || contT > 1) {
				return true;
			}
		}
		return false;
	}

	public static Boolean esPoker(Carta[] mano) {
		int[] nuevaMano = transformar(mano);
		nuevaMano = ordenarCartasPorNumero(nuevaMano);
		for (int i = 0; i < nuevaMano.length - 3; i++) {
			if (nuevaMano[i] == nuevaMano[i + 3]) {
				return true;
			}
		}
		return false;
	}

	public static Boolean esEscaleraDeColor(Carta[] mano) {
		mano = ordenarCartas(mano);
		Carta[] soloP = soloP(mano);
		Carta[] soloT = soloT(mano);
		Carta[] soloC = soloC(mano);
		Carta[] soloD = soloD(mano);
//		int[] soloP = transformar(soloP(mano));
//		int[] soloT = transformar(soloT(mano));
//		int[] soloC = transformar(soloC(mano));
//		int[] soloD = transformar(soloD(mano));
//		if (soloP.length > 4) {
//			if (soloP[3] == 5 && soloP[soloP.length - 1] == 14) {
//				return true;
//			}
//			for (int i = 0; i < soloP.length - 4; i++) {
//				if (soloP[i] + 4 == (soloP[i + 4])) {
//					return true;
//				}
//			}
//		}
//		return false;
		if (soloP.length > 0 && esEscalera(soloP)) {
			return true;
		} else {
			if (soloT.length > 0 && esEscalera(soloT)) {
				return true;
			} else {
				if (soloC.length > 0 && esEscalera(soloC)) {
					return true;
				} else {
					if (soloD.length > 0 && esEscalera(soloD)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	@Override
	public int compare(Carta c1, Carta c2) {
		// TODO Auto-generated method stub
		return 0;
	}

	public static int[] ordenarCartasPorNumero(int[] mano) {
		if (mano.length > 0) {
			Arrays.sort(mano);
		}
		return mano;
	}

	public static Carta[] ordenarCartas(Carta[] mano) {
		if (mano.length > 0) {
			Arrays.sort(mano, (c1, c2) -> c1.numero.compareTo(c2.numero));
		}
		return mano;
	}

	public static Carta[] soloP(Carta[] mano) {
		ArrayList<Carta> nuevaMano = new ArrayList<Carta>();
		for (int i = 0; i < mano.length; i++) {
			if (mano[i].getPalo() == 'P') {
				nuevaMano.add(mano[i]);
			}
		}
		Carta[] mano2 = new Carta[nuevaMano.size()];
		mano2 = nuevaMano.toArray(mano2);
		return mano2;
	}

	public static Carta[] soloT(Carta[] mano) {
		ArrayList<Carta> nuevaMano = new ArrayList<Carta>();
		for (int i = 0; i < mano.length; i++) {
			if (mano[i].getPalo() == 'T') {
				nuevaMano.add(mano[i]);
			}
		}
		Carta[] mano2 = new Carta[nuevaMano.size()];
		mano2 = nuevaMano.toArray(mano2);
		return mano2;
	}

	public static Carta[] soloC(Carta[] mano) {
		ArrayList<Carta> nuevaMano = new ArrayList<Carta>();
		for (int i = 0; i < mano.length; i++) {
			if (mano[i].getPalo() == 'C') {
				nuevaMano.add(mano[i]);
			}
		}
		Carta[] mano2 = new Carta[nuevaMano.size()];
		mano2 = nuevaMano.toArray(mano2);
		return mano2;
	}

	public static Carta[] soloD(Carta[] mano) {
		ArrayList<Carta> nuevaMano = new ArrayList<Carta>();
		for (int i = 0; i < mano.length; i++) {
			if (mano[i].getPalo() == 'D') {
				nuevaMano.add(mano[i]);
			}
		}
		Carta[] mano2 = new Carta[nuevaMano.size()];
		mano2 = nuevaMano.toArray(mano2);
		return mano2;
	}

	/*
	 * //Ordena de menor a mayor las cartas public static int[]
	 * ordenarCartasPorNumero(int[] mano) { int i, j, aux; for (i = 0; i <
	 * mano.length - 1; i++) { for (j = 0; j < mano.length - i - 1; j++) { if
	 * (mano[j + 1] < mano[j]) { aux = mano[j + 1]; mano[j + 1] = mano[j]; mano[j] =
	 * aux; } } } return mano; }
	 */

	// Convierte las J, Q, K y A en números
	public static int[] transformar(Carta[] mano) {
		int[] nuevaMano = new int[mano.length];
		for (int i = 0; i < mano.length; i++) {
			if (mano[i].getNumero().equals("J")) {
				nuevaMano[i] = 11;
			} else {
				if (mano[i].getNumero().equals("Q")) {
					nuevaMano[i] = 12;
				} else {
					if (mano[i].getNumero().equals("K")) {
						nuevaMano[i] = 13;
					} else {
						if (mano[i].getNumero().equals("A")) {
							nuevaMano[i] = 14;
						} else {
							nuevaMano[i] = Integer.parseInt(mano[i].getNumero());
						}
					}
				}
			}
		}
		return nuevaMano;
	}

	public static int[] sinRepetir(int[] mano) {
		if (mano.length > 0) {
			ordenarCartasPorNumero(mano);
			int j = 0;
			for (int i = 0; i < mano.length - 1; i++) {
				if (mano[i] != mano[i + 1]) {
					mano[j++] = mano[i];
				}
			}
			mano[j++] = mano[mano.length - 1];
			int[] nuevaMano = new int[j];
			for (int i = 0; i < j; i++) {
				nuevaMano[i] = mano[i];
			}
			mano = nuevaMano;
		}
		return mano;
	}

}
