package hunt.data;

import java.util.ArrayList;

/**
 * @author Claudio Tanci
 * This class implements a record of type TicTacToe
 *
 */
public class TicTacToeRecord {
	
	// attributes
	ArrayList<NominalAttribute> attributes;
	
	// label
	Label label;
	
	/**
	 * New TicTacToeRecord
	 * @param attributes
	 * @param label
	 */
	public TicTacToeRecord(ArrayList<NominalAttribute> attributes, Label label) {
		super();
		this.attributes = attributes;
		this.label = label;
	}
	
	/**
	 * getAttribute
	 * @param attribute id
	 * @return nominal attribute 
	 */
	public NominalAttribute getAttribute(int attribute) {
		return attributes.get(attribute);
	}
	
	/**
	 * getLabel
	 * @return label
	 */
	public Label getLabel() {
		return label;
	}

	/**
	 * toString
	 * @return record as a String
	 */
	public String toString() {
		return attributes.toString()+label.toString();
	}

}
