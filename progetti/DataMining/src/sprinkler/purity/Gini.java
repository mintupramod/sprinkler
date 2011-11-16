package sprinkler.purity;


import java.io.IOException;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import sprinkler.data.NominalRecord;
import sprinkler.data.RecordSet;
import sprinkler.utilities.Bundle;
import sprinkler.utilities.CSVLoader;


/**
 * @author Claudio Tanci
 * implements Gini index, a type of purity measure
 * 
 */
public class Gini implements PurityInterface {

	/**
	 * value
	 * @param a list of records
	 */
	public float value(RecordSet recordSet) {

//		ArrayList<TicTacToeRecord> list = new ArrayList<TicTacToeRecord>();
		
		// computing a list of labels and occurrences
		HashMap<String, Integer> labels = new HashMap<String, Integer>();
		
		for (NominalRecord record : recordSet.getRecords()) {
			String label = record.getLabel().toString();
			
			if (labels.containsKey(label)) {
				labels.put((label), labels.get(label) + 1);				
			} else {
				labels.put((label), 1);
			}
			
			
		}
		
		
		
//		Iterator<TicTacToeRecord> it = list.iterator();
//		while (it.hasNext()) {
//			TicTacToeRecord sample = it.next();
//			String label = sample.getLabel().toString();
//			if (labels.containsKey(label)) {
//				labels.put((label), labels.get(label) + 1);				
//			} else {
//				labels.put((label), 1);
//			}
			
			

//		}

		// Gini index computing (see "Introduction to Data Mining" eq.4.4, p.158)
		float gini = 1;
		int n = recordSet.size();
		for (String label : labels.keySet()) {
			gini = (float) (gini - Math.pow(( labels.get(label) / (float)n), 2));
		}
		
		// when no record with the appropriate value is present
		if (gini == 1) {gini = 0;}

		return gini;
	}

	/**
	 * main
	 * @param args
	 */
	public static void main(String[] args) {
		test();
	}

	/**
	 * test the Gini index operation
	 */
	public static void test() {
		String strFile = Bundle.getString("Resources.RecordSet"); //$NON-NLS-1$

		RecordSet list;
		try {
			list = CSVLoader.loadRecordSet(strFile);
			
			// compute and display the Gini index

			Gini gini = new Gini();
			System.out.println("Gini index for \""+strFile+"\" labels: "+gini.value(list));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}