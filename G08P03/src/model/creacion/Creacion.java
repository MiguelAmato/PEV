package model.creacion;

import java.util.ArrayList;

import factories.IndividuoFactory;
import model.individuos.Individuo;

public interface Creacion {
	public  ArrayList<Individuo> generarPoblacion(IndividuoFactory factory, int profundidad, int tamPob);
}
