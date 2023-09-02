package model;

import java.util.ArrayList;
import java.util.Collections;

import factories.IndividuoArbolFactory;
import factories.IndividuoFactory;
import model.bloating.Bloating;
import model.bloating.BloatingFund;
import model.creacion.Creacion;
import model.creacion.CreacionCompleta;
import model.cruce.Cruce;
import model.cruce.CruceOperador;
import model.individuos.Individuo;
import model.mutacion.Mutacion;
import model.mutacion.MutacionTerminal;
import model.observers.Observable;
import model.observers.Observer;
import model.seleccion.Seleccion;
import model.seleccion.SeleccionRuleta;
import utils.Pair;
@SuppressWarnings("rawtypes")

public class AlgoritmoGenetico implements Observable<Observer>{
	
	public final static String COMPLETO = "COMPLETO";
	public final static String CRECIENTE = "CRECIENTE";
	public final static String RAMPED = "RAMPED";

	private int tamPoblacion;
	private ArrayList<Individuo> poblacion;
	
	private IndividuoFactory<? extends Individuo> factory;
	private Seleccion seleccion;
	private Cruce cruce;
	private Mutacion mutacion;
	private Creacion creacion;
	private int maxGeneraciones;
	private double probCruce;
	private double probMutacion;
	private Individuo mejorGeneracion; 
	private Bloating contBloating;

	private double porElitismo;
	private boolean bloating;
	private ArrayList<Individuo> elite;
	private ArrayList<Pair<Double, Individuo>> mejoresIntervalo;
	
	private String tipoCreacion;
	private int profundidad;
	
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
		this.factory = new IndividuoArbolFactory();
		this.seleccion = new SeleccionRuleta();
		this.cruce = new CruceOperador(); 
		this.mutacion = new MutacionTerminal(); 
		this.creacion = new CreacionCompleta();
		this.contBloating = new BloatingFund();
		this.porElitismo = 0.0;
		this.tipoCreacion = COMPLETO;
		this.profundidad = 5; 
		this.bloating = false;
		this.mejoresIntervalo = new ArrayList<Pair<Double, Individuo>>();
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
		
			mutacion.mutar(poblacion, probMutacion);
			
			if(bloating) {
				contBloating.controlBloating(poblacion);
			}
			
			if(porElitismo > 0)
				incluirElite();
			
			pobEvaluation(i+1);
		}
		
		
		
		for (Observer o: observers) {
			o.onEnd(this, "Ninguno"); // Se pone ninguno como key para avisar a la grafica normal que tiene que mostrar valores
		}

	}
	
	public void addValor(double valor) {
		mejoresIntervalo.add(new Pair<Double, Individuo>(valor, elMejorAbs));
	}
	
	
	public Pair<double[], double[]> getMejoresIntervalos(){
		double[] valores = new double[mejoresIntervalo.size()];
		double[] fitness = new double[mejoresIntervalo.size()];
		
		for(int i = 0; i < valores.length;i++) {
			valores[i] = mejoresIntervalo.get(i).getFirst();
			fitness[i] = mejoresIntervalo.get(i).getSecond().getFitness();
		}
		
		return new Pair<double[], double[]>(valores,fitness);
		
	}
	
	public String setTextIntervalos() {
		String ret = "El mejor valor del intervalo seleccionado es: ";
		Individuo max = mejoresIntervalo.get(0).getSecond();
		int indMax = 0;
		for (int i = 1; i < mejoresIntervalo.size(); ++i) {
			if (max.mejorFitness(mejoresIntervalo.get(i).getSecond())) {
				max = mejoresIntervalo.get(i).getSecond();
				indMax = i;
			}
		}
		ret += mejoresIntervalo.get(indMax).getFirst() + " con fitness: " + max.getFitness(); 
		return ret;
	}
	
	private boolean parseParam(){
		try {
			if(this.tamPoblacion < 2)
				throw new Exception("El tamaño de la poblacion debe ser superior a 1");
			else if(this.maxGeneraciones < 0)
				throw new Exception("Debe haber un minimo de 0 generaciones");
			else if(this.probCruce < 0 || this.probCruce > 1)
				throw new Exception("La probabilidad de cruce debe estar entre 0 y 100");
			else if(this.probMutacion < 0 || this.probMutacion > 1)
				throw new Exception("La probabilidad de mutacion debe estar entre 0 y 100");
			else if(this.porElitismo < 0 || this.porElitismo > 1)
				throw new Exception("El porcentaje de elitismo debe estar entre 0 y 100");
			return true;
		}
		catch(Exception e) {
			throwException(e.getMessage());
			return false;
		}
	}
	
	public void throwException(String e) {
		for(Observer o: observers)
			o.onError(e);
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
			elite.add(factory.generateInd(poblacion.get(contador).getCromosoma()));
			contador--;
			numElite--;
		}
	}

	private void pobEvaluation(int i) {
		Collections.sort(poblacion);
		
		mejorGeneracion = factory.generateInd(poblacion.get(poblacion.size()-1).getCromosoma());
		
		if (i == 0)
			elMejorAbs = factory.generateInd(mejorGeneracion.getCromosoma());
		
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
		poblacion = creacion.generarPoblacion(factory, profundidad, tamPoblacion);
	}
	
	private <T> ArrayList<Individuo> nuevaGen(ArrayList cromosomas) {
		ArrayList<Individuo> nuevaGen = new ArrayList<Individuo>();
		
		for(int i = 0; i < cromosomas.size();i++) {
			nuevaGen.add(factory.generateInd(cromosomas.get(i))); 
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
	
	public void setCruce(Cruce cruce) {
		this.cruce = cruce;
	}
	
	public void setMutacion(Mutacion mutacion) {
		this.mutacion = mutacion;
	}
	
	public void setCreacion(Creacion creacion) { // Agregada 
		this.creacion = creacion;
	}
	
	public void reset() {
        this.tamPoblacion = 100;
        this.maxGeneraciones = 100;
        this.probCruce = 0.6;
        this.probMutacion = 0.05;
        this.factory = new IndividuoArbolFactory();
        this.seleccion = new SeleccionRuleta();
		this.cruce = new CruceOperador(); // ESTO LO CAMBIE PARA TENER DEFAULT EL CRUCE ARBOL
		this.mutacion = new MutacionTerminal(); // ESTO LO CAMBIE PARA TENER DEFAULT LA MUTACION ARBOL 
        this.porElitismo = 0.0;
        this.bloating = false;
        this.profundidad = 5;

        for (Observer o: observers) {
            o.onReset();
        }
    }

	public void clearMejores() {
		this.mejoresIntervalo.clear();
	}

	public void finIntervalos(String key) {
		for(Observer o : observers) {
			o.onEnd(this, key);
		}
	}

	public void setBloating(boolean value) {
		bloating = value;
		
	}

	public void setProfundidad(int value) {
		this.profundidad = value;
		
	}	
}
