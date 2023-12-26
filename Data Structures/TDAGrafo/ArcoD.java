package TDAGrafo;
import TDALista.*;


public class ArcoD<V,E> implements Edge<E> {

	private E rotulo;
	private VerticeD<V,E> v1,v2; //v1 cola, v2 punta
	private Position<ArcoD<V,E>> posEnEmergentes, posEnIncidentes, posEnArcos;
	
	public ArcoD(E r, VerticeD<V,E> v1, VerticeD<V,E> v2) {
		rotulo = r;
		this.v1 = v1;
		this.v2 = v2;
	}
	
	public void setRotulo(E r) {
		rotulo = r;
	}
	public void setV1(VerticeD<V,E> v) {
		v1 = v;
	}
	public void setV2(VerticeD<V,E> v) {
		v2 = v;
	}
	public void setPosEnEmergentes(Position<ArcoD<V,E>> p) {
		posEnEmergentes = p;
	}
	public void setPosEnIncidentes(Position<ArcoD<V,E>> p) {
		posEnIncidentes = p;
	}
	public void setPosEnArcos(Position<ArcoD<V,E>> p) {
		posEnArcos = p;
	}
	
	@Override
	public E element() {
		return rotulo;
	}
	public VerticeD<V,E> getV1(){
		return v1;
	}
	public VerticeD<V,E> getV2(){
		return v2;
	}
	public Position<ArcoD<V,E>> getPosEnEmergentes(){
		return posEnEmergentes;
	}
	public Position<ArcoD<V,E>> getPosEnIncidentes(){
		return posEnIncidentes;
	}
	public Position<ArcoD<V,E>> getPosEnArcos(){
		return posEnArcos;
	}
}
