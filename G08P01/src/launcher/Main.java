package launcher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.SwingUtilities;

import controller.Controller;
import factories.Individuo1Factory;
import factories.Individuo2Factory;
import factories.Individuo3Factory;
import factories.Individuo4AFactory;
import factories.Individuo4BFactory;
import factories.IndividuoFactory;
import model.AlgoritmoGenetico;
import model.cruce.Cruce;
import model.cruce.CruceAritmetico;
import model.cruce.CruceBLXA;
import model.cruce.CruceMonopunto;
import model.cruce.CruceUniforme;
import model.seleccion.EstocasticoUniversal;
import model.seleccion.Restos;
import model.seleccion.Seleccion;
import model.seleccion.SeleccionRuleta;
import model.seleccion.TorneoDeterministico;
import model.seleccion.TorneoProbabilistico;
import model.seleccion.Truncamiento;
import utils.TipoDato;
import utils.Trio;
import view.MainWindow;

public class Main {

	private static Map<String, Trio<IndividuoFactory, Boolean, TipoDato>> mapaFactories = null;

	private static Map<String, Seleccion> mapaSeleccion = null;
	
	private static ArrayList<String> listaSeleccion = null;
	
	private static Map<String, Cruce> mapaCruceDouble = null;
	
	private static ArrayList<String> listaCruceDouble = null;
	
	private static Map<String, Cruce> mapaCruceBool = null;
	
	private static ArrayList<String> listaCruceBool = null;


	public static void main(String[] args) {
		
		initMaps();
		AlgoritmoGenetico algoritmo = new AlgoritmoGenetico();
		Controller ctrl = new Controller(algoritmo,mapaFactories,mapaSeleccion, mapaCruceBool,mapaCruceDouble, listaCruceBool,listaCruceDouble,listaSeleccion);
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new MainWindow(ctrl);
			}
		});	
	}
	
	private static void initMaps() {
		
		 mapaFactories = new TreeMap<String, Trio<IndividuoFactory, Boolean, TipoDato>>(){{
				put("Función 1: Calibración y prueba",new Trio<IndividuoFactory, Boolean, TipoDato>(new Individuo1Factory(),false,TipoDato.BOOLEAN));
				put("Función 2: GrieWank",new Trio<IndividuoFactory, Boolean, TipoDato>(new Individuo2Factory(),false,TipoDato.BOOLEAN)); 
				put("Función 3: Styblinski-tang", new Trio<IndividuoFactory, Boolean, TipoDato>(new Individuo3Factory(),false,TipoDato.BOOLEAN));
				put("Función 4A: Michalewicz", new Trio<IndividuoFactory, Boolean, TipoDato>(new Individuo4AFactory(),true,TipoDato.BOOLEAN));
				put("Función 4B: Michalewicz", new Trio<IndividuoFactory, Boolean, TipoDato>(new Individuo4BFactory(),true,TipoDato.DOUBLE));
		}};
			
		mapaSeleccion = new HashMap<String, Seleccion>(){{
				put("Ruleta", new SeleccionRuleta());
				put("Torneo Determinístico", new TorneoDeterministico());
				put("Torneo Probabilísitco", new TorneoProbabilistico());
				put("Estocástico Universal", new EstocasticoUniversal());
				put("Truncamiento", new Truncamiento());
				put("Restos", new Restos());
		}};
		
		
		listaSeleccion = new ArrayList<String>() {{
				add("Ruleta");
				add("Torneo Determinístico");
				add("Torneo Probabilísitco");
				add("Estocástico Universal");
				add("Truncamiento");
				add("Restos");
		}};
		
		mapaCruceDouble = new HashMap<String, Cruce>(){{
				put("Cruce Monopunto", new CruceMonopunto());
				put("Cruce Uniforme" , new CruceUniforme());
				put("Cruce Aritmético", new CruceAritmetico());
				put("BLX-Alpha", new CruceBLXA());

		}};
		
		listaCruceDouble = new ArrayList<String>() {{
				add("Cruce Monopunto");
				add("Cruce Uniforme");
				add("Cruce Aritmético");
				add("BLX-Alpha");
		}};
		
		mapaCruceBool = new HashMap<String, Cruce>(){{
				put("Cruce Monopunto", new CruceMonopunto());
				put("Cruce Uniforme" , new CruceUniforme());
		}};
		
		listaCruceBool = new ArrayList<String>() {{
			add("Cruce Monopunto");
			add("Cruce Uniforme");
	}};
	}

}
