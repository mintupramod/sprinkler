package hunt.data;

import java.util.ArrayList;

/**
 * @author Claudio Tanci
 * This class implements the attribute type "nominal" 
 *
 */
public class NominalAttribute {
	
	String value;
	ArrayList<String> domain;
	
	/**
	 * new NominalAttribute
	 * @param attribute 
	 */
	public NominalAttribute(ArrayList<String> domain, String value) {
		this.value = value;
		this.domain = domain;
	}

	/**
	 * new NominalAttribute 
	 */
	public NominalAttribute(ArrayList<String> domain) {
		this.value = null;
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
	 * getValue
	 * @return value of the attribute
	 */
	public String getValue() {
		return this.value;
	}
	
	/**
	 * setValue
	 * @param value of the attribute
	 */
	public void setValue(String string) {
		this.value = string;
	}
	
	/**
	 * toString
	 * @return attribute as a string
	 */
	public String toString(){
		return this.value;
	}

}
