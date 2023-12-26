package Operaciones;

import TDAArbolBinario.ArbolBinarioEnlazado;
import TDAArbolBinario.BinaryTree;
import TDAArbolBinario.BoundaryViolationException;
import TDAArbolBinario.EmptyTreeException;
import TDAArbolBinario.InvalidOperationException;
import TDAArbolBinario.InvalidPositionException;
import TDAArbolBinario.Position;
import TDACola.Cola_con_enlaces;
import TDACola.EmptyQueueException;
import TDACola.Queue;
import TDAColaCP.ColaCPconArreglo;
import TDAColaCP.ColaCPconHeap;
import TDAColaCP.ColaCPconLista;
import TDAColaCP.EmptyPriorityQueueException;
import TDAColaCP.PriorityQueue;
import TDAMapeo.Entry;
import TDAMapeo.InvalidKeyException;
import TDAMapeo.Map;
import TDAMapeo.MapeoHashAbierto;

public class HuffmanClase {
	
	public static BinaryTree<Character> Huffman(String x){
		Map<Character,Integer> f = computarFrecuencia(x);
		Map<Character,Boolean> usados = new MapeoHashAbierto<Character,Boolean>();
		PriorityQueue<Integer,BinaryTree<Character>> Q = new ColaCPconLista<Integer,BinaryTree<Character>>();
		BinaryTree<Character> ArbolRetornar = new ArbolBinarioEnlazado<Character>();
		try {
			for(int i = 0; i<x.length(); i++) {
				if(usados.get(x.charAt(i))==null) {
					BinaryTree<Character> T = new ArbolBinarioEnlazado<Character>();
					T.createRoot(x.charAt(i));
					Q.insert(f.get(x.charAt(i)), T);
					usados.put(x.charAt(i), true);
				}
			}
			while(Q.size()>1) {
				Integer f1 = Q.min().getKey();
				BinaryTree<Character> T1 = Q.removeMin().getValue();
				Integer f2 = Q.min().getKey();
				BinaryTree<Character> T2 = Q.removeMin().getValue();
				BinaryTree<Character> T = new ArbolBinarioEnlazado<Character>();
				System.out.println("f1: "+f1+", T1: "+T1.root().element()+" | f2: "+f2+", T2: "+T2.root().element());
				T.createRoot('#');
				T.attach(T.root(), T1, T2);		//Asi no da el arbol del ejemplo ni a palos.
				Q.insert(f1+f2, T);
				//System.out.println(Q.min().getKey()+": ");
				//por_niveles(Q.min().getValue());
			}
			ArbolRetornar = Q.removeMin().getValue();
		}catch (InvalidOperationException | TDAColaCP.InvalidKeyException | InvalidKeyException | EmptyPriorityQueueException | InvalidPositionException | EmptyTreeException e) {e.printStackTrace();}	
		return ArbolRetornar;
	}
	private static <E> Map<Character,Integer> computarFrecuencia(String x) {
		Map<Character,Integer> f = new MapeoHashAbierto<Character,Integer>();
		for(int i = 0; i<x.length(); i++) 
			try {
				Integer cant = f.get(x.charAt(i));
				if(cant==null) 
					f.put(x.charAt(i), 1);
				else
					f.put(x.charAt(i), cant+1);
			} catch (InvalidKeyException e) {e.printStackTrace();}
		return f;
	}
	private static <E> void decode(BinaryTree<Character> T, Position<Character> actual, String d, Map<Character,String> codigos) {
		try {
			if(T.isExternal(actual)) {
				codigos.put(actual.element(), d);
				System.out.println(actual.element()+":"+d);
				return;
			}
			decode(T,T.left(actual),d+"0",codigos);
			decode(T,T.right(actual),d+"1",codigos);
		} catch (InvalidPositionException | BoundaryViolationException | InvalidKeyException e) {e.printStackTrace();}
	}
	private static <E> String generarString(Map<Character,String> codigos, String x) {
		String codigo = "";
		System.out.println(x);
		for(int i = 0; i<x.length(); i++) 
			try {
				codigo += codigos.get(x.charAt(i));
				//System.out.println(x.charAt(i)+": "+codigos.get(x.charAt(i)));
			} catch (InvalidKeyException e) {e.printStackTrace();}
		return codigo;
	}
	public static <E> String comprimirString(String x) {
		BinaryTree<Character> T  = Huffman(x);
		Map<Character,String> codigos = new MapeoHashAbierto<Character,String>();
		try {
			decode(T,T.root(),"",codigos);
		} catch (EmptyTreeException e) {e.printStackTrace();}
		return generarString(codigos,x);
	}
	private static <E> void por_niveles(BinaryTree<E> t) {
		Queue<Position<E>> Cola = new Cola_con_enlaces<Position<E>>();
		Position<E> n;
		try {
			Cola.enqueue(t.root());
			Cola.enqueue(null);
			while(!Cola.isEmpty()) {
				n = Cola.dequeue();
				if(n!=null) {
					System.out.print(n.element()+" ");
					if(t.hasLeft(n))
						Cola.enqueue(t.left(n));
					if(t.hasRight(n))
						Cola.enqueue(t.right(n));
				}
				else {
					System.out.println();
					if (!Cola.isEmpty())
						Cola.enqueue(null);
				}
			}
		} catch (EmptyTreeException | EmptyQueueException | InvalidPositionException | BoundaryViolationException e) {e.printStackTrace();}
		
	}
	public static <E> String decodificar(BinaryTree<Character> T, String x) {
		String decodificado = "";
		if(!T.isEmpty())
			try {
				Position<Character> posActual = T.root();
				for(int i = 0; i<x.length(); i++) {
					if(x.charAt(i)=='0')
						posActual = T.left(posActual);
					else
						posActual = T.right(posActual);
					if(posActual.element()!='#') { 		//Si el caracter es distinto del caracter que se usa para identificar nodos "intermedios"
						decodificado += posActual.element();
						posActual = T.root();
					}
				}
			} catch (EmptyTreeException | InvalidPositionException | BoundaryViolationException e) {e.printStackTrace();}
		return decodificado;
	}
	public static void main(String[] args) throws InvalidPositionException, BoundaryViolationException, EmptyTreeException {
		//System.out.println(comprimirString("bcaadddccacacac"));
		System.out.println(decodificar(Huffman("bcaadddccacacac"),"1000111110110110100110110110"));
		String s = "";
		for(int i = 0; i<45; i++) 
			s += 'f';
		for(int i = 0; i<16; i++) 
			s += 'e';
		for(int i = 0; i<13; i++)
			s += 'd';
		for(int i = 0; i<12; i++)
			s += 'c';
		for(int i = 0; i<9; i++)
			s += 'b';
		for(int i = 0; i<5; i++)
			s += 'a';
		//System.out.println(comprimirString();
		//BinaryTree<Character> T  = Huffman("aaaaaabbbaaaaaccd");
		//System.out.println(T.left(T.left(T.root())).element());
	}

}
