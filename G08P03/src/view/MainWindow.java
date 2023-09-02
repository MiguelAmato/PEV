package view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import controller.Controller;

public class MainWindow extends JFrame {
	private Controller _ctrl;  
	
	JPanel graph;

	private final int WIDTH = 1200;
	private final int HEIGHT = 745;
	
	private Graph grNormal;
	private AdvancedGraph grAdvanced;
	private FunctionGraph grFunction;
	
	public MainWindow(Controller ctrl) {
		super("Algoritmo Genético");
		_ctrl = ctrl;
		initGUI();
	}

	private void initGUI() {
		JPanel mainPanel = new JPanel(new BorderLayout());
	
		graph = new JPanel(new CardLayout());
		
		LeftPanel lp = new LeftPanel(_ctrl);
		BottomPanel bp = new BottomPanel(_ctrl);
		
		Mediator me = new Mediator(_ctrl, this, lp, bp);
		TopPanel tp = new TopPanel(_ctrl,lp,bp,me);
		
		grNormal = new Graph(_ctrl, me);
		grAdvanced = new AdvancedGraph(_ctrl, me);
		grFunction = new FunctionGraph(_ctrl,me);
		
		graph.add(grNormal, "GRAPH");
		graph.add(grAdvanced, "ADVANCED_GRAPH");
		graph.add(grFunction,"FUNCTION_GRAPH");
		
		mainPanel.add(tp, BorderLayout.PAGE_START);
		mainPanel.add(bp, BorderLayout.PAGE_END);
		mainPanel.add(lp, BorderLayout.WEST);
		mainPanel.add(graph, BorderLayout.CENTER);
		
		setBackground(Color.WHITE);
		this.setContentPane(mainPanel);
		this.pack();
		Dimension s = Toolkit.getDefaultToolkit().getScreenSize();
		setSize(WIDTH, HEIGHT);
		setLocation(s.width/2 - WIDTH /2, s.height/2 - HEIGHT /2);
		this.setVisible(true);
		
	}

	public void changeGraph(String key) {
		CardLayout cl = (CardLayout)(graph.getLayout());
		cl.show(graph, key);
	}

	public void resetGraph() {
		grNormal.onReset();
		grAdvanced.onReset();
		grFunction.onReset();
	}
}
