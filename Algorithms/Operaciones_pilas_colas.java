package Operaciones_con_pilas;

import TDACola.EmptyQueueException;
import TDAPila.*;
import TDACola.*;

public class Operaciones_pilas_colas {

	public static boolean chequear_formato(Queue<Character> q, char x) throws EmptyQueueException { //Entrada n: tamaño de la Cola. O(n)
		boolean es = true;
		if ((x != '\u0000') && !q.isEmpty()) { 
			int cant = 0, cant2 = 0;
			Cola_con_enlaces<Character> i = new Cola_con_enlaces<Character>(); 
			Queue<Character> p = new Cola_con_enlaces<Character>();  
			Character temp;
			while(!q.front().equals(x)) {
				cant++;
				temp = q.dequeue();
				p.enqueue(temp);
				i.enqueue(temp);
			}
			q.dequeue();
			i.invertir();
			cant2 = cant;
			while(cant2>0 && es) {
				if ((q.isEmpty() && cant2>0) || !i.dequeue().equals(q.dequeue())) //abc$cbaabc
					es = false;
				cant2--;
				}
			while(!q.isEmpty() && es) {
				if ((q.isEmpty() && cant>0)|| !p.dequeue().equals(q.dequeue()) )
					es = false;
				cant--;
			}
			if ((q.isEmpty() && cant2>0))
				es = false;
		}
		else
			es = false;
		return es;
	}
	
	public Stack<Queue<Integer>> aplanar_1(Stack<Queue<Integer>> p1, Stack<Queue<Integer>> p2) throws EmptyStackException { //O(n)
		Stack<Queue<Integer>> Out = new Pila_Con_Enlaces<Queue<Integer>>();
		invertir(p1);
		invertir(p2);
		while (p1.size()>0 && p2.size()>0) 
			if (p1.top().size()<p2.top().size())
				Out.push(p1.pop());
			else
				Out.push(p2.pop());
		if (!p1.isEmpty())
			while (!p1.isEmpty()) 
				Out.push(p1.pop());
		else
			while (!p2.isEmpty())
				Out.push(p2.pop());
		return Out;
	}
	private <E> void invertir(Stack<E> p) throws EmptyStackException {
		if (!p.isEmpty()) {
			E dato = p.pop();
			invertir(p);
			push_bottom(dato,p);
			}
	}
	private <E> void push_bottom(E dato, Stack<E> p) throws EmptyStackException {

		if (p.size() == 0)
			p.push(dato);
		else {
			E da = p.pop();
			push_bottom(dato,p);
			p.push(da);
			}
	}
	
	public Queue<Stack<Character>> aplanar_2(Queue<Stack<Character>> c1, Queue<Stack<Character>> c2) throws EmptyStackException, EmptyQueueException{
		Queue<Stack<Character>> Out = new Cola_con_enlaces<Stack<Character>>();
			while(!c1.isEmpty() && !c2.isEmpty()) 
				if (sizeP(c1.front())<sizeP(c2.front()))
					Out.enqueue(c1.dequeue());
				else
					Out.enqueue(c2.dequeue());
			if (!c1.isEmpty())
				while (!c1.isEmpty())
					Out.enqueue(c1.dequeue());
			else
				while (!c2.isEmpty())
					Out.enqueue(c2.dequeue());
		return Out;
	}
	private <E> int sizeP(Stack<Character> p) throws EmptyStackException {
		int cant = 0;
		if (!p.isEmpty()) {
			Character dato = p.pop();
			cant = 1+sizeP(p);
			p.push(dato);
		}
		return cant;
	}
	
	
	@SuppressWarnings("null")
	public static void chequear_cadena(String cad){
		Queue<Character> A;
		Cola_con_enlaces<Character> C = new Cola_con_enlaces<Character>();
		Cola_con_enlaces<Character> inv = new Cola_con_enlaces<Character>();
		boolean es = true;
		try {
		if (cad != null || !cad.isEmpty()) {
			int i = 0; int j;
			while(es && i<cad.length()){
				CompletarC(C,cad,i);
				A = CompletarA(C);
				invertir(A,inv, A.size());
				j = A.size(); Character f1;
				while(es && j>0) {
					if (cad.charAt(i) != A.front() || i==cad.length()) {
						es = false;
					}
					else {
						f1 = A.dequeue();
						A.enqueue(f1);
						i++;
						j--;
					}
				}
				if (cad.charAt(i) != 'x' || (j==0 && cad.charAt(i) != 'x'))
					es = false;
				else
					i++;
				Character f2;
				j = inv.size();
				while(es && i<cad.length() && j>0) {
					if (cad.charAt(i) != inv.front())
						es = false;
					else {
						f2 = inv.dequeue();
						inv.enqueue(f2);
						j--;
						i++;
					}
				}
				if (j==0 && i<cad.length() && cad.charAt(i) == 'x') 
					i++;
				A = null;
				C =  new Cola_con_enlaces<Character>();
				inv = new Cola_con_enlaces<Character>();
			}
		}
		else
			es = false;
		}
		catch(EmptyQueueException e) {e.printStackTrace();}
		System.out.print(es);
	}
	private static String ToString(Queue<Character> c) throws EmptyQueueException {
		String s = ""; int i = c.size(); Character c1;
		while(i>0) {
			c1 = c.dequeue();
			s += c1+" ";
			c.enqueue(c1);
			i--;
		}
		return s;
	}
	
	private static void invertir(Queue<Character> A, Queue<Character> inv, int n) throws EmptyQueueException {
		if (n>0) {
			Character temp = A.dequeue();
			A.enqueue(temp);
			invertir(A,inv,n-1);
			inv.enqueue(temp);
			}
	}
	
	private static Cola_con_enlaces<Character> CompletarA(Cola_con_enlaces<Character> C) throws EmptyQueueException { //O(n)
		Cola_con_enlaces<Character> A = new Cola_con_enlaces<Character>();
		Character temp; int i = C.size();
			while(i>0) {
				temp = C.dequeue();
				A.enqueue(temp);
				C.enqueue(temp);
				i--;
			}
			A.enqueue('z');
			i = C.size()*2;
			while(i>0) {
				temp = C.dequeue();
				A.enqueue(temp);
				C.enqueue(temp);
				i--;
			}
		return A;
	}
	
	private static void CompletarC(Cola_con_enlaces<Character> C, String cad, int i){
		while(i<cad.length() && (cad.charAt(i) != 'z')) {
				C.enqueue(cad.charAt(i));
				i++;
			}
	}
	
	public static void main(String a[]) throws EmptyQueueException {
		chequear_cadena("bzbbxbbzb");
	}
}
