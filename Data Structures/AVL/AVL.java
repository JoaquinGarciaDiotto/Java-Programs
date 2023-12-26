package AVL;

import java.util.Comparator;

public class AVL<E extends Comparable<E>>{
	
	private NodoAVL<E> raiz;
	private Comparator<E> comp;
	
	public AVL(Comparator<E> comp) {
		raiz = new NodoAVL<E>(null);
		this.comp = comp;
	}
	public AVL() {
		raiz = new NodoAVL<E>(null);
		comp = new DefaultComparator<E>();
	}
	public NodoAVL<E> raiz(){
		return raiz;
	}
	public NodoAVL<E> buscar(E e){
		return buscarAux(e,raiz);
	}
	private NodoAVL<E> buscarAux(E e, NodoAVL<E> p){
		NodoAVL<E> nodo = null;
		if(p.getElem()!=null) {
			int c = comp.compare(e, p.getElem());
			if(c==0)
				if(!p.eliminado())
					nodo = p;
				else
					nodo = null;
			else if(c<0)
				nodo = buscarAux(e,p.getIzq());
			else
				nodo = buscarAux(e,p.getDer());
		}
		return nodo;
	}
	public void insert(E x) {
		insertAux(raiz, x);
	}
	private int max(int i, int j) {
		return (i>j)? i : j;
	}
	private void insertAux(NodoAVL<E> p, E item) {
		if(p.getElem()==null) {				
			p.setElement(item);
			p.setAltura(1);
			p.setIzq(new NodoAVL<E>(null));
			p.getIzq().setPadre(p);
			p.setDer(new NodoAVL<E>(null));
			p.getDer().setPadre(p);
		} else {
			int comparacion = comp.compare(item, p.getElem());
			if(comparacion==0) {
				p.setEliminado(false);
			}
			else if(comparacion<0) {				//Item<X, Si El item que estoy insertando es menor al de el nodo actual, tengo que ir a la izquierda
					insertAux(p.getIzq(),item);
					if(Math.abs(p.getIzq().getAltura()-p.getDer().getAltura())>1) { //p==x
						E y = p.getIzq().getElem();
						int compItemY = comp.compare(item, y);
						if(compItemY<0)
							rotacion1(p);
						else
							rotacion2(p);
					}
				} else {					//Item>X
					insertAux(p.getDer(),item);	
					if(Math.abs(p.getIzq().getAltura()-p.getDer().getAltura())>1) {		
						E y = p.getDer().getElem();
						int compItemY = comp.compare(item , y);
						if(compItemY<0)
							rotacion4(p);
						else
							rotacion3(p);
					}
				}
			p.setAltura(max(p.getIzq().getAltura(),p.getDer().getAltura())+1);
		}
	}
	private void rotacion1(NodoAVL<E> p) {	//Rotacion simple izquierda a derecha
		NodoAVL<E> y = p.getIzq();
		NodoAVL<E> px = p.getPadre();
		if(px!=null) {				//Si tiene padre (Si no es la raiz).
			if(px.getDer().equals(p))
				px.setDer(y);
			else if(px.getIzq().equals(p))
				px.setIzq(y);
			}
		else
			raiz = y;
		y.setPadre(px);
		p.setIzq(y.getDer());
		y.getDer().setPadre(p);
		p.setPadre(y);
		y.setDer(p);
		p.setAltura(max(p.getIzq().getAltura(),p.getDer().getAltura())+1);
		y.setAltura(max(y.getIzq().getAltura(),y.getDer().getAltura())+1);
	}
	private void rotacion2(NodoAVL<E> p) {	//Rotacion doble izquierda a derecha. Rotacion doble derecha(Double rotate right)
		NodoAVL<E> y = p.getIzq();
		NodoAVL<E> z = y.getDer();
		NodoAVL<E> px = p.getPadre();
		if(px!=null) {
			if(px.getDer().equals(p))
				px.setDer(z);
			else if(px.getIzq().equals(p))
				px.setIzq(z);
			}
		 else
			raiz = z;
		z.setPadre(px);
		y.setPadre(z);
		p.setPadre(z);
		p.setIzq(z.getDer());
		z.getDer().setPadre(p);
		y.setDer(z.getIzq());
		z.getIzq().setPadre(y);
		z.setDer(p);
		z.setIzq(y);
		p.setAltura(max(p.getIzq().getAltura(),p.getDer().getAltura())+1);
		y.setAltura(max(y.getIzq().getAltura(),y.getDer().getAltura())+1);
		z.setAltura(max(z.getIzq().getAltura(),z.getDer().getAltura())+1);
	}
	private void rotacion3(NodoAVL<E> p) { //Rotacion simple derecha a izquierda
		NodoAVL<E> y = p.getDer();
		NodoAVL<E> px = p.getPadre();
		if(px!=null) { 
			if(px.getDer().equals(p))
				px.setDer(y);
			else if(px.getIzq().equals(p))
				px.setIzq(y);
			}
		 else
			raiz = y;
		y.setPadre(px);
		p.setDer(y.getIzq());
		y.getIzq().setPadre(p);
		p.setPadre(y);
		y.setIzq(p);
		p.setAltura(max(p.getIzq().getAltura(),p.getDer().getAltura())+1);
		y.setAltura(max(y.getIzq().getAltura(),y.getDer().getAltura())+1);
	}
	private void rotacion4(NodoAVL<E> p) { //Rotacion doble derecha a izquierda. Rotacion doble izquierda(Double rotate left)
		NodoAVL<E> y = p.getDer();
		NodoAVL<E> z = y.getIzq();
		NodoAVL<E> px = p.getPadre();
		if(px!=null) {
			if(px.getDer().equals(p))
				px.setDer(z);
			else if(px.getIzq().equals(p))
				px.setIzq(z);
			}
		 else
			raiz = z;
		z.setPadre(px);
		y.setPadre(z);
		p.setPadre(z);
		p.setDer(z.getIzq());
		z.getIzq().setPadre(p);
		y.setIzq(z.getDer());
		z.getDer().setPadre(y);
		z.setIzq(p);
		z.setDer(y);
		p.setAltura(max(p.getIzq().getAltura(),p.getDer().getAltura())+1);
		y.setAltura(max(y.getIzq().getAltura(),y.getDer().getAltura())+1);
		z.setAltura(max(z.getIzq().getAltura(),z.getDer().getAltura())+1);
	}
	public void remove(NodoAVL<E> n) {
		n.setEliminado(true);
	}

}
