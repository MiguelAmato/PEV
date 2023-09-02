package model.cruce;

import java.util.ArrayList;
import java.util.Random;

import model.individuos.Individuo;
import utils.Pair;

public class CruceBLXA extends Cruce{

	private final double ALPHA = 0.5;
	
	
	protected Pair<ArrayList, ArrayList> cruceAux(Individuo padre1, Individuo padre2) {
	
		ArrayList cPadre1 = (ArrayList) padre1.getCromosoma();
		ArrayList cPadre2 = (ArrayList) padre2.getCromosoma();

		
		ArrayList crom1 = new ArrayList<>(cPadre1.size()); 
		ArrayList crom2 = new ArrayList<>(cPadre2.size());
		
		Random r = new Random();
		double aux;
		
		for(int i = 0; i < cPadre1.size();i++) {
			
			double max = Math.max((double)cPadre1.get(i), (double)cPadre2.get(i));
			double min = Math.min((double)cPadre1.get(i), (double)cPadre2.get(i));

			double diff = max-min;
			if(diff == 0) {
				crom1.add(cPadre1.get(i));
				crom2.add(cPadre2.get(i));
			}
			else {
				aux = r.nextDouble(min - (diff * ALPHA), max + (diff * ALPHA));
				crom1.add(aux);
				crom2.add(aux);
			}			
		}
		
		return new Pair<ArrayList,ArrayList>(crom1,crom2);
	}
		
}
