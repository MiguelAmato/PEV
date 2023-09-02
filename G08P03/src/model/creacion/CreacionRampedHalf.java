package model.creacion;

import java.util.ArrayList;

import factories.IndividuoArbolFactory;
import factories.IndividuoFactory;
import model.individuos.Individuo;

public class CreacionRampedHalf implements Creacion{

	public ArrayList<Individuo> generarPoblacion(IndividuoFactory factory, int profundidad, int tamPob) {
	
		ArrayList<Individuo> poblacion = new ArrayList<Individuo>();
		
		int d = profundidad-1;
		
		int indGrupo = tamPob/d;
		
		for(int i = profundidad; i > 1; i--) {
			for(int j = 0; j < indGrupo/2;j++) {
				poblacion.add(factory.generateInd("COMPLETO",i));
				poblacion.add(factory.generateInd("CRECIENTE",i));
			}
		}
		
		int cont = poblacion.size();
		for(int i = 0; i < (tamPob - cont);i++) { // Este for rellena los individuos que faltan
			if(i % 2 == 0) 
				poblacion.add(factory.generateInd("COMPLETO",profundidad));
			else 
				poblacion.add(factory.generateInd("CRECIENTE",profundidad));
		}
				
		return poblacion;
	}

	public static void main(String[] args) {
		CreacionRampedHalf c = new CreacionRampedHalf();
		ArrayList<Individuo> poblacion;
			poblacion = c.generarPoblacion(new IndividuoArbolFactory(), 5,10);
		for(Individuo i: poblacion) {
			System.out.println(i.getDeco());
		}
	}
	
}
