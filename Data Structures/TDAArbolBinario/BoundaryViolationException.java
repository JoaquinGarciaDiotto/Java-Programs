package TDAArbolBinario;

/**
 * Modela la excepcion cuando se intentan sobrepasar los limites del arbol.
 * @author Joaquin Garcia Diotto
 *
 */
public class BoundaryViolationException extends Exception{
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * Inicializa la excepcion indicando el origen del error.
	 * @param m Especifica informacion adicional a cerca de la excepcion.
	 */
	public BoundaryViolationException(String m) {
		super(m);
	}
}
