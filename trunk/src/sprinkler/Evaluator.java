package sprinkler;

import java.util.ArrayList;

import sprinkler.data.NominalRecord;
import sprinkler.data.RecordSet;
import sprinkler.data.TrainingTestCouple;
import sprinkler.utilities.Bundle;


public class Evaluator {
	
	/**
	 * Generate a sample set with replacement
	 * @param record set
	 * @return training-test coupled sets
	 */
	public static TrainingTestCouple generateBootstrapSample(RecordSet recordSet) {
		
		RecordSet trainingSet = new RecordSet();
		RecordSet testSet = new RecordSet();
		
		// building a new training set with the same size of the training set 
		for (int j = 0; j < recordSet.size()-1; j++) {
			// select a random record and add to the sample set
			int i = (int) Math.floor(Math.random() * recordSet.size());
			trainingSet.add(recordSet.getRecord(i));
		}
		
		// building the coupled test set 
		for (NominalRecord record : recordSet.getRecords()) {
			if (! trainingSet.contains(record)) {
				testSet.add(record);
			}
			
		}
		
		TrainingTestCouple sets = new TrainingTestCouple(trainingSet, testSet);
		return sets;
	}

}
