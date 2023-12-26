package TDAMapeo;

import AVL.AVL;
import AVL.DefaultComparator;
import AVL.NodoAVL;
import TDACola.Cola_con_enlaces;
import TDACola.EmptyQueueException;
import TDACola.Queue;
import TDALista.ListaDE;
import TDALista.PositionList;
import TDAMapeo.Map;

public class MapeoConAVL<K extends Comparable<K>,V> implements Map<K,V> {
	public AVL<EntradaComparable<K,V>> avl;
	private int size;
	
	
	public MapeoConAVL() {
		avl = new AVL<EntradaComparable<K,V>>(new DefaultComparator<EntradaComparable<K,V>>());
		size = 0;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size==0;
	}

	@Override
	public V get(K key) throws InvalidKeyException {
		if(key==null) throw new InvalidKeyException("get(); Clave no valida");
		V valor = null;
		NodoAVL<EntradaComparable<K,V>> entrada = avl.buscar(new EntradaComparable<K,V>(key,null)); 
		if(entrada!=null)
			valor = entrada.getElem().getValue();
		return valor;
	} 
	
	@Override
	public V put(K key, V value) throws InvalidKeyException {
		if(key==null) throw new InvalidKeyException("put(); clave no valida");
		EntradaComparable<K,V> entrada = new EntradaComparable<K,V>(key,value);
		V valor = null;
		NodoAVL<EntradaComparable<K,V>> nodo = avl.buscar(entrada);
		if(nodo==null) {
			avl.insert(entrada);
			size++;
		}
		else {
			valor = nodo.getElem().getValue();
			nodo.getElem().setValue(value);
		}
		return valor;
	}

	@Override
	public V remove(K key) throws InvalidKeyException {
		if(key==null) throw new InvalidKeyException("remove(); clave no valida");
		V valor = null;
		NodoAVL<EntradaComparable<K,V>> entrada = avl.buscar(new EntradaComparable<K,V>(key,null));
		if(entrada != null) {
			valor = entrada.getElem().getValue();
			avl.remove(entrada);
			size--;
		}
		return valor;
	}

	@Override
	public Iterable<K> keys() {
		PositionList<K> lista = new ListaDE<K>();
		if(size>0)
			preOrdenKeys(lista,avl.raiz());
		return lista;
	}
	private void preOrdenKeys(PositionList<K> lista, NodoAVL<EntradaComparable<K, V>> nodoAVL) {
		lista.addLast(nodoAVL.getElem().getKey());
		if(nodoAVL.getIzq().getElem()!=null)
			preOrdenKeys(lista,nodoAVL.getIzq());
		if(nodoAVL.getDer().getElem()!=null)
			preOrdenKeys(lista,nodoAVL.getDer());
	}

	@Override
	public Iterable<V> values() {
		PositionList<V> lista = new ListaDE<V>();
		if(size>0)
			preOrdenValues(lista,avl.raiz());
		return lista;
	}
	private void preOrdenValues(PositionList<V> lista, NodoAVL<EntradaComparable<K, V>> nodoAVL) {
		lista.addLast(nodoAVL.getElem().getValue());
		if(nodoAVL.getIzq().getElem()!=null)
			preOrdenValues(lista,nodoAVL.getIzq());
		if(nodoAVL.getDer().getElem()!=null)
			preOrdenValues(lista,nodoAVL.getDer());
	}

	@Override
	public Iterable<Entry<K, V>> entries() {
		PositionList<Entry<K,V>> lista = new ListaDE<Entry<K,V>>();
		if(size>0)
			preOrden(lista,avl.raiz());
		return lista;
	}
	private void preOrden(PositionList<Entry<K,V>> lista, NodoAVL<EntradaComparable<K, V>> n) {
		lista.addLast(n.getElem());
		if(n.getIzq().getElem()!=null)
			preOrden(lista,n.getIzq());
		if(n.getDer().getElem()!=null)
			preOrden(lista,n.getDer());
	}
	public static <E extends Comparable<E>> void por_niveles(AVL<E> t) {
		Queue<NodoAVL<E>> Cola = new Cola_con_enlaces<NodoAVL<E>>();
		NodoAVL<E> n;
		Cola.enqueue(t.raiz());
		Cola.enqueue(null);
		while(!Cola.isEmpty()) {
			try {
				n = Cola.dequeue();
				if(n!=null) {
					System.out.print(n.getElem()+" ");
					if(n.getIzq().getElem()!=null)
						Cola.enqueue(n.getIzq());
					if(n.getDer().getElem()!=null)
						Cola.enqueue(n.getDer());
				}
				else {
					System.out.println();
					if (!Cola.isEmpty())
						Cola.enqueue(null);
				}
			} catch (EmptyQueueException e) {e.printStackTrace();}
		}
	}
}
