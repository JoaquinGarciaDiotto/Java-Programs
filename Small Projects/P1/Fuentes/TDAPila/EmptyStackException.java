package TDAPila;

/**
 * Maneja la excepcion "EmptyStackException".
 * @author Joaquin Garcia Diotto
 * 
 */
public class EmptyStackException extends Exception{
	private static final long serialVersionUID = 1L;

	/**
	 * Crea la excepcion en caso de la pila sea vacia.
	 * @param m Mensaje ingresado el cual sera mostrado en caso de producirse la excepcion.
	 */
	public EmptyStackException(String m) {
		super(m);
	}
}
