package model.seleccion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import model.individuos.Individuo;

public class Ranking implements Seleccion {

	static private final double _beta = 1.5;
	
	public ArrayList<Integer> seleccionar(ArrayList<Individuo> poblacion, int numSeleccionar) {
		
		
		Collections.reverse(poblacion); // Ahora la poblacion esta ordenada de mejor a peor fitness
		
		ArrayList<Double> probabilidades = rankingPunctuation(poblacion); // Codigo de las diapositivas

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

	private ArrayList<Double> rankingPunctuation(ArrayList<Individuo> poblacion) {
		
		ArrayList<Double> probabilidades = new ArrayList<Double>();
		
		double accPunc = 0.0;
		for (int i = 0; i < poblacion.size(); ++i) {
			double probOfIth = (double)i/poblacion.size();
			probOfIth *= 2*(_beta-1);
			probOfIth = _beta - probOfIth;
			probOfIth = (double)probOfIth * ((double)1/poblacion.size());
			probabilidades.add(accPunc);
			accPunc += probOfIth;
		}
		return probabilidades;
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
