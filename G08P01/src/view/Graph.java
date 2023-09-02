package view;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import org.math.plot.*;

import controller.Controller;
import model.AlgoritmoGenetico;
import model.observers.Observer;

public class Graph extends JPanel implements Observer {
	
	Controller ctrl;
	
	Plot2DPanel plot;
	
	int maxGeneraciones;
	
	public Graph(Controller _ctrl) {
		ctrl = _ctrl;
		ctrl.addObserver(this);
		initGUI();
	}

	private void initGUI() {
		setLayout(new BorderLayout());
		setBackground(Color.WHITE);
		Border _defaultBorder = BorderFactory.createLineBorder(Color.BLACK, 1);
		setBorder(BorderFactory.createTitledBorder(_defaultBorder, "Gráfica", TitledBorder.LEFT,TitledBorder.TOP));
		
		plot = new Plot2DPanel();
		
		plot.setAxisLabels("  Generacion", "Valor de la función");

		plot.addLegend("SOUTH");
		
		add(plot, BorderLayout.CENTER);
	}

	@Override
	public void onEnd(AlgoritmoGenetico algoritmo) {
		plot.removeAllPlots();
		double[] generaciones = new double[algoritmo.getMaxGeneraciones() + 1];
		for (int i = 1; i <= algoritmo.getMaxGeneraciones(); ++i) {
			generaciones[i] = i;
		}
		plot.addLinePlot("Mejor Absoluto", generaciones, algoritmo.getMejorAbs());
		plot.addLinePlot("Mejor de la generación", generaciones, algoritmo.getMejorGeneracion());
		plot.addLinePlot("Media de la generación", generaciones, algoritmo.getMedias());
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



