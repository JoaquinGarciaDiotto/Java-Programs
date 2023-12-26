package TDAMapeo;

import A23.A23;
import A23.Nodo23;
import TDALista.ListaDE;
import TDALista.PositionList;

public class MapeoConA23<K extends Comparable<K>,V> implements Map<K,V>{

	private A23<EntradaComparable<K,V>> a;
	private int size;
	
	public MapeoConA23() {
		a = new A23<EntradaComparable<K,V>>();
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
		if(key==null) throw new InvalidKeyException("get(); Clave nula.");
		V v = null;
		Nodo23<EntradaComparable<K,V>> n = a.recuperar(new EntradaComparable<K,V>(key,null));
		if(n!=null)
			v = (n.getK1().getKey()==key)? n.getK1().getValue() : n.getK2().getValue();
		return v;
	}

	@Override
	public V put(K key, V value) throws InvalidKeyException {
		if(key==null) throw new InvalidKeyException("put(); Clave nula.");
		V v = null;
		EntradaComparable<K,V> entrada = new EntradaComparable<K,V>(key,value);
		Nodo23<EntradaComparable<K,V>> nodoBuscado = a.recuperar(entrada);
		if(nodoBuscado==null) {
			//a.insertar(entrada);
			size++;
		}
		else {
			EntradaComparable<K,V> aux;
			if(nodoBuscado.getK1()!=null && nodoBuscado.getK1().getKey()==entrada.getKey()) {
					aux = nodoBuscado.getK1();
					v = aux.getValue();
					aux.setValue(value);
			}
			else if(nodoBuscado.getK2()!=null && nodoBuscado.getK2().getKey()==entrada.getKey()){
					aux = nodoBuscado.getK2();
					v = aux.getValue();
					aux.setValue(value);
			}	
		}
		return v;
	}

	@Override
	public V remove(K key) throws InvalidKeyException {
		if(key==null) throw new InvalidKeyException("remove(); Clave nula.");
		V v = null;
		Nodo23<EntradaComparable<K,V>> nodoBuscado = a.recuperar(new EntradaComparable<K,V>(key,null));
		if(nodoBuscado!=null) {
			if(nodoBuscado.getK1()!=null && nodoBuscado.getK1().getKey()==key) 
					v = nodoBuscado.getK1().getValue();
			else if(nodoBuscado.getK2()!=null && nodoBuscado.getK2().getKey()==key)
					v = nodoBuscado.getK2().getValue();
			//a.remove(nodoBuscado);
			size--;
		}
		return v;
	}

	@Override
	public Iterable<K> keys() {
		PositionList<K> l = new ListaDE<K>();
		if(size>0)
			preOrderKeys(l,a.raiz());
		return l;
	}
	private void preOrderKeys(PositionList<K> l, Nodo23<EntradaComparable<K,V>> n) {
		if(n.getK1()!=null)
			l.addLast(n.getK1().getKey());
		if(n.getK2()!=null)
			l.addLast(n.getK2().getKey());
		if(n.getIzq()!=null)
			preOrderKeys(l,n.getIzq());
		if(n.getMed()!=null)
			preOrderKeys(l,n.getMed());
		if(n.getDer()!=null)
			preOrderKeys(l,n.getDer());
	}
	@Override
	public Iterable<V> values() {
		PositionList<V> l = new ListaDE<V>();
		if(size>0)
			postOrderValues(l,a.raiz());
		return l;
	}
	private void postOrderValues(PositionList<V> l, Nodo23<EntradaComparable<K,V>> n) {
		if(n.getIzq()!=null)
			postOrderValues(l,n.getIzq());
		if(n.getMed()!=null)
			postOrderValues(l,n.getMed());
		if(n.getDer()!=null)
			postOrderValues(l,n.getDer());
		if(n.getK1()!=null)
			l.addLast(n.getK1().getValue());
		if(n.getK2()!=null)
			l.addLast(n.getK2().getValue());
	}

	@Override
	public Iterable<Entry<K, V>> entries() {
		PositionList<Entry<K,V>> l = new ListaDE<Entry<K,V>>();
		if(size>0)
			inOrder(l,a.raiz());
		return l;
	}
	private void inOrder(PositionList<Entry<K,V>> l, Nodo23<EntradaComparable<K,V>> n) {
		if(n.getIzq()!=null)
			inOrder(l,n.getIzq());
		if(n.getK1()!=null)
			l.addLast(n.getK1());
		if(n.getMed()!=null)
			inOrder(l,n.getMed());
		if(n.getK2()!=null)
			l.addLast(n.getK2());
		if(n.getDer()!=null)
			inOrder(l,n.getDer());
	}

}
