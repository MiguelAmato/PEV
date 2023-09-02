package model.bloating;

import java.util.ArrayList;
import java.util.Random;

import model.individuos.Individuo;

public class BloatingTarpeian implements Bloating{

	private final double PROB = 0.33;
	
	@Override
	public void controlBloating(ArrayList<Individuo> poblacion) {
		
		Random r = new Random();
		ArrayList<Integer> numNodos= new ArrayList<Integer>();
		
		int mediaNodos = 0;
		int nodos = 0;
		for(Individuo i: poblacion) {
			nodos = i.getNumNodos();
			mediaNodos += nodos;
			numNodos.add(nodos);
		}
		
		mediaNodos /= poblacion.size();
		
		for(int i = poblacion.size()-1;i >= 0;i--) {
			if(numNodos.get(i) > mediaNodos && (r.nextDouble(0,1) < PROB)) {
			//	poblacion.remove(i);
				poblacion.get(i).setFitness(Double.MAX_VALUE);
			}
		}
		
	}	
}
