package launcher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import controller.Controller;
import factories.IndividuoArbolFactory;
import factories.IndividuoFactory;
import model.AlgoritmoGenetico;
import model.creacion.Creacion;
import model.creacion.CreacionCompleta;
import model.creacion.CreacionCreciente;
import model.creacion.CreacionRampedHalf;
import model.cruce.Cruce;
import model.cruce.CruceOperador;
import model.mutacion.Mutacion;
import model.mutacion.MutacionArSub;
import model.mutacion.MutacionFuncional;
import model.mutacion.MutacionTerminal;
import model.seleccion.EstocasticoUniversal;
import model.seleccion.Ranking;
import model.seleccion.Restos;
import model.seleccion.Seleccion;
import model.seleccion.SeleccionRuleta;
import model.seleccion.TorneoDeterministico;
import model.seleccion.TorneoProbabilistico;
import model.seleccion.Truncamiento;
import utils.Cuarteto;
import utils.Pair;
import view.MainWindow;

public class Main {

	private static Map<String, Pair<IndividuoFactory, Cuarteto<ArrayList<String>,ArrayList<String>,ArrayList<String>,ArrayList<String>>>> mapaFactories = null;

	private static Map<String, Seleccion> mapaSeleccion = null;

	private static Map<String, Cruce> mapaCruce = null;
	
	private static Map<String, Mutacion> mapaMutacion = null;
	
	private static Map<String, Creacion> mapaCreacion = null;
	
	private static ArrayList<String> listaCreacion = null;
	
	private static ArrayList<String> listaSeleccion = null;
	
	private static ArrayList<String> listaMutacionArbol = null;
	
	private static ArrayList<String> listaCruceArbol = null;
	

	public static void main(String[] args) {
		
		initMaps();
		AlgoritmoGenetico algoritmo = new AlgoritmoGenetico();
		Controller ctrl = new Controller(algoritmo,mapaFactories,mapaSeleccion, mapaCruce,mapaMutacion,mapaCreacion);
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new MainWindow(ctrl);
			}
		});	
	}
	
	private static void initMaps() {
		
		mapaSeleccion = new HashMap<String, Seleccion>(){{
			put("Ruleta", new SeleccionRuleta());
			put("Torneo Determinístico", new TorneoDeterministico());
			put("Torneo Probabilísitco", new TorneoProbabilistico());
			put("Estocástico Universal", new EstocasticoUniversal());
			put("Truncamiento", new Truncamiento());
			put("Restos", new Restos());
			put("Ranking", new Ranking());
		}};
		
		mapaCruce = new HashMap<String, Cruce>(){{
			put("Intercambio Subárbol", new CruceOperador());
		}};
		
		mapaMutacion = new HashMap<String, Mutacion>(){{
			put("Terminal", new MutacionTerminal());
			put("Funcional", new MutacionFuncional());
			put("Subárbol", new MutacionArSub());
		}};
		
		mapaCreacion = new HashMap<String,Creacion>(){{
			put("Creciente", new CreacionCreciente());
			put("Completa", new CreacionCompleta());
			put("Ramped and Half", new CreacionRampedHalf());
		}};
		
		listaCreacion = new ArrayList<String>() {{
			add("Completa");
			add("Creciente");
			add("Ramped and Half");
		}};
		
		listaSeleccion = new ArrayList<String>() {{
			add("Ruleta");
			add("Torneo Determinístico");
			add("Torneo Probabilísitco");
			add("Estocástico Universal");
			add("Truncamiento");
			add("Restos");
			add("Ranking");
		}};
	

		listaMutacionArbol = new ArrayList<String>(){{
			add("Terminal");
			add("Funcional");
			add("Subárbol");
		}}; 
		
		listaCruceArbol = new ArrayList<String>() {{
			add("Intercambio Subárbol");
		}};
		
		mapaFactories = new TreeMap<String, Pair<IndividuoFactory, Cuarteto<ArrayList<String>,ArrayList<String>,ArrayList<String>,ArrayList<String>>>>(){{
		 	put("Individuo Arbol", new Pair<IndividuoFactory, Cuarteto<ArrayList<String>, ArrayList<String>, ArrayList<String>,ArrayList<String>>>(new IndividuoArbolFactory(), new Cuarteto<ArrayList<String>,ArrayList<String>,ArrayList<String>,ArrayList<String>>(listaSeleccion,listaCruceArbol,listaMutacionArbol,listaCreacion)));
		}};
			
	}

}
