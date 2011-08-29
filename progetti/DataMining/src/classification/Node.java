/**
 * Implementa il tipo nodo dell'albero di decisione
 */
package classification;

import java.util.ArrayList;

/**
 * @author Claudio Tanci
 *
 */
public class Node {
	
	// il nodo è una foglia?
	Boolean leaf;
	// etichetta della classe del nodo (solo se leaf)
	String label;
	// condizione di test
	String testCondition;
	
	public String getTestCondition() {
		return testCondition;
	}

	public void setTestCondition(String testCondition) {
		this.testCondition = testCondition;
	}

	// lista degli oggetti contenuti nel nodo
	ArrayList<ArrayList<String>> data;
	

	/**
	 * costruisce un nodo a partire dalla lista del suo contenuto
	 * @param data
	 */
	public Node(ArrayList<ArrayList<String>> data) {
		super();
		this.data = data;
	}

	public ArrayList<ArrayList<String>> getData() {
		return data;
	}

	public void setData(ArrayList<ArrayList<String>> data) {
		this.data = data;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * 
	 */
	public Node() {
		// TODO Auto-generated constructor stub
	}

}
