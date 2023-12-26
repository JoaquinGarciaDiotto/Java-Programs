package TDAArbol;

/**
 * Modela la excepcion en caso de que la posicion utilizada no sea valida.
 * @author Joaquin Garcia Diotto
 *
 */
public class InvalidPositionException extends Exception{
	private static final long serialVersionUID = 1L;

	/**
	 * Inicializa la excepcion indicando el origen del error.
	 * @param m Especifica informacion adicional a cerca de la excepcion.
	 */
	public InvalidPositionException(String m) {
		super(m);
	}
}
