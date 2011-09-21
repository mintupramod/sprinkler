package hunt.data;

/**
 * @author Claudio Tanci
 * Label associated to records and decision tree nodes 
 *
 */
public class Label {
	
	String label;
	
	/**
	 * New label
	 * @param label
	 */
	public Label(String string) {
		this.label = string;
	}
	
	/**
	 * toString
	 * @return label as a string
	 */
	public String toString(){
		return this.label;
	}

}
