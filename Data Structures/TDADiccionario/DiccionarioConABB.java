package TDADiccionario;
import ABB.*;
import TDALista.*;

public class DiccionarioConABB<K extends Comparable<K>,V> implements Dictionary<K,V>{
	
	protected ABB<EntradaComparable<K,PositionList<Entry<K,V>>>> abb;
	protected int size;
	
	public DiccionarioConABB() {
		abb = new ABB<EntradaComparable<K,PositionList<Entry<K,V>>>>();
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
	public Entry<K,V> find(K key) throws InvalidKeyException {
		if(key==null) throw new InvalidKeyException("find(); clave no valida");
		NodoABB<EntradaComparable<K,PositionList<Entry<K,V>>>> entrada = abb.buscar(new EntradaComparable<K,PositionList<Entry<K,V>>>(key,null));
		Entry<K,V> e = null;
		if(entrada != null && entrada.getElem()!=null && !entrada.getElem().getValue().isEmpty())
			try {e = entrada.getElem().getValue().first().element();} catch (EmptyListException e1) {e1.printStackTrace();}
		return e;
	}

	@Override
	public Iterable<Entry<K, V>> findAll(K key) throws InvalidKeyException {
		if(key==null) throw new InvalidKeyException("find(); clave no valida");
		PositionList<Entry<K,V>> lista = new ListaDE<Entry<K,V>>();
		NodoABB<EntradaComparable<K,PositionList<Entry<K,V>>>> entrada = abb.buscar(new EntradaComparable<K,PositionList<Entry<K,V>>>(key,null));
		if(entrada != null && entrada.getElem() != null && !entrada.getElem().getValue().isEmpty()) 
			lista = entrada.getElem().getValue();
		return lista;
	}

	@Override
	public Entry<K, V> insert(K key, V value) throws InvalidKeyException {
		if(key==null) throw new InvalidKeyException("insert(); clave no valida");
		EntradaComparable<K,PositionList<Entry<K,V>>> entrada;
		NodoABB<EntradaComparable<K,PositionList<Entry<K,V>>>> nodo;
		Entry<K,V> retorno;
		entrada = new EntradaComparable<K,PositionList<Entry<K,V>>>(key,null);
		nodo = abb.buscar(entrada);
		retorno = new EntradaComparable<K,V>(key,value);
		if(nodo.getElem() != null)
			nodo.getElem().getValue().addLast(retorno);
		else {
			abb.expandir(nodo, entrada);
			entrada.setValue(new ListaDE<Entry<K,V>>());
			entrada.getValue().addLast(retorno);
		}
		size++;
		return retorno;
	}

	@Override
	public Entry<K, V> remove(Entry<K,V> e) throws InvalidEntryException {
		if(e==null) throw new InvalidEntryException("remove(); entrada no valida");
		boolean elimine = false;
		NodoABB<EntradaComparable<K,PositionList<Entry<K,V>>>> Nodo = abb.buscar(new EntradaComparable<K,PositionList<Entry<K,V>>>(e.getKey(),null));
		Position<Entry<K,V>> entry = null;
		Entry<K,V> entrada = null;
		PositionList<Entry<K,V>> lista = Nodo.getElem().getValue();
		try {
			if(!lista.isEmpty()) {
				entry = lista.first();
				while(!elimine && entry != null) {
					if(entry.element() == e) {
						elimine = true;
						size--;
						entrada = lista.remove(entry);
					}
					else
						entry = (entry==lista.last())? null : lista.next(entry);
				}
			}
		}catch(EmptyListException | InvalidPositionException | BoundaryViolationException e1) {e1.printStackTrace();}
		if(elimine)
			return entrada;
		else
			throw new InvalidEntryException("remove(); entrada no existe");
	}

	@Override
	public Iterable<Entry<K, V>> entries() {
		PositionList<Entry<K,V>> lista = new ListaDE<Entry<K,V>>();
		if(size>0)
			preOrden(lista,abb.raiz());
		return lista;
	}
	private void preOrden(PositionList<Entry<K,V>> lista, NodoABB<EntradaComparable<K,PositionList<Entry<K,V>>>> nodoABB) {
		for(Position<Entry<K,V>> e : nodoABB.getElem().getValue().positions())
			lista.addLast(e.element());
		if(nodoABB.getIzq() != null)
			preOrden(lista,nodoABB.getIzq());
		if(nodoABB.getDer() != null)
			preOrden(lista,nodoABB.getDer());
	}
}
