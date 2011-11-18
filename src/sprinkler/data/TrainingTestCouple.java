package sprinkler.data;

/**
 * @author Claudio Tanci
 *
 */
public class TrainingTestCouple {
	
	RecordSet trainingSet;
	RecordSet testSet;

	/**
	 * @param trainingSet
	 * @param testSet
	 */
	public TrainingTestCouple(RecordSet trainingSet, RecordSet testSet) {
		this.trainingSet = trainingSet;
		this.testSet = testSet;
	}
	
	/**
	 * @return
	 */
	public RecordSet getTrainingSet() {
		return this.trainingSet;
	}
	
	/**
	 * @return
	 */
	public RecordSet getTestSet() {
		return this.testSet;
	}

}
