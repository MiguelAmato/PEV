package model.cruce;

import java.util.ArrayList;
import java.util.Random;

import model.individuos.Individuo;
import utils.Pair;
public interface Cruce {
	
	public ArrayList<ArrayList> cruzar(ArrayList<Individuo> poblacion,ArrayList<Integer> seleccionados, double probCruce);

}
