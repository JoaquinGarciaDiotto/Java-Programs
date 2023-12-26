package TDAMapeo;

import ABB.*;
import TDALista.*;

public class MapeoConABB<K extends Comparable<K>,V> implements Map<K,V> {
	
	protected ABB<EntradaComparable<K,V>> abb;
	protected int size;
	
	public MapeoConABB() {
		abb = new ABB<EntradaComparable<K,V>>();
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
		if(key==null) throw new InvalidKeyException("get(); clave nula");
		V valor = null;
		NodoABB<EntradaComparable<K,V>> entrada = abb.buscar(new EntradaComparable<K,V>(key,null));
		if(entrada != null && entrada.getElem() != null)
			valor = entrada.getElem().getValue();
		return valor;
	}
	
	@Override
	public V put(K key, V value) throws InvalidKeyException {
		if(key==null) throw new InvalidKeyException("put(); clave no valida");
		EntradaComparable<K,V> entrada = new EntradaComparable<K,V>(key,value);
		NodoABB<EntradaComparable<K,V>> nodo = abb.buscar(entrada);
		V valor = null;
		if(nodo!=null && nodo.getElem()==null) {
			abb.expandir(nodo, entrada);
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
		NodoABB<EntradaComparable<K,V>> entrada = abb.buscar(new EntradaComparable<K,V>(key,null));
		if(entrada != null && entrada.getElem() != null && entrada.getElem().getKey() != null) {
			valor = entrada.getElem().getValue();
			abb.eliminar(entrada.getElem());
			size--;
		}
		return valor;
	}

	@Override
	public Iterable<K> keys() {
		PositionList<K> lista = new ListaDE<K>();
		for(Entry<K,V> e : entries())
			lista.addLast(e.getKey());
		return lista;
	}

	@Override
	public Iterable<V> values() {
		PositionList<V> lista = new ListaDE<V>();
		for(Entry<K,V> e : entries()) 
			lista.addLast(e.getValue());
		return lista;
	}

	@Override
	public Iterable<Entry<K, V>> entries() {
		PositionList<Entry<K,V>> lista = new ListaDE<Entry<K,V>>();
		if(size>0)
			preOrden(lista,abb.raiz());
		return lista;
	}
	private void preOrden(PositionList<Entry<K,V>> lista, NodoABB<EntradaComparable<K, V>> nodoABB) {
		lista.addLast(nodoABB.getElem());
		if(nodoABB.getIzq() != null)
			preOrden(lista,nodoABB.getIzq());
		if(nodoABB.getDer() != null)
			preOrden(lista,nodoABB.getDer());
	}
}
