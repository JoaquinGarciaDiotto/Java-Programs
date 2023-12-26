package TDAArbolBinario;

import java.util.Iterator;
import TDALista.*;

public class ArbolBinarioEnlazado<E> implements BinaryTree<E>{
	protected BTPosition<E> raiz;
	protected int size;
	
	public ArbolBinarioEnlazado() {
		size = 0;
		raiz = null;
	}
	private ArbolBinarioEnlazado(BTPosition<E> r) {
		size = 1;
		raiz = r;
	}
	
	@Override
	public int size() { //O(1)
		return size;
	}

	@Override
	public boolean isEmpty() { //O(1)
		return size==0;
	}

	@Override
	public Iterator<E> iterator() { //O(n)
		PositionList<E> lista = new ListaDE<E>();
		for(Position<E> p : positions())
			lista.addLast(p.element());
		return lista.iterator();
	}

	@Override
	public Iterable<Position<E>> positions() { //O(n)
		PositionList<Position<E>> lista = new ListaDE<Position<E>>();
		if(size>0)
			preOrden(raiz,lista);
		return lista;
	}
	private void preOrden(Position<E> v, PositionList<Position<E>> lista) {
		lista.addLast(v);
		try {
			if(hasLeft(v)) preOrden(left(v),lista);
			if(hasRight(v)) preOrden(right(v),lista);
		} catch (InvalidPositionException | BoundaryViolationException e) {e.printStackTrace();}
		
	}

	@Override
	public E replace(Position<E> v, E e) throws InvalidPositionException { //O(1)
		E elemento = null;
		BTNodo<E> p = checkPosition(v);
		elemento = v.element();
		p.setElement(e);
		return elemento;
	}

	@Override
	public Position<E> root() throws EmptyTreeException { //O(1)
		if(size==0) throw new EmptyTreeException("Arbol vacio");
		return raiz;
	}

	@Override
	public Position<E> parent(Position<E> v) throws InvalidPositionException, BoundaryViolationException { //O(1)
		BTNodo<E> n = checkPosition(v);
		if(n==raiz) throw new BoundaryViolationException("La posicion es la raiz");
		return n.getParent();
	}

	@Override
	public Iterable<Position<E>> children(Position<E> v) throws InvalidPositionException { //O(1)
		BTNodo<E> n = checkPosition(v);
		PositionList<Position<E>> lista = new ListaDE<Position<E>>();
		if(hasLeft(n))	
			lista.addLast(n.getLeft());
		if(hasRight(n))	
			lista.addLast(n.getRight());
		return lista;
	}

	@Override
	public boolean isInternal(Position<E> v) throws InvalidPositionException { //O(1)
		BTNodo<E> n = checkPosition(v);
		return hasLeft(n) || hasRight(n);
	}

	@Override
	public boolean isExternal(Position<E> v) throws InvalidPositionException { //O(1)
		BTNodo<E> n = checkPosition(v);
		return !hasLeft(n) && !hasRight(n);
	}

	@Override
	public boolean isRoot(Position<E> v) throws InvalidPositionException { //O(1)
		BTNodo<E> n = checkPosition(v);
		return n==raiz;
	}

	@Override
	public Position<E> left(Position<E> v) throws InvalidPositionException, BoundaryViolationException { //O(1)
		BTNodo<E> n = checkPosition(v);
		if(n.getLeft() == null) throw new BoundaryViolationException("left(); no tiene hijo izquierdo");
		return n.getLeft();
	}

	@Override
	public Position<E> right(Position<E> v) throws InvalidPositionException, BoundaryViolationException { //O(1)
		BTNodo<E> n = checkPosition(v);
		if(n.getRight() == null) throw new BoundaryViolationException("right(); no tiene hijo derecho");
		return n.getRight();
	}

	@Override
	public boolean hasLeft(Position<E> v) throws InvalidPositionException { //O(1)
		BTNodo<E> n = checkPosition(v);
		return n.getLeft() != null;
	}

	@Override
	public boolean hasRight(Position<E> v) throws InvalidPositionException { //O(1)
		BTNodo<E> n = checkPosition(v);
		return n.getRight() !=  null;
	}

	@Override
	public Position<E> createRoot(E r) throws InvalidOperationException { //O(1)
		if (!isEmpty()) throw new InvalidOperationException("El Arbol no esta vacio");
		raiz = new BTNodo<E>(r,null);
		size++;
		return raiz;
	}

	@Override
	public Position<E> addLeft(Position<E> v, E r) throws InvalidOperationException, InvalidPositionException { //O(1)
		if(size==0) throw new InvalidPositionException("addLeft(); Arbol vacio");
		BTNodo<E> n = checkPosition(v);
		if(hasLeft(n)) throw new InvalidOperationException("addLeft(); el nodo ya tiene un hijo izquierdo");
		BTNodo<E> hijo = new BTNodo<E>(r,n);
		n.setLeft(hijo);
		size++;
		return hijo;
	}

	@Override
	public Position<E> addRight(Position<E> v, E r) throws InvalidOperationException, InvalidPositionException { //O(1)
		if(size==0) throw new InvalidPositionException("addRight(); Arbol vacio");
		BTNodo<E> n = checkPosition(v);
		if(hasRight(n)) throw new InvalidOperationException("addRight(); el nodo ya tiene un hijo derecho");
		BTNodo<E> hijo = new BTNodo<E>(r,n);
		n.setRight(hijo);
		size++;
		return hijo;
	}

	@Override
	public E remove(Position<E> v) throws InvalidOperationException, InvalidPositionException { //O(1)
		if(size==0) throw new InvalidPositionException("remove(); Arbol vacio");
		BTPosition<E> nodo = checkPosition(v);
		if(hasLeft(nodo) && hasRight(nodo)) throw new InvalidOperationException("remove(); el nodo tiene dos hijos.");
		BTPosition<E> hijo = (hasLeft(nodo))? nodo.getLeft() : nodo.getRight();
		if(hijo != null)
			hijo.setParent(nodo.getParent());
		if(nodo==raiz)
			raiz = hijo;
		else {
			BTPosition<E> p = nodo.getParent();
			if(nodo==p.getLeft())
				p.setLeft(hijo);
			else
				p.setRight(hijo);
		}
		size--;
		E temp = nodo.element();
		nodo.setElement(null);
		nodo.setLeft(null);
		nodo.setRight(null);
		return temp;
	}

	@Override
	public void attach(Position<E> v, BinaryTree<E> t1, BinaryTree<E> t2) throws InvalidPositionException {
		BTPosition<E> raizLocal = checkPosition(v), hiRaizLocal, hdRaizLocal;
		Position<E> raizT1, raizT2;
		
		if(raizLocal.getLeft() != null || raizLocal.getRight() != null) throw new InvalidPositionException("La posicion no corresponde a un nodo hoja");
		try {
			if(!t1.isEmpty()) {
				raizT1 = t1.root();
				hiRaizLocal = new BTNodo<E>(raizT1.element(),raizLocal);
				raizLocal.setLeft(hiRaizLocal);
				clonar(hiRaizLocal,raizT1,t1);
			}
			if(!t2.isEmpty()) {
				raizT2 = t2.root();
				hdRaizLocal = new BTNodo<E>(raizT2.element(),raizLocal);
				raizLocal.setRight(hdRaizLocal);
				clonar(hdRaizLocal,raizT2,t2);
			}
			size += t1.size() + t2.size();
		}catch(EmptyTreeException e) {
			raizLocal.setLeft(null);
			raizLocal.setRight(null);
			e.printStackTrace();
		}
		
	}
	protected void clonar(BTPosition<E> padreL, Position<E> padreT, BinaryTree<E> t) {
		BTPosition<E> hiPadreL, hdPadreL;
		Position<E> hiPadreT, hdPadreT;
		try {
			if(t.hasLeft(padreT)) {
				hiPadreT = t.left(padreT);
				hiPadreL = new BTNodo<E>(hiPadreT.element(),padreL);
				padreL.setLeft(hiPadreL);
				clonar(hiPadreL,hiPadreT,t);
			}
			if(t.hasRight(padreT)) {
				hdPadreT = t.right(padreT);
				hdPadreL = new BTNodo<E>(hdPadreT.element(),padreL);
				padreL.setRight(hdPadreL);
				clonar(hdPadreL,hdPadreT,t);
			}
		}catch(InvalidPositionException | BoundaryViolationException e) {
			padreL.setLeft(null);
			padreL.setRight(null);
		}
	}
	private BTNodo<E> checkPosition(Position<E> p) throws InvalidPositionException {
		try {
			if(p==null) throw new InvalidPositionException("Posicion nula");
			if(p.element()==null) throw new InvalidPositionException("p eliminada previamente");
			return (BTNodo<E>) p;
		}
		catch(ClassCastException e) {
			throw new InvalidPositionException("p no es un nodo de lista");
		}
	}
	
	public void espejar() {
		espejarAux(raiz);
	}
	private BTPosition<E> espejarAux(BTPosition<E> n) {
		if(n!=null && n.element()!=null) {
			BTPosition<E> izq = espejarAux(n.getLeft());
			BTPosition<E> der = espejarAux(n.getRight());
			n.setLeft(der);
			n.setRight(izq);
		}
		return n;
	}
	
	public void padre_de_hojas() {
		padreHojasAux(raiz,0);
	}
	private void padreHojasAux(BTPosition<E> n, int nivel) {
		try {
			if(((hasLeft(n) && !hasRight(n)) && isExternal(n.getLeft())) || ((!hasLeft(n) && hasRight(n) && isExternal(n.getRight()))))
				System.out.println("elemento: "+n.element()+", nivel: "+nivel);
			else 
				if(n!=null && n.element()!=null) {
					padreHojasAux(n.getLeft(),nivel+1);
					padreHojasAux(n.getRight(),nivel+1);
				}
		} catch (InvalidPositionException e) {e.printStackTrace();}
	}

	
	public BinaryTree<E> clone() {
		BinaryTree<E> t2 = new ArbolBinarioEnlazado<E>();
		if(size>0) {
			t2 = new ArbolBinarioEnlazado<E>(((BTNodo<E>) raiz).clone());
		}
		return t2;
	}
}
