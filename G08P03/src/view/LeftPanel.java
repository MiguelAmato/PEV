package view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import controller.Controller;
import model.AlgoritmoGenetico;
import model.observers.Observer;

public class LeftPanel extends JPanel implements Observer{
	
	public final static String NINGUNO = "1";
	public final static String TAM_POBLACION = "Tamaño Población";
	public final static String PROB_CRUCE = "Prob. Cruce";
	public final static String PROB_MUTACION = "Prob. Mutación";
	
	Controller _ctrl;
	
	String individuo;
	
	
	JPanel pobPanel, genePanel, bloatingPanel, selecPanel, crucePanel, mutaPanel, elitePanel;
	
	JLabel pobLabel, geneLabel, bloatingLabel, tipoSelecLabel, tipoCruceLabel, porCruceLabel, tipoMutaLabel, porMutaLabel, porEliteLabel;
	
	JTextField pobText, geneText, cruceText, mutaText, eliteText;
	
	JComboBox selecBox, cruceBox, mutaBox;
	
	JPanel adCrucePanel, adMutaPanel, adTamPobPanel;
	
	JTextField minProbCruce, maxProbCruce, minProbMuta, maxProbMuta, minTamPob, maxTamPob;
	
	JCheckBox bloatingCheckBox;
	
	GridBagConstraints c;
	
	JPanel pobCardPanel, cruceCardPanel, mutaCardPanel;
	
	Map<String, JPanel> mapaPaneles;
	
	String intervaloActual = NINGUNO;
	
	public LeftPanel(Controller ctrl) {
		_ctrl = ctrl;
		_ctrl.addObserver(this);
		individuo = "Individuo Arbol";
		initGUI();
	}

	private void initGUI() {
		setLayout(new GridBagLayout());
		setBackground(Color.WHITE);
		setPreferredSize(new Dimension(300, 625));
		setVisible(true);
		
		initMapa();
		
		poblacionPanel();
		generacionesPanel();
		seleccionPanel();
		crucePanel();
		mutacionPanel();
		elitePanel();
		bloatingPanel();
		
		addPanels();	
	}
	
	private void initMapa() {
		
		pobCardPanel = new JPanel(new CardLayout());
		cruceCardPanel = new JPanel(new CardLayout());
		mutaCardPanel = new JPanel(new CardLayout());
		
		mapaPaneles = new HashMap<String, JPanel>(){{
			put(TAM_POBLACION, pobCardPanel);
			put(PROB_CRUCE, cruceCardPanel);
			put(PROB_MUTACION, mutaCardPanel);
		}};
		
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
	    add(selecPanel, c);
	    c.gridx = 0;
	    c.gridy = 3;
	    add(crucePanel, c);
	    c.gridx = 0;
	    c.gridy = 4;
	    add(mutaPanel, c);
	    c.gridx = 0;
	    c.gridy = 5;
	    add(elitePanel, c);
	    c.gridx = 0;
	    c.gridy = 6;
	    add(bloatingPanel, c);
	}

	private void poblacionPanel() {
		pobPanel = new JPanel();
		pobPanel.setPreferredSize(new Dimension(300, 75));
		pobPanel.setLayout(new GridLayout(2,0));
		pobPanel.setBackground(Color.WHITE);
		pobPanel.setBorder(new EmptyBorder(10,10,10,10));
		
		pobLabel = new JLabel("Tamaño Población (> 1)");
		
		JPanel pobTextPanel = new JPanel(new BorderLayout());
		
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
        
        pobTextPanel.add(pobText, BorderLayout.CENTER);
        
        JPanel adPobTextPanel = new JPanel();
        adPobTextPanel.setLayout(new BoxLayout(adPobTextPanel, BoxLayout.X_AXIS));
    	adPobTextPanel.setBackground(Color.WHITE);
    	
    	minTamPob = new JTextField();
    	
		DocumentListener dlmin = new DocumentListener() {

			@Override
			public void insertUpdate(DocumentEvent e) {
				setMin();	
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				setMin();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				setMin();
			}
			
			private void setMin() {
				try {
					_ctrl.setMin(Integer.parseInt(minTamPob.getText()));
				}
				catch(Exception e) {
					_ctrl.setMin(-1);
				}
			}
		};
        
        minTamPob.getDocument().addDocumentListener(dlmin);
    	
    	maxTamPob = new JTextField();
    	
		DocumentListener dlmax = new DocumentListener() {

			@Override
			public void insertUpdate(DocumentEvent e) {
				setMax();	
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				setMax();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				setMax();
			}
			
			private void setMax() {
				try {
					_ctrl.setMax(Integer.parseInt(maxTamPob.getText()));
				}
				catch(Exception e) {
					_ctrl.setMax(-1);
				}
			}
		};
        
        maxTamPob.getDocument().addDocumentListener(dlmax);
    	
    	adPobTextPanel.add(new JLabel("  Min:  "));
    	adPobTextPanel.add(minTamPob);
    	adPobTextPanel.add(new JLabel("     Max:  "));
    	adPobTextPanel.add(maxTamPob);
		
    	
    	pobCardPanel.add(pobTextPanel, NINGUNO); 
    	pobCardPanel.add(adPobTextPanel, TAM_POBLACION);
    	
		pobPanel.add(pobLabel);
		pobPanel.add(pobCardPanel);
		
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

	// PONER BLOATING
	
	private void bloatingPanel() {
		
		bloatingPanel = new JPanel();
		bloatingPanel.setPreferredSize(new Dimension(300, 75));
		bloatingPanel.setLayout(new GridLayout(2,0));
		bloatingPanel.setBackground(Color.WHITE);
		bloatingPanel.setBorder(new EmptyBorder(10,10,10,10));
		
		bloatingLabel = new JLabel("Bloating:");
		
		bloatingCheckBox = new JCheckBox("Activar Bloating");
		bloatingCheckBox.setBackground(Color.WHITE);
		bloatingCheckBox.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				_ctrl.updateBloating(bloatingCheckBox.isSelected());
			}
			
		});
		
		bloatingPanel.add(bloatingLabel);
		bloatingPanel.add(bloatingCheckBox);
	}
	
	private void seleccionPanel() {
		selecPanel = new JPanel();
		selecPanel.setPreferredSize(new Dimension(300, 75));
		selecPanel.setLayout(new GridLayout(2,0));
		selecPanel.setBackground(Color.WHITE);
		Border _defaultBorder = BorderFactory.createLineBorder(Color.BLACK, 1);
		selecPanel.setBorder(BorderFactory.createTitledBorder(_defaultBorder, "Selección", TitledBorder.LEFT,TitledBorder.TOP));
		
		tipoSelecLabel = new JLabel("Tipo de selección");
				
		String[] selec = new String[_ctrl.getMapaFactories().get(individuo).getSecond().getFirst().size()];
		
		
		for(int i = 0; i < selec.length;i++) {
			selec[i] = _ctrl.getMapaFactories().get(individuo).getSecond().getFirst().get(i);
		}
		
		selecBox = new JComboBox<String>(selec);
		
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
		
		String[] cruc = new String[_ctrl.getMapaFactories().get(individuo).getSecond().getSecond().size()];
		
		for(int i = 0; i < cruc.length;i++) {
			cruc[i] = _ctrl.getMapaFactories().get(individuo).getSecond().getSecond().get(i);
		}
		
		cruceBox = new JComboBox<String>(cruc);
				
		cruceBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				_ctrl.updateCruce((String) cruceBox.getSelectedItem());
			}
		});
		
		porCruceLabel = new JLabel("% Cruce (>= 0 y <= 100)");
		
		cruceText = new JTextField("60.0");
		
		JPanel cruceTextPanel = new JPanel(new BorderLayout());
		
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
        
        cruceTextPanel.add(cruceText, BorderLayout.CENTER);
		
        JPanel adCruceTextPanel = new JPanel();
        adCruceTextPanel.setLayout(new BoxLayout(adCruceTextPanel, BoxLayout.X_AXIS));
        adCruceTextPanel.setBackground(Color.WHITE);
        
    	minProbCruce = new JTextField();
    	
		DocumentListener dlmin = new DocumentListener() {

			@Override
			public void insertUpdate(DocumentEvent e) {
				setMin();	
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				setMin();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				setMin();
			}
			
			private void setMin() {
				try {
					_ctrl.setMin(Integer.parseInt(minProbCruce.getText()));
				}
				catch(Exception e) {
					_ctrl.setMin(-1);
				}
			}
		};
        
		minProbCruce.getDocument().addDocumentListener(dlmin);
    	
		maxProbCruce = new JTextField();
    	
		DocumentListener dlmax = new DocumentListener() {

			@Override
			public void insertUpdate(DocumentEvent e) {
				setMax();	
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				setMax();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				setMax();
			}
			
			private void setMax() {
				try {
					_ctrl.setMax(Integer.parseInt(maxProbCruce.getText()));
				}
				catch(Exception e) {
					_ctrl.setMax(-1);
				}
			}
		};
        
		maxProbCruce.getDocument().addDocumentListener(dlmax);
    	
		adCruceTextPanel.add(new JLabel("  Min:  "));
		adCruceTextPanel.add(minProbCruce);
		adCruceTextPanel.add(new JLabel("     Max:  "));
		adCruceTextPanel.add(maxProbCruce);
        
        cruceCardPanel.add(cruceTextPanel, NINGUNO);
        cruceCardPanel.add(adCruceTextPanel, PROB_CRUCE);
        
		crucePanel.add(tipoCruceLabel);
		crucePanel.add(cruceBox);
		crucePanel.add(porCruceLabel);
		crucePanel.add(cruceCardPanel);
	}

	private void mutacionPanel() {
		mutaPanel = new JPanel();
		mutaPanel.setPreferredSize(new Dimension(300, 125));
		mutaPanel.setLayout(new GridLayout(4,0));
		mutaPanel.setBackground(Color.WHITE);
		Border _defaultBorder = BorderFactory.createLineBorder(Color.BLACK, 1);
		mutaPanel.setBorder(BorderFactory.createTitledBorder(_defaultBorder, "Mutación", TitledBorder.LEFT,TitledBorder.TOP));
		
		tipoMutaLabel = new JLabel("Tipo de mutación");
		
		String[] muta = new String[_ctrl.getMapaFactories().get(individuo).getSecond().getThird().size()];
		
		for(int i = 0; i < muta.length;i++) {
			muta[i] = _ctrl.getMapaFactories().get(individuo).getSecond().getThird().get(i);
		}

		mutaBox = new JComboBox<String>(muta);
		
		mutaBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				_ctrl.updateMutacion((String) mutaBox.getSelectedItem());
			}
		});
		
		porMutaLabel = new JLabel("% Mutación (>= 0 y <= 100)");
		
		mutaText = new JTextField("5.0");
		
		JPanel mutaTextPanel = new JPanel(new BorderLayout());
		
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
        
        mutaTextPanel.add(mutaText, BorderLayout.CENTER);
        
        JPanel adMutaTextPanel = new JPanel();
        adMutaTextPanel.setLayout(new BoxLayout(adMutaTextPanel, BoxLayout.X_AXIS));
        adMutaTextPanel.setBackground(Color.WHITE);
        
    	minProbMuta = new JTextField();
    	
		DocumentListener dlmin = new DocumentListener() {

			@Override
			public void insertUpdate(DocumentEvent e) {
				setMin();	
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				setMin();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				setMin();
			}
			
			private void setMin() {
				try {
					_ctrl.setMin(Integer.parseInt(minProbMuta.getText()));
				}
				catch(Exception e) {
					_ctrl.setMin(-1);
				}
			}
		};
        
		minProbMuta.getDocument().addDocumentListener(dlmin);
    	
		maxProbMuta = new JTextField();
    	
		DocumentListener dlmax = new DocumentListener() {

			@Override
			public void insertUpdate(DocumentEvent e) {
				setMax();	
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				setMax();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				setMax();
			}
			
			private void setMax() {
				try {
					_ctrl.setMax(Integer.parseInt(maxProbMuta.getText()));
				}
				catch(Exception e) {
					_ctrl.setMax(-1);
				}
			}
		};
        
		maxProbMuta.getDocument().addDocumentListener(dlmax);
    	
		adMutaTextPanel.add(new JLabel("  Min:  "));
		adMutaTextPanel.add(minProbMuta);
		adMutaTextPanel.add(new JLabel("     Max:  "));
		adMutaTextPanel.add(maxProbMuta);
        
        mutaCardPanel.add(mutaTextPanel, NINGUNO);
        mutaCardPanel.add(adMutaTextPanel, PROB_MUTACION);
		
		mutaPanel.add(tipoMutaLabel);
		mutaPanel.add(mutaBox);
		mutaPanel.add(porMutaLabel);
		mutaPanel.add(mutaCardPanel);
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
	public void onEnd(AlgoritmoGenetico algoritmo, String key) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onReset() {
        pobText.setText("100");
        geneText.setText("100");
        cruceText.setText("60.0");
        mutaText.setText("5.0");
        eliteText.setText("0.0");
        cruceBox.setSelectedIndex(0);
        mutaBox.setSelectedIndex(0);
        selecBox.setSelectedIndex(0);
        minProbCruce.setText(""); 
        maxProbCruce.setText("");
        minProbMuta.setText(""); 
        maxProbMuta.setText("");
        minTamPob.setText("");
        maxTamPob.setText("");
        bloatingCheckBox.setSelected(false);
        _ctrl.setMin(-1.0);
        _ctrl.setMax(-1.0);
        _ctrl.updatePobSize(100.0);
        _ctrl.updateGenSize(100.0);
        _ctrl.updateProbCruce(60.0/100);
        _ctrl.updateProbMuta(5.0/100);
        _ctrl.updatePorElite(0.0);
  
        
    }

	@Override
	public void onError(String exception) {
		JOptionPane.showMessageDialog(null, exception, "Parámetro inválido", JOptionPane.DEFAULT_OPTION);
	}

	public void refreshCruceBox() {
		
		String[] cruc = new String[_ctrl.getMapaFactories().get(individuo).getSecond().getSecond().size()];
		
		for(int i = 0; i < cruc.length;i++) {
			cruc[i] = _ctrl.getMapaFactories().get(individuo).getSecond().getSecond().get(i);
		}
		
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(cruc);
		
		cruceBox.setModel(model);
		_ctrl.updateCruce(cruc[0]);
	}
	
	public void refreshMutacionBox(){
		
		String[] mut = new String[_ctrl.getMapaFactories().get(individuo).getSecond().getThird().size()];
		
		for(int i = 0; i < mut.length;i++) {
			mut[i] = _ctrl.getMapaFactories().get(individuo).getSecond().getThird().get(i);
		}
		
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(mut);
		
		mutaBox.setModel(model);
		_ctrl.updateMutacion(mut[0]);
	}
	
	public void setIndividuo(String selectedItem) {
		individuo = selectedItem;
	}
	
	public String[] getOpciones() {
		String[] ret = new String[mapaPaneles.keySet().size() + 1];
		ret[0] = "Ninguno";
		int i = 1;
		for (String s : mapaPaneles.keySet()) {
			ret[i] = s;
			i++;
		}
		return ret;
	}
	
	public void changePanel(String key) {
		for(String s : mapaPaneles.keySet()) {
			CardLayout cl = (CardLayout)(mapaPaneles.get(s).getLayout());
			cl.show(mapaPaneles.get(s), NINGUNO);
		}
		if (!key.equals("Ninguno")) {
			CardLayout cl = (CardLayout)(mapaPaneles.get(key).getLayout());
			cl.show(mapaPaneles.get(key), key);
			intervaloActual = key;
		}
		else {
			intervaloActual = NINGUNO;
			
		}
		onReset();
	}
	
}
