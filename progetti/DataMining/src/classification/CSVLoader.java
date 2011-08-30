/*
 * Parse CSV File using StringTokenizer.
 * 
 */
package classification;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;

/**
 * @author Claudio Tanci
 * 
 */
public class CSVLoader {

	/**
	 * @param args
	 */
	public static ArrayList<ArrayList<String>> load(String strFile) {

		ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>(0);

		try {

			// create BufferedReader to read csv file
			BufferedReader br = new BufferedReader(new FileReader(strFile));
			String strLine = "";
			StringTokenizer st = null;

			// read comma separated file line by line
			while ((strLine = br.readLine()) != null) {

				// break comma separated line using ","
				st = new StringTokenizer(strLine, ",");

				ArrayList<String> attributes = new ArrayList<String>(0);

				while (st.hasMoreTokens()) {
					attributes.add(st.nextToken());
				}

				list.add(attributes);

			}

		} catch (Exception e) {
			System.out.println("Error reading csv file: " + e);
		}
		return list;
	}

	public static void main(String[] args) {
		test();
	}

	/**
	 * @param args
	 */
	public static void test() {
		String strFile = Bundle.getString("Resources.TrainingSet"); //$NON-NLS-1$

		ArrayList<ArrayList<String>> list = load(strFile);

		// display csv values
		Iterator<ArrayList<String>> it = list.iterator();
		while (it.hasNext()) {
			ArrayList<String> sample = it.next();
			System.out.println(sample);
		}

	}

}
