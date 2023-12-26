package TRIE;

import java.util.Iterator;
import java.util.Set;

import TDACola.Cola_con_enlaces;
import TDACola.EmptyQueueException;
import TDACola.Queue;
import TDAMapeo.Entry;
import TDAMapeo.InvalidKeyException;
import TDAMapeo.Map;

public class MapeoConTrie<E> implements Map<String, E> {
	
	private NodoTrie<E> raiz;
	private int size;

	public MapeoConTrie() {
		raiz = new NodoTrie<E>(null);
		size=0;
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
	public E get(String key) throws InvalidKeyException { //O(m), m=long(String)
		return getAux(key,0,key.length(),raiz);
	}
	private E getAux(String clave, int i, int n, NodoTrie<E> p) {
		E imagen;
		if(i==n)
			imagen = raiz.getElem();
		else {
			int indice = ((int) clave.charAt(i)) - ((int)'a');
			if(p.getHijo(indice)==null)
				imagen = null;
			else
				imagen = getAux(clave,i+1,n,p.getHijo(indice));
		}
		return imagen;
	}

	@Override
	public E put(String key, E value) throws InvalidKeyException { //O(m)
		if(key==null) throw new InvalidKeyException("put(); Clave no valida");
		return putAux(key,value,0,key.length(),raiz);
	}
	private E putAux(String clave, E valor, int i, int n, NodoTrie<E> p) {
		E imagen;
		if(i<n) {
			int indice = ((int) clave.charAt(i)) - ((int)'a');
			if(p.getHijo(indice)==null)
				p.setHijo(indice, new NodoTrie<E>(p));
			imagen = putAux(clave,valor,i+1,n,p.getHijo(indice));
		}
		else {
			imagen = p.getElem();
			p.setElem(valor);
			size++;
		}
		return imagen;
	}
	
	@Override
	public E remove(String key) throws InvalidKeyException { //O(dm), d=#E
		return removeAux(key,0,key.length(),raiz,0);
	}
	private E removeAux(String clave, int i, int n, NodoTrie<E> p, int indiceP) throws InvalidKeyException {
		E r = null;
		if(i==n) {
			if(p.getElem()==null) throw new InvalidKeyException("Clave inexistente");
			r = raiz.getElem();
			raiz.setElem(null);
		}
		else {
			int indice = ((int) clave.charAt(i)) - ((int)'a');
			if(p.getHijo(indice)==null) throw new InvalidKeyException("Clave inexistente");
			r = removeAux(clave,i+1,n,p.getHijo(indice),indice);
		}
		if(todoNulo(p))
			if(p!=raiz) {
				p.getPadre().setHijo(indiceP, null);
				p.setPadre(null);
			}
		return r;
	}
	private boolean todoNulo(NodoTrie<E> n) { 
		boolean todosNulos = true;
		for(int i = 0; i<26; i++)
			if(n.getHijo(i)!=null) {
				todosNulos = false;
				break;
			}	
		return todosNulos && n.getElem()==null;
	}
	@Override
	public Iterable<String> keys() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<E> values() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<Entry<String, E>> entries() {
		// TODO Auto-generated method stub
		return null;
	}
	public String palabraLarga() { //Si tengo acceso a la estructura
		String palabra = "";
		if(size>0) {
			Queue<NodoTrie<E>> q = new Cola_con_enlaces<NodoTrie<E>>();
			q.enqueue(raiz);
			NodoTrie<E> act, ult = null;
			while(!q.isEmpty()) {
				try {
					act = q.dequeue();
					for(int i = 0; i<26; i++) {
						if(act.getHijo(i)!=null)
							q.enqueue(act.getHijo(i));
					}
					ult = act;
				} catch (EmptyQueueException e) {e.printStackTrace();}
			}
			act = ult;
			while(act!=null) {
				if(act.getPadre()!=null)
					for(int i = 0; i<26; i++) 
						if(act.getPadre().getHijo(i)==act)
							palabra = (char)(i + '0') + palabra;
				act = act.getPadre();
			}
		}
		return palabra;
	}
	public String palabraLargaNoAcceso(Map<String, E> m) { //Si NO tengo acceso a la estructura.
		String larga = "";
		for(String s : m.keys())
			if(s.length()>larga.length())
				larga = s;
		return larga;
	}
	public int cantAble(Set<String> D) {	//Supongo que sera asi porque de otra forma no tiene sentido. Estaria adentro del set????
		int cant = 0;
		Iterator<String> it = D.iterator();
		while(it.hasNext())
			if(it.next().endsWith("able"))
				cant++;
		return cant;
	}
}
