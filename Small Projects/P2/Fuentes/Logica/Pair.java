package Logica;

/**
 * Modela un par de elementos genericos.
 * @author Joaquin Garcia Diotto
 *
 * @param <A> Tipo de dato del primer elemento del par.
 * @param <B> Tipo de dato del segundo elemento del par.
 */
public class Pair<A, B> {
	protected A valorA;
	protected B valorB;
	
	/*
	 * Crea un par de elementos.
	 */
	public Pair(A a, B b) {
		valorA = a;
		valorB = b;
	}
	
	/**
	 * Metodo para obtener el primer elemento del par.
	 * @return Retorna el primer elemento del par.
	 */
	public A getA() {
		return valorA;
	}
	
	/**
	 * Metodo para obtener el segundo elemento del par.
	 * @return Retorna el segundo elemento del par.
	 */
	public B getB() {
		return valorB;
	}
}
