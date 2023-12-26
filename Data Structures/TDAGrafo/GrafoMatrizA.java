package TDAGrafo;
import TDALista.*;

public class GrafoMatrizA<V,E> implements Graph<V,E>{
	
	protected PositionList<Vertex<V>> vertices;
	protected PositionList<Edge<E>> arcos;
	protected Edge<E> [][]	matriz;
	protected int cantVertices;
	
	@SuppressWarnings("unchecked")
	public GrafoMatrizA(int n) {
		vertices = new ListaDE<Vertex<V>>();
		arcos = new ListaDE<Edge<E>>();
		matriz = (Edge<E> [][]) new ArcoM[n][n];
		cantVertices = 0;
	}

	@Override
	public Iterable<Vertex<V>> vertices() { //O(n)
		PositionList<Vertex<V>> lista = new ListaDE<Vertex<V>>();
		for(Vertex<V> v : vertices)
			lista.addLast(v);
		return lista;
	}

	@Override
	public Iterable<Edge<E>> edges() { //O(m)
		PositionList<Edge<E>> lista = new ListaDE<Edge<E>>();
		for(Edge<E> e : arcos)
			lista.addLast(e);
		return lista;
	}

	@Override
	public Iterable<Edge<E>> incidentEdges(Vertex<V> v) throws InvalidVertexException { //O(n+deg(v))
		if(v==null) throw new InvalidVertexException("incidentEdges(); Vertice no valido");
		VerticeM<V> vv = (VerticeM<V>)v;
		int i = vv.getIndice();
		PositionList<Edge<E>> lista = new ListaDE<Edge<E>>();
		for(int j = 0; j<cantVertices; j++) {
			if(matriz[i][j] != null)
				lista.addLast(matriz[i][j]);
		}
		return lista;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Vertex<V> opposite(Vertex<V> v, Edge<E> e) throws InvalidVertexException, InvalidEdgeException { //O(1)
		if(v==null) throw new InvalidVertexException("opposite(); Vertice no valido");
		if(e==null) throw new InvalidEdgeException("opposite(); Arco no valido");
		ArcoM<V,E> ee = (ArcoM<V,E>) e;
		Vertex<V> vertice;
		if(ee.getV1()==v)
			vertice = ee.getV2();
		else if(ee.getV2()==v)
			vertice = ee.getV1();
		else throw new InvalidEdgeException("opposite(); Vertices y arco no relacionados");
		return vertice;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Vertex<V>[] endvertices(Edge<E> e) throws InvalidEdgeException { //O(1)
		if(e==null) throw new InvalidEdgeException("endVertices(); Arco no valido");
		Vertex<V>[] a = (Vertex<V> []) new VerticeM[2];
		ArcoM<V,E> ee = (ArcoM<V,E>)e;
		a[0] = ee.getV1();
		a[1] = ee.getV2();
		return a;
	}

	@Override
	public boolean areAdjacent(Vertex<V> v, Vertex<V> w) throws InvalidVertexException { //O(1)
		if(v==null || w==null) throw new InvalidVertexException("areAdjacent(); Alguno de los vertices no es valido");
		VerticeM<V> vv = (VerticeM<V>)v;
		VerticeM<V> ww = (VerticeM<V>)w;
		int i = vv.getIndice();
		int j = ww.getIndice();
		return matriz[i][j] != null;
	}

	@Override
	public V replace(Vertex<V> v, V x) throws InvalidVertexException { //O(1)
		if(v==null) throw new InvalidVertexException("replace(); Vertice no valido");
		VerticeM<V> vertice = (VerticeM<V>)v;
		V r = vertice.element();
		vertice.setRotulo(x);
		return r;
	}

	@Override
	public Vertex<V> insertVertex(V x) { //O(1)
		VerticeM<V> vv = new VerticeM<V>(x, cantVertices++);
		try {
			vertices.addLast(vv);
			vv.setPosEnVertices(vertices.last());
		} catch (EmptyListException e) {e.printStackTrace();}
		return vv;
	}

	@Override
	public Edge<E> insertEdge(Vertex<V> v, Vertex<V> w, E e) throws InvalidVertexException { //O(1)
		if(v==null || w==null) throw new InvalidVertexException("insertEdge(); Algun vertice es invalido");
		VerticeM<V> vv = (VerticeM<V>)v;
		VerticeM<V> ww = (VerticeM<V>)w;
		int fila = vv.getIndice();
		int col = ww.getIndice();
		ArcoM<V,E> arco = new ArcoM<V,E>(e,vv,ww);
		matriz[fila][col] = matriz[col][fila] = arco;
		arcos.addLast(arco);
		try {arco.setPosEnArcos(arcos.last());} catch (EmptyListException e1) {e1.printStackTrace();}
		return arco;
	}

	@Override
	public V removeVertex(Vertex<V> v) throws InvalidVertexException { //O(n+deg(v))
		if(v==null) throw new InvalidVertexException("removeVertex(); Vertice no valido");
		VerticeM<V> vertice = (VerticeM<V>)v;
		V rotulo = vertice.element();
		try {
			for(Edge<E> e : incidentEdges(v)) {
				removeEdge(e);
			}
			vertices.remove(vertice.getPosEnVertices());
			vertice.setIndice(null);
			vertice.setPosEnVertices(null);
			cantVertices--;
		}catch(InvalidEdgeException | InvalidPositionException e1) {e1.printStackTrace();}
		return rotulo;
	}

	@SuppressWarnings("unchecked")
	@Override
	public E removeEdge(Edge<E> e) throws InvalidEdgeException { //O(1)
		E elemento = null;
		if(e==null) throw new InvalidEdgeException("removeEdge(); Arco no valido");
		try {
			ArcoM<V,E> ee = (ArcoM<V,E>)e;
			int fila = ee.getV1().getIndice();
			int col = ee.getV2().getIndice();
			matriz[fila][col] = matriz[col][fila] = null;
			arcos.remove(ee.getPosEnArcos());
			elemento = e.element();
		}catch(InvalidPositionException e1) {e1.printStackTrace();}
		return elemento;
	}
}
