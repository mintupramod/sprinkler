package hunt;

import hunt.data.Node;
import hunt.data.RecordSet;
import hunt.data.TestCondition;
import hunt.data.NominalRecord;
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
 * This class implements a generic Hunt algorithm for decision tree growing
 * 
 */
public class GenericHunt {

	/**
	 * stoppingCondition has the stopping condition been reached? 
	 * @param Node
	 * @return boolean
	 */
	private static boolean stoppingCondition(Node node) {
		// if all the records have the same label this node is a leaf

		// if (node.getPurity() < 0.3 || node.size() < 40) {
//		if (node.getPurity() < 0.20) {
		
		// complete grow
		if (node.getPurity() == 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * label
	 * @param Node
	 * @return label to be assigned to the node
	 */
	private static String label(Node node) {
		// the node label is the most recurrent label of its records
		RecordSet records = node.getRecords();

		ArrayList<String> list = new ArrayList<String>();
		
		for (NominalRecord record : records.getRecords()) {
			list.add(record.getLabel().toString());
		}

		

		// computing the labels list
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
	 * findBestSplit select the most appropriate attribute to split
	 * NB. only nominal attributes supported
	 * @param Node
	 * @return TestCondition
	 */
	private static TestCondition findBestSplit(Node node) {

		/* TODO leggere gli attributi dal file, o meglio esportare l'elenco
		 * degli attributi all'inizio e leggere in modo dinamico i valori.
		 * questo funziona solamente per il file tic-tac-toe
		 * NB implemented only the multiway split, hardcoded "x","o","b"
		 */
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
	 * split a node
	 * @param parent node to be splitted
	 * @param attribute
	 * @param value
	 * @return a node with all the records with attribute = value
	 */
	private static Node split(Node node, int attribute, String value) {

//		RecordSet list = node.getRecords();
//		ArrayList<ArrayList<String>> newList = new ArrayList<ArrayList<String>>();
		
		RecordSet newRecordSet = new RecordSet();
		
		for (NominalRecord record : node.getRecords().getRecords()) {
			if (record.getAttribute(attribute).toString().equals(value)) {
				newRecordSet.add(record);
			}
			
		}

//		Iterator<ArrayList<String>> it = list.iterator();
//		while (it.hasNext()) {
//
//			ArrayList<String> sample = it.next();
//			if (sample.get(attribute).equals(value))
//				newList.add(sample);
//
//		}

		Node newNode = new Node(newRecordSet);
		return newNode;

	}

	/**
	 * treeGrowth build the decision tree
	 * @param tree
	 * @return tree
	 */
	public static Tree treeGrowth(Tree tree) {

		Node node = (Node) tree.getRoot();
		
		// setting the label for both leaf and not leaf nodes
		node.setLabel(label(node));

		if (stoppingCondition(node)) {
			// node is a leaf
			node.setLeaf(true);

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
	 * main
	 * @param args
	 */
	public static void main(String[] args) {

		testSplit();
//		testHowMany();
		// testClassify();
		 System.exit(0);

		// record set
		String strFile = Bundle.getString("Resources.RecordSet"); //$NON-NLS-1$
		Node root = new Node();

		System.out.println("Reading file data " + strFile);
		try {
			root.setRecords(CSVLoader.loadRecordSet(strFile));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

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

	/**
	 * testSplit test a node splitting
	 */
	private static void testSplit() {

		String strFile = Bundle.getString("Resources.RecordSet"); //$NON-NLS-1$

		Node root = new Node();
		try {
			root.setRecords(CSVLoader.loadRecordSet(strFile));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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

	/**
	 * testHowMany
	 */
	private static void testHowMany() {

		String strFile = Bundle.getString("Resources.RecordSet"); //$NON-NLS-1$

		Node root = new Node();
		try {
			root.setRecords(CSVLoader.loadRecordSet(strFile));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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

	/**
	 * testClassify
	 */
	private static void testClassify() {

		String strFile = Bundle.getString("Resources.RecordSet"); //$NON-NLS-1$

		Node root = new Node();
		try {
			root.setRecords(CSVLoader.loadRecordSet(strFile));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("Classify test");

		System.out.println("Node label: " + label(root));

	}

}