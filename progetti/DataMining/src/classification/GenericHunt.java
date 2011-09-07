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
		// NB gini = 0 or gini = 1
		
//		System.out.println(node.getPurity());
		
//		if (node.getPurity() == 0 || node.getPurity() == 1) {
		if (node.getPurity() < 0.3 || node.getPurity() > 0.7 || node.size() < 4) {
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
	 * label()
	 * determina l'etichetta di classe da assegnare a un nodo foglia dell'albero
	 * 
	 * @param Node
	 * @return String
	 */
	private static String label(Node node){
		// determino l'etichetta adatta al nodo dall'etichetta più ricorrente dei suoi record
		ArrayList<ArrayList<String>> records = node.getRecords();
		
		ArrayList<String> list = new ArrayList<String>();
		
		for (ArrayList<String> record : records) {
			list.add(record.get(record.size()-1));
			
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
		// NB implemented only the multiway split, hardcoded "x","o","b"
		int size = node.getRecords().size();
		String[] values = {"x","o","b"};
		int bestSplit = 0;
		float bestAvg = 1;
		for (int i = 0; i < 9; i++) {
			// computing the weighted average index
			
			float avg = 0; 
			for (String value : values) {
				float valuePurity = (float) split(node, i, value).getPurity();
				int valueNumber = split(node, i, value).size();
				
//				System.out.println("size "+i+" "+value+": "+valueNumber);
				
				//TODO
				
				
				avg = (float) avg +	(float) valueNumber / (float) size * valuePurity;
				
//				System.out.println("attributo "+i+" bestAvg "+bestAvg+" Avg "+avg+" pur/numb"+valuePurity+"/"+valueNumber);
				

				
			}
			
			if (avg < bestAvg) {
				bestAvg = avg;
				bestSplit = i;
				
			}
			
		}

		TestCondition testCondition = new TestCondition();
		testCondition.setIdAttribute(bestSplit);
		testCondition.setValues(values);
		
//		System.out.println("best split "+testCondition.getIdAttribute());

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
	public static Tree treeGrowth(Tree tree){

		Node node = (Node) tree.getRoot();

		if (stoppingCondition(node)) {
			// node is a leaf
//			System.out.println("LEAF");
			node.setLeaf(true);
			node.setLabel(label(node));
			
		} else {
			// splitting the node
			TestCondition bestSplit = findBestSplit(node);
			
			TestCondition testCondition = new TestCondition();
			testCondition.setValues(bestSplit.getValues());
			testCondition.setIdAttribute(bestSplit.getIdAttribute());
			
			node.setTestCondition(testCondition);

			for (String value : findBestSplit(node).getValues()) {

				Node child = split(node, bestSplit.getIdAttribute(), value);
				
				
				// ok only for single value split!
				String[] values = {value};
				testCondition.setValues(values);
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
		
//		testClassify();
//		System.exit(0);

		// file dati
		String strFile = Bundle.getString("Resources.TrainingSet"); //$NON-NLS-1$
		Node root = new Node();
		root.setRecords(CSVLoader.load(strFile));

		//GenericTree<Node> tree = new GenericTree<Node>();
		Tree tree = new Tree();
		tree.setRoot(root);

		tree = (Tree) treeGrowth(tree);

		System.out.println("numero di nodi "+tree.getNumberOfNodes());
		
		System.out.println(tree.toString());
		
		tree.toDot("data/tree.gv");
		
//		System.out.println(tree.getRoot().toString());
		
//		System.out.println(tree.build(GenericTreeTraversalOrderEnum.PRE_ORDER));
		
//		for (GenericTreeNode<Node> node : tree.build(GenericTreeTraversalOrderEnum.PRE_ORDER)) {
//			System.out.println(node.getData());
			
			//			if (node.getData().getTestAttribute()==0) {
//				System.out.println(node.getData().getTestAttribute());			
//			}
			
//		}
		
		
		
//		for (Iterator<GenericTreeNode<Node>> iterator = tree.getRoot().getChildren().iterator(); iterator.hasNext();) {
//			Node node = (Node) iterator.next();
//			System.out.println(node.getTestCondition());
//			System.out.println(node.getLabel());
//		}

	}
	
	private static void testSplit() {
		
		String strFile = Bundle.getString("Resources.TrainingSet"); //$NON-NLS-1$
		
		Node root = new Node();
		root.setRecords(CSVLoader.load(strFile));
		
		System.out.println("Split test");

		String[] values = {"x","o","b"};
		
		int tot = 0;
		for (String value : values) {
			
			Node node = split(root, 1, value);
			
			System.out.println("value "+value+" "+node.size()+", purity "+node.getPurity());
			tot = tot + node.getRecords().size();
		}
		
		System.out.println("____________");
		System.out.println("records "+tot);
	
		
	}
	
	private static void testClassify() {
		
		String strFile = Bundle.getString("Resources.TrainingSet"); //$NON-NLS-1$
		
		Node root = new Node();
		root.setRecords(CSVLoader.load(strFile));
		
		System.out.println("Classify test");
		
		System.out.println("Node label: "+label(root));
	
		
	}
	
}
