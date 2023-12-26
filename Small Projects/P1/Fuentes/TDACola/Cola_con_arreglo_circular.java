package TDACola;

import TDACola.Queue;

/**
 * Clase Cola_con_arreglo_circular.
 * Implementa la interfaz "Queue" y la modela mediante la estructura de datos "Cola con arreglo circular".
 * @author Joaquin Garcia Diotto
 *
 * @param <E> Tipo de dato generico a almacenar en la Cola.
 */

public class Cola_con_arreglo_circular<E> implements Queue<E>{
	
	//Atributos
	protected int f;
	protected int r;
	protected E[] datos;
	
	/**
	 * Constructor N1 de la clase "Con_con_arreglo_circular". Crea una nueva cola de tama�o 10.
	 */
	@SuppressWarnings("unchecked")
	public Cola_con_arreglo_circular() {
		datos = (E[]) new Object[10];
		f = 0;
		r = 0;
	}
	
	/**
	 * Constructor N2 de la clase "Cola_con_arreglo_circular". Crea una nueva cola de tama�o n.
	 * @param n Tama�o de la cola deseado.
	 */
	public Cola_con_arreglo_circular(int n) {
		datos = (E[]) new Object[n];
		f = 0;
		r = 0;
	}
	
	//Metodos
	
	
	//Getters
	
	@Override
	public boolean isEmpty() {
		return f==r;
	}
	
	@Override
	public E front() throws EmptyQueueException{
		if (isEmpty())
			throw new EmptyQueueException("Cola vacia");
		else
			return datos[f];
	}
	
	@Override
	public int size() {
		return (datos.length-f+r)%datos.length;
	}
	
	
	//Setters
	
	@Override
	public void enqueue(E e){
		if (size() == datos.length-1)
			aumentarSize();
		datos[r] = e;
		r = (r+1)%datos.length;
	}
	
	@Override
	public E dequeue() throws EmptyQueueException{
		if (size() == 0)
			throw new EmptyQueueException("Cola vacia");
		else {
			E aux = datos[f];
			datos[f] = null;
			f = (f+1)%datos.length;
			return aux;
		}
	}
	
	/**
	 * Metodo privado para aumentar el tama�o de la Cola en 10 espacios.
	 */
	private void aumentarSize() {
		@SuppressWarnings("unchecked")
		E[] aux = (E[]) new Object[datos.length+10];
		for (int i=0; i<datos.length-1; i++) {
			aux[i] = datos[(f+i)%datos.length];
		}
		f=0;
		r=datos.length-1;
		datos=aux;
	}
}
