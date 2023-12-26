package TDAPila;

/**
 * Implementa la interfaz "Stack" mediante la estructura de datos "Pila Con Enlaces".
 * @author Joaquin Garcia Diotto
 *
 * @param <E> Tipo del elemento a almacenar en la Pila
 */
public class Pila_Con_Enlaces<E> implements Stack<E>{
	//Atributos
	protected Nodo<E> head;
	protected int size;
	
	//Constructor
	/**
	 * Constructor de la clase "Pila_Con_Enlaces"
	 * la cabeza sera nula y el tamaño 0.
	 */
	public Pila_Con_Enlaces() {
		head = null;
		size = 0;
	}
	
	
	//Getters
	
	@Override
	public int size() {
		return size;
	}
	
	@Override
	public boolean isEmpty() {
		return size == 0;
	}
	
	@Override
	public E top() throws EmptyStackException {
		if (size == 0)
			throw new EmptyStackException("La Pila es vacia");
		else
			return head.getElemento();
	}
	
	
	//Setters
	
	@Override
	public void push(E item) {
		head = new Nodo<E>(item, head);
		size++;
	}
	
	@Override
	public E pop() throws EmptyStackException {
		if (size == 0)
			throw new EmptyStackException("La Pila es vacia");
		else {
			E aux = head.getElemento();
			head = head.getSiguiente();
			size--;
			return aux;
		}
	}
	
	
	
	/**
	 * Clase privada anidada "Nodo" para una mayor seguridad, Nodos simplemente enlazados.
	 * @author Joaquin Garcia Diotto
	 * 
	 * @param <E> Tipo de elemento a almacenar en la Pila
	 */
	@SuppressWarnings("hiding")
	private class Nodo<E> {
		//Atributos
		private E elemento;
		private Nodo<E> siguiente;
		
		//Constructores
		
		/**
		 * Constructor N1 de la clase "Nodo"
		 * @param item Elemento a ser almacenado por el nodo.
		 * @param sig Nodo siguiente al actual.
		 */
		private Nodo(E item, Nodo<E> sig) {
			elemento = item;
			siguiente = sig;
		}
		/**
		 * Constructor N2 de la clase "Nodo", crea un nuevo nodo siendo su siguiente nodo nulo.
		 * @param item Elemento a almacenar en el nodo.
		 */
		private Nodo(E item) {
			this(item,null);
		}
		
		//Metodos
		
		
		//Setters
		
		/**
		 * Cambia el elemento almacenado por el nodo
		 * @param elemento Elemento nuevo a ser almacenado.
		 */
		@SuppressWarnings("unused")
		private void setElemento(E elemento) {
			this.elemento =  elemento;
		}
		
		/**
		 * Cambia el nodo siguiente al actual.
		 * @param Siguiente Nodo que sera el siguiente al actual.
		 */
		@SuppressWarnings("unused")
		private void setSiguiente(Nodo<E> Siguiente) {
			siguiente  = Siguiente;
		}
		
		//Getters
		
		/**
		 * Obtiene el elemento almacenado por el nodo.
		 * @return Retorna el elemento almacenado por el nodo.
		 */
		private E getElemento() {
			return elemento;
		}
		
		/**
		 * Obtiene el nodo siguiente al actual.
		 * @return Retorna el nodo siguiente al actual.
		 */
		private Nodo<E> getSiguiente() {
			return siguiente;
		}
	}

}
