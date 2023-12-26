package Operaciones;

public class Pair<K, V> {
	protected K key;
	protected V value;

	public Pair(K k, V v) {
		key = k;
		value = v;
	}

	public void setKey(K k) {
		key = k;
	}

	public void setValue(V v) {
		value = v;
	}

	public K getKey() {
		return key;
	}

	public V getValue() {
		return value;
	}

	public String toString() {
		return "(" + getKey() + " , " + getValue() + ")";
	}
}
