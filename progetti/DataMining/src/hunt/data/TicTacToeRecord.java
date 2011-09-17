package hunt.data;

import java.util.ArrayList;

public class TicTacToeRecord {
	
	ArrayList<NominalAttribute> attributes;
	Label label;
	
	public NominalAttribute getAttribute(int attribute) {
		return attributes.get(attribute);
	}
	
	public Label getLabel() {
		return label;
	}

	/**
	 * @param attributes
	 * @param label
	 */
	public TicTacToeRecord(ArrayList<NominalAttribute> attributes, Label label) {
		super();
		this.attributes = attributes;
		this.label = label;
	}

}
