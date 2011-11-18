package sprinkler.purity;


import java.util.ArrayList;

import sprinkler.data.RecordSet;

/**
 * @author Claudio Tanci
 * interface for purity index (disparity for a partition by an attribute)
 *
 */
public interface PurityInterface {
	float value(RecordSet records);
}
