package TDADiccionario;
import TDALista.*;

/**
 * Modela la interfaz Dictionary mediante la estructura de datos Diccionario Hash Cerrado.
 * @author Joaquin Garcia Diotto
 *
 * @param <K> Tipo de dato utilizado en la clase por las claves del diccionario.
 * @param <V> Tipo de dato utilizado en la clase por los valores del diccionario.
 */
public class DiccionarioHashCerrado<K,V> implements Dictionary<K,V>{
	protected Entrada<K,V> [] DHC;
	protected int N = 53;
	protected int n;
	protected static final float fc = 0.5f;
	
	protected final Entrada<K,V> bucketNoUsado = new Entrada<K,V>(null,null);
	protected final Entrada<K,V> bucketDisp = new Entrada<K,V>(null,null);
	
	@SuppressWarnings("unchecked")
	
	/**
	 * Crea un Diccionario vacio.
	 */
	public DiccionarioHashCerrado() {
		DHC = (Entrada<K,V>[]) new Entrada[N];
		for(int i=0; i<N; i++)
			DHC[i]= bucketNoUsado;
	}
	
	/**
	 * Se obtiene el bucket en donde almacenar una clave.
	 * @param key Clave la cual se requiere obtener el bucket.
	 * @return Retorna el bucket correspondiente a la clave.
	 */
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
	public Entry<K, V> find(K key) throws InvalidKeyException {
		if(key==null) throw new InvalidKeyException("find(); Clave nula");
		Entrada<K,V> e = null, entrada = null;
		int Bucket = hash(key), j = 0;
		boolean encontre = false, recorri = false;
		while(!encontre && e != bucketNoUsado && !recorri) {
			e = DHC[(Bucket + j)%N];
			if(e != bucketDisp && e != bucketNoUsado && e.getKey().equals(key)) {
				entrada = e;
				encontre = true;
			}
			j++;
			recorri = (Bucket + j)%N == Bucket; 									//Si el Bucket en el que estoy es el mismo que el del comienzo, entonces significa que recorri todo el Diccionario dando una vuelta completa, por lo que no encontre la clave.
		}
		return entrada;
	}

	@Override
	public Iterable<Entry<K,V>> findAll(K key) throws InvalidKeyException {
		if(key==null) throw new InvalidKeyException("findAll(): Clave no valida");
		PositionList<Entry<K,V>> lista = new ListaDE<Entry<K,V>>();
		int Bucket = hash(key), j = 0;
		boolean recorri = false;
		Entrada<K,V> entrada = DHC[Bucket];
		while(entrada != bucketNoUsado && !recorri) {
			if(entrada != bucketDisp && entrada.getKey().equals(key))
				lista.addLast(entrada);
			j++;
			entrada = DHC[(Bucket+j)%N];
			recorri = entrada==DHC[Bucket];
		}
		return lista;
	}

	@Override
	public Entry<K,V> insert(K key, V value) throws InvalidKeyException {
		if(key==null) throw new InvalidKeyException("insert(); Clave nula");
		int pos, Bucket = hash(key), j = 0;
		boolean encontreLugar = false;
		if(n >= N*fc)
			redimensionar();
		Bucket = hash(key);
		Entrada<K,V> e = new Entrada<K,V>(key,value);
		while(!encontreLugar) {
			pos = (Bucket+j)%N;
			if(DHC[pos] == bucketNoUsado || DHC[pos] == bucketDisp) {
				DHC[pos] = e;
				n++;
				encontreLugar = true;
			}
			j++;
		}
		return e;
	}
	/**
	 * Aumenta el tamaño del Diccionario.
	 */
	private void redimensionar() {
		Entrada<K,V>[] DHCviejo = DHC;
		DHC = inicializarArreglo(N=nextPrimo(N*2));
		int Bucket, j = 0;
		for(Entrada<K,V> entrada : DHCviejo) {
			if(entrada != bucketNoUsado && entrada != bucketDisp) {
				Bucket = hash(entrada.getKey());
				while(DHC[(Bucket+j)%N] != bucketNoUsado)
					j++;
				DHC[(Bucket+j)%N] = entrada;
				j=0;
			}
		}
	}
	/**
	 * Crea un nuevo Diccionario vacio.
	 * @param cant Tamaño deseado del nuevo arreglo.
	 * @return Retorna un nuevo Diccionario vacio pero con cada componente inicializada.
	 */
	private Entrada<K,V>[] inicializarArreglo(int cant){
		@SuppressWarnings("unchecked")
		Entrada<K,V> [] nuevoArreglo = (Entrada<K,V>[]) new Entrada[cant];
		for(int i=0; i<cant; i++) {
			nuevoArreglo[i] = bucketNoUsado;
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
	public Entry<K,V> remove(Entry<K,V> e) throws InvalidEntryException {
		if(e == null) throw new InvalidEntryException("remove(); Entrada nula");
		int Bucket = hash(e.getKey()), j=0;
		Entrada<K,V> entrada = DHC[Bucket]; 
		boolean elimine = false;
		while(!elimine && entrada != bucketNoUsado) {
			if(entrada != bucketDisp && entrada.getKey().equals(e.getKey()) && entrada.getValue().equals(e.getValue())) {
				DHC[(Bucket+j)%N] = bucketDisp;
				n--;
				elimine = true;
			}
			else {
				j++;
				entrada = DHC[(Bucket+j)%N];
			}
		}
		if(elimine)
			return e;
		else
			throw new InvalidEntryException("remove(); no existe la entrada");
	}

	@Override
	public Iterable<Entry<K, V>> entries() {
		PositionList<Entry<K,V>> lista = new ListaDE<Entry<K,V>>();
		Entrada<K,V> entrada;
		int i = 0, k = 0;
		while(i<n && k<N) {
			entrada = DHC[k];
			if(entrada != bucketDisp && entrada != bucketNoUsado) {
				lista.addLast(entrada);
				i++;
			}
			k++;
		}
		return lista;
	}
}
