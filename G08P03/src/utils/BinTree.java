package utils;

import java.util.HashSet;
import java.util.Set;

public class BinTree<T> {
	
	private T dato;
	private BinTree<T> izq;
	private BinTree<T> der;
	
	public BinTree() {
	}
	
	public BinTree(T dato) {
		this.dato = dato;
	}
	
	public  BinTree<T> copiarArbol(BinTree<T> arbol) {
        if (arbol == null) {
            return null;
        }
 
        BinTree<T> raiz_copy = new BinTree<T>(arbol.dato);
 
        raiz_copy.izq = copiarArbol(arbol.izq);
        raiz_copy.der = copiarArbol(arbol.der);
 
        return raiz_copy;
	}
	
	public void setArbol(BinTree<T> arbol) {
		if (arbol == null) {
            return;
        }
 
		this.dato = arbol.dato;
		
		setArbolIzq(arbol.izq);
		setArbolDer(arbol.der);

	}
	
	public void setArbolIzq(BinTree izq) {
		this.izq = izq;
	}
	
	public void setArbolDer(BinTree der) {
		this.der = der;
	}
	
	public T getDato(){
		return dato;
	}
	
	public BinTree<T> getIzq() {
		return izq;
	}
	
	public BinTree<T> getDer() {
		return der;
	}

	public String decodificar() {
		return arbol_a_string((BinTree<String>)this);
	}
	
	public static final Set<String> operadores = new HashSet<String>() {{add("add"); add("mul"); add("sub");}};
	
	public static final Set<String> terminales = new HashSet<String>() {{add("x"); add("1"); add("2"); add("0"); add("-1"); add("-2");}};
		
	private String arbol_a_string(BinTree<String> arbol) {
	    if(terminales.contains(arbol.getDato()))
	        return arbol.getDato();
	    else {
	        String iz = arbol_a_string(arbol.getIzq());
	        String der = arbol_a_string(arbol.getDer());
	        if(arbol.getDato().equals("add"))
	            return "(" + iz + " + " + der + ")";
	        else if (arbol.getDato().equals("sub"))
	            return "(" + iz + " - " + der + ")";
	        else 
	            return "(" + iz + " * " + der + ")";
	    }
	}
	
	public void setDato(T dato) {
		this.dato = dato;
	}

	public int getNumNodos() {
		if(terminales.contains(dato)) {
			return 1;
		}
		return (izq.getNumNodos() + der.getNumNodos() + 1);
	}

	public void adaptar(int profundidadAct, int profundiadMax) {
		
	}
}

