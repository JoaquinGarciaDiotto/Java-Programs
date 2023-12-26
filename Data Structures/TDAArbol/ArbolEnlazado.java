package TDAArbol;

import java.util.Iterator;
import TDALista.*;

/**
 * Modela la interfaz Tree mediante la estructura de datos Arbol Enlazado.
 * @author Joaquin Garcia Diotto
 *
 * @param <E> Tipo de dato utilizado por la clase.
 */
public class ArbolEnlazado<E> implements Tree<E>{
	protected TNodo<E> raiz;
	protected int size;
	
	/**
	 * Crea un arbol enlazado vacio.
	 */
	public ArbolEnlazado() {
		size=0;
		raiz = new TNodo<E>(null);
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
	public Iterator<E> iterator() {
		PositionList<E> lista = new ListaDE<E>();
		if(size!=0)
			pre(raiz,lista);
		return lista.iterator();
	}
	
	/**
	 * Realiza un recorrido del arbol en forma preOrden.
	 * @param nodo Nodo que recorre todo el arbol, comenzando desde la raiz.
	 * @param lista Lista de elementos en la cual se almacenaran los elementos de los nodos del arbol.
	 */
	private void pre(TNodo<E> nodo, PositionList<E> lista) {
		lista.addLast(nodo.element());
		for(TNodo<E> hijos: nodo.getHijos())
			pre(hijos,lista);
	} 

	@Override
	public Iterable<Position<E>> positions() {
		PositionList<Position<E>> lista = new ListaDE<Position<E>>();
		if(!isEmpty())
			preOrden(raiz,lista);
		return lista;
	}
	
	/**
	 * Realiza un recorrido del arbol en forma preOrden.
	 * @param nodo Nodo que recorre todo el arbol, comenzando desde la raiz.
	 * @param lista Lista de posiciones en la cual se almacenaran los nodos del arbol.
	 */
	private void preOrden(TNodo<E> nodo, PositionList<Position<E>> lista) {
		lista.addLast(nodo);
		for(TNodo<E> hijos: nodo.getHijos())
			preOrden(hijos,lista);
	} 
	
	@Override
	public E replace(Position<E> v, E e) throws InvalidPositionException {
		E elemento = null;
		TNodo<E> p = checkPosition(v);
		elemento = v.element();
		p.setElemento(e);
		return elemento;
	}

	@Override
	public Position<E> root() throws EmptyTreeException {
		if(size==0) throw new EmptyTreeException("Arbol vacio");
		return raiz;
	}

	@Override
	public Position<E> parent(Position<E> v) throws InvalidPositionException, BoundaryViolationException {
		TNodo<E> n = checkPosition(v);
		if(n==raiz) throw new BoundaryViolationException("La posicion es la raiz");
		return n.getPadre();
	}

	@Override
	public Iterable<Position<E>> children(Position<E> v) throws InvalidPositionException { //O(grado de el arbol)
		TNodo<E> n = checkPosition(v);
		PositionList<Position<E>> lista = new ListaDE<Position<E>>();
		for(TNodo<E> p : n.getHijos())
			lista.addLast(p);
		return lista;
	}

	@Override
	public boolean isInternal(Position<E> v) throws InvalidPositionException {
		TNodo<E> nodo = checkPosition(v);
		return !nodo.getHijos().isEmpty();
	}

	@Override
	public boolean isExternal(Position<E> v) throws InvalidPositionException {
		TNodo<E> nodo = checkPosition(v);
		return nodo.getHijos().isEmpty();
	}

	@Override
	public boolean isRoot(Position<E> v) throws InvalidPositionException {
		TNodo<E> nodo = checkPosition(v);
		return nodo == raiz;
	}

	@Override
	public void createRoot(E e) throws InvalidOperationException {
		if (!isEmpty()) throw new InvalidOperationException("El Arbol no esta vacio");
		raiz.setElemento(e);
		size++;
	}

	@Override
	public Position<E> addFirstChild(Position<E> p, E e) throws InvalidPositionException {
		if(size==0) throw new InvalidPositionException("Arbol vacio");
		TNodo<E> nodo = checkPosition(p);
		TNodo<E> nuevo = new TNodo<E>(e,nodo);
		nodo.getHijos().addFirst(nuevo);
		size++;
		return nuevo;
	}

	@Override
	public Position<E> addLastChild(Position<E> p, E e) throws InvalidPositionException {
		if(size==0) throw new InvalidPositionException("Arbol vacio");
		TNodo<E> nodo = checkPosition(p);
		TNodo<E> nuevo = new TNodo<E>(e,nodo);
		nodo.getHijos().addLast(nuevo);
		size++;
		return nuevo;
	}

	@Override
	public Position<E> addBefore(Position<E> p, Position<E> rb, E e) throws InvalidPositionException {
		if(size==0) throw new InvalidPositionException("Arbol vacio");
		TNodo<E> nodo = checkPosition(p);
		TNodo<E> HermanoDerecho = checkPosition(rb);
		if(HermanoDerecho.getPadre() != nodo) throw new InvalidPositionException("El HermanoDerecho no tiene a 'rb' como padre");
		TNodo<E> nuevo = new TNodo<E>(e,nodo);
		PositionList<TNodo<E>> hijos = nodo.getHijos();
		boolean encontre = false;
		try {
			TDALista.Position<TNodo<E>> pp = hijos.first();
			while(pp!=null && !encontre) {
				if(HermanoDerecho == pp.element())
					encontre = true;
				else
					pp = (pp != hijos.last()) ? hijos.next(pp) : null;
			}
			if(!encontre) throw new InvalidPositionException("p no es padre de HermanoDerecho");
			hijos.addBefore(pp, nuevo);
			size++;
		}catch(EmptyListException | TDALista.InvalidPositionException | TDALista.BoundaryViolationException f) {}
		return nuevo;
	}

	@Override
	public Position<E> addAfter(Position<E> p, Position<E> lb, E e) throws InvalidPositionException {
		if(size==0) throw new InvalidPositionException("Arbol vacio");
		TNodo<E> nodo = checkPosition(p);
		TNodo<E> HermanoIzquierdo = checkPosition(lb);
		if(HermanoIzquierdo.getPadre() != nodo) throw new InvalidPositionException("El HermanoIzquierdo no tiene a 'lb' como padre");
		TNodo<E> nuevo = new TNodo<E>(e,nodo);
		PositionList<TNodo<E>> hijos = nodo.getHijos();
		boolean encontre = false;
		try {
			TDALista.Position<TNodo<E>> pp = hijos.first();
			while(pp!=null && !encontre) {
				if(HermanoIzquierdo == pp.element())
					encontre = true;
				else
					pp = (pp != hijos.last()) ? hijos.next(pp) : null;
			}
			if(!encontre) throw new InvalidPositionException("p no es padre de HermanoDerecho");
			hijos.addAfter(pp, nuevo);
			size++;
			
		}catch(EmptyListException | TDALista.InvalidPositionException | TDALista.BoundaryViolationException e1) {}
		return nuevo;
	}

	@Override
	public void removeExternalNode(Position<E> p) throws InvalidPositionException {
		TNodo<E> nodo = checkPosition(p);
		if(!isExternal(nodo)) throw new InvalidPositionException("No es un nodo externo");
		removeNode(nodo);
	}

	@Override
	public void removeInternalNode(Position<E> p) throws InvalidPositionException {
		TNodo<E> nodo = checkPosition(p);
		if(!isInternal(nodo)) throw new InvalidPositionException("No es un nodo interno");
		removeNode(nodo);
	}

	@Override
	public void removeNode(Position<E> p) throws InvalidPositionException {
		if(size==0) throw new InvalidPositionException("Arbol vacio");
		TNodo<E> nodoEliminar = checkPosition(p);
		TNodo<E> padre = nodoEliminar.getPadre();
		TNodo<E> hijo;
		PositionList<TNodo<E>> hijos = nodoEliminar.getHijos();
		PositionList<TNodo<E>> hijosPadre;
		TDALista.Position<TNodo<E>> posHermanos; 
		try {
			if(nodoEliminar == raiz) {
				if(hijos.size()==0)
					raiz = null;
				else {
					if(hijos.size()==1) {
						hijo = hijos.remove(hijos.first());
						hijo.setPadre(null);
						raiz = hijo;
					}
					else throw new InvalidPositionException("No se puede eliminar raiz con mas de un hijo");
				}
			}
			else {
				hijosPadre = padre.getHijos();
				posHermanos = (hijosPadre.isEmpty())? null : hijosPadre.first();
				while(posHermanos != null && posHermanos.element()!=nodoEliminar) {
					posHermanos = (hijosPadre.last()==posHermanos)? null : hijosPadre.next(posHermanos);
				}
				if(posHermanos == null) throw new InvalidPositionException("p no esta en la lista de hijos de su padre, no se lo elimino");
				while(!hijos.isEmpty()) {
					hijo = hijos.remove(hijos.first());
					hijo.setPadre(padre);
					hijosPadre.addBefore(posHermanos,hijo);
				}
				hijosPadre.remove(posHermanos);
			}
			nodoEliminar.setPadre(null);
			nodoEliminar.setElemento(null);
			size--;
		}catch(EmptyListException | TDALista.InvalidPositionException | TDALista.BoundaryViolationException e ) {}
	}
	
	/**
	 * Chequea que la posicion ingresada y su elemento no sean nulos.
	 * @param p Posicion ingresada a chequear.
	 * @return Retorna un un objeto de tipo "DNodo", el cual es la posicion ingresada casteada a DNodo.
	 * @throws InvalidPositionException si la posicion "p" no es un nodo de la lista.
	 */
	private TNodo<E> checkPosition(Position<E> p) throws InvalidPositionException {
		try {
			if(p==null) throw new InvalidPositionException("Posicion nula");
			if(p.element()==null) throw new InvalidPositionException("p eliminada previamente");
			return (TNodo<E>) p;
		}
		catch(ClassCastException e) {
			throw new InvalidPositionException("p no es un nodo de lista");
		}
	}
}
