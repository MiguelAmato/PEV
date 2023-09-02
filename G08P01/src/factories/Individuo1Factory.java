package factories;

import java.util.ArrayList;
import model.individuos.Individuo1;

public class Individuo1Factory implements IndividuoFactory<Individuo1>{

	@Override
	public Individuo1 generateInd(double valorError, int dimension) {
		return new Individuo1(valorError);
	}

	@Override
	public <T> Individuo1 generateInd(ArrayList<T> cromosoma, double valorError, int dimension) {
		return new Individuo1((ArrayList<Boolean>) cromosoma,valorError);
	}
	
}