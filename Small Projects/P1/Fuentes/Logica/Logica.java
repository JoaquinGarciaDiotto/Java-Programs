package Logica;




import java.util.Iterator;

import TDACola.*;
import TDALista.*;

/**
 * Clase Logica.
 * Metodos para el Proyecto N1
 * @author Joaquin Garcia Diotto
 */
public class Logica {
	
	/**
	 * Chequea si una cadena cualquiera tiene el formato correcto propuesto por el enunciado del ejercicio.
	 * @param cad Cadena a chequear
	 * @return Retorna un booleano indicando si la cadena tiene ese formato o no.
	 */
	public static boolean chequear_cadena(String cad){
		Cola_con_arreglo_circular<Character> A;
		Cola_con_arreglo_circular<Character> C;
		Cola_con_arreglo_circular<Character> Ainversa;
		boolean es = true;
		try {
		if (cad != null) {
			A = new Cola_con_arreglo_circular<Character>(cad.length());
			C = new Cola_con_arreglo_circular<Character>(cad.length());
			Ainversa = new Cola_con_arreglo_circular<Character>(cad.length());
			int i = 0; int j;				 //Uso a 'i' para recorrer la cadena, 'j' va a ser usado para para recorrer y controlar las partes "A" y "A'" de la cadena.
			while(es && i<cad.length()){		//En cada iteracion del while, almaceno la parte "C" de la cadena (pero sin consumirla) en una Cola, 
				CompletarC(C,cad,i);			//almaceno lo que se supone debe ser la parte "A" de la cadena en otra Cola y la inversa (o "A'") en una tercera Cola.
				A = CompletarA(C);
				es = A != null;
				j = (A!=null)? A.size(): 0;
				invertirCola(A,Ainversa, j);	
				Character aux1;
				while(es && j>0) {											//Controlo que se cumple la parte "A" de la cadena.
					if (cad.charAt(i) != A.front() || i==cad.length()-1) 
						es = false;
					else {
						aux1 = A.dequeue();
						A.enqueue(aux1);
						i++;
						j--;
					}
				}
				if (cad.charAt(i) != 'x' || (j==0 && cad.charAt(i) != 'x'))
					es = false;
				else
					i++;
				Character aux2;
				j = Ainversa.size();
				while(es && i<cad.length() && j>0) {		//Controlo que se cumple la parte "A'" de la cadena.
					if (cad.charAt(i) != Ainversa.front())
						es = false;
					else {
						aux2 = Ainversa.dequeue();
						Ainversa.enqueue(aux2);
						j--;
						i++;
					}
				}
				if (j==0 && i<cad.length()-1 && cad.charAt(i) == 'x') 
					i++;
				A = new Cola_con_arreglo_circular<Character>(cad.length());
				C =  new Cola_con_arreglo_circular<Character>(cad.length());
				Ainversa = new Cola_con_arreglo_circular<Character>(cad.length());
			}
		}
		else
			es = false;
		}
		catch(EmptyQueueException e) {}
		return es;
	}
	
	/**
	 * A partir de la parte "C" de la cadena, crea una cola de lo que deberia ser la parte "A" de la cadena.
	 * @param C Cola previamente creada a partir de la parte "C" de la cadena.
	 * @return Retorna lo que deberia ser la parte "A" de la cadena.
	 */
	
	private static Cola_con_arreglo_circular<Character> CompletarA(Cola_con_arreglo_circular<Character> C){
		Cola_con_arreglo_circular<Character> A = new Cola_con_arreglo_circular<Character>();
		Character temp; int i = C.size();
		try{
			while(i>0) {			//Se inserta en la nueva Cola la primera parte "C".
				temp = C.dequeue();
				A.enqueue(temp);
				C.enqueue(temp);
				i--;
			}
			A.enqueue('z');
			i = C.size()*2;
			while(i>0 && A != null) { //Se inserta en la nueva Cola dos veces la parte "C" para terminar de forma la parte "A".
				temp = C.dequeue();
				if (temp != 'a' && temp != 'b' && temp != null)
					A = null;
				else {
					A.enqueue(temp);
					C.enqueue(temp);
					i--;
				}
			}
		} catch(EmptyQueueException e) {}
		return A;
	}
	
	/**
	 * Modifica una Cola de forma tal que sea la parte "C" de la cadena.
	 * @param C Cola a modificar.
	 * @param cad Cadena original que se ingreso en el metodo 'chequear_cadena'.
	 * @param i Indice que recorre la cadena a partir de la posicion ingresada.
	 */
	private static void CompletarC(Cola_con_arreglo_circular<Character> C, String cad, int i){
		while(i<cad.length() && (cad.charAt(i) != 'z')) {
				C.enqueue(cad.charAt(i));
				i++;
			}
	}
	
	/**
	 * Modifica una Cola a partir de otra de forma que sea la inversa.
	 * @param A Cola que sera tomada como referencia para invertir.
	 * @param inv Cola que sera modificada para ser la inversa de "A".
	 * @param n Cantidad de caracteres que faltan por recorrer.
	 */
	private static void invertirCola(Queue<Character> A, Queue<Character> inv, int n) {
		if (n>0 && A!=null) {
			try {
				Character temp = A.dequeue();
				A.enqueue(temp);
				invertirCola(A,inv,n-1);
				inv.enqueue(temp);
			}catch (EmptyQueueException e) {}
		}
	}
	
	
	/**
	 * Intercala dos Listas previamente ordenadas ascendentemente.
	 * @param l1 Primera lista ordenada ascendentemente que se desea intercalar.
	 * @param l2 Segunda lista ordenada ascendentemente que se desea intercalar.
	 * @return Retorna una nueva lista ordenada de forma descendente sin repeticiones.
	 */
	public static PositionList<Integer> intercalar(PositionList<Integer> l1, PositionList<Integer> l2){
		PositionList<Integer> l3 = new ListaDE<Integer>();
		if (l1==null) l1 = new ListaDE<Integer>();
		if(l2==null) l2 = new ListaDE<Integer>();
		Iterator<Integer> it1 = l1.iterator(); 
		Iterator<Integer> it2 = l2.iterator(); 
		Integer A1, A2; 
		A1 = (it1.hasNext())? it1.next() : null; 
		A2 = (it2.hasNext())? it2.next() : null;
		while (A1 != null && A2 != null) { 						//Consumo ambas listas hasta que se acabe cualquiera de las dos. 
			if (A1==A2) {										//Agrego siempre los elementos de la lista l1 si son iguales, por lo tanto, tambien se debe consumir la lista l2 ya que ese elemento ya fue "checkeado" y no deberia repetirse.
				l3.addFirst(A1);
				A1 = (it1.hasNext())? it1.next() : null;
				A2 = (it2.hasNext())? it2.next() : null;
			}
			else 									
				if (A1<A2) {									//Agrego el que sea menor.
					l3.addFirst(A1);
					A1 = (it1.hasNext())? it1.next() : null;
				}
				else {
					l3.addFirst(A2);
					A2 = (it2.hasNext())? it2.next() : null;
				}	
		}								
		while(A1 != null) {										//Agrego los elementos restantes de la lista que haya faltado recorrer por completo.
			l3.addFirst(A1);
			A1 = (it1.hasNext())? it1.next() : null;
		}
		while(A2 != null) {
			l3.addFirst(A2);
			A2 = (it2.hasNext())? it2.next() : null;
		}
		return l3;
	}
	
	/**
	 * Crea una nueva lista con los elementos ordenados de la forma indicada por el enunciado.
	 * @param <E> Tipo de dato generico utilizado.
	 * @param l1 Lista la cual sera usada (pero no modificada) en el metodo.
	 * @return Retorna a partir de un metodo auxiliar la la lista requerida.
	 */
	public static <E> PositionList<E> zigzag(PositionList<E> l1){
		PositionList<E> resultado = new ListaDE<E>();
		if (l1 != null && !l1.isEmpty())
		try {
				resultado = zigzagAux(l1.first(),l1.last(), l1.size(), new ListaDE<E>());
		} catch (EmptyListException e) {}
		return resultado;
	}
	/**
	 * Metodo auxiliar recursivo utilizado por el metodo principal zigzag.
	 * @param <E> Elemento generico utilizado por el metodo.
	 * @param ini Posicion inicial de la cual empezar a recorrer y "achicar" la lista.
	 * @param fin Posicion final de la cual empezar a recorrer hacia el inicio y "achicar" la lista.
	 * @param cant Cantidad de elementos restantes por recorrer de la lista.
	 * @param l2 Lista ingresada a recorrer.
	 * @return Retorna una nueva lista con los elementos de l2 cambiados de orden segun lo indicado.
	 */
	private static <E> PositionList<E> zigzagAux(Position<E> ini, Position<E> fin, int cant, ListaDE<E> l2) {
		if (cant>0) {
			if (ini==fin)
				l2.addLast(ini.element());
			else {
				l2.addLast(ini.element());
				l2.addLast(fin.element());
				try {
					zigzagAux(l2.next(ini),l2.prev(fin),cant-2,l2);
				} catch (InvalidPositionException | BoundaryViolationException e) {}
				}
		}
		return l2;
	}
}
