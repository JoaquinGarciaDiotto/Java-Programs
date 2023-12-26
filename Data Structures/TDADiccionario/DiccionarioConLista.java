package TDADiccionario;

import TDALista.*;

public class DiccionarioConLista<K,V> implements Dictionary<K,V>{
	protected ListaDE<Entry<K,V>> D;
	
	
	public DiccionarioConLista() {
		D = new ListaDE<Entry<K,V>>();
	}
	
	@Override
	public int size() { //O(1)
		return D.size();
	}

	@Override
	public boolean isEmpty() { //O(1)
		return D.isEmpty();
	}

	@Override
	public Entry<K,V> find(K key) throws InvalidKeyException { //O(n)
		if(key==null) throw new InvalidKeyException("find(); Clave invalida");
		for(Position<Entry<K,V>> p : D.positions()) {
			if(p.element().getKey().equals(key))
				return p.element();
		}
		return null;
	}

	@Override
	public Iterable<Entry<K, V>> findAll(K key) throws InvalidKeyException { //O(n)
		if(key==null) throw new InvalidKeyException("findAll(); Clave nula");
		PositionList<Entry<K,V>> lista = new ListaDE<Entry<K,V>>();
		for(Position<Entry<K,V>> p : D.positions())
			if(p.element().getKey().equals(key))
				lista.addLast(p.element());
		return lista;
	}

	@Override
	public Entry<K, V> insert(K key, V value) throws InvalidKeyException { // O(1)
		if(key==null) throw new InvalidKeyException("insert(); Clave invalida");
		Entry<K,V> e = new Entrada<K,V>(key,value);
		D.addLast(e);
		return e;
	}

	@Override
	public Entry<K, V> remove(Entry<K,V> e) throws InvalidEntryException { //O(n)
		if(e==null) throw new InvalidEntryException("remove(); entrada nula");
		for(Position<Entry<K,V>> p: D.positions()) {
			if(p.element()==e) {
				try {D.remove(p);} catch (InvalidPositionException e1) {}
				return e;
			}
		}
		throw new InvalidEntryException("remove(); no existe la entrada");
	}

	@Override
	public Iterable<Entry<K, V>> entries() { //O(n)
		return D;
	}
}
