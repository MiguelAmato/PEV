package model.seleccion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import model.individuos.Individuo;
import utils.Pair;

public class SeleccionRuleta implements Seleccion {

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
		
		
		double probabilidad = 0;
		ArrayList<Double> probabilidades = new ArrayList<Double>(poblacion.size());
		for (int i = 0; i < poblacion.size(); ++i) {
			probabilidad += fitness.get(i) / sumaFit;
			probabilidades.add(probabilidad);
		}
		
		Random random = new Random();
		ArrayList<Integer> seleccionados = new ArrayList<Integer>(numSeleccionar);
		double r;
		int indi;
		for (int i = 0; i < numSeleccionar; ++i) {
			r = random.nextDouble();
			indi = buscarIndividuo(probabilidades, r, 0, probabilidades.size());
			seleccionados.add(indi);
		}
		Collections.shuffle(seleccionados);

		return seleccionados;
	}

	private int buscarIndividuo(ArrayList<Double> probabilidades, double r, int ini, int fin) {
		if (ini + 1 == fin) {
			return ini;
		}
		else {
			int mitad = (ini + fin - 1) / 2;
			if (probabilidades.get(mitad) < r) {
				return buscarIndividuo(probabilidades, r, mitad + 1, fin);
			}
			else {
				return buscarIndividuo(probabilidades, r, ini, mitad + 1);
			}
		}
	}

}
