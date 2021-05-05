package juego;

import java.util.Comparator;

public class EvaluadorDeManos implements Comparator<Carta>{
	
	public static Boolean esCartaAlta (Carta[] mano) {
		return true;
	}
	
	public static Boolean esPareja (Carta[] mano) {
		
		return true;
	}

	
	public static Boolean esDoblePareja (Carta[] mano) {
		return true;
	}

	
	public static Boolean esTrio (Carta[] mano) {
		return true;
	}

	
	public static Boolean esEscalera (Carta[] mano) {
		return true;
	}

	
	public static Boolean esColor (Carta[] mano) {
		return true;
	}

	
	public static Boolean esFullHouse (Carta[] mano) {
		return true;
	}

	public static Boolean esPoker (Carta[] mano) {
		return true;
	}
	
	public static Boolean esEscaleraDeColor (Carta[] mano) {
		return true;
	}

	@Override
	public int compare(Carta o1, Carta o2) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public static int[] ordenarCartasPorNumero(int[] mano) {
//		int i = 0;
//		while (i < 7) {
//			if (mano[])
//		}
		
		int i, j, aux;
        for (i = 0; i < mano.length - 1; i++) {
            for (j = 0; j < mano.length - i - 1; j++) {
                if (mano[j + 1] < mano[j]) {
                    aux = mano[j + 1];
                    mano[j + 1] = mano[j];
                    mano[j] = aux;
                }
            }
        }
		return mano;
	}
	
	public static int[] transformar(Carta[] mano) {
		int[] nuevaMano = new int[mano.length];
		for (int i = 0; i < mano.length; i++) {
			if (mano[i].getNumero().equals("J")) {
				nuevaMano[i] = 11;
			}
			else {
				if (mano[i].getNumero().equals("Q")) {
					nuevaMano[i] = 12;
				}
				else {
					if (mano[i].getNumero().equals("K")) {
						nuevaMano[i] = 13;
					}
					else {
						if (mano[i].getNumero().equals("A")) {
							nuevaMano[i] = 14;
						}
						else {
							nuevaMano[i] = Integer.parseInt(mano[i].getNumero());
						}
					}
				}
			}
		}
		return nuevaMano;
	}

}
