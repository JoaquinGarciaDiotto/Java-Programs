package A23;

import java.util.Comparator;

public class A23<E extends Comparable<E>> {
	
	private Nodo23<E> raiz;
	private Comparator<E> comp;
	
	public A23() {
		raiz = new Nodo23<E>(null,null);
		comp = new DefaultComparator<E>();
	}
	public A23(Comparator<E> c) {
		raiz = new Nodo23<E>(null,null);
		comp = c;
	}
	
	public Nodo23<E> raiz(){
		return raiz;
	}
	public Nodo23<E> recuperar(E key) {
		return recuperarAux(raiz,key);
	}
	private Nodo23<E> recuperarAux(Nodo23<E> r, E k) {
		Nodo23<E> n = null;
		if(r.getK1()==k || r.getK2()==k)
			n = r;
		else if(r.getIzq()==null && r.getMed()==null && r.getDer()==null)
			n = null;
		else 
			if(r.getK1()!=null && r.getK2()==null) { //Si tiene una clave (ver si necesito agregar mas condiciones)
				if(comp.compare(k,r.getK1())<0)
					n = recuperarAux(r.getIzq(),k);
				else
					n = recuperarAux(r.getDer(),k);
			}
			else if(r.getK1()!=null && r.getK2()!=null) {
				if(comp.compare(k,r.getK1())<0)
					n = recuperarAux(r.getIzq(),k);
				else if(comp.compare(k, r.getK2())<0)
					n = recuperarAux(r.getMed(),k);
				else
					n = recuperarAux(r.getDer(),k);
			}
		return n;
	}
}
