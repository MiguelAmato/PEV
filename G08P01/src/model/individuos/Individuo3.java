package model.individuos;

import java.util.ArrayList;
import java.util.Random;

public class Individuo3 extends IndBool{

	public Individuo3(double valorError) {
		initIndividuo();	
		this.tamGenes[0] = this.tamGen(valorError, min[0], max[0]);
		this.tamGenes[1] = this.tamGen(valorError, min[1], max[1]);
		int tamTotal = tamGenes[0] + tamGenes[1];
		this.cromosoma = new ArrayList<Boolean>(tamTotal);
		for(int i = 0; i < tamTotal; i++) { this.cromosoma.add(this.rand.nextBoolean()); }
		this.fitness = this.getValor();
	}
	
	public Individuo3(ArrayList<Boolean> cromosoma, double valorError) {
		initIndividuo();
		this.tamGenes[0] = this.tamGen(valorError, min[0], max[0]);
		this.tamGenes[1] = this.tamGen(valorError, min[1], max[1]);
		this.cromosoma = new ArrayList<Boolean>(cromosoma);
		this.fitness = this.getValor();
	}
	
	@Override
	public int compareTo(Individuo o) {
		if(this.getFitness() < o.getFitness())
			 return 1;
		 else if(this.getFitness() == o.getFitness())
			 return 0;
		 else
			 return -1;
	}

	@Override
	protected double getValor() {
		double x1 = this.getFenotipo(0), x2 = this.getFenotipo(1);
		double term1 = Math.pow(x1, 4) - 16 * Math.pow(x1, 2) + 5 * x1;
	    double term2 = Math.pow(x2, 4) - 16 * Math.pow(x2, 2) + 5 * x2;
	    double result = 0.5 * (term1 + term2);
	    return result;
	}

	@Override
	protected String decodificar() {
		String str = "";
		
		for(int i = 0; i < 2; i++) {
			str+= "Variable X" + (i+1) + " = " + getFenotipo(i) + ", ";
		}
		
		str += "Valor de la funcion = " + this.getFitness();  
		
		return str;
	}

	@Override
	public boolean mejorFitness(Individuo individuo) {
		if(individuo.getFitness() < this.getFitness())
			return true;
		return false;
	}

	@Override
	public Double adaptar(Individuo individuo) {
		return  Math.abs(this.getFitness() 	* 1.05) - individuo.getFitness();
	}
	
	private void initIndividuo() {
		this.tamGenes = new int[2];
		this.min = new double[2];
		this.max = new double[2];
		for(int i = 0; i < 2;i++) {
			this.min[i] = -5.0;
			this.max[i] = 5.0;
		}
		this.rand = new Random();
	}

}
