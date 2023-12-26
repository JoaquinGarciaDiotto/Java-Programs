package TDALista;

import java.util.Iterator;

public class ListaDEN<E> implements PositionList<E>{

	protected DNodo<E> cabeza, rabo;
	protected int size;
	
	public ListaDEN() {
		cabeza = rabo = null;
	}
	
	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size==0;
	}

	@Override
	public Position<E> first() throws EmptyListException {
		if(size==0) throw new EmptyListException("first(); Lista vacia");
		return cabeza;
	}

	@Override
	public Position<E> last() throws EmptyListException {
		if(size==0) throw new EmptyListException("first(); Lista vacia");
		return rabo;
	}

	@Override
	public Position<E> next(Position<E> p) throws InvalidPositionException, BoundaryViolationException {
		DNodo<E> n = checkPosition(p);
		if(size==0) throw new InvalidPositionException("next(); Posicion ultima");
		try {
			if(p==last()) throw new BoundaryViolationException("next(); Siguiente del ultimo");
		} catch (EmptyListException e) {e.printStackTrace();}
		return n.getNext();
	}

	@Override
	public Position<E> prev(Position<E> p) throws InvalidPositionException, BoundaryViolationException {
		if (size==0) throw new InvalidPositionException("prev(); Lista vacia");
		DNodo<E> n = checkPosition(p);
		try {
			if (p==first()) throw new BoundaryViolationException("prev(); Posicion primera");
		} catch (EmptyListException e) {e.printStackTrace();}
		return n.getPrev();
	}

	@Override
	public void addFirst(E element) {
		DNodo<E> nuevo;
		if(size==0) {
			nuevo = new DNodo<E>(element,null,null);
			cabeza = rabo = nuevo;
		} else {
			nuevo = new DNodo<E>(element,null,cabeza);
			cabeza.setPrev(nuevo);
			cabeza = nuevo;
		}
		size++;
	}

	@Override
	public void addLast(E element) {
		DNodo<E> nuevo;
		if(size==0) {
			nuevo = new DNodo<E>(element,null,null);
			cabeza = rabo = nuevo;
		} else {
			nuevo = new DNodo<E>(element,rabo,null);
			rabo.setNext(nuevo);
			rabo = nuevo;
		}

		size++;
	}

	@Override
	public void addAfter(Position<E> p, E element) throws InvalidPositionException {
		if(size==0) throw new InvalidPositionException("addAfter(); Lista vacia");
		DNodo<E> n = checkPosition(p);
		try {
			if(p==last())
				addLast(element);
			else {
				DNodo<E> nuevo  = new DNodo<E>(element,n,n.getNext());
				n.setNext(nuevo);
				nuevo.getNext().setPrev(nuevo);
				size++;
			}
		} catch (EmptyListException e) {e.printStackTrace();}
	}

	@Override
	public void addBefore(Position<E> p, E element) throws InvalidPositionException {
		if(size==0) throw new InvalidPositionException("addAfter(); Lista vacia");
		DNodo<E> n = checkPosition(p);
		try {
			if(p==first())
				addFirst(element);
			else {
				DNodo<E> nuevo = new DNodo<E>(element,n.getPrev(),n);
				n.setPrev(nuevo);
				nuevo.getPrev().setNext(nuevo);
				size++;
			}
		} catch (EmptyListException e) {e.printStackTrace();}
	}

	@Override
	public E remove(Position<E> p) throws InvalidPositionException {
		DNodo<E> n = checkPosition(p);
		E elemento = n.getElemento();
		try {
			if(p==first())
				cabeza = n.getNext();
			else
				if(p==last())
					rabo = n.getPrev();
				else
					checkPosition(prev(p)).setNext(n.getNext());
		} catch (EmptyListException | BoundaryViolationException e) {e.printStackTrace();}
		n.setElemento(null);
		n.setNext(null);
		size--;
		return elemento;
	}

	@Override
	public E set(Position<E> p, E element) throws InvalidPositionException {
		DNodo<E> n = checkPosition(p);
		E elemento = n.getElemento();
		n.setElemento(element);
		return elemento;
	}

	@Override
	public Iterator<E> iterator() {
		return new ElementIterator<E>(this);
	}

	@Override
	public Iterable<Position<E>> positions() {
		PositionList<Position<E>> p = new ListaDEN<Position<E>>();
		DNodo<E> actual = cabeza;
		while (actual != null) {
			p.addLast(actual);
			actual = actual.getNext();
		}
		return p;
	}
	
	private DNodo<E> checkPosition(Position<E> p) throws InvalidPositionException{ //O(1)
		try {
			if(p==null) throw new InvalidPositionException("Posicion nula");
			if(p.element()==null) throw new InvalidPositionException("p eliminada previamente");
			return (DNodo<E>) p;
		}
		catch(ClassCastException e) {
			throw new InvalidPositionException("p no es un nodo de lista");
		}
	}
	
}
