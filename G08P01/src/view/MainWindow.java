package view;

import java.awt.BorderLayout;
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

	private final int WIDTH = 1200;
	private final int HEIGHT = 745;
	
	public MainWindow(Controller ctrl) {
		super("Algoritmo Genético");
		_ctrl = ctrl;
		initGUI();
	}

	private void initGUI() {
		JPanel mainPanel = new JPanel(new BorderLayout());
		LeftPanel lp = new LeftPanel(_ctrl);
		mainPanel.add(new TopPanel(_ctrl,lp), BorderLayout.PAGE_START);
		mainPanel.add(new BottomPanel(_ctrl), BorderLayout.PAGE_END);
		mainPanel.add(lp, BorderLayout.WEST);
		mainPanel.add(new Graph(_ctrl), BorderLayout.CENTER);
		
		setBackground(Color.WHITE);
		this.setContentPane(mainPanel);
		this.pack();
		Dimension s = Toolkit.getDefaultToolkit().getScreenSize();
		setSize(WIDTH, HEIGHT);
		setLocation(s.width/2 - WIDTH /2, s.height/2 - HEIGHT /2);
		this.setVisible(true);
		
	}
}
