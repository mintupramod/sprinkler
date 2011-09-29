package hunt;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.text.AttributedCharacterIterator.Attribute;
import java.util.ArrayList;
import java.util.Collection;

import hunt.data.Label;
import hunt.data.Node;
import hunt.data.NominalAttribute;
import hunt.data.RecordSet;
import hunt.data.TicTacToeRecord;
import hunt.data.TrainingTestCouple;
import hunt.data.Tree;
import hunt.utilities.Bundle;
import hunt.utilities.CSVLoader;

/**
 * @author Claudio Tanci
 * build and validate a decision tree
 *
 */
public class Test {
	
	public static void main(String[] args) {
		Test.testAccuracy(1000);
	}

	/**
	 * Calculate accuracy using .638 bootstrap method
	 * (see book)
	 * @param number of samples to take
	 */
	public static void testAccuracy(int samples) {
		
		// load record set for training
		String strFile = Bundle.getString("Resources.RecordSet"); //$NON-NLS-1$
		System.out.println("Loading file data " + strFile);
		RecordSet allRecordSet = new RecordSet();
		try {
			allRecordSet = CSVLoader.loadRecordSet(strFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(samples+" samples");
		
		// generating a decision tree from the training set
		Node root = new Node();

		root.setRecords(CSVLoader.load(strFile));

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
			trainingRoot.setRecordsR(couple.getTrainingSet());
			Tree trainingTree = new Tree();
			trainingTree.setRoot(trainingRoot);
			trainingTree = (Tree) GenericHunt.treeGrowth(trainingTree);
			
			// validate with test recordset for sample j
			double eI = 1 - trainingTree.validate(couple.getTestSet());
			
			// update average of accuracy
			//System.out.println(accBoot+" + (double) .632 * "+eI+ " (double) .638 * "+accS+")) / (double) "+samples);
			accBoot = (accBoot + (((double) .632 * eI + (double) .368 * accS)) / (double) samples);
			
		}
				
		System.out.println("Estimated accuracy "+accBoot);		
		System.out.println("Estimated error rate "+(1-accBoot));
		
		
		
		
		
		
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

}
