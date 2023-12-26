package TDALista;
import java.util.Iterator;

/**
 * Interface Iterable.
 * Define el metodo a ser utilizado por el tipo de dato Iterable.
 * @author Joaquin Garcia Diotto
 *
 * @param <E> Elemento a ser utilizado en el iterador.
 */
public interface Iterable<E> {
	
	/**
	 * Crea un iterador.
	 * @return Retorna un iterador.
	 */
	public Iterator<E> iterator();
}
