package model.seleccion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import model.individuos.Individuo;

public class TorneoDeterministico implements Seleccion{

	private final int SIZE_GRUPO = 3;
	
	@Override
	public ArrayList<Integer> seleccionar(ArrayList<Individuo> poblacion, int numSeleccionar) {
		 ArrayList<Integer> seleccionados = new ArrayList<Integer>();
		 Random r = new Random();
		 for(int i = 0; i < numSeleccionar;i++) {
			  ArrayList<Integer> competidores = new ArrayList<Integer>();
			  for (int j = 0; j < SIZE_GRUPO; j++) {
		            competidores.add(r.nextInt(0,poblacion.size()));
		      }
			 
			  int ganador = competidores.get(0);
			  for (int j = 1; j < SIZE_GRUPO; j++) {
		            int actual = competidores.get(j);
		            if (poblacion.get(ganador).mejorFitness(poblacion.get(actual))) {
		                ganador = actual;
		            }
		       }
			  
			  seleccionados.add(ganador);
		 }
			Collections.shuffle(seleccionados);
		return seleccionados;
	}
}
