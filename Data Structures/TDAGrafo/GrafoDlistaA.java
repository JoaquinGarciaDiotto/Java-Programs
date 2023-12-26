package TDAGrafo;
import TDALista.*;

public class GrafoDlistaA<V,E> implements GraphD<V,E> {
	
	protected PositionList<VerticeD<V,E>> nodos;
	protected PositionList<ArcoD<V,E>> arcos;
	
	public GrafoDlistaA() {
		nodos = new ListaDE<VerticeD<V,E>>();
		arcos = new ListaDE<ArcoD<V,E>>();
	}

	@Override
	public Iterable<Vertex<V>> vertices() { //O(n)
		PositionList<Vertex<V>> lista = new ListaDE<Vertex<V>>();
		for(Vertex<V> v : nodos)
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

	@SuppressWarnings("unchecked")
	@Override
	public Iterable<Edge<E>> incidentEdges(Vertex<V> v) throws InvalidVertexException { //O(m)
		if(v==null) throw new InvalidVertexException("incidentEdges(); Vertice no valido");
		PositionList<Edge<E>> lista = new ListaDE<Edge<E>>();
		for(Edge<E> e : ((VerticeD<V,E>)v).getIncidentes())
			lista.addLast(e);
		return lista;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Iterable<Edge<E>> succesorEdges(Vertex<V> v) throws InvalidVertexException { //Por que no es emergentEdges???? //O(m)
		if(v==null) throw new InvalidVertexException("succesorEdges(); Vertice no valido");
		PositionList<Edge<E>> lista = new ListaDE<Edge<E>>();
		for(Edge<E> e : ((VerticeD<V,E>)v).getEmergentes())
			lista.addLast(e);
		return lista;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Vertex<V> opposite(Vertex<V> v, Edge<E> e) throws InvalidVertexException, InvalidEdgeException { //O(1)
		if(v==null) throw new InvalidVertexException("opposite(); Vertice no valido");
		if(e==null) throw new InvalidEdgeException("opposite(); Arco no valido");
		ArcoD<V,E> arco = (ArcoD<V,E>) e;
		Vertex<V> vertice;
		if(arco.getV1()==v)
			vertice = arco.getV2();
		else if(arco.getV2()==v)
				vertice = arco.getV1();
		else 
			throw new InvalidEdgeException("opposite(); Vertice y arco no relacionados");
		return vertice;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Vertex<V>[] endvertices(Edge<E> e) throws InvalidEdgeException { //O(1)
		if(e==null) throw new InvalidEdgeException("endVertices(); Arco no valido");
		Vertex<V>[] vertices = (Vertice<V,E>[]) new Vertex[2];
		ArcoD<V,E> arco = (ArcoD<V,E>) e;
		vertices[0] = arco.getV1();
		vertices[1] = arco.getV2();
		return vertices;
	}

	@SuppressWarnings("unchecked")
	@Override //Si w es adyacente a v, existe un arco de v a w.
	public boolean areAdjacent(Vertex<V> v, Vertex<V> w) throws InvalidVertexException { //O(deg(v))
		if(v==null || w==null) throw new InvalidVertexException("areAdjacent(); Uno de los vertices ingreados no es valido");
		boolean son = false;
		for(Edge<E> a : ((VerticeD<V,E>)v).getEmergentes())
			son = ((ArcoD<V,E>)a).getV2() == w; //v2 seria la punta de un arco emergente de v.
		return son;
	}

	@SuppressWarnings("unchecked")
	@Override
	public V replace(Vertex<V> v, V x) throws InvalidVertexException { //O(1)
		if(v==null) throw new InvalidVertexException("replace(); Vertice no valido");
		VerticeD<V,E> vertice = (VerticeD<V,E>)v;
		V rotulo = vertice.element();
		vertice.setRotulo(x);
		return rotulo;
	}

	@Override
	public Vertex<V> insertVertex(V x) {
		VerticeD<V,E> v = new VerticeD<V,E>(x);
		nodos.addLast(v);
		try {v.setPosEnListaVertices(nodos.last());} catch (EmptyListException e) {e.printStackTrace();}
		return v;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Edge<E> insertEdge(Vertex<V> v, Vertex<V> w, E e) throws InvalidVertexException { //O(1)
		if(v==null || w==null) throw new InvalidVertexException("insertEdge(); Uno de los vertices no es valido");
		VerticeD<V,E> vv = (VerticeD<V,E>)v;
		VerticeD<V,E> ww = (VerticeD<V,E>)w;
		ArcoD<V,E> arco = new ArcoD<V,E>(e,vv,ww);
		try {
			vv.getEmergentes().addLast(arco);
			arco.setPosEnEmergentes(vv.getEmergentes().last()); 
			ww.getIncidentes().addLast(arco);
			arco.setPosEnIncidentes(ww.getIncidentes().last());
			arcos.addLast(arco);
			arco.setPosEnArcos(arcos.last());
		}catch (EmptyListException e1) {e1.printStackTrace();}
		return arco;
	}

	@SuppressWarnings("unchecked")
	@Override
	public V removeVertex(Vertex<V> v) throws InvalidVertexException { //O(deg(v))
		if(v==null) throw new InvalidVertexException("removeVertex(); Vertice no valido");
		VerticeD<V,E> vert = ((VerticeD<V,E>)v);
		V rotulo = vert.element();
		try {
			for(Edge<E> a : vert.getEmergentes())
				removeEdge(a);
			for(Edge<E> a : vert.getIncidentes())
				removeEdge(a);
			Position<VerticeD<V,E>> pos = vert.getPosEnListaVertices();
			nodos.remove(pos);
		}catch(InvalidPositionException | InvalidEdgeException e) {e.printStackTrace();}
		return rotulo;
	}

	@SuppressWarnings("unchecked")
	@Override
	public E removeEdge(Edge<E> e) throws InvalidEdgeException { //O(1) Esta bien?
		if(e==null) throw new InvalidEdgeException("removeEdge(); Arco no valido");
		ArcoD<V,E> ee = (ArcoD<V,E>)e;
		E rotulo = ee.element();
		try { 
			VerticeD<V,E> v1 = ee.getV1();
			VerticeD<V,E> v2 = ee.getV2();
			v1.getIncidentes().remove(ee.getPosEnIncidentes());
			v1.getEmergentes().remove(ee.getPosEnEmergentes());
			v2.getIncidentes().remove(ee.getPosEnIncidentes());
			v2.getEmergentes().remove(ee.getPosEnEmergentes());
			Position<ArcoD<V,E>> pee = ee.getPosEnArcos();
			arcos.remove(pee);
		}catch(InvalidPositionException e1) {e1.printStackTrace();}
		return rotulo;
	}
	
	
}
