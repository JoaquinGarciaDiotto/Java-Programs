package Operaciones;
import java.util.Iterator;

import TDALista.*;


public class OperacionesConListas{
	public boolean contenido_e_invertido(PositionList<Character> l1, PositionList<Character> l2){
		boolean es = false;
		try {
			Position<Character> AL1 = l1.first(); Position<Character> AL2 = l2.first(); int cant = l2.size();
			es = (cant*2==l1.size());
			while(cant>0 && es) {
				if (!AL1.element().equals(AL2.element()))
					es = false;
				else {
					cant--;
					AL1 = l1.next(AL1);
					AL2 = l2.next(AL2);
				}
					
			}
			cant=l2.size(); AL2 = l2.last();
			while(cant>0 && es) {
				if (!AL1.element().equals(AL2.element()))
					es = false;
				else {
					AL1 = l1.next(AL1);
					AL2 = l2.prev(AL2);
					cant--;
				}
			}
		}
		catch(EmptyListException | InvalidPositionException | BoundaryViolationException e){}
		return es;
	}
	
	public PositionList<Integer> intercalar_sin_repetidos(PositionList<Integer> l1, PositionList<Integer> l2){
		PositionList<Integer> l3 = new ListaSE<Integer>(); int i1 = l1.size(), i2 = l2.size(), k = 0;
		try {
			 Position<Integer> AL1 = l1.first(), AL2 = l2.first(), AL3 = null;
			 while(i1>0 && i2>0) {
				 if (AL2.element().compareTo(AL1.element())>=0) {//Si AL2>AL1
					if (!l3.isEmpty()) {
						if (!pertenece(l3,AL1.element(),k)) {
							l3.addAfter(AL3,AL1.element());
							i1--;
							AL1 = (i1==0)? null : l1.next(AL1);
							AL3 = l3.next(AL3);
							k++;
						}
						else {
							i1--;
							AL1 = (i1==0)? null : l1.next(AL1);
							}
					}
					else {
						l3.addFirst(AL1.element());	
						i1--;
						AL1 = (i1==0)? null : l1.next(AL1);
						AL3 = l3.first();
						k++;
						}
				 }
				 else 
					 if(!pertenece(l3,AL2.element(),k)) {
						 if (!l3.isEmpty()) { 
							 l3.addAfter(AL3,AL2.element());
						 	 i2--;
						 	 AL2 = (i2==0)? null : l2.next(AL2);
						 	 AL3 = l3.next(AL3);
						 	 k++;
						 }	 
					 	 else { 
					 	 	 l3.addFirst(AL2.element());
					 	 	 AL2 = (i2==0)? null : l2.next(AL2);
					 	 	 AL3 = l3.first();
					 	 	 i2--;
					 	 	 k++;
					 	 	 }
						 
				 	}
				 	else {
				 			i2--;
				 			AL2 = (i2==0)? null : l2.next(AL2);
				 		}
			 }
			 if (i1>0) 
				 while(i1>0) {
					 if(!pertenece(l3,AL1.element(),k)) {
						 l3.addAfter(AL3,AL1.element());
						 i1--;
						 AL1 = (i1==0)? null : l1.next(AL1);
						 AL3 = l3.next(AL3);
						 k++;
						 System.out.println("Hola8, i1: "+i1+" k: "+k);
					 }
					 else {
						 i1--;
						 AL1 = (i1==0)? null : l1.next(AL1);
						 System.out.println("Hola9, i1: "+i1);
					 }
				 }
			 else
				 while(i2>0) {
					 if (!pertenece(l3,AL2.element(),k)) {
						 l3.addAfter(AL3,AL2.element());
						 i2--;
						 AL2 = (i2==0)? null : l2.next(AL2);
						 AL3 = l3.next(AL3);
						 k++;
						 System.out.println("Hola10, i2: "+i2+" k: "+k);
					 }
					 
					 else {
						 i2--;
						 AL2 = (i2==0)? null : l2.next(AL2);
						 System.out.println("Hola11, i2: "+i2);
					 } 
				 }
			 return l3;
		}
		catch(EmptyListException | InvalidPositionException | BoundaryViolationException e){e.printStackTrace();}
		return l3;
	}
	
	public static PositionList<Integer> intercalar_ordenados_sin_repetidos(PositionList<Integer> l1, PositionList<Integer> l2){ //Malardo, fijarme en Logica
		PositionList<Integer> l3 = new ListaDE<Integer>(); 
			Iterator<Integer> it1 = l1.iterator(); Iterator<Integer> it2 = l2.iterator(); Integer A1, A2; int k = 0;
				A1 = (it1.hasNext())? it1.next() : null; 
				A2 = (it2.hasNext())? it2.next() : null;
				while (A1 != null && A2 != null) {
					if (A2.compareTo(A1)>=0)
						if (!pertenece(l3,A1,k)) {
							l3.addLast(A1);
							A1 = (it1.hasNext())? it1.next() : null;
							k++;
						}
						else
							A1 = (it1.hasNext())? it1.next() : null;
					else {
						if (!pertenece(l3,A2,k)) {
							l3.addLast(A2);
							k++;
							A2 = (it2.hasNext())? it2.next() : null;
						}
						else 
							A2 = (it2.hasNext())? it2.next() : null;
					}
				}
				if (A1 != null) 
					while(A1 != null) {
						if (!pertenece(l3,A1,k)) {
							l3.addLast(A1);
							A1 = (it1.hasNext())? it1.next() : null;
							k++;
						}
						else
							A1 = (it1.hasNext())? it1.next() : null;
					}
				else
					while(A2 != null) {
						if (!pertenece(l3,A2,k)) {
							l3.addLast(A2);
							k++;
							A2 = (it2.hasNext())? it2.next() : null;
						}
						else 
							A2 = (it2.hasNext())? it2.next() : null;
					}
			
		return l3;
	}
	private static boolean pertenece(PositionList<Integer> l, Integer e, int cant) { //O(n), n=l.size(); ya que recorre toda la estructura "l" en caso de que no este (el peor caso)
		boolean esta = false; 
		try{ 
			if (!l.isEmpty()) {
				Position<Integer> actual = l.first();
				while (cant>0 && !esta) {
					esta = actual.element().equals(e);
					cant--;
					actual = l.next(actual);
				}
			}
			return esta;
		} catch(EmptyListException | InvalidPositionException | BoundaryViolationException f) {f.printStackTrace(); return esta;}
	}
	
	public static <E> void invertir(PositionList<E> l1) { //Asumo l1 Lista Doblemente enlazada para el calculo del tiempo de ejecucion.
		if (!l1.isEmpty()) {
				PositionList<E> l2 = new ListaDE<E>();
				Iterator<E> it1 = l1.iterator();
				try{
					while(it1.hasNext()) 
						l2.addFirst(it1.next());
					Iterator<E> it2 = l2.iterator();
					Position<E> Aux, elim = l1.first();
					while(it2.hasNext()) {
						Aux = l1.next(elim);
						l1.remove(elim);
						elim = Aux;
						l1.addLast(it2.next());
					}
				} catch(EmptyListException | InvalidPositionException | BoundaryViolationException e){e.printStackTrace();}
		}
	}
	
	public static <E> void invertir_recursivo(PositionList<E> l1) {
		try {
			if(l1!=null && !l1.isEmpty())
				invertirAux(l1,l1.first(),l1.size());
		} catch (EmptyListException e) {
			e.printStackTrace();
		}
	}
	private static <E> void invertirAux(PositionList<E> l1,Position<E> p1, int cant) {
		if (cant>1) {
			try {
				Position<E> p2 = l1.next(p1);
				E aux = l1.remove(p1);
				invertirAux(l1,p2,cant-1);
				l1.addLast(aux);
			} catch(InvalidPositionException | BoundaryViolationException e){e.printStackTrace();}
		}
	}
	
	public <E> void lista_resumen(PositionList<E> l1, PositionList<E> l2, PositionList<Terna<E>> r) {
		Iterator<E> it1 = l1.iterator(); E A1;
		Iterator<E> it2 = l2.iterator(); E A2;
		r = new ListaDE<Terna<E>>();
		while (it1.hasNext()) {
			A1=it1.next();
			if (!pertenece1(r,A1)) 
				agregar(r,A1,1);
			else
				contar(r,A1,1);
			
		}
		while(it2.hasNext()) {
			A2=it2.next();
			if (!pertenece1(r,A2))
				agregar(r,A2,2);
			else
				contar(r,A2,2);
			
		}
		System.out.println(ToStringT(r));
	}
	private <E> boolean pertenece1(PositionList<Terna<E>> r, E e) {
		boolean esta = false;
		Iterator<Terna<E>> it = r.iterator();
		while(it.hasNext() && !esta) {
			esta = (it.next().getE()==e);
		}
		return esta;
	}
	private static <E> void agregar(PositionList<Terna<E>> r, E e, int l) {
		Terna<E> T = new Terna<E>();
		T.setE(e);
		if (l==1)
			T.setA(1);
		else
			T.setB(1);
		r.addLast(T);
	}
	private static <E> void contar(PositionList<Terna<E>> r, E e, int l) {
		Iterator<Terna<E>> it = r.iterator(); boolean conte = false; Terna<E> T;
		while(it.hasNext() && !conte) {
			T = it.next();
			if (T.getE()==e) {
				conte = true;
				if (l==1)
					T.setA(T.getA()+1);
				else
					T.setB(T.getB()+1);
			}
		}
	}
	
	private static class Terna<E>{
		private E e;
		private int a;
		private int b;
		
		private Terna() {
			e = null;
			a = 0;
			b = 0;
		}
		
		
		private E getE() {return e;}
		private int getA() {return a;}
		private int getB() {return b;}
		
		private void setE(E e) {this.e=e;}
		private void setA(int a) {this.a=a;}
		private void setB(int b) {this.b=b;}
	}
	
	public static <E> void eliminar(PositionList<E> l1, PositionList<E> l2) {
		Iterator<E> it2 = l2.iterator();
		E e2;
		Position<E> P;
		while(it2.hasNext()) {
			e2 = it2.next();
			Iterator<Position<E>> it1 = l1.positions().iterator();
			while(it1.hasNext()) {
				P = it1.next();
				if(P.element() != null && P.element().equals(e2)) {
					try {l1.remove(P);} catch (InvalidPositionException e) {e.printStackTrace();}
				}
			}
		}
		invertir_recursivo(l2);
		it2 = l2.iterator();
		while(it2.hasNext()) {
			l1.addLast(it2.next());
		}
		
	}
	
	private static <E> String ToString(PositionList<E> l) {
		String s = "["; 
		Iterator<E> it = l.iterator();
		while(it.hasNext()) {
			s+=it.next();
			if(it.hasNext()) 
				s+=",";
		}
		s +="]";
		return s;
	}

	
	private static <E> String ToStringT(PositionList<Terna<E>> l) {
		String s = "<"; 
		Iterator<Terna<E>> it = l.iterator();
		Terna<E> T;
		while(it.hasNext()) {
			T = it.next();
			s+="("+T.getE()+","+T.getA()+","+T.getB()+")";
			if(it.hasNext()) 
				s+=",";
		}
		s +=">";
		return s;
	}
	public static boolean esPalindroma(PositionList<Character> l1) {
		boolean es = true;
		if(l1!=null && !l1.isEmpty()) {
			try {
				es = esPalAux(l1,l1.first(),l1.last(), l1.size());
			} catch (EmptyListException e) {e.printStackTrace();}
		}
		return es;
	}
	private static boolean esPalAux(PositionList<Character> l1, Position<Character> ini, Position<Character> fin, int size) {
		boolean es = true;
		if(size>0)
			try {
				System.out.println(es+" "+ini+" "+fin);
				es = ini.element()==fin.element() && esPalAux(l1,l1.next(ini),l1.prev(fin),size-2);
			} catch (InvalidPositionException | BoundaryViolationException e) {}
		return es;
	}
	
	public static <E> void main(String a[]) {
		//PositionList<Character> l1 = new ListaDE<Character>();
		/*l1.addLast('n');
		l1.addLast('e');
		l1.addLast('x');
		l1.addLast('q');
		l1.addLast('u');
		l1.addLast('e');
		l1.addLast('n');
		System.out.println(""+esPalindroma(l1));*/
		PositionList<Integer> l1 = new ListaDE<Integer>(); PositionList<Integer> l2 = new ListaDE<Integer>();
		l1.addLast(1);
		l1.addLast(1);
		l1.addLast(4);
		l1.addLast(2);
		l1.addLast(1);
		l1.addLast(1);
		l1.addLast(1);
		l1.addLast(1);
		l2.addLast(1);
		l2.addLast(2);
		l2.addLast(3);
		l2.addLast(4);
		l2.addLast(5);
		//eliminar(l1,l2);
		//PositionList<Terna<Integer>> r = null;
		//lista_resumen(l1,l2,r);
		/*
		System.out.println(ToString(l1));*/
		//System.out.println(ToString(l2));
		//System.out.println(ToString(l1));
		//System.out.println(ToString(l1)+" "+l1.size());
		//System.out.println(ToString(l1));
		//invertir(l1);
		//System.out.println(ToString(l1));
		//System.out.println(""+ToString(intercalar_ordenados_sin_repetidos(l1,l2)));
		//[1,2,4]
	}
}
