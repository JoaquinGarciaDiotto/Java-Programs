package AVL;

public class NodoAVL<E> {
	
	private NodoAVL<E> padre, izq, der;
	private E rotulo;
	private int altura;
	private boolean eliminado;

	public NodoAVL(E r){
		altura = 0;
		eliminado = false;
		rotulo = r;
	}
	public E getElem() {
		return rotulo;
	}

	public NodoAVL<E> getPadre() {
		return padre;
	}

	public NodoAVL<E> getIzq() {
		return izq;
	}

	public NodoAVL<E> getDer() {
		return der;
	}
	
	public int getAltura() {
		return altura;
	}
	
	public boolean eliminado() {
		return eliminado;
	}
	
	public void setPadre(NodoAVL<E> p) {
		padre = p;
	}

	public void setIzq(NodoAVL<E> l) {
		izq = l;
	}

	public void setDer(NodoAVL<E> r) {
		der = r;
	}

	public void setElement(E e) {
		rotulo = e;
	}
	public void setAltura(int h) {
		altura = h;
	}
	public void setEliminado(boolean e) {
		eliminado = e;
	}
}
