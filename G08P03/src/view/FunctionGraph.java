package view;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import org.math.plot.Plot2DPanel;

import controller.Controller;
import model.AlgoritmoGenetico;
import model.observers.Observer;
import utils.Pair;

public class FunctionGraph extends JPanel implements Observer {

	private final static String FG = "FUNCTION_GRAPH";
	
	Controller ctrl;
	
	Mediator me;
	
	Plot2DPanel plot;
	
	String key;
	
	int maxGeneraciones;
	
	public FunctionGraph(Controller _ctrl, Mediator me) {
		ctrl = _ctrl;
		this.me = me;
		ctrl.addObserver(this);
		key = "Ninguno";
		initGUI();
	}

	private void initGUI() {
		setLayout(new BorderLayout());
		setBackground(Color.WHITE);
		Border _defaultBorder = BorderFactory.createLineBorder(Color.BLACK, 1);
		setBorder(BorderFactory.createTitledBorder(_defaultBorder, "Gráfica Funciones", TitledBorder.LEFT,TitledBorder.TOP));
		
		plot = new Plot2DPanel();
		
		plot.setAxisLabels("x", "y");

		plot.addLegend("SOUTH");
		
		add(plot, BorderLayout.CENTER);
	}

	@Override
	public void onEnd(AlgoritmoGenetico algoritmo, String key) {
		if (key.equals("Ninguno")) {
			plot.setAxisLabels("x", "y");
			plot.removeAllPlots();
			
			
			Pair<double[], double[]> funcionIndi = algoritmo.getMejor().getFunction();
			Pair<double[], double[]> funcion = algoritmo.getMejor().getListFunction();
			
			plot.addLinePlot("F(x) = x^4 + x^3 + x^2 + x + 1 ", funcion.getFirst(), funcion.getSecond());
			plot.addLinePlot("Funcion Individuo", funcionIndi.getFirst(), funcionIndi.getSecond());
		}
	}
	
	@Override
	public void onReset() {
		plot.removeAllPlots();		
	}

	@Override
	public void onError(String exception) {
		// TODO Auto-generated method stub
		
	}
}
