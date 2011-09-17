package hunt.purity;

import hunt.utilities.Bundle;
import hunt.utilities.CSVLoader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;


/**
 * @author Claudio Tanci
 * 
 */
public class Gini implements PurityInterface {

	public float value(ArrayList<ArrayList<String>> list) {

		// computing a list of labels and occurrences
		HashMap<String, Integer> labels = new HashMap<String, Integer>();

		Iterator<ArrayList<String>> it = list.iterator();
		while (it.hasNext()) {
			ArrayList<String> sample = it.next();
			String label = sample.get(sample.size() - 1);
			if (labels.containsKey(label)) {
				labels.put((label), labels.get(label) + 1);				
			} else {
				labels.put((label), 1);
			}

		}

		// Gini index computing (Introduction to Data Mining eq.4.4, p.158)
		float gini = 1;
		int n = list.size();
		for (String label : labels.keySet()) {
			gini = (float) (gini - Math.pow(((float)labels.get(label) / (float)n), 2));
		}

		return gini;
	}

	public static void main(String[] args) {
		test();
	}

	public static void test() {
		String strFile = Bundle.getString("Resources.TrainingSet"); //$NON-NLS-1$

		ArrayList<ArrayList<String>> list = CSVLoader.load(strFile);

		// compute and display the Gini index
		
		Gini gini = new Gini();
		System.out.println("Gini index for \""+strFile+"\" labels: "+gini.value(list));

	}

}