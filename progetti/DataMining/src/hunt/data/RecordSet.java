package hunt.data;

import hunt.utilities.CSVLoader;

import java.util.ArrayList;

public class RecordSet {
	
	// recordSet
	private ArrayList<TicTacToeRecord> records;
	
	
	public ArrayList<TicTacToeRecord> getRecords() {
		return records;
	}

	public RecordSet(){
		this.records = null;
	}
	
	public RecordSet(String fileName){
		records = CSVLoader.loadRecordSet(fileName);
		
	}

	public void add(TicTacToeRecord record) {
		records.add(record);
		
	}

}
