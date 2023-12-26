package Operaciones;
import java.util.Comparator;

import ABB.DefaultComparator;
import TDAArbolBinario.*;
import TDACola.*;
import TDALista.EmptyListException;
import TDALista.ListaDE;
import TDALista.PositionList;

public class Operaciones_con_arboles_binarios {
	
	public static <E> void preOrden(BinaryTree<E> t) {
		if(!t.isEmpty())
			try {
				preOrdenAux(t, t.root());
			} catch (EmptyTreeException e) {e.printStackTrace();}
	}
	private static <E> void preOrdenAux(BinaryTree<E> t, Position<E> n) {
		System.out.print(n.element());
		try {
			if(t.hasLeft(n))
				preOrdenAux(t, t.left(n));
			if(t.hasRight(n))
				preOrdenAux(t,t.right(n));
		} catch (InvalidPositionException | BoundaryViolationException e) {e.printStackTrace();}
	}
	
	public static <E> void postOrden(BinaryTree<E> t) {
		if(!t.isEmpty())
			try {
				postOrdenAux(t,t.root());
			} catch (EmptyTreeException e) {e.printStackTrace();}
	}
	private static <E> void postOrdenAux(BinaryTree<E> t, Position<E> n) {
		try {
			if(t.hasLeft(n))
				postOrdenAux(t, t.left(n));
			if(t.hasRight(n))
				postOrdenAux(t,t.right(n));
		} catch (InvalidPositionException | BoundaryViolationException e) {e.printStackTrace();}
		System.out.print(n.element());
	}
	
	public static <E> void por_niveles(BinaryTree<E> t) {
		Queue<Position<E>> Cola = new Cola_con_enlaces<Position<E>>();
		Position<E> n;
		try {
			Cola.enqueue(t.root());
			Cola.enqueue(null);
			while(!Cola.isEmpty()) {
				n = Cola.dequeue();
				if(n!=null) {
					System.out.print(n.element()+" ");
					if(t.hasLeft(n))
						Cola.enqueue(t.left(n));
					if(t.hasRight(n))
						Cola.enqueue(t.right(n));
				}
				else {
					System.out.println();
					if (!Cola.isEmpty())
						Cola.enqueue(null);
				}
			}
		} catch (EmptyTreeException | EmptyQueueException | InvalidPositionException | BoundaryViolationException e) {e.printStackTrace();}
		
	}
	
	public static <E> int profundidad(BinaryTree<E> t, Position<E> p) {
		int profundidad = 0;
		try {
			if(!t.isRoot(p)) {
				profundidad = 1 + profundidad(t,t.parent(p));
			}
		} catch (InvalidPositionException | BoundaryViolationException e) {e.printStackTrace();}
		return profundidad;
	}
	public static <E> int altura(BinaryTree<E> t, Position<E> p) {
		int altura = 0; int h;
		try {
			if(t.isInternal(p)) {
				h = 0;
				if(t.hasLeft(p))
					h = Math.max(h, altura(t,t.left(p)));
				if(t.hasRight(p))
					h = Math.max(h, altura(t,t.right(p)));
				altura = 1+h;
			}
		} catch (InvalidPositionException | BoundaryViolationException e) {e.printStackTrace();}
		return altura;
	}
	public static <E> BinaryTree<E> clonar(BinaryTree<E> t1){
		BinaryTree<E> t2 = new ArbolBinarioEnlazado<E>();
		if(t1!=null & !t1.isEmpty()) {
			try {
				t2.createRoot(t1.root().element());
				clonar(t1.root(),t2.root(),t1,t2);
			} catch (InvalidOperationException | EmptyTreeException e) {e.printStackTrace();}
		}
		return t2;
	}
	private static <E> void clonar(Position<E> p1, Position<E> p2, BinaryTree<E> t1, BinaryTree<E> t2) {
		try {
			if(t1.hasLeft(p1)) {
				t2.addLeft(p2, t1.left(p1).element());
				clonar(t1.left(p1),t2.left(p2),t1,t2);
			}
			if(t1.hasRight(p1)) {
				t2.addRight(p2, t1.right(p1).element());
				clonar(t1.right(p1),t2.right(p2),t1,t2);
			}
		} catch (InvalidPositionException | InvalidOperationException | BoundaryViolationException e) {e.printStackTrace();}	
	}
	public static <E> BinaryTree<E> clonar_espejado(BinaryTree<E> t1){
		BinaryTree<E> t2 = new ArbolBinarioEnlazado<E>();
		if(t1!=null & !t1.isEmpty()) {
			try {
				t2.createRoot(t1.root().element());
				clonar_espejado(t1.root(),t2.root(),t1,t2);
			} catch (InvalidOperationException | EmptyTreeException e) {e.printStackTrace();}
		}
		return t2;
	}
	private static <E> void clonar_espejado(Position<E> p1, Position<E> p2, BinaryTree<E> t1, BinaryTree<E> t2) {
		try {
			if(t1.hasLeft(p1)) {
				t2.addRight(p2, t1.left(p1).element());
				clonar_espejado(t1.left(p1),t2.right(p2),t1,t2);
			}
			if(t1.hasRight(p1)) {
				t2.addLeft(p2, t1.right(p1).element());
				clonar_espejado(t1.right(p1),t2.left(p2),t1,t2);
			}
		} catch (InvalidPositionException | InvalidOperationException | BoundaryViolationException e) {e.printStackTrace();}	
	}
	//Dos arboles son iguales sus primeros nodos son iguales y los dos subarboles tambien lo son.
	public static <E> boolean iguales(BinaryTree<E> t1, BinaryTree<E> t2) {
		boolean son = false;
		try {
			if(!t1.isEmpty() && !t2.isEmpty())
				son = iguales(t1,t2,t1.root(),t2.root());
			else
				son = true;
		} catch (EmptyTreeException e) {e.printStackTrace();}
		return son;
	}
	private static <E> boolean iguales(BinaryTree<E> t1, BinaryTree<E> t2, Position<E> p1, Position<E> p2) {
		boolean son = true;
		if(!p1.element().equals(p2.element()))
			son = false;
		else
			try {
				boolean p1TieneIzq = t1.hasLeft(p1), p1TieneDer = t1.hasRight(p1), p2TieneIzq = t2.hasLeft(p2), p2TieneDer = t2.hasRight(p2);
				
				if((p1TieneIzq && p2TieneIzq) && (p1TieneDer && p2TieneDer))
					son = iguales(t1,t2,t1.right(p1),t2.right(p2)) && iguales(t1,t2,t1.left(p1),t2.left(p2));
				else
					if((p1TieneIzq && p2TieneIzq) && (!p1TieneDer && !p2TieneDer))
						son = iguales(t1,t2,t1.left(p1),t2.left(p2));
					else
						if((p1TieneDer && p2TieneDer) && (!p1TieneIzq && !p2TieneIzq))
							son = iguales(t1,t2,t1.right(p1),t2.right(p2));
						else
							if((!p1TieneDer && !p2TieneDer) && (!p1TieneIzq && !p2TieneIzq))
								son = true;
							else
								son = false;
			} catch (InvalidPositionException | BoundaryViolationException  e) {e.printStackTrace();}
		return son;
	}
	//t2 subarbol de t1
	public static <E> boolean subarbol(BinaryTree<E> t1, BinaryTree<E> t2) {
		boolean son = false;
		if(t1 != null && !t1.isEmpty() && t2 != null && !t2.isEmpty())
			try {
				son = subarbol(t1,t2,t1.root(), t2.root());
			} catch (EmptyTreeException e) {e.printStackTrace();}
		return son;
	}
	private static <E> boolean subarbol(BinaryTree<E> t1, BinaryTree<E> t2, Position<E> p1, Position<E> p2) {
		boolean son = false;
		if(p1==null)
			son = true;
		else
			if(p2==null)
				son = false;
			else
				if(iguales(t1,t2,p1,p2))
					son = true;
				else
					try {
						if(t1.hasLeft(p1) && t1.hasRight(p1))
							son = subarbol(t1,t2,t1.left(p1),p2) || subarbol(t1,t2,t1.right(p1),p2);
						else if(t1.hasLeft(p1) && !t1.hasRight(p1))
							son = subarbol(t1,t2,t1.left(p1),p2);
						else if(t1.hasRight(p1) && !t1.hasLeft(p1))
							son = subarbol(t1,t2,t1.right(p1),p2);
						else
							son = false;
					} catch (InvalidPositionException | BoundaryViolationException e) {e.printStackTrace();}
		return son;
	}
	
	public static <E> BinaryTree<E> clonar_arbol_perfecto(int h, TDALista.PositionList<E> l){
		BinaryTree<E> clonA = new ArbolBinarioEnlazado<E>();
		TDALista.Position<E> p;
		if(!l.isEmpty())
			try {
				p = l.first();
				clonA.createRoot(p.element());
				if(l.size()>1) 
					clonarArbolAux(h,l,p,clonA,clonA.root());
			} catch (InvalidOperationException | TDALista.EmptyListException | EmptyTreeException e) {e.printStackTrace();}	
		return clonA;
	}
	private static <E> void clonarArbolAux(int h, TDALista.PositionList<E> l, TDALista.Position<E> p, BinaryTree<E> clon, Position<E> n) {
		if(h>=0) {
			try {
				clon.addLeft(n, p.element());
				clonarArbolAux(h-1,l,l.next(p),clon,clon.left(n));
				clon.addRight(n,p.element());
				clonarArbolAux(h-1,l,l.next(p),clon,clon.right(n));
			} catch (InvalidOperationException | InvalidPositionException | TDALista.InvalidPositionException | TDALista.BoundaryViolationException | BoundaryViolationException e) {e.printStackTrace();}
			
		}
	}
	public static <E> boolean existeCamino(BinaryTree<E> t, Position<E> n1, Position<E> n2) {
		return (!t.isEmpty() && n1!=null && n2!=null && buscarCamino(t,n1, new ListaDE<Position<E>>()) && buscarCamino(t,n2, new ListaDE<Position<E>>()));
	}
	private static <E> boolean buscarCamino(BinaryTree<E> t, Position<E> n, PositionList<Position<E>> l) {
		boolean existe = true;
		if(n==null)
			existe = false;
		else {
			l.addLast(n);
			try {
				if(n!=t.root())
					existe = existe && buscarCamino(t,t.parent(n),l);
			} catch (EmptyTreeException | InvalidPositionException | BoundaryViolationException e) {e.printStackTrace();}
		}
		return existe;
	}
	public static <E> PositionList<Position<E>> camino(BinaryTree<E> t, Position<E> n1, Position<E> n2){
		PositionList<Position<E>> camino,camino1,camino2;
		camino = new ListaDE<Position<E>>();
		camino1 = new ListaDE<Position<E>>();
		camino2 = new ListaDE<Position<E>>();
		TDALista.Position<Position<E>> e1, e2;
		Position<E> ancestro = null;
		try {
			if(n1!=null && n2!=null && !t.isEmpty() && buscarCamino(t,n1,camino1) && buscarCamino(t,n2,camino2)) {
				e1 = camino1.last();
				e2 = camino2.last();
				while(e1.element()==e2.element() && !camino1.isEmpty() && !camino2.isEmpty()) {
					ancestro = e1.element();
					camino1.remove(e1);
					camino2.remove(e2);
					if(!camino1.isEmpty())
						e1 = camino1.last();
					if(!camino2.isEmpty())
						e2 = camino2.last();
				}
				while(!camino1.isEmpty())
					camino.addFirst(camino1.remove(camino1.last()));
				camino.addLast(ancestro);
				while(!camino2.isEmpty())
					camino.addLast(camino2.remove(camino2.last()));
			}
		} catch (EmptyListException | TDALista.InvalidPositionException e) {e.printStackTrace();} 
		return camino;
	}
	public static <E> PositionList<Position<E>> profundidad_justificada(BinaryTree<E> t, Position<E> n1){
		PositionList<Position<E>> camino = new ListaDE<Position<E>>();
		while(n1!=null) {
			camino.addLast(n1);
			try {
				n1 = (n1==t.root())? null : t.parent(n1);
			} catch (EmptyTreeException | InvalidPositionException | BoundaryViolationException e) {e.printStackTrace();}
		}
		return camino;
	}
	public static <E> PositionList<Position<E>> altura_justificada(BinaryTree<E> t, Position<E> n){
		PositionList<Position<E>> aux, lista = new ListaDE<Position<E>>();
		try {
			if(t.hasLeft(n)) {
				aux = altura_justificada(t,t.left(n));
				if(aux.size()>lista.size())
					lista = aux;
			}
			if(t.hasRight(n)) {
				aux = altura_justificada(t,t.right(n));
				if(aux.size()>lista.size())
					lista = aux;
			}
		}catch(InvalidPositionException | BoundaryViolationException e) {e.printStackTrace();}
		lista.addFirst(n);
		return lista;
	}
	public static <E> boolean esPropio(BinaryTree<E> t) {
		boolean es = true;
		if(t!=null && !t.isEmpty())
			try {
				es = esPropioAux(t,t.root());
			} catch (EmptyTreeException e) {e.printStackTrace();}
		return es;
	}
	private static <E> boolean esPropioAux(BinaryTree<E> t, Position<E> n) {
		boolean es = true;
		try {
			if(!t.hasLeft(n) && t.hasRight(n))
				es = false;
			else if (!t.hasRight(n) && t.hasLeft(n))
				es = false;
			else if(t.hasLeft(n) && t.hasRight(n))
				es = esPropioAux(t,t.left(n)) && esPropioAux(t,t.right(n));
		} catch (InvalidPositionException | BoundaryViolationException e) {e.printStackTrace();}
		return es;
	}
	public static <E> boolean igual_profundidad(BinaryTree<E> t1, BinaryTree<E> t2) {
		return profundidad(t1)==profundidad(t2);
	}
	private static <E> int profundidad(BinaryTree<E> t) {
		int profundidad = 0;
		if(t!=null && !t.isEmpty()) {
			try {
				profundidad = profundidad(t,nodoMasProfundo(t,t.root()));
			} catch (EmptyTreeException e) {e.printStackTrace();}
		}
		return profundidad;
	}
	private static<E> Position<E> nodoMasProfundo(BinaryTree<E> t1,Position<E> n) {
		Position<E> nodo = null;
		Queue<Position<E>> q = new Cola_con_enlaces<Position<E>>();
		q.enqueue(n);
		while(!q.isEmpty()) {
			try {
				nodo = q.dequeue();
				if(t1.hasLeft(nodo))
					q.enqueue(t1.left(nodo));
				if(t1.hasRight(nodo))
					q.enqueue(t1.right(nodo));
			} catch (EmptyQueueException | InvalidPositionException | BoundaryViolationException e) {e.printStackTrace();}
		}
		return nodo;
	}
	private static <E> Position<E> nodoMasProfundo2(BinaryTree<E> t, Position<E> n, int altura){
		Position<E> nodo = null;
		if(altura==0)
			nodo = n;
		else if(altura>=1) {
				try {
					if(t.hasLeft(n))
						nodo = nodoMasProfundo2(t,t.left(n),altura-1);
					if (t.hasRight(n) && nodo==null)
						nodo = nodoMasProfundo2(t,t.right(n),altura-1);
				} catch (InvalidPositionException | BoundaryViolationException e) {e.printStackTrace();}
		}
		return nodo;
	}
	public static void imprimir_expresion(BinaryTree<String> t) {
		try {
			System.out.print("prefija: "+imprimir_prefija(t,t.root()));
			System.out.println();
			System.out.print("sufija: "+imprimir_sufija(t,t.root()));
			System.out.println();
			System.out.print("infija: "+imprimir_infija(t,t.root()));
			System.out.println();
		} catch (EmptyTreeException e) {e.printStackTrace();}
	}
	private static String imprimir_infija(BinaryTree<String> t, Position<String> n) {
		String s = "";
		try {
			if(t.hasLeft(n)) {
				if(esOperador(n.element()))
					s += "(";
				s += imprimir_infija(t,t.left(n));
			}
			s += n.element();
			if(t.hasRight(n)) {
				s += imprimir_infija(t,t.right(n));
				if(esOperador(n.element()))
					s += ")";
			}
		} catch (InvalidPositionException | BoundaryViolationException e) {e.printStackTrace();}
		return s;
	}
	private static String imprimir_prefija(BinaryTree<String> t, Position<String> n) {
		String s = "";
		try {
			s += n.element();
			if(t.hasLeft(n))
				s += imprimir_prefija(t, t.left(n));
			if(t.hasRight(n))
				s += imprimir_prefija(t,t.right(n));
		} catch (InvalidPositionException | BoundaryViolationException e) {e.printStackTrace();}
		return s;
	}
	private static String imprimir_sufija(BinaryTree<String> t, Position<String> n) {
		String s = "";
		try {
			if(t.hasLeft(n))
				s += imprimir_sufija(t, t.left(n));
			if(t.hasRight(n))
				s += imprimir_sufija(t,t.right(n));
			s += n.element();
		} catch (InvalidPositionException | BoundaryViolationException e) {e.printStackTrace();}
		return s;
	}
	private static boolean esOperador(String c) {
		return c=="*" || c=="/" || c=="'" || c=="+";
	}
	public static BinaryTree<String> parse(String exp){
		BinaryTree<String> t1,t2, t = new ArbolBinarioEnlazado<String>();
		String op;
		try {
			if(!contieneOperador(exp))
				t.createRoot(exp);
			else {
				exp = eliminarParentesis(exp);
				int i = ultimo_operador(exp);
				op = exp.substring(i, i+1);
				t1 = parse(izquierda(exp,i));
				t2 = parse(derecha(exp,i));
				t.createRoot(op);
				t.attach(t.root(), t1, t2);
			}
		} catch (InvalidOperationException | InvalidPositionException | EmptyTreeException e) {e.printStackTrace();}
		return t;
	}
	private static boolean contieneOperador(String s) {
		return s.contains("-") || s.contains("+") || s.contains("*") || s.contains("/");
	}
	private static String eliminarParentesis(String s) {
		String devolver = new String();
		for(int i = 1; i<s.length()-1; i++) {
			devolver += s.charAt(i);
		}
		return devolver;
	}
	private static int ultimo_operador(String s) {
		int i,cont = i = 0;
		if(s.charAt(0)=='(')
			cont++;
		while(cont>0) {
			i++;
			if(s.charAt(i)=='(')
				cont++;
			else if (s.charAt(i)==')')
				cont--;
		}
		i++;
		return i;
	}
	private static String izquierda(String exp, int op){
		return exp.substring(0, op);
	}
	private static String derecha(String exp, int op){
		return exp.substring(op+1);
	}
	public static Float evaluar(BinaryTree<String> t) {
		Float resultado = null;
		if(t!=null && !t.isEmpty())
			try {
				resultado = evaluarAux(t,t.root());
			} catch (EmptyTreeException e) {e.printStackTrace();}
		return resultado;
	}
	private static Float evaluarAux(BinaryTree<String> t, Position<String> n) {
		Float v1,v2,resultado = null;
		String op;
		try {
			if(t.isExternal(n))
				resultado = Float.parseFloat(n.element());
			else {
				op = n.element();
				v1 = evaluarAux(t,t.left(n));
				v2 = evaluarAux(t,t.right(n));
				resultado = aplicar(op,v1,v2);
			}
		} catch (NumberFormatException | InvalidPositionException | BoundaryViolationException e) {e.printStackTrace();}
		return resultado;
	}
	private static Float aplicar(String op, Float v1, Float v2) {
		Float resultado = null;
		char o = op.charAt(0);
		if(o=='+')
			resultado = v1+v2;
		else if(o=='-')
			resultado = v1-v2;
		else if(o=='/')
			resultado = v1/v2;
		else if(o=='*')
			resultado = v1*v2;
		return resultado;
	}
	public static <E extends Comparable<E>> boolean esAVL(BinaryTree<E> t) {
		boolean es = true;
		if(!t.isEmpty())
			try {
				es = esAVLAux(t,t.root());
			} catch (EmptyTreeException e) {e.printStackTrace();}
		return es;
	}
	private static <E extends Comparable<E>> boolean esAVLAux(BinaryTree<E> t, Position<E> n) {
		boolean es = true;
		int alturaIzq, alturaDer = alturaIzq = -1;
		Comparator<E> comp = new AVL.DefaultComparator<E>();
		try {
			if(t.isInternal(n)) {
				if(t.hasLeft(n))
					alturaIzq = altura(t,t.left(n));
				if(t.hasRight(n))
					alturaDer = altura(t,t.right(n));
				
				if(Math.abs(alturaIzq-alturaDer)>1)
					es = false;
				else if(t.hasLeft(n) && t.hasRight(n))
					es = comp.compare(t.right(n).element(), n.element())>0 && comp.compare(t.left(n).element(), n.element())<0 && esAVLAux(t,t.left(n)) && esAVLAux(t,t.right(n));
				else if(t.hasLeft(n) && !t.hasRight(n))
					es = comp.compare(t.left(n).element(), n.element())<0 && esAVLAux(t,t.left(n));
				else if(!t.hasLeft(n) && t.hasRight(n))
					es = comp.compare(t.right(n).element(), n.element())>0 && esAVLAux(t,t.right(n));
			}
		} catch (InvalidPositionException | BoundaryViolationException e) {e.printStackTrace();}
		return es;
	}
	public static <E extends Comparable<E>> boolean esABB(BinaryTree<E> t) {			//Para que sea ABB, los hijos de cada nodo deben ser de la forma: el hijo izquierdo es menor al padre, y el derecho mayor al padre. (O sea)
		boolean es = true;
		if(t!=null && !t.isEmpty()) {
			try {
				es = esABBAux(t,t.root());
			} catch (EmptyTreeException e) {e.printStackTrace();}
		}
		return es;
	}
	private static <E extends Comparable<E>> boolean esABBAux(BinaryTree<E> t, Position<E> n) {
		boolean es = true;
		Comparator<E> comp = new DefaultComparator<E>();
		try {
			if(t.hasLeft(n) && !t.hasRight(n))
				es = comp.compare(t.left(n).element(), n.element())<0 && esABBAux(t,t.left(n));
			else if(!t.hasLeft(n) && t.hasRight(n))
				es = comp.compare(t.right(n).element(), n.element())>0 && esABBAux(t,t.right(n));
			else if(t.hasLeft(n) && t.hasRight(n))
				es = comp.compare(t.right(n).element(), n.element())>0 && comp.compare(t.left(n).element(), n.element())<0 && esABBAux(t,t.left(n)) && esABBAux(t,t.right(n));
		} catch (InvalidPositionException | BoundaryViolationException e) {e.printStackTrace();}
		return es;
	}
	private static <E> void toString(PositionList<Position<E>> l) {
		for(Position<E> p : l) 
			System.out.print(p.element()+" ");
	}
	
	public void enviarRoman(Roman roman) {
		roman.tp("kentaHouse");
	}
	private class Roman{
		public void tp(String coordenadas) {
			
		}
	}
	
	public static void main(String a[]) {
		BinaryTree<Integer> t = new ArbolBinarioEnlazado<Integer>();
		try {
			Position<Integer> raiz = t.createRoot(0);
			Position<Integer> p1 = t.addLeft(raiz, 1);
			Position<Integer> p2 = t.addRight(raiz, 2);
			Position<Integer> p3 = t.addLeft(p1, 3);
			Position<Integer> p4 = t.addRight(p1, 4);
			//Position<Integer> p5 = t.addRight(p2, 5);
			//Position<Integer> p6 = t.addRight(p1, 6);
			//Position<Integer> p7 = t.addRight(raiz, 7);
			/**
			 * 						0
			 * 					   / \
			 * 					  /   \
			 * 					 1     2
			 * 					/ \
			 * 				   /   \
			 * 				  3     4
			 */
			System.out.println(esAVL(t));
			/*por_niveles(t);
			System.out.println();
			t.podarSubarbolConditional(raiz, 8);
			if(!t.isEmpty())
					por_niveles(t);
			System.out.println();
			System.out.println(t.size());*/
			//preOrden(t);
			/*BinaryTree<String> t1 = new ArbolBinarioEnlazado<String>();
			t1.createRoot("/");
			Position<String> mas1 = t1.addLeft(t1.root(), "+");
			Position<String> siete1 = t1.addRight(t1.root(), "7");
			Position<String> dos1 = t1.addLeft(mas1, "2");
			Position<String> por1 = t1.addRight(mas1, "*");
			Position<String> tres1 = t1.addLeft(por1, "3");
			Position<String> cuatro1 = t1.addRight(por1, "4");*/
			/**
			 * 					/
			 * 				  /   \
			 * 				 /	   \
			 * 				+	    7
			 * 			   / \
			 *    		  /   \
			 *    		 2     *
			 *    			  / \
			 *    			 /   \
			 *    			3	  4
			 */
			/*imprimir_expresion(t1);
			BinaryTree<String> t2 = parse(imprimir_infija(t1,t1.root()));
			por_niveles(t2);
			System.out.println();
			System.out.println(evaluar(t2));*/
		} catch (InvalidOperationException | InvalidPositionException e) {e.printStackTrace(); }
	}
}
