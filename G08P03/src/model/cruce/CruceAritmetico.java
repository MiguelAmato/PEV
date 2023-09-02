package model.cruce;

import java.util.ArrayList;
import java.util.Random;

import model.individuos.Individuo;
import utils.Pair;

public class CruceAritmetico extends Cruce{
	
	private final double ALPHA = 0.6;


	protected Pair<ArrayList, ArrayList> cruceAux(Individuo padre1, Individuo padre2) {
		
		ArrayList cPadre1 = (ArrayList) padre1.getCromosoma();
		ArrayList cPadre2 = (ArrayList) padre2.getCromosoma();

		
		ArrayList crom1 = new ArrayList<>(cPadre1.size()); 
		ArrayList crom2 = new ArrayList<>(cPadre2.size());
		
		
		for(int i = 0; i < cPadre1.size();i++) {
			crom1.add((ALPHA * (double)(cPadre1.get(i))) + ((1-ALPHA) * (double)cPadre2.get(i)));
			crom2.add((ALPHA * (double)(cPadre2.get(i))) + ((1-ALPHA) * (double)cPadre1.get(i)));
		}
		
		return new Pair<ArrayList,ArrayList>(crom1,crom2);

		
	}
		
	
}
