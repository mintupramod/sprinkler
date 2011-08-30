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
	private Boolean leaf;
	// etichetta della classe del nodo (solo se leaf)
	private String label;
	// condizione di test
	private String testCondition;
	// dati (record) contenuti nel nodo in fase di costruzione
	private ArrayList<ArrayList<String>> data;
	
	/**
	 * costruisce un nodo nuovo vuoto
	 */
	public Node() {
		super();
		this.data = null;
		this.leaf = false;
		this.label = "";
		this.testCondition = ""; 
	}
	
	/**
	 * costruisce un nodo a partire dalla lista del suo contenuto
	 * @param data
	 */
	public Node(ArrayList<ArrayList<String>> data) {
		super();
		this.data = data;
		this.leaf = false;
		this.label = "";
		this.testCondition = ""; 
	}
	
	public Boolean isLeaf() {
		return leaf;
	}
	
	public void setLeaf(Boolean leaf) {
		this.leaf = leaf;
	}
	
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getTestCondition() {
		return testCondition;
	}

	public void setTestCondition(String testCondition) {
		this.testCondition = testCondition;
	}

	public ArrayList<ArrayList<String>> getData() {
		return data;
	}

	public void setData(ArrayList<ArrayList<String>> data) {
		this.data = data;
	}

}
