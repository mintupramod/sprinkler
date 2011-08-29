/**
 * 
 */
package classification;

import java.util.ArrayList;


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
	 * @param ArrayList<ArrayList<String>>
	 * @return boolean
	 */
	private static boolean stoppingCondition(ArrayList<ArrayList<String>> list){

		return true;
	}

	/** 
	 * classify()
	 * determina l'etichetta di classe da assegnare a un nodo foglia dell'albero
	 * 
	 * @param ArrayList<ArrayList<String>>
	 * @return String
	 */
	private String classify(ArrayList<ArrayList<String>> list){

		return null;
	}

	/** 
	 * findBestSplit()
	 * seleziona l'attributo su cui splittare il nodo dell'albero 
	 * e ne ritorna l'indice
	 * 
	 * @param ArrayList<ArrayList<String>>
	 * @return int
	 */
	private int findBestSplit(ArrayList<ArrayList<String>> list){

		return 0;
	}

	/** 
	 * createNode()
	 * estende l'albero creando un nuovo nodo
	 * 
	 * @param ArrayList<ArrayList<String>>
	 * @return GenericTreeNode
	 */
	private GenericTreeNode<Node> createNode(ArrayList<ArrayList<String>> list){

		return null;
	}

	/** 
	 * createNode()
	 * estende l'albero creando un nuovo nodo
	 * 
	 * @param ArrayList<ArrayList<String>>
	 * @return GenericTreeNode
	 */
	private GenericTreeNode<Node> createNode(GenericTreeNode<Node> node){

		return null;
	}
	
	
	/** 
	 * treeGrowth()
	 * costruisce l'albero di induzione
	 * 
	 * @param ArrayList<ArrayList<String>>
	 * @return GenericTreeNode
	 */
	public static GenericTreeNode treeGrowth(ArrayList<ArrayList<String>> list){

		if (stoppingCondition()) {


		} else {

		}




		return null;
	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		// file dati
		String strFile = "./data/tic-tac-toe/tic-tac-toe.data";
		GenericTreeNode tree = treeGrowth(CSVLoader.load(strFile));
		

	}

}
