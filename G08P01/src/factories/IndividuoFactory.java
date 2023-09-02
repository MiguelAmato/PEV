package factories;

import java.util.ArrayList;
import model.individuos.Individuo;


public interface IndividuoFactory<T extends Individuo>{
	
	public Individuo generateInd(double valorError, int dimension);
	
	public <T> Individuo generateInd(ArrayList<T> cromosoma, double valorError, int dimension);
	
}
