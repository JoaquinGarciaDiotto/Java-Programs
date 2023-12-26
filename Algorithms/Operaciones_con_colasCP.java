package Operaciones;
import TDAColaCP.*;
import TDADiccionario.Dictionary;
import TDALista.ListaDE;
import TDALista.PositionList;

public class Operaciones_con_colasCP{

	private static PriorityQueue<Integer,Integer> cargarCola(PriorityQueue<Integer,Integer> c) {
		try {
			for(int i = 1; i<8; i++){
			    c.insert(i, null);
			}
		}catch(InvalidKeyException e) {e.printStackTrace();}
		return c;
	}
	
	public static void mostrar_secuencia_ascendente() {
		PriorityQueue<Integer,Integer> c = cargarCola(new ColaCPconLista<Integer,Integer>());
		while(!c.isEmpty()) {
			try {
				System.out.print(c.removeMin().getKey()+" ");
			} catch (EmptyPriorityQueueException e) {e.printStackTrace();}
		}
	}
	public static void mostrar_secuencia_descendente() {
		PriorityQueue<Integer,Integer> c = cargarCola(new ColaCPconLista<Integer,Integer>());
		mostrarSecDescAux(c.size(),c);
	}
	private static void mostrarSecDescAux(int t, PriorityQueue<Integer,Integer> c) {
		if(t>0)
			try {
				Entry<Integer,Integer> e1 = c.removeMin();
				mostrarSecDescAux(t-1,c);
				System.out.print(e1.getKey()+" ");
			} catch (EmptyPriorityQueueException e1) {e1.printStackTrace();}
	}
	
	public static void mostrar_secuencia_especial() {
		PriorityQueue<Integer,Integer> c = cargarCola(new ColaCPconLista<Integer,Integer>(new Comparador_especial()));
		mostrarSecDescAux(c.size(),c);
	}
	public static <E> void ordenar_arreglo(E[] A) {
		PriorityQueue<E,E> C = new ColaCPconLista<E,E>();
		E e = null;
		try {
			for(int i = 0; i<A.length; i++) {
				e = A[i];
				C.insert(e,e);
				A[i] = null;
			}
			for(int i = 0; i<A.length; i++)  //A que se refiere con FirstIn-FirstOut?? El primero en salir de la cola es el primero en entrar al arreglo???
				A[i] = C.removeMin().getValue();
		} catch (InvalidKeyException | EmptyPriorityQueueException e1) {e1.printStackTrace();	}
	}
	public static PositionList<Alumno> mejoresAlumnos(PositionList<Alumno> a, int k){
		PositionList<Alumno> l = new ListaDE<Alumno>();
		PriorityQueue<Float,Alumno> alumnos = new ColaCPconHeap<Float,Alumno>();
		try {
			for(Alumno al : a) 
				alumnos.insert(-obtenerPromedio(al), al);
			while(k>0) {
				l.addLast(alumnos.removeMin().getValue());
				k--;
			}
		} catch (InvalidKeyException | EmptyPriorityQueueException e) {e.printStackTrace();}
		return l;
	}
	private static Float obtenerPromedio(Alumno a) {
		Float promedio = 0f;
		for(ExamenFinal e : a.getFinales())
			promedio += e.getNota();
		if(!a.getFinales().isEmpty())
			promedio /= a.getFinales().size();
		return promedio;
	}
	public int[] valOrdenados(Dictionary<Character,Integer> d) {
		int[] valores = new int[d.size()];
		PriorityQueue<Integer,Integer> q = new ColaCPconHeap<Integer,Integer>();
		try {
			for(TDADiccionario.Entry<Character, Integer> e : d.entries())
				q.insert(e.getValue(), e.getValue());
			for(int i = 0; i<q.size(); i++) 
				valores[i] = q.removeMin().getValue();
		} catch (InvalidKeyException | EmptyPriorityQueueException e1) {e1.printStackTrace();}
		return valores;
	}
	public static <E> void toString(E[] a) {
		for(E e : a)
			System.out.print(e+" ");
	}
	public static void main(String[] args) {
		//System.out.println(cargarCola(new ColaCPconLista<Integer,Integer>()).toString());
		//mostrar_secuencia_descendente();
		//mostrar_secuencia_especial();
		//2 4 6 7 5 3 1
		Integer[]A = {9,2,6,1,8,3,4,5,7,0};
		ordenar_arreglo(A);
		toString(A);
	}
	
	
}
