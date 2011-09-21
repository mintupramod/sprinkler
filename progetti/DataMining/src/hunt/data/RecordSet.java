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
		this.records = null;
	}
	
	/**
	 * New record set from file
	 * @param file name
	 */
	public RecordSet(String fileName){
		records = CSVLoader.loadRecordSet(fileName);
		
	}
	
	/**
	 * getRecords
	 * @return an ArrayList of records
	 */
	public ArrayList<TicTacToeRecord> getRecords() {
		return records;
	}

	/**
	 * add
	 * @param add a record to the record set
	 */
	public void add(TicTacToeRecord record) {
		records.add(record);
		
	}

}
