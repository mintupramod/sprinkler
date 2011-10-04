package hunt.utilities;

import hunt.data.Label;
import hunt.data.NominalAttribute;
import hunt.data.RecordSet;
import hunt.data.TicTacToeRecord;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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
	 * @throws IOException 
	 */
	public static RecordSet loadRecordSet(String strFile) throws IOException {
		
		// domain initialization
		ArrayList<ArrayList<String>> recordsDomains = new ArrayList<ArrayList<String>>();
		
		
		
		
		// TODO load domain method in the file
//		ArrayList<String> domain = new ArrayList<String>(0);
//		domain.add("x");
//		domain.add("o");
//		domain.add("b");
		
//		ArrayList<TicTacToeRecord> recordSet = new ArrayList<TicTacToeRecord>(0);
		RecordSet recordSet = new RecordSet();



			// create BufferedReader to read csv file
			BufferedReader br = new BufferedReader(new FileReader(strFile));
			String strLine = "";
			StringTokenizer st = null;

			// read comma separated file line by line
			while ((strLine = br.readLine()) != null) {
				
				//skip comment lines
				if (!strLine.substring(0).substring(0, 1).equals("#")) {
										
					// break comma separated line using ","
					st = new StringTokenizer(strLine, ",");

					ArrayList<NominalAttribute> attributes = new ArrayList<NominalAttribute>();
					
					// attributes (last token assumed to be a label)
					String l = "";
					
					// recordsDomains index
					int i = 0;
					
					while (st.hasMoreTokens()) {
						
						if (st.countTokens() > 1) {
							NominalAttribute attribute = new NominalAttribute(recordsDomains.get(i), st.nextToken());
							attributes.add(attribute);
						} else {
							l = st.nextToken();
						}
						
						i++;
					}
					
					Label label = new Label(l);

					TicTacToeRecord record = new TicTacToeRecord(attributes, label);
					
					recordSet.add(record);
					
				} else if (strLine.substring(0).substring(0, 3).equals("# {")) {
					// this is the record information line (defines attributes and domains)
					String line = strLine.substring(3, strLine.length()-1);
					for (String attribute : line.split(":")) {
						
						ArrayList<String> domain = new ArrayList<String>();
						
						for (String d : attribute.split(",")) {
							domain.add(d);
						}
						
						recordsDomains.add(domain);
						
					}
					
				}
			}



		return recordSet;
	}

//	/**
//	 * @deprecated
//	 * load
//	 * @param file name
//	 * return an ArrayList of record (deprecated)
//	 */
//	
//	public static ArrayList<ArrayList<String>> load(String strFile) {
//
//		ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>(0);
//
//		try {
//
//			// create BufferedReader to read csv file
//			BufferedReader br = new BufferedReader(new FileReader(strFile));
//			String strLine = "";
//			StringTokenizer st = null;
//
//			// read comma separated file line by line
//			while ((strLine = br.readLine()) != null) {
//				
//				//skip comment lines
//				if (!strLine.substring(0).substring(0, 1).equals("#")) {
//					
//					// break comma separated line using ","
//					st = new StringTokenizer(strLine, ",");
//
//					ArrayList<String> attributes = new ArrayList<String>(0);
//
//					while (st.hasMoreTokens()) {
//						attributes.add(st.nextToken());
//					}
//
//					list.add(attributes);
//					
//				}
//			}
//
//		} catch (Exception e) {
//			System.out.println("Error reading csv file: " + e);
//		}
//		return list;
//	}

	public static void main(String[] args) {
		test();
	}

	/**
	 * test
	 * @param args
	 */
	public static void test() {
		String strFile = Bundle.getString("Resources.RecordSet"); //$NON-NLS-1$

		RecordSet list = new RecordSet();
		try {
			list = loadRecordSet(strFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// display values
		for (TicTacToeRecord record : list.getRecords()) {
			System.out.println(record.toString());
		}

	}

}
