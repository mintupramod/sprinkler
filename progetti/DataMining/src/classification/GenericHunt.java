/**
 * 
 */
package classification;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.print.attribute.standard.Finishings;


import utilities.Gini;
import vivin.*;

/**
 * @author Claudio Tanci
 *
 */
public class GenericHunt {


	/** 
	 * stoppingCondition()
	 * controlla se la condizione per la terminazione dell'accrescimento
	 * dell'albero è stata raggiunta
	 * 
	 * @param Node
	 * @return boolean
	 */
	private static boolean stoppingCondition(Node node){
		// if all the records have the same label this node is a leaf
		// NB gini = 0
		
		System.out.println(node.getPurity());
		
		if (node.getPurity() == 0) {
			return true;
		} else {
			return false;
		}
		
		
		
//		ArrayList<ArrayList<String>> list = node.getRecords();		
//
//		HashSet<String> labels = new HashSet<String>();
//
//		Iterator<ArrayList<String>> it = list.iterator();
//		while (it.hasNext()) {
//			ArrayList<String> sample = it.next();
//			labels.add(sample.get(sample.size()-1));
//		}
//
//		if (labels.size() == 1) {
//			return true;			
//		} else {
//			return false;
//		}
	}

	/** 
	 * classify()
	 * determina l'etichetta di classe da assegnare a un nodo foglia dell'albero
	 * 
	 * @param Node
	 * @return String
	 */
	private static String classify(Node node){
		// determino l'etichetta adatta al nodo dall'etichetta più ricorrente dei suoi record
		ArrayList<ArrayList<String>> list = node.getRecords();

		// lista delle etichette
		HashSet<String> labels = new HashSet<String>();

		Iterator<ArrayList<String>> it = list.iterator();
		while (it.hasNext()) {
			ArrayList<String> sample = it.next();
			labels.add(sample.get(sample.size()-1));
		}

		int max = 0;
		String classLabel = null;

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
	 * findBestSplit()
	 * seleziona l'attributo su cui splittare il nodo dell'albero 
	 * e ne ritorna l'indice
	 * 
	 * NB. implementazione solo per attributi nominali
	 * 
	 * @param Node
	 * @return TestCondition
	 */
	private static TestCondition findBestSplit(Node node){
		
		// TODO leggere gli attributi dal file, o meglio esportare l'elenco degli attributi all'inizio e leggere in modo dinamico i valori.
		// questo funziona solamente per il file tic-tac-toe
		// NB implemented only the multiway split
		int size = node.getRecords().size();
		String[] values = {"x","o","b"};
		int bestSplit = 0;
		float bestAvg = 1;
		for (int i = 1; i < 10; i++) {
			// computing the weighted average index
			float avg = 0; 
			for (String value : values) {
				float valuePurity = split(node, i, value).getPurity();
				int valueNumber = split(node, i, value).howMany(i, value);
				
				avg = avg +	(float) valueNumber / (float) size * valuePurity;
				
				if (avg < bestAvg) {
					bestAvg = avg;
					bestSplit = i;
				}
			}
			
		}

		TestCondition testCondition = new TestCondition();
		testCondition.setIdAttribute(bestSplit);
		testCondition.setValues(values);

		return testCondition;
	}

	/**
	 * split()
	 * ritorna un nodo con tutti i record con attributo = valore
	 *  
	 */
	private static Node split(Node node, int attribute, String value){

		ArrayList<ArrayList<String>> list = node.getRecords();
		ArrayList<ArrayList<String>> newList = new ArrayList<ArrayList<String>>(0);

		Iterator<ArrayList<String>> it = list.iterator();
		while (it.hasNext()) {

			ArrayList<String> sample = it.next();
			if (sample.get(attribute).equals(value)) newList.add(sample);
		}

		Node newNode= new Node(newList);

		return newNode;


	}

	//	/** 
	//	 * createNode()
	//	 * estende l'albero creando un nuovo nodo
	//	 * 
	//	 * @param ArrayList<ArrayList<String>>
	//	 * @return GenericTreeNode
	//	 */
	//	private GenericTreeNode<Node> createNode(ArrayList<ArrayList<String>> list){
	//
	//		return null;
	//	}
	//
	//	/** 
	//	 * createNode()
	//	 * estende l'albero creando un nuovo nodo
	//	 * 
	//	 * @param ArrayList<ArrayList<String>>
	//	 * @return GenericTreeNode
	//	 */
	//	private static Node createNode(){
	//		Node node = new Node();
	//		
	//		return node;
	//	}


	/** 
	 * treeGrowth()
	 * costruisce l'albero di induzione
	 * 
	 * @param Node
	 * @return GenericTreeNode
	 */
	public static GenericTree treeGrowth(GenericTree tree){

		Node node = (Node) tree.getRoot();

		if (stoppingCondition(node)) {
			// node is a leaf
			node.setLeaf(true);
			node.setLabel(classify(node));
		} else {
			// splitting the node
			int attribute = findBestSplit(node).getIdAttribute();
			node.setTestAttribute(attribute);
			//			node.setTestCondition(findBestSplit(node).getValues());

			for (String value : findBestSplit(node).getValues()) {
				System.out.println(value);
				Node child = split(node, attribute, value);

				GenericTree<Node> treeChild = new GenericTree<Node>();
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

		// file dati
		String strFile = Bundle.getString("Resources.TrainingSet"); //$NON-NLS-1$
		Node root = new Node();
		root.setRecords(CSVLoader.load(strFile));

		GenericTree<Node> tree = new GenericTree<Node>();
		tree.setRoot(root);

		tree = treeGrowth(tree);


		System.out.println(tree.getNumberOfNodes());
		//		System.out.println(tree.getRoot().getData().getLabel());


	}

}
