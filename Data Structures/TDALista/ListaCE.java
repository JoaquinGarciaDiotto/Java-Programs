package TDALista;

public class ListaCE<E>{
	protected Nodo<E> cursor;
	protected int size;
	
	public ListaCE() {
		size = 0;
		cursor = null;
	}
	
	public int size() {
		return size;
	}
	public boolean isEmpty() {
		return size==0;
	}
	public Position<E> getCursor() {
		return cursor;
	}
	public void advance(){ //Advance y add reemplazarian a todos los getters y setters ya que simplemente necesitariamos recorrer lo que sea necesario de la lista para realizar la tarea.
		cursor=cursor.getSiguiente();
	}
	public void add(E e) {
		Nodo<E> n = new Nodo<E>(e);
		if (isEmpty()) {
			n.setSiguiente(n);
			cursor=n;
		}
		else {
			n.setSiguiente(cursor.getSiguiente());
			cursor.setSiguiente(n);
		}
		size++;
	}
	public Position<E> remove() { //Eliminar el nodo que le sigue al cursor
		Nodo<E> elim = cursor.getSiguiente();
		if (elim==cursor)
			cursor=null;
		else {
			cursor.setSiguiente(elim.getSiguiente());
			elim.setSiguiente(null);
		}
		size--;
		return elim;
	}
	private void remove(Position<E> p) {
		while(cursor.getSiguiente() != p) {
			advance();
		}
		if (cursor.getSiguiente() == p)
			remove();
	}
	
	public void borrar(ListaCE<E> l, int n) {
		Position<E> posAux, posPrim = l.getCursor(); int cant;
		while(l.size()>1) {
			cant=n;
			while(cant>0) {
				l.advance();
				posPrim = l.getCursor();
				cant--;
				if(cant==0) { 
					l.advance();
					posAux = l.getCursor();
					l.remove(posPrim);
					posPrim = posAux;
					}
			}
		}
		//l.ToString();
	}
	private static <E> String ToString(ListaCE<E> l) {
		String s = "["; 
		Position<E> p = l.getCursor();
		s +=l.getCursor().element()+",";
		l.advance();
		while(l.getCursor() != p) {
			s+=l.getCursor().element();
			if(l.getCursor() != p) 
				s+=",";
			l.advance();
		}
		s +="]";
		return s;
	}
	public static void main(String a[]) {
		ListaCE<Integer> l = new ListaCE<Integer>();
		l.add(1);
		l.add(2);
		l.add(3);
		l.add(4);
		System.out.println(ToString(l));
		l.borrar(l, 4);
		System.out.println(ToString(l));
	}
	
	@SuppressWarnings("hiding")
	private class Nodo<E> implements Position<E>{
		private E elemento;
		private Nodo<E> siguiente;
			
		private Nodo(E item, Nodo<E> sig) {
			elemento = item;
			siguiente = sig;
		}
		private Nodo(E item) {
			this(item,null);
		}
			
		private void setElemento(E elemento) {
			this.elemento =  elemento;
		}
		private void setSiguiente(Nodo<E> Siguiente) {
			siguiente  = Siguiente;
		}
			
		private E getElemento() {
			return elemento;
		}
		private Nodo<E> getSiguiente() {
			return siguiente;
		}
		public E element() {
			return elemento;
		}

	}
}
