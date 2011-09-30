package hunt.utilities;

import java.util.HashMap;

public class NormalDistributionBounds {
	
	/**
	 * 
	 * @param confidence requested (one of ".99" ".98" ".95" ".9" ".8" ".7" ".5")
	 * @return upper/lower bound obtained from a standard normal distribution
	 * @throws Exception 
	 */
	public static float getBound (float confidence) throws Exception {
		
		HashMap<Float, Float> table = new HashMap<Float, Float>();
		table.put((float) .99,(float) 2.58);
		table.put((float) .98,(float) 2.33);
		table.put((float) .95,(float) 1.96);
		table.put((float) .9,(float) 1.65);
		table.put((float) .8,(float) 1.28);
		table.put((float) .7,(float) 1.04);
		table.put((float) .5,(float) .67);
		
		if (table.containsKey(confidence)) {
			return table.get(confidence);
		} else {
			throw new Exception("confidence level not supported (must be one of \".99\" \".98\" \".95\" \".9\" \".8\" \".7\" \".5\")");
		}
		
	}

}
