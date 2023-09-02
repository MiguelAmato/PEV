package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import controller.Controller;
import factories.IndividuoFactory;
import model.AlgoritmoGenetico;
import model.cruce.Cruce;
import model.observers.Observer;
import model.seleccion.Seleccion;

public class TopPanel extends JPanel implements Observer {
	
	private Controller _ctrl;
	
	private String claveGrafica; 
	
	private JLabel problemaLabel, creacionLabel, profundidadLabel;
	
	private JComboBox problemaBox, parametrosBox, creacionBox;
		
	private LeftPanel lp; 	
	
	private JButton botonCambio;
	
	private JSpinner profundidadSpinner;
	
	private BottomPanel bp;
	
	private Mediator med;
	
	public TopPanel(Controller ctrl, LeftPanel lp, BottomPanel bp, Mediator med) {
		_ctrl = ctrl;
		this.lp = lp;
		this.bp = bp;
		this.med = med;
		claveGrafica = "GRAPH";
		_ctrl.addObserver(this);
		initGUI();
	}

	private void initGUI() {
		
		
		parametrosBox = new JComboBox<String>(lp.getOpciones());
		
		parametrosBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lp.changePanel((String)parametrosBox.getSelectedItem());
				bp.changePanel((String)parametrosBox.getSelectedItem());
				String item = (String)parametrosBox.getSelectedItem();
				if(!item.equals("Ninguno")) {
					botonCambio.setVisible(false);
				}
				else {
					botonCambio.setVisible(true);
					med.resetGraph();
				}
			}	
		});
	
		JSeparator separator1 = new JSeparator(JSeparator.VERTICAL);
		separator1.setPreferredSize(new Dimension(20, 0));
		
		problemaLabel = new JLabel("Problema:");
		
		JSeparator separator2 = new JSeparator(JSeparator.VERTICAL);
		separator2.setPreferredSize(new Dimension(5, 0));
		
		problemaBox = new JComboBox<String>(_ctrl.getFuncionKey());
		
		problemaBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				_ctrl.updateIndividuoFactory((String) problemaBox.getSelectedItem());		
				lp.setIndividuo((String) problemaBox.getSelectedItem());
				lp.refreshCruceBox();
				lp.refreshMutacionBox();
			}	
		});
		
		JSeparator separator3 = new JSeparator(JSeparator.VERTICAL);
		separator3.setPreferredSize(new Dimension(5, 0)); 
		
		creacionLabel = new JLabel("Tipo de creación: ");
		
		String[] crea = new String[_ctrl.getMapaFactories().get((String) problemaBox.getSelectedItem()).getSecond().getFourth().size()];
		
		for(int i = 0; i < crea.length;i++) {
			crea[i] = _ctrl.getMapaFactories().get((String) problemaBox.getSelectedItem()).getSecond().getFourth().get(i);
		}
		
		creacionBox = new JComboBox<String>(crea);
		
		creacionBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				_ctrl.updateTipoCreacion((String) creacionBox.getSelectedItem());
			}	
		});
		
		JSeparator separator4 = new JSeparator(JSeparator.VERTICAL);
		separator4.setPreferredSize(new Dimension(5, 0)); 
		
		profundidadLabel = new JLabel("Profundidad máxima: ");
		
		SpinnerNumberModel sp = new SpinnerNumberModel(5,2,5,1);
		profundidadSpinner = new JSpinner(sp);
		profundidadSpinner.setPreferredSize(new Dimension(40,25));
		//profundidadSpinner.setEnabled(_ctrl.getMapaFactories().get((String) problemaBox.getSelectedItem()).getSecond());

		
		profundidadSpinner.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				_ctrl.updateProfundidad((int)profundidadSpinner.getValue());
			}
			
		});
		
		JSeparator separator5 = new JSeparator(JSeparator.VERTICAL);
		separator5.setPreferredSize(new Dimension(5, 0)); 
		
		botonCambio = new JButton("Grafica Función");
		botonCambio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(claveGrafica.equals("GRAPH")) {
					med.changeGraph("FUNCTION_GRAPH");
					claveGrafica = "FUNCTION_GRAPH";
				}
				
				else if(claveGrafica.equals("FUNCTION_GRAPH")) {
					med.changeGraph("GRAPH");
					claveGrafica = "GRAPH";
				}
			}
		});
		
		
		add(new JLabel("Análisis de parametros: "));
		add(parametrosBox);
 		add(separator1);
		add(problemaLabel);
		add(separator2);
		add(problemaBox);
		add(separator3);
		add(creacionLabel);
		add(creacionBox);
		add(separator4);
		add(profundidadLabel);
		add(profundidadSpinner);
		add(separator5);
		add(botonCambio);
		setBackground(Color.WHITE);
		setVisible(true);
		
		
		setPreferredSize(new Dimension(1500, 40));
	}

	@Override
	public void onEnd(AlgoritmoGenetico algoritmo, String key) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onReset() {
        problemaBox.setSelectedIndex(0);
        parametrosBox.setSelectedIndex(0);
        profundidadSpinner.setValue(5);
        creacionBox.setSelectedIndex(0);
    }

	@Override
	public void onError(String exception) {
		// TODO Auto-generated method stub
		
	}
}
