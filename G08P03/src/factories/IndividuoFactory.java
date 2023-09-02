package factories;

import java.util.ArrayList;
import model.individuos.Individuo;


public interface IndividuoFactory<T extends Individuo>{
	
	public Individuo generateInd(String creacion, int profundidad);
	
	public <T> Individuo generateInd(T cromosoma);
	
}
