package factories;

import model.individuos.Individuo;
import model.individuos.IndividuoArbol;
import utils.BinTree;

public class IndividuoArbolFactory implements IndividuoFactory<IndividuoArbol>{

	@Override
	public Individuo generateInd(String creacion, int profundidad) {
		return new IndividuoArbol(creacion,profundidad);
	}

	@Override
	public <T> Individuo generateInd(T cromosoma) {
		return new IndividuoArbol((BinTree<String>) cromosoma);
	}
}
