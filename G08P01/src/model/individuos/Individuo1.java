package model.individuos;

import java.util.ArrayList;
import java.util.Random;

public class Individuo1 extends IndBool{

	public Individuo1(double valorError) {
		initIndividuo();
		this.tamGenes[0] = this.tamGen(valorError, min[0], max[0]);
		this.tamGenes[1] = this.tamGen(valorError, min[1], max[1]);
		int tamTotal = tamGenes[0] + tamGenes[1];
		this.cromosoma = new ArrayList<Boolean>(tamTotal);
		for(int i = 0; i < tamTotal; i++) { this.cromosoma.add(this.rand.nextBoolean()); }
		this.fitness = this.getValor();
	}
	
	public Individuo1(ArrayList<Boolean> cromosoma, double valorError) {
		initIndividuo();
		this.tamGenes[0] = this.tamGen(valorError, min[0], max[0]);
		this.tamGenes[1] = this.tamGen(valorError, min[1], max[1]);
		this.cromosoma = new ArrayList<Boolean>(cromosoma);
		this.fitness = this.getValor();
	}


	protected double getValor() {
		double x1 = this.getFenotipo(0), x2 = this.getFenotipo(1);
		return (21.5 + x1 * Math.sin(4 * Math.PI * x1) + x2 * Math.sin(20 * Math.PI * x2));
	}
	
	private void initIndividuo() {
		this.tamGenes = new int[2];
		this.min = new double[2];
		this.max = new double[2];
		this.min[0] = -3.000;
		this.min[1] = 4.100;
		this.max[0] = 12.100;
		this.max[1] = 5.800;
		this.rand = new Random();
	}

	@Override
	public boolean mejorFitness(Individuo individuo) {
		if(individuo.getFitness() > this.getFitness())
			return true;
		return false;
	}

	@Override
	public Double adaptar(Individuo individuo) {
		return individuo.getFitness() + Math.abs(this.getFitness() 	* 1.05);
	}

	@Override
	public int compareTo(Individuo o) {

		 if(this.getFitness() > o.getFitness())
			 return 1;
		 else if(this.getFitness() == o.getFitness())
			 return 0;
		 else
			 return -1;
	
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



	
	
}
