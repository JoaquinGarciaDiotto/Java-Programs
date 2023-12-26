package TDAGrafo;

/**
 * Modela la excepcion cuando el arco utilizado es invalido.
 * @author Joaquin Garcia Diotto
 *
 */
@SuppressWarnings("serial")
public class InvalidEdgeException extends Exception{
	/**
	 * Inicializa la excepcion indicando el origen del error.
	 * @param m Especifica informacion adicional a cerca de la excepcion.
	 */
	public InvalidEdgeException(String m) {
		super(m);
	}
}
