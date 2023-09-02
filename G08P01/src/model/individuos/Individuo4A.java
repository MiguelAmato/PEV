package model.individuos;

import java.util.ArrayList;
import java.util.Random;

public class Individuo4A extends IndBool{
	
	private int dimension;
	
	public Individuo4A(double valorError, int dimension) {
		this.dimension = dimension;
		initIndividuo();
		
		for(int i = 0; i < dimension;i++) {
			this.tamGenes[i] = this.tamGen(valorError, min[i], max[i]);
		}
		int tamTotal = 0;
		for(int i = 0; i < dimension;i++) {
			tamTotal += tamGenes[i];
		}
		this.cromosoma = new ArrayList<Boolean>(tamTotal);
		for(int i = 0; i < tamTotal; i++) { this.cromosoma.add(this.rand.nextBoolean()); }
		this.fitness = this.getValor();
	}
	
	public Individuo4A(ArrayList<Boolean> cromosoma, double valorError, int dimension) {		
		this.dimension = dimension;

		initIndividuo();
		for(int i = 0; i < dimension;i++) {
			this.tamGenes[i] = this.tamGen(valorError, min[i], max[i]);
		}
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
	       int m = 10;
	        double result = 0;
	        Double[] fenotipos = new Double[dimension];
	        for(int i = 0; i < dimension;i++) {
	        	fenotipos[i] = this.getFenotipo(i);
	        }
	        for (int i = 0; i < fenotipos.length; i++) {
	            double xi = fenotipos[i];
	            double term1 = Math.sin(xi);
	            double term2 = Math.sin((i+1)*xi*xi/Math.PI);
	            double term3 = Math.pow(term2, 2*m);
	            result -= term1 * term3;
	        }
	        return result;
	}

	@Override
	protected String decodificar() {
		String str = "";
		
		for(int i = 0; i < dimension; i++) {
			str+= "X" + (i+1) + " = " + getFenotipo(i) + ", ";
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
		this.tamGenes = new int[dimension];
		this.min = new double[dimension];
		this.max = new double[dimension];
		
		for(int i = 0; i < dimension;i++) {
			this.min[i] = 0;
			this.max[i] = Math.PI;
		}
		
		this.rand = new Random();		
	}

}
