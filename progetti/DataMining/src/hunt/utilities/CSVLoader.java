package hunt.utilities;

import hunt.data.Label;
import hunt.data.NominalAttribute;
import hunt.data.RecordSet;
import hunt.data.TicTacToeRecord;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;

/**
 * @author Claudio Tanci
 * load a record set from a CSV File
 * 
 */
public class CSVLoader {

	/**
	 * loadRecordSet
	 * @param file name
	 * return an ArrayList of records 
	 */
	public static ArrayList<TicTacToeRecord> loadRecordSet(String strFile) {
		
		ArrayList<TicTacToeRecord> recordSet = new ArrayList<TicTacToeRecord>(0);

		try {

			// create BufferedReader to read csv file
			BufferedReader br = new BufferedReader(new FileReader(strFile));
			String strLine = "";
			StringTokenizer st = null;

			// read comma separated file line by line
			while ((strLine = br.readLine()) != null) {

				// break comma separated line using ","
				st = new StringTokenizer(strLine, ",");

				ArrayList<NominalAttribute> attributes = new ArrayList<NominalAttribute>(0);
				
				// attributes (last token assumed to be a label)
				String l = "";
				
				while (st.hasMoreTokens()) {
					
					if (st.countTokens() > 1) {
						NominalAttribute attribute = new NominalAttribute(st.nextToken());
						attributes.add(attribute);
					} else {
						l = st.nextToken();
					}
					
				}
				
				Label label = new Label(l);

				TicTacToeRecord record = new TicTacToeRecord(attributes, label);
				
				recordSet.add(record);

			}

		} catch (Exception e) {
			System.out.println("Error reading csv file: " + e);
		}
		return recordSet;
	}

	/**
	 * @deprecated
	 * load
	 * @param file name
	 * return an ArrayList of record (deprecated)
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
	 * test
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
