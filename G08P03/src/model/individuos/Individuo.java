package model.individuos;

import java.util.Random;

import utils.Pair;

public abstract class Individuo<T> implements Comparable<Individuo>{

	protected T cromosoma;
	protected Random rand;
	protected double fitness;

	public double getFitness() {
		return this.fitness;
	}
	
	public void setFitness(double fitness) {
		this.fitness = fitness;
	}
	
	public String getDeco() {
		return decodificar();
	}
	
	protected abstract double getValor();
	protected abstract String decodificar();
	public abstract double getFenotipo(int gen);
	public abstract boolean mejorFitness(Individuo individuo);
	public abstract Double adaptar(Individuo individuo);
	public abstract int getNumNodos();
	public abstract void setCromosoma(T cromosoma);
	public abstract Pair<double[], double[]> getListFunction();
	public abstract Pair<double[], double[]> getFunction();
	
	public T getCromosoma() {
		return this.cromosoma;
	}

	public void mutarTer(double prob) {}
	public void mutarFun(double prob) {}
	public void mutarSub(double prob) {}



}
