package factories;

import java.util.ArrayList;

import model.individuos.Individuo;
import model.individuos.Individuo4B;

public class Individuo4BFactory implements IndividuoFactory<Individuo4B>{

	@Override
	public Individuo4B generateInd(double valorError, int dimension) {
		return new Individuo4B(valorError, dimension);
	}

	@Override
	public <T> Individuo4B generateInd(ArrayList<T> cromosoma, double valorError, int dimension) {
		return new Individuo4B((ArrayList<Double>) cromosoma,valorError, dimension);
	}

}
