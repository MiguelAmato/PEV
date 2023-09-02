package model.cruce;

import java.util.Random;

import model.individuos.Individuo;
import model.individuos.IndividuoArbol;
import utils.BinTree;
import utils.Pair;

public class CruceOperador extends Cruce{

	private final double PROB_FUNC = 0.9;
	private final double PROB_LADO = 0.5;
	
	@Override
	protected <T> Pair<T,T> cruceAux(Individuo padre1, Individuo padre2) {
		
		Random r = new Random();
		
		BinTree<String> cPadre1 = new BinTree<String>();
		cPadre1 = cPadre1.copiarArbol((BinTree<String>) padre1.getCromosoma());
		BinTree<String> cPadre2 = new BinTree<String>();
		cPadre2 = cPadre2.copiarArbol((BinTree<String>) padre2.getCromosoma());
 		 
		BinTree<String> punto1;
		BinTree<String> punto2;

		if(r.nextDouble(0,1) < PROB_LADO) {
			punto1 = cPadre1.getIzq();
		}
		else {
			punto1 = cPadre1.getDer();
		}
		
		while(punto1.getIzq() != null) { // si no tiene hijo izq o der es terminal
		
			if(r.nextDouble(0,1)< PROB_FUNC) {
				break;
			}
			
			else {
				if(r.nextDouble(0,1) < PROB_LADO) {
					punto1 = punto1.getIzq();
				}
				else {
					punto1 = punto1.getDer();
				}
				
			}
		}
		
		if(r.nextDouble(0,1) < PROB_LADO) {
			punto2 = cPadre2.getIzq();
		}
		else {
			punto2 = cPadre2.getDer();
		}
		
		while(punto2.getIzq() != null) { // si no tiene hijo izq o der es terminal
		
			if(r.nextDouble(0,1)< PROB_FUNC) {
				break;
			}
			
			else {
				if(r.nextDouble(0,1) < PROB_LADO) {
					punto2 = punto2.getIzq();
				}
				else {
					punto2 = punto2.getDer();
				}
				
			}
		}
		
		BinTree<String> aux = new BinTree<>();
		aux = aux.copiarArbol(punto1);
	//	punto1 = punto1.copiarArbol(punto2);
//		punto2 = punto2.copiarArbol(aux);

		punto1.setArbol(punto2);
		punto2.setArbol(aux);
	
		return (Pair<T, T>) new Pair<BinTree<String>,BinTree<String>>(cPadre1,cPadre2);

	}
	
	public static void main(String[] args) {
		
		
		IndividuoArbol a = new IndividuoArbol("COMPLETO",4);
		IndividuoArbol b = new IndividuoArbol("COMPLETO",4);

		System.out.println(a.decodificar());
		System.out.println(b.decodificar());
		
		CruceOperador c = new CruceOperador();
		
		Pair<BinTree<String>,BinTree<String>> p = c.cruceAux(a,b);
		
		System.out.println(p.getFirst().decodificar());
		System.out.println(p.getSecond().decodificar());

		
		System.out.println(a.decodificar());
		System.out.println(b.decodificar());

		IndividuoArbol hijo1 = new IndividuoArbol(p.getFirst());
		IndividuoArbol hijo2 = new IndividuoArbol(p.getSecond());

		hijo1.mutarTer(1);
		hijo2.mutarTer(1);
		
		System.out.println(hijo1.decodificar());
		System.out.println(hijo2.decodificar());

		
		System.out.println(a.decodificar());
		System.out.println(b.decodificar());
	}
}
