package Logica;

import TDADiccionario.Entry;
import TDADiccionario.*;
import TDAPila.*;
import TDALista.ListaDE;
import TDALista.PositionList;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

import TDAArbol.*;
import TDAMapeo.*;
import TDAMapeo.InvalidKeyException;


/**
 * Metodos desarrollados para la reentrega del Proyecto N2 y la reentrega parcial del Proyecto N1.
 * @author Joaquin Garcia Diotto
 *
 */
public class Logica {
	
	/**
	 * A partir de una ruta a un archivo de texto se realiza una correspondencia en: un mapeo entre las palabras y la cantidad de veces que aparecen en el archivo,
	 * y en un diccionario entre las iniciales del archivo y las palabras con dichas iniciales.
	 * @param path Representa la ruta absoluta a un archivo de texto
	 * @return Retorna un par de elementos, un mapeo indicando la cantidad de veces que aparece cada palabra en el archivo, 
	 * y un diccionario indicando las palabras que aparecen con cada inicial del texto.
	 */
	public static Pair<Map<String, Integer>, Dictionary<Character, String>> estadisticas(String path) {
		Map<String,Integer> m = new MapeoHashAbierto<String,Integer>();
		Dictionary<Character,String> d = new DiccionarioHashCerrado<Character,String>();
		Pair<Map<String, Integer>, Dictionary<Character, String>> par = null;
		String linea = "", palabra;
		char inicial;
		int i = 0; Integer value;
		Iterable<Entry<Character,String>> entradasConInicial;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(path));
			linea = reader.readLine();
			while(linea != null) {												
				palabra = "";
				while(i<linea.length()) {
					inicial = linea.charAt(i);
					while(i<linea.length() && linea.charAt(i) != ' ') {							//Consumo una palabra de una linea y la guardo
						palabra += linea.charAt(i);
						i++;
					}
					value = m.get(palabra);															//La inserto como corresponde en el mapeo
					if(value==null) 
						m.put(palabra, 1);
					 else {
						value++;
						m.put(palabra, value);
					}																//La inserto como corresponde en el diccionario
					entradasConInicial = d.findAll(inicial);														
					if(!pertenece(palabra,entradasConInicial))
						d.insert(inicial, palabra);
					i++;
					palabra = "";	
				}
				i=0;
				linea = reader.readLine();
			}
			reader.close();
		} catch (IOException | InvalidKeyException | TDADiccionario.InvalidKeyException e) {}
		par = new Pair<Map<String, Integer>, Dictionary<Character, String>>(m,d);
		return par;
	}
	
	/**
	 * Decide si una palabra existe en una coleccion de palabras.
	 * @param palabra Palabra a verificar si pertenece.
	 * @param entradasConInicial Coleccion iterable de palabras.
	 * @return Retorna verdadero si la palabra pertenece a la coleccion de palabras, falso en caso contrario.
	 */
	private static boolean pertenece(String palabra, Iterable<Entry<Character,String>> entradasConInicial) {
		boolean pertenece = false; 
		Iterator<Entry<Character,String>> it = entradasConInicial.iterator();
		while(!pertenece && it.hasNext())
			pertenece = palabra.contentEquals(it.next().getValue());
		return pertenece;
	}
	
	/**
	 * Encuentra el camino entre dos posiciones en un arbol.
	 * @param <E> Tipo de elemento utilizado por el metodo.
	 * @param t1 Arbol en el que buscar el camino.
	 * @param p1 Primera posicion del arbol.
	 * @param p2 Segunda posicion del arbol.
	 * @return Retorna una lista indicando el camino desde la primera posicion hacia la segunda posicion.
	 */
	
	public static <E> PositionList<Position<E>> camino(Tree<E> t1, Position<E> p1, Position<E> p2){
		Stack<Position<E>> camino1 = new Pila_Con_Enlaces<Position<E>>();
		Stack<Position<E>> camino2 = new Pila_Con_Enlaces<Position<E>>();
		PositionList<Position<E>> lista = new ListaDE<Position<E>>();
		Position<E> ancestroComun = null, e1,e2; 
		boolean termine = false;
		try {
			if(p1!=null && p2!=null && t1!=null && !t1.isEmpty() && buscarCamino(t1,p1,camino1) && buscarCamino(t1,p2,camino2)) {		//Me fijo que todo este en "buenas condiciones"
				while(!termine) {																										//Recorro las dos pilas al mismo tiempo, 	
					e1 = camino1.pop();																									//hasta que llego al final de alguna o si los elementos encontrados son distintos.
					e2 = camino2.pop();
					if(e1.equals(e2))
						ancestroComun = e1;															//Voy guardando el ultimo elemento de ambas pilas para no perderlo, y cuando termine de iterar sera el primer ancestro comun entre las dos pilas.
					else {
						camino1.push(e1);
						camino2.push(e2);
					}
					termine = !e1.equals(e2) || camino1.isEmpty() || camino2.isEmpty();
				}
				if(!camino1.isEmpty() || !camino2.isEmpty())
					camino1.push(ancestroComun);
				while(!camino2.isEmpty())														//Ahora solo queda volcar el resto de los elementos del segundo camino a el camino definitivo.
					camino1.push(camino2.pop());
				while(!camino1.isEmpty())										
					lista.addFirst(camino1.pop());
			}
		} catch (EmptyStackException e) {}
		return lista;
	}
	
	/**
	 * Decide si existe un camino entre una posicion y el nodo raiz del arbol.
	 * @param <E> Tipo de dato utilizado por el metodo.
	 * @param t1 Arbol en el que buscar el camino.
	 * @param p1 Posicion desde la cual empezar a buscar el camino.
	 * @param camino Pila la cual sera modificada con el correspondiente camino.
	 * @return Retorna verdadero si existe tal camino; falso en caso contrario.
	 */
	private static <E> boolean buscarCamino(Tree<E> t1, Position<E> p1, Stack<Position<E>> camino) { 
		boolean existe = true;
	    try { 
	    	if(p1==null) 			//Si resulta ser que la posicion utilizada no pertenece al arbol, entonces p1 en algun momento va a llegar a un padre null y como en la clase del arbol se compara por referencia, entonces no se va a lanzar la excepcion.
	    		existe = false;
	    	else { 					//En caso de que pertenezca al arbol, este seria el camino normal que deberia recorrer y solo tengo que chequear que no llegue a la raiz para evitar producir una excepcion.
	    		camino.push(p1);
	    		if(!t1.isRoot(p1))
	    			existe = existe && buscarCamino(t1,t1.parent(p1),camino);
	    	}
	    }catch(InvalidPositionException | BoundaryViolationException e) {}
	    return existe;
	}
	
	
	/**
	 * A partir de un arbol, crea otro el cual tendra todos sus nodos de forma opuesta o espejada al del arbol original.
	 * @param <E> Tipo de dato utilizado por el metodo.
	 * @param t1 Arbol a clonar de forma espejada.
	 * @return Retorna un nuevo arbol con los nodos del arbol pasado como parametro pero de forma que esten espejados con respecto al arbol original.
	 */
	public static <E> Tree<E> clonar_espejado(Tree<E> t1){
		Tree<E> t2 = new ArbolEnlazado<E>();
		try {
			if(t1!=null && !t1.isEmpty()) {
				t2.createRoot(t1.root().element());
				clonarAux(t1, t2, t1.root(),t2.root());
			}
		} catch (EmptyTreeException | InvalidOperationException e) {}
		return t2;
	}
	
	/**
	 * Metodo privado auxiliar que permite clonar de forma espejada un arbol.
	 * @param <E> Tipo de dato utilizado por el metodo.
	 * @param t1 Arbol original a clonar espejado.
	 * @param t2 Arbol nuevo el cual sera modificado de forma que quede espejado.
	 * @param nodo Nodo el cual recorrera todo el arbol original, comenzando desde la raiz.
	 * @param padreT2  Nodo el cual servira como guia para ir agregando los nodos hijos en el arbol a clonar, comenzando desde la raiz.
	 */
	private static <E> void clonarAux(Tree<E> t1, Tree<E> t2, Position<E> nodo, Position<E> padreT2){
		try { 
			Position<E> hijo;
			for(Position<E> n : t1.children(nodo)) {
				hijo = t2.addFirstChild(padreT2, n.element());
				clonarAux(t1,t2,n,hijo);
			}
		} catch (InvalidPositionException e) {}
	}
	
	/**
	 * Intercala dos Listas previamente ordenadas ascendentemente de forma tal que la lista resultante quede ordenada descendentemente sin repeticiones.
	 * @param l1 Primera lista ordenada ascendentemente que se desea intercalar.
	 * @param l2 Segunda lista ordenada ascendentemente que se desea intercalar.
	 * @return Retorna una nueva lista ordenada de forma descendente sin repeticiones.
	 */
	public static PositionList<Integer> intercalar(PositionList<Integer> l1, PositionList<Integer> l2){
		PositionList<Integer> l3 = new ListaDE<Integer>();
		TDALista.Position<Integer> A1, A2, eInsertar;
		try {
			A1 = (l1==null || l1.isEmpty())? null : l1.first();
			A2 = (l2==null || l2.isEmpty())? null : l2.first();
			while (A1 != null && A2 != null) { 											//Consumo ambas listas hasta que se acabe cualquiera de las dos. 
				if(A1.element()<A2.element()) {											//Agrego el que sea menor, siempre y cuando sea mayor al primer elemnto de la lista a retornar.
					eInsertar = A1;
					A1 = (A1==l1.last())? null : l1.next(A1);
					if(l3.isEmpty() || eInsertar.element()>l3.first().element())
						l3.addFirst(eInsertar.element());
				} 
				else if(A2.element()<A1.element()) {
					eInsertar = A2;
					A2 = (A2==l2.last())? null : l2.next(A2);
					if(l3.isEmpty() || eInsertar.element()>l3.first().element())
						l3.addFirst(eInsertar.element());
				} 
				else {																	//Agrego siempre los elementos de la lista l1 si son iguales, por lo tanto, tambien se debe consumir la lista l2 ya que ese elemento ya fue "checkeado" y no deberia repetirse.
					eInsertar = A1;
					A2 = (A2==l2.last())? null : l2.next(A2);
					A1 = (A1==l1.last())? null : l1.next(A1);
					if(l3.isEmpty() || eInsertar.element()>l3.first().element())
						l3.addFirst(eInsertar.element());
				}														
			}								
			while(A1 != null) {															//Agrego los elementos restantes de la lista que haya faltado recorrer por completo.
				if(l3.isEmpty() || A1.element()>l3.first().element())
					l3.addFirst(A1.element());
				A1 = (A1==l1.last())? null : l1.next(A1);
			}
			while(A2 != null) {
				if(l3.isEmpty() || A2.element()>l3.first().element())
					l3.addFirst(A2.element());
				A2 = (A2==l2.last())? null : l2.next(A2);
			}
		}catch(TDALista.EmptyListException | TDALista.InvalidPositionException | TDALista.BoundaryViolationException e) {}
		return l3;
	}
}
