package TDAArbolBinario;

public class BTNodo<E> implements BTPosition<E>{

	protected E elemento;
	protected BTPosition<E> padre, hi, hd;
	
	public BTNodo(E e, BTPosition<E> p) {
		elemento = e;
		padre = p;
	}
	
	@Override
	public E element() {
		return elemento;
	}

	@Override
	public BTPosition<E> getParent() {
		return padre;
	}

	@Override
	public BTPosition<E> getLeft() {
		return hi;
	}

	@Override
	public BTPosition<E> getRight() {
		return hd;
	}

	@Override
	public void setParent(BTPosition<E> p) {
		padre = p;
	}

	@Override
	public void setLeft(BTPosition<E> l) {
		hi = l;
	}

	@Override
	public void setRight(BTPosition<E> r) {
		hd = r;
	}

	@Override
	public void setElement(E e) {
		elemento = e;
	}
	public BTPosition<E> clone() {
		return cloneAux(this);
	}
	private BTPosition<E> cloneAux(BTPosition<E> n){
		BTPosition<E> nodo = n;
		if(nodo!=null && nodo.element()!=null) {
			nodo = new BTNodo<E>(n.element(),n.getParent());
			nodo.setLeft(cloneAux(n.getLeft()));
			nodo.setRight(cloneAux(n.getRight()));
		}
		return nodo;
	}
}
