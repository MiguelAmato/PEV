package model.seleccion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import model.individuos.Individuo;

public class EstocasticoUniversal implements Seleccion {

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
		Double[] probabilidades = new Double[poblacion.size()];
		
		for (int i = 0; i < poblacion.size(); ++i) {
			probabilidad += fitness.get(poblacion.size()-1-i) / sumaFit;
			probabilidades[i] = probabilidad;
		}
		
		double espacio = 1.0/poblacion.size();
		
		double a = new Random().nextDouble(0,espacio);
		
		ArrayList<Integer> seleccionados = new ArrayList<Integer>(numSeleccionar);
		double indice = 0;
		int contador = 0;
		for(int i = 0; i < numSeleccionar;i++) {
		
			indice = (a + i) / poblacion.size(); 
			while(contador < poblacion.size()) {
				if(probabilidades[contador] > indice) {
					seleccionados.add(poblacion.size() - contador-1);
					break;
				}
				contador++;
			}
		}
		
		Collections.shuffle(seleccionados);
		
		return seleccionados;
	}
 
}
