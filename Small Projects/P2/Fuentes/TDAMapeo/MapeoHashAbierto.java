package TDAMapeo;

import java.util.Iterator;
import TDALista.*;

/**
 * Modela la interfaz Map mediante la estructura de datos Mapeo Hash Abierto.
 * @author Joaquin Garcia Diotto
 *
 * @param <K> Tipo de dato utilizado en la clase por las claves del mapeo.
 * @param <V> Tipo de dato utilizado en la clase por los valores del mapeo.
 */
public class MapeoHashAbierto<K,V> implements Map<K,V>{
	protected PositionList<Entrada<K,V>>[] MHA;
	protected int n;
	protected int N = 10;
	protected static final float fc = 0.5f;

	@SuppressWarnings("unchecked")
	/**
	 * Crea un nuevo Mapeo vacio.
	 */
	public MapeoHashAbierto() {	
		n=0;
		MHA = (PositionList<Entrada<K,V>>[]) new PositionList[N];
		for(int i=0; i<N; i++) {
			MHA[i] = new ListaDE<Entrada<K,V>>();
		}
		
	}
	/**
	 * Se obtiene el bucket en donde almacenar una clave.
	 * @param key Clave la cual se requiere obtener el bucket.
	 * @return Retorna el bucket correspondiente a la clave.
	 */
	public int hash(K key) {
		return Math.abs(key.hashCode()%N);
	}
	
	@Override
	public int size() {
		return (int) n;
	}

	@Override
	public boolean isEmpty() {
		return n==0;
	}

	@Override
	public V get(K key) throws InvalidKeyException {
		V value = null; int Bucket; 
		Position<Entrada<K, V>> entrada; 
		PositionList<Entrada<K,V>> lista; 
		boolean encontre = false;
		if(key==null) throw new InvalidKeyException("get(); Clave nula");
		Bucket = hash(key);
		lista = MHA[Bucket];
		try {
			if(!lista.isEmpty()) {
				entrada = lista.first();
				while(!encontre && entrada != null) {
					if(entrada.element().getKey().equals(key)) {
						encontre = true;
						value = entrada.element().getValue();
					}
					else
						entrada = (entrada==lista.last())? null : lista.next(entrada);
				}
			}
		}catch(EmptyListException | InvalidPositionException | BoundaryViolationException e) {e.printStackTrace();}
		return value;
	}

	@Override
	public V put(K key, V value) throws InvalidKeyException {
		V aux = null; 
		boolean reemplace = false; 
		int Bucket; 
		Iterator<Entrada<K,V>> it; 
		Entrada<K,V> pos;
		if(key==null) throw new InvalidKeyException("put(); Clave nula");
		if(n/(float)N >= fc)
			redimensionar();
		Bucket = hash(key);
		it = MHA[Bucket].iterator();
		while(it.hasNext() && !reemplace) {
			pos = it.next();
			if(pos.getKey().equals(key)) {
				aux = pos.getValue();
				pos.setValue(value);
				reemplace = true;
			}
		}
		if(!reemplace) {
			MHA[Bucket].addLast(new Entrada<K,V>(key,value));
			n++;
		}
		return aux;
	}
	/**
	 * Aumenta el tamaño del mapeo.
	 */
	private void redimensionar() {
		PositionList<Entrada<K,V>>[] MHAviejo = MHA; 
		PositionList<Entrada<K,V>> ListaVieja; 
		Position<Entrada<K,V>> pos; 
		Entrada<K,V> entrada; 
		int Bucket;
		MHA = inicializarArreglo(N=nextPrimo(N*2));
		try {
			for(int i=0; i<MHAviejo.length; i++) {
				ListaVieja = MHAviejo[i];
				if(!ListaVieja.isEmpty()) {
					pos = ListaVieja.first();
					while(!ListaVieja.isEmpty()) {
						entrada = ListaVieja.remove(pos);
						if(!ListaVieja.isEmpty())
							pos = ListaVieja.first();
						Bucket = hash(entrada.getKey());
						MHA[Bucket].addLast(entrada);
					}
				}
			}
		}catch(EmptyListException | InvalidPositionException e) {e.printStackTrace();}
	}
	/**
	 * Crea un nuevo mapeo vacio.
	 * @param cant Tamaño deseado del nuevo mapeo.
	 * @return Retorna un nuevo mapeo vacio pero con cada componente inicializada.
	 */
	private PositionList<Entrada<K,V>>[] inicializarArreglo(int cant){
		 @SuppressWarnings("unchecked")
		PositionList<Entrada<K,V>>[] nuevoArreglo = (PositionList<Entrada<K,V>>[]) new PositionList[cant];
		 for(int i = 0; i<cant; i++) {
			 nuevoArreglo[i] = new ListaDE<Entrada<K,V>>();
		 }
		 return nuevoArreglo;
	}
	/**
	 * A partir de un numero entero, busca el numero primo mas proximo.
	 * @param n Numero entero desde el cual buscar.
	 * @return Retorna el numero primo mas proximo.
	 */
	private int nextPrimo(Integer n) {
		boolean encontre = false;
		while(!encontre) {
			n++;
			encontre = esPrimo(n);
		}
		return n;
	}
	/**
	 * Decide si un cierto numero es primo.
	 * @param n Numero a verificar si es primo.
	 * @return Retorna verdadero indicando si es primo; falso en caso contrario.
	 */
	private boolean esPrimo(int n) {
		boolean es = true; int i, k = i = 2; int f = (int) Math.sqrt(n);
		while(es && i<=f) {
			es = n%i != 0;
			k++;
			i = 2*k-1;
		}
		return es;
	}
	
	@Override
	public V remove(K key) throws InvalidKeyException {
		V value = null; 
		int Bucket; 
		Position<Entrada<K,V>> pos; 
		PositionList<Entrada<K,V>> lista;
		if(key==null) throw new InvalidKeyException("remove(); Clave nula");
		try {
			Bucket = hash(key);
			lista = MHA[Bucket];
			if(!lista.isEmpty()) {
				pos = lista.first();
				while(pos != null) {
					if(pos.element().getKey().equals(key)) {
						value = pos.element().getValue();
						lista.remove(pos);
						pos = null;
						n--;
					}
					else
						pos = (pos==lista.last())? null : lista.next(pos);
				}
			}
		} catch (InvalidPositionException | EmptyListException | BoundaryViolationException e) {}
		return value;
	}

	@Override
	public Iterable<K> keys() {
		PositionList<K> keys = new ListaDE<K>();
		for(int i=0; i<N; i++) {
			if(MHA[i] != null)
				for(Entry<K,V> entrada : MHA[i])
					keys.addLast(entrada.getKey());
		}
		return keys;
	}

	@Override
	public Iterable<V> values(){
		PositionList<V> values = new ListaDE<V>();
		for(int i=0; i<N; i++) {
			if(MHA[i] != null)
				for(Entry<K,V> entrada : MHA[i])
					values.addLast(entrada.getValue());
		}
		return values;
	}

	@Override
	public Iterable<Entry<K, V>> entries() {
		PositionList<Entry<K,V>> entradas = new ListaDE<Entry<K,V>>();
		for(int i=0; i<N; i++) {
			if(MHA[i] != null)
				for(Entry<K,V> entrada : MHA[i])
					entradas.addLast(entrada);
		}
		return entradas;
	}
}
