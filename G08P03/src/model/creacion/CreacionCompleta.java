package model.creacion;

import java.util.ArrayList;

import factories.IndividuoFactory;
import model.individuos.Individuo;

public class CreacionCompleta implements Creacion{

	public ArrayList<Individuo> generarPoblacion(IndividuoFactory factory, int profundidad, int tamPob) {
		ArrayList<Individuo> poblacion = new ArrayList<Individuo>();
		
		for(int i = 0; i < tamPob;i++) {
			poblacion.add(factory.generateInd("COMPLETO",profundidad));
		}
		return poblacion;
	}
}
