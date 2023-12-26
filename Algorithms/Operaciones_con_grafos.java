package Operaciones;

import java.util.Iterator;

import TDACola.Cola_con_enlaces;
import TDACola.EmptyQueueException;
import TDACola.Queue;
import TDAGrafo.Edge;
import TDAGrafo.GrafoDlistaA;
import TDAGrafo.GrafoMatrizA;
import TDAGrafo.Graph;
import TDAGrafo.GraphD;
import TDAGrafo.InvalidEdgeException;
import TDAGrafo.InvalidVertexException;
import TDAGrafo.Vertex;
import TDALista.EmptyListException;
import TDALista.InvalidPositionException;
import TDALista.ListaDE;
import TDALista.Position;
import TDALista.PositionList;
import TDAMapeo.Entry;
import TDAMapeo.InvalidKeyException;
import TDAMapeo.Map;
import TDAMapeo.MapeoConLista;
import TDAMapeo.MapeoHashAbierto;

public class Operaciones_con_grafos {

	public static <V, E> void dfs(Graph<V, E> g) {
		Map<Vertex<V>, Boolean> m = new MapeoHashAbierto<Vertex<V>, Boolean>();
		try {
			for (Vertex<V> v : g.vertices())
				m.put(v, false);
			for (Vertex<V> v : g.vertices())
				if (!m.get(v))
					dfsAux(g, v, m);
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		}
	}

	private static <V, E> void dfsAux(Graph<V, E> g, Vertex<V> v, Map<Vertex<V>, Boolean> m) {
		System.out.println(v.element());
		try {
			m.put(v, true);
			for (Edge<E> a : g.incidentEdges(v)) {
				Vertex<V> w = g.opposite(v, a);
				if (!m.get(w))
					dfsAux(g, w, m);
			}
		} catch (InvalidKeyException | InvalidVertexException | InvalidEdgeException e) {
			e.printStackTrace();
		}
	}

	public static <V, E> void bfs(Graph<V, E> g) {
		Map<Vertex<V>, Boolean> m = new MapeoConLista<Vertex<V>, Boolean>();
		try {
			for (Vertex<V> v : g.vertices())
				m.put(v, false);
			for (Vertex<V> v : g.vertices())
				if (!m.get(v))
					bfsAux(g, v, m);
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		}
	}

	private static <V, E> void bfsAux(Graph<V, E> g, Vertex<V> v, Map<Vertex<V>, Boolean> m) {
		Queue<Vertex<V>> q = new Cola_con_enlaces<Vertex<V>>();
		q.enqueue(v);
		try {
			m.put(v, true);
			while (!q.isEmpty()) {
				Vertex<V> u = q.dequeue();
				System.out.println(u.element());
				for (Edge<E> a : g.incidentEdges(u)) {
					Vertex<V> x = g.opposite(u, a);
					if (!m.get(x)) {
						m.put(x, true);
						q.enqueue(x);
					}
				}
			}
		} catch (InvalidKeyException | EmptyQueueException | InvalidVertexException | InvalidEdgeException e) {
			e.printStackTrace();
		}
	}

	public static <V, E> PositionList<Vertex<V>> camino_corto(Graph<V, E> g, Vertex<V> a, Vertex<V> b) { // Peor caso,
																											// un grafo
																											// con n
																											// nodos y
																											// m=n-1
																											// arcos el
																											// cual es
																											// una lista
																											// y ambos
																											// nodos son
																											// el inicio
																											// y el
																											// final.
		PositionList<Vertex<V>> camino = new ListaDE<Vertex<V>>(); // T(n,m)=O(n)
		Map<Vertex<V>, Boolean> m = new MapeoConLista<Vertex<V>, Boolean>();
		Map<Vertex<V>, Vertex<V>> previo = new MapeoConLista<Vertex<V>, Vertex<V>>();
		Vertex<V> x = b;
		try {
			for (Vertex<V> v : g.vertices()) // ---->c*n
				m.put(v, false);
			if (BFSSearch(g, a, b, previo, m)) { // TBFSSearch(n,m) + c*n
				while (x != null) {
					camino.addFirst(x);
					x = previo.get(x);
				}
			}
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		}
		return camino;
	}

	private static <V, E> boolean BFSSearch(Graph<V, E> g, Vertex<V> s, Vertex<V> t, Map<Vertex<V>, Vertex<V>> previo,
			Map<Vertex<V>, Boolean> m) {
		boolean existe = false;
		Queue<Vertex<V>> q = new Cola_con_enlaces<Vertex<V>>();
		q.enqueue(s);
		try {
			m.put(s, true);
			while (!q.isEmpty() && !existe) { // Realiza n iteraciones
				Vertex<V> x = q.dequeue();
				if (x == t)
					existe = true;
				else
					for (Edge<E> a : g.incidentEdges(x)) { // Realiza solo 2 iteraciones al ser el grafo una lista
						Vertex<V> v = g.opposite(x, a);
						if (!m.get(v)) {
							q.enqueue(v);
							m.put(v, true);
							previo.put(v, x);
						}
					}
			}
		} catch (InvalidKeyException | EmptyQueueException | InvalidVertexException | InvalidEdgeException e) {
		}
		return existe;
	}

	public static <V, E> PositionList<Vertex<V>> camino_economico(Graph<V, Integer> g, Vertex<V> a, Vertex<V> b) { // Peor
																													// caso,
																													// en
																													// el
																													// grafo,
																													// todos
																													// los
																													// nodos
																													// estan
																													// conectados
																													// con
																													// todos
																													// T(n)
																													// =
																													// n!
		PositionList<Vertex<V>> camino = new ListaDE<Vertex<V>>();
		PositionList<Vertex<V>> caminoActual = new ListaDE<Vertex<V>>();
		Map<Vertex<V>, Boolean> m = new MapeoConLista<Vertex<V>, Boolean>();
		for (Vertex<V> v : g.vertices()) // n iteraciones
			try {
				m.put(v, false);
			} catch (InvalidKeyException e) {
				e.printStackTrace();
			}
		camino = caminoMinimo(g, a, b, caminoActual, 0, camino, new Solucion(), m); // T(n,m) = (n-1)! <= n!
		return camino;
	}

	private static <V, E> PositionList<Vertex<V>> caminoMinimo(Graph<V, Integer> g, Vertex<V> origen, Vertex<V> destino,
			PositionList<Vertex<V>> caminoActual, Integer costoCaminoActual, PositionList<Vertex<V>> caminoMinimo,
			Solucion costoCaminoMinimo, Map<Vertex<V>, Boolean> m) {
		try {
			m.put(origen, true);
			caminoActual.addLast(origen);
			if (origen.equals(destino)) {
				if (costoCaminoMinimo.getCosto() == null || costoCaminoActual < costoCaminoMinimo.getCosto()) {
					caminoMinimo = clonado(caminoActual);
					costoCaminoMinimo.setCosto(costoCaminoActual);
				}
			} else
				for (Edge<Integer> a : g.incidentEdges(origen)) { // Se recorren los n-1 nodos, y despue en cada uno de
																	// esos n-1 nodos, se recorren n-2 nodos, es decir,
																	// toma tiempo (n-1)*(n-2) y asi hasta llegar a 1,
																	// entonces --> (n-1)!
					Vertex<V> v = g.opposite(origen, a);
					if (!m.get(v))
						caminoMinimo = caminoMinimo(g, v, destino, caminoActual, costoCaminoActual + a.element(),
								caminoMinimo, costoCaminoMinimo, m);
				}
			caminoActual.remove(caminoActual.last());
			m.put(origen, false);
		} catch (InvalidKeyException | InvalidVertexException | InvalidEdgeException | InvalidPositionException
				| EmptyListException e) {
			e.printStackTrace();
		}
		return caminoMinimo;
	}

	private static <V, E> PositionList<Vertex<V>> clonado(PositionList<Vertex<V>> l) {
		ListaDE<Vertex<V>> clon = new ListaDE<Vertex<V>>();
		for (Position<Vertex<V>> v : l.positions()) {
			clon.addLast(v.element());
		}
		return clon;
	}

	static private class Solucion {
		Integer costo;

		public Solucion() {
			costo = null;
		}

		public void setCosto(Integer c) {
			costo = c;
		}

		public Integer getCosto() {
			return costo;
		}
	}

	public static <V, E> PositionList<PositionList<Vertex<V>>> caminos(Graph<V, E> g, Vertex<V> a, Vertex<V> b) { // T(n,m)
																													// =
																													// n!.
																													// Visito
																													// n-1
																													// nodos,
																													// y
																													// en
																													// todos
																													// esos
																													// n-1
																													// nodos,
																													// paso
																													// a
																													// visitar
																													// n-2
																													// nodos
																													// y
																													// asi
																													// siguiendo.
		PositionList<PositionList<Vertex<V>>> caminos = new ListaDE<PositionList<Vertex<V>>>();
		Map<Vertex<V>, Boolean> m = new MapeoConLista<Vertex<V>, Boolean>();
		PositionList<Vertex<V>> caminoActual = new ListaDE<Vertex<V>>();
		for (Vertex<V> v : g.vertices())
			try {
				m.put(v, false);
			} catch (InvalidKeyException e) {
				e.printStackTrace();
			}
		caminoActual.addLast(a);
		caminosAux(g, a, b, caminoActual, caminos, m);
		return caminos;
	}

	private static <V, E> void caminosAux(Graph<V, E> g, Vertex<V> a, Vertex<V> b, PositionList<Vertex<V>> caminoActual,
			PositionList<PositionList<Vertex<V>>> caminos, Map<Vertex<V>, Boolean> m) {
		if (a.equals(b)) {
			caminos.addLast(clonado(caminoActual));
		} else
			try {
				m.put(a, true);
				for (Edge<E> e : g.incidentEdges(a)) {
					Vertex<V> x = g.opposite(a, e);
					if (!m.get(x)) {
						caminoActual.addLast(x);
						Position<Vertex<V>> temp = caminoActual.last();
						caminosAux(g, x, b, caminoActual, caminos, m);
						caminoActual.remove(temp);
					}
				}
				m.put(a, false);
			} catch (InvalidVertexException | InvalidKeyException | InvalidEdgeException | EmptyListException
					| InvalidPositionException f) {
				f.printStackTrace();
			}
	}

	public static <V, E> PositionList<Vertex<V>> camino_largo(GraphD<V, E> g, Vertex<V> a, Vertex<V> b) { // n!
		PositionList<Vertex<V>> camino = new ListaDE<Vertex<V>>();
		PositionList<PositionList<Vertex<V>>> caminos = caminoLargoAux1(g, a, b);
		for (Position<PositionList<Vertex<V>>> p : caminos.positions()) {
			if (p.element().size() > camino.size())
				camino = p.element();
		}
		return camino;
	}

	private static <V, E> PositionList<PositionList<Vertex<V>>> caminoLargoAux1(GraphD<V, E> g, Vertex<V> a,
			Vertex<V> b) {
		PositionList<PositionList<Vertex<V>>> caminos = new ListaDE<PositionList<Vertex<V>>>();
		Map<Vertex<V>, Boolean> m = new MapeoConLista<Vertex<V>, Boolean>();
		PositionList<Vertex<V>> caminoActual = new ListaDE<Vertex<V>>();
		for (Vertex<V> v : g.vertices())
			try {
				m.put(v, false);
			} catch (InvalidKeyException e) {
				e.printStackTrace();
			}
		caminoActual.addLast(a);
		caminosLargoAux2(g, a, b, caminoActual, caminos, m);
		return caminos;
	}

	private static <V, E> void caminosLargoAux2(GraphD<V, E> g, Vertex<V> a, Vertex<V> b,
			PositionList<Vertex<V>> caminoActual, PositionList<PositionList<Vertex<V>>> caminos,
			Map<Vertex<V>, Boolean> m) {
		if (a.equals(b)) {
			caminos.addLast(clonado(caminoActual));
		} else
			try {
				m.put(a, true);
				for (Edge<E> e : g.succesorEdges(a)) {
					Vertex<V> x = g.opposite(a, e);
					if (!m.get(x)) {
						caminoActual.addLast(x);
						Position<Vertex<V>> temp = caminoActual.last();
						caminosLargoAux2(g, x, b, caminoActual, caminos, m);
						caminoActual.remove(temp);
					}
				}
				m.put(a, false);
			} catch (InvalidVertexException | InvalidKeyException | InvalidEdgeException | EmptyListException
					| InvalidPositionException f) {
				f.printStackTrace();
			}
	}

	public static <V, E> boolean lista_con_cabeza(Graph<V, E> g, Vertex<V> a) { // Peor caso, el grafo es una lista con
																				// la cabeza a. Entonces, m=n-1, ===>
																				// T(n,m) = O(n)
		boolean pertenece, es = true;
		int cant, i = cant = 0;
		Vertex<V> x;
		Iterator<Edge<E>> edgesIt;
		Iterator<Vertex<V>> vertexIt = g.vertices().iterator();
		pertenece = false;
		try {
			if (a != null) {
				while (vertexIt.hasNext()) {
					if (vertexIt.next() == a)
						pertenece = true;
					cant++;
				}
				if (pertenece) {
					i++;
					edgesIt = g.incidentEdges(a).iterator();
					while (es && edgesIt.hasNext()) {
						x = g.opposite(a, edgesIt.next());
						if (edgesIt.hasNext())
							es = false;
						else {
							i++;
							a = x;
							edgesIt = g.incidentEdges(a).iterator();
							if (edgesIt.hasNext())
								edgesIt.next();
						}
					}
				} else
					es = false;
			}
			if (i != cant)
				es = false;
		} catch (InvalidVertexException | InvalidEdgeException f) {
			f.printStackTrace();
		}
		return es;
	}

	public static <V, E> boolean es_arbol(Graph<V, E> g, Vertex<V> a) { // Peor caso, el grafo es un arbol, por lo tanto
																		// hay n nodos y m=n-1 arcos, ===> T(n,m) = O(n)
		boolean es = true;
		Map<Vertex<V>, Boolean> m = new MapeoConLista<Vertex<V>, Boolean>();
		try {
			Iterable<Vertex<V>> vertices = g.vertices();
			if (vertices.iterator().hasNext() && !vertices.iterator().next().equals(a))
				es = false;
			else {
				for (Vertex<V> v : vertices)
					m.put(v, false);
				if (tieneCiclos(g, a, null, m))
					es = false;
				else
					for (Vertex<V> v : vertices)
						if (!m.get(v))
							es = false;
			}
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		}
		return es;
	}

	private static <V, E> boolean tieneCiclos(Graph<V, E> g, Vertex<V> v, Vertex<V> p, Map<Vertex<V>, Boolean> m) {
		boolean tiene = false;
		try {
			m.put(v, true);
			Iterator<Edge<E>> it = g.incidentEdges(v).iterator();
			while (it.hasNext() && !tiene) {
				Vertex<V> x = g.opposite(v, it.next());
				if (!m.get(x)) {
					if (tieneCiclos(g, x, v, m))
						tiene = true;
				} else if (x.equals(p))
					tiene = true;
			}
		} catch (InvalidKeyException | InvalidVertexException | InvalidEdgeException e) {
			e.printStackTrace();
		}
		return tiene;
	}

	public static <V, E> void eliminar_incidente(Graph<V, E> g) { // Se recorren todos los nodos y todos los vertices.
																	// T(n,m)= n*(deg(v))
		int mayor = 0, actual = 0;
		Vertex<V> m = null;
		try {
			for (Vertex<V> v : g.vertices()) {
				for (Edge<E> e : g.incidentEdges(v))
					actual++;
				if (actual > mayor) {
					mayor = actual;
					m = v;
				}
				actual = 0;
			}
			if (m != null)
				g.removeVertex(m);
		} catch (InvalidVertexException f) {
			f.printStackTrace();
		}
	}

	private static <V> void toString(PositionList<Vertex<V>> l) {
		for (Position<Vertex<V>> v : l.positions())
			System.out.print(v.element().element() + " ");
	}

	public static Map<Integer, Integer> DFSMapeo(Graph<Integer, Integer> g, Vertex<Integer> v) {
		Map<Integer, Integer> mapeo = new MapeoHashAbierto<Integer, Integer>();
		Map<Vertex<Integer>, Boolean> visitados = new MapeoConLista<Vertex<Integer>, Boolean>();
		try {
			for (Vertex<Integer> vx : g.vertices())
				visitados.put(vx, false);
			dfsMapeoAux(g, v, mapeo, visitados);
			for (Vertex<Integer> vx : g.vertices())
				if (!visitados.get(vx)) {
					System.out.println("Rotulo VX: " + vx.element());
					dfsMapeoAux(g, vx, mapeo, visitados);
				}
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		}
		return mapeo;
	}

	private static Integer cant_arcos_impares(Graph<Integer, Integer> g, Vertex<Integer> v) {
		Integer cant = 0;
		try {
			for (Edge<Integer> e : g.incidentEdges(v))
				if (e.element() % 2 != 0)
					cant++;
		} catch (InvalidVertexException e) {
			e.printStackTrace();
		}
		return cant;
	}

	private static void dfsMapeoAux(Graph<Integer, Integer> g, Vertex<Integer> v, Map<Integer, Integer> mapeo,
			Map<Vertex<Integer>, Boolean> visitados) {
		try {
			mapeo.put(v.element(), cant_arcos_impares(g, v));
			visitados.put(v, true);
			for (Edge<Integer> a : g.incidentEdges(v)) {
				Vertex<Integer> w = g.opposite(v, a);
				if (!visitados.get(w))
					dfsMapeoAux(g, w, mapeo, visitados);
			}
		} catch (InvalidKeyException | InvalidVertexException | InvalidEdgeException e) {
			e.printStackTrace();
		}
	}

	private static void toString(Map<Integer, Integer> m) {
		for (Entry<Integer, Integer> e : m.entries()) {
			System.out.print("(Rotulo: " + e.getKey() + ", Adyacentes: " + e.getValue() + ") ");
		}
	}

	public <V, E> Vertex<V> primerVertice(GraphD<V, E> g, V r) {
		Vertex<V> aux, verticeR = null;
		Iterator<Vertex<V>> it = g.vertices().iterator();
		while (it.hasNext() && verticeR == null) {
			aux = it.next();
			if (aux.element().equals(r))
				verticeR = aux;
		}
		return verticeR;
	}

	public <V, E> void eliminarVertices(GraphD<V, E> g, V r) {
		for (Vertex<V> v : g.vertices())
			if (v.element().equals(r))
				try {
					g.removeVertex(v);
				} catch (InvalidVertexException e) {
					e.printStackTrace();
				}
	}

	public static <V, E> boolean lista_cabeza(GraphD<V, E> g, Vertex<V> a) { // Me fijo si es una lista simplemente
																				// enlazada de la forma:
																				// 4->5->2->1->7->0...etc, siendo en
																				// este caso a=4; //Fijarse que no funca
																				// cuando el ultimo tienen un arco a si
																				// mismo.
		boolean pertenece, es = true;
		int cant, i = cant = 0;
		Iterator<Vertex<V>> verticesIt = g.vertices().iterator();
		Vertex<V> v;
		Iterator<Edge<E>> edgesIt1, edgesIt2;
		pertenece = false;
		try {
			if (a != null) {
				while (verticesIt.hasNext()) {
					if (verticesIt.next() == a)
						pertenece = true;
					cant++;
				}
				if (pertenece) {
					edgesIt1 = g.incidentEdges(a).iterator();
					edgesIt2 = g.succesorEdges(a).iterator();
					i++;
					v = a;
					while (es && !edgesIt1.hasNext() && edgesIt2.hasNext()) {
						v = g.opposite(v, edgesIt2.next());
						if (edgesIt1.hasNext() || edgesIt2.hasNext())
							es = false;
						else {
							i++;
							edgesIt1 = g.incidentEdges(v).iterator();
							edgesIt2 = g.succesorEdges(v).iterator();
							edgesIt1.next();
						}
					}
				}
			}
		} catch (InvalidVertexException | InvalidEdgeException e) {
			e.printStackTrace();
		}
		if (i != cant)
			es = false;
		return es;
	}

	/**
	 * Chequeo si es un arbol de la forma: A / \ ˅ ˅ B ... C / \ / \ ˅... ˅ ˅... ˅
	 * etc
	 */
	public static <V, E> boolean esArbol(GraphD<V, E> g, Vertex<V> a) {
		boolean pertenece, es = true;
		pertenece = false;
		int cant = 0;
		Vertex<V> v;
		Iterator<Vertex<V>> verticesIt = g.vertices().iterator();
		Map<Vertex<V>, Boolean> m = new MapeoConLista<Vertex<V>, Boolean>();
		try {
			while (verticesIt.hasNext()) {
				v = verticesIt.next();
				m.put(v, false);
				if (v == a) {
					pertenece = true;
					if (g.incidentEdges(v).iterator().hasNext())
						es = false;
				}
			}
			if (pertenece && es) {
				if (!dfsAux(g, a, m))
					es = false;
				for (Vertex<V> ve : g.vertices())
					if (!m.get(ve))
						es = false;
			}
		} catch (InvalidVertexException | InvalidKeyException e) {
			e.printStackTrace();
		}
		return es;
	}

	private static <V, E> boolean dfsAux(GraphD<V, E> g, Vertex<V> v, Map<Vertex<V>, Boolean> m) {
		boolean tieneUnPadre = true;
		try {
			if (m.get(v))
				tieneUnPadre = false;
			else {
				m.put(v, true);
				for (Edge<E> e : g.succesorEdges(v)) {
					Vertex<V> x = g.opposite(v, e);
					tieneUnPadre = dfsAux(g, x, m);
				}
			}
		} catch (InvalidKeyException | InvalidVertexException | InvalidEdgeException e) {
			e.printStackTrace();
		}
		return tieneUnPadre;
	}

	public static <V, E> void eliminarMayorIncidencia(GraphD<V, E> g) {
		Vertex<V> mayor = null;
		int max, cant = max = 0;
		try {
			for (Vertex<V> v : g.vertices()) {
				for (Edge<E> e : g.incidentEdges(v))
					cant++;
				if (cant > max) {
					max = cant;
					mayor = v;
				}
			}
			if (mayor != null)
				g.removeVertex(mayor);
		} catch (InvalidVertexException e) {
			e.printStackTrace();
		}
	}

	public static <V, E> boolean esConexo(Graph<V, E> g) {
		boolean es = true;
		Map<Vertex<V>, Boolean> m = new MapeoConLista<Vertex<V>, Boolean>();
		try {
			for (Vertex<V> v : g.vertices())
				m.put(v, false);
			if (g.vertices().iterator().hasNext())
				dfsAux2(g, g.vertices().iterator().next(), m);
			Iterator<Entry<Vertex<V>, Boolean>> it = m.entries().iterator();
			while (es && it.hasNext())
				if (!it.next().getValue())
					es = false;
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		}
		return es;
	}

	private static <V, E> void dfsAux2(Graph<V, E> g, Vertex<V> v, Map<Vertex<V>, Boolean> m) {
		try {
			m.put(v, true);
			for (Edge<E> a : g.incidentEdges(v)) {
				Vertex<V> w = g.opposite(v, a);
				if (!m.get(w))
					dfsAux2(g, w, m);
			}
		} catch (InvalidKeyException | InvalidVertexException | InvalidEdgeException e) {
			e.printStackTrace();
		}
	}

	public static <V, E> PositionList<PositionList<Vertex<V>>> componentesConexas(Graph<V, E> g) {
		PositionList<PositionList<Vertex<V>>> componentes = new ListaDE<PositionList<Vertex<V>>>();
		Map<Vertex<V>, Boolean> m = new MapeoConLista<Vertex<V>, Boolean>();
		try {
			for (Vertex<V> v : g.vertices())
				m.put(v, false);
			for (Vertex<V> v : g.vertices())
				if (!m.get(v)) {
					PositionList<Vertex<V>> vertices = new ListaDE<Vertex<V>>();
					dfsAux3(g, v, m, vertices);
					componentes.addLast(vertices);
				}
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		}
		return componentes;
	}

	private static <V, E> void dfsAux3(Graph<V, E> g, Vertex<V> v, Map<Vertex<V>, Boolean> m,
			PositionList<Vertex<V>> vertices) {
		vertices.addLast(v);
		try {
			m.put(v, true);
			for (Edge<E> a : g.incidentEdges(v)) {
				Vertex<V> w = g.opposite(v, a);
				if (!m.get(w))
					dfsAux3(g, w, m, vertices);
			}
		} catch (InvalidKeyException | InvalidVertexException | InvalidEdgeException e) {
			e.printStackTrace();
		}
	}

	public static <V, E> boolean variaronConexas(Graph<V, E> g, Vertex<V> v) {
		boolean pertenece, variaron = pertenece = false;
		Iterator<Vertex<V>> vIt = g.vertices().iterator();
		PositionList<PositionList<Vertex<V>>> componentesAntiguas, componentesNuevas;
		while (!pertenece && vIt.hasNext())
			if (vIt.next() == v)
				pertenece = true;
		if (pertenece)
			try {
				componentesAntiguas = componentesConexas(g);
				g.removeVertex(v);
				componentesNuevas = componentesConexas(g);
				if (componentesAntiguas.size() != componentesNuevas.size())
					variaron = true;
			} catch (InvalidVertexException e) {
				e.printStackTrace();
			}

		return variaron;
	}

	public static <V, E> Graph<V, E> Warshall(Graph<V, E> g) {
		try {
			// Graph<V,E> w = copy(g);
			for (Vertex<V> k : g.vertices())
				for (Vertex<V> i : g.vertices())
					if (i != k && g.areAdjacent(i, k))
						for (Vertex<V> j : g.vertices())
							if (i != j && j != k && g.areAdjacent(k, j))
								if (!g.areAdjacent(i, j))
									g.insertEdge(i, j, null);
		} catch (InvalidVertexException e) {
		}
		return g;
	}

	public static <V, E> Map<Pair<Vertex<V>, Vertex<V>>, Vertex<V>> Floyd(GraphD<V, Double> g) {
		Map<Pair<Vertex<V>, Vertex<V>>, Double> A = new MapeoConLista<Pair<Vertex<V>, Vertex<V>>, Double>();
		Map<Pair<Vertex<V>, Vertex<V>>, Vertex<V>> P = new MapeoConLista<Pair<Vertex<V>, Vertex<V>>, Vertex<V>>();
		try {
			for (Vertex<V> i : g.vertices())
				for (Vertex<V> j : g.vertices()) {
					if (g.areAdjacent(i, j))
						A.put(new Pair<Vertex<V>, Vertex<V>>(i, j), costo(g, i, j));
					else
						A.put(new Pair<Vertex<V>, Vertex<V>>(i, j), Double.MAX_VALUE / 2);
					P.put(new Pair<Vertex<V>, Vertex<V>>(i, j), null);
				}
			for (Vertex<V> i : g.vertices())
				A.put(buscar1(A, i, i), 0.0);

			for (Vertex<V> k : g.vertices())
				for (Vertex<V> i : g.vertices())
					for (Vertex<V> j : g.vertices())
						if (A.get(buscar1(A, i, k)) + A.get(buscar1(A, k, j)) < A.get(buscar1(A, i, j))) {
							A.put(buscar1(A, i, j), A.get(buscar1(A, i, k)) + A.get(buscar1(A, k, j)));
							P.put(buscar2(P, i, j), k);
						}
			for (Entry<Pair<Vertex<V>, Vertex<V>>, Double> e : A.entries())
				System.out.println("(" + e.getKey().getKey().element() + "," + e.getKey().getValue().element() + "):"
						+ e.getValue());
		} catch (InvalidVertexException | InvalidKeyException e) {
		}
		return P;
	}

	private static <V, E> Double costo(GraphD<V, Double> g, Vertex<V> i, Vertex<V> j) {
		Double costo, aux = 0.0;
		costo = Double.MAX_VALUE;
		try {
			for (Edge<Double> e : g.succesorEdges(i))
				if (g.opposite(i, e) == j) {
					aux = e.element();
					if (aux < costo)
						costo = aux;
				}
		} catch (InvalidVertexException | InvalidEdgeException e) {
			e.printStackTrace();
		}
		return costo;
	}

	private static <V, E> Pair<Vertex<V>, Vertex<V>> buscar1(Map<Pair<Vertex<V>, Vertex<V>>, Double> A, Vertex<V> i,
			Vertex<V> j) {
		Iterator<Entry<Pair<Vertex<V>, Vertex<V>>, Double>> it = A.entries().iterator();
		boolean encontre = false;
		Pair<Vertex<V>, Vertex<V>> aux, par = null;
		while (!encontre && it.hasNext()) {
			aux = it.next().getKey();
			if (aux.getKey() == i && aux.getValue() == j) {
				encontre = true;
				par = aux;
			}
		}
		return par;
	}

	private static <V, E> Pair<Vertex<V>, Vertex<V>> buscar2(Map<Pair<Vertex<V>, Vertex<V>>, Vertex<V>> P, Vertex<V> i,
			Vertex<V> j) {
		Iterator<Entry<Pair<Vertex<V>, Vertex<V>>, Vertex<V>>> it = P.entries().iterator();
		boolean encontre = false;
		Pair<Vertex<V>, Vertex<V>> aux, par = null;
		while (!encontre && it.hasNext()) {
			aux = it.next().getKey();
			if (aux.getKey() == i && aux.getValue() == j) {
				encontre = true;
				par = aux;
			}
		}
		return par;
	}

	public static <V, E> void recuperarCamino(Map<Pair<Vertex<V>, Vertex<V>>, Vertex<V>> P, Vertex<V> i, Vertex<V> j,
			Queue<Vertex<V>> q) {
		q.enqueue(i);
		recuperarCaminoAux(P, i, j, q);
		q.enqueue(j);
	}

	private static <V, E> void recuperarCaminoAux(Map<Pair<Vertex<V>, Vertex<V>>, Vertex<V>> P, Vertex<V> i,
			Vertex<V> j, Queue<Vertex<V>> q) {
		try {
			Vertex<V> k = P.get(buscar2(P, i, j));
			if (k != null) {
				recuperarCaminoAux(P, i, k, q);
				q.enqueue(k);
				recuperarCaminoAux(P, k, j, q);
			}
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		}
	}

	public static <V, E> Pair<Map<Vertex<V>, Float>, Map<Vertex<V>, Vertex<V>>> Dijkstra(GraphD<V, Float> g,
			Vertex<V> a) { // O(n(n+m)) o log(n)(n+m) en caso de usar una CP en vez de DE.
		Pair<Map<Vertex<V>, Float>, Map<Vertex<V>, Vertex<V>>> r = new Pair<Map<Vertex<V>, Float>, Map<Vertex<V>, Vertex<V>>>(
				null, null);
		Map<Vertex<V>, Float> D = new MapeoHashAbierto<Vertex<V>, Float>();
		Map<Vertex<V>, Vertex<V>> P = new MapeoHashAbierto<Vertex<V>, Vertex<V>>();
		PositionList<Vertex<V>> aux = new ListaDE<Vertex<V>>();
		Vertex<V> v, u = a;
		try {
			for (Vertex<V> i : g.vertices()) {
				D.put(i, Float.MAX_VALUE / 2);
				P.put(i, null);
			}
			D.put(a, 0f);
			for (Vertex<V> i : g.vertices()) {
				u = minimo(D, a, aux);
				aux.addLast(u);
				for (Edge<Float> e : g.succesorEdges(u)) {
					v = g.opposite(u, e);
					if (!pertenece(v, aux))
						if (D.get(u) + e.element() < D.get(v)) {
							D.put(v, D.get(u) + e.element());
							P.put(v, u);
						}
				}
			}
		} catch (InvalidKeyException | InvalidVertexException | InvalidEdgeException e) {
			e.printStackTrace();
		}
		r.setKey(D);
		r.setValue(P);
		return r;
	}

	private static <V, E> Vertex<V> minimo(Map<Vertex<V>, Float> D, Vertex<V> a, PositionList<Vertex<V>> lista) {
		Vertex<V> v = a;
		Float menor = Float.MAX_VALUE;
		;
		for (Entry<Vertex<V>, Float> e : D.entries())
			if (!pertenece(e.getKey(), lista) && e.getValue() < menor) {
				v = e.getKey();
				menor = e.getValue();
			}
		return v;
	}

	private static <V, E> boolean pertenece(Vertex<V> x, PositionList<Vertex<V>> lista) {
		boolean pertenece = false;
		Iterator<Vertex<V>> it = lista.iterator();
		while (!pertenece && it.hasNext())
			pertenece = it.next() == x;
		return pertenece;
	}

	/*
	 * public static <V,E> PositionList<Vertex<V>>
	 * recuperar(Map<Vertex<V>,Vertex<V>> P, Vertex<V> destino, Vertex<V> fuente){
	 * PositionList<Vertex<V>> camino = new ListaDE<Vertex<V>>();
	 * camino.addFirst(fuente); recuperar_aux(P,destino,camino); return camino; }
	 */
	private static <V, E> void recuperar(Map<Vertex<V>, Vertex<V>> P, Vertex<V> v, PositionList<Vertex<V>> camino) {
		try {
			Vertex<V> x = v;
			while (x != null) {
				camino.addFirst(x);
				x = P.get(x);
			}
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		}
	}

	public static <V, E> PositionList<Vertex<V>> caminoFloydPesado(Graph<V, Double> g, Vertex<V> a, Vertex<V> b) {
		PositionList<Vertex<V>> camino = new ListaDE<Vertex<V>>();
		Queue<Vertex<V>> q = new Cola_con_enlaces<Vertex<V>>();
		recuperarCamino(FloydCamino_CortoPesado(g), a, b, q);
		while (!q.isEmpty())
			try {
				camino.addLast(q.dequeue());
			} catch (EmptyQueueException e) {
				e.printStackTrace();
			}
		return camino;
	}

	private static <V, E> Map<Pair<Vertex<V>, Vertex<V>>, Vertex<V>> FloydCamino_CortoPesado(Graph<V, Double> g) {
		Map<Pair<Vertex<V>, Vertex<V>>, Double> A = new MapeoConLista<Pair<Vertex<V>, Vertex<V>>, Double>();
		Map<Pair<Vertex<V>, Vertex<V>>, Vertex<V>> P = new MapeoConLista<Pair<Vertex<V>, Vertex<V>>, Vertex<V>>();
		try {
			for (Vertex<V> i : g.vertices())
				for (Vertex<V> j : g.vertices()) {
					if (g.areAdjacent(i, j))
						A.put(new Pair<Vertex<V>, Vertex<V>>(i, j), costo2(g, i, j));
					else
						A.put(new Pair<Vertex<V>, Vertex<V>>(i, j), Double.MAX_VALUE / 2);
					P.put(new Pair<Vertex<V>, Vertex<V>>(i, j), null);
				}
			for (Vertex<V> i : g.vertices())
				A.put(buscar1(A, i, i), 0.0);

			for (Vertex<V> k : g.vertices())
				for (Vertex<V> i : g.vertices())
					for (Vertex<V> j : g.vertices())
						if (A.get(buscar1(A, i, k)) + A.get(buscar1(A, k, j)) < A.get(buscar1(A, i, j))) {
							A.put(buscar1(A, i, j), A.get(buscar1(A, i, k)) + A.get(buscar1(A, k, j)));
							P.put(buscar2(P, i, j), k);
						}
		} catch (InvalidVertexException | InvalidKeyException e) {
		}
		return P;
	}

	private static <V, E> Double costo2(Graph<V, Double> g, Vertex<V> i, Vertex<V> j) {
		Double costo, aux = 0.0;
		costo = Double.MAX_VALUE;
		try {
			for (Edge<Double> e : g.incidentEdges(i))
				if (g.opposite(i, e) == j) {
					aux = e.element();
					if (aux < costo)
						costo = aux;
				}
		} catch (InvalidVertexException | InvalidEdgeException e) {
			e.printStackTrace();
		}
		return costo;
	}

	public static <V, E> PositionList<Vertex<V>> caminoFloyd(Graph<V, E> g, Vertex<V> a, Vertex<V> b) {
		PositionList<Vertex<V>> camino = new ListaDE<Vertex<V>>();
		Queue<Vertex<V>> q = new Cola_con_enlaces<Vertex<V>>();
		recuperarCamino(FloydCamino_Corto(g), a, b, q);
		while (!q.isEmpty())
			try {
				camino.addLast(q.dequeue());
			} catch (EmptyQueueException e) {
				e.printStackTrace();
			}
		return camino;
	}

	private static <V, E> Map<Pair<Vertex<V>, Vertex<V>>, Vertex<V>> FloydCamino_Corto(Graph<V, E> g) {
		Map<Pair<Vertex<V>, Vertex<V>>, Integer> A = new MapeoConLista<Pair<Vertex<V>, Vertex<V>>, Integer>();
		Map<Pair<Vertex<V>, Vertex<V>>, Vertex<V>> P = new MapeoConLista<Pair<Vertex<V>, Vertex<V>>, Vertex<V>>();
		try {
			for (Vertex<V> i : g.vertices())
				for (Vertex<V> j : g.vertices()) {
					if (g.areAdjacent(i, j))
						A.put(new Pair<Vertex<V>, Vertex<V>>(i, j), 1);
					else
						A.put(new Pair<Vertex<V>, Vertex<V>>(i, j), Integer.MAX_VALUE / 2);
					P.put(new Pair<Vertex<V>, Vertex<V>>(i, j), null);
				}
			for (Vertex<V> i : g.vertices())
				A.put(buscarNoPesado(A, i, i), 0);

			for (Vertex<V> k : g.vertices())
				for (Vertex<V> i : g.vertices())
					for (Vertex<V> j : g.vertices())
						if (A.get(buscarNoPesado(A, i, k)) + A.get(buscarNoPesado(A, k, j)) < A
								.get(buscarNoPesado(A, i, j))) {
							A.put(buscarNoPesado(A, i, j),
									A.get(buscarNoPesado(A, i, k)) + A.get(buscarNoPesado(A, k, j)));
							P.put(buscar2(P, i, j), k);
						}
		} catch (InvalidVertexException | InvalidKeyException e) {
		}
		return P;
	}

	private static <V, E> Pair<Vertex<V>, Vertex<V>> buscarNoPesado(Map<Pair<Vertex<V>, Vertex<V>>, Integer> A,
			Vertex<V> i, Vertex<V> j) {
		Iterator<Entry<Pair<Vertex<V>, Vertex<V>>, Integer>> it = A.entries().iterator();
		boolean encontre = false;
		Pair<Vertex<V>, Vertex<V>> aux, par = null;
		while (!encontre && it.hasNext()) {
			aux = it.next().getKey();
			if (aux.getKey() == i && aux.getValue() == j) {
				encontre = true;
				par = aux;
			}
		}
		return par;
	}

	public static <V, E> PositionList<Vertex<V>> caminoDijkstraPesado(Graph<V, Float> g, Vertex<V> a, Vertex<V> b) {
		PositionList<Vertex<V>> camino = new ListaDE<Vertex<V>>();
		recuperar(DijkstraPesado(g, a).getValue(), b, camino);
		return camino;
	}

	private static <V, E> Pair<Map<Vertex<V>, Float>, Map<Vertex<V>, Vertex<V>>> DijkstraPesado(Graph<V, Float> g,
			Vertex<V> a) {
		Pair<Map<Vertex<V>, Float>, Map<Vertex<V>, Vertex<V>>> r = new Pair<Map<Vertex<V>, Float>, Map<Vertex<V>, Vertex<V>>>(
				null, null);
		Map<Vertex<V>, Float> D = new MapeoHashAbierto<Vertex<V>, Float>();
		Map<Vertex<V>, Vertex<V>> P = new MapeoHashAbierto<Vertex<V>, Vertex<V>>();
		PositionList<Vertex<V>> aux = new ListaDE<Vertex<V>>();
		Vertex<V> v, u = a;
		try {
			for (Vertex<V> i : g.vertices()) {
				D.put(i, Float.MAX_VALUE / 2);
				P.put(i, null);
			}
			D.put(a, 0f);
			for (Vertex<V> i : g.vertices()) {
				u = minimo(D, a, aux);
				aux.addLast(u);
				for (Edge<Float> e : g.incidentEdges(u)) {
					v = g.opposite(u, e);
					if (!pertenece(v, aux))
						if (D.get(u) + e.element() < D.get(v)) {
							D.put(v, D.get(u) + e.element());
							P.put(v, u);
						}
				}
			}
		} catch (InvalidKeyException | InvalidVertexException | InvalidEdgeException e) {
			e.printStackTrace();
		}
		r.setKey(D);
		r.setValue(P);
		return r;
	}

	private static <V, E> Pair<Map<Vertex<V>, Float>, Map<Vertex<V>, Vertex<V>>> DijkstraNoPesado(Graph<V, E> g,
			Vertex<V> a) {
		Pair<Map<Vertex<V>, Float>, Map<Vertex<V>, Vertex<V>>> r = new Pair<Map<Vertex<V>, Float>, Map<Vertex<V>, Vertex<V>>>(
				null, null);
		Map<Vertex<V>, Float> D = new MapeoHashAbierto<Vertex<V>, Float>();
		Map<Vertex<V>, Vertex<V>> P = new MapeoHashAbierto<Vertex<V>, Vertex<V>>();
		PositionList<Vertex<V>> aux = new ListaDE<Vertex<V>>();
		Vertex<V> v, u = a;
		try {
			for (Vertex<V> i : g.vertices()) {
				D.put(i, Float.MAX_VALUE / 2);
				P.put(i, null);
			}
			D.put(a, 0f);
			for (Vertex<V> i : g.vertices()) {
				u = minimo(D, a, aux);
				aux.addLast(u);
				for (Edge<E> e : g.incidentEdges(u)) {
					v = g.opposite(u, e);
					if (!pertenece(v, aux))
						if (D.get(u) + 1 < D.get(v)) {
							D.put(v, D.get(u) + 1);
							P.put(v, u);
						}
				}
			}
		} catch (InvalidKeyException | InvalidVertexException | InvalidEdgeException e) {
			e.printStackTrace();
		}
		r.setKey(D);
		r.setValue(P);
		return r;
	}

	public static <V, E> PositionList<Vertex<V>> caminoDijkstraNoPesado(Graph<V, E> g, Vertex<V> a, Vertex<V> b) {
		PositionList<Vertex<V>> camino = new ListaDE<Vertex<V>>();
		recuperar(DijkstraNoPesado(g, a).getValue(), b, camino);
		return camino;
	}

	public static <V, E> Map<Vertex<V>, Integer> dfsCantVerticesAlcanzables(Graph<V, E> g) {
		Map<Vertex<V>, Integer> m = new MapeoHashAbierto<Vertex<V>, Integer>();
		Map<Vertex<V>, Boolean> mB = new MapeoHashAbierto<Vertex<V>, Boolean>();
		try {
			for (Vertex<V> v : g.vertices())
				m.put(v, 0);
			for (Vertex<V> v : g.vertices()) {
				for (Vertex<V> v2 : g.vertices())
					mB.put(v2, false);
				dfsCantVertAlc(g, v, v, m, mB);
			}
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		}
		return m;
	}

	private static <V, E> void dfsCantVertAlc(Graph<V, E> g, Vertex<V> v, Vertex<V> vActual, Map<Vertex<V>, Integer> m,
			Map<Vertex<V>, Boolean> mB) {
		try {
			m.put(v, m.get(v) + 1);
			mB.put(vActual, true);
			for (Edge<E> a : g.incidentEdges(vActual)) {
				Vertex<V> w = g.opposite(vActual, a);
				if (!mB.get(w))
					dfsCantVertAlc(g, v, w, m, mB);
			}
		} catch (InvalidKeyException | InvalidVertexException | InvalidEdgeException e) {
			e.printStackTrace();
		}
	}

	public static <V, E> void dfsa(Graph<V, E> g) {
		Map<Vertex<V>, Boolean> m = new MapeoConLista<Vertex<V>, Boolean>();
		try {
			for (Vertex<V> v : g.vertices())
				m.put(v, false);
			for (Vertex<V> v : g.vertices())
				if (!m.get(v))
					dfsAuxa(g, v, m);
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		}
	}

	private static <V, E> void dfsAuxa(Graph<V, E> g, Vertex<V> v, Map<Vertex<V>, Boolean> m) {
		System.out.println(v.element());
		try {
			m.put(v, true);
			for (Edge<E> a : g.incidentEdges(v)) {
				Vertex<V> w = g.opposite(v, a);
				if (!m.get(w))
					dfsAuxa(g, w, m);
			}
		} catch (InvalidKeyException | InvalidVertexException | InvalidEdgeException e) {
			e.printStackTrace();
		}
	}

	public static <V, E> boolean HallarCamino(GraphD<V, E> g, Vertex<V> origen, Vertex<V> destino) {
		Map<Vertex<V>, Boolean> m = new MapeoHashAbierto<Vertex<V>, Boolean>();
		PositionList<Vertex<V>> camino = new ListaDE<Vertex<V>>();
		for (Vertex<V> v : g.vertices())
			try {
				m.put(v, false);
			} catch (InvalidKeyException e) {
				e.printStackTrace();
			}
		return HallarCaminoAux(g, origen, destino, camino, m);
	}

	private static <V, E> boolean HallarCaminoAux(GraphD<V, E> g, Vertex<V> origen, Vertex<V> destino,
			PositionList<Vertex<V>> camino, Map<Vertex<V>, Boolean> m) {
		boolean hay = false;
		try {
			m.put(origen, true);
			camino.addLast(origen);
			if (origen.equals(destino))
				hay = true;
			else
				for (Edge<E> e : g.succesorEdges(origen)) {
					Vertex<V> v = g.opposite(origen, e);
					if (!m.get(v))
						hay = HallarCaminoAux(g, v, destino, camino, m);
				}
			if (!hay)
				camino.remove(camino.last());
		} catch (InvalidKeyException | InvalidVertexException | InvalidEdgeException | InvalidPositionException
				| EmptyListException e) {
			e.printStackTrace();
		}
		return hay;
	}

	private static <V, E> boolean HallarCamino2(GraphD<V, E> g, Vertex<V> origen, Vertex<V> destino,
			PositionList<Vertex<V>> camino) {
		Map<Vertex<V>, Boolean> m = new MapeoHashAbierto<Vertex<V>, Boolean>();
		for (Vertex<V> v : g.vertices())
			try {
				m.put(v, false);
			} catch (InvalidKeyException e) {
				e.printStackTrace();
			}
		return HallarCaminoAux(g, origen, destino, camino, m);
	}

	public static <V, E> PositionList<Vertex<V>> HallarCiclo(GraphD<V, E> g, Vertex<V> v) {
		PositionList<Vertex<V>> camino = new ListaDE<Vertex<V>>();
		Map<Vertex<V>, Boolean> m = new MapeoHashAbierto<Vertex<V>, Boolean>();
		for (Vertex<V> w : g.vertices())
			try {
				m.put(w, false);
			} catch (InvalidKeyException e) {
				e.printStackTrace();
			}
		HallarCicloAux(g, v, camino, m);
		return camino;
	}

	private static <V, E> void HallarCicloAux(GraphD<V, E> g, Vertex<V> v, PositionList<Vertex<V>> ciclo,
			Map<Vertex<V>, Boolean> m) {
		boolean encontre;
		PositionList<Vertex<V>> camino = new ListaDE<Vertex<V>>();
		try {
			for (Edge<E> e : g.succesorEdges(v)) {
				Vertex<V> w = g.opposite(v, e);
				encontre = HallarCamino2(g, w, v, camino);
				if (encontre) {
					ciclo.addLast(v);
					for (Vertex<V> x : camino)
						ciclo.addLast(x);
				}
			}
		} catch (InvalidVertexException | InvalidEdgeException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Ver tiempo de ejecucion de los algoritmos.
	 */
	public static <V, E> void main(String a[]) {
		GrafoMatrizA<Integer, Integer> grafo = new GrafoMatrizA<Integer, Integer>(10);
		// Map<Pair<Vertex<Integer>,Vertex<Integer>>,Double> m = new
		// MapeoConLista<Pair<Vertex<Integer>,Vertex<Integer>>,Double>();
		Vertex<Integer> x1 = grafo.insertVertex(1);
		Vertex<Integer> x2 = grafo.insertVertex(2);
		Vertex<Integer> x3 = grafo.insertVertex(3);
		Vertex<Integer> x4 = grafo.insertVertex(4);
		Vertex<Integer> x5 = grafo.insertVertex(5);
		Vertex<Integer> x6 = grafo.insertVertex(6);
		Vertex<Integer> x7 = grafo.insertVertex(7);
		Vertex<Integer> x8 = grafo.insertVertex(8);
		Vertex<Integer> x9 = grafo.insertVertex(9);

		try {
			// Edge<Integer> e11 = grafo.insertEdge(x1, x1, 1);
			Edge<Integer> e12 = grafo.insertEdge(x1, x2, 1);
			// Edge<Integer> e17 = grafo.insertEdge(x1, x7, 2);
			Edge<Integer> e23 = grafo.insertEdge(x2, x3, 1);
			Edge<Integer> e34 = grafo.insertEdge(x3, x4, 1);
			Edge<Integer> e45 = grafo.insertEdge(x4, x5, 1);
			Edge<Integer> e47 = grafo.insertEdge(x5, x6, 1);
			Edge<Integer> e56 = grafo.insertEdge(x6, x7, 1);
			Edge<Integer> e87 = grafo.insertEdge(x7, x8, 1);
			Edge<Integer> e49 = grafo.insertEdge(x8, x9, 1);
			// Edge<Integer> e19 = grafo.insertEdge(x1, x9, 1);
			/*
			 * Edge<Integer> e3 = grafo.insertEdge(x3, x4, 1); Edge<Integer> e4 =
			 * grafo.insertEdge(x4, x5, 1); Edge<Integer> e5 = grafo.insertEdge(x5, x6, 1);
			 */
			/*
			 * for(Edge<Integer> e : grafo.edges())
			 * System.out.print("("+grafo.endvertices(e)[0].element()+","+grafo.endvertices(
			 * e)[1].element()+") "); System.out.println(); Warshall(grafo);
			 * for(Edge<Integer> e : grafo.edges())
			 * System.out.print("("+grafo.endvertices(e)[0].element()+","+grafo.endvertices(
			 * e)[1].element()+") ");
			 */
			// Edge<Integer> e6 = grafo.insertEdge(x6, x6, 1);
			// System.out.println(lista_con_cabeza(grafo,x1));
			// System.out.println(esConexo(grafo));
			// Edge<Integer> e6 = grafo.insertEdge(x4, x5, 2);
			Queue<Vertex<Integer>> q = new Cola_con_enlaces<Vertex<Integer>>();
			// recuperarCamino(Floyd(grafo), x1, x9, q);
			while (!q.isEmpty())
				System.out.print(q.dequeue().element() + ", ");
		} catch (InvalidVertexException | EmptyQueueException e) {
			e.printStackTrace();
		}
		GraphD<Float, Double> gd = new GrafoDlistaA<Float, Double>();
		Vertex<Float> v1 = gd.insertVertex(1f);
		Vertex<Float> v2 = gd.insertVertex(2f);
		Vertex<Float> v3 = gd.insertVertex(3f);
		Vertex<Float> v4 = gd.insertVertex(4f);
		Vertex<Float> v5 = gd.insertVertex(5f);
		try {
			Edge<Double> a1 = gd.insertEdge(v1, v2, 1.0);
			// Edge<Double> a12 = gd.insertEdge(v1, v2, 1.0);
			Edge<Double> a2 = gd.insertEdge(v2, v3, 1.0);
			Edge<Double> a3 = gd.insertEdge(v3, v4, 1.0);
			Edge<Double> a4 = gd.insertEdge(v4, v5, 1.0);
			Edge<Double> a5 = gd.insertEdge(v5, v1, 1.0);
			// Edge<Double> a6 = gd.insertEdge(v5, v4, 1.0);
			PositionList<Vertex<Float>> l = new ListaDE<Vertex<Float>>();
			// recuperar(Dijkstra(gd,v1).getValue(),v1,q);
			System.out.println();
			System.out.println("Main");
			HallarCicloAux(gd, v5, l, null);
			for (Vertex<Float> v : l)
				System.out.print(v.element() + " ");
			// toString(q);
			/*
			 * Queue<Vertex<Float>> q = new Cola_con_enlaces<Vertex<Float>>();
			 * recuperarCamino(Floyd(gd), v1, v4, q); while(!q.isEmpty())
			 * System.out.print(q.dequeue().element()+", "); q = new
			 * Cola_con_enlaces<Vertex<Float>>();
			 */

			// Edge<Integer> a3f = gd.insertEdge(v4, v1, 1);
			// System.out.println(esArbol(gd,v1));
			// System.out.println(lista_cabeza(gd,v3));
		} catch (InvalidVertexException e) {
			e.printStackTrace();
		}
		// toString(DFSMapeo(grafo,x2));
		/*
		 * PositionList<PositionList<Vertex<Integer>>> p = caminos(grafo,x2,x5);
		 * System.out.println(p.size()); for(Position<PositionList<Vertex<Integer>>> l :
		 * p.positions()) { toString(l.element()); System.out.println(); }
		 */
		// eliminar_incidente(grafo);
		// System.out.println(es_arbol(grafo,x1));
	}
}
