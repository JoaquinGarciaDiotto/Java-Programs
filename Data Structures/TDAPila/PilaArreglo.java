package TDAPila;

public class PilaArreglo<E> implements Stack<E>{
	
	protected int tope; 
	protected E[] datos;
	
	@SuppressWarnings("unchecked")
	public PilaArreglo() {
		datos = (E[]) new Object[50];
		tope = 0;
	}
	public void push(E item){
		if (tope == datos.length) 
			incrementarTope();
		datos[tope++] = item;
	}
	public E pop() throws EmptyStackException{
		if (tope == 0)
			throw new EmptyStackException("El arreglo esta vacio");
		else {
			E aux = datos[tope-1];
			datos[(tope--)-1] = null;
			return aux;
		}
	}
	public int size() {
		return tope;
	}
	public E top() throws EmptyStackException{
		if (tope == 0)
			throw new EmptyStackException("El arreglo esta vacio");
		else
			return datos[tope-1];
	}
	public boolean isEmpty() {
		return tope == 0;
	}
	private void incrementarTope() {
		@SuppressWarnings("unchecked")
		E[] p = (E[]) new Object[tope+50];
		for(int i = tope-1; i>=0; i--)
			p[i] = datos[i];
		datos = p;
	}
	
	public void invertir1() { //O(n)
		for (int i = 0, t = tope-1; i<tope/2; i++, t--) {
			E aux = datos[i];
			datos[i] = datos[t];
			datos[t] = aux;
		}
	}
	public void invertir2(Stack<E> p) {
		Stack<E> aux = new Pila_Con_Enlaces<E>();
		while(!p.isEmpty())
			try {
				aux.push(p.pop());
			} catch (EmptyStackException e) {e.printStackTrace();}
		p = aux;
	}
}
