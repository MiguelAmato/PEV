package factories;

import java.util.ArrayList;

import model.individuos.Individuo;
import model.individuos.Individuo4A;

public class Individuo4AFactory implements IndividuoFactory<Individuo4A>{

	@Override
	public Individuo4A generateInd(double valorError, int dimension) {
		return new Individuo4A(valorError, dimension);
	}

	@Override
	public <T> Individuo4A generateInd(ArrayList<T> cromosoma, double valorError, int dimension) {
		return new Individuo4A((ArrayList<Boolean>) cromosoma,valorError, dimension);
	}

}
