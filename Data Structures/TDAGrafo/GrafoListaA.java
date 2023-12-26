package TDAGrafo;
import java.util.Iterator;

import TDALista.*;
import TDAMapeo.*;
import TDACola.*;

public class GrafoListaA<V,E> implements Graph<V,E> {
	
	protected PositionList<Vertice<V,E>> nodos;
	protected PositionList<Arco<V,E>> arcos;
	
	public GrafoListaA() {
		nodos = new ListaDE<Vertice<V,E>>();
		arcos = new ListaDE<Arco<V,E>>();
	}

	@Override
	public Iterable<Vertex<V>> vertices() { //(n=cant vertices, m cant arcos, T(n,m) = O(n)
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
	public Iterable<Edge<E>> incidentEdges(Vertex<V> v) throws InvalidVertexException { //T(n,m) = O(deg(v))
		if(v==null) throw new InvalidVertexException("incidentEdges(); Vertice no valido");
		PositionList<Edge<E>> lista = new ListaDE<Edge<E>>();
		Vertice<V,E> vertice = (Vertice<V,E>) v;
		for(Edge<E> e : vertice.getAdyacentes())
			lista.addLast(e);
		return lista;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Vertex<V> opposite(Vertex<V> v, Edge<E> e) throws InvalidVertexException, InvalidEdgeException { //O(1)
		if(v==null) throw new InvalidVertexException("opposite(); Vertice no valido");
		if(e==null) throw new InvalidEdgeException("opposite(); Arco no valido");
		Arco<V,E> arco = (Arco<V,E>) e;
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
		Arco<V,E> arco = (Arco<V,E>) e;
		vertices[0] = arco.getV1();
		vertices[1] = arco.getV2();
		return vertices;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean areAdjacent(Vertex<V> v, Vertex<V> w) throws InvalidVertexException { //O(deg(v))
		if(v==null || w==null) throw new InvalidVertexException("areAdjacent(); Uno de los vertices es invalido");
		boolean son = false;
		Iterator<Edge<E>> it =  incidentEdges(v).iterator();
		Arco<V,E> arco = null;
		while(it.hasNext() && !son)
			arco = (Arco<V,E>)it.next();
			son = (arco.getV1().equals(w) || (arco.getV2().equals(w)));
		return son;
	}

	@SuppressWarnings("unchecked")
	@Override
	public V replace(Vertex<V> v, V x) throws InvalidVertexException { //O(1)
		if(v==null) throw new InvalidVertexException("replace(); Vertice no valido");
		Vertice<V,E> vertice = (Vertice<V,E>)v;
		V rotulo = vertice.element();
		vertice.setRotulo(x);
		return rotulo;
	}

	@Override
	public Vertex<V> insertVertex(V x) { //O(1)
		Vertice<V,E> v = new Vertice<V,E>(x);
		nodos.addLast(v);
		try {v.setPosEnNodos(nodos.last());} catch (EmptyListException e) {e.printStackTrace();}
		return v;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Edge<E> insertEdge(Vertex<V> v, Vertex<V> w, E e) throws InvalidVertexException { //O(1)
		if(v==null || w==null) throw new InvalidVertexException("insertEdge(); Uno de los vertices no es valido");
		Vertice<V,E> vv = (Vertice<V,E>)v;
		Vertice<V,E> ww = (Vertice<V,E>)w;
		Arco<V,E> arco = new Arco<V,E>(e,vv,ww);
		try {
			vv.getAdyacentes().addLast(arco);
			arco.setPosEnLv1(vv.getAdyacentes().last()); 
			ww.getAdyacentes().addLast(arco);
			arco.setPosEnLv2(ww.getAdyacentes().last());
			arcos.addLast(arco);
			arco.setPosEnArcos(arcos.last());
		}catch (EmptyListException e1) {e1.printStackTrace();}
		return arco;
	}

	@SuppressWarnings("unchecked")
	@Override
	public V removeVertex(Vertex<V> v) throws InvalidVertexException { //O(deg(v))
		if(v==null) throw new InvalidVertexException("removeVertex(); Vertice no valido");
		Vertice<V,E> vert = (Vertice<V,E>) v;
		V rotulo = vert.element();
		try {
			for(Edge<E> a : vert.getAdyacentes())
				removeEdge(a);
			nodos.remove(vert.getPosEnNodos());
		}catch(InvalidPositionException | InvalidEdgeException e) {e.printStackTrace();}
		return rotulo;
	}

	@SuppressWarnings("unchecked")
	@Override
	public E removeEdge(Edge<E> e) throws InvalidEdgeException { //O(1)
		if(e==null) throw new InvalidEdgeException("removeEdge(); Arco no valido");
		Arco<V,E> ee = (Arco<V,E>)e;
		E rotulo = ee.element();
		try { 
			Vertice<V,E> v1 = ee.getV1();
			Vertice<V,E> v2 = ee.getV2();
			v1.getAdyacentes().remove(ee.getPosEnLv1());
			v2.getAdyacentes().remove(ee.getPosEnLv2());
			Position<Arco<V,E>> pee = ee.getPosEnArcos();
			arcos.remove(pee);
		}catch(InvalidPositionException e1) {e1.printStackTrace();}
		return rotulo;
	}
	
	public void dfs() { //O(n+m)
		Map<Vertex<V>,Boolean> m = new MapeoConLista<Vertex<V>,Boolean>();
		try {
			for(Vertex<V> v : nodos)
				m.put(v, false);
			for(Vertex<V> v : nodos) 
				if(!m.get(v))
					dfsAux(v,m);
		} catch (InvalidKeyException e) {e.printStackTrace();}
	}
	@SuppressWarnings("unchecked")
	private void dfsAux(Vertex<V> v, Map<Vertex<V>,Boolean> m) {
		System.out.println(v.element());
		try {
			m.put(v, true);
			for(Edge<E> a : ((Vertice<V,E>)v).getAdyacentes()) {
				Vertex<V> w = opposite(v,a);
				if(!m.get(w))
					dfsAux(w,m);
			}
		} catch (InvalidKeyException | InvalidVertexException | InvalidEdgeException e) {e.printStackTrace();}
	}
	
	public void bfs() { //O(n+m)
		Map<Vertex<V>,Boolean> m = new MapeoConLista<Vertex<V>,Boolean>();
		try {
			for(Vertex<V> v : nodos) 
				m.put(v, false);
			for(Vertex<V> v : nodos) 
				if(!m.get(v))
					bfsAux(v,m);
			} catch (InvalidKeyException e) {e.printStackTrace();}
	}
	@SuppressWarnings("unchecked")
	private void bfsAux(Vertex<V> v, Map<Vertex<V>,Boolean> m) {
		Queue<Vertex<V>> q = new Cola_con_enlaces<Vertex<V>>();
		q.enqueue(v);
		try {
			m.put(v, true);
			while(!q.isEmpty()) {
				Vertex<V> u = q.dequeue();
				System.out.println(u.element());
				for(Edge<E> a : ((Vertice<V,E>)u).getAdyacentes()) {
					Vertex<V> x = opposite(u,a);
					if(!m.get(x)) {
						m.put(x, true);
						q.enqueue(x);
					}
				}
			}
		} catch (InvalidKeyException | EmptyQueueException | InvalidVertexException | InvalidEdgeException e) {e.printStackTrace();}
	}
	
	public void eliminar_rotulo(V r) {  //O(n+m)
		for(Vertex<V> v : nodos) {
			if(v.element()==r)
				try {
					removeVertex(v);
				} catch (InvalidVertexException e) {e.printStackTrace();}
		}
	}
}
