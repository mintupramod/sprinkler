package hunt.data;

public class TrainingTestCouple {
	
	RecordSet trainingSet;
	RecordSet testSet;

	public TrainingTestCouple(RecordSet trainingSet, RecordSet testSet) {
		this.trainingSet = trainingSet;
		this.testSet = testSet;
	}
	
	public RecordSet getTrainingSet() {
		return this.trainingSet;
	}
	
	public RecordSet getTestSet() {
		return this.testSet;
	}

}
