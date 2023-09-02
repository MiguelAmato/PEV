package model.mutacion;

import java.util.ArrayList;

import model.individuos.Individuo;

public interface Mutacion {
	
	void mutar(ArrayList<Individuo> poblacion, double prob);
}
