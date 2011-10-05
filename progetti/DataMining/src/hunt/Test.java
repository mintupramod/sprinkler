package hunt;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.text.AttributedCharacterIterator.Attribute;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import hunt.data.Label;
import hunt.data.Node;
import hunt.data.NominalAttribute;
import hunt.data.RecordSet;
import hunt.data.TicTacToeRecord;
import hunt.data.TrainingTestCouple;
import hunt.data.Tree;
import hunt.utilities.Bundle;
import hunt.utilities.CSVLoader;
import hunt.utilities.NormalDistributionBounds;
import hunt.utilities.Utils;

/**
 * @author Claudio Tanci
 * build and validate a decision tree
 *
 */
public class Test {
	
	public static void main(String[] args) {
		
		float confidence = (float) .99;
		int samples = 100;
		String strFile = Bundle.getString("Resources.RecordSet"); //$NON-NLS-1$
		
		System.out.println("Loading file data " + strFile);
		
		int records;
		try {
			records = CSVLoader.loadRecordSet(strFile).size();
			
			System.out.println(records+" records in the set");
			
			System.out.println(samples+" samples");
			double accBoot = Test.bootstrapAccuracy(strFile, samples);
			System.out.println("Estimated accuracy "+accBoot);		
			
			try {
				System.out.println("Confidence interval for "+confidence+" confidence "+confidenceInterval(confidence, accBoot, records));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (IOException e1) {
			System.out.println("Impossibile leggere il file");
			e1.printStackTrace();
		}
		
	}

	/**
	 * Calculate accuracy using .638 bootstrap method
	 * (see book)
	 * @param number of samples to take
	 * @throws IOException 
	 */
	public static double bootstrapAccuracy(String strFile, int samples) throws IOException {
		
		// load record set for training
		RecordSet allRecordSet = new RecordSet();
		allRecordSet = CSVLoader.loadRecordSet(strFile);
		
		// generating a decision tree from the training set
		Node root = new Node();

		root.setRecords(CSVLoader.loadRecordSet(strFile));

		Tree allRecordsTree = new Tree();
		allRecordsTree.setRoot(root);

		allRecordsTree = (Tree) GenericHunt.treeGrowth(allRecordsTree);

		// accuracy for the training records
		double accS = 1 - allRecordsTree.validate(allRecordSet);
		
		// total accuracy
		double accBoot = 0;
		for (int j = 1; j <= samples; j++) {
			// accuracy of the sample i
			TrainingTestCouple couple = Evaluator.generateBootstrapSample(allRecordSet);

			// generate training tree for sample j
			Node trainingRoot = new Node();
			trainingRoot.setRecords(couple.getTrainingSet());
			Tree trainingTree = new Tree();
			trainingTree.setRoot(trainingRoot);
			trainingTree = (Tree) GenericHunt.treeGrowth(trainingTree);
			
			// validate with test recordset for sample j
			double eI = 1 - trainingTree.validate(couple.getTestSet());
			
			// update average of accuracy
			//System.out.println(accBoot+" + (double) .632 * "+eI+ " (double) .638 * "+accS+")) / (double) "+samples);
			accBoot = (accBoot + (((double) .632 * eI + (double) .368 * accS)) / (double) samples);
			
		}
				
		return accBoot;
		
	}
		
		
	/**
	 * Estimate a confidence interval for the accuracy
	 * (for the error due to the limited number of records, NOT from undersamplig with the bootstrap method) 
	 * (see book 4.6.1 - eq. 4.13)
	 * @param confidence requested (one of ".99" ".98" ".95" ".9" ".8" ".7" ".5")
	 * @param acc previously calculated
	 * @param number of samples
	 * @return confidence intervals
	 * @throws Exception 
	 */
	public static ArrayList<Double> confidenceInterval(float confidence, double acc, int n) throws Exception {
		
		ArrayList<Double> intervals = new ArrayList<Double>();
		
		float z = NormalDistributionBounds.getBound(confidence);
		
		double temp = z * Math.sqrt(Math.pow(z, 2) + 4 * n * acc - 4 * n * Math.pow(acc, 2));
		intervals.add(Utils.round(((2 * n * acc + z) + temp) / (2 * (n + Math.pow(z, 2))), 3));
		intervals.add(Utils.round(((2 * n * acc + z) - temp) / (2 * (n + Math.pow(z, 2))), 3));
		
		return intervals;
		
	}
		
		
		
//		// build the decision tree
//		GenericHunt.main(args);
//		
//		// test error ratio
//		// load the decision tree and classify the record
//		System.out.println("loading decision tree in "+Bundle.getString("Resources.SaveFileName"));
//		
//		FileInputStream f_in;
//		try {
//			f_in = new FileInputStream(Bundle.getString("Resources.SaveFileName"));
//
//			// Read object using ObjectInputStream
//			ObjectInputStream obj_in = new ObjectInputStream(f_in);
//
//			// Read the object
//			Object obj = obj_in.readObject();
//
//			if (obj instanceof Tree) {
//				// Cast object to Tree
//				Tree tree = (Tree) obj;
//				
//				// load validation set in a record set
//				RecordSet recordSet = new RecordSet(Bundle.getString("Resources.ValidationSet"));
//				
//				// validate the decision tree with the validation record set
//				System.out.println("error rate = "+tree.validate(recordSet));
//			}
//
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

}
