package Operaciones;
import TDADiccionario.*;
import TDALista.*;
import TDAMapeo.*;
import TDAMapeo.InvalidKeyException;


public class OpConMapeosYDicc <K,V>{

	public static <K,V> boolean contiene_claves(Map<K,V> m1, Map<K,V> m2) {
		for(TDAMapeo.Entry<K,V> e : m1.entries()) 
			if(!pertenece(e.getKey(),m2))
				return false;
		return true;
	}
	private static <K,V> boolean pertenece(K key, Map<K,V> m) {
		for(TDAMapeo.Entry<K,V> e : m.entries())
			if(e.getKey().equals(key))
				return true;
		return false;
	}
	
	public static <K,V> PositionList<TDAMapeo.Entry<K,V>> contiene_claves_y_valores_diferentes(Map<K,V> m1, Map<K,V> m2){
		PositionList<TDAMapeo.Entry<K,V>> lista = new ListaDE<TDAMapeo.Entry<K,V>>(); int cant = 0;
		for(TDAMapeo.Entry<K, V> e1 : m1.entries()) {
			lista.addLast(e1);
			for(TDAMapeo.Entry<K, V> e2 :  m2.entries())
				if(e1.getKey().equals(e2.getKey()) && !e1.getValue().equals(e2.getValue())) {
					lista.addLast(e2);
					cant++;
				}
			if(cant==0)
				try {
					lista.remove(lista.last());
				}catch (InvalidPositionException | EmptyListException e) {e.printStackTrace();}
			cant=0;
		}
		return lista;
	}
	
	public static <K,V> void acomodar(Map<K,V> m1) {
		Dictionary<V,K> dAux = new DiccionarioConLista<V,K>();
		try {
			for(TDAMapeo.Entry<K, V> e : m1.entries()) {
				dAux.insert(e.getValue(), e.getKey());
			}
			for(K e : m1.keys()) {
				TDADiccionario.Entry<V,K> f =  dAux.find(m1.remove(e));
				m1.put(f.getValue(), f.getKey());
			}
		}catch(TDADiccionario.InvalidKeyException | InvalidKeyException e) {}
	}
	
	public static <K,V> void acomodar2(Map<K,V> m1) {
		Map<V,K> mapAux = new MapeoConLista<V,K>();
		try {
			for(K c : m1.keys()) {
				V v = m1.remove(c);
				mapAux.put(v, c);
			}
			for(V c : mapAux.keys()) {
				K v = mapAux.remove(c);
				m1.put(v, c);
			}
		}catch(InvalidKeyException e) {}
	}
	
	
	public static <K,V> Map<V,K> crear_mapeo_inverso(Map<K,V> m1){ //Si hay mas claves repetidas simplemente se sobreescriben y listo, no hay otra forma de preservarlas ya que no es un diccionario
		Map<V,K> m2 = new MapeoConLista<V,K>();
		for(TDAMapeo.Entry<K, V> e : m1.entries()) {
			try {
				m2.put(e.getValue(), e.getKey());
			} catch (InvalidKeyException e1) {e1.printStackTrace();}
		}
		return m2;
	}
	public static Dictionary<Integer,String> convertir_a_diccionario(Map<String,Integer> m1){
		Dictionary<Integer,String> d = new DiccionarioHashAbierto<Integer,String>();
		for(TDAMapeo.Entry<String,Integer> e : m1.entries()) {
			try {
				d.insert(e.getValue(), e.getKey());
			} catch (TDADiccionario.InvalidKeyException e1) {e1.printStackTrace();}
		}
		return d;
	}
	public static <K,V> Dictionary<K,V> acomodarD(Dictionary<K,V> d){
		Dictionary<K,V> acomodado = new DiccionarioConLista<K,V>();
		Map<K,V> aux = new MapeoConLista<K,V>();
		try {
			for(TDADiccionario.Entry<K, V> e : d.entries()) {
				aux.put(e.getKey(), e.getValue());
			}
			for(TDAMapeo.Entry<K, V> e : aux.entries())
				acomodado.insert(e.getKey(), e.getValue());
		}catch(TDADiccionario.InvalidKeyException | InvalidKeyException e) {e.printStackTrace();}
		return acomodado;
	}
	
	private static <K,V> void ToString(PositionList<TDAMapeo.Entry<K,V>> l) {
		for(TDAMapeo.Entry<K,V> e : l) {
			System.out.print("("+e.getKey()+","+e.getValue()+")"+" ");
		}
	}
	private static <K,V> void ToString(Map<K,V> m) {
		for(TDAMapeo.Entry<K,V> e : m.entries()) {
			System.out.print("("+e.getKey()+","+e.getValue()+")"+" ");
		}
	}
	private static <K,V> void ToString(Dictionary<K,V> d) {
		for(TDADiccionario.Entry<K,V> e : d.entries()) {
			System.out.print("("+e.getKey()+","+e.getValue()+")"+" ");
		}
	}
	public static <K,V> Dictionary<K,V> sinRepetidos(Dictionary<K,V> d){
		Dictionary<K,V> sinR = new DiccionarioConLista<K,V>();
		Map<V,K> mapeo = new MapeoConLista<V,K>();
		try {
			for(TDADiccionario.Entry<K, V> entrada : d.entries())
				mapeo.put(entrada.getValue(),entrada.getKey());
			for(TDAMapeo.Entry<V, K> entrada : mapeo.entries())
				sinR.insert(entrada.getValue(), entrada.getKey());
		}catch(TDAMapeo.InvalidKeyException | TDADiccionario.InvalidKeyException e) {}
		return sinR;
	}
	
	public static void main(String[] args) {
		Map<String,Integer> m1 = new MapeoConLista<String,Integer>();
		Map<String,Integer> m2 = new MapeoConLista<String,Integer>();
		Dictionary<Integer,String> d1 = new DiccionarioConLista<Integer,String>();
		try {
			d1.insert(1, "a");
			d1.insert(2, "b");
			d1.insert(3, "a");
			d1.insert(2, "c");
			d1.insert(1, "d");
			d1.insert(4, "b");
			d1.insert(1, "e");
			ToString(d1);
			System.out.println();
			ToString(sinRepetidos(d1));
			
			m1.put("hola", 1);
			m1.put("hola", 2);
			m1.put("mahuevo", 1);
			m1.put("roman", 1);
			m1.put("pomarola", 2);
			//ToString(m1);
			
			
			//ToString(crear_mapeo_inverso(m1));
			//ToString(convertir_a_diccionario(m1));
			
			m2.put("hola", 1);
			m2.put("hola", 2);
			m2.put("mahuevo", 1);
			m2.put("roman", 2);
			m2.put("gino", 2);
			//acomodar(m2);
			//ToString(contiene_claves_y_valores_diferentes(m1,m2));
			//System.out.println(contiene_claves(m1,m2));
		} catch (InvalidKeyException | TDADiccionario.InvalidKeyException e) {e.printStackTrace();}
		
	}

}
