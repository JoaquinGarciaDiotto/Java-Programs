package TDAArbol;

/**
 * Modela la excepcion en caso de que el arbol este vacio.
 * @author Joaquin Garcia Diotto
 *
 */
public class EmptyTreeException extends Exception{
	private static final long serialVersionUID = 1L;
	
	/**
	 * Inicializa la excepcion indicando el origen del error.
	 * @param m Especifica informacion adicional a cerca de la excepcion.
	 */
	public EmptyTreeException(String m) {
		super(m);
	}
}
