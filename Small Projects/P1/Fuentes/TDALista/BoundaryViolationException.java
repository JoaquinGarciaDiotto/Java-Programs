package TDALista;

/**
 * Clase BoundaryViolationException.
 * Maneja la excepcion "BoundaryViolationException".
 * @author Joaquin Garcia Diotto
 *
 */
public class BoundaryViolationException extends Exception{
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * Crea la excepcion en caso de que se violen los limites de la lista.
	 * @param m Mensaje ingresado el cual sera mostrado en caso de producirse la excepcion.
	 */
	public BoundaryViolationException(String m) {
		super(m);
	}
}
