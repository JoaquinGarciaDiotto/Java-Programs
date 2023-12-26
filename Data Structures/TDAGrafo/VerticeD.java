package TDAGrafo;
import TDALista.*;

public class VerticeD<V,E> implements Vertex<V> {

	private V rotulo;
	private Position<VerticeD<V,E>> posEnListaVertices;
	private PositionList<ArcoD<V,E>> emergentes, incidentes;
	
	public VerticeD(V r) {
		rotulo = r;
		emergentes = new ListaDE<ArcoD<V,E>>();
		incidentes = new ListaDE<ArcoD<V,E>>();
	}
	
	public void setRotulo(V r) {
		rotulo = r;
	}
	public void setPosEnListaVertices(Position<VerticeD<V,E>> p) {
		posEnListaVertices = p;
	}
	public void setEmergentes(PositionList<ArcoD<V,E>> e) {
		emergentes = e;
	}
	public void setIncidentes(PositionList<ArcoD<V,E>> i) {
		incidentes = i;
	}
	
	
	@Override
	public V element() {
		return rotulo;
	}
	public Position<VerticeD<V,E>> getPosEnListaVertices(){
		return posEnListaVertices;
	}
	public PositionList<ArcoD<V,E>> getEmergentes(){
		return emergentes;
	}
	public PositionList<ArcoD<V,E>> getIncidentes(){
		return incidentes;
	}
}
