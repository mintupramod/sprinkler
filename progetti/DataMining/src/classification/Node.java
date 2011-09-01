/**
 * Implementa il tipo nodo dell'albero di decisione
 */
package classification;

import java.util.ArrayList;
import java.util.Iterator;

import utilities.Gini;
import vivin.GenericTreeNode;

/**
 * @author Claudio Tanci
 *
 */
public class Node extends GenericTreeNode<Node> {
	
	// il nodo è una foglia?
	private Boolean leaf;
	// etichetta della classe del nodo (solo se leaf)
	private String label;
	// condizione di test
	private TestCondition testCondition;

	// dati (record) contenuti nel nodo in fase di costruzione
	private ArrayList<ArrayList<String>> records;
	
	/**
	 * costruisce un nodo nuovo vuoto
	 */
	public Node() {
		super();
		this.records = null;
		this.leaf = false;
		this.label = "";
		this.testCondition = new TestCondition(); 
	}
	
	/**
	 * costruisce un nodo a partire dalla lista del suo contenuto
	 * @param data
	 */
	public Node(ArrayList<ArrayList<String>> data) {
		super();
		this.records = data;
		this.leaf = false;
		this.label = "";
		this.testCondition = new TestCondition();
	}
	
	public float getPurity() {
		Gini gini = new Gini();		
		return gini.value(this.records);
	}
	
	public int size() {
		return this.records.size();
	}

	public int howMany(int attribute, String value) {
		int count = 0;
		
		Iterator<ArrayList<String>> it = this.records.iterator();
		while (it.hasNext()) {
			ArrayList<String> sample = it.next();
			if (sample.get(attribute) == value) {
				count++;
			}
		}
		
		return count;
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
	
	public int getTestAttribute() {
		return this.testCondition.getIdAttribute();
	}

	public void setTestAttribute(int testAttribute) {
		this.testCondition.setIdAttribute(testAttribute);
	}

	public String[] getTestCondition() {
		return testCondition.getValues();
	}

	public void setTestCondition(String[] testConditions) {
		this.testCondition.setValues(testConditions);
	}

	public ArrayList<ArrayList<String>> getRecords() {
		return records;
	}

	public void setRecords(ArrayList<ArrayList<String>> records) {
		this.records = records;
	}

}
