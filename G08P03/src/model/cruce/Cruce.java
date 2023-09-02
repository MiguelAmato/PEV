package model.cruce;

import java.util.ArrayList;
import java.util.Random;

import model.individuos.Individuo;
import utils.Pair;
public abstract class Cruce{
	
	public <T> ArrayList<T> cruzar(ArrayList<Individuo> poblacion,ArrayList<Integer> seleccionados, double probCruce){
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
				Pair<T,T> hijos = cruceAux(padre1,padre2);
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
	
	protected abstract <T> Pair<T, T> cruceAux(Individuo padre1, Individuo padre2);

}
