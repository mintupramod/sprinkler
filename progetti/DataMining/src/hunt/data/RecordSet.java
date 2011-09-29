package hunt.data;

import hunt.utilities.CSVLoader;

import java.util.ArrayList;

/**
 * @author Claudio Tanci
 * This class implements a set of records 
 *
 */
public class RecordSet {
	
	// recordSet
	private ArrayList<TicTacToeRecord> records;

	/**
	 * New record set
	 */
	public RecordSet(){
		this.records = new ArrayList<TicTacToeRecord>(0);
	}
	
	/**
	 * New record set from file
	 * @param file name
	 */
//	public RecordSet(String fileName){
//		records = CSVLoader.loadRecordSet(fileName);
//		
//	}
	
	/**
	 * getRecords
	 * @return an ArrayList of records
	 */
	public ArrayList<TicTacToeRecord> getRecords() {
		return records;
	}

	/**
	 * getRecord
	 * @param int
	 * @return record required
	 */
	public TicTacToeRecord getRecord(int i) {
		return records.get(i);
	}
	
	/**
	 * contains
	 * @param record
	 * @return true if record exists in the recordset
	 */
	public boolean contains(TicTacToeRecord record) {
		return records.contains(record);
	}
	
	/**
	 * add
	 * @param add a record to the record set
	 */
	public void add(TicTacToeRecord record) {
		records.add(record);
		
	}
	
	/**
	 * size
	 * @return size of the RecordSet
	 */
	public int size() {
		return records.size();
		
	}
	
	/**
	 * TODO pastrocchio
	 * @deprecated
	 */
	public ArrayList<ArrayList<String>> toArray() {
		ArrayList<ArrayList<String>> array = new ArrayList<ArrayList<String>>(0);
		for (TicTacToeRecord record : records) {
			ArrayList<String> r = new ArrayList<String>(0);
			for (int j = 1; j < record.attributes.size(); j++) {
				r.add(record.getAttribute(j-1).value);
			}
			r.add(record.getLabel().label);
			
			array.add(r);
		}
		
		return array;
	}

}
