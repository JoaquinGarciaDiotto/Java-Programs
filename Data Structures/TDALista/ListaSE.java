package TDALista;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ListaSE<E> implements PositionList<E>{
	protected Nodo<E> cabeza;
	
	
	public ListaSE() {
		cabeza = null;
	}
	
	private ListaSE(Position<E> clon) {
		cabeza = (Nodo<E>) clon;
	}
	
	
	public int size() { //O(n)
		Nodo<E> actual = cabeza; int cant = 0;
		while (actual != null) {
			actual = actual.getSiguiente();
			cant++;
		}
		return cant;
	}
	
	public boolean isEmpty() { //O(1)
		return cabeza==null;
	}
	
	public Position<E> first() throws EmptyListException { //O(1)
		if (cabeza==null)
			throw new EmptyListException("first(); Lista vacia");
		return cabeza;
	}
 
	public Position<E> last() throws EmptyListException { //O(n)
		if (cabeza==null)
			throw new EmptyListException("last(); Lista vacia");
		else {
			Nodo<E> p = cabeza;
			while(p.getSiguiente() != null)
				p = p.getSiguiente();
			return p;
		}
	}
 
	public Position<E> next(Position<E> p) throws InvalidPositionException, BoundaryViolationException { //O(1)
		Nodo<E> n = checkPosition(p);
		if (n.getSiguiente()==null) throw new BoundaryViolationException("next(); Siguiente de ultimo");
		return n.getSiguiente();
	}
 
	private Nodo<E> checkPosition(Position<E> p) throws InvalidPositionException{ //O(1)
		try {
			if(p==null) throw new InvalidPositionException("Posicion nula");
			if(p.element()==null) throw new InvalidPositionException("p eliminada previamente");
			return (Nodo<E>) p;
		}
		catch(ClassCastException e) {
			throw new InvalidPositionException("p no es un nodo de lista");
		}
	}
	
	public Position<E> prev(Position<E> p) throws InvalidPositionException, BoundaryViolationException { //O(n)
		checkPosition(p);
		
		try {
			if (p==first()) throw new BoundaryViolationException("prev(); Posicion primera");
			} catch (EmptyListException e) {
			e.printStackTrace();
			}
		Nodo<E> aux = cabeza;
		while(aux.getSiguiente() != null && aux.getSiguiente() != p) {
			aux = aux.getSiguiente();
			if (aux.getSiguiente() == null) throw new InvalidPositionException("Posicion no pertenece a la lista");
		}
		return aux;
	}
 
	public void addFirst(E element) { //O(1)
		cabeza = new Nodo<E>(element,cabeza);
	}
 
	public void addLast(E element) { //O(n)
		if (isEmpty())
			addFirst(element);
		else {
			Nodo<E> p = cabeza;
			while(p.getSiguiente() != null)
				p = p.getSiguiente();
			p.setSiguiente(new Nodo<E>(element,null));
		}
	}
 
	public void addAfter(Position<E> p, E element) throws InvalidPositionException { //O(1)
		Nodo<E> n = checkPosition(p);
		Nodo<E> nuevo = new Nodo<E>(element);
		nuevo.setSiguiente(n.getSiguiente());
		n.setSiguiente(nuevo);
	}
 
	public void addBefore(Position<E> p, E element) throws InvalidPositionException { //O(n)
		checkPosition(p);
		try{
		if (p==first())
			addFirst(element);
		else {
			addAfter(prev(p),element);
			}
		}
		catch (BoundaryViolationException e) {
			e.printStackTrace();
		} 
		catch (EmptyListException e) {
			e.printStackTrace();
		}
		
	}
 
	public E remove(Position<E> p) throws InvalidPositionException { //O(n)
		
		Nodo<E> n = checkPosition(p);
		E aux = p.element();
		try {
			if(p==first())
				cabeza = n.getSiguiente();
			else
				checkPosition(prev(p)).setSiguiente(n.getSiguiente());
		} catch (EmptyListException | BoundaryViolationException e) {
			e.printStackTrace();
		}
		n.setElemento(null);
		n.setSiguiente(null);
		return aux;
		
	}
 
	public E set(Position<E> p, E element) throws InvalidPositionException { //O(1)
		Nodo<E> n = checkPosition(p);
		E e = p.element();
		n.setElemento(element);
		return e;
	}
 
	public Iterator<E> iterator(){ //O(1)
		return new ElementIterator<E>(this);
	}
 
	public Iterable<Position<E>> positions() { //O(n)
		PositionList<Position<E>> l = new ListaDE<Position<E>>();
		if(cabeza!=null) {
			Nodo<E> actual = cabeza;
			while(actual.getSiguiente()!=null) {
				l.addLast(actual);
				actual = actual.getSiguiente();
			}
		}
		return l;
	}
	
	 public static<E>void eliminar1(PositionList<E>l, E e) throws EmptyListException, InvalidPositionException, BoundaryViolationException{
		 Position<E>p;
		 p = l.first ();
		 while(p != l.last()){
			 if (p.element().equals(e))
				 l.remove(p);
			 p = l.next(p);
		}
	}
	 public static<E>void eliminar2(PositionList<E>l, E e) throws EmptyListException, InvalidPositionException, BoundaryViolationException{
		 Position<E>p, a;
		 p = l.first();
		 while(p != l.last()){
			 if (p.element().equals(e)){
				 a = p;
				 p = l.next(p);
				 l.remove(a);
				 }
			 a = p;
			 p = l.next(p);
		 }
	}
	public static<E> void eliminar(PositionList<E> l, E e) throws EmptyListException, InvalidPositionException, BoundaryViolationException {
		Position<E> p, a;
		p=l.first();
		while(p != l.last()) {
			if (p.equals(e)) {
				a=p;
				p=l.next(p);
				l.remove(a);
			}
		}
	}
	public PositionList<E> clone() {
		return new ListaSE<E>(cabeza.clone());
	}
	public PositionList<E> clone2(){
		PositionList<E> clon = new ListaSE<E>();
		Nodo<E> actual = cabeza;
		Position<E> actualClon;
		clon.addFirst(actual.element());
		try {
			actualClon = clon.first();
			while(actual.getSiguiente()!=null) {
				actual = actual.getSiguiente();
				clon.addAfter(actualClon, actual.element());
				actualClon = clon.next(actualClon);
			}
		}catch(InvalidPositionException | EmptyListException | BoundaryViolationException e) {e.printStackTrace();}
		return clon;
	}
	public void eliminar_consecutivos(E e1, E e2) { //O(n^2)
		Nodo<E> actual = cabeza;
		Nodo<E> ant = null;
		while (actual != null) {
			if (actual.element().equals(e1)) {
				ant = actual;
				if (actual.getSiguiente() != null && actual.getSiguiente().element().equals(e2)) {
					actual = actual.getSiguiente();
					try {
						remove(ant);
						remove(actual);
					} catch (InvalidPositionException e) { 
						e.printStackTrace();
					}
				}
			}
			actual = actual.getSiguiente();
		}
	}
	public void addFirstConditional(E e, E x) {
		if(cabeza!=null && x!=null) 
			if(x.equals(cabeza.element()))
				cabeza = new Nodo<E>(e,cabeza);
	}
}
