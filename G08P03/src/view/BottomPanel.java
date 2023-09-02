package view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
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
	
	JButton resetButton, ejecutarButton, adEjecutarButton;
	JPanel textPanel;
	JLabel solucionLabel;
	JTextArea solucionText;
	JPanel buttonPanel, ejecutarPanel, adEjecutarPanel;
	
	String intervaloActual = "1";
	
	
	public BottomPanel(Controller ctrl) {
		_ctrl = ctrl;
		_ctrl.addObserver(this);
		initGUI();
	}

	protected void initGUI() {
		setFloatable(false);
		
		buttonsConfiguration();
		textConfiguration();
		
		JPanel aux = new JPanel(new FlowLayout());
		aux.setBackground(Color.WHITE);
		buttonPanel = new JPanel(new CardLayout());
		buttonPanel.setBackground(Color.WHITE);
		buttonPanel.add(ejecutarPanel, "1");
		buttonPanel.add(adEjecutarPanel, "2");
		
		aux.add(buttonPanel);
		addSeparator();
		add(resetButton);
		add(textPanel);
		add(aux);
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
		
		ejecutarPanel = new JPanel(new BorderLayout());
		ejecutarPanel.setBackground(Color.WHITE);
		adEjecutarPanel = new JPanel(new BorderLayout());
		adEjecutarPanel.setBackground(Color.WHITE);
		
		resetButton = new JButton("Resetear"); 
		ejecutarButton = new JButton("Ejecutar");
		adEjecutarButton = new JButton("Ejecutar");
		
		ejecutarPanel.add(ejecutarButton, BorderLayout.CENTER);
		adEjecutarPanel.add(adEjecutarButton, BorderLayout.CENTER);
		
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
		adEjecutarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				_ctrl.run(intervaloActual); 
			}
		});
	}

	@Override
	public void onEnd(AlgoritmoGenetico algoritmo, String key) {
		if (key.equals("Ninguno")) {
			
			solucionText.setText(algoritmo.getMejor().getDeco());
			//System.out.println(algoritmo.getMejor().toString());
		}
		else 
			solucionText.setText(algoritmo.setTextIntervalos());
			
	}

	@Override
	public void onReset() {
		solucionText.setText("");	
	}

	@Override
	public void onError(String exception) {
		// TODO Auto-generated method stub
		
	}

	public void changePanel(String item) {
		CardLayout cl = (CardLayout)(buttonPanel.getLayout());
		if (item.equals("Ninguno")) {
			cl.show(buttonPanel, "1");
			intervaloActual = "1";
		}
		else {
			cl.show(buttonPanel, "2");
			intervaloActual = item;

		}
		
	}

}
