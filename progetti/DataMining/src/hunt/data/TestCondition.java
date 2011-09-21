package hunt.data;

/**
 * @author Claudio Tanci
 * This class implements the test condition 
 *
 */
public class TestCondition implements java.io.Serializable {
	
	// attribute
	private int idAttribute;
	
	// values
	private String[] values;
	
	/**
	 * New test condition
	 */
	public TestCondition() {
		super();
		this.idAttribute = 0;
		String[] v = {"x","o","b"};
		this.values = v;
	}

	/**
	 * getIdAttribute
	 * @return attribute id
	 */
	public int getIdAttribute() {
		return idAttribute;
	}

	/**
	 * setIdAttribute
	 * @param attribute id
	 */
	public void setIdAttribute(int idAttribute) {
		this.idAttribute = idAttribute;
	}

	/**
	 * getValues
	 * @return values
	 */
	public String[] getValues() {
		return values;
	}

	/**
	 * setValues
	 * @param values
	 */
	public void setValues(String[] values) {
		this.values = values;
	}

}
