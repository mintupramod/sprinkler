package sprinkler.applet;


import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;

import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JSlider;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

import sprinkler.GenericHunt;
import sprinkler.data.Node;
import sprinkler.data.Tree;
import sprinkler.utilities.CSVLoader;
import java.awt.Color;

/**
 * @author Claudio Tanci
 *
 */
public class SprinklerApplet extends JApplet {
	
	Tree tree = new Tree();
	
	//Create a file chooser
	final JFileChooser fc = new JFileChooser();

	/**
	 * Create the applet.
	 */
	public SprinklerApplet() {
		
		try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception ed) {
            ed.printStackTrace();
        }
		
		getContentPane().setLayout(null);
		
		JButton btnLoadTrainingSet = new JButton("Initialize Model");
		btnLoadTrainingSet.setBounds(240, 60, 189, 25);
		getContentPane().add(btnLoadTrainingSet);
		
		final JButton btnLoadTestSet = new JButton("Test");
		
		btnLoadTestSet.setEnabled(false);
		btnLoadTestSet.setBounds(240, 102, 189, 25);
		getContentPane().add(btnLoadTestSet);
		
		JLabel lblLoadTrainingSet = new JLabel("2) Load Training Set");
		lblLoadTrainingSet.setBounds(12, 57, 215, 30);
		getContentPane().add(lblLoadTrainingSet);
		
		JLabel lblPrepruningValue = new JLabel("1) Set Pre-Pruning value");
		lblPrepruningValue.setBounds(12, 12, 215, 30);
		getContentPane().add(lblPrepruningValue);
		
		final JLabel lblErr = new JLabel("n/a");
		lblErr.setBounds(351, 139, 70, 30);
		getContentPane().add(lblErr);
		
		
		final JSpinner spinner = new JSpinner();
		spinner.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				btnLoadTestSet.setEnabled(false);
				lblErr.setText("n/a");
			}
		});
		spinner.setModel(new SpinnerNumberModel(new Float(0), new Float(0), new Float(1), new Float(0.05)));
		spinner.setBounds(240, 13, 60, 30);
		getContentPane().add(spinner);
		
		JLabel lblErrorRate = new JLabel("Error Rate:");
		lblErrorRate.setBounds(240, 139, 99, 30);
		getContentPane().add(lblErrorRate);
		
		JLabel lblLoadTest = new JLabel("3) Load Test Set");
		lblLoadTest.setBounds(12, 99, 215, 30);
		getContentPane().add(lblLoadTest);
		
		final JLabel lblStatus = new JLabel("Welcome");
		lblStatus.setForeground(Color.GRAY);
		lblStatus.setBounds(12, 190, 417, 15);
		getContentPane().add(lblStatus);

		btnLoadTrainingSet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				// Load training set and generate decision tree
				int returnVal = fc.showOpenDialog(SprinklerApplet.this);
		        if (returnVal == JFileChooser.APPROVE_OPTION) {
		            File file = fc.getSelectedFile();
		            
		            Node root = new Node();
		            
		            lblStatus.setText("Reading data file");
		    		try {
		    			root.setRecords(CSVLoader.loadRecordSet(file));
		    		} catch (IOException e1) {
		    			lblStatus.setText("Sorry, an error occurred while reading the file");
		    		}

		    		lblStatus.setText("Generating decision tree...");
		    		
		    		tree.setRoot(root);

		    		tree = (Tree) GenericHunt.treeGrowth(tree, (Float) spinner.getValue() / Float.valueOf(2));

		    		lblStatus.setText(tree.getNumberOfNodes() + " nodes in the tree");

		    		tree.clean();

		    		btnLoadTestSet.setEnabled(true);
		        }
			}
		});
		
		btnLoadTestSet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int returnVal = fc.showOpenDialog(SprinklerApplet.this);
		        if (returnVal == JFileChooser.APPROVE_OPTION) {
		            File file = fc.getSelectedFile();
		            
		            double errorRate;
		            
		            try {
		            	lblStatus.setText("Validating the model...");
						errorRate = tree.validate(CSVLoader.loadRecordSet(file));
						
						DecimalFormat df = new DecimalFormat("#0.00%");
						lblErr.setText(df.format(errorRate));
						lblStatus.setText("Model validated");
					} catch (IOException e) {
						lblStatus.setText("Sorry, an error occurred while validating");
					}

		        }
			}
		});
	}
}
