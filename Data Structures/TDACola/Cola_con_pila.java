package TDACola;

import TDACola.Queue;
import TDAPila.*;

public class Cola_con_pila<E> implements Queue<E>{
	private Stack<E> elementos;
	
	public Cola_con_pila() {
		elementos = new Pila_Con_Enlaces<E>();
	}
	
	public int size() {
		return elementos.size();
	}
	
	public boolean isEmpty() {
		return elementos.size()==0;
	}
	
	private E getFirst(int n, boolean b){
		E dato = null, aux = null;
		try {
			if (n>1) {
				aux = elementos.pop();
				dato = getFirst(n-1,b);
				elementos.push(aux);
			}
			else 
				if (b)
					dato = elementos.pop();
				else
					dato = elementos.top();
			}catch(EmptyStackException e) {}
		return dato;
	}
	
	public E front() throws EmptyQueueException{;
		E dato = null;
		if (size()==0)
			throw new EmptyQueueException("Vacio");
		else 
			dato = getFirst(size(),false);
		return dato;
	}
	/*private E f(int n) throws EmptyQueueException, EmptyStackException {
		E dato;
		if (n==1) {
			dato = elementos.pop();
			elementos.push(dato);
			return dato;
			}
		else {
			dato = elementos.pop();
			f(n-1);
			elementos.push(dato);
			return dato;
		    }
	}*/
	
	public E dequeue() throws EmptyQueueException{
		E dato = null; 
		if (size()==0)
			throw new EmptyQueueException("Vacio");
		else 
			dato = getFirst(size(),true);
		return dato;
	}
	
	public void enqueue(E e) {
		elementos.push(e);
	}
}
