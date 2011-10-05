package hunt.purity;

import hunt.data.RecordSet;

import java.util.ArrayList;

/**
 * @author Claudio Tanci
 * interface for purity index (disparity for a partition by an attribute)
 *
 */
public interface PurityInterface {
	
	float value(RecordSet records);
	
}
