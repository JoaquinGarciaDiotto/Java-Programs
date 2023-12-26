package Operaciones;
import java.util.Iterator;

import ABB.ABB;
import TDAArbol.*;
import TDAArbol.InvalidPositionException;
import TDAArbol.Position;
import TDACola.*;
import TDALista.*;
import TDAColaCP.*;

public class Operaciones_con_arboles {

	public static <E> void preOrden(Tree<E> t) {
		try {
			if(t!=null && !t.isEmpty())
				preOrdenAux(t,t.root());
		} catch (EmptyTreeException e) {e.printStackTrace();}
	}
	private static <E> void preOrdenAux(Tree<E> t, Position<E> n) {
		System.out.println(n.element());
		try {
			for(Position<E> h : t.children(n))
				preOrdenAux(t,h);
		} catch (InvalidPositionException e) {e.printStackTrace();}
	}
	public static <E> void postOrden(Tree<E> t) {
		try {
			if(t!=null && !t.isEmpty())
				postOrdenAux(t,t.root());
		} catch (EmptyTreeException e) {e.printStackTrace();}
	}
	private static <E> void postOrdenAux(Tree<E> t,Position<E> n) {
		try {
			for(Position<E> hijos : t.children(n))
				postOrdenAux(t,hijos);
			System.out.println(n.element());
		} catch (InvalidPositionException e) {e.printStackTrace();}
		
	}
	public static <E> void por_niveles(Tree<E> t) {
		Queue<Position<E>> Cola = new Cola_con_enlaces<Position<E>>();
		Position<E> n;
		try {
			Cola.enqueue(t.root());
			Cola.enqueue(null);
			while(!Cola.isEmpty()) {
				n = Cola.dequeue();
				if(n!=null) {
					System.out.print(n.element()+" ");
					for(Position<E> hijo : t.children(n))
						Cola.enqueue(hijo);
				}
				else {
					System.out.println();
					if (!Cola.isEmpty())
						Cola.enqueue(null);
				}
			}
		} catch (EmptyTreeException | EmptyQueueException | InvalidPositionException e) {e.printStackTrace();}
		
	}
	public static <E> int profundidad(Tree<E> t, Position<E> p) {
		int profundidad = 0;
		try {
			if(!t.isRoot(p))
				profundidad = 1 + profundidad(t,t.parent(p));
		} catch (InvalidPositionException | BoundaryViolationException e) {e.printStackTrace();}
		return profundidad;
	}
	public static <E> int altura(Tree<E> t, Position<E> p) {
		int altura = 0; int h;
		try {
			if(t.isInternal(p)) {
				h = 0;
				for(Position<E> hijo : t.children(p))
					h = Math.max(h, altura(t,hijo));
				altura = 1+h;
			}
		} catch (InvalidPositionException e) {e.printStackTrace();}
		return altura;
	}
	public static <E> PositionList<Position<E>> altura_justificada(Tree<E> t, Position<E> p){
		PositionList<Position<E>> aux, lista = new ListaDE<Position<E>>();
		try {
			for(Position<E> hijo : t.children(p)) {
				aux = altura_justificada(t,hijo);
				if(aux.size()>lista.size())
					lista = aux;
			}
			if(!t.isRoot(p))
				lista.addLast(p);
		}catch(InvalidPositionException e) {e.printStackTrace();}
		return lista;
	}
	public static <E> Tree<E> clonar(Tree<E> t1){
		Tree<E> t2 = new ArbolEnlazado<E>();
		try {
			if(t1!=null && !t1.isEmpty()) {
				t2.createRoot(t1.root().element());
				clonarAux(t1, t2, t1.root(),t2.root());}
		} catch (EmptyTreeException | InvalidOperationException e) {}
		return t2;
	}
	private static <E> void clonarAux(Tree<E> t1, Tree<E> t2, Position<E> nodo, Position<E> padreT2){
		try {
			Position<E> hijo;
			for(Position<E> n : t1.children(nodo)) {
				hijo = t2.addLastChild(padreT2, n.element());
				clonarAux(t1,t2,n,hijo);
			}
		} catch (InvalidPositionException e) {}
	}
	/*public void listadoEdad(Tree<E> t) {
		ColaCPconLista<Integer,Integer> C = new ColaCPconLista<Integer,Integer>(new Comparador_especial());
	}
	public void preOrdenA(Tree<E> t, PriorityQueue<E,E> C) {
		try {
			preOrdenAuxA((TNodo<E>) t.root(),C);
		} catch (EmptyTreeException e) {}
	}
	private void preOrdenAuxA(TNodo<E> n,PriorityQueue<E,E> C) {
		C.insert(n.element(), value)
		for(TNodo<E> hijos : n.getHijos())
			preOrdenAux(hijos);
	}*/
	private static <E> void toString(PositionList<Position<E>> l) {
		for(Position<E> p : l) {
			System.out.print(p.element()+" ");
		}
		/*System.out.println();
		System.out.print(l.size());*/
	}
	//eliminar static
	public static void imprimir_ascendente_hijos_pares(Tree<Integer> t) {
		PriorityQueue<Integer,Integer> cola = new ColaCPconHeap<Integer,Integer>();
		try {
			for(Position<Integer> p : t.positions())
				cola.insert(cantidad_rotulos_pares(t,p), p.element());
			while(!cola.isEmpty()) 
				System.out.print(cola.removeMin().getValue()+" ");
		} catch (InvalidKeyException | EmptyPriorityQueueException e) {e.printStackTrace();}
			
	}
	private static Integer cantidad_rotulos_pares(Tree<Integer> t, Position<Integer> p) {
		Integer cant = 0;
		try {
			for(Position<Integer> hijo : t.children(p))
				if(hijo.element()%2==0)
					cant++;
		} catch (InvalidPositionException e) {e.printStackTrace();}
		return cant;
	}
	public static void main(String[] args) {
		
		ArbolEnlazado<Integer> t = new ArbolEnlazado<Integer>();
		try {
			t.createRoot(0);
			Position<Integer> p1 = t.addLastChild(t.root(), 1);
			Position<Integer> p2 = t.addLastChild(t.root(), 2);
			Position<Integer> p3 = t.addLastChild(p1, 3);
			Position<Integer> p4 = t.addLastChild(p1, 4);
			Position<Integer> p5 = t.addLastChild(p2, 5);
			Position<Integer> p6 = t.addLastChild(p4, 6);
			Position<Integer> p7 = t.addLastChild(p5, 7);
			Position<Integer> p8 = t.addLastChild(t.root(), 8);
			///Position<Integer> p10 = t.addLastChild(p7, 10);
			//Position<Integer> p12 = t.addLastChild(p7, 12);
			//Position<Integer> p14 = t.addLastChild(p7, 14);
			/*
			 * 				0
			 * 			  / | \
			 * 			 1  8  2
			 * 			/ \   / 
			 * 		   3   4 5   
			 * 			  /   \
			 * 			 6     7
			 */
			por_niveles(t);
			System.out.println("------");
			ABB<Integer> ab = new ABB<Integer>();
			ab.expandir(ab.buscar(0), 0);
			ab.eliminarUltimoNodoHojaConCondicion(50, 4, t);
			por_niveles(t);
			//imprimir_ascendente_hijos_pares(t);
			//0=1, 1=1, 2=0, 4=1, 5=0
			//t.invertir_hijos_nodos_internos(0);
			//por_niveles(t);
			//por_niveles(clonar(t));
			//postOrden(t);
		} catch (InvalidOperationException | EmptyTreeException | InvalidPositionException e) {e.printStackTrace();}
	}

}
