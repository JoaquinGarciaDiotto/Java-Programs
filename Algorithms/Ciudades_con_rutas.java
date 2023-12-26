package Operaciones;

import TDAGrafo.Edge;
import TDAGrafo.GrafoDlistaA;
import TDAGrafo.GraphD;
import TDAGrafo.InvalidEdgeException;
import TDAGrafo.InvalidVertexException;
import TDAGrafo.Vertex;
import TDALista.EmptyListException;
import TDALista.InvalidPositionException;
import TDALista.ListaDE;
import TDALista.Position;
import TDALista.PositionList;
import TDAMapeo.InvalidKeyException;
import TDAMapeo.Map;
import TDAMapeo.MapeoConLista;

public class Ciudades_con_rutas {
	private GraphD<String, Float> territorio;

	public Ciudades_con_rutas() {
		territorio = new GrafoDlistaA<String, Float>();
	}

	public Vertex<String> agregarCiudad(String c) {
		return territorio.insertVertex(c);
	}

	public Edge<Float> agregarRuta(Vertex<String> v, Vertex<String> w, Float f) {
		try {
			return territorio.insertEdge(v, w, f);
		} catch (InvalidVertexException e) {
			e.printStackTrace();
			return null;
		}
	}

	public String eliminarCiudad(Vertex<String> c) {
		try {
			return territorio.removeVertex(c);
		} catch (InvalidVertexException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Float eliminarRuta(Edge<Float> e) {
		try {
			return territorio.removeEdge(e);
		} catch (InvalidEdgeException e1) {
			e1.printStackTrace();
			return null;
		}
	}

	public PositionList<Vertex<String>> camino(String c1, String c2, String c3) {
		PositionList<Vertex<String>> camino1 = camino_economico(territorio, c1, c2);
		PositionList<Vertex<String>> camino2 = camino_economico(territorio, c2, c3);
		try {
			if (!camino2.isEmpty())
				camino2.remove(camino2.first());
		} catch (InvalidPositionException | EmptyListException e) {
			e.printStackTrace();
		}
		for (Position<Vertex<String>> v : camino2.positions())
			camino1.addLast(v.element());
		return camino1;
	}

	private PositionList<Vertex<String>> camino_economico(GraphD<String, Float> g, String a, String b) {
		PositionList<Vertex<String>> camino = new ListaDE<Vertex<String>>();
		PositionList<Vertex<String>> caminoActual = new ListaDE<Vertex<String>>();
		Map<Vertex<String>, Boolean> m = new MapeoConLista<Vertex<String>, Boolean>();
		Vertex<String> va = null, vb = null;
		for (Vertex<String> v : g.vertices())
			try {
				m.put(v, false);
				if (v.element().contentEquals(a))
					va = v;
				else if (v.element().contentEquals(b))
					vb = v;
			} catch (InvalidKeyException e) {
				e.printStackTrace();
			}
		if (va != null && vb != null)
			camino = caminoMinimo(g, va, vb, caminoActual, 0f, camino, new Solucion(), m);
		return camino;
	}

	private PositionList<Vertex<String>> caminoMinimo(GraphD<String, Float> g, Vertex<String> origen,
			Vertex<String> destino, PositionList<Vertex<String>> caminoActual, Float costoCaminoActual,
			PositionList<Vertex<String>> caminoMinimo, Solucion costoCaminoMinimo, Map<Vertex<String>, Boolean> m) {
		try {
			m.put(origen, true);
			caminoActual.addLast(origen);
			if (origen.equals(destino)) {
				if (costoCaminoMinimo.getCosto() == null || costoCaminoActual < costoCaminoMinimo.getCosto()) {
					caminoMinimo = clonado(caminoActual);
					costoCaminoMinimo.setCosto(costoCaminoActual);
				}
			} else
				for (Edge<Float> a : g.succesorEdges(origen)) {
					Vertex<String> v = g.opposite(origen, a);
					if (!m.get(v))
						caminoMinimo = caminoMinimo(g, v, destino, caminoActual, costoCaminoActual + a.element(),
								caminoMinimo, costoCaminoMinimo, m);
				}
			caminoActual.remove(caminoActual.last());
			m.put(origen, false);
		} catch (InvalidKeyException | InvalidVertexException | InvalidEdgeException | InvalidPositionException
				| EmptyListException f) {
			f.printStackTrace();
		}
		return caminoMinimo;
	}

	private PositionList<Vertex<String>> clonado(PositionList<Vertex<String>> l) {
		ListaDE<Vertex<String>> clon = new ListaDE<Vertex<String>>();
		for (Position<Vertex<String>> v : l.positions()) {
			clon.addLast(v.element());
		}
		return clon;
	}

	private class Solucion {
		Float costo;

		public Solucion() {
			costo = null;
		}

		public void setCosto(Float c) {
			costo = c;
		}

		public Float getCosto() {
			return costo;
		}
	}

	public int cantidad_caminos(String a, String b) {
		PositionList<PositionList<Vertex<String>>> caminos = new ListaDE<PositionList<Vertex<String>>>();
		Map<Vertex<String>, Boolean> m = new MapeoConLista<Vertex<String>, Boolean>();
		PositionList<Vertex<String>> caminoActual = new ListaDE<Vertex<String>>();
		Vertex<String> va = null, vb = null;
		for (Vertex<String> v : territorio.vertices())
			try {
				m.put(v, false);
				if (v.element().contentEquals(a))
					va = v;
				else if (v.element().contentEquals(b))
					vb = v;
			} catch (InvalidKeyException e) {
				e.printStackTrace();
			}
		if (va != null && vb != null) {
			caminoActual.addLast(va);
			caminosAux(territorio, va, vb, caminoActual, caminos, m);
		}
		return caminos.size();
	}

	private void caminosAux(GraphD<String, Float> g, Vertex<String> a, Vertex<String> b,
			PositionList<Vertex<String>> caminoActual, PositionList<PositionList<Vertex<String>>> caminos,
			Map<Vertex<String>, Boolean> m) {
		if (a.equals(b)) {
			caminos.addLast(clonado(caminoActual));
		} else
			try {
				m.put(a, true);
				for (Edge<Float> e : g.succesorEdges(a)) {
					Vertex<String> x = g.opposite(a, e);
					if (!m.get(x)) {
						caminoActual.addLast(x);
						Position<Vertex<String>> temp = caminoActual.last();
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
}
