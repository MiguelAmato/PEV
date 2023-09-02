package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
	
	JCheckBox variedad;
	
	JLabel problemaLabel, dimensionLabel;
	
	JComboBox problemaBox;
	
	JSpinner dimensionSpinner;
	
	LeftPanel lp;
	
	public TopPanel(Controller ctrl, LeftPanel lp) {
		_ctrl = ctrl;
		this.lp = lp;
		_ctrl.addObserver(this);
		initGUI();
	}

	private void initGUI() {
		
		variedad = new JCheckBox("Introducir variedad");
		variedad.setBackground(Color.WHITE);
		
		JSeparator separator1 = new JSeparator(JSeparator.VERTICAL);
		separator1.setPreferredSize(new Dimension(20, 0));
		
		problemaLabel = new JLabel("Problema:");
		
		JSeparator separator2 = new JSeparator(JSeparator.VERTICAL);
		separator2.setPreferredSize(new Dimension(5, 0));
		
		problemaBox = new JComboBox<String>(_ctrl.getFuncionKey());
		
		problemaBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				_ctrl.updateIndividuoFactory((String) problemaBox.getSelectedItem());		
				dimensionSpinner.setEnabled(_ctrl.getMapaFactories().get((String) problemaBox.getSelectedItem()).getSecond());
				_ctrl.updateListaCruces((String) problemaBox.getSelectedItem());
				lp.refreshCruceBox();
			}	
		});
		
		JSeparator separator3 = new JSeparator(JSeparator.VERTICAL);
		separator3.setPreferredSize(new Dimension(5, 0)); 
				
		dimensionLabel = new JLabel("Dimension:  ");

		SpinnerNumberModel sp = new SpinnerNumberModel(2,1,10,1);
		dimensionSpinner = new JSpinner(sp);
		dimensionSpinner.setPreferredSize(new Dimension(40,25));
		dimensionSpinner.setEnabled(_ctrl.getMapaFactories().get((String) problemaBox.getSelectedItem()).getSecond());

		
		dimensionSpinner.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				_ctrl.updateDimension((int)dimensionSpinner.getValue());
			}
			
		});
		
		add(variedad);
		add(separator1);
		add(problemaLabel);
		add(separator2);
		add(problemaBox);
		add(separator3);
		add(dimensionLabel);
		add(dimensionSpinner);
		setBackground(Color.WHITE);
		setVisible(true);
		
		
		setPreferredSize(new Dimension(1500, 40));
	}

	@Override
	public void onEnd(AlgoritmoGenetico algoritmo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onReset() {
        problemaBox.setSelectedIndex(0);

    }

	@Override
	public void onError(String exception) {
		// TODO Auto-generated method stub
		
	}
}
