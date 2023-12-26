package ABB;

import java.util.Comparator;

import TDAArbol.BoundaryViolationException;
import TDAArbol.EmptyTreeException;
import TDAArbol.InvalidPositionException;
import TDAArbol.Position;
import TDAArbol.Tree;

public class ABB<E extends Comparable<E>> {
	protected NodoABB<E> raiz;
	protected Comparator<E> comparador;
	
	public ABB()	{
		raiz = new NodoABB<E>(null,null);
		comparador = new DefaultComparator<E>();
	}
	public ABB(Comparator<E> comp) {
		raiz = new NodoABB<E>(null,null);
		comparador = comp;
	}
	
	
	public NodoABB<E> raiz(){
		return raiz;
	}
	public NodoABB<E> buscar(E e){
		return buscarAux(e,raiz);
	}
	private NodoABB<E> buscarAux(E e, NodoABB<E> p){
		NodoABB<E> nodo = null;
		if(p.getElem()==null)
			nodo = p;
		else {
			int c = comparador.compare(e, p.getElem());
			if(c==0)
				nodo = p;
			else if(c<0)
				nodo = buscarAux(e,p.getIzq());
			else
				nodo = buscarAux(e,p.getIzq());
		}
		return nodo;
	}
	public void expandir(NodoABB<E> p, E e) {
		p.setElement(e);
		p.setDer(new NodoABB<E>(null,p));
		p.setIzq(new NodoABB<E>(null,p));
	}
	public void eliminar(E e) {
		NodoABB<E> nodo = buscar(e);
		if(nodo != null)
			eliminarAux(nodo);
	}
	private void eliminarAux(NodoABB<E> p) {
		if(isExternal(p)) {
			p.setElement(null);
			p.setIzq(null);
			p.setDer(null);
		}
		else {
			if(p==raiz) {
				if(soloIzquierdo(raiz)) {
					raiz.setElement(null);
					raiz = raiz.getIzq();
				}
				else if(soloDerecho(raiz)) {
					raiz.setElement(null);
					raiz = raiz.getDer();
				}
				else
					raiz.setElement(eliminarMinimo(raiz.getDer()));
			}
			else if(soloIzquierdo(p)) {
				if(p.getPadre().getIzq()==p)
					p.getPadre().setIzq(p.getIzq());
				else
					p.getPadre().setDer(p.getIzq());
				p.getIzq().setPadre(p.getPadre());
			}
			else if(soloDerecho(p)) {
				if(p.getPadre().getIzq()==p)
					p.getPadre().setIzq(p.getDer());
				else
					p.getPadre().setDer(p.getDer());
				p.getDer().setPadre(p.getPadre());
			}
			else 
				p.setElement(eliminarMinimo(p.getDer()));
		}
	}
	private E eliminarMinimo(NodoABB<E> p) {
		E min = null;
		if(p.getIzq().getElem()==null) {
			min = p.getElem();
			if(p.getDer().getElem()==null) {
				p.setElement(null);
				p.setIzq(null);
				p.setDer(null);
			}
			else {
				p.getPadre().setDer(p.getDer());
				p.getDer().setPadre(p.getPadre());
			}
		}
		else {
			min = eliminarMinimo(p.getIzq());
		}
		return min;
	}
	private boolean isExternal(NodoABB<E> p) { 
		return p.getIzq().getElem() == null && p.getDer().getElem() == null;
	}
	private boolean soloIzquierdo(NodoABB<E> p) {
		return p.getIzq().getElem() != null && p.getDer().getElem() == null;
	}
	private boolean soloDerecho(NodoABB<E> p) {
		return p.getDer().getElem() != null && p.getIzq().getElem() == null;
	}
	
	
	
	public void eliminarUltimoNodoHojaConCondicion(E r, Integer p, Tree<E> T) {
		eliminarAux(r, p, T, raiz);
	}
	private void eliminarAux(E r, Integer p, Tree<E> T, NodoABB<E> nodo) {
		try {
			if(nodo.getElem()!=null) {
				int c = comparador.compare(nodo.getElem(),r);
				if(c<=0) {
					for(Position<E> v : T.positions())
						if(v.element()==nodo.getElem() && profundidad(T,v)<p){
							Position<E> ultimoHijo = null;
							for(Position<E> hijos : T.children(v)) 
								ultimoHijo = hijos;
							if(ultimoHijo!=null && T.isExternal(ultimoHijo)) 
								T.removeExternalNode(ultimoHijo);
						}
					eliminarAux(r,p,T,nodo.getIzq());
					eliminarAux(r,p,T,nodo.getDer());
				}
				else
					eliminarAux(r,p,T,nodo.getIzq());
			}
		} catch (Exception e) {e.printStackTrace();}
	}
	private static <E> int profundidad(Tree<E> t, Position<E> p) {
		int profundidad = 0;
		try {
			if(!t.isRoot(p))
				profundidad = 1 + profundidad(t,t.parent(p));
		} catch (InvalidPositionException | BoundaryViolationException e) {e.printStackTrace();}
		return profundidad;
	}
}
