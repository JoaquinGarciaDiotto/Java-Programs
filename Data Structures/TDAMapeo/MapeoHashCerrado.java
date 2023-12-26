package TDAMapeo;

import TDALista.*;

public class MapeoHashCerrado<K,V> implements Map<K,V>{
	protected Entrada<K,V>[] MHC;
	protected int n; //Cantidad de elementos
	protected int N = 53; //Size total del arreglo
	protected static final float fc = 0.5f;
	
	protected final Entrada<K,V> bucketNoUsado = new Entrada<K,V>(null,null); //null
	protected final Entrada<K,V> bucketDisp = new Entrada<K,V>(null,null);
	
	
	@SuppressWarnings("unchecked")
	public MapeoHashCerrado() {
		MHC = (Entrada<K,V>[]) new Entrada[N];
		for(int i=0; i<N; i++)
			MHC[i]= bucketNoUsado;
	}
	
	protected int hash(K key) {
		return Math.abs(key.hashCode()%N);
	}
	
	@Override
	public int size() {
		return n;
	}

	@Override
	public boolean isEmpty() {
		return n==0;
	}

	@Override
	public V get(K key) throws InvalidKeyException {
		if (key==null) throw new InvalidKeyException("get(); Clave nula");
		V value = null;
		Entrada<K,V>entrada = null;
		int Bucket = hash(key); int j = 0;
		boolean recorri = false, encontre = false;
		while(!encontre && entrada!=bucketNoUsado && !recorri) {
			entrada = MHC[(Bucket + j)%N];
			if(entrada != bucketDisp && entrada != bucketNoUsado && entrada.getKey().equals(key)) {
				value = entrada.getValue();
				encontre = true;
			}
			j++;
			recorri = (Bucket+j)%N == Bucket;
		}
		return value;
	}

	@Override
	public V put(K key, V value) throws InvalidKeyException {
		if(key==null) throw new InvalidKeyException("insert(); Clave nula");
		V v = null;
		int Bucket = hash(key); int j = 0; int pos;
		boolean encontreLugar = false, recorri = false;
		if(n >= N*fc)
			redimensionar();
		Bucket = hash(key);
		Entrada<K,V> e = new Entrada<K,V>(key,value);
		while(!encontreLugar && !recorri) {
			pos = (Bucket+j)%N;
			if(MHC[pos] != bucketDisp && (MHC[pos] == bucketNoUsado || MHC[pos].getKey().equals(key))) {
				if(MHC[pos] != bucketNoUsado) {
					v = MHC[pos].getValue();
					MHC[pos].setValue(value);
				}
				else 
					MHC[pos] = e;
				n++;
				encontreLugar = true;
			}
			j++;
			recorri = (Bucket+j)%N == Bucket; //Me fijo si di una vuelta completa al mapeo
		}
		return v;
	}
	private void redimensionar() {
		Entrada<K,V>[] MHCviejo = MHC;
		MHC = inicializarArreglo(N=nextPrimo(N*2));
		int Bucket; int j = 0;
		for(Entrada<K,V> entrada : MHCviejo) {
			if(entrada != bucketNoUsado && entrada != bucketDisp) {
				Bucket = hash(entrada.getKey());
				while(MHC[(Bucket+j)%N] != bucketNoUsado)
					j++;
				MHC[(Bucket+j)%N] = entrada;
				j=0;
			}
		}
	}
	private Entrada<K,V>[] inicializarArreglo(int cant){
		@SuppressWarnings("unchecked")
		Entrada<K,V> [] nuevoArreglo = (Entrada<K,V>[]) new Entrada[cant];
		for(int i=0; i<cant; i++) {
			nuevoArreglo[i] = bucketNoUsado;
		}
		return nuevoArreglo;
	}
	private int nextPrimo(Integer n) {
		boolean encontre = false;
		while(!encontre) {
			n++;
			encontre = esPrimo(n);
		}
		return n;
	}
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
		if(key == null) throw new InvalidKeyException("remove(); clave nula");
		int Bucket = hash(key); int j=0;
		V value = null;
		Entrada<K,V> entrada = MHC[Bucket]; boolean recorri = false, elimine = false;
		while(!elimine && entrada != bucketNoUsado && !recorri) {
			if(entrada != bucketDisp && entrada.getKey().equals(key)) {
				value = entrada.getValue();
				MHC[(Bucket+j)%N] = bucketDisp;
				n--;
				elimine = true;
			}
			else {
				j++;
				recorri = (Bucket+j)%N == Bucket;
				entrada = MHC[(Bucket+j)%N];
			}
		}
		return value;
	}

	@Override
	public Iterable<K> keys() {
		PositionList<K> lista = new ListaDE<K>();
		for(Entry<K, V> e : entries()) {
			lista.addLast(e.getKey());
		}
		return lista;
	}

	@Override
	public Iterable<V> values() {
		PositionList<V> lista = new ListaDE<V>();
		for(Entry<K, V> e : entries()) {
			lista.addLast(e.getValue());
		}
		return lista;
	}

	@Override
	public Iterable<Entry<K, V>> entries() {
		PositionList<Entry<K,V>> lista = new ListaDE<Entry<K,V>>();
		Entrada<K,V> entrada;
		int i = 0; int k = 0;
		while(i<n && k<N) {
			entrada = MHC[k];
			if(entrada != bucketDisp && entrada != bucketNoUsado) {
				lista.addLast(entrada);
				i++;
			}
			k++;
		}
		return lista;
	}

}
