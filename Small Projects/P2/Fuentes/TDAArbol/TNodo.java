package TDAArbol;

import TDALista.ListaDE;
import TDALista.PositionList;

/**
 * Clase que modela Nodos de arbol.
 * @author Joaquin Garcia Diotto
 *
 * @param <E> Tipo de dato utilizado por la clase.
 */
public class TNodo<E> implements Position<E>{
	private E elemento;
	private TNodo<E> padre;
	private PositionList<TNodo<E>> hijos;
	
	/**
	 * Crea un nuevo nodo no vacio.
	 * @param elem Elemento a almacenar en el nodo.
	 * @param p Nodo padre del nodo.
	 */
	public TNodo(E elem, TNodo<E> p) {
		elemento = elem;
		padre = p;
		hijos = new ListaDE<TNodo<E>>();
	}
	/**
	 * Crea un nodo no vacio sin padre.
	 * @param elem Elemento a lamacenar en el nodo.
	 */
	public TNodo(E elem) {
		this(elem,null);
	}
	
	@Override
	public E element() {
		return elemento;
	}
	
	/**
	 * Obtiene el elemento almacenado por el nodo.
	 * @return Retorna el elemento almacenado por el nodo.
	 */
	public E getElemento() {
		return elemento;
	}
	
	/**
	 * Obtiene el padre del Nodo.
	 * @return Retorna el padre del Nodo.
	 */
	public TNodo<E> getPadre(){
		return padre;
	}
	
	/**
	 * Obtiene los hijos del nodo.
	 * @return Retorna una lista con los hijos del nodo.
	 */
	public PositionList<TNodo<E>> getHijos(){
		return hijos;
	}
	
	/**
	 * Cambia el elemento almacenado por el nodo.
	 * @param elem Elemento nuevo a ser almacenado.
	 */
	public void setElemento(E elem) {
		elemento = elem;
	}
	/**
	 * Cambia el padre del nodo.
	 * @param p Nodo el cual sera el padre del nodo actual.
	 */
	public void setPadre(TNodo<E> p) {
		padre = p;
	}
}
