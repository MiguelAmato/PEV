package factories;

import java.util.ArrayList;

import model.individuos.Individuo;
import model.individuos.Individuo3;

public class Individuo3Factory implements IndividuoFactory<Individuo3>{

	@Override
	public Individuo3 generateInd(double valorError, int dimension) {
		return new Individuo3(valorError);
	}

	@Override
	public <T> Individuo3 generateInd(ArrayList<T> cromosoma, double valorError, int dimension) {
		return new Individuo3((ArrayList<Boolean>) cromosoma,valorError);
	}

}
