package model.individuos;

import java.util.ArrayList;
import java.util.Random;

public abstract class IndDouble extends Individuo<Double>{

	protected int tamGen(double valorError, double min, double max) {
		return 1;
	}
	
	public double getFenotipo(int gen) {
        return cromosoma.get(gen);
    }
	
	public void mutar(double prob) {  
		Random r = new Random();	 
		for(int i = 0; i < cromosoma.size();i++) {
			if(r.nextDouble(0,1) < prob) {
				cromosoma.set(i, r.nextDouble(this.min[i],this.max[i]));
			}
		}
		this.fitness = this.getValor();
	}
	
	public ArrayList<Double> getCromosoma() {
		return this.cromosoma;
	}

}
