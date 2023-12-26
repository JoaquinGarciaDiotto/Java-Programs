package TDAGrafo;
import TDALista.*;


public class Vertice<V,E> implements Vertex<V>{
	private V rotulo;
	private PositionList<Arco<V,E>> adyacentes;
	private Position<Vertice<V,E>> posEnNodos;
	
	public Vertice(V rotulo) {
		this.rotulo = rotulo;
		adyacentes = new ListaDE<Arco<V,E>>();
	}

	public void setRotulo(V nuevoRotulo) {
		rotulo = nuevoRotulo;
	}
	public void setPosEnNodos(Position<Vertice<V,E>> p) {
		posEnNodos = p;
	}
	
	@Override
	public V element() {
		return rotulo;
	}
	public Position<Vertice<V,E>> getPosEnNodos(){
		return posEnNodos;
	}
	public PositionList<Arco<V,E>> getAdyacentes()	{
		return adyacentes;
	}
}
