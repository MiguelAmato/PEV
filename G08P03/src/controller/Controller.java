package controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;


import factories.IndividuoFactory;
import model.AlgoritmoGenetico;
import model.creacion.Creacion;
import model.cruce.Cruce;
import model.mutacion.Mutacion;
import model.observers.Observer;
import model.seleccion.Seleccion;
import utils.Trio;
import utils.Cuarteto;
import utils.Function;
import utils.Pair;


public class Controller {
	
	public final static String NINGUNO = "1";
	public final static String TAM_POBLACION = "Tamaño Población";
	public final static String PROB_CRUCE = "Prob. Cruce";
	public final static String PROB_MUTACION = "Prob. Mutación";
	
	private AlgoritmoGenetico algoritmo;
	
	private Map<String, Pair<IndividuoFactory, Cuarteto<ArrayList<String>,ArrayList<String>,ArrayList<String>,ArrayList<String>>>> mapaFactories;
	private Map<String, Seleccion> mapaSeleccion;
	private Map<String, Cruce> mapaCruce;
	private Map<String ,Mutacion> mapaMutacion;
	private Map<String, Creacion> mapaCreacion;
	
	private Map<String, Function<Double>> mapaFunciones;
	private Map<String, Pair<Double,Double>> mapaExcepciones;
	
	private String tipoCruce;
	
	private double min, max; 
	
	public Controller(AlgoritmoGenetico algoritmo, Map<String, Pair<IndividuoFactory, Cuarteto<ArrayList<String>,ArrayList<String>,ArrayList<String>,ArrayList<String>>>> mapaFactories, Map<String, Seleccion> mapaSeleccion,Map<String, Cruce> mapaCruce, Map<String,Mutacion> mapaMutacion, Map<String,Creacion> mapaCreacion) {
		this.algoritmo = algoritmo;
		this.mapaFactories = mapaFactories;
		this.mapaSeleccion = mapaSeleccion;	
		this.mapaCruce = mapaCruce;
		this.mapaMutacion = mapaMutacion;
		this.mapaCreacion = mapaCreacion;
		initMapa();
	}

	private void initMapa() {
		mapaFunciones = new HashMap<String, Function<Double>>() {{
			put(TAM_POBLACION, (x) -> updatePobSize(x));
			put(PROB_CRUCE, (x) -> updateProbCruce(x/100));
			put(PROB_MUTACION, (x) -> updateProbMuta(x/100));
		}};
		
		mapaExcepciones = new HashMap<String,Pair<Double,Double>>() {{
			put(TAM_POBLACION, new Pair<Double,Double>(2.0,(double)Integer.MAX_VALUE));
			put(PROB_CRUCE, new Pair<Double,Double>(0.0,100.0));
			put(PROB_MUTACION, new Pair<Double,Double>(0.0,100.0));
		}};
	}

	public void run() {
		algoritmo.clearMejores();
		algoritmo.run();
	}
	
	public void run(String key) {
		
		algoritmo.clearMejores();
		
		if(!parseMinMax(key))
			return;
		
		double inc = (max - min) / 10;
		double valor = min;
		for (int i = 0; i < 10; i++) {
			mapaFunciones.get(key).apply(valor);
			algoritmo.run();
			algoritmo.addValor(valor);
			valor += inc;
		}
		algoritmo.finIntervalos(key);
	}
	
	private boolean parseMinMax(String key) {
		if (min < mapaExcepciones.get(key).getFirst() || max > mapaExcepciones.get(key).getSecond()) {
			algoritmo.throwException("Intervalo inválido");
			return false;
		}
		else if(min > max) {
			algoritmo.throwException("El valor mínimo debe ser menor que el valor máximo");
			return false;
		}
		
		return true;
	}

	public void reset() {
		algoritmo.reset();  
	}
	
	public void addObserver(Observer o) {
		algoritmo.addObserver(o);
	}

	public void removeObserver(Observer o) {
		algoritmo.removeObserver(o);
	}

	public String[] getFuncionKey(){
		String[] ret = new String[mapaFactories.size()];
		int i = 0;
		for (String clave:mapaFactories.keySet()) {
		   ret[i] = clave;
		   i++;
		}
		return ret;
	}
	
	public void updatePobSize(double parseInt) {
		algoritmo.setPobSize((int)parseInt);
		
	}

	public void updateGenSize(double parseInt) {
		algoritmo.setGenSize((int)parseInt);
	}

	public void updateProbCruce(double d) {
		algoritmo.setProbCruce(d);
	}

	public void updateProbMuta(double d) {
		algoritmo.setProbMutacion(d);
	}

	public void updatePorElite(double d) {
		algoritmo.setPorElitismo(d);
	}


	public void updateSeleccion(String selectedItem) {
		algoritmo.setSeleccion(mapaSeleccion.get(selectedItem));
	}


	public void updateIndividuoFactory(String selectedItem) {
		algoritmo.setIndividuoFactory(mapaFactories.get(selectedItem).getFirst());
	}
	
	public void updateTipoCreacion(String selectedItem) { // Agregada
		algoritmo.setCreacion(mapaCreacion.get(selectedItem));
	}
	
	public void updateCruce(String selectedItem) {
		algoritmo.setCruce(this.mapaCruce.get(selectedItem));
	}

	public Map<String, Pair<IndividuoFactory, Cuarteto<ArrayList<String>,ArrayList<String>,ArrayList<String>,ArrayList<String>>>> getMapaFactories() {
		return Collections.unmodifiableMap(mapaFactories);
	}

	public void updateMutacion(String selectedItem) {
		algoritmo.setMutacion(this.mapaMutacion.get(selectedItem));
	}
	
	public void setMin(double min) {
		this.min = min;
	}
	
	public void setMax(double max) {
		this.max = max;
	}

	public void updateBloating(boolean value) {
		algoritmo.setBloating(value);
	}

	public void updateProfundidad(int value) {
		algoritmo.setProfundidad(value);
		
	}
}