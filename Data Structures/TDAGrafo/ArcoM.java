package TDAGrafo;
import TDALista.*;

public class ArcoM<V,E> implements Edge<E> {
	private Position<Edge<E>> posEnArcos;
	private VerticeM<V> v1, v2;
	private E rotulo;
	
	public ArcoM(E r, VerticeM<V> v1, VerticeM<V> v2) {
		rotulo = r;
		this.v1 = v1;
		this.v2 = v2;
	}
	
	public VerticeM<V> getV1(){
		return v1;
	}
	public VerticeM<V> getV2(){
		return v2;
	}
	public Position<Edge<E>> getPosEnArcos(){
		return posEnArcos;
	}
	public E element() {
		return rotulo;
	}
	public void setRotulo(E r) {
		rotulo = r;
	}
	public void setV1(VerticeM<V> v) {
		v1=v;
	}
	public void setV2(VerticeM<V> v) {
		v2=v;
	}
	public void setPosEnArcos(Position<Edge<E>> p) {
		posEnArcos = p;
	}
}
