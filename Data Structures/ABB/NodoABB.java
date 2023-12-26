package ABB;

public class NodoABB<E extends Comparable<E>>{

	protected E elemento;
	protected NodoABB<E> padre, hi, hd;
	
	public NodoABB(E e, NodoABB<E> p) {
		elemento = e;
		padre = p;
	}
	
	public E getElem() {
		return elemento;
	}

	public NodoABB<E> getPadre() {
		return padre;
	}

	public NodoABB<E> getIzq() {
		return hi;
	}

	public NodoABB<E> getDer() {
		return hd;
	}

	public void setPadre(NodoABB<E> p) {
		padre = p;
	}

	public void setIzq(NodoABB<E> l) {
		hi = l;
	}

	public void setDer(NodoABB<E> r) {
		hd = r;
	}

	public void setElement(E e) {
		elemento = e;
	}
}
