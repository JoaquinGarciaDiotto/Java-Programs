package TDAColaCP;

import java.util.Comparator;

public class Comparador_especial implements Comparator<Integer> {

	//Retorna 1, si n1 tiene mas prioridad que n2 (n1>n2), retorna -1 si n1 tiene menos priordad que n2 (n1<n2) y retorna 0 si n1 y n2 tienen la misma priordad (n1=n2)
	@Override
	public int compare(Integer n1, Integer n2) {
		int prioridad = 0;
		if(esPar(n1))
			if(esPar(n2)) 
				if(n1<n2)
					prioridad = 1;
				else if(n1>n2)
					prioridad = -1;
				else
					prioridad = 0;
			else //Entonces, n2 es impar y n1 tiene mas priordad que n2.
				prioridad = 1;
		else				//n1 es impar.
			if(esImpar(n2))
				if(n1>n2)
					prioridad = 1;
				else if(n1<n2)
					prioridad = -1;
				else
					prioridad = 0;
			else //Entonces, n2 es par y n1 tiene menos prioridad que n2;
				prioridad = -1;
			
		
		/*if(esPar(n1) && esImpar(n2))
			prioridad = 1;
		else if(esImpar(n1) && esPar(n2))
			prioridad = -1;
		else if(esPar(n1) && esPar(n2)) {
				if(n1<n2)
					prioridad = 1;
				else if(n1>n2)
					prioridad = -1;
				else 
					prioridad = 0;
			}
		else if(esImpar(n1) && esImpar(n2)) {
				if(n1>n2)
					prioridad = 1;
				else if(n1<n2)
					prioridad = -1;
				else 
					prioridad = 0;
			}*/
		return prioridad;
	}
	
	private boolean esPar(int n) {
		return n%2==0;
	}
	private boolean esImpar(int n) {
		return n%2!=0;
	}
}
