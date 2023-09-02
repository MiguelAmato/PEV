package model.individuos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import model.cruce.CruceOperador;
import model.mutacion.MutacionTerminal;
import utils.BinTree;
import utils.Function;
import utils.Function2;
import utils.Pair;

public class IndividuoArbol extends Individuo<BinTree<String>>{
	
	public final static String COMPLETO = "COMPLETO";
	public final static String CRECIENTE = "CRECIENTE";
	
	public static final int MAX_PROFUNDIDAD = 5;
	
	public static final Set<String> operadores = new HashSet<String>() {{add("add"); add("mul"); add("sub");}};
	
	public static final Set<String> terminales = new HashSet<String>() {{add("x"); add("1"); add("2"); add("-1"); add("-2");}};
	

	public static final ArrayList<Pair<Double,Double>> fitnessAux = new ArrayList<Pair<Double,Double>>(){{
	
		add(new Pair<Double,Double>(-1.0,1.0));
		add(new Pair<Double,Double>(-0.98,0.9615));
		add(new Pair<Double,Double>(-0.96,0.9262));
		add(new Pair<Double,Double>(-0.94,0.8937));
		add(new Pair<Double,Double>(-0.92,0.8641));
		add(new Pair<Double,Double>(-0.9,0.8371));
		add(new Pair<Double,Double>(-0.88,0.8126));
		add(new Pair<Double,Double>(-0.86,0.7905));
		add(new Pair<Double,Double>(-0.84,0.7707));
		add(new Pair<Double,Double>(-0.82,0.7531));
		add(new Pair<Double,Double>(-0.8,0.7375));
		add(new Pair<Double,Double>(-0.78,0.7239));
		add(new Pair<Double,Double>(-0.76,0.7122));
		add(new Pair<Double,Double>(-0.74,0.7022));
		add(new Pair<Double,Double>(-0.72,0.6938));
		add(new Pair<Double,Double>(-0.7,0.6870));
		add(new Pair<Double,Double>(-0.68,0.6817));
		add(new Pair<Double,Double>(-0.66,0.6778));
		add(new Pair<Double,Double>(-0.64,0.6752));
		add(new Pair<Double,Double>(-0.62,0.6738));
		add(new Pair<Double,Double>(-0.6,0.6736));
		add(new Pair<Double,Double>(-0.58,0.6744));
		add(new Pair<Double,Double>(-0.56,0.6763));
		add(new Pair<Double,Double>(-0.54,0.6791));
		add(new Pair<Double,Double>(-0.52,0.6829));
		add(new Pair<Double,Double>(-0.5,0.6875));
		add(new Pair<Double,Double>(-0.48,0.6928));
		add(new Pair<Double,Double>(-0.46,0.6990));
		add(new Pair<Double,Double>(-0.44,0.7058));
		add(new Pair<Double,Double>(-0.42,0.7134));
		add(new Pair<Double,Double>(-0.4,0.7216));
		add(new Pair<Double,Double>(-0.38,0.7303));
		add(new Pair<Double,Double>(-0.36,0.7397));
		add(new Pair<Double,Double>(-0.34,0.7496));
		add(new Pair<Double,Double>(-0.32,0.7601));
		add(new Pair<Double,Double>(-0.3,0.7711));
		add(new Pair<Double,Double>(-0.28,0.7825));
		add(new Pair<Double,Double>(-0.26,0.7945));
		add(new Pair<Double,Double>(-0.24,0.8070));
		add(new Pair<Double,Double>(-0.22,0.8200));
		add(new Pair<Double,Double>(-0.2,0.8336));
		add(new Pair<Double,Double>(-0.18,0.8476));
		add(new Pair<Double,Double>(-0.16,0.8621));
		add(new Pair<Double,Double>(-0.14,0.8772));
		add(new Pair<Double,Double>(-0.12,0.8928));
		add(new Pair<Double,Double>(-0.1,0.9091));
		add(new Pair<Double,Double>(-0.08,0.9259));
		add(new Pair<Double,Double>(-0.06,0.9433));
		add(new Pair<Double,Double>(-0.04,0.9615));
		add(new Pair<Double,Double>(-0.02,0.9803));
		add(new Pair<Double,Double>(0.0,1.0));
		add(new Pair<Double,Double>(0.02,1.0204));
		add(new Pair<Double,Double>(0.04,1.0416));
		add(new Pair<Double,Double>(0.06,1.0638));
		add(new Pair<Double,Double>(0.08,1.0869));
		add(new Pair<Double,Double>(0.10,1.1111));
		add(new Pair<Double,Double>(0.12,1.1363));
		add(new Pair<Double,Double>(0.14,1.1627));
		add(new Pair<Double,Double>(0.16,1.1903));
		add(new Pair<Double,Double>(0.18,1.2192));
		add(new Pair<Double,Double>(0.20,1.2496));
		add(new Pair<Double,Double>(0.22,1.2813));
		add(new Pair<Double,Double>(0.24,1.3147));
		add(new Pair<Double,Double>(0.26,1.3497));
		add(new Pair<Double,Double>(0.28,1.3864));
		add(new Pair<Double,Double>(0.30,1.4251));
		add(new Pair<Double,Double>(0.32,1.4656));
		add(new Pair<Double,Double>(0.34,1.5082));
		add(new Pair<Double,Double>(0.36,1.5530));
		add(new Pair<Double,Double>(0.38,1.6001));
		add(new Pair<Double,Double>(0.40,1.6496));
		add(new Pair<Double,Double>(0.42,1.7016));
		add(new Pair<Double,Double>(0.44,1.7562));
		add(new Pair<Double,Double>(0.46,1.8137));
		add(new Pair<Double,Double>(0.48,1.8740));
		add(new Pair<Double,Double>(0.50,1.9375));
		add(new Pair<Double,Double>(0.52,2.0041));
		add(new Pair<Double,Double>(0.54,2.0740));
		add(new Pair<Double,Double>(0.56,2.1475));
		add(new Pair<Double,Double>(0.58,2.2246));
		add(new Pair<Double,Double>(0.60,2.3056));
		add(new Pair<Double,Double>(0.62,2.3904));
		add(new Pair<Double,Double>(0.64,2.4795));
		add(new Pair<Double,Double>(0.66,2.5728));
		add(new Pair<Double,Double>(0.68,2.6706));
		add(new Pair<Double,Double>(0.70,2.7731));
		add(new Pair<Double,Double>(0.72,2.8803));
		add(new Pair<Double,Double>(0.74,2.9926));
		add(new Pair<Double,Double>(0.76,3.1101));
		add(new Pair<Double,Double>(0.78,3.2331));
		add(new Pair<Double,Double>(0.80,3.3616));
		add(new Pair<Double,Double>(0.82,3.4958));
		add(new Pair<Double,Double>(0.84,3.6361));
		add(new Pair<Double,Double>(0.86,3.7826));
		add(new Pair<Double,Double>(0.88,3.9355));
		add(new Pair<Double,Double>(0.90,4.0951));
		add(new Pair<Double,Double>(0.92,4.2614));
		add(new Pair<Double,Double>(0.94,4.4349));
		add(new Pair<Double,Double>(0.96,4.6156));
		add(new Pair<Double,Double>(0.98,4.8039));
		add(new Pair<Double,Double>(1.00,5.0000));
	}};
	
	private static int profundidad;

	
	private static String tipoCreacion;
	
	private Map<String, Function2<BinTree<String>, Integer>> mapaFunciones = new HashMap<String, Function2<BinTree<String>, Integer>>() {{
		put(COMPLETO, (x,y) -> inicializacionCompleta(x,y));
		put(CRECIENTE, (x,y) -> inicializacionCreciente(x,y));
	}};
	
	
	public IndividuoArbol(String tipoCreacion, int profundidad) {
		this.profundidad = profundidad;
		IndividuoArbol.tipoCreacion = tipoCreacion;
		this.cromosoma = new BinTree<String>();
		mapaFunciones.get(tipoCreacion).apply(cromosoma,1);
		this.fitness = this.getValor();
	}
	
	public IndividuoArbol(BinTree<String> cromosoma) {
		this.cromosoma = new BinTree<String>();
		//this.cromosoma.setArbol(cromosoma);
		this.cromosoma = this.cromosoma.copiarArbol(cromosoma);
		this.fitness = this.getValor();
	}
	

	public BinTree<String> inicializacionCompletaAux(int profundidad) {
		BinTree<String> arbolAux;
		if (profundidad < this.profundidad) {
			arbolAux = new BinTree<String>(randElem(operadores));
			arbolAux.setArbolIzq(inicializacionCompletaAux(profundidad+1));
			arbolAux.setArbolDer(inicializacionCompletaAux(profundidad+1));
		}
		else {
			arbolAux = new BinTree<String>(randElem(terminales));
		}
		return arbolAux;
	}
	
	public BinTree<String> inicializacionCrecienteAux(int profundidad) { 
		BinTree<String> arbolAux;
		this.rand = new Random();
		
		if(profundidad == 1) {
			arbolAux = new BinTree<String>(randElem(operadores));
			arbolAux.setArbolIzq(inicializacionCrecienteAux(profundidad+1));
			arbolAux.setArbolDer(inicializacionCrecienteAux(profundidad+1));
		}
		
		else if (profundidad < this.profundidad) {
			String[] nodo = new String[2];
			nodo[0] = randElem(operadores);
			nodo[1] = randElem(terminales);
			arbolAux = new BinTree<String>(nodo[rand.nextInt(0,2)]);
			arbolAux.setArbolIzq(inicializacionCrecienteAux(profundidad+1));
			arbolAux.setArbolDer(inicializacionCrecienteAux(profundidad+1));
		}
		
		else {
			arbolAux = new BinTree<String>(randElem(terminales));
		}
		return arbolAux;
	}
		
	private void toArrayAux(ArrayList<String> array, BinTree<String> a){
		array.add(a.getDato());
		toArrayAux(array,a.getIzq());
		toArrayAux(array,a.getDer());
	}
	
	private void inicializacionCompleta(BinTree<String> arbol, int profundidad) {
		arbol.setArbol(inicializacionCompletaAux(profundidad));
	}
	
	private void inicializacionCreciente(BinTree<String> arbol,int profundidad) {
		arbol.setArbol(inicializacionCrecienteAux(profundidad));
	}
	
	private void RampedAndHalf(BinTree<String> arbol,int profundidad) {
		arbol.setArbol(inicializacionCompletaAux(profundidad));
	}
	
	private double funcArbol(BinTree<String> arbol, double valor) {
		switch(arbol.getDato()) {
			case "add": return (funcArbol(arbol.getIzq(),valor) + funcArbol(arbol.getDer(),valor));
			case "sub":return (funcArbol(arbol.getIzq(),valor) - funcArbol(arbol.getDer(),valor));
			case "mul": return (funcArbol(arbol.getIzq(),valor) * funcArbol(arbol.getDer(),valor));
			case "x": return valor;
			case "y": return valor;
			default: return Integer.parseInt(arbol.getDato());
		}	
	}
	
	@Override
	protected double getValor() {
		
		double sum = 0;
		for(int i = 0; i < fitnessAux.size();i++) {
			double fAux = 0;
			fAux = funcArbol(this.cromosoma,fitnessAux.get(i).getFirst());

			sum += Math.pow(fitnessAux.get(i).getSecond() - fAux,2);
		}
		sum = Math.sqrt(sum);
		return sum;
	}

	public void mutarTer(double prob){
	
		this.rand = new Random();
		
		if(rand.nextDouble(0,1) < prob) {
			BinTree<String> punto1 = cromosoma;
			
			while(!terminales.contains(punto1.getDato())) {
				if(rand.nextDouble(0,1) < 0.5) {
					punto1 = punto1.getDer();
				}
				else 
					punto1 = punto1.getIzq();
			}
			
			String dato = randElem(terminales);
			while(dato == punto1.getDato()) {
				dato = randElem(terminales);
			}
			
			punto1.setDato(dato);
			this.fitness = this.getValor();
		}
		
	}
	
	public void mutarFun(double prob) {
		this.rand = new Random();
		
		if(rand.nextDouble(0,1) < prob) {
			BinTree<String> punto1 = cromosoma;
			
			while(!(terminales.contains(punto1.getDer()) && terminales.contains(punto1.getIzq()))) {
				if(!terminales.contains(punto1.getDer().getDato()) && (rand.nextDouble(0,1) < 0.5)) {
					punto1 = punto1.getDer();
				}
				
				else if(!terminales.contains(punto1.getIzq().getDato()) && (rand.nextDouble(0,1) < 0.5)) {
					punto1 = punto1.getIzq();
				}
					
				else
					break;
			}
			
			String dato = randElem(operadores);
			while(dato == punto1.getDato()) {
				dato = randElem(operadores);
			}
			
			punto1.setDato(dato);
			this.fitness = this.getValor();
		}
	
	}
	
	public void mutarSub(double prob) {
		this.rand = new Random();
		
		if(rand.nextDouble(0,1) < prob) {
			BinTree<String> punto1 = cromosoma;
			
			while(!terminales.contains(punto1.getDato())){
				if(rand.nextDouble(0,1) < 0.5) {
					punto1 = punto1.getIzq();
				}
				else if(rand.nextDouble(0,1) < 0.5) {
					punto1 = punto1.getDer();
				}
				else 
					break;
			}
			
			BinTree<String> aux = new BinTree<String>();
			mapaFunciones.get(tipoCreacion).apply(aux,1);
			
			punto1.setArbol(aux);
			this.fitness = this.getValor();
		}
	}
	
	public static void main (String[] args) {
		IndividuoArbol a = new IndividuoArbol("CRECIENTE",4);

		System.out.println(a.decodificar());
		
		a.mutarSub(1);
		
		System.out.println(a.decodificar());
		
	}
	
	
	@Override
	public String decodificar() {
		double x = this.fitness;
		double y = this.getValor();
		if (x != y) {
			System.out.println("cuidado");
		}
		String str = "F(x) = " + arbol_a_string(cromosoma) + " valor: ";
		if(this.fitness < 0.001) {
			str += "0";
			return str;
		}
		return str += this.fitness;
	}
	
	private String arbol_a_string(BinTree<String> arbol) {
		
		if(terminales.contains(arbol.getDato()))
	        return arbol.getDato();
	    else {
	        String iz = arbol_a_string(arbol.getIzq());
	        String der = arbol_a_string(arbol.getDer());
	        if(arbol.getDato().equals("add"))
	            return "(" + iz + " + " + der + ")";
	        else if (arbol.getDato().equals("sub"))
	            return "(" + iz + " - " + der + ")";
	        else 
	            return "(" + iz + " * " + der + ")";
	    }
	}
	
	public double getFenotipo(int gen) {
		return 0;
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
	
	@Override
	public String toString() {
		return arbol_a_string(cromosoma);
	}
	
	private String randElem(Set<String> set) {
		this.rand = new Random();
		int size = set.size();
		int item = rand.nextInt(size); 
		int i = 0;
		for(String s : set){
		    if (i == item)
		        return s;
		    i++;
		}
		return null;
	}
	
	@Override
	public void setCromosoma(BinTree<String> cromosoma) { 
		this.cromosoma = new BinTree<String>();
		this.cromosoma = this.cromosoma.copiarArbol(cromosoma);
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
	
	public int getNumNodos() {
		return cromosoma.getNumNodos();
	}

	
	public Pair<double[], double[]> getListFunction() {
		double x[] = new double[fitnessAux.size()];
		for(int i = 0; i < fitnessAux.size();i++) {
			x[i] = fitnessAux.get(i).getFirst();
		}
		
		double y[] = new double[fitnessAux.size()];
		for(int i = 0; i < fitnessAux.size();i++) {
			y[i] = fitnessAux.get(i).getSecond();
		}
		
		return new Pair<double[], double[]>(x,y);
	}

	@Override
	public Pair<double[], double[]> getFunction() {
		double x[] = new double[fitnessAux.size()];
		for(int i = 0; i < fitnessAux.size();i++) {
			x[i] = fitnessAux.get(i).getFirst();
		}
		double y[] = new double[fitnessAux.size()];
		for(int i = 0; i < fitnessAux.size();i++) {
			y[i] = funcArbol(this.cromosoma,fitnessAux.get(i).getFirst());
		}
		return new Pair<double[], double[]>(x,y);
	}
}

