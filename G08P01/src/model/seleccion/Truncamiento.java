package model.seleccion;

import java.util.ArrayList;
import java.util.Collections;

import model.individuos.Individuo;

public class Truncamiento implements Seleccion{

	
	private final double P = 0.4;  
			
	@Override
	public ArrayList<Integer> seleccionar(ArrayList<Individuo> poblacion,int numSeleccionar) {
		
		
 		double indiVueltas = numSeleccionar * P;
		
		ArrayList<Integer> seleccionados = new ArrayList<Integer>(numSeleccionar);
		
		while(seleccionados.size() < numSeleccionar){
			int i = 0; 
			while(i < indiVueltas && seleccionados.size() < numSeleccionar) {
				seleccionados.add(poblacion.size()-1-i);
				i++;
			}
		}
		Collections.shuffle(seleccionados);

		return seleccionados;
	}

}
