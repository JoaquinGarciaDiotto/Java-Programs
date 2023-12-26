package Operaciones_con_pilas;

import TDAPila.*;

public class Operaciones_con_pilas {
	
	public static void invertir_arreglo(int [] a) { //O(n)
		Pila_Con_Enlaces<Integer> p = new Pila_Con_Enlaces<Integer>();
		for (int i = 0; i<a.length; i++) {
			p.push(a[i]);
		}
		for (int i = 0; i<a.length; i++) {
				try {
					a[i] = p.pop();
				} catch (EmptyStackException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
	public static <E> void invertir(Stack<E> p) { // O(n)
		
		Stack<E> p2 = new Pila_Con_Enlaces<E>();
		for (int i = 0; i<p.size(); i++) {
			try {
				p2.push(p.pop());
			} catch (EmptyStackException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		p = p2;
	}
	
	public <E> Stack<Integer> aplanar(Stack<Stack<Integer>> ppe) throws EmptyStackException{ //O(n^2)
		Stack<Integer> pe = new Pila_Con_Enlaces<Integer>();
		for (int i = 0; i<ppe.size(); i++) {
			Stack<Integer> temp = ppe.top();
			for (int j = 0; j<temp.size(); j++) {
				pe.push(temp.pop());
			}
		}
		invertir(pe);
		return pe;
	}
}
