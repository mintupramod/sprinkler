package sprinkler;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.text.AttributedCharacterIterator.Attribute;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import sprinkler.data.Label;
import sprinkler.data.Node;
import sprinkler.data.NominalAttribute;
import sprinkler.data.NominalRecord;
import sprinkler.data.RecordSet;
import sprinkler.data.TrainingTestCouple;
import sprinkler.data.Tree;
import sprinkler.utilities.Bundle;
import sprinkler.utilities.CSVLoader;
import sprinkler.utilities.NormalDistributionBounds;
import sprinkler.utilities.Utils;

/**
 * @author Claudio Tanci
 * build and validate a decision tree
 *
 */
public class Validate {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		testPurityVsAccuracy();
	}
		
	/**
	 * 
	 */
	public static void testPurityVsAccuracy() {
//		String strFile = Bundle.getString("Resources.RecordSet"); //$NON-NLS-1$
		String strFile = "./data/tic-tac-toe/tic-tac-toe.data";
		float confidence = (float) .99;
		
		int samples = 10;
		
		float pstart = 0;
		float pstop = (float) 0.5;
		int steps = 6; 

		validate(strFile, samples, pstart, pstop, steps, confidence);
	}
	
	/**
	 * @param file to validate
	 * @param number of samples 
	 * @param pstart
	 * @param pstop
	 * @param steps
	 * @param confidence requested
	 */
	public static void validate(String strFile, int samples, float pstart, float pstop, int steps, float confidence) {
		float step = (pstop - pstart)/(float)(steps-1);
		float purity;

		System.out.println("Stopping condition"+"\t"+"Estimated accuracy"+"\t"+"Confidence Interval");
		for (purity = pstart; purity <= pstop; purity+=step) {
			testPurity(strFile, purity, confidence, samples);
		}
	}
	
	/**
	 * @param file to test
	 * @param purity 
	 * @param confidence requested
	 * @param number of samples
	 */
	public static void testPurity(String strFile, float purity, float confidence, int samples) {
		int records;
		try {
			records = CSVLoader.loadRecordSet(strFile).size();
			
			System.out.print(purity+"\t");
			double accBoot = Validate.bootstrapAccuracy(strFile, samples, (float) purity);
			System.out.print(accBoot+"\t");		
			
			try {
				ArrayList<Double> interval = confidenceInterval(confidence, accBoot, records);
				System.out.print(interval+"\t");
				System.out.println(confidenceAscii(interval, accBoot, 80));
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		} catch (IOException e1) {
			System.out.println("Error reading the file");
			e1.printStackTrace();
		}
	}
	
	/**
	 * 
	 */
	public static void testSingleAccuracy() {
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
			double accBoot = Validate.bootstrapAccuracy(strFile, samples, (float) purity);
			System.out.println("Estimated accuracy "+accBoot);		
			
			try {
				System.out.println("Confidence interval for "+confidence+" confidence "+confidenceInterval(confidence, accBoot, records));
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		} catch (IOException e1) {
			System.out.println("Error reading the file");
			e1.printStackTrace();
		}
	}

	/**
	 * Calculate accuracy using .632 bootstrap method
	 * (see Introduction to Data Mining book)
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
}
