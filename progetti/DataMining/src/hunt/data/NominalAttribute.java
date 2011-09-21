package hunt.data;

/**
 * @author Claudio Tanci
 * This class implements the attribute type "nominal" 
 *
 */
public class NominalAttribute {
	
	String value;
	
	/**
	 * new NominalAttribute
	 * @param attribute 
	 */
	public NominalAttribute(String string) {
		this.value = string;
	}

	/**
	 * new NominalAttribute 
	 */
	public NominalAttribute() {
		this.value = null;
	}
	
	/**
	 * setValue
	 * @param value of the attribute
	 */
	public void setValue(String string) {
		this.value = string;
	}

}
