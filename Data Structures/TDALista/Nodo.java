package TDALista;

public class Nodo<E> implements Position<E>{
	private E elemento;
	private Nodo<E> siguiente;
	
	public Nodo(E item, Nodo<E> sig) {
		elemento = item;
		siguiente = sig;
	}
	public Nodo(E item) {
		this(item,null);
	}
	
	public void setElemento(E elemento) {
		this.elemento =  elemento;
	}
	public void setSiguiente(Nodo<E> Siguiente) {
		siguiente  = Siguiente;
	}
	
	public E element() {
		return elemento;
	}
	public Nodo<E> getSiguiente() {
		return siguiente;
	}
	public Nodo<E> clone(){
		Nodo<E> clon;
		clon = (siguiente==null)? new Nodo<E>(elemento,siguiente) : new Nodo<E>(elemento,siguiente.clone());
		return clon;
	}
}
