package TDAPila;

/**
 * Interface Stack - Estructura de Datos (DCIC-UNS).
 * Define los datos y operaciones aplicables sobre una pila.
 * @author Estructuras de Datos, DCIC-UNS.
 * @version 1.0 - María Lujan Ganuza (2013-2018)
 * @version 1.0 - Federico Joaquín (2019-2020) 
 * @param <E> Tipo de dato de los elementos a almacenar en la pila.
 */
public interface Stack<E> {

	/**
	 * Consulta la cantidad de elementos de la pila.
	 * @return Cantidad de elementos de la pila.
	 */
	public int size();

	/**
	 * Consulta si la pila está vacía.
	 * @return Verdadero si la pila está vacía, falso en caso contrario.
	 */
	public boolean isEmpty();

	/**
	 * Examina el elemento que se encuentra en el tope de la pila.
	 * @return Elemento que se encuentra en el tope de la pila.
	 * @throws EmptyStackException si la pila está vacía. 
	 */
	public E top()throws EmptyStackException;

	/**
	 * Inserta un elemento en el tope de la pila.
	 * @param element Elemento a insertar.
	 */
	public void push(E element);

	/**
	 * Remueve el elemento que se encuentra en el tope de la pila.
	 * @return Elemento removido.
	 * @throws EmptyStackException si la pila está vacía. 
	 */
	public E pop() throws EmptyStackException;
}