/**
 * Misura la disparità tra elementi di un insieme
 */
package hunt.purity;

import java.util.ArrayList;

/**
 * @author Claudio Tanci
 * Calcola la disparità tra sottoinsiemi per una partizone effettuata sull'attributo dato
 *
 */
public interface PurityInterface {
	
	float value(ArrayList<ArrayList<String>> list);
}
