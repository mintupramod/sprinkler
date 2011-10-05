package hunt.data;

import java.util.ArrayList;

/**
 * @author Claudio Tanci
 * Label associated to records and decision tree nodes 
 *
 */
public class Label {
	
	String label;
	ArrayList<String> domain;
	
	/**
	 * New label
	 * @param label
	 */
	public Label(ArrayList<String> domain, String string) {
		this.label = string;
		this.domain = domain;
	}
	
	/**
	 * getDomain
	 * @return domain of the attribute
	 */
	public ArrayList<String> getDomain() {
		return this.domain;
	}
	
	/**
	 * setDomain
	 * @param ArrayList of possible values
	 */
	public void setDomain(ArrayList<String> domain) {
		this.domain = domain;
	}
	
	/**
	 * toString
	 * @return label as a string
	 */
	public String toString(){
		return this.label;
	}

}
