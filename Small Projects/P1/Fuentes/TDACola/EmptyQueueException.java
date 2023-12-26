package TDACola;

/**
 * Clase EmptyQueueException.
 * Maneja la excepción "EmptyQueueException".
 * @author Joaquin Garcia Diotto
 *
 */
public class EmptyQueueException extends Exception{

	private static final long serialVersionUID = 1L;

	
	/**
	 * Crea la excepcion en caso de la cola sea vacia.
	 * @param m Mensaje ingresado el cual sera mostrado en caso de producirse la excepcion.
	 */
	public EmptyQueueException(String m) {
		super(m);
	}
}
