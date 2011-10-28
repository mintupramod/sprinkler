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
import hunt.data.NominalRecord;
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
//		test();
		
		String strFile = Bundle.getString("Resources.RecordSet"); //$NON-NLS-1$
		float confidence = (float) .99;
//		float purity = (float) 0.1;
		float purity;
		int samples = 10;
		int steps = 5;
		
		System.out.println("Stopping condition"+"\t"+"Estimated accuracy"+"\t"+"Confidence Interval");
		for (int i = 1; i < steps+1; i++) {
			purity = (float) 0.5 * (float) i / (float) steps;
			testPurity(strFile, purity, confidence, samples);
		}
		
	}
	
	public static void testPurity(String strFile, float purity, float confidence, int samples) {
		
		int records;
		try {
			records = CSVLoader.loadRecordSet(strFile).size();
			
			System.out.print(purity+"\t");
			double accBoot = Test.bootstrapAccuracy(strFile, samples, (float) purity);
			System.out.print(accBoot+"\t");		
			
			try {
				ArrayList<Double> interval = confidenceInterval(confidence, accBoot, records);
				System.out.print(interval+"\t");
				System.out.println(confidenceAscii(interval, accBoot, 100));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (IOException e1) {
			System.out.println("Impossibile leggere il file");
			e1.printStackTrace();
		}
		
	}
	
	public static void test() {
		
		float confidence = (float) .99;
		float purity = (float) 0.1;
		int samples = 100;
		String strFile = Bundle.getString("Resources.RecordSet"); //$NON-NLS-1$
		
		System.out.println("Loading file data " + strFile);
		
		int records;
		try {
			records = CSVLoader.loadRecordSet(strFile).size();
			
			System.out.println(records+" records in the set");
			System.out.println("Stopping condition: purity value < "+purity);
			System.out.println(samples+" samples");
			double accBoot = Test.bootstrapAccuracy(strFile, samples, (float) purity);
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
	 * @param purity required for stopping splitting
	 * @throws IOException 
	 */
	public static double bootstrapAccuracy(String strFile, int samples, float purity) throws IOException {
		
		// load record set for training
		RecordSet allRecordSet = new RecordSet();
		allRecordSet = CSVLoader.loadRecordSet(strFile);
		
		// generating a decision tree from the training set
		Node root = new Node();

		root.setRecords(CSVLoader.loadRecordSet(strFile));

		Tree allRecordsTree = new Tree();
		allRecordsTree.setRoot(root);

		allRecordsTree = (Tree) GenericHunt.treeGrowth(allRecordsTree, (float) purity);

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
			trainingTree = (Tree) GenericHunt.treeGrowth(trainingTree, (float) purity);
			
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
	 * (see Introduction to Data Mining eq. 4.13 p.190)
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
		intervals.add(Utils.round(((2 * n * acc + z) - temp) / (2 * (n + Math.pow(z, 2))), 3));
		intervals.add(Utils.round(((2 * n * acc + z) + temp) / (2 * (n + Math.pow(z, 2))), 3));
		
		return intervals;
		
	}
	
	/**
	 * ASCII graph from an interval
	 * @param interval from confidenceInterval
	 * @param accuracy
	 * @param lenght of the string
	 * @return 
	 */
	public static String confidenceAscii(ArrayList<Double> interval, double accuracy, int length) {
		String graph = "";
		int a = (int) Math.floor((interval.get(0) * length));
		int b = (int) Math.floor((interval.get(1) * length));
		int x = (int) Math.floor((accuracy * length));
		
		for (int i = 0; i < length; i++) {
			if ((i == a) || (i == b)) {graph = graph+"|";}
			else if (i == x) {graph = graph+"*";}
			else {graph = graph+"-";}
		}
		
		return graph;		
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
