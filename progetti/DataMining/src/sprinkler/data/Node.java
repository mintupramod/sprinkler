package sprinkler.data;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import sprinkler.purity.Gini;
import vivin.GenericTreeNode;

/**
 * @author Claudio Tanci
 * This class implements the single nodes of the decision tree 
 *
 */
public class Node extends GenericTreeNode<Node> {
	
	// counter
	static int counter = 0;

	// name
	private String name;
	
	// is the node a leaf?
	private Boolean leaf;
	
	// label associated with the node
	private String label;
	
	// test condition
	private TestCondition testCondition;

	// records associated with the node while building the tree
	private RecordSet records;
	
	/**
	 * New node
	 */
	public Node() {
		super();
		this.name=Integer.toString(counter++);
		this.records = null;
		this.leaf = false;
		this.label = "";
		this.testCondition = new TestCondition(); 
	}
	
	/**
	 * New node from its records
	 * @param data
	 */
	public Node(RecordSet data) {
		super();
		this.name=Integer.toString(counter++);
		this.records = data;
		this.leaf = false;
		this.label = "";
		this.testCondition = new TestCondition();
	}
	
	/**
	 * getPurity
	 * @return a purity measure of the records
	 */
	public float getPurity() {
		Gini gini = new Gini();
		return gini.value(this.records);
	}
	
	/**
	 * size
	 * @return number of records
	 */
	public int size() {
		return this.records.size();
	}

	/**
	 * howMany
	 * @param attribute
	 * @param value
	 * @return how many records in the node have attribute = value
	 */
	public int howMany(int attribute, String value) {
		int count = 0;

		for (NominalRecord record : this.records.getRecords()) {
			if (record.getAttribute(attribute).toString().equals(value)) {
				count++;
			}
			
		}
		
		return count;
	}
	
	/**
	 * isLeaf
	 * @return true if the node is a leaf, false otherwise
	 */
	public Boolean isLeaf() {
		return leaf;
	}
	
	/**
	 * setLeaf
	 * @param boolean
	 */
	public void setLeaf(Boolean leaf) {
		this.leaf = leaf;
	}
	
	/**
	 * getLabel
	 * @return label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * setLabel
	 * @param label
	 */
	public void setLabel(String label) {
		this.label = label;
	}
	
	/**
	 * getTestAttribute
	 * @return id test number
	 */
	public int getTestAttribute() {
		return this.testCondition.getIdAttribute();
	}

	/**
	 * setTestAttribute
	 * @param id test number
	 */
	public void setTestAttribute(int testAttribute) {
		this.testCondition.setIdAttribute(testAttribute);
	}

	/**
	 * getTestCondition
	 * @return test condition
	 */
	public TestCondition getTestCondition() {
		return testCondition;
	}

	/**
	 * setTestCondition
	 * @param test condition
	 */
	public void setTestCondition(TestCondition testConditions) {
		this.testCondition = testConditions;
	}

	/**
	 * getRecords
	 * @return records
	 */
	public RecordSet getRecords() {
		return records;
	}
	
	/**
	 * setRecords
	 * @param records
	 */
	public void setRecords(RecordSet records) {
		this.records = records;
	}

	/**
	 * getCounter
	 * @return counter
	 */
	public int getCounter() {
    	return this.counter;    	
    }
	
	/**
	 * getName
	 * @return name of the node
	 */
    public String getName() {
    	return this.name;    	
    }
    
    /**
	 * toString
	 * @return node to string
	 */
    public String toString() {
    	return "Test Attr "+getTestAttribute()+" - Test Values "+Arrays.toString(getTestCondition().getValues())+" "+size()+" records  isLeaf="+isLeaf()+" "+getLabel();    	
    }
}
