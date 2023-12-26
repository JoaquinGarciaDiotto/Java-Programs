package TDAGrafo;
import TDALista.*;

public class VerticeM<V> implements Vertex<V>{
	private Position<Vertex<V>> posEnVertices;
	private V rotulo;
	private int indice;
	
	public VerticeM(V r, int i) {
		rotulo = r;
		indice = i;
	}
	
	public void setIndice(Integer i){
		indice = i;
	}
	public void setRotulo(V nuevoRotulo) {
		rotulo = nuevoRotulo;
	}
	public void setPosEnVertices(Position<Vertex<V>> p) {
		posEnVertices = p;
	}
	
	public int getIndice()	{
		return indice;
	}
	public Position<Vertex<V>> getPosEnVertices(){
		return posEnVertices;
	}
	public V element() {
		return rotulo;
	}
}
