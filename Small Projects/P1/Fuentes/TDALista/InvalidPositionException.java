package TDALista;

/**
 * Clase InvalidPositionException.
 * Maneja la excepcion "InvalidPositionException".
 * @author Joaquin Garcia Diotto
 *
 */
public class InvalidPositionException extends Exception{
	private static final long serialVersionUID = 1L;

	/**
	 * Crea la excepcion en caso de que la posicion utilizada no sea valida.
	 * @param m Mensaje ingresado el cual sera mostrado en caso de producirse la excepcion.
	 */
	public InvalidPositionException(String m) {
		super(m);
	}
}
