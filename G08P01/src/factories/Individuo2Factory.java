package factories;

import java.util.ArrayList;

import model.individuos.Individuo;
import model.individuos.Individuo2;

public class Individuo2Factory implements IndividuoFactory<Individuo2> {

	@Override
	public Individuo2 generateInd(double valorError, int dimension) {
		return new Individuo2(valorError);
	}

	@Override
	public <T> Individuo2 generateInd(ArrayList<T> cromosoma, double valorError, int dimension) {
		return new Individuo2((ArrayList<Boolean>) cromosoma,valorError);
	}
	

}
