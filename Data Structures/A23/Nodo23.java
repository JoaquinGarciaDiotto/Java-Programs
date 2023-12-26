package A23;

public class Nodo23<E> {
	
	private Nodo23<E> padre, izq, med, der;
	private E key1, key2;
	
	
	public Nodo23(E k1, E k2){
		key1 = k1;
		key2 = k2;
	}
	
	public void setPadre(Nodo23<E> p) {
		padre = p;
	}
	public void setIzq(Nodo23<E> i) {
		izq = i;
	}
	public void setMed(Nodo23<E> m) {
		med = m;
	}
	public void setDer(Nodo23<E> d) {
		der = d;
	}
	
	public E getK1() {
		return key1;
	}
	public E getK2() {
		return key2;
	}
	public Nodo23<E> getPadre(){
		return padre;
	}
	public Nodo23<E> getIzq(){
		return izq;
	}
	public Nodo23<E> getMed(){
		return med;
	}
	public Nodo23<E> getDer(){
		return der;
	}
}
