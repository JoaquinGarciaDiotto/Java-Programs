package TDAMapeo;


import java.util.Iterator;
import TDALista.*;

public class MapeoConLista<K,V> implements Map<K,V>{
	protected PositionList<Entrada<K,V>> S;
	
	public MapeoConLista() {
		S = new ListaDE<Entrada<K,V>>();
	}
	
	@Override
	public int size() { //O(1)
		return S.size();
	}
	
	@Override
	public boolean isEmpty() { //O(1)
		return S.isEmpty();
	}

	@Override
	public V get(K key) throws InvalidKeyException { //O(n)
		if(key==null) throw new InvalidKeyException("get(); Clave nula");
		for(Position<Entrada<K,V>> p : S.positions()) 			
			if(p.element().getKey().equals(key))
				return p.element().getValue();
		return null;
		/*Iterator<Entrada<K,V>> I = S.iterator();
		V value = null;
		Entrada<K,V> e = null; boolean encontre = false;
		while(I.hasNext() && !encontre) {
			e = I.next();
			if(e.getKey().equals(key)) {
				value = e.getValue();
				encontre = true;
			}
		}
		return value;*/
	}

	@Override
	public V put(K key, V value) throws InvalidKeyException { //O(n)
		if(key==null) throw new InvalidKeyException("put(); Clave nula");
		for(Position<Entrada<K,V>> p:S.positions()) {
			if(p.element().getKey().equals(key)) {
				V aux = p.element().getValue();
				p.element().setValue(value);
				return aux;
			}
		}
		S.addLast(new Entrada<K,V>(key,value));
		return null;
		/*Iterator<Entrada<K,V>> I = S.iterator();
		V v = null;
		Entrada<K,V> e = null; boolean encontre = false;
		while(I.hasNext() && !encontre) {
			e = I.next();
			if(e.getKey().equals(key)) {
				v = e.getValue();
				e.setValue(v);
				encontre = true;
			}
		}
		if(!encontre)
			S.addLast(new Entrada<K,V>(key,value));
		return v;*/
	}

	@Override
	public V remove(K key) throws InvalidKeyException { //O(n)
		if(key==null) throw new InvalidKeyException("remove(); Clave nula");
		for(Position<Entrada<K,V>> p : S.positions()) {
			if(p.element().getKey().equals(key)) {
				V value = p.element().getValue();
				try {S.remove(p);} catch (InvalidPositionException e) {}
				return value;
			}
		}
		return null;
		/*Iterator<Position<Entrada<K,V>>> I = S.positions().iterator();
		V v = null;
		Position<Entrada<K,V>> e = null; boolean encontre = false;
		while(I.hasNext() && !encontre) {
			e = I.next();
			if(e.element().getKey().equals(key)) {
				v = e.element().getValue();
				try {S.remove(e);} catch (InvalidPositionException e1) {}
				encontre = true;
			}
		}
		return v;*/
	}

	@Override
	public Iterable<K> keys() {
		PositionList<K> l = new ListaDE<K>();
		for(Entry<K,V> e : S) {
			l.addLast(e.getKey());
		}
		return l;
	}

	@Override
	public Iterable<V> values() {
		PositionList<V> l = new ListaDE<V>();
		for(Entry<K,V> e : S) {
			l.addLast(e.getValue());
		}
		return l;
	}

	@Override
	public Iterable<Entry<K,V>> entries() {
		PositionList<Entry<K,V>> l = new ListaDE<Entry<K,V>>();
		for(Entry<K,V> e : S) {
			l.addLast(e);
		}
		return l;
	}
}
