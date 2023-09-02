package model.cruce;

import java.util.ArrayList;
import java.util.Random;

import model.individuos.Individuo;
import utils.Pair;

public class CruceUniforme implements Cruce{

	@Override
public ArrayList<ArrayList> cruzar(ArrayList<Individuo> poblacion, ArrayList<Integer> seleccionados, double probCruce) {
		
		ArrayList nuevaGen = new ArrayList<>();
		
		Individuo padre1, padre2;
		
		Random r = new Random();
		
		if(poblacion.size() % 2 == 1) {
			nuevaGen.add(poblacion.get(seleccionados.get(seleccionados.size()-1)).getCromosoma());
			seleccionados.remove(seleccionados.size()-1);
		}
		
		for(int i = 0; i < seleccionados.size();i+=2) {
			
			padre1 = poblacion.get(seleccionados.get(i));
			padre2 = poblacion.get(seleccionados.get(i+1));

			if(r.nextDouble() < probCruce) { // Se cruzan
				Pair<ArrayList,ArrayList> hijos = cruceAux(padre1,padre2);
				nuevaGen.add(hijos.getFirst());
				nuevaGen.add(hijos.getSecond());
			}
			
			else {
				nuevaGen.add(padre1.getCromosoma());
				nuevaGen.add(padre2.getCromosoma());
			}
			
		}
		
		
		
		return nuevaGen;
	}

	private Pair<ArrayList, ArrayList> cruceAux(Individuo padre1, Individuo padre2) {
		
		
		ArrayList cPadre1 = padre1.getCromosoma();
		ArrayList cPadre2 = padre2.getCromosoma();
		
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
