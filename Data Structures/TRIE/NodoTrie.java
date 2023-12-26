package TRIE;

import TDALista.ListaDE;
import TDALista.PositionList;

public class NodoTrie<E> {
	
	private E elemento;
	private NodoTrie<E> padre;
	private NodoTrie<E>[] hijos;
	PositionList<NodoTrie<E>> l = new ListaDE<NodoTrie<E>>();
	
	public NodoTrie(NodoTrie<E> p) {
		hijos = new NodoTrie[26];
		elemento = null;
		padre = p;
		for(int i = 0; i<=26;i++)
			l.addLast(null);
	}
	
	public void setElem(E e) {
		elemento = e;
		
	}
	public void setHijo(int i, NodoTrie<E> h) {
		hijos[i] = h;
	}
	public void setPadre(NodoTrie<E> p) {
		padre = p;
	}
	
	public E getElem() {
		return elemento;
	}
	public NodoTrie<E> getHijo(int i){
		return hijos[i];
	}
	public NodoTrie<E> getPadre(){
		return padre;
	}
}
