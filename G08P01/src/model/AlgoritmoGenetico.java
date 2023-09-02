package model;

import java.util.ArrayList;
import java.util.Collections;

import factories.Individuo1Factory;
import factories.IndividuoFactory;
import model.cruce.Cruce;
import model.cruce.CruceMonopunto;
import model.individuos.Individuo;
import model.observers.Observable;
import model.observers.Observer;
import model.seleccion.Seleccion;
import model.seleccion.SeleccionRuleta;
@SuppressWarnings("rawtypes")

public class AlgoritmoGenetico implements Observable<Observer>{

	private int tamPoblacion;
	private ArrayList<Individuo> poblacion;
	
	private IndividuoFactory<? extends Individuo> factory;
	private Seleccion seleccion;
	private Cruce cruce;
	private int maxGeneraciones;
	private double probCruce;
	private double probMutacion;
	private int tamTorneo;
	private Individuo mejorGeneracion; 
	private int dimension;
	private double valorError;
	private double porElitismo;
	private ArrayList<Individuo> elite;
	
	private double[] medias;
	private double[] mejorGen;
	private double[] mejorAbs;
	
	private Individuo elMejorAbs ;
	
	private ArrayList<Observer> observers;
		
	public AlgoritmoGenetico() { 
		this.tamPoblacion = 100;
		this.maxGeneraciones = 100;
		this.probCruce = 0.6;
		this.probMutacion = 0.05;
		this.dimension = 2;		
		this.factory = new Individuo1Factory();
		this.seleccion = new SeleccionRuleta();
		this.cruce = new CruceMonopunto();
		this.valorError = 0.001;
		this.porElitismo = 0.0;
		observers = new ArrayList<>();
	}
	
	public void run() {
				
		if(!parseParam())
			return;
		
		poblacion = new ArrayList<Individuo>();
		elite = new ArrayList<Individuo>((int)(poblacion.size()*porElitismo));
		medias = new double[maxGeneraciones+1];
		mejorGen = new double[maxGeneraciones+1];
		mejorAbs = new double[maxGeneraciones+1];
		
		initPoblacion();
		
		pobEvaluation(0);
		
		ArrayList<Integer> seleccionados = new ArrayList<Integer>();
		ArrayList cromosomas = new ArrayList<>();
		
		for (int i = 0; i < maxGeneraciones; ++i) {
			
			if(porElitismo > 0)
				seleccionaElite();
			
			seleccionados = seleccion.seleccionar(poblacion, poblacion.size());
			
			cromosomas = cruce.cruzar(poblacion,seleccionados, probCruce);
			
			poblacion = nuevaGen(cromosomas);
		
			for(int j = 0; j < poblacion.size();j++) {
				poblacion.get(j).mutar(probMutacion);
			}
			
			if(porElitismo > 0)
				incluirElite();
			
			pobEvaluation(i+1);
		}
		
		for (Observer o: observers) {
			o.onEnd(this);
		}

	}
	
	private boolean parseParam(){
		try {
			if(this.tamPoblacion < 2)
				throw new Exception("El tamaño de la poblacion debe ser superior a 1");
			else if(this.maxGeneraciones < 0)
				throw new Exception("Debe haber un minimo de 0 generaciones");
			else if(this.valorError <= 0 || this.valorError > 1)
				throw new Exception("El valor de error debe estar entre 0 y 1");
			else if(this.probCruce < 0 || this.probCruce > 1)
				throw new Exception("La probabilidad de cruce debe estar entre 0 y 100");
			else if(this.probMutacion < 0 || this.probMutacion > 1)
				throw new Exception("La probabilidad de mutacion debe estar entre 0 y 100");
			else if(this.porElitismo < 0 || this.porElitismo > 1)
				throw new Exception("El porcentaje de elitismo debe estar entre 0 y 100");
			return true;
		}
		catch(Exception e) {
			
			for(Observer o: observers)
				o.onError(e.getMessage());
			return false;
		}
	}

	private void incluirElite() {
		Collections.sort(poblacion);
		
		for (int i = 0; i < elite.size(); ++i) {
			poblacion.set(i, elite.get(i));
		}
		elite.clear();
	}

	private void seleccionaElite() {
		
		Collections.sort(poblacion);
		double numElite = (porElitismo * poblacion.size());
		int contador = poblacion.size()-1;
		while(numElite > 0) {
			elite.add(poblacion.get(contador));
			contador--;
			numElite--;
		}
	}

	private void pobEvaluation(int i) {
		Collections.sort(poblacion);
		
		mejorGeneracion = poblacion.get(poblacion.size()-1);
		
		if (i == 0)
			elMejorAbs = factory.generateInd(mejorGeneracion.getCromosoma(), valorError, dimension);
		
		else if (elMejorAbs.mejorFitness(mejorGeneracion)) {
			elMejorAbs.setCromosoma(mejorGeneracion.getCromosoma());
		}
		mejorGen[i] = mejorGeneracion.getFitness();
		mejorAbs[i] = elMejorAbs.getFitness();
		
		double media = 0.0;
		for (int j = 0; j < poblacion.size(); j++) {
			media += poblacion.get(j).getFitness();
			
		}
		media = media / poblacion.size();
		
		medias[i] = media;
		
		
	}	
	
	private void initPoblacion() {
		for(int i = 0; i < this.tamPoblacion;i++) {
			poblacion.add(factory.generateInd(valorError, dimension)); 
		}
	}
	
	private <T> ArrayList<Individuo> nuevaGen(ArrayList cromosomas) {
		ArrayList<Individuo> nuevaGen = new ArrayList<Individuo>();
		
		for(int i = 0; i < cromosomas.size();i++) {
			nuevaGen.add(factory.generateInd((ArrayList<T>) cromosomas.get(i),valorError,dimension));
		}
		
		return nuevaGen;
	}

	@Override
	public void addObserver(Observer o) {
		observers.add(o);
	}

	@Override
	public void removeObserver(Observer o) {
		observers.remove(o);
	}
	
	public Individuo getMejor() {
		return elMejorAbs;
	}
	
	public int getMaxGeneraciones() {
		return maxGeneraciones;
	}
	
	public double[] getMejorGeneracion() {
		return mejorGen;
	}
	
	public double[] getMedias() {
		return medias;
	}
	
	public double[] getMejorAbs() {
		return mejorAbs;
	}

	public void setPobSize(int parseInt) {
		this.tamPoblacion = parseInt;
		
	}

	public void setGenSize(int parseInt) {
		this.maxGeneraciones = parseInt;
	}

	public void setValorError(double d) {
		this.valorError = d;
		
	}

	public void setProbCruce(double d) {
		this.probCruce = d;
	}

	public void setProbMutacion(double d) {
		this.probMutacion = d;
		
	}

	public void setPorElitismo(double d) {
		this.porElitismo = d;
		
	}

	public void setSeleccion(Seleccion seleccion) {
		this.seleccion = seleccion;
	}

	public void setProbCruce(Cruce cruce) {
		this.cruce = cruce;
		
	}

	public void setIndividuoFactory(IndividuoFactory individuoFactory) {
		this.factory = individuoFactory;
		
	}
	
	public void setDimension(int value) {
		this.dimension = value;
	}
	
	public void setCruce(Cruce cruce) {
		this.cruce = cruce;
	}
	
	public void reset() {
        this.tamPoblacion = 100;
        this.maxGeneraciones = 100;
        this.probCruce = 0.6;
        this.probMutacion = 0.05;
        this.dimension = 2;
        this.factory = new Individuo1Factory();
        this.seleccion = new SeleccionRuleta();
        this.cruce = new CruceMonopunto();
        this.valorError = 0.001;
        this.porElitismo = 0.0;

        for (Observer o: observers) {
            o.onReset();
        }
    }

	

	
	
}
