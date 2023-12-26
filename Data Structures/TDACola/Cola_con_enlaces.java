package TDACola;

import TDACola.Queue;

public class Cola_con_enlaces<E> implements Queue<E>{
	
	protected Nodo<E> head, tail;
	protected int size;
	
	public Cola_con_enlaces() {
		head = tail = null;
		size = 0;
	}
	
	public boolean isEmpty() {
		return size == 0;
	}
	
	public int size() {
		return size;
	}
	
	public E front() throws EmptyQueueException{
		if (isEmpty()) 
			throw new EmptyQueueException("Cola Vacia");
		else
			return head.getElemento();
	}
	
	public void enqueue(E item) {
		Nodo<E> nodo =  new Nodo<E>(item);
		if (size == 0)
			head = nodo;
		else
			tail.setSiguiente(nodo);
		tail = nodo;
		size++;
	}
	
	public E dequeue() throws EmptyQueueException{
		if (isEmpty())
			throw new EmptyQueueException("Cola Vacia");
		else {
			E aux = head.getElemento();
			head = head.getSiguiente();
			size--;
			if (size == 0)
				tail = null;
			return aux;
		}
	}
	
	public void invertir() throws EmptyQueueException {
		if (!isEmpty()) {
			E dato = dequeue();
			invertir();
			enqueue(dato);
		}
	}
}