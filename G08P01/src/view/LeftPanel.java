package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import controller.Controller;
import model.AlgoritmoGenetico;
import model.observers.Observer;

public class LeftPanel extends JPanel implements Observer{
	
	Controller _ctrl;
	
	TopPanel tp;
	
	JPanel pobPanel, genePanel, errorPanel, selecPanel, crucePanel, mutaPanel, elitePanel;
	
	JLabel pobLabel, geneLabel, errorLabel, tipoSelecLabel, tipoCruceLabel, porCruceLabel, tipoMutaLabel, porMutaLabel, porEliteLabel;
	
	JTextField pobText, geneText, errorText, cruceText, mutaText, eliteText;
	
	JComboBox selecBox, cruceBox, mutaBox;
	
	GridBagConstraints c;
	
	public LeftPanel(Controller ctrl) {
		_ctrl = ctrl;
		this.tp = tp;
		_ctrl.addObserver(this);
		initGUI();
	}

	private void initGUI() {
		setLayout(new GridBagLayout());
		setBackground(Color.WHITE);
		setPreferredSize(new Dimension(300, 625));
		setVisible(true);
		
		poblacionPanel();
		generacionesPanel();
		errorPanel();
		seleccionPanel();
		crucePanel();
		mutacionPanel();
		elitePanel();
		
		addPanels();	
	}
	
	private void addPanels() {
		c = new GridBagConstraints();
		
	    c.gridwidth = 0;
	    c.weightx = 0;
	    c.weighty = 0;

	    c.gridx = 0;
	    c.gridy = 0; 
	    add(pobPanel, c);
	    c.gridx = 0;
	    c.gridy = 1; 
	    add(genePanel, c);
	    c.gridx = 0;
	    c.gridy = 2;
	    add(errorPanel, c);
	    c.gridx = 0;
	    c.gridy = 3;
	    add(selecPanel, c);
	    c.gridx = 0;
	    c.gridy = 4;
	    add(crucePanel, c);
	    c.gridx = 0;
	    c.gridy = 5;
	    add(mutaPanel, c);
	    c.gridx = 0;
	    c.gridy = 6;
	    add(elitePanel, c);
	}

	private void poblacionPanel() {
		pobPanel = new JPanel();
		pobPanel.setPreferredSize(new Dimension(300, 75));
		pobPanel.setLayout(new GridLayout(2,0));
		pobPanel.setBackground(Color.WHITE);
		pobPanel.setBorder(new EmptyBorder(10,10,10,10));
		
		pobLabel = new JLabel("Tamaño Población (> 1)");
		
		pobText = new JTextField("100");
		
		DocumentListener dl = new DocumentListener() {

			@Override
			public void insertUpdate(DocumentEvent e) {
				setPobSize();	
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				setPobSize();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				setPobSize();
			}
			
			private void setPobSize() {
				try {
					_ctrl.updatePobSize(Integer.parseInt(pobText.getText()));
				}
				catch(Exception e) {
					_ctrl.updatePobSize(-1);
				}
			}
		};
        
        pobText.getDocument().addDocumentListener(dl);
		
		pobPanel.add(pobLabel);
		pobPanel.add(pobText);
	}

	private void generacionesPanel() {
		genePanel = new JPanel();
		genePanel.setPreferredSize(new Dimension(300, 75));
		genePanel.setLayout(new GridLayout(2,0));
		genePanel.setBackground(Color.WHITE);
		genePanel.setBorder(new EmptyBorder(10,10,10,10));
		
		geneLabel = new JLabel("Número de generaciones (>= 0)");
		
		geneText = new JTextField("100");
		
		DocumentListener dl = new DocumentListener() {

			@Override
			public void insertUpdate(DocumentEvent e) {
				setGenSize();
				
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				setGenSize();
				
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				setGenSize();
				
			}
			
			private void setGenSize() {
				try {
					_ctrl.updateGenSize(Integer.parseInt(geneText.getText()));
				}
				catch(Exception e) {
					_ctrl.updateGenSize(-1);
				}
				
			}
		};
		
        geneText.getDocument().addDocumentListener(dl);
		
		genePanel.add(geneLabel);
		genePanel.add(geneText);
		
	}

	private void errorPanel() {
		errorPanel = new JPanel();
		errorPanel.setPreferredSize(new Dimension(300, 75));
		errorPanel.setLayout(new GridLayout(2,0));
		errorPanel.setBackground(Color.WHITE);
		errorPanel.setBorder(new EmptyBorder(10,10,10,10));
		
		errorLabel = new JLabel("Valor de error (>0 y <1)");
		
		errorText = new JTextField("0.001");
		
		DocumentListener dl = new DocumentListener() {

			@Override
			public void insertUpdate(DocumentEvent e) {
				setValorError();
				
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				setValorError();
				
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				setValorError();
				
			}
			
			private void setValorError() {
				try {
					_ctrl.updateValorError(Double.parseDouble(errorText.getText()));
				}
				catch(Exception e) {
					_ctrl.updateValorError(-1);
				}
			}
		};
		
		errorText.getDocument().addDocumentListener(dl);
		errorPanel.add(errorLabel);
		errorPanel.add(errorText);
	}
	
	private void seleccionPanel() {
		selecPanel = new JPanel();
		selecPanel.setPreferredSize(new Dimension(300, 75));
		selecPanel.setLayout(new GridLayout(2,0));
		selecPanel.setBackground(Color.WHITE);
		Border _defaultBorder = BorderFactory.createLineBorder(Color.BLACK, 1);
		selecPanel.setBorder(BorderFactory.createTitledBorder(_defaultBorder, "Selección", TitledBorder.LEFT,TitledBorder.TOP));
		
		tipoSelecLabel = new JLabel("Tipo de selección");
				
		selecBox = new JComboBox<String>(_ctrl.getMapaSeleccionKeys());
		
		selecBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				_ctrl.updateSeleccion((String) selecBox.getSelectedItem());
			}
		});
		
		selecPanel.add(tipoSelecLabel);
		selecPanel.add(selecBox);
	}

	private void crucePanel() {
		crucePanel = new JPanel();
		crucePanel.setPreferredSize(new Dimension(300, 125));
		crucePanel.setLayout(new GridLayout(4,0));
		crucePanel.setBackground(Color.WHITE);
		Border _defaultBorder = BorderFactory.createLineBorder(Color.BLACK, 1);
		crucePanel.setBorder(BorderFactory.createTitledBorder(_defaultBorder, "Cruce", TitledBorder.LEFT,TitledBorder.TOP));
		
		tipoCruceLabel = new JLabel("Tipo de cruce");
		
 		cruceBox = new JComboBox<String>( _ctrl.getMapaCruceKeys());
		
		cruceBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				_ctrl.updateCruce((String) cruceBox.getSelectedItem());
			}
		});
		
		porCruceLabel = new JLabel("% Cruce (>= 0 y <= 100)");
		
		cruceText = new JTextField("60.0");
		
		DocumentListener dl = new DocumentListener() {

			@Override
			public void insertUpdate(DocumentEvent e) {
				setProbCruce();
				
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				setProbCruce();
				
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				setProbCruce();
				
			}
			
			private void setProbCruce() {
				try {
					_ctrl.updateProbCruce(Double.parseDouble(cruceText.getText()) / 100);
				}
				catch(Exception e) {
					_ctrl.updateProbCruce(-1);
				}
			}
		};
        
        
        cruceText.getDocument().addDocumentListener(dl);
		
		crucePanel.add(tipoCruceLabel);
		crucePanel.add(cruceBox);
		crucePanel.add(porCruceLabel);
		crucePanel.add(cruceText);
	}

	private void mutacionPanel() {
		mutaPanel = new JPanel();
		mutaPanel.setPreferredSize(new Dimension(300, 125));
		mutaPanel.setLayout(new GridLayout(4,0));
		mutaPanel.setBackground(Color.WHITE);
		Border _defaultBorder = BorderFactory.createLineBorder(Color.BLACK, 1);
		mutaPanel.setBorder(BorderFactory.createTitledBorder(_defaultBorder, "Mutación", TitledBorder.LEFT,TitledBorder.TOP));
		
		tipoMutaLabel = new JLabel("Tipo de mutación");
		
		String[] ejemplo = new String[] {"Mutación Básica"};
		mutaBox = new JComboBox<String>(ejemplo);
		
		porMutaLabel = new JLabel("% Mutación (>= 0 y <= 100)");
		
		mutaText = new JTextField("5.0");
		
		DocumentListener dl = new DocumentListener() {

			@Override
			public void insertUpdate(DocumentEvent e) {
				setProbMuta();
				
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				setProbMuta();
				
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				setProbMuta();
				
			}
			
			private void setProbMuta() {
				try {
					_ctrl.updateProbMuta(Double.parseDouble(mutaText.getText()) / 100);
				}
				catch(Exception e) {
					_ctrl.updateProbMuta(-1);
				}
			}
		};
        
        
        mutaText.getDocument().addDocumentListener(dl);
		
		mutaPanel.add(tipoMutaLabel);
		mutaPanel.add(mutaBox);
		mutaPanel.add(porMutaLabel);
		mutaPanel.add(mutaText);
	}

	private void elitePanel() {
		elitePanel = new JPanel();
		elitePanel.setPreferredSize(new Dimension(300, 75));
		elitePanel.setLayout(new GridLayout(2,0));
		elitePanel.setBackground(Color.WHITE);
		Border _defaultBorder = BorderFactory.createLineBorder(Color.BLACK, 1);
		elitePanel.setBorder(BorderFactory.createTitledBorder(_defaultBorder, "Élite", TitledBorder.LEFT,TitledBorder.TOP));
		
		porEliteLabel = new JLabel("% Élite (>= 0 y <= 100)");
		
		eliteText = new JTextField("0.0");
		
		DocumentListener dl = new DocumentListener() {

			@Override
			public void insertUpdate(DocumentEvent e) {
				setPorElite();
				
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				setPorElite();
				
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				setPorElite();
				
			}
			
			private void setPorElite() {
				try {
					_ctrl.updatePorElite(Double.parseDouble(eliteText.getText()) / 100);
				}
				catch(Exception e) {
					_ctrl.updatePorElite(-1);
				}
			}
		};
        
        
        eliteText.getDocument().addDocumentListener(dl);
		
		elitePanel.add(porEliteLabel);
		elitePanel.add(eliteText);
	}

	@Override
	public void onEnd(AlgoritmoGenetico algoritmo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onReset() {
        pobText.setText("100");
        geneText.setText("100");
        errorText.setText("0.001");
        cruceText.setText("60.0");
        mutaText.setText("5.0");
        eliteText.setText("0.0");
        cruceBox.setSelectedIndex(0);
        mutaBox.setSelectedIndex(0);
        selecBox.setSelectedIndex(0);
    }

	@Override
	public void onError(String exception) {
		JOptionPane.showMessageDialog(null, exception, "Parámetro inválido", JOptionPane.DEFAULT_OPTION);
	}

	public void refreshCruceBox() {
		String[] str = _ctrl.getMapaCruceKeys();
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(str);
		cruceBox.setModel(model);
		_ctrl.updateCruce(str[0]);
	}
}
