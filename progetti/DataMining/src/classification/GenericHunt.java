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
		// se tutti i record del nodo hanno la stessa etichetta lo considero una foglia
		ArrayList<ArrayList<String>> list = node.getData();		
		
		HashSet<String> labels = new HashSet<String>();
		
		Iterator<ArrayList<String>> it = list.iterator();
		while (it.hasNext()) {
			ArrayList<String> sample = it.next();
			labels.add(sample.get(sample.size()-1));
		}
		
		if (labels.size() == 1) {
			return true;			
		} else {
			return false;
		}
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
				ArrayList<ArrayList<String>> list = node.getData();
				
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
	 * @param GenericTreeNode<Node>
	 * @return int
	 */
	private static String findBestSplit(Node node){
		node.getData();

		return null;
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
	public static GenericTreeNode treeGrowth(Node node){

		if (stoppingCondition(node)) {
			// Il nodo considerato è una foglia
			node.setLeaf(true);
			node.setLabel(classify(node));
		} else {
			// Il nodo deve essere "splittato"
			node.setTestCondition(findBestSplit(node));
			
			
			

		}




		return null;
	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		// file dati
		String strFile = "./data/tic-tac-toe/tic-tac-toe.data";
		Node root = new Node();
		root.setData(CSVLoader.load(strFile));
		GenericTreeNode<Node> tree = new GenericTreeNode<Node>(root);
		treeGrowth(root);
		

	}

}
