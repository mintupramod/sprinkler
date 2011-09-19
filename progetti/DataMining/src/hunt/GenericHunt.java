/**
 * 
 */
package hunt;

import hunt.data.Node;
import hunt.data.TestCondition;
import hunt.data.Tree;
import hunt.purity.Gini;
import hunt.utilities.Bundle;
import hunt.utilities.CSVLoader;
import hunt.vivin.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.print.attribute.standard.Finishings;


/**
 * @author Claudio Tanci
 * 
 */
public class GenericHunt {

	/**
	 * stoppingCondition() controlla se la condizione per la terminazione
	 * dell'accrescimento dell'albero è stata raggiunta
	 * 
	 * @param Node
	 * @return boolean
	 */
	private static boolean stoppingCondition(Node node) {
		// if all the records have the same label this node is a leaf

		// if (node.getPurity() < 0.3 || node.size() < 40) {
//		if (node.getPurity() < 0.3) {
		
		// complete grow
		if (node.getPurity() == 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * label() determina l'etichetta di classe da assegnare a un nodo foglia
	 * dell'albero
	 * 
	 * @param Node
	 * @return String
	 */
	private static String label(Node node) {
		// determino l'etichetta adatta al nodo dall'etichetta più ricorrente
		// dei suoi record
		ArrayList<ArrayList<String>> records = node.getRecords();

		ArrayList<String> list = new ArrayList<String>();

		for (ArrayList<String> record : records) {
			list.add(record.get(record.size() - 1));

		}

		// lista delle etichette
		HashSet<String> labels = new HashSet<String>();

		Iterator<String> it = list.iterator();
		while (it.hasNext()) {
			String label = it.next();
			labels.add(label);
		}

		int max = 0;
		String classLabel = "";

		for (String label : labels) {
			int freq = Collections.frequency(list, label);

			if (freq > max) {
				max = freq;
				classLabel = label;
			}
		}

		return classLabel;
	}

	/**
	 * findBestSplit() seleziona l'attributo su cui splittare il nodo
	 * dell'albero e ne ritorna l'indice
	 * 
	 * NB. implementazione solo per attributi nominali
	 * 
	 * @param Node
	 * @return TestCondition
	 */
	private static TestCondition findBestSplit(Node node) {

		// TODO leggere gli attributi dal file, o meglio esportare l'elenco
		// degli attributi all'inizio e leggere in modo dinamico i valori.
		// questo funziona solamente per il file tic-tac-toe
		// NB implemented only the multiway split, hardcoded "x","o","b"
		int size = node.getRecords().size();
		String[] values = { "x", "o", "b" };
		int bestSplit = 0;
		float bestAvg = 1;
		for (int i = 0; i < 9; i++) {
			// computing the weighted average index

			float avg = 0;
			for (String value : values) {
				Node tentativeSplit = split(node, i, value);

				float valuePurity = (float) tentativeSplit.getPurity();
				int valueNumber = tentativeSplit.size();

				avg = (float) avg + (float) valueNumber / (float) size
						* valuePurity;

			}

			if (avg < bestAvg) {
				bestAvg = avg;
				bestSplit = i;

			}

		}

		TestCondition testCondition = new TestCondition();
		testCondition.setIdAttribute(bestSplit);

		ArrayList<String> tobesplittedValue = new ArrayList<String>(0);

		for (String value : values) {
			if (node.howMany(bestSplit, value) > 0) {
				tobesplittedValue.add(value);
			}
		}

		String[] temp = new String[tobesplittedValue.size()];
		temp = tobesplittedValue.toArray(temp);

		testCondition.setValues(temp);

		return testCondition;
	}

	/**
	 * split() ritorna un nodo con tutti i record con attributo = valore
	 * 
	 */
	private static Node split(Node node, int attribute, String value) {

		ArrayList<ArrayList<String>> list = node.getRecords();
		ArrayList<ArrayList<String>> newList = new ArrayList<ArrayList<String>>(
				0);

		Iterator<ArrayList<String>> it = list.iterator();
		while (it.hasNext()) {

			ArrayList<String> sample = it.next();
			if (sample.get(attribute).equals(value))
				newList.add(sample);

		}

		Node newNode = new Node(newList);

		return newNode;

	}

	// /**
	// * createNode()
	// * estende l'albero creando un nuovo nodo
	// *
	// * @param ArrayList<ArrayList<String>>
	// * @return GenericTreeNode
	// */
	// private GenericTreeNode<Node> createNode(ArrayList<ArrayList<String>>
	// list){
	//
	// return null;
	// }
	//
	// /**
	// * createNode()
	// * estende l'albero creando un nuovo nodo
	// *
	// * @param ArrayList<ArrayList<String>>
	// * @return GenericTreeNode
	// */
	// private static Node createNode(){
	// Node node = new Node();
	//
	// return node;
	// }

	/**
	 * treeGrowth() costruisce l'albero di induzione
	 * 
	 * @param Node
	 * @return GenericTreeNode
	 */
	public static Tree treeGrowth(Tree tree) {

		Node node = (Node) tree.getRoot();

		if (stoppingCondition(node)) {
			// node is a leaf
			// System.out.println("LEAF");
			node.setLeaf(true);
			node.setLabel(label(node));

		} else {
			// splitting the node
			TestCondition bestSplit = findBestSplit(node);

			// TestCondition testCondition = new TestCondition();
			// testCondition.setValues(bestSplit.getValues());
			// testCondition.setIdAttribute(bestSplit.getIdAttribute());

			// node.setTestCondition(testCondition);

			for (String value : findBestSplit(node).getValues()) {

				Node child = split(node, bestSplit.getIdAttribute(), value);

				// ok only for single value split!
				String[] values = { value };

				TestCondition testCondition = new TestCondition();
				testCondition.setValues(values);
				testCondition.setIdAttribute(bestSplit.getIdAttribute());

				child.setTestCondition(testCondition);

				Tree treeChild = new Tree();
				treeChild.setRoot(child);

				treeGrowth(treeChild);

				node.addChild(child);

			}

		}

		return tree;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// testHowMany();
		// testClassify();
		// System.exit(0);

		// file dati
		String strFile = Bundle.getString("Resources.TrainingSet"); //$NON-NLS-1$
		Node root = new Node();

		System.out.println("Reading file data " + strFile);
		root.setRecords(CSVLoader.load(strFile));

		System.out.println("Generating decision tree...");
		Tree tree = new Tree();
		tree.setRoot(root);

		tree = (Tree) treeGrowth(tree);

		System.out.println(((Node) tree.getRoot()).size() + " records analyzed");

		System.out.println(tree.getNumberOfNodes() + " nodes in the tree");
		
		System.out.println("Cleaning data set records...");
		tree.clean();
		
		
		// saving the tree
		try {
			String fileName = Bundle.getString("Resources.SaveFileName");
			tree.save(fileName);
			System.out.println("Decision tree saved as "+fileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// exporting tree dot file
		tree.toDot(Bundle.getString("Resources.DotFileName"));		
		
		
		
		
		
		
		



	}

	private static void testSplit() {

		String strFile = Bundle.getString("Resources.TrainingSet"); //$NON-NLS-1$

		Node root = new Node();
		root.setRecords(CSVLoader.load(strFile));

		System.out.println("Split test");

		String[] values = { "x", "o", "b" };

		int tot = 0;
		for (String value : values) {

			Node node = split(root, 1, value);

			System.out.println("value " + value + " " + node.size()
					+ ", purity " + node.getPurity());
			tot = tot + node.getRecords().size();
		}

		System.out.println("____________");
		System.out.println("records " + tot);

	}

	private static void testHowMany() {

		String strFile = Bundle.getString("Resources.TrainingSet"); //$NON-NLS-1$

		Node root = new Node();
		root.setRecords(CSVLoader.load(strFile));

		System.out.println("HowMany for attribute=0 test");

		String[] values = { "x", "o", "b" };

		int tot = 0;
		for (String value : values) {

			System.out.println("value " + value + " " + root.howMany(0, value));

		}

		tot = tot + root.getRecords().size();

		System.out.println("____________");
		System.out.println("records " + tot);

	}

	private static void testClassify() {

		String strFile = Bundle.getString("Resources.TrainingSet"); //$NON-NLS-1$

		Node root = new Node();
		root.setRecords(CSVLoader.load(strFile));

		System.out.println("Classify test");

		System.out.println("Node label: " + label(root));

	}

}
