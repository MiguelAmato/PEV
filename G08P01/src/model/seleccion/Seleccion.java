package model.seleccion;

import java.util.ArrayList;

import model.individuos.Individuo;
import utils.Pair;

public interface Seleccion {
    public ArrayList<Integer> seleccionar(ArrayList<Individuo> poblacion, int numSeleccionar);
}
