package sprinkler.utilities;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;

import sprinkler.data.Label;
import sprinkler.data.NominalAttribute;
import sprinkler.data.NominalRecord;
import sprinkler.data.RecordSet;

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
		return loadRecordSet(new File(strFile));
	}

	/**
	 * loadRecordSet
	 * @param file
	 * return an ArrayList of records 
	 * @throws IOException 
	 */
	public static RecordSet loadRecordSet(File file) throws IOException {
		
		// domain initialization
		ArrayList<ArrayList<String>> recordsDomains = new ArrayList<ArrayList<String>>();
		
		RecordSet recordSet = new RecordSet();

			// create BufferedReader to read csv file
			BufferedReader br = new BufferedReader(new FileReader(file));
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
							// attribute initialization
							NominalAttribute attribute = new NominalAttribute(recordsDomains.get(i), st.nextToken());
							attributes.add(attribute);
						} else {
							// label initialization
							l = st.nextToken();		
						}
						
						i++;
					}
					
					Label label = new Label(recordsDomains.get(recordsDomains.size()-1), l);

					NominalRecord record = new NominalRecord(attributes, label);
					
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

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		test();
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
			e.printStackTrace();
		}

		// display values
		for (NominalRecord record : list.getRecords()) {
			System.out.println(record.toString());
		}
	}
}
