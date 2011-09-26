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
	public NominalAttribute() {
		this.value = null;
		this.domain = null;
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

}
