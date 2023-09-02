package model.mutacion;

import java.util.ArrayList;

import model.individuos.Individuo;

public class MutacionTerminal implements Mutacion{

	@Override
	public void mutar(ArrayList<Individuo> poblacion, double prob) {
		for(Individuo in: poblacion) {
			in.mutarTer(prob);
		}
	}

}
