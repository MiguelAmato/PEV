package model.bloating;

import java.util.ArrayList;

import model.individuos.Individuo;

public class BloatingFund implements Bloating{

	public void controlBloating(ArrayList<Individuo> poblacion) {
		
		int[] nodos = new int[poblacion.size()];
		double[] fit = new double[poblacion.size()];
		for(int i = 0; i < poblacion.size();i++) {
			 nodos[i] = poblacion.get(i).getNumNodos();
			 fit[i] = poblacion.get(i).getFitness();
		}
		
		double cov = covarianza(nodos,fit);
		double var = varianza(fit);
		
		double k = cov / var;
		
		for(int i = 0; i < poblacion.size();i++) {
			 poblacion.get(i).setFitness(poblacion.get(i).getFitness() + k * nodos[i]);
		}
	}
	
	private double covarianza(int[] nodos, double[] fitness) {
		
		int n = nodos.length;
		double m1 = 0.0;
		double m2 = 0.0;
		double sum = 0.0;
		
		for(int i = 0; i < n;i++) {
			m1 += nodos[i];
			m2 += fitness[i];
		}
		m1 /= n;
		m2 /= n;
		
		for(int i = 0; i < n;i++) {
			sum += (nodos[i] - m1) * (fitness[i] - m2);
		}
		
		double cov = sum / (n-1);
		
		return Math.max(cov, 0);
	}
	
	private double varianza(double[] nodos) {
		
		int n = nodos.length;
		double m = 0.0;
		double sum = 0.0;
		
		for(int i = 0; i < n;i++) {
			m += nodos[i];
		}
		
		m /= n;
		
		for(int i = 0;i<n;i++) {
			sum += Math.pow(nodos[i]-m, 2);
		}
		
		double var = sum / (n-1);
		return var;
	}

}
