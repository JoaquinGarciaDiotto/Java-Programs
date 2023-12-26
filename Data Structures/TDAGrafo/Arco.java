package TDAGrafo;
import TDALista.Position;

public class Arco<V,E> implements Edge<E>{
	private E rotulo;
	private Vertice<V,E> v1,v2;
	private Position<Arco<V,E>> posEnArcos;
	private Position<Arco<V,E>> posEnLv1, posEnLv2;
	
	public Arco(E rotulo, Vertice<V,E> v1, Vertice<V,E> v2) {
		this.rotulo = rotulo;
		this.v1 = v1;
		this.v2 = v2;
	}
	
	public void setRotulo(E r) {
		rotulo = r;
	}
	public void setV1(Vertice<V,E> v) {
		v1=v;
	}
	public void setV2(Vertice<V,E> v) {
		v2=v;
	}
	public void setPosEnArcos(Position<Arco<V,E>> p) {
		posEnArcos = p;
	}
	public void setPosEnLv1(Position<Arco<V,E>> p) {
		posEnLv1 = p;
	}
	public void setPosEnLv2(Position<Arco<V,E>> p) {
		posEnLv2 = p;
	}
	
	@Override
	public E element() {
		return rotulo;
	}
	public Vertice<V,E> getV1(){
		return v1;
	}
	public Vertice<V,E> getV2(){
		return v2;
	}
	public Position<Arco<V,E>> getPosEnArcos(){
		return posEnArcos;
	}
	public Position<Arco<V,E>> getPosEnLv1(){
		return posEnLv1;
	}
	public Position<Arco<V,E>> getPosEnLv2(){
		return posEnLv2;
	}
}
