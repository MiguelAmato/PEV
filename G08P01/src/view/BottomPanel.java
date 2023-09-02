package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.border.EmptyBorder;

import controller.Controller;
import factories.IndividuoFactory;
import model.AlgoritmoGenetico;
import model.cruce.Cruce;
import model.observers.Observer;
import model.seleccion.Seleccion;

public class BottomPanel extends JToolBar implements Observer {
	
	private Controller _ctrl;
	
	JButton resetButton, ejecutarButton;
	JPanel textPanel;
	JLabel solucionLabel;
	JTextArea solucionText;
	
	
	public BottomPanel(Controller ctrl) {
		_ctrl = ctrl;
		_ctrl.addObserver(this);
		initGUI();
	}

	private void initGUI() {
		setFloatable(false);
		
		buttonsConfiguration();
		textConfiguration();
		
		addSeparator();
		add(resetButton);
		add(textPanel);
		add(ejecutarButton);
		addSeparator();
		
		setBackground(Color.WHITE);
		setVisible(true);
		setPreferredSize(new Dimension(1500, 40));
	}
	
	private void textConfiguration() {
		textPanel = new JPanel(new BorderLayout());
		textPanel.setBackground(Color.WHITE);
		
		JScrollPane scroll = new JScrollPane();
		
		solucionLabel = new JLabel("Solucion:  ");
		solucionText = new JTextArea("Aqui ira la solucion");
		solucionText.setEditable(false);
		
		scroll.setViewportView(solucionText);
		textPanel.add(solucionLabel,BorderLayout.WEST);
		textPanel.add(scroll);
	}
	
	private void buttonsConfiguration() {
		
		resetButton = new JButton("Resetear"); 
		ejecutarButton = new JButton("Ejecutar");
		actions();
		
	}

	private void actions() {
		ejecutarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				_ctrl.run();
			}
		});
		resetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				_ctrl.reset();
			}
		});
	}

	@Override
	public void onEnd(AlgoritmoGenetico algoritmo) {
		solucionText.setText(algoritmo.getMejor().getDeco());		
	}

	@Override
	public void onReset() {
		solucionText.setText("");	
	}

	@Override
	public void onError(String exception) {
		// TODO Auto-generated method stub
		
	}

}
