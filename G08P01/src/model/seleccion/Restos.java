package model.seleccion;

import java.util.ArrayList;
import java.util.Collections;

import model.individuos.Individuo;

public class Restos implements Seleccion{
	
	@Override
	public ArrayList<Integer> seleccionar(ArrayList<Individuo> poblacion, int numSeleccionar) {

		Individuo peorInd = poblacion.get(0);
		
		
		for (int i = 1; i < poblacion.size(); ++i) {			
			if(!peorInd.mejorFitness(poblacion.get(i))) {
				peorInd = poblacion.get(i);
			}
		}
		
		ArrayList<Double> fitness = new ArrayList<Double>();
		
		double sumaFit = 0;

		for(int i = 0; i < poblacion.size();i++) {
			fitness.add(peorInd.adaptar(poblacion.get(i)));
			sumaFit += fitness.get(i);
		}
		
		
		ArrayList<Double> probabilidades = new ArrayList<Double>(poblacion.size());
		for (int i = 0; i < poblacion.size(); ++i) {
			probabilidades.add(fitness.get(i) / sumaFit);
		}

		ArrayList<Integer> seleccionados = new ArrayList<Integer>(numSeleccionar);
		
		int i = poblacion.size()-1;
		while(i >= 0) {
			int j = 0; 
			while(j < (int)(probabilidades.get(i)*numSeleccionar) && seleccionados.size() < numSeleccionar) {				
				seleccionados.add(i);
				j++;
			}
			i--;
		}
		
		if(seleccionados.size() < numSeleccionar) {
			ArrayList<Integer> seleccionados2 = new ArrayList<Integer>(numSeleccionar - seleccionados.size());
			seleccionados2 = new TorneoDeterministico().seleccionar(poblacion, numSeleccionar - seleccionados.size());
			
			for(int k = 0; k < seleccionados2.size();k++) {
				seleccionados.add(seleccionados2.get(k));
			}
			
		}
		Collections.shuffle(seleccionados);

		return seleccionados;
	}

}
