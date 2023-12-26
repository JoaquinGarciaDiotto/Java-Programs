package TDAArbol;

/**
 * Modela la excepcion en caso de que la operacion realizada sea invalida.
 * @author Joaquin Garcia Diotto
 * 
 */
public class InvalidOperationException extends Exception{
	private static final long serialVersionUID = 1L;
	
	/**
	 * Inicializa la excepcion indicando el origen del error.
	 * @param m Especifica informacion adicional a cerca de la excepcion.
	 */
	public InvalidOperationException(String m) {
		super(m);
	}
}
