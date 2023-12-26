package TDAGrafo;

/**
 * Modela la excepcion cuando el vertice utilizado es invalido.
 * @author Joaquin Garcia Diotto
 *
 */
@SuppressWarnings("serial")
public class InvalidVertexException extends Exception{
	/**
	 * Inicializa la excepcion indicando el origen del error.
	 * @param m Especifica informacion adicional a cerca de la excepcion.
	 */
	public InvalidVertexException(String m) {
		super(m);
	}
}
