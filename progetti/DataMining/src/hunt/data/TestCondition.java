package hunt.data;

public class TestCondition implements java.io.Serializable {
	
	/**
	 * 
	 */
	public TestCondition() {
		super();
		this.idAttribute = 0;
		String[] v = {"x","o","b"};
		this.values = v;
	}

	private int idAttribute;
	
	private String[] values;

	public int getIdAttribute() {
		return idAttribute;
	}

	public void setIdAttribute(int idAttribute) {
		this.idAttribute = idAttribute;
	}

	public String[] getValues() {
		return values;
	}

	public void setValues(String[] values) {
		this.values = values;
	}

}
