package model.cruce;

import java.util.ArrayList;
import java.util.Random;

import model.individuos.Individuo;
import utils.Pair;

public class CruceUniforme extends Cruce{

	protected Pair<ArrayList, ArrayList> cruceAux(Individuo padre1, Individuo padre2) {
		
		
		ArrayList cPadre1 = (ArrayList) padre1.getCromosoma();
		ArrayList cPadre2 = (ArrayList) padre2.getCromosoma();
		
		Random r = new Random();
		
		ArrayList crom1 = new ArrayList<>(cPadre1.size()); 
		ArrayList crom2 = new ArrayList<>(cPadre2.size());
		
		for(int i = 0; i < cPadre1.size();i++) {
			if(r.nextDouble(0,1) < 0.5) {
				crom1.add(cPadre2.get(i));
				crom2.add(cPadre1.get(i));
			}
			else {
				crom1.add(cPadre1.get(i));
				crom2.add(cPadre2.get(i));
			}
		}
		
		return new Pair<ArrayList,ArrayList>(crom1,crom2);
	}

}
