package hunt;

import java.util.ArrayList;

import hunt.data.RecordSet;
import hunt.data.NominalRecord;
import hunt.data.TrainingTestCouple;
import hunt.utilities.Bundle;

public class Evaluator {
	
//	RecordSet trainingSet;
//	RecordSet sampleSet;
//	
//	/**
//	 * @param trainingSet
//	 */
//	public Evaluator(RecordSet trainingSet) {
//		super();
//		this.trainingSet = trainingSet;
//		
//	}

	/**
	 * Generate a sample set with replacement
	 * @param training set
	 * @return sample set
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
